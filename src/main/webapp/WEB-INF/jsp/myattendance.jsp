<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>

<SCRIPT LANGUAGE="JavaScript">
    //一般直接写在一个js文件中
    layui.use(['element','table','form','layer','laydate'], function(){
        var element = layui.element;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var laydate = layui.laydate;
        //第一个实例
        laydate.render({
            elem: '#daterange' //指定元素
            ,type: 'datetime'
            ,range: true
        });
        tableIns =table.render({
            elem: '#demo'
            ,url: 'myattendance/json' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'employeeNumber', title: '签到人工号'}
                ,{field: 'employeeName', title: '签到人姓名'}
                ,{field: 'starttime', title: '上班时间'}
                ,{field: 'endtime', title: '下班时间'}
                ,{field: 'checkin', title: '打卡时间'}
                ,{field: 'checkout', title: '退卡时间'  }
                ,{field: 'clazzName', title: '班次'  }
                ,{field: 'type', title: '类型'  }
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                onEditBtn(data.id);
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if(layEvent === 'check'){ //编辑

                //table.reload("demo",{});
            }
        });

        form.on('submit(save)', function (data) {
            var page = "/myleavee";
            $.post(
                page,
                {"daterange":$("#daterange").val(),"notes":$("#addnotes").val()},
                function(result) {
                    console.log("f"+result);
                    if ("success" == result) {
                        tableIns.reload( {});
                        layer.close(addIndex);
                    }
                    else {
                        console.log(result);
                        layer.msg(result);
                    }
                }
            );
            return false;

        });
    });

    function onAddBtn(){
        //页面层-自定义
        addIndex= layer.open({
            type: 1,
            title:"新建配置",
            closeBtn: false,
            shift: 2,
            area: ['530px', '420px'],
            shadeClose: true,
            // btn: ['新增', '取消'],
            // btnAlign: 'c',
            content: $("#add-main"),
            success: function(layero, index){},
            yes:function(){

            }
        });
        //form.render();
    };

    function onEditBtn(lid){
        //页面层-自定义
        var url="leavee/"+lid+"/json";
        $.post(
            url,
            function(data) {
                console.log(data);
                var json=JSON.parse(data);
                $("#editdaterange").val(json.start+" - "+json.end);
                $("#editnotes").val(json.leavee.notes);
                editIndex= layer.open({
                    type: 1,
                    title:"新建配置",
                    closeBtn: false,
                    shift: 2,
                    area: ['530px', '420px'],
                    shadeClose: true,
                    // btn: ['新增', '取消'],
                    // btnAlign: 'c',
                    content: $("#edit-main"),
                    success: function(layero, index){},
                    yes:function(){

                    }
                });
            });

    };

    function onCheckBtn(lid){
        //页面层-自定义
        var url="leavee/"+lid+"/leaveecheck/json";
        $.post(
            url,
            function(data) {
                console.log(data);
                if(date === "notexist")
                {
                    layer.msg("该请假未审批");
                }
                var json=JSON.parse(data);
                $("#editdaterange").val(json.start+" - "+json.end);
                $("#editnotes").val(json.leavee.notes);
                editIndex= layer.open({
                    type: 1,
                    title:"新建配置",
                    closeBtn: false,
                    shift: 2,
                    area: ['530px', '420px'],
                    shadeClose: true,
                    // btn: ['新增', '取消'],
                    // btnAlign: 'c',
                    content: $("#edit-main"),
                    success: function(layero, index){},
                    yes:function(){

                    }
                });
            });

    };


</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="check">查看审批</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<div class="layui-fluid">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-form toolbar">
                        <div class="layui-form-item">
                            <div class="layui-inline">

                            </div>
                        </div>
                    </div>
                    <table id="demo" lay-filter="test"></table>
                </div>
            </div>
</div>
</body>

<!-- 弹出层 添加 -->
<div id="add-main" style="display: none; width:500px">
    <form class="layui-form" id="add-form"  action="">

        <div class="layui-form-item">
            <label class="layui-form-label" >时间</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input" id="daterange">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >备注</label>
            <div class="layui-input-block">
                <textarea id="addnotes" name="addnotes" placeholder="请输入内容" class="layui-textarea" ></textarea>
                <!-- <input type="hidden" name="id" style="width: 240px" autocomplete="off" class="layui-input"> -->
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="save" >立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary" id="closeBtn" >重置</button>
            </div>
        </div>
    </form>
</div>

<!-- 弹出层 审批-->
<div id="check-main" style="display: none; width:500px">
<form class="layui-form" id="edit-form"  action="">

<div class="layui-form-item">
<label class="layui-form-label" >时间</label>
<div class="layui-input-block">
<input type="text" class="layui-input" id="editdaterange" >
</div>
</div>
<div class="layui-form-item">
<label class="layui-form-label" >备注</label>
<div class="layui-input-block">
<textarea id="editnotes" name="addnotes" placeholder="请输入内容" class="layui-textarea"  disabled="disabled"></textarea>
<!-- <input type="hidden" name="id" style="width: 240px" autocomplete="off" class="layui-input"> -->
</div>
</div>
</form>
</div>


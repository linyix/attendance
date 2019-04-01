<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>

<SCRIPT LANGUAGE="JavaScript">
    var chooseNode;
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        view :{
            showIcon:false
        },
        callback: {
            onClick: zTreeOnClick
        }
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = ${tree};
    $(document).ready(function(){
        zTreeObj = $.fn.zTree.init($("#deptTree"), setting, zNodes);
    });

    function zTreeOnClick(event, treeId, treeNode) {
        chooseDid=treeNode.did;
        chooseNode = treeNode;
        console.log(chooseNode);
        console.log(chooseNode.name);
        tableIns.reload( {
            url: '/department/'+chooseDid+'/employee'
        });
    };

    //一般直接写在一个js文件中
    layui.use(['element','table','form','layer'], function(){
        var element = layui.element;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        //第一个实例
        tableIns =table.render({
            elem: '#demo'
            ,height: 312
            ,url: '' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'number', title: '工号', sort: true}
                ,{field: 'name', title: '姓名'}
                ,{field: 'sex', title: '性别',  sort: true}
                ,{align:'center', toolbar: '#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                console.log(data);
                layer.msg("sdasda");
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if(layEvent === 'edit'){ //编辑
                table.reload("demo",{});
            }
        });

        form.on('submit(save)', function (data) {
            var page = "/employee";
            $.post(
                page,
                {   "name":$("#addname").val(),"number":$("#addnumber").val(),"notes":$("#addnotes").val(),
                    "telephone":$("#addtelephone").val(),"password":$("#addpassword").val(),"sex":$("#addsex option:selected").val(),
                    "departmentId":chooseDid},
                function(result) {
                    if ("success" == result) {
                        tableIns.reload( {});
                    }
                    else {
                        layer.msg(result);
                    }
                }
            );
            return false;

        });
    });

    function onAddBtn(){
        //页面层-自定义
        console.log(chooseNode.name);
        $("#adddepartment").val(chooseNode.name);
        console.log($("#adddepartment").val());
        layer.open({
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



</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm12 layui-col-md3 layui-col-lg2">
            <div class="layui-card">
                <div class="layui-card-body mini-bar">
                    <div class="ztree" id="deptTree"></div>
                </div>
            </div>
        </div>
        <div class="layui-col-sm12 layui-col-md9 layui-col-lg10">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-form toolbar">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input id="name" class="layui-input" type="text" placeholder="账号/姓名/手机号"/>
                            </div>
                            <div class="layui-inline">
                                <input id="timeLimit" class="layui-input" type="text" placeholder="注册时间"/>
                            </div>
                            <div class="layui-inline">
                                <button id="btnSearch" class="layui-btn icon-btn" onclick="onAddBtn()"><i class="layui-icon">&#xe615;</i>搜索</button>
                                <button id="btnAdd" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                                <button id="btnExp" class="layui-btn icon-btn"><i class="layui-icon">&#xe67d;</i>导出</button>
                            </div>
                        </div>
                    </div>
                    <table id="demo" lay-filter="test"></table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

<div id="add-main" style="display: none; width:500px">
    <form class="layui-form" id="add-form"  action="">
        <div class="layui-form-item " >
            <label class="layui-form-label"  >员工工号</label>
            <div class="layui-input-block">
                <input type="text" id="addnumber" name="addnumber" required value=""   lay-verify="required" placeholder="请输入员工工号" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" >
            <label class="layui-form-label"  >员工名称</label>
            <div class="layui-input-block">
                <input type="text" id="addname" name="addname"  required value=""   lay-verify="required" placeholder="请输入员工名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >密码</label>
            <div class="layui-input-block">
                <input type="password" id="addpassword" name="addpassword" required   lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >重复密码</label>
            <div class="layui-input-block">
                <input type="password" id="addpassword2" name="addpassword2" required   lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >性别</label>
            <div class="layui-input-block" >
                <select id="addsex" name="addsex" >
                    <option value="0">男</option>
                    <option value="1">女</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >所在部门</label>
            <div class="layui-input-block">
                <input type="text" id="adddepartment"  name="adddepartment"  value="as"   disabled="false" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >备注&emsp;</label>
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

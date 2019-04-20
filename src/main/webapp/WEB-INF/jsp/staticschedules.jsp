<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>



<div>
    <table border="1" cellspacing="0" style="margin-top:2%;margin-bottom: 2%" class="myschedule">
    <thead>
    <th>周一</th>
    <th>周二</th>
    <th>周三</th>
    <th>周四</th>
    <th>周五</th>
    <th>周六</th>
    <th>周日</th>
    </thead>
    
    <tbody>
        <tr>

            <td>
                <select >
                    <option selected="selected"   style='display: none' value='早班'>
                    </option>
                    <option value='-1'>晚班</option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value='早班'>
                    </option>
                    <option value='-1'>晚班</option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value='早班'>
                    </option>
                    <option value='-1'>晚班</option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value='早班'>
                    </option>
                    <option value='-1'>晚班</option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value='早班'>
                    </option>
                    <option value='-1'>晚班</option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value='早班'>
                    </option>
                    <option value='-1'>晚班</option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value='早班'>
                    </option>
                    <option value='-1'>晚班</option>
                </select>
            </td>
        </tr>
    </tbody>
    <!--
    <tr>
        <td>新手</td>
        <td>
        <select >
            <option selected="selected"  value=''></option>
            <option value='1'>早班</option>
            <option   value='2'>晚班</option>
            <option   value='3'>啊班</option>
        </select>

        </td>

    </tr>
    -->

</table>
    <div class="layui-card" style="width: 70%;margin-left: 10%">
        <div class="layui-card-body">
            <div class="layui-form toolbar">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <button id="btnAdd" class="layui-btn icon-btn" onclick="onAddBtn()"><i class="layui-icon">&#xe654;</i>添加</button>
                    </div>
                </div>
            </div>
            <table id="demo" lay-filter="test"></table>
        </div>
    </div>
</div>

<SCRIPT LANGUAGE="JavaScript">

    //一般直接写在一个js文件中
    layui.use(['element','table','form','layer'], function(){
        element = layui.element;
        table = layui.table;
        layer = layui.layer;
        form = layui.form;
        //第一个实例
        tableIns =table.render({
            elem: '#demo'
            ,url: '/department/1/holiday' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', title: '编号', sort: true}
                ,{field: 'name', title: '假期名称'}
                ,{field: 'starttime', title: '开始日期',  sort: true}
                ,{field: 'endtime', title: '结束日期',  sort: true}
                ,{align:'center', toolbar: '#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                console.log(data);
                onEditBtn(data.id);
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
        form.verify({
            firstpwd: [
                /^[\S]{6,12}$/
                ,'密码必须6到12位，且不能出现空格'
            ] ,
            secondpwd: function(value) {
                console.log("sc");
                console.log(value);
                console.log($("#addpassword").val());
                if(value != $("#addpassword").val()){
                    $("#addpassword2").val("");
                    return '确认密码与密码不一致';
                }
            },
            editsecondpwd: function(value) {
                console.log("sc");
                console.log(value);
                console.log($("#editpassword").val());
                if(value != $("#editpassword").val()){
                    $("#editpassword2").val("");
                    return '确认密码与密码不一致';
                }
            },
            numberalone:function (value) {

                var numberaloneresult;
                $.post(
                    "/employee/numberalone",{"number":value},
                    function(result) {

                        numberaloneresult=result;
                    }
                );

                if(numberaloneresult == "fail")
                {

                    return "该工号已被使用"
                }

            }
        });
        form.on('submit(save)', function (data) {
            var page = "/employee";
            $.post(
                page,
                {   "name":$("#addname").val(),"number":$("#addnumber").val(),"notes":$("#addnotes").val(),
                    "telephone":$("#addtelephone").val(),"email":$("#addemail").val(),
                    "password":$("#addpassword").val(),"sex":$("#addsex option:selected").val(),
                    "departmentId":chooseDid},
                function(result) {
                    if ("success" == result) {
                        tableIns.reload( {});
                        layer.close(addIndex);
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
        $("#adddepartment").val(chooseNode.name);
        console.log($("#adddepartment").val());
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
    function onEditBtn(eid){
        //页面层-自定义
        var url="employee/json/"+eid;
        $.post(
            url,
            function(data) {
                var json=JSON.parse(data);
                $("#editnumber").val(json.employee.number);
                $("#editname").val(json.employee.name);
                $("#editnotes").val(json.employee.notes);
                $("#edittelephone").val(json.employee.telephone);
                $("#editemail").val(json.employee.password);
                choosedepartment=json.employee.departmentId;
                $("#editdepartment").val(chooseNode.name);
                $("#editpassword").val(json.employee.password);
                $("#editpassword2").val(json.employee.password);
                $("#editsex option").prop("selected",false);
                if(json.employee.sex==0)
                {
                    $("#editsex [value='0']").prop("selected",true);
                }
                else
                {
                    $("#editsex [value='1']").prop("selected",true)
                }
                form.render('select');
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

    $(function() {
        $("#editdepartment").click(function () {
            chooseTreeIndex= layer.open({
                type: 1,
                title:"新建配置",
                closeBtn: false,
                shift: 2,
                area: ['400px', '300px'],
                shadeClose: true,
                // btn: ['新增', '取消'],
                // btnAlign: 'c',
                content: $("#tree-main"),
                success: function(layero, index){},
                yes:function(){

                }
            });
        })

    });


</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>



</body>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/header.jsp"%>
<script>
    $(function() {
        $("#toAddStudent").click(function () {
            $("#studentModal").modal('show');
            $("#name").html("");
        });

        $(".toEditStudent").click(function () {
            var etar=$(this).attr("etarget");
            var editid=$("tr[etarget='" + etar + "'] td[etarget='id']").html();
            var editname=$("tr[etarget='" + etar + "'] td[etarget='name']").html();
            var editClassID=$("tr[etarget='" + etar + "'] td[etarget='classId']").html();
            var editSex=$("tr[etarget='" + etar + "'] td[etarget='sex']").html();
            var editAge=$("tr[etarget='" + etar + "'] td[etarget='age']").html();
            var editaddress=$("tr[etarget='" + etar + "'] input[etarget='address']").val();
            $("#editStudentModal").modal('show');
            $("#editStudentTitle").html(editid);
            $("#editName").val(editname);
            $("#editAge").val(editAge);
            $("#editName").val(editname);
            $("#editAddress").html(editaddress);
            $("#editClassesid option").removeAttr("selected");
            $("#editClassesid [value='" + editClassID + "']").prop("selected",true);
            $("#editSex option").removeAttr("selected");
            if($.trim(editSex)=="男")
            {
                $("#editSex [value='1']").prop("selected",true);
            }
            else
            {
                $("#editSex [value='0']").prop("selected",true)
            }

        });



        $("#addStudent").click(function(){
            var name = $("#name").val();
            var address = $("#address").val();
            var classId =$("#classesid option:selected").val();
            var sex=$("#sex option:selected").val();
            var age = $("#age").val();
            if(0==name.length){
                $("span.errorMessage").html("请输入学生姓名");
                $("#studentError").show();
                return false;
            }
            var page = "/student";
            $.post(
                page,
                {"name":name,"address":address,"classId":classId,"sex":sex,"age":age},
                function(result){
                    if("success"==result){
                        location.reload();
                    }
                    else{
                        $("span.errorMessage").html("增加错误");
                        $("div.loginErrorMessageDiv").show();
                    }
                }
            );
            return true;
        });

        $("#editStudent").click(function(){
            var id = $("#editClassesTitle").html();;
            var name = $("#editName").val();
            var address = $("#editAddress").val();
            var classId =$("#editClassesid  option:selected").val();
            var sex=$("#editSex option:selected").val();
            var age = $("#editAge").val();
            if(0==name.length){
                $("span.errorMessage").html("请输入学生姓名");
                $("#studentError").show();
                return false;
            }
            var page = "/student/"+id;
            $.post(
                page,
                {"name":name,"address":address,"classId":classId,"sex":sex,"age":age},
                function(result){
                    if("success"==result){
                        location.reload();
                    }
                    else{
                        $("span.errorMessage").html("增加错误");
                        $("div.loginErrorMessageDiv").show();
                    }
                }
            );
            return true;
        });
    });
</script>

<script>
    //复选框全选
    function ckAll() {
        var flag=document.getElementById("allChecks").checked;
        var cks=document.getElementsByName("ids");
        for(var i=0;i<cks.length;i++){
            cks[i].checked=flag;
        }
    }
    //批量删除
    function del() {
        var ids = "";
        var cks=document.getElementsByName("ids");
        for(var i=0;i<cks.length;i++){
            if(cks[i].checked) {
                ids+=cks[i].value+"_";
            }
        }
        if (ids.length==0) {
            alert("没有选中任何班级");
            return;
        }
        $.post("/student",{"ids":ids,"_method":"DELETE"},function () {
            location.reload();
        })
    }
</script>
<div class="modal " id="studentModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div id="studentError" style="display: none" class="alert alert-danger" >
                    <span class="errorMessage"></span>
                </div>
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">添加学生</h4>
            </div>
            <form >
                <div class="modal-body">

                    <p>学生姓名 <input id="name" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                    <p>学生班级 <select id="classesid" style="width: 50%;display: inline-block" type="text" class="form-control">
                        <c:forEach items="${classList}" var="klass">
                        <option value="${klass.id}" <c:if test="${!empty classes}"><c:if test="${klass.id eq classes.id}">selected</c:if> </c:if>> ${klass.name}</option>
                        </c:forEach>
                    </select>
                    </p>
                    <p>学生性别 <select id="sex" style="width: 50%;display: inline-block" type="text" class="form-control">
                            <option value="1">男</option>
                            <option value="0">女</option>
                    </select>
                    </p>
                    <p>学生年龄 <input id="age" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                    <p>地址</p>
                    <textarea id="address" class="form-control"></textarea>

                </div>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                    <button class="btn btn-primary" id="addStudent" type="button">提交</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


<div class="modal " id="editStudentModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div id="editStudentError" style="display: none" class="alert alert-danger" >
                    <span class="errorMessage"></span>
                </div>
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">学生编号:<span id="editStudentTitle"></span></h4>
            </div>
            <form >
                <div class="modal-body">

                    <p>学生姓名 <input id="editName" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                    <p>学生班级 <select id="editClassesid" style="width: 50%;display: inline-block" type="text" class="form-control" autocomplete="off">
                        <c:forEach items="${classList}" var="klass">
                            <option value="${klass.id}" > ${klass.name}</option>
                        </c:forEach>
                    </select>
                    </p>
                    <p>学生性别 <select id="editSex" style="width: 50%;display: inline-block" type="text" class="form-control" autocomplete="off">
                        <option value="1">男</option>
                        <option value="0">女</option>
                    </select>
                    </p>
                    <p>学生年龄 <input id="editAge" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                    <p>地址</p>
                    <textarea id="editAddress" class="form-control"></textarea>

                </div>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                    <button class="btn btn-primary" id="editStudent" type="button">提交</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">学生管理系统</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">最后一次登录：<fmt:formatDate value="${administor.lastSignin}" pattern="yyyy-MM-dd HH:mm"/></a></li>
                <li><a href="#">${administor.id}</a></li>
                <li><a href="/administor/signout">注销</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="/classes">班级管理 <span class="sr-only">(current)</span></a></li>
                <li class="active"><a href="/student">学生管理</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">


            <h2 class="sub-header">
                <c:if test="${empty classes}">学生管理</c:if> <c:if test="${!empty classes}">${classes.name}</c:if>
                <button id="toAddStudent"style="margin-left: 63%" type="button"  class="btn btn-lg btn-primary">添加学生</button>
                <button style="margin-left: 3%" type="button"  class="btn btn-lg btn-danger" onclick="javascritp:del();">删除选中</button>
            </h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>学生编号</th>
                        <th>学生姓名</th>
                        <th>班级编号</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>编辑</th>
                        <th><label style="margin-bottom: 0px"><input type="checkbox" onclick="ckAll()" id="allChecks">全选</label></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${student}" var="p">
                        <tr etarget="${p.id}">
                            <input type="hidden" etarget="address" value="${p.address}"/>
                            <td etarget="id">${p.id}</td>
                            <td etarget="name">${p.name}</td>
                            <td etarget="classId">${p.classId}</td>
                            <td etarget="sex">
                                <c:if test="${p.sex eq 1}">男</c:if> <c:if test="${p.sex eq 0}">女</c:if>
                            </td>
                            <td etarget="age">${p.age}</td>
                            <td><a etarget="${p.id}" href="#" class="toEditStudent"><span
                                    class="glyphicon glyphicon-edit"></span></a></td>
                            <td><input type="checkbox" name="ids" value="${p.id}"></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/header.jsp"%>




<div class="workingArea">

    <div>
        <form method="post" id="listForm" action="trainlist" >
            姓名 &nbsp;<input  style="margin-right:4%;width: 10%;display: inline-block" name="name" type="text" class="form-control">
            所在部门&nbsp;
            <select  style="margin-right:4%;width: 10%;display: inline-block" type="text" name="department" class="form-control">
                <option selected="selected"   style='display: none' value=''></option>
                <c:forEach items="${departments}" var="department">
                    <option value="${department.id}" <c:if test="${!empty classes}"> <c:if test="${klass.id eq classes.id}">selected</c:if> </c:if> > ${department.name}</option>
                </c:forEach>
            </select>
            工号&nbsp;<input  style="margin-right:4%;width: 10%;display: inline-block" name="number" type="text" class="form-control">
            <button  type="submit" class="btn btn-success">查找</button>

        </form>
        <button  id="toAddEmployee"  class="btn btn-success">新增</button>
    </div>


    <br>
    <br>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed bllu">
            <thead>
            <tr class="success">
                <th>工号</th>
                <th>姓名</th>
                <th>所在部门</th>
                <th>操作</th>
                <th><label style="margin-bottom: 0px"><input type="checkbox" onclick="ckAll()" id="allChecks" >全选</label></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${vos}" var="c">

                <tr>
                    <td style="display:none"> ${c.objs["employee"].id} </td>
                    <td>${c.objs["employee"].number}</td>
                    <td>${c.objs["employee"].name} </td>
                    <td>${c.objs["department"].name}</td>
                    <td><button  type="button"  class="toEditEmployee btn btn-sm btn-primary">编辑</button> </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="modal " id="toEdit" tabindex="-1" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div id="editError" style="display: none" class="alert alert-danger" >
                        <span class="errorMessage"></span>
                    </div>
                    <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">编辑员工</h4>
                </div>
                <form >
                    <div class="modal-body">
                        <p>员工工号 <input id="editnumber" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>员工姓名 <input id="editname" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>密码     <input id="editpassword" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>员工部门 <select id="editdepartment" style="width: 50%;display: inline-block" type="text" class="form-control">
                            <c:forEach items="${departments}" var="department">
                                <option value="${department.id}">  ${department.name}</option>
                            </c:forEach>
                        </select>
                        </p>

                        <p>员工性别 <select id="editsex" style="width: 50%;display: inline-block" type="text" class="form-control">
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                        </p>
                        <p>电话 <input id="edittelephone" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>备注</p><textarea id="editnotes" class="form-control"></textarea>

                    </div>
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                        <button class="btn btn-primary" id="edit" type="button">提交</button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>

    <div class="modal " id="toAdd" tabindex="-1" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div id="addError" style="display: none" class="alert alert-danger" >
                        <span class="errorMessage"></span>
                    </div>
                    <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">新增员工</h4>
                </div>
                <form >
                    <div class="modal-body">
                        <p>员工工号 <input id="addnumber" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>员工姓名 <input id="addname" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>密码     <input id="addpassword" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>员工部门 <select id="adddepartment" style="width: 50%;display: inline-block" type="text" class="form-control">
                            <c:forEach items="${departments}" var="department">
                                <option value="${department.id}">  ${department.name}</option>
                            </c:forEach>
                        </select>
                        </p>

                        <p>员工性别 <select id="addsex" style="width: 50%;display: inline-block" type="text" class="form-control">
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                        </p>
                        <p>电话 <input id="addtelephone" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>备注</p><textarea id="addnotes" class="form-control"></textarea>

                    </div>
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                        <button class="btn btn-primary" id="add" type="button">提交</button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>







</div>


<script>
    $(function() {

        $('.toEditEmployee').click(function(){

            employeeId = $(this).parent().parent().children("td")[0].innerHTML;
            $("#editError").hide();
            var url="employee/json/"+employeeId;
            $.post(
                url,
                function(data) {
                    var json=JSON.parse(data);
                    /*
                    var name =json.employee.name;
                    var id = json.employee.id;
                    var password =json.employee.password;*/
                    $("#toEdit").modal('show');
                    $("#editnumber").val(json.employee.number);
                    $("#editname").val(json.employee.name);
                    $("#editnotes").val(json.employee.notes);
                    $("#edittelephone").val(json.employee.telephone);
                    $("#editpassword").val(json.employee.password);
                    $("#editdepartment option").prop("selected",false);
                    $("#editdepartment [value='" + json.employee.departmentId + "']").prop("selected",true);
                    $("#editsex option").prop("selected",false);
                    if(json.employee.sex==0)
                    {
                        $("#editsex [value='0']").prop("selected",true);
                    }
                    else
                    {
                        $("#editsex [value='1']").prop("selected",true)
                    }
                });
        });

        $("#edit").click(function(){

            if(0==$("#editnumber").val().length  ){
                $("span.errorMessage").html("请输入员工工号");
                $("#editError").show();
                return false;
            }
            if( 0==$("#editname").val.length ){
                $("span.errorMessage").html("请输入员工姓名");
                $("#editError").show();
                return false;
            }
            var page = "/employee/"+employeeId;
            $.post(
                page,
                {"id":employeeId,"name":$("#editname").val(),"number":$("#editnumber").val(),"notes":$("#editnotes").val(),
                    "telephone":$("#edittelephone").val(),"password":$("#editpassword").val(),"sex":$("#editsex option:selected").val(),
                    "departmentId":$("#editdepartment option:selected").val()},
                function(result){
                    if("success"==result){
                        location.reload();
                    }
                    else{
                        $("span.errorMessage").html(result);
                        $("#editError").show();
                    }
                }
            );
            return true;
        });

        $('#toAddEmployee').click(function(){
            $("#addError").hide();
            $("#toAdd").modal('show');
        });

        $("#add").click(function(){

            if(0==$("#addnumber").val().length  ){
                $("span.errorMessage").html("请输入员工工号");
                $("#addError").show();
                return false;
            }
            if( 0==$("#addname").val.length ){
                $("span.errorMessage").html("请输入员工姓名");
                $("#addError").show();
                return false;
            }
            var page = "/employee";
            $.post(
                page,
                {   "name":$("#addname").val(),"number":$("#addnumber").val(),"notes":$("#addnotes").val(),
                    "telephone":$("#addtelephone").val(),"password":$("#addpassword").val(),"sex":$("#addsex option:selected").val(),
                    "departmentId":$("#adddepartment option:selected").val()},
                function(result){
                    if("success"==result){
                        location.reload();
                    }
                    else{
                        $("span.errorMessage").html(result);
                        $("#addError").show();
                    }
                }
            );
            return true;
        });
    });
</script>
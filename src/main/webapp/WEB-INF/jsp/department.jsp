<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>




<div class="workingArea">


    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed bllu">
            <thead>
            <tr class="success">
                <th>部门名称</th>
                <th>操作</th>
                <th><label style="margin-bottom: 0px"><input type="checkbox" onclick="ckAll()" id="allChecks" >全选</label></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${department}" var="c">

                <tr>
                    <td style="display:none"> ${c.id} </td>
                    <td>${c.name}</td>
                    <td>
                        <button  type="button"  class="toEditEmployee btn btn-sm btn-primary">编辑</button>

                        <a href="/department/${c.id}/clazz">
                        <button  type="button"  class="toEditEmployee btn btn-sm btn-primary">班次</button>
                        </a>

                        <button  type="button"  class="toEditEmployee btn btn-sm btn-primary">排班</button>

                        <button  type="button"  class="toEditEmployee btn btn-sm btn-primary">考勤</button>

                        <button  type="button"  class="toEditEmployee btn btn-sm btn-primary">统计</button>
                    </td>
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
                        <p>部门名称 <input id="editname" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
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
                    <h4 class="modal-title">新增部门</h4>
                </div>
                <form >
                    <div class="modal-body">
                        <p>部门名称 <input id="addname" style="width: 50%;display: inline-block" type="text" class="form-control"></p>

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
            var url="department/json/"+employeeId;
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
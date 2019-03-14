<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>




<div class="workingArea">

    <div>
        <form method="post" id="listForm" action="trainlist" >
            姓名 &nbsp;<input  style="margin-right:4%;width: 10%;display: inline-block" name="name" type="text" class="form-control">
            所在部门&nbsp;
            <select id="classesid" style="margin-right:4%;width: 10%;display: inline-block" type="text" name="department" class="form-control">
                <option selected="selected"  value=''></option>
                <c:forEach items="${departments}" var="department">
                    <option value="${department.id}" <c:if test="${!empty classes}"> <c:if test="${klass.id eq classes.id}">selected</c:if> </c:if> > ${department.name}</option>
                </c:forEach>
            </select>
            工号&nbsp;<input  style="margin-right:4%;width: 10%;display: inline-block" name="number" type="text" class="form-control">
            <button  type="submit" class="btn btn-success">查找</button>
        </form>
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
                    <td>${c.objs["employee"].number}</td>
                    <td>${c.objs["employee"].name} </td>
                    <td>${c.objs["department"].name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="modal " id="editModal" tabindex="-1" role="dialog" >
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

                        <p>员工部门 <select id="editdepartment" style="width: 50%;display: inline-block" type="text" class="form-control">
                            <c:forEach items="${classList}" var="klass">
                                <option value="${klass.id}" <c:if test="${!empty classes}"><c:if test="${klass.id eq classes.id}">selected</c:if> </c:if>> ${klass.name}</option>
                            </c:forEach>
                        </select>
                        </p>

                        <p>员工性别 <select id="editsex" style="width: 50%;display: inline-block" type="text" class="form-control">
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

    <div class="modal " id="addModal" tabindex="-1" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div id="studentError" style="display: none" class="alert alert-danger" >
                        <span class="errorMessage"></span>
                    </div>
                    <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">添加员工</h4>
                </div>
                <form >
                    <div class="modal-body">
                        <p>员工姓名 <input id="addnumber" style="width: 50%;display: inline-block" type="text" class="form-control"></p>
                        <p>员工姓名 <input id="addname" style="width: 50%;display: inline-block" type="text" class="form-control"></p>

                        <p>员工部门 <select id="classesid" style="width: 50%;display: inline-block" type="text" class="form-control">
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





</div>
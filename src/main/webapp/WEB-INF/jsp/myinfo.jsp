<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>

<script>
    $(function() {
        $("#save").click(function () {
                $.post(
                    "myinfo",
                    {   "email":$("#email").val(),
                        "telephone":$("#telephone").val()},
                    function(result) {
                        if ("success" == result) {
                            location.reload();
                        }
                        else {
                            location.reload();
                        }
                    }
                );
        }
        );
    }
    );
</script>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">个人信息</div>
        <div class="layui-card-body">
            <form class="layui-form layui-form-pane" action="">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">姓名</label>
                        <div class="layui-input-block">
                            <input type="text"  value="${e.name}" autocomplete="off" class="layui-input" disabled="disabled">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">性别</label>
                        <div class="layui-input-inline">
                            <input type="text" name="number" autocomplete="off" value="<c:if test="${e.sex eq 0}"> 男 </c:if> <c:if test="${e.sex eq 1}"> 女 </c:if>"
                                class="layui-input" disabled="disabled">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">工号</label>
                        <div class="layui-input-block">
                            <input type="text" name="title" autocomplete="off" value="${e.number}" placeholder="请输入标题" class="layui-input" disabled="disabled">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">所在部门</label>
                        <div class="layui-input-inline">
                            <input type="text" name="title" autocomplete="off" value="${d.name}" placeholder="请输入标题" class="layui-input" disabled="disabled">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">邮箱</label>
                        <div class="layui-input-block">
                            <input type="text" id="email" value="${e.email}" autocomplete="off" class="layui-input" >
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">电话</label>
                        <div class="layui-input-inline">
                            <input type="text" id="telephone" name="number" autocomplete="off" value="${e.telephone}"
                                   class="layui-input" >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入内容" class="layui-textarea" disabled="disabled">${e.notes}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn" id="save" lay-submit="" lay-filter="demo2">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

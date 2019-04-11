<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/header.jsp"%>

<script type="text/javascript">
        layui.use(['form','layer','jquery'], function () {

            // 操作对象
            var form = layui.form;
            form.on('submit(login)',function (data) {
                // console.log(data.field);
                $.ajax({
                    url:'login.php',
                    data:data.field,
                    dataType:'text',
                    type:'post',
                    success:function (data) {
                        if (data == '1'){
                            location.href = "../index.php";
                        }else{
                            layer.msg('登录名或密码错误');
                        }
                    }
                })
                return false;
            })

        });
</script>


<div class="login-main">
    <header class="layui-elip">密码重置</header>
    <form class="layui-form">
        <div class="layui-input-inline">
            <input type="text" name="account" required lay-verify="required" placeholder="用户名" autocomplete="off"
                   class="layui-input">
        </div>
        <div class="layui-input-inline">
            <input type="password" name="password" required lay-verify="required" placeholder="密码" autocomplete="off"
                   class="layui-input">
        </div>
        <div class="layui-input-inline login-btn">
            <button lay-submit lay-filter="login" class="layui-btn">登录</button>
        </div>
        <hr/>
        <!--<div class="layui-input-inline">
            <button type="button" class="layui-btn layui-btn-primary">QQ登录</button>
        </div>
        <div class="layui-input-inline">
            <button type="button" class="layui-btn layui-btn-normal">微信登录</button>
        </div>-->
    </form>
</div>




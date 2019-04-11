<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/header.jsp"%>

<script type="text/javascript">
        layui.use(['form','layer','jquery'], function () {

            // 操作对象
            var form = layui.form;
            var layer= layui.layer;
            form.on('submit(login)',function (data) {
                // console.log(data.field);
                $.post(
                    "loginJson",
                    {   "number":$("#number").val(),"password":$("#password").val()},

                    function(result) {
                        if ("success" == result) {
                            window.location.href="logint?number="+$("#number").val()+"&password="+$("#password").val();
                        }
                        else {
                            layer.msg("登录失败，账号或密码错误");
                        }
                    }
                );
                return false;
            })
        })

</script>


<div class="login-main">
    <header class="layui-elip">登录</header>
    <form class="layui-form" action="">
        <div class="layui-input-inline">
            <input id="number" type="text" name="number" required lay-verify="required" placeholder="用户名" autocomplete="off"
                   class="layui-input">
        </div>
        <div class="layui-input-inline">
            <input id="password" type="password" name="password" required lay-verify="required" placeholder="密码" autocomplete="off"
                   class="layui-input">
        </div>
        <div class="layui-input-inline ">
            <button id="loginbutton" lay-submit lay-filter="login" class="layui-btn">登录</button>
        </div>
        <hr/>
        <!--<div class="layui-input-inline">
            <button type="button" class="layui-btn layui-btn-primary">QQ登录</button>
        </div>
        <div class="layui-input-inline">
            <button type="button" class="layui-btn layui-btn-normal">微信登录</button>
        </div>-->
        <p><a href="password" class="fr">忘记密码？</a></p>
    </form>
</div>




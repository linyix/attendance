<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>

<script>
    $(function() {
        $("#logininb").click(function(){
            var id = $("#loginid").html();;
            var name = $("#loginname").val();
            if(0==name.length){
                $("#emptyidpas").show();
                return false;
            }
            return true;
        });
    });
</script>
<div class="container">
    <c:if test="${!empty msg }">
        <div style="text-align: center"  class="alert alert-danger" role="alert">
             账号或密码错误
        </div>
    </c:if>
    <div id="emptyidpas"style="text-align: center ;display: none"  class="alert alert-danger" role="alert">
        账号或密码不能为空
    </div>


    <form class="form-signin" action="administor" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">ID</label>
        <input type="text" id="inputEmail" name="id" class="form-control" placeholder="ID" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

</div> <!-- /container -->
<%@include file="include/Footer.jsp"%>
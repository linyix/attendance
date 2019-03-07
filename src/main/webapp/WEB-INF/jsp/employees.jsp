<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>



<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="/classes">班级管理 <span class="sr-only">(current)</span></a></li>
                <li><a href="/student">学生管理</a></li>

            </ul>
        </div>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">学生管理系统</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-right">

                            <li><a href="#">上市</a></li>
                            <li><a href="/administor/signout">注销</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!--

            -->
            <h2 class="sub-header">班级列表 <button id="toAddClasses"style="margin-left: 58%" type="button"  class="btn btn-lg btn-primary">添加班级</button>
                <button style="margin-left: 5%" type="button"  class="btn btn-lg btn-danger" onclick="javascritp:del();">删除选中</button>
            </h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>班级编号</th>
                        <th>班级名称</th>
                        <th>学生数量</th>
                        <th>编辑</th>
                        <th><label style="margin-bottom: 0px"><input type="checkbox" onclick="ckAll()" id="allChecks" >全选</label></th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr>
                        <td>1,001</td>
                        <td>Lorem</td>
                        <td>ipsum</td>
                        <td>dolor</td>
                        <td>sit</td>
                    </tr>
                    <tr>
                        <td>1,002</td>
                        <td>amet</td>
                        <td>consectetur</td>
                        <td>adipiscing</td>
                        <td>elit</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
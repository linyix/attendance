

<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %> 

<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>员工考勤系统</title>
	<script src="/js/jquery/2.0.0/jquery.min.js"></script>

	<link rel="stylesheet" href="/layui/css/layui.css">
	<script src="/layui/layui.js"></script>

	<link rel="stylesheet" href="/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="/ztree/js/jquery.ztree.core.js"></script>

</head>


	<style type="text/css">
		/*IE 10 */
		.myschedule {
			border-color: rgba(0,0,0,.12);
			text-align: center;
			width: 70%;
			margin:10%;
			font-size:13px;
			color: rgba(0,0,0,.65);
		}
		.myschedule select::-ms-expand{
			display: none;
		}
		.myschedule select{
			/* Chrome */
			-webkit-appearance: none;
			/* Firefox */
			-moz-appearance: none;
			text-indent: 0.01px;
			text-overflow: ' '; /* space */
			width:100%;
			border-width: 0;
			text-align:center;
			text-align-last: center;
			text-align-all: center;
			color:rgba(0,0,0,.65);
		}
		.myschedule option{
			text-align: center;
		}
		.myschedule td{
			background-color: #eee;
		}
	</style>
</head>
<body>


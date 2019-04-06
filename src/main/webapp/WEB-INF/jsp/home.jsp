<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>
<script>

    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');

        var calendar = new FullCalendar.Calendar(calendarEl, {
            plugins: [ 'interaction', 'dayGrid' ],
            header: {
                left: 'prevYear,prev,next,nextYear today',
                center: 'title',
                right: ''
            },
            locale:'zh-cn',
            defaultDate: '2019-04-12',
            navLinks: true, // can click day/week names to navigate views
            editable: true,
            eventLimit: true, // allow "more" link when too many events
            events:'/myschedule'
        });

        calendar.render();
    });

    $(function() {
        $("#checkin").click(function () {
            $.post(
                "myattendance/on",
                function(data) {
                    //location.reload();
                    });
        });
        $("#checkout").click(function () {
            $.post(
                "myattendance/off",
                function(data) {
                    //location.reload();
                });
        });
    });
</script>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm12 layui-col-md6 layui-col-lg6">
            <div class="layui-card">
                <div class="layui-card-body mini-bar">
                    <div>
                        <p>欢迎来到员工考勤管理系统</p>
                        <p>今日班次为早班</p>
                        <p>早班 xxx 午班 xxx 放下面</p>
                        <button id="checkin" class="layui-btn layui-btn-radius layui-btn-normal">上班打卡</button>
                        <button id="checkout" class="layui-btn layui-btn-radius layui-btn-normal">下班打卡</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-sm12 layui-col-md6 layui-col-lg6">
            <div id='calendar'></div>
        </div>
    </div>
</div>

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
            defaultDate: '${today}',
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
        <div class="layui-col-sm12 layui-col-md6 layui-col-lg6" style="font-size:large;">
            <div class="layui-card" style="margin-top: 20px">
                <div class="layui-card-body mini-bar">
                    <div>
                        <p>欢迎来到员工考勤管理系统</p>
                        <br/>
                        <p>
                            <c:if test="${!empty clazz}">今日班次为${clazz.name}:
                                <fmt:formatDate value="${clazz.startTime}" pattern="HH:mm"/>-
                                <fmt:formatDate value="${clazz.endTime}" pattern="HH:mm"/>
                             </c:if>
                            <c:if test="${empty clazz}">今日未排班</c:if>
                        </p>
                        <br/>
                        <c:if test="${cancheckon}">
                            <button id="checkin" class="layui-btn layui-btn-radius layui-btn-normal">
                                上班打卡
                            </button>
                        </c:if>
                        <c:if test="${!cancheckon}">
                            <button id="checkin" class="layui-btn layui-btn-radius layui-btn-disabled">
                                <c:if test="${empty attendances}">上班打卡</c:if>
                                <c:if test="${!empty attendances}">已打卡 <fmt:formatDate value="${attendances.checkIn}" pattern="HH:mm"/></c:if>
                            </button>
                        </c:if>


                        <c:if test="${cancheckout}">
                            <button id="checkout" class="layui-btn layui-btn-radius layui-btn-normal">
                                下班打卡
                            </button>
                        </c:if>
                        <c:if test="${!cancheckout}">
                            <button id="checkout" class="layui-btn layui-btn-radius layui-btn-disabled">
                                <c:if test="${empty attendancesOut}">下班打卡</c:if>

                                <c:if test="${!empty attendancesOut}">
                                <c:if test="${!empty attendancesOut.checkOut}">已退卡 <fmt:formatDate value="${attendancesOut.checkOut}" pattern="HH:mm"/></c:if>
                                </c:if>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="layui-card" style="margin-top: 75px">
                <div class="layui-card-header" style="font-size: large;">所在部门:${department.name}  班次信息</div>
                <div class="layui-card-body">
                    <form class="layui-form layui-form-pane" action="">
                        <c:forEach items="${clazzes}" var="c">
                            <div class="layui-form-item">
                                <label class="layui-form-label">${c.name}</label>
                                <div class="layui-input-block">
                                    <input type="text" name="title" autocomplete="off"
                                           value="<fmt:formatDate value="${c.startTime}" pattern="HH:mm"/> - <fmt:formatDate value="${c.endTime}" pattern="HH:mm"/>"
                                           placeholder="请输入标题" class="layui-input" disabled="disabled">
                                </div>
                            </div>
                        </c:forEach>
                    </form>
                </div>
            </div>
        </div>
        <div class="layui-col-sm12 layui-col-md6 layui-col-lg6">
            <div id='calendar' style="margin: 20px"></div>
        </div>
    </div>
</div>

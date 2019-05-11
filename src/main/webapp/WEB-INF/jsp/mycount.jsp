<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>

<SCRIPT LANGUAGE="JavaScript">
    //一般直接写在一个js文件中
    layui.use(['element','table','form','layer','laydate'], function(){
        var element = layui.element;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var laydate = layui.laydate;
        //第一个实例
        laydate.render({
            elem: '#daterange' //指定元素
            ,type: 'date'
            ,range: true
        });


    });

</script>



<div class="layui-card">
    <div class="layui-card-body">
        <div class="layui-form toolbar">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <input type="text" value="${thisMonth}" class="layui-input" id="daterange">
                </div>
                <div class="layui-inline">
                    <button id="btnSearch" class="layui-btn icon-btn" onclick="onAddBtn()"><i class="layui-icon">&#xe615;</i>搜索</button>
                </div>
            </div>
        </div>
        <div class="layui-collapse">
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">班次天数    ${scheduleWithClazzSize}天</h2>
                <div class="layui-colla-content layui-show">
                    <table class="layui-table" lay-skin="line" lay-size="sm">
                        <thead>
                        <tr>
                            <th>班次</th>
                            <th>上班时间-下班时间</th>
                            <th>班次时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${scheduleWithClazz}" var="s">
                            <tr>
                                <td>${s.clazz.name}</td>
                                <td><fmt:formatDate value="${s.clazz.startTime}" pattern="HH:mm"/>-
                                    <fmt:formatDate value="${s.clazz.endTime}" pattern="HH:mm"/></td>
                                <td><fmt:formatDate value="${s.schedules.clazzDate}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">请假次数: ${leaveesSize}次  时长: 8小时</h2>
                <div class="layui-colla-content ">
                    <table class="layui-table" lay-skin="line" lay-size="sm">
                        <thead>
                        <tr>
                            <th>开始时间</th>
                            <th>结束时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${leavees}" var="s">
                            <tr>
                                <td><fmt:formatDate value="${s.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">外出次数:1次  时长: 8小时</h2>
                <div class="layui-colla-content ">
                    <table class="layui-table" lay-skin="line" lay-size="sm">
                        <thead>
                        <tr>
                            <th>开始时间</th>
                            <th>结束时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${goouts}" var="s">
                            <tr>
                                <td><fmt:formatDate value="${s.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">加班次数:2次  时长: 180分钟</h2>
                <div class="layui-colla-content ">内容区域</div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">迟到次数:3次  时长: 34分钟</h2>
                <div class="layui-colla-content ">
                    <table class="layui-table" lay-skin="line" lay-size="sm">
                        <thead>
                        <tr>
                            <th>上班时间</th>
                            <th>下班时间</th>
                            <th>打卡时间</th>
                            <th>退卡时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${lates}" var="s">
                            <tr>
                                <td><fmt:formatDate value="${s.start}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.end}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.attendance.checkIn}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><c:if test="${hasCheckOut}"></c:if> <fmt:formatDate value="${s.attendance.checkOut}" pattern="yyyy-MM-dd HH:mm"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">早退次数:2次  时长: 30分钟</h2>
                <div class="layui-colla-content ">
                    <table class="layui-table" lay-skin="line" lay-size="sm">
                        <thead>
                        <tr>
                            <th>上班时间</th>
                            <th>下班时间</th>
                            <th>打卡时间</th>
                            <th>退卡时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${leaveEarly}" var="s">
                            <tr>
                                <td><fmt:formatDate value="${s.start}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.end}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.attendance.checkIn}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><c:if test="${hasCheckOut}"></c:if> <fmt:formatDate value="${s.attendance.checkOut}" pattern="yyyy-MM-dd HH:mm"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="layui-colla-item">
                <h2 class="layui-colla-title">未打卡次数    ${unCheckonSize}次</h2>
                <div class="layui-colla-content ">
                    <table class="layui-table" lay-skin="line" lay-size="sm">
                        <thead>
                        <tr>
                            <th>上班时间</th>
                            <th>下班时间</th>
                            <th>打卡时间</th>
                            <th>退卡时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${unCheckon}" var="s">
                            <tr>
                                <td><fmt:formatDate value="${s.start}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.end}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="layui-colla-item">
                <h2 class="layui-colla-title">未退卡次数    ${unCheckOutSize}次</h2>
                <div class="layui-colla-content ">
                    <table class="layui-table" lay-skin="line" lay-size="sm">
                        <thead>
                        <tr>
                            <th>上班时间</th>
                            <th>下班时间</th>
                            <th>打卡时间</th>
                            <th>退卡时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${unCheckOut}" var="s">
                            <tr>
                                <td><fmt:formatDate value="${s.start}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.end}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${s.attendance.checkIn}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>



</body>



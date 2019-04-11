<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>



<script>
    var date = "${checkday}";
    var last = "${last}";
    var next = "${next}";
    $(function() {
        $("select").change(function () {
            var slt = $(this);
            var p1 = $(this).children('option:selected').val();
            var eid = $(this).parent().parent().children("td")[0].innerHTML;
            var index = $(this).parents("tr").find("td").index($(this).parent())-2;
            console.log(p1);
            console.log(index);
            console.log(eid);
            $.post(
                "schedule",{"date":date,"index":index,"cid":p1,"eid":eid},
                function(result) {
                    console.log(result);
                }
            );
        })
    });
</script>

<div>
    <button class="layui-btn layui-btn-sm layui-btn-primary">
        <a href="?date=${last}"><i class="layui-icon layui-icon-prev" ></i></a>
    </button>
    <button class="layui-btn layui-btn-sm layui-btn-primary">
        <a href="?date=${next}"><i class="layui-icon layui-icon-next" ></i></a>
    </button>

<table border="1" cellspacing="0" class="myschedule">
    <thead>
    <th>
        姓名
    </th>
    <c:forEach items="${dateAndWeek}" var="p">
        <th>${p}</th>
    </c:forEach>
    </thead>
    
    <tbody>
    <c:forEach items="${vos}" var="p">
        <tr>
            <td style="display: none">${p.objs["employee"].id}</td>
            <td style="background-color: #FFFFFF">${p.objs["employee"].name}</td>
            <c:forEach items="${p.objs.vos2}" var="c">
                <td>
                    <c:if test="${! c.objs.can}">
                        <c:if test="${! empty c.objs.schedule}"> ${c.objs.clazz.name}</c:if>
                    </c:if>
                    <c:if test="${c.objs.can}">
                    <select >
                        <option selected="selected"   style='display: none' value=''>
                            <c:if test="${!empty c.objs.schedule}">${c.objs.clazz.name} </c:if>
                        </option>
                        <option value='-1'></option>
                        <c:forEach items="${clazzes}" var="clazz">
                            <option value='${clazz.id}'>${clazz.name}</option>
                        </c:forEach>
                    </select>
                    </c:if>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
    <!--
    <tr>
        <td>新手</td>
        <td>
        <select >
            <option selected="selected"  value=''></option>
            <option value='1'>早班</option>
            <option   value='2'>晚班</option>
            <option   value='3'>啊班</option>
        </select>

        </td>

    </tr>
    -->

</table>

</div>

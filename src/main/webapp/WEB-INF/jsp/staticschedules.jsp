<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>



<div>
<table border="1" cellspacing="0" class="myschedule">
    <thead>
    <th>周一</th>
    <th>周二</th>
    <th>周三</th>
    <th>周四</th>
    <th>周五</th>
    <th>周六</th>
    <th>周日</th>
    </thead>
    
    <tbody>
        <tr>
                <td>
                    <select >
                        <option selected="selected"   style='display: none' value=''>
                        </option>
                        <option value='-1'></option>
                    </select>
                </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value=''>
                    </option>
                    <option value='-1'></option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value=''>
                    </option>
                    <option value='-1'></option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value=''>
                    </option>
                    <option value='-1'></option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value=''>
                    </option>
                    <option value='-1'></option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value=''>
                    </option>
                    <option value='-1'></option>
                </select>
            </td>
            <td>
                <select >
                    <option selected="selected"   style='display: none' value=''>
                    </option>
                    <option value='-1'></option>
                </select>
            </td>
        </tr>
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>

<SCRIPT LANGUAGE="JavaScript">
    var chooseNode;
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        view :{
            showIcon:false
        },
        callback: {
            onClick: zTreeOnClick
        }
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = ${tree};
    $(document).ready(function(){
        zTreeObj = $.fn.zTree.init($("#deptTree"), setting, zNodes);
    });

    function zTreeOnClick(event, treeId, treeNode) {
        chooseDid=treeNode.did;
        chooseNode = treeNode;
        console.log(chooseNode);
        console.log(chooseNode.name);
        tableIns.reload( {
            url: '/department/'+chooseDid+'/employee'
        });
    };

    //一般直接写在一个js文件中


</script>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm12 layui-col-md3 layui-col-lg2">
            <div class="layui-card">
                <div class="layui-card-body mini-bar">
                    <div class="ztree" id="deptTree"></div>
                </div>
            </div>
        </div>
        <div class="layui-col-sm12 layui-col-md9 layui-col-lg10">
            <div class="layui-card">
                <div class="layui-card-body">
                    <
                    <iframe src="" frameborder="0" style="width: 100%"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

<div id="add-main" style="display: none; width:500px">
    <form class="layui-form" id="add-form"  action="">
        <div class="layui-form-item " >
            <label class="layui-form-label"  >员工工号</label>
            <div class="layui-input-block">
                <input type="text" id="addnumber" name="addnumber"  value=""   lay-verify="required|numberalone" placeholder="请输入员工工号" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" >
            <label class="layui-form-label"  >员工名称</label>
            <div class="layui-input-block">
                <input type="text" id="addname" name="addname"   value=""   lay-verify="required" placeholder="请输入员工名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >密码</label>
            <div class="layui-input-block">
                <input type="password" id="addpassword" name="addpassword"    lay-verify="required|firstpwd" placeholder="" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >重复密码</label>
            <div class="layui-input-block">
                <input type="password" id="addpassword2" name="addpassword2"    lay-verify="required|secondpwd" placeholder="" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >性别</label>
            <div class="layui-input-block" >
                <select id="addsex" name="addsex" >
                    <option value="0">男</option>
                    <option value="1">女</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >所在部门</label>
            <div class="layui-input-block">
                <input type="text" id="adddepartment"  name="adddepartment"  value=""   disabled="false" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >电话</label>
            <div class="layui-input-block">
                <input type="text" id="addtelephone"  name="addtelephone"  value=""  lay-verify="required|phone"   autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >邮箱</label>
            <div class="layui-input-block">
                <input type="text" id="addemail"  name="addemail"  value=""  lay-verify="required|email"  autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >备注&emsp;</label>
            <div class="layui-input-block">
                <textarea id="addnotes" name="addnotes" placeholder="请输入内容" class="layui-textarea" ></textarea>
                <!-- <input type="hidden" name="id" style="width: 240px" autocomplete="off" class="layui-input"> -->
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="save" >立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary" id="closeBtn" >重置</button>
            </div>
        </div>
    </form>
</div>

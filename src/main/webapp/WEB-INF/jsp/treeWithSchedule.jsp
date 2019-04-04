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
        console.log($("#ifr"));
        $("#ifr").attr("src","/department/"+chooseDid+"/schedule");
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
                    <iframe src="" id="ifr" name ="iframe_b" frameborder="0" style="width: 100%;height: 600px"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

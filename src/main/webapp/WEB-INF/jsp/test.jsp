<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>员工考勤系统</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <link rel="stylesheet" href="ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script src="/js/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript" src="ztree/js/jquery.ztree.core.js"></script>
    <SCRIPT LANGUAGE="JavaScript">
        var zTreeObj;
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        var setting = {
            view :{
                showIcon:false
            }
        };
        // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
        var zNodes = [
            {name:"test1", open:true, children:[
                    {name:"test1_1"}, {name:"test1_2"}]},
            {name:"test2", open:true, children:[
                    {name:"test2_1"}, {name:"test2_2"}]}
        ];
        $(document).ready(function(){
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });
    </SCRIPT>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin" >
    <div class="layui-header" >
        <div class="layui-logo" style="font-weight: lighter;font-size: 17px;color: #eeeeee"><i class="layui-icon layui-icon-app"></i>员工考勤系统</div>
        <!-- 头部区域（可配合layui已有的水平导航）
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        -->
        <ul class="layui-nav layui-layout-right" >
            <li class="layui-nav-item" >
                <a href="javascript:;" >

                    ${user.name}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item" ><a href="/logout">注销</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item">
                    <a href="javascript:;">部门管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="employee" target="iframe_a">员工管理</a></dd>
                        <dd><a href="department" target="iframe_a">部门设置</a></dd>
                        <dd><a href="manage" target="iframe_a">权限管理</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">考勤管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="clazz" target="iframe_a">班次管理</a></dd>
                        <dd><a href="schedule" target="iframe_a">排班管理</a></dd>
                        <dd><a href="leavee" target="iframe_a">请假审批</a></dd>
                        <dd><a href="goout" target="iframe_a">外出审批</a></dd>
                        <dd><a href="overtime" target="iframe_a">加班审批</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">考勤统计</a>
                    <dl class="layui-nav-child">
                        <dd><a href="department/countall" target="iframe_a">部门统计</a></dd>
                        <dd><a href="department/count" target="iframe_a">员工统计</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <iframe src="department" name="iframe_a" style="width: 100%;height: 100%;border-width: 0;background-color: #fbfbfb;"></iframe>

        <!--
        <table id="demo" lay-filter="test"></table>
        <ul id="treeDemo" class="ztree"></ul>
        -->
        <!--

    <div class="layui-footer">
        © layui.com - 底部固定区域
    </div>
    -->
</div>
<script src="/layui/layui.js"></script>
<script>
    //一般直接写在一个js文件中
    layui.use(['element','table','layer'], function(){
        var element = layui.element;
        var table = layui.table;
        var layer = layui.layer;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 312
            ,url: '/employee/jsona/' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'number', title: '工号', sort: true}
                ,{field: 'username', title: '姓名'}
                ,{field: 'telephones', title: '电话',  sort: true}
                ,{align:'center', toolbar: '#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                console.log(data);
                layer.msg("sdasda");
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if(layEvent === 'edit'){ //编辑
                table.reload("demo",{});
            }
        });
    });
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</body>
</html>

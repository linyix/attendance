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
            elem: '#daterange1' //指定元素
            ,type: 'date'
            ,range: false
        });
        tableIns =table.render({
            elem: '#demo'
            ,url: 'myleavee/json' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', title: '编号', }
                ,{field: 'employeeNumber', title: '申请人工号'}
                ,{field: 'employeeName', title: '申请人姓名'}
                ,{field: 'starttime', title: '开始时间'}
                ,{field: 'endtime', title: '结束时间'  }
                ,{field: 'passed', title: '状态'  }
                ,{align:'center', toolbar: '#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                onEditBtn(data.id);
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if(layEvent === 'check'){ //编辑
                    onCheckBtn(data.id);
                //table.reload("demo",{});
            }
        });

        form.on('submit(save)', function (data) {
            var page = "/myleavee";
            $.post(
                page,
                {"daterange":$("#daterange").val(),"notes":$("#addnotes").val()},
                function(result) {
                    console.log("f"+result);
                    if ("success" == result) {
                        tableIns.reload( {});
                        layer.close(addIndex);
                    }
                    else {
                        console.log(result);
                        layer.msg(result);
                    }
                }
            );
            return false;

        });
    });

    function onAddBtn(){
        //页面层-自定义
        addIndex= layer.open({
            type: 1,
            title:"新建配置",
            closeBtn: false,
            shift: 2,
            area: ['530px', '420px'],
            shadeClose: true,
            // btn: ['新增', '取消'],
            // btnAlign: 'c',
            content: $("#add-main"),
            success: function(layero, index){},
            yes:function(){

            }
        });
        //form.render();
    };

    function onEditBtn(lid){
        //页面层-自定义
        var url="leavee/"+lid+"/json";
        $.post(
            url,
            function(data) {
                console.log(data);
                var json=JSON.parse(data);
                $("#editdaterange").val(json.start+" - "+json.end);
                $("#editnotes").val(json.leavee.notes);
                editIndex= layer.open({
                    type: 1,
                    title:"新建配置",
                    closeBtn: false,
                    shift: 2,
                    area: ['530px', '420px'],
                    shadeClose: true,
                    // btn: ['新增', '取消'],
                    // btnAlign: 'c',
                    content: $("#edit-main"),
                    success: function(layero, index){},
                    yes:function(){

                    }
                });
            });

    };

    function onCheckBtn(lid){
        //页面层-自定义
        var url="leavee/"+lid+"/leaveecheck/json";
        $.post(
            url,
            function(data) {
                console.log(data);
                if(data === "notexist")
                {
                    layer.msg("该申请未审批");
                }
                else
                {
                    var json=JSON.parse(data);
                    $("#checkName").val(json.employee.name);
                    $("#checkNumber").val(json.employee.number);
                    $("#checknotes").val(json.leaveecheck.notes);
                    if(json.leaveecheck.pass ==1)
                        $("#checkType").val("通过");
                    else
                        $("#checkType").val("未通过");
                    checkIndex= layer.open({
                        type: 1,
                        title: "新建配置",
                        closeBtn: false,
                        shift: 2,
                        area: ['530px', '420px'],
                        shadeClose: true,
                        // btn: ['新增', '取消'],
                        // btnAlign: 'c',
                        content: $("#check-main"),
                        success: function (layero, index) {
                        },
                        yes: function () {

                        }
                    });
                }
            })

    };


</script>


<div class="layui-tab">
    <ul class="layui-tab-title">
        <li class="layui-this">按天查看</li>
        <li>按周查看</li>
        <li>按月查看</li>
        <li>按季度查看</li>
        <li>年查看</li>
    </ul>


    <div class="layui-tab-content">

        <div class="layui-tab-item layui-show">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-form toolbar">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text"  class="layui-input" id="daterange1">
                            </div>
                            <div class="layui-inline">
                                <button id="btnSearch" class="layui-btn icon-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
                            </div>
                        </div>
                    </div>

                    <div class="layui-fluid">
                        <div class="layui-row layui-col-space15">
                            <div class="layui-col-sm12 layui-col-md5 layui-col-lg5" >
                                <div id="main1"  style="height:300px;"></div>
                                <script type="text/javascript">
                                    // 基于准备好的dom，初始化echarts实例
                                    var myChart = echarts.init(document.getElementById('main1'));

                                    // 指定图表的配置项和数据
                                    var option = {
                                        tooltip: {
                                            trigger: 'item',
                                            formatter: "{a} <br/>{b}: {c} ({d}%)"
                                        },
                                        legend: {
                                            orient: 'vertical',
                                            x: 'left',
                                            data:['已出勤','未出勤']
                                        },
                                        series: [
                                            {
                                                name:'',
                                                type:'pie',
                                                radius: ['50%', '70%'],
                                                color:['#bad7c7','#d9aa98'],
                                                avoidLabelOverlap: false,
                                                label: {
                                                    normal: {
                                                        show: false,
                                                        position: 'center'
                                                    },
                                                    emphasis: {
                                                        show: true,
                                                        textStyle: {
                                                            fontSize: '30',
                                                            fontWeight: 'bold'
                                                        }
                                                    }
                                                },
                                                labelLine: {
                                                    normal: {
                                                        show: false
                                                    }
                                                },
                                                data:[
                                                    {value:12, name:'已出勤'},
                                                    {value:2, name:'未出勤'}
                                                ]
                                            }
                                        ]
                                    };

                                    // 使用刚指定的配置项和数据显示图表。
                                    myChart.setOption(option);
                                </script>

                            </div>

                            <div class="layui-col-sm12 layui-col-md5 layui-col-lg5" style="margin-left: 60px">
                                <div id="main2" style="height:300px;" ></div>
                                <script type="text/javascript">
                                    // 基于准备好的dom，初始化echarts实例
                                    var myChart = echarts.init(document.getElementById('main2'));

                                    // 指定图表的配置项和数据
                                    var option = {
                                        title: {
                                            text: '考勤统计'
                                        },
                                        tooltip: {},
                                        color:'#9bbcc1',
                                        legend: {
                                            data:['人数']
                                        },
                                        xAxis: {
                                            data: ["缺勤","迟到","早退","请假","出差","加班"]
                                        },
                                        yAxis: {},
                                        series: [{
                                            name: '人数',
                                            type: 'bar',
                                            data: [1, 3, 2, 1, 2, 5]
                                        }]
                                    };

                                    // 使用刚指定的配置项和数据显示图表。
                                    myChart.setOption(option);
                                </script>
                            </div>
                        </div>

                        <div id="main3"  style="height:350px;"></div>
                        <script type="text/javascript">
                            // 基于准备好的dom，初始化echarts实例
                            var myChart = echarts.init(document.getElementById('main3'));

                            // 指定图表的配置项和数据
                            var option = {
                                title: {
                                    text: '考勤统计变化情况'
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data: ["缺勤","迟到","早退","请假","出差","加班"],
                                },
                                grid: {
                                    left: '3%',
                                    right: '4%',
                                    bottom: '3%',
                                    containLabel: true
                                },
                                toolbox: {
                                    feature: {
                                        saveAsImage: {}
                                    }
                                },
                                xAxis: {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: ['4/15','4/6','4/7','4/8','4/9','4/10','4/11']
                                },
                                yAxis: {
                                    type: 'value'
                                },
                                series: [
                                    {
                                        name:'缺勤',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 0, 2, 3, 4, 3, 2]
                                    },
                                    {
                                        name:'迟到',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 3, 3, 1, 0, 2, 3]
                                    },
                                    {
                                        name:'早退',
                                        type:'line',
                                        stack: '总量',
                                        data:[3, 1, 4, 3, 3, 5, 2]
                                    },
                                    {
                                        name:'请假',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 2, 1, 3, 2, 1, 1]
                                    },
                                    {
                                        name:'出差',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 2, 4, 4, 3, 2, 2]
                                    },
                                    {
                                        name:'加班',
                                        type:'line',
                                        stack: '总量',
                                        data:[1, 3, 6, 4, 2, 4, 5]
                                    }
                                ]
                            };


                            // 使用刚指定的配置项和数据显示图表。
                            myChart.setOption(option);
                        </script>

                    </div>

                </div>
            </div>
        </div>

        <div class="layui-tab-item layui-show">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-form toolbar">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text"  class="layui-input" id="daterange2">
                            </div>
                            <div class="layui-inline">
                                <button id="btnSearch2" class="layui-btn icon-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
                            </div>
                        </div>
                    </div>

                    <div class="layui-fluid">
                        <div class="layui-row layui-col-space15">
                            <div class="layui-col-sm12 layui-col-md5 layui-col-lg5" >
                                <div id="week1"  style="height:300px;"></div>
                                <script type="text/javascript">
                                    // 基于准备好的dom，初始化echarts实例
                                    var myChart = echarts.init(document.getElementById('week1'));

                                    // 指定图表的配置项和数据
                                    var option = {
                                        tooltip: {
                                            trigger: 'item',
                                            formatter: "{a} <br/>{b}: {c} ({d}%)"
                                        },
                                        legend: {
                                            orient: 'vertical',
                                            x: 'left',
                                            data:['已出勤','未出勤']
                                        },
                                        series: [
                                            {
                                                name:'',
                                                type:'pie',
                                                radius: ['50%', '70%'],
                                                color:['#bad7c7','#d9aa98'],
                                                avoidLabelOverlap: false,
                                                label: {
                                                    normal: {
                                                        show: false,
                                                        position: 'center'
                                                    },
                                                    emphasis: {
                                                        show: true,
                                                        textStyle: {
                                                            fontSize: '30',
                                                            fontWeight: 'bold'
                                                        }
                                                    }
                                                },
                                                labelLine: {
                                                    normal: {
                                                        show: false
                                                    }
                                                },
                                                data:[
                                                    {value:12, name:'已出勤'},
                                                    {value:2, name:'未出勤'}
                                                ]
                                            }
                                        ]
                                    };

                                    // 使用刚指定的配置项和数据显示图表。
                                    myChart.setOption(option);
                                </script>

                            </div>

                            <div class="layui-col-sm12 layui-col-md5 layui-col-lg5" style="margin-left: 60px">
                                <div id="main2" style="height:300px;" ></div>
                                <script type="text/javascript">
                                    // 基于准备好的dom，初始化echarts实例
                                    var myChart = echarts.init(document.getElementById('main2'));

                                    // 指定图表的配置项和数据
                                    var option = {
                                        title: {
                                            text: '考勤统计'
                                        },
                                        tooltip: {},
                                        color:'#9bbcc1',
                                        legend: {
                                            data:['人数']
                                        },
                                        xAxis: {
                                            data: ["缺勤","迟到","早退","请假","出差","加班"]
                                        },
                                        yAxis: {},
                                        series: [{
                                            name: '人数',
                                            type: 'bar',
                                            data: [1, 3, 2, 1, 2, 5]
                                        }]
                                    };

                                    // 使用刚指定的配置项和数据显示图表。
                                    myChart.setOption(option);
                                </script>
                            </div>
                        </div>

                        <div id="main3"  style="height:350px;"></div>
                        <script type="text/javascript">
                            // 基于准备好的dom，初始化echarts实例
                            var myChart = echarts.init(document.getElementById('main3'));

                            // 指定图表的配置项和数据
                            var option = {
                                title: {
                                    text: '考勤统计变化情况'
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data: ["缺勤","迟到","早退","请假","出差","加班"],
                                },
                                grid: {
                                    left: '3%',
                                    right: '4%',
                                    bottom: '3%',
                                    containLabel: true
                                },
                                toolbox: {
                                    feature: {
                                        saveAsImage: {}
                                    }
                                },
                                xAxis: {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: ['4/15','4/6','4/7','4/8','4/9','4/10','4/11']
                                },
                                yAxis: {
                                    type: 'value'
                                },
                                series: [
                                    {
                                        name:'缺勤',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 0, 2, 3, 4, 3, 2]
                                    },
                                    {
                                        name:'迟到',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 3, 3, 1, 0, 2, 3]
                                    },
                                    {
                                        name:'早退',
                                        type:'line',
                                        stack: '总量',
                                        data:[3, 1, 4, 3, 3, 5, 2]
                                    },
                                    {
                                        name:'请假',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 2, 1, 3, 2, 1, 1]
                                    },
                                    {
                                        name:'出差',
                                        type:'line',
                                        stack: '总量',
                                        data:[2, 2, 4, 4, 3, 2, 2]
                                    },
                                    {
                                        name:'加班',
                                        type:'line',
                                        stack: '总量',
                                        data:[1, 3, 6, 4, 2, 4, 5]
                                    }
                                ]
                            };


                            // 使用刚指定的配置项和数据显示图表。
                            myChart.setOption(option);
                        </script>

                    </div>

                </div>
            </div>
        <div class="layui-tab-item">内容3</div>
        <div class="layui-tab-item">内容4</div>
        <div class="layui-tab-item">内容5</div>
    </div>



</div>


</body>



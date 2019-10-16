var rowW;
function timeSearch() {
    rowW = $('#datagrid').datagrid('getSelected');
    if (rowW) {
        var timeS = document.getElementById("timeS");
        $.ajax({
            type: "POST",
            url: pathJs + "/Statistic/areaVisual",
            dataType: "json",
            data: {
                oid: rowW.oldId,
                rid: rowW.rid,
                time:timeS.value
            },
            async: false,
            success: function (data) {
                if (data.success) {
                    //柱状图
                    for(var i = 0;i<data.data.length;i++){
                        var xAxisData = [];
                        var seriesData = [];
                        var legendLineData = [];
                        for (var n = 0; n < data.data[i].areaVisuals.length; n++) {
                            legendLineData.push(data.data[i].areaVisuals[n].areaName);
                        }

                        for (var q = 0; q < data.data[i].areaVisuals.length; q++) {
                            xAxisData.push(data.data[i].areaVisuals[q].areaName);
                            seriesData.push(data.data[i].areaVisuals[q].sumTime);
                            //seriesData[s].data.push(0);
                        }


                        var optionLine_new = {
                            title: {
                                text: '区域时间分布',
                                color: '#1ab394'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: legendLineData
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
                                data: xAxisData
                            },
                            yAxis: {
                                type: 'value',
                                axisLabel: {
                                    formatter: '{value} min'
                                }
                            },
                            series: [
                                {
                                    name: '区域时间统计',
                                    type: 'bar',
                                    itemStyle: {
                                        normal: {
                                            color: function (params) {
                                                // build a color map as your need.
                                                var colorList = [
                                                    '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                                    '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                                    '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                                                ];
                                                return colorList[params.dataIndex]
                                            },
                                            label: {
                                                show: true,
                                                position: 'top',
                                                formatter: '{b}\n{c}'
                                            }
                                        }
                                    },
                                    //  data: [data.data[0].sumTime,data.data[1].sumTime,data.data[2].sumTime,data.data[3].sumTime,data.data[4].sumTime,data.data[5].sumTime,data.data[6].sumTime,data.data[7].sumTime,data.data[8].sumTime,data.data[9].sumTime,data.data[10].sumTime],
                                    data: seriesData,
                                    markPoint: {
                                        tooltip: {
                                            trigger: 'item',
                                            backgroundColor: 'rgba(0,0,0,0)'
                                        },
                                        data: []
                                    }
                                }
                            ]

                        };
                        if(i == 0){
                            $("#mainLineDiv").hide();
                            $("#mainLine1Div").hide();
                            $("#mainLine2Div").hide();
                        }
                        switch(i) {
                            case 0:
                                $("#mainLineDiv .title").html(data.data[0].roomName);
                                myChartLine.setOption(optionLine_new);
                                break;
                            case 1:
                                $("#mainLine1Div .title").html(data.data[1].roomName);
                                myChartLine1.setOption(optionLine_new);
                                break;
                            case 2:
                                $("#mainLine2Div .title").html(data.data[2].roomName);
                                myChartLine2.setOption(optionLine_new);
                        }
                    }
                    if($("#mainLineDiv .title").html().length>0){
                        $("#mainLineDiv").show();
                        if ($("#mainLine1Div .title").html().length > 0) {
                            $("#mainLine1Div").show();
                            if ($("#mainLine2Div .title").html().length > 0) {
                                $("#mainLine2Div").show();
                            }
                            else {
                                $("#mainLine2Div").hide();
                            }
                        }
                        else {
                            $("#mainLine1Div").hide();
                            $("#mainLine2Div").hide();
                        }
                    }
                    else {
                        $("#mainLineDiv").hide();
                        $("#mainLine1Div").hide();
                        $("#mainLine2Div").hide();
                    }


                }
            }
        });
    }else{
        $.messager.alert('提示', '请选择条目！', 'error');
    }
}
function dayB(){
    $("#mainLineDiv").hide();
    $("#mainLine1Div").hide();
    $("#mainLine2Div").hide();
    if($("#mainDiv .title").html().length>0){
        $("#mainDiv").show();
        if ($("#main1Div .title").html().length > 0) {
            $("#main1Div").show();
            if ($("#main2Div .title").html().length > 0) {
                $("#main2Div").show();
            }
            else {
                $("#main2Div").hide();
            }
        }
        else {
            $("#main1Div").hide();
            $("#main2Div").hide();
        }
    }
    else {
        $("#mainDiv").hide();
        $("#main1Div").hide();
        $("#main2Div").hide();
    }

    $(".dayButton").addClass("disabled");
    $(".roomsButton").removeClass("disabled");
}

function roomsB(){
    $.ajax({
        type: "POST",
        url: pathJs+"/Statistic/areaVisual",
        dataType: "json",
        data: {
            oid: row.oldId,
            rid: row.rid
        },
        async:false,
        success:function(data) {
            if (data.success) {
                //柱状图
                for(var i = 0;i<data.data.length;i++){
                    var xAxisData = [];
                    var seriesData = [];
                    var legendLineData = [];
                    for (var n = 0; n < data.data[i].areaVisuals.length; n++) {
                        legendLineData.push(data.data[i].areaVisuals[n].areaName);
                    }

                    for (var q = 0; q < data.data[i].areaVisuals.length; q++) {
                        xAxisData.push(data.data[i].areaVisuals[q].areaName);
                        seriesData.push(data.data[i].areaVisuals[q].sumTime);
                        //seriesData[s].data.push(0);
                    }


                    var optionLine = {
                        title: {
                            text: '区域时间分布',
                            color: '#1ab394'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: legendLineData
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
                            data: xAxisData
                        },
                        yAxis: {
                            type: 'value',
                            axisLabel: {
                                formatter: '{value} min'
                            }
                        },
                        series: [
                            {
                                name: '区域时间统计',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        color: function (params) {
                                            // build a color map as your need.
                                            var colorList = [
                                                '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                                '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                                '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                                            ];
                                            return colorList[params.dataIndex]
                                        },
                                        label: {
                                            show: true,
                                            position: 'top',
                                            formatter: '{b}\n{c}'
                                        }
                                    }
                                },
                                //  data: [data.data[0].sumTime,data.data[1].sumTime,data.data[2].sumTime,data.data[3].sumTime,data.data[4].sumTime,data.data[5].sumTime,data.data[6].sumTime,data.data[7].sumTime,data.data[8].sumTime,data.data[9].sumTime,data.data[10].sumTime],
                                data: seriesData,
                                markPoint: {
                                    tooltip: {
                                        trigger: 'item',
                                        backgroundColor: 'rgba(0,0,0,0)'
                                    },
                                    data: []
                                }
                            }
                        ]

                    };
                    if(i == 0){
                        $("#mainLineDiv .title").html(null);
                        $("#mainLine1Div .title").html(null);
                        $("#mainLine2Div .title").html(null);
                    }
                    switch(i) {
                        case 0:
                            $("#mainLineDiv .title").html(data.data[0].roomName);
                            myChartLine.setOption(optionLine);
                            break;
                        case 1:
                            $("#mainLine1Div .title").html(data.data[1].roomName);
                            myChartLine1.setOption(optionLine);
                            break;
                        case 2:
                            $("#mainLine2Div .title").html(data.data[2].roomName);
                            myChartLine2.setOption(optionLine);
                    }
                }
            }
            $("#mainDiv").hide();
            $("#main1Div").hide();
            $("#main2Div").hide();
            if($("#mainLineDiv .title").html().length>0){
                $("#mainLineDiv").show();
                if ($("#mainLine1Div .title").html().length > 0) {
                    $("#mainLine1Div").show();
                    if ($("#mainLine2Div .title").html().length > 0) {
                        $("#mainLine2Div").show();
                    }
                    else {
                        $("#mainLine2Div").hide();
                    }
                }
                else {
                    $("#mainLine1Div").hide();
                    $("#mainLine2Div").hide();
                }
            }
            else {
                $("#mainLineDiv").hide();
                $("#mainLine1Div").hide();
                $("#mainLine2Div").hide();
            }
            $(".dayButton").removeClass("disabled");
            $(".roomsButton").addClass("disabled");
        }
    });
}
var row;

$(function(){
    $(".active",parent.document).removeClass("active");
    $("#index + li + li",parent.document).addClass("active");
    $("#mainDiv").hide();
    $("#main1Div").hide();
    $("#main2Div").hide();
    $("#mainLineDiv").hide();
    $("#mainLine1Div").hide();
    $("#mainLine2Div").hide();
    $("#datagrid").datagrid({
        onClickRow: function () {
           row = $('#datagrid').datagrid('getSelected');
            rowW=row;
            //获得老人生活规律模型 构建环形图、折线图


            ajax1 = $.ajax({
                type: "POST",
                url: pathJs + "/Statistic/areaLine",
                dataType: "json",
                data: {
                    oid: row.oldId,
                    rid: row.rid
                },
                async: false,
                success: function (data) {
                    for(var i=0;i<data.data.length;i++){
                        var xAxisData=[];
                        var seriesData=[];
                        var legendLineData=[];
                        for(var n=0;n<data.data[i].areaVisual[0].areaVisuals.length;n++){
                            legendLineData.push(data.data[i].areaVisual[0].areaVisuals[n].areaName);
                            seriesData.push({
                                name:data.data[i].areaVisual[0].areaVisuals[n].areaName,
                                type:'line',
                                // stack:'time',
                                data:[]
                            });

                        }
                        for(var q=data.data[i].areaVisual.length - 1;q >=0;q--){
                            xAxisData.push(data.data[i].areaVisual[q].date);
                            for(var s=0;s<seriesData.length;s++){
                                seriesData[s].data.push(data.data[i].areaVisual[q].areaVisuals[s].sumTime);
                            }

                        }
                        var option = {
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: legendLineData
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: xAxisData
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel:{
                                        formatter:'{value} min'
                                    }
                                }
                            ],
                            series: seriesData
                        };
                        if(i == 0){
                            $("#mainDiv .title").html(null);
                            $("#main1Div .title").html(null);
                            $("#main2Div .title").html(null);
                        }

                        switch(i) {
                            case 0:
                                $("#mainDiv .title").html(data.data[0].roomName);
                                myChart.setOption(option);
                                break;
                            case 1:
                                $("#main1Div .title").html(data.data[1].roomName);
                                myChart1.setOption(option);
                                break;
                            case 2:
                                $("#main2Div .title").html(data.data[2].roomName);
                                myChart2.setOption(option);
                        }
                    }
                }
            });
            // $("#mainLineDiv").show();
            // $("#mainLine1Div").show();
            // $("#mainLine2Div").show();
            // $("#mainDiv").show();
            dayB();
            }


       });
});
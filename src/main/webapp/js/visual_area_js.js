//var piedata=[];
var rowW;

$(function() {
    $(".active",parent.document).removeClass("active");
    $("#index + li + li",parent.document).addClass("active");
    $("#main1Div").hide();
    $("#main2Div").hide();
    $("#main3Div").hide();
    $("#main4Div").hide();
    $("#main5Div").hide();
    $("#main6Div").hide();
    $("#mainDiv").hide();
    $("#mainLineDiv").hide();
    $("#datagrid").datagrid({
        onClickRow: function () {
            var row = $('#datagrid').datagrid('getSelected');
            rowW=row;
            //获得老人生活规律模型 构建环形图、折线图
            $.ajax({
                type: "POST",
                url: pathJs+"/visual/getLiveVisual",
                dataType: "json",
                data: {
                    oldId: row.oid
                },
                async:false,
                success:function(data){
                    if(data.success){
                        //环形图
                        var piedata=[];//具体显示数据
                        var legendData=data.data.roomNames;
                        for(var k=0;k<data.data.oldManLive_rooms.length;k++) {
                            piedata.push({value: data.data.oldManLive_rooms[k].value, name: data.data.oldManLive_rooms[k].roomName,time:data.data.oldManLive_rooms[k].time});
                        }
                        var optionPie= {
                            // title : {
                            //     text: '生活规律',
                            //     x:'center',
                            //     y:'center',
                            //     textStyle: {
                            //         fontSize:24,
                            //         fontFamily:'Arial',
                            //         fontWeight:100
                            //     }
                            // },
                            tooltip: {
                                trigger: 'item',
                                formatter: function(params) {
                                    var res = params.name+"<br/>时间段："+ params.data.time+"<br/>一共"+params.value+"分钟 (占全天"+params.percent+"%)";
                                    return res;
                                }
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'left',
                                data:legendData
                            },
                            series: [
                                {
                                    name:'类型',
                                    type:'pie',
                                    radius: ['50%', '70%'],
                                    avoidLabelOverlap: false,
                                    label: {
                                        normal: {
                                            show: false,
                                            position: 'center'
                                        },
                                        emphasis: {
                                            show: false
                                        }
                                    },
                                    labelLine: {
                                        normal: {
                                            show: false
                                        }
                                    },
                                    data:piedata
                                }
                            ]
                        };
                        myChart.setOption(optionPie);


                        //柱状图
                        var xAxisData=[];
                        var seriesData=[];
                        var legendLineData=[];
                        for(var n=0;n<data.data.roomNames.length;n++){
                            if(data.data.roomNames[n].indexOf("&")==-1) {
                                legendLineData.push(data.data.roomNames[n]);
                                seriesData.push({
                                    name:data.data.roomNames[n],
                                    type:'bar',
                                    step: 'start',
                                    data:[]
                                });
                            }
                        }
                        for(var q=0;q<data.data.oldManLive_rooms.length;q++){
                            xAxisData.push(data.data.oldManLive_rooms[q].time);
                            for(var s=0;s<seriesData.length;s++){
                                if(data.data.oldManLive_rooms[q].roomName.indexOf(seriesData[s].name)!=-1){
                                    seriesData[s].data.push(data.data.oldManLive_rooms[q].value);
                                }else{
                                    seriesData[s].data.push(0);
                                }
                            }

                        }

                        var optionLine = {
                            title: {
                                text: '柱状图',
                                color:'#1ab394'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data:legendLineData
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
                                type: 'value'
                            },
                            series:seriesData
                        };
                        myChartLine.setOption(optionLine);

                    }
                }
            });


            // // 获得各房间的模型数据 构建环形图
            // $.ajax({
            //    type: "POST",
            //    url: pathJs+"/visual/getRoomVisual",
            //    dataType: "json",
            //    data: {
            //        oldId: row.oid
            //    },
            //    async:false,
            //    success:function(data){
            //        if(data.success){
            //            $(".visual").hide();
            //            for(var i=0;i<data.data.length;i++){
            //                var piedata=[];//具体显示数据
            //                //var m=0;
            //                for(var k=0;k<data.data[i].time.length;k++) {
            //                    piedata.push({value: data.data[i].time[k].value, name: data.data[i].time[k].type,time:data.data[i].time[k].time});
            //                }
            //                var total=0;//总时间
            //                for(var j=0;j<piedata.length;j++){
            //                    if(piedata[j].name=="活动时间"||piedata[j].name=="休息时间"){
            //                        total+=parseInt(piedata[j].value);
            //                    }
            //                }
            //                var option= {
            //                    title : {
            //                        text: data.data[i].roomName,
            //                        subtext: "共"+total+"分钟",
            //                        x:'center',
            //                        y:'center',
            //                        textStyle: {
            //                            fontSize:24,
            //                            fontFamily:'Arial',
            //                            fontWeight:100
            //                        },
            //                        subtextStyle: {
            //                            fontSize:10,
            //                            fontFamily:'Arial',
            //                            color:"#969696"
            //                        }
            //                    },
            //                    tooltip: {
            //                        trigger: 'item',
            //                        //formatter: "{a} <br/>{b}（{data.data[i].time[m++].time}）<br/>一共{c}分钟 (占全天{d}%)"
            //                        formatter: function(params) {
            //                            var res = params.name+"<br/>时间段："+ params.data.time+"<br/>一共"+params.value+"分钟 (占全天"+params.percent+"%)";
            //                            return res;
            //                        }
            //                    },
            //                    legend: {
            //                        orient: 'vertical',
            //                        x: 'left',
            //                        data:['活动时间','休息时间','不在房间']
            //                    },
            //                    //color:['#A52A2A','#36648B','#FF7F00'],
            //                    series: [
            //                        {
            //                            name:'类型',
            //                            type:'pie',
            //                            radius: ['50%', '70%'],
            //                            avoidLabelOverlap: false,
            //                            label: {
            //                                normal: {
            //                                    show: false,
            //                                    position: 'center'
            //                                },
            //                                emphasis: {
            //                                    show: false
            //                                }
            //                            },
            //                            labelLine: {
            //                                normal: {
            //                                    show: false
            //                                }
            //                            },
            //                            data:piedata
            //                        }
            //                    ]
            //                };
            //                switch(i) {
            //                    case 0:
            //                        myChart1.setOption(option);
            //                        break;
            //                    case 1:
            //                        myChart2.setOption(option);
            //                        break;
            //                    case 2:
            //                        myChart3.setOption(option);
            //                        break;
            //                    case 3:
            //                        myChart4.setOption(option);
            //                        break;
            //                    case 4:
            //                        myChart5.setOption(option);
            //                        break;
            //                    case 5:
            //                        myChart6.setOption(option);
            //                        break;
            //                }
            //            }
            //        }
            //    }
            // });
            dayB();
        }
    });


});

function fixHtmlWidth(percent){
    return $("#datagrid").width()*percent;
}

function dayB(){
    $("#main1Div").hide();
    $("#main2Div").hide();
    $("#main3Div").hide();
    $("#main4Div").hide();
    $("#main5Div").hide();
    $("#main6Div").hide();
    $("#mainDiv").show();
    $("#mainLineDiv").show();
    $(".dayButton").addClass("disabled");
    $(".roomsButton").removeClass("disabled");
}

function roomsB() {
    // 获得各房间的模型数据 构建环形图
    $.ajax({
        type: "POST",
        url: pathJs+"/visual/getRoomVisual",
        dataType: "json",
        data: {
            oldId: rowW.oid
        },
        async:false,
        success:function(data){
            if(data.success){
                // $(".visual").hide();
                for(var i=0;i<data.data.length;i++){
                    var piedata=[];//具体显示数据
                    //var m=0;
                    for(var k=0;k<data.data[i].time.length;k++) {
                        piedata.push({value: data.data[i].time[k].value, name: data.data[i].time[k].type,time:data.data[i].time[k].time});
                    }
                    var total=0;//总时间
                    for(var j=0;j<piedata.length;j++){
                        if(piedata[j].name=="活动时间"||piedata[j].name=="休息时间"){
                            total+=parseInt(piedata[j].value);
                        }
                    }
                    var option= {
                        title : {
                            // text: data.data[i].roomName,
                            subtext: "共"+total+"分钟",
                            x:'center',
                            y:'center',
                            textStyle: {
                                fontSize:24,
                                fontFamily:'Arial',
                                fontWeight:100
                            },
                            subtextStyle: {
                                fontSize:10,
                                fontFamily:'Arial',
                                color:"#969696"
                            }
                        },
                        tooltip: {
                            trigger: 'item',
                            //formatter: "{a} <br/>{b}（{data.data[i].time[m++].time}）<br/>一共{c}分钟 (占全天{d}%)"
                            formatter: function(params) {
                                var res = params.name+"<br/>时间段："+ params.data.time+"<br/>一共"+params.value+"分钟 (占全天"+params.percent+"%)";
                                return res;
                            }
                        },
                        legend: {
                            orient: 'vertical',
                            x: 'left',
                            data:['活动时间','休息时间','不在房间']
                        },
                        //color:['#A52A2A','#36648B','#FF7F00'],
                        series: [
                            {
                                name:'类型',
                                type:'pie',
                                radius: ['50%', '70%'],
                                avoidLabelOverlap: false,
                                label: {
                                    normal: {
                                        show: false,
                                        position: 'center'
                                    },
                                    emphasis: {
                                        show: false
                                    }
                                },
                                labelLine: {
                                    normal: {
                                        show: false
                                    }
                                },
                                data:piedata
                            }
                        ]
                    };
                    switch(i) {
                        case 0:
                            $("#main1Div .title").html(data.data[0].roomName);
                            myChart1.setOption(option);
                            break;
                        case 1:
                            $("#main2Div .title").html(data.data[1].roomName);
                            myChart2.setOption(option);
                            break;
                        case 2:
                            $("#main3Div .title").html(data.data[2].roomName);
                            myChart3.setOption(option);
                            break;
                        case 3:
                            $("#main4Div .title").html(data.data[3].roomName);
                            myChart4.setOption(option);
                            break;
                        case 4:
                            $("#main5Div .title").html(data.data[5].roomName);
                            myChart5.setOption(option);
                            break;
                        case 5:
                            $("#main6Div .title").html(data.data[6].roomName);
                            myChart6.setOption(option);
                            break;
                    }
                }
            }
        }
    });
    $("#mainDiv").hide();
    $("#mainLineDiv").hide();
    if($("#main1Div").html()!=""&&$("#main1Div").html()!=null){
        $("#main1Div").show();
    }
    if($("#main2Div").html()!=""&&$("#main2Div").html()!=null){
        $("#main2Div").show();
    }
    if($("#main3Div").html()!=""&&$("#main3Div").html()!=null){
        $("#main3Div").show();
    }
    if($("#main4Div .title").html().length>0){
        $("#main4Div").show();
    }
    if($("#main5Div .title").html().length>0){
        $("#main5Div").show();
    }
    if($("#main6Div .title").html().length>0){
        $("#main6Div").show();
    }
    $(".dayButton").removeClass("disabled");
    $(".roomsButton").addClass("disabled");
}




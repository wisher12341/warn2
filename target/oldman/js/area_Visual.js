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
                    var xAxisData = [];
                    var seriesData = [];
                    var legendLineData = [];
                    for (var n = 0; n < data.data.length; n++) {
                        legendLineData.push(data.data[n].areaName);
                    }
                    for (var q = 0; q < data.data.length; q++) {
                        xAxisData.push(data.data[q].areaName);
                        seriesData.push(data.data[q].sumTime);
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
                                name: 'ECharts例子个数统计',
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
                    myChartLine.setOption(optionLine_new);

                }
            }
        });
    }else{
        $.messager.alert('提示', '请选择要修改的条目！', 'error');
    }
}

$(function(){
    $(".active",parent.document).removeClass("active");
    $("#index + li + li",parent.document).addClass("active");
    $("#mainDiv").hide();
    $("#mainLineDiv").hide();
    $("#datagrid").datagrid({
        onClickRow: function () {
            var row = $('#datagrid').datagrid('getSelected');
            rowW=row;
            //获得老人生活规律模型 构建环形图、折线图
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
                        var xAxisData = [];
                        var seriesData = [];
                        var legendLineData = [];
                        for (var n = 0; n < data.data.length; n++) {
                            legendLineData.push(data.data[n].areaName);
                        }

                        for (var q = 0; q < data.data.length; q++) {
                            xAxisData.push(data.data[q].areaName);
                            seriesData.push(data.data[q].sumTime);
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
                                    name: 'ECharts例子个数统计',
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
                        myChartLine.setOption(optionLine);

                    }
                }
                });
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
                  var xAxisData=[];
                  var seriesData=[];
                  var legendLineData=[];
                  for(var n=0;n<data.data[0].areaVisuals.length;n++){
                          legendLineData.push(data.data[0].areaVisuals[n].areaName);
                          seriesData.push({
                              name:data.data[0].areaVisuals[n].areaName,
                              type:'line',
                             // stack:'time',
                              data:[]
                          });

                  }
                  for(var q=data.data.length - 1;q >=0;q--){
                      xAxisData.push(data.data[q].date);
                      for(var s=0;s<seriesData.length;s++){
                              seriesData[s].data.push(data.data[q].areaVisuals[s].sumTime);
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
                  myChart.setOption(option);
              }
          });
            $("#mainLineDiv").show();
            $("#mainDiv").show();
            }

       });
});
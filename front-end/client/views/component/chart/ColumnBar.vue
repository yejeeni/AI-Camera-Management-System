<template>
    <div id="chartdiv" ref="chartdiv"></div>
</template>

<script>
import * as am5 from '@amcharts/amcharts5';
import * as am5xy from '@amcharts/amcharts5/xy';
import am5themes_Animated from '@amcharts/amcharts5/themes/Animated';

export default {
    props: {
        chartData: {
            type: Array,
            default: [],
        },

    },
    data() {
        return {
            sortOrder: ["보행자", "자전거", "승용차", "오토바이", "버스", "트럭"],
        };
    },
    methods: {
        createChart(data) {
            let chartWarp = this.$refs["chartdiv"]; // 차트 상위 div ref 매칭
            chartWarp.innerHTML = ""; // 차트 상위 div 내용 초기화 (기존 차트 삭제)
            let div = document.createElement("div"); // 차트를 담을 빈 div 생성 (차트 하위 div)
            div.style.width = "100%"; // 차트를 담을 div의 넓이
            div.style.height = "100%"; // 차트를 담을 div의 높이
            chartWarp.appendChild(div); // 차트 상위 div 안에 차트 하위 div를 추가
            let root = am5.Root.new(div); // 차트 하위 div에 차트(root) 담기
            this.charts = root; // 차트 정보 전역에 담기

            // let root = am5.Root.new(this.$refs.chartdiv);
            root._logo.dispose();

            // Set themes
            // https://www.amcharts.com/docs/v5/concepts/themes/
            root.setThemes([
                am5themes_Animated.new(root)
            ]);


            // Create chart
            // https://www.amcharts.com/docs/v5/charts/xy-chart/
            let chart = root.container.children.push(am5xy.XYChart.new(root, {
                panX: false,
                panY: false,
                wheelX: "panX",
                wheelY: "zoomX",
                paddingLeft: 0,
                layout: root.verticalLayout
            }));

            // Add scrollbar
            // https://www.amcharts.com/docs/v5/charts/xy-chart/scrollbars/
            chart.set("scrollbarX", am5.Scrollbar.new(root, {
                orientation: "horizontal"
            }));



            // Create axes
            // https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
            let xRenderer = am5xy.AxisRendererX.new(root, {
                minorGridEnabled: true
            });
            let xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
                categoryField: "year",
                renderer: xRenderer,
                tooltip: am5.Tooltip.new(root, {})
            }));

            xRenderer.grid.template.setAll({
                location: 1
            })

            xAxis.data.setAll(data);

            let yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
                min: 0,
                // max: 100,
                // numberFormat: "#'%'",
                // strictMinMax: true,
                // calculateTotals: true,
                renderer: am5xy.AxisRendererY.new(root, {
                    strokeOpacity: 0.1
                })
            }));


            // Add legend
            // https://www.amcharts.com/docs/v5/charts/xy-chart/legend-xy-series/
            let legend = chart.children.push(am5.Legend.new(root, {
                centerX: am5.p50,
                x: am5.p50
            }));


            // Add series
            // https://www.amcharts.com/docs/v5/charts/xy-chart/series/
            function makeSeries(name, fieldName) {
                let series = chart.series.push(am5xy.ColumnSeries.new(root, {
                    name: name,
                    stacked: true,
                    xAxis: xAxis,
                    yAxis: yAxis,
                    valueYField: fieldName,
                    // valueYShow: "valueYTotalPercent",
                    categoryXField: "year"
                }));

                series.columns.template.setAll({
                    tooltipText: "{name}, {categoryX}: {valueY}",
                    // tooltipText: "{name}, {categoryX}:{valueYTotalPercent.formatNumber('#.#')}%",
                    tooltipY: am5.percent(10)
                });
                series.data.setAll(data);

                console.log("Creating series for " + data);

                // Make stuff animate on load
                // https://www.amcharts.com/docs/v5/concepts/animations/
                series.appear();

                series.bullets.push(function () {
                    return am5.Bullet.new(root, {
                        sprite: am5.Label.new(root, {
                            text: "{valueY}",
                            // text: "{valueYTotalPercent.formatNumber('#.#')}%",
                            fill: root.interfaceColors.get("alternativeText"),
                            centerY: am5.p50,
                            centerX: am5.p50,
                            populateText: true
                        })
                    });
                });

                legend.data.push(series);
            }

            let keys = null;
            if (data && data.length > 0 && data[0]) {
                keys = Object.keys(data[0]).filter(key => key !== "year");

                this.sortOrder.forEach(key => {
                    if (keys.includes(key)) {
                        let capitalizedKey = key.charAt(0).toUpperCase() + key.slice(1);
                        makeSeries(capitalizedKey, key);
                    }
                });

                // Make stuff animate on load
                // https://www.amcharts.com/docs/v5/concepts/animations/
                chart.appear(1000, 100);
            } else {
                // data가 null 또는 비어있는 경우
                console.log("else문");
                return;
            }


            // // console.log("key",keys)

            // keys.forEach(key => {
            //     let capitalizedKey = key.charAt(0).toUpperCase() + key.slice(1);
            //      makeSeries(capitalizedKey, key);
            // });



        },


    },
    watch: {
        'chartData': function (newData, oldData) {
            console.log(newData);
            this.createChart(newData);
        },
    },
    mounted() {
    },
};
</script>

<style scoped>
#chartdiv {
    height: 100%;
}
</style>


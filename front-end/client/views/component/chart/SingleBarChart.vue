<template>
    <div class="chart" id="chartdiv" ref="chartdiv"></div>
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
            // required: true,
        },
    },
    data() {
        return {
            chart: null,
            sortOrder: ["보행자", "자전거", "승용차", "오토바이", "버스", "트럭"],
        };
    },
    methods: {
        sortData(data) {
            data.sort((a, b) => {
                return this.sortOrder.indexOf(a.category) - this.sortOrder.indexOf(b.category);
            });
        },
        createChart(data) {
            this.sortData(data);
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
            let chart = root.container.children.push(am5xy.XYChart.new(root, {
                panX: true,
                panY: true,
                wheelX: "panX",
                wheelY: "zoomX",
                pinchZoomX: true,
                paddingLeft:0,
                paddingRight:1
            }));

            // Add cursor
            let cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
            cursor.lineY.set("visible", false);

            // Create axes
            let xRenderer = am5xy.AxisRendererX.new(root, { minGridDistance: 30 });
            xRenderer.labels.template.setAll({
                rotation: 0,

            });

            xRenderer.grid.template.setAll({
                location: 1
            });

            let xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
                maxDeviation: 0.3,
                categoryField: "category",
                renderer: xRenderer,
                tooltip: am5.Tooltip.new(root, {})
            }));

            let yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
                maxDeviation: 0.3,
                renderer: am5xy.AxisRendererY.new(root, {
                    strokeOpacity: 0.1
                })
            }));

            // Create series
            let series = chart.series.push(am5xy.ColumnSeries.new(root, {
                name: "Series 1",
                xAxis: xAxis,
                yAxis: yAxis,
                valueYField: "value",
                sequencedInterpolation: true,
                categoryXField: "category",
                tooltip: am5.Tooltip.new(root, {
                    labelText: "{valueY}"
                })
            }));

            series.columns.template.setAll({ cornerRadiusTL: 5, cornerRadiusTR: 5, strokeOpacity: 0 });
            series.columns.template.adapters.add("fill", function (fill, target) {
                return chart.get("colors").getIndex(series.columns.indexOf(target));
            });

            series.columns.template.adapters.add("stroke", function (stroke, target) {
                return chart.get("colors").getIndex(series.columns.indexOf(target));
            });

            
            // Set data
            xAxis.data.setAll(data);
            series.data.setAll(data);


            // Make stuff animate on load
            series.appear(1000);
            chart.appear(1000, 100);
            this.chart = chart;
        },
    },
    watch: {
        'chartData': function (newData, oldData) {
            this.createChart(newData);
        },
    },
    mounted() {
    },
};
</script>

<style scoped>
#chartdiv{
    height: 300px;
}
</style>
  
  
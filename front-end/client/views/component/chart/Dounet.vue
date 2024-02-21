<template>
        <div id="chartdiv" ref="chartdiv"></div>
</template>
  
<script>
import * as am5 from '@amcharts/amcharts5';
import * as am5percent from '@amcharts/amcharts5/percent';
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
        };
    },
    methods: {
        createChart(data) {
            console.log(data);
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
            // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/
            let chart = root.container.children.push(am5percent.PieChart.new(root, {
                layout: root.verticalLayout,
                innerRadius: am5.percent(50)
            }));


            // Create series
            // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Series
            let series = chart.series.push(am5percent.PieSeries.new(root, {
                valueField: "value",
                categoryField: "category",
                alignLabels: false
            }));

            // series.labels.template.setAll({
            //     textType: "circular",
            //     centerX: 0,
            //     centerY: 0
            // });


            // Set data
            // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Setting_data
            series.data.setAll(data);


            // Create legend
            // https://www.amcharts.com/docs/v5/charts/percent-charts/legend-percent-series/
            let legend = chart.children.push(am5.Legend.new(root, {
                centerX: am5.percent(50),
                x: am5.percent(50),
                marginTop: 15,
                marginBottom: 15,
            }));

            legend.data.setAll(series.dataItems);


            // Play initial series animation
            // https://www.amcharts.com/docs/v5/concepts/animations/#Animation_of_series
            series.appear(1000, 100);
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
  
  
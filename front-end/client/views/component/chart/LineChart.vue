<template>
        <div class="chart" id="chartdiv" ref="chartdiv"></div>
</template>
 
<script>
import * as am5 from '@amcharts/amcharts5';
import * as am5xy from '@amcharts/amcharts5/xy';
import am5themes_Animated from '@amcharts/amcharts5/themes/Animated';


const App = {
    props: {
        chartData: {
            type: Array,
            default: [],
            // required: true,
        },
        keyMapping: {
            type: Object,
        },
        columnX:{
            type: String
        },
        columnY:{
            type: String
        }

    },
    data() {
        return {
            // y축 컬럼 목록
            valueDataList: [],

            // 차트 옵션 변수 목록
            chartOptions: {
                layout: "verticalLayout", // 하위요소(범례 등) 위치 (수평 배치: "horizontalLayout", 수직 배치: "verticalLayout")


                /*************** 글자 (시작) ***************/
                themeFontFamily: "none", // 글꼴 (기본: "none")
                themeFontSize: "1.2rem", // 글자 크기 (기본: "1rem")
                themeFontWeight: "bold", // 글자 두께 (기본: "normal")
                themeFill: "#7b7b7b", // 글자 색상 (기본: "#000000")
                /*************** 글자 (끝) ***************/


                /*************** 그리드 (시작) ***************/
                themeGridVisible: true, // 전체 그리드 표시 여부 (활성화: true, 비활성화: false)
                themeStrokeWidth: 1.5, // 전체 그리드 두께 (기본: 1, 0 ≤ x)
                themeStroke: "#000000", // 전체 그리드 색상 (기본: "#000000")

                minGridDistance: 100, // 밸류축 그리드 밀도 (그리드 간의 최소 픽셀 거리) (기본: undefined, -1 ≤ x ≤ 최댓값)
                /*************** 그리드 (끝) ***************/


                numberUnit: "", // 표기할 숫자 단위 (사용안함: "")

                min: 0, // 축 최소값, 기본값은 데이터의 최소값 (기본: undefined)
                max: undefined, // 축 최대값, 기본값은 데이터의 최대값 + 10% (기본: undefined)
                strictMinMax: false, // 설정한 min max값 사용 여부, 비활성화 시 설정한 min max값을 차트 모양에 이상적인 값으로 자동 조절 (활성화: true, 비활성화: false)
                extraMin: undefined, // 데이터 최소값에서 (x * 100)% 만큼 축 최소값 범위를 추가, 사용시 축 최소값 비활성화 (기본: undefined, 0.1 ≤ x ≤ 1)
                extraMax: undefined, // 데이터 최대값에서 (x * 100)% 만큼 축 최대값 범위를 추가, 사용시 축 최대값 비활성화  (기본: undefined, 0.1 ≤ x ≤ 1)


                /*************** 마우스 (시작) ***************/
                lineXVisible: true, // 마우스 x축 라인 표시 여부 (활성화: true, 비활성화: false)
                lineYVisible: false, // 마우스 y축 라인 표시 여부 (활성화: true, 비활성화: false)

                panX: true, // 마우스 x축 드래그 이동 여부 (활성화: true, 비활성화: false)
                panY: false, // 마우스 y축 드래그 이동 여부 (활성화: true, 비활성화: false)
                behavior: "none", // 마우스 드래그 확대 범위, 사용 시 드래그 이동 비활성화 (사용안함: "none", x축: "zoomX", y축: "zoomY", xy축: "zoomXY")

                wheelX: "none", // 마우스 휠 수평 회전 시 (사용안함: "none", 가로줌: "zoomX", 세로줌: "zoomY", 가로세로줌: "zoomXY", 가로이동: "panX", 세로이동: "panY", 가로세로이동: "panXY")
                wheelY: "zoomX", // 마우스 휠 수직 회전 시 (사용안함: "none", 가로줌: "zoomX", 세로줌: "zoomY", 가로세로줌: "zoomXY", 가로이동: "panX", 세로이동: "panY", 가로세로이동: "panXY")
                /*************** 마우스 (끝) ***************/


                scrollbarX: false, // x축 스크롤바 표시 여부
                scrollbarY: false, // y축 스크롤바 표시 여부

                categoryAxisTooltip: true, // 카테고리축 툴팁 숨김 여부 (숨김: true, 표시: false)
                valueAxisTooltip: true, // 밸류축 툴팁 숨김 여부 (숨김: true, 표시: false)
                seriesTooltip: false, // 셀 툴팁 숨김 여부 (숨김: true, 표시: false)


                /*************** 셀 (시작) ***************/
                seriesColorList: ["ffffff","#4e79a6", "#f28e2c", "#e15659", "#76b7b1"], // 차트 셀 색상 목록 (기본: [])
                bullet: "Circle", // 글머리기호 (사용안함: "", 원형: "Circle", 텍스트: "Label")

                // 막대 차트 전용
                cornerRadius: 5, // 셀 모서리 둥금 정도 (0 ≤ x)

                // 라인 차트 전용
                lineSeries: "Line", // 라인 차트 종류 (직선: "Line", 곡선: "SmoothedXLine", 계단: "StepLine")
                lineStrokeDasharray: [10, 0], // 점선 설정 (점선 길이, 점선 간격, 두번째 점선 길이, 두번째 점선 간격...) (사용안함: [])
                lineFillHidden: true, // 채우기 숨김 여부 (숨김: false, 표시: true)
                /*************** 셀 (끝) ***************/

                // 파이 차트 전용
                donutInnerRadiusPercent: 40, // 도넛 내부 원 크기 (0 ≤ x ≤ 100)
                startAngle: 360, // 차트 시작 각도 (0 ≤ x ≤ 360)
                endAngle: 0, // 차트 끝 각도 (0 ≤ x ≤ 360)
                labelPosition: "CurcleOut", // 라벨 위치 (원 밖:"CurcleOut" 원 근처:"CurcleNear" 원 안:"CurcleIn")
                radius: 30, // 차트와 라벨 간의 거리 (기본: 30, ±x)
                pieText: true, // 라벨 밸류 표시 형식 (%: true, 실제값: false)
                pieTooltipText: false, // 툴팁 밸류 표시 형식 (%: true, 실제값: false)
            }

        };
    },
    methods: {

        // 라인 차트 생성 함수
        createLineChart: function (theme, data, columnX, columnY, valueDataList, chartOptions, keyMapping) {
            let mappedData = [];
            let keys = Object.keys(data[0]);

            for (let dataIndex = 0; dataIndex < data.length; dataIndex++) {
                let currentItem = data[dataIndex];
                let mappedItem = {};

                for (let i = 0; i < keys.length; i++) {
                    let key = keys[i];
                    let koreanKey = keyMapping[key];

                    if (koreanKey) {
                        mappedItem[koreanKey] = currentItem[key];
                    }
                }

                mappedData.push(mappedItem);
            }

            let chartData = JSON.parse(JSON.stringify(mappedData)); // 차트 params 데이터 복사
            let options = JSON.parse(JSON.stringify(chartOptions)); // 차트 params 데이터 복사

            /*************** 차트 생성 (시작) ***************/
            let chartWarp = this.$refs["chartdiv"]; // 차트 상위 div ref 매칭
            chartWarp.innerHTML = ""; // 차트 상위 div 내용 초기화 (기존 차트 삭제)

            let div = document.createElement("div"); // 차트를 담을 빈 div 생성 (차트 하위 div)
            div.style.width = "100%"; // 차트를 담을 div의 넓이
            div.style.height = "100%"; // 차트를 담을 div의 높이
            chartWarp.appendChild(div); // 차트 상위 div 안에 차트 하위 div를 추가

            let root = am5.Root.new(div); // 차트 하위 div에 차트(root) 담기
            this.charts = root; // 차트 정보 전역에 담기

            // let root = am5.Root.new("chartdiv"); // root 생성 및 id 매칭
            // let root = am5.Root.new(this.$refs.chartdiv); // root 생성 및 ref 매칭
            /*************** 차트 생성 (끝) ***************/
            /******************************************************************************************/



            /******************************************************************************************/
            /*************** 차트 전체 옵션 (시작) ***************/
            // root.setThemes([am5themes_Animated.new(root)]); // 툴팁 등 애니메이션 효과

            let myTheme = am5.Theme.new(root);

            myTheme.rule("Label").setAll({ // 전체 글자 설정
                // myTheme.rule("AxisLabel").setAll({ // 축 글자 설정
                fontFamily: options['themeFontFamily'], // 글꼴
                fontSize: options['themeFontSize'], // 글자 크기
                fontWeight: options['themeFontWeight'], // 글자 두께
                fill: am5.color(options['themeFill']), // 글자 색상 (툴팁 제외)
            });

            // 전체 그리드 설정
            myTheme.rule("Grid").setAll({
                visible: options['themeGridVisible'], // 그리드 표시 여부
                strokeWidth: options['themeStrokeWidth'], // 그리드 두께
                stroke: am5.color(options['themeStroke']), // 그리드 색상
            });

            root.setThemes([am5themes_Animated.new(root), myTheme]);
            /*************** 차트 전체 옵션 (끝) ***************/
            /******************************************************************************************/



            // 숫자 형식 설정
            root.numberFormatter.setAll({
                numberFormat: `#,###.##'${options['numberUnit']}`, // 숫자 포맷 형식
                // numericFields: ["valueY"] // 포맷할 필드
            });



            /******************************************************************************************/
            /*************** 차트 테마 (시작) ***************/
            let chart = root.container.children.push(
                am5xy.XYChart.new(root, {
                    panX: options['panX'], // x축 드래그 이동 여부
                    panY: options['panY'], // y축 드래그 이동 여부
                    wheelX: options['wheelX'], // 마우스 휠 수평 회전 시 (사용 안 함)
                    wheelY: options['wheelY'], // 마우스 휠 수직 회전 시
                    layout: root[options['layout']] // 하위요소(범례 등) 위치
                })
            );
            /*************** 차트 테마 (끝) ***************/
            /******************************************************************************************/



            /******************************************************************************************/
            /*************** 축 (시작) ***************/
            // 가로 세로 막대에 따라 축 변경
            let categoryAxisRenderer = null;
            let valueAxisRenderer = null;
            let categoryAxes = null;
            let valueAxes = null;
            let categoryInversed = null; // 카테고리 셀 순서 (순차: false, 역순: true)

            categoryAxisRenderer = "AxisRendererX";
            valueAxisRenderer = "AxisRendererY";
            categoryAxes = "xAxes";
            valueAxes = "yAxes";
            categoryInversed = false;

            // 축 최대최소값 사용 시 최대최소값 범위 비활성화
            if (options['min'] !== undefined) {
                options['extraMin'] == undefined
            };
            if (options['max'] !== undefined) {
                options['extraMax'] == undefined
            }

            // 카테고리축 렌더링 옵션 (= 가로 막대 y축)
            let categoryRenderer = am5xy[categoryAxisRenderer].new(root, {
                inversed: categoryInversed,
            });

            // 카테고리축 추가
            let categoryAxis = chart[categoryAxes].push(
                am5xy.CategoryAxis.new(root, {
                    categoryField: columnX,
                    renderer: categoryRenderer,
                })
            );
            // 카테고리축 툴팁
            categoryAxis.set("tooltip", am5.Tooltip.new(root, {
                forceHidden: options['categoryAxisTooltip'], // 툴팁 숨김 여부
            }));

            // 밸류축 렌더링 옵션 (= 가로 막대 y축)
            let valueRenderer = am5xy[valueAxisRenderer].new(root, {
                minGridDistance: options['minGridDistance'], // 그리드 간격
            });

            // 밸류축 추가
            let valueAxis = chart[valueAxes].push(
                am5xy.ValueAxis.new(root, {
                    min: options['min'], // 최소값
                    max: options['max'], // 최대값
                    strictMinMax: options['strictMinMax'], // 정확한 최대 최소값 사용 여부
                    extraMin: options['extraMin'], // 최소값 범위
                    extraMax: options['extraMax'], // 최대값 범위
                    renderer: valueRenderer,
                })
            );
            // 밸류축 툴팁
            valueAxis.set("tooltip", am5.Tooltip.new(root, {
                forceHidden: options['valueAxisTooltip'], // 툴팁 숨김 여부
            }));
            /*************** 축 (끝) ***************/
            /******************************************************************************************/



            /******************************************************************************************/
            /*************** 그래프 생성 (시작) ***************/
            // 가로 세로 막대에 따라 축 변경
            let xAxis = null;
            let yAxis = null;
            let categoryField = null;
            let valueField = null;
            let categoryXY = null;
            let valueXY = null;

            xAxis = categoryAxis;
            yAxis = valueAxis;
            categoryField = "categoryXField";
            valueField = "valueYField";
            categoryXY = "categoryX";
            valueXY = "valueY";

            let series = {};

            function createSeries(name, valueData, seriesColor) {
                series = chart.series.push(
                    am5xy[`${options['lineSeries']}Series`].new(root, {
                        name: name,
                        xAxis: xAxis,
                        yAxis: yAxis,
                        [categoryField]: columnX,
                        [valueField]: valueData,
                        stroke: am5.color(seriesColor), // 선 색상
                        fill: am5.color(seriesColor), // 채우기 색상
                        // minDistance: 10, // 선 단순화 정도 (픽셀 단위만큼의 포인트 생략)
                        // minBulletDistance: 10 // 글머리기호 단순화 정도 (픽셀 단위만큼의 포인트 생략)
                        // maskBullets: true, // 글머리기호 차트 영역 밖에서 잘림 설정 (활성화: true, 비활성화: false)
                        // openValueYField: "value2", // 채우기 영역을 다른 valueField에 맞춤
                        // 셀 툴팁
                        tooltip: am5.Tooltip.new(root, {
                            forceHidden: options['seriesTooltip'], // 툴팁 숨김 여부
                            labelText: `{${categoryXY}}: {${valueXY}}`, // 툴팁 텍스트
                            // pointerOrientation: "vertical", // 툴팁 포인트가 가리키는 방향 ("left", "right", "up", "down", "vertical", "horizontal")
                            // tooltipY: 0, // 셀 위에 위치 (기본: 0, 상승: x < 0, 하락: 0 < x)
                        }),
                        // sequencedInterpolation: true, // 방방 거리는 애니메이션
                    })
                );

                // 다중 차트 툴팁 변경
                if (theme == "ClusterLine") {
                    series.get("tooltip").set("labelText", `${name}: {${valueXY}}`);
                }

                // 선 옵션
                series.strokes.template.setAll({
                    strokeWidth: 2, // 선 굵기
                    strokeDasharray: options['lineStrokeDasharray'], // 점선 설정
                });

                // 채우기 옵션
                series.fills.template.setAll({
                    forceHidden: options['lineFillHidden'], // 채우기 효과 숨김 여부
                    visible: true, // 채우기 표시 여부 (시각적으로만 사라지고 옵션은 적용된 상태, 범례 아이콘에 표시됨) (기본: false)
                    fillOpacity: 0.5, // 채우기 투명도
                });

                // 글머리기호
                series.bullets.push(function (root) {
                    let lineBulletSprite = {}; // 글머기기호 별 옵션
                    if (options['bullet'] == "Circle") { // 원형 글머리기호
                        lineBulletSprite = {
                            strokeWidth: 2, // 테두리 두께
                            stroke: root.interfaceColors.get("background"), // 테두리 색상
                            radius: 5, // 원형 크기
                            fill: am5.color(seriesColor)// 원형 색상
                        }
                    } else if (options['bullet'] == "Label") { // 텍스트 글머리기호
                        lineBulletSprite = {
                            text: `{${columnY}}`, // 텍스트 내용
                            centerX: am5.percent(50), // 수평 위치, 모든 차트에 적용 (p0, p50, p100)
                            centerY: am5.percent(50), // 수직 위치, 모든 차트에 적용 (p0, p50, p100)
                            populateText: true // 밸류 필드를 텍스트 내용으로 사용할 때 밸류 값을 사용 (활성화: true, 비활성화: false)
                        }
                    }

                    if (!options['bullet']) { // 글머리기호 미사용 시
                        return null
                    } else {
                        return am5.Bullet.new(root, { // 글머리기호 사용 시
                            locationX: 0.5, // 수평 위치, 차트마다 적용 유무가 다름 (0 ≤ x ≤ 1)
                            locationY: 0.5, // 수직 위치, 차트마다 적용 유무가 다름 (0 ≤ x ≤ 1)
                            sprite: am5[options['bullet']].new(root, lineBulletSprite)
                        });
                    }
                });

                series.data.setAll(chartData); // 차트 내용 데이터 할당

                series.appear(1000); // 차트 내용 등장 시간
            }

            // 셀 생성 함수 실행
            if (theme == "Line") {
                createSeries(columnY, columnY, options['seriesColorList'][0]);
            } else if (theme == "ClusterLine") {
                // 데이터 밸류 목록만큼 셀 생성
                for (let i = 1; i < valueDataList.length; i++) {
                    createSeries(valueDataList[i], valueDataList[i], options['seriesColorList'][i]);
                }
            }
            /*************** 그래프 생성 (끝) ***************/
            /******************************************************************************************/



            /******************************************************************************************/
            /*************** 그래프 옵션 (시작) ***************/
            /* // 셀 모양
            let columnsOption = {};
            if(theme == "XY") {
                columnsOption = {
                    cornerRadiusTL: options['cornerRadius'], // 셀 왼쪽 모서리 원형 정도
                    cornerRadiusTR: options['cornerRadius'], // 셀 오른쪽 모서리 원형 정도
                }
            } else if(theme == "YX") {
                columnsOption = {
                    cornerRadiusTR: options['cornerRadius'], // 셀 오른쪽 모서리 원형 정도
                    cornerRadiusBR: options['cornerRadius'], // 셀 왼쪽 모서리 원형 정도
                }
            };
            series.columns.template.setAll(columnsOption);

            // 자동 색상 기본 설정
            series.columns.template.adapters.add("fill", function (fill, target) {
                return chart.get("colors").getIndex(series.columns.indexOf(target));
            });
            series.columns.template.adapters.add("stroke", function (stroke, target) {
                return chart.get("colors").getIndex(series.columns.indexOf(target));
            });

            // 셀 색상 (전체 셀을 비슷한 색상으로 구성)
            if(options['seriesColor'] != "none") {
                chart.get("colors").set("colors", [
                    am5.color(options['seriesColor'])
                ]);
            } */

            // 사용자 색상 목록 (셀 하나씩 색상 지정 가능, series에서 베이스 색상 설정 필요)
            /* chart.get("colors").set("colors", [
                am5.color(0x095256),
                am5.color(0x087f8c),
                am5.color(0x5aaa95),
                am5.color(0x86a873),
                am5.color(0xbb9f06)
            ]); */
            /*************** 그래프 옵션 (끝) ***************/
            /******************************************************************************************/



            categoryAxis.data.setAll(chartData); // x축 범주 명 데이터 할당

            /******************************************************************************************/
            /*************** 마우스 이벤트 (시작) ***************/
            // 마우스 커서 오버 시 효과
            let cursor = chart.set("cursor", am5xy.XYCursor.new(root, {
                behavior: options['behavior'], // 드래그 확대 활성화 범위 (사용 시 드래그 이동 비활성화)
            }));
            // x축과 연결된 라인 옵션
            cursor.lineX.setAll({
                visible: options['lineXVisible'], // 라인 표시 여부
                // stroke: am5.color(0x550000), // 라인 색상
                // strokeWidth: 2, // 라인 두께
                // strokeDasharray: [20, 5, 5, 5] // 점선 설정 (점선 길이, 점선 간격, 두번째 점선 길이, 두번째 점선 간격...)
            });
            // y축과 연결된 라인 옵션
            cursor.lineY.setAll({
                visible: options['lineYVisible'], // 라인 표시 여부
            });
            /*************** 마우스 이벤트 (끝) ***************/
            /******************************************************************************************/



            /******************************************************************************************/
            /*************** 스크롤바 (시작) ***************/
            // x축 스크롤바
            let scrollbarX = chart.set("scrollbarX", am5.Scrollbar.new(root, {
                visible: options['scrollbarX'], // 스크롤바 표시 여부
                orientation: "horizontal", // 스크롤 배치 방향
                minHeight: 8 // 스크롤 너비
            }));
            scrollbarX.startGrip.set("scale", 0.7); // 스크롤 시작 버튼 크기
            scrollbarX.endGrip.set("scale", 0.7); // 스크롤 끝 버튼 크기

            // y축 스크롤바
            let scrollbarY = chart.set("scrollbarY", am5.Scrollbar.new(root, {
                visible: options['scrollbarY'], // 스크롤바 표시 여부
                orientation: "vertical", // 스크롤 배치 방향
                minHeight: 8 // 스크롤 너비
            }));
            scrollbarY.startGrip.set("scale", 0.7); // 스크롤 시작 버튼 크기
            scrollbarY.endGrip.set("scale", 0.7); // 스크롤 끝 버튼 크기
            /*************** 스크롤바 (끝) ***************/
            /******************************************************************************************/


            root._logo.dispose(); //amChart 로고 삭제

            // 히트맵일 시 일반범례 사용 안 하고 return
            if (theme == "HeatMap") {
                return { "root": root, "chart": chart }
            };



            /******************************************************************************************/
            /*************** 범례 (시작) ***************/
            let nameField = null;

            if (theme == "XY") {
                nameField = "categoryX"
            } else if (theme == "YX") {
                nameField = "categoryY"
            }

            let legend = chart.children.push(am5.Legend.new(root, {
                x: am5.percent(50), // 범례 중심 위치 (50: 중앙)
                centerX: am5.percent(50), // 범례 중심 위치 (50: 중앙)
                nameField: nameField, // 카테고리 범례 사용시 필요
                /* layout: am5.GridLayout.new(root, {
                    maxColumns: 3, // 한 줄의 최대 개수
                    fixedWidthGrid: true // 너비가 동일 (활성화: true, 비활성화: false)
                }) */
                /* marginTop: 15,
                marginBottom: 15, */
            }));

            legend.valueLabels.template.set("forceHidden", true); // 범례 밸류값 숨김

            // 범례 아이콘 둥글게
            // legend.markerRectangles.template.setAll({
            //     cornerRadiusTL: 10,
            //     cornerRadiusTR: 10,
            //     cornerRadiusBL: 10,
            //     cornerRadiusBR: 10
            // });

            // 툴팁은 series(전체)에 있고 dataItem(개인)에는 툴팁이 없기 때문에 툴팁 hover가 작동 안 하는 것으로 추정됨
            /* // 범례에 마우스 오버 시 해당 셀 툴팁 표시 (카테고리 범례만)
            legend.itemContainers.template.events.on("pointerover", function (e) {
                let seriesDataItem = e.target.dataItem.dataContext;
                if (seriesDataItem) {
                    let graphics = seriesDataItem.get("graphics");
                    if (graphics) {
                        graphics.hover();
                    }
                }
            });

            // 범례에 마우스 아웃 시 해당 셀 툴팁 미표시 (카테고리 범례만)
            legend.itemContainers.template.events.on("pointerout", function (e) {
                let seriesDataItem = e.target.dataItem.dataContext;
                if (seriesDataItem) {
                    let graphics = seriesDataItem.get("graphics");
                    if (graphics) {
                        graphics.unhover();
                    }
                }
            }); */

            if (theme == "XY" || theme == "YX" || theme == "Pie" || theme == "Donut") {
                legend.data.setAll(series.dataItems); // 범례 데이터 할당(카테고리)
            } else {
                legend.data.setAll(chart.series.values); // 범례 데이터 할당(value)
            }
            /*************** 범례 (끝) ***************/
            /******************************************************************************************/

            chart.appear(1000, 100); // 차트 배경 등장 시간
        },
    },
    watch: {
        'chartData': function (newData, oldData) {
            // this.createChart(newData);
            this.valueDataList = Object.values(this.keyMapping);
            this.createLineChart("ClusterLine", newData, this.columnX, this.columnY, this.valueDataList, this.chartOptions, this.keyMapping);
        },
    },
    computed: {

    },
    components: {

    },
    mounted() {
        // this.chartCreate(this.data)
    }
}

export default App;
</script>
 
<style scoped>
.chart{
    width: 100%;
    height: 100%;
}
</style>
 
 
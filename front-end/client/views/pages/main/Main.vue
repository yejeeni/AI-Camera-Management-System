<template>
    <div class="wrap_view">
        <div class="wrap_title">
            <h1>AI 카메라 관리 시스템</h1>
        </div>
        <div class="content-wrap container">
            <div class="total-wrap">
                <div>
                    <h3 class="main-title">오늘 누적 인식 수</h3>
                </div>
                <div class="flex total-button justify-between">
                    <button v-for="(tab, index) in tabs" :key="index" :class="{ 'active': activeTab === index }"
                        @click="activeTab = index">
                        <svg-icon type="mdi" :path="tab.iconPath" class="svg-icon"></svg-icon>
                        <div class="total-tab-text">
                            <a href="javascript:void(0)">{{ tab.name }}</a>
                            <div class="flex align-end">
                                <p>{{ tab.count }}</p>
                                <p>건</p>
                            </div>
                        </div>
                    </button>
                </div>
                <div class="total-chart">
                    <!-- <LineChart :chartData="equipmentData" :keyMapping="keyMap" columnX="날짜" columnY="장비1" /> -->
                    <SingleBarChart :chartData="equipmentDataForChart" columnX="날짜" columnY="장비1" />
                </div>
            </div>
            <div class="wrap-button flex justify-between">
                <div class="flex justify-between align-center content-button">
                    <p class="main-title flex20">실시간 관제</p>
                    <a href="/control.page" class="flex20"><img src="../../../resources/img/main-live.png" alt="">실사간 현황
                        관제</a>
                    <a href="/detections.page" class="flex20"><img src="../../../resources/img/main-live3.png" alt="">상황 발생
                        정보조회</a>
                    <a href="/troblList.page" class="flex20"><img src="../../../resources/img/main-error.png" alt="">장애 발생
                        정보조회</a>
                    <a href="/equipment.page" class="flex20"><img src="../../../resources/img/main-run.png" alt="">장비관리</a>
                </div>
                <div class="flex justify-between align-center content-button">
                    <p class="main-title flex30 ">통계분석</p>
                    <a href="/statistics.page#tab01" class="flex35"><img src="../../../resources/img/main-live3.png"
                            alt="">상황 발생</a>
                    <a href="/statistics.page#tab02" class="flex35"><img src="../../../resources/img/main-error.png"
                            alt="">장애 발생</a>
                </div>
            </div>
            <div class="wrap-list flex justify-between">
                <div class="wrap-list-tab ">
                    <p class="main-title">장애 발생 내역</p>
                    <div class="error-list ">
                        <div class="table-zone">
                            <div class="list-info flex justify-between align-center">
                                <table class="list-table">
                                    <!-- col 꼭 너비 기재해야함! 그래야 100%로 차지함 -->
                                    <colgroup>
                                        <col style="width:20% ;">
                                        <col style="width:35% ;">
                                        <col style="width:25%;">
                                        <col style="width:20%;">
                                    </colgroup>
                                    <tbody>
                                        <tr v-for="(eqpmnTroubleOne, idx) in recentEqpmnTroubleList" :key="idx"
                                            @click="goTroblListPage()">
                                            <td>{{ eqpmnTroubleOne.eqpmn_nm }}</td>
                                            <td> {{ eqpmnTroubleOne.trobl_nm }}</td>
                                            <td>{{ eqpmnTroubleOne.ocrn_dt }}</td>
                                            <td>{{ eqpmnTroubleOne.recovery_yn === 'Y' ? '복구 완료' : '복구 미완료' }}</td>
                                        </tr>
                                        <tr v-if="recentEqpmnTroubleList.length == 0">
                                            <td colspan="5" class="no-list">데이터가 없습니다.</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="wrap-list-tab">
                    <p class="main-title">상황 발생 내역</p>
                    <div class="event-list">
                        <div class="table-zone">
                            <div class="list-info flex justify-between align-center">
                                <table class="list-table">
                                    <!-- col 꼭 너비 기재해야함! 그래야 100%로 차지함 -->
                                    <colgroup>
                                        <col style="width:33% ;">
                                        <col style="width:33% ;">
                                        <col style="width: 33%;">
                                    </colgroup>
                                    <tbody>
                                        <tr v-for="(detectionEventOne, idx) in recentDetectEventList" :key="idx"
                                            @click="goDetectionPage()">
                                            <td>{{ detectionEventOne.eqpmn_nm }}</td>
                                            <td>객체 인식 발생</td>
                                            <td>{{ detectionEventOne.ocrn_dt }}</td>
                                        </tr>
                                        <tr v-if="recentDetectEventList.length == 0">
                                            <td colspan="5" class="no-list">데이터가 없습니다.</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import SvgIcon from '@jamescoyle/vue-icon';
import SingleBarChart from '../../component/chart/SingleBarChart.vue';
import { mdiCarHatchback, mdiMotorbike, mdiBicycle, mdiBusSide, mdiTruck, mdiRun } from '@mdi/js';
import { useStore } from "vuex";
export default {
    data() {
        return {
            store: useStore(),
            pathmdiRun: mdiRun,
            pathcar: mdiCarHatchback,
            pathcarbike: mdiMotorbike,
            pathcarcycle: mdiBicycle,
            pathmdiBusSide: mdiBusSide,
            pathTruck: mdiTruck,
            activeTab: true,
            tabs: [
                // { name: "전체정보", icon: mdiFolderAlert , open: this.openModal},
                { name: "보행자", iconPath: mdiRun },
                { name: "자전거", iconPath: mdiBicycle },
                { name: "승용차", iconPath: mdiCarHatchback },
                { name: "오토바이", iconPath: mdiMotorbike },
                { name: "버스", iconPath: mdiBusSide },
                { name: "트럭", iconPath: mdiTruck },
            ],
            // 최신 객체인식 기록
            recentDetectEventList: [],

            // 최신 장비장애 기록
            recentEqpmnTroubleList: [],

            equipmentDataForChart: [],
        }
    },
    methods: {
        /**
         * 최신 5개 객체 인식 기록 조회
         */
        recentObjectDetectEventList: function () {
            const vm = this;
            axios({
                url: "/recentObjectDetectEventList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: {},
            })
                .then(function (response) {
                    console.log("최신순: ", response.data);
                    vm.recentDetectEventList = response.data;
                })
                .catch(function (error) {
                    console.log("recentObjectDetectEventList - error : ", error);
                });
        },

        /**
         *  최신 5개 장애 정보 
         */
        recentEqpmnTroubleSelectList: function () {
            const vm = this;

            axios({
                url: "/recentEqpmnTroubleSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: {},
            })
                .then(function (response) {
                    vm.recentEqpmnTroubleList = response.data;
                    console.log("장비장애 - ", response.data);
                })
                .catch(function (error) {
                    console.log("recentEqpmnTroubleSelectList - error : ", error);
                });
        },

        /**
        * 오늘 모든 장비에 대한 객체 인식 이벤트의 총합을 가져오는 메서드
        */
        todayObjectDetectEventSumByAllEquipment: function () {
            const vm = this;
            axios({
                url: "/todayObjectDetectEventSumByAllEquipment.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: {},
            })
                .then(function (response) {
                    vm.tabs = vm.tabs.map(tab => ({
                        ...tab,
                        // response에서 탭 이름에 해당하는 카운트 값을 가져와서 업데이트
                        count: response.data[tab.name] || 0
                    }));
                    // 차트 데이터 업데이트
                    vm.equipmentDataForChart = Object.keys(response.data).map(category => {
                        return {
                            // response의 카테고리와 값으로 구성된 객체 반환
                            category: category,
                            value: response.data[category]
                        };
                    });
                    console.log("tabs - ", vm.tabs);
                })
                .catch(function (error) {
                    console.log("todayObjectDetectEventSumByAllEquipment - error : ", error);
                });
        },

        //장애 발생 내역 클릭 시 장비장애 조회 페이지로 이동
        goTroblListPage: function () {
            this.$router.push({ path: '/troblList.page' })
        },

        //이벤트 발생 내역 클릭 시 객체인식 조회 페이지로 이동
        goDetectionPage: function () {
            this.$router.push({ path: '/detections.page' })
        }

    },
    watch: {

    },
    computed: {

    },
    components: {
        SvgIcon,
        'SingleBarChart': SingleBarChart
    },
    mounted() {
        this.todayObjectDetectEventSumByAllEquipment();
        this.recentObjectDetectEventList();
        this.recentEqpmnTroubleSelectList();

        console.log('main mounted');
        
        
    }
}
</script>

<style>
.wrap_view {
    width: 100%;
    height: 100vh;
    padding: 2rem 5rem;
    background-color: #f7f6fb;

}

.wrap_title {
    font-family: "GmarketSansB";
    padding: 0 0 2rem;
}

.content-wrap {
    gap: 2rem;
    height: 100%;
}

.total-wrap {
    border-radius: 1rem;
    box-shadow: 5px 5px 10px #33333336;
    padding: 2rem;
    background-color: white;
    height: auto;

}

.content-wrap h3 {
    font-size: 1.5rem;
    padding: 0 0 2rem;
}

.total-button {
    width: 100%;
    flex-direction: row;
}

.total-tab-text {
    text-align: left;
    font-size: 1.7rem;
}

.total-tab-text a {
    font-family: 'Pretendard';
    color: #213f99;
}

.total-tab-text div p:first-child {
    font-size: 5rem;
    font-weight: bold;
    color: var(--color-orange);
}

.total-tab-text div p:last-child {
    font-size: 2rem;
    padding: 0 0 1rem;
    color: var(--color-black);
}

.total-button button {
    width: 16%;
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    border: 1px solid var(--color-gray);
    padding: 1rem;
    border-radius: 1rem;
    position: relative;
}

.total-button button {
    font-weight: 600;

}

.total-button button .svg-icon {
    color: var(--color-blue);
    font-size: 3rem;
    width: 60px;
    height: 60px;
    padding: 1rem;
    border-radius: 100rem;
    margin-right: 1.5rem;
    background-color: var(--color-blueE);
}

.total-button button.active {
    background-color: var(--color-blue);
    color: white;

}

.total-button button.active .svg-icon {
    background-color: var(--color-white);
}

.total-button button.active a {
    color: white;
}

.total-button button.active .total-tab-text div p:last-child {
    color: white;
}



.total-chart {
    height: 280px;
}



.wrap-button {
    gap: 20px;
}

.content-button {
    flex-grow: 1;
    gap: 1;
    background-color: var(--color-white);
    box-shadow: 5px 5px 10px #33333336;
    padding: 1rem 2rem;
    border-radius: 1rem;
}

.content-button a {
    padding: 15px 10px;
    font-size: 1.5rem;
    border: 1px solid var(--color-gray);
    border-radius: 1rem;
    font-family: 'Pretendard';
    font-weight: 600;
}

.content-button a img {
    padding: 0 1rem;
    width: 50px;
    height: 30px;

}



.wrap-list {
    min-height: 20%;
    gap: 1.5rem;
}

.wrap-list-tab {
    width: 49.5%;
    border-radius: 1rem;
    background-color: white;
    padding: 1rem 2rem;
    box-shadow: 5px 5px 10px #33333336;
}

.wrap-list-tab p {
    font-size: 1.7rem;
    font-weight: 600;
    padding: 1rem 0;
}

.error-list li,
.event-list li {
    padding: 0.5rem 0;
    display: flex;
    justify-content: space-between;
    font-size: 1.5rem;
    font-weight: 600;
}

.main-img-content img {
    width: 30px;
    height: 30px;
    margin: 0 1rem;
}

.event-list td:first-child,
.error-list td:first-child {
    text-align: left;
    padding: 0rem 0 0 1rem;
}
</style>
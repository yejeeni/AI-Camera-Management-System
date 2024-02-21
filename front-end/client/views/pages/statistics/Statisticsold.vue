<template>
    <div class="container statics-page">
        <div class="page-titleZone flex justify-between align-center">
            <p class="main-title">통계분석</p>
            <PageNavigation />
        </div>
        <div class="content">
            <div class="row">
                <div class="flex justify-between align-center">
                    <ul class="tabNav flex justify-between flex20">
                        <li @click="showTab('tab1')" class="flex50"><a href="#tab01"
                                :class="{ activeTab: activeTab === 'tab1' }">객체</a></li>
                        <li @click="showTab('tab2')" class="flex50"><a href="#tab02"
                                :class="{ activeTab: activeTab === 'tab2' }">장비</a></li>
                    </ul>
                    <div class="flex justify-end flex80 ">
                        <div class="search-bar">
                            <div class="flex justify-end align-center">
                                <select name="" id="" class="square-select" v-model="statisticsCriteria">
                                    <option value="hourly">시간별</option>
                                    <option value="daily">일별</option>
                                    <option value="monthly">월별</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <ul class="tab-content">
                    <span class="box-title" v-if="equipment.eqpmn_id !== null">
                        {{ equipment.eqpmn_id }} {{ equipment.eqpmn_nm }}의 차트
                    </span>
                    <span class="box-title" v-else>
                        전체 장비 차트
                    </span>
                    <li v-show="activeTab === 'tab1'" >
                        <div class="mapContent">
                            <ColumnBar :chartData="showChart ? objectDataList : []" />
                        </div>


                    </li>
                    <li v-show="activeTab === 'tab2'">
                        <div class="mapContent">
                            <LineChart :chartData="equipmentData" :keyMapping="keyMap" columnX="날짜" columnY="장비1" />
                        </div>
                    </li>
                </ul>
                <div class="flex justify-between">

                    <div class="flex40">
                        
                        
                        <div class="searchbar-zone">
                            <div class="flex justify-between align-center">
                                <div class="count-zone">
                                    <p>총 <span>{{ dataListCount }}</span>건</p>
                                </div>
                            </div>

                            <div class="flex justify-between align-center">
                                <p class=" main-title">김하영짱</p>
                                <div class="flex justify-end">

                                    <select name="" id="" style="width:150px; margin-right:10px" v-model="dataListSearch.option">
                                        <option value="all">전체</option>
                                        <option value="eqpmn_id">장비ID</option>
                                        <option value="eqpmn_nm">장비명</option>
                                        <option value="camera_nm">카메라이름</option>
                                        <option value="mdfr_id">등록자ID</option>
                                    </select>
                                    <div class="search-square">
                                        <input type="text" class="square-input" placeholder="검색어를 입력하세요" name="keyword" id="keyword"
                                            v-model="dataListSearch.keyword" @keyup.enter="goToFirstPageAndLoadEquipmentsList()">
                                        <button class="square-button" @click="goToFirstPageAndLoadEquipmentsList()">
                                            <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                        </button>
                                    </div>
                                    <div class="cunt-selectZone">
                                        <select v-model="dataListSearch.perPage" name="perPage" id="perPage"
                                            @change="goToFirstPageAndLoadEquipmentsList()">
                                            <option value="5">5개 보기</option>
                                            <option value="10">10개 보기</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="table-zone">
                            <table class="list-table">
                                <colgroup>
                                    <col style="width: ;">
                                    <col style="width: ;">
                                    <col style="width: ;">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>장비ID</th>
                                        <th>장비명</th>
                                        <th>카메라이름</th>
                                       
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(item, id) in dataList" :key="item.eqpmn_id" @click="dataSelectOne(item)">
                                        <td>{{ item.eqpmn_id }}</td>
                                        <td>{{ item.eqpmn_nm }}</td>
                                        <td>{{ item.camera_nm }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
        
                        <PaginationButton v-model:currentPage="dataListSearch.currentPage" :perPage="dataListSearch.perPage"
                            :total-count="dataListCount" :max-range="5" :click="equipmentSelectList" />
                    </div>
                    <div class="flex60">
                        <div style="padding: 9.5px 0;"></div>
                        <p class="page-titleZone  main-title">장비상세</p>
                        <div class="table-zone">
                            <table class="list-table" v-if="activeTab == 'tab1'">
                                <colgroup>
                                    <col style="width: 22%">
                                    <col style="width: 13%">
                                    <col style="width: 13%">
                                    <col style="width: 13%">
                                    <col style="width: 13%">
                                    <col style="width: 13%">
                                    <col style="width: 13%">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>기간</th>
                                        <th>오토바이</th>
                                        <th>보행자</th>
                                        <th>버스</th>
                                        <th>자전거</th>
                                        <th>승용차</th>
                                        <th>트럭</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(objectDataOne) in objectDataList">
                                        <td>{{ objectDataOne.year }}</td>
                                        <td>{{ objectDataOne.오토바이 }}</td>
                                        <td>{{ objectDataOne.보행자 }}</td>
                                        <td>{{ objectDataOne.버스 }}</td>
                                        <td>{{ objectDataOne.자전거 }}</td>
                                        <td>{{ objectDataOne.승용차 }}</td>
                                        <td>{{ objectDataOne.트럭 }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <table class="list-table" v-else>
                                <colgroup>
                                    <col style="width: 22%">
                                    <col style="width: 19.5%">
                                    <col style="width: 19.5%">
                                    <col style="width: 19.5%">
                                    <col style="width: 19.5%">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>기간</th>
                                        <th>CPU 장애</th>
                                        <th>메모리 장애</th>
                                        <th>연결 장애</th>
                                        <th>디스크 장애</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(equipmentDataOne) in equipmentData">
                                        <td>{{ equipmentDataOne.date }}</td>
                                        <td>{{ equipmentDataOne.value1 }}</td>
                                        <td>{{ equipmentDataOne.value2 }}</td>
                                        <td>{{ equipmentDataOne.value3 }}</td>
                                        <td>{{ equipmentDataOne.value4 }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ColumnBar from '../../component/chart/ColumnBar.vue';
import LineChart from '../../component/chart/LineChart.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import axios from 'axios';

export default {
    data() {
        return {
            activeTab: 'tab1',
            objectDataList: [],
            equipmentData: [],
            showChart: false,
            keyMap: {
                date: '날짜',
                value1: 'CPU',
                value2: '메모리',
                value3: '연결',
                value4: '디스크',
            },

            detectionWeeklyData: [],

            actualData: [],
            statisticsCriteria: 'hourly',

            // 장비검색
            dataListSearch: {
                currentPage: 1,
                perPage: 5,
                keyword: '',
                option: 'all'
            },
            dataList: [],
            dataListCount: null,
            eqpmn_id: null,


            equipment: {
                eqpmn_id: null,
                eqpmn_nm: null,
                eqpmn_lat: null,
                eqpmn_lon: null,
                camera_ip: null,
                camera_nm: null,
                cntn_id: null,
                cntn_pw: null,
                reg_dt: null,
                rgtr_id: null,
                mdfcn_dt: null,
                mdfr_id: null,
                use_yn: null
            },

            searchPath: mdiMagnify,
        }
    },
    methods: {
        // 시간별 객체 인식 분석 그래프
        hourlyAnalsDetectData: function () {
            const vm = this;
            axios({
                url: "/hourlyAnalsDetectData.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    console.log("hourlyAnalsDetectData - response: ", response.data);
                    vm.objectDataList = response.data;
                    vm.showChart = true;
                })
                .catch(function (error) {
                    console.log("hourlyAnalsDetectData - error : ", error);
                });
        },

        // 일별 객체 인식 분석 그래프
        dailyAnalsDetectData: function () {
            const vm = this;
            axios({
                url: "/dailyAnalsDetectData.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    console.log("dailyAnalsDetectData - response: ", response.data);
                    vm.objectDataList = response.data;
                    vm.showChart = true;
                })
                .catch(function (error) {
                    console.log("dailyAnalsDetectData - error : ", error);
                });
        },

        // 월별 객체 인식 분석 그래프
        monthlyAnalsDetectData: function () {
            const vm = this;
            axios({
                url: "/monthlyAnalsDetectData.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    console.log("monthlyAnalsDetectData - response: ", response.data);
                    vm.objectDataList = response.data;
                    vm.showChart = true;
                })
                .catch(function (error) {
                    console.log("monthlyAnalsDetectData - error : ", error);
                });
        },

        // 시간별 장비 장애 분석 그래프
        hourlyAnalsTroubleData: function () {
            const vm = this;
            axios({
                url: "/hourlyAnalsTroubleData.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    console.log("hourlyAnalsTroubleData - response: ", response.data);
                    vm.equipmentData = response.data;
                })
                .catch(function (error) {
                    console.log("hourlyAnalsTroubleData - error : ", error);
                });
        },

        // 일별 장비 장애 분석 그래프
        dailyAnalsTroubleData: function () {
            const vm = this;
            axios({
                url: "/dailyAnalsTroubleData.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    console.log("dailyAnalsTroubleData - response: ", response.data);
                    vm.equipmentData = response.data;
                })
                .catch(function (error) {
                    console.log("dailyAnalsTroubleData - error : ", error);
                });
        },

        // 월별 장비 장애 분석 그래프
        monthlyAnalsTroubleData: function () {
            const vm = this;
            axios({
                url: "/monthlyAnalsTroubleData.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    console.log("monthlyAnalsTroubleData - response: ", response.data);
                    vm.equipmentData = response.data;
                })
                .catch(function (error) {
                    console.log("monthlyAnalsTroubleData - error : ", error);
                });
        },


        selectStatisticsCriteria() {
            console.log("value - ", this.statisticsCriteria);
            if (this.statisticsCriteria == 'hourly') {
                this.hourlyAnalsDetectData();
                this.hourlyAnalsTroubleData();
            } else if (this.statisticsCriteria == 'daily') {
                this.dailyAnalsDetectData();
                this.dailyAnalsTroubleData();
            } else {
                this.monthlyAnalsDetectData();
                this.monthlyAnalsTroubleData();
            }
        },

        //장비조회
        equipmentSelectList: function () {
            const vm = this;
            axios({
                url: "/equipmentSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.dataListSearch,
            })
                .then(function (response) {
                    vm.dataList = response.data.equipmentSelectList;
                    vm.dataListCount = response.data.equipmentSelectListCount;
                })
                .catch(function (error) {
                    alert("장비 조회에 오류가 발생했습니다.");
                });
        },

        dataSelectOne: function (item) {
            this.equipment = item;
            if (this.statisticsCriteria == 'hourly') {
                this.hourlyAnalsDetectData();
                this.hourlyAnalsTroubleData();
            } else if (this.statisticsCriteria == 'daily') {
                this.dailyAnalsDetectData();
                this.dailyAnalsTroubleData();
            } else {
                this.monthlyAnalsDetectData();
                this.monthlyAnalsTroubleData();
            }

        },

        // 첫 페이지로 초기화하고 장비 목록을 조회합니다.
        goToFirstPageAndLoadEquipmentsList: function () {
            this.dataListSearch.currentPage = 1;
            this.equipmentSelectList();
        },

        showTab: function (tabName) {
            this.activeTab = tabName;
        },

    },
    watch: {
        'statisticsCriteria'() {
            Object.keys(this.equipment).forEach(key => {
                this.equipment[key] = null;
            });
            this.selectStatisticsCriteria();
        }
    },
    computed: {

    },
    components: {
        'ColumnBar': ColumnBar,
        'LineChart': LineChart,
        'PageNavigation': PageNavigation,
        'PaginationButton': PaginationButton,
        'SvgIcon': SvgIcon
    },
    mounted() {
        console.log('main mounted');
        this.hourlyAnalsDetectData();
        this.hourlyAnalsTroubleData();
        this.equipmentSelectList();
    }
}
</script>


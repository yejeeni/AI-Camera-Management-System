<template>
    <div class="container" style="margin: 1rem;">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">통계분석</p>
        </div>
        <div class="content-box flex justify-between">
            <div class="left-content flex20">
                <div class="flex-column ">
                    <div class="host-zone flex20">

                        <div class="searchbar-zone">
                            <div class="flex justify-between align-center">
                                <div class="count-zone">
                                    <p>총 <span>{{ dataListCount }}</span>건의 장비</p>
                                </div>
                            </div>

                            <div class="flex justify-between align-center">
                                <div class=" w100 mt10 flex justify-end">

                                    <select name="" id="" v-model="dataListSearch.option">
                                        <option value="all">전체</option>
                                        <option value="eqpmn_id">장비ID</option>
                                        <option value="eqpmn_nm">장비명</option>
                                        <option value="camera_nm">카메라이름</option>
                                    </select>
                                    <div class="search-square ">
                                        <input type="text" class="option-searchbar" style="width: 100%;" placeholder="검색어를 입력하세요" name="keyword"
                                            id="keyword" v-model="dataListSearch.keyword"
                                            @keyup.enter="goToFirstPageAndLoadEquipmentsList()">
                                        <button class="square-button" @click="goToFirstPageAndLoadEquipmentsList()">
                                            <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                        </button>
                                    </div>
                                    <!-- <div class="cunt-selectZone">
                                        <select v-model="dataListSearch.perPage" name="perPage" id="perPage"
                                            @change="goToFirstPageAndLoadEquipmentsList()">
                                            <option value="5">5개 보기</option>
                                            <option value="10">10개 보기</option>
                                        </select>
                                    </div> -->
                                </div>
                                <div class="table-zone">
                                    <!-- <table class="list-table" v-if="activeTab == 'tab1'">
                                        <colgroup>
                                            <col style="width: ;">
                                            <col style="width: ;">
                                            <col style="width: ;">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>장비 ID</th>
                                                <th>객체명</th>
                                                <th>카메라이름</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="(item, id) in dataList" :key="item.eqpmn_id"
                                                @click="dataSelectOne(item)">
                                                <td>{{ item.eqpmn_id }}</td>
                                                <td>{{ item.eqpmn_nm }}</td>
                                                <td>{{ item.camera_nm }}</td>
                                            </tr>
                                        </tbody>
                                    </table> -->
                                    <table class="list-table">
                                        <colgroup>
                                            <col style="width: 33.3%;">
                                            <col style="width: 33.3%;">
                                            <col style="width: 33.3%;">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>장비ID</th>
                                                <th>장비명</th>
                                                <th>카메라명</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="(item) in dataList" :key="item.eqpmn_id"
                                                @click="dataSelectOne(item)">
                                                <td>{{ item.eqpmn_id }}</td>
                                                <td>{{ item.eqpmn_nm }}</td>
                                                <td>{{ item.camera_nm }}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>


                            </div>
                        </div>

                    </div>
                    <PaginationButton v-model:currentPage="dataListSearch.currentPage" :perPage="dataListSearch.perPage"
                        :total-count="dataListCount" :max-range="5" :click="equipmentSelectList" />
                </div>
            </div>
            <div class="right-content flex80">
                <div class="searchbar-zone">
                    <div class="flex align-center">
                        <div class="content" style="margin-bottom: 0px;">
                            <div class="row">
                                <!-- 버튼 -->
                                <ul class="tabNav flex justify-start flex20 mb40">
                                    <li @click="showTab('tab1')" class="flex15"><a href="#tab01"
                                            :class="{ activeTab: activeTab === 'tab1' }"
                                            :style="{ backgroundColor: buttonColor }">객체 인식</a></li>
                                    <li @click="showTab('tab2')" class="flex15"><a href="#tab02"
                                            :class="{ activeTab: activeTab === 'tab2' }"
                                            :style="{ backgroundColor: buttonColor }">장비 장애</a></li>
                                </ul>
                                <ul class="tab-content">
                                    <span class="box-title" v-if="equipment.eqpmn_id !== null">
                                        {{ equipment.eqpmn_id }} {{ equipment.camera_nm }} 그래프
                                    </span>
                                    <span class="box-title" v-else>
                                        전체 장비 그래프
                                    </span>
                                    <select class="square-select" v-model="timeUnit">
                                        <option value="hourly">시간별</option>
                                        <option value="daily">일별</option>
                                        <option value="monthly">월별</option>
                                    </select>
                                    <li v-show="activeTab === 'tab1'">
                                        <div class="mapContent">
                                            <ColumnBar :chartData="showChart ? objectDataList : []" />
                                        </div>
                                    </li>
                                    <li v-show="activeTab === 'tab2'">
                                        <div class="mapContent">
                                            <LineChart :chartData="troubleDataList" :keyMapping="keyMap" columnX="날짜"
                                                columnY="장비1" />
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="flex-column">
                            <div class="table-zone">
                                <div class="list-info flex align-center">
                                    <div style="padding: 9.5px 0;"></div>
                                    <p class="page-titleZone  main-title">상세 통계</p>
                                    <div class="table-zone">
                                        <table class="list-table" v-if="activeTab == 'tab1'">
                                            <colgroup>
                                                <col style="width: 16%">
                                                <col style="width: 12%">
                                                <col style="width: 12%">
                                                <col style="width: 12%">
                                                <col style="width: 12%">
                                                <col style="width: 12%">
                                                <col style="width: 12%">
                                                <col style="width: 12%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>기간</th>
                                                    <th>보행자</th>
                                                    <th>자전거</th>
                                                    <th>승용차</th>
                                                    <th>오토바이</th>
                                                    <th>버스</th>
                                                    <th>트럭</th>
                                                    <th>총합</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr v-for="(objectDataOne) in objectDataList">
                                                    <td>{{ objectDataOne.year }}</td>
                                                    <td>{{ objectDataOne.보행자 }}</td>
                                                    <td>{{ objectDataOne.자전거 }}</td>
                                                    <td>{{ objectDataOne.승용차 }}</td>
                                                    <td>{{ objectDataOne.오토바이 }}</td>
                                                    <td>{{ objectDataOne.버스 }}</td>
                                                    <td>{{ objectDataOne.트럭 }}</td>
                                                    <td>
                                                        {{ parseInt(objectDataOne.오토바이) + parseInt(objectDataOne.보행자) +
                                                            parseInt(objectDataOne.버스) + parseInt(objectDataOne.자전거) +
                                                            parseInt(objectDataOne.승용차) + parseInt(objectDataOne.트럭) }}
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                        <table class="list-table" v-else>
                                            <colgroup>
                                                <col style="width: 20%">
                                                <col style="width: 20%">
                                                <col style="width: 20%">
                                                <col style="width: 20%">
                                                <col style="width: 20%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>기간</th>
                                                    <th>CPU 장애</th>
                                                    <th>메모리 장애</th>
                                                    <th>연결 장애</th>
                                                    <th>총합</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr v-for="(troubleDataOne) in troubleDataList">
                                                    <td>{{ troubleDataOne.date }}</td>
                                                    <td>{{ troubleDataOne.value1 }}</td>
                                                    <td>{{ troubleDataOne.value2 }}</td>
                                                    <td>{{ troubleDataOne.value3 }}</td>
                                                    <td>{{ troubleDataOne.총합 }}</td>
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
        </div>
    </div>
</template>

<script>
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
import ColumnBar from '../../component/chart/ColumnBar.vue';
import LineChart from '../../component/chart/LineChart.vue';
import axios from 'axios';

export default {
    data() {
        return {
            searchPath: mdiMagnify,
            startDate: true,
            endDate: true,
            activeTab: 'tab1',
            objectDataList: [],
            troubleDataList: [],
            showChart: false,
            keyMap: {
                date: '날짜',
                value1: 'CPU',
                value2: '메모리',
                value3: '연결',
            },
            buttonColor: '',

            // 시간 단위
            timeUnit: 'hourly',

            // 장비 목록 조회
            dataListSearch: {
                currentPage: 1,
                perPage: 15,
                keyword: '',
                option: 'all'
            },
            dataList: [],
            dataListCount: null,

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

        handleDateChange: function () {
            if (this.startDate) {
                this.startDate = false
            } else if (this.endDate) {
                this.endDate = false
            }
        },
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
                    // console.log("vm.eqpmn = ", vm.equipment);
                    console.log("hourlyAnalsTroubleData - response: ", response.data);
                    vm.troubleDataList = response.data;
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
                    vm.troubleDataList = response.data;
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
                    vm.troubleDataList = response.data;
                })
                .catch(function (error) {
                    console.log("monthlyAnalsTroubleData - error : ", error);
                });
        },


        selectTimeUnit() {
            console.log("value - ", this.timeUnit);
            if (this.timeUnit == 'hourly') {
                this.hourlyAnalsDetectData();
                this.hourlyAnalsTroubleData();
            } else if (this.timeUnit == 'daily') {
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
            if (this.timeUnit == 'hourly') {
                this.hourlyAnalsDetectData();
                this.hourlyAnalsTroubleData();
            } else if (this.timeUnit == 'daily') {
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
            console.log("장비: ", this.equipment);
            console.log("showTab에서 resetData 전 dataList:", this.dataList);
            this.resetData();
            console.log("장비: ", this.equipment);
            this.dataSelectOne(this.equipment);
            this.activeTab = tabName;
        },

        resetData: function () {
            this.equipment = {
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
            }
        },
    },
    watch: {
        'timeUnit'() {
            // Object.keys(this.equipment).forEach(key => {
            //     this.equipment[key] = null;
            // });
            this.selectTimeUnit();
        }
    },
    computed: {

    },
    components: {
        'ColumnBar': ColumnBar,
        'LineChart': LineChart,
        'PageNavigation': PageNavigation,
        'SvgIcon': SvgIcon,
        'PaginationButton': PaginationButton
    },
    mounted() {
        console.log('main mounted');
        this.hourlyAnalsDetectData();
        this.hourlyAnalsTroubleData();
        this.equipmentSelectList();
    }
}
</script>

<style lang="css" scoped>
.content-zone {
    border: 1px solid #eee;
    padding: 10px;
    height: calc(100% - 55px);
    overflow: auto;
}

.tab-content .mapContent {
    height: 350px;
    width: 100%;
}

.tabNav {
    padding: 10px 0;
}
.tabNav li{
    border: 1px solid var(--color-blue);
    border-radius: 5px;
}
.tabNav li a {
    display: block;
    font-size: 1.6rem;
    text-align: center;
    padding: 5px 10px;
}

.tabNav li a.activeTab {
    background-color: var(--color-blue);
    color: var(--color-white);
    font-weight: 600;
    padding: 5px 10px;
    border-radius: 5px;
}

.tabNav li:last-child a {
    border-right: 0;
}
</style>
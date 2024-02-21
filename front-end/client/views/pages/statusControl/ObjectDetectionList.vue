<template>
    <div class="padding-main">

        <div class="container">
            <div class="page-titleZone flex justify-between">
                <p class="main-title">객체 인식 조회</p>
            </div>
            <div class="content">
                <div class="row mt20">
                 
                    <div class="searchbar-zone flex justify-between">
                        <div class="flex align-center justify-end">
                            <span class="option-title">인식 객체</span>
                            <div class="input-container flex">
                                <label class="check-label">
                                    <input type="checkbox" v-model="searchCondition.person" name="check"
                                        class="custom-checkbox">
                                    <span>사람</span>
                                </label>
                                <label class="check-label">
                                    <input type="checkbox" v-model="searchCondition.bicycle" name="check"
                                        class="custom-checkbox">
                                    <span>자전거</span>
                                </label>
                                <label class="check-label">
                                    <input type="checkbox" v-model="searchCondition.car" name="check" class="custom-checkbox">
                                    <span>승용차</span>
                                </label>
                                <label class="check-label">
                                    <input type="checkbox" v-model="searchCondition.bus" name="check" class="custom-checkbox">
                                    <span>버스</span>
                                </label>
                                <label class="check-label">
                                    <input type="checkbox" v-model="searchCondition.motorcycle" name="check"
                                        class="custom-checkbox">
                                    <span>오토바이</span>
                                </label>
                                <label class="check-label">
                                    <input type="checkbox" v-model="searchCondition.truck" name="check" class="custom-checkbox">
                                    <span>트럭</span>
                                </label>
                            </div>
                        </div>
                        <div class="flex justify-end align-center">
                            <span class="option-title">일자</span>
                            <input type="date" name="" id="search-date" class="square-date" v-model="searchCondition.startDate"
                                @input="updateEndDateMin" :class="{ 'date-placeholder': startDate }" data-placeholder="일자 선택">
                            <span class="coupler">~</span>
                            <input type="date" name="" id="search-date" class="square-date" v-model="searchCondition.endDate"
                                :min="searchCondition.startDate" :class="{ 'date-placeholder': endDate }"
                                data-placeholder="일자 선택">
                            <span class="option-title">검색유형</span>
                            <select name="" id="" class="square-select" v-model="searchCondition.searchType">
                                <option value="all">전체</option>
                                <option value="eqpmn_id">장비 ID</option>
                                <option value="eqpmn_nm">장비명</option>
                            </select>
                            <div class="search-square">
                                <input type="text" class="square-input" v-model="searchCondition.searchKeyword"
                                    placeholder="검색어를 입력하세요." @keyup.enter="searchObjectDetectionList">
                                <button class="square-button" @click="searchObjectDetectionList">
                                    <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="table-zone">
                        <div class="flex mb10 justify-between align-center">
                            <div class="count-zone">
                                <p>총 <span>{{ eventSelectListCount }}</span>건</p>
                            </div>
                            <div class="cunt-selectZone">
                                <select name="" id="" v-model="searchCondition.perPage">
                                    <option value=10>10개 보기</option>
                                    <option value=20>20개 보기</option>
                                </select>
                            </div>
                        </div>
                        <table class="list-table">
                            <colgroup>
                                <col style="width: 15%">
                                <col style="width: 15%">
                                <col style="width: 15%">
                                <col style="width: 15%">
                                <col style="width: 40%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>장비 ID</th>
                                    <th>장비명</th>
                                    <th>일자</th>
                                    <th>정보</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(detectionEventOne, eIdx) in eventSelectList" :key="eIdx"
                                    @click="openModal(detectionEventOne)">
                                    <td>{{ idx - eIdx }}</td>
                                    <td>{{ detectionEventOne.eqpmn_id }}</td>
                                    <td>{{ detectionEventOne.eqpmn_nm }}</td>
                                    <td>{{ detectionEventOne.ocrn_dt }}</td>
                                    <td>
                                        <span v-if="detectionEventOne.person_cnt > 0">
                                            사람 {{ detectionEventOne.person_cnt }}건&nbsp;&nbsp;&nbsp;
                                        </span>
                                        <span v-if="detectionEventOne.bicycle_cnt > 0">
                                            자전거 {{ detectionEventOne.bicycle_cnt }}건&nbsp;&nbsp;&nbsp;
                                        </span>
                                        <span v-if="detectionEventOne.car_cnt > 0">
                                            승용차 {{ detectionEventOne.car_cnt }}건&nbsp;&nbsp;&nbsp;
                                        </span>
                                        <span v-if="detectionEventOne.bus_cnt > 0">
                                            버스 {{ detectionEventOne.bus_cnt }}건&nbsp;&nbsp;&nbsp;
                                        </span>
                                        <span v-if="detectionEventOne.motorcycle_cnt > 0">
                                            오토바이 {{ detectionEventOne.motorcycle_cnt }}건&nbsp;&nbsp;&nbsp;
                                        </span>
                                        <span v-if="detectionEventOne.truck_cnt > 0">
                                            트럭 {{ detectionEventOne.truck_cnt }}건
                                        </span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <PaginationButton v-model:currentPage="searchCondition.currentPage" :perPage="searchCondition.perPage"
                        :totalCount="eventSelectListCount" :maxRange="10" :click="objectDetectEventSelectList" />
                </div>
            </div>
        </div>
        <div v-show="isModalOpen" class="modal-wrapper">
            <div class="modal-container">
                <div class="modal-title">
                    <div class="flex justify-between align-center">
                        <h2>상세정보</h2>
                        <button class="close-btn" @click="closeModal">X</button>
                    </div>
                </div>
                <div class="modal-content-monthly" v-show="modalType == 'tab-modal'">
                    <ul class="tab-content">
                        <li id="tab01" v-show="activeTab === 'tab1'">
                            <div style="text-align: center;">
                                <img :src="'data:image/jpeg;base64,' + detectionEventOne.image" alt="이미지">
                            </div>
                            <table class="form-table mt10">
                                <colgroup>
                                    <col style="width: 15%;" />
                                    <col style="width: 85%;" />
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>장비 ID</th>
                                        <td>{{ detectionEventOne.eqpmn_id }}</td>
                                    </tr>
                                    <tr>
                                        <th>장비명</th>
                                        <td>{{ detectionEventOne.eqpmn_nm }}</td>
                                    </tr>
                                    <tr>
                                        <th>일자</th>
                                        <td>{{ detectionEventOne.ocrn_dt }}</td>
                                    </tr>
                                    <tr>
                                        <th>위치</th>
                                        <td>{{ detectionEventOne.eqpmn_lat }}, {{ detectionEventOne.eqpmn_lon }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <table class="list-table mt10">
                                <thead>
                                    <tr>
                                        <th>사람</th>
                                        <th>자전거</th>
                                        <th>승용차</th>
                                        <th>버스</th>
                                        <th>오토바이</th>
                                        <th>트럭</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>{{ detectionEventOne.person_cnt }}</td>
                                        <td>{{ detectionEventOne.bicycle_cnt }}</td>
                                        <td>{{ detectionEventOne.car_cnt }}</td>
                                        <td>{{ detectionEventOne.bus_cnt }}</td>
                                        <td>{{ detectionEventOne.motorcycle_cnt }}</td>
                                        <td>{{ detectionEventOne.truck_cnt }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </li>
                        <li id="tab01" v-show="activeTab === 'tab1'"></li>
                    </ul>
                </div>
                <div class="modal-end flex justify-end">
                    <button v-show="modalType === 'tab-modal'" class="blue-btn small-btn" @click="closeModal">확인</button>
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
import axios from 'axios';
export default {
    data() {
        return {
            searchPath: mdiMagnify,
            inputValue: null,
            isModalOpen: false,
            activeTab: 'tab1',
            modalType: 'tab-modal',

            // 상세 정보
            detectionEventOne: {
                eqpmn_id: null,
                eqpmn_nm: null,
                ocrn_dt: null,
                eqpmn_lat: null,
                eqpmn_lon: null,
                person_cnt: null,
                bus_cnt: null,
                bicycle_cnt: null,
                car_cnt: null,
                truck_cnt: null,
                motorcycle_cnt: null,
                image: null
            },

            // 검색 조건 및 목록 페이지네이션
            searchCondition: {
                searchType: "all",
                searchKeyword: "",
                startDate: null,
                endDate: null,
                eqpmn_nm: null,
                person: false,
                bicycle: false,
                car: false,
                bus: false,
                truck: false,
                motorcycle: false,
                currentPage: 1,
                perPage: 10,
                filters: [],
            },
            eventSelectList: [],
            eventSelectListCount: null,
            idx: null,
        }
    },

    methods: {
        // 객체 인식 기록 조회
        objectDetectEventSelectList: function () {
            const vm = this;
            axios({
                url: "/objectDetectEventSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.searchCondition
            })
                .then(function (response) {
                    console.log("objectDetectEventSelectList - response: ", response.data);
                    console.log("searchCondition = ", vm.searchCondition);
                    vm.eventSelectList = response.data.selectList;
                    vm.eventSelectListCount = response.data.selectListCount;
                    vm.idx = vm.eventSelectListCount - (vm.searchCondition.currentPage - 1) * vm.searchCondition.perPage;
                })
                .catch(function (error) {
                    console.log("objectDetectEventSelectList - error : ", error);
                    alert("전체 객체 인식 기록 조회에 오류가 발생했습니다.");
                });
        },

        updateEndDateMin() {
            // 시작 일자를 선택하면 종료 일자의 min 속성을 업데이트
            this.$nextTick(() => {
                this.searchCondition.endDate = ''; // 종료 일자 초기화
            });
        },

        // postSelectListPage: function () {
        //     this.$router.push({ path: "/objectDetectionList.page", query: { 'page': this.searchCondition.currentPage } });
        // },

        // 탭 변경
        showTab: function (tabName) {
            this.activeTab = tabName;
        },
        closeModal: function () {
            this.isModalOpen = false;
        },
        openModal: function (detectionEvent) {
            this.detectionEventOne = detectionEvent;
            this.isModalOpen = true;
        },

        searchObjectDetectionList: function () {
            this.searchCondition.currentPage = 1;
            this.objectDetectEventSelectList();
        },
        
        updateFilters: function(filter, add) {
            this.searchCondition.currentPage = 1;
            if (add) {
                this.searchCondition.filters.push(filter);
            } else {
                const index = this.searchCondition.filters.indexOf(filter);
                if (index >= 0) {
                    this.searchCondition.filters.splice(index, 1);
                }   
            }
            this.objectDetectEventSelectList();
        },
    },
    watch: {
        // 'searchCondition.currentPage': 'objectDetectEventSelectList',
        'searchCondition.perPage'() {
            this.searchObjectDetectionList();
        },

        'searchCondition.person'(newVal) {
            this.updateFilters('person', newVal);
        },
        'searchCondition.bicycle'(newVal) {
            this.updateFilters('bicycle', newVal);
        },
        'searchCondition.car'(newVal) {
            this.updateFilters('car', newVal);
        },
        'searchCondition.bus'(newVal) {
            this.updateFilters('bus', newVal);
        },
        'searchCondition.truck'(newVal) {
            this.updateFilters('truck', newVal);
        },
        'searchCondition.motorcycle'(newVal) {
            this.updateFilters('motorcycle', newVal);
        },
        // 검색조건 입력 시 해당 조건으로 바로 조회
        // 'searchCondition.person': 'searchObjectDetectionList',
        // 'searchCondition.bicycle': 'searchObjectDetectionList',
        // 'searchCondition.car': 'searchObjectDetectionList',
        // 'searchCondition.bus': 'searchObjectDetectionList',
        // 'searchCondition.motorcycle': 'searchObjectDetectionList',
        // 'searchCondition.truck': 'searchObjectDetectionList',
        // 'searchCondition.startDate': 'searchObjectDetectionList',
        // 'searchCondition.endDate': 'searchObjectDetectionList',
    },
    computed: {
    },
    components: {
        'PageNavigation': PageNavigation,
        'PaginationButton': PaginationButton,
        'SvgIcon': SvgIcon
    },
    created() {
    },
    mounted() {
        console.log('main mounted');
        this.objectDetectEventSelectList();
    }
}
</script>

<style scoped>


.modal-container {
    width: 38%;
    height: 90%;
}

.modal-content-monthly{
    padding: 20px 20px;
}

</style>
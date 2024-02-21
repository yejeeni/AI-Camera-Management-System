<template>
    <div class="padding-main">

        <div class="container">
            <div class="page-titleZone flex justify-between">
                <p class="main-title">장비 장애 조회</p>
            </div>
            <div class="content">
                <div class="row mt20">
                
                    <div class="searchbar-zone flex justify-between">
                        <div class="flex ">
                            <div class="flex  align-center justify-end">
                                <span class="option-title">장애 코드</span>
                                <div class="input-container flex">
                                    <label class="check-label">
                                        <input type="checkbox" name="check" class="custom-checkbox"
                                            v-model="errListCheckbox['ERR-CONNECTION']"
                                            @change="isErrListAllFalse('ERR-CONNECTION')">
                                        <span>통신 장애</span>
                                    </label>
                                    <!-- <label class="check-label">
                                        <input type="checkbox" name="check" class="custom-checkbox"
                                            v-model="errListCheckbox['ERR-CPU-TEMP']" @change="isErrListAllFalse('ERR-CPU-TEMP')">
                                        <span>CPU 온도</span>
                                    </label> -->
                                    <label class="check-label">
                                        <input type="checkbox" name="check" class="custom-checkbox"
                                            v-model="errListCheckbox['ERR-CPU-USAGE']"
                                            @change="isErrListAllFalse('ERR-CPU-USAGE')">
                                        <span>CPU 사용량</span>
                                    </label>
                                    <!-- <label class="check-label">
                                        <input type="checkbox" name="check" class="custom-checkbox"
                                            v-model="errListCheckbox['ERR-DISK-USAGE']" @change="isErrListAllFalse('ERR-DISK-USAGE')">
                                        <span>디스크 사용량</span>
                                    </label> -->
                                    <label class="check-label">
                                        <input type="checkbox" name="check" class="custom-checkbox"
                                            v-model="errListCheckbox['ERR-MEM-USAGE']"
                                            @change="isErrListAllFalse('ERR-MEM-USAGE')">
                                        <span>메모리 사용량</span>
                                    </label>
                                </div>
                            </div>
                            <div class="flex  align-center justify-end">
                                <span class="option-title">복구 여부</span>
                                <div class="input-container flex">
                                    <label class="check-label">
                                        <input type="checkbox" name="check" class="custom-checkbox"
                                            v-model="searchCondition.recovery" checked>
                                        <span>복구</span>
                                    </label>
                                    <label class="check-label">
                                        <input type="checkbox" name="check" class="custom-checkbox"
                                            v-model="searchCondition.nonRecovery" checked>
                                        <span>미복구</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="flex justify-end align-center">
                            <span class="option-title">일자</span>
                            <input type="date" name="" id="search-date" class="square-date"
                                v-model="searchCondition.startDate" @input="updateEndDateMin"
                                :class="{ 'date-placeholder': startDate }" data-placeholder="일자 선택"
                                @change="handleDateChange">
                            <span class="coupler">~</span>
                            <input type="date" name="" id="search-date" class="square-date"
                                v-model="searchCondition.endDate" :min="searchCondition.startDate"
                                :class="{ 'date-placeholder': endDate }" data-placeholder="일자 선택"
                                @change="handleDateChange">
                            <span class="option-title">검색유형</span>
                            <select name="" id="" class="square-select" v-model="searchCondition.searchType">
                                <option value="all">전체</option>
                                <option value="eqpmn_id">장비 ID</option>
                                <option value="eqpmn_nm">장비명</option>
                            </select>
                            <div class="search-square">
                                <input type="text" class="square-input" v-model="searchCondition.searchKeyword"
                                    placeholder="검색어를 입력하세요." @keyup.enter="searchTroubleList">
                                <button class="square-button" @click="searchTroubleList">
                                    <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="table-zone">
                        <div class="flex mb10 justify-between align-center">
                            <div class="count-zone">
                                <p>총 <span>{{ eqpmnTroubleListCount }}</span>건</p>
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
                                <col style="width: 10%">
                                <col style="width: 15%">
                                <col style="width: 15%">
                                <col style="width: 15%">
                                <col style="width: 15%">
                                <col style="width: 15%">
                                <col style="width: 15%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>장비 ID</th>
                                    <th>장비명</th>
                                    <th>발생 일자</th>
                                    <th>장애명</th>
                                    <th>복구 여부</th>
                                    <th>복구 일자</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(equipmentTroubleOne, eIdx) in eqpmnTroubleList" :key="eIdx">
                                    <td>{{ idx - eIdx }}</td>
                                    <td>{{ equipmentTroubleOne.eqpmn_id }}</td>
                                    <td>{{ equipmentTroubleOne.eqpmn_nm }}</td>
                                    <td>{{ equipmentTroubleOne.ocrn_dt }}</td>
                                    <td>{{ equipmentTroubleOne.trobl_nm }}</td>
                                    <td>{{ equipmentTroubleOne.recovery_yn }}</td>
                                    <td>{{ equipmentTroubleOne.recovery_dt }}</td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <PaginationButton v-model:currentPage="searchCondition.currentPage" :perPage="searchCondition.perPage"
                        :totalCount="eqpmnTroubleListCount" :maxRange="5" :click="eqpmnTroubleSelectList" />
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

            eqpmnTroubleList: [],
            eqpmnTroubleListCount: null,
            idx: null,

            // 검색 조건 및 목록 페이지네이션
            searchCondition: {
                searchType: 'all',
                searchKeyword: null,
                startDate: null,
                endDate: null,
                eqpmn_nm: null,
                // errList: ['ERR-CONNECTION', 'ERR-CPU-TEMP', 'ERR-CPU-USAGE', 'ERR-DISK-USAGE', 'ERR-MEM-USAGE'],
                errList: ['ERR-CONNECTION', 'ERR-CPU-USAGE', 'ERR-MEM-USAGE'],
                recovery: true,
                nonRecovery: true,
                currentPage: 1,
                perPage: 10,
            },

            // 에러 체크박스
            errListCheckbox: {
                'ERR-CONNECTION': true,
                // 'ERR-CPU-TEMP': true,
                'ERR-CPU-USAGE': true,
                // 'ERR-DISK-USAGE': true,
                'ERR-MEM-USAGE': true,
            },

            lastChangedRecoveryCheckbox: null,
            lastChangedErrCheckbox: null,
        }
    },

    methods: {
        // 장비 장애 조회
        eqpmnTroubleSelectList: function () {
            const vm = this;
            if (this.searchCondition.errList.length == 0) {
                alert("장애 코드는 하나 이상 선택해야 합니다.");
                return;
            }
            console.log(this.searchCondition);

            axios({
                url: "/eqpmnTroubleSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.searchCondition
            })
                .then(function (response) {
                    vm.eqpmnTroubleList = response.data.selectList;
                    vm.eqpmnTroubleListCount = response.data.selectListCount;
                    vm.idx = vm.eqpmnTroubleListCount - (vm.searchCondition.currentPage - 1) * vm.searchCondition.perPage;
                })
                .catch(function (error) {
                    console.log("eqpmnTroubleSelectList - error : ", error);
                    alert("장비 장애 조회에 오류가 발생했습니다.");
                });
        },

        // 탭 변경
        showTab: function (tabName) {
            this.activeTab = tabName;
        },

        updateEndDateMin() {
            // 시작 일자를 선택하면 종료 일자의 min 속성을 업데이트
            this.$nextTick(() => {
                this.searchCondition.endDate = ''; // 종료 일자 초기화
            });
        },

        // 현재 페이지를 1로 바꾸고 목록을 조회하는 메서드
        searchTroubleList: function () {
            this.searchCondition.currentPage = 1;
            this.eqpmnTroubleSelectList();
        },

        // recovery 체크박스가 모두 false인지 검사하는 메서드
        isRecoveryAllFalse() {
            if (!this.searchCondition.recovery && !this.searchCondition.nonRecovery) {
                alert('하나 이상 선택해야 합니다.');
                if (this.lastChangedRecoveryCheckbox) {
                    // 마지막으로 변경된 항목을 다시 체크
                    this.$nextTick(() => {
                        this.searchCondition[this.lastChangedRecoveryCheckbox] = true;
                    });
                }
            } else {
                // 현재 변경된 항목 저장
                this.lastChangedRecoveryCheckbox = this.searchCondition.recovery ? 'recovery' : 'nonRecovery';
                this.searchTroubleList(); // 조건에 맞게 조회
            }
        },

        // errList 체크박스가 모두 false인지 검사하는 메서드
        isErrListAllFalse(errCode) {
            if (Object.values(this.errListCheckbox).every(value => value === false)) { // 에러코드 체크박스가 모두 false가 됐으면
                alert('하나 이상 선택해야 합니다.');
                // 마지막으로 변경된 항목을 다시 체크
                this.$nextTick(() => {
                    this.errListCheckbox[errCode] = true;
                });
                return;
            } else {
                if (this.searchCondition.errList.includes(errCode)) { // 이미 추가된 에러코드를 선택했을 경우 -> 체크 해제했을 경우
                    const index = this.searchCondition.errList.indexOf(errCode);
                    if (index !== -1) {
                        this.searchCondition.errList.splice(index, 1); // errCodeList에서 해당 에러코드를 제거
                    }
                } else {
                    this.searchCondition.errList.push(errCode); // 선택한 에러코드를 추가
                }
                this.searchTroubleList(); // 조건에 맞게 조회

                console.log("updateErrCode - ", this.searchCondition.errList);
            }
        },
    },
    watch: {
        'searchCondition.perPage'() { // perPage 선택 시
            this.searchTroubleList();
        },
        'searchCondition.recovery': function () {
            this.isRecoveryAllFalse();
        },
        'searchCondition.nonRecovery': function () {
            this.isRecoveryAllFalse();
        },
        'searchCondition.startDate': 'searchTroubleList',
        'searchCondition.endDate': 'searchTroubleList',
        'searchCondition.searchKeyword': 'searchTroubleList',

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
        this.eqpmnTroubleSelectList();
    }
}
</script>

<style scoped></style>
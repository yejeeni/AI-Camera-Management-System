<template>
    <div class="padding-main">

        <div class="container">
            <div class="page-titleZone flex justify-between">
                <p class="main-title">장비관리</p>
            </div>
            <div class="content">
                <div class="row mt20">

                    <div class="searchbar-zone">
                        <div class="flex justify-end align-center">
                            <span class="option-title">검색유형</span>
                            <select name="" id="" style="width:150px; margin-right:10px" v-model="dataListSearch.option">
                                <option value="all">전체</option>
                                <option value="eqpmn_id">장비 ID</option>
                                <option value="eqpmn_nm">장비명</option>
                                <option value="camera_nm">카메라명</option>
                                <option value="mdfr_id">등록자 ID</option>
                            </select>
                            <div class="search-square">
                                <input type="text" class="square-input" placeholder="검색어를 입력하세요" name="keyword" id="keyword"
                                    v-model="dataListSearch.keyword" @keyup.enter="goToFirstPageAndLoadEquipmentsList()">
                                <button class="square-button" @click="goToFirstPageAndLoadEquipmentsList()">
                                    <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="table-zone">
                        <div class="flex justify-between align-center">
                            <div class="count-zone">
                                <p>총 <span>{{ dataListCount }}</span>건</p>
                            </div>
                            <div class="cunt-selectZone">
                                <select v-model="dataListSearch.perPage" name="perPage" id="perPage"
                                    @change="goToFirstPageAndLoadEquipmentsList()">
                                    <option value="10">10개 보기</option>
                                    <option value="20">20개 보기</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="table-zone">
                        <table class="list-table">
                            <colgroup>
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                                <col style="width: ;">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>장비 ID</th>
                                    <th>장비명</th>
                                    <th>장비 위도</th>
                                    <th>장비 경도</th>
                                    <th>카메라 IP</th>
                                    <th>카메라명</th>
                                    <th>엣지 ID</th>
                                    <th>엣지 PW</th>
                                    <th>등록일자</th>
                                    <th>등록자 ID</th>
                                    <th>수정일자</th>
                                    <th>수정자 ID</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(item, id) in dataList" :key="item.eqpmn_id" @click="dataSelectOne(item)">
                                    <td><input type="checkbox" name="item.eqpmn_id" id="item.eqpmn_id"
                                            @click.stop="inDeleteList(item)"></td>
                                    <td>{{ item.eqpmn_id }}</td>
                                    <td>{{ item.eqpmn_nm }}</td>
                                    <td>{{ item.eqpmn_lat }}</td>
                                    <td>{{ item.eqpmn_lon }}</td>
                                    <td>{{ item.camera_ip }}</td>
                                    <td>{{ item.camera_nm }}</td>
                                    <td>{{ item.cntn_id }}</td>
                                    <td>{{ item.cntn_pw }}</td>
                                    <td>{{ item.reg_dt }}</td>
                                    <td>{{ item.rgtr_id }}</td>
                                    <td>{{ item.mdfcn_dt }}</td>
                                    <td>{{ item.mdfr_id }}</td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="flex justify-end mt10">
                            <button class="red-border-btn small-btn" @click="equipmentDelete">선택 항목 삭제</button>
                        </div>
                    </div>

                    <PaginationButton v-model:currentPage="dataListSearch.currentPage" :perPage="dataListSearch.perPage"
                        :total-count="dataListCount" :max-range="5" :click="equipmentSelectList" />

                </div>
                <div class="row form-box ">
                    <div class="content-titleZone ">
                        <p class="box-title">{{ buttonLabel }}</p>
                    </div>
                    <div class="table-zone">
                        <table class="form-table2">
                            <colgroup>
                                <col style="width: 10%;">
                                <col style="width: 40%;">
                                <col style="width: 10%;">
                                <col style="width: 40%;">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>장비명</th>
                                    <td><input type="text" name="eqpmn_nm" id="eqpmn_nm" v-model="equipment.eqpmn_nm"
                                            class="full-input" placeholder="장비명을 입력하세요"></td>
                                </tr>
                                <tr>
                                    <th>장비 위도</th>
                                    <td><input type="text" name="eqpmn_lat" id="eqpmn_lat" v-model="equipment.eqpmn_lat"
                                            class="full-input" placeholder="장비의 위도를 입력하세요"></td>
                                    <th>장비 경도</th>
                                    <td><input type="text" name="eqpmn_lon" id="eqpmn_lon" v-model="equipment.eqpmn_lon"
                                            class="full-input" placeholder="장비의 경도를 입력하세요"></td>
                                </tr>
                                <tr>
                                    <th>카메라 IP</th>
                                    <td><input type="text" name="camera_ip" id="camera_ip" v-model="equipment.camera_ip"
                                            class="full-input" placeholder="카메라 IP를 입력하세요"></td>
                                    <th>카메라명</th>
                                    <td><input type="text" name="camera_nm" id="camera_nm" v-model="equipment.camera_nm"
                                            class="full-input" placeholder="카메라명을 입력하세요"></td>
                                </tr>
                                <tr>
                                    <th>엣지 ID</th>
                                    <td><input type="text" name="cntn_id" id="cntn_id" v-model="equipment.cntn_id"
                                            class="full-input" placeholder="엣지 ID를 입력하세요"></td>
                                    <th>엣지 PW</th>
                                    <td><input type="text" name="cntn_pw" id="cntn_pw" v-model="equipment.cntn_pw"
                                            class="full-input" placeholder="엣지 PW를 입력하세요"></td>
                                </tr>
                            </tbody>

                        </table>
                    </div>
                    <div class="flex justify-end">
                        <button class="orange-border-btn small-btn" @click="handleButtonAction()">{{ buttonLabel }}</button>
                        <button class="darkg-border-btn small-btn" @click="initializeForm()">초기화</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
export default {
    data() {
        return {
            dataList: [],
            deleteList: [], // 삭제할 항목들의 리스트를 관리합니다.
            dataListCount: null,

            dataListSearch: {
                currentPage: 1,
                perPage: 10,
                keyword: '',
                option: 'all'
            },

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

            isEditMode: false,
            selectedRow: null,

            searchPath: mdiMagnify,
            inputValue: null,

        }
    },
    methods: {
        //장비등록
        equipmentInsert: function () {
            const vm = this;
            axios({
                url: "/equipmentInsert.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    if (response.data > 0) {
                        alert("장비를 등록했습니다.");
                        vm.resetData();
                        vm.equipmentSelectList();
                    }
                    else {
                        alert("장비 등록에 실패 했습니다.");
                    }
                })
                .catch(function (error) {
                    alert("동일한 장비 ID가 있습니다.");
                });
        },

        //장비조회
        equipmentSelectList: function () {
            const vm = this;
            vm.deleteList = [];
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

        //장비수정
        equipmentUpdate: function () {
            const vm = this;
            axios({
                url: "/equipmentUpdate.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment
            })
                .then(function (response) {
                    if (response.data > 0) {
                        alert("장비를 수정 하였습니다.");
                        vm.equipmentSelectList();
                    } else {
                        alert("장비 수정에 실패했습니다.");
                    }
                })
                .catch(function (error) {
                    alert("장비 수정에 오류가 발생했습니다.");
                });
        },


        //장비삭제
        equipmentDelete: function () {
            const vm = this;

            if (vm.deleteList.length > 0) {
                if (confirm("장비를 삭제하시겠습니까?") == false) {
                    return;
                }
            } else {
                alert("장비를 선택하세요.");
                return;
            }
            axios({
                url: "/equipmentDelete.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { deleteList: vm.deleteList },
            })
                .then(function (response) {
                    vm.equipmentSelectList();
                    alert("장비가 삭제되었습니다.");

                })
                .catch(function (error) {
                    console.log("equipmentDelete - error : ", error);
                    alert("장비 삭제에 오류가 발생했습니다.");
                });
        },


        inDeleteList: function (eqpmn_id) {
            const index = this.deleteList.indexOf(eqpmn_id);
            if (index !== -1) {
                // eqpmn_id가 이미 deleteList에 존재합니다. 이 경우 해당 항목을 삭제합니다.
                this.deleteList.splice(index, 1);
            } else {
                // eqpmn_id가 deleteList에 존재하지 않습니다. 이 경우 해당 항목을 추가합니다.
                this.deleteList.push(eqpmn_id);
            }
        },


        //데이터 편집 및 유효성검사
        handleButtonAction() {
            const regexForLat = /^-?(3[3-8]|[3-7]\d|\d{1,2})\.\d{6}$/;
            const regexForLon = /^-?(12[4-9]|13[0-2]|\d{1,2})\.\d{6}$/;
            const regexForIp = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

            if (!this.equipment.eqpmn_nm) {
                alert("장비명을 입력해주세요.");
                return;
            }

            if (!this.equipment.eqpmn_lat) {
                alert("위도, 경도를 올바르게 입력해주세요. (형식: 숫자 3개.숫자 6개)");
                return;
            }
            if (!regexForLat.test(this.equipment.eqpmn_lat)) {
                alert("위도 형식이 맞지 않습니다.");
                return;
            }

            if (!this.equipment.eqpmn_lon) {
                alert("경도를 올바르게 입력해주세요. (형식: 숫자 3개.숫자 6개)");
                return;
            }
            if (!regexForLon.test(this.equipment.eqpmn_lon)) {
                alert("경도 형식이 맞지 않습니다.");
                return;
            }

            if (!this.equipment.camera_ip) {
                alert("카메라 IP를 올바르게 입력해주세요. (형식: 숫자.숫자.숫자.숫자)");
                return;
            }
            if (!regexForIp.test(this.equipment.camera_ip)) {
                alert("카메라 IP 형식이 맞지 않습니다.");
                return;
            }

            if (!this.equipment.camera_nm) {
                alert("카메라명을 입력해주세요.");
                return;
            }

            if (!this.equipment.cntn_id) {
                alert("엣지 ID를 입력해주세요.");
                return;
            }

            if (!this.equipment.cntn_pw) {
                alert("엣지 PW를 입력해주세요.");
                return;
            }


            if (this.isEditMode) {
                this.equipmentUpdate();
            } else {
                this.equipmentInsert();
            }
        },

        // 입력 폼과 버튼을 기본값으로 초기화
        initializeForm: function () {
            this.resetData();
            this.isEditMode = false;
        },

        // equipment데이터 초기화
        resetData: function () {
            Object.keys(this.equipment).forEach(key => {
                this.equipment[key] = null;
            });
        },

        //장비 상세 보기 
        dataSelectOne: function (item) {
            this.isEditMode = true
            this.selectedRow = item.eqpmn_id;
            this.equipment = Object.assign({}, item);
        },

        // 첫 페이지로 초기화하고 장비 목록을 조회합니다.
        goToFirstPageAndLoadEquipmentsList: function () {
            this.dataListSearch.currentPage = 1;
            this.equipmentSelectList();
        }

    },
    watch: {},
    computed: {
        //상태에 따라 버튼 라벨을 '수정' 또는 '등록'으로 반환
        buttonLabel() {
            return this.isEditMode ? '수정' : '등록';
        },

    },
    components: {
        'PageNavigation': PageNavigation,
        'PaginationButton': PaginationButton,
        'SvgIcon': SvgIcon
    },
    mounted() {
        this.equipmentSelectList();
        console.log('main mounted');
    }
}
</script>

<style scoped></style>
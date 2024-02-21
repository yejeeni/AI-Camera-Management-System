<template>
    <div class="padding-main">

        <div class="container">
            <div class="page-titleZone flex justify-between">
                <p class="main-title">사용자 관리</p>
            </div>

            <ul class="tabNav flex justify-start">
                <li @click="showTab('tab1')" tabstatu:true class="flex15"><a href="#UserList"
                        :class="{ activeTab: activeTab === 'tab1' }" @click="preventScroll">사용자 조회</a></li>
                <li @click="showTab('tab2')" tabstatu:false class="flex15"><a href="#UserLog"
                        :class="{ activeTab: activeTab === 'tab2' }">사용자 접속 기록</a></li>
            </ul>
            <div class="content">
                <li id="UserList" v-show="activeTab === 'tab1'">
                    <div class="row">
                        <div class="content-titleZone">
                            <p class="box-title">사용자 목록</p>
                        </div>
                        <div class="searchbar-zone">
                            <div class="flex justify-end align-center">
                                <span class="option-title">검색유형</span>
                                <select name="" id="" style="width:150px; margin-right:10px"
                                    v-model="dataListSearch.searchType">
                                    <!-- 암호화로 인해 전체 검색 삭제<option value="all">전체</option> -->
                                    <option value="user_name">사용자명</option>
                                    <option value="user_id">사용자 ID</option>
                                    <option value="user_eml">이메일</option>
                                    <option value="user_telno">전화번호</option>
                                </select>
                                <div class="search-square">
                                    <input type="text" class="square-input" placeholder="검색어를 입력하세요"
                                        v-model="dataListSearch.searchText" @keyup.enter="searchButtenClick">
                                    <button class="square-button" @click="searchButtenClick()">
                                        <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="table-zone ">
                            <div class="flex mb10 justify-between align-center">
                                <div class="count-zone">
                                    <p>총 <span>{{ dataListCount }}</span>건</p>
                                </div>
                                <div class="cunt-selectZone">
                                    <select v-model="dataListSearch.perPage" @change="searchButtenClick()">
                                        <option value="10">10개 보기</option>
                                        <option value="20">20개 보기</option>
                                    </select>
                                </div>
                            </div>
                            <table class="list-table">
                                <colgroup>
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
                                        <th>사용자명</th>
                                        <th>사용자 ID</th>
                                        <th>이메일</th>
                                        <th>전화번호</th>
                                        <th>등록 일자</th>
                                        <th>수정 일자</th>
                                        <th>권한</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <tr class="item" v-for="(item, ) in dataList" :key="item.id"
                                        :class="{ 'selected-row': selectedRow == item.user_id }"
                                        @click="userSelectOne(item)">
                                        <td><input type="checkbox" name="" id="" v-model="item.isChecked"
                                                @click.stop="inDeleteList(item)"></td>

                                        <td>{{ item.user_name }}</td>
                                        <td>{{ item.user_id }}</td>
                                        <td>{{ item.user_eml }}</td>
                                        <td>{{ item.user_telno }}</td>
                                        <td>{{ item.reg_dt }}</td>
                                        <td>{{ item.mdfcn_dt }}</td>
                                        <td>{{ item.user_auth }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="flex mt10 justify-end">
                                <button class="red-border-btn small-btn" @click="userDelete()">선택항목 삭제</button>
                            </div>
                        </div>
                        <PaginationButton v-model:currentPage="dataListSearch.currentPage" :perPage="dataListSearch.perPage"
                            :total-count="dataListCount" :max-range="5" :click="userSelectList" />
                    </div>
                    <div class="row form-box">
                        <div class="content-titleZone">
                            <p class="box-title">{{ formTitle }}</p>
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
                                        <th>사용자명</th>
                                        <td><input type="text" class="full-input" placeholder="한글 또는 영어로 입력하세요"
                                                v-model="user.user_name"></td>
                                        <th>사용자 ID</th>
                                        <td>
                                            <div class="flex justify-between">
                                                <input type="text" class="full-input flex80" placeholder="소문자와 숫자를 포함한 4~20자리를 입력하세요"
                                                    v-model="user.user_id" :disabled="isEditMode" />
                                                <button v-if="!isEditMode" class="blue-border-btn small-btn flex20"
                                                    @click="userIdDuplicateCheck()">중복 확인</button>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>비밀번호</th>
                                        <td><input type="password" class="full-input" placeholder="대소문자를 포함한 6~20자리를 입력하세요 (특수문자 포함 권장)"
                                                v-model="user.user_pw" :disabled="isEditMode" /></td>
                                        <th v-if="!isEditMode">비밀번호 확인</th>
                                        <th v-else="">비밀번호 초기화</th>
                                        <td>
                                            <div class="flex"><input v-if="!isEditMode" class="full-input" type="password"
                                                    placeholder="대소문자를 포함한 6~20자리를 입력하세요 (특수문자 포함 권장)" v-model="user.user_pw_check" />

                                                <button v-else class="blue-border-btn small-btn" @click="resetpw">비밀번호
                                                    초기화</button>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>이메일</th>
                                        <td><input type="text" class="full-input" placeholder="이메일을 입력하세요"
                                                v-model="user.user_eml" /></td>
                                        <th>전화번호</th>
                                        <td>
                                            <div class="flex"><input type="text" class="full-input"
                                                    placeholder="전화번호를 입력하세요" v-model="user.user_telno" /></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>권한 설정</th>
                                        <th style="text-align: left;">
                                            <label>
                                                <input type="radio" v-model="user.user_auth" value="Basic"
                                                    :disabled="(user.user_auth === 'Admin' && userAuth !== 'root') || (user.user_auth === 'root' && userAuth !== 'root')" />
                                                Basic
                                            </label>
                                            <label v-if="isAdmin || userAuth === 'root'">
                                                <input type="radio" v-model="user.user_auth" value="Admin"
                                                    :disabled="(user.user_auth === 'Admin' && userAuth !== 'root') || (user.user_auth === 'root' && userAuth !== 'root')" />
                                                Admin
                                            </label>

                                        </th>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="flex justify-end">
                            <button class="blue-border-btn small-btn" @click="buttonclickAction()">{{ buttonLabel
                            }}</button>
                            <button class="darkg-border-btn small-btn" @click="resetButtenClick()">초기화</button>
                        </div>
                    </div>
                </li>

                <!-- 사용자 접속 기록 탭 -->
                <li id="UserLog" v-show="activeTab === 'tab2'">
                    <div class="row">
                        <div class="content-titleZone">
                            <p class="box-title">접속 기록</p>
                        </div>
                        <div class="searchbar-zone">
                            <div class="flex justify-end align-center">
                                <span class="option-title">일자</span>
                                <input type="date" v-model="dataListSearch.startDate">
                                <span class="coupler">~</span>
                                <input type="date" v-model="dataListSearch.endDate" :min="dataListSearch.startDate"
                                    @change="searchAccecssButtenClick">
                                <span class="option-title">검색유형</span>
                                <select name="" id="" style="width:150px; margin-right:10px"
                                    v-model="dataListSearch.searchType">
                                    <option value="all">전체</option>
                                    <option value="cntn_ty">접속 환경</option>
                                    <option value="cntn_ip">접속 IP</option>
                                    <option value="user_id">사용자 ID</option>
                                </select>

                                <div class="search-square">
                                    <input type="text" class="square-input" placeholder="Search"
                                        v-model="dataListSearch.searchText" @keyup.enter="searchAccecssButtenClick">
                                    <button class="square-button" @click="searchAccecssButtenClick()">
                                        <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="table-zone">
                            <div class="flex mb10 justify-between align-center">
                                <div class="count-zone">
                                    <p>총 <span>{{ dataAccessRcordSelectListCount }}</span>건</p>
                                </div>
                                <div class="cunt-selectZone">
                                    <select v-model="dataListSearch.perPage" @change="searchAccecssButtenClick()">
                                        <option value="10">10개 보기</option>
                                        <option value="20">20개 보기</option>
                                    </select>
                                </div>
                            </div>
                            <table class="list-table">
                                <colgroup>
                                    <col style="width: ;">
                                    <col style="width: ;">
                                    <col style="width: ;">
                                    <col style="width: ;">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>접속 일자</th>
                                        <th>접속 환경</th>
                                        <th>접속 IP</th>
                                        <th>사용자 ID</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <tr class="item" v-for="(item, index) in dataList" :key="item.dt">
                                        <td>{{ item.cntn_dt }}</td>
                                        <td>{{ item.cntn_ty }}</td>
                                        <td>{{ item.cntn_ip }}</td>
                                        <td>{{ item.user_id }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="flex justify-end">
                            </div>
                        </div>
                        <PaginationButton v-model:currentPage="dataListSearch.currentPage" :perPage="dataListSearch.perPage"
                            :total-count="dataAccessRcordSelectListCount" :max-range="5"
                            :click="userAccessRcordSelectList" />

                    </div>
                </li>
            </div>
        </div>
    </div>
</template>

<script>
//비동기적 화면의 일부부만 변경 = axios
import axios from "axios";
import PageNavigation from '../../component/PageNavigation.vue';
import PaginationButton from '../../component/PaginationButton.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
import AlertModal from "../../template/templateElement/AlertModal.vue";
import { useStore } from "vuex";

export default {
    data() {
        return {
            store: useStore(),
            userAuth: sessionStorage.getItem('user_auth') || null,

            searchPath: mdiMagnify,
            inputValue: null,

            //데이터 목록
            dataList: [],

            //한번에 삭제할 데이터를 저장하는 리스트
            deleteList: [],

            dataListCount: null,

            dataAccessRcordSelectListCount: null,

            dataListSearch: {
                currentPage: 1,
                //페이지 내에서 출력할 객체
                perPage: 10,
                //키워드 안에 있는 정보로 검색
                searchText: '',
                //옵션을 선택하기 전에 사용자 ID 검색으로 기본값 설정
                searchType: 'user_id',

            },

            tabstatu: true,

            // 사용자 등록 및 수정 입력 데이터

            user: {

                user_id: null,
                user_name: null,
                user_pw: null,
                user_pw_check: null,
                user_eml: null,
                user_telno: null,
                user_auth: null,
                reg_dt: null,
                rgt_id: null,
                mdfcn_dt: null,
                mdfr_id: null,
                use_yn: null,

            },
            // 선택된 데이터
            selectedRow: null,

            //데이터 편집상태 = false = 수정버튼 > true = 등록버튼
            isEditMode: false,



            //ID 체크상태
            idCheckStatus: false,

            activeTab: 'tab1',

            cntn: {

                cntn_dt: null,
                cntn_ty: null,
                cntn_ip: null,
                user_id: null,
            },


        }

    },
    methods: {

        // 탭 변경
        showTab: function (tabName) {
            this.activeTab = tabName;

            this.dataListSearch = {
                currentPage: 1,
                perPage: 10,
                searchText: '',
                searchType: 'user_id',
                startDate: '',
                endDate: ''
            };

            this.isEditMode = false;
        },
        preventScroll(event) {
            event.preventDefault();
        },




        // 데이터 목록 조회
        userSelectList: function () {
            const vm = this;
            vm.deleteList = [];
            axios({
                url: "/userSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.dataListSearch,
            })
                .then(function (response) {
                    console.log("dataList - response : ", response.data);
                    vm.dataList = response.data.userSelectList;
                    vm.dataListCount = response.data.userSelectListCount;
                })
                .catch(function (error) {
                    console.log("dataList - error : ", error);
                    alert("목록 조회에 오류가 발생했습니다.");
                });
        },

        // 데이터 등록
        userInsert: function () {
            const vm = this;
            axios({
                url: "/userInsert.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.user,
            })
                .then(function (response) {
                    console.log("userInsert - response : ", response.data);
                    if (response.data > 0) {  // 서버 응답을 확인하여 상태를 처리합니다.
                        alert("사용자를 등록했습니다.");
                        vm.resetForm();
                        vm.userSelectList(); // 업데이트된 데이터 목록 조회

                    }
                    else {
                        alert("사용자 등록에 실패했습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("userInsert - error : ", error);
                    alert("사용자 등록에 오류가 발생했습니다.");
                });
        },


        // ID 중복 확인
        userIdDuplicateCheck: function () {
            const vm = this;
            if (!this.user.user_id) {
                alert("사용자 ID를 입력해 주세요.");
                return;
            }
            var idExp = /^[a-z0-9_\.\-]{4,20}$/
            if (!idExp.test(this.user.user_id)) {
                alert("사용자 ID는 소문자와 숫자를 포함한 4~20자리입니다.");
                return;
            }
            axios({
                url: "/userIdDuplicateCheck.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { user_id: vm.user.user_id },
            })
                .then(function (response) {
                    console.log("userIdDuplicateCheck - response : ", response.data);
                    if (response.data > 0) {
                        alert("중복된 사용자 ID입니다.");
                    } else {
                        alert("사용 가능한 사용자 ID입니다.");
                        vm.idCheckStatus = true;

                    }
                })
                .catch(function (error) {
                    console.log("userIdDuplicateCheck - error : ", error);
                    alert("중복확인에 오류가 발생했습니다.");
                });
        },


        //사용자 정보 수정

        userUpdate: function () {
            const vm = this;

            if (vm.userAuth === 'Admin' && (vm.user.user_auth === 'Admin' || vm.user.user_auth === 'Root')) {
                alert('Admin 사용자는 Root 또는 Admin 사용자를 수정할 수 없습니다.');
                return;
            }

            axios({
                url: "/userUpdate.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.user
            })
                .then(function (response) {
                    console.log("userUpdate - response : ", response.data);
                    if (response.data > 0) {
                        alert("수정했습니다.");
                        vm.userSelectList(); // 업데이트된 데이터 목록 조회
                        vm.resetButtenClick();
                    } else {
                        alert("수정에 실패했습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("userUpdate - error : ", error);
                    alert("수정에 오류가 발생했습니다.");
                });
        },


        //비밀번호 초기화 버튼
        resetpw: function () {
            if (confirm("비밀번호를 초기화 하시겠습니까?\n비밀번호가 ID와 동일하게 변경됩니다.") == false) {
                return;
            }
            const vm = this;
            axios({
                url: "/userResetPw.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.user
            })
                .then(function (response) {
                    console.log("userResetPw - response : ", response.data);
                    if (response.data > 0) {
                        alert("초기화되었습니다.");
                        vm.userSelectList(); // 업데이트된 데이터 목록 조회
                        //상세정보 초기화
                        vm.resetButtenClick();

                    } else {
                        alert("초기화에 실패했습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("userResetPw - error : ", error);
                    alert("초기화에 오류가 발생했습니다.");
                });
        },


        // 데이터 삭제
        // 선택한 정보를 저장할 목록


        // inDeleteList: function (users) {
        //     this.deleteList.push(users);
        // },

        inDeleteList: function (users) {
            const index = this.deleteList.indexOf(users);
            if (index !== -1) {
                // eqpmn_id가 이미 deleteList에 존재합니다. 이 경우 해당 항목을 삭제합니다.
                this.deleteList.splice(index, 1);
            } else {
                // eqpmn_id가 deleteList에 존재하지 않습니다. 이 경우 해당 항목을 추가합니다.
                this.deleteList.push(users);
            }
        },

        userDelete: function (index) {
            const vm = this;

            if (vm.deleteList.length > 0) {
                if (confirm("사용자를 삭제하시겠습니까?") == false) {
                    return;
                }
            } else {
                alert("사용자를 선택하세요.");
                return;
            }

            axios({
                url: "/userDelete.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { deleteList: vm.deleteList },
            })
                .then(function (response) {
                    console.log("userDelete - response : ", response.data);
                    if (response.data > 0) {
                        alert("삭제했습니다.");
                        vm.userSelectList(); // 업데이트된 데이터 목록 조회
                        //상세정보 초기화
                        vm.resetForm();

                    } else {
                        alert("삭제에 실패했습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("userDelete - error : ", error);
                    alert("삭제에 오류가 발생했습니다.");
                });
        },

        // 데이터 상세 조회
        userSelectOne: function (item, user_id) {
            this.selectedRow = item.user_id; // 선택된 row 저장

            this.isEditMode = true; // 데이터 편집 상태: 등록 -> 수정

            // 클릭한 데이터 정보를 user 객체에 할당

            this.user = Object.assign({}, item);

        },

        // 초기화 버튼을 눌렀을때
        resetButtenClick: function () {
            this.resetForm();

            this.isEditMode = false; // 데이터 편집 상태: 수정 -> 등록
        },


        // 입력된 데이터 내용 초기화 user내의 입력값 초기화
        resetForm: function () {
            Object.keys(this.user).forEach(key => {
                this.user[key] = null;
            });
        },


        // 버튼을 클릭했을때 유효성 검사
        buttonclickAction() {
            if (!this.user.user_name) {
                alert("사용자명을 입력하세요");
                return;
            }
            if (!/^[\uAC00-\uD7A3a-zA-Z\s]+$/.test(this.user.user_name)) {
                alert("사용자명은 한글과 영어만 사용 가능합니다.");
                return;
            }

            if (!this.user.user_id) {
                alert("사용자 ID를 입력하세요");
                return;
            }
            var idExp = /^[a-z0-9_\.\-]{4,20}$/
            if (!idExp.test(this.user.user_id)) {
                alert("사용자 ID는 소문자와 숫자를 포함한 4~20자리입니다.");
                return;
            }
            if (!this.isEditMode) {
                if (this.idCheckStatus != true) {
                    alert("사용자 ID 중복체크를 하세요.");
                    return;
                }
            }
            if (!this.user.user_pw) {
                alert("비밀번호를 입력하세요");
                return;
            }
            var pwExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+=-]{6,20}$/
            if (!this.isEditMode) {
                if (!pwExp.test(this.user.user_pw)) {
                    alert("비밀번호는 대소문자를 포함한 6~20자리입니다. (특수문자 포함 권장)");
                    return;
                }
            }
            if (!this.isEditMode) {
                if (this.user.user_pw != this.user.user_pw_check) {
                    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                    return;
                }
            }
            if (!this.user.user_eml) {
                alert("이메일을 입력하세요");
                return;
            }
            var emailExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/
            if (!emailExp.test(this.user.user_eml)) {
                alert("이메일 형식이 맞지 않습니다.");
                return;
            }
            if (!this.user.user_telno) {
                alert("전화번호를 입력하세요");
                return;
            }
            var phoneExp = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/
            if (!phoneExp.test(this.user.user_telno)) {
                alert("전화번호 형식이 맞지 않습니다.")
                return;
            }


            if (this.isEditMode) { // 데이터 편집 상태: 수정
                this.userUpdate(); // 데이터 수정 함수 실행
            } else { // 데이터 편집 상태: 등록
                this.userInsert(); // 데이터 등록 함수 실행
            }
        },
        //검색버튼을 클릭했을때 페이지를 1로 변경
        searchButtenClick: function () {
            this.dataListSearch.currentPage = 1;
            this.userSelectList();

        },

        //탭2의검색버튼을 클릭했을때 페이지를 1로 변경
        searchAccecssButtenClick: function () {
            this.dataListSearch.currentPage = 1;
            this.userAccessRcordSelectList();

        },



        // 접속 기록 목록 조회
        userAccessRcordSelectList: function () {
            const vm = this;
            axios({
                url: "/userAccessRcordSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.dataListSearch,
            })
                .then(function (response) {
                    console.log("dataList - response : ", response.data);
                    vm.dataList = response.data.userAccessRcordSelectList;
                    vm.dataAccessRcordSelectListCount = response.data.userAccessRcordSelectListCount;
                })
                .catch(function (error) {
                    console.log("userAccessRcordSelectList - error : ", error);
                    alert("접속 기록 목록 조회에 오류가 발생했습니다.");
                });
        },


    },
    watch: {

        //users_userid의 값이 변경되면 idCheckStatus의 값을 false로 변경
        'user.user_id': function () {
            this.idCheckStatus = false;
            console.log("ID값 변경");
        },

        //tab1에서 tab2로 이동하면 tab2의 리스트 정보 출력
        activeTab: function (newVal) {
            if (newVal === 'tab1') {
                this.dataListSearch.currentPage = 1;
                this.userSelectList();
            } else if (newVal === 'tab2') {
                this.dataListSearch.currentPage = 1;
                this.userAccessRcordSelectList();
                this.resetForm();

            }
        }

    },
    computed: {
        // 텍스트 상태가 동적으로 변환
        formTitle() {
            return this.isEditMode ? '사용자 수정' : '사용자 등록';
        },


        // 버튼 레이블을 동적으로 반환
        buttonLabel() {
            return this.isEditMode ? '수정' : '등록';
        },

        isAdmin() {
            return this.userAuth === "root";
        },

    },
    components: {
        'PageNavigation': PageNavigation,
        'PaginationButton': PaginationButton,
        'SvgIcon': SvgIcon
    },
    mounted() {
        console.log('main mounted');
        this.userSelectList();
    }
}
</script>

<style scoped>
.tabNav {
    padding: 10px 0;
}

.tabNav li a {
    background-color: var(--color-white);
    border: 1px solid var(--color-blue);
    border-radius: 5px;
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
</style>
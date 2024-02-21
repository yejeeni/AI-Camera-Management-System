<template>
    <div class="myPage-wrap container">
        <div class="myPage-content content-box justify-center align-center">
            <div class="myPage-sub content-box" v-if="pageState === 'myPage'">
                <div class="page-titleZone">
                    <h1 class="main-title">마이페이지</h1>
                </div>
                <div class="sec-wrap">
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title">아이디</p>
                        </div>
                        <div class="flex align-center info-content flex80">
                            <span>{{ user.user_id }}</span>
                        </div>
                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title ">이름</p>
                        </div>
                        <div class="flex align-center info-content flex80">
                            <span>{{ user.user_name }}</span>
                        </div>
                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title ">E-mail</p>
                        </div>
                        <div class="flex80">
                            <span>{{ user.user_eml }}</span>
                        </div>
                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title ">전화번호</p>
                        </div>
                        <div class="flex80">
                            <span>{{ user.user_telno }}</span>
                        </div>
                    </div>
                    <div class="myPage-button-wrap flex justify-end ">
                        <button class="blue-btn small-btn" @click="pageUpdate">정보수정</button>
                        <button class="gray-border-btn small-btn" @click="pagePwUpdate">비밀번호 변경</button>
                    </div>
                </div>
            </div>

            <!-- 수정하기 버튼 클릭시 나타나는 화면 -->
            <div class="myPage-sub content-box" v-if="pageState === 'myPageUpdate'">
                <div class="page-titleZone">
                    <h1 class="main-title">정보 수정</h1>

                </div>
                <div class="sec-wrap ">
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title">아이디</p>
                        </div>
                        <div class="flex align-center info-content flex80">
                            <span>{{ user.user_id }}</span>
                        </div>
                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title ">이름</p>
                        </div>
                        <div class="flex align-center info-content flex80">
                            <input type="text" class="info-input" v-model="user.user_name">
                        </div>
                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title">E-mail</p>
                        </div>
                        <div class="flex align-center info-content flex80">
                            <div class="flex justify-end">
                                <input type="text" class="info-input" v-model="user.user_eml">
                            </div>
                        </div>

                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex20">
                            <p class="box-title">전화번호</p>
                        </div>
                        <div class="flex align-center info-content flex80">
                            <input type="text" class="info-input" v-model="user.user_telno" @keyup.enter="mypageUserUpdate">
                        </div>
                    </div>
                    <div class="myPage-button-wrap flex justify-end ">
                        <button class="blue-btn small-btn" @click="pageCansle">취소</button>
                        <button class="gray-border-btn small-btn" @click="mypageUserUpdate">확인</button>
                    </div>
                </div>
            </div>

            <!-- 비밀번호 변경 버튼 클릭시 나타나는 화면 -->
            <div class="myPage-sub content-box" v-if="pageState === 'pwUpdate'">
                <div class="page-titleZone">
                    <h1 class="main-title">비밀번호 변경</h1>

                </div>
                <div class="sec-wrap ">
                    <div class="flex justify-between info-section">
                        <div class="flex35">
                            <p class="box-title">현재 비밀번호</p>
                        </div>
                        <div class="flex align-center info-content flex65">
                            <input type="password" class="info-input" v-model="user.old_pw">
                        </div>
                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex35">
                            <p class="box-title ">새 비밀번호</p>
                        </div>
                        <div class="flex align-center info-content flex65">
                            <input type="password" class="info-input" v-model="user.new_pw">
                        </div>
                    </div>
                    <div class="flex justify-between info-section">
                        <div class="flex35">
                            <p class="box-title">새 비밀번호 확인</p>
                        </div>
                        <div class="flex align-center info-content flex65">
                            <input type="password" class="info-input" v-model="user.new_pw_check"
                                @keyup.enter="myPagePwUpdate">
                        </div>
                    </div>
                    <div class="myPage-button-wrap flex justify-end ">
                        <button class="blue-btn small-btn" @click="pageCansle">취소</button>
                        <button class="gray-border-btn small-btn" @click="myPagePwUpdate">변경</button>
                    </div>
                </div>
            </div>

            <!-- 비밀번호 확인 -->
            <div class="myPage-sub content-box" v-if="pageState === 'passwordCheck'">
                <div class="page-titleZone">
                    <h1 class="main-title">비밀번호 확인</h1>
                </div>
                <div class="sec-wrap">
                    <div class="flex justify-between info-section">
                        <div class="flex30">
                            <p class="box-title">비밀번호</p>
                        </div>
                        <div class="flex70">
                            <input type="password" class="info-input" v-model="user.old_pw" @keyup.enter="checkPassword">
                        </div>
                    </div>
                    <div class="myPage-button-wrap flex justify-end ">
                        <button class="blue-btn small-btn" @click="checkPassword">확인</button>
                    </div>
                </div>
            </div>


        </div>
    </div>
</template>

<script>
import { useStore } from "vuex";
import axios from "axios";

export default {
    data() {
        return {
            show: true,
            store: useStore(),
            user: {
                user_id: null,
                user_name: null,
                user_pw: null,
                user_pw_check: null,
                user_eml: null,
                user_telno: null,
                mdfcn_dt: null,
                mdfr_id: null,
                old_pw: null,
                new_pw: null,
                new_pw_check: null,
            },

            pageState: 'passwordCheck',
        }
    },
    methods: {

        //마이페이지 페이지 이동
        pageUpdate() {
            this.userSelectOne()
            this.pageState = 'myPageUpdate'

        },
        pageCansle() {
            this.userSelectOne()
            this.pageState = 'myPage'
        },
        pagePwUpdate() {
            this.pageState = 'pwUpdate'
        },

        //세션이 없는 상태로 마이페이지 접근시
        userSelectOne: function () {
            const user_id = sessionStorage.getItem("user_id");
            const vm = this;
            axios({
                url: "/userSelectOne.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { "user_id": user_id },
            })
                .then(function (response) {
                    vm.user = response.data

                })
                .catch(function (error) {
                    console.log("dataList - error : ", error);
                });
        },

        //사용자 개인정보 수정
        userUpdate: function () {
            const vm = this;
            axios({
                url: "/MyUserUpdate.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.user
            })
                .then(function (response) {
                    //console.log("userUpdate - response : ", response.data);
                    if (confirm("정보를 수정하시겠습니까?") == false) {
                        return;
                    }
                    if (response.data > 0) {
                        alert("수정했습니다.");
                        vm.pageState = 'myPage'
                    } else {
                        alert("수정에 실패했습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("userUpdate - error : ", error);
                    alert("수정에 오류가 발생했습니다.");
                });
        },

        //사용자 비밀변호 변경시 비밀번호 체크
        userPwCheck: function () {
            const vm = this;
            axios({
                url: "/userPwCheck.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.user
            })
                .then(function (response) {
                    //console.log("userPwCheck - response : ", response.data);
                    if (response.data > 0) {
                        vm.userPwUpdate()
                        vm.resetForm()
                    } else {
                        vm.resetForm()
                        alert("비밀번호가 맞지않습니다.");
                        return;
                    }
                })
                .catch(function (error) {
                    console.log("userPwCheck - error : ", error);
                    alert("비밀번호 확인 오류");
                });
        },
        //사용자 비밀번호 업데이트
        userPwUpdate: function () {
            if (confirm("비밀변호를 변경 하시겠습니까?") == false) {
                return;
            }
            const vm = this;
            axios({
                url: "/userPwUpdate.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.user
            })
                .then(function (response) {
                    //console.log("userPwCheck - response : ", response.data);

                    if (response.data > 0) {
                        alert("비밀번호가 변경 되었습니다.");
                        vm.pageState = 'myPage'
                    } else {
                        alert("변경 실패.");
                    }
                })
                .catch(function (error) {
                    console.log("userPwUpdate - error : ", error);
                    alert("비밀번호 변경 오류");
                });
        },

        //새 비밀번호 업데이트
        myPagePwUpdate() {
            if (!this.user.old_pw) {
                alert("비밀번호를 입력해 주세요.");
                return;
            }
            if (!this.user.new_pw) {
                alert("새 비밀번호를 입력해 주세요.");
                return;
            }
            var pwExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+=-]{6,}$/
            if (!pwExp.test(this.user.new_pw)) {
                alert("새 비밀번호형식이 맞지 않습니다.");
                return;
            }
            if (this.user.new_pw == this.user.old_pw) {
                alert("새 비밀번호는 기존 비밀번호와 달라야 합니다.");
                return;
            }
            if (this.user.new_pw != this.user.new_pw_check) {
                alert("새 비밀번호 확인이 다릅니다.");
                return;
            }
            //비밀번호 유효성 검사후 비밀번호 체크
            this.userPwCheck()

        },
        //마이페이지 접근 확인을 위해 비밀번호 확인
        checkPassword: function () {
            if (!this.user.old_pw) {
                alert("비밀번호를 입력해주세요.");
                return;
            }
            const vm = this;
            axios({
                url: "/userPwCheck.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.user
            })
                .then(function (response) {
                    //console.log("userPwCheck - response : ", response.data);

                    if (response.data > 0) {
                        vm.pageState = 'myPage';
                        vm.resetForm()
                    } else {
                        alert("비밀번호가 맞지않습니다.");
                        vm.resetForm()
                    }
                })
                .catch(function (error) {
                    console.log("userPwCheck - error : ", error);
                    alert("비밀번호 확인 오류");
                });
        },

        //사용자 정보 업데이트
        mypageUserUpdate() {
            if (!this.user.user_name) {
                alert("사용자명을 입력해 주세요.");
                return;
            }
            if (!/^[\uAC00-\uD7A3a-zA-Z\s]+$/.test(this.user.user_name)) {
                alert("사용자명은 한글과 영어만 사용 가능합니다.");
                return;
            }
            if (!this.user.user_eml) {
                alert("이메일을 입력해 주세요.");
                return;
            }
            var emailExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/
            if (!emailExp.test(this.user.user_eml)) {
                alert("이메일 형식이 맞지 않습니다.");
                return;
            }
            if (!this.user.user_telno) {
                alert("전화번호를 입력해 주세요.");
                return;
            }
            var phoneExp = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/
            if (!phoneExp.test(this.user.user_telno)) {
                alert("전화번호 형식이 맞지 않습니다.")
                return;
            }
            //업데이트 함수 실행
            this.userUpdate()
        },
        //비밀번호 입력칸 초기화
        resetForm: function () {
            Object.keys(this.user).forEach(key => {
                if (key == 'old_pw' || key == 'new_pw' || key == 'new_pw_check')
                    this.user[key] = null;
            });
        },


    },
    watch: {

    },
    computed: {

    },
    components: {
    },
    mounted() {
        console.log('main mounted');
        //페이지에 접근할 시
        this.userSelectOne()
    }
}
</script>
<style>
* {
    box-sizing: border-box;
}

.myPage-content {
    width: 25%;
    margin: 0 auto;
    padding: 10rem 4rem ;
}

.sec-wrap input[type="text"] {
    border: 1px solid var(--color-gray);
}

.sec-wrap input[type="password"] {
    border: 1px solid var(--color-gray);
    min-width: 100px;
    padding: 5px 10px;
    border-radius: 5px;
    font-size: 1.3rem;
}

.myPage-content .sec-wrap {
    background-color: var(--color-white);
    border-radius: 20px;
    padding: 50px 20px;
}

.info-section {
    padding: 20px 0;
    font-size: 1.5rem;
    border-bottom: 1px solid var(--color-gray);
}

.info-section p {
    color: var(--color-blue);
}

.myPage-button-wrap {
    margin-top: 2rem;
}
</style>
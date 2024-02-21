<template>
    <div class="login-page">
        <div class="login-wrap flex-center">
            <div class="login-content">
                <div class="logo">
                    <img src="../../../resources/img/headerLogo.png" alt="">
                </div>
                <div class="login-zone">
                    <div class="input-wrap">
                        <input type="text" placeholder="아이디를 입력하세요." class="login-input" v-model="loginData.login_id" />
                        <input type="password" placeholder="비밀번호를 입력하세요." class="login-input" v-model="loginData.login_pw"
                            @keyup.enter="userLogin" />
                    </div>
                    <td class="error-message" :class="{ 'invisible': !loginFailed }">아이디 비밀번호를 확인해주세요.</td>

                    <div class="found-zone flex-center">
                        <router-link to="">아이디찾기</router-link>
                        <router-link to="">비밀번호 찾기</router-link>
                    </div>
                </div>
                <button class="blue-btn large-btn" @click="userLogin()">로그인</button>
            </div>
        </div>
    </div>
</template>

<script>

import axios from "axios";
import { useStore } from "vuex";

export default {
    data() {
        return {

            //로그인 입력 데이터
            loginData: {
                login_id: null,
                login_pw: null
            },

            //로그인시 세션 정보를 저장하는 변수
            store: useStore(),

            //로그인 상태를 표시 로그인에 실패한다면 값을 true로 변경됨
            loginFailed: false,
        }
    },
    methods: {

        //로그인 
        userLogin: function () {
            const vm = this;

            if (!vm.loginData.login_id || !vm.loginData.login_pw) {
                alert("아이디와 비밀번호를 모두 입력해주세요.");
                return;
            }

            axios({
                url: "/userLogin.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },              
                data: { user_id: vm.loginData.login_id, user_pw: vm.loginData.login_pw },
            })
                .then(function (response) {

                    sessionStorage.setItem('user_id', response.data.user_id);
                    sessionStorage.setItem('user_name', response.data.user_name);
                    sessionStorage.setItem('user_auth', response.data.user_auth);

                    if (sessionStorage.getItem('user_id') !== null && sessionStorage.getItem('user_id') == '') {
                        vm.$router.push({ path: "/login.page", query: {} });
                    } else {
                        vm.$router.push({ path: "/" }).then(() => {
                            vm.loginFailed = true;

                            // 라우팅이 완전히 처리된 후에 페이지를 강제로 새로고침합니다.
                            location.reload();
                        });
                    }
                })
                .catch(function (error) {

                    vm.loginFailed = true;
                    console.log("idcheckSearch - error : ", error);
                    if (error.response) {
                        alert("로그인에 오류가 발생했습니다.");
                    }
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
        console.log('Login mounted');
    }
}
</script>

<style scoped>
.login-page {
    width: 100%;
    height: 100%;
}

.login-wrap {
    width: 100%;
    height: 100%;
}

.login-content {
    min-width: 300px;
    max-width: 500px;
    padding: 30px;
    border: 1px solid #eee;
    border-radius: 10px;
    background-color: #fff;

    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.logo {
    width: 100%;
    margin-bottom: 30px;
}

.logo>img {
    display: block;
    margin: 0 auto;
}

.login-input {
    margin-left: 0;
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #f1f5ff;
    width: 100%;
}

.login-input:last-child {
    margin-bottom: 0;
}

.found-zone {
    padding: 30px 0 10px 0;
}

.found-zone>a {
    font-size: 1.4rem;
    padding: 0 10px;
}

.login-page .blue-btn {
    margin-left: 0;
    padding: 10px 0;
}

.error-message {
    float: right;
    font-size: 20px;
    color: red;
}
.error-message.invisible {
    visibility: hidden;
}
</style>
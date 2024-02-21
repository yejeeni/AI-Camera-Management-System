<template>
    <header>
        <div :class="className === 'side' ? 'flex-between' : ''">
            <div class="logo" :style="logoStyle">
                <a href="/">
                    <img src="../../resources/img/headerLogo.png" alt="BI MANAGER LOGO">
                </a>
            </div>
            <div class="user-info flex justify-end align-center">
                <div class="sms">
                    <svg-icon type="mdi" v-if="userId" :path="mdiEmail" color="#213f99"></svg-icon>
                </div>
                <div class="user flex align-center" @click="goToMyPage()">
                    <div class="user-img">
                        <svg-icon type="mdi" v-if="userId" :path="mdiAccountCircle" color="#213f99"></svg-icon>
                    </div>
                    <!-- <span class="user-name">{{ store.state.loginUser.user_id }}님</span> -->
                    <span class="user-name" v-if="userId">{{ userName }}님</span>

                </div>
                <!-- <button class="logout-btn" @click="userLogout()">로그아웃</button> -->
                <button class="logout-btn " v-if="userId" @click="logout()">로그아웃</button>
            </div>
        </div>
    </header>
</template>

<script>
import axios from "axios";
import SvgIcon from '@jamescoyle/vue-icon'
import { useStore } from "vuex";
import { mdiEmail, mdiAccountCircle } from '@mdi/js'
export default {
    props: {
        className: String
    },
    data() {
        return {
            mdiEmail: mdiEmail,
            mdiAccountCircle: mdiAccountCircle,
            store: useStore(),
            userId: sessionStorage.getItem('user_id') || null,
            userName: sessionStorage.getItem('user_name') || null,

        }
    },
    methods: {
        //로그아웃
        logout: function () {
            if (!confirm("로그아웃 하시겠습니까?")) {
                return;
            }
            const vm = this;
            console.log("로그아웃 하려는 정보 조회")
            axios({
                url: "/userLogout.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { user_id: vm.userId },
            })
                .then(function (response) {
                    console.log("response.data", response.data)
                    sessionStorage.removeItem("user_id");
                    sessionStorage.removeItem("user_name");
                    // vm.$router.push({ path: "/login.page", query: {} });

                    vm.$router.push({ path: "/login.page", query: {} }).then(() => {
                        vm.loginFailed = true;

                        // 라우팅이 완전히 처리된 후에 페이지를 강제로 새로고침합니다.
                        location.reload();
                    });
                })
                .catch(function (error) {
                    alert("로그아웃에 실패했습니다. 다시 시도해주세요.")
                });
        },
        goToMyPage() {
            this.$router.push("/mypage.page");
        },
    },
    watch: {

    },
    computed: {
        logoStyle() {
            if (this.className === 'top') {
                return {
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%,-50%)'
                }
            } else {
                return {}
            }
        }
    },
    components: {
        'SvgIcon': SvgIcon
    },
    mounted() {
        console.log('Header mounted');

        // var item = JSON.parse(localStorage.getItem('user_id'));
        // console.log("header item check", item)
        // if (item !== null) {
        //     var now = new Date();
        //     // 만료되었으면
        //     if (now.getTime() > item.expiry) {
        //         console.log("now.getTime()", now.getTime())
        //         // 항목 삭제
        //         localStorage.removeItem('user_id');
        //     }
        // }
    }
}
</script>

<style>
.user {
    display: flex;
    align-items: center;
    cursor: pointer;
}
</style>
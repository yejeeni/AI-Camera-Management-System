<template>
    <nav class="top-menu" v-if="userId">
        <ul class="main-menu">
            <li v-for="(mainMenu, idx) in menuList" :key="idx" @mouseover="showAllSubMenus" @mouseout="hideAllSubMenus">
                <router-link :to="mainMenu.path" v-if="mainMenu.path" class="depth1"
                    :class="{ active: isMainMenuActive(mainMenu) }">{{ mainMenu.pathName }}</router-link>
                <p class="depth1" v-else :class="{ active: isMainMenuActive(mainMenu) }">{{ mainMenu.pathName }}</p>
                <ul v-if="mainMenu.subMenu" class="sub-menu"
                    :style="subShow ? 'max-height:500px; opacity:1' : 'max-height:0px; opacity:0'">
                    <li v-for="(subMenu, idx) in mainMenu.subMenu" :key="idx" :class="{ active: isSubMenuActive(subMenu) }">
                        <router-link :to="subMenu.path">{{ subMenu.pathName }}</router-link>
                    </li>
                </ul>
            </li>
        </ul>
        <div class="navbg" :style="subShow ? `height: ${maxSubMenuHeight}px` : `height: 0px`"></div>
    </nav>
</template>
 
<script>
import { ResetControl } from "@amcharts/amcharts5/.internal/charts/stock/toolbar/ResetControl";
import { useStore } from "vuex";
export default {
    data() {
        return {
            store: useStore(),
            userId: sessionStorage.getItem('user_id') || null,
            userAuth: sessionStorage.getItem('user_auth') || null,

            menuList: [],
            currentRoute: null,
            subShow: false
        }
    },
    methods: {
        showAllSubMenus() {
            this.subShow = true;
        },
        hideAllSubMenus() {
            this.subShow = false;
        },

        isMainMenuActive(mainMenu) {
            return this.currentRoute && (this.currentRoute.path === mainMenu.path || this.isSubMenuActive(mainMenu.subMenu));
        },

        isSubMenuActive(subMenu) {
            if (!subMenu) return false;
            for (let i = 0; i < subMenu.length; i++) {
                if (this.currentRoute.path === subMenu[i].path) {
                    return true;
                }
            }
            return false;
        },
    },
    watch: {
        $route(to, from) {
            this.currentRoute = to;
        },
    },
    computed: {
        maxSubMenuHeight: function () {
            let maxSubItems = Math.max(...this.menuList.map(menu => {
                return menu.subMenu ? menu.subMenu.length : 0;
            }));
            return maxSubItems * 48; // 48px는 각 메뉴 항목의 높이로 가정
        }
    },
    components: {

    },
    mounted() {
        this.menuList = [
            { path: "/", pathName: "메인" },
            {
                pathName: "실시간 관제",
                subMenu: [
                    { path: "/control.page", pathName: "실시간 현황 관제" },
                    { path: "/detections.page", pathName: "상황 발생 조회" },
                    { path: "/troblList.page", pathName: "장비 장애 조회" }
                ]
            },
            {
                pathName: "장비관리",
                subMenu: [
                    { path: "/equipment.page", pathName: "장비관리" },
                    // { path: "/equipment.page", pathName: "장비 등록/수정" }
                ]
            },
            {
                pathName: "통계분석",
                subMenu: [
                    { path: "/statistics.page", pathName: "통계분석" }
                ]
            },

            //{
            //     pathName: "템플릿",
            //     subMenu: [
            //         { path: "/searchbar.page", pathName: "서치바" },
            //         { path: "/table.page", pathName: "테이블" },
            //         { path: "/btnPosition.page", pathName: "버튼별 위치" },
            //         { path: "/formModal.page", pathName: "form modal" },
            //         { path: "/alertModal.page", pathName: "alert modal" },
            //         { path: "/icon.page", pathName: "icon" }
            //     ]
            // },
            // {
            //     pathName: "레이아웃 템플릿",
            //     subMenu: [
            //         { path: "/vertical.page", pathName: "수직 레이아웃" },
            //         { path: "/horizontal.page", pathName: "수평 레이아웃" }
            //     ]
            // },
            // {
            //     pathName: "가이드",
            //     subMenu: [
            //         { path: "/guide.page", pathName: "가이드" }
            //     ]
            // },
        ];
        //유저의 권한이 root, Admin일 경우에만 메뉴 표시
        if (this.userAuth === 'Admin' || this.userAuth === 'root') {
            this.menuList.push({
                pathName: "시스템관리",
                subMenu: [
                    { path: "/user.page", pathName: "사용자관리" }
                ]
            });
        }

        console.log('Menu mounted');
        
    }
}
</script>
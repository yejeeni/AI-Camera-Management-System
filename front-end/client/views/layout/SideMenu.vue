<template>
    <nav class="side-menu">
        <ul class="main-menu">
            <li v-for="(mainMenu, idx) in menuList" :key="idx" @click="toggleMenu(mainMenu)">
                <div class="flex-between">
                    <p>{{ mainMenu.pathName }}</p>
                    <svg-icon v-if="mainMenu.subMenu" type="mdi" :path="mainMenu.path" color="#fff"></svg-icon>
                </div>
                <ul v-if="mainMenu.subMenu" class="sub-menu" :style="{ 'max-height': mainMenu.isOpen ? '360px' : '0' }">
                    <li v-for="(subMenu, idx) in mainMenu.subMenu" :key="idx">
                        <router-link :to="subMenu.subPath">{{ subMenu.pathName }}</router-link>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>
</template>
 
<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMenuUp, mdiMenuDown } from '@mdi/js';
export default {
    data() {
        return {
            menuList: [
                // { path: "/", pathName: "Dashboard", },
                {
                    mainPath: "/", pathName: "데이터관리",
                    subMenu: [
                        { subPath: "/fileManagement.page", pathName: "파일관리" },
                        { subPath: "/hostManagement.page", pathName: "호스트관리" },
                        { subPath: "/", pathName: "데이터셋 목록" },
                        { subPath: "/", pathName: "데이터 공유 관리" },
                    ],
                    isOpen: false,
                    path: mdiMenuUp,
                },
                {
                    mainPath: "/", pathName: "메타관리",
                    subMenu: [
                        { subPath: "/", pathName: "데이터 메타 정보" },
                        { subPath: "/", pathName: "표준 용어 관리" },
                    ],
                    isOpen: false,
                    path: mdiMenuUp,
                },
                {
                    mainPath: "/", pathName: "작업관리",
                    subMenu: [
                        { subPath: "/scheduleManagement.page", pathName: "작업 스케줄 관리" },
                        { subPath: "/scheduleLogManagement.page", pathName: "로그 관리" },
                    ],
                    isOpen: false,
                    path: mdiMenuUp,
                },
                {
                    mainPath: "/", pathName: "데이터활용",
                    subMenu: [
                        { subPath: "/", pathName: "데이터 활용 관리" },
                        { subPath: "/", pathName: "데이터 활용 공유 관리" },
                        { subPath: "/", pathName: "GIS데이터 관리" },
                        { subPath: "/", pathName: "데이터현황 관리" },
                        { subPath: "/", pathName: "OpenAPI 목록" },
                    ],
                    isOpen: false,
                    path: mdiMenuUp,
                },
                {
                    mainPath: "/", pathName: "통합관리",
                    subMenu: [
                        { subPath: "/", pathName: "사용자관리" },
                        { subPath: "/", pathName: "부서관리" },
                        { subPath: "/", pathName: "연계정보관리" },
                    ],
                    isOpen: false,
                    path: mdiMenuUp,
                },
                {
                    mainPath: "/", pathName: "정보관리",
                    subMenu: [
                        { subPath: "/", pathName: "내정보관리" },
                        { subPath: "/", pathName: "비밀번호 변경" },
                        { subPath: "/", pathName: "부서메일" },
                    ],
                    isOpen: false,
                    path: mdiMenuUp,
                },
                { mainPath: "/", pathName: "ai solution", path: mdiMenuUp, },
            ],

        }
    },
    methods: {
        toggleMenu: function (menu) {
            this.menuList.forEach(item => {
                if (item === menu) {
                    item.isOpen = !menu.isOpen;
                    item.path = item.isOpen ? mdiMenuDown : mdiMenuUp

                } else{
                    item.isOpen = false;
                    item.path = mdiMenuUp;
                }
            });
        },
    },
    watch: {

    },
    computed: {

    },
    components: {
        'SvgIcon': SvgIcon
    },
    mounted() {
        console.log('Menu mounted');
    }
}
</script>
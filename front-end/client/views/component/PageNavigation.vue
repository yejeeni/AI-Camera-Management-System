<template>
    <p class="navigate_bar"><router-link to="/">Home</router-link><span> > {{ pathName() }}</span></p>
</template>

<script>
import { useRoute } from 'vue-router';
export default {
    data() {
        return {
            //현재 라우터의 정보
            route: useRoute(),

            // 페이지 경로 목록
            pageList: [
                { path: "/", name: "대시보드", },
                { path: "/fileManagement.page", name: "파일 관리", },
                { path: "/hostManagement.page", name: "호스트 관리", },
                { path: "/scheduleManagement.page", name: "작업 스케줄 관리", },
                { path: "/scheduleLogManagement.page", name: "로그 관리", },
                { path: "/objectDetectionList.page", name: "객체 인식 조회", },
            ],
        }
    },
    methods: {
        pathName: function () {
            for (let i = 0; i < this.pageList.length; i++) {
                if (this.route.path == this.pageList[i]['path']) {
                    return this.pageList[i]['name'];
                }
            }
            return "대시보드"
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
    }
}
</script>

<style scoped>
.navigate_bar {
    padding: 10px 0px;
    font-size: 1.4rem;
}
</style>

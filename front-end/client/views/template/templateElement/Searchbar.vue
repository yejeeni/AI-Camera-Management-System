<template>
    <div class="content">
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">기본 Search Bar</p>
            <div style="padding:30px 0">
                <DefaultSearchBar />
            </div>
        </div>
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">상세 Search Bar</p>
            <div>
                <DetailSearchBar />
            </div>
        </div>
    </div>
</template>

<script>
import DefaultSearchBar from './DefaultSearchBar.vue';
import DetailSearchBar from './DetailSearchBar.vue';
export default {
    data() {
        return {

        }
    },
    methods: {

    },
    watch: {

    },
    computed: {

    },
    components: {
        'DefaultSearchBar': DefaultSearchBar,
        'DetailSearchBar': DetailSearchBar
    },
    mounted() {
    }
}
</script>

<style scoped>
/* */
.box {
    padding: 50px;
    border: 1px solid #eee;
    margin-bottom: 30px;
    background-color: #fff;
}
</style>
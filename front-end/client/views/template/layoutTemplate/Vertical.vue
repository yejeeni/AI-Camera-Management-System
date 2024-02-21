<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">페이지 제목을 적어주세요</p>
            <PageNavigation />
        </div>
        <div class="content-box flex justify-between">
            <div class="left-content flex20">
                <div class="flex-column justify-between">
                    <div class="host-zone flex20">
                        <div class="content-titleZone">
                            <p class="box-title">호스트 선택</p>
                        </div>
                        <select name="" id="" class="full-select">
                            <option value="">172.0.0.1</option>
                        </select>
                    </div>
                    <div class="file-zone flex80">
                        <div class="content-titleZone flex justify-between">
                            <p class="box-title">폴더 리스트</p>
                            <button class="darkg-border-btn small-btn">폴더추가</button>
                        </div>
                        <div class="content-zone">
                        </div>
                    </div>
                </div>
            </div>
            <div class="right-content flex80">
                <div class="searchbar-zone">
                    <div class="flex justify-end align-center">
                        <input type="date" name="" id="search-date" class="square-date"
                            :class="{ 'date-placeholder': startDate }" data-placeholder="날짜 선택" @change="handleDateChange">
                        <span class="coupler">~</span>
                        <input type="date" name="" id="search-date" class="square-date"
                            :class="{ 'date-placeholder': endDate }" data-placeholder="날짜 선택" @change="handleDateChange">
                        <select name="" id="" class="square-select">
                            <option value="all">전체</option>
                        </select>
                        <div class="search-square">
                            <input type="text" class="square-input" placeholder="Search">
                            <button class="square-button">
                                <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="flex-column">
                    <div class="table-zone">
                        <div class="list-info flex justify-between align-center">
                        <div class="count-zone">
                            <p>총 <span>40</span>건 중 <span>01</span>건 선택</p>
                        </div>
                        <div class="cunt-selectZone">
                            <select name="" id="">
                                <option value="">10개 보기</option>
                                <option value="">20개 보기</option>
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
                                    <th>No</th>
                                    <th>user ID</th>
                                    <th>이름</th>
                                    <th>생년월일</th>
                                    <th>성별</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>test123</td>
                                    <td>홍길동</td>
                                    <td>1999.01.01</td>
                                    <td>여</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <PaginationButton />
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
export default {
    data() {
        return {
            searchPath: mdiMagnify,
            startDate:true,
            endDate:true
        }
    },
    methods: {
        handleDateChange:function() {
           if(this.startDate){
            this.startDate = false
           }else if(this.endDate){
            this.endDate = false
           }
        } 
    },
    watch: {

    },
    computed: {

    },
    components: {
        'PageNavigation': PageNavigation,
        'SvgIcon': SvgIcon,
        'PaginationButton': PaginationButton
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>

<style lang="css" scoped>.content-zone {
    border: 1px solid #eee;
    padding: 10px;
    height: calc(100% - 55px);
    overflow: auto;
}</style>
<template>
    <div class="container">
        <div class="page-titleZone flex justify-between">
            <p class="main-title">페이지 제목을 적어주세요</p>
            <PageNavigation />
        </div>
        <div class="content">
            <div class="row">
                <div class="content-titleZone">
                    <p class="box-title">목록</p>
                </div>
                <div class="searchbar-zone">
                    <div class="flex justify-end align-center">
                        <span class="option-title">검증유형</span>
                        <select name="" id="" class="square-select">
                            <option value="all">전체</option>
                        </select>
                        <span class="option-title">품질지표명</span>
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
                            <col style="width: ;">
                        </colgroup>
                        <thead>
                            <tr>
                                <th><input type="checkbox" name="" id=""></th>
                                <th>No</th>
                                <th>user ID</th>
                                <th>이름</th>
                                <th>생년월일</th>
                                <th>성별</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="" id=""></td>
                                <td>1</td>
                                <td>test123</td>
                                <td>홍길동</td>
                                <td>1999.01.01</td>
                                <td>여</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="flex justify-end">
                    <button class="red-border-btn small-btn">선택항목 삭제</button>
                </div>
                <PaginationButton />
            </div>
            <div class="row form-box">
                <div class="content-titleZone">
                    <p class="box-title">등록</p>
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
                                <th>user ID</th>
                                <td><input type="text" name="" id="" class="full-input"></td>
                                <th>이름</th>
                                <td><input type="text" name="" id="" class="full-input"></td>
                            </tr>
                            <tr>
                                <th>생년월일</th>
                                <td>
                                    <div class="flex justify-between ">
                                        <select name="" id="" class="flex30"></select>
                                        <select name="" id="" class="flex30"></select>
                                        <select name="" id="" class="flex30"></select>
                                    </div>
                                </td>
                                <th>성별</th>
                                <td>
                                    <select name="" id="" class="full-select"></select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="flex justify-end">
                    <button class="blue-border-btn small-btn">등록</button>
                    <!-- 값이 있을때 수정 버튼으로 변경 -->
                    <!-- <button class="orange-border-btn small-btn">수정</button> -->
                    <button class="darkg-border-btn small-btn">초기화</button>
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
            inputValue: null
        }
    },
    methods: {

    },
    watch: {

    },
    computed: {

    },
    components: {
        'PageNavigation': PageNavigation,
        'PaginationButton': PaginationButton,
        'SvgIcon': SvgIcon
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>

<style scoped></style>
<template>
    <div class="content">
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">타이틀 옆 버튼 위치</p>
            <!-- 여기서부터 복사 -->
            <div class="flex justify-between align-center">
                <p class="box-title">폴더 리스트</p>
                <button class="darkg-border-btn small-btn">폴더추가</button>
            </div>
        </div>
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">다운로드버튼 및 crud 한페이지에서 이루어지는 page의 조회 및 삭제</p>
            <!-- 여기서부터 복사 -->
            <div class="flex justify-end">
                <button class="green-border-btn small-btn">파일 다운로드</button>
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
                </tbody>
            </table>
        </div>
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">조건 검색과 같이 들어가는 버튼</p>
            <!-- 여기서부터 복사 -->
            <div class="flex justify-between align-center">
                <div class="flex20">
                    <button class="darkg-border-btn small-btn">선택이동</button>
                    <button class="darkg-border-btn small-btn">선택복사</button>
                    <button class="darkg-border-btn small-btn">선택삭제</button>
                </div>
                <div class="flex justify-end flex80">
                    <div class="search-bar">
                        <div class="flex justify-end align-center">
                            <input type="date" name="" id="" class="square-date">
                            <span class="coupler">~</span>
                            <input type="date" name="" id="" class="square-date">
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
                </tbody>
            </table>
        </div>
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">등록</p>
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
                </tbody>
            </table>
            <!-- 여기서부터 복사 -->
            <div class="flex justify-end">
                <button class="darkg-btn small-btn">등록</button>
            </div>
        </div>
        <div class="box">
            <p style="font-size:2rem; font-weight:800; margin-bottom:10px">수정 삭제 목록</p>
            <table class="form-table">
                <colgroup>
                    <col style="width: ;">
                    <col style="width: ;">
                    <col style="width: ;">
                    <col style="width: ;">
                </colgroup>
                <tbody>
                    <tr>
                        <th>user ID</th>
                        <td><input type="text" name="" id=""></td>
                        <th>이름</th>
                        <td><input type="text" name="" id=""></td>
                    </tr>
                    <tr>
                        <th>생년월일</th>
                        <td>
                            <div class="flex justify-between">
                                <select name="" id="" class="flex30"></select>
                                <select name="" id="" class="flex30"></select>
                                <select name="" id="" class="flex30"></select>
                            </div>
                        </td>
                        <th>성별</th>
                        <td>
                            <select name="" id=""></select>
                        </td>
                    </tr>
                </tbody>
            </table>
            <!-- 여기서부터 복사 -->
            <div class="flex justify-end">
                <button class="red-border-btn small-btn">삭제</button>
                <button class="darkg-border-btn small-btn">목록</button>
                <button class="green-border-btn small-btn">수정</button>
            </div>
        </div>
    </div>
</template>

<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
export default {
    data() {
        return {
            searchPath: mdiMagnify
        }
    },
    methods: {

    },
    watch: {

    },
    computed: {

    },
    components: {
        'SvgIcon': SvgIcon
    },
    mounted() {
    }
}
</script>

<style scoped>
.box {
    padding: 50px;
    border: 1px solid #eee;
    margin-bottom: 30px;
}
</style>
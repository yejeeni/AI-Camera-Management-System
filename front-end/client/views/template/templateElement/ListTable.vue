<template>
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
            <!-- col 꼭 너비 기재해야함! 그래야 100%로 차지함 -->
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
                <tr v-if="postList === 0">
                    <td colspan="5" class="no-list">검색조건에 해당하는 데이터가 없습니다.</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

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
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>

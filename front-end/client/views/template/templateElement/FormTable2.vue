<template>
    <div class="table-zone">
        <table class="form-table2">
                        <!-- col 꼭 너비 기재해야함! 그래야 100%로 차지함 -->
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


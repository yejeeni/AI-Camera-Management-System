<template>
    <div v-show="isModalOpen" class="modal-wrapper">
        <div class="modal-container">
            <div class="modal-title">
                <div class="flex justify-between align-center">
                    <h2>센서 알람 설정</h2>
                    <button class="close-btn" @click="closeModal">X</button>
                </div>
            </div>
            <div class="modal-content-monthly">
                <div class="table-zone">
                    <table class="list-table">
                        <!-- col 꼭 너비 기재해야함! 그래야 100%로 차지함 -->
                        <colgroup>
                            <col style="width: 22.5%;">
                            <col style="width: 22.5%;">
                            <col style="width: 22.5%;">
                            <col style="width: 22.5%;">
                            <col style="width: 10%;">
                        </colgroup>
                        <thead>
                            <tr>
                                <th>컬럼명</th>
                                <th>한글명</th>
                                <th>MIN</th>
                                <th>MAX</th>
                                <th>사용여부</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>SENS1</td>
                                <td><input type="text" name="" id="" class="full-input" value="벨브1"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="checkbox" name="" id=""></td>
                            </tr>
                            <tr>
                                <td>SENS2</td>
                                <td><input type="text" name="" id="" class="full-input" value="벨브2"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="checkbox" name="" id=""></td>
                            </tr>
                            <tr>
                                <td>SENS3</td>
                                <td><input type="text" name="" id="" class="full-input" value="벨브3"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="checkbox" name="" id=""></td>
                            </tr>
                            <tr>
                                <td>SENS4</td>
                                <td><input type="text" name="" id="" class="full-input" value="벨브4"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="checkbox" name="" id=""></td>
                            </tr>
                            <tr>
                                <td>SENS5</td>
                                <td><input type="text" name="" id="" class="full-input" value="벨브5"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="checkbox" name="" id=""></td>
                            </tr>
                            <tr>
                                <td>SENS6</td>
                                <td><input type="text" name="" id="" class="full-input" value="벨브6"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="text" name="" id="" class="full-input"/></td>
                                <td><input type="checkbox" name="" id=""></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-end flex justify-end">
                <button v-show="modalType === 'form-modal'" class="orange-btn small-btn">접속</button>
                <button v-show="modalType === 'tab-modal'" class="blue-btn small-btn">확인</button>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            isModalOpen: true,
            activeTab: 'tab1',
            modalType: 'tab-modal'
        }
    },
    methods: {
        // 탭 변경
        showTab: function (tabName) {
            this.activeTab = tabName;
        },
        closeModal: function () {
            this.isModalOpen = false;
        }
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
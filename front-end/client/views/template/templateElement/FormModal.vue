<template>
    <div v-show="isModalOpen" class="modal-wrapper">
        <div class="modal-container">
            <div class="modal-title">
                <div class="flex justify-between align-center">
                    <h2>모달 제목</h2>
                    <button class="close-btn" @click="closeModal">X</button>
                </div>
            </div>
            <div class="modal-content-monthly" v-show="modalType == 'tab-modal'">
                <ul class="tab-nav flex justify-between">
                    <li @click="showTab('tab1')" class="flex50"><a href="#tab01"
                            :class="{ activeTab: activeTab === 'tab1' }">탭정보1</a></li>
                    <li @click="showTab('tab2')" class="flex50"><a href="#tab02"
                            :class="{ activeTab: activeTab === 'tab2' }">탭정보2</a></li>
                </ul>
                <ul class="tab-content">
                    <li id="tab01" v-show="activeTab === 'tab1'">
                        <table class="form-table">
                            <colgroup>
                                <col style="width: 15%;" />
                                <col style="width: 85%;" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>파일명</th>
                                    <td></td>
                                </tr>
                                <tr>
                                    <th>API URL</th>
                                    <td></td>
                                </tr>
                                <tr>
                                    <th>결과타입</th>
                                    <td>
                                        <input type="radio" name="result-type" id="json" class="chekck-type" checked>
                                        <label for="json" class="chekcktype-label">
                                            json
                                        </label>
                                        <input type="radio" name="result-type" id="xml" class="chekck-type">
                                        <label for="xml" class="chekcktype-label">
                                            xml
                                        </label>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <table class="list-table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>key</th>
                                    <th>value</th>
                                    <th>예외</th>
                                    <th>날짜</th>
                                    <th>삭제</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>04:00:00</td>
                                    <td>접속성공</td>
                                    <td>접속성공</td>
                                    <td>접속성공</td>
                                    <td>접속성공</td>
                                </tr>
                            </tbody>
                        </table>
                    </li>
                    <li id="tab01" v-show="activeTab === 'tab1'">
                    </li>
                </ul>
            </div>
            <div class="modal-content-monthly" v-show="modalType == 'form-modal'">
                <table class="form-table">
                    <colgroup>
                        <col style="width: 15%;" />
                        <col style="width: 85%;" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>제목</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>DBMS</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>호스트</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>PORT</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>DB명</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>접속ID</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>접속PW</th>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
                <div class="flex justify-end">
                    <button class="blue-btn small-btn">접속 테스트</button>
                </div>
                <table class="list-table orange">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>접속시간</th>
                            <th>접속결과</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>04:00:00</td>
                            <td>접속성공</td>
                        </tr>
                    </tbody>
                </table>
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
            // modalType: 'form-modal',
             modalType: 'tab-modal'
        }
    },
    methods: {
        // 탭 변경
        showTab: function (tabName) {
            this.activeTab = tabName;
        },
        closeModal:function(){
            this.isModalOpen=false;
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
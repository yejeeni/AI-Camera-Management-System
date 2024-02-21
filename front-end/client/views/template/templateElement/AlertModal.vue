<template>
    <div v-show="isModalOpen" class="modal-wrapper">
        <div class="modal-container small-modal">
            <div class="modal-title text-ct">
                <h2>{{title}}</h2>
            </div>
            <div class="modal-content-monthly">
                <p class="alert-write text-ct"  v-html="message">                   
                </p>
            </div>
            <div class="modal-end flex justify-center">
                <button class="gray-btn large-btn flex50" id="confirmCancle" @click="closeModal" v-show="confirmAt">취소</button>
                <button class="blue-btn large-btn flex50" id="confirmOk" @click="closeModal">확인</button>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    props: {
        title : {
            type : String,
            default : '모달 제목'
        },
        message : {
            type : String,
            default : '경고 메세지를 입력해주세요. <br /> 삭제,수정,추가 등등'
        },
    },
    data() {
        return {
            isModalOpen: false,
            activeTab: 'tab1',
            modalType: 'tab-modal',
            title : this.title,
            message : this.message,
            confirmAt : false
        }
    },
    methods: {
        // 탭 변경
        showTab: function (tabName) {
            this.activeTab = tabName;
        },

        // 닫기
        closeModal:function(){
            this.isModalOpen = false;
        },

        // 모달 호출
        showModal : function(){
            
            this.confirmAt = false;
            this.isModalOpen = true;
            document.getElementById("confirmOk").focus()
        },

        // confirm 호출
        showConfirm : async function(){
            this.confirmAt = true;
            this.isModalOpen = true;   
            document.getElementById("confirmOk").focus()
            const promise = new Promise((resolve, reject) => {               
                document.getElementById("confirmCancle").addEventListener("click", async () => {   
                    resolve('cancle')
                });
    
                document.getElementById("confirmOk").addEventListener("click", async () => {                   
                    resolve('ok')                 
                });
            });

            return promise.then(
                (id) => {         
                    if(id == 'cancle'){
                        return false;  
                    }else if(id =='ok'){
                        return true;          
                    }
                 
                }     
            );
        },

        setTitle : function(Title){
            this.title = Title;
        },

        setMessage : function(message){
            this.message = message;
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
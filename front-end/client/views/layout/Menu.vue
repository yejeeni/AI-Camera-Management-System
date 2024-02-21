<template>
    <div>
        메뉴 : 
        <router-link to="/"><span>Main</span></router-link>
        ,
        <router-link to="/main2"><span>Main2</span></router-link>
    </div>
</template>
 
<script>
export default {
    data () {
        return {
        }
    },
    methods: {

    },
    watch: {

    },
    computed: {

    },
    mounted() {
        console.log('Menu mounted');
    }
}
</script>
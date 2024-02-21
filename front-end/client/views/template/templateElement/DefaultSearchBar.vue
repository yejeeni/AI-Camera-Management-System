<template>
    <div class="search-bar">
        <div class="flex justify-end align-center">
            <input type="date" name="" id="search-date" class="square-date" :class="{ 'date-placeholder': startDate }" data-placeholder="날짜 선택" @change="handleDateChange">
            <span class="coupler">~</span>
            <input type="date" name="" id="search-date" class="square-date" :class="{ 'date-placeholder': endDate }" data-placeholder="날짜 선택" @change="handleDateChange">
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
</template>

<script>
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
        'SvgIcon': SvgIcon
    },
    mounted() {
        console.log('main mounted');
    }
}
</script>

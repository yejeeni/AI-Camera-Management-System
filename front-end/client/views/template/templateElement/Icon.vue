<template>
    <div class="icon-zone">
        <p class="flex justify-center align-center state green"><svg-icon type="mdi" :width="21" :height="21" :path="checkPath"></svg-icon> <span>성공</span></p>
        <p class="flex justify-center align-center state red"><svg-icon type="mdi" :width="21" :height="21" :path="xPath"></svg-icon> <span>실패</span></p>
        <p class="flex justify-center align-center state orange"><img src="../../../resources/img/loading_mini_icon.png" alt=""> <span>진행중</span></p>
    </div>
</template>

<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiCheckCircle, mdiCloseCircle } from '@mdi/js';
export default {
    data() {
        return {
            checkPath: mdiCheckCircle,
            xPath: mdiCloseCircle,

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
        console.log('main mounted');
    }
}
</script>

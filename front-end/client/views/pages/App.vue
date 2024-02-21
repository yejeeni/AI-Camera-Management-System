<template>
   <div v-cloak :class="layoutType === 'side' ? 'dashboard-wrap' : 'layout-wrap'">
      <Header :className="layoutType" />
      <SideMenu v-if="layoutType === 'side'" />
      <TopMenu v-else />
      <div class="main">
         <PageNavigation v-if="layoutType === 'side'" />
         <router-view />
      </div>
   </div>
</template>

<script>
import Header from '../layout/Header.vue';
import SideMenu from '../layout/SideMenu.vue';
import TopMenu from '../layout/TopMenu.vue';
import PageNavigation from '../component/PageNavigation.vue';
import { useStore } from "vuex";
const App = {
   data: () => {
      return {
         // side혹은 top 둘중 한개 타입
         layoutType: "top",
         store: useStore()
      }
   },
   methods: {

   },
   watch: {

   },
   computed: {

   },
   components: {
      'Header': Header,
      'SideMenu': SideMenu,
      'TopMenu': TopMenu,
      'PageNavigation': PageNavigation,
   },
   mounted: () => {
      console.log('Vue mounted');
   }
}

export default App;
</script>

<style scoped>
[v-cloak] {
   display: none;
}

.main-warp {
   border: 1px solid #000;
   margin: 20px 0px;
}
</style>

import { createWebHistory, createRouter } from "vue-router";

import Main from "../pages/main/Main.vue";
import Control from "../pages/control/Control.vue";
import Ecuipment from "../pages/ecuipment/ecuipment.vue";
import Statistics from "../pages/statistics/statistics.vue";
import System from "../pages/system/System.vue";
import Mypage from "../pages/mypage/Mypage.vue"
import Mypagesub from "../pages/mypage/Mypagesub.vue"
import Login from "../pages/login/Login.vue";
import ObjectDetectionList from "../pages/statusControl/ObjectDetectionList.vue";
import TroblList from "../pages/trobl/TroblList.vue";
import User from "../pages/system/User.vue";



// 템플릿 
import Searchbar from "../template/templateElement/Searchbar.vue";
import Table from "../template/templateElement/Table.vue";
import BtnPosition from "../template/templateElement/BtnPosition.vue";
import Horizontal from "../template/layoutTemplate/Horizontal.vue";
import Vertical from "../template/layoutTemplate/Vertical.vue";
import FormModal from "../template/templateElement/FormModal.vue"
import ListModal from "../template/templateElement/ListModal.vue"
import AlertModal from "../template/templateElement/AlertModal.vue"
import Icon from "../template/templateElement/Icon.vue"
import Guide from "../template/guide/TemplateGuide.vue";

const routes = [
  /* 메인화면 */
  { path: "/", name: "Main", component: Main },
  {path: "/control.page",name: "Control",component: Control},
  {path: "/equipment.page",name: "Ecuipment",component: Ecuipment},
  {path: "/statistics.page",name: "Statistics",component: Statistics},
  {path: "/system.page",name: "System",component: System},
  {path: "/login.page",name: "Login",component: Login,},
  {path: "/mypage.page",name: "Mypage",component: Mypage,},
  {path: "/mypagesub.page",name: "Mypagesub",component: Mypagesub,},  
  {path: "/detections.page",name: "detections",component: ObjectDetectionList},
  {path: "/troblList.page",name: "troblList",component: TroblList},
  {path: "/user.page",name: "User",component: User,},




  // 템플릿 화면
  {path: "/searchbar.page",name: "Searchbar",component: Searchbar,},
  {path: "/table.page",name: "Table",component: Table,},
  {path: "/btnPosition.page",name: "BtnPosition",component: BtnPosition,},
  {path: "/horizontal.page",name: "Horizontal",component: Horizontal,},
  {path: "/vertical.page",name: "Vertical",component: Vertical,},
  {path: "/formModal.page",name: "FormModal",component: FormModal,},
  {path: "/listModal.page",name: "ListModal",component: ListModal,},
  {path: "/alertModal.page",name: "AlertModal",component: AlertModal,},
  {path: "/icon.page",name: "Icon",component: Icon,},
  {path: "/guide.page",name: "Guide",component: Guide,},
];

const AppRouter = createRouter({
  history: createWebHistory(),
  routes,
});

AppRouter.beforeEach((to, from, next) => {
  // 세션 스토리지에서 사용자 ID를 가져옵니다.
  const userId = sessionStorage.getItem('user_id');

  // 사용자 ID가 없고, 이동하려는 페이지가 로그인 페이지가 아닐 때 로그인 페이지로 이동합니다.
  if (!userId && to.path !== '/login.page') {
    next('/login.page');
  }  else if (userId && to.path === '/login.page') {
    next('/');
  }else {
    // 사용자 ID가 있거나 로그인 페이지로 이동하는 경우 그대로 진행합니다.
    next();
  }
});

export default AppRouter;

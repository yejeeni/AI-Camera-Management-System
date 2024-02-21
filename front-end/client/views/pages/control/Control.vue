<template>
    <div id="map" class="map-main" style="width:100%; height: 100%;">
        <div class="map-menu" style="z-index: 2;">
            <div class="menu-tab navbar">
                <ul class="list-item">
                    <li v-for="(tab, index) in tabs" :key="index" @click="showModal(index)"
                        :class="{ active: activePath === index }">
                        <svg-icon type="mdi" :path="tab.icon"></svg-icon>
                        <a href="javascript:void(0)">{{ tab.name }}</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 이벤트정보탭 -->
        <div class="map-modal-wrap" id="openModal2" v-show="activePath === 0">
            <div class="flex-column">
                <div class="content-titleZone flex justify-between">
                    <p>오늘 발생한 상황</p>
                    <button class="darkg-border-btn map-close-modal" @click="closeModal"> &lt; </button>
                </div>
                <h4 class="mb20">{{ currentDate }} {{ currentTime }} 기준</h4>
                <div class="mapContent">
                    <Dounet :chartData="showChart ? todayEvent : []" />
                </div>
            </div>
            <div>
                <div class="content-titleZone">
                    <p>최근 발생한 상황 5건</p>
                </div>

                <div class="table-zone">
                    <table class="list-table">
                        <colgroup>
                            <col style="width: 18%">
                            <col style="width: 22%">
                            <col style="width: 60%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th>장비명</th>
                                <th>일자</th>
                                <th>정보</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="recentDetectEventList.length > 0"
                                v-for="(detectionEventOne, eIdx) in recentDetectEventList" :key="eIdx"
                                :style="{ color: isRecent(detectionEventOne.ocrn_dt) ? 'red' : 'black' }"
                                @click="openObjectDetectionModal(detectionEventOne)">
                                <td>{{ detectionEventOne.eqpmn_nm }}</td>
                                <td>{{ detectionEventOne.ocrn_dt }}</td>
                                <td>
                                    <span v-if="detectionEventOne.person_cnt > 0">
                                        사람 {{ detectionEventOne.person_cnt }}&nbsp;
                                    </span>
                                    <span v-if="detectionEventOne.bicycle_cnt > 0">
                                        자전거 {{ detectionEventOne.bicycle_cnt }}&nbsp;
                                    </span>
                                    <span v-if="detectionEventOne.car_cnt > 0">
                                        승용차 {{ detectionEventOne.car_cnt }}&nbsp;
                                    </span>
                                    <span v-if="detectionEventOne.motorcycle_cnt > 0">
                                        오토바이 {{ detectionEventOne.motorcycle_cnt }}&nbsp;
                                    </span>
                                    <span v-if="detectionEventOne.truck_cnt > 0">
                                        트럭 {{ detectionEventOne.truck_cnt }}
                                    </span>
                                </td>
                            </tr>
                            <tr v-else>
                                <td>-</td>
                                <td>-</td>
                                <td>금일 발생한 데이터가 없습니다.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- 객체 인식 상세보기 모달 -->
        <div class="modalArea">

            <div v-show="isDetectionModalOpen" class="modal-wrapper">
                <div class="modal-container">
                    <div class="modal-title">
                        <div class="flex justify-between align-center">
                            <h2>상세정보</h2>
                            <button class="close-btn" @click="closeObjectDetectionModal">X</button>
                        </div>
                    </div>
                    <div class="modal-content-monthly" v-show="modalType == 'tab-modal'">
                        <ul class="tab-content">
                            <!-- <li id="tab01" v-show="activeTab === 'tab1'"> -->
                            <div style="text-align: center;">
                                <img :src="'data:image/jpeg;base64,' + detectionEventOne.image" alt="이미지">
                            </div>
                            <table class="form-table mt10">
                                <colgroup>
                                    <col style="width: 15%;" />
                                    <col style="width: 85%;" />
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>장비 ID</th>
                                        <td>{{ detectionEventOne.eqpmn_id }}</td>
                                    </tr>
                                    <tr>
                                        <th>장비명</th>
                                        <td>{{ detectionEventOne.eqpmn_nm }}</td>
                                    </tr>
                                    <tr>
                                        <th>일자</th>
                                        <td>{{ detectionEventOne.ocrn_dt }}</td>
                                    </tr>
                                    <tr>
                                        <th>위치</th>
                                        <td>{{ detectionEventOne.eqpmn_lat }}, {{ detectionEventOne.eqpmn_lon }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <table class="list-table mt10">
                                <thead>
                                    <tr>
                                        <th>사람</th>
                                        <th>자전거</th>
                                        <th>승용차</th>
                                        <th>오토바이</th>
                                        <th>트럭</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>{{ detectionEventOne.person_cnt }}</td>
                                        <td>{{ detectionEventOne.bicycle_cnt }}</td>
                                        <td>{{ detectionEventOne.car_cnt }}</td>
                                        <td>{{ detectionEventOne.motorcycle_cnt }}</td>
                                        <td>{{ detectionEventOne.truck_cnt }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <!-- </li> -->
                            <!-- <li id="tab01" v-show="activeTab === 'tab1'"></li> -->
                        </ul>
                    </div>
                    <div class="modal-end flex justify-end">
                        <button v-show="modalType === 'tab-modal'" class="blue-btn small-btn"
                            @click="closeObjectDetectionModal">확인</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 장애정보 탭 -->
        <div class="map-modal-wrap" id="openModal3" v-show="activePath === 1">
            <div class="flex-column">
                <div class="content-titleZone flex justify-between">
                    <p>오늘 발생한 장애</p>
                    <button class="darkg-border-btn map-close-modal" @click="closeModal"> &lt; </button>
                </div>
                <h4 class="mb20">{{ currentDate }} {{ currentTime }} 기준</h4>
                <div class="mapContent">
                    <Dounet :chartData="showChart ? todayTrouble : []" />
                </div>
            </div>
            <div>
                <div class="content-titleZone">
                    <p>최근 발생한 장애 5건</p>
                </div>

                <div class="table-zone">
                    <table class="list-table">
                        <colgroup>
                            <col style="width: 18%;">
                            <col style="width: 22%;">
                            <col style="width: 40%;">
                            <col style="width: 20%;">
                        </colgroup>
                        <thead>
                            <tr>
                                <th>장비명</th>
                                <th>일자</th>
                                <th>장애명</th>
                                <th>복구여부</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="eqpmnTroubleList.length > 0" v-for="(equipmentTroubleOne, eIdx) in eqpmnTroubleList"
                                :key="eIdx" :style="{ color: isRecent(equipmentTroubleOne.ocrn_dt) ? 'red' : 'black' }"
                                @click="troubleClick(equipmentTroubleOne)">
                                <td>{{ equipmentTroubleOne.eqpmn_nm }}</td>
                                <td>{{ equipmentTroubleOne.ocrn_dt }}</td>
                                <td>{{ equipmentTroubleOne.trobl_nm }}</td>
                                <td>{{ equipmentTroubleOne.recovery_yn }}</td>
                                <td></td>
                            </tr>
                            <tr v-else>
                                <td>-</td>
                                <td>-</td>
                                <td>금일 발생한 데이터가 없습니다.</td>
                                <td>-</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- 장비등록 탭 -->
        <div class="map-modal-wrap" id="openModal3" v-show="activePath === 2">
            <div>
                <div class="content-titleZone flex justify-between">
                    <p>장비 등록</p>
                    <button class="darkg-border-btn  map-close-modal " @click="closeModal"> &lt; </button>
                </div>
                <div class="table-zone">
                    <table class="form-table">
                        <colgroup>
                            <col style="width: 30%;">
                            <col style="width: 70%;">
                            <col style="width: ;">
                            <col style="width: ;">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>장비명</th>
                                <td><input type="text" name="" id="" class="full-input" v-model="inputEquipment.eqpmn_nm"
                                        placeholder="장비명을 입력하세요"></td>
                            </tr>
                            <tr>
                                <th>카메라 IP</th>
                                <td><input type="text" name="" id="" class="full-input" v-model="inputEquipment.camera_ip"
                                        placeholder="카메라 IP를 입력하세요"></td>
                            </tr>
                            <tr>
                                <th>카메라명</th>
                                <td><input type="text" name="" id="" class="full-input" v-model="inputEquipment.camera_nm"
                                        placeholder="카메라명을 입력하세요"></td>
                            </tr>
                            <tr>
                                <th>엣지 ID</th>
                                <td><input type="text" name="" id="" class="full-input" v-model="inputEquipment.cntn_id"
                                        placeholder="엣지 ID를 입력하세요"></td>
                            </tr>
                            <tr>
                                <th>엣지 PW</th>
                                <td><input type="text" name="" id="" class="full-input" v-model="inputEquipment.cntn_pw"
                                        placeholder="엣지 PW를 입력하세요"></td>
                            </tr>
                            <tr>
                                <th>위도,경도</th>
                                <td>
                                    <div class="flex justify-between align-center">
                                        <div class="map-input-path flex30">
                                            <svg-icon type="mdi" :width="15" :height="15" :path="pathLatitude"
                                                class="icon"></svg-icon>
                                            <input type="text" name="" id="" class="full-input icon-parents"
                                                v-model="inputEquipment.eqpmn_lat" placeholder="위도를 입력하세요">
                                        </div>
                                        <div class="map-input-path flex30">
                                            <svg-icon type="mdi" :width="15" :height="15" :path="pathmdiLongitude"
                                                class="icon"></svg-icon>
                                            <input type="text" name="" id="" class="full-input icon-parents"
                                                v-model="inputEquipment.eqpmn_lon" placeholder="경도를 입력하세요">
                                        </div>
                                        <button class="darkg-border-btn small-btn flex30"
                                            @click="toggleFindLocation()">지도검색</button>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="flex justify-between align-center control-button">
                    <button class="blue-btn small-btn flex50" @click="equipmentInsert()">등록</button>
                    <button class="skyblue-btn small-btn flex50" @click="initializeForm()">취소</button>
                </div>
            </div>
        </div>
        <!-- 장비검색 탭 -->
        <div class="map-modal-wrap" id="openModal3" v-show="activePath === 3">
            <div>
                <div class="content-titleZone">
                    <div class="flex justify-between align-center" v-show="modalType1">
                        <p v-if="!modalType4">장비검색</p>
                        <p v-else>{{ equipment.eqpmn_nm }}</p>
                        <button class="darkg-border-btn map-close-modal" @click="closeModal"> &lt; </button>
                    </div>
                    <div class="flex justify-between align-center" v-show="modalType3">
                        <p>{{ equipment.eqpmn_nm }}</p>
                        <button class="darkg-border-btn map-close-modal" @click="closeModal2"> &lt; </button>
                    </div>
                </div>
                <div v-show="modalType2" class="modal2">
                    <div class="search-bar">
                        <div class="flex justify-end align-center">
                            <select name="" id="" v-model="dataListSearch.option" class="square-select full-select flex20">
                                <option value="all">전체</option>
                                <option value="eqpmn_id">장비 ID</option>
                                <option value="eqpmn_nm">장비명</option>
                                <option value="camera_nm">카메라명</option>
                            </select>
                            <div class="search-square">
                                <input type="text" class="square-input full-input flex60" placeholder="검색어를 입력하세요"
                                    name="keyword" id="keyword" v-model="dataListSearch.keyword"
                                    @keyup.enter="goToFirstPageAndLoadEquipmentsList()">
                                <button class="square-button" @click="goToFirstPageAndLoadEquipmentsList()">
                                    <svg-icon type="mdi" :path="searchPath" class="square-icon"></svg-icon>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="table-zone">
                        <table class="list-table">
                            <colgroup>
                                <col style="width: 16%;">
                                <col style="width: 29%;">
                                <col style="width: 29%;">
                                <col style="width: 26%;">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>장비 ID</th>
                                    <th>장비명</th>
                                    <th>카메라명</th>
                                    <th>등록일자</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(item, id) in dataList" :key="item.eqpmn_id" @click="[dataSelectOne(item)]">
                                    <!-- <tr v-for="(item, id) in dataList" :key="item.eqpmn_id" @click="openEq(item)"> -->
                                    <td>{{ item.eqpmn_id }}</td>
                                    <td>{{ item.eqpmn_nm }}</td>
                                    <td>{{ item.camera_nm }}</td>
                                    <td>{{ item.reg_dt }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <PaginationButton v-model:currentPage="dataListSearch.currentPage" :perPage="dataListSearch.perPage"
                        :total-count="dataListCount" :max-range="5" :click="equipmentSelectList" />
                </div>
                <!-- 모달창 눌렀을때 뜨는 탭 -->
                <div v-show="modalType4" class="modal3 flex-column ">
                    <div class="control-cctv-area">
                        <canvas ref="canvas"></canvas>
                    </div>
                    <div>
                        <div class="table-zone" v-show="openDetail1">
                            <div>
                                <table class="form-table">
                                    <!-- col 꼭 너비 기재해야함! 그래야 100%로 차지함 -->
                                    <colgroup>
                                        <col style="width: 25%;">
                                        <col style="width: 25%;">
                                        <col style="width: 25%;">
                                        <col style="width: 25%;">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>장비 ID</th>
                                            <td>{{ equipment.eqpmn_id }}</td>

                                            <th>장비명</th>
                                            <td>{{ equipment.eqpmn_nm }}</td>
                                        </tr>
                                        <tr>
                                            <th>장비 위도</th>
                                            <td>{{ equipment.eqpmn_lat }}</td>

                                            <th>장비 경도</th>
                                            <td>{{ equipment.eqpmn_lon }}</td>
                                        </tr>
                                        <tr>
                                            <th>카메라 IP</th>
                                            <td>{{ equipment.camera_ip }}</td>

                                            <th>카메라명</th>
                                            <td>{{ equipment.camera_nm }}</td>
                                        </tr>
                                        <tr>
                                            <th>엣지 ID</th>
                                            <td>{{ equipment.cntn_id }}</td>

                                            <th>엣지 PW</th>
                                            <td>{{ equipment.cntn_pw }}</td>
                                        </tr>
                                        <tr>
                                            <th>등록일자</th>
                                            <td>{{ equipment.reg_dt }}</td>

                                            <th>등록자 ID</th>
                                            <td>{{ equipment.rgtr_id }}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="flex justify-end buttonArea">
                                <button class="small-btn blue-btn" @click="openInput">수정</button>
                                <button class="small-btn gray-border-btn" @click="equipmentDeleteOne">삭제</button>
                            </div>
                        </div>
                        <div class="table-zone " v-show="openDetail2">
                            <table class="form-table">
                                <!-- col 꼭 너비 기재해야함! 그래야 100%로 차지함 -->
                                <colgroup>
                                    <col style="width: 25%;">
                                    <col style="width: 25%;">
                                    <col style="width: 25%;">
                                    <col style="width: 25%;">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>장비 ID</th>
                                        <td>
                                            {{ equipment.eqpmn_id }}
                                        </td>
                                        <th>장비명</th>
                                        <td>
                                            <input class="half-input" type="text" v-model="inputEquipment.eqpmn_nm">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>장비 위도</th>
                                        <td>
                                            <input class="half-input" type="text" v-model="inputEquipment.eqpmn_lat">
                                        </td>
                                        <th>장비 경도</th>
                                        <td>
                                            <input class="half-input" type="text" v-model="inputEquipment.eqpmn_lon">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>카메라 IP</th>
                                        <td>
                                            <input class="half-input" type="text" v-model="inputEquipment.camera_ip">
                                        </td>
                                        <th>카메라명</th>
                                        <td>
                                            <input class="half-input" type="text" v-model="inputEquipment.camera_nm">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>엣지 ID</th>
                                        <td>
                                            <input class="half-input" type="text" v-model="inputEquipment.cntn_id">
                                        </td>
                                        <th>엣지 PW</th>
                                        <td>
                                            <input class="half-input" type="text" v-model="inputEquipment.cntn_pw">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>등록일자</th>
                                        <td>
                                            {{ equipment.reg_dt }}
                                        </td>
                                        <th>등록자 ID</th>
                                        <td>
                                            {{ equipment.rgtr_id }}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="flex justify-end buttonArea">
                                <button class="small-btn blue-btn" @click="openInput2">확인</button>
                                <button class="small-btn gray-border-btn" @click="openInput3">취소</button>
                            </div>
                        </div>
                        <div class="modal-content-monthly" v-show="modalType == 'tab-modal'">
                            <ul class="tab-nav tab-navinner flex justify-between">
                                <li @click="showTab('tab3')" class="flex50"><a href="#tab03"
                                        :class="{ activeTabinner: activeTabinner === 'tab3' }">객체 인식 기록</a></li>
                                <li @click="showTab('tab4')" class="flex50"><a href="#tab04"
                                        :class="{ activeTabinner: activeTabinner === 'tab4' }">장비 장애 기록</a></li>
                            </ul>
                            <ul class="tab-content">
                                <li id="tab01" v-show="activeTabinner === 'tab3'">
                                    <table class="list-table">
                                        <colgroup>
                                            <col style="width: 25%">
                                            <col style="width: 75%">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>일자</th>
                                                <th>정보</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-if="recentDetectionEventListByEqpmn.length > 0"
                                                v-for="(detectionEventOne) in recentDetectionEventListByEqpmn"
                                                :style="{ color: isRecent(detectionEventOne.ocrn_dt) ? 'red' : 'black' }"
                                                @click="openObjectDetectionModal(detectionEventOne)">
                                                <td>{{ detectionEventOne.ocrn_dt }}</td>
                                                <td>
                                                    <span v-if="detectionEventOne.person_cnt > 0">
                                                        사람 {{ detectionEventOne.person_cnt }}&nbsp;
                                                    </span>
                                                    <span v-if="detectionEventOne.bicycle_cnt > 0">
                                                        자전거 {{ detectionEventOne.bicycle_cnt }}&nbsp;
                                                    </span>
                                                    <span v-if="detectionEventOne.car_cnt > 0">
                                                        승용차 {{ detectionEventOne.car_cnt }}&nbsp;
                                                    </span>
                                                    <span v-if="detectionEventOne.bus_cnt > 0">
                                                        버스 {{ detectionEventOne.bus_cnt }}
                                                    </span>
                                                    <span v-if="detectionEventOne.motorcycle_cnt > 0">
                                                        오토바이 {{ detectionEventOne.motorcycle_cnt }}&nbsp;
                                                    </span>
                                                    <span v-if="detectionEventOne.truck_cnt > 0">
                                                        트럭 {{ detectionEventOne.truck_cnt }}
                                                    </span>
                                                </td>
                                            </tr>
                                            <tr v-else>
                                                <td>-</td>
                                                <td>데이터가 없습니다.</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </li>
                                <li id="tab02" v-show="activeTabinner === 'tab4'">
                                    <table class="list-table">
                                        <colgroup>
                                            <col style="width: 25%;">
                                            <col style="width: 50%;">
                                            <col style="width: 25%;">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>일자</th>
                                                <th>장애명</th>
                                                <th>복구여부</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-if="eqpmnTroubleListByEqpmn.length > 0"
                                                v-for="(equipmentTroubleOne, eIdx) in eqpmnTroubleListByEqpmn" :key="eIdx"
                                                :style="{ color: isRecent(equipmentTroubleOne.ocrn_dt) ? 'red' : 'black' }">
                                                <td>{{ equipmentTroubleOne.ocrn_dt }}</td>
                                                <td>{{ equipmentTroubleOne.trobl_nm }}</td>
                                                <td>{{ equipmentTroubleOne.recovery_yn }}</td>
                                            </tr>
                                            <tr v-else>
                                                <td>-</td>
                                                <td>데이터가 없습니다.</td>
                                                <td>-</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- <div class="map-notice">
            <ul class="tab-nav flex justify-between">
                <li @click="showTab('tab1')" class="flex50"><a href="#" :class="{ activeTab: activeTab === 'tab1' }">이벤트</a>
                </li>

                <li @click="showTab('tab2')" class="flex50"><a href="#" :class="{ activeTab: activeTab === 'tab2' }">장비</a>
                </li>
            </ul>
            <ul class="tab-content-control content-tab1" v-show="activeTab === 'tab1'">
                <li id="tab01" class="flex align-center" v-for="event in events" :key="event.id">
                    <svg-icon type="mdi" :path="path5" style="color: var(--color-blue) ;"></svg-icon>
                    <a href="javascript:void(0)">{{ event.data }}</a>
                </li>
            </ul>
            <ul class="tab-content-control content-tab2" v-show="activeTab === 'tab2'">
                <li id="tab01" class="flex align-center" v-for="tabList in tabLists" :key="tabList.id">
                    <svg-icon type="mdi" :path="path5" style="color: var(--color-blue)"></svg-icon>
                    <a href="javascript:void(0)">{{ tabList.data }}</a>
                </li>
            </ul>
        </div> -->
    </div>
</template>

<script>
import axios from 'axios';
import SvgIcon from '@jamescoyle/vue-icon';
import PaginationButton from '../../component/PaginationButton.vue';
import { mdiTools, mdiCctv, mdiRobotVacuumVariantAlert, mdiFolderAlert, mdiCalendarSearch, mdiMagnify, mdiLatitude, mdiLongitude, mdiBullhornOutline } from '@mdi/js';
import Dounet from '../../component/chart/Dounet.vue';

export default {
    name: "my-component",
    data() {
        return {
            userId: sessionStorage.getItem('user_id') || null,


            map: null,
            path: mdiFolderAlert,
            path1: mdiCalendarSearch,
            path2: mdiRobotVacuumVariantAlert,
            path3: mdiTools,
            path4: mdiMagnify,
            path5: mdiBullhornOutline,
            searchPath: mdiMagnify,
            cctvPath: mdiCctv,
            pathLatitude: mdiLatitude,
            pathmdiLongitude: mdiLongitude,
            activeTab: 'tab1',
            activeTabinner: 'tab3',
            activePath: null,
            modalType1: true,
            modalType2: true,
            dataList: [],
            modalType2: true,
            modalType3: false,
            modalType4: false,
            openDetail1: true,
            openDetail2: false,
            isModalOpen: false,

            tabs: [
                // { name: "전체정보", icon: mdiFolderAlert , open: this.openModal},
                { name: "상황정보", icon: mdiCalendarSearch, },
                { name: "장애정보", icon: mdiRobotVacuumVariantAlert, },
                { name: "장비등록", icon: mdiTools, },
                { name: "장비검색", icon: mdiMagnify, }
            ],
            events: [
                { id: 'event1', data: '이벤트1 데이터' },
                { id: 'event2', data: '이벤트2 데이터' },
                { id: 'event2', data: '이벤트2 데이터' },
                { id: 'event2', data: '이벤트2 데이터' },
                { id: 'event2', data: '이벤트2 데이터' },
            ],
            tabLists: [
                { id: 'event1', data: '이벤트1 데이터' },
                { id: 'event2', data: '이벤트2 데이터' },
            ],
            tabList: {
                data: '5555555'
            },
            showChart: false,
            todayEvent: [],
            emptyData:
                [{
                    '데이터 없음': 0
                }],

            /////////////////////시작 지도, 장비////////////////////

            dataListCount: null,

            // 장비목록
            eqpmnList: null,

            isModalOpen: true,
            modalType: 'tab-modal',

            markers: [],
            todayTrouble: [],

            currentDate: '',
            currentTime: '',

            equipment: {
                eqpmn_id: null,
                eqpmn_nm: null,
                eqpmn_lat: null,
                eqpmn_lon: null,
                camera_ip: null,
                camera_nm: null,
                cntn_id: null,
                cntn_pw: null,
                reg_dt: null,
                rgtr_id: null,
                mdfcn_dt: null,
                mdfr_id: null,
                use_yn: null
            },

            inputEquipment: {
                eqpmn_id: null,
                eqpmn_nm: null,
                eqpmn_lat: null,
                eqpmn_lon: null,
                camera_ip: null,
                camera_nm: null,
                cntn_id: null,
                cntn_pw: null,
                reg_dt: null,
                rgtr_id: null,
                mdfcn_dt: null,
                mdfr_id: null,
                use_yn: null
            },
            map: null,
            clusterer: null,

            findLocation: false, // 지도검색 버튼 클릭 여부
            clickListener: null, // 지도에서 클릭한 위치의 위도경도를 받아오는 클릭리스너

            // 장비 검색 페이지네이션
            dataListSearch: {
                currentPage: 1,
                perPage: 10,
                keyword: '',
                option: 'all'
            },

            activeMarker: null, // 선택한 마커
            activeMarkerId: null, // 선택한 마커의 장비 id
            beforeImg: null, // 선택한 마커의 클릭 이전 이미지
            markerStates: {}, // 마커의 상태 (장비 고장인지 아닌지)
            status: null, // 선택한 장비의 정상/고장 상태 확인

            /////////////////////끝 지도, 장비////////////////////

            /////////////////////객체 인식/////////////////////
            // 최신 정보 리스트
            recentDetectEventList: [],
            // 특정 카메라의 최신 정보 리스트
            recentDetectionEventListByEqpmn: [],

            // 상세 정보
            detectionEventOne: {
                eqpmn_id: null,
                eqpmn_nm: null,
                ocrn_dt: null,
                eqpmn_lat: null,
                eqpmn_lon: null,
                person_cnt: null,
                bicycle_cnt: null,
                car_cnt: null,
                truck_cnt: null,
                motorcycle_cnt: null,
                image: null
            },

            isDetectionModalOpen: false, // 객체인식 상세보기 모달창 열고 닫음
            /////////////////////////////////////////////////

            //////////////////////장비 장애///////////////////
            eqpmnTroubleList: [],
            eqpmnTroubleListByEqpmn: [],
            /////////////////////////////////////////////////
            validation: null,
        }

    },
    methods: {
        // 탭 변경
        showTab: function (tabName) {
            this.activeTab = tabName;
            this.activeTabinner = tabName;
        },
        showModal: function (idx) {
            if (this.activePath === idx) {
                this.activePath = null;
            } else {
                this.activePath = idx;
                this.modalType2 = true;
                this.modalType4 = false;
            }
            this.resetData();
            this.showChart = true;
        },
        closeModal: function () { // 모달창 닫기 창 
            this.activePath = null;
            this.showChart = false; // 차트를 숨김
            this.resetData();
            this.inputToShow();
        },
        closeModal2: function () {
            this.modalType3 = !this.modalType3;
            this.modalType4 = !this.modalType4;
            this.modalType1 = !this.modalType1;
            this.modalType2 = !this.modalType2;
        },
        openInput2() {
            if (confirm("장비를 수정하시겠습니까?") == false) {
                return;
            }
            this.validation = this.handleButtonAction();
            if (this.validation == -1) {
                return;
            } else {
                this.inputToShow();
                this.equipmentUpdate();
            }

            this.openDetail1 = true;
            this.openDetail2 = false;
        },
        openInput() { // 수정버튼
            this.openDetail1 = false;
            this.openDetail2 = true;
            this.showToInput();
        },
        openInput3() { // 취소버튼
            this.openDetail1 = true;
            this.openDetail2 = false;
            this.showToInput();
        },

        //////////////////////////////////// 지도 시작 /////////////////////////////////////

        // equipment데이터 초기화
        resetData: function () {
            Object.keys(this.equipment).forEach(key => {
                this.equipment[key] = null;
            });
        },

        showToInput: function () {
            Object.keys(this.equipment).forEach(key => {
                this.inputEquipment[key] = this.equipment[key]; // 입력폼(2)에 노출값(1)을 저장한다. 원래값으로 돌아옴.
            });
        },

        inputToShow: function () {
            Object.keys(this.inputEquipment).forEach(key => {
                this.equipment[key] = this.inputEquipment[key];  // 노출값(1)에 입력값(2)을 저장한다. 
            });
        },

        // 지도의 처음 옵션
        initMap() {
            const container = document.getElementById("map");
            const options = {
                center: new kakao.maps.LatLng(35.831681, 128.758423),
                level: 4
            };
            this.map = new kakao.maps.Map(container, options);
            this.map.setMaxLevel(12);
        },

        // 새로운 마커 지우기
        clearMarkers: function () {
            const vm = this;
            for (let i = 0; i < vm.markers.length; i++) {
                vm.markers[i].setMap(null);
            }
            vm.markers = [];
        },

        /**
         * 마커에 클릭 리스너를 추가하는 메서드
         * 
         * @param {마커} marker 
         * @param {장비} item 
         * @param {마커_기본_이미지} markerImage 
         * @param {마커_선택_이미지} selectedMarkerImage 
         */
        addClickListener: function (marker, item, markerImage, selectedMarkerImage) {
            const vm = this;
            const imageSrc = 'https://i.ibb.co/dQRS2WQ/marker-3.png'; // 기본 마커 이미지 URL
            const imageSize = new kakao.maps.Size(60, 60); // 마커 이미지의 크기
            const imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커 이미지의 옵션 설정
            const markerImage2 = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption); // 정상 상태의 장비 마커 이미지

            kakao.maps.event.addListener(marker, 'click', function () {
                if (vm.activeMarker != null && vm.status == 'Y') { // 이전에 선택된 마커가 있고, 정상 상태의 장비였다면 원래 이미지로 복원
                    vm.activeMarker.setImage(markerImage2);
                }
                if (item.status == 'Y') {
                    marker.setImage(selectedMarkerImage); // 현재 선택한 마커의 이미지를 선택 이미지로 변경
                    vm.beforeImg = markerImage; // 복원시킬 이미지 저장
                }
                vm.status = item.status; // 선택한 장비의 상태 업데이트
                vm.activeMarker = marker; // 활성화된 마커를 업데이트
                vm.activeMarkerId = item.eqpmn_id; // 활성화된 마커의 장비 ID 저장

                vm.moveToLocation(item.eqpmn_lat, item.eqpmn_lon);

                vm.equipmentSelectOne(item.eqpmn_id); // 장비 상세보기

                // 선택한 장비의 기록 조회
                vm.recentObjectDetectEventByEqpmn();
                vm.recentEqpmnTroubleSelectListByEqpmn();

                // 모달창 제어
                vm.activePath = 3;
                vm.modalType4 = true;
                vm.modalType2 = false;
                vm.openDetail1 = true;
                vm.openDetail2 = false;
            });
        },

        /**
         * 주어진 데이터와 이미지를 이용하여 카카오 맵 위에 마커를 생성하는 메서드
         * 
         * @param {장비} item 
         * @param {마커_기본_이미지} markerImage 
         */
        createMarker: function (item, markerImage) {
            const markerPosition = new kakao.maps.LatLng(parseFloat(item.eqpmn_lat), parseFloat(item.eqpmn_lon)); // 마커에 장비의 위도 경도 설정
            const marker = new kakao.maps.Marker({
                position: markerPosition,
                image: markerImage // 초기 마커 이미지 설정
            });
            return marker;
        },

        /**
         * 마커를 지도에 추가하는 메서드
         * 
         * @param {마커} marker 
         * @param {지도} map 
         */
        addMarkerToMap: function (marker, map) {
            marker.setMap(map);
        },

        /**
         * 새로운 마커를 추가하는 메서드
         * 
         * @param {장비목록} eqpmnList 
         * @param {지도} map 
         */
        addMarkers: function (eqpmnList, map) {
            const vm = this;
            const imageSrc = 'https://i.ibb.co/dQRS2WQ/marker-3.png'; // 기본 마커 이미지 URL
            const selectedImageSrc = 'https://i.ibb.co/7k2sVBP/marker.png'; // 선택된 마커 이미지 URL
            const errorImageSrc = 'https://i.ibb.co/bKh5rzz/marker-1.png'; // 에러 이미지 URL
            const imageSize = new kakao.maps.Size(60, 60); // 마커 이미지의 크기
            const imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커 이미지의 옵션 설정
            const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption); // 정상 상태의 마커
            const selectedMarkerImage = new kakao.maps.MarkerImage(selectedImageSrc, imageSize, imageOption); // 클릭된 마커
            const errMarkerImage = new kakao.maps.MarkerImage(errorImageSrc, imageSize, imageOption); // 에러 상태의 마커

            vm.clearMarkers();

            eqpmnList.forEach(function (item) {
                if (item.status == 'Y') { // 정상 장비라면
                    if (item.eqpmn_id == vm.activeMarkerId) {
                        // 활성화된 마커의 장비 ID와 일치하는 경우
                        // 해당 마커를 클릭된 마커 이미지로 생성
                        const marker = vm.createMarker(item, selectedMarkerImage);
                        vm.addClickListener(marker, item, selectedMarkerImage, selectedMarkerImage);
                        vm.addMarkerToMap(marker, map);
                        vm.markers.push(marker);  // 마커를 마커 배열에 저장합니다.

                        vm.activeMarker = marker; // 마커를 활성화된 마커로 설정
                        vm.beforeImg = markerImage; // 이전 이미지 저장
                    } else { // 활성화된 마커와 다른 장비 ID
                        const marker = vm.createMarker(item, markerImage);
                        vm.addClickListener(marker, item, markerImage, selectedMarkerImage);
                        vm.addMarkerToMap(marker, map);
                        vm.markers.push(marker);
                    }
                } else { // 에러 장비라면
                    const marker = vm.createMarker(item, errMarkerImage);
                    vm.addClickListener(marker, item, errMarkerImage, selectedMarkerImage);
                    vm.addMarkerToMap(marker, map);
                    vm.markers.push(marker);
                }
            });

            if (vm.clusterer != null) {
                vm.clusterer.clear();
            }

            vm.clusterer = new kakao.maps.MarkerClusterer({
                map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
                averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
                minLevel: 8, // 클러스터 할 최소 지도 레벨 
                //minClusterSize: 1,
                markers: vm.markers // 클러스터에 마커 추가
            });
            vm.clusterer.redraw();
        },

        // 지도검색 버튼 클릭 후 지도를 클릭하면 위도 경도 위치값을 할당하는 메서드
        toggleFindLocation() {
            if (!this.map) {
                alert("지도가 아직 초기화되지 않았습니다. 잠시 후 다시 시도해주세요.");
                return;
            }

            if (this.findLocation) {
                kakao.maps.event.removeListener(this.map, 'click', this.clickListener);
                this.findLocation = false;
            } else {
                this.clickListener = kakao.maps.event.addListener(this.map, 'click', (mouseEvent) => {
                    const latlng = mouseEvent.latLng;
                    this.inputEquipment.eqpmn_lat = latlng.getLat().toFixed(6);
                    this.inputEquipment.eqpmn_lon = latlng.getLng().toFixed(6);
                    this.findLocation = false;
                    kakao.maps.event.removeListener(this.map, 'click', this.clickListener);
                });
                this.findLocation = true;
            }
        },

        // 위도 경도를 기준으로 위치 이동
        moveToLocation: function (lat, lon) {
            var moveLatLon = new kakao.maps.LatLng(lat, lon);
            this.map.panTo(moveLatLon);
        },

        // 장애정보 위치 이동하기
        troubleClick: function (equipmentTroubleOne) {
            this.moveToLocation(equipmentTroubleOne.eqpmn_lat, equipmentTroubleOne.eqpmn_lon);
        },

        /////////////////////////////////// 지도 끝 ////////////////////////////////////

        //////////////////////////////// 장비 시작 /////////////////////////////////////

        // 장애 유무를 포함한 장비 목록 조회
        eqpmnSelectListIncludeTroblYN: function () {
            const vm = this;
            axios({
                url: "/eqpmnSelectListIncludeTroblYN.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.dataListSearch,
            })
                .then(function (response) {
                    // vm.clearMarkers();
                    console.log("eqpmnSelectListIncludeTroblYN - response : ", response.data);
                    vm.eqpmnList = response.data.equipmentSelectList;
                    vm.addMarkers(vm.eqpmnList, vm.map);
                })
                .catch(function (error) {
                    console.log("eqpmnSelectListIncludeTroblYN - error : ", error);
                });
        },

        // 장비 리스트 조회 
        equipmentSelectList: function () {
            const vm = this;
            axios({
                url: "/equipmentSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.dataListSearch,
            })
                .then(function (response) {
                    console.log("데이터 조회 - response : ", response.data);
                    if (response.data.equipmentSelectList) {
                        vm.dataList = response.data.equipmentSelectList;
                        vm.dataListCount = response.data.equipmentSelectListCount;
                    } else {
                        alert("조회된 데이터가 없습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("dataList - error : ", error);
                    alert("장비 리스트 조회에 오류가 발생했습니다.");
                });
        },

        // 장비 상세 조회
        equipmentSelectOne: function (eqpmn_id) {
            const vm = this;
            axios({
                url: "/equipmentSelectOne.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { eqpmn_id: eqpmn_id },
            })
                .then(function (response) {
                    // 성공적으로 응답을 받았을 때 실행할 코드
                    console.log("한 정보만 뜨기- response : ", response.data);
                    vm.equipment = response.data; // 받은 데이터로 상태를 업데이트합니다.
                    // 여기에 받은 데이터를 사용하여 팝업을 표시하거나 다른 UI 업데이트를 수행할 수 있습니다.
                })
                .catch(function (error) {
                    // 요청이 실패했을 때 실행할 코드
                    console.log("equipmentSelectOne - error : ", error);
                    alert("장비 상세조회에 오류가 발생했습니다."); // 사용자에게 오류를 알립니다.
                });
        },

        // 장비 등록하기
        equipmentInsert: function () {
            const vm = this;
            vm.validation = vm.handleButtonAction();
            if (vm.validation == -1) {
                return;
            }

            axios({
                url: "/equipmentInsert.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment,
            })
                .then(function (response) {
                    console.log("데이터 등록하기 - response : ", response.data);
                    if (response.data && response.data > 0) {
                        alert("지도에 등록하였습니다.");
                        // vm.createSingleMarker(vm.equipment, vm.map);
                        vm.resetData();
                        vm.eqpmnSelectListIncludeTroblYN();
                        vm.equipmentSelectList();
                    } else {
                        alert("장비 등록에 실패 하였습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("cctvdataInsert - error : ", error);
                    alert("장비 등록에 오류가 발생했습니다.");
                });
        },

        // 장비 삭제
        equipmentDeleteOne: function () {
            const vm = this;
            if (confirm("장비를 삭제하시겠습니까?") == false) {
                return;
            }
            axios({
                url: "/equipmentDeleteOne.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { eqpmn_id: vm.equipment.eqpmn_id },
            })
                .then(function (response) {
                    console.log("equipmentDelete - response : ", response.data);
                    if (response.data == 1) {
                        alert("장비 삭제에 성공하였습니다.")
                    }
                    vm.eqpmnSelectListIncludeTroblYN();
                    vm.equipmentSelectList();

                    // 장비상세 창 닫기
                    vm.modalType3 = false;
                    vm.modalType4 = false;
                    vm.activePath = null;
                })
                .catch(function (error) {
                    console.log("equipmentDelete - error : ", error);
                    alert("장비 삭제에 오류가 발생했습니다.");
                });
        },

        // 장비 수정
        equipmentUpdate: function () {
            const vm = this;
            axios({
                url: "/equipmentUpdate.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: vm.equipment
            })
                .then(function (response) {
                    console.log("equipmentUpdate - response : ", response.data);
                    if (response.data > 0) {
                        alert("장비를 수정했습니다.");
                        vm.eqpmnSelectListIncludeTroblYN();
                        vm.equipmentSelectList();
                    } else {
                        alert("장비 수정에 실패했습니다.");
                    }
                })
                .catch(function (error) {
                    console.log("equipmentUpdate - error : ", error);
                    alert("장비 수정에 오류가 발생했습니다.");
                });
        },

        // 장비 검색에서 장비 선택 시 실행되는 메서드
        dataSelectOne: function (item) {
            this.activeMarkerId = item.eqpmn_id; // 선택 장비의 ID를 activeMarkerId에 저장
            this.equipment = Object.assign({}, item); // 장비의 상세정보를 복사
            this.moveToLocation(item.eqpmn_lat, item.eqpmn_lon);
            this.modalType3 = !this.modalType3; // 탭 관리
            this.modalType4 = !this.modalType4;
            this.modalType1 = !this.modalType1;
            this.modalType2 = !this.modalType2;
            this.recentObjectDetectEventByEqpmn(); // 선택 장비의 객체 인식 목록
            this.recentEqpmnTroubleSelectListByEqpmn(); // 선택 장비의 장비 장애 목록
        },

        // 입력 폼과 버튼을 기본값으로 초기화
        initializeForm: function () {
            this.resetData();
        },

        // equipment데이터 초기화
        resetData: function () {
            Object.keys(this.inputEquipment).forEach(key => {
                this.inputEquipment[key] = null;
            });
        },

        //데이터 편집 및 유효성검사
        handleButtonAction: function () {
            const regexForLat = /^-?(3[3-8]|[3-7]\d|\d{1,2})\.\d{6}$/;
            const regexForLon = /^-?(12[4-9]|13[0-2]|\d{1,2})\.\d{6}$/;
            const regexForIp = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

            if (!this.inputEquipment.eqpmn_nm) {
                alert("장비명을 입력해주세요.");
                return -1;
            }

            if (!this.inputEquipment.eqpmn_lat) {
                alert("위도, 경도를 올바르게 입력해주세요. (형식: 숫자 3개.숫자 6개)");
                return -1;
            }
            if (!regexForLat.test(this.inputEquipment.eqpmn_lat)) {
                alert("위도 형식이 맞지 않습니다.");
                return -1;
            }

            if (!this.inputEquipment.eqpmn_lon) {
                alert("경도를 올바르게 입력해주세요. (형식: 숫자 3개.숫자 6개)");
                return -1;
            }
            if (!regexForLon.test(this.inputEquipment.eqpmn_lon)) {
                alert("경도 형식이 맞지 않습니다.");
                return -1;
            }

            if (!this.inputEquipment.camera_ip) {
                alert("카메라 IP를 올바르게 입력해주세요. (형식: 숫자.숫자.숫자.숫자)");
                return -1;
            }
            if (!regexForIp.test(this.inputEquipment.camera_ip)) {
                console.log("test: ", this.inputEquipment.camera_ip);
                alert("카메라 IP 형식이 맞지 않습니다.");
                return -1;
            }

            if (!this.inputEquipment.camera_nm) {
                alert("카메라명을 입력해주세요.");
                return -1;
            }

            if (!this.inputEquipment.cntn_id) {
                alert("엣지 ID를 입력해주세요.");
                return -1;
            }

            if (!this.inputEquipment.cntn_pw) {
                alert("엣지 PW를 입력해주세요.");
                return -1;
            }

            this.inputToShow();
        },

        // 첫 페이지로 초기화하고 장비 목록을 조회합니다.
        goToFirstPageAndLoadEquipmentsList: function () {
            this.dataListSearch.currentPage = 1;
            this.equipmentSelectList();
            this.resetData();
        },

        //////////////////////////////////// 끝 지도 장비  //////////////////////////////

        //////////////////////////////////// 객체인식 시작 ///////////////////////////////////

        // 오늘 최신 5개 객체 인식 기록 조회
        todayRecentObjectDetectEventList: function () {
            const vm = this;
            axios({
                url: "/todayRecentObjectDetectEventList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: {},
            })
                .then(function (response) {
                    console.log("최신순: ", response.data);
                    vm.recentDetectEventList = response.data;
                })
                .catch(function (error) {
                    console.log("todayRecentObjectDetectEventList - error : ", error);
                });
        },


        // 반복 실행 메서드
        recentDataUpdate() {
            // 메서드를 일정 시간마다 반복 실행
            this.intervalId = setInterval(() => {
                this.eqpmnSelectListIncludeTroblYN();
                if (this.activePath == 0) { // 이벤트 정보를 열어두고 있으면
                    this.todayRecentObjectDetectEventList();
                } else if (this.activePath == 1) { // 장애정보를 열어두고 있으면
                    this.todayRecentEqpmnTroubleSelectList();
                }
                if (this.equipment.eqpmn_id != null) { // 선택 장비가 있으면
                    this.recentObjectDetectEventByEqpmn();
                    this.recentEqpmnTroubleSelectListByEqpmn();
                }
            }, 10000);
        },

        // 오늘 객체 인식 수 조회
        todayObjectDetectEventSumByAllEquipment: function () {
            const vm = this;
            axios({
                url: "/todayObjectDetectEventSumByAllEquipment.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: {},
            })
                .then(function (response) {
                    console.log("cnt", response.data);
                    vm.todayEvent = Object.keys(response.data).map(category => {
                        return {
                            category: category,
                            value: response.data[category]
                        };
                    })
                })
                .catch(function (error) {
                    console.log("todayObjectDetectEventSumByAllEquipment - error : ", error);
                });
        },

        // 특정 장비의 최신 5개 객체 인식 기록
        recentObjectDetectEventByEqpmn: function () {
            const vm = this;
            axios({
                url: "/recentObjectDetectEventByEqpmn.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { eqpmn_id: vm.activeMarkerId },
            })
                .then(function (response) {
                    vm.recentDetectionEventListByEqpmn = response.data;
                    console.log("특정객체인식 - ", vm.recentDetectionEventListByEqpmn);
                })
                .catch(function (error) {
                    console.log("recentObjectDetectEventByEqpmn - error : ", error);
                });
        },


        /////////////////////////////////// 객체인식 끝 ///////////////////////////////////

        ///////////////////////////////// 장비장애 시작 ///////////////////////////////////

        // 오늘 최신 5개 장비 장애 조회
        todayRecentEqpmnTroubleSelectList: function () {
            const vm = this;
            axios({
                url: "/todayRecentEqpmnTroubleSelectList.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: {},
            })
                .then(function (response) {
                    vm.eqpmnTroubleList = response.data;
                    console.log("장비장애 - ", response.data);
                })
                .catch(function (error) {
                    console.log("todayRecentEqpmnTroubleSelectList - error : ", error);
                });
        },

        // 오늘 장비 장애 수 조회
        todayEqpmnTroubleSum: function () {
            const vm = this;
            axios({
                url: "/todayEqpmnTroubleSum.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: {},
            })
                .then(function (response) {
                    console.log("cnt", response.data);
                    vm.todayTrouble = Object.keys(response.data).map(category => {
                        return {
                            category: category,
                            value: response.data[category]
                        };
                    })
                })
                .catch(function (error) {
                    console.log("todayEqpmnTroubleSum - error : ", error);
                });
        },

        // 특정 장비의 최신 5개 장애 조회
        recentEqpmnTroubleSelectListByEqpmn: function () {
            const vm = this;

            axios({
                url: "/recentEqpmnTroubleSelectListByEqpmn.json",
                method: "post",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                data: { eqpmn_id: vm.activeMarkerId },
            })
                .then(function (response) {
                    vm.eqpmnTroubleListByEqpmn = response.data;
                    console.log("특정장비장애 - ", response.data);
                })
                .catch(function (error) {
                    console.log("recentEqpmnTroubleSelectListByEqpmn - error : ", error);
                });
        },

        ////////////////////////////// 장비장애 끝 //////////////////////////////////

        // 현재 시간을 불러오는 메서드
        getCurrentDateTime() {
            const now = new Date();

            // 일자 포맷을 원하는 형태로 조정할 수 있습니다.
            const dateOptions = { year: 'numeric', month: 'long', day: 'numeric' };
            const timeOptions = { hour: 'numeric', minute: 'numeric', second: 'numeric' };

            // 데이터 바인딩에 사용될 형태로 데이터 업데이트
            this.currentDate = now.toLocaleDateString('ko-KR', dateOptions);
            this.currentTime = now.toLocaleTimeString('ko-KR', timeOptions);
        },

        closeObjectDetectionModal: function () {
            this.isDetectionModalOpen = false;
        },

        openObjectDetectionModal: function (detectionEvent) {
            this.detectionEventOne = detectionEvent;
            this.isDetectionModalOpen = true;
        },
    },
    watch: {

    },
    computed: {
        // 최신 정보인지 확인하는 메서드
        isRecent() {
            return (eventTime) => {
                const currentTime = new Date();

                // 형식에 맞게 Date 객체 생성
                const eventDate = new Date(eventTime);
                const timeDiffMinutes = (currentTime - eventDate) / (1000 * 60);

                // 당일이고 10분 이내라면 true
                return timeDiffMinutes > 0 && timeDiffMinutes <= 10;
            };
        },
    },
    components: {
        'SvgIcon': SvgIcon,
        'Dounet': Dounet,
        'PaginationButton': PaginationButton,
    },
    mounted() {
        // this.fetchData();

        // 제일 먼저 실행 되어야됨 
        if (window.kakao && window.kakao.maps) {
            this.initMap();
            this.eqpmnSelectListIncludeTroblYN();
        } else {
            const script = document.createElement("script");
            script.onload = () => {
                kakao.maps.load(() => {
                    this.initMap();
                    this.eqpmnSelectListIncludeTroblYN();
                });
            };
            script.src = "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=[발급받은 Key]&libraries=clusterer";
            document.head.appendChild(script);
        }

        this.$nextTick(() => {
            const client = new WebSocket('ws://localhost:9999');
            const canvas = this.$refs.canvas;
            const player = new jsmpeg(client, { canvas: canvas });
            canvas.style.width = '480px';
            canvas.style.height = '450px'
        });

        this.todayRecentObjectDetectEventList();
        this.todayRecentEqpmnTroubleSelectList();
        this.todayObjectDetectEventSumByAllEquipment();

        this.eqpmnSelectListIncludeTroblYN();
        this.equipmentSelectList();

        this.todayEqpmnTroubleSum();
        this.todayEqpmnTroubleSum();

        this.recentDataUpdate();
        this.getCurrentDateTime();
    },
    beforeDestroy() {
        clearInterval(this.intervalId);
    },
    beforeRouteLeave(to, from, next) {
        console.log("beforeRouteLeave is called");
        clearInterval(this.intervalId);
        next();
    },
}
</script>

<style lang="css" scoped>
#map {
    position: relative;

}

.map-menu {
    position: absolute;
    z-index: 10;
    background-color: #f8f9fe;
    overflow: hidden;
    top: 30px;
    left: 51px;
    width: 78px;
    height: 425px;
    border-radius: 1rem;
    box-shadow: 5px 5px 10px #33333365;
}

.map-notice {
    position: absolute;
    z-index: 10;
    background-color: #f8f9fe;
    top: 30px;
    right: 51px;
    width: 616px;
    height: 150px;
    padding: 0 1.5rem 0.5rem 1.5rem;
    border-radius: 1rem;
    box-shadow: 5px 5px 10px #33333365;
}

.menu-tab {
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-columns: 1fr;
    align-items: center;
    text-align: center;

}

.tab-nav {
    border-top: 0px;
    border-bottom: 0px;
}

.tab-nav li a {
    padding: 1rem 0;
    border-right: 0px;
}

.tab-nav li a.activeTab {
    color: white;
    background-color: var(--color-blue);
    border-radius: 1rem;
    padding: 1rem 0;
}

.tab-navinner li a.activeTabinner {
    color: white;
    background-color: var(--color-blue);
    border-radius: 1rem;
    padding: 1rem 0;
}

.navbar .list-item {
    width: 100%;
    height: 100%;
    margin: 0 auto;
    display: grid;
    grid-template-columns: 1fr;
    align-items: center;
    padding: 1rem 0;
}

.navbar .list-item li {
    display: grid;
    grid-template-columns: 1fr;
    justify-items: center;
    align-items: center;
    list-style: none;
    font-size: 1rem;
    position: relative;
    align-items: center;
    padding: 1rem 0;

}

.navbar .list-item li.active {
    border-bottom-left-radius: 1em;
    border-top-left-radius: 1rem;
    width: 90%;
    position: relative;
    left: 11%;
    background-color: var(--color-blue);
    color: white;
    padding: 1rem 0;
}

.navbar .list-item li.active a {
    color: white;
    padding: 0.5rem 0;
}

.navbar .list-item li.active::before {
    position: absolute;
    content: '';
    top: -20px;
    right: 0;
    width: 20px;
    height: 20px;
    border-bottom-right-radius: 20px;
    background-color: var(--color-blue);
    box-shadow: 5px 5px 0 5px var(--color-blue);
    background: transparent;
}

.navbar .list-item li.active::after {
    position: absolute;
    content: '';
    bottom: -20px;
    right: 0;
    width: 20px;
    height: 20px;
    border-top-right-radius: 20px;
    background-color: var(--color-blue);
    box-shadow: 5px -5px 0 5px var(--color-blue);
    background: transparent;
}

.navbar .list-item li a {
    font-size: 1.3rem;
}

.map-modal-wrap {
    position: absolute;
    z-index: 100;
    top: 30px;
    left: 150px;
    width: 560px;
    background-color: white;
    padding: 2.4rem 3.4rem;
    min-height: 465px;
    max-height: 93%;
    overflow-y: auto;
    border-radius: 1rem;
    box-shadow: 5px 5px 10px #33333365;

}

.map-modal-wrap p {
    font-size: 1.7rem;
    font-family: 'Pretendard';
    font-weight: 600;
    padding: 1.3rem 0;
}

#openModal.active {
    display: block;
}

.mapContent {
    width: 100%;
    height: 450px;
}

.map-input-path {
    position: relative;
}

.map-input-path>.icon {
    position: absolute;
    top: 50%;
    left: 0;
    transform: translateY(-50%);
}

.content-tab1.activeTab {
    display: none;
}

.tab-content-control {
    height: 50%;
    overflow-y: auto;
}

.tab-content-control li {
    padding: 1px 0;
}

.tab-content-control a {
    font-size: 1.7rem;
    padding: 0.5px;
}

.control-button button {
    margin-left: 0px;
}

.map-close-modal {
    border: 1px solid var(--color-gray);
    border-radius: 10rem;
    width: 50px;
    height: 50px;
}

.buttonArea {
    margin-top: 2rem;
}

.modal-container {
    width: 38%;
    height: 90%;
}

.modal-content-monthly {
    /* padding: 20px 20px; */
}
</style>

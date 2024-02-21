<template>
    <div class="table-zone">
        <div class="flex justify-between align-center">
            <div class="count-zone">
                <p>총 <span>40</span>건 중 <span>01</span>건 선택</p>
            </div>
            <div class="cunt-selectZone">
                <select name="" id="">
                    <option value="">10개 보기</option>
                    <option value="">20개 보기</option>
                </select>
            </div>
        </div>
        <div class="table-zone">
            <table class="list-table">
            <colgroup>
                <col style="width: ;">
                <col style="width: ;">
                <col style="width: ;">
                <col style="width: ;">
            </colgroup>
            <thead>
                <tr>
                    <th>No <button class="tp-btn"><svg-icon type="mdi" :width="15" :height="15" :path="settingPath" :color="'#213f99'"></svg-icon></button></th>
                    <th>user ID <button class="tp-btn"><svg-icon type="mdi" :width="15" :height="15" :path="settingPath" :color="'#213f99'"></svg-icon></button></th>
                    <th>이름 <button class="tp-btn"><svg-icon type="mdi" :width="15" :height="15" :path="settingPath" :color="'#213f99'"></svg-icon></button></th>
                    <th>생년월일 <button class="tp-btn"><svg-icon type="mdi" :width="15" :height="15" :path="settingPath" :color="'#213f99'"></svg-icon></button></th>
                    <th>성별 <button class="tp-btn"><svg-icon type="mdi" :width="15" :height="15" :path="settingPath" :color="'#213f99'"></svg-icon></button></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>test123</td>
                    <td>홍길동</td>
                    <td>1999.01.01</td>
                    <td>여</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>test123</td>
                    <td>홍길동</td>
                    <td>1999.01.01</td>
                    <td>여</td>
                </tr>
            </tbody>
        </table>
        </div>
    </div>
</template>

<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiCog } from '@mdi/js';

export default {
    data() {
        return {
            settingPath: mdiCog
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

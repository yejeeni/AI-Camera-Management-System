<template>
    <div class="search-bar">
        <div class="option-searchbar">
            <div class="search-top flex justify-center">
                <div class="flex align-center">
                    <input type="date" name="" id="" class="square-date">
                    <span class="coupler">~</span>
                    <input type="date" name="" id="" class="square-date">
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
            <div class="search-bottom">
                <div class="flex justify-between">
                    <div class="flex flex30 align-center justify-center">
                    <span class="option-title">단일조건</span>
                        <div class="input-container flex">
                            <label class="radio-label">
                                <input type="radio" name="radio" checked="" class="custom-radiobox ">
                                <span>HTML</span>
                            </label>
                            <label class="radio-label">
                                <input type="radio" name="radio" class="custom-radiobox ">
                                <span>CSS</span>
                            </label>
                            <label class="radio-label">
                                <input type="radio" name="radio" class="custom-radiobox ">
                                <span>Javascript</span>
                            </label>
                        </div>
                    </div>
                    <div class="flex flex30 align-center justify-center">
                    <span class="option-title">다중조건</span>
                        <div class="input-container flex">
                            <label class="check-label">
                                <input type="checkbox" name="check" checked="" class="custom-checkbox ">
                                <span>HTML</span>
                            </label>
                            <label class="check-label">
                                <input type="checkbox" name="check" class="custom-checkbox ">
                                <span>CSS</span>
                            </label>
                            <label class="check-label">
                                <input type="checkbox" name="check" class="custom-checkbox ">
                                <span>Javascript</span>
                            </label>
                        </div>
                    </div>
                    <div class="flex flex30 align-center justify-center">
                    <span class="option-title">다중조건</span>
                        <div class="input-container flex">
                            <label class="check-label">
                                <input type="checkbox" name="check" checked="" class="custom-checkbox ">
                                <span>HTML</span>
                            </label>
                            <label class="check-label">
                                <input type="checkbox" name="check" class="custom-checkbox ">
                                <span>CSS</span>
                            </label>
                            <label class="check-label">
                                <input type="checkbox" name="check" class="custom-checkbox ">
                                <span>Javascript</span>
                            </label>
                        </div>
                    </div>
                </div>
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
            searchPath: mdiMagnify
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

<style>
.search-top {
    padding: 15px 0;
    border-bottom: 1px solid #aaa;
}

/* radio css */
.input-container label {
    display: flex;
    cursor: pointer;
    font-weight: 500;
    position: relative;
    overflow: hidden;
    margin-bottom: 3px;
}

.input-container label input.custom-radiobox,
.input-container label input.custom-checkbox {
    position: absolute;
    left: -9999px;
}

.input-container label input.custom-radiobox:checked+span {
    background-color: #5b72b8;
    color: #fff;
}

.input-container label input.custom-checkbox:checked+span {
    background-color: #f8bb59;
    color: #fff;
}

.input-container label input.custom-radiobox:checked+span:before {
    box-shadow: inset 0 0 0 4px #213f99;
}

.input-container label input.custom-checkbox:checked+span:before {
    box-shadow: inset 0 0 0 4px #ff9d00;
}

.input-container label span {
    display: flex;
    align-items: center;
    padding: 3px 7px;
    border-radius: 10px;
    transition: 0.25s ease;
    color: #333;
}


.input-container label.radio-label span:hover {
    background-color: #d6d6e5;
}

.input-container label.check-label span:hover {
    background-color: #f4e3c2;
}

.input-container label.radio-label span:before,
.input-container label.check-label span:before {
    display: flex;
    flex-shrink: 0;
    content: "";
    background-color: #fff;
    width: 15px;
    height: 15px;
    border-radius: 50%;
    margin-right: 3px;
    transition: 0.25s ease;
    box-shadow: inset 0 0 0 1px #333;
}

.input-container label.check-label span:before {
    border-radius: 0%;
}
</style>
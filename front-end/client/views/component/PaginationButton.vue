<template lang="html">
	<div class="component-pagination" id="pagination">
		<div class="pagination-button-type">
			<a class="first-page" @click="excute(1)" title="첫 번 째 페이지로 이동"></a>
			<a class="prev" @click="excute(currentPage - 1)" title="이전 페이지로 이동"></a>
			<a @click="excute(i)" v-for="i in createRange" :class="{ 'active': currentPage == i }">{{ i }}</a>
			<a class="next" @click="excute(currentPage + 1)" title="다음 페이지로 이동"></a>
			<a class="end-page" @click="excute(maxEndPage)" title="마지막 페이지로 이동"></a>
		</div>
	</div>
</template>

<script>
export default {
	props: {
		currentPage: {
			type: Number,
			default: 0
		},
		perPage: {
			type: Number,
			default: 10
		},
		totalCount: {
			type: Number,
			default: 0
		},
		maxRange: {
			type: Number,
			default: 5
		},
		click: Function
	},
	emits: ['update:currentPage'],
	data: function () {
		return {
			//data
		}
	},
	methods: {
		excute: function (i) {
			if (i >= 1 && i <= this.maxEndPage) {
				if (i != this.currentPage) {
					this.$emit('update:currentPage', i);//부모 currentPage에 선택한 page번호 할당
					this.click(i);//부모 function 실행
				} else {
					return;
				}
			} else {
				alert('이동할 페이지가 없습니다.');
			}
		}
	},
	computed: {
		startPage: function () {
			return Math.floor((this.currentPage - 1) / this.maxRange) * this.maxRange + 1;
		},
		endPage: function () {
			if (this.maxEndPage < this.currentEndPage) {
				return this.maxEndPage;
			} else {
				return this.currentEndPage;
			}
		},
		currentEndPage: function () {
			return this.maxRange * Math.ceil((this.currentPage / this.maxRange));
		},
		maxEndPage: function () {
			return Math.ceil(this.totalCount / this.perPage);
		},
		createRange: function () {
			var range = [];
			for (var i = this.startPage; i <= this.endPage; i++) {
				range.push(i);
			}
			if (range.length == 0) {
					range.push(1);
			}
			return range;
		}
	},
	watch: {

	},
	//beforeCreate: function () {},
	//created: function () {},
	//beforeUpdate: function () {},
	//updated: function () {},
	mounted: function () {
	}
}
</script>

<style scoped>

</style>
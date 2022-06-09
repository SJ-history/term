<!--eslint-disable-->
<template>
  <div>
    <table class="table table-striped col-sm-12">
      <thead>
        <tr>
          <th scope="col" class="col-sm-1">예약자</th>
          <th scope="col" class="col-sm-3">예약자 연락처</th>
          <th scope="col" class="col-sm-3">예약자 이메일</th>
          <th scope="col" class="col-sm-2">예약 날짜</th>
          <th scope="col" class="col-sm-2">예약 인원</th>
        </tr>
      </thead>
      <tbody v-for="(p, i) in paginatedData" :key="i">
        <tr>
          <td>{{ p.user_name }}</td>
          <td>{{ p.phone1 }}-{{ p.phone2 }}-{{ p.phone3 }}</td>
          <td>{{ p.email1 }}@{{ p.email2 }}</td>
          <td>{{ p.class_date }}</td>
          <td>{{ p.reserve_num }}명 <span v-if="p.class_date <= today">(참여 완료)</span></td>
        </tr>
        <tr v-if="listArray.length == 0">
          <td colspan="5">예약 인원이 없습니다.</td>
        </tr>
      </tbody>
      <tbody v-if="listArray.length == 0">
        <tr >
          <td class="noMember" colspan="5">예약 인원이 없습니다.</td>
        </tr>
      </tbody>
    </table>
    <div class="btn-cover">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn btn btn-danger">이전</button>
      <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn btn btn-danger">다음</button>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  data() {
    return {
      today:new Date(new Date().getTime()).toISOString().slice(0, -14),
      pageNum: 0,
      isValue: ""
    };
  },
  props: {
    listArray: { type: Array, required: true },
    pageSize: { type: Number, required: false, default: 10 }
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
  },
  computed: {
    pageCount() {
      let listLeng = this.listArray.length;
      let listSize = this.pageSize;
      let page = Math.floor((listLeng - 1) / listSize) + 1;
      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize;
      const end = start + this.pageSize;
      return this.listArray.slice(start, end);
    }
  }
};
</script>

<style scoped>
.btn-cover {
  margin-top: 1.5rem;
  text-align: center;
}
.btn-cover .page-btn {
  width: 5rem;
}
.btn-cover .page-count {
  padding: 0 1rem;
}
.noMember {
  padding: 20% 40% 20% 40%;
}
</style>

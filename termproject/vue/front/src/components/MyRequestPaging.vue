<!--eslint-disable-->
<template>
  <div>
    <table class="table table-striped col-sm-12">
      <thead>
        <tr>
          <th scope="col" class="col-sm-2">요청 날짜.</th>
          <th scope="col" class="col-sm-2">유형.</th>
          <th scope="col" class="col-sm-6">제목.</th>
          <th scope="col" class="col-sm-2">처리.</th>
        </tr>
      </thead>
      <tbody v-for="(p, i) in paginatedData" :key="i">
        <tr :id="p.request_no" @click="active">
          <td>{{ p.request_date.substring(0, 10) }}</td>
          <td>{{ p.request_type }}</td>
          <td>{{ p.request_title }}</td>
          <td>{{ p.request_condition == "N" ? "대기중" : "처리완료" }}</td>
        </tr>
        <tr :id="p.request_no + 'detail'" style="display:none">
          <td colspan="4" style="border:outset">
            <table class="table table-hover">
              <tbody>
                <tr>
                  <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                    클래스 명
                  </th>
                  <td scope="col" class="col-sm-8" style="padding-left:20px">
                    {{ p.title }}
                  </td>
                </tr>
                <tr>
                  <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                    클래스 날짜
                  </th>
                  <td scope="col" class="col-sm-8" style="padding-left:20px">
                    {{ p.start_date }} ~ {{ p.end_date }}
                  </td>
                </tr>
                <tr>
                  <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                    클래스 주제
                  </th>
                  <td scope="col" class="col-sm-8" style="padding-left:20px">
                    {{ p.head }}
                  </td>
                </tr>
                <tr>
                  <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                    요청 내용
                  </th>
                  <td scope="col" class="col-sm-8" style="padding-left:20px">
                    {{ p.request_content }}
                  </td>
                </tr>
              </tbody>
            </table>
          </td>
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
    active(event) {
      if (document.getElementById(event.currentTarget.id + "detail").style.display == "none") {
        document.getElementById(event.currentTarget.id + "detail").style.display = "table-row";
        if (this.isValue != "") {
          if (event.currentTarget.id == this.isValue.id) {
            return false;
          }
          document.getElementById(this.isValue.id + "detail").style.display = "none";
        }
      } else {
        document.getElementById(event.currentTarget.id + "detail").style.display = "none";
      }
      this.isValue = event.currentTarget;
    }
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
</style>

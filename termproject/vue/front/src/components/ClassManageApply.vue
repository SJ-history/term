<!--eslint-disable-->
<template>
  <div class="container">
    <form @submit.prevent="onSubmit">
      <div class="container col-sm-6">
        <h3 style="font-weight:bold">클래스 변경 신청</h3>
        <br />

        <table class="table table-hover col-sm-12">
          <tbody>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                클래스 명
              </th>
              <td scope="col" class="col-sm-8" style="padding-left:20px">
                {{ content.title }}
              </td>
            </tr>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                클래스 날짜
              </th>
              <td scope="col" class="col-sm-8" style="padding-left:20px">
                {{ content.start_date }} ~ {{ content.end_date }}
              </td>
            </tr>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                클래스 주제
              </th>
              <td scope="col" class="col-sm-8" style="padding-left:20px">
                {{ content.head }}
              </td>
            </tr>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                요청 유형
              </th>
              <td scope="col" class="col-sm-8">
                <select name="request" class="form-control" v-model="request_type">
                  <option value="" selected="selected">요청 유형을 선택해주세요.</option>
                  <option value="클래스 삭제">클래스 삭제</option>
                  <option value="클래스 날짜 변경">클래스 날짜 변경</option>
                  <option value="기타 요청">기타 요청</option>
                </select>
              </td>
            </tr>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                요청 제목
              </th>
              <td scope="col" class="col-sm-8">
                <input type="text" class="form-control" placeholder="제목" v-model="request_title" />
              </td>
            </tr>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                요청 내용
              </th>
              <td scope="col" class="col-sm-8">
                <textarea type="text" placeholder="요청 사항 내용" v-model="request_content" class="form-control" rows="8" style="resize:none" />
              </td>
            </tr>
          </tbody>
        </table>

        <div class="row">
          <button @click.prevent="goBack" class="btn_write_review1">취소</button>
          <button @click.prevent="manageRequest" class="btn_write_review2">요청 등록</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
/* eslint-disable */

export default {
  data() {
    return {
      content_no: this.$route.query.content_no,
      content: [],
      request_type: "",
      request_title: "",
      request_content: ""
    };
  },
  mounted() {
    this.classManageList();
  },
  methods: {
    classManageList() {
      const params = new URLSearchParams();
      params.append("content_no", this.content_no);

      this.$axios.post("/agency/classDetail", params).then(res => {
        this.content = res.data;
      });
    },
    goBack() {
      this.$router.go(-1);
    },
    manageRequest() {
      if (this.request_type == "") {
        alert("요청 유형을 등록해주세요.");
        return false;
      } else if (this.request_title == "" || this.request_title.length > 100) {
        alert("요청 제목을 등록해주세요.\n최대 100글자");
        return false;
      } else if (this.request_content == "") {
        alert("요청 내용을 등록해주세요.");
        return false;
      }

      const params = new URLSearchParams();
      params.append("content_no", this.content_no);
      params.append("user_id", this.$session.get("userInfo"));
      params.append("request_type", this.request_type);
      params.append("request_title", this.request_title);
      params.append("request_content", this.request_content);
      this.$axios.post("/agency/classRequest", params).then(res => {
        if (res.data > 0) {
          alert("요청이 등록되었습니다.\n 결과는 마이페이지에서 확인할 수 있습니다.");
        } else {
          alert("요청 실패!");
        }
        window.location.href = "/";
      });
    }
  }
};
</script>

<style scoped>
.btn_write_review1 {
  display: block;
  border: 1px solid #ff5862;
  color: #ff5862;
  height: 46px;
  line-height: 42px;
  width: 50%;
  text-align: center;
  font-weight: bold;
  font-size: 14px;
  border-radius: 5px;
}
.btn_write_review2 {
  display: block;
  border: 1px solid #ff5862;
  background-color: #ff5862;
  color: white;
  height: 46px;
  line-height: 42px;
  width: 50%;
  text-align: center;
  font-weight: bold;
  font-size: 14px;
  border-radius: 5px;
}
</style>

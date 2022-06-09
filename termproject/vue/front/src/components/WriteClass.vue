<!--eslint-disable-->
<template>
  <div class="container">
    <form @submit.prevent="onSubmit">
      <div class="container col-sm-6">
        <h3 style="font-weight:bold">클래스 등록하기</h3>
        <br />

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="title" style="font-weight:bold">제목</label>
          </div>
          <div class="col-sm-8">
            <input type="text" class="form-control underline" placeholder="제목을 입력해주세요." v-model="title" id="title" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="agency" style="font-weight:bold">작성자</label>
          </div>
          <div class="col-sm-8">
            <input type="text" class="form-control underline" style="background-color:white" :value="agency" id="agency" readonly />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="ps_num" style="font-weight:bold">수강 인원</label>
          </div>
          <div class="col-sm-8">
            <input type="number" class="form-control underline" min="1" v-model="ps_num" id="ps_num" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="category" style="font-weight:bold">카테고리 분류</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-3">
                <input type="radio" name="category" value="디저트" id="디저트" v-model="category" />
                <label for="디저트">디저트</label>
              </div>
              <div class="col-sm-3">
                <input type="radio" name="category" value="패션/잡화" id="패션/잡화" v-model="category" />
                <label for="패션/잡화">패션/잡화</label>
              </div>
              <div class="col-sm-3">
                <input type="radio" name="category" value="리빙" id="리빙" v-model="category" />
                <label for="리빙">리빙</label>
              </div>
              <div class="col-sm-3">
                <input type="radio" name="category" value="기타" id="기타" v-model="category" />
                <label for="기타">기타</label>
              </div>
            </div>
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="image0" style="font-weight:bold">썸네일 등록</label>
          </div>
          <div class="col-sm-8">
            <v-file-input label="썸네일 이미지 등록" id="image0" accept="image/*" @change="selectFile0" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="head" style="font-weight:bold">주제</label>
          </div>
          <div class="col-sm-8">
            <input type="text" class="form-control underline" placeholder="주제를 입력해주세요." v-model="head" id="head" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="body" style="font-weight:bold">본문</label>
          </div>
          <div class="col-sm-8">
            <textarea type="text" placeholder="상세 내용" id="body" v-model="body" class="form-control" rows="8" style="resize:none" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="image1" style="font-weight:bold">이미지 등록</label>
          </div>
          <div class="col-sm-8">
            <v-file-input label="참고 이미지 등록" id="image1" @change="selectFile1" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="youtube" style="font-weight:bold">첨부 영상</label>
          </div>
          <div class="col-sm-8">
            <input type="text" class="form-control underline" placeholder="유튜브 url을 입력해주세요.(선택)" v-model="youtube" id="youtube" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="ADDR" style="font-weight:bold">클래스 장소</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-8">
                <input type="text" placeholder="주소" v-model="cl_addr" id="cl_addr" class="form-control" />
              </div>
              <div class="col-sm-4">
                <button id="ADDR" class="btn btn-warning form-control" @click.prevent="postSearch">주소 검색</button>
              </div>
            </div>
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="start_date" style="font-weight:bold">클래스 일정</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-6">
                <label for="start_date" style="font-weight:bold">시작일</label>
                <input type="date" class="form-control" :min="date" v-model="start_date" id="start_date" @change="end_date = ''" />
              </div>
              <div class="col-sm-6">
                <label for="end_date" style="font-weight:bold">마감일</label>
                <input :disabled="start_date == ''" type="date" class="form-control" :min="start_date" v-model="end_date" id="end_date" />
              </div>
            </div>
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="start_time" style="font-weight:bold">클래스 시간</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-6">
                <label for="start_time" style="font-weight:bold">시작 시간</label>
                <input type="time" class="form-control" v-model="start_time" id="start_time" />
              </div>

              <div class="col-sm-6">
                <label for="end_time" style="font-weight:bold">종료 시간</label>
                <input :disabled="start_time == ''" type="time" class="form-control" :min="start_time" v-model="end_time" id="end_time" />
              </div>
            </div>
          </div>
        </div>
        <br />
        <button type="submit" class="btn btn-info form-control">클래스 등록</button>
      </div>
    </form>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";

export default {
  data() {
    return {
      title: "",
      agency: "",
      ps_num: "",
      category: "",
      image0: "",
      head: "",
      body: "",
      image1: "",
      youtube: "",
      cl_addr: "",
      start_date: "",
      end_date: "",
      start_time: "",
      end_time: "",
      date: new Date(new Date().getTime()).toISOString().slice(0, -14)
    };
  },
  mounted() {
    this.AGENCY();
  },
  methods: {
    postSearch() {
      new window.daum.Postcode({
        oncomplete: data => {
          if (data.jibunAddress == "") {
            this.cl_addr = data.roadAddress;
          } else {
            this.cl_addr = data.jibunAddress;
          }
        }
      }).open();
    },
    AGENCY() {
      const params = new URLSearchParams();
      params.append("user_id", this.$session.get("userInfo"));
      axios.post("/agency/info", params).then(res => {
        this.agency = res.data;
      });
    },
    onSubmit() {
      if (this.title == "" || this.title.length > 60) {
        alert("제목을 입력해주세요.\n최대 60글자");
        document.getElementById("title").focus();
        return false;
      } else if (this.ps_num == "") {
        alert("수강 인원을 설정해주세요.");
        document.getElementById("ps_num").focus();
        return false;
      } else if (this.category == "") {
        alert("카테고리를 설정해주세요.");
        return false;
      } else if (this.image0 == "") {
        alert("썸네일 이미지를 등록해주세요.");
        document.getElementById("image0").focus();
        return false;
      } else if (this.head == "" || this.head.length > 100) {
        alert("주제를 등록해주세요.\n최대 100글자");
        document.getElementById("head").focus();
        return false;
      } else if (this.body == "") {
        alert("본문 내용을 등록해주세요.");
        document.getElementById("body").focus();
        return false;
      } else if (this.body.length > 200000) {
        alert("본문 내용을 등록해주세요.\n최대 200000글자");
        document.getElementById("body").focus();
        return false;
      } else if (this.image1 == "") {
        alert("참고 이미지를 등록해주세요.");
        document.getElementById("image1").focus();
        return false;
      } else if (this.cl_addr == "") {
        alert("클래스 장소를 등록해주세요.");
        document.getElementById("cl_addr").focus();
        return false;
      } else if (this.start_date == "") {
        alert("클래스 시작날짜를 등록해주세요.");
        document.getElementById("start_date").focus();
        return false;
      } else if (this.end_date == "") {
        alert("클래스 마감날짜를 등록해주세요.");
        document.getElementById("end_date").focus();
        return false;
      } else if (this.start_time == "") {
        alert("클래스 시작 시간을 등록해주세요.");
        document.getElementById("start_time").focus();
        return false;
      } else if (this.end_time == "") {
        alert("클래스 종료 시간을 등록해주세요.");
        document.getElementById("end_time").focus();
        return false;
      }

      const formData = new FormData();
      formData.append("user_id", this.$session.get("userInfo"));
      formData.append("title", this.title);
      formData.append("agency", this.agency);
      formData.append("ps_num", this.ps_num);
      formData.append("category", this.category);
      formData.append("img0", this.image0);
      formData.append("head", this.head);
      formData.append("body", this.body);
      formData.append("img1", this.image1);
      if(this.youtube != ''){
        formData.append("youtube", this.youtube);
      }
      formData.append("cl_addr", this.cl_addr);
      formData.append("start_date", this.start_date);
      formData.append("end_date", this.end_date);
      formData.append("start_time", this.start_time);
      formData.append("end_time", this.end_time);

      axios.post("/agency/writeClass", formData, { headers: { "Content-Type": "multipart/form-data" } }).then(res => {
        if (res.data) {
          alert("클래스 등록이 완료되었습니다.");
          window.location.href = "/";
        } else {
          alert("등록 실패");
        }
      });
    },
    selectFile0(file) {
      this.image0 = file;
    },
    selectFile1(file) {
      this.image1 = file;
    }
  }
};
</script>

<style scoped>
.underline {
  border: none;
  border-bottom: 1px solid #ccc;
}
</style>

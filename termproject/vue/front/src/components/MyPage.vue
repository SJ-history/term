<!--eslint-disable-->
<template>
  <div class="container">
    <div class="container col-sm-6 text-center">
      <h3 style="font-weight:bold">마이 페이지</h3>
      <br />
      <!-- 솜씨당 Middle바 Start-->
      <v-main style="padding:0px;">
        <v-bottom-navigation color="#2d3753" horizontal>
          <a @click="myinfo" class="v-btn" id="mi" style="text-decoration:underline">
            <span style="font-weight:bold">내 정보</span>
          </a>
          <a @click="reserve" class="v-btn" id="re">
            <span style="font-weight:bold">나의 클래스</span>
          </a>
          <a @click="interest" class="v-btn" id="ir">
            <span style="font-weight:bold">관심 목록</span>
          </a>
          <a v-if="this.$session.get('isSeller') == 'Y'" @click="myClass" class="v-btn" id="mc">
            <span style="font-weight:bold">클래스 관리</span>
          </a>
          <a v-if="this.$session.get('isSeller') == 'Y'" @click="myRequest" class="v-btn" id="mr">
            <span style="font-weight:bold">요청 목록</span>
          </a>
          <a class="v-btn" @click="showModal = true">
            <span style="font-weight:bold">회원 탈퇴</span>
          </a>
        </v-bottom-navigation>
      </v-main>
      <!-- 솜씨당 Middle바 End-->
    </div>

    <MyPageInfo v-if="myPageInfoTr" />
    <MyClass v-if="myClassTr" :classList="classList" />
    <FavoriteList v-if="favoriteListTr" :classList="classList" />
    <ClassManage v-if="classManageTr" :classList="classList" />
    <MyRequest v-if="myRequestTr" />

    <Teleport to="body">
      <!-- use the modal component, pass in the prop -->
      <modal :show="showModal" @close="showModal = false" @withdrawal="withdrawal">
        <template #header>
          <h3>회원 탈퇴</h3>
        </template>
      </modal>
    </Teleport>
  </div>
</template>

<script>
/*eslint-disable*/
import Modal from "@/components/Modal.vue";
import MyPageInfo from "@/components/MyPageInfo.vue";
import MyClass from "@/components/MyClass.vue";
import FavoriteList from "@/components/FavoriteList.vue";
import ClassManage from "@/components/ClassManage.vue";
import MyRequest from "@/components/MyRequest.vue";

export default {
  components: {
    Modal,
    MyPageInfo,
    MyClass,
    FavoriteList,
    ClassManage,
    MyRequest
  },
  data() {
    return {
      showModal: false,
      myPageInfoTr: true,
      myClassTr: false,
      favoriteListTr: false,
      classManageTr: false,
      myRequestTr: false,
      classList:[],
    };
  },
  methods: {
    myinfo() {
      this.myPageInfoTr = true;
      this.myClassTr = false;
      this.favoriteListTr = false;
      this.classManageTr = false;
      this.myRequestTr = false;
      document.getElementById("mi").style.textDecoration = "underline";
      document.getElementById("re").style.textDecoration = "";
      document.getElementById("ir").style.textDecoration = "";
      if (this.$session.get("isSeller") == "Y") {
        document.getElementById("mc").style.textDecoration = "";
        document.getElementById("mr").style.textDecoration = "";
      }
    },
    reserve() {
      const params = new URLSearchParams();
      params.append('user_id', this.$session.get('userInfo'));
      params.append('act', 0);
      this.$axios.post("/api/myClassList", params).then((res) => {
          this.classList = res.data;
      });

      this.myClassTr = true;
      this.myPageInfoTr = false;
      this.favoriteListTr = false;
      this.classManageTr = false;
      this.myRequestTr = false;
      document.getElementById("mi").style.textDecoration = "";
      document.getElementById("re").style.textDecoration = "underline";
      document.getElementById("ir").style.textDecoration = "";
      if (this.$session.get("isSeller") == "Y") {
        document.getElementById("mc").style.textDecoration = "";
        document.getElementById("mr").style.textDecoration = "";
      }
    },
    interest() {
      const params = new URLSearchParams();
      params.append("user_id", this.$session.get("userInfo"));
      this.$axios.post("/agency/favoriteClass", params).then(res => {
        this.classList = res.data;
      });

      this.favoriteListTr = true;
      this.myPageInfoTr = false;
      this.myClassTr = false;
      this.classManageTr = false;
      this.myRequestTr = false;
      document.getElementById("mi").style.textDecoration = "";
      document.getElementById("re").style.textDecoration = "";
      document.getElementById("ir").style.textDecoration = "underline";
      if (this.$session.get("isSeller") == "Y") {
        document.getElementById("mc").style.textDecoration = "";
        document.getElementById("mr").style.textDecoration = "";
      }
    },
    myClass() {
      const params = new URLSearchParams();
      params.append("user_id", this.$session.get("userInfo"));
      params.append("state", 0);
      this.$axios.post("/agency/myClassList", params).then(res => {
        this.classList = res.data;
      });

      this.classManageTr = true;
      this.myPageInfoTr = false;
      this.myClassTr = false;
      this.favoriteListTr = false;
      this.myRequestTr = false;
      document.getElementById("mi").style.textDecoration = "";
      document.getElementById("re").style.textDecoration = "";
      document.getElementById("ir").style.textDecoration = "";
      document.getElementById("mc").style.textDecoration = "underline";
      document.getElementById("mr").style.textDecoration = "";
    },
    myRequest() {
      this.myRequestTr = true;
      this.myPageInfoTr = false;
      this.myClassTr = false;
      this.favoriteListTr = false;
      this.classManageTr = false;
      document.getElementById("mi").style.textDecoration = "";
      document.getElementById("re").style.textDecoration = "";
      document.getElementById("ir").style.textDecoration = "";
      document.getElementById("mc").style.textDecoration = "";
      document.getElementById("mr").style.textDecoration = "underline";
    },
    withdrawal() {
      alert("탈퇴페이지로 이동합니다.\n비밀번호를 입력해주세요.");
      window.location.href = "/withdraw";
    }
  }
};
</script>

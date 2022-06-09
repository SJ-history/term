<template>
  <v-app id="app">
    <v-main style="margin:0px auto">
      <h1 style="text-align:center; margin-top:30%">본인 인증</h1>
      <img src="../assets/img/logo.jpg" />
      <div class="login-block">
        <button class="btn btn-info form-control" style="height: 48px; font-size:16px" @click="emailAccess">
          이메일로 인증하기
        </button>
        <hr />
        <div class="login-btn-box">
          <div class="login-btn-kakao">
            &emsp;<a @click="kakaoAccess">카카오톡으로 인증하기</a>
          </div>
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
/* eslint-disable */
import axios from "axios";

export default {
  methods: {
    kakaoAccess() {
      var user_id = this.$session.get("userInfo");
      // Kakao Developers에서 요약 정보 -> JavaScript 키
      window.Kakao.init("ffaa85e1d4b540c962a43864357d870e");
      window.Kakao.Auth.login({
        success: function() {
          window.Kakao.API.request({
            url: "/v2/user/me",
            data: { property_keys: ["kakao_account.email"] },
            success: async function(response) {
              const params = new URLSearchParams();
              params.append("access_mail", response.kakao_account.email);
              params.append("user_id", user_id);

              await axios.post("/api/sellerApply", params).then(res => {
                if (res.data) {
                  alert("인증이 완료되었습니다.\n작가등록 승인까지 최소1~3일 정도 소요됩니다.");
                  window.location.href = "/";
                } else {
                  alert("인증 실패");
                }
              });
            },
            fail: function(error) {
              console.log(error);
            }
          });
        },
        fail: function(error) {
          console.log(error);
        }
      });
    },
    emailAccess() {
      window.location.href = "/emailAccess";
    }
  }
};
</script>

<style scoped>
.login-block {
  position: absolute;
  width: 300px;
  top: 35%;
  left: 50%;
  margin: 0 0 0 -150px;
}
.login-btn-box .login-btn-kakao::before {
  position: absolute;
  top: 95px;
  left: 30px;
  width: 40px;
  height: 21px;
  background-image: url(../assets/img/login_kakaotalk.svg);
  background-size: 24px 21px;
  content: "";
}
.login-btn-box .login-btn-kakao a {
  display: block;
  border-radius: 5px;
  background-color: #ffeb00;
  line-height: 48px;
  height: 48px;
  text-align: center;
  padding-left: 60px;
  font-size: 16px;
  color: #3c1e1e;
  transition: 0.3s all;
  cursor: pointer;
  margin-top: -25px;
}
</style>

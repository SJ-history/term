<!-- eslint-disable -->
<template>
  <v-app id="app">
    <v-main v-if="!codeChecked" style="margin:0px auto" id="id1">
      <h1 style="text-align:center;margin-top:20%">이메일 인증</h1>
      <br />
      <v-container style="position: relative;" class="text-xs-center">
        <v-layout row wrap class="text-xs-center">
          <v-flex>
            <v-card flat class="mx-auto" max-width="800">
              <v-row>
                <v-col>
                  <v-form style="width: 400px; height: 300px; margin-top:20%">
                    <div class="mx-3">
                      <v-icon color="black" size="30px">email</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="사용자 이메일 입력" v-model="email1" id="email1" required />
                      </div>
                    </div>
                    <div v-if="inCode" class="mx-3">
                      <v-icon color="black" size="30px">title</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="인증번호 입력" v-model="codeNumber" required />
                      </div>
                    </div>
                    <v-card-actions>
                      <v-btn color="#2c4f91" dark large block @click.prevent="emailAccess" id="wd">인증번호 전송</v-btn>
                    </v-card-actions>
                  </v-form>
                </v-col>
              </v-row>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
/* eslint-disable */
export default {
  name: "wdSearch",
  data() {
    return {
      inCode: false,
      email1: "",
      codeNumber: "",
      code: ""
    };
  },
  methods: {
    emailAccess() {
      if (!this.inCode) {
        const params = new URLSearchParams();
        params.append("user_id", this.$session.get("userInfo"));
        params.append("email1", this.email1);

        this.$axios.post("/api/emailAccess", params).then(res => {
          if (res.data) {
            this.inCode = !this.inCode;
            document.getElementById("email1").readOnly = true;
            document.getElementById("wd").innerText = "인증번호 확인";
            alert("인증번호를 전송했습니다. \nGmail 이용자는 스팸메일함을 확인해주세요.");
            this.code = res.data;
          } else {
            alert("일치하는 이메일이 없습니다.\n가입시 입력한 이메일을 입력해주세요.");
          }
        });
      } else {
        if (this.codeNumber == this.code) {
          const params = new URLSearchParams();
          params.append("user_id", this.$session.get("userInfo"));
          params.append("access_mail", this.email1);

          this.$axios.post("/api/sellerApply", params).then(res => {
            if (res.data) {
              alert("인증이 완료되었습니다.\n작가등록 승인까지 최소1~3일 정도 소요됩니다.");
              window.location.href = "/";
            } else {
              alert("인증 실패");
            }
          });
        } else {
          alert("인증번호가 일치하지 않습니다.");
        }
      }
    }
  }
};
</script>

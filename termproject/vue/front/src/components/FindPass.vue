<!-- eslint-disable -->
<template>
  <v-app id="app">
    <v-main v-if="!codeChecked" style="margin:0px auto" id="id1">
      <h1 style="text-align:center;margin-top:20%">비밀번호 찾기</h1>
      <br />
      <v-container style="position: relative;" class="text-xs-center">
        <v-layout row wrap class="text-xs-center">
          <v-flex>
            <v-card flat class="mx-auto" max-width="800">
              <v-row>
                <v-col>
                  <v-form style="width: 400px; height: 300px; margin-top:20%">
                    <div class="mx-3">
                      <v-icon color="black" size="30px">person</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="사용자 아이디 입력" v-model="user_id" id="user_id" required />
                      </div>
                    </div>
                    <div v-if="inId" class="mx-3">
                      <v-icon color="black" size="30px">email</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="사용자 이메일 입력" v-model="email1" id="email1" required />
                      </div>
                    </div>
                    <div v-if="inWd" class="mx-3">
                      <v-icon color="black" size="30px">title</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="인증번호 입력" v-model="codeNumber" required />
                      </div>
                    </div>
                    <v-card-actions>
                      <v-btn color="#2c4f91" dark large block @click.prevent="wdSearch" id="wd">아이디 확인</v-btn>
                    </v-card-actions>
                  </v-form>
                </v-col>
              </v-row>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>

    <v-main v-if="codeChecked" style="margin:0px auto" id="id1">
      <h1 style="text-align:center;margin-top:20%">비밀번호 변경</h1>
      <br />
      <v-container style="position: relative;" class="text-xs-center">
        <v-layout row wrap class="text-xs-center">
          <v-flex>
            <v-card flat class="mx-auto" max-width="800">
              <v-row>
                <v-col>
                  <div style="width: 400px; height: 300px; margin-top:20%">
                    <div class="mx-3">
                      <v-icon color="black" size="30px">person</v-icon>
                      <div class="mx-1">
                        <v-text-field v-model="user_id" id="user_id" required readonly />
                      </div>
                    </div>
                    <div class="mx-3">
                      <v-icon color="black" size="30px">lock</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="새로운 비밀번호 입력" type="password" v-model="newPass" required />
                      </div>
                    </div>
                    <v-card-actions>
                      <v-btn color="#2c4f91" dark large block @click.prevent="passEdit">비밀번호 변경 하기</v-btn>
                    </v-card-actions>
                  </div>
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
      inId: false,
      inWd: false,
      codeChecked: false,
      user_id: "",
      email1: "",
      codeNumber: "",
      code: "",
      newPass: ""
    };
  },
  methods: {
    wdSearch() {
      if (!this.inId) {
        const params = new URLSearchParams();
        params.append("user_id", this.user_id);

        this.$axios.post("/api/compareId", params).then(res => {
          if (res.data == "success") {
            this.inId = !this.inId;
            document.getElementById("user_id").readOnly = true;
            document.getElementById("wd").innerText = "이메일 인증번호 전송";
            alert("아이디가 확인되었습니다. 이메일을 입력해주세요.");
          } else {
            alert("일치하는 회원정보가 없습니다.\n아이디를 확인해주세요.");
          }
        });
      } else if (!this.inWd) {
        const params = new URLSearchParams();
        params.append("user_id", this.user_id);
        params.append("email1", this.email1);

        this.$axios.post("/api/mailSend", params).then(res => {
          if (res.data) {
            this.inWd = !this.inWd;
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
          alert("인증성공\n변경할 비밀번호를 입력해주세요.");
          this.codeChecked = !this.codeChecked;
        } else {
          alert("인증번호가 일치하지 않습니다.");
        }
      }
    },
    passEdit() {
      // 비밀번호 정규식
      var pwTest = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
      if (!pwTest.test(this.newPass)) {
        alert("비밀번호를 확인해주세요!(공백 X)\n특수문자 포함 8자 이상 입력부탁드립니다.");
        return false;
      }
      const params = new URLSearchParams();
      params.append("user_id", this.user_id);
      params.append("user_wd", this.newPass);

      this.$axios.post("/api/passEdit", params).then(res => {
        if (res.data == "success") {
          alert("비밀번호가 변경되었습니다.");
        } else {
          alert("변경 실패");
        }
        window.location.href = "/login";
      });
    }
  }
};
</script>

<template>
  <v-app id="app">
    <v-main style="margin:0px auto">
      <h1 style="text-align:center;margin-top:20%">로그인</h1>
      <br />
      <v-container style="position: relative;" class="text-xs-center">
        <v-layout row wrap class="text-xs-center">
          <v-flex>
            <v-card flat class="mx-auto" max-width="800">
              <v-row>
                <v-col>
                  <v-form @submit.prevent="login()" style="width: 400px; height: 300px; margin-top:20%">
                    <div class="mx-3">
                      <v-icon color="black" size="30px">person</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="사용자 아이디 입력" v-model="user_id" required />
                      </div>
                    </div>
                    <div class="mx-3">
                      <v-icon color="black" size="30px">lock</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="비밀번호 입력" type="password" v-model="user_wd" required />
                      </div>
                    </div>
                    <div>
                      &emsp;<a href="/findId">아이디 찾기</a>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<a href="/findPass">비밀번호 찾기</a>
                    </div>
                    <v-card-actions>
                      <v-btn color="#2c4f91" dark large block type="submit">로그인</v-btn>
                    </v-card-actions>
                    <hr />
                    <v-card-actions>
                      <v-btn color="red" dark large block href="/join">회원 가입</v-btn>
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
  name: "login",
  data() {
    return {
      user_id: "",
      user_wd: ""
    };
  },
  methods: {
    login() {
      const params = new URLSearchParams();
      params.append("user_id", this.user_id);
      params.append("user_wd", this.user_wd);

      this.$axios.post("/api/login", params).then(res => {
        if (res.data.includes("success")) {
          var se = res.data.split("success")[1];
          this.$session.set("userInfo", se.substring(0, se.length - 1));
          this.$session.set("isSeller", se.substring(se.length - 1, se.length));
          alert("로그인 완료");
          window.location.href = "/";
        } else {
          alert(res.data);
        }
      });
    }
  }
};
</script>

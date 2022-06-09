<!-- eslint-disable -->
<template>
  <v-app id="app">
    <v-main v-if="!codeChecked" style="margin:0px auto" id="id1">
      <h1 style="text-align:center;margin-top:20%">회원 탈퇴</h1>
      <br />
      <v-container style="position: relative;" class="text-xs-center">
        <v-layout row wrap class="text-xs-center">
          <v-flex>
            <v-card flat class="mx-auto">
              <v-row>
                <v-col>
                  <v-form style="width: 400px; height: 300px; margin-top:20%">
                    <div class="mx-3">
                      <v-icon color="black" size="30px">person</v-icon>
                      <div class="mx-1">
                        <v-text-field v-model="user_id" id="user_id" required readonly />
                      </div>
                    </div>
                    <div class="mx-3">
                      <v-icon color="black" size="30px">lock</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="비밀번호 입력" type="password" v-model="user_wd" required />
                      </div>
                    </div>
                    <v-card-actions>
                      <v-btn color="#2c4f91" dark large block @click.prevent="wdSearch">회원 탈퇴</v-btn>
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
  data() {
    return {
      user_id: this.$session.get("userInfo"),
      user_wd: ""
    };
  },
  methods: {
    wdSearch() {
      const params = new URLSearchParams();
      params.append("user_id", this.user_id);
      params.append("user_wd", this.user_wd);

      this.$axios.post("/api/withDraw", params).then(res => {
        if (res.data == "success") {
          this.$session.remove("userInfo");
          alert("탈퇴 완료");
          window.location.href = "/login";
        } else {
          alert(res.data);
        }
      });
    }
  }
};
</script>

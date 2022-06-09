<!-- eslint-disable -->

<template>
  <v-app id="app">
    <v-main v-if="!tr" style="margin:0px auto" id="id1">
      <h1 style="text-align:center;margin-top:20%">회원정보 입력</h1>
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
                        <v-text-field placeholder="사용자 이메일 입력" v-model="email1" required />
                      </div>
                    </div>
                    <div class="mx-3">
                      <v-icon color="black" size="30px">phone</v-icon>
                      <div class="mx-1">
                        <v-text-field placeholder="사용자 핸드폰 번호 입력" v-model="phone1" required />
                      </div>
                    </div>
                    <v-card-actions>
                      <v-btn color="#2c4f91" dark large block @click="idSearch">아이디 찾기</v-btn>
                    </v-card-actions>
                  </v-form>
                </v-col>
              </v-row>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>

    <v-main v-if="tr" style="margin:0px auto" id="id2">
      <h1 style="text-align:center;margin-top:20%">회원 아이디</h1>
      <br />
      <v-container style="position: relative;" class="text-xs-center">
        <v-layout row wrap class="text-xs-center">
          <v-flex>
            <v-card flat class="mx-auto" max-width="800">
              <v-row>
                <v-col>
                  <div class="mx-3">
                    <div class="mx-1 row" v-bind:key="dto.id" v-for="dto in list" style="width:450px">
                      <div class="col-sm-6">
                        <v-icon color="black" size="30px">person</v-icon><strong>{{ dto.user_id }}</strong>
                      </div>
                      <div class="col-sm-6">
                        가입일 : {{ dto.join_date.substring(0, 10) }}
                      </div>
                    </div>
                    <br />
                  </div>
                  <v-card-actions>
                    <v-btn color="#2c4f91" dark large block href="/login">로그인</v-btn>
                  </v-card-actions>
                  <hr />
                  <v-card-actions>
                    <v-btn color="red" dark large block href="/findPass">비밀번호 찾기</v-btn>
                  </v-card-actions>
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
  name: "idSearch",
  data() {
    return {
      tr: false,
      email1: "",
      phone1: "",
      list: []
    };
  },
  methods: {
    idSearch() {
      const params = new URLSearchParams();
      params.append("email1", this.email1);
      params.append("phone1", this.phone1);

      if (this.email1 == "") {
        alert("회원가입시 입력한 이메일을 입력해주세요");
        return false;
      } else if (this.phone1 == "") {
        alert("회원가입시 입력한 전화번호를 입력해주세요");
        return false;
      }

      this.$axios.post("/api/idSearch", params).then(res => {
        if (res.data) {
          this.tr = !this.tr;
          this.list = res.data;
        } else {
          alert("일치하는 회원정보가 없습니다.\n이메일과 핸드폰 번호를 확인해주세요.");
          return false;
        }
      });
    }
  }
};
</script>

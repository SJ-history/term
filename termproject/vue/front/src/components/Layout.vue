<!-- eslint-disable -->
<template>
  <v-app id="inspire">
    <!-- 솜씨당 Nav바 Start-->
    <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="accent" dark>
      <v-toolbar-title v-bind:style="[!this.$session.has('userInfo') ? { width: '342px' } : this.$session.get('isSeller') == 'N' ? { width: '498px' } : { width: '514px' }]">
       <a href="/" class="black--text" style="text-decoration:underline; margin-left:60px" ><img :src="require('@/assets/재능거래 플랫폼.png')" /></a>
      </v-toolbar-title>

      <v-spacer />
      <a href="/classList" class="v-btn" style="width:200px"><span>진행중인 클래스</span></a>
      <a href="/endClass" class="v-btn" style="width:200px"><span>마감된 클래스</span></a>
      <v-spacer />

      <!-- Login 전 -->
      <v-btn v-if="this.$session.get('isSeller') == 'S'" color="accent">
        등록 대기중<span class="material-icons">face</span>
      </v-btn>
      <v-btn v-else-if="this.$session.get('isSeller') == 'Y'" href="/writeClass" color="accent">
        클래스 등록<span class="material-icons">face</span>
      </v-btn>
      <v-btn v-else v-bind:href="[!this.$session.has('userInfo') ? '/login' : '/artistChange']" color="accent">
        작가 등록<span class="material-icons">face</span>
      </v-btn>

      <p>&emsp;</p>
      <v-btn v-if="!this.$session.has('userInfo')" href="/login" color="accent">
        Login<span class="material-icons">login</span>
      </v-btn>
      <!-- Login 전 -->

      <!-- Login 후 -->
      <v-btn v-if="this.$session.has('userInfo')" href="/myPage" color="accent">
        My Page<span class="material-icons">account_circle</span>
      </v-btn>
      <p v-if="this.$session.has('userInfo')">&emsp;</p>
      <v-btn v-if="this.$session.has('userInfo')" @click="logout" color="accent">
        Logout<span class="material-icons">logout</span>
      </v-btn>
      <!-- Login 후 -->

      <p>&emsp;</p>
      <v-btn href="/search" color="accent">
        검색<span class="material-icons">search</span>
      </v-btn>
    </v-app-bar>
    <!-- 솜씨당 Nav바 End-->

    <br /><br /><br />

    <router-view />

    <!-- Footer Start -->
    <v-footer :padless="true">
      <v-card flat tile width="100%" class="secondary white--text text-center">
        <v-card-text class="white--text pt-0">
          <p>COPYRIGHT © 재능거래 플랫폼, inc All rights reserved</p>
          <p>본 사이트는 포트 폴리오 사이트로 제작되어 상업적 목적이 아닙니다.</p>
          <!-- <p>본 사이트는 OJT TERMPROJECT 사이트로 제작되어 상업적 목적이 아닙니다.</p> -->
          <p>사용된 일부 이미지 및 폰트 등은 별도의 출처가 있음을 밝혀 드립니다.</p>
          <v-divider></v-divider>
          <v-divider></v-divider>
          <p></p>
          <p>NAME: 박상준 / PHONE:000-2535-3146 / E-MAIL: qlla1452@naver.com</p>
          <!-- <p>NAME: 박상준 / PHONE:000-2535-3146 / E-MAIL: Sangjun.Park@ta9.co.kr</p> -->
        </v-card-text>
      </v-card>
    </v-footer>
    <!--Footer End  -->
  </v-app>
</template>

<script>
/*eslint-disable*/
export default {
  methods: {
    logout() {
      this.$session.destroy();
      window.location.href = "/";
    }
  }
};
</script>

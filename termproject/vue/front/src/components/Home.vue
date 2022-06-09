<!-- eslint-disable -->
<template>
  <div>
    <v-carousel hide-delimiters :cycle="true" :interval="3000">
      <v-carousel-item :src="require('@/assets/title2.jpg')" />
      <v-carousel-item :src="require('@/assets/title4.jpg')" />
      <v-carousel-item :src="require('@/assets/title.jpg')" />
      <v-carousel-item :src="require('@/assets/title3.jpg')" />
      <v-carousel-item :src="require('@/assets/title5.jpg')" />
    </v-carousel>

    <v-container>
      <v-row no-gutters>
        <v-col :cols="12">
          <v-card-text tile outlined>
            <v-card-title class="subheading ">최신 클래스!</v-card-title>
            <v-divider></v-divider>

            <div class="row">
              <div v-for="(list, i) in classList" :key="i" class="col-12 col-md-3 col-sm-6 col-xs-6 text-center">
                <v-hover v-slot:default="{ hover }" open-delay="200">
                  <v-card :elevation="hover ? 16 : 2">
                    <v-img class="white--text align-end" height="200px" :src="'agency/display/' + list.image0" />
                    <a @click="favorite(list.content_no)" v-text="favoriteList.includes(list.content_no) ? 'favorite' : 'favorite_border'" class="material-icons heart" />
                    <v-card-text class="text--primary text-center">
                      <div>{{ textCut(list.title) }}</div>
                      <div>{{ textCut(list.head) }}</div>
                      <div>{{ list.start_date }} ~ {{ list.end_date }}</div>
                    </v-card-text>
                    <div class="text-center">
                      <v-btn :href="'/classDetail?content_no=' + list.content_no" class="ma-2" outlined color="info">예약 하기</v-btn>
                    </div>
                  </v-card>
                </v-hover>
              </div>
            </div>
          </v-card-text>
        </v-col>
      </v-row>
    </v-container>
    <br />
  </div>
</template>

<script>
/*eslint-disable*/
export default {
  data() {
    return {
      classList: [],
      favoriteList: [],
      limitDate: new Date(new Date().getTime()).toISOString().slice(0, -14),
    };
  },
  mounted() {
    this.reload();
  },
  methods: {
    reload() {
      if (this.$session.has("userInfo")) {
        if (this.$session.get("isSeller") != "Y") {
          const params = new URLSearchParams();
          params.append("user_id", this.$session.get("userInfo"));
          this.$axios.post("/api/reload", params).then(res => {
            this.$session.set("isSeller", res.data);
          });
        }
      }
      this.$axios.post("/agency/newestList").then(res => {
        this.classList = res.data;
      });
      if (this.$session.has("userInfo")) {
        const params = new URLSearchParams();
        params.append("user_id", this.$session.get("userInfo"));
        this.$axios.post("/agency/favoriteList", params).then(res => {
          this.favoriteList = res.data;
        });
      }
    },
    textCut(txt, len, lastTxt) {
      // 기본값
      if (len == "" || len == null) {
        len = 20;
      }
      if (lastTxt == "" || lastTxt == null) {
        lastTxt = "...";
      }
      // 반환값
      if (txt.length > len) {
        txt = txt.substr(0, len) + lastTxt;
      }
      return txt;
    },
    favorite(content_no) {
      if (this.$session.has("userInfo")) {
        const params = new URLSearchParams();
        params.append("user_id", this.$session.get("userInfo"));
        params.append("content_no", content_no);
        this.$axios.post("/agency/favorite", params).then(res => {
          this.favoriteList = res.data;
        });
      } else {
        alert("로그인이 필요한 기능입니다.");
        return false;
      }
    },
  }
};
</script>

<style scoped>
.heart {
  position: absolute;
  left: 15px;
  bottom: 80px;
}
</style>

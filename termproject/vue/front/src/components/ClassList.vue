<!-- eslint-disable -->
<template>
  <div>
    <!-- 솜씨당 Middle바 Start-->
    <v-main style="padding:0px">
      <v-bottom-navigation color="#2d3753" horizontal>
        <v-btn @click.prevent="CATEGORY">전체</v-btn>
        <v-btn @click.prevent="CATEGORY">디저트</v-btn>
        <v-btn @click.prevent="CATEGORY">패션/잡화</v-btn>
        <v-btn @click.prevent="CATEGORY">리빙</v-btn>
        <v-btn @click.prevent="CATEGORY">기타</v-btn>
      </v-bottom-navigation>
    </v-main>
    <!-- 솜씨당 Middle바 End-->

    <v-container>
      <v-row no-gutters>
        <v-col :cols="12">
          <v-card-text class="" tile outlined>
            <v-card-title class="subheading" style="font-weight:bold">{{ category }} 클래스!</v-card-title>

            <div v-if="classList.length != 0">
              <div class="text-right">
                <v-btn @click="order" color="white">최신순
                  <span class="material-icons" id="or">arrow_downward</span>
                </v-btn>
              </div>
              <v-divider></v-divider>

              <div class="row">
                <div  v-for="(list, i) in classList.slice(0, cnt)" :key="i" class="col-12 col-md-3 col-sm-6 col-xs-6 text-center">
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
                        <v-btn :href="'/classDetail?content_no=' + list.content_no" class="ma-2" outlined color="info" >예약 하기</v-btn>
                      </div>
                    </v-card>
                  </v-hover>
                </div>
              </div>
              <div class="text-center mt-12">
                <div class="text-center">
                  <v-btn @click="cntMore" color="lightgray">더 많은 클래스 보기</v-btn>
                </div>
              </div>
            </div>
            <div v-if="classList.length == 0"><NoData /></div>
          </v-card-text>
        </v-col>
      </v-row>
    </v-container>
    <br />
  </div>
</template>

<script>
/*eslint-disable*/
import NoData from "@/components/NoData.vue";

export default {
  components: {
    NoData
  },
  data() {
    return {
      classList: [],
      category: "전체",
      cnt: 8,
      favoriteList: []
    };
  },
  created() {
    this.list();
  },
  methods: {
    list() {
      const params = new URLSearchParams();
      params.append("category", this.category);
      this.$axios.post("/agency/list", params).then(res => {
        this.classList = res.data;
      });
      if (this.$session.has("userInfo")) {
        params.append("user_id", this.$session.get("userInfo"));
        this.$axios.post("/agency/favoriteList", params).then(res => {
          this.favoriteList = res.data;
        });
      }
    },
    CATEGORY(event) {
      this.cnt = 8;
      this.category = event.currentTarget.textContent;
      this.list();
    },
    order() {
      if (document.getElementById("or").innerText == "arrow_upward") {
        document.getElementById("or").innerText = "arrow_downward";
      } else {
        document.getElementById("or").innerText = "arrow_upward";
      }
      this.classList = this.classList.reverse();
    },
    cntMore() {
      this.cnt = this.cnt + 8;
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
    }
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

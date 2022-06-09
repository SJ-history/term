<!-- eslint-disable -->
<template>
  <div>
    <v-container>
      <v-row no-gutters>
        <v-col :cols="12">
          <v-card-text class="" tile outlined>
            <v-card-title class="subheading ">
              <select name="sta" id="sta" style="font-weight:bold; text-decoration:underline;" @change="classState">
                <option value="0" selected="selected">운영중인</option>
                <option value="1">마감된</option>
              </select>
              &emsp;클래스!
            </v-card-title>

            <!-- <v-card-title class="subheading" style="font-weight:bold">운영중인 클래스!</v-card-title> -->

            <div v-if="classList.length != 0">
              <v-divider></v-divider>
              <div class="row">
                <div v-for="(list, i) in classList.slice(0, cnt)" :key="i" class="col-12 col-md-3 col-sm-6 col-xs-6 text-center">
                  <v-hover v-slot:default="{ hover }" open-delay="200">
                    <v-card :elevation="hover ? 16 : 2">
                      <v-img class="white--text align-end" height="200px" :src="'agency/display/' + list.image0" />
                      <v-card-text class="text--primary text-center">
                        <div>{{ textCut(list.title) }}</div>
                        <div>{{ textCut(list.head) }}</div>
                        <div>{{ list.start_date }} ~ {{ list.end_date }}</div>
                      </v-card-text>
                      <div v-if="state == 0" class="text-center">
                        <v-btn :href="'/classManageApply?content_no=' + list.content_no" class="ma-2" outlined color="info">클래스 변경 신청</v-btn>
                        <v-btn :href="'/classReservationList?content_no=' + list.content_no" class="ma-2" outlined color="info">예약 정보 확인</v-btn>
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
  props:{ classList:Array },
  components: {
    NoData
  },
  data() {
    return {
      cnt: 8,
      first:false,
      state:0,
    };
  },
  mounted() {
    this.list(this.state);
  },
  methods: {
    list(state) {
      const params = new URLSearchParams();
      params.append("user_id", this.$session.get("userInfo"));
      params.append("state", state);

      if(this.first){
        this.$axios.post("/agency/myClassList", params).then(res => {
          this.classList = res.data;
        });
      }
      this.first = true;
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
    classState(event){
      this.state = event.target.value;
      this.list(this.state);
    }
  }
};
</script>

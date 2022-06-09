<!-- eslint-disable -->
<template>
  <div>
    <v-main class="text-center" style="padding-top:15px">
        <button @click.prevent="category" class="doClass" id="d1" style="background-color:lightgrey">참여 예정</button>
        <button @click.prevent="category" class="doClass" id="d2">참여 완료</button>
    </v-main>

    <v-container>
      <v-row no-gutters>
        <v-col :cols="12">
          <v-card-text tile outlined>

            <div v-if="classList.length != 0">
            <v-divider></v-divider>
              <div class="row">
                <div v-for="(list, i) in classList.slice(0, cnt)" :key="i" class="col-12 col-md-3 col-sm-6 col-xs-6 text-center">
                  <v-hover v-slot:default="{ hover }" open-delay="200">
                    <v-card :elevation="hover ? 16 : 2">
                      <v-img class="white--text align-end" height="200px" :src="'agency/display/'+list.image0"/>
                      <a @click="favorite(list.content_no)" v-text="favoriteList.includes(list.content_no) ? 'favorite' : 'favorite_border'" class="material-icons heart"/>
                      <v-card-text class="text--primary text-center">
                        <div>{{textCut(list.title)}}</div>
                        <div>{{textCut(list.head)}}</div>
                        <div  v-if="act == 0">예약 날짜 : {{list.class_date}}</div>
                        <div  v-if="act == 1">참여 날짜 : {{list.class_date}}</div>
                        <div>예약 인원 : {{list.reserve_num}}명</div>
                      </v-card-text>
                      <div class="text-center">
                        <v-btn :href="'/classDetail?content_no='+list.content_no" class="ma-2" outlined color="info">상세 보기</v-btn>
                        <v-btn v-if="act == 0" @click.prevent="cancel(list.reserve_no)" class="ma-2" outlined color="info">예약 취소</v-btn>
                        <v-btn v-if="act == 1 && list.review_date == null" :href="'/classDetail?content_no='+list.content_no + '&reserve_no=' + list.reserve_no" class="ma-2" outlined color="info">후기 작성</v-btn>
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
    <br>
  </div>
</template>

<script>
/*eslint-disable*/
import NoData from '@/components/NoData.vue'

export default {
  props:{ classList:Array },
  components:{
    NoData,
  },
  data(){
    return{
      cnt:8,
      act:0,
      favoriteList:[],
    }
  },
	mounted(){
    this.list();
	},
	 methods:{
    list(){
      const params = new URLSearchParams();
      params.append('user_id', this.$session.get('userInfo'));
      params.append('act', this.act);
      if(this.first){
        this.$axios.post("/api/myClassList", params).then((res) => {
            this.classList = res.data;
        });
      }
      this.first = true;
      this.$axios.post("/agency/favoriteList", params).then((res) => {
        this.favoriteList = res.data;
      });
    },
    category(event){
      this.cnt = 8;
      if(event.currentTarget.textContent == "참여 예정"){
        document.getElementById("d1").style.backgroundColor = "lightgrey";
        document.getElementById("d2").style.backgroundColor = "";
        this.act = 0;
      }
      else if(event.currentTarget.textContent == "참여 완료"){
        document.getElementById("d1").style.backgroundColor = "";
        document.getElementById("d2").style.backgroundColor = "lightgrey";
        this.act = 1;
      }
      this.list();
    },
    cntMore(){ this.cnt = this.cnt + 8; },
    textCut(txt, len, lastTxt) {
      // 기본값
      if (len == "" || len == null) { len = 20; }
      if (lastTxt == "" || lastTxt == null) { lastTxt = "..."; }
      // 반환값
      if (txt.length > len) { txt = txt.substr(0, len) + lastTxt; }
      return txt;
    },
    favorite(content_no){
      if(this.$session.has('userInfo')){
        const params = new URLSearchParams();
        params.append('user_id', this.$session.get('userInfo'));
        params.append('content_no', content_no);
        this.$axios.post("/agency/favorite", params).then((res) => {
          this.favoriteList = res.data;
        });
      }
      else{
        alert("로그인이 필요한 기능입니다.");
        return false;
      }
    },
    cancel(reserve_no){
      if (!confirm("예약을 취소하시겠습니까?")) { return false; }
      else {
        const params = new URLSearchParams();
        params.append('reserve_no', reserve_no);
        this.$axios.post("/api/cancel", params).then((res) => {
          if(res.data){ alert("예약이 취소되었습니다."); }
          else{ alert("취소 실패!"); }
        });
        this.list();
      }
    },
  }
}
</script>

<style scoped>
.doClass {
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 16px;
  font-weight: 700;

  border: 1px solid #ff5862;
  color: #ff5862;
}
.heart {
  position: absolute;
  left: 15px;
  bottom: 80px;
}
</style>
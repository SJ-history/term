<!-- eslint-disable -->
<template>
  <div>
    <v-container>
      <div class="row" style="max-width:1280px; margin:0 auto">
        <div class="col-md-8 col-sm-8 col-xs-12">
          <v-img class="white--text align-end" height="500px" :src="'agency/display/' + content.image0" style="border-radius: 10px 10px 0 0;" />
          <div class="detail-title-text">
            <v-row dense>
              <v-col cols="12" sm="12" class="pt-6">
                <h3>{{ content.title }}</h3>
              </v-col>
            </v-row>
          </div>

          <v-breadcrumbs class="pb-0" :items="breadcrums"></v-breadcrumbs>
          <v-divider></v-divider>
          <v-breadcrumbs class="pb-0" :items="breadcrums"></v-breadcrumbs>

          <div style="margin:0 auto">
            <b-tabs content-class="mt-3">
              <b-tab title="상세 정보" active>
                <h4 style="padding:15px 0 10px 24px">상세정보</h4>
                <pre style="white-space:pre-wrap;">{{ content.body }}</pre>
                <v-breadcrumbs class="pb-0" :items="breadcrums"></v-breadcrumbs>
                <v-divider></v-divider>
              </b-tab>
              <b-tab title="후기">
                <Reviews :reserve_no="reserve_no" :content_no="content_no" />
              </b-tab>
              <b-tab title="변경 및 공지">
                <Notice />
              </b-tab>
              <b-tab title="참고 이미지">
                <v-img class="white--text align-end" height="500px" :src="'agency/display/' + content.image1"/>
              </b-tab>
            </b-tabs>
          </div>
        </div>

        <div class="col-md-4 col-sm-4 col-xs-12">
          <v-card outlined>
            <v-card-title>클래스 일정</v-card-title>
            <ul class="info_text">
              <li class="info_location">
                <a @click="showMap(content.cl_addr)">
                  <span class="material-symbols-outlined">pin_drop</span>
                  <u style="position:absolute; left:60px;">{{ textCut(content.cl_addr) }}</u>
                </a>
              </li>
              <li class="info_location">
                <span class="material-symbols-outlined">timer</span>
                <span style="position:absolute; left:60px;">
                {{ content.start_time.substring(0, 5) }} ~
                {{ content.end_time.substring(0, 5) }}
                </span>
              </li>
              <li class="info_location">
                <span class="material-symbols-outlined">group</span>
                <span style="position:absolute; left:60px;">{{ content.ps_num }}명</span>
              </li>
              <li v-if="content.youtube != null" class="info_location">
                <span class="material-icons">videocam</span>
                <a :href="content.youtube" target='_blank' style="position:absolute; left:60px;">참고 영상 보러가기</a>
              </li>
              <li class="info_location">
                <small>예약일자, 신청 인원수 선택 후 신청하기 버튼을 눌러주세요.<br />원데이 클래스 특성상 당일 예약은 불가능합니다.</small>
              </li>

              <div id="map" class="map"></div>
            </ul>
            <v-divider></v-divider>
            <div class="btn-swip">
              <a @click="order" class="dateSelect">
                <span class="material-symbols-outlined dateSelectSpan1">calendar_month</span>
                날짜선택
                <span class="material-symbols-outlined dateSelectSpan2" id="or">expand_more</span>
              </a>
            </div>

            <div class="class-date" id="cal" style="padding-bottom:0px">
              <Calander
                :start_date="content.start_date"
                :end_date="content.end_date"
                :content_no="content_no"
                :ps_num="content.ps_num"
                @class_date="selected"
              />
            </div>

            <div class="class-confirm03">
              <div class="c03-count">
                <a @click="numMi">-</a>
                <input type="text" id="quantity" :value="ps_cnt" class="only-number" />
                <a @click="numPl">+</a>
              </div>
              <div class="c03-charge">
                {{ ps_cnt }}/{{ content.ps_num - reserve_num }}
                <span>명</span>
              </div>
            </div>

            <div class="class-confirm04">
              <a @click="apply">신청하기</a>
            </div>
          </v-card>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
/* eslint-disable */
import Notice from "@/components/Notice.vue";
import Calander from "@/components/Calander.vue";
import Reviews from "@/components/Reviews.vue";

export default {
  data() {
    return {
      ps_cnt: 1,
      content: [],
      content_no: this.$route.query.content_no,
      reserve_no: this.$route.query.reserve_no,
      class_date: "",
      reserve_num: 0,
      sd: false
    };
  },
  components: {
    Reviews,
    Notice,
    Calander
  },
  mounted() {
    this.classDetail();
  },
  methods: {
    classDetail() {
      const params = new URLSearchParams();
      params.append("content_no", this.content_no);

      this.$axios.post("/agency/classDetail", params).then(res => {
        this.content = res.data;
      });
    },
    showMap(addr) {
      window.open("https://map.kakao.com/link/search/" + addr, "width:700, height=900", "_blank");
    },
    textCut(txt, len, lastTxt) {
      // 기본값
      if (len == "" || len == null) {
        len = 25;
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
    order() {
      if (document.getElementById("or").innerText == "expand_more") {
        document.getElementById("or").innerText = "expand_less";
        document.getElementById("cal").style.display = "block";
      } else {
        document.getElementById("or").innerText = "expand_more";
        document.getElementById("cal").style.display = "none";
      }
    },
    numMi() {
      if (this.ps_cnt <= 1) {
        alert("최소 1명부터 신청이 가능합니다.");
        return false;
      }
      this.ps_cnt = this.ps_cnt - 1;
    },
    numPl() {
      if (this.ps_cnt >= this.content.ps_num - this.reserve_num) {
        alert("예약 가능한 인원은 " + (this.content.ps_num - this.reserve_num) + "명입니다.");
        return false;
      }
      this.ps_cnt = this.ps_cnt + 1;
    },
    selected(class_date) {
      const params = new URLSearchParams();
      params.append("content_no", this.content_no);
      params.append("class_date", class_date);
      this.class_date = class_date;
      this.$axios.post("/agency/reserveSearch", params).then(res => {
        this.ps_cnt = 1;
        this.reserve_num = res.data;
      });
    },
    apply() {
      if (!this.$session.has("userInfo")) {
        alert("로그인이 필요한 기능입니다.");
        return false;
      } else {
        if (this.class_date == "") {
          alert("예약일자를 선택해주세요.");
          return false;
        }

        const params = new URLSearchParams();
        params.append("user_id", this.$session.get("userInfo"));
        params.append("content_no", this.content_no);
        params.append("ps_num", this.content.ps_num);
        params.append("class_date", this.class_date);
        params.append("reserve_num", this.ps_cnt);
        this.$axios.post("/agency/reserve", params).then(res => {
          if (res.data == 'success') {
            alert("예약이 완료되었습니다.\n자세한 사항은 작가님 연락을 통해 공지됩니다.");
            window.location.href = "/";
          } else {
            alert(res.data);
          }
        });
      }
    }
  }
};
</script>

<style scoped>
.detail-title-text {
  position: relative;
  border-radius: 0 0 10px 10px;
  border: 1px solid #d5dadf;
  border-top: 0 none;
  padding: 25px 30px 40px;
}
h3 {
  word-break: keep-all;
  font-size: 24px;
  font-weight: bold;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  white-space: normal;
  line-height: 1.2;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  width: 90%;
}
h4 {
  font-size: 20px;
  margin-top: 10px;
  margin-bottom: 10px;
}
.v-application ul {
  padding-left: 0px;
}
.info_text {
  overflow: hidden;
}
.info_text li {
  float: left;
  width: 50%;
  padding-left: 32px;
  padding-right: 0px;
  padding-bottom: 35px;
  font-size: 15px;
  line-height: 20px;
  margin: 6px 0;
  height: 22px;
  float: none !important;
  width: auto !important;
  font-size: 14px;
}
.info_location {
  margin-top: 5px;
  padding-right: 120px;
  word-break: keep-all;
}
.btn-swip {
  border-bottom: 1px solid #e4e9ef;
}
.dateSelect {
  position: relative;
  display: block;
  height: 56px;
  line-height: 56px;
  padding-left: 54px;
  font-size: 16px;
  cursor: pointer;
}
.dateSelectSpan1 {
  position: absolute;
  top: 18px;
  left: 24px;
  display: block;
  width: 20px;
  height: 20px;
  content: "";
}
.dateSelectSpan2 {
  position: absolute;
  top: 18px;
  right: 30px;
  display: block;
  width: 20px;
  height: 20px;
  content: "";
}
.class-date {
  border-bottom: 1px solid #e4e9ef;
  padding: 10px 0 30px;
  display: none;
}
.class-confirm03 {
  overflow: hidden;
  padding: 30px 30px 30px;
}
.class-confirm03 .c03-count {
  float: left;
  padding-top: 10px;
}
.class-confirm03 .c03-count a {
  display: inline-block;
  border-radius: 3px;
  border: 1px solid #b0bbc9;
  color: #b0bbc9;
  font-size: 18px;
  height: 24px;
  line-height: 18px;
  width: 24px;
  text-align: center;
  vertical-align: middle;
}
.class-confirm03 .c03-count input {
  width: 34px;
  text-align: center;
  border: 0 none;
  font-size: 16px;
  height: 24px;
  line-height: 24px;
  font-weight: bold;
  vertical-align: middle;
}
.class-confirm03 .c03-charge {
  float: right;
  font-size: 26px;
  font-weight: bold;
}
.class-confirm03 .c03-charge span {
  font-weight: normal;
  font-size: 16px;
}
.class-confirm04 {
  padding-bottom: 24px;
}
.class-confirm04 a {
  display: block;
  height: 50px;
  line-height: 50px;
  width: 380px;
  margin: 0 auto;
  background-color: #73dbc6;
  text-align: center;
  color: #fff;
  font-weight: bold;
}
</style>

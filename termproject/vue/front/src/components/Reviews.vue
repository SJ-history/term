<!-- eslint-disable -->
<template>
  <div>
    <div class="review-header">
      <div class="review-count">{{ reviewsCnt }}개의 후기</div>
      <button @click.prevent="review" class="btn_write_review">후기 작성하기</button>
    </div>
    <div v-if="reTf" class="review_wrap" style="border: ridge;">
      <div class="txt">
        <div class="txt_id">내용</div>
        <div class="txt_date">
          <span :style="[review_content.length > 500 ? { color:'red' } : { }]">{{ review_content.length }}</span>/500
        </div>
        <div class="txt_con">
          <textarea type="text" placeholder="후기를 작성해주세요!" v-model="review_content" class="form-control" rows="3" style="resize:none" />
        </div>
        <div class="txt_btn text-right">
          <button @click.prevent="reviewWrite" class="btn btn-info">작성 완료</button>
        </div>
        <div class="txt"></div>
      </div>
    </div>

    <!-- 문의 글 List Start -->
    <div class="review_wrap" v-for="(rev, i) in reviews" :key="i">
      <div class="txt">
        <div class="txt_id">{{ rev.user_id }}</div>
        <div class="txt_date">{{ rev.review_date.substring(0, 10) }}</div>
      </div>
      <div class="comment">
        <div class="txt_con">{{ rev.review_content }}</div>
      </div>
      <v-divider></v-divider>
    </div>
    <!-- 문의 글 List End -->
  </div>
</template>

<script>
/* eslint-disable */
export default {
  props: { reserve_no: Number, content_no: Number },
  data() {
    return {
      reviews: [],
      review_content: "",
      reTf: false,
      reviewsCnt: 0
    };
  },
  mounted() {
    this.reviewList();
  },
  methods: {
    reviewList() {
      const params = new URLSearchParams();
      params.append("content_no", this.content_no);
      this.$axios.post("/agency/reviewList", params).then(res => {
        this.reviews = res.data;
        this.reviewsCnt = res.data.length;
      });
    },
    review() {
      if (this.reserve_no == null) {
        alert("참여하지 않은 클래스입니다.\n후기는 참여완료 후 작성할 수 있습니다.");
        return false;
      }
      const params = new URLSearchParams();
      params.append("reserve_no", this.reserve_no);
      this.$axios.post("/api/reviewChecked", params).then(res => {
        if (res.data) {
          alert("이미 후기를 작성했습니다.");
          this.reTf = false;
          return false;
        } else{
          this.reTf = true;
        }
      });
    },
    reviewWrite() {
      if (this.review_content.length > 500) {
        alert("최대 500자 까지 작성할 수 있습니다.");
        this.review_content = this.review_content.substring(0, 500);
      } else if(this.review_content == '') {
        alert("후기를 작성해주세요.");
      } else {
        const params = new URLSearchParams();
        params.append("user_id", this.$session.get("userInfo"));
        params.append("content_no", this.content_no);
        params.append("reserve_no", this.reserve_no);
        params.append("review_content", this.review_content);
        this.$axios.post("/api/reviewWrite", params).then(res => {
          if (res.data) {
            this.reviews = res.data;
            this.reviewsCnt = res.data.length;
            this.reTf = false;
            alert("후기 작성 완료");
          }
        });
      }
    }
  }
};
</script>

<style scoped>
.review-header {
  position: relative;
  margin: 30px 0 20px;
}
.review-header .review-count {
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
}
.btn_write_review {
  position: absolute;
  top: 0px;
  right: 20px;
  display: block;
  border: 1px solid #ff5862;
  color: #ff5862;
  height: 46px;
  line-height: 42px;
  width: 150px;
  text-align: center;
  font-weight: bold;
  font-size: 14px;
  border-radius: 5px;
}
.review_wrap {
  padding: 15px 20px;
  position: relative;
  margin-bottom: 10px;
}
.review_wrap .txt {
  padding-left: 20px;
}
.review_wrap .txt .txt_id {
  font-weight: bold;
  font-size: 16px;
}
.review_wrap .txt .txt_date {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 13px;
  color: #b2bcc8;
}
.review_wrap .txt .txt_con {
  margin-top: 5px;
}
.review_wrap .txt .txt_btn {
  padding: 8px 0 0;
}
.review_wrap .txt .txt_btn a {
  display: inline-block;
  margin-left: 5px;
  font-size: 12px;
  padding: 5px 2px;
  color: #b2bcc8;
}
.review_wrap .comment {
  position: relative;
  margin-top: 10px;
  background-color: #f6f7f8;
  border-radius: 10px;
  padding: 20px;
}
.review_wrap .comment .cmt_id {
  font-weight: bold;
  font-size: 16px;
}
.review_wrap .comment .cmt_date {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 13px;
  color: #b2bcc8;
}
.review_wrap .comment .cmt_con {
  margin-top: 5px;
}
</style>

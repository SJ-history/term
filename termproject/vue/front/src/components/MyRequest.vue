<!--eslint-disable-->
<template>
  <div class="container">
    <div class="container col-sm-8">
      <legend>요청 목록</legend>
      <div v-if="content.length != 0">
        <MyRequestPaging :listArray="content" />
      </div>
      <div v-if="content.length == 0"><NoData /></div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import NoData from "@/components/NoData.vue";
import MyRequestPaging from "@/components/MyRequestPaging.vue";

export default {
  components: {
    MyRequestPaging,
    NoData
  },
  data() {
    return {
      content: []
    };
  },
  created() {
    const params = new URLSearchParams();
    params.append("user_id", this.$session.get("userInfo"));
    this.$axios.post("/agency/requestList", params).then(res => {
      this.content = res.data;
    });
  }
};
</script>

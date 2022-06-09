<!-- eslint-disable -->
<template>
  <div class="container">
    <div class="container green_window">
      <input type="text" class="input_text" v-model="query" style="ime-mode:active" placeholder="찾으시는 취미가 있나요?" />
      <button tabindex="3" class="btn_submit">
        <span class="ico_search_submit material-icons">search</span>
      </button>
    </div>
    <div><SearchList :query="query" /></div>
  </div>
</template>

<script>
/*eslint-disable*/
import SearchList from "@/components/SearchList.vue";
export default {
  components: {
    SearchList
  },
  data() {
    return {
      query: ""
    };
  }
};
</script>

<style scoped>
.green_window {
  position: relative;
  width: 582px;
  height: 52px;
  border: 2px solid #19cece;
  -webkit-border-radius: 2px;
  border-radius: 2px;
}
.input_text {
  width: 500px;
  height: 24px;
  padding: 13px 15px;
  margin: 1px;
  background-color: #fff;
  font-size: 18px;
  line-height: 24px;
  color: #000;
  font-weight: 700;
  outline: 0;
}
.btn_submit {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  width: 56px;
  background-color: #19cece;
  -webkit-border-radius: 0 2px 2px 0;
  border-radius: 0 2px 2px 0;
  cursor: pointer;
}
.btn_submit .ico_search_submit {
  display: inline-block;
  width: 22px;
  height: 22px;
  background-position: -420px 0;
  background-repeat: no-repeat;
  vertical-align: top;
}
</style>

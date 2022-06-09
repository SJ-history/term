<!--eslint-disable-->
<template>
  <div class="container">
    <form @submit.prevent="onSubmit">
      <div class="container col-sm-6">
        <h3 style="font-weight:bold">클래스 예약 정보 확인</h3>
        <br />

        <table class="table table-hover col-sm-12">
          <tbody>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                클래스 명
              </th>
              <td scope="col" class="col-sm-8" style="padding-left:20px">
                {{ content.title }}
              </td>
            </tr>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                클래스 날짜
              </th>
              <td scope="col" class="col-sm-8" style="padding-left:20px">
                {{ content.start_date }} ~ {{ content.end_date }}
              </td>
            </tr>
            <tr>
              <th scope="col" class="col-sm-4 active" style="font-weight:bold; background-color:whitesmoke">
                클래스 주제
              </th>
              <td scope="col" class="col-sm-8" style="padding-left:20px">
                {{ content.head }}
              </td>
            </tr>
            <tr>
              <th scope="col" style="font-weight:bold; background-color:whitesmoke" colspan="2">
                예약자 목록
              </th>
            </tr>
            <tr>
              <th scope="col" style="font-weight:bold; background-color:whitesmoke" colspan="2">
                <ClassReservationListPaging :listArray="userList" />
              </th>
            </tr>
          </tbody>
        </table>
      </div>
    </form>
  </div>
</template>

<script>
import ClassReservationListPaging from '@/components/ClassReservationListPaging.vue'

/* eslint-disable */
export default {
  components: {
    ClassReservationListPaging,
  },
  data() {
    return {
      content_no: this.$route.query.content_no,
      content: [],
      userList: [],
    };
  },mounted() {
    this.classManageList();
  },
  methods: {
    classManageList() {
      const params = new URLSearchParams();
      params.append("content_no", this.content_no);

      this.$axios.post("/agency/classDetail", params).then(res => {
        this.content = res.data;
      });
    },
  },
  created() {
    const params = new URLSearchParams();
    params.append("user_id", this.$session.get("userInfo"));
    params.append("content_no", this.content_no);
    this.$axios.post("/agency/reserveUserList", params).then(res => {
      this.userList = res.data;
    });
  }
};
</script>

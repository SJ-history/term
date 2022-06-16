<!--eslint-disable-->
<template>
  <div class="container">
    <form @submit.prevent="onSubmit">
      <div class="container col-sm-6">
        <br />
        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="user_name">이름</label>
          </div>
          <div class="col-sm-8">
            <input type="text" class="form-control" placeholder="이름" id="user_name" name="user_name" readonly />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="user_id">아이디</label>
          </div>
          <div class="col-sm-8">
            <input type="text" class="form-control" placeholder="아이디" id="user_id" name="user_id" readonly />
          </div>
        </div>

        <div class="row col-sm-12" id="pa" style="display:none">
          <div class="col-sm-4">
            <label for="user_wd">비밀번호</label>
          </div>
          <div class="col-sm-8">
            <input type="password" class="form-control" placeholder="비밀번호" id="user_wd" name="user_wd" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="ADDR">주소</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="우편 번호" id="post" name="post" readonly />
              </div>
              <div class="col-sm-4">
                <button id="ADDR" class="btn btn-warning form-control" @click.prevent="postSearch" disabled>주소 검색</button>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12">
                <input type="text" placeholder="주소" id="addr1" name="addr1" class="form-control" readonly />
              </div>
              <div class="col-sm-12">
                <input type="text" placeholder="상세주소" id="addr2" name="addr2" class="form-control" readonly />
              </div>
            </div>
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="phone1">전화번호</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-3">
                <select name="phone1" class="form-control" id="phone1" disabled>
                  <option value="010" selected="selected">010</option>
                  <option value="011">011</option>
                  <option value="011">016</option>
                  <option value="016">017</option>
                  <option value="016">018</option>
                </select>
              </div>
              <p style="padding:15px">-</p>
              <div class="col-sm-4">
                <input type="text" placeholder="중간번호" class="form-control" id="phone2" name="phone2" readonly />
              </div>
              <p style="padding:15px">-</p>
              <div class="col-sm-3">
                <input type="text" placeholder="끝번호" class="form-control" id="phone3" name="phone3" readonly />
              </div>
            </div>
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="email1">이메일</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-3">
                <input type="text" placeholder="Email" class="form-control" id="email1" name="email1" readonly />
              </div>
              <p style="padding:15px">@</p>
              <div class="col-sm-4">
                <input type="text" placeholder="직접 입력" class="form-control" id="email2" name="email2" readonly />
              </div>
              <div class="col-sm-4" style="margin-right: -1px;">
                <select name="EMAIL" id="EMAIL" class="form-control" @change="emailChange" disabled>
                  <option value="" selected="selected">직접 입력</option>
                  <option value="naver.com">naver.com</option>
                  <option value="nate.com">nate.com</option>
                  <option value="gmail.com">gmail.com</option>
                  <option value="yahoo.com">yahoo.com</option>
                  <option value="hanmail.net">hanmail.net</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <br />
        <button type="submit" class="btn btn-danger form-control" id="submitBtn">수정 하기</button>
      </div>
    </form>
  </div>
</template>

<script>
/*eslint-disable*/

export default {
  data: {
    return: {
      myInfo: [],
      change: false
    }
  },
  mounted() {
    this.infoList();
  },
  methods: {
    infoList() {
      const params = new URLSearchParams();
      params.append("user_id", this.$session.get("userInfo"));

      this.$axios.post("/api/myInfo", params).then(res => {
        this.myInfo = res.data;
        document.getElementById("user_name").value = this.myInfo.user_name;
        document.getElementById("user_id").value = this.myInfo.user_id;
        document.getElementById("post").value = this.myInfo.post;
        document.getElementById("addr1").value = this.myInfo.addr1;
        document.getElementById("addr2").value = this.myInfo.addr2;
        document.getElementById("phone1").value = this.myInfo.phone1;
        document.getElementById("phone2").value = this.myInfo.phone2;
        document.getElementById("phone3").value = this.myInfo.phone3;
        document.getElementById("email1").value = this.myInfo.email1;
        document.getElementById("email2").value = this.myInfo.email2;
        document.getElementById("EMAIL").value = this.myInfo.email2;
      });
    },
    onSubmit() {
      if (!this.change) {
        this.change = true;
        document.getElementById("pa").style.display = "";
        document.getElementById("post").readOnly = false;
        document.getElementById("addr1").readOnly = false;
        document.getElementById("addr2").readOnly = false;
        document.getElementById("phone1").disabled = false;
        document.getElementById("phone2").readOnly = false;
        document.getElementById("phone3").readOnly = false;
        document.getElementById("email1").readOnly = false;
        document.getElementById("email2").readOnly = false;
        document.getElementById("EMAIL").disabled = false;
        document.getElementById("ADDR").disabled = false;

        document.getElementById("submitBtn").innerText = "회원 정보 변경";
        document.getElementById("submitBtn").className =
          "btn btn-success form-control";
      } else {
        var user_wd = document.getElementById("user_wd").value;
        if (!user_wd) {
          alert("비밀번호를 입력해주세요");
          document.getElementById("user_wd").focus;
        } else {
          var post = document.getElementById("post").value;
          var addr1 = document.getElementById("addr1").value;
          var addr2 = document.getElementById("addr2").value;
          var phone1 = document.getElementById("phone1").value;
          var phone2 = document.getElementById("phone2").value;
          var phone3 = document.getElementById("phone3").value;
          var email1 = document.getElementById("email1").value;
          var email2 = document.getElementById("email2").value;

          // 우편번호 숫자5자리 정규식
          var postTest = /^[0-9]{5}$/g;
          // 전화번호 글자수 및 한글 정규식
          var phoneTest = /^([0-9]{3,4})$/;
          // 이메일 앞 글자수 정규식
          var emailTest1 = /^[a-z0-9\.\-_]{4,40}$/;
          // 이메일 뒤 글자수 정규식
          var emailTest2 = /^([a-z0-9\-]+\.)+[a-z]{2,6}$/;

          if (!postTest.test(post)) {
            alert("우편번호를 확인해주세요.");
            document.getElementById("post").focus();
            return false;
          } else if (post == "" || addr1 == "" || addr2 == "") {
            alert("주소를 입력해주세요");
            document.getElementById("post").focus();
            return false;
          } else if (phone1 == "" || phone2 == "" || phone3 == "") {
            alert("핸드폰 번호를 입력해주세요");
            document.getElementById("phone1").focus();
            return false;
          } else if (!phoneTest.test(phone2)) {
            alert("핸드폰 번호를 확인 해주세요(중간 번호)\n숫자만 입력 가능(3 ~ 4자리)");
            document.getElementById("phone2").focus();
            return false;
          } else if (!phoneTest.test(phone3)) {
            alert("핸드폰 번호를 확인 해주세요(끝 번호)\n숫자만 입력 가능(3 ~ 4자리)");
            document.getElementById("phone3").focus();
            return false;
          } else if (!emailTest1.test(email1)) {
            alert("이메일을 확인 해주세요(앞)\n최대 40 글자");
            document.getElementById("email1").focus();
            return false;
          } else if (!emailTest2.test(email2)) {
            alert("이메일을 확인 해주세요(뒤)\n최대 20 글자");
            document.getElementById("email2").focus();
            return false;
          }

          const params = new URLSearchParams();
          params.append("user_id", this.$session.get("userInfo"));
          params.append("user_wd", user_wd);
          params.append("post", post);
          params.append("addr1", addr1);
          params.append("addr2", addr2);
          params.append("phone1", phone1);
          params.append("phone2", phone2);
          params.append("phone3", phone3);
          params.append("email1", email1);
          params.append("email2", email2);

          this.$axios.post("/api/infoEdit", params).then(res => {
            if (res.data.includes("success")) {
              alert("회원 정보 수정 완료");
              window.location.href = "/";
            } else {
              alert(res.data);
            }
          });
        }
      }
    },
    postSearch() {
      new window.daum.Postcode({
        oncomplete: data => {
          document.getElementById("post").value = data.zonecode;
          if (data.jibunAddress == "") {
            document.getElementById("addr1").value = data.roadAddress;
          } else {
            document.getElementById("addr1").value = data.jibunAddress;
          }
          document.getElementById("addr2").value = '';
          document.getElementById("addr2").focus();
        }
      }).open();
    },
    emailChange(event) {
      if (event.target.value == "") {
        document.getElementById("email2").readOnly = false;
      } else {
        document.getElementById("email2").readOnly = true;
      }
      document.getElementById("email2").value = event.target.value;
    }
  }
};
</script>

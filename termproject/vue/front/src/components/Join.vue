<!--eslint-disable-->
<template>
  <div class="container">
    <form @submit.prevent="onSubmit">
      <div class="container col-sm-6">
        <h3 style="font-weight:bold">회원가입</h3>
        <br />

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="user_name">이름</label>
          </div>
          <div class="col-sm-8">
            <input type="text" class="form-control" placeholder="이름" id="user_name" name="user_name" />
          </div>
        </div>

        <div class="row col-sm-12">
          <div class="col-sm-4">
            <label for="user_id">아이디</label>
          </div>
          <div class="col-sm-8">
            <div class="row">
              <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="아이디" id="user_id" name="user_id" />
              </div>
              <div class="col-sm-4">
                <button class="btn btn-success form-control" id="over" @click.prevent="idCheck">중복 검사</button>
              </div>
            </div>
          </div>
        </div>

        <div class="row col-sm-12">
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
                <input type="text" class="form-control" placeholder="우편 번호" id="post" name="post" />
              </div>
              <div class="col-sm-4">
                <button id="ADDR" class="btn btn-warning form-control" @click.prevent="postSearch">주소 검색</button>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12">
                <input type="text" placeholder="주소" id="addr1" name="addr1" class="form-control" />
              </div>
              <div class="col-sm-12">
                <input type="text" placeholder="상세주소" id="addr2" name="addr2" class="form-control" />
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
                <select name="phone1" class="form-control" id="phone1">
                  <option value="010" selected="selected">010</option>
                  <option value="011">011</option>
                  <option value="011">016</option>
                  <option value="016">017</option>
                  <option value="016">018</option>
                </select>
              </div>
              <p style="padding:15px">-</p>
              <div class="col-sm-4">
                <input type="text" placeholder="중간번호" class="form-control" id="phone2" name="phone2" />
              </div>
              <p style="padding:15px">-</p>
              <div class="col-sm-3">
                <input type="text" placeholder="끝번호" class="form-control" id="phone3" name="phone3" />
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
                <input type="text" placeholder="Email" class="form-control" id="email1" name="email1" />
              </div>
              <p style="padding:15px">@</p>
              <div class="col-sm-4">
                <input type="text" placeholder="직접 입력" class="form-control" id="email2" name="email2" />
              </div>
              <div class="col-sm-4">
                <select name="EMAIL" class="form-control" @change="emailChange">
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
        <button type="submit" class="btn btn-info form-control">회원가입</button>
      </div>
    </form>
  </div>
</template>

<script>
/*eslint-disable*/
import axios from "axios";

export default {
  data: {
    return: {
      isChecked: false
    }
  },
  methods: {
    postSearch() {
      new window.daum.Postcode({
        oncomplete: data => {
          document.getElementById("post").value = data.zonecode;
          if (data.jibunAddress == "") {
            document.getElementById("addr1").value = data.roadAddress;
          } else {
            document.getElementById("addr1").value = data.jibunAddress;
          }
          document.getElementById("addr2").focus();
        }
      }).open();
    },
    idCheck() {
      if (this.isChecked) {
        this.isChecked = !this.isChecked;
        document.getElementById("user_id").readOnly = false;
        document.getElementById("over").innerText = "중복 검사";
        document.getElementById("over").className = "btn btn-success form-control";
        return false;
      }
      // 아이디 정규식
      var idTest = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/;

      var user_id = document.getElementById("user_id").value;
      if (!idTest.test(user_id)) {
        alert("아이디를 확인해주세요!(공백 X)\n아이디는 4~20자 이내 까지 입력이 가능합니다.");
        document.getElementById("user_id").focus();
        return false;
      } else {
        const params = new URLSearchParams();
        params.append("user_id", user_id);

        axios.post("/api/idChecked", params).then(res => {
          if (res.data == "overlap") {
            alert("사용중인 아이디 입니다.");
            return false;
          }
          alert("사용 가능한 아이디 입니다.");
          document.getElementById("user_id").readOnly = true;
          document.getElementById("over").innerText = "변경 하기";
          document.getElementById("over").className = "btn btn-danger form-control";
          this.isChecked = !this.isChecked;
        });
      }
    },
    emailChange(event) {
      if (event.target.value == "") {
        document.getElementById("email2").readOnly = false;
      } else {
        document.getElementById("email2").readOnly = true;
      }
      document.getElementById("email2").value = event.target.value;
    },
    onSubmit() {
      var user_name = document.getElementById("user_name").value;
      var user_id = document.getElementById("user_id").value;
      var user_wd = document.getElementById("user_wd").value;
      var post = document.getElementById("post").value;
      var addr1 = document.getElementById("addr1").value;
      var addr2 = document.getElementById("addr2").value;
      var phone1 = document.getElementById("phone1").value;
      var phone2 = document.getElementById("phone2").value;
      var phone3 = document.getElementById("phone3").value;
      var email1 = document.getElementById("email1").value;
      var email2 = document.getElementById("email2").value;

      // 이름 글자수 및 한글 정규식
      var nameTest = /^[가-힣]{2,6}$/;
      // 아이디 정규식
      var idTest = /^[A-Za-z]{1}[A-Za-z0-9]{3,20}$/;
      // 비밀번호 정규식
      var pwTest = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/;
      // 전화번호 글자수 및 한글 정규식
      var phoneTest = /^([0-9]{3,4})$/;
      // 이메일 앞 글자수 정규식
      var emailTest1 = /^[a-z0-9\.\-_]{4,40}$/;
      // 이메일 뒤 글자수 정규식
      var emailTest2 = /^([a-z0-9\-]+\.)+[a-z]{2,6}$/;

      if (!nameTest.test(user_name)) {
        alert("이름을 확인해주세요!(공백 X)\n이름은 한글만 입력 가능하며 \n최대 6자 이내 까지 입력이 가능합니다.");
        document.getElementById("user_name").focus();
        return false;
      } else if (!idTest.test(user_id)) {
        alert("아이디를 확인해주세요!(공백 X)\n아이디는 4~20자 이내 까지 입력이 가능합니다.");
        document.getElementById("user_id").focus();
        return false;
      } else if (!pwTest.test(user_wd)) {
        alert("비밀번호를 확인해주세요!(공백 X)\n특수문자 포함 8~20자 이내 까지 입력이 가능합니다.");
        document.getElementById("user_wd").focus();
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
      } else if (document.getElementById("user_id").readOnly == false) {
        alert("아이디 중복검사를 해주세요");
        document.getElementById("user_id").focus();
        return false;
      }

      const params = new URLSearchParams();
      params.append("user_name", user_name);
      params.append("user_id", user_id);
      params.append("user_wd", user_wd);
      params.append("post", post);
      params.append("addr1", addr1);
      params.append("addr2", addr2);
      params.append("phone1", phone1);
      params.append("phone2", phone2);
      params.append("phone3", phone3);
      params.append("email1", email1);
      params.append("email2", email2);

      axios.post("/api/join", params).then(res => {
        if (res.data == "success") {
          alert("회원가입 완료");
        } else {
          alert("회원가입 실패");
        }
        window.location.href = "/login";
      });
    }
  }
};
</script>

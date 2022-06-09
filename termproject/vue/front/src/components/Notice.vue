<!-- eslint-disable -->
<template>
  <div>
    <h3 style="font-size:17px; margin-bottom:40px; margin-top:40px; font-weight:bold">클래스 스케줄 변경 시 정책</h3>
    <ol class="cancel-text-list">
      <li>
        <div class="num">1</div>
        <div class="txt">
          일정 변경 후 환불신청시, 협의(구매)되었던 가장 빠른 일정으로 환불 규정 적용
        </div>
        <div class="num">2</div>
        <div class="txt">
          일정 변경은 의무사항이 아니며, 작가님과 협의 및 변경이 가능한 경우에만 적용됩니다.<br />
          <small>(1일 이전 당일 변경은 변경불가)</small>
        </div>
      </li>
    </ol>
    <span>
      * 모든 환불 건은 클래스 일정을 기준으로 환불처리<br />
      * 환불 정책이외 환불이 필요할 경우 구매자와 작가 직접 협의
    </span>
    <p style="margin-top:30px!important; font-weight:bold">
      솜씨당은 통신판매중개자이며, 통신판매의 당사자가 아닙니다.<br />
      상품, 상품정보, 거래에 관한 의무와 책임은 판매자에 있습니다.
    </p>
  </div>
</template>

<style scoped>
/* eslint-disable */
.cancel-text-list {
  list-style: none;
  margin: 10px 0;
  padding: 0;
}
.cancel-text-list li .num {
  left: 0;
  position: absolute;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background-color: #b2bcc8;
  color: #fff;
  text-align: center;
  font-size: 16px;
  line-height: 24px;
  vertical-align: middle;
  margin-left: 15px;
}
.cancel-text-list li .txt {
  margin-left: 30px;
  margin-bottom: 20px;
}
</style>

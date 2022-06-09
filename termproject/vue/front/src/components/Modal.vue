<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header">회원 탈퇴</slot>
          </div>
          <div class="modal-body">
            <slot name="body">정말로 탈퇴 하시겠습니까?</slot>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              <button class="modal-default-button" @click="$emit('withdrawal')">&emsp;탈퇴&emsp;</button>
              <button class="modal-default-button" @click="$emit('close')">&emsp;취소&emsp;</button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
/* eslint-disable */
export default {
  props: {
    show: Boolean
  }
};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
  width: 500px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: lightgrey;
}
.modal-leave-to {
  opacity: 0;
}
</style>

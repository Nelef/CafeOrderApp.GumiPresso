<template>
  <b-container class="container">
    <h1>회원 가입</h1>
    <b-form-group class="register-view-form">
      <div class="register-view-div">
        <div class="form-group">
          <label for="input-id">아이디</label>
          <b-form-input
            type="text"
            class="form-control"
            id="input-id"
            placeholder="ID 입력"
            v-model="form.id"
          />
          <span class="error" v-show="isNotRegister">다른 아이디를 사용해 주세요</span>
        </div>
        <div class="form-group">
          <label for="input-name">이름</label>
          <b-form-input
            type="text"
            class="form-control"
            id="input-name"
            placeholder="이름 입력"
            v-model="form.name"
          />
        </div>
        <div class="form-group">
          <label for="input-pw">비밀번호</label>
          <b-form-input
            type="password"
            class="form-control"
            id="input-pw"
            placeholder="Password"
            v-model="form.pass"
          />
        </div>
      </div>
      <button @click="register()" type="submit" class="btn btn-primary">가입</button>
      <button @click="reset()" type="submit" class="btn btn-danger">취소</button>
    </b-form-group>
  </b-container>
</template>

<script>
import store from "@/store";
export default {
  name: "register-view",
  data() {
    return {
      form: {
        id: "",
        name: "",
        pass: "",
        stamps: 0,
      },
      isNotRegister: false,
    };
  },
  methods: {
    register() {
      if (!this.form.id) {
        alert("아이디를 입력하세요.");
        return;
      }

      if (!this.form.name) {
        alert("이름을 입력하세요.");
        return;
      }

      if (!this.form.pass) {
        alert("비밀번호를 입력하세요.");
        return;
      }

      store.dispatch("insertUser", this.form);

      let user = store.getters.getUser;
      console.log(user);
      // 회원가입 된 상태이면
      if (user.id) {
        this.reset();
        this.movePage("/");
        this.isNotRegister = false;
      } else {
        this.isNotRegister = true;
      }
    },
    reset() {
      this.form.id = "";
      this.form.name = "";
      this.form.pass = "";
    },
    movePage(url) {
      this.$router.push(url);
    },
  },
};
</script>

<style scope>
.error {
  color: red;
}
.form-group > input {
  text-align: center;
}
</style>

<template>
  <b-container>
    <h1>로그인</h1>
    <b-form-group class="login-view-form">
      <div class="login-view-div">
        <div class="form-group">
          <label for="input-id">아이디</label>
          <b-form-input
            type="text"
            class="form-control"
            id="input-id"
            placeholder="ID 입력"
            v-model="form.id"
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
      <button @click="login()" type="button" class="btn btn-primary">로그인</button>
    </b-form-group>
  </b-container>
</template>

<script>
import router from "@/router";
import store from "@/store";
export default {
  data() {
    return {
      form: {
        id: "",
        pass: "",
      },
    };
  },
  computed: {
    loginUser() {
      return store.getters.getLoginUser;
    },
  },
  methods: {
    login() {
      this.$store.dispatch("login", {
        data: this.form,
        callback: function () {
          router.push({ name: "product-list-view" });
        },
      });
    },
    movePage(url) {
      this.$router.push(url);
    },
  },
};
</script>

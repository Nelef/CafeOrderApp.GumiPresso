<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark" id="nav">
    <div class="container">
      <a class="navbar-brand" @click="movePage('/')"><img id="logo" src="@/assets/logo.png" /></a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" @click="movePage('/')"
              >SSAFY Cafe <span class="sr-only">(current)</span></a
            >
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" v-show="!loginUser.id">
          <button
            @click="movePage('/login')"
            class="btn btn-outline-success my-2 my-sm-0"
            style="margin: 10px"
            type="button"
          >
            로그인
          </button>
          <button
            @click="movePage('/register')"
            class="btn btn-outline-info my-2 my-sm-0"
            type="button"
          >
            회원가입
          </button>
        </form>
        <form class="form-inline my-2 my-lg-0" v-show="loginUser.id">
          <button
            @click="movePage('/user-info')"
            class="btn btn-outline-success my-2 my-sm-0"
            style="margin: 10px"
            type="button"
          >
            {{ loginUser.name }}
          </button>

          <button @click="logout()" class="btn btn-outline-info my-2 my-sm-0" type="button">
            로그아웃
          </button>
        </form>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "header-nav",
  methods: {
    movePage(url) {
      this.$router.push(url);
    },
    logout() {
      this.$store.dispatch("resetLoginUser", {
        callback: function () {
          this.$cookies.remove("loginId");
        },
      });
      this.movePage("/");
    },
  },
  computed: {
    user() {
      let loginUser = this.$store.getters.getLoginUser;
      return loginUser;
    },
    loginUser() {
      var id = this.$cookies.get("loginId");
      console.log(id);
      this.$store.dispatch("me", {
        data: id,
        callback: function () {
          console.log(this.user);
        },
      });
      return this.$store.getters.getLoginUser;
    },
  },
};
</script>

<style scope>
#logo {
  width: 30px;
}
#nav {
  margin-bottom: 5%;
}
</style>

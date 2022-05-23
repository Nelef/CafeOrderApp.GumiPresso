<template>
  <tr>
    <td>{{ id }}</td>
    <td>{{ user_id }}</td>
    <td>{{ rating }}</td>
    <td>{{ comment }}</td>
    <b-button @click="modify()" class="m-2" v-show="this.visible">수정</b-button>
    <b-button class="m-2" v-show="this.visible">삭제</b-button>
  </tr>
</template>

<script>
export default {
  name: "list-row",
  data() {
    return {
      visible: false,
    };
  },
  props: {
    id: { type: String },
    user_id: { type: String },
    rating: { type: Number },
    comment: { type: String },
    loginUserCheck: { type: String },
    index: { type: Number },
  },
  computed: {
    loginUser() {
      let loginUser = this.$store.getters.getLoginUser;
      console.log(loginUser.id);
      return loginUser;
    },
  },
  created() {
    if (this.loginUser.id == this.loginUserCheck) {
      this.visible = true;
    }
    console.log(this.visible);
  },
  methods: {
    modify() {
      let text = window.prompt("수정할 텍스트: ");
      this.$emit("modify-event", text);
    },
  },
};
</script>

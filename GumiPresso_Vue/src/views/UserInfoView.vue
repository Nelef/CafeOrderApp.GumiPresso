<template>
  <div>
    <b-container>
      <div>
        <h4>{{ user.name }} 회원님</h4>
      </div>
      <hr />
      <div>ID : {{ user.id }}</div>
      <div>Stamp : {{ user.stamps }}개</div>
      <div>{{ this.level }} {{ this.grade }} 단계 입니다</div>
      <div>{{ this.remainStamp }}만 더 모으면 다음 단계 입니다</div>
    </b-container>
    <b-container>
      <hr />
      <hr />
      이제까지 주문 내역은
      <hr />

      <order-detail-view />
    </b-container>
  </div>
</template>

<script>
import OrderDetailView from "@/views/OrderDetailView.vue";
//import VueCookies from "vue-cookies";
export default {
  data() {
    return {
      users: {
        id: "",
        pass: "",
        name: "",
        stamps: "",
      },
      level: "",
      remainStamp: 0,
      grade: 0,

      orderList: [
        {
          orderid: "dd",
          date: "",
        },

        {
          orderid: "ddd",
          date: "",
        },
      ],
    };
  },
  components: {
    OrderDetailView,
  },

  methods: {
    getGrade(stamp) {
      let level;
      let grade;
      let remainStamp;
      if (stamp == 0) {
        level = "나무";
      } else if (stamp < 50) {
        level = "씨앗";
        grade = parseInt(stamp / 10) + 1;
        remainStamp = 10 - (stamp % 10);
      } else if (stamp < 125) {
        level = "꽃";
        grade = parseInt((stamp - 50) / 15);
        remainStamp = 15 - (stamp % 15) + 1;
      } else if (stamp < 225) {
        level = "열매";
        grade = parseInt((stamp - 125) / 20) + 1;
        remainStamp = 20 - (stamp % 20);
      } else {
        level = "커피콩";
        grade = parseInt((stamp - 225) / 25) + 1;
        remainStamp = 25 - (stamp % 20);
      }
      this.level = level;
      this.grade = grade;
      this.remainStamp = remainStamp;
    },
  },
  computed: {
    user() {
      return this.$store.getters.getLoginUser;
    },
  },
  created() {
    this.getGrade(this.user.stamps);
  },
};
</script>

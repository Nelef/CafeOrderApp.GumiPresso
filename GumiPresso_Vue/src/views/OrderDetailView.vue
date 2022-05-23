<template>
  <div class="container">
    <div v-for="(order, idx) in orderList" :key="idx">
      <div>
        <a @click="openPage(order)">{{ order.orderTime }} {{ order.orderId }}</a>
      </div>
    </div>
    <hr />
    <div v-show="show">
      <div v-for="(item, idx) in orderJoinProduct" :key="idx">
        <!-- <b-card
          :img-src="require('@/assets/menu/' + item.img)"
          img-left="img-left"
          img-height="50"
          img-width="50"
        > -->
        <b-card-text style="display: inline">
          품명: {{ item.name }}, 단가: {{ item.price }}, {{ item.quantity }}잔</b-card-text
        >
        <b-button disabled>{{ item.price * item.quantity }}원</b-button>
        <!-- </b-card> -->
      </div>

      <hr />
      <p>총 비용: {{ getTotalCost() }}원, 스탬프 적립 : {{ getStamps() }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "order-datail-view",
  data() {
    return {
      // orderList: [],
      orderDetailList: [],
      // orderJoinProduct: [
      //   {
      //     name: "",
      //     price: 0,
      //     quantity: 0,
      //     img: "",
      //   },
      // ],
      show: false,
    };
  },
  created() {
    this.$store.dispatch("selectOrder", this.loginUser);
    this.$store.dispatch("selectOrderDetail", 0);
    // this.orderList = this.$store.getters.getOrderList;
  },
  computed: {
    orderJoinProduct() {
      let o = this.$store.getters.getOrderDetailList;
      console.log(o);
      return o;
    },
    loginUser() {
      return this.$store.getters.getLoginUser;
    },
    orderList() {
      return this.$store.getters.getOrderList;
    },
  },

  methods: {
    openPage(idx) {
      this.$store.dispatch("selectOrderDetail", idx.oId);
      this.show = true;
      this.orderJoinProduct = this.$store.getters.getOrderDetailList;
      // this.orderDetailList = this.$store.getters.getOrderDetailList;
      // let tmp = [];
      // this.$store.dispatch("selectProducts");
      // tmp = this.$store.getters.getProducts;

      // for (let i = 0; i < this.orderDetailList.length; i++) {
      //   let tmp2 = {
      //     name: "",
      //     price: 0,
      //     quantity: 0,
      //     img: "",
      //   };
      //   tmp2.name = tmp[this.orderDetailList[i].product_id - 1].name;
      //   tmp2.price = tmp[this.orderDetailList[i].product_id - 1].price;
      //   tmp2.quantity = this.orderDetailList[i].quantity;
      //   tmp2.img = tmp[this.orderDetailList[i].product_id - 1].img;

      //   this.orderJoinProduct.push(tmp2);
      // }
      // this.orderJoinProduct.splice(0, 1);
    },
    getTotalCost() {
      let sum = 0;
      for (let i = 0; i < this.orderJoinProduct.length; i++) {
        sum += this.orderJoinProduct[i].price * this.orderJoinProduct[i].quantity;
      }
      return sum;
    },
    getStamps() {
      let sum = 0;
      for (let i = 0; i < this.orderJoinProduct.length; i++) {
        sum += this.orderJoinProduct[i].quantity;
      }
      return sum;
    },
  },
};
</script>

<style scoped></style>

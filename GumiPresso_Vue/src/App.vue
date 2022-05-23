<template>
  <div id="app">
    <header-nav />
    <router-view />
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
export default {
  name: "app",
  components: {
    HeaderNav,
  },
  created() {
    let t_user = [
      { id: "ssafy01", name: "싸피01", pass: "pass01", stamps: 0 },
      { id: "ssafy02", name: "싸피02", pass: "pass02", stamps: 0 },
      { id: "ssafy03", name: "싸피03", pass: "pass03", stamps: 0 },
      { id: "ssafy04", name: "싸피04", pass: "pass04", stamps: 0 },
      { id: "ssafy05", name: "싸피05", pass: "pass05", stamps: 0 },
      { id: "dd", name: "싸피05", pass: "d", stamps: 3 },
      { id: "id 01", name: "싸피55", pass: "d", stamps: 3 },
    ];
    localStorage.setItem("t_user", JSON.stringify(t_user));

    let t_product = [
      { id: 1, name: "coffee1", type: "coffee", price: 1000, img: "coffee1.png" },
      { id: 2, name: "coffee2", type: "coffee", price: 2000, img: "coffee2.png" },
      { id: 3, name: "coffee3", type: "coffee", price: 3000, img: "coffee3.png" },
      { id: 4, name: "coffee4", type: "coffee", price: 4000, img: "coffee4.png" },
      { id: 5, name: "coffee5", type: "coffee", price: 5000, img: "coffee5.png" },
      { id: 6, name: "coffee6", type: "coffee", price: 6000, img: "coffee6.png" },
      { id: 7, name: "coffee7", type: "coffee", price: 7000, img: "coffee7.png" },
      { id: 8, name: "coffee8", type: "coffee", price: 8000, img: "coffee8.png" },
      { id: 9, name: "coffee9", type: "coffee", price: 9000, img: "coffee9.png" },
      { id: 10, name: "cookie", type: "cookie", price: 10000, img: "cookie.png" },
    ];
    localStorage.setItem("t_product", JSON.stringify(t_product));

    let t_order = [
      {
        order_id: 1,
        user_id: "ssafy01",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 2,
        user_id: "ssafy01",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 3,
        user_id: "dd",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 4,
        user_id: "dd",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 5,
        user_id: "3",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 6,
        user_id: "id 06",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 7,
        user_id: "id 07",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 8,
        user_id: "id 08",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 9,
        user_id: "id 09",
        order_time: "2022-02-18 19:52:46",
        completed: "N",
      },
      {
        order_id: 10,
        user_id: "id 10",
        order_time: "2022-02-18 19:52:46,",
        completed: "N",
      },
    ];
    localStorage.setItem("t_order", JSON.stringify(t_order));

    let t_order_detail = [
      { order_id: 1, product_id: 1, quantity: 1 },
      { order_id: 2, product_id: 2, quantity: 2 },
      { order_id: 3, product_id: 3, quantity: 3 },
      { order_id: 3, product_id: 4, quantity: 4 },
      { order_id: 3, product_id: 5, quantity: 5 },
      { order_id: 4, product_id: 6, quantity: 6 },
      { order_id: 4, product_id: 7, quantity: 7 },
      { order_id: 8, product_id: 8, quantity: 8 },
      { order_id: 9, product_id: 9, quantity: 9 },
      { order_id: 10, product_id: 10, quantity: 10 },
    ];
    localStorage.setItem("t_order_detail", JSON.stringify(t_order_detail));

    let t_comment = [
      { id: 1, user_id: "id 01", product_id: 1, rating: 1, comment: "comment 01" },
      { id: 2, user_id: "id 02", product_id: 1, rating: 2, comment: "comment 02" },
      { id: 3, user_id: "id 03", product_id: 1, rating: 3, comment: "comment 03" },
      { id: 4, user_id: "id 04", product_id: 4, rating: 4, comment: "comment 04" },
      { id: 5, user_id: "id 05", product_id: 5, rating: 5, comment: "comment 05" },
      { id: 6, user_id: "id 06", product_id: 6, rating: 6, comment: "comment 06" },
      { id: 7, user_id: "id 07", product_id: 7, rating: 7, comment: "comment 07" },
      { id: 8, user_id: "id 08", product_id: 8, rating: 8, comment: "comment 08" },
      { id: 9, user_id: "id 09", product_id: 9, rating: 9, comment: "comment 09" },
      { id: 10, user_id: "id 10", product_id: 10, rating: 10, comment: "comment 10" },
    ];
    localStorage.setItem("t_comment", JSON.stringify(t_comment));
  },
};
</script>

<style></style>

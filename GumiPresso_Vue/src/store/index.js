import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common.js";

// import dayjs from "dayjs";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {
      id: "",
      name: "",
      pass: "",
      stamps: 0,
    },
    loginUser: {
      id: "",
      name: "",
      pass: "",
      stamps: 0,
    },
    products: [],
    productDetail: {
      id: 0,
      name: "",
      type: "",
      price: 0,
      img: "",
    },
    order: {
      userid: "",
      time: "",
    },
    orderList: [],
    orderDetailList: [],
    orderDetail: [],
    comments: [],
    comment: {
      id: 0,
      userId: "",
      productId: 0,
      rating: 0,
      comment: "",
    },
    orderQuantity: 0,
  },
  getters: {
    getOrderQuantity(state) {
      return state.orderQuantity;
    },
    getOrderDetailList(state) {
      return state.orderDetailList;
    },
    getOrderList(state) {
      return state.orderList;
    },
    getOrderDetail(state) {
      return state.orderDetail;
    },
    getOrder(state) {
      return state.order;
    },
    getUser(state) {
      return state.user;
    },
    getLoginUser(state) {
      return state.loginUser;
    },
    getProducts(state) {
      return state.products;
    },
    getProductDetail(state) {
      return state.productDetail;
    },
    getComments(state) {
      return state.comments;
    },
  },
  mutations: {
    SELECT_ORDER(state, order) {
      state.orderList = order;
    },
    INSERT_ORDER_DETAIL(state, orderDetail) {
      state.orderDetail = orderDetail;
    },
    INSERT_ORDER(state, order) {
      state.order.userid = order.orderid;
      state.order.time = order.time;
    },
    INSERT_USER(state, user) {
      state.user.id = user.id;
      state.user.name = user.name;
      state.user.pass = user.pass;
      state.user.stamps = user.stamps;
    },
    LOGIN_USER(state, loginUser) {
      state.loginUser.id = loginUser.id;
      state.loginUser.name = loginUser.name;
      state.loginUser.pass = loginUser.pass;
      state.loginUser.stamps = loginUser.stamps;
    },
    SELECT_PRODUCT(state, products) {
      state.products = products;
    },
    SELECT_PRODUCT_DETAIL(state, productDetail) {
      state.productDetail.id = productDetail.id;
      state.productDetail.name = productDetail.name;
      state.productDetail.type = productDetail.type;
      state.productDetail.price = productDetail.price;
      state.productDetail.img = productDetail.img;
    },
    SELECT_ORDER_LIST(state, orderDetailList) {
      state.orderDetailList = orderDetailList;
    },
    INSERT_STAMP(state) {
      state.user = state;
    },
    SELECT_COMMENTS(state, comments) {
      state.comments = comments;
    },
    SELECT_COMMENT(state, comment) {
      state.comment = comment;
    },
    INSERT_COMMENT(state, comment) {
      state.comments.push(comment);
    },

    //총주문수량
    SELECT_ORDER_DETAIL_LIST(state, orderQuantity) {
      state.orderQuantity = orderQuantity;
    },
    //총주문수량
  },
  actions: {
    selectOrderQuantity({ commit }, productId) {
      http
        .get(`/order/detail/${productId.data}`)
        .then((response) => {
          commit("SELECT_ORDER_DETAIL_LIST", response.data);
          productId.callback;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    selectOrderDetail({ commit }, orderid) {
      http
        .get(`/order/${orderid}`)
        .then((response) => {
          commit("SELECT_ORDER_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    selectOrder({ commit }, user) {
      http
        .post("/order/", user)
        .then((response) => {
          commit("SELECT_ORDER", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    insertOrder({ commit }, orderList) {
      http
        .post("/order/order", orderList)
        .then((response) => {
          commit("INSERT_ORDER", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    insertUser({ commit }, user) {
      http
        .post("/user/join", user)
        .then((response) => {
          commit("INSERT_USER", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    login({ commit }, user) {
      http
        .post("/user/login", user.data)
        .then((response) => {
          commit("LOGIN_USER", response.data);
          console.log(response.data);
          user.callback();
        })
        .catch((error) => {
          console.log(error);
          alert("로그인 실패. 아이디와 비밀번호를 확인하세요");
        });
    },
    resetLoginUser({ commit }) {
      http
        .get("/user/logout")
        .then((response) => {
          commit("LOGIN_USER", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    me({ commit }, user) {
      http
        .get("/user/me", user)
        .then((response) => {
          commit("LOGIN_USER", response.data);
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
          alert("다시 로그인 해 주세요");
        });
    },
    selectProducts({ commit }) {
      http
        .get("/product/")
        .then((response) => {
          commit("SELECT_PRODUCT", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    selectProductDetail({ commit }, product_id) {
      http
        .get(`/product/${product_id}`)
        .then((response) => {
          commit("SELECT_PRODUCT_DETAIL", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    selectComments({ commit }, product_id) {
      http
        .get(`/comment/${product_id.data}`)
        .then((response) => {
          commit("SELECT_COMMENTS", response.data);
          product_id.callback();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    addComment({ commit }, payload) {
      console.log("store-add", payload.data);
      http
        .post("/comment/", payload.data)
        .then((response) => {
          console.log("reponse", response.data);
          commit("INSERT_COMMENT", response.data);
          payload.callback();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    updateComment({ commit }, comment) {
      http
        .put(`/comment/`, comment)
        .then((response) => {
          commit("SELECT_COMMENT", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteComment({ commit }, payload) {
      http
        .delete(`/comment/${payload.data}`)
        .then((response) => {
          commit("SELECT_COMMENTS", response.data);
          console.log(response);
          payload.callback();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  modules: {},
});

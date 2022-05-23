<template>
  <div>
    <b-button v-b-toggle.sidebar-right v-show="loginUser.id">주문서 보기</b-button>
    <b-sidebar id="sidebar-right" title="주문서 작성중" right shadow v-show="showCart">
      <div v-for="(item, idx) in cartItem" :key="idx">
        <div v-if="idx > 0">
          <b-card :title="item.name" img-left="img-left" img-height="50" img-width="50">
            <img
              :src="require(`@/assets/menu/${item.img}`)"
              style="height: 30px; margin-right: 10%"
            />
            <b-card-text style="display: inline; margin-right: 10%">{{ item.name }}</b-card-text>
            <b-button>{{ item.count }}</b-button>
          </b-card>
        </div>
        <div v-else><div></div></div>
      </div>
      <div style="text-align: center">
        <b-button v-show="false">{{ totalcount }}</b-button>
        <div style="margin-top: 5%" v-show="cartItem.length > 1">{{ totalPrice }}원</div>
        <hr />
        <b-button @click="order()" variant="primary" v-show="cartItem.length > 1">주문</b-button>
      </div>
    </b-sidebar>

    <b-container id="container">
      <b-row>
        <b-col cols="3" v-for="(item, idx) in products" :key="idx" id="product-list-div">
          <b-card :title="item.name" :img-src="require('@/assets/menu/' + item.img)" img-top>
            <b-card-text>{{ item.price }} 원</b-card-text>
          </b-card>
          <div class="item-tr" v-show="loginUser.id">
            <tr id="button-list-tr">
              <td>
                <b-button
                  @click="addCount(idx)"
                  v-model="eachCount"
                  class="product-list-button"
                  variant="primary"
                  >{{ data_eachCount[idx] }}</b-button
                >
              </td>
              <td>
                <b-button @click="addCount(idx)" class="product-list-button" variant="success"
                  >+</b-button
                >
              </td>
              <td>
                <b-button @click="decCount(idx)" class="product-list-button">-</b-button>
              </td>
              <td>
                <b-button @click="movePage(idx)" class="product-list-button" variant="warning"
                  >정보</b-button
                >
              </td>
            </tr>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import store from "@/store";
export default {
  name: "product-list-view",
  data() {
    return {
      data_eachCount: [0],
      cartItem: [
        {
          id: 0,
          name: "",
          type: "",
          prcie: "",
          img: "",
          count: 0,
        },
      ],
      orderList: [
        {
          userId: "",
          productId: 0,
          quantity: 0,
        },
      ],
      totalcount: 0,
      showCartBtn: true,
      showCart: true,
      totalPrice: 0,
    };
  },
  created() {
    this.$store.dispatch("selectProducts");
    this.data_eachCount.length = this.products.length;
    this.data_eachCount.fill(0);
  },
  methods: {
    order() {
      for (let i = 1; i < this.cartItem.length; i++) {
        let o = {
          userId: "",
          orderId: 0,
          productId: 0,
          quantity: 0,
        };
        o.userId = this.loginUser.id;
        o.productId = this.cartItem[i].id;
        o.quantity = this.cartItem[i].count;
        this.orderList.push(o);
      }
      this.$store.dispatch("insertOrder", this.orderList);

      alert("주문이 완료되었습니다.");
      this.movePage("");
    },
    addCount(idx) {
      let isInCart = false;
      let cartIdx;
      this.data_eachCount[idx]++;
      for (let i = 0; i < this.cartItem.length; i++) {
        if (this.products[idx].id === this.cartItem[i].id) {
          isInCart = true;
          cartIdx = i;
          break;
        }
      }
      if (!isInCart) {
        this.cartItem.push(this.products[idx]);
        this.cartItem[this.cartItem.length - 1].count = 1;
      } else {
        this.cartItem[cartIdx].count++;
      }
      this.totalPrice = this.products[idx].price + this.totalPrice;
      this.totalcount++;
    },
    decCount(idx) {
      let cartIdx;
      if (this.data_eachCount[idx] > 0) {
        this.data_eachCount[idx]--;
      }
      for (let i = 0; i < this.cartItem.length; i++) {
        if (this.products[idx].id === this.cartItem[i].id) {
          cartIdx = i;
          break;
        }
      }
      this.cartItem[cartIdx].count--;
      if (this.cartItem[cartIdx].count === 0) {
        this.cartItem.splice(cartIdx, 1);
      }
      this.totalcount--;
    },

    movePage(idx) {
      if (idx === "") {
        this.$router.push("/user-info");
      } else {
        this.$router.push({ name: "product-detail-view", params: { id: this.products[idx].id } });
      }
    },
  },
  computed: {
    products() {
      return this.$store.getters.getProducts;
    },
    loginUser() {
      let loginUser = store.getters.getLoginUser;
      console.log(loginUser.id);
      return loginUser;
    },
    eachCount(index) {
      return this.data_eachCount[index];
    },
  },
};
</script>

<style scope>
.item-tr {
  display: flex;
  justify-content: center;
}
#container {
  text-align: center;
}
#product-list-div {
  margin-top: 1%;
}
#button-list-tr {
  margin-top: 2%;
}
.product-list-button {
  margin-right: 10px;
}
</style>

<template>
  <div>
    <b-container>
      <div class="p-5">
        <b-card-title class="text-center">상품 평점</b-card-title>
      </div>

      <b-row>
        <b-col>
          <img :src="require('@/assets/menu/' + this.product.img)" />
        </b-col>

        <b-list-group style="width: 40rem" class="text-center">
          <b-list-group-item> 상품명 : {{ product.name }}</b-list-group-item>
          <b-list-group-item> 상품단가 : {{ product.price }}</b-list-group-item>
          <b-list-group-item> 총 주문 수량 : {{ this.totalOrderQuantity }}</b-list-group-item>
          <b-list-group-item> 평가 수 : {{ this.comments.length }}</b-list-group-item>
          <b-list-group-item> 평균 평점 : {{ this.avg_rateing }}</b-list-group-item>
        </b-list-group>
      </b-row>
      <b-row class="full-width">
        <b-col cols="9"></b-col>
        <b-col cols="2" class="m-3">
          <b-button v-b-modal.modal-prevent-closing>한줄평 남기기</b-button>
          <b-modal
            id="modal-prevent-closing"
            ref="modal"
            title="한줄평 남기기"
            @show="resetModal"
            @hidden="resetModal"
            @ok="handleOk"
          >
            <form ref="form" @submit.stop.prevent="handleSubmit">
              <b-form-group
                label="평점"
                label-for="rating-input"
                invalid-feedback="(1~10)"
                :state="ratingState"
              >
                <b-form-input
                  type="number"
                  id="name-input"
                  v-model="rating"
                  invalid-feedback="1~10"
                  :state="ratingState"
                  required
                ></b-form-input>
              </b-form-group>
              <b-form-group
                label="한줄평"
                label-for="comment-input"
                invalid-feedback="한줄평"
                :state="commentState"
              >
                <b-form-input
                  id="name-input"
                  v-model="com"
                  :state="commentState"
                  required
                ></b-form-input>
              </b-form-group>
            </form>
          </b-modal>
        </b-col>
      </b-row>

      <hr class="my-3" />
      <b-row>
        <div class="m-2">자신이 남긴 평가만 수정, 삭제할 수 있습니다.</div>
      </b-row>
      <div v-if="comments.length">
        <table>
          <colgroup>
            <col style="width: 5%" />
            <col style="width: 20%" />
            <col style="width: 40%" />
            <col style="width: 20%" />
            <col style="width: 15%" />
          </colgroup>
          <thead>
            <tr>
              <th>번호</th>
              <th>사용자</th>
              <th>평점</th>
              <th>한줄평</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(comment, index) in comments" :key="index">
              <td>{{ index + 1 }}</td>
              <td>{{ comment.userId }}</td>
              <td>{{ comment.rating }}</td>
              <td>{{ comment.comment }}</td>
              <b-button
                @click="modifyComment(comment)"
                class="m-2"
                v-show="checkVisibleModify(comment.userId)"
                >수정</b-button
              >
              <b-button
                @click="deleteComment(comment.id)"
                class="m-2"
                v-show="checkVisibleModify(comment.userId)"
                >삭제</b-button
              >
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else class="text-center">상품평이 없습니다.</div>
    </b-container>
  </div>
</template>

<script>
import store from "@/store";

export default {
  name: "product-detail-view",
  watch: {
    comments() {
      console.log("in watch");
      this.getAvgRating();
    },
  },
  computed: {
    product() {
      return store.getters.getProductDetail;
    },
    comments() {
      return store.getters.getComments;
    },
    // loginUser() {
    //   this.loginUser = store.getters.getLoginUser;
    //   console.log(loginUser.id);
    //   return this.loginUser;
    // },
  },
  data() {
    return {
      productId: 0,

      visible: false,

      rating: 0,
      ratingState: null,
      submittedRatings: [],

      com: "",
      commentState: null,
      submittedComments: [],

      loginUser: {
        id: "",
        name: "",
        pass: "",
        stamps: 0,
      },

      avg_rateing: 0,
      totalOrderQuantity: 0,
    };
  },
  created() {
    this.loginUser = store.getters.getLoginUser;
    this.productId = `${this.$route.params.id}`;
    store.dispatch("selectProductDetail", `${this.$route.params.id}`);
    store.dispatch("selectComments", {
      data: this.$route.params.id,
      callback: function () {
        // this.comments = store.getters.getComments;
      },
    });
    store.dispatch("selectOrderQuantity", {
      data: this.productId,
      callback: function () {},
    });
    this.totalOrderQuantity = store.getters.getOrderQuantity;
  },
  methods: {
    checkVisibleModify(id) {
      if (this.loginUser.id == id) {
        return true;
      }
      return false;
    },
    deleteComment(index) {
      console.log("index", index);
      let pid = this.$route.params.id;
      store.dispatch("deleteComment", {
        data: index,
        callback: function () {
          // alert("삭제가 완료되었습니다");
          store.dispatch("selectComments", {
            data: pid,
            callback: function () {},
          });
        },
      });
      this.comments = store.getters.getComments;
    },
    addComment(comment) {
      let pid = this.$route.params.id;
      console.log(pid);
      store.dispatch("addComment", {
        data: comment,
        callback: function () {
          store.dispatch("selectComments", {
            data: pid,
            callback: function () {
              // this.comments = store.getters.getComments;
            },
          });
        },
      });
    },
    modifyComment(comment) {
      let text = window.prompt("수정할 텍스트: ");
      // let send_com = {
      //   id: 0,
      //   userId: "",
      //   productId: 0,
      //   rating: 0,
      //   comment: "",
      // };

      // send_com.userId = this.loginUser.id;
      // send_com.productId = `${this.$route.params.id}`;
      // send_com.rating = parseInt(this.rating);
      // send_com.comment = text;
      comment.comment = text;
      console.log(comment);
      this.$store.dispatch("updateComment", comment);
    },
    getAvgRating() {
      let res = 0;
      for (let i = 0; i < this.comments.length; i++) {
        res += this.comments[i].rating;
      }
      if (!this.comments.length) {
        this.avg_rateing = 0;
      } else {
        this.avg_rateing = res / this.comments.length;
        console.log("leng" + res);
      }
    },
    checkFormValidity() {
      const valid = this.$refs.form.checkValidity();
      this.ratingState = valid;
      this.commentState = valid;
      return valid;
    },
    resetModal() {
      this.rating = 0;
      this.ratingState = null;
      this.com = "";
      this.commentState = null;
    },
    handleOk(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault();
      // Trigger submit handler
      this.handleSubmit();
    },
    handleSubmit() {
      // Exit when the form isn't valid
      if (!this.checkFormValidity()) {
        return;
      }
      if (0 >= parseInt(this.rating) || parseInt(this.rating) > 6) {
        alert("평점은 1~5으로 입력해주세요");
        return;
      }

      let send_com = {
        id: 0,
        userId: "",
        productId: 0,
        rating: 0,
        comment: "",
      };
      // send_com.id = parseInt(this.comments.length + 1);
      send_com.userId = this.loginUser.id;
      send_com.productId = `${this.$route.params.id}`;
      send_com.rating = parseInt(this.rating);
      send_com.comment = this.com;

      console.log(send_com.comment, send_com.userId);
      this.addComment(send_com);

      // Hide the modal manually
      this.$nextTick(() => {
        this.$bvModal.hide("modal-prevent-closing");
      });
    },
  },
};
</script>

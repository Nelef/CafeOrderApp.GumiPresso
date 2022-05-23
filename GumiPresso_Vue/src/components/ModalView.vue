<template>
  <div class="my-modal" v-if="visible" @click.self="handleWrapperClick">
    <div class="my-modal__dialog">
      <header class="modal-header">
        <span>{{ title }}</span>
      </header>
      <div class="modal-body">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">평점</span>
          </div>
          <input
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            v-model="comment.rating"
          />
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">한줄평</span>
          </div>
          <textarea
            class="form-control"
            aria-label="With textarea"
            v-model="comment.comment"
          ></textarea>
        </div>
      </div>
      <div class="modal-footer">
        <b-button @click="handleWrapperClick()">등록</b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "my-modal",
  props: {
    visible: {
      type: Boolean,
      require: true,
      default: false,
    },
    title: {
      type: String,
      require: false,
    },
    no: { type: String },
    user_id: { type: String },
    product_id: { type: Number },
  },
  data() {
    return {
      comment: {
        id: "",
        user_id: "",
        product_id: "",
        rating: 0,
        comment: "",
      },
    };
  },
  methods: {
    handleWrapperClick() {
      this.comment.id = this.no;
      this.comment.user_id = this.user_id;
      this.comment.product_id = this.product_id;
      console.log(this.comment.comment, this.comment.user_id);
      this.$store.dispatch("addComment", this.comment);
      this.$emit("update:visible", false);
      this.$store.getters.getComments;
    },
  },
};
</script>

<style lang="scss">
$module: "my-modal";
.#{$module} {
  // This is modal bg
  background-color: rgba(0, 0, 0, 0.7);
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  //This is modal layer
  &__dialog {
    left: 50%;
    top: 75px;
    width: 600px;
    position: absolute;
    background: #fff;
    margin-bottom: 50px;
  }

  &__header {
    font-size: 28px;
    font-weight: bold;
    line-height: 1.29;
    padding: 16px 16px 0 25px;
    position: relative;
  }
  &__body {
    padding: 25px;
    min-height: 150px;
    max-height: 412px;
    overflow-y: scroll;
  }
}
</style>

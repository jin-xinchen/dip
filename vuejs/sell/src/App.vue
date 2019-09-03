<template>
  <div id="app">
    <v-header :seller="seller"></v-header>
    <div class="tab border-1px">
      <div class="tab-item">
        <router-link to="/goods">商品</router-link>
      </div>
      <div class="tab-item">
        <router-link to="/ratings">评论</router-link>
        </div>
      <div class="tab-item">
      <router-link to="/seller">商家</router-link>
      </div>
    </div>
    <router-view/>

  </div>
</template>

<script type="text/ecmascript-6">
  import header from './components/header/header.vue';
  const ERR_OK = 0;
  export default {
    data() {
      return {
        seller: {}
      };
    },
    created() {
      this.$http.get('/api/seller').then((response) => {
        response = response.body;
        // console.log(response);
        if (response.error === ERR_OK) {
          this.seller = response.data;
        //  console.log(this.seller);
        }
      });
    },
    components: {
      'v-header': header
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus">
  #app
   .tab
     display:flex
     width:100%
     height:40px
     line-height:40px
     // border-bottom: 1px solid rgba(7, 17, 27, 0.1)
     position: relative
     &:after
       display: block
       position: absolute
       left: 0
       bottom: 0
       width: 100%
       border-top: 1px solid rgba(7, 17, 27, 0.1)
       content: ' '
     position: relative
     &:before
       display: block
       position: absolute
       left: 0
       top: 0
       width: 100%
       border-top: 1px solid rgba(7, 17, 27, 0.1)
       content: ' '
     .tab-item
        flex:1
        text-align:center
        & > a
          display:block
          font-size:14px
          color:rgb(77, 85, 93)
          &.router-link-active
            color:rgb(240,20,20)
    .border-1px
      &::after
        -webkit-transform scaleY(2)
        transform: scaleY(2)
@media (-webkit-min-device-pixel-ratio: 1.5),(-webkit-min-device-pixel-ratio: 1.5)
  .border-1px
    &::after
      -webkit-transform scaleY(0.7)
      transform: scaleY(0.7)
@media (-webkit-min-device-pixel-ratio: 2),(-webkit-min-device-pixel-ratio: 2)
  .border-1px
    &::after
      -webkit-transform scaleY(0.5)
      transform: scaleY(0.5)
@media (-webkit-min-device-pixel-ratio: 1.5),(-webkit-min-device-pixel-ratio: 1.5)
  .border-1px
    &::before
      -webkit-transform scaleY(0.7)
      transform: scaleY(0.7)
@media (-webkit-min-device-pixel-ratio: 2),(-webkit-min-device-pixel-ratio: 2)
  .border-1px
    &::before
      -webkit-transform scaleY(0.5)
      transform: scaleY(0.5)
</style>

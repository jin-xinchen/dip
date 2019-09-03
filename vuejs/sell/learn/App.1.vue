<template>
  <div id="app">
    <v-header></v-header>
    <div class="tab">
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

<script>
  import header from './components/header/header.vue';
  export default {
    components: {
      'v-header': header
    }
  };
</script>
//使用coli.io网站，用ip地址产生二维码，如输入http://192.168.1.2:8080/#!/goods
用手机微信扫描，在手机上就可以看到网页了。
<style lang="stylus" rel="stylesheet/stylus">
  #app
   .tab
     display:flex
     width:100%
     height:40px
     line-height:40px
     border-bottom: 1px solid rgba(7, 17, 27, 0.1)
     .tab-item
        flex:1
        text-align:center
        & > a
          display:block
          font-size:14px
          color:rgb(77, 85, 93)
          &.router-link-active
            color:rgb(240,20,20)
</style>

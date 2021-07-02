<template>
  <div class="logo">
    <h1 v-if="showTitle">{{ title }}</h1>
<!--    <router-link :to="{name:'dashboard'}">-->
      <!-- update-begin- author:sunjianlei --- date:20190814 --- for: logo颜色根据主题颜色变化 -->
<!--      <img v-if="layoutMode === 'sidemenu'" src="~@/assets/logo.png" alt="logo">-->
<!--      <img v-else src="~@/assets/logo_top.png" alt="logo">-->
      <!-- update-begin- author:sunjianlei --- date:20190814 --- for: logo颜色根据主题颜色变化 -->

<!--    </router-link>-->
  </div>
</template>

<script>
  import { mixin } from '@/utils/mixin.js'

  export default {
    name: 'Logo',
    mixins: [mixin],
    props: {
      title: {
        type: String,
        default: window.SYS_TITLE,
        required: false
      },
      showTitle: {
        type: Boolean,
        default: true,
        required: false
      }
    }
  }
</script>
<style lang="less" scoped>
  /*缩小首页布 局顶部的高度*/
  @height: 59px;

  .sider {
    box-shadow: none !important;
    .logo {
      color: #ffffff;
      height: @height !important;
      line-height: @height !important;
      box-shadow: none !important;
      transition: background 300ms;
      background: #002140;
      h1 {
        font-size: 24px;
        color: #ffffff;
      }
      a {
        color: white;
        &:hover {
          color: rgba(255, 255, 255, 0.8);
        }
      }

    }

    &.light .logo {
      background-color: @primary-color;
    }
  }
</style>
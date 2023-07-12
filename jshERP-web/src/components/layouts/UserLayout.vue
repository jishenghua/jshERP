<template>
  <div class="back-layout">
    <div id="userLayout" :class="['user-layout-wrapper', device]">
      <div class="container">
        <div class="poster-img">
          <img src="/static/rightImg.png?v=320">
        </div>
        <div class="right-form">
          <div class="top">
            <div class="header">
              <a-row>
                <a-col>
                  <a href="/">
                    <span class="title">{{systemTitle}}</span>
                    <small class="desc">V3.2</small>
                  </a>
                </a-col>
              </a-row>
            </div>
          </div>
          <route-view></route-view>
        </div>
      </div>
    </div>
    <div class="footer" v-if="device === 'desktop'">
      <p>
        © 2015-2030 {{systemTitle}} - All Right Reserved
        <a style="color:#00458a;" :href="systemUrl" target="_blank">官方网站</a>
        版权所有
      </p>
    </div>
  </div>
</template>

<script>
  import RouteView from "@/components/layouts/RouteView"
  import { mixinDevice } from '@/utils/mixin.js'

  export default {
    name: "UserLayout",
    components: { RouteView },
    mixins: [mixinDevice],
    data () {
      return {
        systemTitle: window.SYS_TITLE,
        systemUrl: window.SYS_URL,
      }
    },
    mounted () {
      document.body.classList.add('userLayout')
    },
    beforeDestroy () {
      document.body.classList.remove('userLayout')
    },
  }
</script>

<style scoped>
  .back-layout {
    width: 100%;
    height: 100%;
    background-image: url(/static/bgimg.png?v=1);
    background-size: cover;
    background-repeat: no-repeat;
    position: relative;
    overflow: hidden;
  }
  #userLayout.user-layout-wrapper.mobile {
    position: fixed;
    left: 6%;
    top: 10%;
    margin-left: 0px;
  }
</style>
<style lang="less" scoped>
  #userLayout.user-layout-wrapper {
    position: fixed;
    left: 50%;
    top: 16%;
    margin-left: -543px;
    height: 100%;

    &.mobile {
      .container {
        .main {
          max-width: 368px;
          width: 98%;
        }
      }
      .poster-img {
        display: none;
      }
    }

    .container {
      float: left;
      width: 100%;
      z-index: 99;
      height: 70%;

      .poster-img {
        float: left;
        height: 100%;
      }

      .right-form {
        background-size: 100%;
        position: relative;
        width: 340px;
        height: 520px;
        background: rgba(255, 255, 255, 1);
        border-radius: 8px;
        right: 0;
        top: 0;
        padding: 30px 30px 0 30px;
        margin-top: 30px;
        -webkit-box-shadow: 0 2px 6px 0 rgb(0 0 0 / 10%);
        box-shadow: 0 2px 6px 0 rgb(0 0 0 / 10%);
        overflow: hidden;

        a {
          text-decoration: none;
        }

        .top {
          text-align: center;

          .header {
            height: 44px;
            line-height: 44px;
            margin-top: 45px;
            margin-bottom: 45px;
            .title {
              font-size: 35px;
              color: #666;
              font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
              font-weight: 700;
              position: relative;
              top: 2px;
            }
            .desc {
              font-size: 16px;
              color: #666;
              margin-top: 12px;
              margin-left: 10px;
              margin-bottom: 40px;
            }
          }
        }

        .main {
          min-width: 260px;
          width: 280px;
          margin: 0 auto;
        }
      }
    }
  }
  .footer {
    position: fixed;
    left: 0;
    bottom: 0;
    width: 100%;
    padding: 0 16px;
    margin: 48px 0 12px;
    text-align: center;

    .links {
      margin-bottom: 8px;
      font-size: 14px;
      a {
        color: rgba(0, 0, 0, 0.45);
        transition: all 0.3s;
        &:not(:last-child) {
          margin-right: 40px;
        }
      }
    }
    .copyright {
      color: rgba(0, 0, 0, 0.45);
      font-size: 14px;
    }
  }
</style>
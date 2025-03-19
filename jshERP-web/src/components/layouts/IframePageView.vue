<template>
    <iframe :id="id" :src="url" frameborder="0" width="100%" :height="height" scrolling="auto"></iframe>
</template>

<script>
  import {mixinDevice} from '@/utils/mixin.js'

  export default {
    name: "IframePageContent",
    inject:['closeCurrent'],
    mixins: [mixinDevice],
    data () {
      return {
        url: "",
        id:"",
        height: ""
      }
    },
    created () {
      this.goUrl()
    },
    methods: {
      goUrl () {
        let url = this.$route.meta.url
        this.id = this.$route.path
        if (this.isMobile()) {
          this.height = 800
        } else {
          this.height = document.documentElement.clientHeight-100
        }
        console.log("------url------"+url)
        if (url !== null && url !== undefined) {
          if(url) {
            url = url.replace('/system','')
            if(url.indexOf('.html') === -1) {
              //地址不以.html结尾的，需要重新构造url
              let urlArr = url.split('/')
              url = '/' + urlArr[1] + '/' + urlArr[2] + '/' + urlArr[2] + '.html?t=' + urlArr[3]
            }
            url = document.location.protocol + '//' + window.location.host + url
          }
          this.url = url
        }
      }
    }
  }
</script>

<style>
</style>
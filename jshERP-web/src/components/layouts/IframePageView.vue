<template>
    <iframe :id="id" :src="url" frameborder="0" width="100%" :height="height" scrolling="auto"></iframe>
</template>

<script>
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
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
        console.log("------token------"+Vue.ls.get(ACCESS_TOKEN))
        if (url !== null && url !== undefined) {
          //外部url加入token
          let token = Vue.ls.get(ACCESS_TOKEN);
          if(url) {
            url = url.replace('/system','')
            url = document.location.protocol + '//' + window.location.host + url
          }
          this.url = url + '?token=' + token;
        }
      }
    }
  }
</script>

<style>
</style>
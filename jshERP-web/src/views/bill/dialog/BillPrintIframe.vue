<template>
  <div></div>
</template>

<script>
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import {mixinDevice} from '@/utils/mixin.js'

  export default {
    name: "BillPrintIframe",
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
    updated () {
      this.goUrl()
    },
    watch: {
      $route(to, from) {
        this.goUrl();
      }
    },
    methods: {
      show(record, type) {

      },
      goUrl () {
        let url = this.$route.meta.url
        this.id = this.$route.path
        if (this.isMobile()) {
          this.height = 800
        } else {
          this.height = document.documentElement.clientHeight-130
        }
        console.log("------url------"+url)
        console.log("------token------"+Vue.ls.get(ACCESS_TOKEN))
        if (url !== null && url !== undefined) {
          //外部url加入token
          let token = Vue.ls.get(ACCESS_TOKEN);
          this.url = url + '?token=' + token;
        }
      }
    }
  }
</script>

<style>
</style>
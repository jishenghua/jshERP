<template>
    <iframe :id="id" :src="url" frameborder="0" width="100%" :height="height" scrolling="auto"></iframe>
</template>

<script>
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import PageLayout from '../page/PageLayout'
  import RouteView from './RouteView'

  export default {
    name: "IframePageContent",
    inject:['closeCurrent'],
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
      goUrl () {
        let url = this.$route.meta.url
        this.id = this.$route.path
        this.height = document.documentElement.clientHeight-130
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
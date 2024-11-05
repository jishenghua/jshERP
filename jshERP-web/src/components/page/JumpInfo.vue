<template>
  <a-modal
    :title="title"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :footer="null"
    :closable="false"
    cancelText=""
    style="top:15%;height: 70%;overflow-y: hidden">
    <div>
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :span="24" style="height: 150px; padding: 50px">
            请使用新网址：<a href="https://cloud.gyjerp.com" target="_blank" style="text-decoration:underline">https://cloud.gyjerp.com</a>
            <a href="https://cloud.gyjerp.com" target="_blank" style="padding-left: 10px; font-weight: bold">点击立即跳转</a>
            <br/>
            <br/>
            如有疑问请加微信：shenhua861584
          </a-col>
        </a-row>
      </a-form>
    </div>
  </a-modal>
</template>

<script>
import { postAction } from '@/api/manage'

export default {
  name: "JumpInfo",
  data () {
    return {
      title: "跳转提示",
      modalWidth: 500,
      visible: false,
      confirmLoading: false,
    }
  },
  methods: {
    handleShow: function () {
      this.visible = true
    }
  }
}
</script>
<style scoped>
</style>
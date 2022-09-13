<template>
  <a-modal
    :title="title"
    :width="400"
    :visible="visible"
    :confirm-loading="confirmLoading"
    @cancel="handleCancel"
    wrapClassName="ant-modal-cust-warp"
    style="top:25%;height: 45%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" @click="handleCancel">取消</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-row class="form-row" :gutter="24">
        <a-col :md="24" :sm="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="第一步：">
            <a target="_blank" href="/doc/goods_template.xls"><b>商品Excel模板[下载]</b></a>
            <p>注意：模板中的第一行请勿删除</p>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row class="form-row" :gutter="24">
        <a-col :md="24" :sm="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="第二步：">
            <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleMaterialImportExcel">
              <a-button type="primary" icon="import">导入</a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
    </a-spin>
  </a-modal>
</template>

<script>
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import Vue from 'vue'
  export default {
    name: 'MaterialImportModal',
    data () {
      return {
        title:"",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 18 },
        },
        confirmLoading: false,
        disableMixinCreated: true,
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
        url: {
          importExcelUrl: "/material/importExcel",
        }
      }
    },
    created () {
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}${this.url.importExcelUrl}`;
      }
    },
    methods: {
      init () {
        this.model = Object.assign({}, '')
        this.visible = true
      },
      /* 导入 */
      handleMaterialImportExcel(info){
        this.loading = true
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          if (info.file.response) {
            // this.$message.success(`${info.file.name} 文件上传成功`);
            if (info.file.response.code === 200) {
              this.$message.success(info.file.response.data || `${info.file.name} 文件上传成功`)
            } else {
              this.$message.warning(info.file.response.data)
            }
            this.$emit('ok')
            this.visible = false;
            this.loading = false
          } else {
            this.$message.error(`${info.file.name} ${info.file.response.data}.`);
            this.loading = false
          }
        } else if (info.file.status === 'error') {
          this.$message.error(`文件上传失败: ${info.file.msg} `)
          this.loading = false
        }
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>

<style scoped>

</style>
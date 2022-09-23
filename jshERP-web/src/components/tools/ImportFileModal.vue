<template>
  <a-modal
    :title="title"
    :width="400"
    :visible="visible"
    :confirm-loading="confirmLoading"
    :maskClosable="false"
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
            <a target="_blank" :href="templateUrl"><b>{{templateName}}</b></a>
            <p>提示：模板中的第一行请勿删除</p>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row class="form-row" :gutter="24">
        <a-col :md="24" :sm="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="第二步：">
            <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
              <a-button type="primary" icon="import">导入</a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
    </a-spin>
  </a-modal>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  export default {
    name: 'ImportFileModal',
    mixins:[JeecgListMixin],
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
        templateUrl: '',
        templateName: '',
        url: {
          importExcelUrl: '',
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
      initModal(apiUrl, templateUrl, templateName) {
        this.url.importExcelUrl = apiUrl
        this.templateUrl = templateUrl
        this.templateName = templateName
        this.visible = true
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
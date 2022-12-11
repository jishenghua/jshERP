<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="800"
      :visible="visible"
      :confirmLoading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'89px','left':'151px'}"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭"
      wrapClassName="ant-modal-cust-warp"
      style="top:25%;height: 50%;overflow-y: hidden">
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机器码">
            <a-input v-decorator.trim="[ 'platformKey' ]" :readOnly="true"/>
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="插件激活码">
            <a-textarea :rows="2" placeholder="请输入插件激活码" v-decorator="[ 'platformValue' ]"/>
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import {getPlatformConfigByKey } from '@/api/api'
  import { getAction, postAction } from '../../../api/manage'
  export default {
    name: "PluginModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        machineCode: '',
        activationCode: '',
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
      }
    },
    created () {
    },
    methods: {
      edit () {
        this.form.resetFields();
        this.model = Object.assign({}, {});
        getAction("/plugin/getMacWithSecret").then((res)=>{
          if(res && res.code == 200) {
            this.model.platformKey = res.data
            getPlatformConfigByKey( {"platformKey": "activation_code"}).then((res)=>{
              if(res && res.code == 200) {
                this.model.platformValue = res.data.platformValue
                this.visible = true;
                this.$nextTick(() => {
                  this.form.setFieldsValue(pick(this.model, 'platformKey','platformValue'))
                });
              }
            })
          }
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            formData.platformKey = 'activation_code'
            postAction('/platformConfig/updatePlatformConfigByKey', formData).then((res)=>{
              if(res.code === 200){
                that.$message.info('填写成功！');
              }else{
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>
<style scoped>

</style>
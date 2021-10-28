<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:15%;height: 80%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" v-if="isReadOnly" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form" id="accountModal">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="名称">
          <a-input placeholder="请输入名称" v-decorator.trim="[ 'name', validatorRules.name]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="编号">
          <a-input placeholder="请输入编号" v-decorator.trim="[ 'serialNo', validatorRules.serialNo]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="期初金额">
          <a-input placeholder="请输入期初金额" v-decorator.trim="[ 'initialAmount' ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="当前余额">
          <a-input placeholder="请输入当前余额" :read-only="true" v-decorator.trim="[ 'currentAmount' ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
          <a-textarea :rows="2" placeholder="请输入备注" v-decorator="[ 'remark' ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import {addAccount,editAccount,checkAccount } from '@/api/api'
  import {autoJumpNextInput} from "@/utils/util"
  export default {
    name: "AccountModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        isReadOnly: false,
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
        validatorRules:{
          name:{
            rules: [
              { required: true, message: '请输入名称!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' },
              { validator: this.validateAccountName}
            ]},
          serialNo:{
            rules: [
              { required: true, message: '请输入编号!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
            ]
          }
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name', 'serialNo', 'initialAmount', 'currentAmount', 'remark'))
          autoJumpNextInput('accountModal')
        });
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
            let obj;
            if(!this.model.id){
              obj=addAccount(formData);
            }else{
              obj=editAccount(formData);
            }
            obj.then((res)=>{
              if(res.code === 200){
                that.$emit('ok');
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
      },
      validateAccountName(rule, value, callback){
        let params = {
          name: value,
          id: this.model.id?this.model.id:0
        };
        checkAccount(params).then((res)=>{
          if(res && res.code===200) {
            if(!res.data.status){
              callback();
            } else {
              callback("名称已经存在");
            }
          } else {
            callback(res.data);
          }
        });
      }
    }
  }
</script>
<style scoped>

</style>
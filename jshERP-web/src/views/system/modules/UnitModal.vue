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
    style="top:20%;height: 70%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" v-if="isReadOnly" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="基本单位">
          <a-input placeholder="请输入基本单位" v-decorator.trim="[ 'basicUnit', validatorRules.basicUnit]" />
        </a-form-item>
      </a-form>
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="副单位">
          <a-input placeholder="请输入副单位" v-decorator.trim="[ 'otherUnit', validatorRules.otherUnit]" />
        </a-form-item>
      </a-form>
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="比例">
          <a-input addon-before="基本单位 : 副单位=1 :" placeholder="请输入比例" v-decorator.trim="[ 'ratio', validatorRules.ratio]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import {addUnit,editUnit,checkUnit } from '@/api/api'
  export default {
    name: "UnitModal",
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
          basicUnit:{
            rules: [
              { required: true, message: '请输入基本单位!' },
              { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
            ]},
          otherUnit:{
            rules: [
              { required: true, message: '请输入副单位!' },
              { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
            ]},
          ratio:{
            rules: [
              { required: true, message: '请输入比例!' }
            ]}
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
          this.form.setFieldsValue(pick(this.model,'basicUnit','otherUnit','ratio'))
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
              obj=addUnit(formData);
            }else{
              obj=editUnit(formData);
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
      }
    }
  }
</script>
<style scoped>

</style>
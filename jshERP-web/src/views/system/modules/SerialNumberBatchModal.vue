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
    style="top:10%;height: 80%;overflow-y: hidden">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号前缀">
          <a-input placeholder="请输入序列号前缀" v-decorator.trim="[ 'serialNumberPrefix', validatorRules.serialNumberPrefix]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号数量">
          <a-input placeholder="请输入序列号数量" v-decorator.trim="[ 'batAddTotal', validatorRules.batAddTotal]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="商品条码">
          <j-select-serial-material v-decorator="[ 'materialCode', validatorRules.materialCode]" :multi="false" @change="onInputChange"/>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="商品名称">
          <a-input v-decorator="[ 'materialName' ]" :readOnly="true"/>
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
  import JSelectSerialMaterial from '@/components/jeecgbiz/JSelectSerialMaterial'
  import {batAddSerialNumber,getMaterialByBarCode} from '@/api/api'
  import { getMpListShort } from "@/utils/util"
  import Vue from 'vue'
  export default {
    name: "SerialNumberBatchModal",
    components: {
      JSelectSerialMaterial
    },
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
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
          serialNumberPrefix:{
            rules: [
              { required: true, message: '请输入序列号前缀!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
            ]
          },
          batAddTotal:{
            rules: [{ required: true, message: '请输入序列号数量!' }]
          },
          materialCode:{
            rules: [{ required: true, message: '请选择商品条码!' }]
          }
        }
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
          this.form.setFieldsValue(pick(this.model,'serialNumberPrefix', 'batAddTotal', 'materialCode', 'materialName', 'remark'))
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
              obj=batAddSerialNumber(formData);
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
      onInputChange(value) {
        let param = {
          barCode: value,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        }
        getMaterialByBarCode(param).then((res) => {
          if (res && res.code === 200) {
            this.form.setFieldsValue({'materialName': res.data.name})
          }
        })
      }
    }
  }
</script>
<style scoped>

</style>
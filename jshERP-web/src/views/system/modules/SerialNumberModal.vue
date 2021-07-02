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
    style="top:15%;height: 70%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" v-if="isReadOnly" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号">
          <a-input placeholder="请输入序列号" v-decorator.trim="[ 'serialNumber', validatorRules.serialNumber]" />
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
  import {addSerialNumber,editSerialNumber,checkSerialNumber,getMaterialByBarCode} from '@/api/api'
  import { getMpListShort } from "@/utils/util"
  import Vue from 'vue'
  export default {
    name: "SerialNumberModal",
    components: {
      JSelectSerialMaterial
    },
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
          serialNumber:{
            rules: [
              { required: true, message: '请输入序列号!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' },
              { validator: this.validateSerialNumberName}
            ]
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
          this.form.setFieldsValue(pick(this.model,'serialNumber', 'materialCode', 'materialName', 'remark'))
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
              obj=addSerialNumber(formData);
            }else{
              obj=editSerialNumber(formData);
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
      validateSerialNumberName(rule, value, callback){
        let params = {
          name: value,
          id: this.model.id?this.model.id:0
        };
        checkSerialNumber(params).then((res)=>{
          if(res && res.code===200) {
            if(!res.data.status){
              callback();
            } else {
              callback("序列号已经存在");
            }
          } else {
            callback(res.data);
          }
        });
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
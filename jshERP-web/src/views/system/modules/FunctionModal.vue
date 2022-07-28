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
    style="top:10%;height: 90%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" v-if="isReadOnly" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form" id="functionModal">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="编号">
          <a-input placeholder="请输入编号" v-decorator.trim="[ 'number', validatorRules.number]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="名称">
          <a-input placeholder="请输入名称" v-decorator.trim="[ 'name', validatorRules.name]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级编号">
          <a-input placeholder="请输入上级编号" v-decorator.trim="[ 'parentNumber', validatorRules.parentNumber ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="链接">
          <a-input placeholder="请输入链接" v-decorator.trim="[ 'url', validatorRules.url ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="组件">
          <a-input placeholder="请输入组件" v-decorator.trim="[ 'component', validatorRules.component ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="排序">
          <a-input placeholder="请输入排序" v-decorator.trim="[ 'sort', validatorRules.sort ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="功能按钮">
          <j-select-multiple placeholder="请选择功能按钮" v-model="jselectMultiple.value" :options="jselectMultiple.options"/>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="图标">
          <a-input placeholder="请输入图标" v-decorator.trim="[ 'icon', validatorRules.icon ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否启用">
          <a-switch checked-children="启用" un-checked-children="禁用" v-model="enabledSwitch" @change="onChange"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import {addFunction,editFunction,checkFunction, checkNumber } from '@/api/api'
  import {autoJumpNextInput} from "@/utils/util"
  import JSelectMultiple from '@/components/jeecg/JSelectMultiple'
  export default {
    name: "FunctionModal",
    components: {
      JSelectMultiple
    },
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        enabledSwitch: true, //是否启用
        isReadOnly: false,
        jselectMultiple: {
          options: [
            { text: '编辑', value: '1' },
            { text: '审核', value: '2' },
            { text: '反审核', value: '7' },
            { text: '导入导出', value: '3' },
            { text: '启用禁用', value: '4' },
            { text: '打印', value: '5' },
            { text: '作废', value: '6' }
          ],
          value: ''
        },
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
          number:{
            rules: [
              { required: true, message: '请输入编号!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' },
              { validator: this.validateNumber}
            ]
          },
          name:{
            rules: [
              { required: true, message: '请输入名称!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' },
              { validator: this.validateName}
            ]
          },
          parentNumber:{
            rules: [
              { required: true, message: '请输入上级编号!' }
            ]
          },
          url:{
            rules: [
              { required: true, message: '请输入链接!' }
            ]
          },
          component:{
            rules: [
              { required: true, message: '请输入组件!' }
            ]
          },
          sort:{
            rules: [
              { required: true, message: '请输入排序!' }
            ]
          },
          icon:{
            rules: [
              { required: true, message: '请输入图标!' }
            ]
          },
        },
      }
    },
    created () {
    },
    methods: {
      onChange(checked) {
        this.model.enabled = checked
      },
      add () {
        this.edit({});
        this.model.enabled = true
        this.enabledSwitch = true
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        if(record.enabled!=null){
          this.enabledSwitch = record.enabled?true:false;
        }
        if(this.model.id){
          this.jselectMultiple.value = record.pushBtn
        } else {
          this.jselectMultiple.value = ''
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'number', 'name', 'parentNumber', 'parentName', 'url', 'component', 'sort', 'pushBtn', 'icon', 'enabled'))
          autoJumpNextInput('functionModal')
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
            formData.pushBtn = this.jselectMultiple.value
            let obj;
            if(!this.model.id){
              obj=addFunction(formData);
            }else{
              obj=editFunction(formData);
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
      validateNumber(rule, value, callback){
        let params = {
          number: value,
          id: this.model.id?this.model.id:0
        };
        checkNumber(params).then((res)=>{
          if(res && res.code===200) {
            if(!res.data.status){
              callback();
            } else {
              callback("编号已经存在！");
            }
          } else {
            callback(res.data);
          }
        });
      },
      validateName(rule, value, callback){
        let params = {
          name: value,
          id: this.model.id?this.model.id:0
        };
        checkFunction(params).then((res)=>{
          if(res && res.code===200) {
            if(!res.data.status){
              callback();
            } else {
              callback("名称已经存在！");
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
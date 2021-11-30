<template>
  <a-modal
    :title="title"
    :width="700"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:25%; height:50%; overflow-y:hidden">
    <template slot="footer">
      <a-button key="back" v-if="isReadOnly" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form" id="unitModal">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="基本单位">
          <a-input placeholder="请输入基本单位(小单位)" v-decorator.trim="[ 'basicUnit', validatorRules.basicUnit]" />
        </a-form-item>
      </a-form>
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="副单位">
          <a-input placeholder="请输入副单位(大单位)" style="width:48%" v-decorator.trim="[ 'otherUnit' ]" />
          =
          <a-input suffix="基本单位" placeholder="请输入比例" style="width:48%" v-decorator.trim="[ 'ratio' ]" />
        </a-form-item>
      </a-form>
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="副单位2">
          <a-input placeholder="请输入副单位2(大单位)" style="width:48%" v-decorator.trim="[ 'otherUnitTwo' ]" />
          =
          <a-input suffix="基本单位" placeholder="请输入比例2" style="width:48%" v-decorator.trim="[ 'ratioTwo' ]" />
        </a-form-item>
      </a-form>
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="副单位3">
          <a-input placeholder="请输入副单位3(大单位)" style="width:48%" v-decorator.trim="[ 'otherUnitThree' ]" />
          =
          <a-input suffix="基本单位" placeholder="请输入比例3" style="width:48%" v-decorator.trim="[ 'ratioThree' ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import {addUnit,editUnit,checkUnit } from '@/api/api'
  import {autoJumpNextInput} from "@/utils/util"
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
              { required: true, message: '请输入比例!' },
              { validator: this.validateRatio}
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
          this.form.setFieldsValue(pick(this.model,'basicUnit','otherUnit','ratio','otherUnitTwo','ratioTwo','otherUnitThree','ratioThree'))
          autoJumpNextInput('unitModal')
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
            if(!formData.otherUnit) {
              that.$message.warning('抱歉，副单位不能为空！');
              that.confirmLoading = false;
              return;
            }
            if(!formData.ratio) {
              that.$message.warning('抱歉，此时比例不能为空！');
              that.confirmLoading = false;
              return;
            }
            if(formData.otherUnitTwo && !formData.ratioTwo) {
              that.$message.warning('抱歉，此时比例2不能为空！');
              that.confirmLoading = false;
              return;
            }
            if(formData.otherUnitThree && !formData.ratioThree) {
              that.$message.warning('抱歉，此时比例3不能为空！');
              that.confirmLoading = false;
              return;
            }
            if(formData.basicUnit === formData.otherUnit) {
              that.$message.warning('抱歉，基本单位与副单位不能相同！');
              that.confirmLoading = false;
              return;
            }
            if(formData.basicUnit === formData.otherUnitTwo) {
              that.$message.warning('抱歉，基本单位与副单位2不能相同！');
              that.confirmLoading = false;
              return;
            }
            if(formData.basicUnit === formData.otherUnitThree) {
              that.$message.warning('抱歉，基本单位与副单位3不能相同！');
              that.confirmLoading = false;
              return;
            }
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
      },
      validateRatio(rule, value, callback) {
        if (value > 1) {
          callback();
        } else {
          callback("比例必须大于1");
        }
      }
    }
  }
</script>
<style scoped>

</style>
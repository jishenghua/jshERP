<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="600"
      :visible="visible"
      :confirmLoading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="取消"
      okText="保存"
      style="top:15%;height: 60%;">
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="字典名称">
            <a-input placeholder="请输入字典名称" v-decorator.trim="[ 'dictName', validatorRules.dictName]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="字典类型">
            <a-input placeholder="请输入字典类型" v-decorator.trim="[ 'dictType', validatorRules.dictType]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="状态">
            <a-select style="width:100%" placeholder="请选择状态" v-decorator.trim="[ 'status' ]">
              <a-select-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :value="dict.value">
                {{ dict.label }}
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
            <a-textarea :rows="2" placeholder="请输入备注" v-decorator.trim="[ 'remark' ]" />
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import { addDictType, editDictType } from '@/api/api'
  import { mixinDevice } from '@/utils/mixin'
  export default {
    name: "DictModal",
    dicts: ['sys_normal_disable'],
    mixins: [mixinDevice],
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
          dictName:{
            rules: [
              { required: true, message: '请输入字典名称!' }
            ]
          },
          dictType:{
            rules: [
              { required: true, message: '请输入字典类型!' }
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
          this.form.setFieldsValue(pick(this.model,'dictName', 'dictType', 'status', 'remark'))
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
            let obj;
            if(!this.model.dictId){
              obj=addDictType(formData)
            }else{
              obj=editDictType(formData)
            }
            obj.then((res)=>{
              if(res.code === 200){
                that.$emit('ok');
                that.close();
              }else{
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
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
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
      style="top:10%;height: 80%;">
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="字典类型">
            <a-input placeholder="请输入字典类型" v-decorator.trim="[ 'dictType' ]" :readOnly="true" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="字典标签">
            <a-input placeholder="请输入字典标签" v-decorator.trim="[ 'dictLabel', validatorRules.dictLabel]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="字典键值">
            <a-input placeholder="请输入字典键值" v-decorator.trim="[ 'dictValue', validatorRules.dictValue]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="字典排序">
            <a-input-number style="width: 100%" placeholder="请输入字典排序" v-decorator.trim="[ 'dictSort', validatorRules.dictSort ]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="样式属性">
            <a-input placeholder="请输入样式属性" v-decorator.trim="[ 'cssClass' ]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="回显样式">
            <a-select placeholder="请选择回显样式" showSearch allow-clear optionFilterProp="children" v-decorator.trim="[ 'listClass' ]">
              <a-select-option v-for="(item,index) in listClassOptions" :key="index" :value="item.value">
                {{ item.label + '(' + item.value + ')' }}
              </a-select-option>
            </a-select>
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
  import { addDictData, editDictData } from '@/api/api'
  import { mixinDevice } from '@/utils/mixin'
  export default {
    name: "DictDataModal",
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
          dictLabel:{
            rules: [
              { required: true, message: '请输入字典标签!' }
            ]
          },
          dictValue:{
            rules: [
              { required: true, message: '请输入字典键值!' }
            ]
          },
          dictSort:{
            rules: [
              { required: true, message: '请输入字典排序!' }
            ]
          }
        },
        // 数据标签回显样式
        listClassOptions: [
          {
            value: "default",
            label: "默认"
          },
          {
            value: "blue",
            label: "主要"
          },
          {
            value: "green",
            label: "成功"
          },
          {
            value: "grey",
            label: "信息"
          },
          {
            value: "orange",
            label: "警告"
          },
          {
            value: "red",
            label: "危险"
          }
        ],
      }
    },
    created () {
    },
    methods: {
      add (dictType) {
        this.edit({});
        this.model.dictType = dictType
        this.model.dictSort = 0
        this.model.listClass = 'default'
        this.model.status = '0'
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'dictType', 'dictSort', 'listClass', 'status'))
        })
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'dictType', 'dictLabel', 'dictValue', 'cssClass', 'dictSort', 'listClass', 'status', 'remark'))
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
            if(!this.model.dictCode){
              obj=addDictData(formData)
            }else{
              obj=editDictData(formData)
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
<template>
  <a-modal
    :title="title"
    :width="500"
    :visible="visible"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:35%;height: 30%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" v-if="isReadOnly" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="请输入数量">
          <a-input placeholder="请输入数量" v-decorator.trim="[ 'number', validatorRules.number]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  export default {
    name: 'BatchSetStockModal',
    data () {
      return {
        title:"批量设置",
        visible: false,
        isReadOnly: false,
        batchType: '',
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
          number:{
            rules: [
              { required: true, message: '请输入数量!' }
            ]}
        }
      }
    },
    created () {
    },
    methods: {
      add (type) {
        this.batchType = type
        if(type === 'initStock') {
          this.title = '期初库存-批量设置'
        } else if(type === 'lowSafeStock') {
          this.title = '最低安全库存-批量设置'
        } else if(type === 'highSafeStock') {
          this.title = '最高安全库存-批量设置'
        }
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        let number = this.form.getFieldValue('number')
        this.$emit('ok', number, this.batchType);
        this.visible = false
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>

<style scoped>

</style>
<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="500"
      :visible="visible"
      :confirm-loading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'89px','left':'151px'}"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭"
      wrapClassName="ant-modal-cust-warp"
      style="top:30%;height: 30%;overflow-y: hidden">
      <template slot="footer">
        <a-button key="back" v-if="isReadOnly" @click="handleCancel">
          关闭
        </a-button>
      </template>
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="请输入价格">
            <a-input placeholder="请输入价格" v-decorator.trim="[ 'price', validatorRules.price]" />
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>

<script>
  export default {
    name: 'BatchSetPriceModal',
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
          price:{
            rules: [
              { required: true, message: '请输入价格!' }
            ]}
        }
      }
    },
    created () {
    },
    methods: {
      add (type) {
        this.batchType = type
        if(type === 'purchase') {
          this.title = '采购价-批量设置'
        } else if(type === 'commodity') {
          this.title = '零售价-批量设置'
        } else if(type === 'wholesale') {
          this.title = '销售价-批量设置'
        } else if(type === 'low') {
          this.title = '最低售价-批量设置'
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
        let price = this.form.getFieldValue('price')
        this.$emit('ok', price, this.batchType);
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
<template>
  <a-modal
    :title="title"
    :width="650"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:20%;height: 60%;overflow-y: hidden">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户1">
              <a-select style="width:185px;" placeholder="请选择结算账户" v-decorator="[ 'oneAccountId' ]" :dropdownMatchSelectWidth="false" allowClear>
                <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算金额">
              <a-input-number placeholder="请输入金额" v-decorator.trim="[ 'oneAccountPrice' ]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户2">
              <a-select style="width:185px;" placeholder="请选择结算账户" v-decorator="[ 'twoAccountId' ]" :dropdownMatchSelectWidth="false" allowClear>
                <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算金额">
              <a-input-number placeholder="请输入金额" v-decorator.trim="[ 'twoAccountPrice' ]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户3">
              <a-select style="width:185px;" placeholder="请选择结算账户" v-decorator="[ 'threeAccountId' ]" :dropdownMatchSelectWidth="false" allowClear>
                <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算金额">
              <a-input-number placeholder="请输入金额" v-decorator.trim="[ 'threeAccountPrice' ]" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import {getAccount} from '@/api/api'
  export default {
    name: 'ManyAccountModal',
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        accountList: [],
        accountIdList: [],
        accountMoneyList: [],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        form: this.$form.createForm(this)
      }
    },
    created () {
      this.initAccount()
    },
    methods: {
      edit (idStr, moneyStr) {
        this.form.resetFields();
        this.model = Object.assign({}, {});
        if(idStr && idStr.indexOf(',')>-1) {
          let idList = idStr.split(",")
          if(idList[0]) {this.model.oneAccountId = idList[0]-0}
          if(idList[1]) {this.model.twoAccountId = idList[1]-0}
          if(idList[2]) {this.model.threeAccountId = idList[2]-0}
          let moneyList = moneyStr.split(",")
          if(moneyList[0]) {this.model.oneAccountPrice = Math.abs(moneyList[0])}
          if(moneyList[1]) {this.model.twoAccountPrice = Math.abs(moneyList[1])}
          if(moneyList[2]) {this.model.threeAccountPrice = Math.abs(moneyList[2])}
        }
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'oneAccountId','oneAccountPrice',
            'twoAccountId','twoAccountPrice','threeAccountId','threeAccountPrice'))
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
            let allPrice = 0
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            console.log(formData)
            if(formData.oneAccountId!==undefined) {
              that.accountIdList.push(formData.oneAccountId)
            }
            if(formData.twoAccountId!==undefined) {
              that.accountIdList.push(formData.twoAccountId)
            }
            if(formData.threeAccountId!==undefined) {
              that.accountIdList.push(formData.threeAccountId)
            }
            if(formData.oneAccountPrice!==undefined) {
              that.accountMoneyList.push(formData.oneAccountPrice)
              allPrice = allPrice + formData.oneAccountPrice
            }
            if(formData.twoAccountPrice!==undefined) {
              that.accountMoneyList.push(formData.twoAccountPrice)
              allPrice = allPrice + formData.twoAccountPrice
            }
            if(formData.threeAccountPrice!==undefined) {
              that.accountMoneyList.push(formData.threeAccountPrice)
              allPrice = allPrice + formData.threeAccountPrice
            }
            that.$emit('ok', that.accountIdList, that.accountMoneyList, allPrice);
            that.close();
          }
        })
      },
      handleCancel () {
        this.close()
      },
      initAccount(){
        let that = this;
        getAccount({}).then((res)=>{
          if(res && res.code === 200) {
            that.accountList = res.data.accountList
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
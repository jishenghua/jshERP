<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :keyboard="false"
    :forceRender="true"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 100%;overflow-y: hidden">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款会员">
              <a-select placeholder="选择付款会员" v-decorator="[ 'organId', validatorRules.organId ]"
                :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                <a-select-option v-for="(item,index) in retailList" :key="index" :value="item.id">
                  {{ item.supplier }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
              <a-select placeholder="选择财务人员" v-decorator="[ 'handsPersonId', validatorRules.handsPersonId ]"
                :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                <a-select-option v-for="(item,index) in personList" :key="index" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
              <j-date v-decorator="['billTime', validatorRules.billTime]" :show-time="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
              <a-input placeholder="请输入单据编号" v-decorator.trim="[ 'billNo' ]" :readOnly="true"/>
            </a-form-item>
          </a-col>
        </a-row>
        <j-editable-table
          :ref="refKeys[0]"
          :loading="accountTable.loading"
          :columns="accountTable.columns"
          :dataSource="accountTable.dataSource"
          :maxHeight="300"
          :rowNumber="false"
          :rowSelection="true"
          :actionButton="true"
          @valueChange="onValueChange" />
        <a-row class="form-row" :gutter="24">
          <a-col :lg="24" :md="24" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="">
              <a-textarea :rows="2" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合计金额">
              <a-input placeholder="请输入合计收款" v-decorator.trim="[ 'totalPrice' ]" :readOnly="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款金额">
              <a-input placeholder="请输入收款金额" v-decorator.trim="[ 'changeAmount' ]" :readOnly="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="附件">
              <j-upload v-model="fileList" bizPath="financial"></j-upload>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </j-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { FinancialModalMixin } from '../mixins/FinancialModalMixin'
  import JUpload from '@/components/jeecg/JUpload'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "AdvanceInModal",
    mixins: [JEditableTableMixin, FinancialModalMixin],
    components: {
      JUpload,
      JDate
    },
    data () {
      return {
        title:"操作",
        width: '1200px',
        moreStatus: false,
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        visible: false,
        model: {},
        fileList:[],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        refKeys: ['accountDataTable', ],
        activeKey: 'accountDataTable',
        accountTable: {
          loading: false,
          dataSource: [],
          columns: [
            { title: '账户名称',key: 'accountId',width: '20%', type: FormTypes.select, placeholder: '请选择${title}', options: [],
              allowSearch:true, validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '金额',key: 'eachAmount', width: '10%', type: FormTypes.inputNumber, statistics: true, placeholder: '请选择${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '备注',key: 'remark', width: '30%', type: FormTypes.input, placeholder: '请选择${title}'}
          ]
        },
        confirmLoading: false,
        validatorRules:{
          organId:{
            rules: [
              { required: true, message: '请选择付款会员!' }
            ]
          },
          handsPersonId:{
            rules: [
              { required: true, message: '请选择财务人员!' }
            ]
          },
          billTime:{
            rules: [
              { required: true, message: '请选择单据日期!' }
            ]
          }
        },
        url: {
          add: '/accountHead/addAccountHeadAndDetail',
          edit: '/accountHead/updateAccountHeadAndDetail',
          detailList: '/accountItem/getDetailList'
        }
      }
    },
    created () {
    },
    methods: {
      //调用完edit()方法之后会自动调用此方法
      editAfter() {
        if (this.action === 'add') {
          this.addInit("SYF")
          this.fileList = []
        } else {
          this.model.billTime = this.model.billTimeStr
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'organId', 'handsPersonId', 'billTime', 'billNo', 'remark',
                  'totalPrice', 'changeAmount'))
          });
          this.fileList = this.model.fileName
          // 加载子表数据
          let params = {
            headerId: this.model.id
          }
          let url = this.readOnly ? this.url.detailList : this.url.detailList;
          this.requestSubTableData(url, params, this.accountTable);
        }
        this.initRetail()
        this.initPerson()
        this.initDetailAccount()
      },
      //提交单据时整理成formData
      classifyIntoFormData(allValues) {
        let totalPrice = 0
        let billMain = Object.assign(this.model, allValues.formValue)
        let detailArr = allValues.tablesValue[0].values
        billMain.type = '收预付款'
        for(let item of detailArr){
          totalPrice += item.eachAmount-0
        }
        billMain.totalPrice = totalPrice
        if(this.fileList && this.fileList.length > 0) {
          billMain.fileName = this.fileList
        }
        if(this.model.id){
          billMain.id = this.model.id
        }
        return {
          info: JSON.stringify(billMain),
          rows: JSON.stringify(detailArr),
        }
      }
    }
  }
</script>
<style scoped>

</style>
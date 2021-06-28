<template>
  <a-card :bordered="false">
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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-select placeholder="选择客户" v-decorator="[ 'organId', validatorRules.organId ]" :dropdownMatchSelectWidth="false">
                  <a-select-option v-for="(item,index) in cusList" :key="index" :value="item.id">
                    {{ item.supplier }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经手人">
                <a-select placeholder="选择经手人" v-decorator="[ 'handsPersonId', validatorRules.handsPersonId ]" :dropdownMatchSelectWidth="false">
                  <a-select-option v-for="(item,index) in personList" :key="index" :value="item.id">
                    {{ item.name }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                <j-date v-decorator="['billTime']" :show-time="true"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                <a-input placeholder="请输入单据编号" v-decorator.trim="[ 'billNo' ]" :readOnly="true"/>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <!-- 操作按钮 -->
              <div class="action-button">
                <a-button type="primary" icon="plus" @click="handleClickAdd">批量新增</a-button>
                <span class="gap"></span>
                <a-button type="primary" icon="minus" @click="handleClear">清空</a-button>
              </div>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="24">
              <j-editable-table
                :ref="refKeys[0]"
                :loading="accountTable.loading"
                :columns="accountTable.columns"
                :dataSource="accountTable.dataSource"
                :maxHeight="300"
                :rowNumber="false"
                :rowSelection="false"
                :actionButton="false" />
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="">
                <a-textarea :rows="2" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠金额">
                <a-input placeholder="请输入优惠金额" v-decorator.trim="[ 'changeAmount' ]" />
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </j-modal>
    <debt-bill-list ref="debtBillList" @ok="debtBillListOk"></debt-bill-list>
  </a-card>
</template>
<script>
  import pick from 'lodash.pick'
  import DebtBillList from '../dialog/DebtBillList'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { FinancialModalMixin } from '../mixins/FinancialModalMixin'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "MoneyInModal",
    mixins: [JEditableTableMixin, FinancialModalMixin],
    components: {
      DebtBillList,
      JDate
    },
    data () {
      return {
        title:"操作",
        width: '1200px',
        moreStatus: false,
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 0,
        visible: false,
        model: {},
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
            { title: '销售单据编号',key: 'billNumber',width: '20%', type: FormTypes.input, readonly: true },
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
              { required: true, message: '请选择客户!' }
            ]
          },
          handsPersonId:{
            rules: [
              { required: true, message: '请选择经手人!' }
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
      this.initDetailAccount()
    },
    methods: {
      //调用完edit()方法之后会自动调用此方法
      editAfter() {
        if (this.action === 'add') {
          this.addInit("SK")
        } else {
          this.model.billTime = this.model.billTimeStr
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'organId', 'handsPersonId', 'billTime', 'billNo', 'remark', 'changeAmount'))
          });
          // 加载子表数据
          let params = {
            headerId: this.model.id
          }
          let url = this.readOnly ? this.url.detailList : this.url.detailList;
          this.requestSubTableData(url, params, this.accountTable);
        }
      },
      //提交单据时整理成formData
      classifyIntoFormData(allValues) {
        let totalPrice = 0
        let billMain = Object.assign(this.model, allValues.formValue)
        let detailArr = allValues.tablesValue[0].values
        billMain.type = '收款'
        for(let item of detailArr){
          totalPrice += item.eachAmount-0
        }
        billMain.totalPrice = totalPrice
        if(this.model.id){
          billMain.id = this.model.id
        }
        return {
          info: JSON.stringify(billMain),
          rows: JSON.stringify(detailArr),
        }
      },
      handleClickAdd() {
        this.$refs.debtBillList.show('出库', '销售', '客户', "0")
        this.$refs.debtBillList.title = "选择销售出库"
      },
      handleClear() {
        this.accountTable.dataSource = []
      },
      debtBillListOk(selectBillRows) {
        if(selectBillRows && selectBillRows.length>0) {
          this.requestSubTableDataEx(selectBillRows, this.accountTable);
        }
      },
      /** 查询某个tab的数据,给明细里面的价税合计赋值 */
      requestSubTableDataEx(selectBillRows, tab, success) {
        tab.loading = true
        let listEx = []
        for(let i=0; i<selectBillRows.length; i++){
          let info = selectBillRows[i]
          info.billNumber = info.number
          info.eachAmount = info.totalPrice
          listEx.push(info)
        }
        tab.dataSource = listEx
        typeof success === 'function' ? success(res) : ''
        tab.loading = false
      }
    }
  }
</script>
<style scoped>
  .action-button {
    margin-bottom: 8px;
  }
  .gap {
    padding-left: 8px;
  }
</style>
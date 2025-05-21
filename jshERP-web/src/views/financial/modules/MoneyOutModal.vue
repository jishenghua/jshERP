<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :keyboard="false"
    :forceRender="true"
    fullscreen
    switchFullscreen
    @cancel="handleCancel"
    :id="prefixNo"
    style="top:20px;height: 95%;">
    <template slot="footer">
      <a-button @click="handleCancel">取消</a-button>
      <a-button v-if="checkFlag && isCanCheck" :loading="confirmLoading" @click="handleOkAndCheck">保存并审核</a-button>
      <a-button type="primary" :loading="confirmLoading" @click="handleOk">保存（Ctrl+S）</a-button>
      <!--发起多级审核-->
      <a-button v-if="!checkFlag" @click="handleWorkflow()" type="primary">提交流程</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
              <a-select placeholder="请选择供应商" v-decorator="[ 'organId', validatorRules.organId ]"
                :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children" @change="onChangeOrgan">
                <div slot="dropdownRender" slot-scope="menu">
                  <v-nodes :vnodes="menu" />
                  <a-divider style="margin: 4px 0;" />
                  <div v-if="quickBtn.vendor" class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="addSupplier"><a-icon type="plus" /> 新增供应商</div>
                  <div class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="initSupplier"><a-icon type="reload" /> 刷新</div>
                </div>
                <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                  {{ item.supplier }}
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
              <a-input placeholder="请输入单据编号" v-decorator.trim="[ 'billNo' ]" />
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
              <a-select placeholder="请选择财务人员" v-decorator="[ 'handsPersonId' ]"
                        :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                <div slot="dropdownRender" slot-scope="menu">
                  <v-nodes :vnodes="menu" />
                  <a-divider style="margin: 4px 0;" />
                  <div v-if="quickBtn.person" class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="addPerson"><a-icon type="plus" /> 新增经手人</div>
                  <div class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="initPerson"><a-icon type="reload" /> 刷新</div>
                </div>
                <a-select-option v-for="(item,index) in personList" :key="index" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <j-editable-table
          :ref="refKeys[0]"
          :loading="accountTable.loading"
          :columns="accountTable.columns"
          :dataSource="accountTable.dataSource"
          :minWidth="minWidth"
          :maxHeight="300"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="false"
          :actionDeleteButton="true"
          @deleted="onDeleted"
          @valueChange="onValueChange">
          <template #buttonBefore>
            <a-row :gutter="24" style="float:left;padding-bottom:8px;">
              <a-col :md="12" :sm="24">
                <a-button type="primary" icon="plus" @click="handleClickAdd">选择单据</a-button>
              </a-col>
              <a-col :md="12" :sm="24" style="padding-left:0">
                <a-button type="primary" icon="plus" @click="selectBeginNeed('供应商')">选择期初</a-button>
              </a-col>
            </a-row>
          </template>
          <template #buttonAfter>
            <a-row :gutter="24" style="float:left;padding-bottom:8px;">
              <a-col :md="12" :sm="24">
                <a-button icon="link" @click="handleWaitNeed('供应商')">待付款</a-button>
              </a-col>
            </a-row>
          </template>
        </j-editable-table>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="24" :md="24" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="">
              <a-textarea :rows="2" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款账户">
              <a-select placeholder="请选择付款账户" v-decorator="[ 'accountId', validatorRules.accountId ]"
                :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                <div slot="dropdownRender" slot-scope="menu">
                  <v-nodes :vnodes="menu" />
                  <a-divider style="margin: 4px 0;" />
                  <div v-if="quickBtn.account" class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="addAccount"><a-icon type="plus" /> 新增</div>
                  <div class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="initAccount"><a-icon type="reload" /> 刷新</div>
                </div>
                <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合计付款">
              <a-input placeholder="请输入合计付款" v-decorator.trim="[ 'totalPrice' ]" :readOnly="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠金额">
              <a-input placeholder="请输入优惠金额" v-decorator.trim="[ 'discountMoney', validatorRules.discountMoney ]" @change="onChangeDiscountMoney" />
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实际付款">
              <a-input placeholder="请输入实际付款" v-decorator.trim="[ 'changeAmount' ]" :readOnly="true"/>
            </a-form-item>
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
    <debt-bill-list ref="debtBillList" @ok="debtBillListOk"></debt-bill-list>
    <vendor-modal ref="vendorModalForm" @ok="vendorModalFormOk"></vendor-modal>
    <account-modal ref="accountModalForm" @ok="accountModalFormOk"></account-modal>
    <person-modal ref="personModalForm" @ok="personModalFormOk"></person-modal>
    <wait-need-list ref="waitNeedList" @ok="waitNeedListOk"></wait-need-list>
    <workflow-iframe ref="modalWorkflow" @ok="workflowModalFormOk"></workflow-iframe>
  </j-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import DebtBillList from '../dialog/DebtBillList'
  import VendorModal from '../../system/modules/VendorModal'
  import AccountModal from '../../system/modules/AccountModal'
  import PersonModal from '../../system/modules/PersonModal'
  import WaitNeedList from '../dialog/WaitNeedList'
  import WorkflowIframe from '@/components/tools/WorkflowIframe'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { FinancialModalMixin } from '../mixins/FinancialModalMixin'
  import JUpload from '@/components/jeecg/JUpload'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "MoneyInModal",
    mixins: [JEditableTableMixin, FinancialModalMixin],
    components: {
      DebtBillList,
      VendorModal,
      AccountModal,
      PersonModal,
      WaitNeedList,
      WorkflowIframe,
      JUpload,
      JDate,
      VNodes: {
        functional: true,
        render: (h, ctx) => ctx.props.vnodes,
      }
    },
    data () {
      return {
        title:"操作",
        width: '1600px',
        moreStatus: false,
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 0,
        visible: false,
        prefixNo: 'FK',
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
            { title: '采购单据编号',key: 'billNumber',width: '20%', type: FormTypes.input, readonly: true },
            { title: '应付欠款',key: 'needDebt', width: '10%', type: FormTypes.inputNumber, statistics: true, readonly: true },
            { title: '已付欠款', key: 'finishDebt', width: '10%', type: FormTypes.inputNumber, statistics: true, readonly: true },
            { title: '本次付款',key: 'eachAmount', width: '10%', type: FormTypes.inputNumber, statistics: true, placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '备注',key: 'remark', width: '20%', type: FormTypes.input, placeholder: '请输入${title}'}
          ]
        },
        confirmLoading: false,
        validatorRules:{
          organId:{
            rules: [{ required: true, message: '请选择供应商!' }]
          },
          billTime:{
            rules: [{ required: true, message: '请选择单据日期!' }]
          },
          accountId:{
            rules: [{ required: true, message: '请选择付款账户!' }]
          },
          discountMoney:{
            rules: [{ required: true, message: '请输入优惠金额!' }]
          },
          changeAmount:{
            rules: [{ required: true, message: '请输入收款金额!' }]
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
        this.billStatus = '0'
        if (this.action === 'add') {
          this.addInit(this.prefixNo)
          this.fileList = []
          if(this.actionWithOrgan) {
            //自动弹出待收款客户列表
            let that = this
            setTimeout(function() {
              that.$refs.waitNeedList.show('供应商')
            },1000)
          }
        } else {
          this.model.billTime = this.model.billTimeStr
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'organId', 'handsPersonId', 'billTime', 'billNo', 'remark',
                  'accountId', 'totalPrice', 'discountMoney', 'changeAmount'))
          });
          this.fileList = this.model.fileName
          // 加载子表数据
          let params = {
            headerId: this.model.id
          }
          let url = this.readOnly ? this.url.detailList : this.url.detailList;
          this.requestSubTableData(url, params, this.accountTable);
        }
        this.initSystemConfig()
        this.initSupplier()
        this.initPerson()
        this.initAccount()
        this.initQuickBtn()
      },
      //提交单据时整理成formData
      classifyIntoFormData(allValues) {
        let totalPrice = 0
        let billMain = Object.assign(this.model, allValues.formValue)
        let detailArr = allValues.tablesValue[0].values
        billMain.type = '付款'
        for(let item of detailArr){
          totalPrice += item.eachAmount-0
        }
        billMain.totalPrice = 0-totalPrice
        billMain.changeAmount = 0-billMain.changeAmount
        if(this.fileList && this.fileList.length > 0) {
          billMain.fileName = this.fileList
        }
        if(this.model.id){
          billMain.id = this.model.id
        }
        billMain.status = this.billStatus
        return {
          info: JSON.stringify(billMain),
          rows: JSON.stringify(detailArr),
        }
      },
      handleClickAdd() {
        let organId = this.form.getFieldValue('organId')
        if(organId){
          this.$refs.debtBillList.show(organId, '入库', '采购', '供应商', "")
          this.$refs.debtBillList.title = "请选择采购欠款单据"
        } else {
          this.$message.warning('请选择供应商！');
        }
      },
      handleClear() {
        this.accountTable.dataSource = []
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
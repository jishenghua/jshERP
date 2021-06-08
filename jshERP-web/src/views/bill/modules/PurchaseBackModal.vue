<template>
  <a-card :bordered="false" class="card-area">
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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-select placeholder="选择供应商" v-decorator="[ 'organId', validatorRules.organId ]" :dropdownMatchSelectWidth="false">
                  <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                    {{ item.supplier }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                <j-date v-decorator="['operTime']" :show-time="true"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                <a-input placeholder="请输入单据编号" v-decorator.trim="[ 'number' ]" :readOnly="true"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <j-editable-table
            :ref="refKeys[0]"
            :loading="materialTable.loading"
            :columns="materialTable.columns"
            :dataSource="materialTable.dataSource"
            :maxHeight="300"
            :rowNumber="false"
            :rowSelection="true"
            :actionButton="true"
            @valueChange="onValueChange"
            @added="onAdded"
            @deleted="onDeleted" />
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="">
                <a-textarea :rows="2" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                <a-input style="width:185px;" placeholder="请输入优惠率" v-decorator.trim="[ 'discount' ]" suffix="%" @keyup="onKeyUpDiscount"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="退款优惠">
                <a-input placeholder="请输入付款优惠" v-decorator.trim="[ 'discountMoney' ]" @keyup="onKeyUpDiscountMoney"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠后金额">
                <a-input placeholder="请输入优惠后金额" v-decorator.trim="[ 'discountLastMoney' ]" :readOnly="true"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                <a-input placeholder="请输入其它费用" v-decorator.trim="[ 'otherMoney' ]" @keyup="onKeyUpOtherMoney"/>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                <a-select style="width:185px;" placeholder="选择结算账户" v-decorator="[ 'accountId', validatorRules.accountId ]"
                          :dropdownMatchSelectWidth="false" allowClear @select="selectAccount">
                  <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                    {{ item.name }}
                  </a-select-option>
                </a-select>
                <a-tooltip title="多账户明细">
                  <a-button type="default" icon="folder" style="margin-left: 8px;" size="small" v-show="manyAccountBtnStatus" @click="handleManyAccount"/>
                </a-tooltip>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次退款">
                <a-input placeholder="请输入本次付款" v-decorator.trim="[ 'changeAmount' ]" @keyup="onKeyUpChangeAmount"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                <a-input placeholder="请输入本次欠款" v-decorator.trim="[ 'debt' ]" :readOnly="true"/>
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </j-modal>
    <many-account-modal ref="manyAccountModalForm" @ok="manyAccountModalFormOk"></many-account-modal>
  </a-card>
</template>
<script>
  import pick from 'lodash.pick'
  import ManyAccountModal from '../dialog/ManyAccountModal'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { BillModalMixin } from '../mixins/BillModalMixin'
  import { getMpListShort, changeListFmtMinus} from "@/utils/util"
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "PurchaseBackModal",
    mixins: [JEditableTableMixin, BillModalMixin],
    components: {
      ManyAccountModal,
      JDate
    },
    data () {
      return {
        title:"操作",
        width: '1450px',
        moreStatus: false,
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        visible: false,
        operTimeStr: '',
        prefixNo: 'CGTH',
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        refKeys: ['materialDataTable', ],
        activeKey: 'materialDataTable',
        materialTable: {
          loading: false,
          dataSource: [],
          columns: [
            { title: '仓库名称', key: 'depotId', width: '8%', type: FormTypes.select, placeholder: '请选择${title}', options: [] },
            { title: '条码', key: 'barCode', width: '10%', type: FormTypes.popupJsh },
            { title: '名称', key: 'name', width: '8%', type: FormTypes.input, readonly: true },
            { title: '规格', key: 'standard', width: '5%', type: FormTypes.input, readonly: true },
            { title: '型号', key: 'model', width: '5%', type: FormTypes.input, readonly: true },
            { title: '扩展信息', key: 'materialOther', width: '6%', type: FormTypes.input, readonly: true },
            { title: '库存', key: 'stock', width: '5%', type: FormTypes.input, readonly: true },
            { title: '单位', key: 'unit', width: '4%', type: FormTypes.input, readonly: true },
            { title: '数量', key: 'operNumber', width: '5%', type: FormTypes.inputNumber, statistics: true },
            { title: '单价', key: 'unitPrice', width: '5%', type: FormTypes.inputNumber},
            { title: '含税单价', key: 'taxUnitPrice', width: '6%', type: FormTypes.inputNumber},
            { title: '金额', key: 'allPrice', width: '5%', type: FormTypes.inputNumber, statistics: true },
            { title: '税率(%)', key: 'taxRate', width: '6%', type: FormTypes.inputNumber},
            { title: '税额', key: 'taxMoney', width: '5%', type: FormTypes.inputNumber, statistics: true },
            { title: '价税合计', key: 'taxLastMoney', width: '6%', type: FormTypes.inputNumber, statistics: true },
            { title: '备注', key: 'remark', width: '5%', type: FormTypes.input }
          ]
        },
        confirmLoading: false,
        validatorRules:{
          operTime:{
            rules: [
              { required: true, message: '请输入单据日期!' }
            ]
          },
          organId:{
            rules: [
              { required: true, message: '请选择供应商!' }
            ]
          },
          accountId:{
            rules: [
              { required: true, message: '请选择结算账户!' }
            ]
          }
        },
        url: {
          add: '/depotHead/addDepotHeadAndDetail',
          edit: '/depotHead/updateDepotHeadAndDetail',
          detailList: '/depotItem/getDetailList'
        }
      }
    },
    created () {
    },
    methods: {
      //调用完edit()方法之后会自动调用此方法
      editAfter() {
        if (this.action === 'add') {
          this.addInit(this.prefixNo)
        } else {
          this.model.operTime = this.model.operTimeStr
          this.model.debt = (this.model.discountLastMoney + this.model.otherMoney - this.model.changeAmount).toFixed(2)
          if(this.model.accountId == null) {
            this.model.accountId = 0
            this.manyAccountBtnStatus = true
            this.accountIdList = this.model.accountIdList
            this.accountMoneyList = this.model.accountMoneyList
          } else {
            this.manyAccountBtnStatus = false
          }
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'organId', 'operTime', 'number', 'remark',
              'discount','discountMoney','discountLastMoney','otherMoney','accountId','changeAmount','debt'))
          });
          // 加载子表数据
          let params = {
            headerId: this.model.id,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
          }
          let url = this.readOnly ? this.url.detailList : this.url.detailList;
          this.requestSubTableData(url, params, this.materialTable);
        }
      },
      //提交单据时整理成formData
      classifyIntoFormData(allValues) {
        let totalPrice = 0
        let billMain = Object.assign(this.model, allValues.formValue)
        let detailArr = allValues.tablesValue[0].values
        billMain.type = '出库'
        billMain.subType = '采购退货'
        billMain.defaultNumber = billMain.number
        for(let item of detailArr){
          totalPrice += item.allPrice-0
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
      manyAccountModalFormOk(idList, moneyList, allPrice) {
        this.accountIdList = idList
        this.accountMoneyList = changeListFmtMinus(moneyList)
        this.$nextTick(() => {
          this.form.setFieldsValue({'changeAmount':allPrice})
        });
      }
    }
  }
</script>
<style scoped>

</style>
<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :keyboard="false"
    :forceRender="true"
    v-bind:prefixNo="prefixNo"
    fullscreen
    switchHelp
    switchFullscreen
    @cancel="handleCancel"
    :id="prefixNo"
    style="top:20px;height: 95%;">
    <template slot="footer">
      <a-button @click="handleCancel">取消</a-button>
      <a-button v-if="billPrintFlag && isShowPrintBtn" @click="handlePrint('采购订单')">三联打印预览</a-button>
      <!--      <a-button  :loading="confirmLoading" @click="handleOk">保存（Ctrl+S）</a-button>-->
      <a-button type="primary" v-if="checkFlag && isCanCheck" :loading="confirmLoading" @click="handleOkAndCheck">保存并审核</a-button>
      <!--发起多级审核-->
      <a-button v-if="!checkFlag" @click="handleWorkflow()" type="primary">提交流程</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商" data-step="1" data-title="供应商"
              data-intro="供应商必须选择，如果发现需要选择的供应商尚未录入，可以在下拉框中点击新增供应商进行录入">
              <a-select placeholder="请选择供应商" v-decorator="[ 'organId', validatorRules.organId ]"
                :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                <div slot="dropdownRender" slot-scope="menu">
                  <v-nodes :vnodes="menu" />
                  <a-divider style="margin: 4px 0;" />
                  <div v-if="quickBtn.vendor" class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="addSupplier"><a-icon type="plus" /> 新增供应商</div>
                  <div class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="initSupplier(0)"><a-icon type="reload" /> 刷新列表</div>
                </div>
                <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                  {{ item.supplier + ' ' + item.telephone }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
              <j-date v-decorator="['operTime', validatorRules.operTime]" :show-time="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号" data-step="2" data-title="单据编号"
              data-intro="单据编号自动生成、自动累加、开头是单据类型的首字母缩写，累加的规则是每次打开页面会自动占用一个新的编号">
              <a-input placeholder="请输入单据编号" v-decorator.trim="[ 'number' ]" />
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联请购单" data-step="3" data-title="关联请购单"
                         data-intro="采购订单单据可以通过关联请购单来选择已录入的请购单，选择之后会自动加载请购单的内容，
              提交之后原来的请购单会对应的改变单据状态。另外本系统支持分批多次关联">
              <a-input-search placeholder="请选择关联请购单" v-decorator="[ 'linkApply' ]" @search="onSearchLinkApply" :readOnly="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item v-if="purchaseBySaleFlag" :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联订单" data-step="3" data-title="关联订单"
                         data-intro="采购订单单据可以通过关联订单来选择已录入的销售订单，选择之后会自动加载订单的内容，
              提交之后原来的销售订单会对应的改变单据状态。另外本系统支持分批多次关联">
              <a-input-search placeholder="请选择关联销售订单" v-decorator="[ 'linkNumber' ]" @search="onSearchLinkNumber" :readOnly="true"/>
            </a-form-item>
          </a-col>
        </a-row>
        <j-editable-table id="billModal"
          :ref="refKeys[0]"
          :loading="materialTable.loading"
          :columns="materialTable.columns"
          :dataSource="materialTable.dataSource"
          :minWidth="minWidth"
          :maxHeight="300"
          :rowNumber="false"
          :rowSelection="rowCanEdit"
          :actionButton="rowCanEdit"
          :dragSortAndNumber="rowCanEdit"
          @valueChange="onValueChange"
          @added="onAdded"
          @deleted="onDeleted">
          <template #buttonAfter>
            <a-row v-if="rowCanEdit" :gutter="24" style="float:left;padding-bottom: 5px;" data-step="4" data-title="扫码录入" data-intro="此功能支持扫码枪扫描商品条码进行录入">
              <a-col v-if="scanStatus" :md="6" :sm="24">
                <a-button @click="scanEnter">扫码录入</a-button>
              </a-col>
              <a-col v-if="!scanStatus" :md="16" :sm="24" style="padding: 0 8px 0 12px">
                <a-input placeholder="请扫描商品条码并回车" v-model="scanBarCode" @pressEnter="scanPressEnter" ref="scanBarCode"/>
              </a-col>
              <a-col v-if="!scanStatus" :md="6" :sm="24" style="padding: 0px 12px 0 0">
                <a-button @click="stopScan">收起扫码</a-button>
              </a-col>
            </a-row>
            <a-row :gutter="24" style="float:left;padding-bottom: 5px;">
              <a-col :md="24" :sm="24">
                <a-button style="margin-left: 8px" @click="handleHistoryBillList"><a-icon type="history" />历史单据</a-button>
              </a-col>
            </a-row>
            <a-row v-if="rowCanEdit" :gutter="24" style="float:left;padding-bottom: 5px;padding-left:20px;">
              <a-button icon="import" @click="onImport(prefixNo)">导入明细</a-button>
            </a-row>
          </template>
        </j-editable-table>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="24" :md="24" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="">
              <a-textarea :rows="1" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率" data-step="5" data-title="优惠率"
                         data-intro="针对单据明细中商品总金额进行优惠的比例">
              <a-input style="width:80%;" placeholder="请输入优惠率" v-decorator.trim="[ 'discount' ]" suffix="%" @change="onChangeDiscount"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款优惠" data-step="6" data-title="付款优惠"
                         data-intro="针对单据明细中商品总金额进行优惠的金额">
              <a-input placeholder="请输入付款优惠" v-decorator.trim="[ 'discountMoney' ]" @change="onChangeDiscountMoney"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠后金额" data-step="7" data-title="优惠后金额"
                         data-intro="针对单据明细中商品总金额进行优惠后的金额">
              <a-input placeholder="请输入优惠后金额" v-decorator.trim="[ 'discountLastMoney' ]" :readOnly="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户" data-step="8" data-title="结算账户"
                         data-intro="如果在下拉框中选择多账户，则可以通过多个结算账户进行结算">
              <a-select style="width:80%;" placeholder="请选择结算账户" v-decorator="[ 'accountId' ]"
                        :dropdownMatchSelectWidth="false" allowClear @select="selectAccount">
                <div slot="dropdownRender" slot-scope="menu">
                  <v-nodes :vnodes="menu" />
                  <a-divider style="margin: 4px 0;" />
                  <div v-if="quickBtn.account" class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="addAccount"><a-icon type="plus" /> 新增</div>
                  <div class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="initAccount(0)"><a-icon type="reload" /> 刷新</div>
                </div>
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
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="支付订金" data-step="9" data-title="支付订金"
                         data-intro="填写订金之后，在采购入库单会自动计算扣除订金">
              <a-input placeholder="请输入支付订金" v-decorator.trim="[ 'changeAmount', validatorRules.price ]" @change="onChangeChangeAmount"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="附件" data-step="10" data-title="附件" data-intro="可以上传与单据相关的图片、文档，支持多个文件">
              <j-upload v-model="fileList" bizPath="bill"></j-upload>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
    <many-account-modal ref="manyAccountModalForm" @ok="manyAccountModalFormOk"></many-account-modal>
    <import-item-modal ref="importItemModalForm" @ok="importItemModalFormOk"></import-item-modal>
    <vendor-modal ref="vendorModalForm" @ok="vendorModalFormOk"></vendor-modal>
    <account-modal ref="accountModalForm" @ok="accountModalFormOk"></account-modal>
    <link-bill-list ref="linkBillList" @ok="linkBillListOk"></link-bill-list>
    <history-bill-list ref="historyBillListModalForm"></history-bill-list>
    <workflow-iframe ref="modalWorkflow" @ok="workflowModalFormOk"></workflow-iframe>
    <bill-print-iframe ref="modalPrint"></bill-print-iframe>
  </j-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import ManyAccountModal from '../dialog/ManyAccountModal'
  import ImportItemModal from '../dialog/ImportItemModal'
  import LinkBillList from '../dialog/LinkBillList'
  import VendorModal from '../../system/modules/VendorModal'
  import AccountModal from '../../system/modules/AccountModal'
  import HistoryBillList from '../dialog/HistoryBillList'
  import WorkflowIframe from '@/components/tools/WorkflowIframe'
  import BillPrintIframe from '../dialog/BillPrintIframe'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { BillModalMixin } from '../mixins/BillModalMixin'
  import { findBillDetailByNumber, getCurrentSystemConfig } from '@/api/api'
  import { getMpListShort, changeListFmtMinus,handleIntroJs } from "@/utils/util"
  import JUpload from '@/components/jeecg/JUpload'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "PurchaseOrderModal",
    mixins: [JEditableTableMixin,BillModalMixin],
    components: {
      ManyAccountModal,
      ImportItemModal,
      LinkBillList,
      VendorModal,
      AccountModal,
      HistoryBillList,
      WorkflowIframe,
      BillPrintIframe,
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
        addDefaultRowNum: 1,
        visible: false,
        supList: [],
        depotList: [],
        operTimeStr: '',
        prefixNo: 'CGDD',
        fileList:[],
        rowCanEdit: true,
        //以销定购的场景开关
        purchaseBySaleFlag: false,
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
        tableKeys:['materialDataTable', ],
        activeKey: 'materialDataTable',
        materialTable: {
          loading: false,
          dataSource: [],
          columns: [
            { title: '', key: 'hiddenKey', width: '1%', type: FormTypes.hidden },
            { title: '条码', key: 'barCode', width: '12%', type: FormTypes.popupJsh, kind: 'material', multi: true,
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '名称', key: 'name', width: '10%', type: FormTypes.normal },
            { title: '规格', key: 'standard', width: '9%', type: FormTypes.normal },
            { title: '型号', key: 'model', width: '9%', type: FormTypes.normal },
            { title: '颜色', key: 'color', width: '5%', type: FormTypes.normal },
            { title: '品牌', key: 'brand', width: '6%', type: FormTypes.normal },
            { title: '制造商', key: 'mfrs', width: '6%', type: FormTypes.normal },
            { title: '扩展1', key: 'otherField1', width: '4%', type: FormTypes.normal },
            { title: '扩展2', key: 'otherField2', width: '4%', type: FormTypes.normal },
            { title: '扩展3', key: 'otherField3', width: '4%', type: FormTypes.normal },
            { title: '库存', key: 'stock', width: '5%', type: FormTypes.normal },
            { title: '单位', key: 'unit', width: '4%', type: FormTypes.normal },
            { title: '多属性', key: 'sku', width: '9%', type: FormTypes.normal },
            { title: '原数量', key: 'preNumber', width: '4%', type: FormTypes.normal },
            { title: '已采购', key: 'finishNumber', width: '4%', type: FormTypes.normal },
            { title: '数量', key: 'operNumber', width: '5%', type: FormTypes.inputNumber, statistics: true,
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '单价', key: 'unitPrice', width: '5%', type: FormTypes.inputNumber },
            { title: '金额', key: 'allPrice', width: '5%', type: FormTypes.inputNumber, statistics: true },
            { title: '税率', key: 'taxRate', width: '4%', type: FormTypes.inputNumber,placeholder: '%'},
            { title: '税额', key: 'taxMoney', width: '5%', type: FormTypes.inputNumber, readonly: true, statistics: true },
            { title: '价税合计', key: 'taxLastMoney', width: '7%', type: FormTypes.inputNumber, statistics: true },
            { title: '备注', key: 'remark', width: '6%', type: FormTypes.input},
            { title: '关联id', key: 'linkId', width: '5%', type: FormTypes.hidden },
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
          }
        },
        url: {
          add: '/depotHead/addDepotHeadAndDetail',
          edit: '/depotHead/updateDepotHeadAndDetail',
          detailList: '/depotItem/getDetailList',
          importExcelUrl: "/depotItem/importItemExcel",
        }
      }
    },
    created () {
    },
    methods: {
      //调用完edit()方法之后会自动调用此方法
      editAfter() {
        this.billStatus = '0'
        this.currentSelectDepotId = ''
        this.rowCanEdit = true
        this.materialTable.columns[1].type = FormTypes.popupJsh
        this.changeColumnHide()
        this.changeFormTypes(this.materialTable.columns, 'preNumber', 0)
        this.changeFormTypes(this.materialTable.columns, 'finishNumber', 0)
        if (this.action === 'add') {
          this.addInit(this.prefixNo)
          this.fileList = []
          this.$nextTick(() => {
            handleIntroJs(this.prefixNo, 1)
          })
        } else {
          if(this.model.linkNumber) {
            this.rowCanEdit = false
            this.materialTable.columns[1].type = FormTypes.normal
          }
          this.model.operTime = this.model.operTimeStr
          if(this.model.accountId == null && this.model.accountIdList) {
            this.model.accountId = 0
            this.manyAccountBtnStatus = true
            this.accountIdList = this.model.accountIdList
            this.accountMoneyList = this.model.accountMoneyList
          } else {
            this.manyAccountBtnStatus = false
          }
          this.fileList = this.model.fileName
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'organId', 'operTime', 'number', 'linkApply', 'linkNumber', 'remark',
            'discount','discountMoney','discountLastMoney','accountId','changeAmount'))
          });
          // 加载子表数据
          let params = {
            headerId: this.model.id,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList')),  //扩展属性
            linkType: 'basic'
          }
          let url = this.readOnly ? this.url.detailList : this.url.detailList;
          this.requestSubTableData(url, params, this.materialTable);
        }
        //复制新增单据-初始化单号和日期
        if(this.action === 'copyAdd') {
          this.model.id = ''
          this.model.tenantId = ''
          this.copyAddInit(this.prefixNo)
        }
        this.initSystemConfig()
        this.initSupplier(0)
        this.initAccount(0)
        this.initPlatform()
        this.initQuickBtn()
        this.handleChangeOtherField()
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let totalPrice = 0
        let billMain = Object.assign(this.model, allValues.formValue)
        let detailArr = allValues.tablesValue[0].values
        billMain.type = '其它'
        billMain.subType = '采购订单'
        for(let item of detailArr){
          item.depotId = '' //订单不需要仓库
          totalPrice += item.allPrice-0
        }
        billMain.totalPrice = 0-totalPrice
        billMain.changeAmount = 0-billMain.changeAmount
        if(billMain.accountId === 0) {
          billMain.accountId = ''
        }
        this.accountMoneyList = changeListFmtMinus(this.accountMoneyList)
        billMain.accountIdList = this.accountIdList.length>0 ? JSON.stringify(this.accountIdList) : ""
        billMain.accountMoneyList = this.accountMoneyList.length>0 ? JSON.stringify(this.accountMoneyList) : ""
        if(this.fileList && this.fileList.length > 0) {
          billMain.fileName = this.fileList
        } else {
          billMain.fileName = ''
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
      handleHistoryBillList() {
        let organId = this.form.getFieldValue('organId')
        this.$refs.historyBillListModalForm.show('其它', '采购订单', '供应商', organId);
        this.$refs.historyBillListModalForm.disableSubmit = false;
      },
      onSearchLinkNumber() {
        this.$refs.linkBillList.purchaseShow('其它', '销售订单', '客户', "1,3","0,3")
        this.$refs.linkBillList.title = "请选择销售订单"
      },
      onSearchLinkApply() {
        this.$refs.linkBillList.purchaseShow('其它', '请购单', '客户', "1,3")
        this.$refs.linkBillList.title = "请选择请购单"
      },
      linkBillListOk(selectBillDetailRows, linkNumber, organId) {
        this.rowCanEdit = false
        this.materialTable.columns[1].type = FormTypes.normal
        this.changeFormTypes(this.materialTable.columns, 'preNumber', 1)
        this.changeFormTypes(this.materialTable.columns, 'finishNumber', 1)
        if(selectBillDetailRows && selectBillDetailRows.length>0) {
          let discountLastMoney = 0
          for(let j=0; j<selectBillDetailRows.length; j++) {
            let info = selectBillDetailRows[j];
            if (info.preNumber) {
              info.operNumber = info.preNumber - info.finishNumber
              info.unitPrice = info.purchaseDecimal
              info.allPrice = (info.operNumber * info.unitPrice).toFixed(2) - 0;
              info.taxRate = 0
              info.taxMoney = 0
              info.taxLastMoney = info.allPrice
              discountLastMoney += info.allPrice
            }
            info.linkId = info.id
            this.changeColumnShow(info)
          }
          //根据单号查询单据类型
          findBillDetailByNumber({'number':linkNumber}).then((res) => {
            if (res.code === 200) {
              if(res.data && res.data.subType === '请购单') {
                //关联请购单
                this.$nextTick(() => {
                  this.form.setFieldsValue({
                    'linkApply': linkNumber
                  })
                })
              } else {
                this.$nextTick(() => {
                  this.form.setFieldsValue({
                    'linkNumber': linkNumber
                  })
                })
              }
            }
          })
          //给优惠后金额重新赋值
          discountLastMoney = discountLastMoney?discountLastMoney:0
          this.$nextTick(() => {
            this.form.setFieldsValue({
              'discountLastMoney': discountLastMoney.toFixed(2),
              'changeAmount': discountLastMoney.toFixed(2)
            })
          })
          this.materialTable.dataSource = selectBillDetailRows
        }
      }
    }
  }
</script>
<style scoped>

</style>
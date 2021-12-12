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
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="会员卡号">
              <a-select placeholder="选择会员卡号" v-decorator="[ 'organId' ]"
                :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                <div slot="dropdownRender" slot-scope="menu">
                  <v-nodes :vnodes="menu" />
                  <a-divider style="margin: 4px 0;" />
                  <div v-if="isTenant" style="padding: 4px 8px; cursor: pointer;"
                       @mousedown="e => e.preventDefault()" @click="addMember"><a-icon type="plus" /> 新增会员</div>
                </div>
                <a-select-option v-for="(item,index) in retailList" :key="index" :value="item.id">
                  {{ item.supplier }}
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
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
              <a-input placeholder="请输入单据编号" v-decorator.trim="[ 'number' ]" :readOnly="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单据">
              <a-input-search placeholder="请选择关联单据" v-decorator="[ 'linkNumber' ]" @search="onSearchLinkNumber" :readOnly="true"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="18" :md="12" :sm="24">
            <j-editable-table id="billModal"
              :ref="refKeys[0]"
              :loading="materialTable.loading"
              :columns="materialTable.columns"
              :dataSource="materialTable.dataSource"
              :minWidth="1100"
              :maxHeight="300"
              :rowNumber="false"
              :rowSelection="true"
              :actionButton="true"
              :dragSort="true"
              @valueChange="onValueChange"
              @added="onAdded"
              @deleted="onDeleted">
              <template #buttonAfter>
                <a-row :gutter="24" style="float:left;" data-step="4" data-title="扫码录入" data-intro="此功能支持扫码枪扫描商品条码进行录入">
                  <a-col v-if="scanStatus" :md="6" :sm="24">
                    <a-button @click="scanEnter">扫码录入</a-button>
                  </a-col>
                  <a-col v-if="!scanStatus" :md="16" :sm="24" style="padding: 0 6px 0 12px">
                    <a-input placeholder="请扫码商品条码并回车" v-model="scanBarCode" @pressEnter="scanPressEnter" ref="scanBarCode"/>
                  </a-col>
                  <a-col v-if="!scanStatus" :md="6" :sm="24" style="padding: 0px">
                    <a-button @click="stopScan">收起扫码</a-button>
                  </a-col>
                </a-row>
                <a-row :gutter="24" style="float:left;">
                  <a-col :md="24" :sm="24">
                    <a-dropdown>
                      <a-menu slot="overlay">
                        <a-menu-item key="1" @click="handleBatchSetDepot"><a-icon type="setting"/>批量设置</a-menu-item>
                        <a-menu-item v-if="isTenant" key="2" @click="addDepot"><a-icon type="plus"/>新增仓库</a-menu-item>
                      </a-menu>
                      <a-button style="margin-left: 8px">仓库操作 <a-icon type="down" /></a-button>
                    </a-dropdown>
                  </a-col>
                </a-row>
              </template>
            </j-editable-table>
          </a-col>
          <a-col :lg="6" :md="12" :sm="24">
            <a-row class="form-row" :gutter="24">
              <a-col :lg="24" :md="6" :sm="6"><br/><br/></a-col>
              <a-col :lg="24" :md="6" :sm="6">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据金额">
                  <a-input v-decorator.trim="[ 'changeAmount' ]" :style="{color:'purple'}" :readOnly="true"/>
                </a-form-item>
              </a-col>
              <a-col :lg="24" :md="6" :sm="6">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款金额">
                  <a-input v-decorator.trim="[ 'getAmount' ]" :style="{color:'red'}" defaultValue="0" @keyup="onKeyUpGetAmount"/>
                </a-form-item>
              </a-col>
              <a-col :lg="24" :md="6" :sm="6">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="找零">
                  <a-input v-decorator.trim="[ 'backAmount' ]" :style="{color:'green'}" :readOnly="true" defaultValue="0"/>
                </a-form-item>
              </a-col>
              <a-col :lg="24" :md="6" :sm="6">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款账户">
                  <a-select placeholder="选择付款账户" v-decorator="[ 'accountId', validatorRules.accountId ]" :dropdownMatchSelectWidth="false">
                    <div slot="dropdownRender" slot-scope="menu">
                      <v-nodes :vnodes="menu" />
                      <a-divider style="margin: 4px 0;" />
                      <div v-if="isTenant" style="padding: 4px 8px; cursor: pointer;"
                           @mousedown="e => e.preventDefault()" @click="addAccount"><a-icon type="plus" /> 新增结算账户</div>
                    </div>
                    <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </a-row>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="24" :md="24" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="">
              <a-textarea :rows="1" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="附件">
              <j-upload v-model="fileList" bizPath="bill"></j-upload>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
    <link-bill-list ref="linkBillList" @ok="linkBillListOk"></link-bill-list>
    <member-modal ref="memberModalForm" @ok="memberModalFormOk"></member-modal>
    <depot-modal ref="depotModalForm" @ok="depotModalFormOk"></depot-modal>
    <account-modal ref="accountModalForm" @ok="accountModalFormOk"></account-modal>
    <batch-set-depot ref="batchSetDepotModalForm" @ok="batchSetDepotModalFormOk"></batch-set-depot>
  </j-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import LinkBillList from '../dialog/LinkBillList'
  import MemberModal from '../../system/modules/MemberModal'
  import DepotModal from '../../system/modules/DepotModal'
  import AccountModal from '../../system/modules/AccountModal'
  import BatchSetDepot from '../dialog/BatchSetDepot'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { BillModalMixin } from '../mixins/BillModalMixin'
  import { getMpListShort } from "@/utils/util"
  import { getAccount } from '@/api/api'
  import { getAction } from '@/api/manage'
  import JUpload from '@/components/jeecg/JUpload'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "RetailBackModal",
    mixins: [JEditableTableMixin, BillModalMixin],
    components: {
      LinkBillList,
      MemberModal,
      DepotModal,
      AccountModal,
      BatchSetDepot,
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
        operTimeStr: '',
        prefixNo: 'LSTH',
        fileList:[],
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
            { title: '仓库名称', key: 'depotId', width: '7%', type: FormTypes.select, placeholder: '请选择${title}', options: [],
              allowSearch:true, validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '条码', key: 'barCode', width: '12%', type: FormTypes.popupJsh, kind: 'material', multi: true,
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '名称', key: 'name', width: '9%', type: FormTypes.normal },
            { title: '规格', key: 'standard', width: '6%', type: FormTypes.normal },
            { title: '型号', key: 'model', width: '6%', type: FormTypes.normal },
            { title: '颜色', key: 'color', width: '5%', type: FormTypes.normal },
            { title: '扩展信息', key: 'materialOther', width: '7%', type: FormTypes.normal },
            { title: '库存', key: 'stock', width: '5%', type: FormTypes.normal },
            { title: '单位', key: 'unit', width: '4%', type: FormTypes.normal },
            { title: '序列号', key: 'snList', width: '12%', type: FormTypes.input, placeholder: '多个序列号请用逗号隔开',
              validateRules: [{ pattern: /^\S{1,100}$/, message: '请小于100位字符' }]
            },
            { title: '批号', key: 'batchNumber', width: '7%', type: FormTypes.input },
            { title: '有效期', key: 'expirationDate',width: '9%', type: FormTypes.date },
            { title: '多属性', key: 'sku', width: '5%', type: FormTypes.normal },
            { title: '数量', key: 'operNumber', width: '5%', type: FormTypes.inputNumber, statistics: true,
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            { title: '单价', key: 'unitPrice', width: '5%', type: FormTypes.inputNumber},
            { title: '金额', key: 'allPrice', width: '5%', type: FormTypes.inputNumber, statistics: true },
            { title: '备注', key: 'remark', width: '7%', type: FormTypes.input }
          ]
        },
        confirmLoading: false,
        validatorRules:{
          operTime:{
            rules: [
              { required: true, message: '请输入单据日期!' }
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
        this.changeColumnHide()
        this.changeFormTypes(this.materialTable.columns, 'snList', 0)
        this.changeFormTypes(this.materialTable.columns, 'batchNumber', 0)
        this.changeFormTypes(this.materialTable.columns, 'expirationDate', 0)
        if (this.action === 'add') {
          this.addInit(this.prefixNo)
          this.fileList = []
        } else {
          this.model.operTime = this.model.operTimeStr
          if(this.model.backAmount) {
            this.model.getAmount = (this.model.changeAmount + this.model.backAmount).toFixed(2)
          } else {
            this.model.getAmount = this.model.changeAmount
          }
          this.fileList = this.model.fileName
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'organId', 'operTime', 'number', 'linkNumber', 'remark',
              'discount','discountMoney','discountLastMoney','otherMoney','accountId','changeAmount','getAmount','backAmount'))
          });
          // 加载子表数据
          let params = {
            headerId: this.model.id,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
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
        this.initRetail()
        this.initDepot()
        this.initAccount()
      },
      //提交单据时整理成formData
      classifyIntoFormData(allValues) {
        let totalPrice = 0
        let billMain = Object.assign(this.model, allValues.formValue)
        let detailArr = allValues.tablesValue[0].values
        billMain.type = '入库'
        billMain.subType = '零售退货'
        billMain.defaultNumber = billMain.number
        for(let item of detailArr){
          totalPrice += item.allPrice-0
        }
        billMain.totalPrice = 0-totalPrice
        billMain.changeAmount = 0-billMain.changeAmount
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
      },
      initAccount(){
        getAccount({}).then((res)=>{
          if(res && res.code === 200) {
            this.accountList = res.data.accountList
          }
        })
      },
      //改变实收金额、收款金额的值
      autoChangePrice(target) {
        let allLastMoney = target.statisticsColumns.allPrice
        this.$nextTick(() => {
          this.form.setFieldsValue({'changeAmount':allLastMoney,'getAmount':allLastMoney,'backAmount':0})
        });
      },
      //改变收款金额
      onKeyUpGetAmount(e) {
        const value = e.target.value
        let changeAmount = this.form.getFieldValue('changeAmount')-0
        let backAmount = (value - changeAmount).toFixed(2)-0
        this.$nextTick(() => {
          this.form.setFieldsValue({'backAmount':backAmount})
        });
      },
      onSearchLinkNumber() {
        this.$refs.linkBillList.show('出库', '零售', '会员', "1")
        this.$refs.linkBillList.title = "选择零售出库"
      },
      linkBillListOk(selectBillRows) {
        if(selectBillRows && selectBillRows.length>0) {
          let record = selectBillRows[0]
          this.$nextTick(() => {
            this.form.setFieldsValue({
              'organId': record.organId,
              'linkNumber': record.number,
              'remark': record.remark,
              'getAmount': record.totalPrice,
              'changeAmount': record.totalPrice,
              'backAmount': 0
            })
          });
          // 加载子表数据
          let params = {
            headerId: record.id,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
          }
          this.requestSubTableDataEx(this.url.detailList, params, this.materialTable);
        }
      },
      /** 查询某个tab的数据,给明细里面的价税合计赋值 */
      requestSubTableDataEx(url, params, tab, success) {
        tab.loading = true
        getAction(url, params).then(res => {
          if(res && res.code === 200){
            let list = res.data.rows
            let listEx = []
            for(let j=0; j<list.length; j++){
              let info = list[j];
              info.taxMoney = 0
              info.taxLastMoney = info.allPrice
              listEx.push(info)
            }
            tab.dataSource = listEx
            typeof success === 'function' ? success(res) : ''
          }
        }).finally(() => {
          tab.loading = false
        })
      }
    }
  }
</script>
<style scoped>

</style>
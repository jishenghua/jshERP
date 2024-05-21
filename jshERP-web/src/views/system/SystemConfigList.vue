<template>
  <a-card :style="cardStyle" :bordered="false">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司名称">
              <a-input placeholder="请输入公司名称" v-decorator.trim="[ 'companyName' ]" @change="handleCompanyName" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系人">
              <a-input placeholder="请输入联系人" v-decorator.trim="[ 'companyContacts' ]" @change="handleCompanyContacts" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司地址">
              <a-input placeholder="请输入公司地址" v-decorator.trim="[ 'companyAddress' ]" @change="handleCompanyAddress" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司电话">
              <a-input placeholder="请输入公司电话" v-decorator.trim="[ 'companyTel' ]" @change="handleCompanyTel" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司传真">
              <a-input placeholder="请输入公司传真" v-decorator.trim="[ 'companyFax' ]" @change="handleCompanyFax" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司邮编">
              <a-input placeholder="请输入公司邮编" v-decorator.trim="[ 'companyPostCode' ]" @change="handleCompanyPostCode" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售协议">
              <a-input placeholder="请输入销售协议" v-decorator.trim="[ 'saleAgreement' ]" @change="handleSaleAgreement" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="仓库权限">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="depotFlagSwitch" @change="onDepotChange"></a-switch>
              （启用后，需要到<b>用户管理</b>进行<b>分配仓库</b>）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户权限">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="customerFlagSwitch" @change="onCustomerChange"></a-switch>
              （启用后，需要到<b>用户管理</b>进行<b>分配客户</b>）
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="支持负库存">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="minusStockFlagSwitch" @change="onMinusStockChange"></a-switch>
              （启用后，单据<b>支持负库存</b>录入）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="以销定购">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="purchaseBySaleFlagSwitch" @change="onPurchaseBySaleChange"></a-switch>
              （启用后，根据<b>销售订单</b>来定制<b>采购订单</b>，进货后再发给客户）
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="超出关联单据">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="overLinkBillFlagSwitch" @change="onOverLinkBillChange"></a-switch>
              （启用后，允许当前单据<b>超出关联单据</b>的商品数量进行出入库）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="更新单价">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="updateUnitPriceFlagSwitch" @change="onUpdateUnitPriceChange"></a-switch>
              （启用后，会根据单据录入自动更新商品单价，默认是启用状态）
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="强审核">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="forceApprovalFlagSwitch" @change="onForceApprovalChange"></a-switch>
              （启用后，只有<b>已审核</b>的单据才能产生库存，涉及库存查询的相关报表。启用或关闭后需到<b>商品管理</b>批量<b>修正库存</b>，请按实际业务谨慎操作）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="出入库管理">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="inOutManageFlagSwitch" @change="onInOutManageChange"></a-switch>
              （启用后，采购销售相关单据都需经过<b>其它出入库</b>单据，才能产生库存。启用或关闭后需到<b>商品管理</b>批量<b>修正库存</b>，请按实际业务谨慎操作）
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="多账户">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="multiAccountFlagSwitch" @change="onMultiAccountChange"></a-switch>
              （启用后，采购订单、采购入库、采购退货、销售订单、销售出库、销售退货等单据的结算账户可以进行多账户选择）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="移动平均价">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="moveAvgPriceFlagSwitch" @change="onMoveAvgPriceChange"></a-switch>
              （默认为关闭状态，代表成本价等于商品信息页面录入的采购价。开启之后将通过移动平均来计算成本价）
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24" v-if="isShowApproval">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="多级审核">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="multiLevelApprovalFlagSwitch" @change="onMultiLevelApprovalChange"></a-switch>
              <a-select placeholder="请选择流程类型" v-model="multiBillTypeSelect" style="width:400px;padding-left:10px"
                        mode="multiple" :maxTagCount="6" :dropdownMatchSelectWidth="false"
                        showSearch allow-clear optionFilterProp="children" @change="onMultiBillTypeChange">
                <a-select-option v-for="(item,index) in billTypeList" :key="index" :value="item.key">
                  {{ item.value }}
                </a-select-option>
              </a-select>
              <br/>（启用后，多级审核需配置流程，开启后需刷新浏览器才能看到效果）<a-button type="link" @click="handleReload">点此刷新</a-button>
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-card>
</template>
<!-- b y 7 5 2 7  1 8 9 2 0 -->
<script>
  import pick from 'lodash.pick'
  import JSelectMultiple from '@/components/jeecg/JSelectMultiple'
  import { addSystemConfig, editSystemConfig } from '@/api/api'
  import { autoJumpNextInput } from '@/utils/util'
  import { getAction } from '@/api/manage'
  import { mixinDevice } from '@/utils/mixin.js'

  export default {
    name: "SystemConfigList",
    mixins: [mixinDevice],
    components: {
      JSelectMultiple
    },
    data () {
      return {
        title:"操作",
        cardStyle: '',
        visible: true,
        model: {},
        depotFlagSwitch: false, //仓库权限状态
        customerFlagSwitch: false, //客户权限状态
        minusStockFlagSwitch: false, //负库存状态
        purchaseBySaleFlagSwitch: false, //以销定购状态
        overLinkBillFlagSwitch: false, //超出关联单据状态
        updateUnitPriceFlagSwitch: true, //更新单价状态
        forceApprovalFlagSwitch: false, //强审核
        inOutManageFlagSwitch: false, //出入库管理
        multiLevelApprovalFlagSwitch: false, //多级审核
        originalMultiLevelApprovalFlag: '0', //原始多级审核状态
        multiBillTypeSelect: [], //单据类型
        originalMultiBillTypeSelect: [], //原始单据类型
        isShowApproval: false, //是否展示多级审核
        multiAccountFlagSwitch: false, //多账户
        moveAvgPriceFlagSwitch: false, //移动平均价
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
        billTypeList: [
          { 'key': 'LSCK', 'value': '零售出库' },
          { 'key': 'LSTH', 'value': '零售退货' },
          { 'key': 'QGD', 'value': '请购单' },
          { 'key': 'CGDD', 'value': '采购订单' },
          { 'key': 'CGRK', 'value': '采购入库' },
          { 'key': 'CGTH', 'value': '采购退货' },
          { 'key': 'XSDD', 'value': '销售订单' },
          { 'key': 'XSCK', 'value': '销售出库' },
          { 'key': 'XSTH', 'value': '销售退货' },
          { 'key': 'QTRK', 'value': '其它入库单' },
          { 'key': 'QTCK', 'value': '其它出库单' },
          { 'key': 'DBCK', 'value': '调拨出库' },
          { 'key': 'ZZD', 'value': '组装单' },
          { 'key': 'CXD', 'value': '拆卸单' },
          { 'key': 'SR', 'value': '收入单' },
          { 'key': 'ZC', 'value': '支出单' },
          { 'key': 'SK', 'value': '收款单' },
          { 'key': 'FK', 'value': '付款单' },
          { 'key': 'ZZ', 'value': '转账单' },
          { 'key': 'SYF', 'value': '收预付款单' },
        ]
      }
    },
    created () {
      this.init()
      this.loadPlugins()
      if(this.isDesktop()) {
        this.cardStyle = 'height:' + (document.documentElement.clientHeight-125) + 'px'
      }
    },
    methods: {
      //初始化加载内容
      init () {
        let param = {
          search: {"companyName":""},
          currentPage: 1,
          pageSize: 10
        }
        getAction('/systemConfig/list', param).then((res)=>{
          if(res.code === 200){
            let record = res.data.rows[0]
            this.form.resetFields();
            this.model = Object.assign({}, record);
            this.visible = true;
            this.$nextTick(() => {
              this.form.setFieldsValue(pick(this.model,'companyName', 'companyContacts', 'companyAddress',
                'companyTel', 'companyFax', 'companyPostCode', 'saleAgreement'))
            });
            if(record.id) {
              if (record.depotFlag != null) {
                this.depotFlagSwitch = record.depotFlag == '1' ? true : false;
              }
              if (record.customerFlag != null) {
                this.customerFlagSwitch = record.customerFlag == '1' ? true : false;
              }
              if (record.minusStockFlag != null) {
                this.minusStockFlagSwitch = record.minusStockFlag == '1' ? true : false;
              }
              if (record.purchaseBySaleFlag != null) {
                this.purchaseBySaleFlagSwitch = record.purchaseBySaleFlag == '1' ? true : false;
              }
              if (record.overLinkBillFlag != null) {
                this.overLinkBillFlagSwitch = record.overLinkBillFlag == '1' ? true : false;
              }
              if (record.updateUnitPriceFlag != null) {
                this.updateUnitPriceFlagSwitch = record.updateUnitPriceFlag == '1' ? true : false;
              }
              if (record.forceApprovalFlag != null) {
                this.forceApprovalFlagSwitch = record.forceApprovalFlag == '1' ? true : false;
              }
              if (record.inOutManageFlag != null) {
                this.inOutManageFlagSwitch = record.inOutManageFlag == '1' ? true : false;
              }
              if (record.multiLevelApprovalFlag != null) {
                this.multiLevelApprovalFlagSwitch = record.multiLevelApprovalFlag == '1' ? true : false;
                this.originalMultiLevelApprovalFlag = record.multiLevelApprovalFlag
              }
              if (record.multiBillType != null && record.multiBillType != '') {
                this.multiBillTypeSelect = record.multiBillType.split(',')
                this.originalMultiBillTypeSelect = record.multiBillType
              }
              if (record.multiAccountFlag != null) {
                this.multiAccountFlagSwitch = record.multiAccountFlag == '1' ? true : false;
              }
              if (record.moveAvgPriceFlag != null) {
                this.moveAvgPriceFlagSwitch = record.moveAvgPriceFlag == '1' ? true : false;
              }
            }
          } else {
            this.$message.info(res.data);
          }
        })
      },
      loadPlugins() {
        //校验是否存在多级审批插件
        getAction('/plugin/checkByPluginId', { pluginIds: 'workflow' }).then((res)=> {
          if (res.code === 200) {
            if(res.data) {
              this.isShowApproval = true
            }
          }
        })
        //校验是否存在盘点插件
        getAction('/plugin/checkByPluginId', { pluginIds: 'stock-check' }).then((res)=> {
          if (res.code === 200) {
            if(res.data) {
              this.billTypeList.push({ 'key': 'PDLR', 'value': '盘点录入' }, { 'key': 'PDFP', 'value': '盘点复盘' })
              //校验是否存在生产插件
              getAction('/plugin/checkByPluginId', { pluginIds: 'produce' }).then((res)=> {
                if (res.code === 200) {
                  if(res.data) {
                    this.billTypeList.push({ 'key': 'SC', 'value': '生产任务' }, { 'key': 'WW', 'value': '委外任务' })
                  }
                }
              })
            }
          }
        })
      },
      handleCompanyName(event) {
        this.model.companyName = event.target.value
        if(this.model.companyName && this.model.companyName.length>30) {
          this.$message.warning('公司名称长度超过30个字符')
        } else {
          this.handleChange()
        }
      },
      handleCompanyContacts(event) {
        this.model.companyContacts = event.target.value
        this.handleChange()
      },
      handleCompanyAddress(event) {
        this.model.companyAddress = event.target.value
        this.handleChange()
      },
      handleCompanyTel(event) {
        this.model.companyTel = event.target.value
        this.handleChange()
      },
      handleCompanyFax(event) {
        this.model.companyFax = event.target.value
        this.handleChange()
      },
      handleCompanyPostCode(event) {
        this.model.companyPostCode = event.target.value
        this.handleChange()
      },
      handleSaleAgreement(event) {
        this.model.saleAgreement = event.target.value
        if(this.model.saleAgreement && this.model.saleAgreement.length>400) {
          this.$message.warning('销售协议长度超过400个字符')
        } else {
          this.handleChange()
        }
      },
      onDepotChange(checked) {
        this.model.depotFlag = checked?'1':'0'
        this.handleChange()
      },
      onCustomerChange(checked) {
        this.model.customerFlag = checked?'1':'0'
        this.handleChange()
      },
      onMinusStockChange(checked) {
        this.model.minusStockFlag = checked?'1':'0'
        this.handleChange()
      },
      onPurchaseBySaleChange(checked) {
        this.model.purchaseBySaleFlag = checked?'1':'0'
        this.handleChange()
      },
      onOverLinkBillChange(checked) {
        this.model.overLinkBillFlag = checked?'1':'0'
        this.handleChange()
      },
      onUpdateUnitPriceChange(checked) {
        this.model.updateUnitPriceFlag = checked?'1':'0'
        this.handleChange()
      },
      onForceApprovalChange(checked) {
        this.model.forceApprovalFlag = checked?'1':'0'
        this.handleChange()
      },
      onInOutManageChange(checked) {
        this.model.inOutManageFlag = checked?'1':'0'
        this.handleChange()
      },
      onMultiLevelApprovalChange(checked) {
        this.model.multiLevelApprovalFlag = checked?'1':'0'
        if(!checked) {
          this.multiBillTypeSelect = []
          this.model.multiBillType = ''
        }
        this.handleChange()
      },
      onMultiBillTypeChange() {
        this.model.multiBillType = this.multiBillTypeSelect.join(",")
        this.handleChange()
      },
      onMultiAccountChange(checked) {
        this.model.multiAccountFlag = checked?'1':'0'
        this.handleChange()
      },
      onMoveAvgPriceChange(checked) {
        this.model.moveAvgPriceFlag = checked?'1':'0'
        this.handleChange()
      },
      //改变内容
      handleChange() {
        this.confirmLoading = true
        let obj
        if(!this.model.id){
          obj = addSystemConfig(this.model)
        }else{
          obj = editSystemConfig(this.model)
        }
        obj.then((res)=>{
          if(res.code === 200){
            this.init()
          }else{
            this.$message.warning(res.data.message)
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      },
      //刷新浏览器
      handleReload() {
        location.reload()
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
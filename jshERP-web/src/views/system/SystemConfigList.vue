<template>
  <a-card :style="cardStyle" :bordered="false">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form" id="systemConfigModal">
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司名称">
              <a-input placeholder="请输入公司名称" v-decorator.trim="[ 'companyName', validatorRules.companyName]" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系人">
              <a-input placeholder="请输入联系人" v-decorator.trim="[ 'companyContacts' ]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司地址">
              <a-input placeholder="请输入公司地址" v-decorator.trim="[ 'companyAddress' ]" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司电话">
              <a-input placeholder="请输入公司电话" v-decorator.trim="[ 'companyTel' ]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司传真">
              <a-input placeholder="请输入公司传真" v-decorator.trim="[ 'companyFax' ]" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公司邮编">
              <a-input placeholder="请输入公司邮编" v-decorator.trim="[ 'companyPostCode' ]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售协议">
              <a-input placeholder="请输入销售协议" v-decorator.trim="[ 'saleAgreement', validatorRules.saleAgreement ]" />
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="仓库权限">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="depotFlagSwitch" @change="onDepotChange"></a-switch>
              （如果启用则需要到<b>用户管理</b>进行<b>分配仓库</b>）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户权限">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="customerFlagSwitch" @change="onCustomerChange"></a-switch>
              （如果启用则需要到<b>用户管理</b>进行<b>分配客户</b>）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="负库存">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="minusStockFlagSwitch" @change="onMinusStockChange"></a-switch>
              （如果启用则单据支持<b>负库存</b>录入）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="以销定购">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="purchaseBySaleFlagSwitch" @change="onPurchaseBySaleChange"></a-switch>
              （如果启用则根据<b>销售订单</b>来定制<b>采购订单</b>，进货后再发给客户）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="强审核">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="forceApprovalFlagSwitch" @change="onForceApprovalChange"></a-switch>
              （如果启用则只有<b>已审核</b>的单据才生效，涉及库存和报表，需批量修正库存）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
        <a-row class="form-row" :gutter="24" v-if="isShowApproval">
          <a-col :lg="12" :md="12" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="多级审核">
              <a-switch checked-children="启用" un-checked-children="关闭" v-model="multiLevelApprovalFlagSwitch" @change="onMultiLevelApprovalChange"></a-switch>
              <a-select placeholder="请选择流程类型" v-model="multiBillTypeSelect" style="width:400px;padding-left:10px"
                        mode="multiple" :maxTagCount="6" :dropdownMatchSelectWidth="false"
                        showSearch allow-clear optionFilterProp="children">
                <a-select-option v-for="(item,index) in billTypeList" :key="index" :value="item.key">
                  {{ item.value }}
                </a-select-option>
              </a-select>
              <br/>（如果启用多级审核，则需配置流程，开启会自动刷新浏览器）
            </a-form-item>
          </a-col>
          <a-col :lg="12" :md="12" :sm="24"></a-col>
        </a-row>
      </a-form>
    </a-spin>
    <a-row :gutter="24">
      <a-col :md="24" align="middle">
        <a-button type="primary" @click="handleOk">保存</a-button>
        <a-button style="margin-left:20px" @click="handleReset">重置</a-button>
      </a-col>
    </a-row>
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
        forceApprovalFlagSwitch: false, //强审核
        multiLevelApprovalFlagSwitch: false, //多级审核
        originalMultiLevelApprovalFlag: '0', //原始多级审核状态
        multiBillTypeSelect: [], //单据类型
        originalMultiBillTypeSelect: [], //原始单据类型
        isReadOnly: false,
        isShowApproval: false,
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
        ],
        validatorRules:{
          companyName:{
            rules: [
              { required: true, message: '请输入公司名称!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
            ]
          },
          saleAgreement:{
            rules: [
              { min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' }
            ]
          }
        }
      }
    },
    created () {
      this.init()
      if(this.isDesktop()) {
        this.cardStyle = 'height:' + (document.documentElement.clientHeight-125) + 'px'
      }
    },
    methods: {
      onDepotChange(checked) {
        this.model.depotFlag = checked?'1':'0'
      },
      onCustomerChange(checked) {
        this.model.customerFlag = checked?'1':'0'
      },
      onMinusStockChange(checked) {
        this.model.minusStockFlag = checked?'1':'0'
      },
      onPurchaseBySaleChange(checked) {
        this.model.purchaseBySaleFlag = checked?'1':'0'
      },
      onForceApprovalChange(checked) {
        this.model.forceApprovalFlag = checked?'1':'0'
      },
      onMultiLevelApprovalChange(checked) {
        this.model.multiLevelApprovalFlag = checked?'1':'0'
        if(!checked) {
          this.multiBillTypeSelect = []
        }
      },
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
              autoJumpNextInput('systemConfigModal')
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
              if (record.forceApprovalFlag != null) {
                this.forceApprovalFlagSwitch = record.forceApprovalFlag == '1' ? true : false;
              }
              if (record.multiLevelApprovalFlag != null) {
                this.multiLevelApprovalFlagSwitch = record.multiLevelApprovalFlag == '1' ? true : false;
                this.originalMultiLevelApprovalFlag = record.multiLevelApprovalFlag
              }
              if (record.multiBillType != null && record.multiBillType != '') {
                this.multiBillTypeSelect = record.multiBillType.split(',')
                this.originalMultiBillTypeSelect = record.multiBillType
              }
            }
          } else {
            this.$message.info(res.data);
          }
        })
        //校验是否存在多级审批插件
        getAction('/plugin/checkByPluginId', { pluginIds: 'workflow' }).then((res)=> {
          if (res.code === 200) {
            if(res.data) {
              this.isShowApproval = true
            }
          }
        })
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            formData.multiBillType = this.multiBillTypeSelect.join(",")
            let obj;
            if(!this.model.id){
              obj=addSystemConfig(formData);
            }else{
              obj=editSystemConfig(formData);
            }
            obj.then((res)=>{
              if(res.code === 200){
                this.init()
                that.$message.info('保存成功！');
                //如果多级审核切换状态需要刷新浏览器
                if(this.originalMultiLevelApprovalFlag!= formData.multiLevelApprovalFlag ||
                  this.originalMultiBillTypeSelect!=formData.multiBillType) {
                  location.reload()
                }
              }else{
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
        })
      },
      handleReset () {
        this.form.resetFields();
        this.depotFlagSwitch = false
        this.customerFlagSwitch = false
        this.minusStockFlagSwitch = false
        this.purchaseBySaleFlagSwitch = false
        this.forceApprovalFlagSwitch = false
        this.multiLevelApprovalFlagSwitch = false
        this.multiBillTypeSelect = []
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
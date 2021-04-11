import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
import {findBySelectSup,findBySelectCus,findBySelectRetail,getMaterialByBarCode,findStockByDepotAndBarCode,getAccount,getPersonByNumType} from '@/api/api'
import { getAction,putAction } from '@/api/manage'
import { getMpListShort, getNowFormatDateTime } from "@/utils/util"
import Vue from 'vue'

export const BillModalMixin = {
  data() {
    return {
      action: '',
      manyAccountBtnStatus: false,
      supList: [],
      cusList: [],
      retailList: [],
      personList: {
        options: [],
        value: ''
      },
      depotList: [],
      accountList: [],
      accountIdList: [],
      accountMoneyList: [],
      spans: {
        labelCol1: {span: 2},
        wrapperCol1: {span: 22},
        //1_5: 分为1.5列（相当于占了2/3）
        labelCol1_5: { span: 3 },
        wrapperCol1_5: { span: 21 },
        labelCol2: {span: 4},
        wrapperCol2: {span: 20},
        labelCol3: {span: 6},
        wrapperCol3: {span: 18},
        labelCol6: {span: 12},
        wrapperCol6: {span: 12}
      },
    };
  },
  created () {
    this.initSupplier()
    this.initCustomer()
    this.initRetail()
    this.initSalesman()
    this.initDepot()
    this.initAccount()
  },
  computed: {
    readOnly: function() {
      return this.action !== "add" && this.action !== "edit";
    }
  },
  methods: {
    addInit(amountNum) {
      getAction('/sequence/buildNumber').then((res) => {
        if (res && res.code === 200) {
          this.form.setFieldsValue({'number':amountNum + res.data.defaultNumber})
        }
      })
      this.$nextTick(() => {
        this.form.setFieldsValue({'operTime':getNowFormatDateTime(), 'discount': 0,
          'discountMoney': 0, 'discountLastMoney': 0, 'otherMoney': 0, 'changeAmount': 0, 'debt': 0})
      })
      this.$nextTick(() => {
        getAccount({}).then((res)=>{
          if(res && res.code === 200) {
            for (const item of res.data.accountList) {
              if(item.isDefault){
                this.form.setFieldsValue({'accountId': item.id})
              }
            }
          }
        })
      })
      this.$nextTick(() => {
        this.form.setFieldsValue({'payType': '现金'})
      })
      this.accountIdList = []
      this.accountMoneyList = []
      this.manyAccountBtnStatus = false
    },
    initSupplier() {
      let that = this;
      findBySelectSup({}).then((res)=>{
        if(res) {
          that.supList = res;
        }
      });
    },
    initCustomer() {
      let that = this;
      findBySelectCus({}).then((res)=>{
        if(res) {
          that.cusList = res;
        }
      });
    },
    initRetail() {
      let that = this;
      findBySelectRetail({}).then((res)=>{
        if(res) {
          that.retailList = res;
        }
      });
    },
    initSalesman() {
      let that = this;
      getPersonByNumType({type:1}).then((res)=>{
        if(res) {
          that.personList.options = res;
        }
      });
    },
    initDepot() {
      let that = this;
      getAction('/depot/findDepotByUserId?UBType=UserDepot&UBKeyId=').then((res) => {
        if (res) {
          for(let i=0; i<res.length; i++) {
            let depotInfo = {};
            depotInfo.value = res[i].id+'' //注意-此处value必须为字符串格式
            depotInfo.text = res[i].depotName
            depotInfo.title = res[i].depotName
            for(let item of that.materialTable.columns){
              if(item.key == 'depotId' || item.key == 'anotherDepotId') {
                item.options.push(depotInfo)
              }
            }
          }
        }
      })
    },
    initAccount(){
      let that = this;
      getAccount({}).then((res)=>{
        if(res && res.code === 200) {
          let list = res.data.accountList
          list.splice(0,0,{id: 0, name: '多账户'})
          that.accountList = list
        }
      })
    },
    handleManyAccount(){
      this.selectAccount(0)
    },
    selectAccount(value){
      if(value === 0) { //多账户
        this.$refs.manyAccountModalForm.edit(this.accountIdList, this.accountMoneyList)
        this.$refs.manyAccountModalForm.title = "多账户结算"
        this.manyAccountBtnStatus = true
      } else {
        this.accountIdList = []
        this.accountMoneyList = []
        this.manyAccountBtnStatus = false
      }
    },
    //单元值改变一个字符就触发一次
    onValueChange(event) {
      let that = this
      const { type, row, column, value, target } = event
      let param,operNumber,unitPrice,taxUnitPrice,allPrice,taxRate,taxMoney,taxLastMoney
      switch(column.key) {
        case "depotId":
          if(row.barCode){
            that.getStockByDepotBarCode(row, target)
          }
          break;
        case "barCode":
          param = {
            barCode: value,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
          }
          getMaterialByBarCode(param).then((res) => {
            if (res && res.code === 200) {
              target.setValues([{
                rowKey: row.id,
                values: {
                  barCode: res.data.mBarCode,
                  name: res.data.name,
                  standard: res.data.standard,
                  model: res.data.model,
                  materialOther: res.data.materialOther,
                  unit: res.data.commodityUnit,
                  operNumber: 1,
                  unitPrice: res.data.purchaseDecimal,
                  taxUnitPrice: res.data.purchaseDecimal,
                  allPrice: res.data.purchaseDecimal,
                  taxRate: 0,
                  taxMoney: 0,
                  taxLastMoney: res.data.purchaseDecimal
                }
              }]);
              that.getStockByDepotBarCode(row, target)
              target.recalcAllStatisticsColumns()
              that.autoChangePrice(target)
            }
          });
          break;
        case "operNumber":
          operNumber = value-0
          taxRate = row.taxRate-0 //税率
          unitPrice = row.unitPrice-0 //单价
          taxUnitPrice = row.taxUnitPrice-0
          allPrice = (unitPrice*operNumber).toFixed(2)-0
          taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
          taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {allPrice: allPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
        case "unitPrice":
          operNumber = row.operNumber-0 //数量
          unitPrice = value-0 //单价
          taxRate = row.taxRate-0 //税率
          taxUnitPrice = (unitPrice*(1+taxRate*0.01)).toFixed(2)-0
          allPrice = (unitPrice*operNumber).toFixed(2)-0
          taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
          taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {taxUnitPrice: taxUnitPrice, allPrice: allPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
        case "allPrice":
          operNumber = row.operNumber-0 //数量
          taxRate = row.taxRate-0 //税率
          allPrice = value-0
          unitPrice = (allPrice/operNumber).toFixed(2)-0 //单价
          taxUnitPrice =(unitPrice*(1+taxRate*0.01)).toFixed(2)-0
          taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
          taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {unitPrice: unitPrice, taxUnitPrice: taxUnitPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
        case "taxRate":
          operNumber = row.operNumber-0 //数量
          allPrice = row.allPrice-0
          unitPrice = row.unitPrice-0
          taxRate = value-0 //税率
          taxUnitPrice =(unitPrice*(1+taxRate*0.01)).toFixed(2)-0
          taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
          taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {taxUnitPrice: taxUnitPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
        case "taxLastMoney":
          operNumber = row.operNumber-0 //数量
          taxLastMoney = value-0
          taxRate = row.taxRate-0 //税率
          taxUnitPrice = (taxLastMoney/operNumber).toFixed(2)-0
          unitPrice = (taxUnitPrice/(1+taxRate*0.01)).toFixed(2)-0
          allPrice = (unitPrice*operNumber).toFixed(2)-0
          taxMoney =(taxLastMoney-allPrice).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {unitPrice: unitPrice, taxUnitPrice: taxUnitPrice, allPrice: allPrice, taxMoney: taxMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
      }
    },
    //删除一行或多行的时候触发
    onDeleted(ids, target) {
      target.recalcAllStatisticsColumns()
      this.autoChangePrice(target)
    },
    //根据仓库和条码查询库存
    getStockByDepotBarCode(row, target){
      findStockByDepotAndBarCode({ depotId: row.depotId, barCode: row.barCode }).then((res) => {
        if (res && res.code === 200) {
          target.setValues([{rowKey: row.id, values: {stock: res.data.stock}}])
          target.recalcAllStatisticsColumns()
        }
      })
    },
    //改变优惠、本次付款、欠款的值
    autoChangePrice(target) {
      let allTaxLastMoney = target.statisticsColumns.taxLastMoney
      let discount = this.form.getFieldValue('discount')-0
      let otherMoney = this.form.getFieldValue('otherMoney')-0
      let discountMoney = (discount*0.01*allTaxLastMoney).toFixed(2)-0
      let discountLastMoney = (allTaxLastMoney-discountMoney).toFixed(2)-0
      let changeAmountNew = (discountLastMoney + otherMoney).toFixed(2)-0
      this.$nextTick(() => {
        this.form.setFieldsValue({'discount':discount,'discountMoney':discountMoney,'discountLastMoney':discountLastMoney,
          'changeAmount':changeAmountNew,'debt':0})
      });
    },
    //改变优惠率
    onKeyUpDiscount(e) {
      const value = e.target.value-0
      let discountMoney = this.form.getFieldValue('discountMoney')-0
      let discountLastMoney = this.form.getFieldValue('discountLastMoney')-0
      let otherMoney = this.form.getFieldValue('otherMoney')-0
      let allTaxLastMoney = (discountMoney + discountLastMoney).toFixed(2)-0
      let discountMoneyNew = (allTaxLastMoney*value*0.01).toFixed(2)-0
      let discountLastMoneyNew = (allTaxLastMoney - discountMoneyNew).toFixed(2)-0
      let changeAmountNew = (discountLastMoneyNew + otherMoney).toFixed(2)-0
      this.$nextTick(() => {
        this.form.setFieldsValue({'discountMoney':discountMoneyNew,'discountLastMoney':discountLastMoneyNew,
          'changeAmount':changeAmountNew,'debt':0})
      });
    },
    //改变付款优惠
    onKeyUpDiscountMoney(e) {
      const value = e.target.value-0
      let discount = this.form.getFieldValue('discount')-0
      let discountLastMoney = this.form.getFieldValue('discountLastMoney')-0
      let otherMoney = this.form.getFieldValue('otherMoney')-0
      if(discount !== 100) {
        let allTaxLastMoney = (discountLastMoney/(1-discount/100)).toFixed(2)-0
        let discountNew = (value/allTaxLastMoney*100).toFixed(2)-0
        let discountLastMoneyNew = (allTaxLastMoney - value).toFixed(2)-0
        let changeAmountNew = (discountLastMoneyNew + otherMoney).toFixed(2)-0
        this.$nextTick(() => {
          this.form.setFieldsValue({'discount':discountNew,'discountLastMoney':discountLastMoneyNew,
            'changeAmount':changeAmountNew,'debt':0})
        });
      }
    },
    //其它费用
    onKeyUpOtherMoney(e) {
      const value = e.target.value-0
      let discountLastMoney = this.form.getFieldValue('discountLastMoney')-0
      let changeAmountNew = (discountLastMoney + value).toFixed(2)-0
      this.$nextTick(() => {
        this.form.setFieldsValue({'changeAmount':changeAmountNew, 'debt':0})
      });
    },
    //改变本次付款
    onKeyUpChangeAmount(e) {
      const value = e.target.value-0
      let discountLastMoney = this.form.getFieldValue('discountLastMoney')-0
      let otherMoney = this.form.getFieldValue('otherMoney')-0
      let debtNew = (discountLastMoney + otherMoney - value).toFixed(2)-0
      this.$nextTick(() => {
        this.form.setFieldsValue({'debt':debtNew})
      });
    }
  }
}
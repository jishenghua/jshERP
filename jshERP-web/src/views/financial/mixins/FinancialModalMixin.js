import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
import {findBySelectSup,findBySelectCus,findBySelectRetail,findStockByDepotAndBarCode,getAccount,getPersonByType,findInOutItemByParam} from '@/api/api'
import { getAction,putAction } from '@/api/manage'
import { getMpListShort, getNowFormatDateTime } from "@/utils/util"
import Vue from 'vue'

export const FinancialModalMixin = {
  data() {
    return {
      action: '',
      supList: [],
      cusList: [],
      retailList: [],
      personList: [],
      accountList: [],
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
    this.initPerson()
  },
  computed: {
    readOnly: function() {
      return this.action !== "add" && this.action !== "edit";
    }
  },
  methods: {
    addInit(amountNum) {
      this.$nextTick(() => {
        this.form.setFieldsValue({'billTime':getNowFormatDateTime()})
      });
      getAction('/sequence/buildNumber').then((res) => {
        if (res && res.code === 200) {
          this.form.setFieldsValue({'billNo':amountNum + res.data.defaultNumber})
        }
      })
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
    initPerson() {
      let that = this;
      getPersonByType({type:'财务员'}).then((res)=>{
        if(res && res.code === 200) {
          that.personList = res.data.personList;
        }
      })
    },
    initInOutItem(type) {
      let that = this;
      findInOutItemByParam({type:type}).then((res)=>{
        if(res) {
          for(let i=0; i<res.length; i++) {
            let inOutItemInfo = {};
            inOutItemInfo.value = res[i].id+'' //注意-此处value必须为字符串格式
            inOutItemInfo.text = res[i].name
            inOutItemInfo.title = res[i].name
            for(let item of that.accountTable.columns){
              if(item.key == 'inOutItemId') {
                item.options.push(inOutItemInfo)
              }
            }
          }
        }
      })
    },
    //账户-用于主表
    initAccount(){
      let that = this;
      getAccount({}).then((res)=>{
        if(res && res.code === 200) {
          that.accountList = res.data.accountList;
        }
      })
    },
    //账户-用于明细
    initDetailAccount(){
      let that = this;
      getAccount({}).then((res)=>{
        if(res && res.code === 200) {
          let list = res.data.accountList;
          for(let i=0; i<list.length; i++) {
            let accountInfo = {};
            accountInfo.value = list[i].id+'' //注意-此处value必须为字符串格式
            accountInfo.text = list[i].name
            accountInfo.title = list[i].name
            for(let item of that.accountTable.columns){
              if(item.key == 'accountId') {
                item.options.push(accountInfo)
              }
            }
          }
        }
      })
    },
    //单元值改变一个字符就触发一次
    onValueChange(event) {
      let that = this
      const { type, row, column, value, target } = event
      let param,operNumber,unitPrice,allPrice,taxRate,taxMoney,taxLastMoney
      switch(column.key) {
        case "operNumber":
          unitPrice = row.unitPrice
          taxRate = row.taxRate
          allPrice = unitPrice*value
          taxMoney =(taxRate/100)*allPrice
          taxLastMoney = allPrice + taxMoney
          target.setValues([{rowKey: row.id, values: {allPrice: allPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          let allTaxLastMoney = target.statisticsColumns.taxLastMoney
          this.$nextTick(() => {
            this.form.setFieldsValue({'discount':0,'discountMoney':0,'discountLastMoney':allTaxLastMoney,
              'changeAmount':allTaxLastMoney,'debt':0})
          });
          break;
        case "unitPrice":
          operNumber = row.operNumber
          target.setValues([{rowKey: row.id, values: {allPrice: value*operNumber}}])
          target.recalcAllStatisticsColumns()
          break;
        case "allPrice":
          operNumber = row.operNumber
          target.setValues([{rowKey: row.id, values: {unitPrice: value/operNumber}}])
          target.recalcAllStatisticsColumns()
          break;
      }
    },
    //根据仓库和条码查询库存
    getStockByDepotBarCode(row, target){
      findStockByDepotAndBarCode({ depotId: row.depotId, barCode: row.barCode }).then((res) => {
        if (res && res.code === 200) {
          target.setValues([{rowKey: row.id, values: {stock: res.data.stock}}])
          target.recalcAllStatisticsColumns()
        }
      })
    }
  }
}
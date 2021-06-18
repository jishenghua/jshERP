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
      getAction('/sequence/buildNumber').then((res) => {
        if (res && res.code === 200) {
          this.form.setFieldsValue({'billNo':amountNum + res.data.defaultNumber})
        }
      })
      this.$nextTick(() => {
        this.form.setFieldsValue({'billTime':getNowFormatDateTime()})
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
      switch(column.key) {
        case "eachAmount":
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
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
    },
    //改变优惠、本次付款、欠款的值
    autoChangePrice(target) {
      let allEachAmount = target.statisticsColumns.eachAmount-0
      this.$nextTick(() => {
        this.form.setFieldsValue({'changeAmount':allEachAmount})
      });
    }
  }
}
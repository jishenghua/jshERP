import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
import {findBySelectSup,findBySelectCus,findBySelectRetail,findBySelectOrgan,findStockByDepotAndBarCode,getAccount,getPersonByType,findInOutItemByParam} from '@/api/api'
import { getAction,putAction } from '@/api/manage'
import { getMpListShort, getNowFormatDateTime } from "@/utils/util"
import { USER_INFO } from "@/store/mutation-types"
import Vue from 'vue'

export const FinancialModalMixin = {
  data() {
    return {
      action: '',
      supList: [],
      cusList: [],
      retailList: [],
      organList: [],
      personList: [],
      accountList: [],
      billStatus: '0',
      isCanCheck: true,
      isTenant: false,
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
    let userInfo = Vue.ls.get(USER_INFO)
    this.isTenant = userInfo.id === userInfo.tenantId? true:false
    let realScreenWidth = window.screen.width * window.devicePixelRatio
    this.width = realScreenWidth<1600?'1300px':'1600px'
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
        this.form.setFieldsValue({'billTime':getNowFormatDateTime(), 'totalPrice': 0, 'discountMoney': 0, 'changeAmount': 0})
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
    initOrgan() {
      let that = this;
      findBySelectOrgan({}).then((res)=>{
        if(res) {
          that.organList = res;
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
          for(let item of that.accountTable.columns){
            if(item.key == 'inOutItemId') {
              item.options = []
              for(let i=0; i<res.length; i++) {
                let inOutItemInfo = {};
                inOutItemInfo.value = res[i].id + '' //注意-此处value必须为字符串格式
                inOutItemInfo.text = res[i].name
                inOutItemInfo.title = res[i].name
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
          for(let item of that.accountTable.columns){
            if(item.key == 'accountId') {
              item.options = []
              for(let i=0; i<list.length; i++) {
                let accountInfo = {};
                accountInfo.value = list[i].id + '' //注意-此处value必须为字符串格式
                accountInfo.text = list[i].name
                accountInfo.title = list[i].name
                item.options.push(accountInfo)
              }
            }
          }
        }
      })
    },
    addSupplier() {
      this.$refs.vendorModalForm.add();
      this.$refs.vendorModalForm.title = "新增";
      this.$refs.vendorModalForm.disableSubmit = false;
    },
    addCustomer() {
      this.$refs.customerModalForm.add();
      this.$refs.customerModalForm.title = "新增客户（提醒：如果找不到新添加的客户，请到用户管理检查是否分配了该客户权限）";
      this.$refs.customerModalForm.disableSubmit = false;
    },
    addAccount() {
      this.$refs.accountModalForm.add();
      this.$refs.accountModalForm.title = "新增结算账户";
      this.$refs.accountModalForm.disableSubmit = false;
    },
    addPerson() {
      this.$refs.personModalForm.add();
      this.$refs.personModalForm.title = "新增经手人";
      this.$refs.personModalForm.disableSubmit = false;
    },
    vendorModalFormOk() {
      this.initSupplier()
    },
    customerModalFormOk() {
      this.initCustomer()
    },
    accountModalFormOk() {
      this.initAccount()
    },
    personModalFormOk() {
      this.initPerson()
    },
    //单元值改变一个字符就触发一次
    onValueChange(event) {
      let that = this
      const { type, row, column, value, target } = event
      switch(column.key) {
        case "eachAmount":
          target.recalcAllStatisticsColumns()
          that.autoChangeAmount(target)
          break;
      }
    },
    //改变本次欠款的值
    autoChangeAmount(target) {
      let allEachAmount = target.statisticsColumns.eachAmount-0
      let discountMoney = this.form.getFieldValue('discountMoney')-0
      if(!discountMoney) {
        discountMoney = 0
      }
      let changeAmount = (allEachAmount-discountMoney).toFixed(2)
      this.$nextTick(() => {
        this.form.setFieldsValue({'totalPrice':allEachAmount, 'changeAmount':changeAmount})
      });
    },
    //改变优惠金额
    onKeyUpDiscountMoney(e) {
      const value = e.target.value-0
      let totalPrice = this.form.getFieldValue('totalPrice')-0
      let changeAmount = (totalPrice-value).toFixed(2)
      this.$nextTick(() => {
        this.form.setFieldsValue({'changeAmount':changeAmount})
      });
    },
    //选择欠款单据
    debtBillListOk(selectBillRows) {
      if(selectBillRows && selectBillRows.length>0) {
        this.requestSubTableDataEx(selectBillRows, this.accountTable);
      }
    },
    /** 查询某个tab的数据,给明细里面的金额赋值 */
    requestSubTableDataEx(selectBillRows, tab, success) {
      tab.loading = true
      let listEx = []
      let changeAmount = 0
      for(let i=0; i<selectBillRows.length; i++){
        let info = selectBillRows[i]
        info.billNumber = info.number
        info.needDebt = (info.discountLastMoney + info.otherMoney - info.changeAmount).toFixed(2)
        info.eachAmount =  (info.discountLastMoney + info.otherMoney - info.changeAmount - info.finishDebt).toFixed(2);
        if(info.eachAmount != 0) {
          changeAmount += info.eachAmount-0
          listEx.push(info)
        }
      }
      tab.dataSource = listEx
      this.$nextTick(() => {
        this.form.setFieldsValue({'totalPrice':changeAmount, 'changeAmount':changeAmount})
      });
      typeof success === 'function' ? success(res) : ''
      tab.loading = false
    },
    //保存并审核
    handleOkAndCheck() {
      this.billStatus = '1'
      this.handleOk()
    },
  }
}
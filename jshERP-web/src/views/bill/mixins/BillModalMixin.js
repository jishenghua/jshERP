import { FormTypes, getListData } from '@/utils/JEditableTableUtil'
import {findBySelectSup,findBySelectCus,findBySelectRetail,getMaterialByBarCode,findStockByDepotAndBarCode,getAccount,
  getPersonByNumType, getBatchNumberList} from '@/api/api'
import { getAction,putAction } from '@/api/manage'
import { getMpListShort, getNowFormatDateTime } from "@/utils/util"
import { USER_INFO } from "@/store/mutation-types"
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
      billUnitPirce: '',
      scanBarCode: '',
      scanStatus: true,
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
      this.accountIdList = []
      this.accountMoneyList = []
      this.manyAccountBtnStatus = false
    },
    copyAddInit(amountNum) {
      getAction('/sequence/buildNumber').then((res) => {
        if (res && res.code === 200) {
          this.form.setFieldsValue({'number':amountNum + res.data.defaultNumber})
        }
      })
      this.$nextTick(() => {
        this.form.setFieldsValue({'operTime':getNowFormatDateTime()})
      })
    },
    /** 查询某个tab的数据 */
    requestSubTableData(url, params, tab, success) {
      tab.loading = true
      getAction(url, params).then(res => {
        if(res && res.code === 200){
          tab.dataSource = res.data.rows
          for(let i=0; i<tab.dataSource.length; i++){
            let info = tab.dataSource[i]
            this.changeColumnShow(info)
          }
          typeof success === 'function' ? success(res) : ''
        }
      }).finally(() => {
        tab.loading = false
      })
    },
    //改变字段的状态，1-显示 0-隐藏
    changeFormTypes(columns, key, type) {
      for(let i=0; i<columns.length; i++){
        if(columns[i].key === key) {
          if(type){
            if(key === 'snList' || key === 'batchNumber') {
              if(this.prefixNo === 'LSCK' || this.prefixNo === 'CGTH'  || this.prefixNo === 'XSCK' || this.prefixNo === 'QTCK') {
                columns[i].type = FormTypes.popupJsh //显示
              } else {
                columns[i].type = FormTypes.input //显示
              }
            } else if(key === 'expirationDate') {
              if(this.prefixNo === 'LSTH' || this.prefixNo === 'CGRK' || this.prefixNo === 'XSTH' || this.prefixNo === 'QTRK') {
                columns[i].type = FormTypes.date //显示
              } else {
                columns[i].type = FormTypes.normal //显示
              }
            } else {
              columns[i].type = FormTypes.normal //显示
            }
          } else {
            columns[i].type = FormTypes.hidden //隐藏
          }
        }
      }
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
      getAction('/depot/findDepotByCurrentUser').then((res) => {
        if(res.code === 200){
          let arr = res.data
          for(let item of that.materialTable.columns){
            if(item.key == 'depotId' || item.key == 'anotherDepotId') {
              item.options = []
              for(let i=0; i<arr.length; i++) {
                let depotInfo = {};
                depotInfo.value = arr[i].id + '' //注意-此处value必须为字符串格式
                depotInfo.text = arr[i].depotName
                depotInfo.title = arr[i].depotName
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
    manyAccountModalFormOk(idList, moneyList, allPrice) {
      this.accountIdList = idList
      this.accountMoneyList = moneyList
      let discountLastMoney = this.form.getFieldValue('discountLastMoney')-0
      let otherMoney = this.form.getFieldValue('otherMoney')-0
      let debt = (discountLastMoney + otherMoney - allPrice).toFixed(2)
      this.$nextTick(() => {
        this.form.setFieldsValue({'changeAmount':allPrice, 'debt':debt})
      });
    },
    addSupplier() {
      this.$refs.vendorModalForm.add();
      this.$refs.vendorModalForm.title = "新增供应商";
      this.$refs.vendorModalForm.disableSubmit = false;
    },
    addCustomer() {
      this.$refs.customerModalForm.add();
      this.$refs.customerModalForm.title = "新增客户（提醒：如果找不到新添加的客户，请到用户管理检查是否分配了该客户权限）";
      this.$refs.customerModalForm.disableSubmit = false;
    },
    addMember() {
      this.$refs.memberModalForm.add();
      this.$refs.memberModalForm.title = "新增会员";
      this.$refs.memberModalForm.disableSubmit = false;
    },
    handleBatchSetDepot() {
      this.$refs.batchSetDepotModalForm.add();
      this.$refs.batchSetDepotModalForm.title = "批量设置仓库";
      this.$refs.batchSetDepotModalForm.disableSubmit = false;
    },
    addDepot() {
      this.$refs.depotModalForm.add();
      this.$refs.depotModalForm.title = "新增仓库";
      this.$refs.depotModalForm.disableSubmit = false;
    },
    addAccount() {
      this.$refs.accountModalForm.add();
      this.$refs.accountModalForm.title = "新增结算账户";
      this.$refs.accountModalForm.disableSubmit = false;
    },
    vendorModalFormOk() {
      this.initSupplier()
    },
    customerModalFormOk() {
      this.initCustomer()
    },
    memberModalFormOk() {
      this.initRetail()
    },
    batchSetDepotModalFormOk(depotId) {
      this.getAllTable().then(tables => {
        return getListData(this.form, tables)
      }).then(allValues => {
        //获取单据明细列表信息
        let detailArr = allValues.tablesValue[0].values
        let barCodes = ''
        for(let detail of detailArr){
          barCodes += detail.barCode + ','
        }
        if(barCodes) {
          barCodes = barCodes.substring(0, barCodes.length-1)
        }
        let param = {
          barCode: barCodes,
          depotId: depotId,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList')),  //扩展属性
          prefixNo: this.prefixNo
        }
        getMaterialByBarCode(param).then((res) => {
          if (res && res.code === 200) {
            let mList = res.data
            //构造新的列表数组，用于存放单据明细信息
            let newDetailArr = []
            if(mList && mList.length) {
              for (let i = 0; i < detailArr.length; i++) {
                let item = detailArr[i]
                item.depotId = depotId
                item.stock = mList[i].stock
                newDetailArr.push(item)
              }
            } else {
              for (let i = 0; i < detailArr.length; i++) {
                let item = detailArr[i]
                item.depotId = depotId
                newDetailArr.push(item)
              }
            }
            this.materialTable.dataSource = newDetailArr
          }
        })
      })
    },
    depotModalFormOk() {
      this.initDepot()
    },
    accountModalFormOk() {
      this.initAccount()
    },
    onAdded(event) {
      const { row, target } = event
      getAction('/depot/findDepotByCurrentUser').then((res) => {
        if (res.code === 200) {
          let arr = res.data
          for (let i = 0; i < arr.length; i++) {
            if(arr[i].isDefault){
              target.setValues([{rowKey: row.id, values: {depotId: arr[i].id+''}}])
            }
          }
        }
      })
    },
    //单元值改变一个字符就触发一次
    onValueChange(event) {
      let that = this
      const { type, row, column, value, target } = event
      let param,snList,batchNumber,operNumber,unitPrice,allPrice,taxRate,taxMoney,taxLastMoney
      switch(column.key) {
        case "depotId":
          if(row.barCode){
            that.getStockByDepotBarCode(row, target)
          }
          break;
        case "barCode":
          param = {
            barCode: value,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList')),  //扩展属性
            prefixNo: this.prefixNo
          }
          getMaterialByBarCode(param).then((res) => {
            if (res && res.code === 200) {
              let mList = res.data
              if (value.indexOf(',') > -1) {
                //多个条码
                this.$refs.materialDataTable.getValues((error, values) => {
                  values.pop()  //移除最后一行数据
                  let mArr = values
                  for (let i = 0; i < mList.length; i++) {
                    let mInfo = mList[i]
                    this.changeColumnShow(mInfo)
                    let mObj = this.parseInfoToObj(mInfo)
                    mObj.depotId = mInfo.depotId
                    mObj.stock = mInfo.stock
                    mArr.push(mObj)
                  }
                  let taxLastMoneyTotal = 0
                  for (let j = 0; j < mArr.length; j++) {
                    taxLastMoneyTotal += mArr[j].taxLastMoney-0
                    //组合和拆分单据给商品类型进行重新赋值
                    if(j===0) {
                      mArr[0].mType = '组合件'
                    } else {
                      mArr[j].mType = '普通子件'
                    }
                  }
                  this.materialTable.dataSource = mArr
                  target.statisticsColumns.taxLastMoney = taxLastMoneyTotal
                  that.autoChangePrice(target)
                })
              } else {
                //单个条码
                findStockByDepotAndBarCode({ depotId: row.depotId, barCode: row.barCode }).then((res) => {
                  if (res && res.code === 200) {
                    let mArr = []
                    let mInfo = mList[0]
                    this.changeColumnShow(mInfo)
                    let mInfoEx = this.parseInfoToObj(mInfo)
                    mInfoEx.stock = res.data.stock
                    let mObj = {
                      rowKey: row.id,
                      values: mInfoEx
                    }
                    mArr.push(mObj)
                    target.setValues(mArr);
                    target.recalcAllStatisticsColumns()
                    that.autoChangePrice(target)
                    target.autoSelectBySpecialKey('operNumber')
                  }
                })
              }
            }
          });
          break;
        case "snList":
          snList = value
          if(snList) {
            let snArr = snList.split(',')
            operNumber = snArr.length
            taxRate = row.taxRate-0 //税率
            unitPrice = row.unitPrice-0 //单价
            allPrice = (unitPrice*operNumber).toFixed(2)-0
            taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
            taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
            target.setValues([{rowKey: row.id, values: {operNumber: operNumber, allPrice: allPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
            target.recalcAllStatisticsColumns()
            that.autoChangePrice(target)
          }
          break;
        case "batchNumber":
          batchNumber = value
          getBatchNumberList({name:'', depotId: row.depotId, barCode: row.barCode, batchNumber: batchNumber}).then((res) => {
            if (res && res.code === 200) {
              if(res.data && res.data.rows) {
                let info = res.data.rows[0]
                operNumber = info.totalNum
                taxRate = row.taxRate-0 //税率
                unitPrice = row.unitPrice-0 //单价
                allPrice = (unitPrice*operNumber).toFixed(2)-0
                taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
                taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
                target.setValues([{rowKey: row.id, values: {expirationDate: info.expirationDateStr, operNumber: operNumber,
                    allPrice: allPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
                target.recalcAllStatisticsColumns()
                that.autoChangePrice(target)
              }
            }
          })
          break;
        case "operNumber":
          operNumber = value-0
          taxRate = row.taxRate-0 //税率
          unitPrice = row.unitPrice-0 //单价
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
          allPrice = (unitPrice*operNumber).toFixed(2)-0
          taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
          taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {allPrice: allPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
        case "allPrice":
          operNumber = row.operNumber-0 //数量
          taxRate = row.taxRate-0 //税率
          allPrice = value-0
          unitPrice = (allPrice/operNumber).toFixed(2)-0 //单价
          taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
          taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {unitPrice: unitPrice, taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
        case "taxRate":
          operNumber = row.operNumber-0 //数量
          allPrice = row.allPrice-0
          unitPrice = row.unitPrice-0
          taxRate = value-0 //税率
          taxMoney =((taxRate*0.01)*allPrice).toFixed(2)-0
          taxLastMoney = (allPrice + taxMoney).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {taxMoney: taxMoney, taxLastMoney: taxLastMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
        case "taxLastMoney":
          operNumber = row.operNumber-0 //数量
          taxLastMoney = value-0
          taxRate = row.taxRate-0 //税率
          unitPrice = (taxLastMoney/operNumber/(1+taxRate*0.01)).toFixed(2)-0
          allPrice = (unitPrice*operNumber).toFixed(2)-0
          taxMoney =(taxLastMoney-allPrice).toFixed(2)-0
          target.setValues([{rowKey: row.id, values: {unitPrice: unitPrice, allPrice: allPrice, taxMoney: taxMoney}}])
          target.recalcAllStatisticsColumns()
          that.autoChangePrice(target)
          break;
      }
    },
    //转为商品对象
    parseInfoToObj(mInfo) {
      return {
        barCode: mInfo.mBarCode,
        name: mInfo.name,
        standard: mInfo.standard,
        model: mInfo.model,
        color: mInfo.color,
        materialOther: mInfo.materialOther,
        unit: mInfo.commodityUnit,
        sku: mInfo.sku,
        operNumber: 1,
        unitPrice: mInfo.billPrice,
        allPrice: mInfo.billPrice,
        taxRate: 0,
        taxMoney: 0,
        taxLastMoney: mInfo.billPrice
      }
    },
    //使得型号、颜色、扩展信息、sku等为隐藏
    changeColumnHide() {
      this.changeFormTypes(this.materialTable.columns, 'model', 0)
      this.changeFormTypes(this.materialTable.columns, 'color', 0)
      this.changeFormTypes(this.materialTable.columns, 'materialOther', 0)
      this.changeFormTypes(this.materialTable.columns, 'sku', 0)
    },
    //使得sku、序列号、批号、到期日等为显示
    changeColumnShow(info) {
      if(info.model) {
        this.changeFormTypes(this.materialTable.columns, 'model', 1)
      }
      if(info.color) {
        this.changeFormTypes(this.materialTable.columns, 'color', 1)
      }
      if(info.materialOther) {
        this.changeFormTypes(this.materialTable.columns, 'materialOther', 1)
      }
      if(info.sku) {
        this.changeFormTypes(this.materialTable.columns, 'sku', 1)
      }
      if(info.enableSerialNumber === "1") {
        this.changeFormTypes(this.materialTable.columns, 'snList', 1)
      }
      if(info.enableBatchNumber === "1") {
        this.changeFormTypes(this.materialTable.columns, 'batchNumber', 1)
        this.changeFormTypes(this.materialTable.columns, 'expirationDate', 1)
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
      let allTaxLastMoney = target.statisticsColumns.taxLastMoney-0
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
    },
    scanEnter() {
      this.scanStatus = false
      this.$nextTick(() => {
        this.$refs.scanBarCode.focus()
      })
    },
    //扫码之后回车
    scanPressEnter() {
      if(this.scanBarCode) {
        this.getAllTable().then(tables => {
          return getListData(this.form, tables)
        }).then(allValues => {
          let param = {
            barCode: this.scanBarCode,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList')),  //扩展属性
            prefixNo: this.prefixNo
          }
          getMaterialByBarCode(param).then((res) => {
            if (res && res.code === 200) {
              let hasFinished = false
              let allLastMoney = 0
              let allTaxLastMoney = 0
              //获取单据明细列表信息
              let detailArr = allValues.tablesValue[0].values
              //构造新的列表数组，用于存放单据明细信息
              let newDetailArr = []
              for(let detail of detailArr){
                if(detail.barCode) {
                  //如果条码重复，就在给原来的数量加1
                  if(detail.barCode === this.scanBarCode) {
                    detail.operNumber = (detail.operNumber-0)+1
                    //由于改变了商品数量，需要同时更新相关金额和价税合计
                    let taxRate = detail.taxRate-0 //税率
                    let unitPrice = detail.unitPrice-0 //单价
                    detail.allPrice = (unitPrice*detail.operNumber).toFixed(2)-0
                    detail.taxMoney = ((taxRate*0.01)*detail.allPrice).toFixed(2)-0
                    detail.taxLastMoney = (detail.allPrice + detail.taxMoney).toFixed(2)-0
                    hasFinished = true
                  }
                  newDetailArr.push(detail)
                }
              }
              if(!hasFinished) {
                //将扫码的条码对应的商品加入列表
                let item = {}
                item.barCode = this.scanBarCode
                let mList = res.data
                if(mList && mList.length>0) {
                  let mInfo = mList[0]
                  this.changeColumnShow(mInfo)
                  item.depotId = mInfo.depotId
                  item.name = mInfo.name
                  item.standard = mInfo.standard
                  item.model = mInfo.model
                  item.color = mInfo.color
                  item.materialOther = mInfo.materialOther
                  item.stock = mInfo.stock
                  item.unit = mInfo.commodityUnit
                  item.sku = mInfo.sku
                  item.operNumber = 1
                  item.unitPrice = mInfo.billPrice
                  item.allPrice = mInfo.billPrice
                  item.taxRate = 0
                  item.taxMoney = 0
                  item.taxLastMoney = mInfo.billPrice
                  newDetailArr.push(item)
                } else {
                  this.$message.warning('抱歉，此条码不存在商品信息！');
                }
              }
              //组合和拆分单据给商品类型进行重新赋值
              for(let i=0; i< newDetailArr.length; i++) {
                if(i===0) {
                  newDetailArr[0].mType = '组合件'
                } else {
                  newDetailArr[i].mType = '普通子件'
                }
              }
              this.materialTable.dataSource = newDetailArr
              //更新优惠后金额、本次付款等信息
              for(let newDetail of newDetailArr){
                allLastMoney = allLastMoney + (newDetail.allPrice-0)
                allTaxLastMoney = allTaxLastMoney + (newDetail.taxLastMoney-0)
              }
              let discount = this.form.getFieldValue('discount')-0
              let otherMoney = this.form.getFieldValue('otherMoney')-0
              let discountMoney = (discount*0.01*allTaxLastMoney).toFixed(2)-0
              let discountLastMoney = (allTaxLastMoney-discountMoney).toFixed(2)-0
              let changeAmountNew = (discountLastMoney + otherMoney).toFixed(2)-0
              if(this.prefixNo === 'LSCK' || this.prefixNo === 'LSTH') {
                this.$nextTick(() => {
                  this.form.setFieldsValue({'changeAmount':allLastMoney,'getAmount':allLastMoney,'backAmount':0})
                });
              } else {
                this.$nextTick(() => {
                  this.form.setFieldsValue({'discount':discount,'discountMoney':discountMoney,'discountLastMoney':discountLastMoney,
                    'changeAmount':changeAmountNew,'debt':0})
                });
              }
              //置空扫码的内容
              this.scanBarCode = ''
              this.$refs.scanBarCode.focus()
            }
          })
        })
      }
    },
    stopScan() {
      this.scanStatus = true
      this.scanBarCode = ''
    }
  }
}
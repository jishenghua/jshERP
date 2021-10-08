import { FormTypes, getListData } from '@/utils/JEditableTableUtil'
import {findBySelectSup,findBySelectCus,findBySelectRetail,getMaterialByBarCode,findStockByDepotAndBarCode,getAccount,
  getPersonByNumType, getBatchNumberList} from '@/api/api'
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
      billUnitPirce: '',
      scanBarCode: '',
      scanStatus: true,
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
            if(tab.dataSource[i].snList) {
              this.changeFormTypes(this.materialTable.columns, 'snList', 1)
            }
            if(tab.dataSource[i].batchNumber) {
              this.changeFormTypes(this.materialTable.columns, 'batchNumber', 1)
            }
            if(tab.dataSource[i].expirationDate) {
              this.changeFormTypes(this.materialTable.columns, 'expirationDate', 1)
            }
            if(tab.dataSource[i].sku) {
              this.changeFormTypes(this.materialTable.columns, 'sku', 1)
            }
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
              if(this.prefixNo === 'XSCK') {
                columns[i].type = FormTypes.popupJsh //显示
              } else {
                columns[i].type = FormTypes.input //显示
              }
            } else if(key === 'expirationDate') {
              if(this.prefixNo === 'CGRK' || this.prefixNo === 'XSTH' || this.prefixNo === 'CGTH') {
                columns[i].type = FormTypes.date //显示
              } else {
                columns[i].type = FormTypes.input //显示
              }
            } else {
              columns[i].type = FormTypes.input //显示
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
      let param,snList,batchNumber,operNumber,unitPrice,taxUnitPrice,allPrice,taxRate,taxMoney,taxLastMoney
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
                    if(mInfo.sku) {
                      this.changeFormTypes(this.materialTable.columns, 'sku', 1)
                    }
                    if(mInfo.enableSerialNumber === "1") {
                      this.changeFormTypes(this.materialTable.columns, 'snList', 1)
                    }
                    if(mInfo.enableBatchNumber === "1") {
                      this.changeFormTypes(this.materialTable.columns, 'batchNumber', 1)
                      this.changeFormTypes(this.materialTable.columns, 'expirationDate', 1)
                    }
                    let mObj = this.parseInfoToObj(mInfo)
                    mObj.depotId = mInfo.depotId
                    mObj.stock = mInfo.stock
                    mArr.push(mObj)
                  }
                  let taxLastMoneyTotal = 0
                  for (let j = 0; j < mArr.length; j++) {
                    taxLastMoneyTotal += mArr[j].taxLastMoney-0
                  }
                  this.materialTable.dataSource = mArr
                  target.statisticsColumns.taxLastMoney = taxLastMoneyTotal
                  that.autoChangePrice(target)
                })
              } else {
                //单个条码
                let mArr = []
                for (let i = 0; i < mList.length; i++) {
                  let mInfo = mList[i]
                  if(mInfo.sku) {
                    this.changeFormTypes(this.materialTable.columns, 'sku', 1)
                  }
                  if(mInfo.enableSerialNumber === "1") {
                    this.changeFormTypes(this.materialTable.columns, 'snList', 1)
                  }
                  if(mInfo.enableBatchNumber === "1") {
                    this.changeFormTypes(this.materialTable.columns, 'batchNumber', 1)
                    this.changeFormTypes(this.materialTable.columns, 'expirationDate', 1)
                  }
                  let mObj = {
                    rowKey: row.id,
                    values: this.parseInfoToObj(mInfo)
                  }
                  mArr.push(mObj)
                }
                target.setValues(mArr);
                that.getStockByDepotBarCode(row, target)
                target.recalcAllStatisticsColumns()
                that.autoChangePrice(target)
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
            taxUnitPrice = row.taxUnitPrice-0
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
                taxUnitPrice = row.taxUnitPrice-0
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
    //转为商品对象
    parseInfoToObj(mInfo) {
      return {
        barCode: mInfo.mBarCode,
        name: mInfo.name,
        standard: mInfo.standard,
        model: mInfo.model,
        materialOther: mInfo.materialOther,
        unit: mInfo.commodityUnit,
        sku: mInfo.sku,
        operNumber: 1,
        unitPrice: mInfo.billPrice,
        taxUnitPrice: mInfo.billPrice,
        allPrice: mInfo.billPrice,
        taxRate: 0,
        taxMoney: 0,
        taxLastMoney: mInfo.billPrice
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
                  if(mInfo.sku) {
                    this.changeFormTypes(this.materialTable.columns, 'sku', 1)
                  }
                  if(mInfo.enableSerialNumber === "1") {
                    this.changeFormTypes(this.materialTable.columns, 'snList', 1)
                  }
                  if(mInfo.enableBatchNumber === "1") {
                    this.changeFormTypes(this.materialTable.columns, 'batchNumber', 1)
                    this.changeFormTypes(this.materialTable.columns, 'expirationDate', 1)
                  }
                  item.depotId = mInfo.depotId
                  item.name = mInfo.name
                  item.standard = mInfo.standard
                  item.model = mInfo.model
                  item.materialOther = mInfo.materialOther
                  item.stock = mInfo.stock
                  item.unit = mInfo.commodityUnit
                  item.sku = mInfo.sku
                  item.operNumber = 1
                  item.unitPrice = mInfo.billPrice
                  item.taxUnitPrice = mInfo.billPrice
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
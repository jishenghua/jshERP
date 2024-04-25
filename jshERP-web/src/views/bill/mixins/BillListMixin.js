import Vue from 'vue'
import {getAction } from '@/api/manage'
import { FormTypes } from '@/utils/JEditableTableUtil'
import {findBillDetailByNumber, findBySelectSup, findBySelectCus, findBySelectRetail, getUserList, getAccount, waitBillCount,
  getCurrentSystemConfig, getPlatformConfigByKey} from '@/api/api'
import { getCheckFlag, getFormatDate, getPrevMonthFormatDate } from '@/utils/util'
import moment from 'moment'

export const BillListMixin = {
  data () {
    return {
      /* 原始审核是否开启 */
      checkFlag: true,
      /* 单据Excel是否开启 */
      isShowExcel: false,
      waitTotal: 0,
      dateFormat: 'YYYY-MM-DD',
      billExcelUrl: '',
      supList: [],
      cusList: [],
      retailList: [],
      userList: [],
      accountList: [],
      // 实际索引
      settingDataIndex:[],
      // 实际列
      columns:[],
      queryParam: {
        beginTime: getPrevMonthFormatDate(3),
        endTime: getFormatDate(),
        createTimeRange: [moment(getPrevMonthFormatDate(3)), moment(getFormatDate())]
      }
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },

    isBatchDelEnabled: function () {
      for (let i = 0; i < this.selectedRowKeys.length; i++) {
        if (!this.selectionRows[i].actionsEnabled.delete) {
          return false;
        }
      }
      return true;
    }
  },
  created() {
    this.initColumnsSetting()
    this.isShowExcel = Vue.ls.get('isShowExcel');
  },
  methods: {
    myHandleAdd() {
      this.$refs.modalForm.action = "add";
      if(this.btnEnableList.indexOf(2)===-1) {
        this.$refs.modalForm.isCanCheck = false
      }
      this.handleAdd();
    },
    myHandleCopyAdd(record) {
      this.$refs.modalForm.action = "copyAdd";
      if(this.btnEnableList.indexOf(2)===-1) {
        this.$refs.modalForm.isCanCheck = false
      }
      //复制单据的时候需要移除关联单据的相关信息
      record.linkNumber = ''
      record.billType = ''
      record.deposit = ''
      this.$refs.modalForm.edit(record);
      this.$refs.modalForm.title = "复制新增";
      this.$refs.modalForm.disableSubmit = false;
      //开启明细的编辑模式
      this.$refs.modalForm.rowCanEdit = true
      this.$refs.modalForm.materialTable.columns[1].type = FormTypes.popupJsh
    },
    myHandleEdit(record) {
      if(record.status === '0') {
        this.$refs.modalForm.action = "edit";
        if(this.btnEnableList.indexOf(2)===-1) {
          this.$refs.modalForm.isCanCheck = false
        }
        //查询单条单据信息
        findBillDetailByNumber({ number: record.number }).then((res) => {
          if (res && res.code === 200) {
            let item = res.data
            this.handleEdit(item)
          }
        })
      } else {
        this.$message.warning("抱歉，只有未审核的单据才能编辑，请先进行反审核！")
      }
    },
    myHandleDelete(record) {
      if(record.status === '0') {
        this.handleDelete(record.id)
      } else {
        this.$message.warning("抱歉，只有未审核的单据才能删除，请先进行反审核！")
      }
    },
    myHandleDetail(record, type, prefixNo) {
      if(this.btnEnableList.indexOf(7)===-1) {
        this.$refs.modalDetail.isCanBackCheck = false
      }
      this.handleDetail(record, type, prefixNo);
    },
    handleApprove(record) {
      this.$refs.modalForm.action = "approve";
      this.$refs.modalForm.edit(record);
      this.$refs.modalForm.title = "审核";
    },
    searchReset() {
      this.queryParam = {
        type: this.queryParam.type,
        subType: this.queryParam.subType,
        beginTime: getPrevMonthFormatDate(3),
        endTime: getFormatDate(),
        createTimeRange: [moment(getPrevMonthFormatDate(3)), moment(getFormatDate())]
      }
      this.loadData(1);
    },
    onDateChange: function (value, dateString) {
      this.queryParam.beginTime=dateString[0];
      this.queryParam.endTime=dateString[1];
      this.queryParam.createTimeRange = [moment(dateString[0]), moment(dateString[1])]
    },
    onDateOk(value) {
      console.log(value);
    },
    initSystemConfig() {
      getCurrentSystemConfig().then((res) => {
        if(res.code === 200 && res.data){
          let multiBillType = res.data.multiBillType
          let multiLevelApprovalFlag = res.data.multiLevelApprovalFlag
          this.checkFlag = getCheckFlag(multiBillType, multiLevelApprovalFlag, this.prefixNo)
          this.inOutManageFlag = res.data.inOutManageFlag==='1'?true:false
        }
      })
      getPlatformConfigByKey({ "platformKey": "bill_excel_url" }).then((res) => {
        if (res && res.code === 200) {
          if(res.data.platformValue) {
            this.billExcelUrl = res.data.platformValue
          }
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
    getDepotData() {
      getAction('/depot/findDepotByCurrentUser').then((res)=>{
        if(res.code === 200){
          this.depotList = res.data;
        }else{
          this.$message.info(res.data);
        }
      })
    },
    initUser() {
      getUserList({}).then((res)=>{
        if(res) {
          this.userList = res;
        }
      });
    },
    initAccount() {
      getAccount({}).then((res)=>{
        if(res && res.code === 200) {
          this.accountList = res.data.accountList
        }
      })
    },
    initWaitBillCount(type, subType, status) {
      waitBillCount({search: {
          type: type, subType: subType, status: status
        }}).then((res)=>{
        if(res && res.code === 200) {
          this.waitTotal = res.data.total
        }
      })
    },
    //加载初始化列
    initColumnsSetting(){
      let columnsStr = Vue.ls.get(this.prefixNo)
      if(columnsStr && columnsStr.indexOf(',')>-1) {
        this.settingDataIndex = columnsStr.split(',')
      } else {
        this.settingDataIndex = this.defDataIndex
      }
      this.columns = this.defColumns.filter(item => {
        return this.settingDataIndex.includes(item.dataIndex)
      })
    },
    //列设置更改事件
    onColChange (checkedValues) {
      this.columns = this.defColumns.filter(item => {
        return checkedValues.includes(item.dataIndex)
      })
      let columnsStr = checkedValues.join()
      Vue.ls.set(this.prefixNo, columnsStr)
    },
    //恢复默认
    handleRestDefault() {
      Vue.ls.remove(this.prefixNo)
      this.initColumnsSetting()
    },
    //导出单据
    handleExport() {
      let search = this.getQueryParams().search
      this.$refs.billExcelIframe.show(this.model, this.billExcelUrl + '?search=' + search + '&type=1', 150)
      this.$refs.billExcelIframe.title = "确认导出"
    }
  }
}
import Vue from 'vue'
import { getAction, postAction } from '@/api/manage'
import { FormTypes } from '@/utils/JEditableTableUtil'
import { findBillDetailByNumber, findBySelectSup, findBySelectCus, findBySelectRetail, getUserList, getAccount,
  waitBillCount, getCurrentSystemConfig, getPlatformConfigByKey, getPersonByNumType } from '@/api/api'
import { getCheckFlag, getFormatDate, getMpListShort, getPrevMonthFormatDate } from '@/utils/util'
import moment from 'moment'
import pick from 'lodash.pick'

export const BillListMixin = {
  data () {
    return {
      /* 原始审核是否开启 */
      checkFlag: true,
      /* 单据Excel是否开启 */
      isShowExcel: false,
      //以销定购的场景开关
      purchaseBySaleFlag: false,
      setTimeFlag: null,
      waitTotal: 0,
      dateFormat: 'YYYY-MM-DD',
      billExcelUrl: '',
      defaultDepotId: '',
      supList: [],
      cusList: [],
      retailList: [],
      salesManList: [],
      userList: [],
      accountList: [],
      // 实际索引
      settingDataIndex: [],
      // 存储展开的行key
      expandedRowKeys: [],
      // 实际列
      columns:[],
      // 明细表头
      detailColumns:[],
      // 列定义
      defDetailColumns: [],
      retailOutColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      retailBackColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      purchaseApplyColumns: [
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '单位', dataIndex: 'unit'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '已采购', dataIndex: 'finishNumber'},
        { title: '备注', dataIndex: 'remark'}
      ],
      purchaseOrderColumns: [
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '已采购', dataIndex: 'finishNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '税率(%)', dataIndex: 'taxRate'},
        { title: '税额', dataIndex: 'taxMoney'},
        { title: '价税合计', dataIndex: 'taxLastMoney'},
        { title: '备注', dataIndex: 'remark'}
      ],
      purchaseInColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '已入库', dataIndex: 'finishNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '税率(%)', dataIndex: 'taxRate'},
        { title: '税额', dataIndex: 'taxMoney'},
        { title: '价税合计', dataIndex: 'taxLastMoney'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      purchaseBackColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '已出库', dataIndex: 'finishNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '税率(%)', dataIndex: 'taxRate'},
        { title: '税额', dataIndex: 'taxMoney'},
        { title: '价税合计', dataIndex: 'taxLastMoney'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      saleOrderColumns: [
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '已销售', dataIndex: 'finishNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '税率(%)', dataIndex: 'taxRate'},
        { title: '税额', dataIndex: 'taxMoney'},
        { title: '价税合计', dataIndex: 'taxLastMoney'},
        { title: '备注', dataIndex: 'remark'}
      ],
      saleOutColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '已出库', dataIndex: 'finishNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '税率(%)', dataIndex: 'taxRate'},
        { title: '税额', dataIndex: 'taxMoney'},
        { title: '价税合计', dataIndex: 'taxLastMoney'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      saleBackColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '已入库', dataIndex: 'finishNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '税率(%)', dataIndex: 'taxRate'},
        { title: '税额', dataIndex: 'taxMoney'},
        { title: '价税合计', dataIndex: 'taxLastMoney'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      otherInColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      otherOutColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '序列号', dataIndex: 'snList', width:300},
        { title: '批号', dataIndex: 'batchNumber'},
        { title: '有效期', dataIndex: 'expirationDate'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      allocationOutColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '调入仓库', dataIndex: 'anotherDepotName'},
        { title: '单位', dataIndex: 'unit'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '重量', dataIndex: 'weight'},
        { title: '仓位货架', dataIndex: 'position'},
        { title: '备注', dataIndex: 'remark'}
      ],
      assembleColumns: [
        { title: '商品类型', dataIndex: 'mType'},
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '备注', dataIndex: 'remark'}
      ],
      disassembleColumns: [
        { title: '商品类型', dataIndex: 'mType'},
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '颜色', dataIndex: 'color'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '备注', dataIndex: 'remark'}
      ],
      stockCheckReplayColumns: [
        { title: '仓库名称', dataIndex: 'depotName'},
        { title: '条码', dataIndex: 'barCode'},
        { title: '名称', dataIndex: 'name'},
        { title: '规格', dataIndex: 'standard'},
        { title: '型号', dataIndex: 'model'},
        { title: '品牌', dataIndex: 'brand'},
        { title: '制造商', dataIndex: 'mfrs'},
        { title: '扩展1', dataIndex: 'otherField1'},
        { title: '扩展2', dataIndex: 'otherField2'},
        { title: '扩展3', dataIndex: 'otherField3'},
        { title: '库存', dataIndex: 'stock'},
        { title: '单位', dataIndex: 'unit'},
        { title: '多属性', dataIndex: 'sku'},
        { title: '数量', dataIndex: 'operNumber'},
        { title: '单价', dataIndex: 'unitPrice'},
        { title: '金额', dataIndex: 'allPrice'},
        { title: '备注', dataIndex: 'remark'}
      ],
      quickBtn: {
        retailBack: '',
        purchaseOrder: '',
        purchaseIn: '',
        purchaseBack: '',
        saleOut: '',
        saleBack: ''
      },
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
    loadData(arg) {
      // 重置展开状态
      this.expandedRowKeys = []
      if (arg === 1) {
        this.ipagination.current = 1
      }
      let params = this.getQueryParams() //查询条件
      this.loading = true
      getAction(this.url.list, params).then((res) => {
        if (res.code===200) {
          this.dataSource = res.data.rows
          this.ipagination.total = res.data.total
          this.tableAddTotalRow(this.columns, this.dataSource)
        } else if(res.code===510){
          this.$message.warning(res.data)
        } else {
          this.$message.warning(res.data.message)
        }
        this.loading = false
        this.onClearSelected()
      })
    },
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
      let columnIndex = record.subType === '组装单' || record.subType === '拆卸单'?2:1
      this.$refs.modalForm.materialTable.columns[columnIndex].type = FormTypes.popupJsh
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
    batchForceClose: function () {
      if(!this.url.forceCloseBatch){
        this.$message.error("请设置url.forceCloseBatch属性!")
        return
      }
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！')
      } else {
        let ids = "";
        for (let a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ","
        }
        let that = this
        this.$confirm({
          title: "确认强制结单",
          content: "是否强制结单选中数据?",
          onOk: function () {
            that.loading = true
            postAction(that.url.forceCloseBatch, {ids: ids}).then((res) => {
              if(res.code === 200){
                that.loadData()
              } else {
                that.$message.warning(res.data.message)
              }
            }).finally(() => {
              that.loading = false
            });
          }
        });
      }
    },
    batchForceClosePurchase: function () {
      if(!this.url.forceClosePurchaseBatch){
        this.$message.error("请设置url.forceClosePurchaseBatch属性!")
        return
      }
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！')
      } else {
        let ids = "";
        for (let a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ","
        }
        let that = this
        this.$confirm({
          title: "确认强制结单(以销定购)",
          content: "是否强制结单选中数据?",
          onOk: function () {
            that.loading = true
            postAction(that.url.forceClosePurchaseBatch, {ids: ids}).then((res) => {
              if(res.code === 200){
                that.loadData()
              } else {
                that.$message.warning(res.data.message)
              }
            }).finally(() => {
              that.loading = false
            });
          }
        });
      }
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
      this.loadData(1)
    },
    onDateChange: function (value, dateString) {
      this.queryParam.beginTime=dateString[0]
      this.queryParam.endTime=dateString[1]
      if(dateString[0] && dateString[1]) {
        this.queryParam.createTimeRange = [moment(dateString[0]), moment(dateString[1])]
      }
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
          this.purchaseBySaleFlag = res.data.purchaseBySaleFlag==='1'?true:false
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
    initSalesman() {
      let that = this;
      getPersonByNumType({type:1}).then((res)=>{
        if(res) {
          that.salesManList = res;
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
        if(this.purchaseBySaleFlag) {
          //以销定购-开启
          return this.settingDataIndex.includes(item.dataIndex)
        } else {
          //以销定购-关闭
          if(this.prefixNo === 'CGDD') {
            //采购订单只显示除了关联订单之外的列
            if(item.dataIndex!=='linkNumber') {
              return this.settingDataIndex.includes(item.dataIndex)
            }
          } else {
            return this.settingDataIndex.includes(item.dataIndex)
          }
        }
      })
    },
    //加载快捷按钮：转入库、转出库等
    initQuickBtn() {
      let btnStrList = Vue.ls.get('winBtnStrList') //按钮功能列表 JSON字符串
      if (btnStrList) {
        for (let i = 0; i < btnStrList.length; i++) {
          if (btnStrList[i].btnStr) {
            this.quickBtn.retailBack = btnStrList[i].url === '/bill/retail_back'?btnStrList[i].btnStr:this.quickBtn.retailBack
            this.quickBtn.purchaseOrder = btnStrList[i].url === '/bill/purchase_order'?btnStrList[i].btnStr:this.quickBtn.purchaseOrder
            this.quickBtn.purchaseIn = btnStrList[i].url === '/bill/purchase_in'?btnStrList[i].btnStr:this.quickBtn.purchaseIn
            this.quickBtn.purchaseBack = btnStrList[i].url === '/bill/purchase_back'?btnStrList[i].btnStr:this.quickBtn.purchaseBack
            this.quickBtn.saleOut = btnStrList[i].url === '/bill/sale_out'?btnStrList[i].btnStr:this.quickBtn.saleOut
            this.quickBtn.saleBack = btnStrList[i].url === '/bill/sale_back'?btnStrList[i].btnStr:this.quickBtn.saleBack
          }
        }
      }
    },
    handleSearchSupplier(value) {
      let that = this
      if(this.setTimeFlag != null){
        clearTimeout(this.setTimeFlag);
      }
      this.setTimeFlag = setTimeout(()=>{
        findBySelectSup({key: value}).then((res) => {
          if(res) {
            that.supList = res;
          }
        })
      },500)
    },
    handleSearchCustomer(value) {
      let that = this
      if(this.setTimeFlag != null){
        clearTimeout(this.setTimeFlag);
      }
      this.setTimeFlag = setTimeout(()=>{
        findBySelectCus({key: value}).then((res) => {
          if(res) {
            that.cusList = res;
          }
        })
      },500)
    },
    handleSearchRetail(value) {
      let that = this
      if(this.setTimeFlag != null){
        clearTimeout(this.setTimeFlag);
      }
      this.setTimeFlag = setTimeout(()=>{
        findBySelectRetail({key: value}).then((res) => {
          if(res) {
            that.retailList = res;
          }
        })
      },500)
    },
    getDepotByCurrentUser() {
      getAction('/depot/findDepotByCurrentUser').then((res) => {
        if (res.code === 200) {
          if (res.data.length === 1) {
            this.defaultDepotId = res.data[0].id+''
          } else {
            for (let i = 0; i < res.data.length; i++) {
              if(res.data[i].isDefault){
                this.defaultDepotId = res.data[i].id+''
              }
            }
          }
        }
      })
    },
    //跳转到下一个单据页面
    transferBill(type, quickBtnStr) {
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！')
      } else if (this.selectedRowKeys.length > 1) {
        this.$message.warning('只能选择一条记录！')
      } else {
        let info = this.selectionRows[0]
        if(info.status === '1' || info.status === '3') {
          let linkType = 'basic'
          if(type === '转采购订单-以销定购') {
            linkType = 'purchase'
          } else {
            linkType = 'basic'
          }
          let param = {
            headerId: info.id,
            mpList : '',
            linkType: linkType
          }
          getAction('/depotItem/getDetailList', param).then((res) => {
            if (res.code === 200) {
              let deposit = info.changeAmount - info.finishDeposit
              let transferParam = {
                list: res.data.rows,
                number: info.number,
                organId: info.organId,
                discountMoney: info.discountMoney,
                deposit: deposit,
                remark: info.remark,
                accountId: info.accountId,
                salesMan: info.salesMan
              }
              if(type === '转采购订单-以销定购') {
                this.$refs.transferPurchaseModalForm.action = "add"
                this.$refs.transferPurchaseModalForm.transferParam = transferParam
                this.$refs.transferPurchaseModalForm.defaultDepotId = this.defaultDepotId
                this.$refs.transferPurchaseModalForm.add()
                this.$refs.transferPurchaseModalForm.title = type
                if(quickBtnStr.indexOf(2)===-1) {
                  this.$refs.transferPurchaseModalForm.isCanCheck = false
                }
              } else {
                this.$refs.transferModalForm.action = "add"
                this.$refs.transferModalForm.transferParam = transferParam
                this.$refs.transferModalForm.defaultDepotId = this.defaultDepotId
                this.$refs.transferModalForm.add()
                this.$refs.transferModalForm.title = type
                if(quickBtnStr.indexOf(2)===-1) {
                  this.$refs.transferModalForm.isCanCheck = false
                }
              }
            }
          })
        } else {
          this.$message.warning('该状态不能' + type + '！')
        }
      }
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
    },
    // 展开/折叠行
    onExpand(expanded, record) {
      if (expanded) {
        this.expandedRowKeys = [...new Set([...this.expandedRowKeys, record.id])]
        let showType = 'basic'
        if(record.subType === '采购' || record.subType === '采购退货' || record.subType === '销售' || record.subType === '销售退货') {
          if (record.status === '3') {
            showType = 'other'
          }
        } else {
          if (record.status === '3') {
            showType = 'basic'
          } else if (record.purchaseStatus === '3') {
            showType = 'purchase'
          }
        }
        let params = {
          headerId: record.id,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList')),  //扩展属性
          linkType: showType,
          isReadOnly: '0'
        }
        let url = '/depotItem/getDetailList'
        this.requestSubTableData(record, url, params)
      } else {
        this.expandedRowKeys = this.expandedRowKeys.filter(key => key !== record.id)
      }
    },
    requestSubTableData(record, url, params, success) {
      record.loading = true
      getAction(url, params).then(res => {
        if(res && res.code === 200){
          record.childrens = res.data.rows
          this.initSetting(record, record.childrens)
          record.loading = false
          typeof success === 'function' ? success(res) : ''
        }
      }).finally(() => {
        record.loading = false
      })
    },
    initSetting(record, ds) {
      if (this.prefixNo === 'LSCK') {
        this.defDetailColumns = this.retailOutColumns
      } else if (this.prefixNo === 'LSTH') {
        this.defDetailColumns = this.retailBackColumns
      } else if (this.prefixNo === 'QGD') {
        this.defDetailColumns = this.purchaseApplyColumns
      } else if (this.prefixNo === 'CGDD') {
        this.defDetailColumns = this.purchaseOrderColumns
      } else if (this.prefixNo === 'CGRK') {
        this.defDetailColumns = this.purchaseInColumns
      } else if (this.prefixNo === 'CGTH') {
        this.defDetailColumns = this.purchaseBackColumns
      } else if (this.prefixNo === 'XSDD') {
        this.defDetailColumns = this.saleOrderColumns
      } else if (this.prefixNo === 'XSCK') {
        this.defDetailColumns = this.saleOutColumns
      } else if (this.prefixNo === 'XSTH') {
        this.defDetailColumns = this.saleBackColumns
      } else if (this.prefixNo === 'QTRK') {
        this.defDetailColumns = this.otherInColumns
      } else if (this.prefixNo === 'QTCK') {
        this.defDetailColumns = this.otherOutColumns
      } else if (this.prefixNo === 'DBCK') {
        this.defDetailColumns = this.allocationOutColumns
      } else if (this.prefixNo === 'ZZD') {
        this.defDetailColumns = this.assembleColumns
      } else if (this.prefixNo === 'CXD') {
        this.defDetailColumns = this.disassembleColumns
      } else if (this.prefixNo === 'PDFP') {
        this.defDetailColumns = this.stockCheckReplayColumns
      }
      //动态替换扩展字段
      this.handleChangeOtherField()
      //判断序列号、批号、有效期、多属性、重量、仓位货架、扩展、备注等是否有值
      let needAddkeywords = []
      for (let i = 0; i < ds.length; i++) {
        if(ds[i].snList) {
          needAddkeywords.push('snList')
        }
        if(ds[i].batchNumber) {
          needAddkeywords.push('batchNumber')
        }
        if(ds[i].expirationDate) {
          needAddkeywords.push('expirationDate')
        }
        if(ds[i].sku) {
          needAddkeywords.push('sku')
        }
        if(ds[i].weight) {
          needAddkeywords.push('weight')
        }
        if(ds[i].position) {
          needAddkeywords.push('position')
        }
        if(ds[i].brand) {
          needAddkeywords.push('brand')
        }
        if(ds[i].mfrs) {
          needAddkeywords.push('mfrs')
        }
        if(ds[i].otherField1) {
          needAddkeywords.push('otherField1')
        }
        if(ds[i].otherField2) {
          needAddkeywords.push('otherField2')
        }
        if(ds[i].otherField3) {
          needAddkeywords.push('otherField3')
        }
        if(ds[i].taxRate) {
          needAddkeywords.push('taxRate')
        }
        if(ds[i].remark) {
          needAddkeywords.push('remark')
        }
      }
      let currentCol = []
      if(record.status === '3') {
        //部分采购|部分销售的时候显示全部列
        for(let i=0; i<this.defDetailColumns.length; i++){
          currentCol.push(this.defDetailColumns[i])
        }
        this.detailColumns = currentCol
      } else if(record.purchaseStatus === '3') {
        //将已出库的标题转为已采购，针对销售订单转采购订单的场景
        for(let i=0; i<this.defDetailColumns.length; i++){
          let info = {}
          info.title = this.defDetailColumns[i].title
          info.dataIndex = this.defDetailColumns[i].dataIndex
          if(this.defDetailColumns[i].width) {
            info.width = this.defDetailColumns[i].width
          }
          if(this.defDetailColumns[i].dataIndex === 'finishNumber') {
            info.title = '已采购'
          }
          if(this.defDetailColumns[i].dataIndex === 'barCode') {
            info.scopedSlots = { customRender: 'customBarCode' }
          }
          currentCol.push(info)
        }
        this.detailColumns = currentCol
      } else {
        for(let i=0; i<this.defDetailColumns.length; i++){
          //移除列
          let needRemoveKeywords = ['finishNumber','snList','batchNumber','expirationDate','sku','weight','position',
            'brand','mfrs','otherField1','otherField2','otherField3','taxRate','remark']
          if(needRemoveKeywords.indexOf(this.defDetailColumns[i].dataIndex)===-1) {
            let info = {}
            info.title = this.defDetailColumns[i].title
            info.dataIndex = this.defDetailColumns[i].dataIndex
            if(this.defDetailColumns[i].width) {
              info.width = this.defDetailColumns[i].width
            }
            if(this.defDetailColumns[i].dataIndex === 'barCode') {
              info.scopedSlots = { customRender: 'customBarCode' }
            }
            currentCol.push(info)
          }
          //添加有数据的列
          if(needAddkeywords.indexOf(this.defDetailColumns[i].dataIndex)>-1) {
            let info = {}
            info.title = this.defDetailColumns[i].title
            info.dataIndex = this.defDetailColumns[i].dataIndex
            if(this.defDetailColumns[i].width) {
              info.width = this.defDetailColumns[i].width
            }
            currentCol.push(info)
          }
        }
        this.detailColumns = currentCol
      }
    },
    //动态替换扩展字段
    handleChangeOtherField() {
      let mpStr = getMpListShort(Vue.ls.get('materialPropertyList'))
      if(mpStr) {
        let mpArr = mpStr.split(',')
        if(mpArr.length ===3) {
          for (let i = 0; i < this.defDetailColumns.length; i++) {
            if(this.defDetailColumns[i].dataIndex === 'otherField1') {
              this.defDetailColumns[i].title = mpArr[0]
            }
            if(this.defDetailColumns[i].dataIndex === 'otherField2') {
              this.defDetailColumns[i].title = mpArr[1]
            }
            if(this.defDetailColumns[i].dataIndex === 'otherField3') {
              this.defDetailColumns[i].title = mpArr[2]
            }
          }
        }
      }
    }
  }
}
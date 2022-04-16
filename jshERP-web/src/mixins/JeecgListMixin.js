/**
 * 新增修改完成调用 modalFormOk方法 编辑弹框组件ref定义为modalForm
 * 高级查询按钮调用 superQuery方法  高级查询组件ref定义为superQueryModal
 * data中url定义 list为查询列表  delete为删除单条记录  deleteBatch为批量删除
 */
import { filterObj } from '@/utils/util';
import { deleteAction, getAction, postAction, downFile, getFileAccessHttpUrl } from '@/api/manage'
import Vue from 'vue'
import { ACCESS_TOKEN } from "@/store/mutation-types"
import {mixinDevice} from '@/utils/mixin.js'

export const JeecgListMixin = {
  mixins: [mixinDevice],
  data(){
    return {
      //token header
      tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
      /*卡片样式 */
      cardStyle: '',
      /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
      queryParam: {},
      /* 数据源 */
      dataSource:[],
      /* 分页参数 */
      ipagination:{
        current: 1,
        pageSize: 10,
        pageSizeOptions: ['10', '20', '30'],
        showTotal: (total, range) => {
          return range[0] + "-" + range[1] + " 共" + total + "条"
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      /* 控制table高度 */
      scroll: {},
      /* 排序参数 */
      isorter:{
        column: 'createTime',
        order: 'desc',
      },
      /* 筛选参数 */
      filters: {},
      /* table加载状态 */
      loading:false,
      /* table选中keys*/
      selectedRowKeys: [],
      /* table选中records*/
      selectionRows: [],
      /* 查询折叠 */
      toggleSearchStatus:false,
      /* 高级查询条件生效状态 */
      superQueryFlag:false,
      /* 高级查询条件 */
      superQueryParams: '',
      /** 高级查询拼接方式 */
      superQueryMatchType: 'and',
      /** 是否加载时就执行 */
      disableMixinCreated: false,
      /* 按钮权限 */
      btnEnableList: ''
    }
  },
  created() {
    if(this.isDesktop()) {
      this.cardStyle = 'height:' + (document.documentElement.clientHeight-125) + 'px'
    }
    if(!this.disableMixinCreated){
      //console.log(' -- mixin created -- ')
      this.loadData();
      //初始化字典配置 在自己页面定义
      this.initDictConfig();
      //初始化按钮权限
      this.initActiveBtnStr();
    }
  },
  mounted () {
    this.initScroll()
  },
  methods:{
    loadData(arg) {
      if(!this.url.list){
        this.$message.error("请设置url.list属性!")
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1;
      }
      var params = this.getQueryParams();//查询条件
      this.loading = true;
      getAction(this.url.list, params).then((res) => {
        if (res.code===200) {
          this.dataSource = res.data.rows;
          this.ipagination.total = res.data.total;
          this.tableAddTotalRow(this.columns, this.dataSource)
        }
        if(res.code===510){
          this.$message.warning(res.data)
        }
        this.loading = false;
      })
    },
    initDictConfig(){
      //console.log("--这是一个假的方法!")
    },
    handleSuperQuery(params, matchType) {
      //高级查询方法
      if(!params){
        this.superQueryParams=''
        this.superQueryFlag = false
      }else{
        this.superQueryFlag = true
        this.superQueryParams=JSON.stringify(params)
        this.superQueryMatchType = matchType
      }
      this.loadData(1)
    },
    getQueryParams() {
      //获取查询条件
      let sqp = {}
      if(this.superQueryParams){
        sqp['superQueryParams']=encodeURI(this.superQueryParams)
        sqp['superQueryMatchType'] = this.superQueryMatchType
      }
      let searchObj = {}
      searchObj.search = JSON.stringify(this.queryParam);
      var param = Object.assign(sqp, searchObj, this.isorter ,this.filters);
      param.field = this.getQueryField();
      param.currentPage = this.ipagination.current;
      param.pageSize = this.ipagination.pageSize;
      return filterObj(param);
    },
    getQueryField() {
      var str = "id,";
      this.columns.forEach(function (value) {
        str += "," + value.dataIndex;
      });
      return str;
    },

    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys;
      this.selectionRows = selectionRows;
    },
    onClearSelected() {
      this.selectedRowKeys = [];
      this.selectionRows = [];
    },
    searchQuery() {
      this.loadData(1);
    },
    superQuery() {
      this.$refs.superQueryModal.show();
    },
    searchReset() {
      this.queryParam = {}
      this.loadData(1);
    },
    batchSetStatus: function (status) {
      if(!this.url.batchSetStatusUrl){
        this.$message.error("请设置url.batchSetStatusUrl属性!")
        return
      }
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！');
        return;
      } else {
        var ids = "";
        for (var a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ",";
        }
        var that = this;
        this.$confirm({
          title: "确认操作",
          content: "是否操作选中数据?",
          onOk: function () {
            that.loading = true;
            postAction(that.url.batchSetStatusUrl, {status: status, ids: ids}).then((res) => {
              if(res.code === 200){
                that.loadData();
                that.onClearSelected();
              } else {
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.loading = false;
            });
          }
        });
      }
    },
    batchDel: function () {
      if(!this.url.deleteBatch){
        this.$message.error("请设置url.deleteBatch属性!")
        return
      }
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！');
        return;
      } else {
        var ids = "";
        for (var a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ",";
        }
        var that = this;
        this.$confirm({
          title: "确认删除",
          content: "是否删除选中数据?",
          onOk: function () {
            that.loading = true;
            deleteAction(that.url.deleteBatch, {ids: ids}).then((res) => {
              if(res.code === 200){
                that.loadData();
                that.onClearSelected();
              } else {
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.loading = false;
            });
          }
        });
      }
    },
    handleDelete: function (id) {
      if(!this.url.delete){
        this.$message.error("请设置url.delete属性!")
        return
      }
      var that = this;
      deleteAction(that.url.delete, {id: id}).then((res) => {
        if(res.code === 200){
          that.loadData();
        } else {
          that.$message.warning(res.data.message);
        }
      });
    },
    handleEdit: function (record) {
      this.$refs.modalForm.edit(record);
      this.$refs.modalForm.title = "编辑";
      this.$refs.modalForm.disableSubmit = false;
    },
    handleAdd: function () {
      this.$refs.modalForm.add();
      this.$refs.modalForm.title = "新增";
      this.$refs.modalForm.disableSubmit = false;
    },
    handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field;
        this.isorter.order = "ascend" == sorter.order ? "asc" : "desc"
      }
      if(pagination && pagination.current) {
        this.ipagination = pagination;
      }
      this.loadData();
    },
    handleToggleSearch(){
      this.toggleSearchStatus = !this.toggleSearchStatus;
    },
    // 给popup查询使用(查询区域不支持回填多个字段，限制只返回一个字段)
    getPopupField(fields){
      return fields.split(',')[0]
    },
    modalFormOk() {
      // 新增/修改 成功时，重载列表
      this.loadData();
    },
    handleDetail:function(record, type){
      this.$refs.modalDetail.show(record, type);
      this.$refs.modalDetail.title=type+"-详情";
    },
    /* 导出 */
    handleExportXls2(){
      let paramsStr = encodeURI(JSON.stringify(this.getQueryParams()));
      let url = `${window._CONFIG['domianURL']}/${this.url.exportXlsUrl}?paramsStr=${paramsStr}`;
      window.location.href = url;
    },
    handleExportXls(fileName){
      if(!fileName || typeof fileName != "string"){
        fileName = "导出文件"
      }
      let param = {...this.queryParam};
      if(this.selectedRowKeys && this.selectedRowKeys.length>0){
        param['selections'] = this.selectedRowKeys.join(",")
      }
      console.log("导出参数",param)
      downFile(this.url.exportXlsUrl,param).then((data)=>{
        if (!data) {
          this.$message.warning("文件下载失败")
          return
        }
        if (typeof window.navigator.msSaveBlob !== 'undefined') {
          window.navigator.msSaveBlob(new Blob([data],{type: 'application/vnd.ms-excel'}), fileName+'.xls')
        }else{
          let url = window.URL.createObjectURL(new Blob([data],{type: 'application/vnd.ms-excel'}))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', fileName+'.xls')
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link); //下载完成移除元素
          window.URL.revokeObjectURL(url); //释放掉blob对象
        }
      })
    },
    /* 导入 */
    handleImportExcel(info){
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        if (info.file.response) {
          // this.$message.success(`${info.file.name} 文件上传成功`);
          if (info.file.response.code === 200) {
            this.$message.success(info.file.response.data || `${info.file.name} 文件上传成功`)
          } else {
            this.$message.warning(info.file.response.data)
          }
          this.loadData()
        } else {
          this.$message.error(`${info.file.name} ${info.file.response.data}.`);
        }
      } else if (info.file.status === 'error') {
        this.$message.error(`文件上传失败: ${info.file.msg} `);
      }
    },
    /* 图片预览 */
    getImgView(text){
      if(text && text.indexOf(",")>0){
        text = text.substring(0,text.indexOf(","))
      }
      return getFileAccessHttpUrl(text)
    },
    /* 文件下载 */
    uploadFile(text){
      if(!text){
        this.$message.warning("未知的文件")
        return;
      }
      if(text.indexOf(",")>0){
        text = text.substring(0,text.indexOf(","))
      }
      let url = getFileAccessHttpUrl(text)
      window.open(url);
    },
    /* 按钮权限 */
    initActiveBtnStr() {
      let btnStrList = Vue.ls.get('winBtnStrList'); //按钮功能列表 JSON字符串
      this.btnEnableList = ""; //按钮列表
      let pathName = location.pathname
      if(pathName.indexOf('/plugins')>-1) {
        pathName = '/system' + pathName
      }
      if (pathName && btnStrList) {
        for (let i = 0; i < btnStrList.length; i++) {
          if (btnStrList[i].url === pathName) {
            if (btnStrList[i].btnStr) {
              this.btnEnableList = btnStrList[i].btnStr;
            }
          }
        }
      }
    },
    /* 初始化表格横向或纵向滚动 */
    initScroll() {
      if (this.isMobile()) {
        this.scroll.y = ''
      } else {
        let basicLength = 274
        let searchWrapperDomLen=0, operatorDomLen=0
        //搜索区域
        let searchWrapperDom = document.getElementsByClassName('table-page-search-wrapper')
        //操作按钮区域
        let operatorDom = document.getElementsByClassName('table-operator')
        if(searchWrapperDom && searchWrapperDom[0]) {
          searchWrapperDomLen = searchWrapperDom[0].offsetHeight
        }
        if(operatorDom && operatorDom[0]) {
          operatorDomLen = operatorDom[0].offsetHeight+10
        }
        this.scroll.y = document.documentElement.clientHeight-searchWrapperDomLen-operatorDomLen-basicLength
      }
    },
    /** 表格增加合计行 */
    tableAddTotalRow(columns, dataSource) {
      if(dataSource.length>0 && this.ipagination.pageSize%10===1) {
        //分页条数为11、21、31等的时候增加合计行
        let numKey = 'rowIndex'
        let totalRow = { [numKey]: '合计' }
        //需要合计的列
        let parseCols = 'initialStock,currentStock,currentStockPrice,initialAmount,thisMonthAmount,currentAmount,inSum,inSumPrice,inOutSumPrice,' +
          'outSum,outSumPrice,outInSumPrice,operNumber,allPrice,numSum,priceSum,prevSum,thisSum,thisAllPrice,billMoney,changeAmount,' +
          'allPrice,currentNumber,lowSafeStock,highSafeStock,lowCritical,highCritical'
        columns.forEach(column => {
          let { key, dataIndex } = column
          if (![key, dataIndex].includes(numKey)) {
            let total = 0
            dataSource.forEach(data => {
              if(parseCols.indexOf(dataIndex)>-1) {
                if(data[dataIndex]) {
                  total += Number.parseFloat(data[dataIndex])
                } else {
                  total += 0
                }
              } else {
                total = '-'
              }
            })
            if (total !== '-') {
              total = total.toFixed(2)
            }
            totalRow[dataIndex] = total
          }
        })
        dataSource.push(totalRow)
        //总数要增加合计的行数，每页都有一行合计，所以总数要加上
        let size = Math.ceil(this.ipagination.total/(this.ipagination.pageSize-1))
        this.ipagination.total = this.ipagination.total + size
      }
    },
    paginationChange(page, pageSize) {
      this.ipagination.current = page
      this.ipagination.pageSize = pageSize
      this.loadData(this.ipagination.current);
    },
    paginationShowSizeChange(current, size) {
      this.ipagination.current = current
      this.ipagination.pageSize = size
      this.loadData(this.ipagination.current);
    }
  }

}
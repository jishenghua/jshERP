<!-- from 7 5 2 71 8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入条码、名称、助记码、规格、型号等信息" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-range-picker
                    style="width: 100%"
                    v-model="queryParam.createTimeRange"
                    format="YYYY-MM-DD"
                    :placeholder="['开始时间', '结束时间']"
                    @change="onDateChange"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24" >
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" icon="printer">打印</a-button>
                  <a-button style="margin-left: 8px" @click="exportExcel" icon="download">导出</a-button>
                  <a @click="handleToggleSearch" style="margin-left: 8px">
                    {{ toggleSearchStatus ? '收起' : '展开' }}
                    <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
                  </a>
                </span>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item>
                  <span>总数量：{{operNumberTotalStr}}，总金额：{{allPriceTotalStr}}</span>
                </a-form-item>
              </a-col>
            </a-row>
            <template v-if="toggleSearchStatus">
              <a-row :gutter="24">
                <a-col :md="6" :sm="24">
                  <a-form-item label="单据编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入单据编号" v-model="queryParam.number"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="往来单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="请选择往来单位" v-model="queryParam.organId"
                              :dropdownMatchSelectWidth="false" showSearch allow-clear optionFilterProp="children" @search="handleSearchOrgan">
                      <div slot="dropdownRender" slot-scope="menu">
                        <v-nodes :vnodes="menu" />
                        <a-divider style="margin: 4px 0;" />
                        <div class="dropdown-btn" @mousedown="e => e.preventDefault()" @click="initOrgan"><a-icon type="reload" /> 刷新列表</div>
                      </div>
                      <a-select-option v-for="(item,index) in organList" :key="index" :value="item.id">
                        {{ item.supplier }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select
                      optionFilterProp="children"
                      :dropdownMatchSelectWidth="false"
                      showSearch allow-clear style="width: 100%"
                      placeholder="请选择仓库"
                      v-model="queryParam.depotId">
                      <a-select-option v-for="(depot,index) in depotList" :value="depot.id" :key="index">
                        {{ depot.depotName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="操作员" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="请选择操作员" showSearch allow-clear optionFilterProp="children" v-model="queryParam.creator">
                      <a-select-option v-for="(item,index) in userList" :key="index" :value="item.id">
                        {{ item.userName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24" v-if="orgaTree.length">
                  <a-form-item label="机构" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-tree-select style="width:100%" allow-clear :treeData="orgaTree"
                                   v-model="queryParam.organizationId" placeholder="请选择机构">
                    </a-tree-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="商品类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                                   :treeData="categoryTree" v-model="queryParam.categoryId" placeholder="请选择商品类别">
                    </a-tree-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入备注" v-model="queryParam.remark"></a-input>
                  </a-form-item>
                </a-col>
              </a-row>
            </template>
          </a-form>
        </div>
        <!-- table区域-begin -->
        <section ref="print" id="reportPrint">
          <a-table
            bordered
            ref="table"
            size="middle"
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :components="handleDrag(columns)"
            :pagination="false"
            :scroll="scroll"
            :loading="loading"
            @change="handleTableChange">
            <span slot="customTitle">
              <a-popover trigger="click" placement="right">
                <template slot="content">
                  <a-checkbox-group @change="onColChange" v-model="settingDataIndex" :defaultValue="settingDataIndex">
                    <a-row style="width: 600px">
                      <template v-for="(item,index) in defColumns">
                        <template>
                          <a-col :span="6">
                            <a-checkbox :value="item.dataIndex" v-if="item.dataIndex==='rowIndex'" disabled></a-checkbox>
                            <a-checkbox :value="item.dataIndex" v-if="item.dataIndex!=='rowIndex'">
                              <j-ellipsis :value="item.title" :length="10"></j-ellipsis>
                            </a-checkbox>
                          </a-col>
                        </template>
                      </template>
                    </a-row>
                    <a-row style="padding-top: 10px;">
                      <a-col>
                        恢复默认列配置：<a-button @click="handleRestDefault" type="link" size="small">恢复默认</a-button>
                      </a-col>
                    </a-row>
                  </a-checkbox-group>
                </template>
                <a-icon type="setting" />
              </a-popover>
            </span>
            <span slot="numberCustomRender" slot-scope="text, record">
              <a @click="myHandleDetail(record)">{{record.number}}</a>
            </span>
          </a-table>
          <a-row :gutter="24" style="margin-top: 8px;text-align:right;">
            <a-col :md="24" :sm="24">
              <a-pagination @change="paginationChange" @showSizeChange="paginationShowSizeChange"
                size="small"
                show-size-changer
                :showQuickJumper="true"
                :current="ipagination.current"
                :page-size="ipagination.pageSize"
                :page-size-options="ipagination.pageSizeOptions"
                :total="ipagination.total"
                :show-total="(total, range) => `共 ${total-Math.ceil(total/ipagination.pageSize)} 条`">
                <template slot="buildOptionText" slot-scope="props">
                  <span>{{ props.value-1 }}条/页</span>
                </template>
              </a-pagination>
            </a-col>
          </a-row>
        </section>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <bill-detail ref="modalDetail"></bill-detail>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import BillDetail from '../bill/dialog/BillDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getFormatDate, getPrevMonthFormatDate } from '@/utils/util'
  import {getAction} from '@/api/manage'
  import {findBySelectOrgan, findBillDetailByNumber, getUserList, queryMaterialCategoryTreeList, getAllOrganizationTreeByUser} from '@/api/api'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  import Vue from 'vue'
  export default {
    name: "OutDetail",
    mixins:[JeecgListMixin],
    components: {
      BillDetail,
      JEllipsis,
      VNodes: {
        functional: true,
        render: (h, ctx) => ctx.props.vnodes,
      }
    },
    data () {
      return {
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {
          organId: undefined,
          number: '',
          materialParam:'',
          depotId: undefined,
          beginTime: getPrevMonthFormatDate(3),
          endTime: getFormatDate(),
          createTimeRange: [moment(getPrevMonthFormatDate(3)), moment(getFormatDate())],
          type: "出库",
          creator: undefined,
          organizationId: undefined,
          remark: ''
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201', '301', '1001', '2001', '3001']
        },
        organList: [],
        depotList: [],
        userList: [],
        orgaTree: [],
        categoryTree:[],
        operNumberTotalStr: '0',
        allPriceTotalStr: '0',
        setTimeFlag: null,
        tabKey: "1",
        pageName: 'outDetail',
        // 默认索引
        defDataIndex:['rowIndex','number','barCode','mname','standard','model','mUnit','operNumber', 'unitPrice','allPrice',
          'taxRate','taxMoney','sname','dname','operTime','newRemark'],
        // 默认列
        defColumns: [
          {
            dataIndex: 'rowIndex', width:40, align:"center", slots: { title: 'customTitle' },
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {
            title: '单据编号', dataIndex: 'number', width: 100,
            scopedSlots: { customRender: 'numberCustomRender' },
          },
          {title: '条码', dataIndex: 'barCode', sorter: (a, b) => a.barCode - b.barCode, width: 80},
          {title: '名称', dataIndex: 'mname', width: 120, ellipsis:true},
          {title: '规格', dataIndex: 'standard', width: 60, ellipsis:true},
          {title: '型号', dataIndex: 'model', width: 60, ellipsis:true},
          {title: '颜色', dataIndex: 'color', width: 40, ellipsis:true},
          {title: '品牌', dataIndex: 'brand', width: 60, ellipsis:true},
          {title: '制造商', dataIndex: 'mfrs', width: 60, ellipsis:true},
          {title: '单位', dataIndex: 'mUnit', width: 50, ellipsis:true},
          {title: '多属性', dataIndex: 'sku', width: 100, ellipsis:true},
          {title: '数量', dataIndex: 'operNumber', sorter: (a, b) => a.operNumber - b.operNumber, width: 60},
          {title: '单价', dataIndex: 'unitPrice', sorter: (a, b) => a.unitPrice - b.unitPrice, width: 60},
          {title: '金额', dataIndex: 'allPrice', sorter: (a, b) => a.allPrice - b.allPrice, width: 60},
          {title: '税率(%)', dataIndex: 'taxRate', width: 60},
          {title: '税额', dataIndex: 'taxMoney', sorter: (a, b) => a.taxMoney - b.taxMoney, width: 60},
          {title: '往来单位', dataIndex: 'sname', width: 80, ellipsis:true},
          {title: '仓库', dataIndex: 'dname', width: 80, ellipsis:true},
          {title: '出库日期', dataIndex: 'operTime', width: 70},
          {title: '备注', dataIndex: 'newRemark', width: 100, ellipsis:true}
        ],
        url: {
          list: "/depotHead/findInOutDetail",
        }
      }
    },
    created () {
      this.getDepotData()
      this.initOrgan()
      this.initUser()
      this.loadAllOrgaData()
      this.loadCategoryTreeData()
      this.initColumnsSetting()
    },
    methods: {
      moment,
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize-1;
        return param;
      },
      onDateChange: function (value, dateString) {
        this.queryParam.beginTime=dateString[0]
        this.queryParam.endTime=dateString[1]
        if(dateString[0] && dateString[1]) {
          this.queryParam.createTimeRange = [moment(dateString[0]), moment(dateString[1])]
        }
      },
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        let params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.code===200) {
            this.dataSource = res.data.rows;
            this.ipagination.total = res.data.total;
            this.operNumberTotalStr = res.data.operNumberTotal.toFixed(2)
            this.allPriceTotalStr = res.data.allPriceTotal.toFixed(2)
            this.tableAddTotalRow(this.columns, this.dataSource)
          } else if(res.code===510){
            this.$message.warning(res.data)
          } else {
            this.$message.warning(res.data.message)
          }
          this.loading = false;
        })
      },
      initOrgan() {
        let that = this;
        findBySelectOrgan({}).then((res)=>{
          if(res) {
            that.organList = res;
          }
        });
      },
      handleSearchOrgan(value) {
        let that = this
        if(this.setTimeFlag != null){
          clearTimeout(this.setTimeFlag);
        }
        this.setTimeFlag = setTimeout(()=>{
          findBySelectOrgan({key: value}).then((res) => {
            if(res) {
              that.organList = res;
            }
          })
        },500)
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
      loadAllOrgaData(){
        let that = this
        let params = {}
        getAllOrganizationTreeByUser(params).then((res)=>{
          if(res){
            that.orgaTree = res
          }
        })
      },
      loadCategoryTreeData(){
        let that = this;
        let params = {};
        params.id='';
        queryMaterialCategoryTreeList(params).then((res)=>{
          if(res){
            that.categoryTree = [];
            for (let i = 0; i < res.length; i++) {
              let temp = res[i];
              that.categoryTree.push(temp);
            }
          }
        })
      },
      myHandleDetail(record) {
        findBillDetailByNumber({ number: record.number }).then((res) => {
          if (res && res.code === 200) {
            this.$refs.modalDetail.isCanBackCheck = false
            this.handleDetail(res.data, record.newType)
          }
        })
      },
      searchQuery() {
        if(this.queryParam.beginTime == '' || this.queryParam.endTime == ''){
          this.$message.warning('请选择单据日期！')
        } else {
          this.loadData(1);
        }
      },
      exportExcel() {
        let list = []
        let head = '单据编号,条码,名称,规格,型号,颜色,品牌,制造商,单位,多属性,数量,单价,金额,税率(%),税额,往来单位,仓库,出库日期,备注'
        for (let i = 0; i < this.dataSource.length; i++) {
          let item = []
          let ds = this.dataSource[i]
          item.push(ds.number, ds.barCode, ds.mname, ds.standard, ds.model, ds.color, ds.brand, ds.mfrs, ds.mUnit, ds.sku,
            ds.operNumber, ds.unitPrice, ds.allPrice, ds.taxRate, ds.taxMoney, ds.sname, ds.dname, ds.operTime, ds.newRemark)
          list.push(item)
        }
        let tip = '单据日期：' + this.queryParam.beginTime + '~' + this.queryParam.endTime
        this.handleExportXlsPost('出库明细', '出库明细', head, tip, list)
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
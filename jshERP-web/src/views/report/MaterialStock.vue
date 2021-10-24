<!-- from 7 5 2 7 1 8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="3" :sm="24">
                <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select
                    showSearch optionFilterProp="children"
                    style="width: 100%"
                    placeholder="请选择仓库"
                    v-model="queryParam.depotId">
                    <a-select-option v-for="(depot,index) in depotList" :value="depot.id">
                      {{ depot.depotName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="3" :sm="24">
                <a-form-item label="类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                       :treeData="categoryTree" v-model="queryParam.categoryId" placeholder="请选择类别">
                  </a-tree-select>
                </a-form-item>
              </a-col>
              <a-col :md="4" :sm="24">
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="条码/名称/规格/型号" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="3" :sm="24">
                <a-form-item label="零库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select v-model="queryParam.zeroStock">
                    <a-select-option value="0">隐藏</a-select-option>
                    <a-select-option value="1">显示</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-col :md="4" :sm="24">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" icon="printer">打印</a-button>
                  <a-button style="margin-left: 8px" @click="exportExcel" icon="download">导出</a-button>
                </a-col>
              </span>
              <a-col :md="3" :sm="24">
                <a-form-item label="当前总库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  {{currentStock}}
                </a-form-item>
              </a-col>
              <a-col :md="3" :sm="24">
                <a-form-item label="当前总库存金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  {{currentStockPrice}}
                </a-form-item>
              </a-col>
            </a-row>
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
            :pagination="false"
            :scroll="scroll"
            :loading="loading"
            @change="handleTableChange">
              <span slot="action" slot-scope="text, record">
                <a @click="showMaterialInOutList(record)">{{record.id?'流水':''}}</a>
              </span>
          </a-table>
          <a-row :gutter="24" style="margin-top: 8px;text-align:right;">
            <a-col :md="24" :sm="24">
              <a-pagination @change="paginationChange" @showSizeChange="paginationShowSizeChange"
                size="small"
                show-size-changer
                :showQuickJumper="true"
                :page-size="ipagination.pageSize"
                :page-size-options="ipagination.pageSizeOptions"
                :total="ipagination.total"
                :show-total="(total, range) => `共 ${total} 条`">
                <template slot="buildOptionText" slot-scope="props">
                  <span>{{ props.value-1 }}条/页</span>
                </template>
              </a-pagination>
            </a-col>
          </a-row>
        </section>
        <!-- table区域-end -->
        <material-in-out-list ref="materialInOutList" @ok="modalFormOk"></material-in-out-list>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import MaterialInOutList from './modules/MaterialInOutList'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction } from '@/api/manage'
  import {queryMaterialCategoryTreeList} from '@/api/api'
  import { getMpListShort, openDownloadDialog, sheet2blob} from "@/utils/util"
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  import Vue from 'vue'
  export default {
    name: "MaterialStock",
    mixins:[JeecgListMixin],
    components: {
      MaterialInOutList,
      JEllipsis
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
          depotId:'',
          categoryId:'',
          materialParam:'',
          zeroStock: '0',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        depotList: [],
        categoryTree:[],
        currentStock: '',
        currentStockPrice: '',
        // 表头
        columns: [
          {
            title: '#', dataIndex: 'rowIndex', width:40, align:"center",
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '条码', dataIndex: 'mBarCode', width: 80},
          {title: '名称', dataIndex: 'name', width: 140},
          {title: '规格', dataIndex: 'standard', width: 80},
          {title: '型号', dataIndex: 'model', width: 80},
          {title: '颜色', dataIndex: 'color', width: 80},
          {title: '类别', dataIndex: 'categoryName', width: 80},
          {title: '单位', dataIndex: 'unitName', width: 60},
          {title: '单价', dataIndex: 'purchaseDecimal', sorter: (a, b) => a.purchaseDecimal - b.purchaseDecimal, width: 60},
          {title: '初始库存', dataIndex: 'initialStock', sorter: (a, b) => a.initialStock - b.initialStock, width: 60},
          {title: '当前库存', dataIndex: 'currentStock', sorter: (a, b) => a.currentStock - b.currentStock, width: 60},
          {title: '当前库存金额', dataIndex: 'currentStockPrice', sorter: (a, b) => a.currentStockPrice - b.currentStockPrice, width: 80},
          { title: '库存流水', dataIndex: 'action', align:"center", width: 100,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/material/getListWithStock"
        }
      }
    },
    created() {
      this.getDepotData()
      this.loadTreeData()
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
      getDepotData() {
        getAction('/depot/findDepotByCurrentUser').then((res)=>{
          if(res.code === 200){
            this.depotList = res.data;
          }else{
            this.$message.info(res.data);
          }
        })
      },
      loadTreeData(){
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
      searchQuery() {
        this.loadData(1);
      },
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
            this.currentStock = res.data.currentStock;
            this.currentStockPrice = res.data.currentStockPrice;
          }
          if(res.code===510){
            this.$message.warning(res.data)
          }
          this.loading = false;
        })
      },
      showMaterialInOutList(record) {
        this.$refs.materialInOutList.show(record);
        this.$refs.materialInOutList.title = "查看商品库存流水（全部仓库）";
        this.$refs.materialInOutList.disableSubmit = false;
      },
      exportExcel() {
        let aoa = [['条码', '名称', '规格', '型号', '颜色', '类别', '单位', '单价', '初始库存', '当前库存', '当前库存金额']]
        for (let i = 0; i < this.dataSource.length; i++) {
          let ds = this.dataSource[i]
          let item = [ds.mBarCode, ds.name, ds.standard, ds.model, ds.color, ds.categoryName, ds.unitName,
            ds.purchaseDecimal, ds.initialStock, ds.currentStock, ds.currentStockPrice]
          aoa.push(item)
        }
        openDownloadDialog(sheet2blob(aoa), '商品库存')
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
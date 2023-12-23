<!-- from 7 5 2 7 1 8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="5" :sm="24">
                <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select
                    mode="multiple" :maxTagCount="1"
                    optionFilterProp="children"
                    showSearch style="width: 100%"
                    placeholder="请选择仓库"
                    v-model="depotSelected">
                    <a-select-option v-for="(depot,index) in depotList" :key="index" :value="depot.id">
                      {{ depot.depotName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="5" :sm="24">
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="条码/名称/规格/型号/颜色" v-model="queryParam.materialParam"></a-input>
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
              <a-col :md="8" :sm="24">
                <a-form-item>
                  <span>总库存：{{currentStock}}，总库存金额：{{currentStockPrice}}，总重量：{{currentWeight}}</span>
                </a-form-item>
              </a-col>
              <template v-if="toggleSearchStatus">
                <a-col :md="5" :sm="24">
                  <a-form-item label="类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                                   :treeData="categoryTree" v-model="queryParam.categoryId" placeholder="请选择类别">
                    </a-tree-select>
                  </a-form-item>
                </a-col>
                <a-col :md="5" :sm="24">
                  <a-form-item label="仓位货架" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input style="width: 100%" placeholder="请输入仓位货架查询" v-model="queryParam.position"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="零库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select v-model="queryParam.zeroStock">
                      <a-select-option value="0">隐藏</a-select-option>
                      <a-select-option value="1">显示</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </template>
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
            :components="handleDrag(columns)"
            :pagination="false"
            :scroll="scroll"
            :loading="loading"
            @change="handleTableChange">
              <span slot="action" slot-scope="text, record">
                <a @click="showMaterialInOutList(record)">{{record.id?'流水':''}}</a>
              </span>
              <template slot="customBarCode" slot-scope="text, record">
                <div :style="record.imgName?'float:left;line-height:30px':'float:left;'">{{record.mBarCode}}</div>
                <a-popover placement="right" trigger="click">
                  <template slot="content">
                    <img :src="getImgUrl(record.imgName, record.imgLarge)" width="500px" />
                  </template>
                  <div class="item-info" v-if="record.imgName">
                    <img v-if="record.imgName" :src="getImgUrl(record.imgName, record.imgSmall)" class="item-img" title="查看大图" />
                  </div>
                </a-popover>
              </template>
              <template slot="customRenderStock" slot-scope="text, record">
                <a-tooltip :title="record.bigUnitStock">
                  {{text}}
                </a-tooltip>
              </template>
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
        <material-in-out-list ref="materialInOutList" @ok="modalFormOk"></material-in-out-list>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import MaterialInOutList from './modules/MaterialInOutList'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction, getFileAccessHttpUrl } from '@/api/manage'
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
          categoryId:'',
          materialParam:'',
          position:'',
          zeroStock: '0',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201', '301', '1001', '2001', '3001']
        },
        depotSelected:[],
        depotList: [],
        categoryTree:[],
        currentStock: '',
        currentStockPrice: '',
        currentWeight: '',
        // 表头
        columns: [
          {
            title: '#', dataIndex: 'rowIndex', width:40, align:"center",
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '库存流水', dataIndex: 'action', align:"center", width: 60,
            scopedSlots: { customRender: 'action' }
          },
          {title: '条码', dataIndex: 'mBarCode', width: 100,
            scopedSlots: { customRender: 'customBarCode' }
          },
          {title: '名称', dataIndex: 'name', width: 140, ellipsis:true},
          {title: '规格', dataIndex: 'standard', width: 100, ellipsis:true},
          {title: '型号', dataIndex: 'model', width: 100, ellipsis:true},
          {title: '颜色', dataIndex: 'color', width: 60, ellipsis:true},
          {title: '类别', dataIndex: 'categoryName', width: 60, ellipsis:true},
          {title: '仓位货架', dataIndex: 'position', width: 60, ellipsis:true},
          {title: '单位', dataIndex: 'unitName', width: 60, ellipsis:true},
          {title: '单价', dataIndex: 'purchaseDecimal', sorter: (a, b) => a.purchaseDecimal - b.purchaseDecimal, width: 60},
          {title: '初始库存', dataIndex: 'initialStock', sorter: (a, b) => a.initialStock - b.initialStock, width: 60},
          {title: '库存', dataIndex: 'currentStock', sorter: (a, b) => a.currentStock - b.currentStock, width: 60,
            scopedSlots: { customRender: 'customRenderStock' }
          },
          {title: '库存金额', dataIndex: 'currentStockPrice', sorter: (a, b) => a.currentStockPrice - b.currentStockPrice, width: 80},
          {title: '重量', dataIndex: 'currentWeight', sorter: (a, b) => a.currentWeight - b.currentWeight, width: 60}
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
    mounted () {
      this.scroll.x = 1620
    },
    methods: {
      moment,
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        if(this.depotSelected && this.depotSelected.length>0) {
          param.depotIds = this.depotSelected.join()
        }
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
      getImgUrl(imgName, type) {
        if(imgName && imgName.split(',')) {
          type = type? type + '/':''
          return getFileAccessHttpUrl('systemConfig/static/' + type + imgName.split(',')[0])
        } else {
          return ''
        }
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
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        let params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.code===200) {
            this.dataSource = res.data.rows;
            this.ipagination.total = res.data.total;
            this.tableAddTotalRow(this.columns, this.dataSource)
            this.currentStock = res.data.currentStock.toFixed(2)
            this.currentStockPrice = res.data.currentStockPrice.toFixed(2)
            this.currentWeight = res.data.currentWeight.toFixed(2)
          }
          if(res.code===510){
            this.$message.warning(res.data)
          }
          this.loading = false;
        })
      },
      showMaterialInOutList(record) {
        let depotIds = ''
        if(this.depotSelected && this.depotSelected.length>0) {
          depotIds = this.depotSelected.join()
        }
        this.$refs.materialInOutList.show(record, depotIds);
        this.$refs.materialInOutList.title = "查看商品库存流水";
        this.$refs.materialInOutList.disableSubmit = false;
      },
      exportExcel() {
        let aoa = [['条码', '名称', '规格', '型号', '颜色', '类别', '单位', '单价', '初始库存', '库存', '库存金额', '重量']]
        for (let i = 0; i < this.dataSource.length; i++) {
          let ds = this.dataSource[i]
          let item = [ds.mBarCode, ds.name, ds.standard, ds.model, ds.color, ds.categoryName, ds.unitName,
            ds.purchaseDecimal, ds.initialStock, ds.currentStock, ds.currentStockPrice, ds.currentWeight]
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
<style scoped>
  .item-info {
    float:left;
    width:30px;
    height:30px;
    margin-left:8px
  }
  .item-img {
    cursor:pointer;
    position: static;
    display: block;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
</style>
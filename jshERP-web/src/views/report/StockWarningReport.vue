<!-- gitee 7 5 2 7 1 8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select
                    optionFilterProp="children"
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
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入条码、名称、助记码、规格、型号等信息" v-model="queryParam.materialParam"></a-input>
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
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" icon="printer">打印</a-button>
                  <a-button style="margin-left: 8px" @click="exportExcel" icon="download">导出</a-button>
                </span>
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
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import {getAction} from '@/api/manage'
  import { queryMaterialCategoryTreeList } from '@/api/api'
  import { getMpListShort } from "@/utils/util"
  import Vue from 'vue'
  export default {
    name: "StockWarningReport",
    mixins:[JeecgListMixin],
    components: {
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
          materialParam:'',
          depotId: undefined,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        depotList: [],
        categoryTree:[],
        tabKey: "1",
        pageName: 'stockWarningReport',
        // 默认索引
        defDataIndex:['rowIndex','depotName','barCode','mname','mstandard','mmodel','materialUnit','currentNumber',
          'lowSafeStock','highSafeStock','lowCritical','highCritical'],
        // 默认列
        defColumns: [
          {
            dataIndex: 'rowIndex', width:40, align:"center", slots: { title: 'customTitle' },
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '仓库', dataIndex: 'depotName', width: 100, ellipsis:true},
          {title: '条码', dataIndex: 'barCode', sorter: (a, b) => a.barCode - b.barCode, width: 100},
          {title: '名称', dataIndex: 'mname', width: 100, ellipsis:true},
          {title: '规格', dataIndex: 'mstandard', width: 80, ellipsis:true},
          {title: '型号', dataIndex: 'mmodel', width: 80, ellipsis:true},
          {title: '颜色', dataIndex: 'mcolor', width: 50, ellipsis:true},
          {title: '品牌', dataIndex: 'brand', width: 80, ellipsis:true},
          {title: '制造商', dataIndex: 'mmfrs', width: 80, ellipsis:true},
          {title: '扩展1', dataIndex: 'motherField1', width: 80, ellipsis:true},
          {title: '扩展2', dataIndex: 'motherField2', width: 80, ellipsis:true},
          {title: '扩展3', dataIndex: 'motherField3', width: 80, ellipsis:true},
          {title: '单位', dataIndex: 'materialUnit', width: 60, ellipsis:true},
          {title: '库存', dataIndex: 'currentNumber', sorter: (a, b) => a.currentNumber - b.currentNumber, width: 80},
          {title: '最低安全库存', dataIndex: 'lowSafeStock', sorter: (a, b) => a.lowSafeStock - b.lowSafeStock, width: 100},
          {title: '最高安全库存', dataIndex: 'highSafeStock', sorter: (a, b) => a.highSafeStock - b.highSafeStock, width: 100},
          {title: '建议入库量', dataIndex: 'lowCritical', sorter: (a, b) => a.lowCritical - b.lowCritical, width: 80},
          {title: '建议出库量', dataIndex: 'highCritical', sorter: (a, b) => a.highCritical - b.highCritical, width: 80}
        ],
        url: {
          list: "/depotItem/findStockWarningCount"
        }
      }
    },
    created () {
      this.getDepotData()
      this.loadCategoryTreeData()
      this.initColumnsSetting()
      this.handleChangeOtherField(0)
    },
    methods: {
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
      //动态替换扩展字段
      handleChangeOtherField(showQuery) {
        let mpStr = getMpListShort(Vue.ls.get('materialPropertyList'))
        if(mpStr) {
          let mpArr = mpStr.split(',')
          if(mpArr.length ===3) {
            if(showQuery) {
              this.queryTitle.mp1 = mpArr[0]
              this.queryTitle.mp2 = mpArr[1]
              this.queryTitle.mp3 = mpArr[2]
            }
            for (let i = 0; i < this.defColumns.length; i++) {
              if(this.defColumns[i].dataIndex === 'motherField1') {
                this.defColumns[i].title = mpArr[0]
              }
              if(this.defColumns[i].dataIndex === 'motherField2') {
                this.defColumns[i].title = mpArr[1]
              }
              if(this.defColumns[i].dataIndex === 'motherField3') {
                this.defColumns[i].title = mpArr[2]
              }
            }
          }
        }
      },
      exportExcel() {
        let list = []
        let mpStr = getMpListShort(Vue.ls.get('materialPropertyList'))
        let head = '仓库,条码,名称,规格,型号,颜色,品牌,制造商,' + mpStr + ',单位,库存,最低安全库存,最高安全库存,建议入库量,建议出库量'
        for (let i = 0; i < this.dataSource.length; i++) {
          let item = []
          let ds = this.dataSource[i]
          item.push(ds.depotName, ds.barCode, ds.mname, ds.mstandard, ds.mmodel, ds.mcolor, ds.brand, ds.mmfrs,
            ds.motherField1, ds.motherField2, ds.motherField3, ds.materialUnit, ds.currentNumber, ds.lowSafeStock, ds.highSafeStock, ds.lowCritical, ds.highCritical)
          list.push(item)
        }
        let tip = '库存预警查询'
        this.handleExportXlsPost('库存预警', '库存预警', head, tip, list)
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
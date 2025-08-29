<!-- from 7 5 2 7 1 8 9 2 0 -->
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
                <a-form-item label="库存周期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-range-picker
                    style="width: 100%"
                    v-model="queryParam.createTimeRange"
                    format="YYYY-MM-DD"
                    :placeholder="['开始时间', '结束时间']"
                    @change="onDateChange"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
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
                  <span>本期总结存：{{totalStockStr}}，总结存金额：{{totalCountMoneyStr}}</span>
                </a-form-item>
              </a-col>
            </a-row>
            <template v-if="toggleSearchStatus">
              <a-row :gutter="24">
                <a-col :md="6" :sm="24">
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
                <a-col :md="6" :sm="24">
                  <a-form-item label="类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                                   :treeData="categoryTree" v-model="queryParam.categoryId" placeholder="请选择类别">
                    </a-tree-select>
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
            <span slot="action" slot-scope="text, record">
              <a @click="showMaterialDepotStockList(record)">{{record.id?'分布':''}}</a>
            </span>
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
            <template slot="customPic" slot-scope="text, record">
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
        <material-depot-stock-list-with-time ref="materialDepotStockListWithTime" @ok="modalFormOk"></material-depot-stock-list-with-time>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import MaterialDepotStockListWithTime from './modules/MaterialDepotStockListWithTime'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction, getFileAccessHttpUrl } from '@/api/manage'
  import {queryMaterialCategoryTreeList} from '@/api/api'
  import { getFormatDate, getMpListShort, getPrevMonthFormatDate } from '@/utils/util'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  import Vue from 'vue'
  export default {
    name: "InOutStockReport",
    mixins:[JeecgListMixin],
    components: {
      MaterialDepotStockListWithTime,
      JEllipsis
    },
    data () {
      return {
        // 查询条件
        currentMonth: moment().format('YYYY-MM'),
        monthFormat: 'YYYY-MM',
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        queryParam: {
          depotId: undefined,
          beginTime: getPrevMonthFormatDate(1),
          endTime: getFormatDate(),
          createTimeRange: [moment(getPrevMonthFormatDate(1)), moment(getFormatDate())],
          materialParam:'',
          categoryId: undefined,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        depotSelected:[],
        depotList: [],
        categoryTree:[],
        totalStockStr: '0',
        totalCountMoneyStr: '0',
        pageName: 'inOutStockReport',
        // 默认索引
        defDataIndex:['rowIndex','action','barCode','materialName','materialStandard','materialModel','unitName','unitPrice',
          'prevSum','inSum','outSum','thisSum','thisAllPrice'],
        // 默认列
        defColumns: [
          {
            dataIndex: 'rowIndex', width:40, align:"center", slots: { title: 'customTitle' },
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '库存详情', dataIndex: 'action', align:"center", width: 60,
            scopedSlots: { customRender: 'action' }
          },
          {title: '图片', dataIndex: 'pic', width: 45, scopedSlots: { customRender: 'customPic' }},
          {title: '条码', dataIndex: 'barCode', sorter: (a, b) => a.barCode - b.barCode, width: 100},
          {title: '名称', dataIndex: 'materialName', width: 120, ellipsis:true},
          {title: '规格', dataIndex: 'materialStandard', width: 80, ellipsis:true},
          {title: '型号', dataIndex: 'materialModel', width: 80, ellipsis:true},
          {title: '颜色', dataIndex: 'materialColor', width: 50, ellipsis:true},
          {title: '品牌', dataIndex: 'materialBrand', width: 80, ellipsis:true},
          {title: '制造商', dataIndex: 'materialMfrs', width: 80, ellipsis:true},
          {title: '扩展1', dataIndex: 'otherField1', width: 50, ellipsis:true},
          {title: '扩展2', dataIndex: 'otherField2', width: 50, ellipsis:true},
          {title: '扩展3', dataIndex: 'otherField3', width: 50, ellipsis:true},
          {title: '单位', dataIndex: 'unitName', width: 60, ellipsis:true},
          {title: '成本价', dataIndex: 'unitPrice', sorter: (a, b) => a.unitPrice - b.unitPrice, width: 60},
          {title: '上期结存数量', dataIndex: 'prevSum', sorter: (a, b) => a.prevSum - b.prevSum, width: 80},
          {title: '入库数量', dataIndex: 'inSum', sorter: (a, b) => a.inSum - b.inSum, width: 60},
          {title: '出库数量', dataIndex: 'outSum', sorter: (a, b) => a.outSum - b.outSum, width: 60},
          {title: '本期结存数量', dataIndex: 'thisSum', sorter: (a, b) => a.thisSum - b.thisSum, width: 80,
            scopedSlots: { customRender: 'customRenderStock' }
          },
          {title: '结存金额', dataIndex: 'thisAllPrice', sorter: (a, b) => a.thisAllPrice - b.thisAllPrice, width: 60}
        ],
        url: {
          list: "/depotItem/getInOutStock",
          totalCountMoney: "/depotItem/getInOutStockCountMoney"
        }
      }
    },
    created() {
      this.getDepotData()
      this.loadTreeData()
      this.getTotalCountMoney()
      this.initColumnsSetting()
      this.handleChangeOtherField(0)
    },
    methods: {
      moment,
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        if(this.depotSelected && this.depotSelected.length>0) {
          param.depotIds = this.depotSelected.join()
        }
        param.monthTime = this.queryParam.monthTime;
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
      getDepotData() {
        getAction('/depot/findDepotByCurrentUser').then((res)=>{
          if(res.code === 200){
            this.depotList = res.data;
          }else{
            this.$message.info(res.data);
          }
        })
      },
      getTotalCountMoney(){
        let param = Object.assign({}, this.queryParam, this.isorter);
        if(this.depotSelected && this.depotSelected.length>0) {
          param.depotIds = this.depotSelected.join()
        }
        param.monthTime = this.queryParam.monthTime;
        getAction(this.url.totalCountMoney, param).then((res)=>{
          if(res && res.code === 200) {
            this.totalStockStr = res.data.totalStock.toFixed(2)
            this.totalCountMoneyStr = res.data.totalCount.toFixed(2)
          }
        })
      },
      onChange: function (value, dateString) {
        console.log(dateString);
        this.queryParam.monthTime=dateString;
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
        if(this.queryParam.beginTime === '' || this.queryParam.endTime === ''){
          this.$message.warning('请选择库存周期！')
        } else {
          this.loadData(1);
          this.getTotalCountMoney();
        }
      },
      showMaterialDepotStockList(record) {
        let depotIds = ''
        if(this.depotSelected && this.depotSelected.length>0) {
          depotIds = this.depotSelected.join()
        }
        this.$refs.materialDepotStockListWithTime.show(record, depotIds, this.queryParam.beginTime, this.queryParam.endTime);
        this.$refs.materialDepotStockListWithTime.title = "查看进销存统计库存分布（条码：" + record.barCode + "，名称：" + record.materialName + "）";
        this.$refs.materialDepotStockListWithTime.disableSubmit = false;
      },
      exportExcel() {
        let list = []
        let mpStr = getMpListShort(Vue.ls.get('materialPropertyList'))
        let head = '条码,名称,规格,型号,颜色,品牌,制造商,' + mpStr + ',单位,成本价,上期结存数量,入库数量,出库数量,本期结存数量,结存金额'
        for (let i = 0; i < this.dataSource.length; i++) {
          let item = []
          let ds = this.dataSource[i]
          item.push(ds.barCode, ds.materialName, ds.materialStandard, ds.materialModel, ds.materialColor, ds.materialBrand,
            ds.materialMfrs, ds.otherField1, ds.otherField2, ds.otherField3, ds.unitName, ds.unitPrice,
            ds.prevSum, ds.inSum, ds.outSum, ds.thisSum, ds.thisAllPrice)
          list.push(item)
        }
        let tip = '库存周期：' + this.queryParam.beginTime + '~' + this.queryParam.endTime
        this.handleExportXlsPost('进销存统计', '进销存统计', head, tip, list)
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
    width:38px;
    height:38px;
    margin-left:6px
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
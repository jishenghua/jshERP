<!-- from 7 5 2 7 1 8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="4" :sm="24">
                <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select
                    mode="multiple" :maxTagCount="1"
                    optionFilterProp="children"
                    showSearch style="width: 100%"
                    placeholder="请选择仓库"
                    v-model="depotSelected">
                    <a-select-option v-for="(depot,index) in depotList" :value="depot.id">
                      {{ depot.depotName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="4" :sm="24">
                <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-month-picker placeholder="请选择月份" :default-value="moment(currentMonth, monthFormat)"
                        style="width:100%" :format="monthFormat" @change="onChange"/>
                </a-form-item>
              </a-col>
              <a-col :md="4" :sm="24">
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="条码/名称/规格/型号" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="5" :sm="24">
                <span class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" icon="printer">打印</a-button>
                  <a-button style="margin-left: 8px" @click="exportExcel" icon="download">导出</a-button>
                </span>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item>
                  <span>总结存金额：{{totalCountMoneyStr}}</span>
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
  import { getAction } from '@/api/manage'
  import { getMpListShort, openDownloadDialog, sheet2blob} from "@/utils/util"
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  import Vue from 'vue'
  export default {
    name: "InOutStockReport",
    mixins:[JeecgListMixin],
    components: {
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
          depotId:'',
          monthTime: moment().format('YYYY-MM'),
          materialParam:'',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        depotSelected:[],
        depotList: [],
        totalCountMoneyStr: '0元',
        disableMixinCreated: true,
        // 表头
        columns: [
          {
            title: '#', dataIndex: 'rowIndex', width:40, align:"center",
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '条码', dataIndex: 'barCode', width: 100},
          {title: '名称', dataIndex: 'materialName', width: 120},
          {title: '规格', dataIndex: 'materialStandard', width: 80},
          {title: '型号', dataIndex: 'materialModel', width: 80},
          {title: '扩展信息', dataIndex: 'materialOther', width: 80},
          {title: '单位', dataIndex: 'unitName', width: 60},
          {title: '单价', dataIndex: 'unitPrice', sorter: (a, b) => a.unitPrice - b.unitPrice, width: 60},
          {title: '上月结存数量', dataIndex: 'prevSum', sorter: (a, b) => a.prevSum - b.prevSum, width: 80},
          {title: '入库数量', dataIndex: 'inSum', sorter: (a, b) => a.inSum - b.inSum, width: 60},
          {title: '出库数量', dataIndex: 'outSum', sorter: (a, b) => a.outSum - b.outSum, width: 60},
          {title: '本月结存数量', dataIndex: 'thisSum', sorter: (a, b) => a.thisSum - b.thisSum, width: 80},
          {title: '结存金额', dataIndex: 'thisAllPrice', sorter: (a, b) => a.thisAllPrice - b.thisAllPrice, width: 60}
        ],
        url: {
          list: "/depotItem/findByAll",
          totalCountMoney: "/depotItem/totalCountMoney",
          exportXlsUrl: "/depotItem/exportExcel"
        }
      }
    },
    created() {
      this.getDepotData()
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
            let count = res.data.totalCount.toString();
            if (count.lastIndexOf('.') > -1) {
              count = count.substring(0, count.lastIndexOf('.') + 3);
            }
            this.totalCountMoneyStr = count + "元";
          }
        })
      },
      onChange: function (value, dateString) {
        console.log(dateString);
        this.queryParam.monthTime=dateString;
      },
      searchQuery() {
        if(this.depotSelected.length===0){
          this.$message.warning('请选择仓库！')
        } else if(this.queryParam.monthTime == ''){
          this.$message.warning('请选择月份！')
        } else {
          this.loadData(1);
          this.getTotalCountMoney();
        }
      },
      exportExcel() {
        let aoa = [['条码', '名称', '规格', '型号', '扩展信息', '单位', '单价', '上月结存数量', '入库数量', '出库数量', '本月结存数量', '结存金额']]
        for (let i = 0; i < this.dataSource.length; i++) {
          let ds = this.dataSource[i]
          let item = [ds.barCode, ds.materialName, ds.materialStandard, ds.materialModel, ds.materialOther, ds.unitName, ds.unitPrice,
            ds.prevSum, ds.inSum, ds.outSum, ds.thisSum, ds.thisAllPrice]
          aoa.push(item)
        }
        openDownloadDialog(sheet2blob(aoa), '进销存统计')
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
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
                    <a-select-option v-for="(depot,index) in depotList" :value="depot.id">
                      {{ depot.depotName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="条码/名称/规格/型号" v-model="queryParam.materialParam"></a-input>
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
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import {getAction} from '@/api/manage'
  import { getMpListShort, openDownloadDialog, sheet2blob} from "@/utils/util"
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
          depotId: '',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        depotList: [],
        tabKey: "1",
        // 表头
        columns: [
          {
            title: '#', dataIndex: 'rowIndex', width:40, align:"center",
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '仓库', dataIndex: 'depotName', width: 100},
          {title: '条码', dataIndex: 'barCode', width: 100},
          {title: '名称', dataIndex: 'mname', width: 100},
          {title: '规格', dataIndex: 'mstandard', width: 80},
          {title: '型号', dataIndex: 'mmodel', width: 80},
          {title: '扩展信息', dataIndex: 'materialOther', width: 100},
          {title: '单位', dataIndex: 'materialUnit', width: 60},
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
      exportExcel() {
        let aoa = [['仓库', '条码', '名称', '规格', '型号', '扩展信息', '单位', '库存', '最低安全库存', '最高安全库存', '建议入库量', '建议出库量']]
        for (let i = 0; i < this.dataSource.length; i++) {
          let ds = this.dataSource[i]
          let item = [ds.depotName, ds.barCode, ds.mname, ds.mstandard, ds.mmodel, ds.materialOther, ds.materialUnit,
            ds.currentNumber, ds.lowSafeStock, ds.highSafeStock, ds.lowCritical, ds.highCritical]
          aoa.push(item)
        }
        openDownloadDialog(sheet2blob(aoa), '库存预警')
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
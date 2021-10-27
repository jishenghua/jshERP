<!-- from 7 5 2 7 1 8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-month-picker placeholder="请选择月份" :default-value="moment(currentMonth, monthFormat)"
                         style="width:100%" :format="monthFormat" @change="onChange"/>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="条码、名称、规格、型号" v-model="queryParam.materialParam"></a-input>
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
                :show-total="(total, range) => `共 ${total-parseInt(total/ipagination.pageSize)-1} 条`">
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
  import { getMpListShort, openDownloadDialog, sheet2blob} from "@/utils/util"
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  import Vue from 'vue'
  export default {
    name: "BuyInReport",
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
          monthTime: moment().format('YYYY-MM'),
          materialParam:'',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        tabKey: "1",
        // 表头
        columns: [
          {
            title: '#', dataIndex: 'rowIndex', width:40, align:"center",
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '条码', dataIndex: 'barCode', width: 160},
          {title: '名称', dataIndex: 'materialName', width: 160},
          {title: '规格', dataIndex: 'materialStandard', width: 80},
          {title: '型号', dataIndex: 'materialModel', width: 80},
          {title: '扩展信息', dataIndex: 'materialOther', width: 150},
          {title: '单位', dataIndex: 'materialUnit', width: 80},
          {title: '进货数量', dataIndex: 'inSum', sorter: (a, b) => a.inSum - b.inSum, width: 80},
          {title: '进货金额', dataIndex: 'inSumPrice', sorter: (a, b) => a.inSumPrice - b.inSumPrice, width: 80},
          {title: '退货数量', dataIndex: 'outSum', sorter: (a, b) => a.outSum - b.outSum, width: 80},
          {title: '退货金额', dataIndex: 'outSumPrice', sorter: (a, b) => a.outSumPrice - b.outSumPrice, width: 80}
        ],
        url: {
          list: "/depotItem/buyIn",
        }
      }
    },
    methods: {
      moment,
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.monthTime = this.queryParam.monthTime;
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize-1;
        return param;
      },
      onChange: function (value, dateString) {
        this.queryParam.monthTime=dateString;
      },
      searchQuery() {
        if(this.queryParam.monthTime == ''){
          this.$message.warning('请选择月份！')
        } else {
          this.loadData(1);
        }
      },
      exportExcel() {
        let aoa = [['条码', '名称', '规格', '型号', '扩展信息', '单位', '进货数量', '进货金额', '退货数量', '退货金额']]
        for (let i = 0; i < this.dataSource.length; i++) {
          let ds = this.dataSource[i]
          let item = [ds.barCode, ds.materialName, ds.materialStandard, ds.materialModel, ds.materialOther, ds.materialUnit,
            ds.inSum, ds.inSumPrice, ds.outSum, ds.outSumPrice]
          aoa.push(item)
        }
        openDownloadDialog(sheet2blob(aoa), '进货统计')
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
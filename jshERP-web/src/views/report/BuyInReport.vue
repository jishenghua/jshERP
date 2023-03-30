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
                  <a-input placeholder="条码/名称/规格/型号" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-range-picker
                    style="width: 100%"
                    v-model="queryParam.createTimeRange"
                    :default-value="defaultTimeStr"
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
                  <span>实际采购金额：{{realityPriceTotal}}</span>
                </a-form-item>
              </a-col>
              <template v-if="toggleSearchStatus">
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
  import { getNowFormatYear, getMpListShort, openDownloadDialog, sheet2blob} from "@/utils/util"
  import {getAction} from '@/api/manage'
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
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        queryParam: {
          materialParam:'',
          beginTime: getNowFormatYear() + '-01-01',
          endTime: moment().format('YYYY-MM-DD'),
          depotId: '',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList')),
          roleType: Vue.ls.get('roleType'),
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        dateFormat: 'YYYY-MM-DD',
        currentDay: moment().format('YYYY-MM-DD'),
        defaultTimeStr: '',
        depotList: [],
        realityPriceTotal: '',
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
          {title: '名称', dataIndex: 'materialName', width: 160, ellipsis:true},
          {title: '规格', dataIndex: 'materialStandard', width: 80, ellipsis:true},
          {title: '型号', dataIndex: 'materialModel', width: 80, ellipsis:true},
          {title: '扩展信息', dataIndex: 'materialOther', width: 150, ellipsis:true},
          {title: '单位', dataIndex: 'materialUnit', width: 80, ellipsis:true},
          {title: '采购数量', dataIndex: 'inSum', sorter: (a, b) => a.inSum - b.inSum, width: 80},
          {title: '采购金额', dataIndex: 'inSumPrice', sorter: (a, b) => a.inSumPrice - b.inSumPrice, width: 80},
          {title: '退货数量', dataIndex: 'outSum', sorter: (a, b) => a.outSum - b.outSum, width: 80},
          {title: '退货金额', dataIndex: 'outSumPrice', sorter: (a, b) => a.outSumPrice - b.outSumPrice, width: 80},
          {title: '实际采购金额', dataIndex: 'inOutSumPrice', sorter: (a, b) => a.inOutSumPrice - b.inOutSumPrice, width: 100}
        ],
        url: {
          list: "/depotItem/buyIn"
        }
      }
    },
    created () {
      this.getDepotData()
      this.defaultTimeStr = [moment(getNowFormatYear() + '-01-01', this.dateFormat), moment(this.currentDay, this.dateFormat)]
    },
    mounted () {
      this.scroll.x = 1620
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
      onDateChange: function (value, dateString) {
        console.log(dateString[0],dateString[1]);
        this.queryParam.beginTime=dateString[0];
        this.queryParam.endTime=dateString[1];
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
            this.realityPriceTotal = res.data.realityPriceTotal
          }
          if(res.code===510){
            this.$message.warning(res.data)
          }
          this.loading = false;
        })
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
      searchQuery() {
        if(this.queryParam.beginTime == '' || this.queryParam.endTime == ''){
          this.$message.warning('请选择单据日期！')
        } else {
          this.loadData(1);
        }
      },
      exportExcel() {
        let aoa = [['条码', '名称', '规格', '型号', '扩展信息', '单位', '进货数量', '进货金额', '退货数量', '退货金额', '实际采购金额']]
        for (let i = 0; i < this.dataSource.length; i++) {
          let ds = this.dataSource[i]
          let item = [ds.barCode, ds.materialName, ds.materialStandard, ds.materialModel, ds.materialOther, ds.materialUnit,
            ds.inSum, ds.inSumPrice, ds.outSum, ds.outSumPrice, ds.inOutSumPrice]
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
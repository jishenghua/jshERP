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
                <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select placeholder="选择客户" v-model="queryParam.organId"
                    :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                    <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                      {{ item.supplier }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="5" :sm="24">
                <a-form-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-range-picker
                    style="width: 210px"
                    v-model="queryParam.createTimeRange"
                    :default-value="defaultTimeStr"
                    format="YYYY-MM-DD"
                    :placeholder="['开始时间', '结束时间']"
                    @change="onDateChange"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="4" :sm="24">
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" icon="printer">打印</a-button>
                  <a-button style="margin-left: 8px" @click="exportExcel" icon="download">导出</a-button>
                </span>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item>
                  {{firstTotal}} {{lastTotal}}
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
        <bill-detail ref="modalBillDetail"></bill-detail>
        <financial-detail ref="modalFinancialDetail"></financial-detail>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import BillDetail from '../bill/dialog/BillDetail'
  import FinancialDetail from '../financial/dialog/FinancialDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getNowFormatMonth, openDownloadDialog, sheet2blob} from "@/utils/util"
  import { getAction } from '@/api/manage'
  import {findBySelectCus, findBillDetailByNumber, findFinancialDetailByNumber} from '@/api/api'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  export default {
    name: "CustomerAccount",
    mixins:[JeecgListMixin],
    components: {
      BillDetail,
      FinancialDetail,
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
          supType: "客户",
          organId: '',
          beginTime: getNowFormatMonth() + '-01',
          endTime: moment().format('YYYY-MM-DD'),
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        dateFormat: 'YYYY-MM-DD',
        currentDay: moment().format('YYYY-MM-DD'),
        defaultTimeStr: '',
        supList: [],
        firstTotal: '',
        lastTotal: '',
        tabKey: "1",
        // 表头
        columns: [
          {
            title: '#', dataIndex: 'rowIndex', width:40, align:"center",
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {
            title: '单据编号', dataIndex: 'number', width: 140,
            scopedSlots: { customRender: 'numberCustomRender' },
          },
          {title: '类型', dataIndex: 'type', width: 100},
          {title: '单位名称', dataIndex: 'supplierName', width: 200},
          {title: '单据金额', dataIndex: 'billMoney', width: 80},
          {title: '实际支付', dataIndex: 'changeAmount', width: 80},
          {title: '本期变化', dataIndex: 'allPrice', width: 80},
          {title: '单据日期', dataIndex: 'oTime', width: 160}
        ],
        url: {
          list: "/depotHead/findStatementAccount",
        }
      }
    },
    created () {
      this.initSupplier()
      this.defaultTimeStr = [moment(getNowFormatMonth() + '-01', this.dateFormat), moment(this.currentDay, this.dateFormat)]
    },
    methods: {
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize-1;
        return param;
      },
      initSupplier() {
        let that = this;
        findBySelectCus({}).then((res)=>{
          if(res) {
            that.supList = res;
          }
        });
      },
      onDateChange: function (value, dateString) {
        console.log(dateString[0],dateString[1]);
        this.queryParam.beginTime=dateString[0];
        this.queryParam.endTime=dateString[1];
      },
      myHandleDetail(record) {
        if(record.type === '收入' || record.type === '收款') {
          findFinancialDetailByNumber({ billNo: record.number }).then((res) => {
            if (res && res.code === 200) {
              this.$refs.modalFinancialDetail.show(res.data, record.type);
              this.$refs.modalFinancialDetail.title="详情";
            }
          })
        } else {
          findBillDetailByNumber({ number: record.number }).then((res) => {
            if (res && res.code === 200) {
              this.$refs.modalBillDetail.show(res.data, record.type);
              this.$refs.modalBillDetail.title="详情";
            }
          })
        }
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
            if(this.queryParam.organId) {
              this.firstTotal = '期初应收：' + res.data.firstMoney + "，"
              this.lastTotal = '期末应收：' + res.data.lastMoney
            }
          }
          if(res.code===510){
            this.$message.warning(res.data)
          }
          this.loading = false;
        })
      },
      searchQuery() {
        if(this.queryParam.beginTime === '' || this.queryParam.endTime === ''){
          this.$message.warning('请选择单据日期！')
        } else {
          this.loadData(1);
        }
      },
      exportExcel() {
        let aoa = [['单据编号', '类型', '单位名称', '单据金额', '实际支付', '本期变化', '单据日期']]
        for (let i = 0; i < this.dataSource.length; i++) {
          let ds = this.dataSource[i]
          let item = [ds.number, ds.type, ds.supplierName, ds.billMoney, ds.changeAmount, ds.allPrice, ds.oTime]
          aoa.push(item)
        }
        openDownloadDialog(sheet2blob(aoa), '客户对账')
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
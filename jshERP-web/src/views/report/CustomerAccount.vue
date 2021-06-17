<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="4" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                  <a-select placeholder="选择客户" v-model="queryParam.organId" :dropdownMatchSelectWidth="false">
                    <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                      {{ item.supplier }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
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
              <a-col :md="2" :sm="24">
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                </span>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item>
                  {{firstTotal}} {{lastTotal}} {{pleaseSelect}}
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </div>
        <!-- table区域-begin -->
        <a-table
          bordered
          ref="table"
          size="middle"
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          @change="handleTableChange">
          <span slot="numberCustomRender" slot-scope="text, record">
            <a @click="myHandleDetail(record)">{{record.number}}</a>
          </span>
        </a-table>
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
  import { getNowFormatMonth } from '@/utils/util';
  import {findBySelectCus, findSupplierById, findDepotHeadTotalPay, findAccountHeadTotalPay, findBillDetailByNumber, findFinancialDetailByNumber} from '@/api/api'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  export default {
    name: "BuyInReport",
    mixins:[JeecgListMixin],
    components: {
      BillDetail,
      FinancialDetail,
      JEllipsis
    },
    data () {
      return {
        // 查询条件
        queryParam: {
          supType: "客户",
          organId: '',
          beginTime: getNowFormatMonth() + '-01',
          endTime: moment().format('YYYY-MM-DD'),
        },
        dateFormat: 'YYYY-MM-DD',
        currentDay: moment().format('YYYY-MM-DD'),
        defaultTimeStr: '',
        supList: [],
        firstTotal: '',
        lastTotal: '',
        pleaseSelect: '（请选择客户）',
        tabKey: "1",
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:40,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
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
        labelCol: {
          xs: { span: 1 },
          sm: { span: 2 },
        },
        wrapperCol: {
          xs: { span: 10 },
          sm: { span: 16 },
        },
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
        param.pageSize = this.ipagination.pageSize;
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
      initTotal(prefix, time, type) {
        findSupplierById({supplierId: this.queryParam.organId}).then((res)=>{
          if (res && res.data && res.data[0]) {
            let beginNeedGet = res.data[0].BeginNeedGet;
            let beginNeedPay = res.data[0].BeginNeedPay;
            findDepotHeadTotalPay({supplierId: this.queryParam.organId, endTime: time, supType: "customer" }).then((res)=>{
              if (res && res.code === 200 && res.data && res.data.rows) {
                let moneyA = res.data.rows.getAllMoney.toFixed(2) - 0;
                findAccountHeadTotalPay({supplierId: this.queryParam.organId, endTime: time, supType: "customer" }).then((res)=>{
                  if (res && res.code === 200 && res.data && res.data.rows) {
                    let moneyB = res.data.rows.getAllMoney.toFixed(2) - 0;
                    let money = moneyA + moneyB;
                    let moneyBeginNeedGet = beginNeedGet - 0; //期初应收
                    let moneyBeginNeedPay = beginNeedPay - 0; //期初应付
                    money = (money + moneyBeginNeedGet - moneyBeginNeedPay).toFixed(2);
                    if(type === 'first') {
                      this.firstTotal = prefix + money + "，"
                    } else if(type === 'last') {
                      this.lastTotal = prefix + money
                    }
                  }
                })
              }
            })
          }
        })
      },
      initStatistics() {
        if(this.queryParam.organId) {
          this.initTotal('期初应收：', this.queryParam.beginTime, 'first')
          this.initTotal('期末应收：', this.queryParam.endTime, 'last')
          this.pleaseSelect = ''
        }
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
      searchQuery() {
        if(this.queryParam.beginTime == '' || this.queryParam.endTime == ''){
          this.$message.warning('请选择单据日期！')
        } else {
          this.loadData(1);
          this.initStatistics();
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
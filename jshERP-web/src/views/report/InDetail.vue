<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="4" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                  <a-select placeholder="选择供应商" v-model="queryParam.organId"
                    :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                    <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                      {{ item.supplier }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="4" :sm="24">
                <a-form-item label="仓库">
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
              <a-col :md="5" :sm="24">
                <a-form-item label="商品信息">
                  <a-input placeholder="名称、规格、型号" v-model="queryParam.materialParam"></a-input>
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
              <a-col :md="4" :sm="24" >
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" type="primary" icon="printer">打印</a-button>
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
            :pagination="ipagination"
            :loading="loading"
            @change="handleTableChange">
            <span slot="numberCustomRender" slot-scope="text, record">
              <a @click="myHandleDetail(record)">{{record.number}}</a>
            </span>
          </a-table>
        </section>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <bill-detail ref="modalDetail"></bill-detail>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import BillDetail from '../bill/dialog/BillDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getNowFormatMonth } from '@/utils/util';
  import {getAction} from '@/api/manage'
  import {findBySelectSup, findBillDetailByNumber} from '@/api/api'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  export default {
    name: "BuyInReport",
    mixins:[JeecgListMixin],
    components: {
      BillDetail,
      JEllipsis
    },
    data () {
      return {
        // 查询条件
        queryParam: {
          organId: '',
          materialParam:'',
          depotId: '',
          beginTime: getNowFormatMonth() + '-01',
          endTime: moment().format('YYYY-MM-DD'),
          type: "入库"
        },
        dateFormat: 'YYYY-MM-DD',
        currentDay: moment().format('YYYY-MM-DD'),
        defaultTimeStr: '',
        supList: [],
        depotList: [],
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
          {title: '名称', dataIndex: 'mname', width: 120},
          {title: '规格', dataIndex: 'standard', width: 100},
          {title: '型号', dataIndex: 'model', width: 100},
          {title: '单价', dataIndex: 'unitPrice', width: 60},
          {title: '入库数量', dataIndex: 'operNumber', width: 80},
          {title: '金额', dataIndex: 'allPrice', width: 60},
          {title: '供应商', dataIndex: 'sname', width: 200},
          {title: '仓库', dataIndex: 'dname', width: 120},
          {title: '入库日期', dataIndex: 'operTime', width: 120}
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
          list: "/depotHead/findInDetail",
        }
      }
    },
    created () {
      this.getDepotData()
      this.initSupplier()
      this.defaultTimeStr = [moment(getNowFormatMonth() + '-01', this.dateFormat), moment(this.currentDay, this.dateFormat)]
    },
    methods: {
      moment,
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return param;
      },
      onDateChange: function (value, dateString) {
        console.log(dateString[0],dateString[1]);
        this.queryParam.beginTime=dateString[0];
        this.queryParam.endTime=dateString[1];
      },
      initSupplier() {
        let that = this;
        findBySelectSup({}).then((res)=>{
          if(res) {
            that.supList = res;
          }
        });
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
      myHandleDetail(record) {
        findBillDetailByNumber({ number: record.number }).then((res) => {
          if (res && res.code === 200) {
            this.handleDetail(res.data, record.newType);
          }
        })
      },
      searchQuery() {
        if(this.queryParam.beginTime == '' || this.queryParam.endTime == ''){
          this.$message.warning('请选择单据日期！')
        } else {
          this.loadData(1);
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
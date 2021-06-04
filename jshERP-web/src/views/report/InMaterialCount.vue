<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="4" :sm="24">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
              <a-select placeholder="选择供应商" v-model="queryParam.organId" :dropdownMatchSelectWidth="false">
                <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                  {{ item.supplier }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="24">
            <a-form-item label="仓库">
              <a-select
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
          <a-col :md="4" :sm="24" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery">查询</a-button>
            </span>
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
    </a-table>
    <!-- table区域-end -->
  </a-card>
</template>
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getNowFormatMonth } from '@/utils/util';
  import {getAction} from '@/api/manage'
  import {findBySelectSup} from '@/api/api'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  export default {
    name: "BuyInReport",
    mixins:[JeecgListMixin],
    components: {
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
          {title: '名称', dataIndex: 'mName', width: 150},
          {title: '规格', dataIndex: 'standard', width: 100},
          {title: '型号', dataIndex: 'model', width: 100},
          {title: '类型', dataIndex: 'categoryName', width: 120},
          {title: '入库数量', dataIndex: 'numSum', width: 120},
          {title: '入库金额', dataIndex: 'priceSum', width: 120}
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
          list: "/depotHead/findInOutMaterialCount",
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
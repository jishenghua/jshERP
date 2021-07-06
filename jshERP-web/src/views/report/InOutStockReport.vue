<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
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
              <a-col :md="4" :sm="24">
                <a-form-item label="月份">
                  <a-month-picker placeholder="请选择月份" :default-value="moment(currentMonth, monthFormat)" :format="monthFormat" @change="onChange"/>
                </a-form-item>
              </a-col>
              <a-col :md="5" :sm="24">
                <a-form-item label="商品信息">
                  <a-input placeholder="名称、规格、型号" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="4" :sm="24" >
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" type="primary" icon="download" @click="handleExportXls('库存状况')">导出</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" type="primary" icon="printer">打印</a-button>
                </span>
              </a-col>
              <a-col :md="4" :sm="24" >
                <a-form-item label="本月合计金额">
                  {{totalCountMoneyStr}}
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
            :pagination="ipagination"
            :loading="loading"
            @change="handleTableChange">
          </a-table>
        </section>
        <!-- table区域-end -->
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction } from '@/api/manage'
  import { getMpListShort } from "@/utils/util"
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
        queryParam: {
          depotId:'',
          monthTime: moment().format('YYYY-MM'),
          materialParam:'',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        tabKey: "1",
        depotList: [],
        totalCountMoneyStr: '',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {title: '名称', dataIndex: 'materialName', width: 160},
          {title: '规格', dataIndex: 'materialStandard', width: 80},
          {title: '型号', dataIndex: 'materialModel', width: 80},
          {title: '扩展信息', dataIndex: 'materialOther', width: 120},
          {title: '单位', dataIndex: 'unitName', width: 80},
          {title: '单价', dataIndex: 'unitPrice', width: 60},
          {title: '上月结存数量', dataIndex: 'prevSum', width: 120},
          {title: '入库数量', dataIndex: 'inSum', width: 80},
          {title: '出库数量', dataIndex: 'outSum', width: 80},
          {title: '本月结存数量', dataIndex: 'thisSum', width: 120},
          {title: '结存金额', dataIndex: 'thisAllPrice', width: 80}
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
          list: "/depotItem/findByAll",
          totalCountMoney: "/depotItem/totalCountMoney",
          exportXlsUrl: "/depotItem/exportExcel",
        }
      }
    },
    created() {
      this.getDepotData()
      this.getTotalCountMoney()
    },
    methods: {
      moment,
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.monthTime = this.queryParam.monthTime;
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
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
        getAction(this.url.totalCountMoney, this.queryParam).then((res)=>{
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
        if(this.queryParam.monthTime == ''){
          this.$message.warning('请选择月份！')
        } else {
          this.loadData(1);
          this.getTotalCountMoney();
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
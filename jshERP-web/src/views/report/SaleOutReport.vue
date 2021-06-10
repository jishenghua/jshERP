<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="月份">
                  <a-month-picker placeholder="请选择月份" :default-value="moment(currentMonth, monthFormat)" :format="monthFormat" @change="onChange"/>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="商品信息">
                  <a-input placeholder="请输入商品信息" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="3" :sm="24" >
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
    </a-col>
  </a-row>
</template>
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getMpListShort } from "@/utils/util"
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  import Vue from 'vue'
  export default {
    name: "SaleOutReport",
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
          monthTime: moment().format('YYYY-MM'),
          materialParam:'',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))
        },
        tabKey: "1",
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
          {title: '扩展信息', dataIndex: 'materialOther', width: 150},
          {title: '单位', dataIndex: 'materialUnit', width: 80},
          {title: '销售数量', dataIndex: 'outSum', width: 80},
          {title: '销售金额', dataIndex: 'outSumPrice', width: 80},
          {title: '退货数量', dataIndex: 'inSum', width: 80},
          {title: '退货金额', dataIndex: 'inSumPrice', width: 80},
          {title: '实际销售金额', dataIndex: 'outInSumPrice', width: 100}
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
          list: "/depotItem/saleOut"
        }
      }
    },
    methods: {
      moment,
      create(){
      },
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.monthTime = this.queryParam.monthTime;
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return param;
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
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
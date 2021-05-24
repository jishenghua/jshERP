<template>
  <a-card :bordered="false" class="card-area">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!-- 搜索区域 -->
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="单据编号" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="请输入单据编号" v-model="queryParam.billNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="10">
            <a-form-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-range-picker
                style="width: 210px"
                v-model="queryParam.createTimeRange"
                format="YYYY-MM-DD"
                :placeholder="['开始时间', '结束时间']"
                @change="onDateChange"
                @ok="onDateOk"
              />
            </a-form-item>
          </a-col>
          <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
            <a-col :md="6" :sm="24">
              <a-button type="primary" @click="searchQuery">查询</a-button>
              <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
            </a-col>
          </span>
        </a-row>
      </a-form>
    </div>
    <!-- 操作按钮区域 -->
    <div class="table-operator"  style="margin-top: 5px">
      <a-button v-if="btnEnableList.indexOf(1)>-1" @click="myHandleAdd" type="primary" icon="plus">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" v-if="btnEnableList.indexOf(1)>-1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作 <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>
    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        <span slot="action" slot-scope="text, record">
          <a @click="myHandleDetail(record, '收预付款')">查看</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
          <a v-if="btnEnableList.indexOf(1)>-1" @click="myHandleEdit(record)">编辑</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
          <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <advance-in-modal ref="modalForm" @ok="modalFormOk"></advance-in-modal>
    <financial-detail ref="modalDetail"></financial-detail>
  </a-card>
</template>
<script>
  import AdvanceInModal from './modules/AdvanceInModal'
  import FinancialDetail from './dialog/FinancialDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { FinancialListMixin } from './mixins/FinancialListMixin'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "AdvanceInList",
    mixins:[JeecgListMixin, FinancialListMixin],
    components: {
      AdvanceInModal,
      FinancialDetail,
      JDate
    },
    data () {
      return {
        // 查询条件
        queryParam: {
          billNo: "",
          searchMaterial: "",
          type: "收预付款"
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
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
          { title: '付款会员', dataIndex: 'organName',width:140},
          { title: '单据编号', dataIndex: 'billNo',width:160},
          { title: '操作员', dataIndex: 'userName',width:80},
          { title: '单据日期 ', dataIndex: 'billTimeStr',width:160},
          { title: '合计', dataIndex: 'totalPrice',width:80},
          { title: '备注', dataIndex: 'remark',width:200},
          {
            title: '操作',
            dataIndex: 'action',
            width:200,
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/accountHead/list",
          delete: "/accountHead/delete",
          deleteBatch: "/accountHead/deleteBatch"
        }
      }
    },
    computed: {

    },
    methods: {

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
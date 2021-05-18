<template>
  <a-card :bordered="false" class="card-area">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!-- 搜索区域 -->
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="序列号" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="请输入序列号查询" v-model="queryParam.serialNumber"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="商品名称" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="请输入商品名称查询" v-model="queryParam.materialName"></a-input>
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
      <a-button v-if="btnEnableList.indexOf(1)>-1" @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button v-if="btnEnableList.indexOf(1)>-1" @click="handleBatchAdd" type="primary" icon="plus">批量新增</a-button>
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
          <a @click="handleEdit(record)">编辑</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
          <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
        <!-- 状态渲染模板 -->
        <template slot="customRenderFlag" slot-scope="isSell">
          <a-tag v-if="isSell==1" color="green">是</a-tag>
          <a-tag v-if="isSell==0" color="orange">否</a-tag>
        </template>
        <template slot="customRenderTime" slot-scope="timeStr">
          {{simpleDateFormat(timeStr, 'yyyy-MM-dd hh:mm:ss')}}
        </template>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <serial-number-modal ref="modalForm" @ok="modalFormOk"></serial-number-modal>
    <serial-number-batch-modal ref="serialNumberBatchModel" @ok="modalFormOk"></serial-number-batch-modal>
  </a-card>
</template>
<script>
  import SerialNumberModal from './modules/SerialNumberModal'
  import SerialNumberBatchModal from './modules/SerialNumberBatchModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { formatDate } from "@/utils/util"
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "SerialNumberList",
    mixins:[JeecgListMixin],
    components: {
      SerialNumberModal,
      SerialNumberBatchModal,
      JDate
    },
    data () {
      return {
        // 查询条件
        queryParam: {materialName:'',serialNumber:''},
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
          {title: '序列号',align: "left", dataIndex: 'serialNumber', width: 180},
          {title: '商品条码', align: "center",dataIndex: 'materialCode', width: 120},
          {title: '商品名称', align: "center",dataIndex: 'materialName', width: 120},
          {title: '单据编号', align: "center", dataIndex: 'depotHeadNumber', width: 140},
          {title: '已卖出', align: "center", dataIndex: 'isSell', width: 60,
            scopedSlots: { customRender: 'customRenderFlag' }
          },
          {title: '创建时间',align: "center",  dataIndex: 'createTime', width: 150,
            scopedSlots: { customRender: 'customRenderTime' }
          },
          {title: '更新时间', align: "center", dataIndex: 'updateTime', width: 150,
            scopedSlots: { customRender: 'customRenderTime' }
          },
          {title: '备注',align: "center",  dataIndex: 'remark',width: 140},
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/serialNumber/list",
          delete: "/serialNumber/delete",
          deleteBatch: "/serialNumber/deleteBatch"
        }
      }
    },
    computed: {

    },
    methods: {
      handleBatchAdd() {
        this.$refs.serialNumberBatchModel.add();
        this.$refs.serialNumberBatchModel.title = "批量新增";
        this.$refs.serialNumberBatchModel.disableSubmit = false;
      },
      simpleDateFormat(millisecond, format) {
        return formatDate(millisecond, format)
      },
      handleEdit: function (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
        if(this.btnEnableList.indexOf(1)===-1) {
          this.$refs.modalForm.isReadOnly = true
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
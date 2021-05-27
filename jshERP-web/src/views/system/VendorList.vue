<template>
  <a-card :bordered="false" class="card-area">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!-- 搜索区域 -->
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="5" :sm="10">
            <a-form-item label="名称" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="请输入名称查询" v-model="queryParam.supplier"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="10">
            <a-form-item label="手机号码" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="请输入手机号码查询" v-model="queryParam.telephone"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="10">
            <a-form-item label="联系电话" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="请输入联系电话查询" v-model="queryParam.phonenum"></a-input>
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
      <a-button v-if="btnEnableList.indexOf(1)>-1" type="primary" icon="plus" @click="handleAdd">新增</a-button>
      <a-upload v-if="btnEnableList.indexOf(1)>-1" name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-popover title="导入注意点">
          <template slot="content">
            <p>预收款、期初应收、期初应付、税率均为数值且要大于0；<br/>另外期初应收、期初应付不能同时输入</p>
          </template>
          <a-button type="primary" icon="import">导入</a-button>
        </a-popover>
      </a-upload>
      <a-button type="primary" icon="download" @click="handleExportXls('供应商信息')">导出</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" v-if="btnEnableList.indexOf(1)>-1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
          <a-menu-item key="2" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(true)"><a-icon type="check-square"/>启用</a-menu-item>
          <a-menu-item key="3" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(false)"><a-icon type="close-square"/>禁用</a-menu-item>
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
        <template slot="customRenderFlag" slot-scope="enabled">
          <a-tag v-if="enabled" color="green">启用</a-tag>
          <a-tag v-if="!enabled" color="orange">禁用</a-tag>
        </template>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <vendor-modal ref="modalForm" @ok="modalFormOk"></vendor-modal>
  </a-card>
</template>
<script>
  import VendorModal from './modules/VendorModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "VendorList",
    mixins:[JeecgListMixin],
    components: {
      VendorModal,
      JDate
    },
    data () {
      return {
        // 查询条件
        queryParam: {
          supplier:'',
          type:'供应商',
          telephone:'',
          phonenum:''
        },
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
          { title: '名称',dataIndex: 'supplier',width:150},
          { title: '联系人', dataIndex: 'contacts',width:70,align:"center"},
          { title: '手机号码', dataIndex: 'telephone',width:110,align:"center"},
          { title: '联系电话', dataIndex: 'phoneNum',width:100,align:"center"},
          { title: '预付款',dataIndex: 'advanceIn',width:70,align:"center"},
          { title: '期初应收',dataIndex: 'beginNeedGet',width:80,align:"center"},
          { title: '期初应付',dataIndex: 'beginNeedPay',width:80,align:"center"},
          { title: '期末应收',dataIndex: 'allNeedGet',width:80,align:"center"},
          { title: '期末应付',dataIndex: 'allNeedPay',width:80,align:"center"},
          { title: '税率(%)', dataIndex: 'taxRate',width:80,align:"center"},
          { title: '状态',dataIndex: 'enabled',width:70,align:"center",
            scopedSlots: { customRender: 'customRenderFlag' }
          },
          {
            title: '操作',
            dataIndex: 'action',
            width: 200,
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/supplier/list",
          delete: "/supplier/delete",
          deleteBatch: "/supplier/deleteBatch",
          importExcelUrl: "/supplier/importExcel",
          exportXlsUrl: "/supplier/exportExcel",
          batchSetStatusUrl: "/supplier/batchSetStatus"
        }
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}${this.url.importExcelUrl}`;
      }
    },
    methods: {
      searchReset() {
        this.queryParam = {
          type:'供应商',
        }
        this.loadData(1);
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
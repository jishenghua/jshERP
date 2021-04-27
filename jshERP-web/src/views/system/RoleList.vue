<template>
  <a-card :bordered="false" class="card-area">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!-- 搜索区域 -->
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="角色名称" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="请输入角色名称查询" v-model="queryParam.name"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
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
          <a @click="handleSetFunction(record)">分配功能</a>
          <a-divider type="vertical" />
          <a @click="handleSetPushBtn(record)">分配按钮</a>
          <a-divider type="vertical" />
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <role-modal ref="modalForm" @ok="modalFormOk"></role-modal>
    <role-function-modal ref="roleFunctionModal" @ok="modalFormOk"></role-function-modal>
    <role-push-btn-modal ref="rolePushBtnModal" @ok="modalFormOk"></role-push-btn-modal>
  </a-card>
</template>
<script>
  import RoleModal from './modules/RoleModal'
  import RoleFunctionModal from './modules/RoleFunctionModal'
  import RolePushBtnModal from './modules/RolePushBtnModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "RoleList",
    mixins:[JeecgListMixin],
    components: {
      RoleModal,
      RoleFunctionModal,
      RolePushBtnModal,
      JDate
    },
    data () {
      return {
        description: '角色管理页面',
        // 查询条件
        queryParam: {name:'',},
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
            title: '角色名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title: '数据类型',
            align:"center",
            dataIndex: 'type'
          },
          {
            title: '描述',
            align:"center",
            dataIndex: 'description'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/role/list",
          delete: "/role/delete",
          deleteBatch: "/role/deleteBatch"
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleSetFunction(record) {
        this.$refs.roleFunctionModal.edit(record);
        this.$refs.roleFunctionModal.title = "分配功能";
        this.$refs.roleFunctionModal.disableSubmit = false;
      },
      handleSetPushBtn(record) {
        this.$refs.rolePushBtnModal.edit(record);
        this.$refs.rolePushBtnModal.title = "分配按钮";
        this.$refs.rolePushBtnModal.disableSubmit = false;
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
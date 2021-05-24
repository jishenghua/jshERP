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
      <a-button v-if="btnEnableList.indexOf(1)>-1" @click="handleAdd" type="primary" icon="plus">新增</a-button>
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
          <a v-if="btnEnableList.indexOf(1)>-1" @click="handleSetFunction(record)">分配功能</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
          <a v-if="btnEnableList.indexOf(1)>-1" @click="handleSetPushBtn(record.id)">分配按钮</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
          <a @click="handleEdit(record)">编辑</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
          <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <role-modal ref="modalForm" @ok="modalFormOk"></role-modal>
    <role-function-modal ref="roleFunctionModal" @ok="roleFunctionModalFormOk"></role-function-modal>
    <role-push-btn-modal ref="rolePushBtnModal" @ok="modalFormOk"></role-push-btn-modal>
    <a-modal v-model="roleFunctionModalVisible" title="操作提示" @ok="handleTipOk">
      <p>分配功能已经操作成功！现在继续<b>分配按钮</b>吗？</p>
    </a-modal>
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
        roleFunctionModalVisible: false,
        currentRoleId: '',
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
            dataIndex: 'name',
            width: 100
          },
          {
            title: '数据类型',
            align:"center",
            dataIndex: 'type',
            width: 100
          },
          {
            title: '描述',
            align:"center",
            dataIndex: 'description',
            width: 100
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width: 150,
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
        this.$refs.roleFunctionModal.title = "分配功能【分配之后请继续分配按钮】";
        this.$refs.roleFunctionModal.disableSubmit = false;
      },
      handleSetPushBtn(roleId) {
        this.$refs.rolePushBtnModal.edit(roleId);
        this.$refs.rolePushBtnModal.title = "分配按钮";
        this.$refs.rolePushBtnModal.disableSubmit = false;
      },
      roleFunctionModalFormOk(id) {
        //重载列表
        this.loadData();
        this.roleFunctionModalVisible = true;
        this.currentRoleId = id
      },
      handleTipOk() {
        if(this.currentRoleId) {
          this.roleFunctionModalVisible = false;
          this.handleSetPushBtn(this.currentRoleId)
        }
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
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="角色名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入角色名称查询" v-model="queryParam.name"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入备注查询" v-model="queryParam.description"></a-input>
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
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="batchDel" icon="delete">删除</a-button>
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(true)" icon="check-square">启用</a-button>
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(false)" icon="close-square">禁用</a-button>
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
            :scroll="scroll"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="handleSetFunction(record)">分配功能</a>
              <a-divider type="vertical" />
              <a @click="handleSetPushBtn(record.id, record.name)">分配按钮</a>
              <a-divider type="vertical" />
              <a @click="handleEdit(record)">编辑</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>
              <a-modal v-model="roleModalVisible" title="操作提示" @ok="handleRoleTip(record)">
                <p>保存角色已经操作成功！现在继续<b>分配功能</b>吗？</p>
              </a-modal>
            </span>
            <span slot="typeTitle">
              数据类型
              <a-tooltip title="1、全部数据-该角色对应的用户可以看到全部单据；2、本机构数据-该角色对应的用户可以看到自己所在机构的全部单据；
                3、个人数据-该角色对应的用户只可以看到自己的单据。单据是指采购入库、销售出库等">
                <a-icon type="question-circle" />
              </a-tooltip>
            </span>
            <span slot="priceLimitTitle">
              价格屏蔽
              <a-tooltip title="价格屏蔽支持多选，主要用于控制首页和单据的价格屏蔽">
                <a-icon type="question-circle" />
              </a-tooltip>
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
        <role-modal ref="modalForm" @ok="roleModalFormOk"></role-modal>
        <role-function-modal ref="roleFunctionModal" @ok="roleFunctionModalFormOk"></role-function-modal>
        <role-push-btn-modal ref="rolePushBtnModal" @ok="modalFormOk"></role-push-btn-modal>
        <a-modal v-model="roleFunctionModalVisible" title="操作提示" @ok="handleRoleFunctionTip">
          <p>分配功能已经操作成功！现在继续<b>分配按钮</b>吗？</p>
        </a-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- f r o m 7 5  2 7 1  8 9 2 0 -->
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
        roleModalVisible: false,
        roleFunctionModalVisible: false,
        currentRoleId: '',
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {
          name: '',
          description: '',
        },
        urlPath: '/system/role',
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width: 180,
            scopedSlots: { customRender: 'action' },
          },
          {
            title: '角色名称', align:"left", dataIndex: 'name', width: 120
          },
          {
            align:"left", dataIndex: 'type', width: 100,
            slots: { title: 'typeTitle' }
          },
          {
            align:"left", dataIndex: 'priceLimitStr', width: 300,
            slots: { title: 'priceLimitTitle' }
          },
          {
            title: '备注', align:"left", dataIndex: 'description', width: 150
          },
          { title: '排序', align:"left", dataIndex: 'sort', width: 50},
          { title: '状态',dataIndex: 'enabled',width:60,align:"center",
            scopedSlots: { customRender: 'customRenderFlag' }
          }
        ],
        url: {
          list: "/role/list",
          delete: "/role/delete",
          deleteBatch: "/role/deleteBatch",
          batchSetStatusUrl: "/role/batchSetStatus"
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
        this.$refs.roleFunctionModal.title = "分配功能给：" + record.name + "【分配之后请继续分配按钮】"
        this.$refs.roleFunctionModal.disableSubmit = false;
      },
      handleSetPushBtn(roleId, roleName) {
        this.$refs.rolePushBtnModal.edit(roleId);
        this.$refs.rolePushBtnModal.title = "分配按钮给：" + roleName
        this.$refs.rolePushBtnModal.disableSubmit = false;
      },
      roleModalFormOk() {
        //重载列表
        this.loadData()
        this.roleModalVisible = true
      },
      roleFunctionModalFormOk(id) {
        //重载列表
        this.loadData()
        this.roleFunctionModalVisible = true
        this.currentRoleId = id
      },
      handleRoleTip(record) {
        if(record) {
          this.roleModalVisible = false
          this.handleSetFunction(record)
        }
      },
      handleRoleFunctionTip() {
        if(this.currentRoleId) {
          this.roleFunctionModalVisible = false
          let roleName = ''
          for (let i = 0; i < this.dataSource.length; i++) {
            if(this.dataSource[i].id == this.currentRoleId) {
              roleName = this.dataSource[i].name
            }
          }
          this.handleSetPushBtn(this.currentRoleId, roleName)
        }
      },
      handleAdd: function () {
        this.$refs.modalForm.add();
        this.$refs.modalForm.title = "新增【保存之后请继续分配功能】";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleEdit: function (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑【保存之后请继续分配功能】";
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
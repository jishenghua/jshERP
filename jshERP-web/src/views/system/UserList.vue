<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="12">
            <a-form-item label="登录名称">
              <a-input placeholder="输入登录名称模糊查询" v-model="queryParam.loginName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="12">
            <a-form-item label="用户姓名">
              <a-input placeholder="输入用户姓名模糊查询" v-model="queryParam.userName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery">查询</a-button>
              <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 操作按钮区域 -->
    <div class="table-operator" style="border-top: 5px">
      <a-button v-if="btnEnableList.indexOf(1)>-1" @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" v-if="btnEnableList.indexOf(1)>-1" ><a-icon type="delete" @click="batchDel"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>
    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        bordered
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        <span slot="action" slot-scope="text, record">
          <a v-if="btnEnableList.indexOf(1)>-1 && depotFlag === '1' " @click="btnSetDepot(record)">分配仓库</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1 && depotFlag === '1'" type="vertical" />
          <a v-if="btnEnableList.indexOf(1)>-1 && customerFlag === '1'" @click="btnSetCustomer(record)">分配客户</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1 && customerFlag === '1'" type="vertical" />
          <a @click="handleEdit(record)">编辑</a>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical"/>
          <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
          <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical"/>
          <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定重置密码为123456吗?" @confirm="() => handleReset(record.id)">
            <a>重置密码</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <user-modal ref="modalForm" @ok="modalFormOk"></user-modal>
    <user-depot-modal ref="userDepotModal" @ok="modalFormOk"></user-depot-modal>
    <user-customer-modal ref="userCustomerModal" @ok="modalFormOk"></user-customer-modal>
  </a-card>
</template>

<script>
  import UserModal from './modules/UserModal'
  import UserDepotModal from './modules/UserDepotModal'
  import UserCustomerModal from './modules/UserCustomerModal'
  import {postAction} from '@/api/manage';
  import {getCurrentSystemConfig} from '@/api/api'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import JInput from '@/components/jeecg/JInput'
  export default {
    name: "UserList",
    mixins: [JeecgListMixin],
    components: {
      UserModal,
      UserDepotModal,
      UserCustomerModal,
      JInput
    },
    data() {
      return {
        queryParam: {},
        depotFlag: '0',
        customerFlag: '0',
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
          { title: '登录名称', dataIndex: 'loginName', width: 100, align: "center"},
          { title: '用户姓名', dataIndex: 'username', width: 100, align: "center"},
          { title: '用户类型', dataIndex: 'userType', width: 80, align: "center" },
          { title: '角色', dataIndex: 'roleName', width: 100, align: "center"},
          { title: '机构', dataIndex: 'orgAbr', width: 115, align: "center"},
          { title: '电话号码', dataIndex: 'phonenum', width: 120, align: "center"},
          { title: '排序', dataIndex: 'userBlngOrgaDsplSeq', width: 60, align: "center"},
          {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'},
            align: "center",
            width: 260
          }
        ],
        url: {
          list: "/user/list",
          delete: "/user/delete",
          deleteBatch: "/user/deleteBatch",
          resetPwd: "/user/resetPwd"
        },
      }
    },
    created () {
      this.getSystemConfig()
    },
    methods: {
      getSystemConfig() {
        getCurrentSystemConfig().then((res) => {
          if(res.code === 200){
            let systemConfig = res.data
            this.depotFlag = systemConfig.depotFlag
            this.customerFlag = systemConfig.customerFlag
          } else {
            this.$message.warning(res.data);
          }
        })
      },
      searchQuery() {
        this.loadData(1);
        this.getSystemConfig();
      },
      searchReset() {
        this.queryParam = {}
        this.loadData(1);
        this.getSystemConfig();
      },
      handleEdit: function (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
        if(this.btnEnableList.indexOf(1)===-1) {
          this.$refs.modalForm.isReadOnly = true
        }
      },
      handleReset(id) {
        let that = this;
        postAction(that.url.resetPwd, {id: id}).then((res) => {
          if(res.code === 200){
            that.$message.info('重置密码成功！');
            that.loadData();
          } else {
            that.$message.warning(res.data.message);
          }
        })
      },
      btnSetDepot(record) {
        this.$refs.userDepotModal.edit(record);
        this.$refs.userDepotModal.title = "分配仓库";
        this.$refs.userDepotModal.disableSubmit = false;
      },
      btnSetCustomer(record) {
        this.$refs.userCustomerModal.edit(record);
        this.$refs.userCustomerModal.title = "分配客户";
        this.$refs.userCustomerModal.disableSubmit = false;
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
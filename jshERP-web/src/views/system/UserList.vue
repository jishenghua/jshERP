<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="输入登录名称模糊查询" v-model="queryParam.loginName"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="用户姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="输入用户姓名模糊查询" v-model="queryParam.userName"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
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
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="batchDel" icon="delete">删除</a-button>
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(0)" icon="check-square">启用</a-button>
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(2)" icon="close-square">禁用</a-button>
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
            :scroll="scroll"
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
              <a-divider type="vertical"/>
              <a @click="handleResetModal(record)">重置密码</a>
            </span>
            <!-- 状态渲染模板 -->
            <template slot="customRenderFlag" slot-scope="status">
              <a-tag v-if="status===0" color="green">启用</a-tag>
              <a-tag v-if="status===2" color="orange">禁用</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <user-modal ref="modalForm" @ok="modalFormOk"></user-modal>
        <user-depot-modal ref="userDepotModal" @ok="modalFormOk"></user-depot-modal>
        <user-customer-modal ref="userCustomerModal" @ok="modalFormOk"></user-customer-modal>
        <user-reset-modal ref="userResetModal" @ok="modalFormOk"></user-reset-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- b y 7 5 2 7  1 8 9 2 0 -->
<script>
  import UserModal from './modules/UserModal'
  import UserDepotModal from './modules/UserDepotModal'
  import UserCustomerModal from './modules/UserCustomerModal'
  import UserResetModal from './modules/UserResetModal'
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
      UserResetModal,
      JInput
    },
    data() {
      return {
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        queryParam: {},
        urlPath: '/system/user',
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
          {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'},
            align: "center",
            width: 200
          },
          { title: '登录名称', dataIndex: 'loginName', width: 100, align: "left"},
          { title: '用户姓名', dataIndex: 'username', width: 100, align: "left"},
          { title: '用户类型', dataIndex: 'userType', width: 80, align: "left" },
          { title: '角色', dataIndex: 'roleName', width: 100, align: "left"},
          { title: '机构', dataIndex: 'orgAbr', width: 100, align: "left"},
          { title: '是否经理', dataIndex: 'leaderFlagStr', width: 60, align: "left"},
          { title: '电话号码', dataIndex: 'phonenum', width: 80, align: "left"},
          { title: '排序', dataIndex: 'userBlngOrgaDsplSeq', width: 40, align: "left"},
          { title: '状态',dataIndex: 'status',width:60,align:"center",
            scopedSlots: { customRender: 'customRenderFlag' }
          }
        ],
        url: {
          list: "/user/list",
          delete: "/user/delete",
          deleteBatch: "/user/deleteBatch",
          resetPwd: "/user/resetPwd",
          batchSetStatusUrl: "/user/batchSetStatus"
        },
      }
    },
    created () {
      this.getSystemConfig()
    },
    methods: {
      getSystemConfig() {
        getCurrentSystemConfig().then((res) => {
          if(res.code === 200 && res.data){
            this.depotFlag = res.data.depotFlag
            this.customerFlag = res.data.customerFlag
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
      handleResetModal(record) {
        this.$refs.userResetModal.edit(record);
        this.$refs.userResetModal.title = "请输入" + record.loginName + "的新密码";
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
        this.$refs.userDepotModal.title = "分配仓库给：" + record.username
        this.$refs.userDepotModal.disableSubmit = false;
      },
      btnSetCustomer(record) {
        this.$refs.userCustomerModal.edit(record);
        this.$refs.userCustomerModal.title = "分配客户给：" + record.username
        this.$refs.userCustomerModal.disableSubmit = false;
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
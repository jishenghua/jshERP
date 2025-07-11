<!-- f r o m 7 5  2 7 1  8 9 2 0 -->
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
                <a-form-item label="仓库名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入仓库名称查询" v-model="queryParam.name"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入备注查询" v-model="queryParam.remark"></a-input>
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
              <a v-if="btnEnableList.indexOf(1)>-1 && depotFlag === '1' && quickBtn.user.indexOf(1)>-1 " @click="btnSetUser(record)">分配用户</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1 && depotFlag === '1' && quickBtn.user.indexOf(1)>-1 " type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定设为默认吗?" @confirm="() => handleSetDefault(record.id)">
                <a>设为默认</a>
              </a-popconfirm>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a @click="handleEdit(record)">编辑</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <!-- 状态渲染模板 -->
            <template slot="customRenderEnabledFlag" slot-scope="enabled">
              <a-tag v-if="enabled" color="green">启用</a-tag>
              <a-tag v-if="!enabled" color="orange">禁用</a-tag>
            </template>
            <template slot="customRenderFlag" slot-scope="isDefault">
              <a-tag v-if="isDefault" color="green">是</a-tag>
              <a-tag v-if="!isDefault" color="orange">否</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <depot-modal ref="modalForm" @ok="modalFormOk"></depot-modal>
        <depot-user-modal ref="depotUserModal"></depot-user-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- BY cao_yu-li -->
<script>
  import DepotModal from './modules/DepotModal'
  import DepotUserModal from './modules/DepotUserModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  import { postAction } from '@api/manage'
  import { getCurrentSystemConfig } from '@/api/api'
  import Vue from 'vue'
  export default {
    name: "DepotList",
    mixins:[JeecgListMixin],
    components: {
      DepotModal,
      DepotUserModal,
      JDate
    },
    data () {
      return {
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {name:'',remark:''},
        depotFlag: '0',
        quickBtn: {
          user: ''
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
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width: 200,
            scopedSlots: { customRender: 'action' },
          },
          {title: '仓库名称', dataIndex: 'name', width: 200},
          {title: '仓库地址', dataIndex: 'address', width: 200},
          {title: '仓储费', dataIndex: 'warehousing', width: 80},
          {title: '搬运费', dataIndex: 'truckage', width: 80},
          {title: '负责人', dataIndex: 'principalName', width: 80},
          {title: '备注', dataIndex: 'remark', width: 120},
          {title: '排序', dataIndex: 'sort', width: 60},
          { title: '状态',dataIndex: 'enabled',width:60,align:"center",
            scopedSlots: { customRender: 'customRenderEnabledFlag' }
          },
          {title: '是否默认',dataIndex: 'isDefault',width:80,align:"center",
            scopedSlots: { customRender: 'customRenderFlag' }
          }
        ],
        url: {
          list: "/depot/list",
          delete: "/depot/delete",
          deleteBatch: "/depot/deleteBatch",
          setDefault: "/depot/updateIsDefault",
          batchSetStatusUrl: "/depot/batchSetStatus"
        }
      }
    },
    created() {
      this.getSystemConfig()
      this.initQuickBtn()
    },
    methods: {
      getSystemConfig() {
        getCurrentSystemConfig().then((res) => {
          if(res.code === 200 && res.data){
            this.depotFlag = res.data.depotFlag
          }
        })
      },
      //加载快捷按钮：分配用户
      initQuickBtn() {
        let btnStrList = Vue.ls.get('winBtnStrList') //按钮功能列表 JSON字符串
        if (btnStrList) {
          for (let i = 0; i < btnStrList.length; i++) {
            if (btnStrList[i].btnStr) {
              this.quickBtn.user = btnStrList[i].url === '/system/user'?btnStrList[i].btnStr:this.quickBtn.user
            }
          }
        }
      },
      handleSetDefault: function (id) {
        if(!this.url.setDefault){
          this.$message.error("请设置url.delete属性!")
          return
        }
        let that = this;
        postAction(that.url.setDefault, {id: id}).then((res) => {
          if(res.code === 200){
            that.loadData();
          } else {
            that.$message.warning(res.data.message);
          }
        });
      },
      handleEdit: function (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
        if(this.btnEnableList.indexOf(1)===-1) {
          this.$refs.modalForm.isReadOnly = true
        }
      },
      btnSetUser(record) {
        this.$refs.depotUserModal.edit(record)
        this.$refs.depotUserModal.title = "分配用户给：" + record.name
        this.$refs.depotUserModal.disableSubmit = false
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
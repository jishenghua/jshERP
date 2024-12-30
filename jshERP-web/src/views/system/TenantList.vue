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
                <a-form-item label="租户角色" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select v-model="queryParam.roleId" placeholder="请选择租户角色">
                    <a-select-option v-for="(item,index) in tenantRoleList" :key="index" :value="item.id">
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="租户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select v-model="queryParam.type" placeholder="请选择租户类型">
                    <a-select-option value="0">试用租户</a-select-option>
                    <a-select-option value="1">付费租户</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
                  <a @click="handleToggleSearch" style="margin-left: 8px">
                    {{ toggleSearchStatus ? '收起' : '展开' }}
                    <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
                  </a>
                </span>
              </a-col>
              <template v-if="toggleSearchStatus">
                <a-col :md="6" :sm="24">
                  <a-form-item label="租户状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select v-model="queryParam.enabled" placeholder="请选择操作状态">
                      <a-select-option value="1">启用</a-select-option>
                      <a-select-option value="0">禁用</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-model="queryParam.remark" placeholder="请输入备注"></a-input>
                  </a-form-item>
                </a-col>
              </template>
            </a-row>
          </a-form>
        </div>
        <!-- 操作按钮区域 -->
        <div class="table-operator" style="border-top: 5px">
          <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
          <a-button @click="batchSetStatus(1)" icon="check-square">启用</a-button>
          <a-button @click="batchSetStatus(0)" icon="close-square">禁用</a-button>
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
              <a @click="handleEdit(record)">编辑</a>
            </span>
            <!-- 状态渲染模板 -->
            <template slot="customRenderType" slot-scope="type">
              <a-tag v-if="type==0">试用租户</a-tag>
              <a-tag v-if="type==1" color="green">付费租户</a-tag>
            </template>
            <template slot="customRenderEnabled" slot-scope="enabled">
              <a-tag v-if="enabled" color="green">启用</a-tag>
              <a-tag v-if="!enabled" color="orange">禁用</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <tenant-modal ref="modalForm" @ok="modalFormOk"></tenant-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- b y 7 5 2 7  1 8 9 2 0 -->
<script>
  import TenantModal from './modules/TenantModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import JInput from '@/components/jeecg/JInput'
  import { getTenantRoleList } from '@/api/api'
  export default {
    name: "TenantList",
    mixins: [JeecgListMixin],
    components: {
      TenantModal,
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
        queryParam: {
          loginName: '',
          roleId: '',
          type: '',
          enabled: '',
          remark: ''
        },
        tenantRoleList: [],  //租户角色列表
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
            width: 100
          },
          { title: '登录名称', dataIndex: 'loginName', width: 100, align: "center"},
          { title: '用户数量', dataIndex: 'userCount', width: 60, align: "center"},
          { title: '用户数量限制', dataIndex: 'userNumLimit', width: 80, align: "center"},
          { title: '租户角色', dataIndex: 'roleName', width: 80, align: "center"},
          { title: '租户类型',dataIndex: 'type',width:60,align:"center",
            scopedSlots: { customRender: 'customRenderType' }
          },
          { title: '租户状态',dataIndex: 'enabled',width:60,align:"center",
            scopedSlots: { customRender: 'customRenderEnabled' }
          },
          { title: '创建时间', dataIndex: 'createTimeStr', width: 100, align: "center"},
          { title: '到期时间', dataIndex: 'expireTimeStr', width: 100, align: "center"},
          { title: '备注', dataIndex: 'remark', width: 200, align: "center", ellipsis:true}
        ],
        url: {
          list: "/tenant/list",
          batchSetStatusUrl: "/tenant/batchSetStatus"
        },
      }
    },
    created () {
      this.getTenantRoleList()
    },
    methods: {
      getTenantRoleList() {
        getTenantRoleList().then((res)=>{
          if(res) {
            this.tenantRoleList = res
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
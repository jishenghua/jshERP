<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
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
                <a-form-item label="租户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select v-model="queryParam.type" placeholder="请选择租户类型">
                    <a-select-option value="0">免费租户</a-select-option>
                    <a-select-option value="1">付费租户</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="租户状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select v-model="queryParam.enabled" placeholder="请选择操作状态">
                    <a-select-option value="1">启用</a-select-option>
                    <a-select-option value="0">禁用</a-select-option>
                  </a-select>
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
          <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item key="1" @click="batchSetStatus(1)"><a-icon type="check-square"/>启用</a-menu-item>
              <a-menu-item key="2" @click="batchSetStatus(0)"><a-icon type="close-square"/>禁用</a-menu-item>
            </a-menu>
            <a-button>
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
            :scroll="scroll"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
            </span>
            <!-- 状态渲染模板 -->
            <template slot="customRenderType" slot-scope="type">
              <a-tag v-if="type==0">免费租户</a-tag>
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
          type: '',
          enabled: ''
        },
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
          { title: '用户数量限制', dataIndex: 'userNumLimit', width: 100, align: "center"},
          { title: '租户类型',dataIndex: 'type',width:70,align:"center",
            scopedSlots: { customRender: 'customRenderType' }
          },
          { title: '租户状态',dataIndex: 'enabled',width:70,align:"center",
            scopedSlots: { customRender: 'customRenderEnabled' }
          },
          { title: '创建时间', dataIndex: 'createTimeStr', width: 100, align: "center"},
          { title: '到期时间', dataIndex: 'expireTimeStr', width: 100, align: "center"},
          { title: '描述', dataIndex: 'remark', width: 200, align: "center", ellipsis:true},
          {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'},
            align: "center",
            width: 100
          }
        ],
        url: {
          list: "/tenant/list",
          batchSetStatusUrl: "/tenant/batchSetStatus"
        },
      }
    },
    created () {
    },
    methods: {
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
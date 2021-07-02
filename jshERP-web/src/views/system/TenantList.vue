<template>
  <a-row :gutter="24">
    <a-col :md="24">
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
          <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
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
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
            </span>
          </a-table>
        </div>
        <!-- table区域-end -->
        <tenant-modal ref="modalForm" @ok="modalFormOk"></tenant-modal>
      </a-card>
    </a-col>
  </a-row>
</template>

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
        queryParam: {
          loginName: ""
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
          { title: '单据数量限制', dataIndex: 'billsNumLimit', width: 100, align: "center"},
          { title: '创建时间', dataIndex: 'createTimeStr', width: 100, align: "center"},
          {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'},
            align: "center",
            width: 100
          }
        ],
        url: {
          list: "/tenant/list"
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
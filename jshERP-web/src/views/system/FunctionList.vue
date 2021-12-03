<!-- f r o m 7 5  2 7 1  8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入名称查询" v-model="queryParam.name"></a-input>
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
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
            </a-menu>
            <a-button>
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
            :scroll="scroll"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
              <a-divider type="vertical" />
              <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <!-- 状态渲染模板 -->
            <template slot="customRenderFlag" slot-scope="enabled">
              <a-tag v-if="enabled==1" color="green">启用</a-tag>
              <a-tag v-if="enabled==0" color="orange">禁用</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <function-modal ref="modalForm" @ok="modalFormOk"></function-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import FunctionModal from './modules/FunctionModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "FunctionList",
    mixins:[JeecgListMixin],
    components: {
      FunctionModal,
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
        queryParam: {name:'',type:''},
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
          {title: '编号 ', dataIndex: 'number', width: 80},
          {title: '名称', dataIndex: 'name', width: 100},
          {title: '上级编号', dataIndex: 'parentNumber', width: 80},
          {title: '链接', dataIndex: 'url', width: 250},
          {title: '组件', dataIndex: 'component', width: 250},
          {title: '排序', dataIndex: 'sort', width: 80},
          {
            title: '是否启用', dataIndex: 'enabled', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderFlag' }
          },
          {title: '图标', dataIndex: 'icon', width: 110},
          {
            title: '操作',
            dataIndex: 'action',
            width: 200,
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/function/list",
          delete: "/function/delete",
          deleteBatch: "/function/deleteBatch"
        }
      }
    },
    computed: {

    },
    methods: {

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
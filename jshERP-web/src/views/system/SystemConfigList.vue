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
                <a-form-item label="公司名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入公司名称查询" v-model="queryParam.companyName"></a-input>
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
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="myHandleAdd" type="primary" icon="plus">新增</a-button>
          <a-dropdown>
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
              <a @click="handleEdit(record)">编辑</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <!-- 状态渲染模板 -->
            <template slot="customRenderFlag" slot-scope="flag">
              <a-tag v-if="flag==1" color="green">启用</a-tag>
              <a-tag v-if="flag==0" color="orange">关闭</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <systemConfig-modal ref="modalForm" @ok="modalFormOk"></systemConfig-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- b y 7 5 2 7  1 8 9 2 0 -->
<script>
  import SystemConfigModal from './modules/SystemConfigModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "SystemConfigList",
    mixins:[JeecgListMixin],
    components: {
      SystemConfigModal,
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
        queryParam: {companyName:'',},
        // 表头
        columns: [
          {
            title: '#', dataIndex: '', key:'rowIndex', width:40, align:"center",
            customRender:function (text,record,index) {
              return parseInt(index)+1;
            }
          },
          {title: '公司名称', dataIndex: 'companyName', width: 150},
          {title: '联系人', dataIndex: 'companyContacts', width: 80, align: "center"},
          {title: '公司地址', dataIndex: 'companyAddress', width: 150, align: "center"},
          {title: '公司电话', dataIndex: 'companyTel', width: 120, align: "center"},
          {title: '公司传真', dataIndex: 'companyFax', width: 120, align: "center"},
          {title: '公司邮编', dataIndex: 'companyPostCode', width: 80, align: "center"},
          {
            title: '仓库权限', dataIndex: 'depotFlag', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderFlag' }
          },
          {
            title: '客户权限', dataIndex: 'customerFlag', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderFlag' }
          },
          {
            title: '负库存', dataIndex: 'minusStockFlag', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderFlag' }
          },
          {title: '操作', dataIndex: 'action', width: 150, align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/systemConfig/list",
          delete: "/systemConfig/delete",
          deleteBatch: "/systemConfig/deleteBatch"
        },
      }
    },
    computed: {
    },
    methods: {
      myHandleAdd() {
        if(this.ipagination.total>=1) {
          this.$message.warning('抱歉，只能填写一条系统配置信息！');
        } else {
          this.handleAdd()
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
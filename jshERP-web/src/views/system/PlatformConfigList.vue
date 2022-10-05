<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
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
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
            </span>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <platform-config-modal ref="modalForm" @ok="modalFormOk"></platform-config-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- f r o m 7 5  2 7 1  8 9 2 0 -->
<script>
  import PlatformConfigModal from './modules/PlatformConfigModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  export default {
    name: "PlatformConfigList",
    mixins:[JeecgListMixin],
    components: {
      PlatformConfigModal
    },
    data () {
      return {
        currentRoleId: '',
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {platformKey:'',},
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
            width: 100,
            scopedSlots: { customRender: 'action' },
          },
          {
            title: '配置名称',
            dataIndex: 'platformKeyInfo',
            width: 100
          },
          {
            title: '配置值',
            dataIndex: 'platformValue',
            width: 500
          }
        ],
        url: {
          list: "/platformConfig/list",
          delete: "/platformConfig/delete",
          deleteBatch: "/platformConfig/deleteBatch"
        },
      }
    },
    methods: {
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
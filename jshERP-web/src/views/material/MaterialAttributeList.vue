<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
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
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
            </span>
          </a-table>
        </div>
        <!-- table区域-end -->
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  export default {
    name: "MaterialAttributeList",
    mixins:[JeecgListMixin],
    components: {
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
        queryParam: {attributeField:''},
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
          {title: '属性名', dataIndex: 'attributeName', width: 100},
          {title: '属性值', dataIndex: 'attributeValue', width: 400},
          {
            title: '操作',
            dataIndex: 'action',
            width: 100,
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/materialAttribute/list",
          delete: "/materialAttribute/delete",
          deleteBatch: "/materialAttribute/deleteBatch"
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
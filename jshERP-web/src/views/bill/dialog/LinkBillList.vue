<template>
  <a-modal
    :title="title"
    :width="1250"
    :visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 100%;overflow-y: hidden">
    <!-- table区域-begin -->
    <a-table
      bordered
      ref="table"
      size="middle"
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="ipagination"
      :loading="loading"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: getType}"
      :customRow="rowAction"
      @change="handleTableChange">
      <template slot="customRenderStatus" slot-scope="status">
        <a-tag v-if="status === '0'" color="red">未审核</a-tag>
        <a-tag v-if="status === '1'" color="green">已审核</a-tag>
        <a-tag v-if="status === '2' && queryParam.subType === '采购订单'" color="cyan">完成采购</a-tag>
        <a-tag v-if="status === '2' && queryParam.subType === '销售订单'" color="cyan">完成销售</a-tag>
        <a-tag v-if="status === '3' && queryParam.subType === '采购订单'" color="blue">部分采购</a-tag>
        <a-tag v-if="status === '3' && queryParam.subType === '销售订单'" color="blue">部分销售</a-tag>
      </template>
    </a-table>
    <!-- table区域-end -->
  </a-modal>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  export default {
    name: 'LinkBillList',
    mixins:[JeecgListMixin],
    data () {
      return {
        title: "操作",
        visible: false,
        disableMixinCreated: true,
        selectedRowKeys: [],
        selectionRows: [],
        selectBillRows: [],
        selectBillIds: '',
        queryParam: {
          number: "",
          searchMaterial: "",
          type: "",
          subType: "",
          status: ""
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
          { title: '', dataIndex: 'organName',width:120},
          { title: '单据编号', dataIndex: 'number',width:150},
          { title: '商品信息', dataIndex: 'materialsList',width:280, ellipsis:true,
            customRender:function (text,record,index) {
              if(text) {
                return text.replace(",","，");
              }
            }
          },
          { title: '单据日期', dataIndex: 'operTimeStr',width:145},
          { title: '操作员', dataIndex: 'userName',width:70},
          { title: '金额合计', dataIndex: 'totalPrice',width:70},
          { title: '状态', dataIndex: 'status', width: 70, align: "center",
            scopedSlots: { customRender: 'customRenderStatus' }
          }
        ],
        url: {
          list: "/depotHead/list"
        }
      }
    },
    computed: {
      getType: function () {
        return 'radio';
      }
    },
    created() {
    },
    methods: {
      show(type, subType, organType, status) {
        this.queryParam.type = type
        this.queryParam.subType = subType
        this.queryParam.status = status
        this.columns[1].title = organType
        this.model = Object.assign({}, {});
        this.visible = true;
        this.loadData(1)
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      handleOk () {
        this.getSelectBillRows();
        this.$emit('ok', this.selectBillRows);
        this.close();
      },
      getSelectBillRows() {
        let dataSource = this.dataSource;
        let billIds = "";
        this.selectBillRows = [];
        for (let i = 0, len = dataSource.length; i < len; i++) {
          if (this.selectedRowKeys.includes(dataSource[i].id)) {
            this.selectBillRows.push(dataSource[i]);
            billIds = billIds + "," + dataSource[i].id
          }
        }
        this.selectBillIds = billIds.substring(1);
      },
      rowAction(record, index) {
        return {
          on: {
            click: () => {
              let arr = []
              arr.push(record.id)
              this.selectedRowKeys = arr
            },
            dblclick: () => {
              let arr = []
              arr.push(record.id)
              this.selectedRowKeys = arr
              this.handleOk()
            }
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>
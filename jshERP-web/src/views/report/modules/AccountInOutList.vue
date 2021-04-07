<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
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
      @change="handleTableChange">
    </a-table>
    <!-- table区域-end -->
  </a-modal>
</template>
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import {getAction} from '@/api/manage'
  export default {
    name: "AccountInOutList",
    mixins:[JeecgListMixin],
    components: {
      JEllipsis
    },
    data () {
      return {
        title:"操作",
        visible: false,
        disableMixinCreated: false,
        // 查询条件
        queryParam: {
          accountId:'',
          initialAmount:''
        },
        tabKey: "1",
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
          { title: '单据编号', dataIndex: 'number', width: 150},
          { title: '类型', dataIndex: 'type', width: 100},
          { title: '单位信息', dataIndex: 'supplierName', width: 150},
          { title: '金额', dataIndex: 'changeAmount', width: 80},
          { title: '余额', dataIndex: 'balance', width: 80,
            customRender:function (t,r,index) {
              if (r.aList && r.amList) {
                let aListArr = r.aList.toString().split(",");
                let amListArr = r.amList.toString().split(",");
                let res = "";
                for (let i = 0; i < aListArr.length; i++) {
                  if (aListArr[i] == this.queryParam.accountId) {
                    res = amListArr[i];
                  }
                }
                return res + "[多账户]";
              }
              else {
                return r.changeAmount;
              }
            }
          },
          { title: '单据日期', dataIndex: 'operTime', width: 180}
        ],
        labelCol: {
          xs: { span: 1 },
          sm: { span: 2 },
        },
        wrapperCol: {
          xs: { span: 10 },
          sm: { span: 16 },
        },
        url: {
          list: "/account/findAccountInOutList"
        }
      }
    },
    created() {
    },
    methods: {
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return param;
      },
      show(record) {
        this.model = Object.assign({}, record);
        this.visible = true;
        this.queryParam.accountId = record.id
        this.queryParam.initialAmount = record.initialAmount
        this.loadData(1)
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
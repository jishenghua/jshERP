<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="800"
      :visible="visible"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @cancel="handleCancel"
      cancelText="关闭"
      style="top:100px;height: 80%;">
      <template slot="footer">
        <a-button key="back" @click="handleCancel">取消(ESC)</a-button>
      </template>
      <!-- table区域-begin -->
      <a-table
        bordered
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :components="handleDrag(columns)"
        pagination="false"
        :loading="loading"
        @change="handleTableChange">
      </a-table>
      <!-- table区域-end -->
    </a-modal>
  </div>
</template>
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import { mixinDevice } from '@/utils/mixin'

  export default {
    name: "MaterialDepotStockListWithTime",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JEllipsis
    },
    data () {
      return {
        title:"操作",
        visible: false,
        disableMixinCreated: true,
        toFromType: '',
        currentMaterialId: '',
        // 查询条件
        queryParam: {
          depotIds: '',
          materialId:'',
          unitPrice:'',
          beginTime:'',
          endTime:'',
        },
        ipagination:{
          pageSizeOptions: ['10', '20', '30', '100', '200']
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
          { title: '仓库名称', dataIndex: 'depotName', width: 200},
          { title: '库存数量', dataIndex: 'currentNumber', width: 100},
          { title: '成本价', dataIndex: 'unitPrice', width: 100},
          { title: '库存金额', dataIndex: 'allPrice', width: 100}
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
          list: "/depotItem/getMaterialDepotStockByParam"
        }
      }
    },
    created() {
    },
    methods: {
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter)
        param.field = this.getQueryField()
        param.materialId = this.currentMaterialId
        param.currentPage = this.ipagination.current
        param.pageSize = this.ipagination.pageSize
        return param
      },
      show(record, depotIds, beginTime, endTime) {
        this.model = Object.assign({}, record);
        this.currentMaterialId = record.id
        this.visible = true;
        this.queryParam.depotIds = depotIds
        this.queryParam.materialId = record.id
        this.queryParam.unitPrice = record.unitPrice
        this.queryParam.beginTime = beginTime
        this.queryParam.endTime = endTime
        this.loadData(1)
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      onDateOk(value) {
        console.log(value);
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
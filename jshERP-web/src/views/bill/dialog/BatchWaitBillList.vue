<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="1300"
      :visible="visible"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @cancel="handleCancel"
      style="top:20px;height: 95%;">
      <template slot="footer">
        <a-button key="back" @click="handleCancel">取消</a-button>
      </template>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <!-- 搜索区域 -->
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :md="6" :sm="24">
              <a-form-item label="单据编号" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
                <a-input placeholder="请输入单据编号查询" v-model="queryParam.number"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="商品信息" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
                <a-input placeholder="条码|名称|规格|型号" v-model="queryParam.materialParam"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-range-picker
                  style="width: 100%"
                  v-model="queryParam.createTimeRange"
                  format="YYYY-MM-DD"
                  :placeholder="['开始时间', '结束时间']"
                  @change="onDateChange"
                  @ok="onDateOk"
                />
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
        <a-button @click="handleBatchInOut" type="primary" icon="plus">批量{{queryParam.type}}</a-button>
        <span style="padding-left:10px">注意：含有序列号、批号商品或部分入库状态不能批量{{queryParam.type}}，需要到新增界面关联单据</span>
      </div>
      <!-- table区域-begin -->
      <a-table
        bordered
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :components="handleDrag(columns)"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        <span slot="numberCustomRender" slot-scope="text, record">
          <a v-if="!queryParam.purchaseStatus" @click="myHandleDetail(record)">{{record.number}}</a>
          <span v-if="queryParam.purchaseStatus">{{record.number}}</span>
        </span>
        <template slot="customRenderStatus" slot-scope="text, record">
          <template>
            <a-tag v-if="record.status === '1'" color="green">已审核</a-tag>
            <a-tag v-if="record.status === '3' && queryParam.type === '入库'" color="blue">部分入库</a-tag>
            <a-tag v-if="record.status === '3' && queryParam.type === '出库'" color="blue">部分出库</a-tag>
          </template>
        </template>
      </a-table>
      <!-- table区域-end -->
      <!-- 表单区域 -->
      <bill-detail ref="billDetail"></bill-detail>
    </a-modal>
  </div>
</template>

<script>
  import BillDetail from './BillDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {mixinDevice} from '@/utils/mixin'
  import { findBillDetailByNumber, batchAddDepotHeadAndDetail } from '@/api/api'
  export default {
    name: 'BatchWaitBillList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BillDetail
    },
    data () {
      return {
        title: "操作",
        visible: false,
        disableMixinCreated: true,
        selectedRowKeys: [],
        linkNumber: '',
        organId: '',
        remark: '',
        queryParam: {
          number: "",
          materialParam: "",
          type: "",
          subType: "",
          status: ""
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        // 表头
        columns: [
          { title: '', dataIndex: 'organName',width:120, ellipsis:true},
          { title: '单据编号', dataIndex: 'number',width:130,
            scopedSlots: { customRender: 'numberCustomRender' },
          },
          { title: '商品信息', dataIndex: 'materialsList',width:280, ellipsis:true,
            customRender:function (text,record,index) {
              if(text) {
                return text.replace(",","，");
              }
            }
          },
          { title: '单据日期', dataIndex: 'operTimeStr',width:145},
          { title: '操作员', dataIndex: 'userName',width:70},
          { title: '数量', dataIndex: 'materialCount',width:60},
          { title: '状态', dataIndex: 'status', width: 70, align: "center",
            scopedSlots: { customRender: 'customRenderStatus' }
          }
        ],
        dataSource:[],
        url: {
          list: "/depotHead/waitBillList"
        }
      }
    },
    created() {
    },
    methods: {
      show(type, subType, status) {
        this.queryParam.type = type
        this.queryParam.subType = subType
        this.queryParam.status = status
        this.columns[0].title = '供应商或客户'
        this.model = Object.assign({}, {});
        this.visible = true;
        this.loadData(1)
      },
      myHandleDetail(record) {
        findBillDetailByNumber({ number: record.number }).then((res) => {
          if (res && res.code === 200) {
            let type = res.data.depotHeadType
            type = type.replace('其它','')
            this.$refs.billDetail.show(res.data, type)
            this.$refs.billDetail.title=type+"-详情"
          }
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      onSelectChange(selectedRowKeys) {
        this.selectedRowKeys = selectedRowKeys;
      },
      onDateChange: function (value, dateString) {
        this.queryParam.beginTime=dateString[0];
        this.queryParam.endTime=dateString[1];
      },
      onDateOk(value) {
        console.log(value);
      },
      searchReset() {
        this.queryParam = {
          type: this.queryParam.type,
          subType: this.queryParam.subType,
          status: "1,3"
        }
        this.loadData(1);
      },
      handleBatchInOut() {
        const that = this
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！')
        } else {
          this.$confirm({
            title: "确认批量操作",
            content: "是否批量操作选中的单据?",
            onOk: function () {
              let ids = "";
              for (let i = 0; i < that.selectedRowKeys.length; i++) {
                ids += that.selectedRowKeys[i] + ",";
              }
              that.confirmLoading = true
              batchAddDepotHeadAndDetail({ 'ids': ids }).then((res) => {
                if (res.code === 200) {
                  that.$emit('ok')
                  that.selectedRowKeys = []
                  that.confirmLoading = false
                  that.close()
                } else {
                  that.$message.warning(res.data.message)
                  that.confirmLoading = false
                }
              })
            }
          })
        }
      }
    }
  }
</script>

<style scoped>

</style>
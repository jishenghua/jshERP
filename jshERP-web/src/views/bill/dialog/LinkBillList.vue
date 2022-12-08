<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="1250"
      :visible="visible"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'101px','left':'151px'}"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭"
      wrapClassName="ant-modal-cust-warp"
      style="top:20px;height: 95%;overflow-y: hidden">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper" v-if="selectType === 'list'">
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
      <!-- table区域-begin -->
      <a-table v-if="selectType === 'list'"
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
        <span slot="numberCustomRender" slot-scope="text, record">
          <a v-if="!queryParam.purchaseStatus" @click="myHandleDetail(record)">{{record.number}}</a>
          <span v-if="queryParam.purchaseStatus">{{record.number}}</span>
        </span>
        <template slot="customRenderStatus" slot-scope="text, record">
          <template v-if="!queryParam.purchaseStatus">
            <a-tag v-if="record.status === '0'" color="red">未审核</a-tag>
            <a-tag v-if="record.status === '1'" color="green">已审核</a-tag>
            <a-tag v-if="record.status === '2' && queryParam.subType === '采购订单'" color="cyan">完成采购</a-tag>
            <a-tag v-if="record.status === '2' && queryParam.subType === '销售订单'" color="cyan">完成销售</a-tag>
            <a-tag v-if="record.status === '3' && queryParam.subType === '采购订单'" color="blue">部分采购</a-tag>
            <a-tag v-if="record.status === '3' && queryParam.subType === '销售订单'" color="blue">部分销售</a-tag>
          </template>
          <template v-if="queryParam.purchaseStatus">
            <a-tag v-if="record.purchaseStatus === '0'" color="red">未采购</a-tag>
            <a-tag v-if="record.purchaseStatus === '2' && queryParam.subType === '销售订单'" color="cyan">完成采购</a-tag>
            <a-tag v-if="record.purchaseStatus === '3' && queryParam.subType === '销售订单'" color="blue">部分采购</a-tag>
          </template>
        </template>
      </a-table>
      <a-table v-if="selectType === 'detail'"
        bordered
        ref="table"
        size="middle"
        rowKey="id"
        :pagination="false"
        :columns="columnsDetail"
        :dataSource="dataSourceDetail"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedDetailRowKeys, onChange: onSelectDetailChange, type: 'checkbox'}"
        @change="handleTableChange">
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
  import { findBillDetailByNumber } from '@/api/api'
  import { getAction } from '@/api/manage'
  import Vue from 'vue'
  export default {
    name: 'LinkBillList',
    mixins:[JeecgListMixin],
    components: {
      BillDetail
    },
    data () {
      return {
        title: "操作",
        visible: false,
        disableMixinCreated: true,
        selectedRowKeys: [],
        selectedDetailRowKeys: [],
        selectBillRows: [],
        selectBillDetailRows: [],
        showType: 'basic',
        selectType: 'list',
        linkNumber: '',
        organId: '',
        discount: '',
        deposit: '',
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
          { title: '金额合计', dataIndex: 'totalPrice',width:70},
          { title: '含税合计', dataIndex: 'totalTaxLastMoney',width:70,
            customRender:function (text,record,index) {
              if(record.discountLastMoney) {
                return (record.discountMoney + record.discountLastMoney).toFixed(2);
              } else {
                return record.totalPrice;
              }
            }
          },
          { title: '状态', dataIndex: 'status', width: 70, align: "center",
            scopedSlots: { customRender: 'customRenderStatus' }
          }
        ],
        columnsDetail: [
          { title: '条码', dataIndex: 'barCode',width:120},
          { title: '名称', dataIndex: 'name',width:100, ellipsis:true},
          { title: '规格', dataIndex: 'standard',width:100, ellipsis:true},
          { title: '型号', dataIndex: 'model',width:150, ellipsis:true},
          { title: '单位', dataIndex: 'unit',width:50},
          { title: '数量', dataIndex: 'operNumber',width:80},
          { title: '单价', dataIndex: 'unitPrice',width:80},
          { title: '金额', dataIndex: 'allPrice',width:80},
          { title: '税率(%)', dataIndex: 'taxRate', width:80},
          { title: '税额', dataIndex: 'taxMoney', width:80},
          { title: '价税合计', dataIndex: 'taxLastMoney', width:80},
          { title: '备注', dataIndex: 'remark',width:100, ellipsis:true},
        ],
        dataSource:[],
        dataSourceDetail: [],
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
        this.selectType = 'list'
        this.showType = 'basic'
        this.queryParam.type = type
        this.queryParam.subType = subType
        this.queryParam.roleType = Vue.ls.get('roleType')
        this.queryParam.status = status
        this.columns[0].title = organType
        this.model = Object.assign({}, {});
        this.visible = true;
        this.loadData(1)
      },
      purchaseShow(type, subType, organType, status, purchaseStatus) {
        this.selectType = 'list'
        this.showType = 'purchase'
        this.queryParam.type = type
        this.queryParam.subType = subType
        this.queryParam.roleType = '全部数据'
        this.queryParam.status = status
        this.queryParam.purchaseStatus = purchaseStatus
        this.columns[0].title = organType
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
      onSelectDetailChange(selectedRowKeys) {
        this.selectedDetailRowKeys = selectedRowKeys;
      },
      handleOk () {
        if(this.selectType === 'list') {
          this.getSelectBillRows();
          this.selectType = 'detail'
          this.title = "选择单据明细"
          if(this.selectBillRows && this.selectBillRows.length>0) {
            let record = this.selectBillRows[0]
            this.linkNumber = record.number
            this.organId = record.organId
            this.discount = record.discount
            this.deposit = record.changeAmount - record.finishDeposit
            this.remark = record.remark
            this.loadDetailData(1)
          }
        } else {
          if(this.selectedDetailRowKeys.length) {
            this.getSelectBillDetailRows()
            this.$emit('ok', this.selectBillDetailRows, this.linkNumber, this.organId, this.discount, this.deposit, this.remark)
            this.close()
          } else {
            this.$message.warning('抱歉，请选择单据明细！')
          }
        }
      },
      //查询明细列表
      loadDetailData(arg) {
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        if(this.selectBillRows && this.selectBillRows.length>0) {
          let record = this.selectBillRows[0]
          let param = {
            headerId: record.id,
            mpList : '',
            linkType: this.showType
          }
          this.loading = true;
          getAction('/depotItem/getDetailList', param).then((res) => {
            if (res.code===200) {
              let list = res.data.rows;
              let listEx = []
              for(let j=0; j<list.length; j++){
                let info = list[j];
                if(info.preNumber !== info.finishNumber) {
                  //去掉已经全部转换的明细
                  listEx.push(info)
                } else {
                  if(this.queryParam.subType === '采购' || this.queryParam.subType === '销售' || this.queryParam.subType === '零售') {
                    //针对退货单，不过滤明细
                    listEx.push(info)
                  }
                }
              }
              this.dataSourceDetail = listEx
              this.ipagination.total = res.data.total;
            }
            if(res.code===510){
              this.$message.warning(res.data)
            }
            this.loading = false;
          })
        }
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
      getSelectBillRows() {
        let dataSource = this.dataSource;
        this.selectBillRows = [];
        for (let i = 0, len = dataSource.length; i < len; i++) {
          if (this.selectedRowKeys.includes(dataSource[i].id)) {
            this.selectBillRows.push(dataSource[i]);
          }
        }
      },
      getSelectBillDetailRows() {
        let dataSource = this.dataSourceDetail;
        this.selectBillDetailRows = [];
        for (let i = 0, len = dataSource.length; i < len; i++) {
          if (this.selectedDetailRowKeys.includes(dataSource[i].id)) {
            this.selectBillDetailRows.push(dataSource[i]);
          }
        }
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
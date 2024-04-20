<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="1250"
      :visible="visible"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @cancel="handleCancel"
      cancelText="关闭"
      style="top:50px;height: 90%;">
      <template slot="footer">
        <a-button @click="handleCancel">关闭</a-button>
      </template>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <!-- 搜索区域 -->
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :md="4" :sm="24" v-if="organLabel">
              <a-form-item :label="organLabel" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParam.organId" :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children">
                  <a-select-option v-for="(item,index) in supplierList" :key="index" :value="item.id">
                    {{ item.supplier }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="5" :sm="24">
              <a-form-item label="单号" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
                <a-input placeholder="请输入单据编号" v-model="queryParam.number"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="5" :sm="24">
              <a-form-item label="商品" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
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
              <a-col :md="4" :sm="24">
                <a-button type="primary" @click="searchQuery">查询</a-button>
                <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
              </a-col>
            </span>
          </a-row>
        </a-form>
      </div>
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
        <span slot="numberCustomRender" slot-scope="text, record">
          <a @click="myHandleDetail(record)">{{record.number}}</a>
        </span>
        <template slot="customRenderStatus" slot-scope="text, record">
          <a-tag v-if="record.status === '0'" color="red">未审核</a-tag>
          <a-tag v-if="record.status === '1'" color="green">已审核</a-tag>
          <a-tag v-if="record.status === '2' && queryParam.subType === '采购订单'" color="cyan">完成采购</a-tag>
          <a-tag v-if="record.status === '2' && queryParam.subType === '销售订单'" color="cyan">完成销售</a-tag>
          <a-tag v-if="record.status === '3' && queryParam.subType === '采购订单'" color="blue">部分采购</a-tag>
          <a-tag v-if="record.status === '3' && queryParam.subType === '销售订单'" color="blue">部分销售</a-tag>
          <a-tag v-if="record.status === '9' && queryParam.subType === '采购订单'" color="orange">审核中</a-tag>
          <a-tag v-if="record.status === '9' && queryParam.subType === '销售订单'" color="orange">审核中</a-tag>
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
  import { findBySelectSup, findBySelectCus, findBillDetailByNumber} from '@/api/api'
  import Vue from 'vue'
  export default {
    name: 'HistoryBillList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BillDetail,
    },
    data () {
      return {
        title: "历史单据",
        visible: false,
        disableMixinCreated: true,
        organLabel: '',
        supplierList: [],
        queryParam: {
          organId: "",
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
          {
            title: '#', dataIndex: '', key:'rowIndex', width:40, align:"center", customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          { title: '', dataIndex: 'organName',width:120, ellipsis:true},
          { title: '单据编号', dataIndex: 'number',width:150,
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
        dataSource:[],
        url: {
          list: "/depotHead/list"
        }
      }
    },
    created() {
    },
    methods: {
      show(type, subType, organType, organId) {
        this.queryParam.type = type
        this.queryParam.subType = subType
        this.columns[1].title = organType
        this.organLabel = organType
        this.model = Object.assign({}, {})
        this.visible = true
        this.loadSupplier(organType, organId)
        this.loadData(1)
      },
      loadSupplier(organType, organId) {
        if(organType === '供应商') {
          findBySelectSup({}).then((res)=>{
            if(res) {
              this.supplierList = res
              if(organId) {
                this.queryParam.organId = organId
                this.loadData(1)
              }
            }
          })
        } else if(organType === '客户') {
          findBySelectCus({}).then((res)=>{
            if(res) {
              this.supplierList = res
              if(organId) {
                this.queryParam.organId = organId
                this.loadData(1)
              }
            }
          })
        }
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
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
          status: ""
        }
        this.loadData(1);
      },
      myHandleDetail(record) {
        let that = this
        findBillDetailByNumber({ number: record.number }).then((res) => {
          if (res && res.code === 200) {
            let type = res.data.depotHeadType
            type = type.replace('其它','')
            that.$refs.billDetail.show(res.data, type);
            that.$refs.billDetail.title="详情";
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
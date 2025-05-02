<!-- create jishenghua-->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="单据编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入单据编号" v-model="queryParam.number"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="商品信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入条码、名称、助记码、规格、型号等信息" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-range-picker
                    style="width:100%"
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
                  <a @click="handleToggleSearch" style="margin-left: 8px">
                    {{ toggleSearchStatus ? '收起' : '展开' }}
                    <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
                  </a>
                </a-col>
              </span>
            </a-row>
            <template v-if="toggleSearchStatus">
              <a-row :gutter="24">
                <a-col :md="6" :sm="24">
                  <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="请选择客户" showSearch optionFilterProp="children" v-model="queryParam.organId">
                      <a-select-option v-for="(item,index) in cusList" :key="index" :value="item.id">
                        {{ item.supplier }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="操作员" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="请选择操作员" showSearch optionFilterProp="children" v-model="queryParam.creator">
                      <a-select-option v-for="(item,index) in userList" :key="index" :value="item.id">
                        {{ item.userName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="请选择单据状态" v-model="queryParam.status">
                      <a-select-option value="0">未审核</a-select-option>
                      <a-select-option value="9" v-if="!checkFlag">审核中</a-select-option>
                      <a-select-option value="1">已审核</a-select-option>
                      <a-select-option value="3">部分销售</a-select-option>
                      <a-select-option value="2">完成销售</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="单据备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入单据备注" v-model="queryParam.remark"></a-input>
                  </a-form-item>
                </a-col>
              </a-row>
            </template>
          </a-form>
        </div>
        <!-- 操作按钮区域 -->
        <div class="table-operator"  style="margin-top: 5px">
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="myHandleAdd" type="primary" icon="plus">新增</a-button>
          <a-button v-if="btnEnableList.indexOf(1)>-1" icon="delete" @click="batchDel">删除</a-button>
          <a-button v-if="quickBtn.saleOut && btnEnableList.indexOf(1)>-1" icon="share-alt" @click="transferBill('转销售出库')">转销售出库</a-button>
          <a-tooltip title="可将状态是部分销售的单据强制完成">
            <a-button v-if="btnEnableList.indexOf(1)>-1" icon="issues-close" @click="batchForceClose">强制结单</a-button>
          </a-tooltip>
          <a-tooltip title="可将状态是部分采购的单据强制完成">
            <a-button v-if="purchaseBySaleFlag && btnEnableList.indexOf(1)>-1" icon="issues-close" @click="batchForceClosePurchase">强制结单-以销定购</a-button>
          </a-tooltip>
          <a-button v-if="checkFlag && btnEnableList.indexOf(2)>-1" icon="check" @click="batchSetStatus(1)">审核</a-button>
          <a-button v-if="checkFlag && btnEnableList.indexOf(7)>-1" icon="stop" @click="batchSetStatus(0)">反审核</a-button>
          <a-button v-if="isShowExcel && btnEnableList.indexOf(3)>-1" icon="download" @click="handleExport">导出</a-button>
          <a-popover trigger="click" placement="right">
            <template slot="content">
              <a-checkbox-group @change="onColChange" v-model="settingDataIndex" :defaultValue="settingDataIndex">
                <a-row style="width: 500px">
                  <template v-for="(item,index) in defColumns">
                    <template>
                      <a-col :span="8">
                        <a-checkbox :value="item.dataIndex">
                          <j-ellipsis :value="item.title" :length="10"></j-ellipsis>
                        </a-checkbox>
                      </a-col>
                    </template>
                  </template>
                </a-row>
                <a-row style="padding-top: 10px;">
                  <a-col>
                    恢复默认列配置：<a-button @click="handleRestDefault" type="link" size="small">恢复默认</a-button>
                  </a-col>
                </a-row>
              </a-checkbox-group>
            </template>
            <a-button icon="setting">列设置</a-button>
          </a-popover>
          <a-tooltip placement="left" title="销售订单不涉及收款金额，销售订单可以转销售出库单，但需要先对销售订单进行审核。
          勾选单据之后可以进行批量操作（删除、审核、反审核）" slot="action">
            <a-icon v-if="btnEnableList.indexOf(1)>-1" type="question-circle" style="font-size:20px;float:right;" />
          </a-tooltip>
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
            :components="handleDrag(columns)"
            :pagination="ipagination"
            :scroll="scroll"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            :expandedRowKeys="expandedRowKeys"
            @expand="onExpand"
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="myHandleDetail(record, '销售订单', prefixNo)">查看</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a v-if="btnEnableList.indexOf(1)>-1" @click="myHandleEdit(record)">编辑</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a v-if="btnEnableList.indexOf(1)>-1" @click="myHandleCopyAdd(record)">复制</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => myHandleDelete(record)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <template slot="customRenderStatus" slot-scope="status">
              <a-tag v-if="status == '0'" color="red">未审核</a-tag>
              <a-tag v-if="status == '1'" color="green">已审核</a-tag>
              <a-tag v-if="status == '2'" color="cyan">完成销售</a-tag>
              <a-tag v-if="status == '3'" color="blue">部分销售</a-tag>
              <a-tag v-if="status == '9'" color="orange">审核中</a-tag>
            </template>
            <template slot="customRenderPurchaseStatus" slot-scope="purchaseStatus">
              <a-tag v-if="purchaseStatus == '0'" color="red">未采购</a-tag>
              <a-tag v-if="purchaseStatus == '2'" color="cyan">完成采购</a-tag>
              <a-tag v-if="purchaseStatus == '3'" color="blue">部分采购</a-tag>
            </template>
            <a-table
              bordered
              size="small"
              slot="expandedRowRender"
              slot-scope="record"
              :loading="record.loading"
              :columns="detailColumns"
              :dataSource="record.childrens"
              :row-key="record => record.id"
              :pagination="false">
            </a-table>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <sale-order-modal ref="modalForm" @ok="modalFormOk" @close="modalFormClose"></sale-order-modal>
        <sale-out-modal ref="transferModalForm" @ok="modalFormOk" @close="modalFormClose"></sale-out-modal>
        <bill-detail ref="modalDetail" @ok="modalFormOk" @close="modalFormClose"></bill-detail>
        <bill-excel-iframe ref="billExcelIframe" @ok="modalFormOk" @close="modalFormClose"></bill-excel-iframe>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import SaleOrderModal from './modules/SaleOrderModal'
  import SaleOutModal from './modules/SaleOutModal'
  import BillDetail from './dialog/BillDetail'
  import BillExcelIframe from '@/components/tools/BillExcelIframe'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { BillListMixin } from './mixins/BillListMixin'
  import { getCurrentSystemConfig } from '@/api/api'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "SaleOrderList",
    mixins:[JeecgListMixin,BillListMixin],
    components: {
      SaleOrderModal,
      SaleOutModal,
      BillDetail,
      BillExcelIframe,
      JEllipsis,
      JDate
    },
    data () {
      return {
        // 查询条件
        queryParam: {
          number: "",
          materialParam: "",
          type: "其它",
          subType: "销售订单",
          organId: undefined,
          depotId: undefined,
          creator: undefined,
          status: undefined,
          remark: ""
        },
        prefixNo: 'XSDD',
        //以销定购开关
        purchaseBySaleFlag: false,
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 默认索引
        defDataIndex:['action','organName','number','materialsList','operTimeStr','userName','materialCount','totalPrice','totalTaxLastMoney',
          'changeAmount','status','purchaseStatus'],
        // 默认列
        defColumns: [
          {
            title: '操作',
            dataIndex: 'action',
            align:"center", width: 180,
            scopedSlots: { customRender: 'action' },
          },
          { title: '客户', dataIndex: 'organName',width:120, ellipsis:true},
          { title: '单据编号', dataIndex: 'number',width:140},
          { title: '商品信息', dataIndex: 'materialsList',width:220, ellipsis:true},
          { title: '单据日期', dataIndex: 'operTimeStr',width:145},
          { title: '销售人员', dataIndex: 'salesManStr',width:120},
          { title: '操作员', dataIndex: 'userName',width:80, ellipsis:true},
          { title: '数量', dataIndex: 'materialCount',width:60},
          { title: '金额合计', dataIndex: 'totalPrice',width:80},
          { title: '含税合计', dataIndex: 'totalTaxLastMoney',width:80,
            customRender:function (text,record,index) {
              if(record.discountLastMoney) {
                return (record.discountMoney + record.discountLastMoney).toFixed(2);
              } else {
                return record.totalPrice;
              }
            }
          },
          { title: '优惠率', dataIndex: 'discount',width:60,
            customRender:function (text,record,index) {
              return text? text + '%':''
            }
          },
          { title: '收款优惠', dataIndex: 'discountMoney',width:80},
          { title: '优惠后金额', dataIndex: 'discountLastMoney',width:100},
          { title: '结算账户', dataIndex: 'accountName',width:80},
          { title: '收取订金', dataIndex: 'changeAmount',width:80},
          { title: '备注', dataIndex: 'remark',width:200},
          { title: '采购进度', dataIndex: 'purchaseStatus', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderPurchaseStatus' }
          },
          { title: '状态', dataIndex: 'status', width: 70, align: "center",
            scopedSlots: { customRender: 'customRenderStatus' }
          }
        ],
        url: {
          list: "/depotHead/list",
          delete: "/depotHead/delete",
          deleteBatch: "/depotHead/deleteBatch",
          forceCloseBatch: "/depotHead/forceCloseBatch",
          forceClosePurchaseBatch: "/depotHead/forceClosePurchaseBatch",
          batchSetStatusUrl: "/depotHead/batchSetStatus"
        }
      }
    },
    created() {
      this.initSystemConfig()
      this.initCustomer()
      this.initUser()
      this.getSystemConfig()
      this.initQuickBtn()
      this.getDepotByCurrentUser()
    },
    computed: {
    },
    methods: {
      getSystemConfig() {
        let statusIndex = 0
        for(let i=0; i<this.columns.length; i++){
          if(this.columns[i].dataIndex === 'purchaseStatus') {
            statusIndex = i
          }
        }
        getCurrentSystemConfig().then((res) => {
          if(res.code === 200 && res.data){
            let purchaseBySaleFlag = res.data.purchaseBySaleFlag
            if(purchaseBySaleFlag === "0") {
              if(statusIndex>0) {
                //移除采购进度列
                this.columns.splice(statusIndex, 1)
              }
            } else {
              if(statusIndex===0) {
                let purchaseStatusObj = { title: '采购进度', dataIndex: 'purchaseStatus', width: 70, align: "center",
                  scopedSlots: { customRender: 'customRenderPurchaseStatus' }
                }
                //添加采购进度列
                this.columns.splice(statusIndex-1, 0, purchaseStatusObj)
              }
            }
          } else {
            if(statusIndex>0) {
              //移除采购进度列
              this.columns.splice(statusIndex, 1)
            }
          }
        })
      },
      searchQuery() {
        this.loadData(1)
        this.getSystemConfig()
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
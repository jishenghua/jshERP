<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :maskClosable="false"
    :keyboard="false"
    :forceRender="true"
    @cancel="handleCancel"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 100%;overflow-y: hidden">
    <template slot="footer">
      <a-button v-if="billPrintFlag" @click="handlePrint">三联打印预览</a-button>
      <!--此处为解决缓存问题-->
      <a-button v-if="billType === '零售出库'" v-print="'#retailOutPrint'">普通打印</a-button>
      <a-button v-if="billType === '零售退货入库'" v-print="'#retailBackPrint'">普通打印</a-button>
      <a-button v-if="billType === '采购订单'" v-print="'#purchaseOrderPrint'">普通打印</a-button>
      <a-button v-if="billType === '采购入库'" v-print="'#purchaseInPrint'">普通打印</a-button>
      <a-button v-if="billType === '采购退货出库'" v-print="'#purchaseBackPrint'">普通打印</a-button>
      <a-button v-if="billType === '销售订单'" v-print="'#saleOrderPrint'">普通打印</a-button>
      <a-button v-if="billType === '销售出库'" v-print="'#saleOutPrint'">普通打印</a-button>
      <a-button v-if="billType === '销售退货入库'" v-print="'#saleBackPrint'">普通打印</a-button>
      <a-button v-if="billType === '其它入库'" v-print="'#otherInPrint'">普通打印</a-button>
      <a-button v-if="billType === '其它出库'" v-print="'#otherOutPrint'">普通打印</a-button>
      <a-button v-if="billType === '调拨出库'" v-print="'#allocationOutPrint'">普通打印</a-button>
      <a-button v-if="billType === '组装单'" v-print="'#assemblePrint'">普通打印</a-button>
      <a-button v-if="billType === '拆卸单'" v-print="'#disassemblePrint'">普通打印</a-button>
      <a-button v-if="billType === '盘点复盘'" v-print="'#stockCheckReplayPrint'">普通打印</a-button>
      <a-button key="back" @click="handleCancel">取消</a-button>
    </template>
    <a-form :form="form">
      <!--零售出库-->
      <template v-if="billType === '零售出库'">
        <section ref="print" id="retailOutPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="会员卡号">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款类型">
                {{model.payType}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="18" :md="12" :sm="24">
              <div :style="tableWidthRetail">
                <a-table
                  ref="table"
                  size="middle"
                  bordered
                  rowKey="id"
                  :pagination="false"
                  :columns="columns"
                  :dataSource="dataSource">
                </a-table>
              </div>
            </a-col>
            <a-col :span="6">
              <a-row class="form-row" :gutter="24">
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据金额">
                    {{model.changeAmount}}
                  </a-form-item>
                </a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款金额">
                    {{model.getAmount}}
                  </a-form-item>
                </a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="找零">
                    {{model.backAmount}}
                  </a-form-item>
                </a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款账户">
                    {{model.accountName}}
                  </a-form-item>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--零售退货-->
      <template v-else-if="billType === '零售退货入库'">
        <section ref="print" id="retailBackPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="会员卡号">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单据">
                <a @click="myHandleDetail(model.linkNumber)">{{model.linkNumber}}</a>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="18" :md="12" :sm="24">
              <div :style="tableWidthRetail">
                <a-table
                  ref="table"
                  size="middle"
                  bordered
                  rowKey="id"
                  :pagination="false"
                  :columns="columns"
                  :dataSource="dataSource">
                </a-table>
              </div>
            </a-col>
            <a-col :span="6">
              <a-row class="form-row" :gutter="24">
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据金额">
                    {{model.changeAmount}}
                  </a-form-item>
                </a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款金额">
                    {{model.getAmount}}
                  </a-form-item>
                </a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="找零">
                    {{model.backAmount}}
                  </a-form-item>
                </a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款账户">
                    {{model.accountName}}
                  </a-form-item>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--采购订单-->
      <template v-else-if="billType === '采购订单'">
        <section ref="print" id="purchaseOrderPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-if="purchaseBySaleFlag" :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联订单">
                <a @click="myHandleDetail(model.linkNumber)">{{model.linkNumber}}</a>
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="支付订金">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
        </section>
      </template>
      <!--采购入库-->
      <template v-else-if="billType === '采购入库'">
        <section ref="print" id="purchaseInPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联订单">
                <a @click="myHandleDetail(model.linkNumber)">{{model.linkNumber}}</a>
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col v-if="model.deposit" :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="扣除订金">
                {{model.deposit}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次付款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col v-if="financialBillNoList.length" :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款单号">
                <template v-for="(item, index) in financialBillNoList">
                  <a @click="myHandleFinancialDetail(item.billNo)">{{item.billNo}}</a><br/>
                </template>
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--采购退货-->
      <template v-else-if="billType === '采购退货出库'">
        <section ref="print" id="purchaseBackPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单据">
                <a @click="myHandleDetail(model.linkNumber)">{{model.linkNumber}}</a>
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="退款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次退款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
            </a-col>
          </a-row>
        </section>
      </template>
      <!--销售订单-->
      <template v-else-if="billType === '销售订单'">
        <section ref="print" id="saleOrderPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                {{model.salesManStr}}
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收取订金">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
        </section>
      </template>
      <!--销售出库-->
      <template v-else-if="billType === '销售出库'">
        <section ref="print" id="saleOutPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联订单">
                <a @click="myHandleDetail(model.linkNumber)">{{model.linkNumber}}</a>
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col v-if="model.deposit" :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="扣除订金">
                {{model.deposit}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次收款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                {{model.salesManStr}}
              </a-form-item>
            </a-col>
            <a-col v-if="financialBillNoList.length" :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款单号">
                <template v-for="(item, index) in financialBillNoList">
                  <a @click="myHandleFinancialDetail(item.billNo)">{{item.billNo}}</a><br/>
                </template>
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--销售退货-->
      <template v-else-if="billType === '销售退货入库'">
        <section ref="print" id="saleBackPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单据">
                <a @click="myHandleDetail(model.linkNumber)">{{model.linkNumber}}</a>
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="退款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次退款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                {{model.salesManStr}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--其它入库-->
      <template v-else-if="billType === '其它入库'">
        <section ref="print" id="otherInPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单据">
                {{model.linkNumber}} {{model.billType}}
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--其它出库-->
      <template v-else-if="billType === '其它出库'">
        <section ref="print" id="otherOutPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单据">
                {{model.linkNumber}} {{model.billType}}
              </a-form-item>
            </a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--调拨出库-->
      <template v-else-if="billType === '调拨出库'">
        <section ref="print" id="allocationOutPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--组装单-->
      <template v-else-if="billType === '组装单'">
        <section ref="print" id="assemblePrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--拆卸单-->
      <template v-else-if="billType === '拆卸单'">
        <section ref="print" id="disassemblePrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--盘点复盘-->
      <template v-else-if="billType === '盘点复盘'">
        <section ref="print" id="stockCheckReplayPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单据">
                {{model.linkNumber}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
          </a-row>
          <div :style="tableWidth">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :pagination="false"
              :columns="columns"
              :dataSource="dataSource">
            </a-table>
          </div>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <template v-if="fileList && fileList.length>0">
        <a-row class="form-row" :gutter="24">
          <a-col :span="10">
            <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 3 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 21 }}" label="附件">
              <j-upload v-model="fileList" bizPath="bill" :disabled="true" :buttonVisible="false"></j-upload>
            </a-form-item>
          </a-col>
          <a-col :span="14"></a-col>
        </a-row>
      </template>
    </a-form>
    <bill-print-iframe ref="modalDetail"></bill-print-iframe>
    <financial-detail ref="financialDetailModal"></financial-detail>
  </j-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { findBillDetailByNumber, findFinancialDetailByNumber, getPlatformConfigByKey, getCurrentSystemConfig} from '@/api/api'
  import { getMpListShort } from "@/utils/util"
  import BillPrintIframe from './BillPrintIframe'
  import FinancialDetail from '../../financial/dialog/FinancialDetail'
  import JUpload from '@/components/jeecg/JUpload'
  import Vue from 'vue'
  export default {
    name: 'BillDetail',
    components: {
      BillPrintIframe,
      FinancialDetail,
      JUpload
    },
    data () {
      return {
        title: "详情",
        width: '1600px',
        visible: false,
        model: {},
        billType: '',
        billPrintFlag: false,
        fileList: [],
        purchaseBySaleFlag: false,
        financialBillNoList: [],
        tableWidth: {
          'width': '1500px'
        },
        tableWidthRetail: {
          'width': '1150px'
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        form: this.$form.createForm(this),
        loading: false,
        dataSource: [],
        url: {
          detailList: '/depotItem/getDetailList'
        },
        //表头
        columns:[],
        //列定义
        defColumns: [],
        retailOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ],
        retailBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ],
        purchaseOrderColumns: [
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '已入库', dataIndex: 'finishNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '税率(%)', dataIndex: 'taxRate'},
          { title: '税额', dataIndex: 'taxMoney'},
          { title: '价税合计', dataIndex: 'taxLastMoney'},
          { title: '备注', dataIndex: 'remark'}
        ],
        purchaseInColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '税率(%)', dataIndex: 'taxRate'},
          { title: '税额', dataIndex: 'taxMoney'},
          { title: '价税合计', dataIndex: 'taxLastMoney'},
          { title: '备注', dataIndex: 'remark'}
        ],
        purchaseBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '税率(%)', dataIndex: 'taxRate'},
          { title: '税额', dataIndex: 'taxMoney'},
          { title: '价税合计', dataIndex: 'taxLastMoney'},
          { title: '备注', dataIndex: 'remark'}
        ],
        saleOrderColumns: [
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '已出库', dataIndex: 'finishNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '税率(%)', dataIndex: 'taxRate'},
          { title: '税额', dataIndex: 'taxMoney'},
          { title: '价税合计', dataIndex: 'taxLastMoney'},
          { title: '备注', dataIndex: 'remark'}
        ],
        saleOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '税率(%)', dataIndex: 'taxRate'},
          { title: '税额', dataIndex: 'taxMoney'},
          { title: '价税合计', dataIndex: 'taxLastMoney'},
          { title: '备注', dataIndex: 'remark'}
        ],
        saleBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '税率(%)', dataIndex: 'taxRate'},
          { title: '税额', dataIndex: 'taxMoney'},
          { title: '价税合计', dataIndex: 'taxLastMoney'},
          { title: '备注', dataIndex: 'remark'}
        ],
        otherInColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ],
        otherOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ],
        allocationOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '调入仓库', dataIndex: 'anotherDepotName'},
          { title: '单位', dataIndex: 'unit'},
          { title: '序列号', dataIndex: 'snList'},
          { title: '批号', dataIndex: 'batchNumber'},
          { title: '有效期', dataIndex: 'expirationDate'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ],
        assembleColumns: [
          { title: '商品类型', dataIndex: 'mType'},
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ],
        disassembleColumns: [
          { title: '商品类型', dataIndex: 'mType'},
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '颜色', dataIndex: 'color'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ],
        stockCheckReplayColumns: [
          { title: '仓库名称', dataIndex: 'depotName'},
          { title: '条码', dataIndex: 'barCode'},
          { title: '名称', dataIndex: 'name'},
          { title: '规格', dataIndex: 'standard'},
          { title: '型号', dataIndex: 'model'},
          { title: '扩展信息', dataIndex: 'materialOther'},
          { title: '库存', dataIndex: 'stock'},
          { title: '单位', dataIndex: 'unit'},
          { title: '多属性', dataIndex: 'sku'},
          { title: '数量', dataIndex: 'operNumber'},
          { title: '单价', dataIndex: 'unitPrice'},
          { title: '金额', dataIndex: 'allPrice'},
          { title: '备注', dataIndex: 'remark'}
        ]
      }
    },
    created () {
      let realScreenWidth = window.screen.width * window.devicePixelRatio
      this.width = realScreenWidth<1500?'1300px':'1550px'
      this.tableWidth = {
        'width': realScreenWidth<1500?'1250px':'1500px'
      }
    },
    methods: {
      initSetting(record, type, ds) {
        if (type === '零售出库') {
          this.defColumns = this.retailOutColumns
        } else if (type === '零售退货入库') {
          this.defColumns = this.retailBackColumns
        } else if (type === '采购订单') {
          this.defColumns = this.purchaseOrderColumns
        } else if (type === '采购入库') {
          this.defColumns = this.purchaseInColumns
        } else if (type === '采购退货出库') {
          this.defColumns = this.purchaseBackColumns
        } else if (type === '销售订单') {
          this.defColumns = this.saleOrderColumns
        } else if (type === '销售出库') {
          this.defColumns = this.saleOutColumns
        } else if (type === '销售退货入库') {
          this.defColumns = this.saleBackColumns
        } else if (type === '其它入库') {
          this.defColumns = this.otherInColumns
        } else if (type === '其它出库') {
          this.defColumns = this.otherOutColumns
        } else if (type === '调拨出库') {
          this.defColumns = this.allocationOutColumns
        } else if (type === '组装单') {
          this.defColumns = this.assembleColumns
        } else if (type === '拆卸单') {
          this.defColumns = this.disassembleColumns
        } else if (type === '盘点复盘') {
          this.defColumns = this.stockCheckReplayColumns
        }
        //判断序列号、批号、有效期、多属性是否有值
        let needAddkeywords = []
        for (let i = 0; i < ds.length; i++) {
          if(ds[i].snList) {
            needAddkeywords.push('snList')
          }
          if(ds[i].batchNumber) {
            needAddkeywords.push('batchNumber')
          }
          if(ds[i].expirationDate) {
            needAddkeywords.push('expirationDate')
          }
          if(ds[i].sku) {
            needAddkeywords.push('sku')
          }
        }
        if(record.status === '3') {
          //部分采购|部分销售的时候显示全部列
          this.columns = this.defColumns
        } else if(record.purchaseStatus === '3') {
          //将已出库的标题转为已采购，针对销售订单转采购订单的场景
          let currentCol = []
          for(let i=0; i<this.defColumns.length; i++){
            let info = {}
            info.title = this.defColumns[i].title
            info.dataIndex = this.defColumns[i].dataIndex
            info.width = this.defColumns[i].width
            if(this.defColumns[i].dataIndex === 'finishNumber') {
              info.title = '已采购'
            }
            currentCol.push(info)
          }
          this.columns = currentCol
        } else {
          let currentCol = []
          for(let i=0; i<this.defColumns.length; i++){
            //移除列
            let needRemoveKeywords = ['finishNumber','snList','batchNumber','expirationDate','sku']
            if(needRemoveKeywords.indexOf(this.defColumns[i].dataIndex)===-1) {
              let info = {}
              info.title = this.defColumns[i].title
              info.dataIndex = this.defColumns[i].dataIndex
              info.width = this.defColumns[i].width
              currentCol.push(info)
            }
            //添加有数据的列
            if(needAddkeywords.indexOf(this.defColumns[i].dataIndex)>-1) {
              let info = {}
              info.title = this.defColumns[i].title
              info.dataIndex = this.defColumns[i].dataIndex
              info.width = this.defColumns[i].width
              currentCol.push(info)
            }
          }
          this.columns = currentCol
        }
      },
      initPlatform() {
        getPlatformConfigByKey({"platformKey": "bill_print_flag"}).then((res)=> {
          if (res && res.code === 200) {
            if(this.billType === '采购订单'||this.billType === '采购入库'||this.billType === '采购退货出库'||
              this.billType === '销售订单'||this.billType === '销售出库'||this.billType === '销售退货入库') {
              this.billPrintFlag = res.data.platformValue==='1'?true:false
            }
          }
        })
      },
      getSystemConfig() {
        getCurrentSystemConfig().then((res) => {
          if(res.code === 200 && res.data){
            this.purchaseBySaleFlag = res.data.purchaseBySaleFlag==='1'?true:false
          }
        })
      },
      getFinancialBillNoByBillId(billId) {
        getAction('/accountHead/getFinancialBillNoByBillId', {billId: billId}).then(res => {
          if(res && res.code === 200){
            this.financialBillNoList = res.data
          }
        })
      },
      show(record, type) {
        this.billType = type
        //附件下载
        this.fileList = record.fileName
        this.visible = true;
        this.model = Object.assign({}, record);
        if(this.model.backAmount) {
          this.model.getAmount = (this.model.changeAmount + this.model.backAmount).toFixed(2)
        } else {
          this.model.getAmount = this.model.changeAmount
        }
        this.model.debt = (this.model.discountLastMoney + this.model.otherMoney - (this.model.deposit + this.model.changeAmount)).toFixed(2)
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id'))
        });
        let showType = 'basic'
        if(record.status === '3') {
          showType = 'basic'
        } else if(record.purchaseStatus === '3') {
          showType = 'purchase'
        }
        let params = {
          headerId: this.model.id,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList')),  //扩展属性
          linkType: showType
        }
        let url = this.readOnly ? this.url.detailList : this.url.detailList;
        this.requestSubTableData(record, type, url, params);
        this.initPlatform()
        this.getSystemConfig()
        this.getFinancialBillNoByBillId(this.model.id)
      },
      requestSubTableData(record, type, url, params, success) {
        this.loading = true
        getAction(url, params).then(res => {
          if(res && res.code === 200){
            this.dataSource = res.data.rows
            this.initSetting(record, type, this.dataSource)
            typeof success === 'function' ? success(res) : ''
          }
        }).finally(() => {
          this.loading = false
        })
      },
      handleCancel() {
        this.close()
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      myHandleDetail(billNumber) {
        findBillDetailByNumber({ number: billNumber }).then((res) => {
          if (res && res.code === 200) {
            let type = res.data.type === "其它"? "":res.data.type
            this.show(res.data, res.data.subType + type);
            this.title = res.data.subType + type + "-详情";
          }
        })
      },
      myHandleFinancialDetail(billNo) {
        let that = this
        findFinancialDetailByNumber({ billNo: billNo }).then((res) => {
          if (res && res.code === 200) {
            if(that.$refs.financialDetailModal) {
              that.$refs.financialDetailModal.show(res.data, res.data.type);
              that.$refs.financialDetailModal.title= res.data.type + "-详情";
            }
          }
        })
      },
      //三联打印预览
      handlePrint() {
        getPlatformConfigByKey({"platformKey": "bill_print_url"}).then((res)=> {
          if (res && res.code === 200) {
            let billPrintUrl = res.data.platformValue + '?no=' + this.model.number
            let billPrintHeight = this.dataSource.length*50 + 600
            this.$refs.modalDetail.show(this.model, billPrintUrl, billPrintHeight);
            this.$refs.modalDetail.title = this.billType + "-三联打印预览";
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
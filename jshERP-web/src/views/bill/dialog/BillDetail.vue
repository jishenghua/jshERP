<template>
  <a-card :bordered="false">
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
        <a-button v-if="billType === '调拨出库'" v-print="'#allocationOutPrint'">普通打印</a-button>
        <a-button v-if="billType === '组装单'" v-print="'#assemblePrint'">普通打印</a-button>
        <a-button v-if="billType === '拆卸单'" v-print="'#disassemblePrint'">普通打印</a-button>
        <a-button v-if="billType === '其它入库'" v-print="'#otherInPrint'">普通打印</a-button>
        <a-button v-if="billType === '其它出库'" v-print="'#otherOutPrint'">普通打印</a-button>
        <a-button v-if="billType === '采购退货出库'" v-print="'#purchaseBackPrint'">普通打印</a-button>
        <a-button v-if="billType === '采购入库'" v-print="'#purchaseInPrint'">普通打印</a-button>
        <a-button v-if="billType === '采购订单'" v-print="'#purchaseOrderPrint'">普通打印</a-button>
        <a-button v-if="billType === '零售退货入库'" v-print="'#retailBackPrint'">普通打印</a-button>
        <a-button v-if="billType === '零售出库'" v-print="'#retailOutPrint'">普通打印</a-button>
        <a-button v-if="billType === '销售退货入库'" v-print="'#saleBackPrint'">普通打印</a-button>
        <a-button v-if="billType === '销售订单'" v-print="'#saleOrderPrint'">普通打印</a-button>
        <a-button v-if="billType === '销售出库'" v-print="'#saleOutPrint'">普通打印</a-button>
        <a-button key="back" @click="handleCancel">取消</a-button>
      </template>
      <a-form :form="form">
          <!--调拨出库-->
          <template v-if="billType === '调拨出库'">
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
                  :columns="allocationOutColumns"
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
                  :columns="assembleColumns"
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
                  :columns="disassembleColumns"
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
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单号">
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
                  :columns="otherInColumns"
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
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联单号">
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
                  :columns="otherOutColumns"
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
                  :columns="purchaseBackColumns"
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
                  :columns="purchaseInColumns"
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
                <a-col :span="6">
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
                <a-col :span="6"></a-col>
              </a-row>
              <div :style="tableWidth">
                <a-table
                  ref="table"
                  size="middle"
                  bordered
                  rowKey="id"
                  :pagination="false"
                  :columns="purchaseOrderColumns"
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
                      :columns="retailBackColumns"
                      :dataSource="dataSource">
                    </a-table>
                  </div>
                </a-col>
                <a-col :span="6">
                  <a-row class="form-row" :gutter="24">
                    <a-col :lg="24" :md="6" :sm="6">
                      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实付金额">
                        {{model.changeAmount}}
                      </a-form-item>
                    </a-col>
                    <a-col :lg="24" :md="6" :sm="6">
                      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款金额">
                        {{model.changeAmount}}
                      </a-form-item>
                    </a-col>
                    <a-col :lg="24" :md="6" :sm="6">
                      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="找零">
                        0
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
          <!--零售出库-->
          <template v-else-if="billType === '零售出库'">
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
                      :columns="retailOutColumns"
                      :dataSource="dataSource">
                    </a-table>
                  </div>
                </a-col>
                <a-col :span="6">
                  <a-row class="form-row" :gutter="24">
                    <a-col :lg="24" :md="6" :sm="6">
                      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实收金额">
                        {{model.changeAmount}}
                      </a-form-item>
                    </a-col>
                    <a-col :lg="24" :md="6" :sm="6">
                      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款金额">
                        {{model.changeAmount}}
                      </a-form-item>
                    </a-col>
                    <a-col :lg="24" :md="6" :sm="6">
                      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="找零">
                        0
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
                  :columns="saleBackColumns"
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
                  :columns="saleOrderColumns"
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
                  :columns="saleOutColumns"
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
                <a-col :span="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                    {{model.salesManStr}}
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
    </j-modal>
    <bill-print-iframe ref="modalDetail"></bill-print-iframe>
  </a-card>
</template>

<script>
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { findBillDetailByNumber, getPlatformConfigByKey} from '@/api/api'
  import { getMpListShort } from "@/utils/util"
  import BillPrintIframe from './BillPrintIframe'
  import JUpload from '@/components/jeecg/JUpload'
  import Vue from 'vue'
  export default {
    name: 'BillDetail',
    components: {
      BillPrintIframe,
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
        tableWidth: {
          'width': '1550px'
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
        allocationOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '调入仓库', dataIndex: 'anotherDepotName', width: '8%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        assembleColumns: [
          { title: '商品类型', dataIndex: 'mType',width:'7%'},
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        disassembleColumns: [
          { title: '商品类型', dataIndex: 'mType',width:'7%'},
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        otherInColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        otherOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        purchaseBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '7%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '5%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        purchaseInColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '7%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '5%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        purchaseOrderColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        retailBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        retailOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        saleBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '7%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '5%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        saleOrderColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        saleOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '7%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '多属性', dataIndex: 'sku', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '5%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ]
      }
    },
    created () {
    },
    methods: {
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
      show(record, type) {
        this.billType = type
        //附件下载
        this.fileList = record.fileName
        this.visible = true;
        this.model = Object.assign({}, record);
        this.model.debt = (this.model.discountLastMoney + this.model.otherMoney - this.model.changeAmount).toFixed(2)
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id'))
        });
        let params = {
          headerId: this.model.id,
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        }
        let url = this.readOnly ? this.url.detailList : this.url.detailList;
        this.requestSubTableData(url, params);
        this.initPlatform()
      },
      requestSubTableData(url, params, success) {
        this.loading = true
        getAction(url, params).then(res => {
          if(res && res.code === 200){
            this.dataSource = res.data.rows
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
      //三联打印预览
      handlePrint() {
        getPlatformConfigByKey({"platformKey": "bill_print_url"}).then((res)=> {
          if (res && res.code === 200) {
            let billPrintUrl = res.data.platformValue + '?no=' + this.model.number
            let billPrintHeight = this.dataSource.length*50 + 400
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
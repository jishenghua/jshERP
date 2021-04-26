<template>
  <a-card :bordered="false" class="card-area">
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
      <a-form :form="form">
        <!--调拨出库-->
        <template v-if="billType === '调拨出库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="allocationOutColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--组装单-->
        <template v-else-if="billType === '组装单'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="assembleColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--拆卸单-->
        <template v-else-if="billType === '拆卸单'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="disassembleColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--其它入库-->
        <template v-else-if="billType === '其它入库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="otherInColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--其它出库-->
        <template v-else-if="billType === '其它出库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="otherOutColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--采购退货-->
        <template v-else-if="billType === '采购退货出库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="purchaseBackColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="退款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次退款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
            </a-col>
          </a-row>
        </template>
        <!--采购入库-->
        <template v-else-if="billType === '采购入库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联订单">
                {{model.linkNumber}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="purchaseInColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次付款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
            </a-col>
          </a-row>
        </template>
        <!--采购订单-->
        <template v-else-if="billType === '采购订单'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="purchaseOrderColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--零售退货-->
        <template v-else-if="billType === '零售退货入库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="会员卡号">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="18" :md="12" :sm="24">
              <a-table
                ref="table"
                size="middle"
                bordered
                rowKey="id"
                :pagination="false"
                :columns="retailBackColumns"
                :dataSource="dataSource">
              </a-table>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-row class="form-row" :gutter="24">
                <a-col :lg="24" :md="6" :sm="6"><br/><br/></a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实付金额">
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
        </template>
        <!--零售出库-->
        <template v-else-if="billType === '零售出库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="会员卡号">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款类型">
                {{model.payType}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="18" :md="12" :sm="24">
              <a-table
                ref="table"
                size="middle"
                bordered
                rowKey="id"
                :pagination="false"
                :columns="retailOutColumns"
                :dataSource="dataSource">
              </a-table>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-row class="form-row" :gutter="24">
                <a-col :lg="24" :md="6" :sm="6"><br/><br/></a-col>
                <a-col :lg="24" :md="6" :sm="6">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实付金额">
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
        </template>
        <!--销售退货-->
        <template v-else-if="billType === '销售退货入库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                {{model.salesManStr}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="saleBackColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="退款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次退款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                {{model.salesMan}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--销售订单-->
        <template v-else-if="billType === '销售订单'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                {{model.salesManStr}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="saleOrderColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <!--销售出库-->
        <template v-else-if="billType === '销售出库'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.operTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.number}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关联订单">
                {{model.linkNumber}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="saleOutColumns"
            :dataSource="dataSource">
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠率">
                {{model.discount}}%
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款优惠">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 6 }}" :wrapperCol="wrapperCol" label="优惠后金额">
                {{model.discountLastMoney}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其它费用">
                {{model.otherMoney}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结算账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次收款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="本次欠款">
                {{model.debt}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="销售人员">
                {{model.salesManStr}}
              </a-form-item>
            </a-col>
          </a-row>
        </template>
      </a-form>
    </j-modal>
  </a-card>
</template>

<script>
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { getMpListShort } from "@/utils/util"
  import Vue from 'vue'
  export default {
    name: 'BillDetail',
    data () {
      return {
        title: "详情",
        width: '1450px',
        visible: false,
        model: {},
        billType: '',
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
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        purchaseBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '6%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '6%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '6%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        purchaseInColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '6%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '6%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '6%'},
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
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        saleBackColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '6%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '6%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '6%'},
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
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ],
        saleOutColumns: [
          { title: '仓库名称', dataIndex: 'depotName', width: '8%'},
          { title: '条码', dataIndex: 'barCode', width: '10%'},
          { title: '名称', dataIndex: 'name', width: '8%'},
          { title: '规格', dataIndex: 'standard', width: '5%'},
          { title: '型号', dataIndex: 'model', width: '5%'},
          { title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          { title: '库存', dataIndex: 'stock', width: '5%'},
          { title: '单位', dataIndex: 'unit', width: '4%'},
          { title: '数量', dataIndex: 'operNumber', width: '5%'},
          { title: '单价', dataIndex: 'unitPrice', width: '5%'},
          { title: '含税单价', dataIndex: 'taxUnitPrice', width: '6%'},
          { title: '金额', dataIndex: 'allPrice', width: '5%'},
          { title: '税率(%)', dataIndex: 'taxRate', width: '6%'},
          { title: '税额', dataIndex: 'taxMoney', width: '5%'},
          { title: '价税合计', dataIndex: 'taxLastMoney', width: '6%'},
          { title: '备注', dataIndex: 'remark', width: '5%'}
        ]
      }
    },
    created () {
    },
    methods: {
      show(record, type) {
        this.billType = type
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
      }
    }
  }
</script>

<style scoped>

</style>
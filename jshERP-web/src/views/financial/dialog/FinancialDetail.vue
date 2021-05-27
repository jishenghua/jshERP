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
        <!--收预付款-->
        <template v-if="financialType === '收预付款'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款会员">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经手人">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.billNo}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="advanceInColumns"
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
        <!--转账-->
        <template v-if="financialType === '转账'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经手人">
                <a-input v-decorator="['id']" hidden/>
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.billNo}}
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
            :columns="giroColumns"
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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实付金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
        </template>
        <!--收入-->
        <template v-if="financialType === '收入'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="往来单位">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经手人">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.billNo}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="itemInColumns"
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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
        </template>
        <!--支出-->
        <template v-if="financialType === '支出'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="往来单位">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经手人">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.billNo}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="itemOutColumns"
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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
        </template>
        <!--收款-->
        <template v-if="financialType === '收款'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款单位">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经手人">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.billNo}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="moneyInColumns"
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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
        </template>
        <!--付款-->
        <template v-if="financialType === '付款'">
          <a-row class="form-row" :gutter="24">
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款单位">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经手人">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.billNo}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :pagination="false"
            :columns="moneyOutColumns"
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
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
            <a-col :lg="6" :md="12" :sm="24"></a-col>
          </a-row>
        </template>
      </a-form>
    </j-modal>
  </a-card>
</template>
<script>
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  export default {
    name: 'FinancialDetail',
    data () {
      return {
        title: "详情",
        width: '1200px',
        visible: false,
        model: {},
        financialType: '',
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        form: this.$form.createForm(this),
        loading: false,
        dataSource: [],
        url: {
          detailList: '/accountItem/getDetailList'
        },
        advanceInColumns: [
          { title: '账户名称',dataIndex: 'accountName',width: '30%'},
          { title: '金额',dataIndex: 'eachAmount', width: '30%'},
          { title: '备注',dataIndex: 'remark', width: '30%'}
        ],
        giroColumns: [
          { title: '账户名称',dataIndex: 'accountName',width: '30%'},
          { title: '金额',dataIndex: 'eachAmount', width: '30%'},
          { title: '备注',dataIndex: 'remark', width: '30%'}
        ],
        itemInColumns: [
          { title: '收入项目',dataIndex: 'inOutItemName',width: '30%'},
          { title: '金额',dataIndex: 'eachAmount', width: '30%'},
          { title: '备注',dataIndex: 'remark', width: '30%'}
        ],
        itemOutColumns: [
          { title: '支出项目',dataIndex: 'inOutItemName',width: '30%'},
          { title: '金额',dataIndex: 'eachAmount', width: '30%'},
          { title: '备注',dataIndex: 'remark', width: '30%'}
        ],
        moneyInColumns: [
          { title: '账户名称',dataIndex: 'accountName',width: '20%'},
          { title: '金额',dataIndex: 'eachAmount', width: '10%'},
          { title: '备注',dataIndex: 'remark', width: '30%'}
        ],
        moneyOutColumns: [
          { title: '账户名称',dataIndex: 'accountName',width: '20%'},
          { title: '金额',dataIndex: 'eachAmount', width: '10%'},
          { title: '备注',dataIndex: 'remark', width: '30%'}
        ],
      }
    },
    created () {
    },
    methods: {
      show(record, type) {
        this.financialType = type
        this.visible = true;
        this.model = Object.assign({}, record);
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id'))
        });
        let params = {
          headerId: this.model.id,
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
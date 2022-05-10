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
      <!--此处为解决缓存问题-->
      <a-button v-if="financialType === '收预付款'" v-print="'#advanceInPrint'" ghost type="primary">打印</a-button>
      <a-button v-if="financialType === '转账'" v-print="'#giroPrint'" ghost type="primary">打印</a-button>
      <a-button v-if="financialType === '收入'" v-print="'#itemInPrint'" ghost type="primary">打印</a-button>
      <a-button v-if="financialType === '支出'" v-print="'#itemOutPrint'" ghost type="primary">打印</a-button>
      <a-button v-if="financialType === '收款'" v-print="'#moneyInPrint'" ghost type="primary">打印</a-button>
      <a-button v-if="financialType === '付款'" v-print="'#moneyOutPrint'" ghost type="primary">打印</a-button>
      <a-button key="back" @click="handleCancel">取消</a-button>
    </template>
    <a-form :form="form">
      <!--收预付款-->
      <template v-if="financialType === '收预付款'">
        <section ref="print" id="advanceInPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款会员">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
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
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合计金额">
                {{model.totalPrice}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
        </section>
      </template>
      <!--转账-->
      <template v-if="financialType === '转账'">
        <section ref="print" id="giroPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
                <a-input v-decorator="['id']" hidden/>
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据编号">
                {{model.billNo}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
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
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实付金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
        </section>
      </template>
      <!--收入-->
      <template v-if="financialType === '收入'">
        <section ref="print" id="itemInPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
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
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收入账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收入金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
        </section>
      </template>
      <!--支出-->
      <template v-if="financialType === '支出'">
        <section ref="print" id="itemOutPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
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
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="支出账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="支出金额">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
            <a-col :span="6"></a-col>
            <a-col :span="6"></a-col>
          </a-row>
        </section>
      </template>
      <!--收款-->
      <template v-if="financialType === '收款'">
        <section ref="print" id="moneyInPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
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
            <span slot="numberCustomRender" slot-scope="text, record">
              <a @click="myHandleDetail(record)">{{record.billNumber}}</a>
            </span>
          </a-table>
          <a-row class="form-row" :gutter="24">
            <a-col :lg="24" :md="24" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="" style="padding:20px 10px;">
                {{model.remark}}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收款账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合计收款">
                {{model.totalPrice}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠金额">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实际收款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <!--付款-->
      <template v-if="financialType === '付款'">
        <section ref="print" id="moneyOutPrint">
          <a-row class="form-row" :gutter="24">
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
                <a-input v-decorator="['id']" hidden/>
                {{model.organName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="财务人员">
                {{model.handsPersonName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单据日期">
                {{model.billTimeStr}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
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
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="付款账户">
                {{model.accountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合计付款">
                {{model.totalPrice}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="优惠金额">
                {{model.discountMoney}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="实际付款">
                {{model.changeAmount}}
              </a-form-item>
            </a-col>
          </a-row>
        </section>
      </template>
      <template v-if="fileList && fileList.length>0">
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 3 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 21 }}" label="附件">
              <j-upload v-model="fileList" bizPath="bill" :disabled="true" :buttonVisible="false"></j-upload>
            </a-form-item>
          </a-col>
          <a-col :span="12"></a-col>
        </a-row>
      </template>
    </a-form>
    <!-- 表单区域 -->
    <bill-detail ref="modalDetail"></bill-detail>
  </j-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import BillDetail from '../../bill/dialog/BillDetail'
  import { getAction } from '@/api/manage'
  import { findBillDetailByNumber} from '@/api/api'
  import JUpload from '@/components/jeecg/JUpload'
  export default {
    name: 'FinancialDetail',
    components: {
      BillDetail,
      JUpload
    },
    data () {
      return {
        title: "详情",
        width: '1600px',
        visible: false,
        model: {},
        financialType: '',
        fileList: [],
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
          {
            title: '销售单据编号', dataIndex: 'billNumber', width: '20%',
            scopedSlots: { customRender: 'numberCustomRender' },
          },
          { title: '应收欠款',dataIndex: 'needDebt', width: '10%'},
          { title: '已收欠款',dataIndex: 'finishDebt', width: '10%'},
          { title: '本次收款',dataIndex: 'eachAmount', width: '10%'},
          { title: '备注',dataIndex: 'remark', width: '20%'}
        ],
        moneyOutColumns: [
          { title: '采购单据编号',dataIndex: 'billNumber',width: '20%'},
          { title: '应付欠款',dataIndex: 'needDebt', width: '10%'},
          { title: '已付欠款',dataIndex: 'finishDebt', width: '10%'},
          { title: '本次付款',dataIndex: 'eachAmount', width: '10%'},
          { title: '备注',dataIndex: 'remark', width: '20%'}
        ],
      }
    },
    created () {
      let realScreenWidth = window.screen.width * window.devicePixelRatio
      this.width = realScreenWidth<1600?'1300px':'1600px'
    },
    methods: {
      show(record, type) {
        this.financialType = type
        //附件下载
        this.fileList = record.fileName
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
      },
      myHandleDetail(record) {
        findBillDetailByNumber({ number: record.billNumber }).then((res) => {
          if (res && res.code === 200) {
            this.$refs.modalDetail.show(res.data, res.data.subType + res.data.type);
            this.$refs.modalDetail.title= res.data.subType + res.data.type + "-详情";
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
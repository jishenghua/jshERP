<!-- by 7527 18920 -->
<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="1600"
      :visible="visible"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @cancel="handleCancel"
      cancelText="关闭"
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
              <a-form-item label="单据编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input placeholder="请输入单据编号" v-model="queryParam.billNo"></a-input>
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
                  <a-select-option value="1">已审核</a-select-option>
                </a-select>
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
            <template v-if="toggleSearchStatus">
              <a-col :md="6" :sm="24">
                <a-form-item label="财务人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select placeholder="请选择财务人员" showSearch optionFilterProp="children" v-model="queryParam.handsPersonId">
                    <a-select-option v-for="(item,index) in personList" :key="index" :value="item.id">
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="账户信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select placeholder="请选择账户信息" showSearch optionFilterProp="children" v-model="queryParam.accountId">
                    <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="单据备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入单据备注" v-model="queryParam.remark"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="销售单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入销售单号" v-model="queryParam.number"></a-input>
                </a-form-item>
              </a-col>
            </template>
          </a-row>
        </a-form>
      </div>
      <!-- table区域-begin -->
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :components="handleDrag(columns)"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange">
        <span slot="billNoCustomRender" slot-scope="text, record">
          <a @click="myHandleDetail(record, queryParam.type, prefixNo)">{{text}}</a>
        </span>
        <template slot="customRenderStatus" slot-scope="status">
          <a-tag v-if="status == '0'" color="red">未审核</a-tag>
          <a-tag v-if="status == '1'" color="green">已审核</a-tag>
          <a-tag v-if="status == '9'" color="orange">审核中</a-tag>
        </template>
      </a-table>
      <!-- table区域-end -->
      <!-- 表单区域 -->
      <financial-detail ref="modalDetail" @ok="modalFormOk" @close="modalFormClose"></financial-detail>
    </a-modal>
  </div>
</template>
<script>
  import FinancialDetail from '../../financial/dialog/FinancialDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { FinancialListMixin } from '../../financial/mixins/FinancialListMixin'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "HistoryFinancialList",
    mixins:[JeecgListMixin, FinancialListMixin],
    components: {
      FinancialDetail,
      JDate
    },
    data () {
      return {
        title:"",
        visible: false,
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {
          billNo: "",
          beginTime: "",
          endTime: "",
          searchMaterial: "",
          type: "",
          organId: "",
          creator: "",
          handsPersonId: "",
          accountId: "",
          status: "",
          remark: "",
          number: ""
        },
        prefixNo: '',
        disableMixinCreated: true,
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
          {
            title: '单据编号', dataIndex: 'billNo', width: 120, scopedSlots: { customRender: 'billNoCustomRender' },
          },
          { title: '客户', dataIndex: 'organName',width:140, ellipsis:true},
          { title: '单据日期 ', dataIndex: 'billTimeStr',width:140},
          { title: '操作员', dataIndex: 'userName',width:100, ellipsis:true},
          { title: '财务人员', dataIndex: 'handsPersonName',width:100},
          { title: '合计收款', dataIndex: 'totalPrice',width:80},
          { title: '优惠金额', dataIndex: 'discountMoney',width:80},
          { title: '实际收款', dataIndex: 'changeAmount',width:80},
          { title: '备注', dataIndex: 'remark',width:160},
          { title: '状态', dataIndex: 'status', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderStatus' }
          }
        ],
        url: {
          list: "/accountHead/list"
        }
      }
    },
    computed: {
    },
    created () {
      this.initSystemConfig()
      this.initUser()
      this.initPerson()
      this.initAccount()
    },
    methods: {
      show() {
        if(this.queryParam.type === '付款') {
          this.columns[2].title = '供应商'
          this.columns[6].title = '合计付款'
          this.columns[8].title = '实际付款'
          this.prefixNo = 'FK'
        } else if(this.queryParam.type === '收款') {
          this.columns[2].title = '客户'
          this.columns[6].title = '合计收款'
          this.columns[8].title = '实际收款'
          this.prefixNo = 'SK'
        }
        this.loadData(1)
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      searchReset() {
        this.queryParam = {
          organId: this.queryParam.organId,
          beginTime: this.queryParam.beginTime,
          endTime: this.queryParam.endTime,
          type: this.queryParam.type
        }
        this.loadData(1);
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
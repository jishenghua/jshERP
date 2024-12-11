<!-- f r o m 7 5  2 7 1  8 9 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :style="cardStyle" :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select placeholder="请选择供应商" v-model="queryParam.organId"
                    :dropdownMatchSelectWidth="false" showSearch allow-clear optionFilterProp="children">
                    <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                      {{ item.supplier }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="账单周期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-range-picker
                    style="width: 100%"
                    v-model="queryParam.createTimeRange"
                    format="YYYY-MM-DD"
                    :placeholder="['开始时间', '结束时间']"
                    @change="onDateChange"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <span class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" v-print="'#reportPrint'" icon="printer">打印</a-button>
                  <a-button style="margin-left: 8px" @click="exportExcel" icon="download">导出</a-button>
                  <a @click="handleToggleSearch" style="margin-left: 8px">
                    {{ toggleSearchStatus ? '收起' : '展开' }}
                    <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
                  </a>
                </span>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item>
                  {{firstTotal}} {{lastTotal}}
                </a-form-item>
              </a-col>
              <template v-if="toggleSearchStatus">
                <a-col :md="6" :sm="24">
                  <a-form-item label="欠款情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select v-model="queryParam.hasDebt">
                      <a-select-option value="1">有欠款</a-select-option>
                      <a-select-option value="0">无欠款</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </template>
            </a-row>
          </a-form>
        </div>
        <!-- table区域-begin -->
        <section ref="print" id="reportPrint">
          <a-table
            bordered
            ref="table"
            size="middle"
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :components="handleDrag(columns)"
            :pagination="false"
            :scroll="scroll"
            :loading="loading"
            @change="handleTableChange">
            <span slot="customTitle">
              <a-popover trigger="click" placement="right">
                <template slot="content">
                  <a-checkbox-group @change="onColChange" v-model="settingDataIndex" :defaultValue="settingDataIndex">
                    <a-row style="width: 600px">
                      <template v-for="(item,index) in defColumns">
                        <template>
                          <a-col :span="6">
                            <a-checkbox :value="item.dataIndex" v-if="item.dataIndex==='rowIndex'" disabled></a-checkbox>
                            <a-checkbox :value="item.dataIndex" v-if="item.dataIndex!=='rowIndex'">
                              <j-ellipsis :value="item.title" v-if="item.dataIndex!=='allNeed'" :length="10"></j-ellipsis>
                              <j-ellipsis value="期末应付" v-if="item.dataIndex==='allNeed'" :length="10"></j-ellipsis>
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
                <a-icon type="setting" />
              </a-popover>
            </span>
            <span slot="action" slot-scope="text, record">
              <a @click="showDebtAccountList(record)">{{record.id?'详情':''}}</a>
            </span>
            <span slot="allNeedTitle">
              期末应付
              <a-tooltip title="期末应付=期初应付+本期欠款-本期付款">
                <a-icon type="question-circle" />
              </a-tooltip>
            </span>
          </a-table>
          <a-row :gutter="24" style="margin-top: 8px;text-align:right;">
            <a-col :md="24" :sm="24">
              <a-pagination @change="paginationChange" @showSizeChange="paginationShowSizeChange"
                size="small"
                show-size-changer
                :showQuickJumper="true"
                :current="ipagination.current"
                :page-size="ipagination.pageSize"
                :page-size-options="ipagination.pageSizeOptions"
                :total="ipagination.total"
                :show-total="(total, range) => `共 ${total-Math.ceil(total/ipagination.pageSize)} 条`">
                <template slot="buildOptionText" slot-scope="props">
                  <span>{{ props.value-1 }}条/页</span>
                </template>
              </a-pagination>
            </a-col>
          </a-row>
        </section>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <debt-account-list ref="debtAccountList"></debt-account-list>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import DebtAccountList from './modules/DebtAccountList'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getFormatDate, getNowFormatYear, getPrevMonthFormatDate } from '@/utils/util'
  import { getAction } from '@/api/manage'
  import {findBySelectSup} from '@/api/api'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import moment from 'moment'
  export default {
    name: "VendorAccount",
    mixins:[JeecgListMixin],
    components: {
      DebtAccountList,
      JEllipsis
    },
    data () {
      return {
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {
          supplierType: "供应商",
          organId: undefined,
          hasDebt: '1',
          beginTime: getPrevMonthFormatDate(3),
          endTime: getFormatDate(),
          createTimeRange: [moment(getPrevMonthFormatDate(3)), moment(getFormatDate())],
        },
        ipagination:{
          pageSize: 11,
          pageSizeOptions: ['11', '21', '31', '101', '201']
        },
        supList: [],
        firstTotal: '',
        lastTotal: '',
        tabKey: "1",
        pageName: 'vendorAccount',
        // 默认索引
        defDataIndex:['rowIndex','action','supplier','contacts','telephone','phoneNum','email','preNeed','debtMoney','backMoney','allNeed'],
        // 默认列
        defColumns: [
          {
            dataIndex: 'rowIndex', width:40, align:"center", slots: { title: 'customTitle' },
            customRender:function (t,r,index) {
              return (t !== '合计') ? (parseInt(index) + 1) : t
            }
          },
          {title: '欠款详情', dataIndex: 'action', align:"center", width: 80,
            scopedSlots: { customRender: 'action' }
          },
          {title: '供应商', dataIndex: 'supplier', width: 150, ellipsis:true},
          {title: '联系人', dataIndex: 'contacts', width: 100, ellipsis:true},
          {title: '手机号码', dataIndex: 'telephone', width: 100},
          {title: '联系电话', dataIndex: 'phoneNum', width: 100},
          {title: '电子邮箱', dataIndex: 'email', width: 100},
          {title: '期初应付', dataIndex: 'preNeed', sorter: (a, b) => a.preNeed - b.preNeed, width: 80},
          {title: '本期欠款', dataIndex: 'debtMoney', sorter: (a, b) => a.debtMoney - b.debtMoney, width: 80},
          {title: '本期付款', dataIndex: 'backMoney', sorter: (a, b) => a.backMoney - b.backMoney, width: 80},
          {dataIndex: 'allNeed', sorter: (a, b) => a.allNeed - b.allNeed, width: 80,
            slots: { title: 'allNeedTitle' }
          }
        ],
        url: {
          list: "/depotHead/getStatementAccount",
        }
      }
    },
    created () {
      this.initSupplier()
      this.initColumnsSetting()
    },
    methods: {
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize-1;
        return param;
      },
      initSupplier() {
        let that = this;
        findBySelectSup({}).then((res)=>{
          if(res) {
            that.supList = res;
          }
        });
      },
      onDateChange: function (value, dateString) {
        this.queryParam.beginTime=dateString[0]
        this.queryParam.endTime=dateString[1]
        if(dateString[0] && dateString[1]) {
          this.queryParam.createTimeRange = [moment(dateString[0]), moment(dateString[1])]
        }
      },
      loadData(arg) {
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        let params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.code===200) {
            this.dataSource = res.data.rows;
            this.ipagination.total = res.data.total;
            this.tableAddTotalRow(this.columns, this.dataSource)
            this.firstTotal = '期初应付：' + res.data.firstMoney + "，"
            this.lastTotal = '期末应付：' + res.data.lastMoney
          } else if(res.code===510){
            this.$message.warning(res.data)
          } else {
            this.$message.warning(res.data.message)
          }
          this.loading = false;
        })
      },
      searchQuery() {
        if(this.queryParam.beginTime === '' || this.queryParam.endTime === ''){
          this.$message.warning('请选择单据日期！')
        } else {
          this.loadData(1);
        }
      },
      exportExcel() {
        let list = []
        let head = '供应商,联系人,手机号码,联系电话,电子邮箱,期初应付,本期欠款,本期付款,期末应付'
        for (let i = 0; i < this.dataSource.length; i++) {
          let item = []
          let ds = this.dataSource[i]
          item.push(ds.supplier, ds.contacts, ds.telephone, ds.phoneNum, ds.email, ds.preNeed, ds.debtMoney, ds.backMoney, ds.allNeed)
          list.push(item)
        }
        let tip = '单据日期：' + this.queryParam.beginTime + '~' + this.queryParam.endTime
        this.handleExportXlsPost('供应商对账', '供应商对账', head, tip, list)
      },
      showDebtAccountList(record) {
        this.$refs.debtAccountList.show(record.id, '入库', '采购', '供应商', "", this.queryParam.beginTime, this.queryParam.endTime)
        this.$refs.debtAccountList.title = "欠款详情"
        this.$refs.debtAccountList.disableSubmit = false
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
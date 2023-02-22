<!-- by j i s h e n g hua -->
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
                  <a-input placeholder="请输入单据编号" v-model="queryParam.billNo"></a-input>
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
              <a-col :md="6" :sm="24">
                <a-form-item label="往来单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select placeholder="选择往来单位" showSearch optionFilterProp="children" v-model="queryParam.organId">
                    <a-select-option v-for="(item,index) in organList" :key="index" :value="item.id">
                      {{ item.supplier }}
                    </a-select-option>
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
                    <a-select placeholder="选择财务人员" showSearch optionFilterProp="children" v-model="queryParam.handsPersonId">
                      <a-select-option v-for="(item,index) in personList" :key="index" :value="item.id">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="操作员" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="选择操作员" showSearch optionFilterProp="children" v-model="queryParam.creator">
                      <a-select-option v-for="(item,index) in userList" :key="index" :value="item.id">
                        {{ item.userName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="收入账户" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="选择收入账户" showSearch optionFilterProp="children" v-model="queryParam.accountId">
                      <a-select-option v-for="(item,index) in accountList" :key="index" :value="item.id">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="选择单据状态" v-model="queryParam.status">
                      <a-select-option value="0">未审核</a-select-option>
                      <a-select-option value="1">已审核</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="单据备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入单据备注" v-model="queryParam.remark"></a-input>
                  </a-form-item>
                </a-col>
              </template>
            </a-row>
          </a-form>
        </div>
        <!-- 操作按钮区域 -->
        <div class="table-operator"  style="margin-top: 5px">
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="myHandleAdd" type="primary" icon="plus">新增</a-button>
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item key="1" v-if="btnEnableList.indexOf(1)>-1"@click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
              <a-menu-item key="2" v-if="checkFlag && btnEnableList.indexOf(2)>-1" @click="batchSetStatus(1)"><a-icon type="check"/>审核</a-menu-item>
              <a-menu-item key="3" v-if="checkFlag && btnEnableList.indexOf(7)>-1" @click="batchSetStatus(0)"><a-icon type="stop"/>反审核</a-menu-item>
            </a-menu>
            <a-button>
              批量操作 <a-icon type="down" />
            </a-button>
          </a-dropdown>
          <a-tooltip placement="left" title="收入单主要处理一些销售收入以外的收入如维修服务收入、利息收入、调账收入等。" slot="action">
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
            :pagination="ipagination"
            :scroll="scroll"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="myHandleDetail(record, '收入')">查看</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a v-if="btnEnableList.indexOf(1)>-1" @click="myHandleEdit(record)">编辑</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => myHandleDelete(record)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <template slot="customRenderStatus" slot-scope="status">
              <a-tag v-if="status == '0'" color="red">未审核</a-tag>
              <a-tag v-if="status == '1'" color="green">已审核</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <item-in-modal ref="modalForm" @ok="modalFormOk"></item-in-modal>
        <financial-detail ref="modalDetail" @ok="modalFormOk"></financial-detail>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import ItemInModal from './modules/ItemInModal'
  import FinancialDetail from './dialog/FinancialDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { FinancialListMixin } from './mixins/FinancialListMixin'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "ItemInList",
    mixins:[JeecgListMixin, FinancialListMixin],
    components: {
      ItemInModal,
      FinancialDetail,
      JDate
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
          billNo: "",
          searchMaterial: "",
          type: "收入",
          organId: "",
          creator: "",
          handsPersonId: "",
          accountId: "",
          status: "",
          remark: "",
          roleType: Vue.ls.get('roleType')
        },
        // 表头
        columns: [
          {
            title: '操作',
            dataIndex: 'action',
            width:200,
            align:"center",
            scopedSlots: { customRender: 'action' },
          },
          { title: '往来单位', dataIndex: 'organName',width:140, ellipsis:true},
          { title: '财务人员', dataIndex: 'handsPersonName',width:140},
          { title: '单据编号', dataIndex: 'billNo',width:160},
          { title: '单据日期 ', dataIndex: 'billTimeStr',width:160},
          { title: '操作员', dataIndex: 'userName',width:80, ellipsis:true},
          { title: '收入金额', dataIndex: 'changeAmount',width:80},
          { title: '备注', dataIndex: 'remark',width:200},
          { title: '状态', dataIndex: 'status', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderStatus' }
          }
        ],
        url: {
          list: "/accountHead/list",
          delete: "/accountHead/delete",
          deleteBatch: "/accountHead/deleteBatch",
          batchSetStatusUrl: "/accountHead/batchSetStatus"
        }
      }
    },
    computed: {
    },
    created () {
      this.initSystemConfig()
      this.initOrgan()
      this.initUser()
      this.initPerson()
      this.initAccount()
    },
    methods: {
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
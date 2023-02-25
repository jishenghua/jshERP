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
                  <a-input placeholder="请输入条码、名称、规格、型号、颜色、扩展信息" v-model="queryParam.materialParam"></a-input>
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
              <template v-if="toggleSearchStatus">
                <a-col :md="6" :sm="24">
                  <a-form-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="选择供应商" showSearch optionFilterProp="children" v-model="queryParam.organId">
                      <a-select-option v-for="(item,index) in supList" :key="index" :value="item.id">
                        {{ item.supplier }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="仓库名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="请选择仓库" showSearch optionFilterProp="children" v-model="queryParam.depotId">
                      <a-select-option v-for="(depot,index) in depotList" :value="depot.id">
                        {{ depot.depotName }}
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
                  <a-form-item label="关联单据" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入关联单据" v-model="queryParam.linkNumber"></a-input>
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
              <a-menu-item key="1" v-if="btnEnableList.indexOf(1)>-1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
              <a-menu-item key="2" v-if="checkFlag && btnEnableList.indexOf(2)>-1" @click="batchSetStatus(1)"><a-icon type="check"/>审核</a-menu-item>
              <a-menu-item key="3" v-if="checkFlag && btnEnableList.indexOf(7)>-1" @click="batchSetStatus(0)"><a-icon type="stop"/>反审核</a-menu-item>
            </a-menu>
            <a-button>
              批量操作 <a-icon type="down" />
            </a-button>
          </a-dropdown>
          <a-tooltip placement="left" title="可以进行库存初始化，生产管理模块的成品入库。" slot="action">
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
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="myHandleDetail(record, '其它入库', prefixNo)">查看</a>
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
              <a-tag v-if="status == '9'" color="orange">审核中</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <other-in-modal ref="modalForm" @ok="modalFormOk"></other-in-modal>
        <bill-detail ref="modalDetail" @ok="modalFormOk"></bill-detail>
      </a-card>
    </a-col>
  </a-row>
</template>
<!--power by ji shenghua-->
<script>
  import OtherInModal from './modules/OtherInModal'
  import BillDetail from './dialog/BillDetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { BillListMixin } from './mixins/BillListMixin'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "OtherInList",
    mixins:[JeecgListMixin,BillListMixin],
    components: {
      OtherInModal,
      BillDetail,
      JDate
    },
    data () {
      return {
        // 查询条件
        queryParam: {
          number: "",
          materialParam: "",
          type: "入库",
          subType: "其它",
          roleType: Vue.ls.get('roleType'),
          organId: "",
          depotId: "",
          creator: "",
          linkNumber: "",
          status: "",
          remark: ""
        },
        prefixNo: 'QTRK',
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 表头
        columns: [
          {
            title: '操作',
            dataIndex: 'action',
            align:"center", width: 150,
            scopedSlots: { customRender: 'action' },
          },
          { title: '供应商', dataIndex: 'organName',width:120, ellipsis:true},
          { title: '单据编号', dataIndex: 'number',width:160},
          { title: '商品信息', dataIndex: 'materialsList',width:220, ellipsis:true,
            customRender:function (text,record,index) {
              if(text) {
                return text.replace(",","，");
              }
            }
          },
          { title: '单据日期', dataIndex: 'operTimeStr',width:145},
          { title: '操作员', dataIndex: 'userName',width:80, ellipsis:true},
          { title: '数量', dataIndex: 'materialCount',width:60},
          { title: '金额合计', dataIndex: 'totalPrice',width:80},
          { title: '状态', dataIndex: 'status', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderStatus' }
          }
        ],
        url: {
          list: "/depotHead/list",
          delete: "/depotHead/delete",
          deleteBatch: "/depotHead/deleteBatch",
          batchSetStatusUrl: "/depotHead/batchSetStatus"
        }
      }
    },
    computed: {
    },
    created () {
      this.initSystemConfig()
      this.initSupplier()
      this.getDepotData()
      this.initUser()
    },
    methods: {
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
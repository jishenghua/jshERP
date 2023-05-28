<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="close"
    cancelText="关闭"
    style="top:12%;height: 90%;overflow-y: hidden"
    wrapClassName="ant-modal-cust-warp">
    <a-row :gutter="10" style="padding: 10px; margin: -10px">
      <a-col :md="16" :sm="24">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="onSearch">
            <a-row :gutter="24">
              <a-col :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号">
                  <a-input ref="name" placeholder="请输入序列号并回车" v-model="queryParam.name"></a-input>
                </a-form-item>
              </a-col>
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-col :md="12" :sm="24">
                  <a-button type="primary" @click="onSearch">查询</a-button>
                  <a-button style="margin-left: 8px" type="primary" @click="checkAll">全选</a-button>
                  <a-button style="margin-left: 8px" @click="searchReset(1)">重置</a-button>
                </a-col>
              </span>
            </a-row>
          </a-form>
        </div>
      </a-col>
      <a-col :md="8" :sm="24">
        <span style="font-size: 16px;font-weight: bold;">此处为已选中的序列号列表</span>
        <a-button style="margin-left: 8px" @click="clearAllSn">清空</a-button>
      </a-col>
    </a-row>
    <a-row :gutter="10" style="padding: 10px; margin: -10px">
      <a-col :md="16" :sm="24">
        <a-table
          ref="table"
          style="width:95%"
          :scroll="scrollTrigger"
          size="middle"
          rowKey="id"
          :columns="leftColumns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          @change="handleTableChange">
          <span slot="action" slot-scope="text, record">
              <a @click="checkSn(record)">选中</a>
          </span>
        </a-table>
      </a-col>
      <a-col :md="8" :sm="24">
        <a-table
          ref="table"
          :scroll="scrollTrigger"
          size="middle"
          rowKey="id"
          :columns="rightColumns"
          :dataSource="checkDataSource"
          :pagination="false"
          :loading="loading"
          @change="handleTableChange">
           <span slot="action" slot-scope="text, record">
              <a @click="removeSn(record)">移除</a>
           </span>
        </a-table>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
  import { getAction } from '@/api/manage'
  import {getEnableSerialNumberList} from '@/api/api'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'JSelectSnModal',
    mixins:[JeecgListMixin],
    components: {},
    props: ['rows', 'multi', 'barCode'],
    data() {
      return {
        modalWidth: 1000,
        queryParam: {
          name: "",
          depotItemId: '',
          depotId: '',
          barCode: ''
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        categoryTree:[],
        leftColumns: [
          {dataIndex: 'serialNumber', title: '序列号', width: 100, align: 'left'},
          {dataIndex: 'inBillNo', title: '入库单号', width: 100, align: 'left'},
          {dataIndex: 'createTimeStr', title: '创建时间', width: 100, align: 'left'},
          {dataIndex: 'action', title: '操作', align:"center", width: 50, scopedSlots: { customRender: 'action' }}
        ],
        rightColumns: [
          {dataIndex: 'serialNumber', title: '序列号', width: 100, align: 'left'},
          {tdataIndex: 'action', title: '操作', align:"center", width: 50, scopedSlots: { customRender: 'action' }}
        ],
        scrollTrigger: {y: 460},
        dataSource: [],
        checkDataSource: [],
        selectedRowKeys: [],
        selectRows: [],
        selectIds: [],
        title: '选择序列号',
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30', '100', '200'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + ' 共' + total + '条'
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        isorter: {
          column: 'createTime',
          order: 'desc'
        },
        departTree: [],
        depotList: [],
        visible: false,
        form: this.$form.createForm(this),
        loading: false,
        expandedKeys: [],
      }
    },
    computed: {
      // 计算属性的 getter
      getType: function () {
        return this.multi == true ? 'checkbox' : 'radio';
      }
    },
    watch: {
      barCode: {
        immediate: true,
        handler() {
          this.initBarCode()
        }
      },
    },
    created() {
      this.loadData()
    },
    methods: {
      initBarCode() {
        if (this.barCode) {
          this.$emit('initComp', this.barCode)
        } else {
          this.$emit('initComp', '')
        }
      },
      loadData(arg) {
        if(this.rows) {
          if(JSON.parse(this.rows).depotId && JSON.parse(this.rows).barCode ){
            let depotItemId = JSON.parse(this.rows).id
            if(depotItemId.length<=19) {
              this.queryParam.depotItemId = depotItemId-0
            }
            this.queryParam.depotId = JSON.parse(this.rows).depotId-0
            this.queryParam.barCode = JSON.parse(this.rows).barCode
          }
        }
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.loading = true
        let params = this.getQueryParams()//查询条件
        getEnableSerialNumberList(params).then((res) => {
          if (res && res.code === 200) {
            this.dataSource = res.data.rows
            this.ipagination.total = res.data.total
            if(res.data.total ===1) {
              if(this.queryParam.name === this.dataSource[0].serialNumber) {
                let obj = {
                  id: this.dataSource[0].id,
                  serialNumber: this.dataSource[0].serialNumber
                }
                this.checkSn(obj)
                this.queryParam.name = ''
              }
            }
          }
        }).finally(() => {
          this.loading = false
        })
      },
      showModal() {
        this.visible = true
        this.$nextTick(() => this.$refs.name.focus())
        this.loadData()
        this.form.resetFields()
        //加载存在的序列号
        if(this.rows) {
          this.checkDataSource = []
          let rowObj = JSON.parse(this.rows)
          let snArr = rowObj.snList.split(',')
          for (let i = 0; i < snArr.length; i++) {
            let snObj = {
              'id': snArr[i],
              'serialNumber': snArr[i]
            }
            this.checkDataSource.push(snObj)
          }
        }
      },
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.page = this.ipagination.current;
        param.rows = this.ipagination.pageSize;
        return param;
      },
      getQueryField() {
        let str = 'id,';
        for (let a = 0; a < this.columns.length; a++) {
          str += ',' + this.columns[a].dataIndex;
        }
        return str;
      },
      checkAll() {
        this.checkDataSource = []
        let data = this.dataSource
        for (let i = 0; i < data.length; i++) {
          let checkObj = {
            id: data[i].id,
            serialNumber: data[i].serialNumber
          }
          this.checkDataSource.push(checkObj)
        }
      },
      clearAllSn() {
        this.checkDataSource = []
      },
      searchReset(num) {
        let that = this;
        if (num !== 0) {
          if(this.rows) {
            this.queryParam.name=''
            if(JSON.parse(this.rows).depotId && JSON.parse(this.rows).barCode ){
              let depotItemId = JSON.parse(this.rows).id
              if(depotItemId.length<=19) {
                this.queryParam.depotItemId = depotItemId-0
              }
              this.queryParam.depotId = JSON.parse(this.rows).depotId-0
              this.queryParam.barCode = JSON.parse(this.rows).barCode
            }
          }
          that.loadData(1);
        }
        that.selectedRowKeys = [];
        that.selectIds = [];
      },
      close() {
        this.searchReset(0);
        this.visible = false;
      },
      handleTableChange(pagination, filters, sorter) {
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = 'ascend' === sorter.order ? 'asc' : 'desc';
        }
        this.ipagination = pagination;
        this.loadData();
      },
      handleSubmit() {
        let that = this;
        this.getSelectRows();
        that.$emit('ok', that.selectRows, that.selectIds);
        that.searchReset(0)
        that.close();
      },
      checkSn(record) {
        let checkObj = {
          id: record.id,
          serialNumber: record.serialNumber
        }
        let data = this.checkDataSource
        let isExist = false
        for (let i = 0; i < data.length; i++) {
          if(data[i].serialNumber === record.serialNumber) {
            isExist = true
          }
        }
        if(isExist) {
          this.$message.warning('抱歉，此序列号已经选择过！');
        } else {
          this.checkDataSource.push(checkObj)
        }
      },
      removeSn(record) {
        let oldArr = this.checkDataSource
        let newArr = []
        for (let i = 0; i < oldArr.length; i++) {
          if(oldArr[i].id !== record.id) {
            newArr.push(oldArr[i])
          }
        }
        this.checkDataSource = newArr
      },
      //获取选择信息
      getSelectRows() {
        let ids = ""
        this.selectRows = this.checkDataSource
        let data = this.checkDataSource
        for (let i = 0; i < data.length; i++) {
          ids = ids + "," + data[i].serialNumber
        }
        this.selectIds = ids.substring(1)
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      onSearch() {
        this.loadData(1);
      },
      modalFormOk() {
        this.loadData();
      }
    }
  }
</script>

<style scoped>
  .ant-table-tbody .ant-table-row td {
    padding-top: 10px;
    padding-bottom: 10px;
  }

  #components-layout-demo-custom-trigger .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }
</style>
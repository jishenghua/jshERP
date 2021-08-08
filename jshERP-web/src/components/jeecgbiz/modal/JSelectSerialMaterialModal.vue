<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="close"
    cancelText="关闭"
    style="margin-top: -70px"
    wrapClassName="ant-modal-cust-warp"
  >
    <a-row :gutter="10" style="padding: 10px; margin: -10px">
      <a-col :md="24" :sm="24">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="onSearch">
            <a-row :gutter="24">
              <a-col :md="12" :sm="12">
                <a-form-item label="商品信息" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
                  <a-input placeholder="请输入条码、名称、规格、型号" v-model="queryParam.q"></a-input>
                </a-form-item>
              </a-col>
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-col :md="12" :sm="12">
                  <a-button type="primary" @click="onSearch">查询</a-button>
                  <a-button style="margin-left: 8px" @click="searchReset(1)">重置</a-button>
                </a-col>
              </span>
            </a-row>
          </a-form>
          <a-table
            ref="table"
            :scroll="scrollTrigger"
            size="middle"
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type: getType}"
            :loading="loading"
            :customRow="rowAction"
            @change="handleTableChange">
          </a-table>
        </div>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
  import {filterObj} from '@/utils/util'
  import {getSerialMaterialBySelect} from '@/api/api'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'JSelectSerialMaterialModal',
    mixins:[JeecgListMixin],
    components: {},
    props: ['modalWidth', 'multi', 'barCode'],
    data() {
      return {
        queryParam: {
          q: ''
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        columns: [
          {dataIndex: 'mBarCode', title: '条码', width: 100, align: 'left'},
          {dataIndex: 'name', title: '名称', width: 100},
          {dataIndex: 'standard', title: '规格', width: 80},
          {dataIndex: 'model', title: '型号', width: 80}
        ],
        scrollTrigger: {},
        dataSource: [],
        selectedRowKeys: [],
        selectMaterialRows: [],
        selectMaterialIds: [],
        title: '选择序列号商品',
        ipagination: {
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '20', '30'],
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
      // 该方法触发屏幕自适应
      this.resetScreenSize()
      this.loadData()
    },
    methods: {
      initBarCode() {
        if (this.barCode) {
          this.$emit('initComp', this.barCode)
        } else {
          // JSelectUserByDep组件bug issues/I16634
          this.$emit('initComp', '')
        }
      },
      async loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.loading = true
        let params = this.getQueryParams()//查询条件
        await getSerialMaterialBySelect(params).then((res) => {
          if (res) {
            this.dataSource = res.rows
            this.ipagination.total = res.total
          }
        }).finally(() => {
          this.loading = false
        })
      },
      // 触发屏幕自适应
      resetScreenSize() {
        let screenWidth = document.body.clientWidth;
        if (screenWidth < 500) {
          this.scrollTrigger = {x: 800};
        } else {
          this.scrollTrigger = {};
        }
      },
      showModal() {
        this.visible = true;
        this.loadData();
        this.form.resetFields();
      },
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.page = this.ipagination.current;
        param.rows = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        let str = 'id,';
        for (let a = 0; a < this.columns.length; a++) {
          str += ',' + this.columns[a].dataIndex;
        }
        return str;
      },
      searchReset(num) {
        let that = this;
        if (num !== 0) {
          that.queryParam = {};
          that.loadData(1);
        }
        that.selectedRowKeys = [];
        that.selectMaterialIds = [];
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
        this.getSelectMaterialRows();
        that.$emit('ok', that.selectMaterialRows, that.selectMaterialIds);
        that.searchReset(0)
        that.close();
      },
      //获取选择信息
      getSelectMaterialRows(rowId) {
        let dataSource = this.dataSource;
        let materialIds = "";
        this.selectMaterialRows = [];
        for (let i = 0, len = dataSource.length; i < len; i++) {
          if (this.selectedRowKeys.includes(dataSource[i].id)) {
            this.selectMaterialRows.push(dataSource[i]);
            materialIds = materialIds + "," + dataSource[i].mBarCode
          }
        }
        this.selectMaterialIds = materialIds.substring(1);
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
      },
      rowAction(record, index) {
        return {
          on: {
            click: () => {
              let arr = []
              arr.push(record.id)
              this.selectedRowKeys = arr
            },
            dblclick: () => {
              let arr = []
              arr.push(record.id)
              this.selectedRowKeys = arr
              this.handleSubmit()
            }
          }
        }
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
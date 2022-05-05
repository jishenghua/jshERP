<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="close"
    cancelText="关闭"
    style="top:5%;height: 100%;overflow-y: hidden"
    wrapClassName="ant-modal-cust-warp"
  >
    <a-row :gutter="10" style="padding: 10px; margin: -10px">
      <a-col :md="24" :sm="24">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="onSearch">
            <a-row :gutter="24">
              <a-col :md="6" :sm="8">
                <a-form-item label="商品" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
                  <a-input ref="material" placeholder="条码、名称、规格、型号" v-model="queryParam.q"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="8">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="类别">
                  <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                                 :treeData="categoryTree" v-model="queryParam.categoryId" placeholder="请选择类别">
                  </a-tree-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="8">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="仓库">
                  <a-select placeholder="选择仓库" v-model="queryParam.depotId" @change="onDepotChange"
                    :dropdownMatchSelectWidth="false" showSearch optionFilterProp="children" allow-clear>
                    <a-select-option v-for="(item,index) in depotList" :key="index" :value="item.id">
                      {{ item.depotName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-col :md="6" :sm="24">
                  <a-button type="primary" @click="loadMaterialData(1)">查询</a-button>
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
            <template slot="customRenderEnableSerialNumber" slot-scope="enableSerialNumber">
              <a-tag v-if="enableSerialNumber==1" color="green">有</a-tag>
              <a-tag v-if="enableSerialNumber==0" color="orange">无</a-tag>
            </template>
            <template slot="customRenderEnableBatchNumber" slot-scope="enableBatchNumber">
              <a-tag v-if="enableBatchNumber==1" color="green">有</a-tag>
              <a-tag v-if="enableBatchNumber==0" color="orange">无</a-tag>
            </template>
          </a-table>
        </div>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
  import { httpAction, getAction } from '@/api/manage'
  import {filterObj, getMpListShort} from '@/utils/util'
  import {getMaterialBySelect, queryMaterialCategoryTreeList} from '@/api/api'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import Vue from 'vue'

  export default {
    name: 'JSelectMaterialModal',
    mixins:[JeecgListMixin],
    components: {},
    props: ['modalWidth', 'rows', 'multi', 'barCode'],
    data() {
      return {
        queryParam: {
          q: '',
          depotId: ''
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
        columns: [
          {dataIndex: 'mBarCode', title: '条码', width: 100, align: 'left', ellipsis:true},
          {dataIndex: 'name', title: '名称', width: 120, ellipsis:true},
          {dataIndex: 'categoryName', title: '类别', width: 80, ellipsis:true},
          {dataIndex: 'standard', title: '规格', width: 80, ellipsis:true},
          {dataIndex: 'model', title: '型号', width: 80, ellipsis:true},
          {dataIndex: 'color', title: '颜色', width: 80, ellipsis:true},
          {dataIndex: 'unit', title: '单位', width: 70, ellipsis:true},
          {dataIndex: 'sku', title: '多属性', width: 80, ellipsis:true},
          {dataIndex: 'stock', title: '库存', width: 60},
          {dataIndex: 'expand', title: '扩展信息', width: 80, ellipsis:true},
          {dataIndex: 'enableSerialNumber', title: '序列号', width: 60, align: "center",
            scopedSlots: { customRender: 'customRenderEnableSerialNumber' }
          },
          {dataIndex: 'enableBatchNumber', title: '批号', width: 50, align: "center",
            scopedSlots: { customRender: 'customRenderEnableBatchNumber' }
          }
        ],
        scrollTrigger: {},
        dataSource: [],
        selectedRowKeys: [],
        selectMaterialRows: [],
        selectMaterialIds: [],
        title: '选择商品',
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
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
        disableMixinCreated: true,
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
      loadMaterialData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.loading = true
        let params = this.getQueryParams()//查询条件
        getMaterialBySelect(params).then((res) => {
          if (res) {
            this.dataSource = res.rows
            this.ipagination.total = res.total
            if(res.total ===1) {
              this.title = '选择商品【再次回车可以直接选中】'
              this.$nextTick(() => this.$refs.material.focus());
            } else {
              this.title = '选择商品'
            }
          }
        }).finally(() => {
          this.loading = false
        })
      },
      loadTreeData(){
        let that = this;
        let params = {};
        params.id='';
        queryMaterialCategoryTreeList(params).then((res)=>{
          if(res){
            that.categoryTree = [];
            for (let i = 0; i < res.length; i++) {
              let temp = res[i];
              that.categoryTree.push(temp);
            }
          }
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
      showModal(barCode) {
        this.visible = true;
        this.title = '选择商品'
        this.queryParam.q = barCode
        this.$nextTick(() => this.$refs.material.focus());
        this.loadTreeData()
        this.getDepotList()
        this.initDepotSelect()
        this.loadMaterialData();
        this.form.resetFields();
      },
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.mpList = getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
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
          that.loadMaterialData(1);
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
        this.loadMaterialData();
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
      getDepotList() {
        let that = this;
        getAction('/depot/findDepotByCurrentUser').then((res) => {
          if(res.code === 200){
            that.depotList = res.data
          }
        })
      },
      initDepotSelect() {
        if(this.rows) {
          if(JSON.parse(this.rows).depotId){
            this.queryParam.depotId = JSON.parse(this.rows).depotId-0
          }
        }
      },
      onDepotChange(value) {
        this.queryParam.depotId = value
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      onSearch() {
        if(this.dataSource && this.dataSource.length===1) {
          if(this.queryParam.q === this.dataSource[0].mBarCode||
            this.queryParam.q === this.dataSource[0].name||
            this.queryParam.q === this.dataSource[0].standard||
            this.queryParam.q === this.dataSource[0].model) {
            let arr = []
            arr.push(this.dataSource[0].id)
            this.selectedRowKeys = arr
            this.handleSubmit()
          } else {
            this.loadMaterialData(1)
          }
        } else {
          this.loadMaterialData(1)
        }
      },
      modalFormOk() {
        this.loadMaterialData()
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
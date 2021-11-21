<!-- by 7527189 2 0 -->
<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                   :treeData="categoryTree" v-model="queryParam.categoryId" placeholder="请选择类别">
                  </a-tree-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="条码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入条码查询" v-model="queryParam.barCode"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入名称查询" v-model="queryParam.name"></a-input>
                </a-form-item>
              </a-col>
              <template v-if="toggleSearchStatus">
                <a-col :md="6" :sm="24">
                  <a-form-item label="规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入规格查询" v-model="queryParam.standard"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入型号查询" v-model="queryParam.model"></a-input>
                  </a-form-item>
                </a-col>
              </template>
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
            </a-row>
          </a-form>
        </div>
        <!-- 操作按钮区域 -->
        <div class="table-operator"  style="margin-top: 5px">
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="handleAdd" type="primary" icon="plus">新增</a-button>
          <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
            <a-popover title="表格模板">
              <template slot="content">
                <p><a target="_blank" href="/doc/goods_template.xls"><b>商品Excel模板下载</b></a></p>
              </template>
              <a-button type="primary" icon="import">导入</a-button>
            </a-popover>
          </a-upload>
          <a-button type="primary" icon="download" @click="handleExportXls('商品信息')">导出</a-button>
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item key="1" v-if="btnEnableList.indexOf(1)>-1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
              <a-menu-item key="2" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(true)"><a-icon type="check-square"/>启用</a-menu-item>
              <a-menu-item key="3" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(false)"><a-icon type="close-square"/>禁用</a-menu-item>
              <a-menu-item key="4" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetMaterialCurrentStock()"><a-icon type="stock"/>修正库存</a-menu-item>
            </a-menu>
            <a-button>
              批量操作 <a-icon type="down" />
            </a-button>
          </a-dropdown>
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
              <a @click="handleEdit(record)">编辑</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <span slot="customRenderUnit" slot-scope="text, record">
              {{text?text:record.unitName}}
              <a-tag v-if="record.sku">SKU</a-tag>
            </span>
            <template slot="customRenderEnabled" slot-scope="enabled">
              <a-tag v-if="enabled" color="green">启用</a-tag>
              <a-tag v-if="!enabled" color="orange">禁用</a-tag>
            </template>
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
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <material-modal ref="modalForm" @ok="modalFormOk"></material-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import MaterialModal from './modules/MaterialModal'
  import {queryMaterialCategoryTreeList} from '@/api/api'
  import { postAction } from '@/api/manage'
  import { getMpListShort } from "@/utils/util"
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "MaterialList",
    mixins:[JeecgListMixin],
    components: {
      MaterialModal,
      JDate
    },
    data () {
      return {
        categoryTree:[],
        mPropertyListShort: '',
        model: {},
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {
          categoryId:'',
          barCode:'',
          name:'',
          standard:'',
          model:'',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        // 表头
        columns: [
          {title: '条码', dataIndex: 'mBarCode', width: '8%'},
          {title: '名称', dataIndex: 'name', width: '10%'},
          {title: '规格', dataIndex: 'standard', width: '6%'},
          {title: '型号', dataIndex: 'model', width: '6%'},
          {title: '颜色', dataIndex: 'color', width: '4%'},
          {title: '类别', dataIndex: 'categoryName', width: '6%'},
          {title: '扩展信息', dataIndex: 'materialOther', width: '6%'},
          {title: '单位', dataIndex: 'unit', width: '6%',
            scopedSlots: { customRender: 'customRenderUnit' }
          },
          {title: '保质期', dataIndex: 'expiryNum', width: '4%'},
          {title: '库存', dataIndex: 'stock', width: '5%'},
          {title: '采购价', dataIndex: 'purchaseDecimal', width: '5%'},
          {title: '零售价', dataIndex: 'commodityDecimal', width: '5%'},
          {title: '销售价', dataIndex: 'wholesaleDecimal', width: '5%'},
          {title: '最低售价', dataIndex: 'lowDecimal', width: '5%'},
          {title: '状态', dataIndex: 'enabled', width: '4%', align: "center",
            scopedSlots: { customRender: 'customRenderEnabled' }
          },
          {title: '序列号', dataIndex: 'enableSerialNumber', width: '4%', align: "center",
            scopedSlots: { customRender: 'customRenderEnableSerialNumber' }
          },
          {title: '批号', dataIndex: 'enableBatchNumber', width: '3%', align: "center",
            scopedSlots: { customRender: 'customRenderEnableBatchNumber' }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width: '6%',
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/material/list",
          delete: "/material/delete",
          deleteBatch: "/material/deleteBatch",
          importExcelUrl: "/material/importExcel",
          exportXlsUrl: "/material/exportExcel",
          batchSetStatusUrl: "/material/batchSetStatus",
          batchSetMaterialCurrentStockUrl: "/material/batchSetMaterialCurrentStock"
        }
      }
    },
    created() {
      this.model = Object.assign({}, {});
      this.loadTreeData();
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}${this.url.importExcelUrl}`;
      }
    },
    methods: {
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
      batchSetMaterialCurrentStock: function () {
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
        } else {
          let ids = "";
          for (let a = 0; a < this.selectedRowKeys.length; a++) {
            ids += this.selectedRowKeys[a] + ",";
          }
          let that = this;
          this.$confirm({
            title: "确认操作",
            content: "是否操作选中数据?",
            onOk: function () {
              that.loading = true;
              postAction(that.url.batchSetMaterialCurrentStockUrl, {ids: ids}).then((res) => {
                if(res.code === 200){
                  that.$message.info('修正库存成功！');
                  that.loadData();
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.data.message);
                }
              }).finally(() => {
                that.loading = false;
              });
            }
          });
        }
      },
      handleEdit: function (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
        if(this.btnEnableList.indexOf(1)===-1) {
          this.$refs.modalForm.isReadOnly = true
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
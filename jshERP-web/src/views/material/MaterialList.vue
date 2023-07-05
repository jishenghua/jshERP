<!-- by 7527189 2 0 -->
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
                <a-form-item label="类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                   :treeData="categoryTree" v-model="queryParam.categoryId" placeholder="请选择类别">
                  </a-tree-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="关键词" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入条码、名称、规格、型号查询" v-model="queryParam.materialParam"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="颜色" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入颜色查询" v-model="queryParam.color"></a-input>
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
                  <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="请选择状态" v-model="queryParam.enabled">
                      <a-select-option value="1">启用</a-select-option>
                      <a-select-option value="0">禁用</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="有无序列号" v-model="queryParam.enableSerialNumber">
                      <a-select-option value="1">有</a-select-option>
                      <a-select-option value="0">无</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select placeholder="有无批号" v-model="queryParam.enableBatchNumber">
                      <a-select-option value="1">有</a-select-option>
                      <a-select-option value="0">无</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="仓位货架" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input style="width: 100%" placeholder="请输入仓位货架查询" v-model="queryParam.position"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="扩展信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input style="width: 100%" placeholder="请输入扩展信息查询" v-model="queryParam.materialOther"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="基础重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input-number style="width: 100%" placeholder="请输入基础重量查询" v-model="queryParam.weight"></a-input-number>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="保质期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input-number style="width: 100%" placeholder="请输入保质期查询" v-model="queryParam.expiryNum"></a-input-number>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入备注查询" v-model="queryParam.remark"></a-input>
                  </a-form-item>
                </a-col>
              </template>
            </a-row>
          </a-form>
        </div>
        <!-- 操作按钮区域 -->
        <div class="table-operator"  style="margin-top: 5px">
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="handleAdd" type="primary" icon="plus">新增</a-button>
          <a-button v-if="btnEnableList.indexOf(1)>-1" @click="handleImportXls()" type="primary" icon="import">导入</a-button>
          <a-button type="primary" icon="download" @click="handleExportXls('商品信息')">导出</a-button>
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item key="1" v-if="btnEnableList.indexOf(1)>-1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
              <a-menu-item key="2" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(true)"><a-icon type="check-square"/>启用</a-menu-item>
              <a-menu-item key="3" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetStatus(false)"><a-icon type="close-square"/>禁用</a-menu-item>
              <a-menu-item key="4" v-if="btnEnableList.indexOf(1)>-1" @click="batchEdit()"><a-icon type="edit"/>批量编辑</a-menu-item>
              <a-menu-item key="5" v-if="btnEnableList.indexOf(1)>-1" @click="batchSetMaterialCurrentStock()"><a-icon type="stock"/>修正库存</a-menu-item>
            </a-menu>
            <a-button>
              批量操作 <a-icon type="down" />
            </a-button>
          </a-dropdown>
          <a-popover trigger="click" placement="right">
            <template slot="content">
              <a-checkbox-group @change="onColChange" v-model="settingColumns" :defaultValue="settingColumns">
                <a-row style="width: 500px">
                  <template v-for="(item,index) in defColumns">
                    <template>
                      <a-col :span="8">
                        <a-checkbox :value="item.dataIndex">
                          <j-ellipsis :value="item.title" :length="10"></j-ellipsis>
                        </a-checkbox>
                      </a-col>
                    </template>
                  </template>
                </a-row>
              </a-checkbox-group>
            </template>
            <a-button icon="setting">列设置</a-button>
          </a-popover>
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
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, columnWidth:'40px'}"
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
              <a-divider v-if="btnEnableList.indexOf(1)>-1" type="vertical" />
              <a-popconfirm v-if="btnEnableList.indexOf(1)>-1" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <template slot="customPic" slot-scope="text, record">
              <a-popover placement="right" trigger="click">
                <template slot="content">
                  <img :src="getImgUrl(record.imgName, '')" width="500px" />
                </template>
                <img v-if="record.imgName" :src="getImgUrl(record.imgName, 'mini')" style="cursor:pointer; max-width:36px; max-height:27px;" title="查看大图" />
              </a-popover>
            </template>
            <template slot="customBarCode" slot-scope="text, record">
              {{record.mBarCode}}
            </template>
            <template slot="customName" slot-scope="text, record">
              {{record.name}}
              <a-tag v-if="record.enableSerialNumber==1" color="orange">序</a-tag>
              <a-tag v-if="record.enableBatchNumber==1" color="orange">批</a-tag>
            </template>
            <template slot="customRenderStock" slot-scope="text, record">
              <a-tooltip :title="record.bigUnitStock">
                {{text}}
              </a-tooltip>
            </template>
            <template slot="customRenderEnabled" slot-scope="enabled">
              <a-tag v-if="enabled" color="green">启用</a-tag>
              <a-tag v-if="!enabled" color="orange">禁用</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <material-modal ref="modalForm" @ok="modalFormOk"></material-modal>
        <import-file-modal ref="modalImportForm" @ok="modalFormOk"></import-file-modal>
        <batch-set-info-modal ref="batchSetInfoModalForm" @ok="modalFormOk"></batch-set-info-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import MaterialModal from './modules/MaterialModal'
  import ImportFileModal from '@/components/tools/ImportFileModal'
  import BatchSetInfoModal from './modules/BatchSetInfoModal'
  import { queryMaterialCategoryTreeList } from '@/api/api'
  import { postAction, getFileAccessHttpUrl } from '@/api/manage'
  import { getMpListShort } from '@/utils/util'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'

  export default {
    name: "MaterialList",
    mixins:[JeecgListMixin],
    components: {
      MaterialModal,
      ImportFileModal,
      BatchSetInfoModal,
      JEllipsis,
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
          materialParam:'',
          color:'',
          materialOther:'',
          weight:'',
          expiryNum:'',
          enabled: '',
          enableSerialNumber:'',
          enableBatchNumber:'',
          position: '',
          remark:'',
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        },
        ipagination:{
          pageSizeOptions: ['10', '20', '30', '50', '100', '200']
        },
        // 实际表头
        columns:[],
        // 初始化设置的表头
        settingColumns:['mBarCode','name','standard','model','color','categoryName','materialOther','unit', 'stock',
          'purchaseDecimal','commodityDecimal','wholesaleDecimal','lowDecimal','enabled','enableSerialNumber','enableBatchNumber','action'],
        // 默认的列
        defColumns: [
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width: 100,
            scopedSlots: { customRender: 'action' },
          },
          {title: '图片', dataIndex: 'pic', width: 45, scopedSlots: { customRender: 'customPic' }},
          {title: '条码', dataIndex: 'mBarCode', width: 120},
          {title: '名称', dataIndex: 'name', width: 160, scopedSlots: { customRender: 'customName' }},
          {title: '规格', dataIndex: 'standard', width: 120},
          {title: '型号', dataIndex: 'model', width: 120},
          {title: '颜色', dataIndex: 'color', width: 70, ellipsis:true},
          {title: '类别', dataIndex: 'categoryName', width: 100, ellipsis:true},
          {title: '扩展信息', dataIndex: 'materialOther', width: 100, ellipsis:true},
          {title: '单位', dataIndex: 'unit', width: 100, ellipsis:true,
            customRender:function (t,r,index) {
              if (r) {
                let name = t?t:r.unitName
                if(r.sku) {
                  return name + '[SKU]';
                } else {
                  return name;
                }
              }
            }
          },
          {title: '基础重量', dataIndex: 'weight', width: 80},
          {title: '保质期', dataIndex: 'expiryNum', width: 60},
          {title: '库存', dataIndex: 'stock', width: 80,
            scopedSlots: { customRender: 'customRenderStock' }
          },
          {title: '采购价', dataIndex: 'purchaseDecimal', width: 80},
          {title: '零售价', dataIndex: 'commodityDecimal', width: 80},
          {title: '销售价', dataIndex: 'wholesaleDecimal', width: 80},
          {title: '最低售价', dataIndex: 'lowDecimal', width: 80},
          {title: '仓位货架', dataIndex: 'position', width: 80},
          {title: '备注', dataIndex: 'remark', width: 80},
          {title: '状态', dataIndex: 'enabled', align: "center", width: 60,
            scopedSlots: { customRender: 'customRenderEnabled' }
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
      this.initColumnsSetting()
      this.loadTreeData();
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}${this.url.importExcelUrl}`;
      }
    },
    methods: {
      //加载初始化列
      initColumnsSetting(){
        let columnsStr = Vue.ls.get('materialColumns')
        if(columnsStr && columnsStr.indexOf(',')>-1) {
          this.settingColumns = columnsStr.split(',')
        }
        this.columns = this.defColumns.filter(item => {
          if (this.settingColumns.includes(item.dataIndex)) {
            return true
          }
          return false
        })
      },
      //列设置更改事件
      onColChange (checkedValues) {
        this.columns = this.defColumns.filter(item => {
          if (checkedValues.includes(item.dataIndex)) {
            return true
          }
          return false
        })
        let columnsStr = checkedValues.join()
        Vue.ls.set('materialColumns', columnsStr)
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
      batchSetMaterialCurrentStock () {
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
      batchEdit() {
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
        } else {
          let ids = "";
          for (let a = 0; a < this.selectedRowKeys.length; a++) {
            ids += this.selectedRowKeys[a] + ",";
          }
          this.$refs.batchSetInfoModalForm.edit(ids);
          this.$refs.batchSetInfoModalForm.title = "批量编辑";
        }
      },
      handleEdit: function (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
        if(this.btnEnableList.indexOf(1)===-1) {
          this.$refs.modalForm.showOkFlag = false
        }
      },
      getImgUrl(imgName, type) {
        if(imgName && imgName.split(',')) {
          type = type? type + '/':''
          return getFileAccessHttpUrl('systemConfig/static/' + type + imgName.split(',')[0])
        } else {
          return ''
        }
      },
      handleImportXls() {
        let importExcelUrl = this.url.importExcelUrl
        let templateUrl = '/doc/goods_template.xls'
        let templateName = '商品Excel模板[下载]'
        this.$refs.modalImportForm.initModal(importExcelUrl, templateUrl, templateName);
        this.$refs.modalImportForm.title = "商品导入";
      },
      searchReset() {
        this.queryParam = {
          mpList: getMpListShort(Vue.ls.get('materialPropertyList'))  //扩展属性
        }
        this.loadData(1);
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
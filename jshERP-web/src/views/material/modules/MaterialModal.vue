<template>
  <j-modal
    :title="title"
    :width="1300"
    :visible="visible"
    :confirmLoading="confirmLoading"
    v-bind:prefixNo="prefixNo"
    switchHelp
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    :id="prefixNo"
    style="top:5%;height: 100%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" v-if="isReadOnly" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-tabs default-active-key="1">
          <a-tab-pane key="1" tab="基本信息" forceRender>
            <a-row class="form-row" :gutter="24" id="materialHeadModal">
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="名称" data-step="1" data-title="名称" data-intro="名称必填，可以重复">
                  <a-input placeholder="请输入名称" v-decorator.trim="[ 'name', validatorRules.name]"/>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="规格" data-step="2" data-title="规格" data-intro="规格不必填，比如：10克">
                  <a-input placeholder="请输入规格" v-decorator.trim="[ 'standard' ]"/>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="型号" data-step="3" data-title="型号" data-intro="型号是比规格更小的属性，比如：RX-01">
                  <a-input placeholder="请输入型号" v-decorator.trim="[ 'model' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 4 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" label="单位"
                  data-step="4" data-title="单位" data-intro="此处支持单个单位和多单位，勾选多单位就可以切换到多单位的下拉框，多单位需要先在【计量单位】页面进行录入。
                  比如牛奶有瓶和箱两种单位，12瓶=1箱，这就构成了多单位，多单位中有个换算比例">
                  <a-row class="form-row" :gutter="24">
                    <a-col :lg="15" :md="15" :sm="24">
                      <a-input placeholder="输入单位" :hidden="unitStatus" v-decorator.trim="[ 'unit' ]" @change="onlyUnitOnChange" />
                      <a-select :value="unitList" placeholder="选择单位" v-decorator="[ 'unitId' ]" @change="manyUnitOnChange"
                                :hidden="manyUnitStatus" :dropdownMatchSelectWidth="false">
                        <div slot="dropdownRender" slot-scope="menu">
                          <v-nodes :vnodes="menu" />
                          <a-divider style="margin: 4px 0;" />
                          <div style="padding: 4px 8px; cursor: pointer;"
                               @mousedown="e => e.preventDefault()" @click="addUnit"><a-icon type="plus" /> 新增计量单位</div>
                        </div>
                        <a-select-option v-for="(item,index) in unitList"
                          :key="index" :value="item.id">
                          {{ item.name }}
                        </a-select-option>
                      </a-select>
                    </a-col>
                    <a-col :lg="9" :md="9" :sm="24">
                      <a-checkbox :checked="unitChecked" @change="unitOnChange">多单位</a-checkbox>
                    </a-col>
                  </a-row>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="颜色">
                  <a-input placeholder="请输入颜色" v-decorator.trim="[ 'color' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="基础重量" data-step="5" data-title="基础重量"
                  data-intro="请填写基本单位对应的重量，用于计算按重量分摊费用时单据中各行商品分摊的费用成本">
                  <a-input placeholder="请输入基础重量(kg)" v-decorator.trim="[ 'weight' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="保质期" data-step="6" data-title="保质期"
                  data-intro="保质期指的是商品的保质期(天)，主要针对带生产日期的，此类商品一般有批号">
                  <a-input-number style="width: 100%" placeholder="请输入保质期(天)" v-decorator.trim="[ 'expiryNum' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 4 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" label="类别"
                  data-step="7" data-title="类别" data-intro="类别需要在【商品类别】页面进行录入，录入之后在此处进行调用">
                  <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                                 :treeData="categoryTree" v-decorator="[ 'categoryId' ]" placeholder="请选择类别">
                  </a-tree-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号" data-step="8" data-title="序列号"
                  data-intro="此处是商品的序列号开关，如果选择了有，则在采购入库单据需要录入该商品的序列号，在销售出库单据需要选择该商品的序列号进行出库">
                  <a-select placeholder="有无序列号" v-decorator="[ 'enableSerialNumber' ]">
                    <a-select-option value="1">有</a-select-option>
                    <a-select-option value="0">无</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="批号" data-step="9" data-title="批号"
                  data-intro="此处是商品的批号开关，如果选择了有，则在采购入库单据需要录入该商品的批号和生产日期，在销售出库单据需要选择该商品的批号进行出库">
                  <a-select placeholder="有无批号" v-decorator="[ 'enableBatchNumber' ]">
                    <a-select-option value="1">有</a-select-option>
                    <a-select-option value="0">无</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="多属性" data-step="10" data-title="多属性"
                  data-intro="多属性是针对的sku商品（比如服装、鞋帽行业），此处开关如果启用就可以在下方进行多sku的配置，配置具体的颜色、尺码之类的组合">
                  <a-switch checked-children="启用" un-checked-children="关闭" v-model="skuSwitch" :disabled="switchDisabled" @change="onSkuChange"></a-switch>
                </a-form-item>
              </a-col>
            </a-row>
            <a-card v-if="skuSwitch">
              <a-row class="form-row" :gutter="24">
                <a-col :md="6" :sm="24">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="sku.manyColor">
                    <a-select mode="multiple" v-decorator="[ 'manyColor' ]" showSearch optionFilterProp="children">
                      <a-select-option v-for="(item,index) in sku.manyColorList" :key="index" :value="item.value">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="sku.manySize">
                    <a-select mode="multiple" v-decorator="[ 'manySize' ]" showSearch optionFilterProp="children">
                      <a-select-option v-for="(item,index) in sku.manySizeList" :key="index" :value="item.value">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="sku.other1">
                    <a-select mode="multiple" v-decorator="[ 'other1' ]" showSearch optionFilterProp="children">
                      <a-select-option v-for="(item,index) in sku.other1List" :key="index" :value="item.value">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </a-row>
              <a-row class="form-row" :gutter="24">
                <a-col :md="6" :sm="24">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="sku.other2">
                    <a-select mode="multiple" v-decorator="[ 'other2' ]" showSearch optionFilterProp="children">
                      <a-select-option v-for="(item,index) in sku.other2List" :key="index" :value="item.value">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="sku.other3">
                    <a-select mode="multiple" v-decorator="[ 'other3' ]" showSearch optionFilterProp="children">
                      <a-select-option v-for="(item,index) in sku.other3List" :key="index" :value="item.value">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="生成条码">
                    <a-switch v-model="barCodeSwitch" @change="onBarCodeChange"></a-switch>
                  </a-form-item>
                </a-col>
              </a-row>
            </a-card>
            <div style="margin-top:8px;" id="materialDetailModal">
              <j-editable-table
                ref="editableMeTable"
                :loading="meTable.loading"
                :columns="meTable.columns"
                :dataSource="meTable.dataSource"
                :minWidth="1000"
                :maxHeight="300"
                :rowNumber="true"
                :rowSelection="true"
                :actionButton="true"
                @added="onAdded">
                <template #buttonAfter>
                  <a-button @click="batchSetPrice('purchase')">采购价-批量</a-button>
                  <a-button style="margin-left: 8px" @click="batchSetPrice('commodity')">零售价-批量</a-button>
                  <a-button style="margin-left: 8px" @click="batchSetPrice('wholesale')">销售价-批量</a-button>
                  <a-button style="margin-left: 8px" @click="batchSetPrice('low')">最低售价-批量</a-button>
                </template>
              </j-editable-table>
              <!-- 表单区域 -->
              <batch-set-price-modal ref="priceModalForm" @ok="batchSetPriceModalFormOk"></batch-set-price-modal>
            </div>
            <a-row class="form-row" :gutter="24">
              <a-col :lg="24" :md="24" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}" label="">
                  <a-textarea :rows="2" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
                </a-form-item>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane key="2" tab="扩展信息" forceRender>
            <a-row v-if="mpShort.mfrs.enabled" class="form-row" :gutter="24">
              <a-col :lg="8" :md="8" :sm="8">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="mpShort.mfrs.name">
                  <a-input v-decorator.trim="[ 'mfrs' ]" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row v-if="mpShort.otherField1.enabled" class="form-row" :gutter="24">
              <a-col :lg="8" :md="8" :sm="8">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="mpShort.otherField1.name">
                  <a-input v-decorator.trim="[ 'otherField1' ]" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row v-if="mpShort.otherField2.enabled" class="form-row" :gutter="24">
              <a-col :lg="8" :md="8" :sm="8">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="mpShort.otherField2.name">
                  <a-input v-decorator.trim="[ 'otherField2' ]" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row v-if="mpShort.otherField3.enabled" class="form-row" :gutter="24">
              <a-col :lg="8" :md="8" :sm="8">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="mpShort.otherField3.name">
                  <a-input v-decorator.trim="[ 'otherField3' ]" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane key="3" tab="库存数量" forceRender>
            <j-editable-table
              ref="editableDepotTable"
              :loading="depotTable.loading"
              :columns="depotTable.columns"
              :dataSource="depotTable.dataSource"
              :minWidth="1000"
              :maxHeight="300"
              :rowNumber="true"
              :rowSelection="false"
              :actionButton="false">
              <template #buttonAfter>
                <a-button style="margin: 0px 0px 8px 0px" @click="batchSetStock('initStock')">期初库存-批量</a-button>
                <a-button style="margin-left: 8px" @click="batchSetStock('lowSafeStock')">最低安全库存-批量</a-button>
                <a-button style="margin-left: 8px" @click="batchSetStock('highSafeStock')">最高安全库存-批量</a-button>
              </template>
            </j-editable-table>
            <!-- 表单区域 -->
            <batch-set-stock-modal ref="stockModalForm" @ok="batchSetStockModalFormOk"></batch-set-stock-modal>
          </a-tab-pane>
          <a-tab-pane key="4" tab="图片信息" forceRender>
            <a-row class="form-row" :gutter="24">
              <a-col :lg="12" :md="12" :sm="24">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 4 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" label="图片信息">
                  <j-image-upload v-model="fileList" bizPath="material" text="上传" isMultiple></j-image-upload>
                </a-form-item>
              </a-col>
              <a-col :lg="12" :md="12" :sm="24"></a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </a-form>
    </a-spin>
    <unit-modal ref="unitModalForm" @ok="unitModalFormOk"></unit-modal>
  </j-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import BatchSetPriceModal from './BatchSetPriceModal'
  import BatchSetStockModal from './BatchSetStockModal'
  import UnitModal from '../../system/modules/UnitModal'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { FormTypes, VALIDATE_NO_PASSED, getRefPromise, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import {queryMaterialCategoryTreeList,checkMaterial,checkMaterialBarCode,getAllMaterialAttribute,getMaxBarCode} from '@/api/api'
  import { handleIntroJs,autoJumpNextInput } from "@/utils/util"
  import { httpAction, getAction } from '@/api/manage'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "MaterialModal",
    components: {
      BatchSetPriceModal,
      BatchSetStockModal,
      UnitModal,
      JImageUpload,
      JDate,
      JEditableTable,
      VNodes: {
        functional: true,
        render: (h, ctx) => ctx.props.vnodes,
      }
    },
    data () {
      return {
        title:"操作",
        visible: false,
        categoryTree: [],
        unitList: [],
        depotList: [],
        fileList:[],
        unitStatus: false,
        manyUnitStatus: true,
        unitChecked: false,
        skuSwitch: false, //sku开启状态
        switchDisabled: false, //开关的启用状态
        barCodeSwitch: false, //生成条码开关
        maxBarCodeInfo: '', //最大条码
        prefixNo: 'material',
        sku: {
          manyColor: '多颜色',
          manySize: '多尺寸',
          other1: '自定义1',
          other2: '自定义2',
          other3: '自定义3',
          manyColorList: [],
          manySizeList: [],
          other1List: [],
          other2List: [],
          other3List: [],
        },
        model: {},
        isReadOnly: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        mpShort: {
          mfrs: {},
          otherField1: {},
          otherField2: {},
          otherField3: {}
        },
        meTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '条码', key: 'barCode', width: '15%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' },
                { pattern: /^[1-9]\d*$/, message: '请输入零以上的正整数' },
                { pattern: /^\d{4,13}$/, message: '4到13位数字' },
                { handler: this.validateBarCode}]
            },
            {
              title: '单位', key: 'commodityUnit', width: '8%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            {
              title: '多属性', key: 'sku', width: '10%', type: FormTypes.input, defaultValue: '', readonly:true, placeholder: '点击生成条码赋值'
            },
            {
              title: '采购价', key: 'purchaseDecimal', width: '9%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '零售价', key: 'commodityDecimal', width: '9%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '销售价', key: 'wholesaleDecimal', width: '9%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '最低售价', key: 'lowDecimal', width: '9%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            }
          ]
        },
        depotTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '仓库', key: 'name', width: '15%', type: FormTypes.normal
            },
            {
              title: '期初库存数量', key: 'initStock', width: '15%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '最低安全库存数量', key: 'lowSafeStock', width: '15%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '最高安全库存数量', key: 'highSafeStock', width: '15%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            }
          ]
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          name:{
            rules: [
              { required: true, message: '请输入名称!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
            ]
          },
          unit:{
            rules: [
              { required: true, message: '请输入单位!' }
            ]
          }
        },
        url: {
          add: '/material/add',
          edit: '/material/update',
          materialsExtendList: '/materialsExtend/getDetailList',
          depotWithStock: '/depot/getAllListWithStock'
        }
      }
    },
    created () {
      this.maxBarCodeAction();
      this.loadTreeData();
      this.loadUnitListData();
      this.loadParseMaterialProperty();
    },
    methods: {
      // 获取所有的editableTable实例
      getAllTable() {
        return Promise.all([
          getRefPromise(this, 'editableMeTable'),
          getRefPromise(this, 'editableDepotTable')
        ])
      },
      add () {
        //隐藏多属性
        this.meTable.columns[2].type = FormTypes.hidden
        // 默认新增一条数据
        this.getAllTable().then(editableTables => {
          editableTables[0].add()
        })
        this.edit({});
        this.$nextTick(() => {
          handleIntroJs('material', 11)
        });
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.activeKey = '1'
        this.skuSwitch = false
        this.barCodeSwitch = false
        this.visible = true;
        if(JSON.stringify(record) === '{}') {
          this.fileList = []
        } else {
          setTimeout(() => {
            this.fileList = record.imgName
          }, 5)
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'name', 'standard', 'unit', 'unitId', 'model', 'color',
            'categoryId','enableSerialNumber','enableBatchNumber','expiryNum','weight','remark','mfrs','otherField1','otherField2','otherField3'))
          autoJumpNextInput('materialHeadModal')
          autoJumpNextInput('materialDetailModal')
        });
        this.initMaterialAttribute()
        // 加载子表数据
        if (this.model.id) {
          //禁用多属性开关
          this.switchDisabled = true
          // 判断是否是多单位
          if(this.model.unit){
            this.unitChecked = false
            this.unitStatus = false
            this.manyUnitStatus = true
          } else {
            this.unitChecked = true
            this.unitStatus = true
            this.manyUnitStatus = false
          }
          let params = { materialId: this.model.id }
          //编辑商品的时候多属性字段可以修改
          this.meTable.columns[2].readonly = false
          this.requestMeTableData(this.url.materialsExtendList, params, this.meTable)
          this.requestDepotTableData(this.url.depotWithStock, { mId: this.model.id }, this.depotTable)
        } else {
          this.switchDisabled = false
          this.meTable.columns[2].readonly = true
          this.requestDepotTableData(this.url.depotWithStock, { mId: 0 }, this.depotTable)
        }
      },
      /** 查询条码tab的数据 */
      requestMeTableData(url, params, tab) {
        tab.loading = true
        getAction(url, params).then(res => {
          for (let i = 0; i < res.data.rows.length; i++) {
            if(res.data.rows[i].sku) {
              this.meTable.columns[2].type = FormTypes.input
            } else {
              this.meTable.columns[2].type = FormTypes.hidden
            }
          }
          tab.dataSource = res.data.rows || []
        }).finally(() => {
          tab.loading = false
        })
      },
      /** 查询仓库tab的数据 */
      requestDepotTableData(url, params, tab) {
        tab.loading = true
        getAction(url, params).then(res => {
          tab.dataSource = res.data || []
        }).finally(() => {
          tab.loading = false
        })
      },
      close () {
        this.$emit('close');
        this.visible = false
        this.unitStatus = false
        this.manyUnitStatus = true
        this.unitChecked = false
        this.getAllTable().then(editableTables => {
          editableTables[0].initialize()
          editableTables[1].initialize()
        })
      },
      handleOk () {
        this.validateFields()
      },
      handleCancel () {
        this.close()
      },
      /** 触发表单验证 */
      validateFields() {
        this.getAllTable().then(tables => {
          /** 一次性验证主表和所有的次表 */
          return validateFormAndTables(this.form, tables)
        }).then(allValues => {
          let formData = this.classifyIntoFormData(allValues)
          formData.sortList = [];
          if(formData.unit === undefined) {formData.unit = ''}
          if(formData.unitId === undefined) {formData.unitId = ''}
          if(this.unitChecked) {formData.unit = ''} else {formData.unitId = ''}
          // 发起请求
          return this.requestAddOrEdit(formData)
        }).catch(e => {
          if (e.error === VALIDATE_NO_PASSED) {
            // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
            this.activeKey = e.index == null ? this.activeKey : (e.index + 1).toString()
          } else {
            console.error(e)
          }
        })
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let materialMain = Object.assign(this.model, allValues.formValue)
        return {
          ...materialMain, // 展开
          meList: allValues.tablesValue[0].values,
          stock: allValues.tablesValue[1].values,
        }
      },

      /** 发起新增或修改的请求 */
      requestAddOrEdit(formData) {
        if(formData.unit === '' && formData.unitId === '') {
          this.$message.warning('抱歉，单位为必填项！');
          return;
        }
        if(formData.meList.length === 0) {
          this.$message.warning('抱歉，请输入条码信息！');
          return;
        }
        //校验商品是否存在，通过校验商品的名称、型号、规格、颜色、单位、制造商等
        let param = {
          id: this.model.id?this.model.id:0,
          name: this.model.name,
          model: this.parseParam(this.model.model),
          color: this.parseParam(this.model.color),
          standard: this.parseParam(this.model.standard),
          mfrs: this.parseParam(this.model.mfrs),
          otherField1: this.parseParam(this.model.otherField1),
          otherField2: this.parseParam(this.model.otherField2),
          otherField3: this.parseParam(this.model.otherField3),
          unit: this.parseParam(this.model.unit),
          unitId: this.parseParam(this.model.unitId)
        }
        checkMaterial(param).then((res)=>{
          if(res && res.code===200) {
            if(res.data.status){
              this.$message.warning('抱歉，该商品已存在！');
              return;
            } else {
              //进一步校验单位
              let manyUnitselected = ''
              if(formData.unitId) {
                for(let i=0; i<this.unitList.length; i++) {
                  if(this.unitList[i].id == formData.unitId) {
                    manyUnitselected = this.unitList[i].name
                  }
                }
              }
              let manyUnitInfo = manyUnitselected.substring(0, manyUnitselected.indexOf("("));
              let unitArr = manyUnitInfo.split(",");
              if(!formData.unit) {
                //此时为多单位
                if (formData.meList.length<2){
                  this.$message.warning('多单位的商品条码行数至少要有两行，请再新增一行条码信息！');
                  return;
                }
                if(formData.meList[0].commodityUnit != unitArr[0]) {
                  this.$message.warning('条码之后的单位填写有误，单位【' + formData.meList[0].commodityUnit
                    + '】请修改为【' + unitArr[0] + '】！');
                  return;
                }
                if(formData.meList[1].commodityUnit != unitArr[1]) {
                  this.$message.warning('条码之后的单位填写有误，单位【' + formData.meList[1].commodityUnit
                    + '】请修改为【' + unitArr[1] + '】！');
                  return;
                }
              }
              for(let i=0; i<formData.meList.length; i++) {
                let commodityUnit = formData.meList[i].commodityUnit;
                if(formData.unit) {
                  if(commodityUnit != formData.unit) {
                    this.$message.warning('条码之后的单位填写有误，单位【' + commodityUnit + '】请修改为【'
                      + formData.unit + '】！');
                    return;
                  }
                } else if(manyUnitselected) {
                  if(commodityUnit != unitArr[0] && commodityUnit != unitArr[1]) {
                    this.$message.warning('条码之后的单位填写有误，单位【' + commodityUnit + '】请修改为【'
                      + unitArr[0]+ '】或【' + unitArr[1]+ '】！');
                    return;
                  }
                }
              }
              if(this.fileList && this.fileList.length > 0) {
                formData.imgName = this.fileList
              } else {
                formData.imgName = ''
              }
              //接口调用
              let url = this.url.add, method = 'post'
              if (this.model.id) {
                url = this.url.edit
                method = 'put'
              }
              const that = this;
              this.confirmLoading = true
              httpAction(url, formData, method).then((res) => {
                if(res.code === 200){
                  that.$emit('ok');
                  that.confirmLoading = false
                  that.close();
                }else{
                  that.$message.warning(res.data.message);
                  that.confirmLoading = false
                }
              }).finally(() => {
              })
            }
          }
        })
      },
      parseParam(param) {
        return param ? param: ""
      },
      validateBarCode(type, value, row, column, callback, target) {
        let params = {
          barCode: value,
          id: row.id.length >= 20?0: row.id
        };
        checkMaterialBarCode(params).then((res)=>{
          if(res && res.code===200) {
            if(!res.data.status){
              callback(true);
            } else {
              callback(false, '该条码已经存在');
            }
          } else {
            callback(false, res.data);
          }
        });
      },
      maxBarCodeAction(){
        getMaxBarCode({}).then((res)=> {
          if (res && res.code === 200) {
            this.maxBarCodeInfo = res.data.barCode - 0
          }
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
      loadUnitListData(){
        let that = this;
        let params = {};
        params.currentPage = 1;
        params.pageSize = 100;
        getAction('/unit/list', params).then((res) => {
          if(res){
            that.unitList = res.data.rows;
          }
        })
      },
      onSkuChange(checked) {
        this.skuSwitch = checked
        if (checked) {
          this.meTable.columns[2].type = FormTypes.input
          this.form.setFieldsValue({ 'color': '' })
        } else {
          this.meTable.columns[2].type = FormTypes.hidden
        }
      },
      onBarCodeChange(checked) {
        let unit = this.form.getFieldValue('unit')
        if(unit) {
          if(checked){
            //计算多属性已经选择了几个
            let count = this.getNumByField('manyColor') + this.getNumByField('manySize')
              + this.getNumByField('other1') + this.getNumByField('other2') + this.getNumByField('other3')
            if(count === 2) {
              let skuArr = []
              if(this.getNumByField('manyColor')) {
                skuArr.push(this.form.getFieldValue('manyColor'))
              }
              if(this.getNumByField('manySize')) {
                skuArr.push(this.form.getFieldValue('manySize'))
              }
              if(this.getNumByField('other1')) {
                skuArr.push(this.form.getFieldValue('other1'))
              }
              if(this.getNumByField('other2')) {
                skuArr.push(this.form.getFieldValue('other2'))
              }
              if(this.getNumByField('other3')) {
                skuArr.push(this.form.getFieldValue('other3'))
              }
              let skuArrOne = skuArr[0]
              let skuArrTwo = skuArr[1]
              let barCodeSku = []
              for (let i = 0; i < skuArrOne.length; i++) {
                for (let j = 0; j < skuArrTwo.length; j++) {
                  barCodeSku.push(skuArrOne[i] + ',' + skuArrTwo[j])
                }
              }
              let meTableData = []
              getMaxBarCode({}).then((res)=>{
                if(res && res.code===200) {
                  let maxBarCode = res.data.barCode-0
                  for (let i = 0; i < barCodeSku.length; i++) {
                    let currentBarCode = maxBarCode + i + 1
                    meTableData.push({barCode: currentBarCode, commodityUnit: unit, sku: barCodeSku[i]})
                  }
                  this.meTable.dataSource = meTableData
                }
              })
            } else {
              this.$message.warning('请选择两个属性！');
              this.barCodeSwitch = false;
            }
          } else {
            this.meTable.dataSource = []
          }
        } else {
          this.$message.warning('请填写单位，注意不要勾选多单位！');
          this.barCodeSwitch = false;
        }
      },
      getNumByField(field) {
        let num = 0
        if(this.form.getFieldValue(field)) {
          if(this.form.getFieldValue(field).length>0) {
            num = 1
          }
        }
        return num
      },
      onAdded(event) {
        const { row, target } = event
        let unit = ''
        if(this.unitStatus == false) {
          unit = this.form.getFieldValue('unit')
        }
        this.maxBarCodeInfo = this.maxBarCodeInfo + 1
        target.setValues([{rowKey: row.id, values: {barCode: this.maxBarCodeInfo, commodityUnit: unit?unit:''}}])
      },
      batchSetPrice(type) {
        if(this.skuSwitch || this.model.id){
          this.$refs.priceModalForm.add(type);
          this.$refs.priceModalForm.disableSubmit = false;
        } else {
          this.$message.warning('抱歉，只有开启多属性才能进行批量操作！');
        }
      },
      batchSetStock(type) {
        this.$refs.stockModalForm.add(type);
        this.$refs.stockModalForm.disableSubmit = false;
      },
      batchSetPriceModalFormOk(price, batchType) {
        let arr = this.meTable.dataSource
        if(arr.length === 0) {
          this.$message.warning('请先录入条码、单位等信息！');
        } else {
          let meTableData = []
          for (let i = 0; i < arr.length; i++) {
            let meInfo = {barCode: arr[i].barCode, commodityUnit: arr[i].commodityUnit, sku: arr[i].sku,
              purchaseDecimal: arr[i].purchaseDecimal, commodityDecimal: arr[i].commodityDecimal,
              wholesaleDecimal: arr[i].wholesaleDecimal, lowDecimal: arr[i].lowDecimal}
            if(batchType === 'purchase') {
              meInfo.purchaseDecimal = price-0
            } else if(batchType === 'commodity') {
              meInfo.commodityDecimal = price-0
            } else if(batchType === 'wholesale') {
              meInfo.wholesaleDecimal = price-0
            } else if(batchType === 'low') {
              meInfo.lowDecimal = price-0
            }
            if(arr[i].id) {
              meInfo.id = arr[i].id
            }
            meTableData.push(meInfo)
          }
          this.meTable.dataSource = meTableData
        }
      },
      batchSetStockModalFormOk(stock, batchType) {
        let arr = this.depotTable.dataSource
        let depotTableData = []
        for (let i = 0; i < arr.length; i++) {
          let depotInfo = {name: arr[i].name, initStock: arr[i].initStock,
            lowSafeStock: arr[i].lowSafeStock, highSafeStock: arr[i].highSafeStock}
          if (batchType === 'initStock') {
            depotInfo.initStock = stock - 0
          } else if (batchType === 'lowSafeStock') {
            depotInfo.lowSafeStock = stock - 0
          } else if (batchType === 'highSafeStock') {
            depotInfo.highSafeStock = stock - 0
          }
          if (arr[i].id) {
            depotInfo.id = arr[i].id
          }
          depotTableData.push(depotInfo)
        }
        this.depotTable.dataSource = depotTableData
      },
      initMaterialAttribute() {
        getAllMaterialAttribute({}).then((res)=>{
          if(res && res.code===200) {
            if(res.data) {
              this.sku.manyColor = res.data.manyColorName;
              this.sku.manySize = res.data.manySizeName;
              this.sku.other1 = res.data.other1Name;
              this.sku.other2 = res.data.other2Name;
              this.sku.other3 = res.data.other3Name;
              this.sku.manyColorList = res.data.manyColorValue;
              this.sku.manySizeList = res.data.manySizeValue;
              this.sku.other1List = res.data.other1Value;
              this.sku.other2List = res.data.other2Value;
              this.sku.other3List = res.data.other3Value;
            }
          }
        });
      },
      loadParseMaterialProperty() {
        let mpList = Vue.ls.get('materialPropertyList')
        for (let i = 0; i < mpList.length; i++) {
          if (mpList[i].nativeName === "制造商") {
            this.mpShort.mfrs.name = mpList[i].anotherName
            this.mpShort.mfrs.enabled = mpList[i].enabled
          }
          if (mpList[i].nativeName === "自定义1") {
            this.mpShort.otherField1.name = mpList[i].anotherName
            this.mpShort.otherField1.enabled = mpList[i].enabled
          }
          if (mpList[i].nativeName === "自定义2") {
            this.mpShort.otherField2.name = mpList[i].anotherName
            this.mpShort.otherField2.enabled = mpList[i].enabled
          }
          if (mpList[i].nativeName === "自定义3") {
            this.mpShort.otherField3.name = mpList[i].anotherName
            this.mpShort.otherField3.enabled = mpList[i].enabled
          }
        }
      },
      onlyUnitOnChange(e) {
        this.$refs.editableMeTable.getValues((error, values) => {
          let mArr = values
          for (let i = 0; i < mArr.length; i++) {
            let mInfo = mArr[i]
            mInfo.commodityUnit = e.target.value
          }
          this.meTable.dataSource = mArr
        })
      },
      manyUnitOnChange(value) {
        let unitArr = this.unitList
        let basicUnit = '', otherUnit = ''
        for (let i = 0; i < unitArr.length; i++) {
          if(unitArr[i].id === value) {
            basicUnit = unitArr[i].basicUnit
            otherUnit = unitArr[i].otherUnit
          }
        }
        this.$refs.editableMeTable.getValues((error, values) => {
          let mArr = values
          for (let i = 0; i < mArr.length; i++) {
            let mInfo = mArr[i]
            if(i===0) {
              mInfo.commodityUnit = basicUnit
            } else {
              mInfo.commodityUnit = otherUnit
            }
          }
          this.meTable.dataSource = mArr
        })
      },
      unitOnChange (e) {
        let isChecked = e.target.checked;
        if(isChecked) {
          this.unitStatus = true;
          this.manyUnitStatus = false;
          this.unitChecked = true;
        } else {
          this.unitStatus = false;
          this.manyUnitStatus = true;
          this.unitChecked = false;
        }
      },
      addUnit() {
        this.$refs.unitModalForm.add();
        this.$refs.unitModalForm.title = "新增计量单位";
        this.$refs.unitModalForm.disableSubmit = false;
      },
      unitModalFormOk() {
        this.loadUnitListData()
      }
    }
  }
</script>
<style scoped>
  .input-table {
    max-width: 100%;
    min-width: 1550px;
  }
</style>
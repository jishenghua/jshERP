<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    v-bind:prefixNo="prefixNo"
    fullscreen
    switchHelp
    switchFullscreen
    @cancel="handleCancel"
    :id="prefixNo"
    :style="modalStyle">
    <template slot="footer">
      <a-button key="back" @click="handleCancel">取消</a-button>
      <a-button type="primary" v-if="showOkFlag" :loading="confirmLoading" @click="handleOk">保存（Ctrl+S）</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-tabs default-active-key="1" size="small">
          <a-tab-pane key="1" tab="基本信息" id="materialHeadModal" forceRender>
            <a-row class="form-row" :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="名称" data-step="1" data-title="名称" data-intro="名称必填，可以重复">
                  <a-input placeholder="请输入名称" v-decorator.trim="[ 'name', validatorRules.name ]" @change="handleNameChange" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="规格" data-step="2" data-title="规格" data-intro="规格不必填，比如：10克">
                  <a-input placeholder="请输入规格" v-decorator.trim="[ 'standard', validatorRules.standard ]"/>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="型号" data-step="3" data-title="型号" data-intro="型号是比规格更小的属性，比如：RX-01">
                  <a-input placeholder="请输入型号" v-decorator.trim="[ 'model', validatorRules.model ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单位"
                  data-step="4" data-title="单位" data-intro="此处支持单个单位和多单位，勾选多单位就可以切换到多单位的下拉框，多单位需要先在【计量单位】页面进行录入。
                  比如牛奶有瓶和箱两种单位，12瓶=1箱，这就构成了多单位，多单位中有个换算比例">
                  <a-row class="form-row" :gutter="24">
                    <a-col :lg="15" :md="15" :sm="24" style="padding:0px 0px 0px 12px;">
                      <a-input placeholder="输入单位" v-if="!unitChecked" v-decorator.trim="[ 'unit', validatorRules.unit ]" @change="onlyUnitOnChange" />
                      <a-select :value="unitList" placeholder="选择多单位" v-decorator="[ 'unitId', validatorRules.unitId ]" @change="manyUnitOnChange"
                        showSearch optionFilterProp="children" v-if="unitChecked" :dropdownMatchSelectWidth="false">
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
                    <a-col :lg="9" :md="9" :sm="24" style="padding:0px; text-align:center">
                      <a-checkbox :checked="unitChecked" @change="unitOnChange">多单位</a-checkbox>
                    </a-col>
                  </a-row>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row class="form-row" :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="颜色" data-step="5" data-title="颜色"
                             data-intro="请填写商品的颜色，如果是多属性商品可以不填（下面有多属性开关）">
                  <a-input placeholder="请输入颜色" v-decorator.trim="[ 'color' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="品牌" data-step="6" data-title="品牌"
                             data-intro="请填写商品的品牌，方便区别不同品牌的商品">
                  <a-input placeholder="请输入品牌" v-decorator.trim="[ 'brand' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="助记码" data-step="7" data-title="助记码"
                             data-intro="助记码自动生成，助记码是商品名称的首字母缩写">
                  <a-input placeholder="" v-decorator.trim="[ 'mnemonic' ]" :readOnly="true" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="类别"
                             data-step="8" data-title="类别" data-intro="类别需要在【商品类别】页面进行录入，录入之后在此处进行调用">
                  <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                                 :treeData="categoryTree" v-decorator="[ 'categoryId' ]" placeholder="请选择类别">
                  </a-tree-select>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row class="form-row" :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="基础重量" data-step="9" data-title="基础重量"
                  data-intro="请填写基本单位对应的重量，用于计算按重量分摊费用时单据中各行商品分摊的费用成本">
                  <a-input-number style="width: 100%" placeholder="请输入基础重量(kg)" v-decorator.trim="[ 'weight' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="保质期" data-step="10" data-title="保质期"
                  data-intro="保质期指的是商品的保质期(天)，主要针对带生产日期的，此类商品一般有批号">
                  <a-input-number style="width: 100%" placeholder="请输入保质期(天)" v-decorator.trim="[ 'expiryNum' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="仓位货架" data-step="11" data-title="仓位货架"
                             data-intro="仓位货架指的是仓库中的仓位和货架号，主要适用于仓库较大的场景，方便查找商品的准确位置">
                  <a-input style="width: 100%" placeholder="请输入仓位货架" v-decorator.trim="[ 'position' ]" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="制造商" data-step="12" data-title="制造商"
                             data-intro="请填写商品的制造商，一般适用于制造行业">
                  <a-input placeholder="请输入制造商" v-decorator.trim="[ 'mfrs' ]" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row class="form-row" :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号" data-step="13" data-title="序列号"
                  data-intro="此处是商品的序列号开关，如果选择了有，则在采购入库单据需要录入该商品的序列号，在销售出库单据需要选择该商品的序列号进行出库">
                  <a-tooltip title="如果选择为有，则在采购入库单需要录入该商品的序列号">
                    <a-select placeholder="有无序列号" v-decorator="[ 'enableSerialNumber' ]">
                      <a-select-option value="1">有</a-select-option>
                      <a-select-option value="0">无</a-select-option>
                    </a-select>
                  </a-tooltip>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="批号" data-step="14" data-title="批号"
                  data-intro="此处是商品的批号开关，如果选择了有，则在采购入库单据需要录入该商品的批号和有效期，在销售出库单据需要选择该商品的批号进行出库">
                  <a-tooltip title="如果选择为有，则在采购入库单需要录入该商品的批号和有效期">
                    <a-select placeholder="有无批号" v-decorator="[ 'enableBatchNumber' ]">
                      <a-select-option value="1">有</a-select-option>
                      <a-select-option value="0">无</a-select-option>
                    </a-select>
                  </a-tooltip>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24" v-if="!model.id">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="多属性" data-step="15" data-title="多属性"
                  data-intro="多属性是针对的sku商品（比如服装、鞋帽行业），此处开关如果启用就可以在下方进行多sku的配置，配置具体的颜色、尺码之类的组合">
                  <a-tooltip title="多属性针对服装、鞋帽等行业，需要先录入单位才能激活此处输入框">
                    <a-tag class="tag-info" v-if="!manySkuStatus">需要先录入单位才能激活</a-tag>
                    <a-select mode="multiple" v-decorator="[ 'manySku' ]" showSearch optionFilterProp="children"
                      placeholder="请选择多属性（可多选）" @change="onManySkuChange" v-show="manySkuStatus">
                      <a-select-option v-for="(item,index) in materialAttributeList" :key="index" :value="item.value" :disabled="item.disabled">
                        {{ item.name }}
                      </a-select-option>
                    </a-select>
                  </a-tooltip>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row class="form-row" :gutter="24">
              <a-col :md="12" :sm="24" v-if="manySkuSelected>=1">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 4 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" :label="skuOneTitle">
                  <a-select mode="multiple" v-decorator="[ 'skuOne' ]" showSearch optionFilterProp="children"
                            placeholder="请选择（可多选）" @select="onSkuChange" @deselect="onSkuOneDeSelect">
                    <a-select-option v-for="(item,index) in skuOneList" :key="index" :value="item.value">
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="12" :sm="24" v-if="manySkuSelected>=2">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 4 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" :label="skuTwoTitle">
                  <a-select mode="multiple" v-decorator="[ 'skuTwo' ]" showSearch optionFilterProp="children"
                            placeholder="请选择（可多选）" @select="onSkuChange" @deselect="onSkuTwoDeSelect">
                    <a-select-option v-for="(item,index) in skuTwoList" :key="index" :value="item.value">
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="12" :sm="24" v-if="manySkuSelected>=3">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 4 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" :label="skuThreeTitle">
                  <a-select mode="multiple" v-decorator="[ 'skuThree' ]" showSearch optionFilterProp="children"
                            placeholder="请选择（可多选）" @select="onSkuChange" @deselect="onSkuThreeDeSelect">
                    <a-select-option v-for="(item,index) in skuThreeList" :key="index" :value="item.value">
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </a-row>
            <div style="margin-top:8px;" id="materialDetailModal">
              <j-editable-table
                ref="editableMeTable"
                :loading="meTable.loading"
                :columns="meTable.columns"
                :dataSource="meTable.dataSource"
                :height="300"
                :minWidth="1000"
                :maxHeight="300"
                :rowNumber="false"
                :rowSelection="true"
                :actionButton="true"
                @valueChange="onValueChange"
                @added="onAdded"
                @deleted="onDeleted">
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
                  <a-textarea :rows="1" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
                </a-form-item>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane key="2" tab="扩展信息" forceRender>
            <a-row v-if="mpShort.otherField1.enabled" class="form-row" :gutter="24">
              <a-col :lg="6" :md="6" :sm="6">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="mpShort.otherField1.name">
                  <a-input v-decorator.trim="[ 'otherField1' ]" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row v-if="mpShort.otherField2.enabled" class="form-row" :gutter="24">
              <a-col :lg="6" :md="6" :sm="6">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="mpShort.otherField2.name">
                  <a-input v-decorator.trim="[ 'otherField2' ]" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row v-if="mpShort.otherField3.enabled" class="form-row" :gutter="24">
              <a-col :lg="6" :md="6" :sm="6">
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
            <a-row class="form-row" :gutter="24" style="padding-top:20px">
              <a-col :lg="18" :md="18" :sm="24">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 3 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" label="图片信息">
                  <j-image-upload v-model="fileList" bizPath="material" text="上传" isMultiple></j-image-upload>
                </a-form-item>
              </a-col>
              <a-col :lg="6" :md="6" :sm="24"></a-col>
            </a-row>
            <a-row class="form-row" :gutter="24">
              <a-col :lg="18" :md="18" :sm="24">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 3 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" label="上传提示">
                  图片最多4张，且单张大小不超过1M
                </a-form-item>
              </a-col>
              <a-col :lg="6" :md="6" :sm="24"></a-col>
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
  import { FormTypes, getRefPromise, VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import { checkMaterial, checkMaterialBarCode, getMaterialAttributeNameList,
    getMaterialAttributeValueListById, getMaxBarCode, queryMaterialCategoryTreeList, changeNameToPinYin } from '@/api/api'
  import { removeByVal, autoJumpNextInput, handleIntroJs } from '@/utils/util'
  import { getAction, httpAction } from '@/api/manage'
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
        width: '1300px',
        visible: false,
        modalStyle: '',
        categoryTree: [],
        unitList: [],
        depotList: [],
        fileList:[],
        unitStatus: false,
        manyUnitStatus: true,
        unitChecked: false,
        manySkuStatus: false,
        switchDisabled: false, //开关的启用状态
        barCodeSwitch: false, //生成条码开关
        maxBarCodeInfo: '', //最大条码
        meDeleteIdList: [], //删除条码信息的id数组
        prefixNo: 'material',
        materialAttributeList: [],
        skuOneTitle: '属性1',
        skuTwoTitle: '属性2',
        skuThreeTitle: '属性3',
        skuOneList: [],
        skuTwoList: [],
        skuThreeList: [],
        manySkuSelected: 0,
        model: {},
        showOkFlag: true,
        setTimeFlag: null,
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
                { pattern: /^.{4,40}$/, message: '长度为4到40位' },
                { handler: this.validateBarCode}]
            },
            {
              title: '单位', key: 'commodityUnit', width: '8%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            {
              title: '多属性', key: 'sku', width: '25%', type: FormTypes.input, defaultValue: '', readonly:true, placeholder: '请输入${title}'
            },
            {
              title: '采购价', key: 'purchaseDecimal', width: '9%', type: FormTypes.inputNumber, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '零售价', key: 'commodityDecimal', width: '9%', type: FormTypes.inputNumber, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '销售价', key: 'wholesaleDecimal', width: '9%', type: FormTypes.inputNumber, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '最低售价', key: 'lowDecimal', width: '9%', type: FormTypes.inputNumber, defaultValue: '', placeholder: '请输入${title}'
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
              title: '期初库存数量', key: 'initStock', width: '15%', type: FormTypes.inputNumber, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '最低安全库存数量', key: 'lowSafeStock', width: '15%', type: FormTypes.inputNumber, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '最高安全库存数量', key: 'highSafeStock', width: '15%', type: FormTypes.inputNumber, defaultValue: '', placeholder: '请输入${title}'
            }
          ]
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          name:{
            rules: [
              { required: true, message: '请输入名称!' },
              { max: 100, message: '长度请小于100个字符', trigger: 'blur' }
            ]
          },
          standard:{
            rules: [
              { max: 100, message: '长度请小于100个字符', trigger: 'blur' }
            ]
          },
          model:{
            rules: [
              { max: 100, message: '长度请小于100个字符', trigger: 'blur' }
            ]
          },
          unit:{
            rules: [
              { required: true, message: '请输入单位!' }
            ]
          },
          unitId:{
            rules: [
              { required: true, message: '请选择多单位!' }
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
      this.loadParseMaterialProperty()
      let realScreenWidth = window.screen.width
      this.width = realScreenWidth<1500?'1200px':'1400px'
    },
    mounted() {
      document.getElementById(this.prefixNo).addEventListener('keydown', this.handleOkKey)
    },
    beforeDestroy() {
      document.getElementById(this.prefixNo).removeEventListener('keydown', this.handleOkKey)
    },
    methods: {
      // 快捷键
      handleOkKey(e) {
        const key = window.event.keyCode ? window.event.keyCode : window.event.which
        if (key === 83 && e.ctrlKey) {
          //保存 CTRL+S
          this.handleOk()
          e.preventDefault()
        }
      },
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
        this.edit({})
        this.$nextTick(() => {
          handleIntroJs('material', 11)
        })
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.activeKey = '1'
        this.manySkuSelected = 0
        this.barCodeSwitch = false
        this.manySkuStatus = false
        this.maxBarCodeInfo = ''
        this.visible = true
        this.meDeleteIdList = []
        this.modalStyle = 'top:20px;height: 95%;'
        if(JSON.stringify(record) === '{}') {
          this.fileList = []
        } else {
          setTimeout(() => {
            this.fileList = record.imgName
          }, 5)
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'name', 'standard', 'unit', 'unitId', 'model', 'color', 'brand', 'mnemonic',
            'categoryId','enableSerialNumber','enableBatchNumber','position','expiryNum','weight','remark','mfrs','otherField1','otherField2','otherField3'))
          autoJumpNextInput('materialHeadModal')
          autoJumpNextInput('materialDetailModal')
        });
        this.initMaterialAttribute()
        this.loadTreeData()
        this.loadUnitListData()
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
        this.$emit('close')
        this.visible = false
        this.modalStyle = ''
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
        if(formData.meList.length === 0) {
          this.$message.warning('抱歉，请输入条码信息！');
          return;
        }
        if(formData.enableSerialNumber === '1' && formData.enableBatchNumber === '1') {
          this.$message.warning('抱歉，序列号和批号只能选择一项！');
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
              let basicUnit = '', otherUnit = '', otherUnitTwo = '', otherUnitThree = ''
              if(formData.unitId) {
                let unitArr = this.unitList
                for(let i=0; i < unitArr.length; i++) {
                  if(unitArr[i].id == formData.unitId) {
                    basicUnit = unitArr[i].basicUnit
                    otherUnit = unitArr[i].otherUnit
                    if(unitArr[i].otherUnitTwo) {
                      otherUnitTwo = unitArr[i].otherUnitTwo
                    }
                    if(unitArr[i].otherUnitThree) {
                      otherUnitThree = unitArr[i].otherUnitThree
                    }
                  }
                }
              }
              if(!formData.unit) {
                //此时为多单位
                if (formData.meList.length<2){
                  this.$message.warning('多单位的商品条码行数至少要有两行，请再新增一行条码信息！');
                  return;
                }
                if(formData.meList[0].commodityUnit != basicUnit) {
                  this.$message.warning('条码之后的单位填写有误，单位【' + formData.meList[0].commodityUnit
                    + '】请修改为【' + basicUnit + '】！');
                  return;
                }
                if(formData.meList[1].commodityUnit != otherUnit) {
                  this.$message.warning('条码之后的单位填写有误，单位【' + formData.meList[1].commodityUnit
                    + '】请修改为【' + otherUnit + '】！');
                  return;
                }
              }
              let skuCount = 0
              for(let i=0; i<formData.meList.length; i++) {
                let commodityUnit = formData.meList[i].commodityUnit;
                if(formData.unit) {
                  if(commodityUnit != formData.unit) {
                    this.$message.warning('条码之后的单位填写有误，单位【' + commodityUnit + '】请修改为【'
                      + formData.unit + '】！');
                    return;
                  }
                } else if(formData.unitId) {
                  if(commodityUnit != basicUnit && commodityUnit != otherUnit && commodityUnit != otherUnitTwo && commodityUnit != otherUnitThree) {
                    let warnInfo = '条码之后的单位填写有误，单位【' + commodityUnit + '】请修改为【' + basicUnit+ '】或【' + otherUnit+ '】'
                    if(otherUnitTwo) {
                      warnInfo += '或【' + otherUnitTwo+ '】'
                    }
                    if(otherUnitThree) {
                      warnInfo += '或【' + otherUnitThree+ '】'
                    }
                    warnInfo += '！'
                    this.$message.warning(warnInfo);
                    return;
                  }
                }
                if(formData.sku) {
                  skuCount++
                }
              }
              //对最低和最高安全库存进行校验
              for (let i = 0; i < formData.stock.length; i++) {
                let depotStockObj = formData.stock[i]
                if(skuCount && depotStockObj.initStock && depotStockObj.initStock-0) {
                  this.$message.warning('抱歉，多属性商品不能录入期初库存，建议进行盘点录入！')
                  return
                }
                if(formData.enableSerialNumber === '1' && depotStockObj.initStock && depotStockObj.initStock-0) {
                  this.$message.warning('抱歉，序列号商品不能录入期初库存，建议进行入库单据录入！')
                  return
                }
                if(formData.enableBatchNumber === '1' && depotStockObj.initStock && depotStockObj.initStock-0) {
                  this.$message.warning('抱歉，批号商品不能录入期初库存，建议进行入库单据录入！')
                  return
                }
                if(depotStockObj.lowSafeStock && depotStockObj.highSafeStock) {
                  if(depotStockObj.lowSafeStock-0 > depotStockObj.highSafeStock-0) {
                    this.$message.warning('抱歉，' + depotStockObj.name + '的最低安全库存大于最高安全库存！')
                    return
                  }
                }
              }
              //图片校验
              if(this.fileList && this.fileList.length > 0) {
                formData.imgName = this.fileList
                let fileArr = this.fileList.split(',')
                if(fileArr.length > 4) {
                  this.$message.warning('抱歉，商品图片不能超过4张！');
                  return
                }
              } else {
                formData.imgName = ''
              }
              formData.meDeleteIdList = this.meDeleteIdList
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
        getAction('/unit/getAllList', params).then((res) => {
          if(res){
            that.unitList = res.data;
          }
        })
      },
      onManySkuChange(value) {
        this.manySkuSelected = value.length
        //控制多属性下拉框中选择项的状态
        if(value.length < 3){
          this.materialAttributeList.forEach((item,index,array)=>{
            (array.indexOf(item.value) === -1)?Vue.set(array[index], 'disabled', false):''
          })
        }else{
          this.materialAttributeList.forEach((item,index,array)=>{
            (value.indexOf(item.value) === -1)?Vue.set(array[index], 'disabled', true):''
          })
        }
        //更新属性1和属性2和属性3的下拉框
        if(value.length <= 3) {
          let skuOneId = value[0]
          let skuTwoId = value[1]
          let skuThreeId = value[2]
          this.materialAttributeList.forEach(item => {
            if(item.value === skuOneId) {
              this.skuOneTitle = item.name
            }
            if(item.value === skuTwoId) {
              this.skuTwoTitle = item.name
            }
            if(item.value === skuThreeId) {
              this.skuThreeTitle = item.name
            }
          })
          getMaterialAttributeValueListById({'id': skuOneId}).then((res)=>{
            if(res) {
              this.skuOneList = res
            }
          })
          getMaterialAttributeValueListById({'id': skuTwoId}).then((res)=>{
            if(res) {
              this.skuTwoList = res
            }
          })
          getMaterialAttributeValueListById({'id': skuThreeId}).then((res)=>{
            if(res) {
              this.skuThreeList = res
            }
          })
        }
        //控制条码列表中的多属性列
        if(value.length>0) {
          this.meTable.columns[2].type = FormTypes.input
        } else {
          this.meTable.columns[2].type = FormTypes.hidden
        }
        this.barCodeSwitch = false;
        this.meTable.dataSource = []
      },
      onSkuChange() {
        let skuOneData = this.form.getFieldValue('skuOne')
        let skuTwoData = this.form.getFieldValue('skuTwo')
        let skuThreeData = this.form.getFieldValue('skuThree')
        this.autoSkuList(skuOneData, skuTwoData, skuThreeData)
      },
      onSkuOneDeSelect(value) {
        let skuOneData = this.form.getFieldValue('skuOne')
        let skuTwoData = this.form.getFieldValue('skuTwo')
        let skuThreeData = this.form.getFieldValue('skuThree')
        removeByVal(skuOneData, value)
        this.autoSkuList(skuOneData, skuTwoData, skuThreeData)
      },
      onSkuTwoDeSelect(value) {
        let skuOneData = this.form.getFieldValue('skuOne')
        let skuTwoData = this.form.getFieldValue('skuTwo')
        let skuThreeData = this.form.getFieldValue('skuThree')
        removeByVal(skuTwoData, value)
        this.autoSkuList(skuOneData, skuTwoData, skuThreeData)
      },
      onSkuThreeDeSelect(value) {
        let skuOneData = this.form.getFieldValue('skuOne')
        let skuTwoData = this.form.getFieldValue('skuTwo')
        let skuThreeData = this.form.getFieldValue('skuThree')
        removeByVal(skuThreeData, value)
        this.autoSkuList(skuOneData, skuTwoData, skuThreeData)
      },
      autoSkuList(skuOneData, skuTwoData, skuThreeData) {
        let unit = this.form.getFieldValue('unit')
        if(unit) {
          //计算多属性已经选择了几个
          let skuArr = []
          if(this.getNumByField('skuOne')) {
            skuArr.push(skuOneData)
          }
          if(this.getNumByField('skuTwo')) {
            skuArr.push(skuTwoData)
          }
          if(this.getNumByField('skuThree')) {
            skuArr.push(skuThreeData)
          }
          let skuArrOne = skuArr[0]
          let skuArrTwo = skuArr[1]
          let skuArrThree = skuArr[2]
          let count = this.getNumByField('skuOne') + this.getNumByField('skuTwo') + this.getNumByField('skuThree')
          let barCodeSku = []
          if(count === 1) {
            let skuArrOnly = []
            if(this.getNumByField('skuOne')) {
              skuArrOnly = skuOneData
            } else if(this.getNumByField('skuTwo')) {
              skuArrOnly = skuTwoData
            } else if(this.getNumByField('skuThree')) {
              skuArrOnly = skuThreeData
            }
            for (let i = 0; i < skuArrOnly.length; i++) {
              barCodeSku.push(skuArrOnly[i])
            }
          } else if(count === 2) {
            for (let i = 0; i < skuArrOne.length; i++) {
              for (let j = 0; j < skuArrTwo.length; j++) {
                barCodeSku.push(skuArrOne[i] + '/' + skuArrTwo[j])
              }
            }
          } else if(count === 3) {
            for (let i = 0; i < skuArrOne.length; i++) {
              for (let j = 0; j < skuArrTwo.length; j++) {
                for (let k = 0; k < skuArrThree.length; k++) {
                  barCodeSku.push(skuArrOne[i] + '/' + skuArrTwo[j] + '/' + skuArrThree[k])
                }
              }
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
          this.$message.warning('请填写单位（注意不要勾选多单位）');
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
        if(this.maxBarCodeInfo === '') {
          getMaxBarCode({}).then((res)=> {
            if (res && res.code === 200) {
              this.maxBarCodeInfo = res.data.barCode - 0
              this.maxBarCodeInfo = this.maxBarCodeInfo + 1
              target.setValues([{rowKey: row.id, values: {barCode: this.maxBarCodeInfo, commodityUnit: unit?unit:''}}])
            }
          })
        } else {
          this.maxBarCodeInfo = this.maxBarCodeInfo + 1
          target.setValues([{rowKey: row.id, values: {barCode: this.maxBarCodeInfo, commodityUnit: unit?unit:''}}])
        }
      },
      onDeleted(value) {
        this.meDeleteIdList = (value)
      },
      //单元值改变一个字符就触发一次
      onValueChange(event) {
        const { type, row, column, value, target } = event
        switch(column.key) {
          case "purchaseDecimal":
          case "commodityDecimal":
          case "wholesaleDecimal":
          case "lowDecimal":
            this.changeDecimalByValue(row)
            break;
        }
      },
      //修改商品明细中的价格触发计算
      changeDecimalByValue(row) {
        let unitArr = this.unitList
        let basicUnit = '', otherUnit = '', ratio = 1, otherUnitTwo = '', ratioTwo = 1, otherUnitThree = '', ratioThree = 1
        for (let i = 0; i < unitArr.length; i++) {
          if(unitArr[i].id === this.form.getFieldValue('unitId')) {
            basicUnit = unitArr[i].basicUnit
            otherUnit = unitArr[i].otherUnit
            ratio = unitArr[i].ratio
            if(unitArr[i].otherUnitTwo) {
              otherUnitTwo = unitArr[i].otherUnitTwo
              ratioTwo = unitArr[i].ratioTwo
            }
            if(unitArr[i].otherUnitThree) {
              otherUnitThree = unitArr[i].otherUnitThree
              ratioThree = unitArr[i].ratioThree
            }
          }
        }
        if(row.commodityUnit === basicUnit) {
          this.$refs.editableMeTable.getValues((error, values) => {
            let mArr = values, basicPurchaseDecimal='', basicCommodityDecimal='', basicWholesaleDecimal='', basicLowDecimal=''
            for (let i = 0; i < mArr.length; i++) {
              let mInfo = mArr[i]
              if(i===0) {
                basicPurchaseDecimal = mInfo.purchaseDecimal
                basicCommodityDecimal = mInfo.commodityDecimal
                basicWholesaleDecimal = mInfo.wholesaleDecimal
                basicLowDecimal = mInfo.lowDecimal
              } else {
                //副单位进行换算
                if(basicPurchaseDecimal) { mInfo.purchaseDecimal = (basicPurchaseDecimal*ratio).toFixed(2)}
                if(basicCommodityDecimal) { mInfo.commodityDecimal = (basicCommodityDecimal*ratio).toFixed(2)}
                if(basicWholesaleDecimal) { mInfo.wholesaleDecimal = (basicWholesaleDecimal*ratio).toFixed(2)}
                if(basicLowDecimal) { mInfo.lowDecimal = (basicLowDecimal*ratio).toFixed(2)}
                if(otherUnitTwo && i===2) {
                  if(basicPurchaseDecimal) { mInfo.purchaseDecimal = (basicPurchaseDecimal*ratioTwo).toFixed(2)}
                  if(basicCommodityDecimal) { mInfo.commodityDecimal = (basicCommodityDecimal*ratioTwo).toFixed(2)}
                  if(basicWholesaleDecimal) { mInfo.wholesaleDecimal = (basicWholesaleDecimal*ratioTwo).toFixed(2)}
                  if(basicLowDecimal) { mInfo.lowDecimal = (basicLowDecimal*ratioTwo).toFixed(2)}
                }
                if(otherUnitThree && i===3) {
                  if(basicPurchaseDecimal) { mInfo.purchaseDecimal = (basicPurchaseDecimal*ratioThree).toFixed(2)}
                  if(basicCommodityDecimal) { mInfo.commodityDecimal = (basicCommodityDecimal*ratioThree).toFixed(2)}
                  if(basicWholesaleDecimal) { mInfo.wholesaleDecimal = (basicWholesaleDecimal*ratioThree).toFixed(2)}
                  if(basicLowDecimal) { mInfo.lowDecimal = (basicLowDecimal*ratioThree).toFixed(2)}
                }
              }
            }
            this.meTable.dataSource = mArr
          })
        }
      },
      batchSetPrice(type) {
        if(this.manySkuSelected>0 || this.model.id){
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
        getMaterialAttributeNameList().then((res)=>{
          if(res) {
            this.materialAttributeList = res
          }
        })
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
      handleNameChange(e) {
        let that = this
        if(e.target.value) {
          if(this.setTimeFlag != null){
            clearTimeout(this.setTimeFlag)
          }
          this.setTimeFlag = setTimeout(()=>{
            changeNameToPinYin({name: e.target.value}).then((res) => {
              if (res && res.code === 200) {
                that.form.setFieldsValue({'mnemonic':res.data})
              } else {
                that.$message.warning(res.data)
              }
            })
          },500)
        } else {
          that.form.setFieldsValue({'mnemonic':''})
        }
      },
      onlyUnitOnChange(e) {
        if(e.target.value) {
          //单位有填写了之后则显示多属性的文本框
          this.manySkuStatus = true
        } else {
          this.manySkuStatus = false
        }
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
        let basicUnit = '', otherUnit = '', ratio = 1, otherUnitTwo = '', ratioTwo = 1, otherUnitThree = '', ratioThree = 1
        for (let i = 0; i < unitArr.length; i++) {
          if(unitArr[i].id === value) {
            basicUnit = unitArr[i].basicUnit
            otherUnit = unitArr[i].otherUnit
            ratio = unitArr[i].ratio
            if(unitArr[i].otherUnitTwo) {
              otherUnitTwo = unitArr[i].otherUnitTwo
              ratioTwo = unitArr[i].ratioTwo
            }
            if(unitArr[i].otherUnitThree) {
              otherUnitThree = unitArr[i].otherUnitThree
              ratioThree = unitArr[i].ratioThree
            }
          }
        }
        this.$refs.editableMeTable.getValues((error, values) => {
          let mArr = values, basicPurchaseDecimal='', basicCommodityDecimal='', basicWholesaleDecimal='', basicLowDecimal=''
          for (let i = 0; i < mArr.length; i++) {
            let mInfo = mArr[i]
            if(i===0) {
              mInfo.commodityUnit = basicUnit
              basicPurchaseDecimal = mInfo.purchaseDecimal
              basicCommodityDecimal = mInfo.commodityDecimal
              basicWholesaleDecimal = mInfo.wholesaleDecimal
              basicLowDecimal = mInfo.lowDecimal
            } else {
              //副单位进行换算
              mInfo.commodityUnit = otherUnit
              if(basicPurchaseDecimal) { mInfo.purchaseDecimal = (basicPurchaseDecimal*ratio).toFixed(2)}
              if(basicCommodityDecimal) { mInfo.commodityDecimal = (basicCommodityDecimal*ratio).toFixed(2)}
              if(basicWholesaleDecimal) { mInfo.wholesaleDecimal = (basicWholesaleDecimal*ratio).toFixed(2)}
              if(basicLowDecimal) { mInfo.lowDecimal = (basicLowDecimal*ratio).toFixed(2)}
              if(otherUnitTwo && i===2) {
                mInfo.commodityUnit = otherUnitTwo
                if(basicPurchaseDecimal) { mInfo.purchaseDecimal = (basicPurchaseDecimal*ratioTwo).toFixed(2)}
                if(basicCommodityDecimal) { mInfo.commodityDecimal = (basicCommodityDecimal*ratioTwo).toFixed(2)}
                if(basicWholesaleDecimal) { mInfo.wholesaleDecimal = (basicWholesaleDecimal*ratioTwo).toFixed(2)}
                if(basicLowDecimal) { mInfo.lowDecimal = (basicLowDecimal*ratioTwo).toFixed(2)}
              }
              if(otherUnitThree && i===3) {
                mInfo.commodityUnit = otherUnitThree
                if(basicPurchaseDecimal) { mInfo.purchaseDecimal = (basicPurchaseDecimal*ratioThree).toFixed(2)}
                if(basicCommodityDecimal) { mInfo.commodityDecimal = (basicCommodityDecimal*ratioThree).toFixed(2)}
                if(basicWholesaleDecimal) { mInfo.wholesaleDecimal = (basicWholesaleDecimal*ratioThree).toFixed(2)}
                if(basicLowDecimal) { mInfo.lowDecimal = (basicLowDecimal*ratioThree).toFixed(2)}
              }
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
    min-width: 1200px;
  }
  .tag-info {
    font-size:14px;
    height:32px;
    line-height:32px;
    width:100%;
    padding: 0px 11px;
    color: #bbb;
    background-color: #ffffff;
  }
</style>
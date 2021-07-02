<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
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
            <a-row class="form-row" :gutter="24">
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="名称">
                  <a-input placeholder="请输入名称" v-decorator.trim="[ 'name', validatorRules.name]" />
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="规格">
                  <a-input placeholder="请输入规格" v-decorator.trim="[ 'standard' ]" />
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单位">
                  <a-row class="form-row" :gutter="24">
                    <a-col :lg="13" :md="13" :sm="24">
                      <a-input placeholder="输入单位" :hidden="unitStatus" v-decorator.trim="[ 'unit' ]" />
                      <a-select :value="unitList" placeholder="选择单位" v-decorator="[ 'unitId' ]"
                                :hidden="manyUnitStatus" :dropdownMatchSelectWidth="false">
                        <a-select-option v-for="(item,index) in unitList"
                          :key="index" :value="item.id">
                          {{ item.name }}
                        </a-select-option>
                      </a-select>
                    </a-col>
                    <a-col :lg="11" :md="11" :sm="24">
                      <a-checkbox :checked="unitChecked" @change="unitOnChange">多单位</a-checkbox>
                    </a-col>
                  </a-row>
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="型号">
                  <a-input placeholder="请输入型号" v-decorator.trim="[ 'model' ]" />
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="颜色">
                  <a-input placeholder="请输入颜色" v-decorator.trim="[ 'color' ]" />
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="类别">
                  <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                   :treeData="categoryTree" v-decorator="[ 'categoryId' ]" placeholder="请选择类别">
                  </a-tree-select>
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号">
                  <a-select placeholder="请选择序列号" v-decorator="[ 'enableSerialNumber' ]">
                    <a-select-option value="1">有</a-select-option>
                    <a-select-option value="0">无</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="安全存量">
                  <a-input placeholder="请输入安全存量" v-decorator.trim="[ 'safetyStock' ]" />
                </a-form-item>
              </a-col>
              <a-col :lg="8" :md="12" :sm="24">
              </a-col>
            </a-row>
            <j-editable-table
              ref="editableMeTable"
              :loading="meTable.loading"
              :columns="meTable.columns"
              :dataSource="meTable.dataSource"
              :maxHeight="300"
              :rowNumber="true"
              :rowSelection="true"
              :actionButton="true"/>
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
          <a-tab-pane key="3" tab="初始库存" forceRender>
            <j-editable-table
              ref="editableDepotTable"
              :loading="depotTable.loading"
              :columns="depotTable.columns"
              :dataSource="depotTable.dataSource"
              :maxHeight="300"
              :rowNumber="true"
              :rowSelection="false"
              :actionButton="false"/>
          </a-tab-pane>
          <a-tab-pane key="4" tab="图片信息" forceRender>
            <a-row class="form-row" :gutter="24">
              <a-col :lg="12" :md="12" :sm="24">
                <a-form-item :labelCol="{xs: { span: 24 },sm: { span: 4 }}" :wrapperCol="{xs: { span: 24 },sm: { span: 20 }}" label="图片">
                  <j-image-upload v-model="fileList" bizPath="material" isMultiple></j-image-upload>
                </a-form-item>
              </a-col>
              <a-col :lg="12" :md="12" :sm="24"></a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { FormTypes, VALIDATE_NO_PASSED, getRefPromise, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import {queryMaterialCategoryTreeList,checkMaterial,checkMaterialBarCode} from '@/api/api'
  import { httpAction, getAction } from '@/api/manage'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDate from '@/components/jeecg/JDate'
  import Vue from 'vue'
  export default {
    name: "MaterialModal",
    components: {
      JImageUpload,
      JDate,
      JEditableTable
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
              title: '条码', key: 'barCode', width: '30%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' },
                { pattern: /^[1-9]\d*$/, message: '请输入零以上的正整数' },
                { pattern: /^\d{4,13}$/, message: '4到13位数字' },
                { handler: this.validateBarCode}]
            },
            {
              title: '单位', key: 'commodityUnit', width: '12%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }]
            },
            {
              title: '采购价', key: 'purchaseDecimal', width: '12%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '零售价', key: 'commodityDecimal', width: '12%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '销售价', key: 'wholesaleDecimal', width: '12%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
            },
            {
              title: '最低售价', key: 'lowDecimal', width: '12%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
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
              title: '库存数量', key: 'initStock', width: '15%', type: FormTypes.input, defaultValue: '', placeholder: '请输入${title}'
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
        // 默认新增一条数据
        this.getAllTable().then(editableTables => {
          editableTables[0].add()
        })
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.activeKey = '1'
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
            'categoryId','enableSerialNumber','safetyStock','remark','mfrs','otherField1','otherField2','otherField3'))
        });
        // 加载子表数据
        if (this.model.id) {
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
          this.requestMeTableData(this.url.materialsExtendList, params, this.meTable)
          this.requestDepotTableData(this.url.depotWithStock, { mId: this.model.id }, this.depotTable)
        } else {
          this.requestDepotTableData(this.url.depotWithStock, { mId: 0 }, this.depotTable)
        }
      },
      /** 查询条码tab的数据 */
      requestMeTableData(url, params, tab) {
        tab.loading = true
        getAction(url, params).then(res => {
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
          id: row.id.length == 20?0: row.id
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
        getAction('/unit/list', params).then((res) => {
          if(res){
            that.unitList = res.data.rows;
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
      }
    }
  }
</script>
<style scoped>

</style>
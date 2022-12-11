<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="1000"
      :visible="visible"
      :confirm-loading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'89px','left':'151px'}"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      wrapClassName="ant-modal-cust-warp"
      style="top:20%;height: 45%;overflow-y: hidden">
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-row class="form-row" :gutter="24">
            <a-col :md="8" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="颜色">
                <a-input placeholder="请输入颜色" v-decorator.trim="[ 'color' ]" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="基础重量">
                <a-input-number style="width: 100%" placeholder="请输入基础重量(kg)" v-decorator.trim="[ 'weight' ]" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="保质期">
                <a-input-number style="width: 100%" placeholder="请输入保质期(天)" v-decorator.trim="[ 'expiryNum' ]" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="类别">
                <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
                               :treeData="categoryTree" v-decorator="[ 'categoryId' ]" placeholder="请选择类别">
                </a-tree-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号">
                <a-select placeholder="有无序列号" v-decorator="[ 'enableSerialNumber' ]">
                  <a-select-option value="1">有</a-select-option>
                  <a-select-option value="0">无</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="批号">
                <a-select placeholder="有无批号" v-decorator="[ 'enableBatchNumber' ]">
                  <a-select-option value="1">有</a-select-option>
                  <a-select-option value="0">无</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
                <a-textarea :rows="1" placeholder="请输入备注" v-decorator="[ 'remark' ]" style="margin-top:8px;"/>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>

<script>
  import {queryMaterialCategoryTreeList, batchUpdateMaterial} from '@/api/api'
  export default {
    name: 'BatchSetInfoModal',
    data () {
      return {
        title:"批量编辑",
        visible: false,
        categoryTree: [],
        materialIds: '',
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
      }
    },
    created () {
    },
    methods: {
      loadTreeData(){
        let that = this
        let params = {}
        params.id=''
        queryMaterialCategoryTreeList(params).then((res)=>{
          if(res){
            that.categoryTree = [];
            for (let i = 0; i < res.length; i++) {
              let temp = res[i];
              that.categoryTree.push(temp)
            }
          }
        })
      },
      edit (ids) {
        this.materialIds = ids
        this.form.resetFields()
        this.model = Object.assign({}, '')
        this.loadTreeData()
        this.visible = true
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true
            let formData = Object.assign(this.model, values)
            if(JSON.stringify(formData) === '{}') {
              that.$message.warning('抱歉，请输入要批量编辑的内容！')
              that.confirmLoading = false
              return
            }
            if(formData.enableSerialNumber === '1' && formData.enableBatchNumber === '1' ) {
              that.$message.warning('抱歉，序列号和批号只能选择一项！')
              that.confirmLoading = false
              return
            }
            let paramObj = {
              ids: this.materialIds,
              material: JSON.stringify(formData)
            }
            batchUpdateMaterial(paramObj).then((res)=>{
              if(res.code === 200){
                that.$emit('ok')
              }else{
                that.$message.warning(res.data.message)
              }
            }).finally(() => {
              that.confirmLoading = false
              that.close()
            })
          }
        })
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>

<style scoped>

</style>
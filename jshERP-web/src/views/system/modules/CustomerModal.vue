<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="1200"
      :visible="visible"
      :confirmLoading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="取消"
      okText="保存"
      style="top:10%;height: 80%;">
      <template slot="footer">
        <a-button key="back" v-if="isReadOnly" @click="handleCancel">
          取消
        </a-button>
      </template>
      <a-spin :spinning="confirmLoading">
        <a-form :form="form" id="customerModal" style="max-width: 800px; margin: 0 auto;">
          <a-row class="form-row" :gutter="24">
            <a-col :span="24">
              <a-form-item :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }" label="名称">
                <a-input placeholder="请输入名称" v-decorator.trim="[ 'supplier', validatorRules.supplier]" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="24">
              <a-form-item :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }" label="电话">
                <a-input placeholder="请输入电话" v-decorator.trim="[ 'telephone', validatorRules.telephone ]" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="24">
              <a-form-item :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }" label="地址">
                <a-input placeholder="请输入地址" v-decorator.trim="[ 'address' ]" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="24">
            <a-col :span="24">
              <a-form-item :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }" label="备注">
                <a-textarea :rows="2" placeholder="请输入备注" v-decorator.trim="[ 'description' ]" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import {addSupplier,editSupplier,checkSupplier } from '@/api/api'
  import {autoJumpNextInput} from "@/utils/util"
  import {mixinDevice} from '@/utils/mixin'
  export default {
    name: "CustomerModal",
    mixins: [mixinDevice],
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        isReadOnly: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 4 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          supplier:{
            rules: [
              { required: true, message: '请输入名称!' },
              { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' },
              { validator: this.validateSupplierName}
            ]
          },
          telephone: {
            rules: [
              { required: false },
              { pattern: /^((0\d{2,3}-\d{7,8})|(1[3-9]\d{9}))$/, message: '请输入正确的电话格式!' }
            ]
          }
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'supplier', 'contacts', 'telephone', 'email',
            'phoneNum', 'fax', 'beginNeedGet', 'allNeedGet', 'taxNum', 'taxRate',
            'bankName', 'accountNumber', 'address', 'sort', 'description'))
          autoJumpNextInput('customerModal')
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            if(this.model.beginNeedGet && this.model.beginNeedPay) {
              that.$message.warn("期初应收和期初应付不能同时输入");
              that.confirmLoading = false;
              return;
            }
            formData.type = "客户";
            let obj;
            if(!this.model.id){
              obj=addSupplier(formData);
            }else{
              obj=editSupplier(formData);
            }
            obj.then((res)=>{
              if(res.code === 200){
                that.$emit('ok');
              }else{
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel () {
        this.close()
      },
      validateSupplierName(rule, value, callback){
        let params = {
          name: value,
          type: '客户',
          id: this.model.id?this.model.id:0
        };
        checkSupplier(params).then((res)=>{
          if(res && res.code===200) {
            if(!res.data.status){
              callback();
            } else {
              callback("名称已经存在");
            }
          } else {
            callback(res.data);
          }
        });
      }
    }
  }
</script>
<style scoped>

</style>
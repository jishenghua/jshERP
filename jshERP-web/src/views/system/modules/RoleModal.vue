<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="800"
      :visible="visible"
      :confirmLoading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭"
      style="top:15%;height: 60%;">
      <template slot="footer">
        <a-button key="back" v-if="isReadOnly" @click="handleCancel">
          关闭
        </a-button>
      </template>
      <a-spin :spinning="confirmLoading">
        <a-form :form="form" id="roleModal">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="角色名称">
            <a-input placeholder="请输入角色名称" v-decorator.trim="[ 'name', validatorRules.name]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="数据类型">
            <a-select placeholder="请选择数据类型" v-decorator="[ 'type', validatorRules.type]" style="width:94%">
              <a-select-option value="全部数据">全部数据</a-select-option>
              <a-select-option value="本机构数据">本机构数据</a-select-option>
              <a-select-option value="个人数据">个人数据</a-select-option>
            </a-select>
            <a-tooltip title="1、全部数据-该角色对应的用户可以看到全部单据；2、本机构数据-该角色对应的用户可以看到自己所在机构的全部单据；
                3、个人数据-该角色对应的用户只可以看到自己的单据。单据是指采购入库、销售出库等">
              <a-icon type="question-circle" style="width:6%; padding-left: 5px; font-size: 18px;" />
            </a-tooltip>
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="价格屏蔽">
            <j-select-multiple style="width:94%" placeholder="请选择价格屏蔽" v-model="priceLimitList.value" :options="priceLimitList.options"/>
            <a-tooltip title="价格屏蔽支持多选，主要用于控制首页界面和物料的价格屏蔽">
              <a-icon type="question-circle" style="width:6%; padding-left: 5px; font-size: 18px;" />
            </a-tooltip>
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
            <a-textarea :rows="1" placeholder="请输入备注" v-decorator="[ 'description', validatorRules.description ]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="排序">
            <a-input placeholder="请输入排序" v-decorator.trim="[ 'sort' ]" />
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import JSelectMultiple from '@/components/jeecg/JSelectMultiple'
  import {addRole,editRole,checkRole } from '@/api/api'
  import {autoJumpNextInput} from "@/utils/util"
  import {mixinDevice} from '@/utils/mixin'
  export default {
    name: "RoleModal",
    mixins: [mixinDevice],
    components: {
      JSelectMultiple
    },
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        isReadOnly: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        priceLimitList: {
          options: [
            { 'value': '1', 'text': '屏蔽首页采购价'},
            { 'value': '2', 'text': '屏蔽首页零售价'},
            { 'value': '3', 'text': '屏蔽首页销售价'},
            { 'value': '4', 'text': '屏蔽单据采购价'},
            { 'value': '5', 'text': '屏蔽单据零售价'},
            { 'value': '6', 'text': '屏蔽单据销售价'}
          ],
          value: ''
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          name:{
            rules: [
              { required: true, message: '请输入角色名称!' },
              { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' },
              { validator: this.validateRoleName}
            ]
          },
          type:{
            rules: [
              { required: true, message: '请选择数据类型!' }
            ]
          },
          description:{
            rules: [
              { min: 0, max: 126, message: '长度不超过 126 个字符', trigger: 'blur' }
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
        this.priceLimitList.value = this.model.priceLimit
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name', 'type', 'sort', 'description'))
          autoJumpNextInput('roleModal')
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
            formData.priceLimit = this.priceLimitList.value
            let obj;
            if(!this.model.id){
              obj=addRole(formData);
            }else{
              obj=editRole(formData);
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
      validateRoleName(rule, value, callback){
        let params = {
          name: value,
          id: this.model.id?this.model.id:0
        };
        checkRole(params).then((res)=>{
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
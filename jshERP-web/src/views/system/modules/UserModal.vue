<template>
  <a-modal
    :title="title"
    :width="800"
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
        <a-form-item label="登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input placeholder="请输入登录名称" v-decorator.trim="[ 'loginName', validatorRules.loginName]" :readOnly="!!model.id"
           suffix="初始密码：123456" />
        </a-form-item>
        <a-form-item label="用户姓名" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入用户姓名" v-decorator.trim="[ 'username', validatorRules.username]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="角色">
          <a-select v-if="model.roleName!='租户'" placeholder="选择角色" v-decorator="[ 'roleId' ]" :dropdownMatchSelectWidth="false">
            <a-select-option v-for="(item,index) in roleList" :key="index" :value="item.id">
              {{ item.text }}
            </a-select-option>
          </a-select>
          <a-col v-if="model.roleName=='租户'"><a-row>租户</a-row></a-col>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构">
          <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" allow-clear
             :treeData="orgaTree" v-decorator="[ 'orgaId' ]" placeholder="请选择机构">
          </a-tree-select>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="职位">
          <a-input placeholder="请输入职位" v-decorator.trim="[ 'position' ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="电话号码">
          <a-input placeholder="请输入电话号码" v-decorator.trim="[ 'phonenum' ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="电子邮箱">
          <a-input placeholder="请输入电子邮箱" v-decorator.trim="[ 'email' ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="排序">
          <a-input placeholder="请输入排序" v-decorator.trim="[ 'userBlngOrgaDsplSeq' ]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="描述">
          <a-textarea :rows="1" placeholder="请输入描述" v-decorator="[ 'description' ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import Vue from 'vue'
  import JSelectPosition from '@/components/jeecgbiz/JSelectPosition'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getAction } from '@/api/manage'
  import {addUser,editUser,queryOrganizationTreeList,findUserRole} from '@/api/api'
  import { disabledAuthFilter } from "@/utils/authFilter"
  import {duplicateCheck } from '@/api/api'
  import JImageUpload from '../../../components/jeecg/JImageUpload'
  export default {
    name: "UserModal",
    components: {
      JImageUpload,
      JSelectPosition
    },
    data () {
      return {
        title:"操作",
        visible: false,
        modalWidth:800,
        drawerWidth:700,
        orgaTree: [],
        roleList: [],
        userId:"", //保存用户id
        isReadOnly: false,
        disableSubmit:false,
        dateFormat:"YYYY-MM-DD",
        validatorRules:{
          loginName:{
            rules: [{
              required: true, message: '请输入登录名称!'
            }]
          },
          username:{
            rules: [{
              required: true, message: '请输入用户姓名!'
            }]
          }
        },
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        uploadLoading:false,
        confirmLoading: false,
        headers:{},
        form:this.$form.createForm(this)
      }
    },
    created () {
      this.loadOrgaData()
      this.loadRoleData()
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        let that = this;
        that.form.resetFields();
        that.userId = record.id;
        that.visible = true;
        that.model = Object.assign({}, record);
        that.$nextTick(() => {
          that.form.setFieldsValue(pick(this.model,'loginName','username','roleId','orgaId','position',
            'phonenum','email','userBlngOrgaDsplSeq','description'))
        });
      },
      close() {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            let obj;
            if(!this.model.id){
              formData.id = this.userId;
              obj=addUser(formData);
            }else{
              obj=editUser(formData);
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
      handleCancel() {
        this.close()
      },
      loadOrgaData(){
        let that = this;
        let params = {};
        params.id='';
        queryOrganizationTreeList(params).then((res)=>{
          if(res){
            that.orgaTree = res
          }
        })
      },
      loadRoleData(){
        let that = this;
        let params = {
          'UBType': 'UserRole',
          'UBKeyId': ''
        };
        findUserRole(params).then((res)=>{
          if(res){
            that.roleList = res
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
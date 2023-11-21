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
      cancelText="取消"
      okText="保存"
      style="top:5%;height: 85%;">
      <template slot="footer">
        <a-button key="back" v-if="isReadOnly" @click="handleCancel">
          取消
        </a-button>
      </template>
      <a-spin :spinning="confirmLoading">
        <a-form :form="form" id="userModal">
          <a-form-item label="登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入登录名称" v-decorator.trim="[ 'loginName', validatorRules.loginName]" :readOnly="!!model.id"
             suffix="初始密码：123456" />
          </a-form-item>
          <a-form-item label="用户姓名" :labelCol="labelCol" :wrapperCol="wrapperCol" >
            <a-input placeholder="请输入用户姓名" v-decorator.trim="[ 'username', validatorRules.username]" />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="角色">
            <a-select v-if="model.roleName!='租户'" placeholder="选择角色" v-decorator="[ 'roleId', validatorRules.roleId]" :dropdownMatchSelectWidth="false">
              <a-select-option v-for="(item,index) in roleList" :key="index" :value="item.id">
                {{ item.name }}
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
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否经理">
            <a-select placeholder="请选择是否经理" v-decorator="[ 'leaderFlag' ]">
              <a-select-option value="1">是</a-select-option>
              <a-select-option value="0">否</a-select-option>
            </a-select>
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
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
            <a-textarea :rows="2" placeholder="请输入备注" v-decorator="[ 'description' ]" />
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import Vue from 'vue'
  import JSelectPosition from '@/components/jeecgbiz/JSelectPosition'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getAction } from '@/api/manage'
  import {addUser,editUser,queryOrganizationTreeList,roleAllList} from '@/api/api'
  import { disabledAuthFilter } from "@/utils/authFilter"
  import {autoJumpNextInput} from "@/utils/util"
  import {mixinDevice} from '@/utils/mixin'
  import JImageUpload from '../../../components/jeecg/JImageUpload'
  export default {
    name: "UserModal",
    mixins: [mixinDevice],
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
          },
          roleId:{
            rules: [{
              required: true, message: '请选择角色!'
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
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.loadOrgaData()
        this.loadRoleData()
        this.form.resetFields();
        this.userId = record.id;
        this.visible = true;
        this.model = Object.assign({}, record);
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'loginName','username','roleId','orgaId','position','leaderFlag',
            'phonenum','email','userBlngOrgaDsplSeq','description'))
          autoJumpNextInput('userModal')
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
                that.close();
              }else{
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
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
        roleAllList({}).then((res)=>{
          if(res){
            this.roleList = res
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
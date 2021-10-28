<template>
  <a-modal
    :title="title"
    :width="800"
    :ok=false
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form" id="organizationModal">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="名称">
          <a-input placeholder="请输入名称" v-decorator="['orgAbr', validatorRules.orgAbr ]"/>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="编号">
          <a-input placeholder="请输入编号" v-decorator="['orgNo', validatorRules.orgNo ]"/>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级机构">
          <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
                         allow-clear treeDefaultExpandAll="true"
               :treeData="departTree" v-model="model.parentId" placeholder="请选择上级机构">
          </a-tree-select>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="排序">
          <a-input-number v-decorator="[ 'sort' ]"/>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
          <a-textarea placeholder="请输入备注":rows="2" v-decorator.trim="[ 'remark' ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import { queryOrganizationTreeList, checkOrganization } from '@/api/api'
  import {autoJumpNextInput} from "@/utils/util"
  import pick from 'lodash.pick'
  import ATextarea from 'ant-design-vue/es/input/TextArea'
  export default {
    name: "OrganizationModal",
    components: { ATextarea },
    data () {
      return {
        departTree:[],
        orgTypeData:[],
        phoneWarning:'',
        departName:"",
        title:"操作",
        visible: false,
        disableSubmit:false,
        model: {},
        menuhidden:false,
        menuusing:true,
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
        validatorRules:{
          orgAbr: {
            rules: [
              { required: true, message: '请输入名称!'},
              { validator: this.validateName}
            ]
          },
          orgNo: {rules: [{required: true, message: '请输入编码!'}]}
        },
        url: {
          add: "/organization/add",
        }
      }
    },
    created () {
    },
    methods: {
      loadTreeData(){
        var that = this;
        let params = {};
        params.id='';
        queryOrganizationTreeList(params).then((res)=>{
          if(res){
            that.departTree = [];
            for (let i = 0; i < res.length; i++) {
              let temp = res[i];
              that.departTree.push(temp);
            }
          }
        })
      },
      add () {
        this.edit();
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, {});
        this.visible = true;
        this.loadTreeData();
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(record, 'orgAbr', 'orgNo', 'parentId', 'sort', 'remark'))
          autoJumpNextInput('organizationModal')
        });
      },
      close () {
        this.$emit('close');
        this.disableSubmit = false;
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            //时间格式化
            console.log(formData)
            httpAction(this.url.add,formData,"post").then((res)=>{
              if(res.code == 200){
                that.$message.success(res.data.message);
                that.loadTreeData();
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
      validateName(rule, value, callback){
        let params = {
          name: value,
          id: this.model.id?this.model.id:0
        };
        checkOrganization(params).then((res)=>{
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
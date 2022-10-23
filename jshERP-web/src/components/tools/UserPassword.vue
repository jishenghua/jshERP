<template>
  <a-modal
    :title="title"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:25%;height: 50%;overflow-y: hidden">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="旧密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input type="password" placeholder="请输入旧密码" v-decorator="[ 'oldpassword', validatorRules.oldpassword]" />
        </a-form-item>
        <a-form-item label="新密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input type="password" placeholder="新密码至少6位，区分大小写" v-decorator="[ 'password', validatorRules.password]" />
        </a-form-item>
        <a-form-item label="确认新密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input type="password"  placeholder="请确认新密码" v-decorator="[ 'confirmPassword', validatorRules.confirmPassword]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { putAction } from '@/api/manage'
  import md5 from 'md5'
  export default {
    name: "UserPassword",
    data () {
      return {
        title:"修改密码",
        modalWidth:800,
        visible: false,
        confirmLoading: false,
        validatorRules:{
          oldpassword:{
            rules: [{
              required: true, message: '请输入旧密码!',
            }],
          },
          password:{
            rules: [
              { required: true, message: '请输入新密码!'},
              { validator: this.handlePassword }
            ],
            validateTrigger: ['change', 'blur'],
            validateFirst: true
          },
          confirmPassword:{
            rules: [
              { required: true, message: '请确认新密码!' },
              { validator: this.handleConfirmPassword }
            ],
            validateTrigger: ['change', 'blur'],
            validateFirst: true
          }
        },
        confirmDirty:false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        form:this.$form.createForm(this),
        url: "/user/updatePwd",
        userId:"",
      }
    },
    methods: {
      show(userId){
        if(!userId){
          this.$message.warning("当前系统无登陆用户!");
        }else{
          this.userId = userId
          this.form.resetFields();
          this.visible = true;
        }
      },
      handleCancel () {
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true
            values.oldpassword = md5(values.oldpassword)
            values.password = md5(values.password)
            let params = Object.assign({userId:this.userId},values)
            console.log("修改密码提交数据",params)
            putAction(this.url,params).then((res)=>{
              if(res.code === 200){
                if(res.data.status === 2 || res.data.status === 3) {
                  that.$message.warning(res.data.message)
                } else {
                  that.$message.success(res.data.message)
                  that.close()
                }
              }else{
                that.$message.warning(res.data.message)
              }
            }).finally(() => {
              that.confirmLoading = false
            })
          }
        })
      },
      handlePassword(rule, value, callback) {
        let oldpassword = this.form.getFieldValue('oldpassword')
        if(oldpassword === value) {
          callback(new Error('新密码和旧密码不能相同!'))
        }
        let reg = /^(?=.*[a-z])(?=.*\d).{6,}$/;
        if (!reg.test(value)) {
          callback(new Error('密码由6位数字、小写字母组成!'))
        }
        callback()
      },
      handleConfirmPassword(rule, value, callback) {
        let password = this.form.getFieldValue('password')
        if (value === undefined) {
          callback(new Error('请输入密码!'))
        }
        if (value && password && value.trim() !== password.trim()) {
          callback(new Error('两次密码不一致!'))
        }
        callback()
      }
    }
  }
</script>

<style scoped>

</style>


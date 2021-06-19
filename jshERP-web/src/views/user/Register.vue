<template>
  <div class="main user-layout-register">
    <a-form ref="formRegister" :autoFormCreate="(form)=>{this.form = form}" id="formRegister">
      <a-form-item
        fieldDecoratorId="username"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '用户名不能为空'}], validateTrigger: ['change', 'blur'], validateFirst: true}">
        <a-input size="large" type="text" autocomplete="false" placeholder="请输入用户名"></a-input>
      </a-form-item>

      <a-popover placement="rightTop" trigger="click" :visible="state.passwordLevelChecked">
        <template slot="content">
          <div :style="{ width: '240px' }">
            <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span></div>
            <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor "/>
            <div style="margin-top: 10px;">
              <span>请至少输入 8 个字符。请不要使用容易被猜到的密码。</span>
            </div>
          </div>
        </template>
        <a-form-item
          fieldDecoratorId="password"
          :fieldDecoratorOptions="{rules: [{ required: false}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur'], validateFirst: true}">
          <a-input size="large" type="password" @click="handlePasswordInputClick" autocomplete="false" placeholder="至少8位密码，区分大小写"></a-input>
        </a-form-item>
      </a-popover>

      <a-form-item
        fieldDecoratorId="password2"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '至少8位密码，区分大小写' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur'], validateFirst: true}">

        <a-input size="large" type="password" autocomplete="false" placeholder="确认密码"></a-input>
      </a-form-item>

      <a-row :gutter="0">
        <a-col :span="16">
          <a-form-item fieldDecoratorId="inputCode">
            <!--20200510 cfm: 为方便测试，不输入验证码可： default-value="xxxx"-->
            <a-input
              size="large"
              type="text"
              default-value=""
              @change="inputCodeChange"
              placeholder="请输入验证码">
              <a-icon slot="prefix" type="smile" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="8" style="text-align: right">
          <img v-if="requestCodeSuccess" style="margin-top: 2px;" :src="randCodeImage" @click="handleChangeCheckCode"/>
          <img v-else style="margin-top: 2px;" src="../../assets/checkcode.png" @click="handleChangeCheckCode"/>
        </a-col>
      </a-row>

      <a-form-item>
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="register-button"
          :loading="registerBtn"
          @click.stop.prevent="handleSubmit"
          :disabled="registerBtn">注册
        </a-button>
        <router-link class="login" :to="{ name: 'login' }">使用已有账户登录</router-link>
      </a-form-item>

      <div class="login-copyright">
        <a-row>
          <a-col>
            © 2015-2030 {{systemTitle}} - Powered By
            <a style="color:#00458a;" :href="systemUrl" target="_blank">官方网站</a>
          </a-col>
        </a-row>
      </div>
    </a-form>
  </div>
</template>

<script>
  import {mixinDevice} from '@/utils/mixin.js'
  import {getSmsCaptcha} from '@/api/login'
  import {getAction, postAction} from '@/api/manage'
  import {checkOnlyUser} from '@/api/api'

  const levelNames = {
    0: '低',
    1: '低',
    2: '中',
    3: '强'
  }
  const levelClass = {
    0: 'error',
    1: 'error',
    2: 'warning',
    3: 'success'
  }
  const levelColor = {
    0: '#ff0000',
    1: '#ff0000',
    2: '#ff7e05',
    3: '#52c41a',
  }
  export default {
    name: "Register",
    components: {},
    mixins: [mixinDevice],
    data() {
      return {
        systemTitle: window.SYS_TITLE,
        systemUrl: window.SYS_URL,
        form: null,
        randCode:'',
        randCodeImage:'',
        requestCodeSuccess:false,
        state: {
          time: 60,
          smsSendBtn: false,
          passwordLevel: 0,
          passwordLevelChecked: false,
          percent: 10,
          progressColor: '#FF0000'
        },
        registerBtn: false
      }
    },
    computed: {
      passwordLevelClass() {
        return levelClass[this.state.passwordLevel]
      },
      passwordLevelName() {
        return levelNames[this.state.passwordLevel]
      },
      passwordLevelColor() {
        return levelColor[this.state.passwordLevel]
      }
    },
    created () {
      this.handleChangeCheckCode();
    },
    methods: {
      handleChangeCheckCode(){
        this.currdatetime = new Date().getTime();
        getAction(`/user/randomImage/${this.currdatetime}`).then(res=>{
          if(res.code == 200){
            this.randCode = res.data.codeNum;
            this.randCodeImage = res.data.base64;
            this.requestCodeSuccess=true
          }else{
            this.$message.error(res.data)
            this.requestCodeSuccess=false
          }
        }).catch(()=>{
          this.requestCodeSuccess=false
        })
      },
      handlePasswordLevel(rule, value, callback) {

        let level = 0
        let reg = /^(?=.*[a-z])(?=.*\d).{8,}$/;
        if (!reg.test(value)) {
          callback(new Error('密码由8位数字、小写字母组成!'))
        }
        // 判断这个字符串中有没有数字
        if (/[0-9]/.test(value)) {
          level++
        }
        // 判断字符串中有没有字母
        if (/[a-zA-Z]/.test(value)) {
          level++
        }
        // 判断字符串中有没有特殊符号
        if (/[^0-9a-zA-Z_]/.test(value)) {
          level++
        }
        this.state.passwordLevel = level
        this.state.percent = level * 30
        if (level >= 2) {
          if (level >= 3) {
            this.state.percent = 100
          }
          callback()
        } else {
          if (level === 0) {
            this.state.percent = 10
          }
          callback(new Error('密码强度不够'))
        }
      },

      handlePasswordCheck(rule, value, callback) {
        let password = this.form.getFieldValue('password')
        //console.log('value', value)
        if (value === undefined) {
          callback(new Error('请输入密码'))
        }
        if (value && password && value.trim() !== password.trim()) {
          callback(new Error('两次密码不一致'))
        }
        callback()
      },

      handlePhoneCheck(rule, value, callback) {
        var params = {
          phone: value,
        };
        checkOnlyUser(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback("手机号已存在!")
          }
        })
      },

      handlePasswordInputClick() {
        if (!this.isMobile()) {
          this.state.passwordLevelChecked = true
          return;
        }
        this.state.passwordLevelChecked = false
      },

      handleSubmit() {
        this.form.validateFields((err, values) => {
          if (!err) {
            if(values.inputCode == this.randCode) {
              let register = {
                loginName: values.username,
                password: values.password
              };
              postAction("/user/registerUser", register).then((res) => {
                if(res.code === 200){
                  this.$notification.success({
                    message: '提示',
                    description: "注册成功，请进行登录！",
                    duration: 2
                  });
                  let that = this;
                  setTimeout(function () {
                    that.form.resetFields();
                  },2000);
                } else {
                  this.$notification['error']({
                    message: "提示",
                    description: "注册失败",
                    duration: 2
                  });
                }
              })
            } else {
              this.$notification['error']({
                message: "提示",
                description: "验证码错误",
                duration: 2
              });
            }
          }
        })
      },
      registerFailed(message) {
        this.$notification['error']({
          message: "注册失败",
          description: message,
          duration: 2,
        });

      },
      requestFailed(err) {
        this.$notification['error']({
          message: '错误',
          description: ((err.response || {}).data || {}).message || "请求出现错误，请稍后再试",
          duration: 4,
        });
        this.registerBtn = false;
      },
    },
    watch: {
      'state.passwordLevel'(val) {
        console.log(val)

      }
    }
  }
</script>
<style lang="less">
  .user-register {

    &.error {
      color: #ff0000;
    }

    &.warning {
      color: #ff7e05;
    }

    &.success {
      color: #52c41a;
    }

  }

  .user-layout-register {
    .ant-input-group-addon:first-child {
      background-color: #fff;
    }
  }
</style>
<style lang="less" scoped>
  .user-layout-register {

    & > h3 {
      font-size: 16px;
      margin-bottom: 20px;
    }

    .getCaptcha {
      display: block;
      width: 100%;
      height: 40px;
    }

    .register-button {
      width: 50%;
    }

    .login {
      float: right;
      line-height: 40px;
    }
  }
  .login-copyright {
    text-align: center;
    margin-top: 20px
  }
  .login-copyright, .login-copyright a {
    color: #666
  }
</style>
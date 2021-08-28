<!-- b y 7 5 2 7  1 8 9 2 0 -->
<template>
  <div class="main">
    <a-form :form="form" class="user-layout-login" ref="formLogin" id="formLogin">
      <a-form-item>
        <a-input
          size="large"
          v-decorator="['loginName',{initialValue:'', rules: validatorRules.loginName.rules}]"
          type="text"
          placeholder="请输入用户名">
          <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input>
      </a-form-item>

      <a-form-item>
        <a-input
          v-decorator="['password',{initialValue:'', rules: validatorRules.password.rules}]"
          size="large"
          type="password"
          autocomplete="false"
          placeholder="密码">
          <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input>
      </a-form-item>

      <a-form-item>
        <a-checkbox v-decorator="['rememberMe', {initialValue: true, valuePropName: 'checked'}]" >自动登陆</a-checkbox>
        <router-link :to="{ name: 'register'}" class="forge-password" style="float: right;margin-right: 10px;" >
          注册租户
        </router-link>
      </a-form-item>

      <a-form-item style="margin-top:24px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="loginBtn"
          @click.stop.prevent="handleSubmit"
          :disabled="loginBtn">确定
        </a-button>
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
<!-- BY cao_yu_li -->
<script>
  import md5 from "md5"
  import api from '@/api'
  import TwoStepCaptcha from '@/components/tools/TwoStepCaptcha'
  import { mapActions } from "vuex"
  import { timeFix } from "@/utils/util"
  import Vue from 'vue'
  import { ACCESS_TOKEN ,ENCRYPTED_STRING} from "@/store/mutation-types"
  import { putAction,postAction,getAction } from '@/api/manage'
  import { encryption , getEncryptedString } from '@/utils/encryption/aesEncrypt'
  import store from '@/store/'
  import { USER_INFO } from "@/store/mutation-types"

  export default {
    components: {
      TwoStepCaptcha
    },
    data () {
      return {
        customActiveKey: "tab1",
        systemTitle: window.SYS_TITLE,
        systemUrl: window.SYS_URL,
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        loginType: 0,
        requiredTwoStepCaptcha: false,
        stepCaptchaVisible: false,
        form: this.$form.createForm(this),
        encryptedString:{
          key:"",
          iv:"",
        },
        state: {
          time: 60,
          smsSendBtn: false,
        },
        validatorRules:{
          loginName:{rules: [{ required: true, message: '请输入用户名!'},{validator: this.handleLoginName}]},
          password:{rules: [{ required: true, message: '请输入密码!',validator: 'click'}]}
        },
        verifiedCode:"",
        inputCodeContent:"", //20200510 cfm: 为方便测试，不输入验证码可 ""-->"xxxx"
        inputCodeNull:true,
        departList:[],
        departVisible:false,
        departSelected:"",
        currentUsername:"",
        validate_status:"",
        currdatetime:'',
        randCodeImage:'',
        requestCodeSuccess:false
      }
    },
    created () {
      this.currdatetime = new Date().getTime();
      Vue.ls.remove(ACCESS_TOKEN)
      this.getRouterData();
    },
    methods: {
      ...mapActions([ "Login", "Logout" ]),
      // handler
      handleLoginName (rule, value, callback) {
        const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (regex.test(value)) {
          this.loginType = 0
        } else {
          this.loginType = 1
        }
        callback()
      },
      handleSubmit () {
        let that = this
        let loginParams = {};
        that.loginBtn = true;
        // 使用账户密码登陆
        if (that.customActiveKey === 'tab1') {
          that.form.validateFields([ 'loginName', 'password', 'rememberMe' ], { force: true }, (err, values) => {
            if (!err) {
              loginParams.loginName = values.loginName
              loginParams.password = md5(values.password)
              //loginParams.remember_me = values.rememberMe
              console.log("登录参数",loginParams)
              that.Login(loginParams).then((res) => {
                this.departConfirm(res, loginParams.loginName)
              }).catch((err) => {
                that.requestFailed(err);
              });
            }else {
              that.loginBtn = false;
            }
          })
        }
      },
      loginSuccess (res) {
        this.$router.push({ path: "/dashboard/analysis" })
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`,
        });
        if(res.data && res.data.user) {
          if(res.data.user.loginName === 'admin'){
            let desc = 'admin只是平台运维用户，真正的管理员是租户(测试账号为jsh），admin不能编辑任何业务数据，只能配置平台菜单和创建租户'
            this.$message.info(desc,30)
          }
        }
        this.initMPropertyShort();
      },
      cmsFailed(err){
        this.$notification[ 'error' ]({
          message: "登录失败",
          description:err,
          duration: 4,
        });
      },
      requestFailed (err) {
        this.$notification[ 'error' ]({
          message: '登录失败',
          description: ((err.response || {}).data || {}).message || err.message || "请求出现错误，请稍后再试",
          duration: 4,
        });
        this.loginBtn = false;
      },
      generateCode(value){
        this.verifiedCode = value.toLowerCase()
      },
      departConfirm(res, loginName){
        if(res.code==200){
          let err = {};
          if(res.data.msgTip == 'user can login'){
            Vue.ls.set('winBtnStrList', res.data.userBtn, 7 * 24 * 60 * 60 * 1000);
            Vue.ls.set('roleType', res.data.roleType, 7 * 24 * 60 * 60 * 1000);
            this.loginSuccess(res)
          } else if(res.data.msgTip == 'user is not exist'){
            err.message = '用户不存在';
            this.requestFailed(err)
            this.Logout();
          } else if(res.data.msgTip == 'user password error'){
            err.message = '用户密码不正确';
            this.requestFailed(err)
            this.Logout();
          } else if(res.data.msgTip == 'user is black'){
            err.message = '用户被禁用';
            this.requestFailed(err)
            this.Logout();
          } else if(res.data.msgTip == 'tenant is black'){
            if(loginName === 'jsh') {
              err.message = 'jsh用户已停用，请注册租户进行体验！';
            } else {
              err.message = '用户所属的租户被禁用';
            }
            this.requestFailed(err)
            this.Logout();
          } else if(res.data.msgTip == 'tenant is expire'){
            err.message = '用户所属的租户已过期';
            this.requestFailed(err)
            this.Logout();
          } else if(res.data.msgTip == 'access service error'){
            err.message = '查询服务异常';
            this.requestFailed(err)
            this.Logout();
          }
        }else{
          this.requestFailed(res)
          this.Logout();
        }
      },
      getRouterData(){
        this.$nextTick(() => {
          if (this.$route.params.username) {
            this.form.setFieldsValue({
              'username': this.$route.params.username
            });
          }
        })
      },
      //获取密码加密规则
      getEncrypte(){
        var encryptedString = Vue.ls.get(ENCRYPTED_STRING);
        if(encryptedString == null){
          getEncryptedString().then((data) => {
            this.encryptedString = data
          });
        }else{
          this.encryptedString = encryptedString;
        }
      },
      //加载商品属性
      initMPropertyShort(){
        let mPropertyListShort = '';
        let params = {};
        params.currentPage = 1;
        params.pageSize = 100;
        getAction('/materialProperty/list', params).then((res) => {
          if(res && res.code === 200){
            if(res.data) {
              let thisRows = res.data.rows; //属性列表
              Vue.ls.set('materialPropertyList', thisRows, 7 * 24 * 60 * 60 * 1000);
            }
          }
        })
      }
    }
  }
</script>

<style lang="less" scoped>
  .user-layout-login {
    label {
      font-size: 14px;
    }

    .getCaptcha {
      display: block;
      width: 100%;
      height: 40px;
    }

    .forge-password {
      font-size: 14px;
      font-weight: bolder;
    }

    button.login-button {
      padding: 0 15px;
      font-size: 16px;
      height: 40px;
      width: 100%;
    }

    .user-login-other {
      text-align: left;
      margin-top: 24px;
      line-height: 22px;

      .item-icon {
        font-size: 24px;
        color: rgba(0,0,0,.2);
        margin-left: 16px;
        vertical-align: middle;
        cursor: pointer;
        transition: color .3s;

        &:hover {
          color: #1890ff;
        }
      }

      .register {
        float: right;
      }
    }
  }

</style>
<style>
  .valid-error .ant-select-selection__placeholder{
    color: #f5222d;
  }
  .login-copyright {
    text-align: center;
    margin-top: 20px
  }
  .login-copyright, .login-copyright a {
    color: #666
  }
</style>
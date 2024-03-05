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
          placeholder="请输入密码">
          <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input>
      </a-form-item>

      <a-row :gutter="0">
        <a-col :span="14">
          <a-form-item>
            <a-input
              v-decorator="['inputCode']"
              size="large"
              type="text"
              default-value=""
              placeholder="请输入验证码">
              <a-icon slot="prefix" type="smile" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="10" style="text-align: right">
          <img v-if="requestCodeSuccess" style="margin-top: 2px;" :src="randCodeImage" @click="handleChangeCheckCode"/>
          <img v-else style="margin-top: 2px;" src="../../assets/checkcode.png" @click="handleChangeCheckCode"/>
        </a-col>
      </a-row>

      <a-form-item>
        <a-checkbox :checked="checked" @change="handleChange">记住密码</a-checkbox>
        <router-link v-if="registerFlag==='1'" :to="{ name: 'register'}" class="forge-password" style="float: right;margin-right: 10px;" >
          注册租户
        </router-link>
      </a-form-item>

      <a-form-item style="margin-top:16px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="loginBtn"
          @click.stop.prevent="handleSubmit"
          :disabled="loginBtn">登 录
        </a-button>
      </a-form-item>

      <div class="login-copyright" v-if="device === 'mobile'">
        <a-row>
          <a-col>
            © 2015-2030 Powered By
            <a style="color:#00458a;" :href="systemUrl" target="_blank">官方网站</a>
          </a-col>
        </a-row>
      </div>
    </a-form>
  </div>
</template>
<!-- BY cao_yu_li -->
<script>
  import md5 from 'md5'
  import TwoStepCaptcha from '@/components/tools/TwoStepCaptcha'
  import { mapActions } from 'vuex'
  import { timeFix } from '@/utils/util'
  import Vue from 'vue'
  import { getPlatformConfigByKey} from '@/api/api'
  import { ACCESS_TOKEN, ENCRYPTED_STRING } from '@/store/mutation-types'
  import { getAction } from '@/api/manage'
  import { getEncryptedString } from '@/utils/encryption/aesEncrypt'
  import { mixinDevice } from '@/utils/mixin.js'

  export default {
    components: {
      TwoStepCaptcha
    },
    mixins: [mixinDevice],
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
        randCode:'',
        randCodeImage:'',
        registerFlag:'',
        requestCodeSuccess:false,
        checked: false
      }
    },
    created () {
      this.loadInfo()
      this.checkScreen()
      this.currdatetime = new Date().getTime();
      Vue.ls.remove(ACCESS_TOKEN)
      this.getRouterData()
      this.getRegisterFlag()
      this.handleChangeCheckCode()
    },
    methods: {
      ...mapActions([ "Login", "Logout" ]),
      // handler
      loadInfo() {
        //从缓存中获取登录名和密码
        this.$nextTick(() => {
          if(Vue.ls.get('cache_loginName') && Vue.ls.get('cache_password')) {
            this.form.setFieldsValue({'loginName': Vue.ls.get('cache_loginName')})
            this.form.setFieldsValue({'password': Vue.ls.get('cache_password')})
            this.checked = true
          }
        })
        //从注册页面跳转过来，给登录名进行赋值
        if(this.$route.params.loginName) {
          this.$nextTick(() => {
            //先清空缓存
            Vue.ls.remove('cache_loginName')
            Vue.ls.remove('cache_password')
            this.form.setFieldsValue({'loginName':this.$route.params.loginName})
            this.form.setFieldsValue({'password': ''})
            this.checked = false
          })
        }
      },
      handleLoginName (rule, value, callback) {
        const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (regex.test(value)) {
          this.loginType = 0
        } else {
          this.loginType = 1
        }
        callback()
      },
      //切换勾选
      handleChange(e) {
        this.checked = e.target.checked
      },
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
      handleSubmit () {
        let that = this
        let loginParams = {};
        that.loginBtn = true;
        // 使用账户密码登陆
        if (that.customActiveKey === 'tab1') {
          that.form.validateFields([ 'loginName', 'password', 'inputCode' ], { force: true }, (err, values) => {
            if (!err) {
              if(values.inputCode === this.randCode) {
                loginParams.loginName = values.loginName
                loginParams.password = md5(values.password)
                if(that.checked) {
                  //勾选的时候进行缓存
                  Vue.ls.set('cache_loginName', values.loginName)
                  Vue.ls.set('cache_password', values.password)
                } else {
                  //没勾选的时候清缓存
                  Vue.ls.remove('cache_loginName')
                  Vue.ls.remove('cache_password')
                }
                that.Login(loginParams).then((res) => {
                  this.departConfirm(res, loginParams.loginName)
                }).catch((err) => {
                  that.requestFailed(err);
                });
              } else {
                this.$notification['error']({
                  message: "提示",
                  description: "验证码错误",
                  duration: 2
                });
                this.loginBtn = false
              }
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
          } else {
            getPlatformConfigByKey({ "platformKey": "bill_excel_url" }).then((res) => {
              if (res && res.code === 200) {
                if(res.data.platformValue) {
                  Vue.ls.set('isShowExcel', true);
                } else {
                  Vue.ls.set('isShowExcel', false);
                }
              }
            })
            getAction("/user/infoWithTenant",{}).then(res=>{
              if(res && res.code === 200) {
                let currentTime = new Date(); //新建一个日期对象，默认现在的时间
                let expireTime = new Date(res.data.expireTime); //设置过去的一个时间点，"yyyy-MM-dd HH:mm:ss"格式化日期
                let type = res.data.type  //租户类型，0免费租户，1付费租户
                let difftime = expireTime - currentTime; //计算时间差
                let tipInfo = '您好，服务即将到期，请及时续费！'
                //0免费租户-如果距离到期还剩5天就进行提示续费
                if(type === '0' && difftime<86400000*5) {
                  this.$message.warning(tipInfo,8)
                }
                //1付费租户-如果距离到期还剩15天就进行提示续费
                if(type === '1' && difftime<86400000*15) {
                  this.$message.warning(tipInfo,8)
                }
              }
            })
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
            err.message = '试用期已结束，请联系客服续费';
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
      getRegisterFlag(){
        getAction('/platformConfig/getPlatform/registerFlag').then((res) => {
          this.registerFlag = res + ''
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
        getAction('/materialProperty/getAllList').then((res) => {
          if(res && res.code === 200){
            if(res.data) {
              let thisRows = res.data; //属性列表
              Vue.ls.set('materialPropertyList', thisRows, 7 * 24 * 60 * 60 * 1000);
            }
          }
        })
      },
      checkScreen() {
        let percentage = '100%'
        let basicWidth = 1920
        const currentWidth = window.screen.width
        const currentHeight = window.screen.height
        //浏览器的当前比例
        const currentRatio = window.devicePixelRatio.toFixed(2)
        //浏览器需要调整的比例
        let needRatio = 1
        let ratio = currentWidth/basicWidth
        if(ratio>0.5 && ratio<0.67) {
          percentage = '50%'
          needRatio = 0.5
        } if(ratio>=0.67 && ratio<0.75) {
          percentage = '67%'
          needRatio = 0.67
        } else if(ratio>=0.75 && ratio<0.8) {
          percentage = '75%'
          needRatio = 0.75
        } else if(ratio>=0.8 && ratio<0.9) {
          percentage = '80%'
          needRatio = 0.8
        } else if(ratio>=1.1 && ratio<1.25) {
          percentage = '110%'
          needRatio = 1.1
        } else if(ratio>=1.25 && ratio<1.5) {
          percentage = '125%'
          needRatio = 1.25
        } else if(ratio>=1.5 && ratio<1.75) {
          percentage = '150%'
          needRatio = 1.5
        }
        //console.log(currentRatio)
        //console.log(needRatio)
        if(currentRatio-0 !== needRatio) {
          this.openNotificationWithIcon('warning', currentWidth, currentHeight, percentage)
        }
      },
      openNotificationWithIcon(type, currentWidth, currentHeight, percentage) {
        this.$notification[type]({
          message: '浏览器的缩放比例调整提示',
          description: '检测到您显示器的分辨率为：' + currentWidth + '*' + currentHeight + ' ，为了获得更好的操作体验，建议您将浏览器的缩放比例调整至' + percentage,
          duration: 10
        });
      },
    }
  }
</script>

<style lang="less" scoped>
  .user-layout-login {
    label {
      font-size: 14px;
    }

    .ant-form-item {
      margin-bottom: 16px;
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

    .weixin {
      padding-left:10px;
      color: red;
      cursor:pointer
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
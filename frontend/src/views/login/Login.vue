<template>
  <div class="login">
    <a-form
      @submit.prevent="doLogin"
      :form="form"
    >
      <a-alert
        type="error"
        :closable="true"
        v-show="error"
        :message="error"
        showIcon
        style="margin-bottom: 24px;"
      >
      </a-alert>
        <a-form-item>
          <a-input
            ref="loginUser"
            size="large"
            v-decorator="['name',{rules: [{ required: true, message: '请输入账户名', whitespace: true}]}]"
          >
            <a-icon
              slot="prefix"
              type="user"
            ></a-icon>
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input
            size="large"
            type="password"
            v-decorator="['password',{rules: [{ required: true, message: '请输入密码', whitespace: true}]}]"
          >
            <a-icon
              slot="prefix"
              type="lock"
            ></a-icon>
          </a-input>
        </a-form-item>
        <!-- <a-form-item>
          <a-row>
            <a-col :span="19">
              <a-input
                size="large"
                v-decorator="['verifyCode',{rules: [{ required: true, message: '请输入验证码', whitespace: true}]}]"
                placeholder="请输入验证码"
              >
              </a-input>
            </a-col>
            <a-col :span="4" :offset="1">
              <img :src="imgUrl" style="width:80px;height:40px" @click="createCode" >
                <a-tag color="#87d068" @click="createCode">{{ showCode}}</a-tag> 
            </a-col>
          </a-row>
        </a-form-item> -->
      <!-- <a-form-item> -->
        <a-button
          :loading="loading"
          style="width: 100%; margin-top: 4px; background: ##009180"
          size="large"
          htmlType="submit"
          type="primary"
        >
          登录
        </a-button>
      <!-- </a-form-item> -->
    </a-form>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import SelecteType from './SelectType'
import {Encrypt} from '../../utils/secret'

export default {
  beforeCreate () {
    this.form = this.$form.createForm(this)
  },
  components: { SelecteType, Encrypt },
  name: 'Login',
  data () {
    return {
      loading: false,
      error: '',
      selectVisiable: false,
      checkCode: '',
      showCode: '',
      imgUrl: 'http://localhost:1088/captcha.jpg'
    }
  },
  mounted () {
    // this.createCode()
    this.$nextTick(() => {
      this.$refs.loginUser.focus()
    })
  },
  computed: {
    systemName () {
      return '华中科技大学同济医学院附属协和医院医师综合管理平台'
    },
    copyright () {
      return this.$store.state.setting.copyright
    }
  },
  created () {
    this.$db.clear()
    this.$router.options.routes = []
  },
  methods: {
    ...mapMutations({
      setToken: 'account/setToken',
      setExpireTime: 'account/setExpireTime',
      setPermissions: 'account/setPermissions',
      setRoles: 'account/setRoles',
      setUser: 'account/setUser',
      setTheme: 'setting/setTheme',
      setLayout: 'setting/setLayout',
      setMultipage: 'setting/setMultipage',
      fixSiderbar: 'setting/fixSiderbar',
      fixHeader: 'setting/fixHeader',
      setColor: 'setting/setColor'
    }),
    doLogin () {
      console.log(this.$const)
      // 用户名密码登录
      this.form.validateFields(['name', 'password'], (errors, values) => {
        if (!errors) {
          let name = this.form.getFieldValue('name')
          let password = this.form.getFieldValue('password')
          // let verifyCodeActual = this.form.getFieldValue('verifyCode')
          this.loading = true
          this.$post('login', {
            username: name,
            password: Encrypt(password)
          }).then((r) => {
            console.info(r)
            let data = r.data.data
            this.saveLoginData(data)
            setTimeout(() => {
              this.loading = false
            }, 500)
            if (data.user.roleId == this.$const.RoleEnum.YWC || data.user.roleId == this.$const.RoleEnum.FSK || data.user.roleId == this.$const.RoleEnum.FSKGR || data.user.roleId == this.$const.RoleEnum.HFK) {
              this.$router.push({path: '/ywc'})
            } else if (data.user.roleId == this.$const.RoleEnum.GR) {
              this.$router.push({path: '/personalManagement', query: {type: 1}})
            } else if (data.user.roleId == this.$const.RoleEnum.KSZR) {
              this.$router.push({path: '/FileManagement', query: {type: 1}})
            } else if (data.user.roleId == this.$const.RoleEnum.YKYS) {
              this.$router.push({path: '/WorkforceManagement', query: {type: 1}})
            }
             else {
              this.$router.push('/')
            }
          }).catch((e) => {
            console.error(e)
            setTimeout(() => {
              this.loading = false
            }, 500)
          })
        }
      })
    },
    createCode () {
      // let code = "";
      // let code2="";
      // const codeLength = 4; //验证码的长度  
      // const random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
      //   'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //随机数  
      // for (let i = 0; i < codeLength; i++) { //循环操作  
      //   let index = Math.floor(Math.random() * 36); //取得随机数的索引（0~35）  
      //   code += random[index]; //根据索引取得随机数加到code上  
      //   code2 += "        "+random[index];
      // }
      // this.checkCode = code; //把code值赋给验证码  
      // this.showCode=code2;
      this.imgUrl = "http://localhost:1088/captcha.jpg?index="+Math.floor(Math.random() * 36);
    },
    open () {
      this.selectVisiable = true
    },
    close () {
      this.selectVisiable = false
    },
    observe (type, vendorId) {
      this.selectVisiable = false
      if (vendorId == '' || vendorId.length < 32)
      {
        this.$message.warning('注册码不能为空或注册码位数不对!')
        return false
      }
      else{
        this.$emit('regist', 'Modify', vendorId)
      }
    },
    regist () {
      this.$emit('regist', 'Regist')
    },
    getCaptcha () {
      this.$message.warning('暂未开发')
    },
    saveLoginData (data) {
      this.setToken(data.token)
      this.setExpireTime(data.exipreTime)
      this.setUser(data.user)
      this.setPermissions(data.permissions)
      this.setRoles(data.roles)
      this.setTheme(data.config.theme)
      this.setLayout(data.config.layout)
      this.setMultipage(data.config.multiPage === '1')
      this.fixSiderbar(data.config.fixSiderbar === '1')
      this.fixHeader(data.config.fixHeader === '1')
      this.setColor(data.config.color)
    }
  }
}
</script>

<style lang="less">
  .login {
    .ant-form-item {
      margin-bottom: 20px!important;
      .anticon {
        font-size: 20px!important;
        padding-right: 10px;
        border-right: 1px solid #e0e0e5;
      }
      .ant-input-lg {
        height: 55px!important;
        padding-left: 55px;
        border: none;
        border-bottom: 1px solid #d9d9d9;
        background-color: #fff;
        font-size: 18px!important;
        border-radius: 0;
        &:focus {
          border: none;
          border-color: #fff;
          border-bottom: 1px solid #d9d9d9!important;
          box-shadow: none;
        }
      }
      .ant-input-affix-wrapper:hover .ant-input:not(.ant-input-disabled) {
        border-bottom: 1px solid #d9d9d9;
      }
      .has-error .ant-input:focus {
        border: none;
        box-shadow: none!important;
      }
      .ant-form-explain {
        margin-top: 4px;
      }
    }
    .ant-btn {
      background-color: #009180;
      border-color: #009180;
      height: 55px!important;
      font-size: 20px!important;
    }
 }

</style>

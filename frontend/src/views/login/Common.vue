<template>
  <div class="container">
    <div class="login-box">
      <div class="login-left">
        <div class="top">
          <div class="header">
            <!-- <img width="80" alt="logo" class="logo" src="static/img/logo.png" />
            <span class="title">{{systemName}}</span> -->
          </div>
          <div class="desc">
            <span class="title">{{ systemName }}</span>
          </div>
        </div>
        <div class="login-wel">欢迎您，请登录！</div>
        <component
          :is="componentName"
          @regist="handleRegist"
          :vendorId="vendorId"
          :class="mainContent"
        ></component>
      </div>
      <div class="login-right"></div>
    <!-- <global-footer :copyright="copyright" /> -->
    </div>
  </div>
</template>

<script>
import GlobalFooter from "../common/GlobalFooter";
import Login from "./Login";
import Regist from "./Regist";
import Modify from "./UpdateVendorInfo";

export default {
  name: "Common",
  components: { GlobalFooter, Login, Regist, Modify },
  data() {
    return {
      componentName: "Login",
      mainContent: "main-content",
      vendorId: "",
    };
  },
  computed: {
    systemName() {
      return "医师综合管理平台";
    },
    copyright() {
      return this.$store.state.setting.copyright;
    },
  },
  methods: {
    handleRegist(val, val2) {
      console.log("val2:" + val2);
      if (val == "Login") {
        this.mainContent = "main-content";
      } else {
        this.mainContent = "main-content2";
      }
      this.componentName = val;
      if (val2 != null) {
        this.vendorId = val2;
        console.log(val2);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  // background: #009180 no-repeat center 110px;
  background-size: 100%;
  position: relative;
  background-image: url('/static/img/login-bg.jpg');
  background-repeat: no-repeat;
  background-size: 100% 100%;
  width: 100%;
  height: 100%;
  .login-box{
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    margin: auto;
    width: 1084px;
    height: 600px;
    background: #fff;
    border-radius: 10px;
  .login-left {
    box-sizing: border-box;
    display: inline-block;
    width: 600px;
    height: 600px;
    vertical-align: top;
    box-sizing: border-box;
    padding: 40px 100px;
    .top {
      text-align: center;

      .header {
        background: url(~/static/img/logo3.png) no-repeat 100% 100%;
        text-align: center;
        margin: 20px auto 10px;
        width: 398px;
        height: 76px;
      }

      .desc {
        .title {
          font-size: 28px;
          color: #009180;
          font-family: "Myriad Pro", "Helvetica Neue", Arial, Helvetica,
            sans-serif;
          font-weight: 600;
        }
      }
    }
    .login-wel {
      margin: 80px 0 10px;
      font-size: 20px;
      color: #009180;
    }
  }
  .login-right {
      background: url('/static/img/login-right-img.jpg');
      background-repeat: no-repeat;
      background-size: 100% 100%;
      display: inline-block;
      width: 480px;
      height: 600px;
      vertical-align: top;
      border-radius: 0 10px 10px 0px;
    }
  }
}
</style>

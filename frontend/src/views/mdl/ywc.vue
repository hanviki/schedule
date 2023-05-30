<template>
  <div class="card-area">
    <a-row class="row" :gutter="40">
      <a-col :span="6" v-hasPermission="['ggwsk:yes']">
        <a-card class="card card1">
          <router-link :to="{ path: '/sdl/Sdl/SdlManage', query: { type: 1 } }">
            <div class="">
              <img src="/static/img/scheduling_system_icon.png" alt="" />
              <p>医师排班管理</p>
              <p>Physician scheduling management</p>
            </div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :span="6" v-hasPermission="['ggwsk:yes']">
        <a-card class="card card2">
          <router-link
            :to="{ path: '/mdl/MdlYwc/BaseInfo', query: { type: 1 } }"
          >
            <div>
              <img src="/static/img/management_system_icon.png" alt="" />
              <p>资质管理</p>
              <p>Qualification management</p>
            </div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :span="6" v-hasNoPermission="['xfk:yes']">
        <a-card class="card card3">
          <router-link :to="{ path: '/fs/FsShow', query: { type: 1 } }">
            <div>
              <img
                src="/static/img/radiation_protection_safety_management_icon.png"
                alt=""
              />
              <p>辐射防护安全管理</p>
              <p>Radiation protection safety management</p>
            </div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :span="6" v-hasPermission="['myk:yes']">
        <a-card class="card card2">
          <router-link :to="{ path: '/dept/DeptShow', query: { type: 1 } }">
            <div>
              <img
                src="/static/img/management_system_icon.png"
                alt=""
              />
              <p>科室证件管理</p>
              <p>department credentials management</p>
            </div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :span="6" v-hasPermission="['xfk:yes']">
        <a-card class="card card3">
          <router-link :to="{ path: '/xf/XfkShow', query: { type: 1 } }">
            <div>
              <img
                src="/static/img/radiation_protection_safety_management_icon.png"
                alt=""
              />
              <p>行风建设管理</p>
              <p>Industry atmosphere construction</p>
            </div>
          </router-link>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      hei: window.innerHeight - 130 + "px",
    };
  },
  computed: {
    ggwsk() {
      let permissions = this.$store.state.account.permissions; 
      return permissions.includes("ggwsk:no")
    },
    xfk() {
      let permissions = this.$store.state.account.permissions; 
      return permissions.includes("fsk:no")
    },
    wsk() {
      let permissions = this.$store.state.account.permissions; 
      return permissions.includes("xfk:no")
    },
    myk() {
      let permissions = this.$store.state.account.permissions; 
      return permissions.includes("myk:no")
    }
  },
};
</script>

<style lang="less" scoped>
.card-area {
  background-image: url("/static/img/bga.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  width: 100%;
  height: 100%;
  .row {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
  }
  .card {
    border-radius: 10px;
    color: #fff;
    .ant-card-body {
      padding: 36px;
    }
    img {
      width: 120px;
      height: 130px;
      margin: 30px 0 35px;
    }
    p {
      color: #fff;
      font-weight: 500 !important;
      font-size: 24px !important;
    }
  }
  .card1 {
    background: #dd9814;
  }
  .card2 {
    background: #48a452;
  }
  .card3 {
    background: #906fd7;
  }
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
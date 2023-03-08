<template>
  <!-- <a-button v-show="isShow" class="back_btn" type="primary" icon="rollback" @click="goBack">首页</a-button> -->
  <div :class="['comPage', pageBg]">
    <page-content :class="[breadcrumb_area, breadcrumbBg]" :breadcrumb="breadcrumb" :title="title" :logo="logo">
      <a-tabs
        v-if="tabOptions&&tabOptions.length"
        class="page-tabs"
        :active-key="active"
        @change="changeTab(active, tabOptions[active])"
      >
        <a-tab-pane  v-for="(item, i) in tabOptions" :key="i" forceRender>
          <span class="tab_content" slot="tab" :pagekey="i" @click="changeTab(i, item)">
            <a-icon
              style="fontSize: 24px"
              :type="item.icon"
            />
            <p>{{item.label}}</p>
          </span>
        </a-tab-pane>
      </a-tabs>
      <router-view></router-view>
    </page-content>
  </div>
  
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'ComPage',
  props: ['logo', 'title', 'tabOptions'],
  data () {
    return {
      isShow: true,
      active: 0,
      breadcrumb: []
    }
  },
  components: {
    PageContent: () => import('../common/PageContent')
  },
  computed: {
    ...mapState({
      user: state => state.account.user
    }),
    multipage () {
      return this.$store.state.setting.multipage
    },
    pageBg() {
      return this.$route.query.type == 1 ? 'card-area-bg' : 'card-area-white'
    },
    breadcrumbBg() {
      return this.tabOptions && this.tabOptions.length > 0 ? 'breadcrumb_padding' : 'breadcrumb_padding1'
    },
    breadcrumb_area() {
      let arr = [
        this.$const.RoleEnum.YWC,
        this.$const.RoleEnum.FSK,
      ]
      return arr.includes(Number(this.user.roleId)) ? 'breadcrumb_area' : ''
    } 
  },
  updated () {
    this.getBreadcrumb()
  },
  created() {
    // 添加刷新事件
    window.addEventListener('beforeUnload', this.beforeUnloadFn())
  },
  destroyed() {
    window.removeEventListener('beforeUnload', this.beforeUnloadFn())
  },
  mounted() {
    this.getBreadcrumb();
    this.changeTab(this.active, this.tabOptions[this.active]);
  },
  methods: {
    beforeUnloadFn() {
      // 浏览器刷新 路由和页面保持一致
      this.tabOptions.forEach((ele, i) => {
        if (this.$route.path == ele.path) {
          this.active = i;
          this.changeTab(i, ele);
          // this.clickTab(i, ele);
        }
      });
    },
    getBreadcrumb () {
      this.breadcrumb = this.$route.matched
      console.log(this.breadcrumb, 88888888888)
    },
    changeTab(name, item) {
      this.active = name;
      this.$emit('setChangeTab', name, item)
    },
    // clickTab(name, item) {
    //   this.$emit('setChangeTab', name, item)
    // },
  },
};
</script>
<style lang="less" scoped>
// .card-area-bg{
//   background: url('/static/img/bga.png') no-repeat 100% 100%;
// }
// .card-area-white {
//   width: 100%;
//   height: 100%;
//   background: #fff;
// }
// .breadcrumb_area {
//   background: none!important;
//   padding: 0 20px!important;
//   // padding: 20px 0 !important;
//   border: none!important;
//   height: 100%!important;
// }
.breadcrumb_padding {
  padding-top: 60px!important;
}
.breadcrumb_padding1 {
  padding: 20px!important;
}
</style>
<style lang="less">
.comPage {
  width: 100%;
  height: 100%;
  // overflow-y: auto;
  .page-header-wide, .detail {
    height: 100%!important;
  }
  .page-tabs {
    text-align: center;
    width: 90%;
    margin: 0 5%;
    position: fixed;
    top: 65px;
    z-index: 99;
    .ant-tabs-tab {
      padding: 15px 100px 5px;
      margin-right: 0 !important;
    }
    .ant-tabs-nav {
      background: #fff !important;
      border-radius: 6px !important;
      box-shadow: 0 2px 4px 0px #eee;
      margin-bottom: 10px;
      font-family: "Microsoft YaHei";
      font-weight: 600;
      font-size: 16px;
    }
    .ant-tabs-top-bar {
      border: none !important;
    }
    .tab_content {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      p {
        margin: 5px 0;
      }
    }
  }
}
@media screen and (max-width: 1445px) {
  .comPage .ant-tabs-tab {
      padding: 15px 60px 5px !important;
      margin-right: 0 !important;
    }
}
@media screen and (max-width: 1681px) {
  .comPage .ant-tabs-tab {
      padding: 15px 70px 5px !important;
      margin-right: 0 !important;
    }
}
@media screen and (max-width: 1920px) {
  .comPage .ant-tabs-tab {
      padding: 15px 70px 5px !important;
      margin-right: 0 !important;
    }
}
@media screen and (min-width: 1921px) {
  .comPage .ant-tabs-tab {
      padding: 15px 100px 5px !important;
      margin-right: 0 !important;
    }
}


</style>
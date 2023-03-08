<template>
  <a-layout-header :class="[fixHeader && 'ant-header-fixedHeader', layout === 'side' ? (sidebarOpened ? 'ant-header-side-opened' : 'ant-header-side-closed') : null, theme, 'global-header', ywcTheme ]">
    <div :class="['global-header-wide', layout]">
      <router-link v-if="isMobile || layout === 'head'" to="/" :class="['logo', isMobile ? null : 'pc', theme]">
        <img width="32" src="static/img/logo.png"  alt=""/>
        <h1 v-if="!isMobile">协和医院医师综合管理平台</h1>
      </router-link>
      <a-divider v-if="isMobile" type="vertical" />
      <a-icon v-if="layout === 'side'" class="trigger" :type="collapsed ? 'menu-unfold' : 'menu-fold'" @click="toggleCollapse"/>
      <div v-if="layout === 'head'" class="global-header-menu">
        <i-menu style="height: 64px; line-height: 64px;" class="system-top-menu" :theme="theme" mode="horizontal" :menuData="menuData" @select="onSelect"/>
      </div>
    </div>
    <div :class="['global-header-right', theme]">
      <header-avatar class="header-item"/>
    </div>
  </a-layout-header>
</template>

<script>
import HeaderAvatar from './HeaderAvatar'
import IMenu from '@/components/menu/menu'
import { mapState } from 'vuex'

export default {
  name: 'GlobalHeader',
  components: {IMenu, HeaderAvatar},
  props: ['collapsed', 'menuData'],
  computed: {
    ...mapState({
      isMobile: state => state.setting.isMobile,
      layout: state => state.setting.layout,
      systemName: state => state.setting.systemName,
      sidebarOpened: state => state.setting.sidebar.opened,
      fixHeader: state => state.setting.fixHeader,
      user: state => state.account.user
    }),
    theme () {
      let arr = [
        this.$const.RoleEnum.YWC,
        this.$const.RoleEnum.FSK,
        this.$const.RoleEnum.GR,
        this.$const.RoleEnum.KSZR,
        this.$const.RoleEnum.YKYS,
        this.$const.RoleEnum.FSKGR,
        this.$const.RoleEnum.HFK
      ]
      return this.layout === 'side' ? 'light' : arr.includes(Number(this.user.roleId)) ? 'blue' : this.$store.state.setting.theme
    },
    ywcTheme () {
      let arr = [
        this.$const.RoleEnum.YWC,
        // this.$const.RoleEnum.FSK,
        // this.$const.RoleEnum.YKYS,
        // this.$const.RoleEnum.KSZR,
      ]
      return (arr.includes(Number(this.user.roleId)) && this.$route.path.search('mdl') > -1) ? 'resetHeaderHeight' : '';
    }
  },
  mounted() {
    console.log(this.$route.path, 9999999999)
  },
  methods: {
    toggleCollapse () {
      this.$emit('toggleCollapse')
    },
    onSelect (obj) {
      this.$emit('menuSelect', obj)
    }
  }
}
</script>

<style lang="less" scoped>
  .trigger {
    font-size: 20px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }
  .header-item{
    padding: 0 19px;
    display: inline-block;
    height: 100%;
    cursor: pointer;
    i{
      font-size: 16px;
      color: rgba(0,0,0,.65);
    }
  }
  .global-header{
    padding: 0 12px 0 0;
    -webkit-box-shadow: 0 1px 4px rgba(0,21,41,.08);
    box-shadow: 0 1px 4px rgba(0,21,41,.08);
    // position: relative;
    display: flex;
    align-items: center;
    justify-content: space-between;
    &.light{
      background: #fff;
    }
    &.dark{
      background: #393e46;
    }
    &.blue {
      background: #009180;
      color: #fff;
    }
    .global-header-wide{
      display: flex;
      align-items: center;
      &.head{
        padding: 0 24px;
      }
      &.side{
      }
      .logo {
        height: 64px;
        line-height: 64px;
        // vertical-align: top;
        display: inline-block;
        padding: 0 12px 0 24px;
        cursor: pointer;
        font-size: 20px;
        &.pc{
          padding: 0 12px 0 0;
        }
        img {
          display: inline-block;
          vertical-align: middle;
        }
        h1{
          display: inline-block;
          font-size: 16px;
        }
        &.dark h1{
          color: #fff;
        }
      }
      .global-header-menu{
        display: inline-block;
      }
    }
    .global-header-right{
        &.dark{
          color: #fff;
          i{
            color: #fff;
          }
        }
      }
  }
  .resetHeaderHeight {
    height: 100px;
    padding-bottom: 30px;
  }
  .ant-header-fixedHeader {
    // position: fixed;
    // top: 0;
    // right: 0;
    // z-index: 15;
    width: 100%;
    transition: width .2s;

    &.ant-header-side-opened {
      width: 100%;
      padding-left: 254px;
    }

    &.ant-header-side-closed {
      width: 100%;
      padding-left: 80px;
    }
  }
</style>
<style lang="less">
  .global-header-right {
    margin-right: 20px;
    .ant-dropdown-trigger {
      height: 100%;
      display: flex !important;;
      justify-content: center;
      align-items: center;
      .ant-avatar-image {
        display: inline-block;
        margin: 0;
      }
    }
  }
</style>

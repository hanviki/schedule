<template>
  <a-locale-provider :locale="chinese">
    <div id="febs">
      <router-view />
    </div>
  </a-locale-provider>
</template>

<script>
import enquireScreen from './utils/device'
import chinese from 'ant-design-vue/lib/locale-provider/zh_CN'
import 'moment/locale/zh-cn'

export default {
  name: 'Febs',
  data () {
    return {
      chinese
    }
  },
  created () {
    let _this = this
    enquireScreen(isMobile => {
      _this.$store.commit('setting/setDevice', isMobile)
    })
  }
}
</script>
<style lang="less">
// html, body {
//   font-family: "Microsoft YaHei";
// }
:global {
  .dragable-ghost {
    border: 1px dashed #aaaaaa;
    opacity: 0.65;
  }
  .dragable-chose {
    border: 1px dashed #aaaaaa;
    opacity: 0.65;
  }
  .dragable-drag {
    border: 1px dashed #aaaaaa;
    opacity: 0.65;
  }
}

::-webkit-scrollbar {
  width: 15px;
  height: 15px;
}

::-webkit-scrollbar-track {
  border-radius: 1px;
}

::-webkit-scrollbar-thumb {
  border-radius: 1px;
  background: rgba(0, 0, 0, 0.2);
}

.single-page {
  height: 100%;
  margin: 0;
}

.card-area {
  width: 100%;
}

.not-menu-page {
  background: #fff;
  padding: 16px 32px;
  border-left: 1px solid #e8e8e8;
  border-right: 1px solid #e8e8e8;
  border-bottom: 1px solid #e8e8e8;
}

.ant-btn,
.ant-input .input,
.ant-pagination-item,
.ant-pagination-prev .ant-pagination-item-link,
.ant-pagination-next .ant-pagination-item-link,
.ant-tag,
.ant-modal-content,
.ant-select-selection,
.ant-select-dropdown,
.ant-input-group-addon,
.ant-input-number-input,
.ant-input-number,
.ant-pagination-options-quick-jumper input,
.ant-alert {
  border-radius: 4px !important;
}

.ant-tabs.ant-tabs-card .ant-tabs-card-bar .ant-tabs-tab,
.ant-tabs.ant-tabs-card > .ant-tabs-bar .ant-tabs-tab {
  border-radius: 3px 3px 0 0 !important;
}
.ant-card-wider-padding .ant-card-body {
  padding: 5px 10px !important;
}

.ant-modal-mask {
  background-color: rgba(0, 0, 0, 0.6) !important;
}

.ant-modal-header {
  border-bottom-color: #fff !important;
}

.ant-menu-dark .ant-menu-inline.ant-menu-sub {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2) inset !important;
}

.ant-menu-inline .ant-menu-item,
.ant-menu-vertical .ant-menu-item {
  margin-top: 0 !important;
}
.ant-menu-dark,
.ant-menu-dark .ant-menu-sub {
  background: #393e46 !important;
}

.ant-table-row-expand-icon {
  margin-right: 1rem !important;
}

:root .ant-tabs-tab-prev {
  border: 1px solid #e8e8e8;
  border-radius: 3px 0 0 0;
  background: #fafafa;
}
.ant-card-loading {
  &:after {
    width: 0 !important;
  }
}
.ant-tabs-tab-next.ant-tabs-tab-arrow-show {
  border: 1px solid #e8e8e8;
  border-radius: 0 3px 0 0;
  background: #fafafa;
}
.ant-layout-header,
.system-top-menu {
  height: 64px;
  line-height: 64px;
}
.ant-form-item {
  margin-bottom: 0.25rem !important;
}
.ant-menu-inline,
.ant-menu-vertical,
.ant-menu-vertical-left {
  border-right: 0 solid #ccc !important;
}
.ant-drawer-body {
  padding-bottom: 3rem !important;
}
.page-tabs .ant-tabs-close-x {
  color: #fff !important;
  margin-left: 0.3rem !important;
}
.page-tabs:hover .ant-tabs-close-x {
  color: #f95476 !important;
}
.drawer-bootom-button {
  position: absolute;
  bottom: 0;
  width: 100%;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  left: 0;
  background: #fff;
  border-radius: 0 0 2px 2px;
}
.search {
  margin-bottom: 0.125rem !important;
}
i {
  font-size: 0.97rem;
}
p {
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
  white-space: nowrap;
}
@media screen and (min-width: 1400px) {
}

@media screen and (max-width: 1399px) {
  p,
  .ant-pagination,
  .ant-form,
  .ant-dropdown,
  .ant-form-item,
  .ant-select,
  .ant-breadcrumb,
  .ant-form label,
  .ant-btn,
  .ant-table,
  .ant-menu-vertical .ant-menu-item,
  .ant-menu-vertical-left .ant-menu-item,
  .ant-menu-vertical-right .ant-menu-item,
  .ant-menu-inline .ant-menu-item,
  .ant-menu-vertical .ant-menu-submenu-title,
  .ant-menu-vertical-left .ant-menu-submenu-title,
  .ant-menu-vertical-right .ant-menu-submenu-title,
  .ant-menu-inline .ant-menu-submenu-title {
    font-size: 13px !important;
  }
  .ant-card-head {
    font-size: 14px !important ;
  }
  .page-tabs .ant-tabs-nav-container {
    font-size: 13px !important;
  }
}
.ant-table-thead > tr > th, .ant-table-tbody > tr > td {
    padding: 6px 6px;
}
tr.ant-table-expanded-row td > .ant-table-wrapper {
    margin: -6px -6px -7px;
}
.ant-modal-body{
  padding: 8px 24px;
}
.ant-modal-body{
  padding: 8px 24px;
}
.ant-modal-header{
  padding: 4px 24px;
}
.ant-modal-footer{
  padding: 5px 16px;
}
.ant-modal-close-x{
  width: 32px;
  height: 32px;
  line-height: 32px;
}
.ant-table-pagination.ant-pagination{
  margin: 4px 0 0 0 !important;
}
.ant-table{
  color:#393e46 !important;
  font-size: 14px !important;
}
.ant-fullcalendar-column-header-inner{
  font-size: 20px !important;
  text-align: center;
  font-weight: bold!important;
}
.ant-fullcalendar-value{
 // font-weight: bold!important;
  text-align: center!important;
}
</style>
<style>
.multi-page {
  height: calc(100% - 50px);
  margin: -24px 0 0;
}
 .fold{
  width: calc(100% - 216px);
  display: inline-block
}
@media screen and (max-width: 900px) {
  .fold {
    width: 100%;
  }
}
</style>

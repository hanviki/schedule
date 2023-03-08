<template>
  <a-card :bordered="false"  class="card-area" >
    <a-tabs
      class="page-tabs"
      :active-key="active"
      @change="changeTab"
    >
      <a-tab-pane  v-for="(item, i) in tabOptions" :key="i" forceRender>
        <span class="tab_content" slot="tab" :pagekey="i">
          <!-- <img src="/static/img/radiation_protection_safety_management_icon.png" alt=""> -->
          <a-icon
            type="down-circle-outlined"
            class="editable-cell-icon-check"
          />
          <!-- <down-circle-outlined /> -->
          <p>{{item.label}}</p>
        </span>
      </a-tab-pane>
    </a-tabs>
    <div>
      <!-- <BaseInfo v-show="activePage==0" /> -->
      <!-- <ManageInfo v-show="activePage==1" />
      <AdminShow v-show="activePage==2" />
      <AdminTecShow v-show="activePage==3" />
      <AdminBL v-show="activePage==4" />
      <MdlBForeign v-show="activePage==5" /> -->
    </div>
 </a-card>
</template>
<script>
export default {
  name: 'Phy',
  data () {
    return {
      isShow: true,
      subShow: 0,
      title: '',
      active: 0,
      tabOptions: [
        {label: '基本档案', icon: ''},
        {label: '管理资质'},
        {label: '业务资质管理'},
        {label: '技术资质管理'},
        {label: '不良记分管理'},
        {label: '应急保障管理'}
      ]
    }
  },
  components: {
    MdlBQulificationList: () => import('./MdlBQulification/MdlBQulificationList'),
    UserInfo: () => import('./UserInfo'),
    // MdlYwc: () => import('./MdlYwc'),
    BaseInfo: () => import('./BaseInfo'),
    ManageInfo: () => import('./MdlBManager/ManageInfo'),
    AdminShow: () => import('./AdminShow'),
    AdminTecShow: () => import('./AdminTecShow'),
    AdminBL: () => import('./AdminBL'),
    MdlBForeign: () => import('./MdlBForeign/MdlBForeign'),
  },
  mounted() {
    this.changeTab(this.active);
  },
  methods: {
    changeTab(name) {
      this.active = name;
    },
    openQulifaction(index,title) {
      this.isShow = false;
      this.subShow = index;
      this.title= title
    },
    Back() {
      this.isShow = true
    }
  },
};
</script>
<style lang="less" scoped>
.card-area{
  background-image: url('../../../static/img/bga.png');
  background-repeat: no-repeat;
  background-size: 100% 100%;
  width: 100%;
  height: 100%;
  position: relative;
  .card-area_bg {
    width: 100%;
    height: 100%;
    background: #fff;
    opacity: 0.8;
    position: absolute;
    top: 0;
    left: 0;
  }
  .page-tabs {
    .tab_content {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      p {
        margin-top: 15px;
      }
    }
    img {
      display: inline-block;
      width: 40px;
      height: 40px;
    }
  }
}
.cuhov:hover{
   background: linear-gradient(to bottom left,#1c998c,#24ada0)
}
.cu{
  width:200px;
  color: #08c;
}
.cu:hover {
    background-color: #1c998c;
    color: #fff;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
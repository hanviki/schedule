<template>
 <div class="baseInfo">
    <a-row v-if="isShow" class="row" :gutter="40">
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #34bab9"
          @click="openQulifaction(0,'机构执业资质')"
        >
          <a-icon type="file-text" :bordered="false" />
          <div class="titleDiv">
            机构执业资质
          </div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #01aff1"
          @click="openQulifaction(1,'医师执业资质')"
        >
          <a-icon type="file-done" :bordered="false" />
          <div class="titleDiv">
            医师执业资质
          </div>
        </div>
      </a-col>
    </a-row>
    <div v-if="!isShow">
      <div class="title">{{title}}</div>
      <mdl-b-qulification-list v-show="subShow==0">
      </mdl-b-qulification-list>
      <user-info v-show="subShow==1"></user-info>
     </div>
 </div>
</template>
<script>
export default {
  name: 'BaseInfo',
  data () {
    return {
      isShow: true,
      subShow: 0,
      title: ''
    }
  },
  watch: {
    '$route.query': {
      handler(v) {
        if (v && v.type == 1) {
          this.isShow = true;
        }
      },
      immediate: true,
      deep: true,
    }
  },
  components: {
    MdlBQulificationList: () => import('./MdlBQulification/MdlBQulificationList'),
    UserInfo: () => import('./UserInfo')
  },
  methods: {
    openQulifaction(index,title) {
      this.subShow = index;
      this.title = title;
      this.isShow = false;
      this.$router.push({path: this.$route.path, query: {}})
    },
  },
};
</script>
<style lang="less" scoped>
.baseInfo{
  width: 100%;
  margin-top: 20px;
  .row {
    width: 100%;
    height: 70%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    // padding-bottom: 100px;
  }
}

.cuhov:hover{
   background: linear-gradient(to bottom left,#1c998c,#24ada0)
}
.cu{
  height: 250px;
  border-radius: 6px;
  color: #fff;
  background: #fff;
  padding: 20px;
  cursor: pointer;
  .anticon {
    margin: 20px 0 50px;
    font-size: 70px;
  }
  .titleDiv {
    line-height: 26px;
    font-weight: bold;
    font-size: 22px;
  }
}
.cu:hover {
  opacity: 0.5;
  color: #000;
}
.title {
  color: #42b983;
  font-size: 16px;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
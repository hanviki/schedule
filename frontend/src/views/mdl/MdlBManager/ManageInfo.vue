<template>
  <div class="manageInfo">
    <a-row v-if="isShow" class="row" :gutter="40">
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #01aff1"
          @click="openQulifaction(0, '医疗主任')"
        >
          <a-icon type="codepen" :bordered="false" />
          <div class="titleDiv">
            医疗主任
          </div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #34bab9"
          @click="openQulifaction(1, '医疗组长')"
          :bordered="false"
        >
          <a-icon
            type="code-sandbox"
            :bordered="false"
          />
          <div class="titleDiv">
            医疗组长
          </div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #79c346"
          @click="openQulifaction(2, '质控员')"
        >
          <a-icon
            type="ant-design"
            :bordered="false"
          />
          <div class="titleDiv">
            质控员
          </div>
        </div>
      </a-col>
    </a-row>
    <div v-if="!isShow">
      <div class="title">{{title}}</div>
      <mdl-b-manage-list v-show="subShow == 0" type="医疗主任">
      </mdl-b-manage-list>
      <mdl-b-manage-list v-show="subShow == 1" type="医疗组长">
      </mdl-b-manage-list>
      <mdl-b-manage-list v-show="subShow == 2" type="质控员">
      </mdl-b-manage-list>
    </div>
  </div>
</template>
<script>
export default {
  name: 'ManageInfo',
  data () {
    return {
      isShow: true,
      subShow: 0,
      title: ''
    };
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
    MdlBManageList: () => import('./MdlBManagerList.vue')
  },
  methods: {
    openQulifaction(index, title) {
      this.title = title,
      this.subShow = index;
      this.isShow = false;
      this.$router.push({path: this.$route.path, query: {}})
    },
  },
};
</script>
<style lang="less" scoped>
.manageInfo{
  width: 100%;
  margin-top: 20px;
  .row {
    width: 100%;
    height: 70%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
  }
}
.cuhov:hover {
  background: linear-gradient(to bottom left, #1c998c, #24ada0);
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
    font-weight: bold;
    font-size: 22px;
    line-height: 26px;
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
@import "../../../../static/less/Common";
</style>

<template>
 <div class="mdlBForeign">
    <!-- <a-row v-if="isShow" class="row" :gutter="40">
      <a-col :span="5" >
        <div
          class="cu"
          @click="openQulifaction(0,'机构执业资质')"
          :bordered="false"
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
          @click="openQulifaction(1,'医师执业资质')"
          :bordered="false"
        >
          <a-icon type="file-done" :bordered="false" />
          <div class="titleDiv">
            医师执业资质
          </div>
        </div>
      </a-col>
    </a-row> -->
    <div v-if="!isShow">
      <!-- <a-row>
        <a-col :sm="6">
          <a-button type="primary"  @click="Back" icon="rollback">返回</a-button>
        </a-col>
        <a-col :sm="18">
          <span style="font-size: 20px; text-align: center; color: green">{{title}}</span>
        </a-col>
      </a-row> -->
      <div class="title">应急保障管理</div>
      <MdlBForeignTable />
     </div>
 </div>
</template>
<script>
export default {
  name: 'MdlBForeign',
  data () {
    return {
      isShow: true,
      title: '应急保障管理'
    }
  },
  components: {
    MdlBForeignTable: () => import('./MdlBForeign/MdlBForeign'),
  },
  watch: {
    '$route.query': {
      handler(v) {
        if (v && v.type == 1) {
          this.isShow = true;
        } else {
          this.isShow = false;
        }
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    Back() {
      this.isShow = true;
      this.$router.push({path: this.$route.path, query: {type: 1}})
    },
  },
};
</script>
<style lang="less" scoped>
.mdlBForeign{
  width: 100%;
  height: 100%;
  margin-top: 20px;
  .row {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
  }
}

.cuhov:hover{
   background: linear-gradient(to bottom left,#1c998c,#24ada0)
}
.cu{
  height: 220px;
  border-radius: 6px;
  color: #08c;
  background: #fff;
  padding: 20px;
  .anticon {
    margin: 20px 0 30px;
    font-size: 70px;
  }
  .titleDiv {
    font-weight: bold;
    font-size: 22px;
    line-height: 26px;
  }
}
.cu:hover {
    background-color: #1c998c;
    color: #fff;
}
.title {
  color: #42b983;
  font-size: 16px;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
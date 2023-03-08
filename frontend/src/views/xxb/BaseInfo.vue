<template>
  <div class="card-area xxb_baseInfo">
    <a-row v-if="isShow" class="row" :gutter="40">
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #01aff1"
          @click="openQulifaction(0, '临床类-单科申报')"
        >
          <a-icon type="file-text" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">临床类-单科申报</div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #34bab9"
          @click="openQulifaction(1, '临床类-多科联合申报')"
        >
          <a-icon type="file-done" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">临床类-多科联合申报</div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div
          class="cu"
          style="background-color: #79c346"
          @click="openQulifaction(2, '检验检查类')"
        >
          <a-icon type="file-done" style="font-size: 60px" :bordered="false" />
          <div style="font-size: 20px; margin-top: 10px">检验检查类</div>
        </div>
      </a-col>
    </a-row>
    <div v-else>
      <div class="row_btn">
        <a-button class="back_btn" type="text" icon="rollback" @click="Back">返回</a-button>
        <div class="title">{{title}}</div>
      </div>
      <xxbBLcone-list v-show="subShow == 0"> </xxbBLcone-list>
      <xxbBLcmany-list v-show="subShow == 1"> </xxbBLcmany-list>
      <xxbBCheck-list v-show="subShow == 2"> </xxbBCheck-list>
    </div>
  </div>
</template>
<script>
import xxbBCheckList from "../xxb/XxbBCheck/XxbBCheck";
import xxbBLconeList from "../xxb/XxbBLcone/XxbBLcone";
import xxbBLcmanyList from "../xxb/XxbBLcmany/XxbBLcmany";
export default {
  name: "xxb_baseInfo",
  data() {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: { xxbBCheckList, xxbBLconeList, xxbBLcmanyList },
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
  methods: {
    openQulifaction(index, title) {
      this.isShow = false;
      this.subShow = index;
      this.title = title;
      this.$router.push({path: this.$route.path, query: {}})
    },
    Back() {
      this.isShow = true;
      this.$router.push({path: this.$route.path, query: {type: 1}})
    },
  },
};
</script>
<style lang="less" scoped>
.xxb_baseInfo{
  width: 100%;
  height: 100%;
  .row {
    width: 100%;
    height: 70%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    margin-top: 50px;
  }
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
.row_btn{
  margin: 10px;
  display: flex;
  align-items: center;
  font-size: 16px;
  .back_btn {
    border: none;
    box-shadow: 0px;
    color: #42b983;
  }
  .title {
    border-left: 1px solid #d8d8d8;
    padding-left: 10px;
    line-height: 16px;
    color: #42b983;
  }
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";

</style>
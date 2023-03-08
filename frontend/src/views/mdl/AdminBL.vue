<template>
  <div class="adminBL">
    <div v-if="isShow">
      <a-row class="row" :gutter="40">
        <a-col :span="5" >
          <div
            class="cu"
             style="background-color: #1c998c"
            @click="openQulifaction(1, '不良记分指标')"
          >
            <a-icon type="profile" :bordered="false" />
            <div class="titleDiv">
              不良记分指标
            </div>
          </div>
        </a-col>
        <a-col :span="5" >
          <div
            class="cu"
            style="background-color: #a874c7"
            @click="openQulifaction(2, '不良记分记录')"
          >
            <a-icon type="profile" :bordered="false" />
            <div class="titleDiv">
              不良记分记录
            </div>
          </div>
        </a-col>
        <a-col :span="5" >
          <div
            class="cu"
            style="background-color: #16aea2"
            @click="openQulifaction(3, '不良记分汇总')"
          >
            <a-icon type="project" :bordered="false" />
            <div class="titleDiv">
              不良记分汇总
            </div>
          </div>
        </a-col>
      </a-row>
      <a-row class="row" :gutter="40">
        <a-col :span="5" >
          <div
            class="cu"
            style="background-color: #dbb752"
            @click="openQulifaction(4, '不良记分记录明细')"
          >
            <a-icon type="wallet" :bordered="false" />
            <div class="titleDiv">
              不良记分记录明细
            </div>
          </div>
        </a-col>
        <a-col :span="5" >
          <div
            class="cu"
            style="background-color: #51cbdb"
            @click="openQulifaction(5, '年度累计分数预警')"
          >
            <a-icon type="global" :bordered="false" />
            <div  class="titleDiv">
              年度累计分数预警
            </div>
          </div>
        </a-col>
        <a-col :span="5" >
          <div
            class="cu"
            style="background-color: #79c346"
            @click="openQulifaction(6, '连续两年累计分数预警')"
          >
            <a-icon type="file-done" :bordered="false" />
            <div class="titleDiv">
              连续两年累计分数预警
            </div>
          </div>
        </a-col>
      </a-row>
    </div>
    <div v-if="!isShow">
      <div class="title">{{title}}</div>
      <!-- <chu-fang-info v-show="subShow==0">
        </chu-fang-info> -->
      <mdl-d-badscore v-show="subShow==1"></mdl-d-badscore>
       <mdl-b-badrecord v-show="subShow==2"></mdl-b-badrecord>
       <mdl-b-badrecord-index v-show="subShow==3"></mdl-b-badrecord-index>
      <mdl-b-badrecord-detail v-show="subShow==4"></mdl-b-badrecord-detail>
      <mdl-b-badrecord-year v-show="subShow==5"></mdl-b-badrecord-year>
      <bad-report v-show="subShow==6"></bad-report>
    </div>
  </div>
</template>
<script>
import BadReport from './MdlBBadrecord/BadReport.vue';
import MdlBBadrecord from './MdlBBadrecord/MdlBBadrecord.vue';
import MdlBBadrecordDetail from './MdlBBadrecord/MdlBBadrecordDetail.vue';
import MdlBBadrecordIndex from './MdlBBadrecord/MdlBBadrecordIndex.vue';
import MdlBBadrecordYear from './MdlBBadrecord/MdlBBadrecordYear.vue';
import MdlDBadscore from './MdlDBadscore/MdlDBadscore.vue';

export default {
  name: 'AdminBL',
  data () {
    return {
      isShow: true,
      subShow: 1,
      title: "",
    };
  },
  components: {
    MdlDBadscore,
    MdlBBadrecord,
    MdlBBadrecordIndex,
    MdlBBadrecordDetail,
    MdlBBadrecordYear,
    BadReport
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
  methods: {
    openQulifaction(index, title) {
      this.subShow = index;
      this.title = title;
      this.isShow = false;
      this.$router.push({path: this.$route.path, query: {}})
    },
  },
};
</script>
<style lang="less" scoped>
.adminBL{
  width: 100%;
  margin-top: 20px;
  .row {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    margin-bottom: 30px;
  }
}
.cuhov:hover {
  background: linear-gradient(to bottom left, #1c998c, #24ada0);
}
.cu{
  height: 220px;
  border-radius: 6px;
  color: #fff;
  background: #fff;
  padding: 20px;
  cursor: pointer;
  .anticon {
    margin: 20px 0;
    font-size: 70px;
  }
  .titleDiv {
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
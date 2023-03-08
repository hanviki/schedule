<template>
  <div class="card-area">
    <!-- <a-button v-show="isShow" class="back_btn" type="primary" icon="rollback" @click="goBack()">首页</a-button> -->
    <a-row v-if="isShow" class="row" :gutter="40">
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">人员台账</div>
            <div class="subDiv" @click="openQulifaction(1, '科室人员台账')">
              科室人员台账
            </div>
             <div class="subDiv"  @click="openQulifaction(13, '科室人员信息变更申请')">
              科室人员信息变更申请
            </div>
             <div class="subDiv" v-if="fsk" @click="openQulifaction(12, '科室人员信息变更审核')">
              科室人员信息变更审核
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">考试</div>
            <div class="subDiv" @click="openQulifaction(2, '考试记录维护')">
              考试记录维护
            </div>
            <div class="subDiv" @click="openQulifaction(3, '考试提醒报表')">
              考试提醒报表
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #dbb752">
          <div class="bigDiv" >
            <div class="titleDiv">体检</div>
            <div class="subDiv" @click="openQulifaction(4, '体检记录维护')">
              体检记录维护
            </div>
            <div class="subDiv" @click="openQulifaction(5, '体检提醒报表')">
              体检提醒报表
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="profile"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
     
       
    </a-row>
    <a-row v-if="isShow" class="row" :gutter="40">
       <a-col :span="5">
        <div class="cuDiv"  style="background-color: #79c346">
          <div class="bigDiv" >
            <div class="titleDiv">剂量牌</div>
            <div class="subDiv" v-if="fsk" @click="openQulifaction(6, '剂量牌管理')">
              剂量牌管理
            </div>
            <div class="subDiv" v-if="fsk" @click="openQulifaction(7, '剂量牌领还记录')">
              剂量牌领还记录
            </div>
            <div class="subDiv" v-if="fsk" @click="openQulifaction(8, '剂量牌领还分析报表')">
              剂量牌领还分析报表
            </div>
            <div class="subDiv" v-if="fsk" @click="openQulifaction(9, '个人剂量牌当量维护')">
              个人剂量牌当量维护
            </div>
            <div class="subDiv" @click="openQulifaction(10, '甲状腺屏蔽维护')">
              甲状腺屏蔽维护
            </div>
            <div class="subDiv" @click="openQulifaction(11, '个人剂量牌当量报表')">
              个人剂量牌当量报表
            </div>
          </div>
          <div class="imageDiv">
            <a-avatar :size="60" style=" backgroundColor:white" >
              <a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" />
            </a-avatar>
          </div>
        </div>
      </a-col>
       <a-col :span="5">
        <div class="cuDiv"  style="background-color: orange">
          <div class="bigDiv" >
            <div class="titleDiv">设备台账</div>
             <div class="subDiv" v-if="!fsk" @click="openQulifaction(17, '科室设备信息')">
              科室设备信息
            </div>
            <div class="subDiv" v-if="fsk" @click="openQulifaction(19, '设备信息汇总报表')">
              设备信息汇总报表
            </div>
            <div class="subDiv" v-if="fsk" @click="openQulifaction(14, '设备信息')">
              设备信息
            </div>
             <div class="subDiv" v-if="fsk" @click="openQulifaction(15, '字典信息')">
              字典信息
            </div>
           <div class="subDiv"  @click="openQulifaction(16, '科室防护设备配备信息')">
              科室防护设备配备信息
            </div>
            <div class="subDiv" v-if="fsk" @click="openQulifaction(18, '科室防护设备配备信息汇总报表')">
              科室防护设备配备信息汇总报表
            </div>
          </div>
          <div class="imageDiv">
            <a-avatar :size="60" style=" backgroundColor:white" >
              <a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" />
            </a-avatar>
          </div>
        </div>
      </a-col>
       <a-col :span="5">
       </a-col>
    </a-row>
    <div v-if="!isShow">
      <!-- <a-row class="row_btn">
        <a-col :sm="6">
          <a-button type="primary" @click="Back" icon="rollback">返回</a-button>
        </a-col>
        <a-col :sm="18">
          <span style="font-size: 20px; text-align: center; color: green">{{title}}</span>
        </a-col>
      </a-row> -->
      <div class="row_btn">
        <a-button class="back_btn" type="text" icon="rollback" @click="Back">返回</a-button>
        <div class="title">{{title}}</div>
      </div>
      <!-- <chu-fang-info v-show="subShow==0">
        </chu-fang-info> -->
     <fs-b-user v-show="subShow == 1">
     </fs-b-user>
     <fs-b-exame v-show="subShow == 2">
     </fs-b-exame>
     <fs-b-user-exame v-show="subShow == 3">
     </fs-b-user-exame>
     <fs-b-health v-show="subShow == 4">
     </fs-b-health>
     <fs-b-user-health v-show="subShow == 5">
     </fs-b-user-health>
     <fs-b-quarter v-show="subShow == 6">
     </fs-b-quarter>
     <fs-b-quarter-d v-show="subShow == 7">
     </fs-b-quarter-d>
     <fs-b-quarter-d-index v-show="subShow == 8">
     </fs-b-quarter-d-index>
     <fs-b-qy-index v-show="subShow == 9">
     </fs-b-qy-index>
     <fs-b-qy v-show="subShow == 10">
     </fs-b-qy>
     <fs-b-qy-report v-show="subShow == 11">
     </fs-b-qy-report>
     <fs-b-changerecord-audit v-show="subShow == 12">
     </fs-b-changerecord-audit>
     <fs-b-changerecord v-show="subShow == 13">
     </fs-b-changerecord>
     <asset-b-info v-show="subShow == 14">
     </asset-b-info>
     <asset-d-dictory v-show="subShow == 15"></asset-d-dictory>
<asset-b-peibei v-show="subShow == 16"></asset-b-peibei>
<asset-b-info-personal v-show="subShow == 17"></asset-b-info-personal>
<asset-b-peibei-index  v-show="subShow == 18"></asset-b-peibei-index>
<asset-b-info-index v-show="subShow == 19"></asset-b-info-index>
    </div>
  </div>
</template>
<script>
import FsBExame from './FsBExame/FsBExame.vue';
import FsBHealth from './FsBHealth/FsBHealth.vue';
import FsBQuarter from './FsBQuarter/FsBQuarter.vue';
import FsBUser from './FsBUser/FsBUser.vue';
import FsBUserExame from './FsBUser/FsBUserExame.vue';
import FsBUserHealth from './FsBUser/FsBUserHealth.vue';
import FsBQuarterD from './FsBQuarterD/FsBQuarterD.vue';
import FsBQy from './FsBQy/FsBQy.vue';
import FsBQuarterDIndex from './FsBQuarterD/FsBQuarterDIndex.vue';
import FsBQyReport from './FsBQy/FsBQyReport.vue';
import FsBQyIndex from './FsBQy/FsBQyIndex.vue';
import FsBChangerecordAudit from './FsBChangerecord/FsBChangerecordAudit.vue';
import FsBChangerecord from './FsBChangerecord/FsBChangerecord.vue';
import AssetBInfo from '../asset/AssetBInfo/AssetBInfo.vue'
import AssetDDictory from '../asset/AssetDDictory/AssetDDictory.vue'

import AssetBPeibei from '../asset/AssetBPeibei/AssetBPeibei.vue'
import AssetBInfoPersonal from '../asset/AssetBInfo/AssetBInfoPersonal.vue';
import AssetBPeibeiIndex from '../asset/AssetBPeibei/AssetBPeibeiIndex.vue';
import AssetBInfoIndex from '../asset/AssetBInfo/AssetBInfoIndex.vue';
export default {
  name: "FsShow",
  data() {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: {
    FsBUser,
    FsBExame,
    FsBUserExame,
    FsBHealth,
    FsBQuarter,
    FsBUserHealth,
    FsBQuarterD,
    FsBQy,
    FsBQuarterDIndex,
    FsBQyReport,
    FsBQyIndex,
    FsBChangerecord,
    FsBChangerecordAudit,
    FsBChangerecord,
    AssetBInfo,
    AssetDDictory,
    AssetBPeibei,
    AssetBInfoPersonal,
    AssetBPeibeiIndex,
    AssetBInfoIndex 
  },
  computed: {
    xfk() {
      let permissions = this.$store.state.account.permissions; 
      return !(permissions.includes("ggwsk:no"))
    },
    fsk() {
    let permissions = this.$store.state.account.permissions; 
      return !(permissions.includes("fsk:no"))
    },
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
      this.isShow = false;
      this.subShow = index;
      this.title = title;
      this.$router.push({path: this.$route.path, query: {}})
    },
    Back() {
      this.isShow = true;
      this.$router.push({path: this.$route.path, query: {type: 1}})
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>
<style lang="less" scoped>
.card-area {
  width: 100%;
  height: 100%;
  .row {
    width: 100%;
    height: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
.cuhov:hover {
  background: linear-gradient(to bottom left, #05582f, #24ada0);
}
.cuDiv {
  height: 300px;
  text-align: left;
  padding: 20px;
  margin-bottom: 15px;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
}
.titleDiv {
  width: 100%;
  margin-top: 10px;
  font-weight: bold;
  font-size: 22px;
  line-height: 26px;
  color: #fff;
}
.subDiv {
  width: 100%;
  font-size: 18px;
  line-height: 18px;
  margin-top: 15px;
  color: #fff;
  cursor: pointer;
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
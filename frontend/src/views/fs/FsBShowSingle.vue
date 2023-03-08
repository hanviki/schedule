<template>
  <a-card :bordered="false" class="card-area" >
    <div v-if="isShow" style="text-align: center; display:flex;justify-content:center;">
     
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">人员台账</div>
            <div class="subDiv">
              <a @click="openQulifaction(1, '科室人员台账')" href="#">
                科室人员台账
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(12, '科室人员信息变更申请')" href="#">
                科室人员信息变更申请
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">考试</div>

            <div class="subDiv">
              <a @click="openQulifaction(2, '考试记录维护')" href="#">
                考试记录维护
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(3, '考试提醒报表')" href="#">
                考试提醒报表
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
        <div class="cuDiv"  style="background-color: #dbb752">
          <div class="bigDiv" >
            <div class="titleDiv">体检</div>
            <div class="subDiv">
              <a @click="openQulifaction(4, '体检记录维护')" href="#">
                体检记录维护
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(5, '体检提醒报表')" href="#">
                体检提醒报表
              </a>
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="profile"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>

        <div class="cuDiv"  style="background-color: #05582f">
          <div class="bigDiv" >
            <div class="titleDiv">剂量牌</div>
             <!-- <div class="subDiv">
              <a @click="openQulifaction(6, '剂量牌管理')" href="#">
                剂量牌管理
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(7, '剂量牌领还记录')" href="#">
                剂量牌领还记录
              </a>
            </div>
             <div class="subDiv">
              <a @click="openQulifaction(8, '剂量牌领还分析报表')" href="#">
                剂量牌领还分析报表
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(9, '个人剂量牌当量维护')" href="#">
                个人剂量牌当量维护
              </a>
            </div> -->
             <div class="subDiv">
              <a @click="openQulifaction(10, '甲状腺屏蔽维护')" href="#">
                甲状腺屏蔽维护
              </a>
            </div>
            <div class="subDiv">
              <a @click="openQulifaction(11, '个人剂量牌当量报表')" href="#">
                个人剂量牌当量报表
              </a>
            </div>

          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
    
    </div>
    <div v-else="isShow">
      <a-row
        ><a-col :sm="6"
          ><a-button type="primary"  icon="rollback" @click="Back" >返回</a-button></a-col
        ><a-col :sm="18"
          ><span style="font-size: 20px; text-align: center; color: green">{{
            title
          }}</span></a-col
        ></a-row
      >
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
     <fs-b-changerecord v-show="subShow == 12">
     </fs-b-changerecord>
    </div>
  </a-card>
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
import FsBChangerecord from './FsBChangerecord/FsBChangerecord.vue';

export default {
  name: "BaseInfo",
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
    FsBChangerecord
  },
  methods: {
    openQulifaction(index, title) {
      this.isShow = false;
      this.subShow = index;
      this.title = title;
    },
    Back() {
      this.isShow = true;
    },
  },
};
</script>
<style lang="less" scoped>
.cuhov:hover {
  background: linear-gradient(to bottom left, #05582f, #24ada0);
}
.cuDiv {
  width: 240px;
  height: 180px;
  text-align: left;
  padding: 10px;
  margin-left: 60px;
  margin-top: 20px;
  float: left;
}
.imageDiv {
  float: left;
  width: 50px;
  height: 180px;
  padding-top:10px;
}
.bigDiv {
  float: left;
  width: 170px;
  height: 180px;
  color: white;
}
.titleDiv {
  width: 100%;
  padding-top: 10px;
  font-weight: bold;
  font-size: 20px;
  color: #fff;
}
.subDiv {
  height: 20px;
  width: 100%;
  font-size: 14px;
  padding-top: 5px;
  color: #fff;
  a {
    color: #fff;
  }
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
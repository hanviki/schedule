<template>
  <div class="card-area">
    <!-- <a-button v-show="isShow" class="back_btn" type="primary" icon="rollback" @click="goBack()">首页</a-button> -->
    <a-row v-show="isShow" class="row" :gutter="40">
      <a-col :span="5">
        <div class="cuDiv" style="background-color: #34bab9">
          <div class="bigDiv">
            <div class="titleDiv">信息查询</div>
            <div class="subDiv" @click="openQulifaction(1, '科室人员信息')">
              科室人员信息
            </div>
            <div class="subDiv" @click="openQulifaction(2, '移入移出记录')">
              移入移出记录
            </div>
            <div class="subDiv" @click="openQulifaction(3, '未排班数据')">
              未排班数据
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5">
        <div class="cuDiv" style="background-color: #5561c3">
          <div class="bigDiv">
            <div class="titleDiv">数据审核</div>
            <div class="subDiv" @click="openQulifaction(4, '资质申请审核')">
              资质申请审核
            </div>
            <div class="subDiv" @click="openQulifaction(5, '排班数据审核')">
              排班数据审核
            </div>
            <div class="subDiv" @click="openQulifaction(6, '排班修改申请审核')">
              排班修改申请审核
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5">
        <div class="cuDiv" style="background-color: #00acec">
          <div class="bigDiv">
            <div class="titleDiv">报表查询</div>
            <div class="subDiv" @click="openQulifaction(7, '排班打印')">
              排班打印
            </div>
            <div class="subDiv" @click="openQulifaction(8, '夜班费')">
              夜班费
            </div>
          </div>
        <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="global"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
      </div>
      </a-col>
    </a-row>
    <div v-if="!isShow">
      <div class="row_btn">
        <a-button class="back_btn" type="text" icon="rollback" @click="Back">返回</a-button>
        <div class="title">{{title}}</div>
      </div>
      <dept-user-list v-show="subShow==1"></dept-user-list>
      <sdl-b-zizhiapply-audit v-show="subShow==4"></sdl-b-zizhiapply-audit>
      <sdl-b-schedule-audit v-show="subShow==6"></sdl-b-schedule-audit>
      <sdl-b-schedule-list-audit v-show="subShow==5"></sdl-b-schedule-list-audit>
      <sdl-b-deptchange-list v-show="subShow==2"></sdl-b-deptchange-list>
      <sdl-b-print-report v-show="subShow==7"></sdl-b-print-report>
      <no-submit-report v-show="subShow==3"></no-submit-report>
      <sdl-b-ye-ban-report v-show="subShow==8"></sdl-b-ye-ban-report>
    </div>
  </div>
</template>
<script>

import DeptUserList from '../sdl/SdlBUser/DeptUserList.vue'
import SdlBDeptchangeList from './SdlBDeptchange/SdlBDeptchangeList.vue';
import NoSubmitReport from './SdlBSchedule/NoSubmitReport.vue';
import SdlBScheduleAudit from './SdlBSchedule/SdlBScheduleAudit.vue';
import SdlBScheduleListAudit from './SdlBSchedule/SdlBScheduleListAudit.vue';
import SdlBYeBanReport from './SdlBSchedule/SdlBYeBanReport.vue';
import SdlBPrintReport from './SdlBScheduleD/SdlBPrintReport.vue';
import SdlBZizhiapplyAudit from './SdlBZizhiapply/SdlBZizhiapplyAudit.vue';

export default {
  name: "SdlManage",
  data() {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: {
    DeptUserList,
    SdlBZizhiapplyAudit,
    SdlBScheduleAudit,
    SdlBScheduleListAudit,
    SdlBDeptchangeList,
    SdlBPrintReport,
    NoSubmitReport,
    SdlBYeBanReport
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
    height: 70%;
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
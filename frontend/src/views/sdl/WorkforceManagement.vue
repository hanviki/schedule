<template>
  <div :class="['card-area', 'card-area-bg']">
    <a-row class="row" :gutter="40">
      <a-col :span="6">
        <div class="cuDiv"  style="background-color: #1c998c; cursor: pointer;">
          <div class="bigDiv" @click="$router.push({path: '/sdl/SdlBUser/DeptUserList'})">
            <div class="titleDiv">科室人员信息</div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">排班信息维护</div>
            <div class="subDiv" @click="$router.push({path: '/sdl/SdlBZizhiapply/SdlBZizhiapplyList'})">
               资质变更申请
            </div>
            <div class="subDiv" @click="$router.push({path: '/sdl/SdlBSchedule/SdlBScheduleList'})">
               排班维护
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="profile"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="cuDiv"  style="background-color: #dbb752; cursor: pointer;">
          <div class="bigDiv" >
            <div class="titleDiv">报表查询</div>
            <div class="subDiv" @click="$router.push({path: '/sdl/SdlBDeptchange/SdlBDeptchangeList'})">
              移入移出记录
            </div>
            <div class="subDiv" @click="$router.push({path: '/sdl/SdlBSchedule/SdlBDeptReport'})">
              科室排班打印
            </div>
            <div class="subDiv" @click="$router.push({path: '/sdl/SdlBSchedule/SdlBYeBanReportSingle'})">
               夜班费(科室)
            </div>
            <div class="subDiv" @click="$router.push({path: '/sdl/SdlBSchedule/SdlBMenzhenReportSingle'})">
               门诊值班费(科室)
            </div>
            <div class="subDiv" @click="$router.push({path: '/sdl/SdlBSchedule/SdlBReportSingle'})">
              节假日排班报表(科室)
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
    </a-row>
    </div>
  </div>
</template>
<script>

export default {
  name: 'fileManagement',
  data() {
    return {
      subShow: 0,
      title: "",
    };
  },
  components: {
  },
  methods: {

  },
};
</script>
<style lang="less" scoped>
.card-area-bg{
  background: url('/static/img/bga.png') no-repeat 100% 100% !important;
}
.card-area {
  width: 100%;
  height: 100%;
  .row {
    width: 100%;
    height: 70%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0;
    padding-top: 50px;
  }
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
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
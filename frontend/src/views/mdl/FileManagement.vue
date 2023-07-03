<template>
  <div :class="['card-area', 'card-area-bg']">
    <a-row class="row" :gutter="40">
      <a-col :span="6">
        <div class="cuDiv"  style="background-color: #1c998c; cursor: pointer;">
          <div class="bigDiv" >
            <div class="titleDiv">个人信息查询及维护</div>
            <div class="subDiv" @click="$router.push({path: '/mdl/PersonalInfo'})">
              个人资料
            </div>
            <div class="subDiv" @click="$router.push({path: '/fs/FsBQy/FsBQySingle'})">
              放射辐射甲状腺屏蔽维护
            </div>
            <div class="subDiv" @click="$router.push({path: '/fs/FsBExameSingle/FsBExame'})">
              放射辐射考试记录维护
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">新技术新业务管理</div>
            <div class="subDiv" @click="$router.push({path: '/xxb/DeptFlowInfo', query: {type: 1} })">
               新技术新业务审核
            </div>
            <div class="subDiv" @click="$router.push({path: '/xxb/BaseInfo'})">
               新技术新业务申报
            </div>
            <div class="subDiv" @click="$router.push({path: '/xxb/XxbBZq/XxbBZq'})">
              项目中期反馈
            </div>
            <div class="subDiv" @click="$router.push({path: '/xxb/XxbBMq/XxbBMq'})">
              项目总结反馈
            </div>
            <div class="subDiv" @click="$router.push({path: '/xxb/XxbBCheck/XxbBCheckAuditReport'})">
              新技术新业务审核情况查询
            </div>
            <div class="subDiv" @click="$router.push({path: '/xxb/XxbBCheck/XxbBCheckSingleReport'})">
              新技术新业务申报情况查询
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="profile"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="cuDiv"  style="background-color: #dbb752; cursor: pointer;">
          <div class="bigDiv" >
            <div class="titleDiv">新技术新业务成果奖</div>
            <div class="subDiv" @click="$router.push({path: '/xxb/XxbBResultFlow'})">
              新技术新业务成果奖审核
            </div>
            <div class="subDiv" @click="$router.push({path: '/xxb/XxbBResult'})">
              新技术新业务成果奖申报
            </div>
             <div class="subDiv" @click="$router.push({path: '/xxb/XxbBResultFlow/XxbBResultReportSingle'})">
              新技术新业务成果奖申报情况查询
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
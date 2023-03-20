<template>
  <div class="adminTecShow">
    <a-row v-if="isShow" class="row" :gutter="40">
      <a-col :span="5" >
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">新技术新业务立项</div>
            <div class="subDiv" @click="openQulifaction(1, '部门负责人设置')">
              部门负责人设置
            </div>
            <div class="subDiv" @click="openQulifaction(2, '新技术新业务审核')">
              新技术新业务审核
            </div>
            <div class="subDiv" @click="openQulifaction(3, '新技术新业务上会文件')">
              新技术新业务上会文件
            </div>
            <div class="subDiv" @click="openQulifaction(4, '项目中期反馈审核')">
              项目中期反馈审核
            </div>
            <div class="subDiv" @click="openQulifaction(5, '项目总结反馈审核')">
              项目总结反馈审核
            </div>
            <div class="subDiv" @click="openQulifaction(6, '新技术新业务报表')">
              新技术新业务报表
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div class="cuDiv"  style="background-color: #dbb752">
          <div class="bigDiv">
            <div class="titleDiv">新技术新业务成果报奖</div>
            <div class="subDiv" @click="openQulifaction(21, '新技术新业务成果奖审核')">
              新技术新业务成果奖审核
            </div>
            <div class="subDiv" @click="openQulifaction(22, '新技术新业务成果奖报表')">
              新技术新业务成果奖报表
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white" ><a-icon type="global"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">手术资质</div>
            <div class="subDiv" @click="openQulifaction(7, '手术目录')">
              手术目录
            </div>
            <div class="subDiv" @click="openQulifaction(8, '手术资质')">
              手术资质
            </div>
            <div class="subDiv" @click="openQulifaction(23, '中医医疗技术目录及授权表')">
              中医医疗技术目录及授权表
            </div>
            <div class="subDiv" @click="openQulifaction(24, '操作分级管理目录')">
              操作分级管理目录
            </div>
             <div class="subDiv" @click="openQulifaction(25, '操作分级目录及资质授权')">
              操作分级目录及资质授权
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5" >
        <div class="cuDiv"  style="background-color: #79c346">
          <div class="bigDiv">
            <div class="titleDiv">新技术新业务历史记录</div>
            <div class="subDiv" @click="openQulifaction(9, '（1998-2021年）新技术新业务立项总结')">
              立项总结
            </div>
            <div class="subDiv" @click="openQulifaction(10, '历年新技术新业务获奖名单')">
              历年新技术新业务获奖名单
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
    </a-row>
    <div v-if="!isShow">
      <div class="title">{{title}}</div>
      <xxb-b-deptleader v-if="subShow == 1"></xxb-b-deptleader>
      <xxb-b-flow-ywc v-if="subShow == 2"></xxb-b-flow-ywc>
      <xxb-b-archive v-if="subShow == 3"></xxb-b-archive>
      <xxb-b-zq-audit v-if="subShow == 4"></xxb-b-zq-audit>
      <xxb-b-mq-audit v-if="subShow == 5"></xxb-b-mq-audit>
     <xxb-b-check-report v-if="subShow == 6"></xxb-b-check-report>
     <mdl-d-surgery v-if="subShow == 7"></mdl-d-surgery>
     <mdl-b-surgeryinfo v-if="subShow==8" ></mdl-b-surgeryinfo>
     <mdl-d-mdt-old v-if="subShow==9">
     </mdl-d-mdt-old>
     <mdl-d-prize-old v-if="subShow==10">
     </mdl-d-prize-old>
     <xxb-b-result-ywc-flow v-if="subShow==21">
     </xxb-b-result-ywc-flow>
     <xxb-b-result-report v-if="subShow==22">
     </xxb-b-result-report>
     <mdl-b-medicalchina v-if="subShow==23"></mdl-b-medicalchina>
     <mdl-d-operation v-if="subShow==24"></mdl-d-operation>
     <mdl-d-operation-doctor v-if="subShow==25"></mdl-d-operation-doctor>
    </div>
  </div>
</template>
<script>
import XxbBArchive from '../xxb/XxbBArchive/XxbBArchive.vue';
import XxbBCheckReport from '../xxb/XxbBCheck/XxbBCheckReport.vue';
import XxbBDeptleader from '../xxb/XxbBDeptleader/XxbBDeptleader.vue';
import XxbBFlowYwc from '../xxb/XxbBFlowYwc/XxbBFlowYwc.vue';
import XxbBMqAudit from '../xxb/XxbBMq/XxbBMqAudit.vue';
import XxbBZqAudit from '../xxb/XxbBZq/XxbBZqAudit.vue';
import MdlDSurgery from './MdlDSurgery/MdlDSurgery.vue';
import MdlBSurgeryinfo from './MdlBSurgeryinfo/MdlBSurgeryinfo.vue'
import MdlDMdtOld from './MdlDMdtOld/MdlDMdtOld.vue'
import MdlDPrizeOld from './MdlDPrizeOld/MdlDPrizeOld.vue'
import XxbBResultReport from '../xxb/XxbBResultFlow/XxbBResultReport.vue'
import XxbBResultYwcFlow from '../xxb/XxbBResultFlow/XxbBResultYwcFlow.vue'
import MdlBMedicalchina from '../mdl/MdlBMedicalchina/MdlBMedicalchina.vue'
import MdlDOperation from './MdlDOperation/MdlDOperation.vue';
import MdlYwc from './MdlYwc.vue';
import MdlDOperationDoctor from './MdlDOperationDoctor/MdlDOperationDoctor.vue';

export default {
  name: "AdminTecShow",
  data() {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: {
    XxbBDeptleader,
    XxbBFlowYwc,
    XxbBArchive,
    XxbBZqAudit,
    XxbBMqAudit,
    XxbBCheckReport,
    MdlDSurgery,
    MdlBSurgeryinfo,
    MdlDMdtOld,
    MdlDPrizeOld,
    XxbBResultYwcFlow,
    XxbBResultReport,
    MdlBMedicalchina,
    MdlDOperation,
    MdlYwc,
    MdlDOperationDoctor
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
.adminTecShow {
  width: 100%;
  margin-top: 20px;
  .row {
    width: 100%;
    height: 70%;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    flex-wrap: wrap;
  }
}
.cuhov:hover {
  background: linear-gradient(to bottom left, #05582f, #24ada0);
}
.cuDiv {
  height: 250px;
  text-align: left;
  padding: 20px;
  margin-bottom: 15px;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
}
.imageDiv {
}
.bigDiv {
  color: white;
}
.titleDiv {
  margin-bottom: 10px;
  font-weight: bold;
  font-size: 22px;
  line-height: 26px;
  color: #fff;
}
.subDiv {
  font-size: 18px;
  padding-top: 5px;
  color: #fff;
  cursor: pointer;
}
.cuDiv:hover {
  opacity: 0.7;
  color: #000;
}
.subDiv:hover {
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
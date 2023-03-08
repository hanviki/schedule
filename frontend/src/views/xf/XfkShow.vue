<template>
  <div class="adminBL">
    <div v-if="isShow">
    <!-- <a-button v-show="isShow" class="back_btn" type="primary" icon="rollback" @click="goBack()">首页</a-button> -->
    <a-row  class="row" :gutter="40">
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">基础数据管理</div>
            <div class="subDiv" @click="openQulifaction(1, '人员信息管理库')">
              人员信息管理库
            </div>
             <div class="subDiv" @click="openQulifaction(2, '行风建设小组名单库')">
              行风建设小组名单库
            </div>
             <div class="subDiv" @click="openQulifaction(3, '社会监督员库')">
              社会监督员库
            </div>
            <div class="subDiv" @click="openQulifaction(4, '文件管理库')">
              文件管理库
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #a874c7">
          <div class="bigDiv">
            <div class="titleDiv">行风办评价</div>
            <div class="subDiv" @click="openQulifaction(5, '评优评先')">
              评优评先
            </div>
            <div class="subDiv" @click="openQulifaction(6, '职称晋升')">
              职称晋升
            </div>
             <div class="subDiv" @click="openQulifaction(7, '人才项目')">
              人才项目
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #dbb752">
          <div class="bigDiv" >
            <div class="titleDiv">行风日常工作</div>
            <div class="subDiv" @click="openQulifaction(8, '行风有效投诉')">
              行风有效投诉
            </div>
            <div class="subDiv" @click="openQulifaction(23, '行风不良记分')">
              行风不良记分
            </div>
            <div class="subDiv" @click="openQulifaction(9, '内部谈话记录库')">
              内部谈话记录库
            </div>
             <div class="subDiv" @click="openQulifaction(10, '自查自纠情况-科室')">
              自查自纠情况-科室
            </div>
             <div class="subDiv" @click="openQulifaction(11, '自查自纠情况-人员')">
              自查自纠情况-人员
            </div>
            <div class="subDiv" @click="openQulifaction(21, '培训情况')">
              培训情况
            </div>
             <div class="subDiv" @click="openQulifaction(12, '考核情况')">
              考核情况
            </div>
              <div class="subDiv"  @click="openQulifaction(19, '行风建设普通会议')">
              行风建设普通会议
            </div>
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="profile"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
      </a-col>
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #79c346">
          <div class="bigDiv" >
            <div class="titleDiv">行风重点工作</div>
            <div class="subDiv"  @click="openQulifaction(13, '重点专科宣教记录')">
              重点专科宣教记录
            </div>
            <div class="subDiv"  @click="openQulifaction(14, '重点专科报告记录')">
              重点专科报告记录
            </div>
            <div class="subDiv"  @click="openQulifaction(15, '行风建设工作要点会议')">
              行风建设工作要点会议
            </div>
            <div class="subDiv"  @click="openQulifaction(16, '各科室合理用药和用耗的规范')">
              各科室合理用药和用耗的规范
            </div>
            <div class="subDiv" @click="openQulifaction(17, '专项治理')">
              专项治理
            </div>
            <div class="subDiv" @click="openQulifaction(18, '专项汇报')">
              专项汇报
            </div>
          
          </div>
          <div class="imageDiv">
            <a-avatar :size="60" style=" backgroundColor:white" >
              <a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" />
            </a-avatar>
          </div>
        </div>
      </a-col>
    </a-row>
       <a-row  class="row" :gutter="40">
        <a-col :span="5">
        <div class="cuDiv"  style="background-color: #51cbdb">
          <div class="bigDiv" >
            <div class="titleDiv">行风个人报告</div>
            <div class="subDiv"  @click="openQulifaction(20, '行风建设个人报告')">
              行风建设个人报告
            </div>
             <div class="subDiv"  @click="openQulifaction(22, '科室行风建设报告')">
              科室行风建设报告
            </div>
           
          </div>
          <div class="imageDiv">
            <a-avatar :size="60" style=" backgroundColor:white" >
              <a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" />
            </a-avatar>
          </div>
        </div>
      </a-col>
       <a-col :span="15">
       </a-col>
    </a-row>
    </div>
    <div v-if="!isShow">
      
      <div class="row_btn">
        <a-button class="back_btn" type="text" icon="rollback" @click="Back">返回</a-button>
        <div class="title">{{title}}</div>
      </div>
      <!-- <chu-fang-info v-show="subShow==0">
        </chu-fang-info> -->
     <user-info v-show="subShow==1">
     </user-info>
     <xf-b-user v-show="subShow==2">
     </xf-b-user>
     <xf-b-social v-show="subShow==3">
     </xf-b-social>
     <xf-b-archive v-show="subShow==4">
     </xf-b-archive>
     <xf-b-asset v-show="subShow==5">
      </xf-b-asset>
      
      <xf-b-person v-show="subShow==7"></xf-b-person>
      <xf-b-tousu v-show="subShow==8">
      </xf-b-tousu>
      <xf-b-innertalk v-show="subShow==9">
      </xf-b-innertalk>
      <xf-b-errordept v-show="subShow==10">
      </xf-b-errordept>
       <xf-b-errorperson  v-show="subShow==11">
       </xf-b-errorperson>
       <xf-b-teachrecord v-show="subShow==13">
       </xf-b-teachrecord>
       <xf-b-reportrecord  v-show="subShow==14">
       </xf-b-reportrecord>
       <xf-b-meeting v-show="subShow==15">
       </xf-b-meeting>
       <xf-b-rules v-show="subShow==16">
       </xf-b-rules>
       <xf-b-project v-show="subShow==17">
       </xf-b-project>
       <xf-b-projectreport v-show="subShow==18">
       </xf-b-projectreport>
       <xf-b-conference v-show="subShow==19">
       </xf-b-conference>
       <xf-b-user-person v-show="subShow==20">
       </xf-b-user-person>
       <mdl-b-peixun v-show="subShow==12">
       </mdl-b-peixun>
       <mdl-b-kaoshi v-show="subShow==21"></mdl-b-kaoshi>
       <xf-b-dept v-show="subShow==22"></xf-b-dept>
       <xf-b-zhicheng v-show="subShow==6">
      </xf-b-zhicheng>
      <mdl-b-badrecord v-show="subShow==23">
      </mdl-b-badrecord>
    </div>
  </div>
</template>
<script>
import MdlBBadrecord from '../mdl/MdlBBadrecord/MdlBBadrecord.vue';
import MdlBKaoshi from '../mdl/MdlBKaoshi/MdlBKaoshi.vue';
import MdlBPeixun from '../mdl/MdlBPeixun/MdlBPeixun.vue';
import UserInfo from './UserInfo.vue';
import XfBArchive from './XfBArchive/XfBArchive.vue';
import XfBAsset from './XfBAsset/XfBAsset.vue';
import XfBConference from './XfBConference/XfBConference.vue';
import XfBDept from './XfBDept.vue';
import XfBErrordept from './XfBErrordept/XfBErrordept.vue';
import XfBErrorperson from './XfBErrorperson/XfBErrorperson.vue';
import XfBInnertalk from './XfBInnertalk/XfBInnertalk.vue';
import XfBMeeting from './XfBMeeting/XfBMeeting.vue';
import XfBPerson from './XfBPerson/XfBPerson.vue';
import XfBProject from './XfBProject/XfBProject.vue';
import XfBProjectreport from './XfBProjectreport/XfBProjectreport.vue';
import XfBReportrecord from './XfBReportrecord/XfBReportrecord.vue';
import XfBRules from './XfBRules/XfBRules.vue';
import XfBSocial from './XfBSocial/XfBSocial.vue';
import XfBTeachrecord from './XfBTeachrecord/XfBTeachrecord.vue';
import XfBTousu from './XfBTousu/XfBTousu.vue';
import XfBUser from './XfBUser/XfBUser.vue';
import XfBUserPerson from './XfBUserPerson.vue';
import XfBZhicheng from './XfBZhicheng.vue';



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
    UserInfo,
    XfBArchive,
    XfBAsset,
    XfBPerson,
    XfBTousu,
    XfBInnertalk,
    XfBErrordept,
    XfBErrorperson,
    XfBTeachrecord,
    XfBReportrecord,
    XfBMeeting,
    XfBProject,
    XfBProjectreport,
    XfBRules,
    XfBSocial,
    XfBUser,
    XfBConference,
    XfBUserPerson,
    MdlBPeixun,
    MdlBKaoshi,
    XfBDept,
    XfBZhicheng,
    MdlBBadrecord
  
  },
  computed: {
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
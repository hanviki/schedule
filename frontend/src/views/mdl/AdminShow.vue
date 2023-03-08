<template>
  <div class="adminShow" >
    <div v-if="isShow">
      <a-row class="row" :gutter="40">
        <a-col :span="5" >
          <div class="cuDiv" style="background-color: #1c998c">
            <div class="bigDiv">
              <div class="titleDiv">处方权管理</div>
                <div class="subDiv" @click="openQulifaction(6, '授权文件')">
                    授权文件
                </div>
                <div class="subDiv" @click="openQulifaction(7, '考试管理')">
                  考试管理
                </div>
                <div class="subDiv" @click="openQulifaction(12, '基本处方权')">
                  基本处方权
                </div>
                <div class="subDiv" @click="openQulifaction(8, '麻精药物处方权')">
                  麻精药物处方权
                </div>
                <div class="subDiv" @click="openQulifaction(9, '抗菌药物分级管理')">
                  抗菌药物分级管理
                </div>
                <div class="subDiv" @click="openQulifaction(10, '抗肿瘤药物分级管理')">
                  抗肿瘤药物分级管理
                </div>
              </div>
              <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
          </div>
        </a-col>
        <a-col :span="5" >
          <div class="cuDiv" style="background-color: #a874c7">
            <div class="bigDiv">
              <div class="titleDiv">会诊资质</div>
              <div class="subDiv" @click="openQulifaction(20, '单科会诊')">
                单科会诊
              </div>
              <div class="subDiv" @click="openQulifaction(21, '多学科会诊')">
                多学科会诊
              </div>
            </div>
            <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;" ><a-icon type="user"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
          </div>
        </a-col>
        <a-col :span="5" >
          <div class="cuDiv" @click="openQulifaction(1, '值班资质')" style="background-color: #dbb752">
            <div class="bigDiv" >
              <div class="titleDiv">值班资质</div>
            </div>
            <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="profile"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
          </div>
        </a-col>
      </a-row>
      <a-row v-if="isShow" class="row" :gutter="40">
        <a-col :span="5" >
          <div class="cuDiv" @click="openQulifaction(3, 'MDT团队管理')" style="background-color: #51cbdb">
            <div class="bigDiv" >
              <div class="titleDiv">MDT团队管理</div>
            </div>
            <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="project"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
          </div>
        </a-col>
        <a-col :span="5" >
          <div class="cuDiv" style="background-color: #79c346">
            <div class="bigDiv">
              <div class="titleDiv">其他资质</div>
              <div class="subDiv" @click="openQulifaction(4, '专业类资质')">
                专业类资质
              </div>
              <div class="subDiv" @click="openQulifaction(5, '行业类资质')">
                行业类资质
              </div>
            </div>
            <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="global"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
          </div>
        </a-col>
        <a-col :span="5" >
         <div class="cuDiv" @click="openQulifaction(22, '专科实验室资质')"  style="background-color: #05582f">
            <div class="bigDiv">
              <div class="titleDiv">专科实验室资质</div>
            </div>
            <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="wallet"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
          </div> 
        </a-col>
        <!-- <a-col :span="4" >
          <div class="cuDiv" @click="openQulifaction(5, '行业类资质')"  style="background-color: #05582f">
            <div class="bigDiv">
              <div class="titleDiv">行业类资质</div>
            </div>
            <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="global"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
          </div>
        </a-col> -->
        <!-- <a-col :span="4" >
          <div class="cuDiv" @click="openQulifaction(6, '授权文件')" style="background-color: #1c998c">
            <div class="bigDiv" >
              <div class="titleDiv">授权文件</div>
            </div>
            <div class="imageDiv"><a-avatar :size="50" style=" backgroundColor:white;padding-top:5px;" ><a-icon type="file-done"  style="font-size: 30px;color:green;" :bordered="false" /></a-avatar></div>
          </div>
        </a-col> -->
      </a-row>
    </div>
    <div v-if="!isShow">
      <div class="title">{{title}}</div>
      <!-- <a-row>
        <a-col :sm="6">
          <a-button type="primary"  icon="rollback" @click="Back" >返回</a-button>
        </a-col>
        <a-col :sm="18">
          <span style="font-size: 20px; text-align: center; color: green">{{title}}</span>
        </a-col>
      </a-row> -->
      <!-- <chu-fang-info v-show="subShow==0">
        </chu-fang-info> -->
      <mdl-b-special v-show="subShow == 4"> </mdl-b-special>
      <mdl-b-profession v-show="subShow == 5"> </mdl-b-profession>
      <schedule-info v-show="subShow == 1"> </schedule-info>
      <mdl-b-archive-list v-show="subShow == 6"> </mdl-b-archive-list>
      <mdl-b-chufang v-show="subShow == 7" type="考试管理"> </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 8" type="麻精药物处方权">
      </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 9" type="抗菌药物分级管理">
      </mdl-b-chufang>
      <mdl-b-chufang v-show="subShow == 10" type="抗肿瘤药物分级管理">
      </mdl-b-chufang>
      <chu-fang-report v-show="subShow == 12" type="基本处方权">
      </chu-fang-report>
      <hui-zhen v-show="subShow == 20" type="单科会诊"> </hui-zhen>
      <hui-zhen v-show="subShow == 21" type="多学科会诊"> </hui-zhen>
      <mdl-b-mdt v-show="subShow == 3" type="MDT团队管理"></mdl-b-mdt>
      <mdl-l-baseinfo v-show="subShow == 22">
      </mdl-l-baseinfo>
    </div>
  </div>
</template>
<script>
import MdlBSpecial from "./MdlBSpecial/MdlBSpecial.vue";
import MdlBProfession from "./MdlBProfession/MdlBProfession.vue";
import ScheduleInfo from "./MdlBManager/ScheduleInfo.vue";
import MdlBArchiveList from "./MdlBArchive/MdlBArchiveList.vue";
import MdlBChufang from "./MdlBChufang/MdlBChufang.vue";
import HuiZhen from "./MdlBManager/HuiZhen.vue";
import ChuFangReport from "./MdlBChufang/ChuFangReport.vue";
import MdlBMdt from './MdlBMdt/MdlBMdt.vue';
import MdlLBaseinfo from './MdlLBaseinfo/MdlLBaseinfo.vue';

export default {
  name: "AdminShow",
  data () {
    return {
      isShow: true,
      subShow: 0,
      title: "",
    };
  },
  components: {
    MdlBSpecial,
    MdlBProfession,
    ScheduleInfo,
    MdlBArchiveList,
    MdlBChufang,
    HuiZhen,
    ChuFangReport,
    MdlBMdt,
    MdlLBaseinfo,
    ChuFangReport,
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
.adminShow{
  width: 100%;
  margin-top: 20px;
  .row {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    margin-bottom: 30px;
  }
}
.cuhov:hover {
  background: linear-gradient(to bottom left, #05582f, #24ada0);
}
.cuDiv {
  height: 250px;
  text-align: left;
  padding: 20px;
  margin-top: 5px;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
}
.imageDiv {
  // float: left;
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
  color: #fff;
  margin-bottom: 4px;
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
<template>
  <div class="card-area">
    <!-- <a-button v-show="isShow" class="back_btn" type="primary" icon="rollback" @click="goBack()">首页</a-button> -->
    <a-row v-if="isShow" class="row" :gutter="40">
      <a-col :span="5">
        <div class="cuDiv"  style="background-color: #1c998c">
          <div class="bigDiv">
            <div class="titleDiv">证件管理</div>
            <div class="subDiv" @click="openQulifaction(1, '科室证件管理')">
              科室证件管理
            </div>
            
          </div>
          <div class="imageDiv"><a-avatar :size="60" style=" backgroundColor:white" ><a-icon type="home"  style="font-size: 30px;color:green" :bordered="false" /></a-avatar></div>
        </div>
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
    <dept-b-user v-show="subShow==1"></dept-b-user>
    
    </div>
  </div>
</template>
<script>
import DeptBUser from '../dept/DeptBUser/DeptBUser.vue';

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
    DeptBUser
    
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
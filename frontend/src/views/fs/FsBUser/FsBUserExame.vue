<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
           
            <a-col :md="6" :sm="24">
              <a-form-item label="人事编号/姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
             <a-col :md="6" :sm="24">
              <a-form-item label="科室" v-bind="formItemLayout">
                <a-input v-model="queryParams.deptName" />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="考试类型" v-bind="formItemLayout">
                <a-select v-model="queryParams.exameType" >
                   <a-select-option :value="-1">
                     全部
                   </a-select-option>
                   <a-select-option value="卫生考试">
                     卫生考试
                   </a-select-option>
                   <a-select-option value="环保考试">
                     环保考试
                   </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
           
        
          </div>
          <span style="float: right; margin-top: 3px">
            
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
            <a @click="toggleAdvanced" style="margin-left: 8px">
              {{ advanced ? "收起" : "展开" }}
              <a-icon :type="advanced ? 'up' : 'down'" />
            </a>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
         <a-button
                  icon="file-excel"
                  @click="exportCustomExcel"
                  style="color: #fff;background-color: #faad14;border-color: #faad14;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
                >导出</a-button>
                <div style="color:red;"> 环保考试：微信线上小程序“HJSLY”报名，根据准备考证信息前往指定地点参加机考，获得成绩报告单后，《成绩报告单》截屏保存 卫生考试：微信线上考试，通过“健康湖北”公众号——“活动与查询”进入，不限次数，考试及格后，截屏保存带身份证件号、考试编号、成绩的《考试合格证明》</div>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.userAccount"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange,
        }"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      >
        <template slot="remark" slot-scope="text, record">
          <a-popover placement="topLeft">
            <template slot="content">
              <div style="max-width: 200px">{{ text }}</div>
            </template>
            <p style="width: 200px; margin-bottom: 0">{{ text }}</p>
          </a-popover>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
        </template>
      </a-table>
    </div>
    <sdlUser-info
      ref="sdlBUserInfoAdd"
      @close="handleAddClose"
      @succ="handleSucc"
      :userInfoVisiable="addVisiable"
    >
    </sdlUser-info>
  <fs-b-user-edit
      ref="sdlBUserEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
  >
  </fs-b-user-edit>
  </a-card>
</template>

<script>
import FsBUserEdit from './FsBUserEdit.vue';
import SdlUserInfo from "./SdlUserInfo.vue";
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "SdlBUser",
  components: { SdlUserInfo, FsBUserEdit },
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: {},
      addVisiable: false,
      editVisiable: false,
      loading: false,
      bordered: true,
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
         {
          title: "人事编码",
          dataIndex: "userAccount",
          width: 120,
        },
           {
          title: "个人剂量编号",
          dataIndex: "grjlbh",
          width: 100,
        },
           {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
         {
          title: "科室编号",
          dataIndex: "deptId",
          width: 100,
        },
         {
          title: "科室名称",
          dataIndex: "deptName",
          width: 100,
        },
        
         {
          title: "放射工作人员证职业类别",
          dataIndex: "zylb",
          width: 100,
        },
         {
          title: "岗位类别",
          dataIndex: "gwlb",
          width: 100,
        },
          {
          title: "放射工龄",
          dataIndex: "fsgl",
          width: 100,
        },
          {
          title: "核定计量牌个数",
          dataIndex: "hdjlpgs",
          width: 100,
        },
          {
          title: "人员状态",
          dataIndex: "state",
          customRender: (text, row, index) => {
            switch (text) {
              case '0':
                return <a-tag color="red">离职</a-tag>;
              case '2':
                return <a-tag color="cyan">退休</a-tag>;
                case '3':
                return <a-tag color="green">在职</a-tag>;
            }
          },
          width: 100,
        },
       
         {
          title: "放射工作人员证号码",
          dataIndex: "fsgzzh",
          width: 100,
        },
         {
          title: "监测状态",
          dataIndex: "jcState",
           customRender: (text, row, index) => {
            switch (text) {
              case '0':
                return <a-tag color="green">开始监测</a-tag>;
              case '1':
                return <a-tag color="red">暂停监测</a-tag>;
              default :
                return ''
            }
          },
          width: 100,
        },
       
         {
          title: "员工工号",
          dataIndex: "yggh",
          width: 100,
        },
       
       
        {
          title: "考试类型",
          dataIndex: "exameType",
          width: 100,
        },
         {
          title: "上次考试时间",
          dataIndex: "exameDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "考试截至日期",
          dataIndex: "valid",
          width: 100,
        },
        
       
       
      ];
    },
  },
  mounted() {
    this.fetch();
  },
  methods: {
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    toggleAdvanced() {
      this.advanced = !this.advanced;
      if (!this.advanced) {
        this.queryParams.comments = "";
      }
    },
    handleAddSuccess() {
      this.addVisiable = false;
      this.$message.success("新增成功");
      this.search();
    },
    handleAddClose() {
      this.addVisiable = false;
     // this.$message.success("移入成功");
     // this.search();
    },
     handleSucc() {
      this.addVisiable = false;
      this.$message.success("移入成功");
      this.search();
    },
    add() {
      this.addVisiable = true;
    },
    handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("修改成功");
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    edit(record) {
      this.$refs.sdlBUserEdit.setFormValues(record);
      this.editVisiable = true;
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要移出的记录");
        return;
      }
      if (this.selectedRowKeys.length > 1) {
        this.$message.warning("请只选择一条移出的记录");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定移出所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被从本科室移出",
        centered: true,
        onOk() {
          let sdlBUserIds = that.selectedRowKeys.join(",");
          that
            .$post("fsBDeptchange/delete", {
              userAccount: sdlBUserIds,
              operationName: "移出",
            })
            .then(() => {
              
              that.$message.success("移出成功");
             
              that.selectedRowKeys = [];
              that.search();
            });
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = [...this.columns]
      //json.splice(json.length-1,1);
     
      let dataJson = JSON.stringify(json)
      let params= this.queryParams
      if (params.jcState=="-1"){
        delete params.jcState
      }
       if (params.state=="-1"){
        delete params.state
      }
      if(params.isDf2=="有"){
         params.hdjlpgs=2
      }
      if(params.isDf2=="无"){
         params.hdjlpgs=1
      }
       if(params.isDf2=="-1"){
         delete params.hdjlpgs
      }
    
      if(params.isDf5=="有"){
         params.state=5
      }
      if(params.isDf5=="无"){
         params.state=4
      }
       if(params.isDf5=="-1"){
         delete params.state
      }
       if(params.exameType=="-1"){
         delete params.exameType
      }
      
      this.$export('fsBUser/excelExame', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        dataJson: dataJson,
        ...params
      })
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
      });
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent;
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
        this.paginationInfo.pageSize = this.pagination.defaultPageSize;
      }
      // 重置列排序规则
      this.sortedInfo = null;
      this.paginationInfo = null;
      // 重置查询参数
      this.queryParams = {};
      this.fetch();
    },
    handleTableChange(pagination, filters, sorter) {
      this.sortedInfo = sorter;
      this.paginationInfo = pagination;
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
      });
    },
    fetch(params = {}) {
      this.loading = true;
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current;
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize;
        params.pageSize = this.paginationInfo.pageSize;
        params.pageNum = this.paginationInfo.current;
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize;
        params.pageNum = this.pagination.defaultCurrent;
      }
      if (params.jcState=="-1"){
        delete params.jcState
      }
       if (params.state=="-1"){
        delete params.state
      }
      if(params.isDf2=="有"){
         params.hdjlpgs=2
      }
      if(params.isDf2=="无"){
         params.hdjlpgs=1
      }
       if(params.isDf2=="-1"){
         delete params.hdjlpgs
      }
    
      if(params.isDf5=="有"){
         params.state=5
      }
      if(params.isDf5=="无"){
         params.state=4
      }
       if(params.isDf5=="-1"){
         delete params.state
      }
       if(params.exameType=="-1"){
         delete params.exameType
      }
      
      this.$get("fsBUser/exameTip", {
        ...params,
      }).then((r) => {
        let data = r.data;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.loading = false;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>

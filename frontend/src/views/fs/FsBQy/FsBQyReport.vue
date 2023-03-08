<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="4" :sm="24">
              <a-form-item label="发薪号" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
             <a-col :md="4" :sm="24">
              <a-form-item label="计量牌号" v-bind="formItemLayout">
                <a-input v-model="queryParams.number" />
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="24">
              <a-form-item label="开始年度" v-bind="formItemLayout">
                <a-input v-model="queryParams.year1" />
                
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="24">
              <a-form-item label="开始季度" v-bind="formItemLayout">
                <a-select v-model="queryParams.quarter1">
                    <a-select-option value="-1">
                  全部
                </a-select-option>
                <a-select-option value="1">
                  1
                </a-select-option>
                <a-select-option value="2">
                  2
                </a-select-option>
                <a-select-option value="3">
                  3
                </a-select-option>
                <a-select-option value="4">
                  4
                </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
             <a-col :md="4" :sm="24">
              <a-form-item label="结束年度" v-bind="formItemLayout">
                <a-input v-model="queryParams.year2" />
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="24">
              <a-form-item label="结束季度" v-bind="formItemLayout">
                 <a-select v-model="queryParams.quarter2">
                <a-select-option value="-1">
                  全部
                </a-select-option>
                <a-select-option value="1">
                  1
                </a-select-option>
                <a-select-option value="2">
                  2
                </a-select-option>
                <a-select-option value="3">
                  3
                </a-select-option>
                <a-select-option value="4">
                  4
                </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="4" :sm="24">
              <a-form-item label="总量>5" v-bind="formItemLayout">
                <a-checkbox v-model="queryParams.isDf" 
                  ></a-checkbox>
              </a-form-item>
            </a-col>
            </template>
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
         <a-button   @click="exportExcel" icon="file-excel" 
          style="color: #fff;background-color: #faad14;border-color: #faad14;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
          >导出</a-button
        >
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
        :scroll="{ x: 1200 }"
        :expandedRowKeys="expandedRowKeys"
        @expand="expandSubGrid"
      >
        <a-table
          ref="subTable"
          slot="expandedRowRender"
          slot-scope="record"
          :columns="innerColumns"
          :dataSource="record.innerData"
          :pagination="false"
          :rowKey="(record2) => record2.id"
          :scroll="{ y: 200 }"
        >
        </a-table>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 12 },
  wrapperCol: { span: 12 },
};
export default {
  name: "fsBQy",
  components: {},
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
      queryParams: {
        year1: '',
        year2: '',
        quarter1: '',
        quarter2: ''
      },
      expandedRowKeys: [],
      addVisiable: false,
      editVisiable: false,
      loading: false,
      bordered: true,
      indData: [],
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "计量牌编号",
          dataIndex: "number",
          width: 100,
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
        {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 100,
        },

        // {
        //   title: "性别",
        //   dataIndex: "sexName",
        //   width: 100,
        // },
        // {
        //   title: "职业类别",
        //   dataIndex: "jobType",
        //   width: 100,
        // },

        {
          title: "总量",
          dataIndex: "toal",
          width: 100,
        },
      ];
    },
    innerColumns() {
      return [
       
        {
          title: "年度",
          dataIndex: "year",
          width: 100,
        },
        {
          title: "季度",
          dataIndex: "quarter",
          width: 100,
        },
        
        {
          title: "职业类别",
          dataIndex: "jobType",
          width: 100,
        },
        {
          title: "剂量计佩戴起始日期",
          dataIndex: "startDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "佩戴天数",
          dataIndex: "days",
          width: 100,
        },
        {
          title: "铅衣外Hp(10)",
          dataIndex: "qyOut",
          width: 100,
           customRender: (text, row, index) => {
            if (text == 0) return "";
           return text
          },
        },
        
        {
          title: "铅衣内Hp(10)",
          dataIndex: "qyIn",
          width: 100,
           customRender: (text, row, index) => {
            if (text == 0) return "";
           return text
          },
        },
        {
          title: "未穿铅衣Hp(10)",
          dataIndex: "qyNo",
          width: 100,
           customRender: (text, row, index) => {
            if (text == 0) return "";
           return text
          },
        },
        {
          title: "是否大剂量",
          dataIndex: "isBig",
          width: 100,
        },
        {
          title: "工作期间是否有甲状腺屏蔽",
          dataIndex: "isPb",
          width: 100,
        },
         {
          title: "总量",
          dataIndex: "toal",
          width: 100,
        },
        {
          title: "附件",
          dataIndex: "fileId",
          customRender: (text, row, index) => {
            if (text != null && text != "") {
              return (
                <a href={this.$baseUrl + row.fileUrl} target="_blank">
                  查看
                </a>
              );
            }
            return "";
          },
          width: 100,
        },
      ];
    },
  },
  mounted() {
    this.fetch();
    // this.fetchIndict();
  },

  methods: {
    moment,
    expandSubGrid(expanded, record) {
      //获取供应计划的数量
      if (expanded) {
        this.expandedRowKeys.push(
          record.userAccount 
        );
        this.handleSubData(record); //获取子表数据
      } else {
        let expandedRowKeys = this.expandedRowKeys.filter(
          (RowKey) => RowKey !== record.userAccount 
        );
        this.expandedRowKeys = expandedRowKeys;
      }
    },
    handleSubData(record) {
      this.loading = true;
      let queryParams = { ...this.queryParams };
      queryParams.userAccount = record.userAccount;
      this.$get("fsBQy", {
        ...queryParams,
        pageSize: 100000,
      }).then((r) => {
        let data = r.data;
        this.loading = false;
        record.innerData = data.rows;
      });
    },
    
    handleStartDateChange(value) {
      if (value == null) {
        delete this.queryParams.startDateFrom;
      } else {
        this.queryParams.startDateFrom = moment(value).format("YYYY-MM-DD");
      }
    },
    handleEndDateChange(value) {
      if (value == null) {
        delete this.queryParams.startDateTo;
      } else {
        this.queryParams.startDateTo = moment(value).format("YYYY-MM-DD");
      }
    },
    handleRefesh() {
      this.search();
    },
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
      this.$refs.mdlBBadrecordEdit.setFormValues(record);
      this.editVisiable = true;
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要删除的记录");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          let mdlBBadrecordIds = that.selectedRowKeys.join(",");
          that.$delete("mdlBBadrecord/" + mdlBBadrecordIds).then(() => {
            that.$message.success("删除成功");
            that.selectedRowKeys = [];
            that.search();
          });
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
    },
    exportExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      let cls=[...this.columns]
    let   queryParams = {...this.queryParams}
     let dataJson = JSON.stringify(cls)
      this.$export("fsBQy/excelReport", {
        sortField: sortField,
        sortOrder: sortOrder,
        dataJson: dataJson,
        ...queryParams,
      });
      
      
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
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
      let year =''
      if(params.year1!==null&&params.year1!==''){
          year= params.year1
      }
       if(params.year1!==null&&params.year1!=''&&params.quarter1!==null &&params.quarter1!=''&&params.quarter1!='-1'){
          year= year +'-'+params.quarter1;
      }
      if(year!=''){
        params.year= year
      }
      let year2 =''
      if(params.year2!==null &&params.year2!='' ){
          year2= params.year2
      }
      if(params.year2!==null&&params.year2!=''&&params.quarter2!==null &&params.quarter2!=''&&params.quarter2!='-1'){
          year2= year2 +'-'+params.quarter2;
      }
      if(year2!=''){
        params.quarter= year2
      }
      if(params.isDf){
        params.state = 1
      }
      else{
        params.state = 0
      }
      this.$get("fsBQy/report", {
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

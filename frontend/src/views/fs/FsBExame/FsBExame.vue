<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="5" :sm="24">
              <a-form-item label="发薪号/姓名" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
             <a-col :md="5" :sm="24">
              <a-form-item label="计量牌编号" v-bind="formItemLayout">
                <a-input v-model="queryParams.number" />
              </a-form-item>
            </a-col>
             <a-col :md="6" :sm="24">
              <a-form-item label="考试类型" v-bind="formItemLayout">
                <a-select v-model="queryParams.exameType" >
                   <a-select-option value="-1">
                全部
            </a-select-option>
                  <a-select-option value="环保考试">
                环保考试
            </a-select-option>
            <a-select-option value="卫生考试">
                卫生考试
            </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
             <a-col :md="8" :sm="24">
             <a-form-item label="考试日期" v-bind="formItemLayout">
                 <a-date-picker style="width:48%;"
                  @change="handleStartDateChange"
                       />-<a-date-picker style="width:48%;"
                  @change="handleEndDateChange"
                       />
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
          icon="plus"
         style="color: #fff;background-color: #1890ff;border-color: #1890ff;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
          @click="add"
          >新增</a-button
        >
        <a-button @click="batchDelete"
         icon="delete"
         style="color: #fff;background-color: #f5222d;border-color: #f5f5f5;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
          >删除</a-button
        >
       <a-button
                  icon="file-excel"
                  @click="exportExcel"
                  style="color: #fff;background-color: #faad14;border-color: #faad14;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
                >导出</a-button>
        <import-excel
          templateUrl="fsBExame/downTemplate"
          @succ="handleRefesh"
          url="fsBExame/import"
        >
        </import-excel>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.id"
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
    <!-- 新增字典 -->
    <fsBExame-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </fsBExame-add>
    <!-- 修改字典 -->
    <fsBExame-edit
      ref="fsBExameEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </fsBExame-edit>
  </a-card>
</template>

<script>
import FsBExameAdd from "./FsBExameAdd";
import FsBExameEdit from "./FsBExameEdit";
import ImportExcel from "../../common/ImportExcel";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "FsBExame",
  components: { FsBExameAdd, FsBExameEdit, ImportExcel },
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
          title: "姓名",
          dataIndex: "userAccountName",
          width: 100,
        },
        {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 100,
        },
        {
          title: "计量牌编号",
          dataIndex: "number",
          width: 100,
        },
        {
          title: "考试类型",
          dataIndex: "exameType",
          width: 100,
        },
        {
          title: "考试时间",
          dataIndex: "exameDate",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "考试编号",
          dataIndex: "exameNum",
          width: 100,
        },
        {
          title: "考试成绩",
          dataIndex: "exameScore",
          width: 100,
        },
        {
          title: "考试总分",
          dataIndex: "exameTotal",
          width: 100,
        },
        {
          title: "考试专业",
          dataIndex: "exameZy",
          width: 100,
        },
        {
          title: "有效截至日期",
          dataIndex: "valid",
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
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 100,
        },
      ];
    },
  },
  mounted() {
    this.fetch();
  },
  methods: {
    moment,
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
      this.$refs.fsBExameEdit.setFormValues(record);
      this.editVisiable = true;
    },
    handleStartDateChange(value) {
      if(value==null){
        delete this.queryParams.exameDateFrom
      }
      else{
        this.queryParams.exameDateFrom = moment(value).format("YYYY-MM-DD");
      }
    },
    handleEndDateChange(value) {
      if(value==null){
        delete this.queryParams.exameDateTo
      }
      else{
        this.queryParams.exameDateTo = moment(value).format("YYYY-MM-DD");
      }
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
          let fsBExameIds = that.selectedRowKeys.join(",");
          that.$delete("fsBExame/" + fsBExameIds).then(() => {
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
     
       let json = [...this.columns]
      json.splice(json.length-1,1);
     
      let dataJson = JSON.stringify(json)
      let params= this.queryParams
  if(params.exameType=="-1"){
        delete params.exameType;
      }
      this.$export("fsBExame/excel", {
        sortField: sortField,
        sortOrder: sortOrder,
         dataJson: dataJson,
        ...params
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
      if(params.exameType=="-1"){
        delete params.exameType;
      }
      this.$get("fsBExame", {
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

<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item label="团队名称" v-bind="formItemLayout">
                <a-input v-model="queryParams.teamName" />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="牵头科室" v-bind="formItemLayout">
                <a-input v-model="queryParams.deptHead" />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="团队负责人" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccountLeader" />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="团队秘书" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccountAssist" />
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="6" :sm="24">
                <a-form-item label="考核年度" v-bind="formItemLayout">
                  <a-input v-model="queryParams.telLeader2" />
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="考核结果" v-bind="formItemLayout">
                  <a-select v-model="queryParams.emailLeader2">
                    <a-select-option value="-1"> 全部 </a-select-option>
                    <a-select-option value="优秀"> 优秀 </a-select-option>
                    <a-select-option value="合格"> 合格 </a-select-option>
                    <a-select-option value="不合格"> 不合格 </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="参与人员" v-bind="formItemLayout">
                  <a-input v-model="queryParams.telAssist" />
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
        <a-button 
        style="color: #fff;background-color: #1890ff;border-color: #1890ff;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
         @click="add" icon="plus">新增</a-button>
        <a-button @click="batchDelete" icon="delete"
        style="color: #fff;background-color: #f5222d;border-color: #f5f5f5;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
        >删除</a-button>
        <a-dropdown v-hasPermission="['mdlBMdt:export']">
          <a-menu slot="overlay">
            <a-menu-item key="export-data" @click="exportExcel"
              >导出Excel</a-menu-item
            >
          </a-menu>
          <a-button>
            更多操作
            <a-icon type="down" />
          </a-button>
        </a-dropdown>
        <import-excel
          v-hasPermission="['mdlBMdt:import']"
          templateUrl="mdlBMdt/downTemplate"
          @succ="handleRefesh"
          url="mdlBMdt/import"
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
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange,
        }"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="{ x: 2500 }"
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
          :scroll="{ x: 800, y: 200 }"
        >
        </a-table>
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
    <mdlBMdt-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </mdlBMdt-add>
    <!-- 修改字典 -->
    <mdlBMdt-edit
      ref="mdlBMdtEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </mdlBMdt-edit>
  </a-card>
</template>

<script>
import MdlBMdtAdd from "./MdlBMdtAdd";
import MdlBMdtEdit from "./MdlBMdtEdit";
import ImportExcel from "../../common/ImportExcel";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "MdlBMdt",
  components: { MdlBMdtAdd, MdlBMdtEdit, ImportExcel },
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      expandedRowKeys: [],
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
          title: "团队名称",
          dataIndex: "teamName",
          width: 200,
        },
        {
          title: "牵头科室",
          dataIndex: "deptHead",
          width: 200,
        },
        {
          title: "是否开通MDT门诊",
          dataIndex: "isMenzhen",
          width: 80,
        },
        {
          title: "团队负责人",
          customHeaderCell: function () {
            return { style: { backgroundColor: "#7FFFD4" } };
          },
          children: [
            {
              title: "姓名",
              dataIndex: "userAccountNameLeader",
              width: 100,
            },
            {
              title: "发薪号",
              dataIndex: "userAccountLeader",
              width: 80,
            },
            {
              title: "电话",
              dataIndex: "telLeader",
              width: 120,
            },
            {
              title: "邮箱",
              dataIndex: "emailLeader",
              width: 150,
            },
          ],
        },
        {
          title: "团队负责人2",
          customHeaderCell: function () {
            return { style: { backgroundColor: "LightSkyBlue" } };
          },
          children: [
            {
              title: "姓名",
              dataIndex: "userAccountNameLeader2",
              width: 100,
            },
            {
              title: "发薪号",
              dataIndex: "userAccountLeader2",
              width: 80,
            },
            {
              title: "电话",
              dataIndex: "telLeader2",
              width: 120,
            },
            {
              title: "邮箱",
              dataIndex: "emailLeader2",
              width: 150,
            },
          ],
        },
        {
          title: "团队秘书",
          customHeaderCell: function () {
            return { style: { backgroundColor: "LightCyan" } };
          },
          children: [
            {
              title: "姓名",
              dataIndex: "userAccountNameAssist",
              width: 100,
            },
            {
              title: "发薪号",
              dataIndex: "userAccountAssist",
              width: 80,
            },
            {
              title: "电话",
              dataIndex: "telAssist",
              width: 120,
            },
            {
              title: "邮箱",
              dataIndex: "emailAssist",
              width: 150,
            },
          ],
        },

        // {
        //   title: "团队秘书2",
        //   customHeaderCell: function () {
        //     return { style: { backgroundColor: "#AFEEEE" } };
        //   },
        //   children: [
        //     {
        //       title: "姓名",
        //       dataIndex: "userAccountNameAssist2",
        //       width: 100,
        //     },
        //     {
        //       title: "发薪号",
        //       dataIndex: "userAccountAssist2",
        //       width: 80,
        //     },
        //     {
        //       title: "电话",
        //       dataIndex: "telAssist2",
        //       width: 120,
        //     },
        //     {
        //       title: "邮箱",
        //       dataIndex: "emailAssist2",
        //       width: 150,
        //     },
        //   ],
        // },

        {
          title: "有效日期",
          children: [
            {
              title: "成立时间",
              dataIndex: "startDate",
              width: 100,
              customRender: (text, row, index) => {
                if (text == null) return "";
                return moment(text).format("YYYY-MM-DD");
              },
            },
            {
              title: "废止时间",
              dataIndex: "endDate",
              width: 100,
              customRender: (text, row, index) => {
                if (text == null) return "";
                return moment(text).format("YYYY-MM-DD");
              },
            },
          ],
        },
        {
          title: "团队申报书",
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
          title: "备注",
          dataIndex: "note",
        },

        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          width: 100,
        },
      ];
    },
    innerColumns() {
      return [
        {
          title: "类型",
          dataIndex: "type",
          width: 150,
          customRender: (value, row, index) => {
            if (value) {
              return <a-tag color="orange">其他成员</a-tag>;
            }
            return <a-tag color="green">核心成员</a-tag>;
          },
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 150,
        },
        {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 100,
        },
        {
          title: "科室",
          dataIndex: "deptNew",
          width: 100,
        },
        {
          title: " ",
          dataIndex: "deptNew222",
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
      this.$refs.mdlBMdtEdit.setFormValues(record);
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
          let mdlBMdtIds = that.selectedRowKeys.join(",");
          that.$delete("mdlBMdt/" + mdlBMdtIds).then(() => {
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
      this.$export("mdlBMdt/excel", {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
      });
    },
    expandSubGrid(expanded, record) {
      //获取供应计划的数量
      if (expanded) {
        this.expandedRowKeys.push(record.id);
        this.handleSubData(record); //获取子表数据
      } else {
        let expandedRowKeys = this.expandedRowKeys.filter(
          (RowKey) => RowKey !== record.id
        );
        this.expandedRowKeys = expandedRowKeys;
      }
    },
    handleSubData(record) {
      this.loading = true;
      let queryParams = {};
      queryParams.baseId = record.id;
      this.$get("mdlBMdtD", {
        ...queryParams,
        sortField: "type",
        sortOrder: "ascend",
        pageSize: 100000,
      }).then((r) => {
        let data = r.data;
        this.loading = false;
        record.innerData = data.rows;
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
      if (params.emailLeader2 == -1) {
        delete params.emailLeader2;
      }
      this.$get("mdlBMdt", {
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

<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'"></div>
          <a-col :md="7" :sm="24">
            <a-form-item label="申报日期" v-bind="formItemLayout">
              <a-date-picker @change="onSqStartChange" style="width:48%" />- 
              <a-date-picker @change="onSqEndChange" style="width:48%"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="审核状态" v-bind="formItemLayout">
              <a-select v-model="queryParams.state" @change="changeState" style="width: 100%;">
                <a-select-option :value="2">
                  未审核
                </a-select-option>
                <a-select-option :value="100">
                  已审核
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
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
            v-hasPermission="['xxbBResult:view']"
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="view(record)"
            title="查看"></a-icon>
          <a-divider type="vertical" v-show="queryParams.state==2" />
          <a-icon
            type="setting"
            theme="twoTone"
            v-show="queryParams.state==2"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-divider type="vertical" />
          <a-icon 
            type="download"
            @click="download(record)"
            title="下载申报表"
           ></a-icon>
           <a-divider type="vertical" />
          <a-icon 
            type="fire"
            @click="downloadExcel(record)"
            title="下载申报表附件"
           ></a-icon>
        </template>
      </a-table>
    </div>
    <xxbBR-flow
      ref="xxbBRFlow"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </xxbBR-flow>
  </a-card>
</template>

<script>
import moment from "moment";
import XxbBRFlow from "../XxbBRFlow";
const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};
export default {
  name: "XxbBRFlowDept",
  components: { XxbBRFlow },
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
      queryParams: { state: 2},
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
          title: "申报科室",
          dataIndex: "deptNew",
          fixed: "left",
          width: 120,
        },
        {
          title: "项目名称",
          dataIndex: "projectName",
          fixed: "left",
          width: 250,
        },
        {
          title: "申报日期",
          dataIndex: "applydat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "项目负责人",
          dataIndex: "userAccountName",
          customRender: (text, row, index) => {
            return row.userAccountName + ' ' + row.userAccount + ' ' + row.yggh
          },
          
        },
        {
          title: "开展日期",
          dataIndex: "kzsrtdat",
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100,
        },
        {
          title: "开展例数",
          dataIndex: "kzls",
          width: 100,
        },
        {
          title: "审核状态",
          dataIndex: "state",
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return '未提交'
              case 1:
                return '已提交'
              case 2:
                return '已审核'
              case 3:
                return '已退回'
              case 9:
                return '终止申报'
              default:
                return text
            }
          },
          width: 80,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 160,
        },
      ];
    },
  },
  mounted() {
    this.fetch();
  },
  methods: {
    moment,
    onSqStartChange (date, dateString) {
      this.queryParams.applydatFrom = dateString;
    },
    onSqEndChange (date, dateString) {
      this.queryParams.applydatTo = dateString;
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
    handleEditSuccess() {
      this.editVisiable = false;
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    view(record) {
      this.$refs.xxbBRFlow.setFormValues(record,'查看','成果奖申报书 医务处审核');
      this.editVisiable = true;
    },
    edit(record) {
      this.$refs.xxbBRFlow.setFormValues(record,'编辑','成果奖申报书 医务处审核');
      this.editVisiable = true;
    },
    download (record) {
      let formData = {}
      formData.id = record.id
      this.$download('xxbBResult/downloadFile', {
        ...formData
      }, `${new Date().getTime()}_` + record.deptNew + '-' + 
        record.projectName + '-' + record.userAccountName + '.pdf')
    },
    downloadExcel (record) {
      let formData = {}
      formData.id = record.id
      this.$download('xxbBResult/excelFile', {
        ...formData
      }, `${new Date().getTime()}_` + record.deptNew + '-' + 
        record.projectName + '-' + record.userAccountName + '.xlsx')
    },
    changeState () {
      this.search()
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
      params.sortField = 'create_Time'
      params.sortOrder = 'descend'
      params.state = this.queryParams.state
      params.comments = "医"
      this.$get("xxbBResult/flowList", {
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

<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="8" :sm="24">
              <a-form-item label="姓名/发薪号" v-bind="formItemLayout">
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="24">
              <a-form-item label="科室变化" v-bind="formItemLayout">
                <a-select v-model="queryParams.isDept">
                <a-select-option value="是">是
                </a-select-option>
                 <a-select-option value="否">否
                </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="24">
              <a-form-item label="岗位类别变化" v-bind="formItemLayout">
                <a-select v-model="queryParams.isGwlb">
                <a-select-option value="是">是
                </a-select-option>
                 <a-select-option value="否">否
                </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="24">
             <a-form-item label="牌个数变化" v-bind="formItemLayout">
                <a-select v-model="queryParams.isNumber">
                <a-select-option value="是">是
                </a-select-option>
                 <a-select-option value="否">否
                </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="24">
             <a-form-item label="检测状态变化" v-bind="formItemLayout">
                <a-select v-model="queryParams.isState">
                <a-select-option value="是">是
                </a-select-option>
                 <a-select-option value="否">否
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
         
          type="primary"
          ghost
          @click="add"
          >新增</a-button
        >
        <a-button
         
          @click="batchDelete"
          >删除</a-button
        >
      
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
          v-show="record.state == 0 ||record.state == 2"
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
    <fsBChangerecord-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </fsBChangerecord-add>
    <!-- 修改字典 -->
    <fsBChangerecord-edit
      ref="fsBChangerecordEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </fsBChangerecord-edit>
  </a-card>
</template>

<script>
import FsBChangerecordAdd from "./FsBChangerecordAdd";
import FsBChangerecordEdit from "./FsBChangerecordEdit";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "FsBChangerecord",
  components: { FsBChangerecordAdd, FsBChangerecordEdit },
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
          title: "原科室",
          dataIndex: "deptOld",
          width: 100,
        },
        {
          title: "新科室",
          dataIndex: "deptNew",
          width: 100,
        },
        
        {
          title: "原岗位类别",
          dataIndex: "gwlbOld",
          width: 100,
        },
        {
          title: "新岗位类别",
          dataIndex: "gwlbNew",
          width: 100,
        },
        {
          title: "原核定计量牌个数",
          dataIndex: "numberOld",
          width: 100,
        },
        {
          title: "新核定计量牌个数",
          dataIndex: "numberNew",
          width: 100,
        },
        {
          title: "原监测状态",
          dataIndex: "stateOld",
          width: 100,
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
        },
        {
          title: "新监测状态",
          dataIndex: "stateNew",
          width: 100,
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
        },
        // {
        //   title: "状态变更日期",
        //   dataIndex: "changeDate",
        //   customRender: (text, row, index) => {
        //     if (text == null) return "";
        //     return moment(text).format("YYYY-MM-DD");
        //   },
        //   width: 100,
        // },
        {
          title: "科室是否变化",
          dataIndex: "isDept",
          width: 100,
           customRender: (text, row, index) => {
            switch (text) {
              case '是':
                return <a-tag color="green">是</a-tag>;
              case '否':
                return <a-tag color="purple">否</a-tag>;
              default:
                return text;
            }
          },
        },
        {
          title: "岗位类别是否变化",
          dataIndex: "isGwlb",
          width: 100,
           customRender: (text, row, index) => {
            switch (text) {
              case '是':
                return <a-tag color="green">是</a-tag>;
              case '否':
                return <a-tag color="purple">否</a-tag>;
              default:
                return text;
            }
          },
        },
        {
          title: "剂量牌个数是否变化",
          dataIndex: "isNumber",
          width: 100,
           customRender: (text, row, index) => {
            switch (text) {
              case '是':
                return <a-tag color="green">是</a-tag>;
              case '否':
                return <a-tag color="purple">否</a-tag>;
              default:
                return text;
            }
          },
        },
        {
          title: "检测状态是否变化",
          dataIndex: "isState",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case '是':
                return <a-tag color="green">是</a-tag>;
              case '否':
                return <a-tag color="purple">否</a-tag>;
              default:
                return text;
            }
          },
        },
       
        {
          title: "状态",
          dataIndex: "state",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="green">未提交</a-tag>;
              case 1:
                return <a-tag color="purple">已提交</a-tag>;
              case 3:
                return <a-tag color="orange">已审核</a-tag>;
                case 2:
                return <a-tag color="red">审核不通过</a-tag>;
              default:
                return text;
            }
          },
          fixed: "right",
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
      this.$refs.fsBChangerecordEdit.setFormValues(record);
      this.editVisiable = true;
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要删除的记录");
        return;
      }
      if (this.selectedRowKeys.length>1) {
        this.$message.warning("请选择一条需要删除的记录");
        return;
      }
      const dataSource = this.dataSource;
      let that = this;
      let rows= dataSource.filter(p=>p.id==that.selectedRowKeys[0])
     if(rows[0].state==1||rows[0].state==3){
        this.$message.warning("已经提交或者审核就不能删除");
     }
     else{
      
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          let fsBChangerecordIds = that.selectedRowKeys.join(",");
          that.$delete("fsBChangerecord/" + fsBChangerecordIds).then(() => {
            that.$message.success("删除成功");
            that.selectedRowKeys = [];
            that.search();
          });
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
     }
    },
    exportExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.$export("fsBChangerecord/excel", {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
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
      params.submitUser= this.$store.state.account.user.username
      params.sortField= "create_time"
      params.sortOrder= "descend"
      this.$get("fsBChangerecord", {
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

<template>
  <a-card :bordered="false" class="card-area">
    <template v-if="!showAdd">
      <div style="text-align: center; font-size: 20px">
        <div>
          <a-card title="本部排班管理员">
            <a-row>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="发薪号">
                  <a-input placeholder="请输入发薪号" v-model="userAccount_1" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="姓名">
                  <a-input
                    placeholder="请输入姓名"
                    v-model="userAccountName_1"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="手机号">
                  <a-input placeholder="请输入联系方式" v-model="telphone_1" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>
          <a-card title="西院排班管理员">
            <a-row>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="发薪号">
                  <a-input placeholder="请输入发薪号" v-model="userAccount_2" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="姓名">
                  <a-input
                    placeholder="请输入姓名"
                    v-model="userAccountName_2"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="手机号">
                  <a-input placeholder="请输入联系方式" v-model="telphone_2" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>
          <a-card title="肿瘤排班管理员">
            <a-row>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="发薪号">
                  <a-input placeholder="请输入发薪号" v-model="userAccount_3" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="姓名">
                  <a-input
                    placeholder="请输入姓名"
                    v-model="userAccountName_3"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="手机号">
                  <a-input placeholder="请输入联系方式" v-model="telphone_3" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>
          <a-card title="金银湖排班管理员">
            <a-row>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="发薪号">
                  <a-input placeholder="请输入发薪号" v-model="userAccount_4" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="姓名">
                  <a-input
                    placeholder="请输入姓名"
                    v-model="userAccountName_4"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item v-bind="formItemLayout" label="手机号">
                  <a-input placeholder="请输入联系方式" v-model="telphone_4" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>
        </div>
        <a-button @click="handlePromise" type="primary">本人确认</a-button>
      </div>
    </template>
    <template v-if="showAdd">
      <div :class="advanced ? 'search' : null">
        <a-form layout="horizontal">
          <a-row>
            <div :class="advanced ? null : 'fold'">
              <a-col :md="8" :sm="24">
                <a-form-item label="排班周" v-bind="formItemLayout">
                  <a-week-picker
                    :format="dateFormat"
                    style="width: 100%"
                    @change="handleWeekChange"
                  >
                  </a-week-picker>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="排班状态" v-bind="formItemLayout">
                  <a-select style="width: 100%" v-model="queryParams.state">
                    <a-select-option value="-1"> 全部 </a-select-option>
                    <a-select-option value="0"> 未提交 </a-select-option>
                    <a-select-option value="1"> 已提交 </a-select-option>
                    <a-select-option value="2"> 审核未通过 </a-select-option>
                    <a-select-option value="3"> 已审核 </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="排班修改状态" v-bind="formItemLayout">
                  <a-select
                    style="width: 100%"
                    v-model="queryParams.stateApply"
                  >
                    <a-select-option value="-1"> 全部 </a-select-option>
                    <a-select-option value="1"> 未发起申请 </a-select-option>
                    <a-select-option value="2"> 申请待审核 </a-select-option>
                    <a-select-option value="3"> 申请未通过 </a-select-option>
                    <a-select-option value="4"> 申请已通过 </a-select-option>
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
          icon="plus"
            v-hasPermission="['sdlBSchedule:add']"
            type="primary"
            ghost
            @click="add"
            >新增</a-button
          >
          <a-button
          icon="delete"
            v-hasPermission="['sdlBSchedule:delete']"
            @click="batchDelete"
            >删除</a-button
          >
          <span style="color: red"
            >更改当前周之前的排班数据，均需要发起申请，通过后方可编辑</span
          >
        </div>
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
          <a-button icon="search" @click="info(record)">查看</a-button>
          <a-button
            v-if="getButtonState(record) == 1"
            icon="edit"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            >编辑</a-button
          >
          <a-button
            v-if="getButtonState(record) == 2"
            icon="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="apply(record)"
            >提交申请</a-button
          >
          <a-button
            v-if="getButtonState(record) == 3"
            icon="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="apply(record)"
            >排班修改申请</a-button
          >
        </template>
      </a-table>

      <!-- 新增字典 -->
      <sdlBSchedule-add
        @close="handleAddClose"
        @success="handleAddSuccess"
        :addVisiable="addVisiable"
      >
      </sdlBSchedule-add>
      <!-- 修改字典 -->
      <sdlBSchedule-edit
        ref="sdlBScheduleEdit"
        @close="handleEditClose"
        @success="handleEditSuccess"
        :editVisiable="editVisiable"
      >
      </sdlBSchedule-edit>
      <sdlBSchedule-info
        ref="sdlBScheduleInfo"
        @close="handleInfoClose"
        :editVisiable="infoVisiable"
      >
      </sdlBSchedule-info>
      <a-modal
        v-model="applyVisiable"
        :centered="true"
        :keyboard="false"
        :width="800"
        ok-text="确认"
        cancel-text="取消"
        @ok="saveApply"
        @cancel="hideModal"
        title="发起申请"
      >
        <a-form :form="form">
          <a-form-item
            v-bind="{
              labelCol: { span: 3 },
              wrapperCol: { span: 19, offset: 1 },
            }"
            label="申请理由"
          >
            <a-textarea
              :rows="8"
              placeholder="请输入申请理由"
              v-decorator="[
                'note',
                { rules: [{ required: true, message: '申请理由不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-form>
      </a-modal>
    </template>
  </a-card>
</template>

<script>
import SdlBScheduleAdd from "./SdlBScheduleAdd";
import SdlBScheduleEdit from "./SdlBScheduleEdit";
import SdlBScheduleInfo from "./SdlBScheduleInfo";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
const formItemLayout2 = {
  labelCol: { span: 3 },
  wrapperCol: { span: 5 },
};
export default {
  name: "SdlBSchedule",
  components: { SdlBScheduleAdd, SdlBScheduleEdit, SdlBScheduleInfo },
  data() {
    return {
      advanced: false,
      dateFormat: "YYYY-MM-DD",
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: {
        order: "descend",
        field: "start_date",
      },
      paginationInfo: null,
      formItemLayout,
      formItemLayout2,
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
      form: this.$form.createForm(this),
      addVisiable: false,
      editVisiable: false,
      infoVisiable: false,
      loading: false,
      bordered: true,
      applyVisiable: false,
      editRcordId: "", //申请ID
      showAdd: false,
      userAccount_1: "",
      userAccountName_1: "",
      telphone_1: "",
      userAccount_2: "",
      userAccountName_2: "",
      telphone_2: "",
      userAccount_3: "",
      userAccountName_3: "",
      telphone_3: "",
      userAccount_4: "",
      userAccountName_4: "",
      telphone_4: "",
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "科室",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "开始日期",
          dataIndex: "startDate",
          width: 100,
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
        },
        {
          title: "结束日期",
          dataIndex: "endDate",
          width: 100,
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
        },
        {
          title: "排班状态",
          dataIndex: "state",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>;
              case 1:
                return <a-tag color="green">已提交</a-tag>;
              case 2:
                return <a-tag color="red">审核未通过</a-tag>;
              case 3:
                return <a-tag color="#f50">已审核</a-tag>;
              case 9:
                return <a-tag color="orange">已完成</a-tag>;
              default:
                return text;
            }
          },
        },
        {
          title: "排班状态审核意见",
          dataIndex: "auditSuggestion",
          width: 200,
        },
        {
          title: "排班修改状态",
          dataIndex: "stateApplyFlag",
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 1:
                return <a-tag color="red">未发起申请</a-tag>;
              case 2:
                return <a-tag color="green">申请中</a-tag>;
              case 3:
                return <a-tag color="red">申请未通过</a-tag>;
              case 4:
                return <a-tag color="#f50">已通过申请</a-tag>;
              case 0:
                return "";
              default:
                return text;
            }
          },
        },
        {
          title: "排班修改理由",
          dataIndex: "note",
          width: 200,
        },
        {
          title: "排班修改意见",
          dataIndex: "auditApplySuggestion",
          width: 200,
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 250,
        },
      ];
    },
  },
  mounted() {
    this.fetchByDeptId();
    this.search();
  },
  methods: {
    handlePromise() {
      var person = [];
      let flag= 0;
      if(this.userAccountName_1!=''&&this.userAccount_1!=''&&this.telphone_1!=''){
        flag =1;
      }
       if(this.userAccountName_2!=''&&this.userAccount_2!=''&&this.telphone_2!=''){
        flag =1;
      }
       if(this.userAccountName_3!=''&&this.userAccount_3!=''&&this.telphone_3!=''){
        flag =1;
      }
       if(this.userAccountName_4!=''&&this.userAccount_4!=''&&this.telphone_4!=''){
        flag =1;
      }
      if(flag==0){
         this.$message.warning("请完整必填其中一项");
         return;
      }
      person.push({
        userAccountName: this.userAccountName_1,
        userAccount: this.userAccount_1,
        telephone: this.telphone_1,
        areaIndex: 0,
      });
      person.push({
        userAccountName: this.userAccountName_2,
        userAccount: this.userAccount_2,
        telephone: this.telphone_2,
        areaIndex: 1,
      });
      person.push({
        userAccountName: this.userAccountName_3,
        userAccount: this.userAccount_3,
        telephone: this.telphone_3,
        areaIndex: 2,
      });
      person.push({
        userAccountName: this.userAccountName_4,
        userAccount: this.userAccount_4,
        telephone: this.telphone_4,
        areaIndex: 3,
      });
      this.$post("sdlBPersoninfo", {
        jsonStr: JSON.stringify(person),
      })
        .then(() => {})
        .catch(() => {
          this.loading = false;
        });
      this.showAdd = true;
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
    handleWeekChange(weekData) {
      console.info(weekData);
      if (weekData == null) {
        this.queryParams.startDateFrom = "";
      } else {
        this.queryParams.startDateFrom = moment(weekData)
          .day(1)
          .format("YYYY-MM-DD"); // 周一日期
      }
    },
    hideModal() {
      this.editRcordId = "";
      this.applyVisiable = false;
    },
    saveApply() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let sdlBSchedule = this.form.getFieldsValue();
          sdlBSchedule.id = this.editRcordId;
          this.$put("sdlBSchedule/apply", {
            ...sdlBSchedule,
          })
            .then(() => {
              this.applyVisiable = false;
              this.editRcordId = "";
              this.$message.success("发起申请成功");
              this.search();
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    getButtonState(record) {
      if (record.state == 9) {
        return 5; //已经不能做任何操作
      }
      //1是编辑 2是提交申请 3.改登  4.都不显示
      if (record.stateApply != "0") {
        //只要是申请审核通过,都是编辑
        if (record.stateApply == 4) {
          //申请审核通过
          return 1;
        }
        if (record.stateApply == 3) {
          //申请审核不通过
          return 3;
        }
        if (record.stateApply == 1) {
          //待发起审核
          return 2;
        }
        if (record.stateApply == 2) {
          //申请中
          return 4;
        }
      }
      if (record.isExpired) {
        //过期
        return 3;
      }
      if (record.state == 3) {
        //过期
        return 3;
      }
      return 1;
    },
    apply(record) {
      this.applyVisiable = true;
      this.editRcordId = record.id;
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
    handleInfoSuccess() {
      this.infoVisiable = false;
      // this.$message.success("修改成功");
      // this.search();
    },
    handleInfoClose() {
      this.infoVisiable = false;
    },
    edit(record) {
      this.$refs.sdlBScheduleEdit.setFormValues(record);
      this.editVisiable = true;
    },
    info(record) {
      this.$refs.sdlBScheduleInfo.setFormValues(record);
      this.infoVisiable = true;
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要删除的记录");
        return;
      }
      var msg = true;
      const data = this.dataSource.filter(
        (p) => this.selectedRowKeys.indexOf(p.id) >= 0
      );
      data.forEach((el) => {
        if (el.state != 0) {
          msg = false;
        }
      });
      if (msg == false) {
        this.$message.warning("只有未提交的数据可以删除，请重新选择");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          let sdlBScheduleIds = that.selectedRowKeys.join(",");
          that.$delete("sdlBSchedule/" + sdlBScheduleIds).then(() => {
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
      this.$export("sdlBSchedule/excel", {
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
      let queryParams = { ...this.queryParams };
      if (queryParams.state == -1) {
        delete queryParams.state;
      }
      if (queryParams.stateApply == -1) {
        delete queryParams.stateApply;
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...queryParams,
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
     // this.sortedInfo = sorter;
      
      this.paginationInfo = pagination;
      this.fetch({
        sortField: this.sortedInfo.field,
        sortOrder: this.sortedInfo.order,
        ...this.queryParams,
      });
    },
    fetchByDeptId() {
      this.$get("sdlBPersoninfo", {
        deptId: this.$store.state.account.user.deptId,
      }).then((r) => {
        let data = r.data;

        let dataDepts = data.rows;
        if (dataDepts.length > 0) {
          this.userAccount_1 = dataDepts[0].userAccount;
          this.userAccountName_1 = dataDepts[0].userAccountName;
          this.telphone_1 = dataDepts[0].telephone;
        }
        if (dataDepts.length > 1) {
          this.userAccount_2 = dataDepts[1].userAccount;
          this.userAccountName_2 = dataDepts[1].userAccountName;
          this.telphone_2 = dataDepts[1].telephone;
        }
         if (dataDepts.length > 2) {
          this.userAccount_3 = dataDepts[2].userAccount;
          this.userAccountName_3 = dataDepts[2].userAccountName;
          this.telphone_3 = dataDepts[2].telephone;
        }
         if (dataDepts.length > 3) {
          this.userAccount_4 = dataDepts[3].userAccount;
          this.userAccountName_4 = dataDepts[3].userAccountName;
          this.telphone_4 = dataDepts[3].telephone;
        }
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
       if (params.state == -1) {
        delete params.state;
      }
      if (params.stateApply == -1) {
        delete params.stateApply;
      }
      this.$get("sdlBSchedule", {
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

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
              <a-form-item label="人员状态" v-bind="formItemLayout">
                <a-select v-model="queryParams.state">
                  <a-select-option :value="-1"> 全部 </a-select-option>
                  <a-select-option :value="3"> 在职 </a-select-option>
                  <a-select-option :value="2"> 退休 </a-select-option>
                  <a-select-option :value="0"> 离职 </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
                <a-form-item label="有无近两年证件检测" v-bind="formItemLayout">
                  <a-select v-model="queryParams.isDf2">
                    <a-select-option value="-1"> 全部 </a-select-option>
                    <a-select-option value="有"> 有 </a-select-option>
                    <a-select-option value="无"> 无 </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            <template v-if="advanced">
              
              
             
              <a-col :md="6" :sm="24">
                <a-form-item label="院区" v-bind="formItemLayout">
                  <a-select v-model="queryParams.yuanqu">
                    <a-select-option value="-1"> 全部 </a-select-option>
                    <a-select-option value="本院">本院</a-select-option>
                    <a-select-option value="西院">西院</a-select-option>
                    <a-select-option value="肿瘤">肿瘤</a-select-option>
                    <a-select-option value="金银湖">金银湖</a-select-option>
                    <a-select-option value="PET中心">PET中心</a-select-option>
                  </a-select>
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
          style="
            color: #fff;
            background-color: #1890ff;
            border-color: #1890ff;
            box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);
          "
          icon="login"
          @click="add"
          >移入数据</a-button
        >
        <a-button
          icon="logout"
          style="
            color: #fff;
            background-color: #f5222d;
            border-color: #f5f5f5;
            box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);
          "
          @click="batchDelete"
          >移出数据</a-button
        >
        <a-button
          icon="file-excel"
          @click="exportCustomExcel"
          style="
            color: #fff;
            background-color: #faad14;
            border-color: #faad14;
            box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);
          "
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
import FsBUserEdit from "./DeptBUserEdit.vue";
import SdlUserInfo from "./SdlUserInfo.vue";
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 10 },
  wrapperCol: { span: 13, offset: 1 },
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
      listAuditInfo: [
        {
          fieldName: "xxy",
          fieldTitle: "显示",
        },
      ], // 当前用户包含的证件数据
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
          title: "院区",
          dataIndex: "yuanqu",
          width: 100,
        },

        {
          title: "工龄",
          dataIndex: "fsgl",
          width: 100,
        },
        
        {
          title: "人员状态",
          dataIndex: "state",
          customRender: (text, row, index) => {
            switch (text) {
              case "0":
                return <a-tag color="red">离职</a-tag>;
              case "2":
                return <a-tag color="cyan">退休</a-tag>;
              case "3":
                return <a-tag color="green">在职</a-tag>;
            }
          },
          width: 100,
        },
        {
          title: "婚姻状况",
          dataIndex: "married",
          width: 100,
        },
       
        {
          title: "开始从事本科室工作时间",
          dataIndex: "fsDate",
          width: 100,
          customRender: (text, row, index) => {
            if (text == null) return "";
            return moment(text).format("YYYY-MM-DD");
          },
        },
       
       
        {
          title: "人员类型",
          dataIndex: "rylx",
          width: 100,
        },
        {
          title: "员工工号",
          dataIndex: "yggh",
          width: 100,
        },

        {
          title: "华科人事编号",
          dataIndex: "ghHk",
          width: 100,
        },
        {
          title: "性别",
          dataIndex: "sexName",
          width: 100,
        },
        {
          title: "出生年月",
          dataIndex: "birthday",
          width: 100,
        },

        {
          title: "员工组",
          dataIndex: "yuangongzu",
          width: 100,
        },
        {
          title: "员工子组",
          dataIndex: "zizu",
          width: 100,
        },
        {
          title: "人事子范围",
          dataIndex: "renshizifw",
          width: 100,
        },
        {
          title: "人事子范围分类",
          dataIndex: "renshizfenlei",
          width: 100,
        },
        {
          title: "身份证号",
          dataIndex: "idCard",
          width: 100,
        },

        {
          title: "职称",
          dataIndex: "zhicheng",
          width: 100,
        },
        {
          title: "岗位等级",
          dataIndex: "xrgwjb",
          width: 100,
        },
        {
          title: "岗位等级开始日期",
          dataIndex: "xrgwjbprsj",
          width: 100,
        },
        {
          title: "临床职称",
          dataIndex: "zyjsgwLc",
          width: 100,
        },
        {
          title: "聘任时间（临床）",
          dataIndex: "appointedDateLc",
          width: 100,
        },
        {
          title: "临床证书编号",
          dataIndex: "bianhaoLc",
          width: 130,
        },
        {
          title: "教学职称",
          dataIndex: "zyjsgw",
          width: 100,
        },
        {
          title: "教学聘任时间",
          dataIndex: "appointedDate",
          width: 100,
        },
        {
          title: "教学证书编号",
          dataIndex: "bianhaoJx",
          width: 130,
        },
        {
          title: "内聘临床专业技术职务",
          dataIndex: "zyjsNp",
          width: 100,
        },
        {
          title: "内聘临床时间",
          dataIndex: "zyjsDateNp",
          width: 100,
        },
        {
          title: "内聘教学专业技术职务",
          dataIndex: "zyjsNpjx",
          width: 100,
        },
        {
          title: "内聘教学时间",
          dataIndex: "zyjsDateNpjx",
          width: 100,
        },
        {
          title: "学历",
          dataIndex: "edu",
          width: 100,
        },
        {
          title: "毕业学校",
          dataIndex: "eduSchool",
          width: 100,
        },
        {
          title: "国籍",
          dataIndex: "guoji",
          width: 100,
        },
        {
          title: "民族",
          dataIndex: "minzu",
          width: 100,
        },
        {
          title: "籍贯",
          dataIndex: "jiguan",
          width: 100,
        },
        {
          title: "政治面貌",
          dataIndex: "politicalStatus",
          width: 100,
        },
        {
          title: "入职时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        {
          title: "参加工作时间",
          dataIndex: "workDate",
          width: 100,
        },
        {
          title: "医师类别",
          dataIndex: "yishiLb",
          width: 100,
        },
        {
          title: "医师级别",
          dataIndex: "yishiJb",
          width: 100,
        },
        {
          title: "医师执业范围",
          dataIndex: "yishiZhiyefanwei",
          width: 100,
        },
        {
          title: "医师资格证书编号",
          dataIndex: "yishiZgzsbianhao",
          width: 130,
        },
        {
          title: "医师执业证书编码",
          dataIndex: "yishiZiyebianhao",
          width: 130,
        },
       
        // {
        //   title: "操作",
        //   dataIndex: "operation",
        //   scopedSlots: { customRender: "operation" },
        //   fixed: "right",
        //   width: 100,
        // },
      ];
    },
  },
  mounted() {
    this.fetchUseraudit();
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
    fetchUseraudit() {
      this.$get("deptBUser/type", {
        deptId: this.$store.state.account.user.deptId
      }).then((r) => {
        //.info(r.data)
        this.listAuditInfo = r.data;

        r.data.forEach((element) => {
          this.columns.push({
            title: element.bookType,
            width: 400,
            children: [
              {
                title: "编号",
                dataIndex: element.filedName + "2",
                width: 100,
              },
              {
                title: "发证时间",
                dataIndex: element.filedName + "3",
                width: 100,
              },
              {
                title: "最近一次校验时间",
                dataIndex: element.filedName + "4",
                width: 100,
              },
               {
                title: "下一次校验时间",
                dataIndex: element.filedName + "7",
                width: 100,
              },
              {
                title: "附件",
                dataIndex: element.filedName + "5",
                customRender: (text, row, index) => {
                  if (text != null && text != "") {
                    return (
                      <a
                        href={this.$baseUrl + row[element.filedName + "6"]}
                        target="_blank"
                      >
                        查看
                      </a>
                    );
                  }
                  return "";
                },
                width: 100,
              },
            ],
          });
        });

        this.columns.push({
          title: "操作",
          dataIndex: "operation",
          width: 130,
          scopedSlots: { customRender: "operation" },
        });
      });
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
    exportCustomExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      let json2 = [...this.columns];
      json2.splice(json2.length - 1, 1);

      let json=[];
      for(var i=0;i<json2.length;i++){
        if(json2[i].children==null ||json2[i].children==undefined){
          json.push(json2[i]);
        }
        else{
          for(var y=0;y<json2[i].children.length;y++){
            json2[i].children[y].isDynamic=1;
            json2[i].children[y].title= json2[i].title +json2[i].children[y].title
            json.push(json2[i].children[y]);
          }
        }
      }
    
      let dataJson = JSON.stringify(json);
      let params = this.queryParams;

     if (params.state == "-1") {
        delete params.state;
      }
      if (params.isDf2 == "有") {
        params.auditMan = 2;
      }
      if (params.isDf2 == "无") {
        params.auditMan = 1;
      }
      if (params.isDf2 == "-1") {
        delete params.auditMan;
      }
      if (params.yuanqu == "-1") {
        delete params.yuanqu;
      }
params.deptId = this.$store.state.account.user.deptId;
      this.$export("deptBUser/excel", {
        sortField: "user_account",
        sortOrder: "ascend",
        dataJson: dataJson,
        ...params,
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
      let params = { ...this.queryParams };
      if (params.jcState == "-1") {
        delete params.jcState;
      }
      if (params.state == "-1") {
        delete params.state;
      }
      if (params.isDf2 == "-1") {
        delete params.isDf2
      }
      
      if (params.yuanqu == "-1") {
        delete params.yuanqu;
      }

      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...params,
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
    
      if (params.state == "-1") {
        delete params.state;
      }
      if (params.isDf2 == "有") {
        params.auditMan = 2;
      }
      if (params.isDf2 == "无") {
        params.auditMan = 1;
      }
      if (params.isDf2 == "-1") {
        delete params.auditMan;
      }

      params.deptId = this.$store.state.account.user.deptId;
      this.$get("deptBUser", {
        ...params,
      }).then((r) => {
        let data = r.data;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.loading = false;
        
        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList
          //  console.info(auditList)
          if (auditList == null || auditList.length == 0) {
            // console.info(this.listAuditInfo)
            this.listAuditInfo.forEach(element2 => {
              // console.info(element2)
              element[element2.fieldName] = ''
              //element.auditNote = element2.auditNote
            });
          }
          else {
            // this.listAuditInfo.forEach(element2 => {
            //   if (!auditList.some(p => p.auditTitletype == element2.fieldName)) {
            //     element[element2.fieldName] = this.setDefaultValue(element2)
            //    // element.auditNote = element2.auditNote
            //   }
            // });
            auditList.forEach(element2 => {
              element[element2.auditTitletype] = element2.auditResult == 'null' ? "" : element2.auditResult
             // element.auditNote = element2.auditNote == 'null' ? "" : element2.auditNote
            });
          }

        });
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

<template>
  <div>
    <div class="operator">
      <select-user-remote
        ref="user"
        @userChang="userChange"
        style="width: 400px"
      ></select-user-remote>
      <a-button
        icon="plus"
        style="
          color: #fff;
          background-color: #1890ff;
          border-color: #1890ff;
          box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);
        "
        @click="add"
        >新增</a-button
      >
      <a-button
        style="
          color: #fff;
          background-color: #f5222d;
          border-color: #f5f5f5;
          box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);
        "
        @click="batchDelete"
        icon="delete"
        >删除</a-button
      >
    </div>
    <!-- 表格区域 -->
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.userAccount"
      :dataSource="dataSource"
      :pagination="false"
      :rowSelection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      :loading="loading"
      :bordered="bordered"
      :scroll="{ x: 900 }"
    >
      <template slot="fileId" slot-scope="text, record">
        <a-button type="dashed" block @click="OpenFile(record)">
          {{ record.fileId != null && record.fileId != "" ? "已上传" : "上传" }}
        </a-button>
      </template>
      <template slot="profession" slot-scope="text, record">
        <a-input
          @blur="(e) => inputChange(e.target.value, record, 'profession')"
          :value="record.profession"
        >
        </a-input>
      </template>
      <template slot="zizhi" slot-scope="text, record">
        <a-input
          @blur="(e) => inputChange(e.target.value, record, 'zizhi')"
          :value="record.zizhi"
        >
        </a-input>
      </template>
    </a-table>
    <tableUpload-file
      ref="upFile"
      :fileId="editRecord.fileId"
      :fileVisiable="fileVisiable"
      @setFileId="setFileId"
    >
    </tableUpload-file>
  </div>
</template>

<script>
import moment from "moment";
import SelectUserRemote from "../../common/SelectUserRemote.vue";
import TableUploadFile from "../../common/TableUploadFile";

export default {
  components: { SelectUserRemote, TableUploadFile },
  name: "AddUser",
  data() {
    return {
      dataSource: [],
      queryParams: {},
      sortedInfo: null,
      selectedRowKeys: [],
      paginationInfo: null,
      loading: false,
      bordered: true,
      userData: [],
      selectUser: {},
      fileVisiable: false,
      editRecord: {
        fileId: "",
      },
    };
  },
  props: {
    baseId: {
      default: "",
    },
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
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
          title: "性别",
          dataIndex: "sexName",
          width: 100,
        },

        {
          title: "电话",
          dataIndex: "telphone",
          width: 100,
        },
        {
          title: "人员序列",
          dataIndex: "xulie",
          width: 100,
        },
        {
          title: "职称",
          dataIndex: "zhicheng",
          width: 100,
        },
        {
          title: "所学专业",
          dataIndex: "profession",
          width: 100,
          scopedSlots: { customRender: "profession" },
        },
        {
          title: "具备的专业资质",
          dataIndex: "zizhi",
          scopedSlots: { customRender: "zizhi" },
          width: 100,
        },
        {
          title: "资质证明",
          dataIndex: "fileId2",
          customRender: (text, row, index) => {
            if (row.fileId != null && row.fileId != "") {
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
          title: "资质证明",
          dataIndex: "fileId",
          scopedSlots: { customRender: "fileId" },
          width: 80,
        },
      ];
    },
  },
  mounted() {
    //this.fetch();
  },

  methods: {
    moment,
    reset() {
      // 取消选中
      this.selectedRowKeys = [];

      // 重置列排序规则
      this.sortedInfo = null;
      this.paginationInfo = null;
      // 重置查询参数
      this.queryParams = {};
      this.dataSource = [];
      this.$refs.user.reset();
      //this.fetch();
    },
    showFile(record) {
      window.location.href = record.fileUrl;
    },
    OpenFile(record) {
      this.editRecord = record;
      this.fileVisiable = true;
      if (record.fileId != undefined && record.fileId != "") {
        this.$refs.upFile.fetch(record.fileId);
      }
    },
    setFileId(fileId, fileUrl) {
      this.fileVisiable = false;
      this.editRecord["fileId"] = fileId;
      this.editRecord["fileUrl"] = fileUrl;
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    userChange(value, option) {
      console.info();
      this.selectUser = option.key;
    },
    inputChange(value, record, filedName) {
      record[filedName] = value;
    },
    add() {
      let flag = 0;
      const data = this.dataSource;
      data.forEach((el) => {
        if (el.userAccount == this.selectUser.userAccount) {
          flag = 1;
        }
      });
      if (flag == 0) {
        if(this.selectUser.userAccount!=''&&this.selectUser.userAccount!=undefined){
        data.push({
          userAccount: this.selectUser.userAccount,
          userAccountName: this.selectUser.userAccountName,
          sexName: this.selectUser.sexname,
          xulie: this.selectUser.renshizifw,
          telphone: this.selectUser.tel,
          zhicheng: this.selectUser.zhichenglc,
          profession: "",
          zizhi: "",
        });
        this.dataSource = data;
        }
      } else {
        this.$message.warning("此用户已经添加，请勿重新添加");
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
          let accounts = that.selectedRowKeys;
          let data = that.dataSource;

          data = data.filter((p) => accounts.indexOf(p.userAccount) < 0);

          that.dataSource = data;

          that.$message.success("删除成功");
          that.selectedRowKeys = [];
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
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
    fetchUser(userAccount) {
      this.$get("sdlBUserMg/search", {
        userAccount,
      }).then((r) => {
        console.log(r);
        this.userData = r.data;
      });
    },
    fetch(params = {}) {
      this.loading = true;

      this.$get("mdlLPerson", {
        baseId: this.baseId,
        pageSize: 10000,
      }).then((r) => {
        let data = r.data;

        this.loading = false;
        this.dataSource = data.rows;
      });
    },
    getAll() {
      const data = this.dataSource;
      let json = [];
      data.forEach((el) => {
        if (el.userAccount != null && el.userAccount != "") {
          json.push({
            baseId: this.baseId,
            ...el,
          });
        }
      });
      return JSON.stringify(json);
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>

<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item v-bind="formItemLayout" label="管理科室">
                <a-select
                  v-model="queryParams.deptName"
                  option-filter-prop="children"
                  :filter-option="filterOption"
                  show-search
                >
                  <a-select-option
                    v-for="d in deptData"
                    :key="d.deptId"
                    :value="`${d.deptId}`"
                  >
                    {{ d.deptName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item v-bind="formItemLayout" label="设备类型（卫生）">
                <a-tree-select
                  v-model="queryParams.assetWeis"
                  :allowClear="true"
                  :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
                  :treeData="dicTreeData0"
                >
                </a-tree-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item v-bind="formItemLayout" label="设备类型（环保）">
                <a-tree-select
                  v-model="queryParams.assetHuanb"
                  :allowClear="true"
                  :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
                  :treeData="dicTreeData1"
                >
                </a-tree-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item v-bind="formItemLayout" label="类别">
                <a-select v-model="queryParams.lb" @change="onChangeLb">
                  <a-select-option value="-1">全部 </a-select-option>
                  <a-select-option value="个人防护用品"
                    >个人防护用品
                  </a-select-option>
                  <a-select-option value="辅助防护用品"
                    >辅助防护用品
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item v-bind="formItemLayout" label="用品及辅助防护设施">
                <a-select @change="onChangeYp" v-model="yp">
                  <a-select-option
                    v-for="al in arr"
                    :key="al.id"
                    :value="al.name"
                    >{{ al.name }}
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
                  @click="exportExcel"
                  style="color: #fff;background-color: #faad14;border-color: #faad14;box-shadow: 0 2px 0 rgba(0, 0, 0, 0.035);"
                >导出</a-button>
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
      </a-table>
    </div>
  </a-card>
</template>

<script>
import moment from "moment";
import AddressSelect from "../../common/AddressSelect.vue";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "AssetBInfo",
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
      queryParams: {},
      addVisiable: false,
      editVisiable: false,
      loading: false,
      bordered: true,
      deptData: [],
      dicTreeData0: [],
      dicTreeData1: [],
      arr1: [],
      arr2: [],
      arr: [],
      yp: ''
    };
  },
  components: { AddressSelect },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
        {
          title: "管理科室",
          dataIndex: "deptName",
          width: 100,
        },
        {
          title: "管理科室ID",
          dataIndex: "deptId",
          width: 100,
        },
        {
          title: "设备类型（卫生）",
          dataIndex: "assetWeisCustom",
          width: 100,
        },
        {
          title: "设备类型（环保）",
          dataIndex: "assetHuanbCustom",
          width: 100,
        },
        {
          title: "类别",
          dataIndex: "lb",
          width: 100,
        },
        {
          title: "用品及辅助防护设施",
          dataIndex: "ypfzfh",
          width: 100,
        },
        {
          title: "数量",
          dataIndex: "shuliang",
          width: 100,
        },
        {
          title: "铅当量",
          dataIndex: "qiandangliang",
          width: 100,
        },
      ];
    },
  },
  mounted() {
    this.fetchDept();
    this.fetch();
    this.$get("assetDDictory").then((r) => {
      let data = r.data.rows.children;
      this.dicTreeData0 = data.filter((p) => p.type == 0);
      this.dicTreeData1 = data.filter((p) => p.type == 1);
    });
    this.fetchLb();
  },
  methods: {
    moment,
    handleRefesh() {
      this.search();
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    fetchLb() {
      this.$get("assetDDictory/dic", { parentId: "B1" }).then((r) => {
        console.info(r);
        this.arr1 = r.data.rows;
      });
      this.$get("assetDDictory/dic", { parentId: "B2" }).then((r) => {
        this.arr2 = r.data.rows;
      });
    },
    onChangeYp(value){
this.queryParams.ypfzfh= value
    },
    onChangeLb(value) {
      console.info(222)
      this.yp= ""
      this.queryParams.ypfzfh= ""
      if (value == "个人防护用品") {
        this.arr = this.arr1;
      }
      if (value == "辅助防护用品") {
        this.arr = this.arr2;
      }
      if(value=="-1"){
        this.queryParams.ypfzfh= ""
      }
    },
    fetchDept() {
      this.$get("sdlBUser/deptNew", {}).then((res) => {
        this.deptData = [];
        this.deptData.push({
          deptId: "-1",
          deptName: "全部",
        });
        if (res.data[0] != null) {
          this.deptData.push(...res.data);
        }
      });
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
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
      this.$refs.assetBInfoEdit.setFormValues(record);
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
          let assetBInfoIds = that.selectedRowKeys.join(",");
          that.$delete("assetBInfo/" + assetBInfoIds).then(() => {
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
      //json.splice(json.length-1,1);
     
      let dataJson = JSON.stringify(json)
      let params= this.queryParams
      if (params.assetState == "-1") {
        delete params.assetState;
      }
      if (params.deptName == "-1") {
        delete params.deptName;
      }
      if (params.assetWeis == undefined) {
        delete params.assetWeis;
      }
      if (params.assetHuanb == undefined) {
        delete params.assetHuanb;
      }
      if (params.lb == "-1") {
        delete params.lb;
      }
      params.sortField="dept_name"
      params.sortOrder= "acsend"
      this.$export("assetBInfo/excel", {
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
      if (params.assetState == "-1") {
        delete params.assetState;
      }
      if (params.deptName == "-1") {
        delete params.deptName;
      }
      if (params.assetWeis == undefined) {
        delete params.assetWeis;
      }
      if (params.assetHuanb == undefined) {
        delete params.assetHuanb;
      }
      if (params.lb == "-1") {
        delete params.lb;
      }
      params.sortField="deptName"
      this.$get("assetBInfo/stastic", {
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

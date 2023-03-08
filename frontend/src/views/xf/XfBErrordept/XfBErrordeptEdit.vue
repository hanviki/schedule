<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="科室">
       <a-select
                  v-decorator="[
            'deptNew',
            { rules: [{ required: true, message: '科室不能为空' }] },
          ]"
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
      <a-form-item v-bind="formItemLayout" label="年度">
        <a-input
          placeholder="请输入年度"
          v-decorator="[
            'year',
            { rules: [{ required: true, message: '年度不能为空' }] },
          ]"
        />
      </a-form-item>
     
      <a-form-item v-bind="formItemLayout" label="自查结果">
        <a-input
          placeholder="请输入自查结果"
          v-decorator="[
            'result',
            { rules: [{ required: true, message: '自查结果不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="反馈日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'feedbackDate',
            { rules: [{ required: true, message: '反馈日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="反馈人">
        <a-input
          placeholder="请输入反馈人"
          v-decorator="[
            'operationName',
            { rules: [{ required: true, message: '反馈人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="审核人">
        <a-input
          placeholder="请输入审核人"
          v-decorator="[
            'auditMan',
            { rules: [{ required: true, message: '审核人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'remark',
            { rules: [{ required: true, message: '备注不能为空' }] },
          ]"
        />
      </a-form-item>
        <a-form-item v-bind="formItemLayout" label="授权文件">
          <a-select
            v-decorator="[
              'archiveId',
              { rules: [{ required: true, message: '授权文件不能为空' }] },
            ]"
            option-filter-prop="children"
            :filter-option="filterOption"
            show-search
            @change="fileChange"
          >
            <a-select-option v-for="d in fileData" :key="d" :value="`${d.id}`">
              {{ d.fileName + " " + d.fileCode }}
            </a-select-option>
          </a-select>
          
        </a-form-item>
    </a-form>
    <div class="drawer-bootom-button" style="z-index: 999999">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 0.8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading"
        >提交</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from 'moment'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBErrordeptAdd",
  props: {
    editVisiable: {
      default: false,
    },
  },
      components: {},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xfBErrordept: {},
      fileData: [],
            deptData: [],
    };
  },
   watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchDept();
        this.fetchFile();
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.xfBErrordept = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    fetchDept() {
      this.$get("sdlBUser/deptNew", { }).then((res) => {
        this.deptData = [];
        // this.deptData.push({
        //   deptId: "-1",
        //   deptName: "全部",
        // });
        console.info(res)
        if(res.data[0]!=null){
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
     fetchFile() {
      this.$get("xfBArchive", { pageNum:1 ,pageSize: 10000, fileType: '自纠情况库' }).then((res) => {
        this.fileData = [];
        this.fileData.push(...res.data.rows);
      });
    },
    fileChange(value, option) {
      this.xfBErrordept["archiveName"] = option.key.fileName;
      this.xfBErrordept["archiveCode"] = option.key.fileCode;
      this.xfBErrordept["fileId"] = option.key.fileId;
      this.xfBErrordept["fileUrl"] = option.key.fileUrl;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$put("xfBErrordept", {
            ...this.xfBErrordept,
          })
            .then(() => {
              this.reset();
              this.$emit("success");
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    setFormValues({ ...xfBErrordept }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "deptNew",
        "year",
        "archiveId",
        "archiveName",
        "archiveCode",
        "fileId",
        "fileUrl",
        "result",
        "feedbackDate",
        "operationName",
        "auditMan",
        "remark",
      ];
      let fieldDates = ["feedbackDate"];
      Object.keys(xfBErrordept).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBErrordept[key] !== null && xfBErrordept[key] !== "") {
              obj[key] = moment(xfBErrordept[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            if (key == "archiveId") {
              if( xfBErrordept[key]==null){
                obj[key] = "";
              }
              else {
              obj[key] = xfBErrordept[key].toString();
              }

            } else {
              obj[key] = xfBErrordept[key];
            }
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBErrordept.id = xfBErrordept.id;
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "userAccountName",
        "userAccount",
        "deptNew",
        "year",
        "archiveId",
        "result",
        "feedbackDate",
        "operationName",
        "auditMan",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBErrordept[_key] = values[_key];
        });
      }
    },
  },
};
</script>

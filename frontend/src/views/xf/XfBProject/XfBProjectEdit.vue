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
      
      <a-form-item v-bind="formItemLayout" label="专项治理名称">
        <a-input
          placeholder="请输入专项治理名称"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '专项治理名称不能为空' }] },
          ]"
        />
      </a-form-item>
     
      <a-form-item v-bind="formItemLayout" label="回复单位">
        <a-input
          placeholder="请输入回复单位"
          v-decorator="[
            'unit',
            { rules: [{ required: true, message: '回复单位不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="要求时间">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'pojectDate',
            { rules: [{ required: true, message: '要求时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="回复时间">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'backDate',
            { rules: [{ required: true, message: '回复时间不能为空' }] },
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
        <a-form-item v-bind="formItemLayout" label="附件">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
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
import UploadSingleFile from "../../common/uploadSingleFile"
import moment from 'moment'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBProjectAdd",
  props: {
    editVisiable: {
      default: false,
    },
  },
    components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xfBProject: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.xfBProject = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
      uploadAgent_1(fileId, fileUrl) {
      this.xfBProject.fileId = fileId;
      this.xfBProject.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBProject.fileId = "";
      this.xfBProject.fileUrl = "";
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$put("xfBProject", {
            ...this.xfBProject,
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
      setFormValues({ ...xfBProject }) {
      let fields = [
      
        "name",
        "type",
        "unit",
        "pojectDate",
        "backDate",
        "fileId",
        "fileUrl",
        "remark",
      ];
      let fieldDates = ["pojectDate", "backDate"];
      Object.keys(xfBProject).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBProject[key] !== null && xfBProject[key] !== "") {
              obj[key] = moment(xfBProject[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBProject[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBProject.id = xfBProject.id;
        let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(xfBProject.fileId);
      },100)
    },
    setFields() {
      let values = this.form.getFieldsValue([
        
        "name",
        "type",
        "unit",
        "pojectDate",
        "backDate",
      
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBProject[_key] = values[_key];
        });
      }
    },
  },
};
</script>

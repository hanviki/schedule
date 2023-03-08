<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="会议内容">
        <a-input
          placeholder="请输入会议内容"
          v-decorator="[
            'content',
            { rules: [{ required: true, message: '会议内容不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="工作要点">
        <a-input
          placeholder="请输入工作要点"
          v-decorator="[
            'summary',
            { rules: [{ required: true, message: '工作要点不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="发文内容简介">
        <a-input
          placeholder="请输入发文内容简介"
          v-decorator="[
            'brife',
            { rules: [{ required: true, message: '发文内容简介不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="参与人">
        <a-input
          placeholder="请输入参与人"
          v-decorator="[
            'joinPerson',
            { rules: [{ required: true, message: '参与人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="会议日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'meDate',
            { rules: [{ required: true, message: '会议日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="医院发文日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'sendDate',
            { rules: [{ required: true, message: '医院发文日期不能为空' }] },
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
      <a-form-item v-bind="formItemLayout" label="政策文件附件">
        <upload-single-file
          ref="fileagent"
          @uploadRemove="removeAgent_1"
          @uploadSuc="uploadAgent_1"
        >
        </upload-single-file>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="医院下文文件附件">
        <upload-single-file
          ref="fileagent2"
          @uploadRemove="removeAgent_2"
          @uploadSuc="uploadAgent_2"
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
import UploadSingleFile from "../../common/uploadSingleFile";
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBMeetingAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  components: { UploadSingleFile },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xfBMeeting: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.xfBMeeting = {};
      this.$refs.fileagent.reset();
      this.$refs.fileagent2.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent_1(fileId, fileUrl) {
      this.xfBMeeting.fileId = fileId;
      this.xfBMeeting.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBMeeting.fileId = "";
      this.xfBMeeting.fileUrl = "";
    },
    uploadAgent_2(fileId, fileUrl) {
      this.xfBMeeting.fileId2 = fileId;
      this.xfBMeeting.fileUrl2 = fileUrl;
    },
    removeAgent_2() {
      this.xfBMeeting.fileId2 = "";
      this.xfBMeeting.fileUrl2 = "";
    },
    setFormValues({ ...xfBMeeting }) {
      let fields = [
        "content",
        "summary",
        "brife",
        "joinPerson",
        "meDate",
        "sendDate",

        "remark",
      ];
      let fieldDates = ["meDate", "sendDate"];
      Object.keys(xfBMeeting).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBMeeting[key] !== null && xfBMeeting[key] !== "") {
              obj[key] = moment(xfBMeeting[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBMeeting[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBMeeting.id = xfBMeeting.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("xfBMeeting", {
            ...this.xfBMeeting,
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
    setFields() {
      let values = this.form.getFieldsValue([
        "userAccountName",
        "userAccount",
        "deptNew",
        "content",
        "summary",
        "brife",
        "joinPerson",
        "meDate",
        "sendDate",
        "fileId",
        "fileUrl",
        "fileId2",
        "fileUrl2",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBMeeting[_key] = values[_key];
        });
      }
    },
  },
};
</script>

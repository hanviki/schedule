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
      <a-form-item v-bind="formItemLayout" label="名称">
        <a-input
          placeholder="请输入名称"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="地点">
        <a-input
          placeholder="请输入地点"
          v-decorator="[
            'address',
            { rules: [{ required: true, message: '地点不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="会议日期">
        <a-date-picker
          v-decorator="[
            'meetDate',
            { rules: [{ required: true, message: '会议日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="参会人员">
        <a-input
          placeholder="请输入参会人员"
          v-decorator="[
            'person',
            { rules: [{ required: true, message: '参会人员不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="会议主要内容">
        <a-input
          placeholder="请输入会议主要内容"
          v-decorator="[
            'content',
            { rules: [{ required: true, message: '会议主要内容不能为空' }] },
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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBConferenceAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
    components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xfBConference: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.xfBConference = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
      uploadAgent_1(fileId, fileUrl) {
      this.xfBConference.fileId = fileId;
      this.xfBConference.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBConference.fileId = "";
      this.xfBConference.fileUrl = "";
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("xfBConference", {
            ...this.xfBConference,
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
        "name",
        "address",
        "meetDate",
        "person",
        "content",
        "remark",
     
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBConference[_key] = values[_key];
        });
      }
    },
  },
};
</script>

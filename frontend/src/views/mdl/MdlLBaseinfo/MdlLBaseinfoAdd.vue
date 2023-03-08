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
      <a-form-item v-bind="formItemLayout" label="实验室编码">
        <a-input
          placeholder="请输入实验室编码"
          v-decorator="[
            'labCode',
            { rules: [{ required: true, message: '实验室编码不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="实验室名称">
        <a-input
          placeholder="请输入实验室名称"
          v-decorator="[
            'labName',
            { rules: [{ required: true, message: '实验室名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="所属科室">
        <a-input
          placeholder="请输入所属科室"
          v-decorator="[
            'deptNew',
            { rules: [{ required: true, message: '所属科室不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="负责人">
        <a-input
          placeholder="请输入负责人"
          v-decorator="[
            'leader',
            { rules: [{ required: true, message: '负责人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="联系方式">
        <a-input
          placeholder="请输入联系方式"
          v-decorator="[
            'telphone',
            { rules: [{ required: true, message: '联系方式不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="实验室联络人">
        <a-input
          placeholder="请输入实验室联络人"
          v-decorator="[
            'linker',
            { rules: [{ required: true, message: '实验室联络人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="联系方式">
        <a-input
          placeholder="请输入联系方式"
          v-decorator="[
            'linkTel',
            { rules: [{ required: true, message: '联系方式不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="实验室地点">
        <a-input
          placeholder="请输入实验室地点"
          v-decorator="[
            'address',
            { rules: [{ required: true, message: '实验室地点不能为空' }] },
          ]"
        />
      </a-form-item>
      <!-- <a-form-item v-bind="formItemLayout" label="实验室资质">
        <a-input
          placeholder="请输入实验室资质"
          v-decorator="[
            'labZz',
            { rules: [{ required: true, message: '实验室资质不能为空' }] },
          ]"
        />
      </a-form-item> -->
      <a-form-item v-bind="formItemLayout" label="实验室资质">
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
import UploadSingleFile from "../../common/uploadSingleFile2"
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlLBaseinfoAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      mdlLBaseinfo: {},
    };
  },
  components: {UploadSingleFile},
  methods: {
    reset() {
      this.loading = false;
      this.mdlLBaseinfo = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
     uploadAgent_1(fileId, fileUrl) {
      this.mdlLBaseinfo.fileId = fileId;
      this.mdlLBaseinfo.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlLBaseinfo.fileId = "";
      this.mdlLBaseinfo.fileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlLBaseinfo", {
            ...this.mdlLBaseinfo,
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
        "labCode",
        "labName",
        "deptNew",
        "leader",
        "telphone",
        "linker",
        "linkTel",
        "address",
        "labZz",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlLBaseinfo[_key] = values[_key];
        });
      }
    },
  },
};
</script>

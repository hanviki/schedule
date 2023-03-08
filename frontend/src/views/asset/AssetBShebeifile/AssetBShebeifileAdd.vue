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
      <a-form-item v-bind="formItemLayout" label="上传日期">
        <a-date-picker
          v-decorator="[
            'scDate',
            { rules: [{ required: true, message: '上传日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件类型">
        <a-select    placeholder="请输入文件类型"
          v-decorator="[
            'type',
            { rules: [{ required: true, message: '文件类型不能为空' }] },
          ]">
          <a-select-option value="放射源放射诊疗许可证">放射源放射诊疗许可证 </a-select-option>
          <a-select-option value="辐射安全许可证">辐射安全许可证 </a-select-option>
           <a-select-option value="进出口资料存档">进出口资料存档 </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="附件">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'note',
            
          ]"
        />
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
  name: "AssetBShebeifileAdd",
  props: {
    addVisiable: {
      default: false,
    },
     baseId: {
      default: "",
    },
  },
  components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      assetBShebeifile: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.assetBShebeifile = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    uploadAgent_1(fileId, fileUrl) {
      this.assetBShebeifile.fileId = fileId;
      this.assetBShebeifile.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.assetBShebeifile.fileId = "";
      this.assetBShebeifile.fileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.assetBShebeifile.baseId= this.baseId
          this.$post("assetBShebeifile", {
            ...this.assetBShebeifile,
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
       
        "scDate",
        "type",
       
        "note",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.assetBShebeifile[_key] = values[_key];
        });
      }
    },
  },
};
</script>

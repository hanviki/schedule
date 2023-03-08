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
      
      <a-form-item v-bind="formItemLayout" label="检测类型">
        
         <a-select  placeholder="请输入检测类型"
          v-decorator="[
            'checkType',
            { rules: [{ required: true, message: '检测类型不能为空' }] },
          ]">
          <a-select-option value="场所检测">场所检测 </a-select-option>
          <a-select-option value="性能检测">性能检测 </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="检测编号">
        <a-input
          placeholder="请输入检测编号"
          v-decorator="[
            'checkNo',
            { rules: [{ required: true, message: '检测编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="检测时间">
        <a-date-picker
          v-decorator="[
            'checkDate',
            { rules: [{ required: true, message: '检测时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="检测结论">
      
        <a-select  placeholder="请输入检测结论"
          v-decorator="[
            'checkResult',
            { rules: [{ required: true, message: '检测结论不能为空' }] },
          ]">
          <a-select-option value="合格">合格
          </a-select-option>
           <a-select-option value="不合格">不合格
          </a-select-option>
          </a-select>
      </a-form-item>
        <a-form-item v-bind="formItemLayout" label="检测文件">
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
      <a-form-item v-bind="formItemLayout" label="涉及的场所">
        <a-input
          placeholder="请输入涉及的场所"
          v-decorator="[
            'checkCs',
            { rules: [{ required: true, message: '涉及的场所不能为空' }] },
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
  name: "AssetBCheckAdd",
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
      assetBCheck: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.assetBCheck = {};
       this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent_1(fileId, fileUrl) {
      this.assetBCheck.fileId = fileId;
      this.assetBCheck.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.assetBCheck.fileId = "";
      this.assetBCheck.fileUrl = "";
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
           this.assetBCheck.baseId = this.baseId;
          this.$post("assetBCheck", {
            ...this.assetBCheck,
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
       
        "checkType",
        "checkNo",
        "checkDate",
        "checkResult",
        "note",
        "checkCs",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.assetBCheck[_key] = values[_key];
        });
      }
    },
  },
};
</script>

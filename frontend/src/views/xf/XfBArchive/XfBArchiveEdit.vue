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
      <a-form-item v-bind="formItemLayout" label="文件名称">
        <a-input
          placeholder="请输入文件名称"
          v-decorator="[
            'fileName',
            { rules: [{ required: true, message: '文件名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件编号">
        <a-input
          placeholder="请输入文件编号"
          v-decorator="[
            'fileCode',
            { rules: [{ required: true, message: '文件编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件时间">
        <a-date-picker
          v-decorator="[
            'fileDate',
            { rules: [{ required: true, message: '文件时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="文件类型">
         <a-select
          placeholder="请输入文件类型"
          v-decorator="[
            'fileType',
            { rules: [{ required: true, message: '文件类型不能为空' }] },
          ]"
        >
        <a-select-option value="评优评先">评优评先</a-select-option>
        <a-select-option value="职称晋升">职称晋升</a-select-option>
        <a-select-option value="人才项目">人才项目</a-select-option>
        <a-select-option value="自纠情况库">自纠情况库</a-select-option>
         <a-select-option value="行风管理文件">行风管理文件</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'note',
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
import moment from "moment";
import UploadSingleFile from "../../common/uploadSingleFile"

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBArchiveEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xfBArchive: {},
    };
  },
  components: {UploadSingleFile},
  methods: {
    reset() {
      this.loading = false;
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
     uploadAgent_1(fileId, fileUrl) {
      this.xfBArchive.fileId = fileId;
      this.xfBArchive.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBArchive.fileId = "";
      this.xfBArchive.fileUrl = "";
    },
    setFormValues({ ...xfBArchive }) {
      let fields = [
        "fileName",
        "fileCode",
        "fileDate",
        "fileType",
        "note",
      ];
      let fieldDates = ["fileDate"];
      Object.keys(xfBArchive).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBArchive[key] !== null && xfBArchive[key] !== "") {
              obj[key] = moment(xfBArchive[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBArchive[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
    
       this.xfBArchive.fileId = xfBArchive.fileId;
      this.xfBArchive.fileUrl = xfBArchive.fileUrl;
      this.xfBArchive.id = xfBArchive.id;
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(xfBArchive.fileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let xfBArchive = this.form.getFieldsValue();
          xfBArchive.id = this.xfBArchive.id;
          xfBArchive.fileId = this.xfBArchive.fileId;
          xfBArchive.fileUrl = this.xfBArchive.fileUrl;
          this.$put("xfBArchive", {
            ...xfBArchive,
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
  },
};
</script>

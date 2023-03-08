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
      <a-form-item v-bind="formItemLayout" label="自查日期">
        <a-date-picker
          v-decorator="[
            'zichaDate',
            { rules: [{ required: true, message: '自查日期不能为空' }] },
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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "AssetBZichaEdit",
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
      assetBZicha: {},
    };
  },
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
      this.assetBZicha.fileId = fileId;
      this.assetBZicha.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.assetBZicha.fileId = "";
      this.assetBZicha.fileUrl = "";
    },
    setFormValues({ ...assetBZicha }) {
      let fields = [ "zichaDate",  "note"];
      let fieldDates = ["zichaDate"];
      Object.keys(assetBZicha).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (assetBZicha[key] !== null && assetBZicha[key] !== "") {
              obj[key] = moment(assetBZicha[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = assetBZicha[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.assetBZicha.id = assetBZicha.id;
       this.assetBZicha.fileId = assetBZicha.fileId;
      this.assetBZicha.fileUrl = assetBZicha.fileUrl;
     
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(assetBZicha.fileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let assetBZicha = this.form.getFieldsValue();
          assetBZicha.id = this.assetBZicha.id;
           assetBZicha.fileId = this.assetBZicha.fileId;
          assetBZicha.fileUrl = this.assetBZicha.fileUrl;
          this.$put("assetBZicha", {
            ...assetBZicha,
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

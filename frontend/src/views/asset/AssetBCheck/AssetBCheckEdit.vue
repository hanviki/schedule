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
      <a-button @click="handleSubmit" type="primary"  :loading="loading"
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
  name: "AssetBCheckEdit",
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
      assetBCheck: {},
    };
  },
   components: {UploadSingleFile},
  methods: {
    reset() {
      this.loading = false;
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
    setFormValues({ ...assetBCheck }) {
      let fields = [
        "checkType",
        "checkNo",
        "checkDate",
        "checkResult",
        "note",
        "checkCs",
      ];
      let fieldDates = ["checkDate"];
      Object.keys(assetBCheck).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (assetBCheck[key] !== null && assetBCheck[key] !== "") {
              obj[key] = moment(assetBCheck[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = assetBCheck[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.assetBCheck.id = assetBCheck.id;
      this.assetBCheck.fileId = assetBCheck.fileId;
      this.assetBCheck.fileUrl = assetBCheck.fileUrl;
     
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(assetBCheck.fileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let assetBCheck = this.form.getFieldsValue();
          assetBCheck.id = this.assetBCheck.id;
           assetBCheck.fileId = this.assetBCheck.fileId;
          assetBCheck.fileUrl = this.assetBCheck.fileUrl;
          this.$put("assetBCheck", {
            ...assetBCheck,
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

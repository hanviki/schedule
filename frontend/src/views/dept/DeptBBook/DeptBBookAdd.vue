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
      <a-form-item v-bind="formItemLayout" label="证件类型">
       <a-select
          placeholder="请输入证件类型"
          v-decorator="[
            'bookType',
            { rules: [{ required: true, message: '证件类型不能为空' }] },
          ]"
        >
        <a-select-option v-for="d in dicType" :key="d.fieldName" :value="d.bookType">{{d.bookType}}</a-select-option>
        </a-select>
      </a-form-item>
     
      <a-form-item v-bind="formItemLayout" label="证件编号">
        <a-input
          placeholder="请输入证件编号"
          v-decorator="[
            'bookNo',
            { rules: [{ required: true, message: '证件编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="发证时间">
        <a-date-picker
          v-decorator="[
            'bookDate',
            { rules: [{ required: true, message: '发证时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'note',
           
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
import UploadSingleFile from "../../common/uploadSingleFile2"
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "DeptBBookAdd",
  props: {
    addVisiable: {
      default: false,
    },
     userAccount: {
      default: "",
    },
    userAccountName: {
      default: "",
    },
  },
    components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      deptBBook: {},
      dicType: []
    };
  },
   watch: {
    addVisiable() {
      if (this.addVisiable) {
        this.fetchDicType()
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.deptBBook = {};
       this.$refs.fileagent.reset();
      this.form.resetFields();
    },
     uploadAgent_1(fileId, fileUrl) {
      this.deptBBook.fileId = fileId;
      this.deptBBook.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.deptBBook.fileId = "";
      this.deptBBook.fileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          
          this.$post("deptBBook", {
            ...this.deptBBook,
            userAccount:this.userAccount,
            userAccountName:this.userAccountName,
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
    fetchDicType(){
          this.$get("deptBUser/type", {
        deptId: this.$store.state.account.user.deptId
      }).then((r) => {
        console.log(r);
        this.dicType = r.data;
      });
    },
     setFormValues({ ...deptBBook }) {
      let fields = [
       
        "bookType",
        "bookNo",
        "bookDate",
        "note",
      ];
      let fieldDates = ["bookDate"];
      Object.keys(deptBBook).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (deptBBook[key] !== null && deptBBook[key] !== "") {
              obj[key] = moment(deptBBook[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = deptBBook[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.deptBBook.id = deptBBook.id;
    },
    setFields() {
      let values = this.form.getFieldsValue([
      
        "bookType",
       
        "bookNo",
        "bookDate",
        "note",
      
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.deptBBook[_key] = values[_key];
        });
      }
    },
  },
};
</script>

<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
     
      <a-form-item v-bind="formItemLayout" label="项目汇报内容">
        <a-input
          placeholder="请输入项目汇报内容"
          v-decorator="[
            'content',
            { rules: [{ required: true, message: '项目汇报内容不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="汇报人">
        <a-input
          placeholder="请输入汇报人"
          v-decorator="[
            'person',
            { rules: [{ required: true, message: '汇报人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="汇报时间">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'pojectDate',
            { rules: [{ required: true, message: '汇报时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="汇报对象">
        <a-input
          placeholder="请输入汇报对象"
          v-decorator="[
            'leader',
            { rules: [{ required: true, message: '汇报对象不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="汇报地点">
        <a-input
          placeholder="请输入汇报地点"
          v-decorator="[
            'address',
            { rules: [{ required: true, message: '汇报地点不能为空' }] },
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
  name: "XfBProjectreportAdd",
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
      xfBProjectreport: {},
    };
  },
    components: {UploadSingleFile},
  methods: {
    reset() {
      this.loading = false;
      this.xfBProjectreport = {};
       this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
      uploadAgent_1(fileId, fileUrl) {
      this.xfBProjectreport.fileId = fileId;
      this.xfBProjectreport.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBProjectreport.fileId = "";
      this.xfBProjectreport.fileUrl = "";
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$put("xfBProjectreport", {
            ...this.xfBProjectreport,
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
       
        "content",
        "person",
        "pojectDate",
        "leader",
        "address",
      
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBProjectreport[_key] = values[_key];
        });
      }
    },
     setFormValues({ ...xfBProjectreport }) {
      let fields = [
       
        "content",
        "person",
        "pojectDate",
        "leader",
        "address",
        "fileId",
        "fileUrl",
        "remark",
      ];
      let fieldDates = ["pojectDate"];
      Object.keys(xfBProjectreport).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (
              xfBProjectreport[key] !== null &&
              xfBProjectreport[key] !== ""
            ) {
              obj[key] = moment(xfBProjectreport[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBProjectreport[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBProjectreport.id = xfBProjectreport.id;
        let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(xfBProjectreport.fileId);
      },100)
    },
  },
};
</script>

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
      <a-form-item v-bind="formItemLayout" label="姓名">
        <a-input
          placeholder="请输入姓名"
          v-decorator="[
            'userAccountName',
            { rules: [{ required: true, message: '姓名不能为空' }] },
          ]"
        />
      </a-form-item>
    
      <a-form-item v-bind="formItemLayout" label="领域">
        <a-input
          placeholder="请输入领域"
          v-decorator="[
            'lingyu',
            { rules: [{ required: true, message: '领域不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="工作单位">
        <a-input
          placeholder="请输入工作单位"
          v-decorator="[
            'workUnit',
            { rules: [{ required: true, message: '工作单位不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="职务">
        <a-input
          placeholder="请输入职务"
          v-decorator="[
            'zw',
            { rules: [{ required: true, message: '职务不能为空' }] },
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
      <a-form-item v-bind="formItemLayout" label="社会兼职">
        <a-input
          placeholder="请输入社会兼职"
          v-decorator="[
            'job',
            { rules: [{ required: true, message: '社会兼职不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="推荐部门">
        <a-input
          placeholder="请输入推荐部门"
          v-decorator="[
            'tjbm',
            { rules: [{ required: true, message: '推荐部门不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开始日期">
        <a-date-picker
          v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '开始日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="结束日期">
        <a-date-picker
          v-decorator="[
            'endDate',
            { rules: [{ required: true, message: '结束日期不能为空' }] },
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

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBSocialEdit",
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
      xfBSocial: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues({ ...xfBSocial }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "deptNew",
        "fileId",
        "fileUrl",
        "lingyu",
        "workUnit",
        "zw",
        "job",
        "tjbm",
        "startDate",
         "telphone",
        "endDate",
        "remark",
      ];
      let fieldDates = ["startDate", "endDate"];
      Object.keys(xfBSocial).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBSocial[key] !== null && xfBSocial[key] !== "") {
              obj[key] = moment(xfBSocial[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBSocial[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBSocial.id = xfBSocial.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let xfBSocial = this.form.getFieldsValue();
          xfBSocial.id = this.xfBSocial.id;
          this.$put("xfBSocial", {
            ...xfBSocial,
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

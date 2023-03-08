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
      <a-form-item v-bind="formItemLayout" label="医嘱编码">
        <a-input
          placeholder="请输入医嘱编码"
          v-decorator="[
            'code',
            { rules: [{ required: true, message: '医嘱编码不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="医嘱名称">
        <a-input
          placeholder="请输入医嘱名称"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '医嘱名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="明细项目名称">
        <a-input
          placeholder="请输入明细项目名称"
          v-decorator="[
            'detailName',
            { rules: [{ required: true, message: '明细项目名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="使用范围">
        <a-input
          placeholder="请输入使用范围"
          v-decorator="[
            'fanwei',
            { rules: [{ required: true, message: '使用范围不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="明细项目编码">
        <a-input
          placeholder="请输入明细项目编码"
          v-decorator="[
            'detailCode',
            { rules: [{ required: true, message: '明细项目编码不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="物价编号">
        <a-input
          placeholder="请输入物价编号"
          v-decorator="[
            'priceCode',
            { rules: [{ required: true, message: '物价编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="金额">
        <a-input-number
          placeholder="请输入金额"
          v-decorator="[
            'amount',
            { rules: [{ required: true, message: '金额不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否接入LIS">
        <a-select
          placeholder="请输入是否接入LIS"
          v-decorator="[
            'isLis',
            { rules: [{ required: true, message: '是否接入LIS不能为空' }] },
          ]"
        >
        <a-select-option value="是">是</a-select-option>
        <a-select-option value="否">否</a-select-option>
        </a-select>
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
  name: "MdlLProjectEdit",
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
      mdlLProject: {},
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
    setFormValues({ ...mdlLProject }) {
      let fields = [
       
        "code",
        "name",
        "detailName",
        "fanwei",
        "detailCode",
        "priceCode",
        "amount",
        "isLis",
      
      ];
      let fieldDates = [];
      Object.keys(mdlLProject).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlLProject[key] !== null && mdlLProject[key] !== "") {
              obj[key] = moment(mdlLProject[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlLProject[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlLProject.id = mdlLProject.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlLProject = this.form.getFieldsValue();
          mdlLProject.id = this.mdlLProject.id;
          this.$put("mdlLProject", {
            ...mdlLProject,
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

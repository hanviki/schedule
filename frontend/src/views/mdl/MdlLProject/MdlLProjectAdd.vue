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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlLProjectAdd",
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
      mdlLProject: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.mdlLProject = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setBaseId(baseId){
      this.mdlLProject.baseId =baseId
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("mdlLProject", {
            ...this.mdlLProject,
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
        "code",
        "name",
        "detailName",
        "fanwei",
        "detailCode",
        "priceCode",
        "amount",
        "isLis",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.mdlLProject[_key] = values[_key];
        });
      }
    },
  },
};
</script>

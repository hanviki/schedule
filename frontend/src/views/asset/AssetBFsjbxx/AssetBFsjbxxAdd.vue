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
      <a-form-item v-bind="formItemLayout" label="创建日期">
        <a-date-picker
          v-decorator="[
            'createDate',
            { rules: [{ required: true, message: '创建日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="类别">
        <a-input
          placeholder="请输入类别"
          v-decorator="[
            'lb',
            { rules: [{ required: true, message: '类别不能为空' }] },
          ]"
        />
        
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="核素名称">
        <a-input
          placeholder="请输入核素名称"
          v-decorator="[
            'hsmc',
            { rules: [{ required: true, message: '核素名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="枚数">
        <a-input
          placeholder="请输入枚数"
          v-decorator="[
            'meishu',
            { rules: [{ required: true, message: '枚数不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="总活度">
        <a-input
          placeholder="请输入总活度"
          v-decorator="[
            'zhd',
            { rules: [{ required: true, message: '总活度不能为空' }] },
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
  name: "AssetBFsjbxxAdd",
  props: {
    addVisiable: {
      default: false,
    },
     baseId: {
      default: "",
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      assetBFsjbxx: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.assetBFsjbxx = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.assetBFsjbxx.baseId= this.baseId
          this.$post("assetBFsjbxx", {
            ...this.assetBFsjbxx,
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
        
        "createDate",
        "lb",
        "hsmc",
        "meishu",
        "zhd",
        "note",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.assetBFsjbxx[_key] = values[_key];
        });
      }
    },
  },
};
</script>

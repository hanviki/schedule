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
      <a-form-item v-bind="formItemLayout" label="操作时间">
        <a-date-picker
          v-decorator="[
            'operationDate',
            { rules: [{ required: true, message: '操作时间不能为空' }] },
          ]"
        />
      </a-form-item>
        <a-form-item v-bind="formItemLayout" label="状态">
        <a-select 
        placeholder="请输入状态"
          v-decorator="[
            'state',
            { rules: [{ required: true, message: '状态不能为空' }] },
          ]"
        >
            <a-select-option :value="0" :key="0">
                开始监测
            </a-select-option>
             <a-select-option :value="1" :key="1">
                暂停监测
            </a-select-option>
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
    </a-form>
    <div class="drawer-bootom-button" style="z-index:999999">
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
  name: "FsBRecordAdd",
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
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      fsBRecord: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.fsBRecord = {};
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
          this.fsBRecord.userAccount= this.userAccount
          this.fsBRecord.userAccountName= this.userAccountName
          this.$post("fsBRecord", {
            ...this.fsBRecord,
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
        "operationDate",
        "state",
        "note",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.fsBRecord[_key] = values[_key];
        });
      }
    },
  },
};
</script>

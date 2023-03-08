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
      <a-form-item v-bind="formItemLayout" label="年度">
        
          <a-select
         placeholder="请输入年度"
          v-decorator="[
            'year',
            { rules: [{ required: true, message: '年度不能为空' }] },
          ]"
        >
           <a-select-option value="2022" :key="2022">
            2022
           </a-select-option>
           <a-select-option value="2023" :key="2023">
            2023
           </a-select-option>
           <a-select-option value="2024" :key="2024">
            2024
           </a-select-option>
           <a-select-option value="2025" :key="2025">
            2025
           </a-select-option>
           <a-select-option value="2026" :key="2026">
            2026
           </a-select-option>
           <a-select-option value="2027" :key="2027">
            2027
           </a-select-option>
           <a-select-option value="2028" :key="2028">
            2028
           </a-select-option>
           <a-select-option value="2029" :key="2029">
            2029
           </a-select-option>
           <a-select-option value="2030" :key="2030">
            2030
           </a-select-option>
           <a-select-option value="2031" :key="2031">
            2031
           </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="季度">
        <a-select
        placeholder="请输入季度"
          v-decorator="[
            'quarter',
            { rules: [{ required: true, message: '季度不能为空' }] },
          ]"
        >
           <a-select-option value="1" :key="1">
            1
           </a-select-option>
           <a-select-option value="2" :key="2">
            2
           </a-select-option>
           <a-select-option value="3" :key="3">
            3
           </a-select-option>
           <a-select-option value="4" :key="4">
            4
           </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="计量牌编号">
        <a-input
          placeholder="请输入计量牌编号"
          v-decorator="[
            'number',
            { rules: [{ required: true, message: '计量牌编号不能为空' }] },
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
  name: "FsBQuarterAdd",
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
      fsBQuarter: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.fsBQuarter = {};
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
          this.$post("fsBQuarter", {
            ...this.fsBQuarter,
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
      let values = this.form.getFieldsValue(["year", "quarter", "number"]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.fsBQuarter[_key] = values[_key];
        });
      }
    },
  },
};
</script>

<template>
  <a-drawer
    title="增加备注"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="年度">
         <a-input placeholder="请输入年度"
         :disabled="true"
          v-decorator="[
            'year',
            { rules: [{ required: true, message: '年度不能为空' }] },
          ]" />
       
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="季度">
        <a-input placeholder="请输入季度"
         :disabled="true"
          v-decorator="[
            'quarter',
            { rules: [{ required: true, message: '季度不能为空' }] },
          ]" />
        
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="计量牌编号">
        <a-input
        :disabled="true"
          placeholder="请输入计量牌编号"
          v-decorator="[
            'number',
            { rules: [{ required: true, message: '计量牌编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="增加备注">
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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "FsBQuarterEdit",
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
      fsBQuarter: {},
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
    setFormValues({ ...fsBQuarter }) {
      let fields = ["year", "quarter", "number","note"];
      let fieldDates = [];
      Object.keys(fsBQuarter).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (fsBQuarter[key] !== null && fsBQuarter[key] !== "") {
              obj[key] = moment(fsBQuarter[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = fsBQuarter[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.fsBQuarter.id = fsBQuarter.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let fsBQuarter = this.form.getFieldsValue();
          fsBQuarter.id = this.fsBQuarter.id;
          this.$put("fsBQuarter", {
            ...fsBQuarter,
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

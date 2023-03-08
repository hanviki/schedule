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
      <a-form-item v-bind="formItemLayout" label="设备状态">
        <a-select  placeholder="请输入设备状态"
          v-decorator="[
            'assetState',
            { rules: [{ required: true, message: '设备状态不能为空' }] },
          ]">
          <a-select-option value="在用">在用 </a-select-option>
          <a-select-option value="停用">停用 </a-select-option>
          <a-select-option value="报废">报废 </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="操作时间">
        <a-date-picker
          v-decorator="[
            'operationDate',
            { rules: [{ required: true, message: '操作时间不能为空' }] },
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
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "AssetBStateEdit",
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
      assetBState: {},
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
    setFormValues({ ...assetBState }) {
      let fields = [ "assetState", "operationDate", "note"];
      let fieldDates = ["operationDate"];
      Object.keys(assetBState).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (assetBState[key] !== null && assetBState[key] !== "") {
              obj[key] = moment(assetBState[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = assetBState[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.assetBState.id = assetBState.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let assetBState = this.form.getFieldsValue();
          assetBState.id = this.assetBState.id;
          this.$put("assetBState", {
            ...assetBState,
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

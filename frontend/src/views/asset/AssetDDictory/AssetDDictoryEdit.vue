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
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="上级类型ID">
          <a-input
            placeholder="请输入上级类型ID"
            v-decorator="[
              'parentId',
              { rules: [{ required: true, message: '上级类型ID不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="类型名称">
          <a-input
            placeholder="请输入类型名称"
            v-decorator="[
              'name',
              { rules: [{ required: true, message: '类型名称不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item
          v-bind="formItemLayout"
          label="0是卫生1是环保2是个人防护用品3辅助防护用品"
        >
          <a-input
            placeholder="请输入0是卫生1是环保2是个人防护用品3辅助防护用品"
            v-decorator="[
              'typeIndex',
              {
                rules: [
                  {
                    required: true,
                    message:
                      '0是卫生1是环保2是个人防护用品3辅助防护用品不能为空',
                  },
                ],
              },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="6">
        <a-form-item v-bind="formItemLayout" label="排序">
          <a-input
            placeholder="请输入排序"
            v-decorator="[
              'orderNum',
              { rules: [{ required: true, message: '排序不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
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
  name: "AssetDDictoryEdit",
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
      assetDDictory: {},
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
    setFormValues({ ...assetDDictory }) {
      let fields = ["parentId", "name", "typeIndex", "orderNum"];
      let fieldDates = [];
      Object.keys(assetDDictory).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (assetDDictory[key] !== null && assetDDictory[key] !== "") {
              obj[key] = moment(assetDDictory[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = assetDDictory[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.assetDDictory.id = assetDDictory.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let assetDDictory = this.form.getFieldsValue();
          assetDDictory.id = this.assetDDictory.id;
          this.$put("assetDDictory", {
            ...assetDDictory,
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

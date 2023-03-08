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
     <a-form-item v-bind="formItemLayout" label="操作">
        <a-select 
       placeholder="请输入操作"
          v-decorator="[
            'operation',
            { rules: [{ required: true, message: '操作不能为空' }] },
          ]"
        >
            <a-select-option value="领" :key="0">
                领
            </a-select-option>
             <a-select-option value="还" :key="1">
                还
            </a-select-option>
        </a-select>
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
  name: "FsBQuarterDEdit",
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
      fsBQuarterD: {},
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
    setFormValues({ ...fsBQuarterD }) {
      let fields = ["year", "quarter", "number", "operation"];
      let fieldDates = [];
      Object.keys(fsBQuarterD).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (fsBQuarterD[key] !== null && fsBQuarterD[key] !== "") {
              obj[key] = moment(fsBQuarterD[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = fsBQuarterD[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.fsBQuarterD.id = fsBQuarterD.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let fsBQuarterD = this.form.getFieldsValue();
          fsBQuarterD.id = this.fsBQuarterD.id;
          this.$put("fsBQuarterD", {
            ...fsBQuarterD,
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

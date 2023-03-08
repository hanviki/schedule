<template>
  <a-drawer
    title="编辑"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      
     <a-form-item v-bind="formItemLayout" label="证件类型">
        <a-select
          placeholder="请输入证件类型"
          v-decorator="[
            'bookType',
            { rules: [{ required: true, message: '证件类型不能为空' }] },
          ]"
        >
        <a-select-option v-for="d in dicType" :key="d.fieldName" :value="d.bookType">{{d.bookType}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="检测时间">
        <a-date-picker
          v-decorator="[
            'checkDate',
            { rules: [{ required: true, message: '检测时间不能为空' }] },
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
  name: "DeptBBookDAdd",
 props: {
    editVisiable: {
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
      deptBBookD: {},
      dicType: []
    };
  },
   watch: {
    editVisiable() {
      if (this.addVisiable) {
        this.fetchDicType()
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.deptBBookD = {};
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
          this.$put("deptBBookD", {
            ...this.deptBBookD,
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
    fetchDicType(){
          this.$get("deptBUser/type", {
        deptId: this.$store.state.account.user.deptId
      }).then((r) => {
        console.log(r);
        this.dicType = r.data;
      });
    },
    setFormValues({ ...deptBBookD }) {
      let fields = [
       
        "bookType",
        "checkDate",
        "note",
      ];
      let fieldDates = ["checkDate"];
      Object.keys(deptBBookD).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (deptBBookD[key] !== null && deptBBookD[key] !== "") {
              obj[key] = moment(deptBBookD[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = deptBBookD[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.deptBBookD.id = deptBBookD.id;
      
    },
    setFields() {
      let values = this.form.getFieldsValue([
       
        "bookType",
        "checkDate",
        "note",
       
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.deptBBookD[_key] = values[_key];
        });
      }
    },
  },
};
</script>

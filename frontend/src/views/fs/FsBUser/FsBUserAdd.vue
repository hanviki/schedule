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
      <a-form-item v-bind="formItemLayout" label="姓名">
        <a-input
          placeholder="请输入姓名"
          v-decorator="[
            'userAccountName',
            { rules: [{ required: true, message: '姓名不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="人事编号">
        <a-input
          placeholder="请输入人事编号"
          v-decorator="[
            'userAccount',
            { rules: [{ required: true, message: '人事编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="个人剂量计编号">
        <a-input
          placeholder="请输入个人剂量计编号"
          v-decorator="[
            'grjlbh',
            { rules: [{ required: true, message: '个人剂量计编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="放射工作人员证职业类别">
        <a-input
          placeholder="请输入放射工作人员证职业类别"
          v-decorator="[
            'zylb',
            {
              rules: [
                { required: true, message: '放射工作人员证职业类别不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="岗位类别">
        <a-input
          placeholder="请输入岗位类别"
          v-decorator="[
            'gwlb',
            { rules: [{ required: true, message: '岗位类别不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="核定计量牌个数">
        <a-input
          placeholder="请输入核定计量牌个数"
          v-decorator="[
            'hdjlpgs',
            { rules: [{ required: true, message: '核定计量牌个数不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="放射工龄">
        <a-input
          placeholder="请输入放射工龄"
          v-decorator="[
            'fsgl',
            { rules: [{ required: true, message: '放射工龄不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开始从事放射工作时间">
        <a-date-picker
          v-decorator="[
            'fsDate',
            {
              rules: [
                { required: true, message: '开始从事放射工作时间不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开始从事放射工作时间">
        <a-date-picker
          v-decorator="[
            'jcksDate',
            {
              rules: [
                { required: true, message: '开始从事放射工作时间不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="放射工作人员证号码">
        <a-input
          placeholder="请输入放射工作人员证号码"
          v-decorator="[
            'fsgzzh',
            {
              rules: [
                { required: true, message: '放射工作人员证号码不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="办证时间">
        <a-date-picker
          v-decorator="[
            'bzDate',
            { rules: [{ required: true, message: '办证时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="科室名称">
        <a-input
          placeholder="请输入科室名称"
          v-decorator="[
            'deptName',
            { rules: [{ required: true, message: '科室名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="附件">
        <a-input
          placeholder="请输入附件"
          v-decorator="[
            'fileId',
            { rules: [{ required: true, message: '附件不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="附件地址">
        <a-input
          placeholder="请输入附件地址"
          v-decorator="[
            'fileUrl',
            { rules: [{ required: true, message: '附件地址不能为空' }] },
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
  name: "FsBUserAdd",
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
      fsBUser: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.fsBUser = {};
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
          this.$post("fsBUser", {
            ...this.fsBUser,
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
        "userAccountName",
        "userAccount",
        "grjlbh",
        "zylb",
        "gwlb",
        "hdjlpgs",
        "fsgl",
        "fsDate",
        "jcksDate",
        "fsgzzh",
        "bzDate",
        "deptName",
        "fileId",
        "fileUrl",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.fsBUser[_key] = values[_key];
        });
      }
    },
  },
};
</script>

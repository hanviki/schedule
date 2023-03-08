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
       <a-form-item v-bind="formItemLayout" label="姓名">
        <a-select
          v-decorator="[
            'userAccount',
            { rules: [{ required: true, message: '人员必填' }] },
          ]"
          @search="handleSearch"
          :filter-option="false"
          show-search
          @change="userChange"
        >
          <a-select-option
            v-for="d2 in userData"
            :key="d2"
            :value="`${d2.userAccount}`"
          >
            {{ d2.userAccountName + " " + d2.userAccount + " " + d2.birthday+ " " + d2.deptNew }}
          </a-select-option>
        </a-select>
      </a-form-item>
    
    
      <a-form-item v-bind="formItemLayout" label="投诉日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'tousuDate',
            { rules: [{ required: true, message: '投诉日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="投诉内容">
        <a-input
          placeholder="请输入投诉内容"
          v-decorator="[
            'content',
            { rules: [{ required: true, message: '投诉内容不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="经办人">
        <a-input
          placeholder="请输入经办人"
          v-decorator="[
            'operationName',
            { rules: [{ required: true, message: '经办人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="转办单位">
        <a-input
          placeholder="请输入转办单位"
          v-decorator="[
            'unit',
            { rules: [{ required: true, message: '转办单位不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="接受日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'receiveDate',
            { rules: [{ required: true, message: '接受日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否有效投诉">
        <a-select
          placeholder="请输入是否有效投诉"
          v-decorator="[
            'isValid',
            { rules: [{ required: true, message: '是否有效投诉不能为空' }] },
          ]"
        >
        <a-select-option value="是">是</a-select-option>
        <a-select-option value="否">否</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="处理结果">
        <a-input
          placeholder="请输入处理结果"
          v-decorator="[
            'redult',
            { rules: [{ required: true, message: '处理结果不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="领导小组意见">
        <a-input
          placeholder="请输入领导小组意见"
          v-decorator="[
            'suggestion',
            { rules: [{ required: true, message: '领导小组意见不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="审核人">
        <a-input
          placeholder="请输入审核人"
          v-decorator="[
            'auditMan',
            { rules: [{ required: true, message: '审核人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'remark',
            { rules: [{ required: true, message: '备注不能为空' }] },
          ]"
        />
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="附件">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
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
import UploadSingleFile from "../../common/uploadSingleFile"
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBTousuEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
    components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xfBTousu: {},
        userData: [],
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
     uploadAgent_1(fileId, fileUrl) {
      this.xfBTousu.fileId = fileId;
      this.xfBTousu.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBTousu.fileId = "";
      this.xfBTousu.fileUrl = "";
    },
     handleSearch(value) {
      this.fetchUser(value);
    },
    fetchUser(userAccount) {
      this.$get("sdlBUserMg/search", {
        userAccount,
      }).then((r) => {
        console.log(r);
        this.userData = r.data;
      });
    },
    userChange(value, option) {
      console.info(option);
      this.xfBTousu["userAccountName"] = option.key.userAccountName;
      this.xfBTousu["userAccount"] = option.key.userAccount;
      this.xfBTousu["deptNew"] = option.key.deptNew;
      this.xfBTousu["zhicheng"] = option.key.zhicheng;
    },
     setFields() {
      let values = this.form.getFieldsValue([
       
        "tousuDate",
        "content",
        "operationName",
        "unit",
        "receiveDate",
        "isValid",
        "redult",
        "suggestion",
        "auditMan",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBTousu[_key] = values[_key];
        });
      }
    },
    setFormValues({ ...xfBTousu }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "deptNew",
        "fileId",
        "fileUrl",
        "tousuDate",
        "content",
        "operationName",
        "unit",
        "receiveDate",
        "isValid",
        "redult",
        "suggestion",
        "auditMan",
        "remark",
      ];
      let fieldDates = ["tousuDate", "receiveDate"];
      Object.keys(xfBTousu).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBTousu[key] !== null && xfBTousu[key] !== "") {
              obj[key] = moment(xfBTousu[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBTousu[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBTousu.id = xfBTousu.id;
      this.fetchUser(xfBTousu.userAccount)
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(xfBTousu.fileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$put("xfBTousu", {
            ...this.xfBTousu,
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

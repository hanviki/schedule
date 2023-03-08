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
      
        <a-form-item v-bind="formItemLayout" label="人员">
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
      <a-form-item v-bind="formItemLayout" label="姓名">
        <a-input disabled v-decorator="['userAccountName']" />
      </a-form-item>
      <!-- <a-form-item v-bind="formItemLayout" label="计量牌编号">
        <a-input
          placeholder="请输入计量牌编号"
          v-decorator="[
            'number',
            { rules: [{ required: true, message: '计量牌编号不能为空' }] },
          ]"
        />
      </a-form-item> -->
      <a-form-item v-bind="formItemLayout" label="考试类型">
       <a-select
        placeholder="请输入考试类型"
          v-decorator="[
            'exameType',
            { rules: [{ required: true, message: '考试类型不能为空' }] },
          ]"
        >
            <a-select-option value="环保考试">
                环保考试
            </a-select-option>
            <a-select-option value="卫生考试">
                卫生考试
            </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="考试时间">
        <a-date-picker
          v-decorator="[
            'exameDate',
            { rules: [{ required: true, message: '考试时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="考试编号">
        <a-input
          placeholder="请输入考试编号"
          v-decorator="[
            'exameNum',
            { rules: [{ required: true, message: '考试编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="考试成绩">
        <a-input
          placeholder="请输入考试成绩"
          v-decorator="[
            'exameScore',
            { rules: [{ required: true, message: '考试成绩不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="考试总分">
        <a-input
          placeholder="请输入考试总分"
          v-decorator="[
            'exameTotal',
            { rules: [{ required: true, message: '考试总分不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="考试专业">
        <a-input
          placeholder="请输入考试专业"
          v-decorator="[
            'exameZy',
            { rules: [{ required: true, message: '考试专业不能为空' }] },
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
import UploadSingleFile from "../../common/uploadSingleFile2"

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "FsBExameEdit",
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
      fsBExame: {},
       userData: [],
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
     uploadAgent_1(fileId, fileUrl) {
      this.fsBExame.fileId = fileId;
      this.fsBExame.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.fsBExame.fileId = "";
      this.fsBExame.fileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
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
      this.form.getFieldDecorator("userAccountName");
      this.form.setFieldsValue({ userAccountName: option.key.userAccountName });
    },
    setFormValues({ ...fsBExame }) {
      let fields = [
        "userAccountName",
        "userAccount",
      //  "number",
        "exameType",
        "exameDate",
        "exameNum",
        "exameScore",
        "exameTotal",
        "exameZy",
      
      ];
      let fieldDates = ["exameDate"];
      Object.keys(fsBExame).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (fsBExame[key] !== null && fsBExame[key] !== "") {
              obj[key] = moment(fsBExame[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = fsBExame[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.fsBExame.id = fsBExame.id;
         this.fsBExame.fileId = fsBExame.fileId;
      this.fsBExame.fileUrl = fsBExame.fileUrl;
   if(fsBExame.fileId!==null && fsBExame.fileId!==''){
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(fsBExame.fileId);
      },100)
     }
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let fsBExame = this.form.getFieldsValue();
          fsBExame.id = this.fsBExame.id;
           fsBExame.fileId = this.fsBExame.fileId;
          fsBExame.fileUrl = this.fsBExame.fileUrl;
          this.$put("fsBExame", {
            ...fsBExame,
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

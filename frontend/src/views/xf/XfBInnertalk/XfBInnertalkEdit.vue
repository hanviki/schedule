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
        <!-- <a-form-item v-bind="formItemLayout" label="姓名">
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
      </a-form-item> -->
     
      <a-form-item v-bind="formItemLayout" label="谈话日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'talkDate',
            { rules: [{ required: true, message: '谈话日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="谈话缘由">
        <a-input
          placeholder="请输入谈话缘由"
          v-decorator="[
            'content',
            { rules: [{ required: true, message: '谈话缘由不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="谈话部门">
        <a-input
          placeholder="请输入谈话部门"
          v-decorator="[
            'deptNew',
            { rules: [{ required: true, message: '谈话部门不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="参与人">
        <a-input
          placeholder="请输入参与人"
          v-decorator="[
            'operationName',
            { rules: [{ required: true, message: '参与人不能为空' }] },
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
     <a-card title="人员" >
        <add-user ref="userHX" :baseId="xfBInnertalk.id" ></add-user>
    </a-card>
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
import UploadSingleFile from "../../common/uploadSingleFile"
import AddUser from './AddTalkUser.vue';
import moment from "moment";
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBInnertalkAdd",
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
      xfBInnertalk: {},
      userData: [],
    };
  },
  components: {UploadSingleFile,AddUser},
  methods: {
    reset() {
      this.loading = false;
      this.xfBInnertalk = {};
      this.$refs.fileagent.reset();
       this.$refs.userHX.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
      uploadAgent_1(fileId, fileUrl) {
      this.xfBInnertalk.fileId = fileId;
      this.xfBInnertalk.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBInnertalk.fileId = "";
      this.xfBInnertalk.fileUrl = "";
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
      this.xfBInnertalk["userAccountName"] = option.key.userAccountName;
      this.xfBInnertalk["userAccount"] = option.key.userAccount;
      this.xfBInnertalk["deptNew"] = option.key.deptNew;
      this.xfBInnertalk["zhicheng"] = option.key.zhicheng;
    },
     setFormValues({ ...xfBInnertalk }) {
      let fields = [
        // "userAccountName",
        // "userAccount",
        "deptNew",
        "fileId",
        "fileUrl",
        "talkDate",
        "content",
        "operationName",
        "remark",
      ];
      let fieldDates = ["talkDate"];
      Object.keys(xfBInnertalk).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBInnertalk[key] !== null && xfBInnertalk[key] !== "") {
              obj[key] = moment(xfBInnertalk[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBInnertalk[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBInnertalk.id = xfBInnertalk.id;
      this.fetchUser(xfBInnertalk.userAccount)
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(xfBInnertalk.fileId);
         that.$refs.userHX.fetch()
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
           let jsond1= this.$refs.userHX.getAll()
          this.$put("xfBInnertalk", {
             user: jsond1,
            ...this.xfBInnertalk,
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
        "deptNew",
        "talkDate",
        "content",
        "operationName",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBInnertalk[_key] = values[_key];
        });
      }
    },
  },
};
</script>

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
    <a-form-item v-bind="formItemLayout" label="实验室编码">
        <a-input
          placeholder="请输入实验室编码"
          v-decorator="[
            'labCode',
            { rules: [{ required: true, message: '实验室编码不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="实验室名称">
        <a-input
          placeholder="请输入实验室名称"
          v-decorator="[
            'labName',
            { rules: [{ required: true, message: '实验室名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="所属科室">
        <a-input
          placeholder="请输入所属科室"
          v-decorator="[
            'deptNew',
            { rules: [{ required: true, message: '所属科室不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="负责人">
        <a-input
          placeholder="请输入负责人"
          v-decorator="[
            'leader',
            { rules: [{ required: true, message: '负责人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="联系方式">
        <a-input
          placeholder="请输入联系方式"
          v-decorator="[
            'telphone',
            { rules: [{ required: true, message: '联系方式不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="实验室联络人">
        <a-input
          placeholder="请输入实验室联络人"
          v-decorator="[
            'linker',
            { rules: [{ required: true, message: '实验室联络人不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="联系方式">
        <a-input
          placeholder="请输入联系方式"
          v-decorator="[
            'linkTel',
            { rules: [{ required: true, message: '联系方式不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="实验室地点">
        <a-input
          placeholder="请输入实验室地点"
          v-decorator="[
            'address',
            { rules: [{ required: true, message: '实验室地点不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="实验室资质">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
      </a-form-item>
    </a-form>
    <a-card title="可开展项目信息" >
       <mdl-l-project ref="mdllp" :baseId="mdlLBaseinfo.id">
       </mdl-l-project>
    </a-card>
    <a-card title="人员资质" >
        <add-user ref="userHX" :baseId="mdlLBaseinfo.id" ></add-user>
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
import moment from "moment";
import AddUser from './AddUser.vue'
import MdlLProject from '../MdlLProject/MdlLProject.vue';
import UploadSingleFile from "../../common/uploadSingleFile2"

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "MdlLBaseinfoEdit",
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
      mdlLBaseinfo: {},
    };
  },
  components: {AddUser, MdlLProject, UploadSingleFile},
  methods: {
    reset() {
      this.loading = false;
       this.$refs.userHX.reset();
        this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent_1(fileId, fileUrl) {
      this.mdlLBaseinfo.fileId = fileId;
      this.mdlLBaseinfo.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.mdlLBaseinfo.fileId = "";
      this.mdlLBaseinfo.fileUrl = "";
    },
    setFormValues({ ...mdlLBaseinfo }) {
      let fields = [
        "labCode",
        "labName",
        "deptNew",
        "leader",
        "telphone",
        "linker",
        "linkTel",
        "address",
        "labZz",
       
      ];
      let fieldDates = [];
      Object.keys(mdlLBaseinfo).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (mdlLBaseinfo[key] !== null && mdlLBaseinfo[key] !== "") {
              obj[key] = moment(mdlLBaseinfo[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = mdlLBaseinfo[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.mdlLBaseinfo.id = mdlLBaseinfo.id;
      this.mdlLBaseinfo.baseId = mdlLBaseinfo.baseId
        this.mdlLBaseinfo.fileId = mdlLBaseinfo.fileId;
      this.mdlLBaseinfo.fileUrl = mdlLBaseinfo.fileUrl;
      let that= this
   if(mdlLBaseinfo.fileId!==null && mdlLBaseinfo.fileId!==''){
      setTimeout(()=>{
        that.$refs.fileagent.setForm(mdlLBaseinfo.fileId);
      },100)
   }
      setTimeout(()=>{
      that.$refs.mdllp.fetch()
        that.$refs.userHX.fetch()
          },100)
     
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let mdlLBaseinfo = this.form.getFieldsValue();
          mdlLBaseinfo.id = this.mdlLBaseinfo.id;
             mdlLBaseinfo.fileId = this.mdlLBaseinfo.fileId;
          mdlLBaseinfo.fileUrl = this.mdlLBaseinfo.fileUrl;
           let jsond1= this.$refs.userHX.getAll()
          this.$put("mdlLBaseinfo", {
            ...mdlLBaseinfo,
            user: jsond1,
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

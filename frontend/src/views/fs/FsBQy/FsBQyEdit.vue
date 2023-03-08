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
         :disabled="type==0"
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
      <a-form-item v-bind="formItemLayout" label="计量牌编号">
        <a-input
        :disabled="type==0"
          placeholder="请输入编号"
          v-decorator="[
            'number',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="年度">
        <a-input
        :disabled="type==0"
          placeholder="请输入年度"
          v-decorator="[
            'year',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="季度">
        <a-input
        :disabled="type==0"
          placeholder="请输入季度"
          v-decorator="[
            'quarter',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="性别">
        <a-input
        :disabled="type==0"
          placeholder="请输入性别"
          v-decorator="[
            'sexName',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="职业类别">
        <a-input
        :disabled="type==0"
          placeholder="请输入职业类别"
          v-decorator="[
            'jobType',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="剂量计佩戴起始日期">
        <a-date-picker
        :disabled="type==0"
          v-decorator="[
            'startDate',
            {
              rules: [
              
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="佩戴天数">
        <a-input
        :disabled="type==0"
          placeholder="请输入佩戴天数"
          v-decorator="[
            'days',
          
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="铅衣外Hp(10)">
        <a-input
        :disabled="type==0"
          placeholder="请输入铅衣外Hp(10)"
          v-decorator="[
            'qyOut',
          
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="铅衣内Hp(10)">
        <a-input
        :disabled="type==0"
          placeholder="请输入铅衣内Hp(10)"
          v-decorator="[
            'qyIn',
          
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="未穿铅衣Hp(10)">
        <a-input
        :disabled="type==0"
          placeholder="请输入未穿铅衣Hp(10)"
          v-decorator="[
            'qyNo',
            
          ]"
        />
      </a-form-item>
       <a-form-item v-bind="formItemLayout" label="是否大剂量">
        <a-select
        :disabled="type==0"
          placeholder="请输入是否大剂量"
          v-decorator="[
            'isBig',
           
          ]"
        >
        <a-select-option value="是">
          是
        </a-select-option>
         <a-select-option value="否">
          否
        </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否有甲状腺屏蔽">
        <a-select
         :disabled="type==1"
          placeholder="请输入是否有甲状腺屏蔽"
          v-decorator="[
            'isPb',
            {
              rules: [
                { required: type==0, message: '是否有甲状腺屏蔽不能为空' },
              ],
            },
          ]"
        >
          <a-select-option value="是">
          是
        </a-select-option>
         <a-select-option value="否">
          否
        </a-select-option>
        </a-select>
      </a-form-item>
     
        <a-form-item v-bind="formItemLayout" label="附件">
        <upload-single-file
        :disabled="type==0"
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
  name: "FsBQyEdit",
  props: {
    editVisiable: {
      default: false,
    },
    type: {
      default: 0
    }
  },
  components: {UploadSingleFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      fsBQy: {},
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
      this.fsBQy.fileId = fileId;
      this.fsBQy.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.fsBQy.fileId = "";
      this.fsBQy.fileUrl = "";
    },
     handleSearch(value) {
      this.fetchUser(value);
    },
    fetchUser(userAccount) {
      this.$get("sdlBUser/search", {
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
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues({ ...fsBQy }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "number",
        "year",
        "quarter",
        "sexName",
        "jobType",
        "startDate",
        "days",
        "qyOut",
        "qyIn",
        "qyNo",
        "isBig",
        "isPb",
     
      ];
      let fieldDates = ["startDate"];
      Object.keys(fsBQy).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (fsBQy[key] !== null && fsBQy[key] !== ""&& fsBQy[key] !== 0) {
              obj[key] = moment(fsBQy[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = fsBQy[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.fsBQy.id = fsBQy.id;
        this.fsBQy.fileId = fsBQy.fileId;
      this.fsBQy.fileUrl = fsBQy.fileUrl;
   
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(fsBQy.fileId);
      },100)
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let fsBQy = this.form.getFieldsValue();
          fsBQy.id = this.fsBQy.id;
           fsBQy.fileId = this.fsBQy.fileId;
          fsBQy.fileUrl = this.fsBQy.fileUrl;
          this.$put("fsBQy", {
            ...fsBQy,
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

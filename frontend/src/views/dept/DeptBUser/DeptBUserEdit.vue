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

     <a-form :form="form" layout="vertical">
       <a-row>
      <a-col :span="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="姓名">
          <a-input
           :disabled="true"
            placeholder="请输入姓名"
            v-decorator="[
              'userAccountName',
              { rules: [{ required: true, message: '姓名不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="人事编号">
          <a-input
           :disabled="true"
            placeholder="请输入人事编号"
            v-decorator="[
              'userAccount',
              { rules: [{ required: true, message: '人事编号不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="5" :offset="1">
        
          <a-form-item label="院区">
            <a-select
              placeholder="请输入院区"
              v-decorator="[
                'yuanqu',
              ]"
            >
            <a-select-option value="本院">本院</a-select-option>
            <a-select-option value="西院">西院</a-select-option>
            <a-select-option value="肿瘤">肿瘤</a-select-option>
            <a-select-option value="金银湖">金银湖</a-select-option>
            <a-select-option value="PET中心">PET中心</a-select-option>
            </a-select>
          </a-form-item>
      </a-col>
     <a-col :span="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="开始从事本科室工作时间">
          <a-date-picker
            v-decorator="[
              'fsDate',
              {
                rules: [
                  { required: true, message: '开始从事本科室工作时间不能为空' },
                ],
              },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="科室名称">
          <a-input
           :disabled="true"
            placeholder="请输入科室名称"
            v-decorator="[
              'deptName',
              { rules: [{ required: true, message: '科室名称不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="科室ID">
          <a-input
          :disabled="true"
            placeholder="请输入科室ID"
            v-decorator="[
              'deptId',
              { rules: [{ required: true, message: '科室ID不能为空' }] },
            ]"
          />
        </a-form-item>
      </a-col>
     
     <a-col :span="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="备注">
          <a-input
            placeholder="请输入备注"
            v-decorator="[
              'note',
             
            ]"
          />
        </a-form-item>
      </a-col>
       </a-row>
    </a-form>

     <dept-b-book :viewVisiable="viewVisiable"  :userAccount="deptBUser.userAccount" :userAccountName="deptBUser.userAccountName">
    </dept-b-book>
    <dept-b-book-d :viewVisiable="viewVisiable"  :userAccount="deptBUser.userAccount" :userAccountName="deptBUser.userAccountName">
    </dept-b-book-d>

    <!--<dept-b-book-d :viewVisiable="viewVisiable"  :userAccount="deptBUser.userAccount" :userAccountName="deptBUser.userAccountName">
    </dept-b-book-d> -->
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
import DeptBBook from '../DeptBBook/DeptBBook.vue';
import DeptBBookD from '../DeptBBookD/DeptBBookD.vue';

const formItemLayout = {
  
};
export default {
  components: { DeptBBook, DeptBBookD },
  name: "DeptBUserEdit",
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
      deptBUser: {},
    };
  },
  computed: {
     viewVisiable: {
      get: function () {
        return this.editVisiable
      },
      set: function () {
      }
    },
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
     
    setFormValues({ ...deptBUser }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "yuanqu",
       
        "fsDate",
        "deptName",
        "deptId",
       
        "note",
      ];
      let fieldDates = ["fsDate"];
      Object.keys(deptBUser).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (deptBUser[key] !== null && deptBUser[key] !== "") {
              obj[key] = moment(deptBUser[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = deptBUser[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.deptBUser.id = deptBUser.id;
      this.deptBUser.userAccount = deptBUser.userAccount;
      this.deptBUser.userAccountName = deptBUser.userAccountName;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let deptBUser = this.form.getFieldsValue();
          deptBUser.id = this.deptBUser.id;
          this.$put("deptBUser", {
            ...deptBUser,
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

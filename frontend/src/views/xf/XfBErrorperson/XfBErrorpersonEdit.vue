<template>
  <a-drawer
    title="新增"
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
      
      <a-form-item v-bind="formItemLayout" label="年度">
        <a-input
          placeholder="请输入年度"
          v-decorator="[
            'year',
            { rules: [{ required: true, message: '年度不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="是否通过">
        <a-select
          placeholder="请输入是否通过"
          v-decorator="[
            'isPass',
            { rules: [{ required: true, message: '是否通过不能为空' }] },
          ]"
        >
        <a-select-option value="是">是
        </a-select-option>
         <a-select-option value="否">否
        </a-select-option>
        </a-select>
      </a-form-item>
     
       <a-form-item v-bind="formItemLayout" label="授权文件">
          <a-select
            v-decorator="[
              'archiveId',
              { rules: [{ required: true, message: '授权文件不能为空' }] },
            ]"
            option-filter-prop="children"
            :filter-option="filterOption"
            show-search
            @change="fileChange"
          >
            <a-select-option v-for="d in fileData" :key="d" :value="`${d.id}`">
              {{ d.fileName + " " + d.fileCode }}
            </a-select-option>
          </a-select>
          
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
import moment from 'moment'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBErrorpersonAdd",
  props: {
    editVisiable: {
      default: false,
    },
  },
   watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchFile();
      }
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      xfBErrorperson: {},
       userData: [],
      fileData: [],
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.xfBErrorperson = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
     fetchFile() {
      this.$get("xfBArchive", { pageNum:1 ,pageSize: 10000, fileType: '自纠情况库' }).then((res) => {
        this.fileData = [];
        this.fileData.push(...res.data.rows);
      });
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
      this.xfBErrorperson["userAccountName"] = option.key.userAccountName;
      this.xfBErrorperson["userAccount"] = option.key.userAccount;
      this.xfBErrorperson["deptNew"] = option.key.deptNew;
      this.xfBErrorperson["zhicheng"] = option.key.zhicheng;
    },
    fileChange(value, option) {
      this.xfBErrorperson["archiveName"] = option.key.fileName;
      this.xfBErrorperson["archiveCode"] = option.key.fileCode;
      this.xfBErrorperson["fileId"] = option.key.fileId;
      this.xfBErrorperson["fileUrl"] = option.key.fileUrl;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$put("xfBErrorperson", {
            ...this.xfBErrorperson,
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
    setFormValues({ ...xfBErrorperson }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "deptNew",
        "year",
        "isPass",
        "fileId",
        "fileUrl",
        "archiveId",
        "archiveName",
        "archiveCode",
        "remark",
      ];
      let fieldDates = [];
      Object.keys(xfBErrorperson).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBErrorperson[key] !== null && xfBErrorperson[key] !== "") {
              obj[key] = moment(xfBErrorperson[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
           if (key == "archiveId") {
              if( xfBErrorperson[key]==null){
                obj[key] = "";
              }
              else {
              obj[key] = xfBErrorperson[key].toString();
              }

            } else {
              obj[key] = xfBErrorperson[key];
            }
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBErrorperson.id = xfBErrorperson.id;
       this.fetchUser(xfBErrorperson.userAccount)
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "userAccountName",
        "userAccount",
        "deptNew",
        "year",
        "isPass",
        "fileId",
        "fileUrl",
        "archiveId",
        "archiveName",
        "archiveCode",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBErrorperson[_key] = values[_key];
        });
      }
    },
  },
};
</script>

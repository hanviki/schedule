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
      <a-form-item v-bind="formItemLayout" label="行风办审核结果">
        <a-input
          placeholder="请输入行风办审核结果"
          v-decorator="[
            'exiamResult',
            { rules: [{ required: true, message: '行风办审核结果不能为空' }] },
          ]"
        />
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
     
      <a-form-item v-bind="formItemLayout" label="日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'powerDate',
            { rules: [{ required: true, message: '日期不能为空' }] },
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
      <a-form-item v-bind="formItemLayout" label="操作人">
        <a-input
          placeholder="请输入操作人"
          v-decorator="[
            'operationName',
            { rules: [{ required: true, message: '操作人不能为空' }] },
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

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBAssetEdit",
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
      xfBAsset: {},
       userData: [],
      fileData: [],
    };
  },
   watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchFile();
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
     filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
     fetchFile() {
      this.$get("xfBArchive", { pageNum:1 ,pageSize: 10000, fileType: '评优评先' }).then((res) => {
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
      this.xfBAsset["userAccountName"] = option.key.userAccountName;
      this.xfBAsset["userAccount"] = option.key.userAccount;
      this.xfBAsset["deptNew"] = option.key.deptNew;
      this.xfBAsset["zhicheng"] = option.key.zhicheng;
    },
    fileChange(value, option) {
      this.xfBAsset["archiveName"] = option.key.fileName;
      this.xfBAsset["archiveCode"] = option.key.fileCode;
      this.xfBAsset["fileId"] = option.key.fileId;
      this.xfBAsset["fileUrl"] = option.key.fileUrl;
    },
    setFormValues({ ...xfBAsset }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "deptNew",
        "exiamResult",
        "archiveId",
        "archiveName",
        "archiveCode",
        "fileId",
        "fileUrl",
        "powerDate",
        "remark",
        "operationName",
        "auditMan",
      ];
      let fieldDates = ["powerDate"];
      Object.keys(xfBAsset).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBAsset[key] !== null && xfBAsset[key] !== "") {
              obj[key] = moment(xfBAsset[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
             if (key == "archiveId") {
              if( xfBAsset[key]==null){
                obj[key] = "";
              }
              else {
              obj[key] = xfBAsset[key].toString();
              }

            } else {
              obj[key] = xfBAsset[key];
            }
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBAsset.id = xfBAsset.id;
      this.fetchUser(xfBAsset.userAccount)
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "exiamResult",
        "archiveId",
        "powerDate",
        "remark",
        "operationName",
        "auditMan",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBAsset[_key] = values[_key];
        });
      }
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$put("xfBAsset", {
            ...this.xfBAsset,
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

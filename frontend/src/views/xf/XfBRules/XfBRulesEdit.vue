<template>
  <a-drawer
    title="编辑"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
    
      <a-form-item v-bind="formItemLayout" label="科室">
          <a-select
                  v-decorator="[
            'deptNew',
            { rules: [{ required: true, message: '科室不能为空' }] },
          ]"
                  option-filter-prop="children"
                  :filter-option="filterOption"
                  show-search
                >
                  <a-select-option
                    v-for="d in deptData"
                    :key="d.deptId"
                    :value="`${d.deptId}`"
                  >
                    {{ d.deptName }}
                  </a-select-option>
                </a-select>
      </a-form-item>
        <a-form-item v-bind="formItemLayout" label="日期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'reordDate',
            { rules: [{ required: true, message: '日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="类型">
        <a-select
          placeholder="请输入类型"
          v-decorator="[
            'type',
            { rules: [{ required: true, message: '类型不能为空' }] },
          ]"
        >
        <a-select-option value="药品">药品</a-select-option>
        <a-select-option value="耗材">耗材</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="内容">
        <a-input
          placeholder="请输入内容"
          v-decorator="[
            'content',
            { rules: [{ required: true, message: '内容不能为空' }] },
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
import UploadSingleFile from "../../common/uploadSingleFile"
import moment from 'moment'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "XfBRulesAdd",
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
      xfBRules: {},
      deptData: []
    };
  },
    watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchDept();
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.xfBRules = {};
      this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
     uploadAgent_1(fileId, fileUrl) {
      this.xfBRules.fileId = fileId;
      this.xfBRules.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.xfBRules.fileId = "";
      this.xfBRules.fileUrl = "";
    },
      filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
                 fetchDept() {
      this.$get("sdlBUser/deptNew", { }).then((res) => {
        this.deptData = [];
        // this.deptData.push({
        //   deptId: "-1",
        //   deptName: "全部",
        // });
        console.info(res)
        if(res.data[0]!=null){
         this.deptData.push(...res.data);
        }
      });
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$put("xfBRules", {
            ...this.xfBRules,
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
        setFormValues({ ...xfBRules }) {
      let fields = [
        "fileId",
        "fileUrl",
        "deptNew",
        "type",
        "content",
        "reordDate",
        "remark",
      ];
      let fieldDates = ["reordDate"];
      Object.keys(xfBRules).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (xfBRules[key] !== null && xfBRules[key] !== "") {
              obj[key] = moment(xfBRules[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = xfBRules[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.xfBRules.id = xfBRules.id;
       let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(xfBRules.fileId);
      },100)
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "deptNew",
        "type",
        "content",
        "reordDate",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.xfBRules[_key] = values[_key];
        });
      }
    },
      
  },
};
</script>

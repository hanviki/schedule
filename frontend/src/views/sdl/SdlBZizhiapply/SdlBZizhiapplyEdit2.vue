<template>
  <a-drawer
    title="资质申请审核"
    :maskClosable="false"
    width="90%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <!-- <a-form layout="horizontal">
      <div>
        <a-row>
          <a-col :sm="24">
            <a-form-item
              label="发薪号\姓名"
              v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 20 } }"
            >
              <a-select
                show-search
                :value="value"
                placeholder="输入发薪号\姓名"
                style="width: 100%"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleSearch"
                @change="handleChange"
              >
                <a-select-option v-for="d in userData" :key="d.userAccount">
                  {{ d.userAccount + "    "+d.userAccountName +"    "+d.sexName+ "    "+d.birthday }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </div>
    </a-form> -->
    <a-form :form="form">
      <a-row>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="人事编号">
            <a-input
              :disabled="true"
              v-decorator="[
                'userAccount',
                { rules: [{ required: true, message: '人事编号不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="姓名">
            <a-input :disabled="true" v-decorator="['userAccountName']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="科室">
            <a-input :disabled="true" v-decorator="['deptName']" />
            <a-input type="hidden" v-decorator="['deptId']" />
          </a-form-item>
        </a-col>

        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="出生日期">
            <a-input :disabled="true" v-decorator="['birthday']" />
          </a-form-item>
        </a-col>
        <a-col :sm="16">
          <a-form-item
            v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 20 } }"
            label="病区"
          >
            <a-input :disabled="true" v-decorator="['bqName']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="性别">
            <a-input :disabled="true" v-decorator="['sexName']" />
          </a-form-item>
        </a-col>

        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="人员类别">
            <a-input :disabled="true" v-decorator="['rylx']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="员工组">
            <a-input :disabled="true" v-decorator="['yuangongzu']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="人事子范围">
            <a-input :disabled="true" v-decorator="['renshizifw']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="职称">
            <a-input :disabled="true" v-decorator="['zhicheng']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="临床职称">
            <a-input :disabled="true" v-decorator="['zyjsgwLc']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="临床聘任时间">
            <a-input :disabled="true" v-decorator="['appointedDateLc']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="教学职称">
            <a-input :disabled="true" v-decorator="['zyjsgw']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="教学聘任时间">
            <a-input :disabled="true" v-decorator="['appointedDate']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="内聘临床职务">
            <a-input :disabled="true" v-decorator="['zyjsNp']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="内聘临床时间">
            <a-input :disabled="true" v-decorator="['zyjsDateNp']" />
          </a-form-item>
        </a-col>

        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="内聘教学职务">
            <a-input :disabled="true" v-decorator="['zyjsNpjx']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="内聘教学时间">
            <a-input :disabled="true" v-decorator="['zyjsDateNpjx']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="学历">
            <a-input :disabled="true" v-decorator="['edu']" />
          </a-form-item>
        </a-col>

        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="来校工作时间">
            <a-input :disabled="true" v-decorator="['schoolDate']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="医师类别">
            <a-input :disabled="true" v-decorator="['yishiLb']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="医师级别">
            <a-input :disabled="true" v-decorator="['yishiJb']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="医师执业范围">
            <a-input :disabled="true" v-decorator="['yishiZhiyefanwei']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="医师资格证号">
            <a-input :disabled="true" v-decorator="['yishiZgzsbianhao']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="医师执业证号">
            <a-input :disabled="true" v-decorator="['yishiZiyebianhao']" />
          </a-form-item>
        </a-col>

        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="变更类型">
                <a-input :disabled="true" v-decorator="['applyType']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="原资质">
            <a-input :disabled="true" v-decorator="['userTypeName']" />
            <a-input type="hidden" v-decorator="['userType']" />
          </a-form-item>
        </a-col>
        <a-col :sm="6">
          <a-form-item v-bind="formItemLayout" label="新资质">
                <a-input :disabled="true" v-decorator="['userNewTypeName']" />
          </a-form-item>
        </a-col>
        <a-col :sm="24">
          <a-form-item
            v-bind="{ labelCol: { span: 2 }, wrapperCol: { span: 22 } }"
            label="申请理由"
          >
            <a-textarea
              :disabled="true"
              v-decorator="['note']"
              placeholder="请填写申请理由"
              :auto-size="{ minRows: 5 }"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="24">
          <a-form-item
            v-bind="{ labelCol: { span: 2 }, wrapperCol: { span: 22 } }"
            label="审核意见"
          >
            <a-textarea
              v-decorator="[
                'auditSuggestion',
                { rules: [{ }] },
              ]"
              placeholder="请填写审核意见"
              :auto-size="{ minRows: 5,maxRows: 8 }"
            />
          </a-form-item>
        </a-col>
      </a-row>
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
        >审核通过</a-button
      >
      <a-button @click="handleNoSubmit" type="danger" :loading="loading"
        >审核不通过</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  name: "SdlBZizhiapplyEdit",
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
      sdlBZizhiapply: {},
      value: "",
      data: [],
      userData: [],
      renshizifw: ''
    };
  },
  watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.$get("sdlDZizhi").then((res) => {
          console.info(res.data.rows);
           if(this.renshizifw=='医师'){
            this.data =res.data.rows.filter(p=> [1,2,3,4,5].indexOf(p.id)>=0);
          }
          else if(this.renshizifw=='技术'){
            this.data = res.data.rows.filter(p=> [6,7,8].indexOf(p.id)>=0);
          }
          else {
            this.data = res.data.rows
          }
        });
      }
    },
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
      this.value = "";
      this.data = [];
      this.userData = [];
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },

    search(value) {
      if (value != "") {
        this.$get("sdlBUser/dept", {
          userAccount: value,
        }).then((r) => {
          this.userData = r.data.rows;
        });
      }
    },
    setFormValues({ ...sdlBZizhiapply }) {
      let fields = [
        "userAccount",
        "userAccountName",
        "bqName",
        "userType",
        "userTypeName",
        "deptId",
        "telephone",
        "deptName",
        "positionName",
        "zygwDate",
        "rylx",
        "yggh",
        "ghHk",
        "sexName",
        "qualifyNo",
        "birthday",
        "schoolDate",
        "zyjsgw",
        "bianhaoJx",
        "zyjsgwLc",
        "xcszyjzc",
        "appointedDate",
        "appointedDateLc",
        "zizu",
        "yuangongzu",
        "xrgwjb",
        "xrgwjbprsj",
        "zhicheng",
        "zyjsNp",
        "zyjsDateNp",
        "zyjsNpjx",
        "zyjsDateNpjx",
        "edu",
        "eduSchool",
        "guoji",
        "minzu",
        "jiguan",
        "politicalStatus",
        "bianhaoLc",
        "xingzhengjiebie",
        "idCard",
        "workDate",
        "yishiLb",
        "yishiJb",
        "yishiZhiyefanwei",
        "yishiZgzsbianhao",
        "yishiZiyebianhao",
        "renshizifw",
        "renshizfenlei",
        "userNewType",
        "userNewTypeName",
        "note",
        "applyType",
        "auditSuggestion",
      ];
      let fieldDates = ["applyDate"];
      Object.keys(sdlBZizhiapply).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlBZizhiapply[key] !== "") {
              obj[key] = moment(sdlBZizhiapply[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = sdlBZizhiapply[key];
          }
          this.sdlBZizhiapply[key] = obj[key];
          this.form.setFieldsValue(obj);
        }
      });
      this.renshizifw =sdlBZizhiapply.renshizifw
      this.sdlBZizhiapply.id = sdlBZizhiapply.id;
    },
    setFormValues2({ ...sdlBUser }) {
      let fields = [
        "userAccount",
        "userAccountName",
        "bqName",
        "userType",
        "userTypeName",
        "deptId",
        "telephone",
        "deptName",
        "positionName",
        "zygwDate",
        "rylx",
        "yggh",
        "ghHk",
        "sexName",
        "qualifyNo",
        "birthday",
        "schoolDate",
        "zyjsgw",
        "bianhaoJx",
        "zyjsgwLc",
        "xcszyjzc",
        "appointedDate",
        "appointedDateLc",
        "zizu",
        "yuangongzu",
        "xrgwjb",
        "xrgwjbprsj",
        "zhicheng",
        "zyjsNp",
        "zyjsDateNp",
        "zyjsNpjx",
        "zyjsDateNpjx",
        "edu",
        "eduSchool",
        "guoji",
        "minzu",
        "jiguan",
        "politicalStatus",
        "bianhaoLc",
        "xingzhengjiebie",
        "idCard",
        "workDate",
        "yishiLb",
        "yishiJb",
        "yishiZhiyefanwei",
        "yishiZgzsbianhao",
        "yishiZiyebianhao",
        "renshizifw",
        "renshizfenlei",
        "auditSuggestion",
      ];
      let fieldDates = [];
      Object.keys(sdlBUser).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (sdlBUser[key] !== "") {
              obj[key] = moment(sdlBUser[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = sdlBUser[key];
          }
          this.sdlBZizhiapply[key] = obj[key];
          this.form.setFieldsValue(obj);
        }
      });
    },
    handleSubmit() {
      let that = this;

      that.form.validateFields((err, values) => {
        if (!err) {
          that.$confirm({
            title: "确定审核通过此记录?",
            content: "当您点击确定按钮后，这些记录将会被审核",
            centered: true,
            onOk() {
              let sdlBZizhiapply = that.form.getFieldsValue();
            
              if(sdlBZizhiapply.auditSuggestion==null||sdlBZizhiapply.auditSuggestion==''){
                sdlBZizhiapply.auditSuggestion ='同意'
              }
              sdlBZizhiapply.id = that.sdlBZizhiapply.id;
              sdlBZizhiapply.state = 3;
              that
                .$put("sdlBZizhiapply", {
                  ...sdlBZizhiapply,
                })
                .then(() => {
                  that.reset();
                  that.$emit("success");
                })
                .catch(() => {
                  that.loading = false;
                });
            },
            onCancel() {
              that.selectedRowKeys = [];
            },
          });
        }
      });
    },
    handleNoSubmit() {
      let that = this;

      that.form.validateFields((err, values) => {
        if (!err) {
          that.$confirm({
            title: "确定审核不通过此记录?",
            content: "当您点击确定按钮后，这些记录将会被审核不通过",
            centered: true,
            onOk() {
              let sdlBZizhiapply = that.form.getFieldsValue();
              sdlBZizhiapply.id = that.sdlBZizhiapply.id;
              sdlBZizhiapply.state = 2;
              that
                .$put("sdlBZizhiapply", {
                  ...sdlBZizhiapply,
                })
                .then(() => {
                  that.reset();
                  that.$emit("success");
                })
                .catch(() => {
                  that.loading = false;
                });
            },
            onCancel() {
              that.selectedRowKeys = [];
            },
          });
        }
      });
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "userAccount",
        "userAccountName",
        "bqName",
        "userType",
        "deptId",
        "telephone",
        "deptName",
        "positionName",
        "zygwDate",
        "appointedDate",
        "rylx",
        "sexName",
        "birthday",
        "schoolDate",
        "zyjsgw",
        "zyjsgwLc",
        "xcszyjzc",
        "userTypeName",
        "userNewType",
        "qualifyNo",
        "applyType",
        "note",
        "auditSuggestion",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.sdlBZizhiapply[_key] = values[_key];
        });
      }
    },
    handleSearch(value) {
      this.search(value);
    },
    handleChange(value) {
      this.value = value;
      let data = this.userData.filter((p) => p.userAccount == value);
      this.setFormValues2(data[0]);
    },
    usertypeChange(value) {
      let data = this.data.filter((p) => p.id == value);
      this.sdlBZizhiapply["userNewTypeName"] = data[0].muduleName;
    },
  },
};
</script>
<style lang="less" scoped>
   .ant-input-disabled {
  color: rgba(0, 0, 0, 0.65) !important;
  background-color: #f5f5f5 !important;
}
</style>

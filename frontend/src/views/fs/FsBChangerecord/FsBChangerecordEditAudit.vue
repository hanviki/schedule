<template>
  <a-drawer
    title="审核"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form" :layout="formLayout">
      <a-col :sm="23" :offset="1">
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
              {{ d2.userAccountName + " " + d2.userAccount }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="原科室">
          <a-input
            placeholder="请输入原科室"
            :disabled="true"
            v-decorator="['deptOld']"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="原岗位类别">
          <a-input
            placeholder="请输入原岗位类别"
            :disabled="true"
            v-decorator="['gwlbOld']"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="原核定计量牌个数">
          <a-input
            :disabled="true"
            placeholder="请输入原核定计量牌个数"
            v-decorator="['numberOld', {}]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="原监测状态">
          <a-select
            placeholder="请输入原监测状态"
            :disabled="true"
            v-decorator="['stateOld']"
          >
            <a-select-option value="0">开始监测</a-select-option>
            <a-select-option value="1">暂停监测</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
     
        <!-- <a-input placeholder="请输入新科室" v-decorator="['deptNew']" /> -->
         <a-form-item v-bind="formItemLayout" label="新科室">
                <a-select
                 :disabled="true"
                  v-decorator="['deptNew']"
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
      </a-col>

      <a-col :sm="5" :offset="1">
       <a-form-item label="新岗位类别">
            <a-select
              placeholder="请输入新岗位类别"
              v-decorator="[
                'gwlbNew',
              ]"
            >
         <a-select-option value="诊断放射学">诊断放射学</a-select-option>
         <a-select-option value="核医学">核医学</a-select-option>
         <a-select-option value="放射治疗">放射治疗</a-select-option>
         <a-select-option value="介入放射">介入放射</a-select-option>
            </a-select>
          </a-form-item>
      </a-col>

      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="新核定计量牌个数">
          <a-input-number
            :disabled="true"
            placeholder="请输入新核定计量牌个数"
            v-decorator="['numberNew', {}]"
          />
        </a-form-item>
      </a-col>

      <a-col :sm="5" :offset="1">
        <a-form-item v-bind="formItemLayout" label="新监测状态">
          <a-select
            placeholder="请输入新监测状态"
            :disabled="true"
            v-decorator="['stateNew']"
          >
            <a-select-option value="0">开始监测</a-select-option>
            <a-select-option value="1">暂停监测</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item
          v-bind="formItemLayout"
          :disabled="true"
          label="科室是否变化"
        >
          <a-input
            placeholder="请输入新岗位类别"
            :disabled="true"
            v-decorator="['isDept']"
          />
        </a-form-item>
      </a-col>

      <a-col :sm="5" :offset="1">
        <a-form-item
          v-bind="formItemLayout"
          :disabled="true"
          label="岗位类别是否变化"
        >
          <a-input :disabled="true" v-decorator="['isGwlb']" />
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item
          v-bind="formItemLayout"
          :disabled="true"
          label="计量牌数量是否变化"
        >
          <a-input :disabled="true" v-decorator="['isNumber']" />
        </a-form-item>
      </a-col>
      <a-col :sm="5" :offset="1">
        <a-form-item
          v-bind="formItemLayout"
          :disabled="true"
          label="检测状态是否变化"
        >
          <a-input :disabled="true" v-decorator="['isState']" />
        </a-form-item>
      </a-col>
      <a-col :sm="23" :offset="1">
        <a-form-item
          v-bind="formItemLayout"
          v-show="isShow"
          label="状态变更日期"
        >
          <a-date-picker
            format="YYYY-MM-DD"
            v-decorator="[
              'changeDate',
              {
                rules: [
                  {
                    validator: this.handleValidator,
                  },
                ],
              },
            ]"
          />
        </a-form-item>
      </a-col>
      <a-col :sm="23" :offset="1">
        <a-form-item v-bind="formItemLayout" v-show="isShow" label="状态变更备注">
          <a-input placeholder="请输入状态变更备注" v-decorator="['remark']" />
        </a-form-item>
      </a-col>
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
      <a-button @click="handleSubmit(3)" type="primary" :loading="loading"
        >审核通过</a-button
      >
      <a-button @click="handleSubmit(2)" type="primary" :loading="loading"
        >审核不通过</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
const formItemLayout = {};
import moment from "moment";
export default {
  name: "FsBChangerecordAdd",
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
      fsBChangerecord: {},
      formLayout: "vertical",
      userData: [],
       deptData: [],
      isShow: false,
    };
  },
   watch: {
    editVisiable(){
      if(this.editVisiable){
        this.fetchDept()
      }
   }
  },
  methods: {
    moment,
    reset() {
      this.loading = false;
      this.fsBChangerecord = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleValidator(rule, val, callback) {
      let validateResult = false; // 自定义规则
      let isState = this.form.getFieldValue("isState");
      let changeDate = this.form.getFieldValue("changeDate");
      console.info(changeDate);
      console.info(isState);

      if (isState == "是" && changeDate == undefined) {
        callback("请输入状态变更日期");
      }
      callback();
    },
    
    handleSearch(value) {
      this.fetchUser(value);
    },
     fetchDept() {
      this.$get("sdlBUser/deptNewOwn", {  }).then((res) => {
        this.deptData = [];
        
       if(res.data[0]!=null){
         this.deptData.push(...res.data);
        }
      });
    },
     filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    fetchUser(userAccount) {
      this.$get("fsBUser/search", {
        userAccount,
      }).then((r) => {
        console.log(r);
        this.userData = r.data;
      });
    },
    userChange(value, option) {
      console.info(option);
      this.fsBChangerecord["userAccountName"] = option.key.userAccountName;
      this.fsBChangerecord["userAccount"] = option.key.userAccount;

      this.form.getFieldDecorator("deptOld");
      this.form.setFieldsValue({ deptOld: option.key.deptName });

      this.form.getFieldDecorator("deptNew");
      this.form.setFieldsValue({ deptNew: option.key.deptName });

      this.form.getFieldDecorator("gwlbOld");
      this.form.setFieldsValue({ gwlbOld: option.key.gwlb });
      this.form.getFieldDecorator("gwlbNew");
      this.form.setFieldsValue({ gwlbNew: option.key.gwlb });

      this.form.getFieldDecorator("numberOld");
      this.form.setFieldsValue({ numberOld: option.key.hdjlpgs });
      this.form.getFieldDecorator("numberNew");
      this.form.setFieldsValue({ numberNew: option.key.hdjlpgs });
      this.form.getFieldDecorator("stateOld");
      this.form.setFieldsValue({ stateOld: option.key.jcState });

      this.form.getFieldDecorator("stateNew");
      this.form.setFieldsValue({ stateNew: option.key.jcState });
    },
    handleSubmit(state) {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.fsBChangerecord.state = state;
          this.$put("fsBChangerecord", {
            ...this.fsBChangerecord,
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
    setFormValues({ ...fsBChangerecord }) {
      let fields = [
        // "userAccountName",
        "userAccount",
        "deptNew",
        "deptOld",
        "gwlbNew",
        "gwlbOld",
        "numberNew",
        "numberOld",
        "stateNew",
        "stateOld",
        "changeDate",
        "isDept",
        "isGwlb",
        "isNumber",
        "isState",
        "remark",
      ];
      let fieldDates = ["changeDate"];
      Object.keys(fsBChangerecord).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (fsBChangerecord[key] !== null && fsBChangerecord[key] !== "") {
              obj[key] = moment(fsBChangerecord[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = fsBChangerecord[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.fsBChangerecord.id = fsBChangerecord.id;
      this.fsBChangerecord.userAccountName = fsBChangerecord.userAccountName;
      this.fsBChangerecord.userAccount = fsBChangerecord.userAccount;
      this.isShow = fsBChangerecord.isState == "是";
      this.fetchUser(fsBChangerecord.userAccount);
    },
    setFields() {
      let values = this.form.getFieldsValue([
        //  "userAccountName",
        //"userAccount",
        "deptNew",
        "deptOld",
        "gwlbNew",
        "gwlbOld",
        "numberNew",
        "numberOld",
        "stateNew",
        "stateOld",
        "changeDate",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.fsBChangerecord[_key] = values[_key];
        });
      }
    },
  },
};
</script>
<style lang="less" scoped >
.ant-input-disabled {
  background-color: rgba(0, 0, 0, 0.15) !important;
}
</style>

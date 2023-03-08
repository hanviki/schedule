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
      <a-form-item v-bind="formItemLayout" label="是否复检">
        <a-select
          placeholder="请输入是否复检"
          v-decorator="[
            'isFj',
            
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
      <a-form-item v-bind="formItemLayout" label="体检号">
        <a-input
          placeholder="请输入体检号"
          v-decorator="[
            'tjCode',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="工号">
        <a-input
        disabled
          placeholder="请输入工号"
          v-decorator="[
            'pernr',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="性别">
       <a-input
       disabled
          placeholder="请输入性别"
          v-decorator="[
            'sexy',
            
          ]"
        />
        
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="年龄">
        <a-input
        disabled
          placeholder="请输入年龄"
          v-decorator="[
            'age',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="出生日期">
        <a-input
          disabled
          placeholder="请输入出生日期"
          v-decorator="[
            'birthday',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="婚姻状况">
        <a-select
          placeholder="请输入婚姻状况"
          v-decorator="[
            'married',
            
          ]"
        >
        <a-select-option value="未婚">
          未婚
        </a-select-option>
         <a-select-option value="已婚">
          已婚
        </a-select-option>
        <a-select-option value="离异">
          离异
        </a-select-option>
         </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="单位">
        <a-input
          placeholder="请输入单位"
          v-decorator="[
            'deptUnit',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="身份证">
        <a-input
          placeholder="请输入身份证"
          v-decorator="[
            'idCard',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="危害因素">
        <a-input
          placeholder="请输入危害因素"
          v-decorator="[
            'whys',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="接害工龄(年)">
        <a-input
          placeholder="请输入接害工龄(年)"
          v-decorator="[
            'jhglYear',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="接害工龄(月)">
        <a-input
          placeholder="请输入接害工龄(月)"
          v-decorator="[
            'jhglMonth',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="工种">
        <a-input
          placeholder="请输入工种"
          v-decorator="[
            'gz',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="部门">
        <a-input
          placeholder="请输入部门"
          v-decorator="[
            'deparment',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="体检套餐">
        <a-input
          placeholder="请输入体检套餐"
          v-decorator="[
            'tjtc',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="日期">
        <a-date-picker
          v-decorator="[
            'rq',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="体检类别">
        <a-input
          placeholder="请输入体检类别"
          v-decorator="[
            'tjlb',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="结果">
        <a-input
          placeholder="请输入结果"
          v-decorator="[
            'result',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="结论">
        <a-input
          placeholder="请输入结论"
          v-decorator="[
            'jl',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="处理意见">
        <a-input
          placeholder="请输入处理意见"
          v-decorator="[
            'suggestion',
            
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="体检单位">
        <a-input
          placeholder="请输入体检单位"
          v-decorator="[
            'jcUnit',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="晶状体">
        <a-input
          placeholder="请输入晶状体"
          v-decorator="[
            'jcJzt',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="WBC">
        <a-input
          placeholder="请输入WBC"
          v-decorator="[
            'jcWbc',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="PLT">
        <a-input
          placeholder="请输入PLT"
          v-decorator="[
            'jcPlt',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="倒位率">
        <a-input
          placeholder="请输入倒位率"
          v-decorator="[
            'jcDwl',
         
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="相互易位率">
        <a-input
          placeholder="请输入相互易位率"
          v-decorator="[
            'jcXhywl',
          
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="双着丝粒染色体率">
        <a-input
          placeholder="请输入双着丝粒染色体率"
          v-decorator="[
            'jcSzssrstl',
            {
           
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="着丝粒环率">
        <a-input
          placeholder="请输入着丝粒环率"
          v-decorator="[
            'jcZssl',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="染色体畸变细胞率">
        <a-input
          placeholder="请输入染色体畸变细胞率"
          v-decorator="[
            'jcRstqbxbl',
            {
             
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="染色体畸变率">
        <a-input
          placeholder="请输入染色体畸变率"
          v-decorator="[
            'jcRstjbl',
           
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="淋巴细胞微核细胞率">
        <a-input
          placeholder="请输入淋巴细胞微核细胞率"
          v-decorator="[
            'jcLbxbwhxpl',
            {
              rules: [
              
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="淋巴细胞微核率">
        <a-input
          placeholder="请输入淋巴细胞微核率"
          v-decorator="[
            'jcLbxbwhl',
           
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
  name: "FsBHealthEdit",
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
      fsBHealth: {},
      userData: []
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
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
      this.form.getFieldDecorator("age");
      this.form.setFieldsValue({ age: option.key.age });
      this.form.getFieldDecorator("birthday");
      this.form.setFieldsValue({ birthday: option.key.birthday });
       this.form.getFieldDecorator("pernr");
      this.form.setFieldsValue({ pernr: option.key.yggh });
       this.form.getFieldDecorator("sexy");
      this.form.setFieldsValue({ sexy: option.key.sexname });
      //  this.fsBHealth["age"] = option.key.age;
      // this.fsBHealth["birthday"] = option.key.birthday;
      // this.fsBHealth["pernr"] = option.key.yggh;
      // this.fsBHealth["sexy"] = option.key.sexName;
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues({ ...fsBHealth }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "isFj",
        "tjCode",
        "pernr",
        "sexy",
        "age",
        "birthday",
        "married",
        "deptUnit",
        "idCard",
        "whys",
        "jhglYear",
        "jhglMonth",
        "gz",
        "deparment",
        "tjtc",
        "rq",
        "tjlb",
        "result",
        "jl",
        "suggestion",
        "jcUnit",
        "jcJzt",
        "jcWbc",
        "jcPlt",
        "jcDwl",
        "jcXhywl",
        "jcSzssrstl",
        "jcZssl",
        "jcRstqbxbl",
        "jcRstjbl",
        "jcLbxbwhxpl",
        "jcLbxbwhl",
      ];
      let fieldDates = ["rq"];
      Object.keys(fsBHealth).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (fsBHealth[key] !== null && fsBHealth[key] !== "") {
              obj[key] = moment(fsBHealth[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = fsBHealth[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.fsBHealth.id = fsBHealth.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let fsBHealth = this.form.getFieldsValue();
          fsBHealth.id = this.fsBHealth.id;
          this.$put("fsBHealth", {
            ...fsBHealth,
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

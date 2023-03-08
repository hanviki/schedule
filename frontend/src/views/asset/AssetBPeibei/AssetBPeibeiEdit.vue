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
      
      <a-form-item v-bind="formItemLayout" label="科室">
        <a-select
                 :allowClear="true"
                  v-decorator="['deptName',{ rules: [{ required: true, message: '科室不能为空' }] }]"
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
      <a-form-item v-bind="formItemLayout" label="设备名称">
        <a-input
          placeholder="请输入设备名称"
          v-decorator="[
            'assetName',
            { rules: [{ required: true, message: '设备名称不能为空' }] },
          ]"
        />
      </a-form-item>
        <a-form-item v-bind="formItemLayout" label="设备类型">
        <a-select
          placeholder="请输入设备类型"
          v-decorator="[
            'type',
            { rules: [{ required: true, message: '设备类型不能为空' }] },
          ]"
        >
        <a-select-option value="辐射巡测仪">辐射巡测仪
        </a-select-option>
         <a-select-option value="其他">其他
        </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="生产厂家">
        <a-input
          placeholder="请输入生产厂家"
          v-decorator="[
            'changjia',
            { rules: [{ required: true, message: '生产厂家不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="设备型号">
        <a-input
          placeholder="请输入设备型号"
          v-decorator="[
            'xinghao',
            { rules: [{ required: true, message: '设备型号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="单位管理编号">
        <a-input
          placeholder="请输入单位管理编号"
          v-decorator="[
            'bianhao',
            { rules: [{ required: true, message: '单位管理编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="开始使用日期">
        <a-date-picker
          v-decorator="[
            'startDate',
            { rules: [{ required: true, message: '开始使用日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="结束使用日期">
        <a-date-picker
          v-decorator="[
            'endDate',
            { rules: [{ required: true, message: '结束使用日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'note',
            
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
  name: "AssetBPeibeiEdit",
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
      assetBPeibei: {},
      deptData: []
    };
  },
  watch: {
    editVisiable(){
      if(this.editVisiable){
        let deptId= this.$store.state.account.user.deptId;
         if(deptId==''||deptId==null){
this.fetchDept('sdlBUser/deptNew')
        }
        else{
          this.fetchDept('sdlBUser/deptNewOwn')
        }
      }
   }
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
    fetchDept(url) {
      this.$get(url, {  }).then((res) => {
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
    setFormValues({ ...assetBPeibei }) {
      let fields = [
        "deptName",
        //"deptId",
        "assetName",
        "changjia",
        "xinghao",
        "bianhao",
        "startDate",
        "endDate",
        "note",
        "type"
      ];
      let fieldDates = ["startDate", "endDate"];
      Object.keys(assetBPeibei).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (assetBPeibei[key] !== null && assetBPeibei[key] !== "") {
              obj[key] = moment(assetBPeibei[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = assetBPeibei[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.assetBPeibei.id = assetBPeibei.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let assetBPeibei = this.form.getFieldsValue();
          assetBPeibei.id = this.assetBPeibei.id;
          this.$put("assetBPeibei", {
            ...assetBPeibei,
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

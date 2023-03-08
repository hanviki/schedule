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
      <a-form-item v-bind="formItemLayout" label="类别">
        <a-select   placeholder="请输入类别"
         @change="onChangeLb"
          v-decorator="[
            'lb',
            { rules: [{ required: true, message: '类别不能为空' }] },
          ]">
          <a-select-option value="个人防护用品">个人防护用品 </a-select-option>
          <a-select-option value="辅助防护用品">辅助防护用品 </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="用品及辅助防护设施">
        <a-select  placeholder="请输入用品及辅助防护设施"
          v-decorator="[
            'ypfzfh',
            {
              rules: [
                { required: true, message: '用品及辅助防护设施不能为空' },
              ],
            },
          ]">
          <a-select-option v-for="al in arr" :key="a1.id" :value="al.name">{{al.name}} </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="数量">
        <a-input-number
          placeholder="请输入数量"
          v-decorator="[
            'shuliang',
            { rules: [{ required: true, message: '数量不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="铅当量">
        <a-input-number
          placeholder="请输入铅当量"
          :precision="2"
          v-decorator="[
            'qiandangliang',
            { rules: [{ required: true, message: '铅当量不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="出场日期">
        <a-date-picker
          v-decorator="[
            'chuchangDate',
            { rules: [{ required: true, message: '出场日期不能为空' }] },
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
  name: "AssetBFanghuEdit",
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
      assetBFanghu: {},
       arr1: [],
      arr2: [],
      arr: []
    };
  },
   watch: {
   'editVisiable': {
      handler (e) {
        if(e){
        this.fetchLb()
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
    },
     onChangeLb(value) {
      if(value=='个人防护用品'){
        this.arr =this.arr1;
      }
      if(value=='辅助防护用品'){
        this.arr =this.arr2;
      }
    },
    fetchLb(){
        this.$get('assetDDictory/dic',{parentId: 'B1'}).then((r) => {
          let arr1 = r.data.rows
        });
         this.$get('assetDDictory/dic',{parentId: 'B2'}).then((r) => {
          let arr2 = r.data.rows
        });
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues({ ...assetBFanghu }) {
      let fields = [
        "lb",
        "ypfzfh",
        "shuliang",
        "qiandangliang",
        "chuchangDate",
        "note",
      ];
      let fieldDates = ["chuchangDate"];
      Object.keys(assetBFanghu).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (assetBFanghu[key] !== null && assetBFanghu[key] !== "") {
              obj[key] = moment(assetBFanghu[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = assetBFanghu[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.assetBFanghu.id = assetBFanghu.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let assetBFanghu = this.form.getFieldsValue();
          assetBFanghu.id = this.assetBFanghu.id;
          this.$put("assetBFanghu", {
            ...assetBFanghu,
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

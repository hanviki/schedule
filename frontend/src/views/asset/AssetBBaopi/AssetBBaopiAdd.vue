<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="卫生/环保">
         <a-select   placeholder="请输入卫生/环保"
         @change="onChangeP"
          v-decorator="[
            'type',
            { rules: [{ required: true, message: '卫生/环保不能为空' }] },
          ]">
          <a-select-option value="卫生资料">卫生资料 </a-select-option>
          <a-select-option value="环保资料">环保资料 </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="类别">
        
         <a-select    placeholder="请输入类别"
          v-decorator="[
            'lb',
            { rules: [{ required: true, message: '类别不能为空' }] },
          ]">
          <a-select-option  v-for="al in arr" :key="al.key" :value="al.key">{{al.value}} </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="编号">
        <a-input
          placeholder="请输入编号"
          v-decorator="[
            'bh',
            { rules: [{ required: true, message: '编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="报批时间">
        <a-date-picker
          v-decorator="[
            'baopiDate',
            { rules: [{ required: true, message: '报批时间不能为空' }] },
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
       <a-form-item v-bind="formItemLayout" label="附件">
         <muti-uploadFile :baseId="baseId2" refTab="assetbaopi" ref="mul" ></muti-uploadFile>
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
import MutiUploadFile from '../../common/MutiUploadFile'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
const arr1= [{key: '预评报告',value: '预评报告'},{key: '预评批复',value: '预评批复'},{key: '控评报告',value: '控评报告'},{key: '控评批复',value: '控评批复'}]
const arr2= [{key: '环评报告',value: '环评报告'},{key: '环评批复',value: '环评批复'},{key: '环评验收报告',value: '环评验收报告'}]

export default {
  name: "AssetBBaopiAdd",
  props: {
    addVisiable: {
      default: false,
    },
    baseId: {
      default: "",
    },
  },
  components: {MutiUploadFile},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      assetBBaopi: {},
      arr: [],
      baseId2: ''
    };
  },
   watch: {
   'addVisiable': {
      handler (e) {
        if(e){
        this.fetchGuid()
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    reset() {
      this.loading = false;
      this.assetBBaopi = {};
      this.arr = []
      this.$refs.mul.reset();
      this.form.resetFields();
    },
    fetchGuid(){
     let that = this
      that.$get('comFile/getUid?time=' + new Date().getTime()).then(res => {
        this.baseId2 = res.data.data
       
      })
    },
    uploadAgent_1(fileId, fileUrl) {
      this.assetBBaopi.fileId = fileId;
      this.assetBBaopi.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.assetBBaopi.fileId = "";
      this.assetBBaopi.fileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    onChangeP(value){
       if(value=="卫生资料"){
         this.arr = arr1
       }
       if(value=="环保资料"){
         this.arr = arr2
       }
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.assetBBaopi.baseId = this.baseId;
          this.assetBBaopi.id = this.baseId2;
          this.$post("assetBBaopi", {
            ...this.assetBBaopi,
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
    setFields() {
      let values = this.form.getFieldsValue([
        
        "type",
        "lb",
        "bh",
        "baopiDate",
        
        "note",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.assetBBaopi[_key] = values[_key];
        });
      }
    },
  },
};
</script>

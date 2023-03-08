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
  <a-card title="设备基本信息" :bordered="true"  class="card-area" head-style="font-weight:bold;color:Brown;">
    <a-form :form="form" layout="vertical">
         <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="设备名称">
        <a-input
          placeholder="请输入设备名称"
          v-decorator="[
            'assetName',
            { rules: [{ required: true, message: '设备名称不能为空' }] },
          ]"
        />
      </a-form-item>
         </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="设备类型（卫生）">
         <a-tree-select
          v-decorator="[
            'assetWeis',
            { rules: [{ required: true, message: '设备类型（卫生）不能为空' }] },
          ]"
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="dicTreeData0"
          >
        </a-tree-select>
        
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="设备类型（环保）">
        <a-tree-select
         v-decorator="[
            'assetHuanb',
            { rules: [{ required: true, message: '设备类型（环保）不能为空' }] },
          ]"
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="dicTreeData1"
          :value="parentId1"
          >
        </a-tree-select>
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="生产厂家">
        <a-input
          placeholder="请输入生产厂家"
          v-decorator="[
            'productName',
            
          ]"
        />
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="设备型号">
        <a-input
          placeholder="请输入设备型号"
          v-decorator="[
            'assetXingh',
           
          ]"
        />
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="设备SN码">
        <a-input
          placeholder="请输入设备SN码"
          v-decorator="[
            'assetSn',
            
          ]"
        />
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="设备院内编码">
        <a-input
          placeholder="请输入设备院内编码"
          v-decorator="[
            'assetYnbm',
           
          ]"
        />
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="安装日期">
        <a-date-picker
          v-decorator="[
            'assetDate',
           
          ]"
        />
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="管理科室ID">
        <a-select
                   v-decorator="[
            'deptName',
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
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="设备位置">
       
         <address-select
          ref="addXh"
                        placeholder="请输入设备位置"
                        :defaultValue="defaultValue"
                        @change="selectArea"
                      />
                    </a-form-item>
      </a-form-item>
      </a-col>
      
       <a-col :sm="5"  :offset="1">
      
      <a-form-item v-bind="formItemLayout" label="辐射安全许可证证书">
        <upload-single-file
            ref="fileagent2"
            @uploadRemove="removeAgent_2"
            @uploadSuc="uploadAgent_2"
          >
          </upload-single-file>
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="辐射安全许可证编号">
        <a-input
          placeholder="请输入辐射安全许可证编号"
          v-decorator="[
            'aqBh',
            {
              rules: [
               
              ],
            },
          ]"
        />
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="放射诊疗许可证编号">
        <a-input
          placeholder="请输入放射诊疗许可证编号"
          v-decorator="[
            'fsBh',
            {
              rules: [
                
              ],
            },
          ]"
        />
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
     
       <a-form-item v-bind="formItemLayout" label="放射诊疗许可证书">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
      </a-form-item>
      </a-col>
       <a-col :sm="5"  :offset="1">
      <a-form-item v-bind="formItemLayout" label="备注">
        <a-input
          placeholder="请输入备注"
          v-decorator="[
            'remark',
           
          ]"
        />
      </a-form-item>
      </a-col>
    </a-form>
    </a-card>

    <asset-b-state :viewVisiable="viewVisiable" :baseId="assetBInfo.id" :isEdit="1" ></asset-b-state>
    <asset-b-check :viewVisiable="viewVisiable" :baseId="assetBInfo.id" :isEdit="1"></asset-b-check>
    <asset-b-baopi :viewVisiable="viewVisiable" :baseId="assetBInfo.id" :isEdit="1"></asset-b-baopi>
    <asset-b-zicha :viewVisiable="viewVisiable" :baseId="assetBInfo.id" :isEdit="1"></asset-b-zicha>
    <asset-b-fanghu :viewVisiable="viewVisiable" :baseId="assetBInfo.id" :isEdit="1"></asset-b-fanghu>
    <asset-b-fsjbxx :viewVisiable="viewVisiable" :baseId="assetBInfo.id" :isEdit="1"></asset-b-fsjbxx>
    <asset-b-shebeifile :viewVisiable="viewVisiable" :baseId="assetBInfo.id" :isEdit="1"></asset-b-shebeifile>
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
import AddressSelect from "../../common/AddressSelect.vue";
import UploadSingleFile from "../../common/uploadSingleFile";
import moment from 'moment';
import AssetBState from '../AssetBState/AssetBState.vue';
import AssetBCheck from '../AssetBCheck/AssetBCheck.vue';
import AssetBBaopi from '../AssetBBaopi/AssetBBaopi.vue';
import AssetBZicha from '../AssetBZicha/AssetBZicha.vue';
import AssetBFanghu from '../AssetBFanghu/AssetBFanghu.vue';
import AssetBFsjbxx from '../AssetBFsjbxx/AssetBFsjbxx.vue';
import AssetBShebeifile from '../AssetBShebeifile/AssetBShebeifile.vue';
const formItemLayout = {
};
export default {
  name: "AssetBInfoAdd",
  props: {
    editVisiable: {
      default: false,
    },
    isEdit: {
      default: 1
    }
  },
  components: { AddressSelect, UploadSingleFile, AssetBState, AssetBCheck, AssetBBaopi, AssetBZicha, AssetBFanghu, AssetBFsjbxx, AssetBShebeifile },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      assetBInfo: {},
      dicTreeData0: [],
      parentId0: "",
      dicTreeData1: [],
      parentId1: "",
      deptData: [],
      defaultValue: [],
    };
  },
  watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchDept();
        this.$get("assetDDictory").then((r) => {
          let data = r.data.rows.children;
          this.dicTreeData0 = data.filter((p) => p.type == 0);
          this.dicTreeData1 = data.filter((p) => p.type == 1);
        });
        let that = this;
        setTimeout(function () {
          that.$refs.addXh.setData();
        }, 500);
      }
    },
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
    moment,
    reset() {
      this.loading = false;
      this.assetBInfo = {};
      this.parentId0 = "";
      this.parentId1 = "";
      this.$refs.fileagent.reset();
      this.$refs.fileagent2.reset();
      this.$refs.addXh.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    uploadAgent_1(fileId, fileUrl) {
      this.assetBInfo.aqFileId = fileId;
      this.assetBInfo.aqFileUrl = fileUrl;
    },
    removeAgent_1() {
      this.assetBInfo.aqFileId = "";
      this.assetBInfo.aqFileUrl = "";
    },
    uploadAgent_2(fileId, fileUrl) {
      this.assetBInfo.fsFileId = fileId;
      this.assetBInfo.fsFileUrl = fileUrl;
    },
    removeAgent_2() {
      this.assetBInfo.fsFileId = "";
      this.assetBInfo.fsFileUrl = "";
    },
    fetchDept() {
      this.$get("sdlBUser/deptNew", {}).then((res) => {
        this.deptData = [];
        if (res.data[0] != null) {
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
    //  onDicChange0(value,obj) {
    //   this.parentId0= value

    //   this.assetBInfo.assetWeis = value;
    // },
    // onDicChange1(value,obj) {
    //   this.parentId1= value

    //   this.assetBInfo.assetHuanb = value;
    // },
    selectArea(selectedArea) {
        console.info(selectedArea)
      this.assetBInfo.address = JSON.stringify(selectedArea);
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$put("assetBInfo", {
            ...this.assetBInfo,
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
     setFormValues({ ...assetBInfo }) {
      let fields = [
        "assetName",
        "assetWeis",
        "assetHuanb",
        "productName",
        "assetXingh",
        "assetSn",
        "assetYnbm",
        "assetState",
        "assetDate",
        "deptName",
       
        "address",
        "aqBh",
       
        "fsBh",
       
        "checkResult",
        "performResult",
        "remark",
      ];
      let fieldDates = ["assetDate"];
       Object.keys(assetBInfo).forEach((key) => {
          if (fields.indexOf(key) !== -1) {
            this.form.getFieldDecorator(key);
            let obj = {};
            if (fieldDates.indexOf(key) !== -1) {
              if (assetBInfo[key] !== null && assetBInfo[key] !== "") {
                obj[key] = moment(assetBInfo[key]);
                this.form.setFieldsValue(obj);
              } else {
                obj[key] = "";
              }
            } else {
              if(key=='address'){
                 if (assetBInfo[key] !== null && assetBInfo[key] !== "") {
                this.assetBInfo.province= JSON.parse(assetBInfo[key])
                this.defaultValue= JSON.parse(assetBInfo[key])
                 }
                 else{
                    this.assetBInfo.province= ""
                this.defaultValue= []
                 }
              }
              else{
              obj[key] = assetBInfo[key];
              }
              this.form.setFieldsValue(obj);
            }
          }
        });
        this.assetBInfo.id = assetBInfo.id;
     // this.assetBInfo.address = assetBInfo.address;
        this.assetBInfo.fsFileId = assetBInfo.fsFileId;
      this.assetBInfo.fsFileUrl = assetBInfo.fsFileUrl;
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent2.setForm(assetBInfo.fsFileId);
      },100)
        this.assetBInfo.aqFileId = assetBInfo.aqFileId;
      this.assetBInfo.aqFileUrl = assetBInfo.aqFileUrl;
      setTimeout(()=>{
        that.$refs.fileagent.setForm(assetBInfo.aqFileId);
      },100)
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "assetName",
        "assetWeis",
        "assetHuanb",
        "productName",
        "assetXingh",
        "assetSn",
        "assetYnbm",
        // "assetState",
        "assetDate",
         "deptName",
        //"deptId",
       // "address",
        "aqBh",
        // "aqFileUrl",
        // "aqFileId",
        "fsBh",
        // "fsFileUrl",
        // "fsFileId",
        // "checkResult",
        // "performResult",
        "remark",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.assetBInfo[_key] = values[_key];
        });
      }
    },
  },
};
</script>

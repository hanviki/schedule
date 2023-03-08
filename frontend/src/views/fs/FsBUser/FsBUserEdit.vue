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
    <a-form :form="form" layout="vertical">
      <a-row>
        <a-col :span="7" :offset="1">
          <a-form-item label="姓名">
            <a-input
              placeholder="请输入姓名"
              :disabled="true"
              v-decorator="[
                'userAccountName',
                { rules: [{ required: true, message: '姓名不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="人事编号">
            <a-input
              :disabled="true"
              placeholder="请输入人事编号"
              v-decorator="[
                'userAccount',
                { rules: [{ required: true, message: '人事编号不能为空' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="个人剂量牌编号">
            <a-input
              placeholder="请输入个人剂量牌编号"
              v-decorator="[
                'grjlbh',
                {
                  rules: [
                    { required: true, message: '个人剂量牌编号不能为空' },
                  ],
                },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="放射工作人员证职业类别">
            <a-select
              placeholder="请输入放射工作人员证职业类别"
              v-decorator="[
                'zylb',
                {
                  rules: [
                    {
                      required: true,
                      message: '放射工作人员证职业类别不能为空',
                    },
                  ],
                },
              ]"
            >
             <a-select-option value="诊断放射学2A">诊断放射学2A</a-select-option>
              <a-select-option value="牙科放射学2B">牙科放射学2B</a-select-option>
               <a-select-option value="核医学2C">核医学2C</a-select-option>
                <a-select-option value="放射治疗2D">放射治疗2D</a-select-option>
                 <a-select-option value="介入放射学2E">介入放射学2E</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="院区">
            <a-select
              placeholder="请输入院区"
              v-decorator="[
                'yuanqu',
              ]"
            >
            <a-select-option value="本院">本院</a-select-option>
            <a-select-option value="西院">西院</a-select-option>
            <a-select-option value="肿瘤">肿瘤</a-select-option>
            <a-select-option value="金银湖">金银湖</a-select-option>
            <a-select-option value="PET中心">PET中心</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="岗位类别">
            <a-select
              :disabled="true"
              placeholder="请输入岗位类别"
              v-decorator="[
                'gwlb',
              ]"
            >
         <a-select-option value="诊断放射学">诊断放射学</a-select-option>
         <a-select-option value="核医学">核医学</a-select-option>
         <a-select-option value="放射治疗">放射治疗</a-select-option>
         <a-select-option value="介入放射">介入放射</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
         <a-col :span="7" :offset="1">
          <a-form-item label="婚姻状态">
            <a-select
              placeholder="请输入婚姻状态"
              v-decorator="[
                'married',
              ]"
            >
            <a-select-option value="已婚">
              已婚
            </a-select-option>
            <a-select-option value="未婚">
              未婚
            </a-select-option>
            <a-select-option value="离异">
              离异
            </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="核定计量牌个数">
            <a-select
              placeholder="请输入核定计量牌个数"
               :disabled="true"
              v-decorator="[
                'hdjlpgs',
                {
                  rules: [
                   
                  ],
                },
              ]"
            >
              <a-select-option :value="1" key="1"> 1 </a-select-option>
              <a-select-option :value="2" key="2"> 2 </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="放射工龄">
            <a-input-number
              :precision="2"
              placeholder="请输入放射工龄"
              style="width: 100%"
              v-decorator="[
                'fsgl',
               
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="开始从事放射工作时间">
            <a-date-picker
              v-decorator="[
                'fsDate',
                {
                  rules: [
                  
                  ],
                },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="监测开始时间">
            <a-date-picker
             :disabled="true"
              v-decorator="[
                'jcksDate',
                {
               
                },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="放射工作人员证号码">
            <a-input
              placeholder="请输入放射工作人员证号码"
              v-decorator="[
                'fsgzzh',
                {
                  rules: [
                   
                  ],
                },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="7" :offset="1">
          <a-form-item label="办证时间">
            <a-date-picker
              v-decorator="[
                'bzDate',
               
              ]"
            />
          </a-form-item>
        </a-col>
         <a-col :span="7" :offset="1">
        <a-form-item  label="放射工作人员证">
        <upload-single-file
            ref="fileagent"
            @uploadRemove="removeAgent_1"
            @uploadSuc="uploadAgent_1"
          >
          </upload-single-file>
      </a-form-item>
         </a-col>
         <a-col :span="23" :offset="1">
          <a-form-item label="岗位类别修改原因">
            <a-textarea
              placeholder="请输入岗位类别修改原因"
              v-decorator="[
                'note',
                {
                  rules: [
                   
                  ],
                },
              ]"
            />
          </a-form-item>
        </a-col>
      </a-row>
     
    </a-form>
    <fs-b-record :viewVisiable="viewVisiable"  :userAccount="fsBUser.userAccount" :userAccountName="fsBUser.userAccountName">
    </fs-b-record>
    <fs-b-exame-index :viewVisiable="viewVisiable"  :userAccount="fsBUser.userAccount" :userAccountName="fsBUser.userAccountName">
    </fs-b-exame-index >
    <fs-b-health :viewVisiable="viewVisiable"  :userAccount="fsBUser.userAccount" :userAccountName="fsBUser.userAccountName">
    </fs-b-health>
    <fs-b-qy-personal :viewVisiable="viewVisiable"  :userAccount="fsBUser.userAccount" :userAccountName="fsBUser.userAccountName">
    </fs-b-qy-personal>
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
        >提交</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";
import UploadSingleFile from "../../common/uploadSingleFile"
import FsBRecord from '../FsBRecord/FsBRecord.vue';
import FsBExameIndex from '../FsBExame/FsBExameIndex.vue';
import FsBHealth from '../FsBHealth/FsBHealthIndex.vue';
import FsBQyPersonal from '../FsBQy/FsBQyPersonal.vue';

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "FsBUserEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  components: {UploadSingleFile,FsBRecord, FsBExameIndex, FsBHealth, FsBQyPersonal},
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      fsBUser: {},
     
    };
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
    reset() {
      this.loading = false;
        this.$refs.fileagent.reset();
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
     uploadAgent_1(fileId, fileUrl) {
      this.fsBUser.fileId = fileId;
      this.fsBUser.fileUrl = fileUrl;
    },
    removeAgent_1() {
      this.fsBUser.fileId = "";
      this.fsBUser.fileUrl = "";
    },
    setFormValues({ ...fsBUser }) {
      let fields = [
        "userAccountName",
        "userAccount",
        "grjlbh",
        "zylb",
        "gwlb",
        "hdjlpgs",
        "fsgl",
        "fsDate",
        "jcksDate",
        "fsgzzh",
        "bzDate",
        "married","yuanqu","note"
      ];
      let fieldDates = ["fsDate", "jcksDate", "bzDate"];
      Object.keys(fsBUser).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (fsBUser[key] !== null && fsBUser[key] !== "") {
              obj[key] = moment(fsBUser[key]);
              this.form.setFieldsValue(obj);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = fsBUser[key];
            this.form.setFieldsValue(obj);
          }
        }
      });
      this.fsBUser.id = fsBUser.id;
        this.fsBUser.fileId = fsBUser.fileId;
      this.fsBUser.fileUrl = fsBUser.fileUrl;
      this.fsBUser.userAccount = fsBUser.userAccount;
      this.fsBUser.userAccountName = fsBUser.userAccountName;
      
      if(fsBUser.fileId!=null &&fsBUser.fileId!=''){
      let that=this
      setTimeout(()=>{
        that.$refs.fileagent.setForm(fsBUser.fileId);
      },100)
      }
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let fsBUser = this.form.getFieldsValue();
           fsBUser.fileId = this.fsBUser.fileId;
          fsBUser.fileUrl = this.fsBUser.fileUrl;
          //fsBUser.id = this.fsBUser.id;
          this.$put("fsBUser", {
            ...fsBUser,
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

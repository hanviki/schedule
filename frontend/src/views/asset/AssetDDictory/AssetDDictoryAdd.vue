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
      <a-form-item v-bind="formItemLayout" label="上级类型ID">
        <!-- <a-select
          placeholder="请输入上级类型ID"
          v-decorator="[
            'parentId',
            { rules: [{ required: true, message: '上级类型ID不能为空' }] },
          ]"
        >
          <a-select-option v-for="a1 in arrA1" :value="a1.value" :key="a1.value">
            {{ a1.text }}
          </a-select-option>
         
        </a-select> -->
         <a-tree-select
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="dicTreeData"
          @select="onDicChange"
          :value="parentId"
          >
        </a-tree-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="类型名称">
        <a-input
          placeholder="请输入类型名称"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '类型名称不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="排序">
        <a-input
          placeholder="请输入排序"
          v-decorator="[
            'orderNum',
            { rules: [{ required: true, message: '排序不能为空' }] },
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

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  components: {  },
  name: "AssetDDictoryAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
     dicTreeData:[],
     dicSe: [],
     parentId: '',
     assetDDictory: {}
    };
  },
   watch: {
    addVisiable () {
      if (this.addVisiable) {
        this.$get('assetDDictory').then((r) => {
          this.dicTreeData = r.data.rows.children
        })
      }
    }
  },
  methods: {
    reset() {
      this.loading = false;
      this.parentId= ''
      this.assetDDictory = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    onDicChange(value,obj) {
      this.parentId= value
      this.assetDDictory.parentId = value
    
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("assetDDictory", {
            ...this.assetDDictory,
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
        "name",
        
        "orderNum",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.assetDDictory[_key] = values[_key];
        });
      }
    },
  },
};
</script>

<template>
  <a-drawer
    title="选择手术目录"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="roleEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='手术目录选择'
                   style="margin-bottom: 2rem"
                   :validateStatus="menuSelectStatus"
                   :help="menuSelectHelp"
                   v-bind="formItemLayout">
        <a-tree
          :key="menuTreeKey"
          ref="menuTree"
          :checkable="true"
          :checkStrictly="checkStrictly"
          :defaultCheckedKeys="defaultCheckedKeys[0]"
          @check="handleCheck"
          @expand="handleExpand"
          :expandedKeys="expandedKeys"
          :treeData="menuTreeData">
        </a-tree>
      </a-form-item>
    </a-form>
    <div class="drawer-bootom-button" style="z-index:999999">
      <!-- <a-dropdown style="float: left" :trigger="['click']" placement="topCenter">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="expandAll">展开所有</a-menu-item>
          <a-menu-item key="2" @click="closeAll">合并所有</a-menu-item>
          <a-menu-item key="3" @click="enableRelate">父子关联</a-menu-item>
          <a-menu-item key="4" @click="disableRelate">取消关联</a-menu-item>
        </a-menu>
        <a-button>
          树操作 <a-icon type="up" />
        </a-button>
      </a-dropdown> -->
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'RoleEdit',
  props: {
    roleEditVisiable: {
      default: false
    },
    info: {
      default: {}
    }
  },
  data () {
    return {
      menuTreeKey: +new Date(),
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      menuSelectStatus: '',
      menuSelectHelp: '',
      role: {
        menuId: ''
      },
      menuTreeData: [],
      allTreeKeys: [],
      checkedKeys: [],
      defaultCheckedKeys: [],
      expandedKeys: [],
      checkStrictly: false
    }
  },
  methods: {
    reset () {
      this.menuTreeKey = +new Date()
      this.expandedKeys = []
      this.checkedKeys = []
      this.defaultCheckedKeys = []
      this.menuSelectStatus = this.menuSelectHelp = ''
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    expandAll () {
      this.expandedKeys = this.allTreeKeys
    },
    closeAll () {
      this.expandedKeys = []
    },
    enableRelate () {
      this.checkStrictly = false
    },
    disableRelate () {
      this.checkStrictly = true
    },
    handleCheck (checkedKeys) {
      this.checkedKeys = checkedKeys
      let checkedArr = Object.is(checkedKeys.checked, undefined) ? checkedKeys : checkedKeys.checked
      if (checkedArr.length) {
        this.menuSelectStatus = ''
        this.menuSelectHelp = ''
      } else {
        this.menuSelectStatus = 'error'
        this.menuSelectHelp = '请选择相应的手术目录'
      }
    },
    handleExpand (expandedKeys) {
      this.expandedKeys = expandedKeys
    },
    setFormValues ({...role}) {
      let fields = ['roleName', 'remark']
      Object.keys(role).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          obj[key] = role[key]
          this.form.setFieldsValue(obj)
        }
      })
    },
    handleSubmit () {
      let checkedArr = Object.is(this.checkedKeys.checked, undefined) ? this.checkedKeys : this.checkedKeys.checked
      if (checkedArr.length === 0) {
        this.menuSelectStatus = 'error'
        this.menuSelectHelp = '请选择相应的手术目录'
      } else {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.loading = true
            this.$post('mdlBSurgeryinfoD', {
              baseId: this.info.id,
              surgeryIds: checkedArr.join(',')
            }).then((r) => {
              this.reset()
              this.$emit('success')
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  },
  watch: {
    roleEditVisiable () {
      if (this.roleEditVisiable) {
        this.$get('mdlDSurgery/tree',{
            jb: this.info.jb,
            deptName: this.info.deptName,
            baseId: parseInt(this.info.id)
        }).then((r) => {
          this.menuTreeData = r.data.rows.children
       
          this.allTreeKeys = r.data.ids
          this.$get('mdlBSurgeryinfoD/tree/',{baseId: parseInt(this.info.id)}).then((r) => {
            let subData=  r.data
            console.info(subData)
            if(subData.length==0){
              subData = this.menuTreeData.map(p=>p.id)
            }
            this.defaultCheckedKeys.splice(0, this.defaultCheckedKeys.length, subData)
            this.checkedKeys = subData
            this.expandedKeys = subData
            this.menuTreeKey = +new Date()
          })
        })
      }
    }
  }
}
</script>

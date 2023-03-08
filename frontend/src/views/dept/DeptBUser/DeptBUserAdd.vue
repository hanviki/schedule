<template>
    <a-drawer
            title="新增"
            :maskClosable="false"
            width="80%"
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="addVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人事编号">
                        <a-input placeholder="请输入人事编号" v-decorator="['userAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="院区">
                        <a-input placeholder="请输入院区" v-decorator="['yuanqu', {rules: [{ required: true, message: '院区不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本科室工作工龄">
                        <a-input placeholder="请输入本科室工作工龄" v-decorator="['fsgl', {rules: [{ required: true, message: '本科室工作工龄不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="开始从事本科室工作时间">
                        <a-date-picker v-decorator="[ 'workDate', {rules: [{ required: true, message: '开始从事本科室工作时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室名称">
                        <a-input placeholder="请输入科室名称" v-decorator="['deptName', {rules: [{ required: true, message: '科室名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室ID">
                        <a-input placeholder="请输入科室ID" v-decorator="['deptId', {rules: [{ required: true, message: '科室ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件">
                        <a-input placeholder="请输入附件" v-decorator="['fileId', {rules: [{ required: true, message: '附件不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件地址">
                        <a-input placeholder="请输入附件地址" v-decorator="['fileUrl', {rules: [{ required: true, message: '附件地址不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['note', {rules: [{ required: true, message: '备注不能为空' }] }]" />
                </a-form-item>
        </a-form>
        <div class="drawer-bootom-button" style="z-index:999999">
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
        name: 'DeptBUserAdd',
        props: {
            addVisiable: {
                default: false
            }
        },
        data () {
            return {
                loading: false,
                formItemLayout,
                form: this.$form.createForm(this),
                deptBUser: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.deptBUser = {}
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    this.setFields()
                    this.$post('deptBUser', {
                        ...this.deptBUser
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            },
            setFields () {
                let values = this.form.getFieldsValue([                    'userAccountName'    ,                     'userAccount'    ,                     'yuanqu'    ,                     'fsgl'    ,                     'workDate'    ,                     'deptName'    ,                     'deptId'    ,                     'fileId'    ,                     'fileUrl'    ,                     'note'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.deptBUser[_key] = values[_key] })
                }
            }
        }
    }
</script>

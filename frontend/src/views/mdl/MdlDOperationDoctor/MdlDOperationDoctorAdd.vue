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
                    label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['deptNew', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{ required: true, message: '编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="操作名称">
                        <a-input placeholder="请输入操作名称" v-decorator="['name', {rules: [{ required: true, message: '操作名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="级别">
                        <a-input placeholder="请输入级别" v-decorator="['level', {rules: [{ required: true, message: '级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="授权医师">
                        <a-input placeholder="请输入授权医师" v-decorator="['docto', {rules: [{ required: true, message: '授权医师不能为空' }] }]" />
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
        name: 'MdlDOperationDoctorAdd',
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
                mdlDOperationDoctor: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.mdlDOperationDoctor = {}
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
                    this.$post('mdlDOperationDoctor', {
                        ...this.mdlDOperationDoctor
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
                let values = this.form.getFieldsValue([                    'deptNew'    ,                     'code'    ,                     'name'    ,                     'level'    ,                     'docto'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.mdlDOperationDoctor[_key] = values[_key] })
                }
            }
        }
    }
</script>

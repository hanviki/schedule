<template>
    <a-drawer
            title="修改"
            :maskClosable="false"
            width="80%"
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="editVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['deptNew', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{ required: true, message: '编码不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="操作名称">
                        <a-input placeholder="请输入操作名称" v-decorator="['name', {rules: [{ required: true, message: '操作名称不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="级别">
                        <a-input placeholder="请输入级别" v-decorator="['level', {rules: [{ required: true, message: '级别不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="授权医师">
                        <a-input placeholder="请输入授权医师" v-decorator="['docto', {rules: [{ required: true, message: '授权医师不能为空' }] }]" />
                </a-form-item>
                </a-col>
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
    import moment from 'moment'

    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'MdlDOperationDoctorEdit',
        props: {
            editVisiable: {
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
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            setFormValues ({...mdlDOperationDoctor}) {
                let fields = [  'deptNew'    , 'code'    , 'name'    , 'level'    , 'docto'          ]
                let fieldDates = [            ]
                Object.keys(mdlDOperationDoctor).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(mdlDOperationDoctor[key]!==null && mdlDOperationDoctor[key]!=='') {
                            obj[key] = moment(mdlDOperationDoctor[key])
                            this.form.setFieldsValue(obj)
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = mdlDOperationDoctor[key]
                        this.form.setFieldsValue(obj)
                    }

                }
            })
                this.mdlDOperationDoctor. id                         = mdlDOperationDoctor. id                                    },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let mdlDOperationDoctor = this.form.getFieldsValue()
                    mdlDOperationDoctor. id                        = this.mdlDOperationDoctor.id
                    this.$put('mdlDOperationDoctor', {
                        ...mdlDOperationDoctor
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            }
        }
    }
</script>

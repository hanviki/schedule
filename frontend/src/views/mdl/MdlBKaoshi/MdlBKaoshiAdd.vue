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
                    label="发薪号">
                        <a-input placeholder="请输入发薪号" v-decorator="['userAccount', {rules: [{ required: true, message: '发薪号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="医院">
                        <a-input placeholder="请输入医院" v-decorator="['hospital', {rules: [{ required: true, message: '医院不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="院区">
                        <a-input placeholder="请输入院区" v-decorator="['yuanqu', {rules: [{ required: true, message: '院区不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="快速搜索分类">
                        <a-input placeholder="请输入快速搜索分类" v-decorator="['ksssfl', {rules: [{ required: true, message: '快速搜索分类不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="临床科室分类">
                        <a-input placeholder="请输入临床科室分类" v-decorator="['lcksfl', {rules: [{ required: true, message: '临床科室分类不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="单元">
                        <a-input placeholder="请输入单元" v-decorator="['danyuan', {rules: [{ required: true, message: '单元不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="手机号码">
                        <a-input placeholder="请输入手机号码" v-decorator="['telphone', {rules: [{ required: true, message: '手机号码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="工号">
                        <a-input placeholder="请输入工号" v-decorator="['pernr', {rules: [{ required: true, message: '工号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="角色">
                        <a-input placeholder="请输入角色" v-decorator="['role', {rules: [{ required: true, message: '角色不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="岗位">
                        <a-input placeholder="请输入岗位" v-decorator="['gangwei', {rules: [{ required: true, message: '岗位不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="岗位性质">
                        <a-input placeholder="请输入岗位性质" v-decorator="['gangweixingzhi', {rules: [{ required: true, message: '岗位性质不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="首次阅读时间">
                        <a-input placeholder="请输入首次阅读时间" v-decorator="['firstyuedu', {rules: [{ required: true, message: '首次阅读时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="最后一次阅读时间">
                        <a-input placeholder="请输入最后一次阅读时间" v-decorator="['lastyuedu', {rules: [{ required: true, message: '最后一次阅读时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="累计阅读时长">
                        <a-input placeholder="请输入累计阅读时长" v-decorator="['leijiyuedu', {rules: [{ required: true, message: '累计阅读时长不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="完成率">
                        <a-input placeholder="请输入完成率" v-decorator="['wanchenglv', {rules: [{ required: true, message: '完成率不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="练习次数">
                        <a-input placeholder="请输入练习次数" v-decorator="['lxcs', {rules: [{ required: true, message: '练习次数不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="已获得学分">
                        <a-input placeholder="请输入已获得学分" v-decorator="['yihuoxuefen', {rules: [{ required: true, message: '已获得学分不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否计划内">
                        <a-input placeholder="请输入是否计划内" v-decorator="['ifjihuannei', {rules: [{ required: true, message: '是否计划内不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="身份证">
                        <a-input placeholder="请输入身份证" v-decorator="['idcard', {rules: [{ required: true, message: '身份证不能为空' }] }]" />
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
        name: 'MdlBKaoshiAdd',
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
                mdlBKaoshi: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.mdlBKaoshi = {}
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
                    this.$post('mdlBKaoshi', {
                        ...this.mdlBKaoshi
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
                let values = this.form.getFieldsValue([                    'userAccount'    ,                     'hospital'    ,                     'yuanqu'    ,                     'ksssfl'    ,                     'lcksfl'    ,                     'danyuan'    ,                     'userAccountName'    ,                     'telphone'    ,                     'pernr'    ,                     'role'    ,                     'gangwei'    ,                     'gangweixingzhi'    ,                     'firstyuedu'    ,                     'lastyuedu'    ,                     'leijiyuedu'    ,                     'wanchenglv'    ,                     'lxcs'    ,                     'yihuoxuefen'    ,                     'ifjihuannei'    ,                     'idcard'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.mdlBKaoshi[_key] = values[_key] })
                }
            }
        }
    }
</script>

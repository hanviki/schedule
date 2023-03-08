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
                        label="发薪号">
                        <a-input placeholder="请输入发薪号" v-decorator="['userAccount', {rules: [{ required: true, message: '发薪号不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="排名">
                        <a-input placeholder="请输入排名" v-decorator="['paiming', {rules: [{ required: true, message: '排名不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="医院">
                        <a-input placeholder="请输入医院" v-decorator="['hospital', {rules: [{ required: true, message: '医院不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="院区">
                        <a-input placeholder="请输入院区" v-decorator="['yuanqu', {rules: [{ required: true, message: '院区不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="快速搜索分类">
                        <a-input placeholder="请输入快速搜索分类" v-decorator="['ksssfl', {rules: [{ required: true, message: '快速搜索分类不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="临床科室分类">
                        <a-input placeholder="请输入临床科室分类" v-decorator="['lcksfl', {rules: [{ required: true, message: '临床科室分类不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="单元">
                        <a-input placeholder="请输入单元" v-decorator="['danyuan', {rules: [{ required: true, message: '单元不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="手机号码">
                        <a-input placeholder="请输入手机号码" v-decorator="['telphone', {rules: [{ required: true, message: '手机号码不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="工号">
                        <a-input placeholder="请输入工号" v-decorator="['pernr', {rules: [{ required: true, message: '工号不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="角色">
                        <a-input placeholder="请输入角色" v-decorator="['role', {rules: [{ required: true, message: '角色不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="岗位">
                        <a-input placeholder="请输入岗位" v-decorator="['gangwei', {rules: [{ required: true, message: '岗位不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="岗位性质">
                        <a-input placeholder="请输入岗位性质" v-decorator="['hgangweixingzhi', {rules: [{ required: true, message: '岗位性质不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="开始考试时间">
                        <a-input placeholder="请输入开始考试时间" v-decorator="['starttime', {rules: [{ required: true, message: '开始考试时间不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="结束考试时间">
                        <a-input placeholder="请输入结束考试时间" v-decorator="['endtime', {rules: [{ required: true, message: '结束考试时间不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="考试时长">
                        <a-input placeholder="请输入考试时长" v-decorator="['kaoshishichang', {rules: [{ required: true, message: '考试时长不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="考试成绩">
                        <a-input placeholder="请输入考试成绩" v-decorator="['score', {rules: [{ required: true, message: '考试成绩不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="考试次数">
                        <a-input placeholder="请输入考试次数" v-decorator="['cishu', {rules: [{ required: true, message: '考试次数不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否合格">
                        <a-input placeholder="请输入是否合格" v-decorator="['ifhege', {rules: [{ required: true, message: '是否合格不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="设备编码">
                        <a-input placeholder="请输入设备编码" v-decorator="['shebeibianhao', {rules: [{ required: true, message: '设备编码不能为空' }] }]" />
                </a-form-item>
                </a-col>
                <a-col :sm="6">
                <a-form-item
                        v-bind="formItemLayout"
                        label="身份证">
                        <a-input placeholder="请输入身份证" v-decorator="['idcard', {rules: [{ required: true, message: '身份证不能为空' }] }]" />
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
        name: 'MdlBPeixunEdit',
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
            mdlBPeixun: {}
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
            setFormValues ({...mdlBPeixun}) {
                let fields = [  'userAccount'    , 'paiming'    , 'hospital'    , 'yuanqu'    , 'ksssfl'    , 'lcksfl'    , 'danyuan'    , 'userAccountName'    , 'telphone'    , 'pernr'    , 'role'    , 'gangwei'    , 'hgangweixingzhi'    , 'starttime'    , 'endtime'    , 'kaoshishichang'    , 'score'    , 'cishu'    , 'ifhege'    , 'shebeibianhao'    , 'idcard'          ]
                let fieldDates = [                            ]
                Object.keys(mdlBPeixun).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(mdlBPeixun[key]!==null && mdlBPeixun[key]!=='') {
                            obj[key] = moment(mdlBPeixun[key])
                            this.form.setFieldsValue(obj)
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = mdlBPeixun[key]
                        this.form.setFieldsValue(obj)
                    }

                }
            })
                this.mdlBPeixun. id                                                         = mdlBPeixun. id                                                                    },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let mdlBPeixun = this.form.getFieldsValue()
                    mdlBPeixun. id                                                        = this.mdlBPeixun.id
                    this.$put('mdlBPeixun', {
                        ...mdlBPeixun
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

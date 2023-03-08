<template>
    <a-card title="">
        <div>
            <a-button
                    @click="handleAdd"
                    type="primary"
                    :loading="loading"
            >添加行</a-button>
            <a-button
                    @click="handleDelete"
                    type="primary"
                    :loading="loading"
            >删除行</a-button>
        </div>
        <a-table
                :columns="columns"
                :data-source="dataSource"
                :rowKey="record => record.id"
                :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
                bordered
        >
      <template
                    slot="baseId"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'baseId')"
                      :value="record.baseId"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="labCode"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'labCode')"
                      :value="record.labCode"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="labName"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'labName')"
                      :value="record.labName"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="code"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'code')"
                      :value="record.code"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="name"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'name')"
                      :value="record.name"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="detailName"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'detailName')"
                      :value="record.detailName"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="fanwei"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'fanwei')"
                      :value="record.fanwei"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="detailCode"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'detailCode')"
                      :value="record.detailCode"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="priceCode"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'priceCode')"
                      :value="record.priceCode"
              >
              </a-textarea>
          </div>
            </template>
        <template
                slot="amount"
                slot-scope="text, record"
        >
            <div v-if="record.state==3">
                {{text}}
            </div>
            <div v-else>
                <a-input-number
                        @blur="e => inputChange(e.target.value,record,'amount')"
                        :value="record.amount"
                        :precision="0"
                >
                </a-input-number>
            </div>
        </template>
      <template
                    slot="isLis"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'isLis')"
                      :value="record.isLis"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="fileId"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'fileId')"
                      :value="record.fileId"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="fileUrl"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'fileUrl')"
                      :value="record.fileUrl"
              >
              </a-textarea>
          </div>
            </template>
            <template
                    slot="fileId"
                    slot-scope="text, record"
            >
                <div v-if="record.state==3 || record.state==1">
                    <a :href="record.fileUrl" v-if="text!=null && text !=''" target="_blank" >查看</a>
                </div>
                <div v-else>
                    <a-button
                            type="dashed"
                            block
                            @click="OpenFile(record)"
                    >
                        {{record.fileId!=null &&record.fileId !=''?'已上传':'上传' }}
                    </a-button>
                </div>
            </template>
            <template
                    slot="isUse"
                    slot-scope="text, record"
            >
                <a-checkbox
                        @change="e => onIsUseChange(e,record,'isUse')"
                        :checked="text"
                ></a-checkbox>
            </template>
        </a-table>
        <div>
            <a-button
                    @click="handleSave"
                    type="primary"
                    :loading="loading"
            >保存草稿</a-button>
            <a-button
                    @click="handleSubmit"
                    type="primary"
                    :loading="loading"
            >提交</a-button>
        </div>
        <tableUpload-file
                ref="upFile"
                :fileId="editRecord.fileId"
                :fileVisiable="fileVisiable"
                @setFileId="setFileId"
        >
        </tableUpload-file>
    </a-card>
</template>

<script>
    import moment from 'moment';
    import TableUploadFile from '../../common/TableUploadFile'
    export default {
        data () {
            return {
                dateFormat: 'YYYY-MM-DD',
                dataSource: [],
                selectedRowKeys: [],
                loading: false,
                CustomVisiable: false,
                idNums: 10000,
                fileVisiable: false,
                editRecord: {
                fileId: ''
            }
            }
        },
        components: { TableUploadFile },
        mounted () {
            this.fetch()
        },
        methods: {
            moment,
            showFile (record) {
                window.location.href = record.fileUrl
            },
            OpenFile (record) {
                this.editRecord = record
                this.fileVisiable = true
                if (record.fileId!= undefined && record.fileId != '') {
                    this.$refs.upFile.fetch(record.fileId)
                }
            },
            setFileId (fileId, fileUrl) {
                this.fileVisiable = false
                console.log(fileUrl)
                /**
                 const dataSource = [...this.dataSource]
                 console.log(this.editRecord.id)
                 let record=dataSource.filter(p=>p.id===this.editRecord.id)
                 console.log(record)*/
                this.editRecord["fileId"] = fileId
                this.editRecord["fileUrl"] = fileUrl
                //this.dataSource =[...dataSource]
            },
            onSelectChange (selectedRowKeys, selectedRows) {
                // console.log(selectedRows)
                if (selectedRows.length > 0) {
                    if (selectedRows[0].state != 3 && selectedRows[0].state != 1) {
                        this.selectedRowKeys = selectedRowKeys
                    }
                }
                else{
                    this.selectedRowKeys = selectedRowKeys
                }
            },
            handleChange (date, dateStr, record, filedName) {
                const value = dateStr
                record[filedName] = value
            },
            inputChange (value, record, filedName) {
                console.info(value)
                record[filedName] = value
            },
            handleSelectChange (value, option, record, filedName) {
                console.info(value)
                record[filedName] = value
            },
            onIsUseChange (e, record, filedName) {
                record[filedName] = e.target.checked;
            },
            handleAdd () {
                for (let i = 0; i < 4; i++) {
                    this.dataSource.push({
                        id: (this.idNums + i + 1).toString(),
                        state: 0,
                        baseId: '',
                        labCode: '',
                        labName: '',
                        code: '',
                        name: '',
                        detailName: '',
                        fanwei: '',
                        detailCode: '',
                        priceCode: '',
                        amount: '',
                        isLis: '',
                        fileId: '',
                        fileUrl: '',
                        isUse: false
                    })
                }
                this.idNums = this.idNums + 4
            },
            handleSave () {
                const dataSourceAll = [...this.dataSource]
                const dataSource = dataSourceAll.filter(p=>p.state==0 ||p.state==2)
                let dataAdd = []
                dataSource.forEach(element => {
   if(         element.baseId!= ''       ||    element.labCode!= ''       ||    element.labName!= ''       ||    element.code!= ''       ||    element.name!= ''       ||    element.detailName!= ''       ||    element.fanwei!= ''       ||    element.detailCode!= ''       ||    element.priceCode!= ''       ||    element.amount!= ''       ||    element.isLis!= ''       ||    element.fileId!= ''       ||    element.fileUrl!= ''                     ){
                    dataAdd.push(element)
                }
            });
                if (dataAdd.length === 0) {
                    this.$message.warning('请填写数据！！！')
                }
                else {
                    let jsonStr = JSON.stringify(dataAdd)
                    this.loading = true
                    this.$post('mdlLProject/addNew', {
                        jsonStr: jsonStr,
                        state: 0
                    }).then(() => {
                        // this.reset()
                        this.$message.success('保存成功')
                        this.fetch()
                        this.loading = false
                    }).catch(() => {
                        this.loading = false
                    })
                }
            },
            handleSubmit () {
                let that = this
                this.$confirm({
                    title: '确定提交全部记录?',
                    content: '当您点击确定按钮后，信息将不能修改',
                    centered: true,
                    onOk () {
                        const dataSourceAll = [...that.dataSource]
                        const dataSource = dataSourceAll.filter(p=>p.state==0 ||p.state==2)
                        let dataAdd = []
                        dataSource.forEach(element => {
   if(         element.baseId!= ''       ||    element.labCode!= ''       ||    element.labName!= ''       ||    element.code!= ''       ||    element.name!= ''       ||    element.detailName!= ''       ||    element.fanwei!= ''       ||    element.detailCode!= ''       ||    element.priceCode!= ''       ||    element.amount!= ''       ||    element.isLis!= ''       ||    element.fileId!= ''       ||    element.fileUrl!= ''                     ){
                            dataAdd.push(element)
                        }
                    });
                        if (dataAdd.length === 0) {
                            that.$message.warning('请填写数据！！！')
                        }
                        else {
                            let jsonStr = JSON.stringify(dataAdd)
                            that.loading = true
                            that.$post('mdlLProject/addNew', {
                                jsonStr: jsonStr,
                                state: 1
                            }).then(() => {
                                //this.reset()
                                that.$message.success('提交成功')
                                that.fetch()
                                that.CustomVisiable = false //提交之后 不能再修改
                                that.loading = false
                            }).catch(() => {
                                that.loading = false
                            })
                        }
                    },
                    onCancel () {
                        that.selectedRowKeys = []
                    }
                })


            },
            handleDelete () {
                if (!this.selectedRowKeys.length) {
                    this.$message.warning('请选择需要删除的记录')
                            return
                }
                let that = this
                this.$confirm({
                    title: '确定删除所选中的记录?',
                    content: '当您点击确定按钮后，这些记录将会被彻底删除',
                    centered: true,
                    onOk () {
                        let dcaBPatentIds = that.selectedRowKeys.join(',')
                        const dataSource = [...that.dataSource];
                        let new_dataSource = dataSource.filter(p => that.selectedRowKeys.indexOf(p.id) < 0)
                        that.dataSource = new_dataSource
                        that.$message.success('删除成功')
                                that.selectedRowKeys = []
                    },
                    onCancel () {
                        that.selectedRowKeys = []
                    }
                })
            },
            fetch () {
                this.$get('mdlLProject/custom', {
                }).then((r) => {
                    let data = r.data
                    this.dataSource = data.rows

                for (let i = 0; i < 4; i++) {
                    this.dataSource.push({
                        id: (this.idNums + i + 1).toString(),
                        state: 0,
                            baseId: '' ,
                            labCode: '' ,
                            labName: '' ,
                            code: '' ,
                            name: '' ,
                            detailName: '' ,
                            fanwei: '' ,
                            detailCode: '' ,
                            priceCode: '' ,
                            amount: '' ,
                            isLis: '' ,
                            fileId: '' ,
                            fileUrl: '' ,
                        isUse: false
                })
                    this.idNums = this.idNums + 4
                }
            })
            }
        },
        computed: {
            columns () {
            return [                        {
                                title: '父ID',
                            dataIndex: 'baseId',
                            width: 130,
                            scopedSlots: { customRender: 'baseId' }
                        },
                        {
                                title: '实验室编码',
                            dataIndex: 'labCode',
                            width: 130,
                            scopedSlots: { customRender: 'labCode' }
                        },
                        {
                                title: '实验室名称',
                            dataIndex: 'labName',
                            width: 130,
                            scopedSlots: { customRender: 'labName' }
                        },
                        {
                                title: '医嘱编码',
                            dataIndex: 'code',
                            width: 130,
                            scopedSlots: { customRender: 'code' }
                        },
                        {
                                title: '医嘱名称',
                            dataIndex: 'name',
                            width: 130,
                            scopedSlots: { customRender: 'name' }
                        },
                        {
                                title: '明细项目名称',
                            dataIndex: 'detailName',
                            width: 130,
                            scopedSlots: { customRender: 'detailName' }
                        },
                        {
                                title: '使用范围',
                            dataIndex: 'fanwei',
                            width: 130,
                            scopedSlots: { customRender: 'fanwei' }
                        },
                        {
                                title: '明细项目编码',
                            dataIndex: 'detailCode',
                            width: 130,
                            scopedSlots: { customRender: 'detailCode' }
                        },
                        {
                                title: '物价编号',
                            dataIndex: 'priceCode',
                            width: 130,
                            scopedSlots: { customRender: 'priceCode' }
                        },
                        {
                                title: '金额',
                            dataIndex: 'amount',
                            width: 130,
                            scopedSlots: { customRender: 'amount' }
                        },
                        {
                                title: '是接入LIS',
                            dataIndex: 'isLis',
                            width: 130,
                            scopedSlots: { customRender: 'isLis' }
                        },
                        {
                                title: '附件ID',
                            dataIndex: 'fileId',
                            width: 130,
                            scopedSlots: { customRender: 'fileId' }
                        },
                        {
                                title: '附件地址',
                            dataIndex: 'fileUrl',
                            width: 130,
                            scopedSlots: { customRender: 'fileUrl' }
                        },
                {
                    title: '状态',
                    dataIndex: 'state',
                    width: 100,
                    customRender: (text, row, index) => {
                    switch (text) {
                    case 0:
                        return <a-tag color="purple">未提交</a-tag>
                    case 1:
                        return <a-tag color="green">已提交</a-tag>
                    case 2:
                        return <a-tag color="red">审核未通过</a-tag>
                    case 3:
                        return <a-tag color="#f50">已审核</a-tag>
                    default:
                        return text
                    }
                }
            },
                {
                    title: '审核意见',
                            dataIndex: 'auditSuggestion'
                },
                {
                    title: '是否用于本次评审',
                    dataIndex: 'isUse',
                    scopedSlots: { customRender: 'isUse' },
                    width: 80
                },
                {
                    title: '附件',
                            dataIndex: 'fileId',
                        scopedSlots: { customRender: 'fileId' },
                    width: 80
                }]
            }
        },
    }
</script>

<style lang="less" scoped>
    @import "../../../../static/less/Common";
</style>

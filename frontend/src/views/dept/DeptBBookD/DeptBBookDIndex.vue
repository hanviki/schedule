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
                    slot="userAccount"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'userAccount')"
                      :value="record.userAccount"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="userAccountName"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'userAccountName')"
                      :value="record.userAccountName"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="bookType"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'bookType')"
                      :value="record.bookType"
              >
              </a-textarea>
          </div>
            </template>
            <template
                    slot="checkDate"
                    slot-scope="text, record"
            >
                <div v-if="record.state==3 || record.state==1">
                    {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                    <a-date-picker
                            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                            @change="(e,f) => handleChange(e,f,record,'checkDate')"
                    />
                </div>
            </template>
      <template
                    slot="note"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'note')"
                      :value="record.note"
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
                        userAccount: '',
                        userAccountName: '',
                        bookType: '',
                        checkDate: '',
                        note: '',
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
   if(         element.userAccount!= ''       ||    element.userAccountName!= ''       ||    element.bookType!= ''       ||    element.checkDate!= ''          ||    element.note!= ''                         ||    element.fileId!= ''       ||    element.fileUrl!= ''   ){
                    dataAdd.push(element)
                }
            });
                if (dataAdd.length === 0) {
                    this.$message.warning('请填写数据！！！')
                }
                else {
                    let jsonStr = JSON.stringify(dataAdd)
                    this.loading = true
                    this.$post('deptBBookD/addNew', {
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
   if(         element.userAccount!= ''       ||    element.userAccountName!= ''       ||    element.bookType!= ''       ||    element.checkDate!= ''          ||    element.note!= ''                         ||    element.fileId!= ''       ||    element.fileUrl!= ''   ){
                            dataAdd.push(element)
                        }
                    });
                        if (dataAdd.length === 0) {
                            that.$message.warning('请填写数据！！！')
                        }
                        else {
                            let jsonStr = JSON.stringify(dataAdd)
                            that.loading = true
                            that.$post('deptBBookD/addNew', {
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
                this.$get('deptBBookD/custom', {
                }).then((r) => {
                    let data = r.data
                    this.dataSource = data.rows

                for (let i = 0; i < 4; i++) {
                    this.dataSource.push({
                        id: (this.idNums + i + 1).toString(),
                        state: 0,
                            userAccount: '' ,
                            userAccountName: '' ,
                            bookType: '' ,
                            checkDate: '' ,
                            note: '' ,
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
                                title: '发薪号',
                            dataIndex: 'userAccount',
                            width: 130,
                            scopedSlots: { customRender: 'userAccount' }
                        },
                        {
                                title: '姓名',
                            dataIndex: 'userAccountName',
                            width: 130,
                            scopedSlots: { customRender: 'userAccountName' }
                        },
                        {
                                title: '证件类型',
                            dataIndex: 'bookType',
                            width: 130,
                            scopedSlots: { customRender: 'bookType' }
                        },
                        {
                                title: '检测时间',
                            dataIndex: 'checkDate',
                            width: 130,
                            scopedSlots: { customRender: 'checkDate' }
                        },
                        {
                                title: '备注',
                            dataIndex: 'note',
                            width: 130,
                            scopedSlots: { customRender: 'note' }
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

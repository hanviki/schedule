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
                    slot="parentId"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3 || record.state==1">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'parentId')"
                      :value="record.parentId"
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
                slot="typeIndex"
                slot-scope="text, record"
        >
            <div v-if="record.state==3">
                {{text}}
            </div>
            <div v-else>
                <a-input-number
                        @blur="e => inputChange(e.target.value,record,'typeIndex')"
                        :value="record.typeIndex"
                        :precision="0"
                >
                </a-input-number>
            </div>
        </template>
        <template
                slot="orderNum"
                slot-scope="text, record"
        >
            <div v-if="record.state==3">
                {{text}}
            </div>
            <div v-else>
                <a-input-number
                        @blur="e => inputChange(e.target.value,record,'orderNum')"
                        :value="record.orderNum"
                        :precision="0"
                >
                </a-input-number>
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
                        parentId: '',
                        name: '',
                        typeIndex: '',
                        orderNum: '',
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
   if(         element.parentId!= ''       ||    element.name!= ''       ||    element.typeIndex!= ''       ||    element.orderNum!= ''         ){
                    dataAdd.push(element)
                }
            });
                if (dataAdd.length === 0) {
                    this.$message.warning('请填写数据！！！')
                }
                else {
                    let jsonStr = JSON.stringify(dataAdd)
                    this.loading = true
                    this.$post('assetDDictory/addNew', {
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
   if(         element.parentId!= ''       ||    element.name!= ''       ||    element.typeIndex!= ''       ||    element.orderNum!= ''         ){
                            dataAdd.push(element)
                        }
                    });
                        if (dataAdd.length === 0) {
                            that.$message.warning('请填写数据！！！')
                        }
                        else {
                            let jsonStr = JSON.stringify(dataAdd)
                            that.loading = true
                            that.$post('assetDDictory/addNew', {
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
                this.$get('assetDDictory/custom', {
                }).then((r) => {
                    let data = r.data
                    this.dataSource = data.rows

                for (let i = 0; i < 4; i++) {
                    this.dataSource.push({
                        id: (this.idNums + i + 1).toString(),
                        state: 0,
                            parentId: '' ,
                            name: '' ,
                            typeIndex: '' ,
                            orderNum: '' ,
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
                                title: '上级类型ID',
                            dataIndex: 'parentId',
                            width: 130,
                            scopedSlots: { customRender: 'parentId' }
                        },
                        {
                                title: '类型名称',
                            dataIndex: 'name',
                            width: 130,
                            scopedSlots: { customRender: 'name' }
                        },
                        {
                                title: '0是卫生1是环保2是个人防护用品3辅助防护用品',
                            dataIndex: 'typeIndex',
                            width: 130,
                            scopedSlots: { customRender: 'typeIndex' }
                        },
                        {
                                title: '排序',
                            dataIndex: 'orderNum',
                            width: 130,
                            scopedSlots: { customRender: 'orderNum' }
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

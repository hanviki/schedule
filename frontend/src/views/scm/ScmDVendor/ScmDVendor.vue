<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <div :class="advanced ? null: 'fold'">
          <a-row>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.name" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="编码"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.code" />
              </a-form-item>
            </a-col>
             <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
              <a-select  @change="handleChange">
                <a-select-option key="0" value="0">未审核</a-select-option>
                <a-select-option key="1" value="1">已审核</a-select-option>
                <a-select-option key="2" value="2">审核未通过</a-select-option>
                <a-select-option key="-1" value="-1">全部</a-select-option>
              </a-select>
              </a-form-item>
            </a-col>
          </a-row>
        </div>
        <span style="float: right; margin-top: 3px; margin-bottom: 10px;margin-bottom:10px;">
          <a-button
            type="primary"
            @click="search"
          >查询</a-button>
          <a-button
            style="margin-left: 8px"
            @click="reset"
          >重置</a-button>
          <a
            @click="toggleAdvanced"
            style="margin-left: 8px"
          >
            {{advanced ? '收起' : '展开'}}
            <a-icon :type="advanced ? 'up' : 'down'" />
          </a>
        </span>
      </a-form>
      <a-modal
        title="审核不成功"
        :visible="unAuditvisible"
        :confirm-loading="loading"
        @ok="handleAuditOk"
        @cancel="handleAuditCancel"
      >
        <a-textarea
          placeholder="请输入审核不成功原因"
          v-model="auditCause"
        />
      </a-modal>
    </div>
    <div>
      <div class="operator">
        <!-- <a-button
          type="primary"
          ghost
          @click="save(0)"
        >保存</a-button> -->
        <a-button
          type="primary"
          @click="save(1)"
        >保存并审核</a-button>
        <a-button
          type="danger"
          @click="auditUndo()"
        >审核退回</a-button>
        <a-button
          type="primary"
          @click="open(1)"
        >打开接口</a-button>
        <a-button
          type="danger"
          @click="open(0)"
        >关闭接口</a-button>
        <a-button
          type="primary"
          @click="open(3)"
        >限制资质文件</a-button>
        <a-button
          type="danger"
          @click="open(4)"
        >不限制资质文件</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="scroll"
      >

        <template
          slot="code"
          slot-scope="text, record"
        >
          <editable-cell
            :text="text"
            @change="onCellChange(record.id, 'code', $event)"
          />
        </template>
        <template
          slot="operation"
          slot-scope="text, record"
        >
          <a-icon
            v-hasPermission="['scmDVendor:view']"
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="edit(record)"
            title="查看"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['scmDVendor:update']"
            status="warning"
            text="无权限"
          ></a-badge>
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <scmDVendor-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </scmDVendor-add>
    <!-- 修改字典 -->
    <scmDVendor-edit
      ref="scmDVendorEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </scmDVendor-edit>
  </a-card>
</template>

<script>
import ScmDVendorAdd from './ScmDVendorAdd'
import ScmDVendorEdit from './ScmDVendorEdit'
import EditableCell from '../../common/EditableCell'

export default {
  name: 'ScmDVendor',
  components: { ScmDVendorAdd, ScmDVendorEdit, EditableCell },
  data () {
    return {
      scroll: {
        x: 1500,
        y: window.innerHeight - 200 - 100 - 20
      },
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        lb: 0
      },
      addVisiable: false,
      editVisiable: false,
      unAuditvisible: false,
      auditCause: '',
      loading: false,
      bordered: true
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '编码',
        dataIndex: 'code',
        scopedSlots: { customRender: 'code' },
        fixed: 'left',
        width: 120
      }, {
        title: '名字',
        dataIndex: 'name',
        fixed: 'left',
        width: 180
      }, {
        title: '联系人',
        dataIndex: 'linkPerson',
        width: 120
      }, {
        title: '联系电话',
        dataIndex: 'phone',
        width: 120
      }, {
        title: '邮件',
        dataIndex: 'email',
        width: 180
      }, {
        title: '地址',
        dataIndex: 'address'
      }, {
        title: '状态',
        dataIndex: 'state',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">未审核</a-tag>
            case 1:
              return <a-tag color="green">已审核</a-tag>
            case 2:
              return <a-tag color="red">审核未通过</a-tag>
            default:
              return text
          }
        },
        width: 80,
        fixed: 'right'
      }, {
        title: '接口状态',
        dataIndex: 'jieKouState',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="cyan">限制</a-tag>
            case 1:
              return <a-tag color="red">不限制</a-tag>
            default:
              return text
          }
        },
        width: 80,
        fixed: 'right'
      }, {
        title: '验收资质',
        dataIndex: 'fileState',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="red">不限制</a-tag>
            case 1:
              return <a-tag color="cyan">限制</a-tag>
            default:
              return text
          }
        },
        width: 80,
        fixed: 'right'
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' },
        fixed: 'right',
        width: 60
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
      if (!this.advanced) {
        this.queryParams.comments = ''
      }
    },
    handleChange (value) {
      if (value !== '-1') {
        this.queryParams.state = value
      } else {
        this.queryParams.state = ''
      }
    },
    handleAddSuccess () {
      this.addVisiable = false
      this.$message.success('新增字典成功')
      this.search()
    },
    handleAddClose () {
      this.addVisiable = false
    },
    add () {

    },
    onCellChange (key, dataIndex, value) {
      const dataSource = [...this.dataSource];
      const target = dataSource.find(item => item.id === key);
      console.info(target)
      if (target) {
        target[dataIndex] = value;
        this.dataSource = dataSource;
      }
    },
    open (flag) {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要操作的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定保存所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被保存',
        centered: true,
        onOk () {
          let scmDVendorIds = that.selectedRowKeys.join(',')
          let arrCodes = []
          const dataSource = [...that.dataSource]
          let IsValid = 0
          for (let key in that.selectedRowKeys) {
            let row = dataSource.find(item => item.id === that.selectedRowKeys[key])
            if (flag == 0) {//关闭接口
              if (row.jieKouState == 0) {
                IsValid = 1
                that.$message.warning(`该${row.name}用户已经关闭接口,请确认操作`)
              }
              arrCodes.push({
                id: row.id,
                jieKouState: 0
              })
            }
            if (flag == 1) {//保存审核
              if (row.jieKouState == 1) {
                IsValid = 1
                that.$message.warning(`该${row.name}用户已经打开接口,请确认操作`)
              }
              arrCodes.push({
                id: row.id,
                jieKouState: 1
              })
            }
            if (flag == 3) { // 限制
              if (row.fileState == 1) {
                IsValid = 1
                that.$message.warning(`该${row.name}用户已经限制资质上传,请确认操作`)
              }
              arrCodes.push({
                id: row.id,
                fileState: 1
              })
            }
            if (flag == 4) { // 不限制
              if (row.fileState == 0) {
                IsValid = 1
                that.$message.warning(`该${row.name}用户已经不限制资质上传,请确认操作`)
              }
              arrCodes.push({
                id: row.id,
                fileState: 0
              })
            }
          }
          if (IsValid == 0) {
            let arrJson = JSON.stringify(arrCodes)
            that.$post('scmDVendor/SaveCode', { data: arrJson }).then(() => {
              that.$message.success('操作成功')
              that.selectedRowKeys = []
              that.search()
            })
          }
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    handleAuditOk () {
      let that = this
      // let scmDVendorIds = that.selectedRowKeys.join(',')
      let arrCodes = []
      const dataSource = [...that.dataSource]
      let IsValid = 0
      for (let key in that.selectedRowKeys) {
        let row = dataSource.find(item => item.id === that.selectedRowKeys[key])

        if (row.state == 2) {
          IsValid = 1
          that.$message.warning(`该${row.name}用户已取消审核,请确认操作`)
        }
        arrCodes.push({
          id: row.id,
          code: row.code,
          name: row.name,
          state: 2,
          lb: 0
        })
      }
      if (IsValid === 0) {
        let arrJson = JSON.stringify(arrCodes)
        that.$post('scmDVendor/SaveCode', { data: arrJson, auditCause: this.auditCause }).then(() => {
          this.unAuditvisible = false
          that.$message.success('操作成功')
          that.selectedRowKeys = []
          that.search()
        })
      }
    },
    handleAuditCancel () {
      this.auditCause = ''
      this.unAuditvisible = false
    },
    auditUndo () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要操作的记录')
        return
      }
      this.auditCause = ''
      this.unAuditvisible = true
    },
    save (flag) {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要操作的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定保存所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被保存',
        centered: true,
        onOk () {
          let scmDVendorIds = that.selectedRowKeys.join(',')
          let arrCodes = [];
          const dataSource = [...that.dataSource]
          let IsValid = 0
          for (let key in that.selectedRowKeys) {
            let row = dataSource.find(item => item.id === that.selectedRowKeys[key])
            if (flag == 0) {//保存
              if (row.code == "" || row.state == 1) {
                IsValid = 1
                that.$message.warning(`该${row.name}用户已经审核或账号未填写,请确认操作`)
              }
              arrCodes.push({
                id: row.id,
                code: row.code,
                name: row.name,
                lb: 0
              })
            }
            if (flag == 1) {//保存审核
              if (row.code == "" || row.state == 1) {
                IsValid = 1
                that.$message.warning(`该${row.name}用户已经审核或账号未填写,请确认操作`)
              }
              arrCodes.push({
                id: row.id,
                code: row.code,
                name: row.name,
                state: 1,
                lb: 0
              })
            }
            if (flag == 2) {//取消审核
              if (row.code == "" || row.state == 0 || row.state == 2) {
                IsValid = 1
                that.$message.warning(`该${row.name}用户尚未审核,请确认操作`)
              }
              arrCodes.push({
                id: row.id,
                code: row.code,
                name: row.name,
                state: 2,
                lb: 0
              })
            }
          }
          if (IsValid == 0) {
            let arrJson = JSON.stringify(arrCodes)
            that.$post('scmDVendor/SaveCode', { data: arrJson }).then(() => {
              that.$message.success('操作成功')
              that.selectedRowKeys = []
              that.search()
            })
          }
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    handleEditSuccess () {
      this.editVisiable = false
      this.$message.success('修改字典成功')
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {
      this.$refs.scmDVendorEdit.setFormValues(record)
      this.$refs.scmDVendorEdit.attachList = [
        { title: "企业法人营业执照", isRequire: true, index: 1, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国组织机构代码证", isRequire: true, index: 2, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "税务登记证", isRequire: true, index: 3, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国药品经营许可证", isRequire: true, index: 4, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国药品经营质量管理规范认证证书(GSP)", isRequire: true, index: 5, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },

        { title: "开发票资料及银行账户信息", isRequire: true, index: 6, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业税票模板", isRequire: true, index: 7, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业出库单模板", isRequire: true, index: 8, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业样章备案", isRequire: true, index: 9, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业基本情况和质量保证体系情况表", isRequire: true, index: 10, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "下游客户法人授权委托书模板", isRequire: true, index: 11, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "药品供需双方质量保证协议(正本)", isRequire: true, index: 12, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "药品供需双方质量保证协议(副本)", isRequire: true, index: 13, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国药品经营许可证副本及变更记录", isRequire: false, index: 14, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国医疗器械经营企业许可证", isRequire: false, index: 15, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国危险化学品经营许可证", isRequire: false, index: 16, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "食品流通许可证", isRequire: false, index: 17, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "药品销售单位首次开户应收集资料", isRequire: false, index: 18, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },

      ]
      this.editVisiable = true
    },
    batchDelete () {
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
          let scmDVendorIds = that.selectedRowKeys.join(',')
          that.$delete('scmDVendor/' + scmDVendorIds).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    exportExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('scmDVendor/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列排序规则
      this.sortedInfo = null
      this.paginationInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams
      })
    },
    fetch (params = {}) {
      this.loading = true
      params.lb = 0
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      if (params.sortField == null) {
        params.sortField = "Create_TIME"
        params.sortOrder = "descend"
      }
      this.$get('scmDVendor', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.loading = false
        this.dataSource = data.rows
        this.pagination = pagination
      })
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
<style>
.editable-cell {
  position: relative;
}

.editable-cell-input-wrapper,
.editable-cell-text-wrapper {
  padding-right: 24px;
}

.editable-cell-text-wrapper {
  padding: 1px 24px 1px 5px;
}

.editable-cell-icon,
.editable-cell-icon-check {
  position: absolute;
  right: 0;
  width: 20px;
  cursor: pointer;
}

.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 21px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}

/* .editable-add-btn {
    margin-bottom: 8px;
  } */
</style>


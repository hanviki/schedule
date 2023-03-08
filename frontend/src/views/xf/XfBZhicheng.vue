<template>
  <div>
    <a-spin :spinning="loading">
         <div>
        <a-form layout="horizontal">
          <a-row>
            <div>
              <a-col :md="8" :sm="24">
                <a-form-item label="发薪号/姓名" v-bind="formItemLayout">
                  <a-input v-model="queryParams.userAccount" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="申报年度" v-bind="formItemLayout">
                  <a-input v-model="queryParams.year" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="岗位等级" v-bind="formItemLayout">
                  <a-select
                    mode="multiple"
                    style="width: 100%"
                    @change="handleChangeSearch"
                  >
                    <a-select-option value="正高"> 正高 </a-select-option>
                    <a-select-option value="副高"> 副高 </a-select-option>
                     <a-select-option value="中级"> 中级 </a-select-option>
                    <a-select-option value="初级"> 初级 </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </div>
            <span style="float: right; margin-top: 3px">
               <a-button type="primary" @click="exportCustomExcel">导出</a-button> 
              <a-button type="primary" @click="search">查询</a-button>
              <!-- <a-button style="margin-left: 8px" @click="reset">重置</a-button> -->
            </span>
          </a-row>
        </a-form>
      </div>
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        bordered
        :scroll="scroll"
      >
        <template
          slot="splitHang"
          slot-scope="text, record"
        >
          <p
            style="width:100%;"
            v-for="item in splitStr(text)"
          >{{item}}</p>
        </template>
     
        
      
      </a-table>
     
     
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import requestDca from "../../utils/requestDca";
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      formItemLayout,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        userAccount: '',
        ks: '正高,副高,中级,初级'
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      userAccount_right: '',
      visibleUserInfo_right: false,
      deaYear: '',
      gwDj:'',
      listAuditInfo: [
        { fieldName: 'ydyf' },
        { fieldName: 'ydyffj' },
        { fieldName: 'zzsc' },
        { fieldName: 'zzscypfj' },
        { fieldName: 'jlsc' },
        { fieldName: 'xsddsc' },
        { fieldName: 'xsddscypfj' },
        { fieldName: 'yyxtsc' },
        { fieldName: 'sfssds' },
        { fieldName: 'sfbsds' },

{ fieldName: 'sfdlwcyjspy' },
{ fieldName: 'pyzlsfyl' },

        { fieldName: 'sftgsdsf' },
        { fieldName: 'sdsfypfj' },
        { fieldName: 'sdsfypfj2' },
       { fieldName: 'ylpfbfz2022' },
        { fieldName: 'ylpfdj2022' },

        { fieldName: 'mzylsgypfj' }
        , { fieldName: 'sfypfjyl' },
        { fieldName: 'hlylpf' },
        { fieldName: 'hlylpfdj' },
        { fieldName: 'hljxpfbfz' },
        { fieldName: 'hljxpfdl' },
        { fieldName: 'hlhlzrypfj' },
        { fieldName: 'sshbdts' },
        { fieldName: 'sshkyxts' },
        { fieldName: 'blxwjf' }, {
          fieldName: 'wfzgszcf'
        },
        { fieldName: 'zypfdjyjxl' },

        { fieldName: 'zypfyjxl' },
        { fieldName: 'zypfbfz58' },
        { fieldName: 'zypfdj59' },
        { fieldName: 'sfyszgzs' },
        { fieldName: 'sfjyhlzgzs' },
        { fieldName: 'xingfscsftg' },
        { fieldName: 'sfczxfypfj61' },
        { fieldName: 'zypf52' },
        { fieldName: 'zypfdj52' },
        //beizhu
        { fieldName: 'beizhumenban' },
        { fieldName: 'beizhuyiwuchu' },
        { fieldName: 'beizhuhuli' },
        // { fieldName: 'ifxjjl' },
        // { fieldName: 'yearmore' }
      ], // 当前用户包含的审核数据
    }
  },
  components: {  },
  props: {
    state: {
      default: 1
    },
    activeKey: {
      default: 1
    }
  },
  mounted () {
    this.fetch2(this.queryParams)
  },
  methods: {
    moment,
    splitStr (text) {
      if (text == null) return ''
      return text.split('#')
    },
    handleChangeSearch(value) {
      if (value != "") {
        this.queryParams.ks = value;
      } else {
        this.queryParams.ks = "正高,副高,中级,初级";
      }
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch2({
        sortField: "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer) ",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
    },
    fetch2 (params = {}) {
      this.loading = true
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
      params.sortField = "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer)   "
      params.sortOrder = "ascend"
      let that = this
      requestDca.get('dcaBReport/dca', {
        state: 2,
        ...params
      }).then((r) => {
        that.loading = false
        let data = r.data
        const pagination = { ...that.pagination }
        pagination.total = data.total
        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList
          // console.info(auditList)
          if (auditList != null && auditList.length > 0) {
            // console.info(this.listAuditInfo)
            that.listAuditInfo.forEach(element2 => {
              //  console.info('element2' + element2)
              let lire = auditList.filter(p => p.auditTitletype == element2.fieldName);
              // console.info(lire);
              if (lire.length > 0) {
                element[element2.fieldName] = lire[0].auditResult
              }
              else {
                element[element2.fieldName] = ''
              }
            });
          }
          else {
            that.listAuditInfo.forEach(element2 => {
              element[element2.fieldName] = ''
            });
          }

        });
        that.dataSource = data.rows
        that.pagination = pagination
      }
      )
    },
    ExportDeclareReport (record) {
      this.$download('dcaBCopyUser/excel', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        gwdj: record.gwdj,//岗位等级
        npPositionName: record.npPositionName,
        sexName: record.gwdj //岗位等级
      }, record.userAccount + ".pdf")
    },
    ExportDocReport (record) {
      this.$download('dcaBCopyUser/doc', {
        userAccount: record.userAccount,
        year: record.year,
        npPositionName: record.npPositionName,
        gwdj: record.gwdj//岗位等级
      }, record.year + '_' + record.userAccount + '_高级职称表' + ".docx")
    },
    ExportAttachReport (record) {
      this.$download('dcaBCopyUser/attach', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        npPositionName: record.npPositionName
      }, record.year + record.userAccount + ".pdf")
    },
      handleBack(record) {
      // let jsonStr = JSON.stringify(record)
      let vRecord = {};
      vRecord.id = record.id;
      vRecord.userAccount = record.userAccount;
      vRecord.state = 0;
      var that = this;
      that.$confirm({
        title: "确定退回所选中的记录?",
        content: "当您点击确定按钮后，此记录将退回待处理状态！",
        centered: true,
        onOk() {
          // vRecord.dcaBAuditdynamicList=''
          that.loading = true;
          that
            .$put("dcaBReport", {
              ...vRecord,
            })
            .then(() => {
              // this.reset()
              that.$message.success("退回成功");
              that.search();
              that.loading = false;
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleSave (record) {

      // let jsonStr = JSON.stringify(record)
      let vRecord = {}
      vRecord.id = record.id
      vRecord.userAccount = record.userAccount
      vRecord.state = 0
      // vRecord.dcaBAuditdynamicList=''
      this.loading = true
      this.$put('dcaBReport', {
        ...vRecord
      }).then(() => {
        // this.reset()
        this.$message.success('保存成功')
        this.search()
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    showUserInfoRight (record) {
      //debugger
      this.visibleUserInfo_right = true
      this.userAccount_right = record.userAccount
      this.deaYear = record.state == 2 ? record.year : ''
       this.gwDj = record.gwdj
    },
    onCloseUserInfoRight () {
      this.visibleUserInfo_right = false
    },
    fetch (userAccount) {
      this.loading = true
      this.queryParams.userAccount = userAccount
      let params = {}
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.paginationInfo.defaultCurrent
      }
      params.sortField = "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer)   "
      params.sortOrder = "ascend"
      params.userAccount = userAccount
      let that = this
      requestDca.get('dcaBReport', {
        state: this.state,
        ...params
      }).then((r) => {
        that.loading = false
        let data = r.data
        const pagination = { ...that.pagination }
        pagination.total = data.total
        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList

          // console.info(auditList)
          if (auditList != null && auditList.length > 0) {
            // console.info(this.listAuditInfo)
            that.listAuditInfo.forEach(element2 => {
              //  console.info('element2' + element2)
              let lire = auditList.filter(p => p.auditTitletype == element2.fieldName);
              // console.info(lire);
              if (lire.length > 0) {
                element[element2.fieldName] = lire[0].auditResult
              }
              else {
                element[element2.fieldName] = ''
              }
            });
          }
          else {
            that.listAuditInfo.forEach(element2 => {
              element[element2.fieldName] = ''
            });
          }


        });
        that.dataSource = data.rows
        that.pagination = pagination
      }
      )
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned INTEGER)  ",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = [
        {
          title: '申报年度',
          dataIndex: 'year',
          width: 100
        },
        
        {
          title: '人事编号6',
          dataIndex: 'userAccount',
          width: 80
        },
        {
          title: '申报等级7',
          dataIndex: 'gwdj'
        },
        {
          title: '科室8',
          dataIndex: 'ks',
        },
       
        {
          title: '员工组',
          dataIndex: 'yuangongzu',
          width: 80
        },
        {
          title: '姓名10',
          dataIndex: 'userAccountName'
        },
        {
          title: '出生年月',
          dataIndex: 'birthdaystr'
        },
        {
          title: '年龄12',
          dataIndex: 'age'
        },
        {
          title: '性别13',
          dataIndex: 'sexName'
        },
        {
          title: '学历(位)14',
          dataIndex: 'edu'
        },
        {
          title: '毕业时间15',
          dataIndex: 'eduDate'
        },

        {
          title: '现职务名称16',
          dataIndex: 'positionName',
          width: 100
        },
        {
          title: '聘任时间17',
          dataIndex: 'zygwDate',
          width: 100
        },

        {
          title: '申报职称18',
          dataIndex: 'npPositionName',
          width: 100
        },
        {
          title: '来院时间19',
          dataIndex: 'schoolDate',
          width: 100
        },

       
        {
          title: '是否存在收受红包的有效投诉94',
          dataIndex: 'sshbdts',
          width: 100,
        },
        {
          title: '是否存在收受回扣的有效投诉95',
          dataIndex: 'sshkyxts',
          width: 120
        },
        {
          title: '是否违反湖北96',
          dataIndex: 'blxwjf',
          width: 200
        },
        {
          title: '是否违反97',
          dataIndex: 'wfzgszcf',
          width: 150
        },
        {
          title: '行风审查是否通过98',
          dataIndex: 'xingfscsftg',
          width: 120,

        },
        {
          title: '是否存在行风建设一99',
          dataIndex: 'sfczxfypfj61',
          width: 150
        },
        {
          title: '联系方式',
          dataIndex: 'telephone',
          width: 100
        }
      ];
      let listj = ['ylpfbfz2022','ylpfdj2022','ydyf', 'ydyffj', 'zzsc', 'zzscypfj', 'jlsc', 'jlscypfj', 'xsddsc', 'xsddscypfj', 'zypfyjxl', 'zypfdjyjxl', 'yyxtsc', 'yyxtypfj', 'zypfbfz58', 'zypfdj59', 'sfssds', 'sfbsds', 'sftgsdsf',
        'sdsfypfj', 'sdsfypfj2', 'mzylsgypfj', 'sfypfjyl', 'sfyszgzs','sfdlwcyjspy','pyzlsfyl',
        'hlylpf', 'hlylpfdj', 'hljxpfbfz', 'hljxpfdl', 'hlhlzrypfj', 'sfjyhlzgzs', 'sshbdts', 'sshkyxts', 'blxwjf', 'wfzgszcf', 'xingfscsftg', 'sfczxfypfj61',
        'zypf52', 'zypfdj52', 'beizhuyiwuchu', 'beizhumenban', 'beizhuhuli'];

      json.forEach(element => {
        if (listj.includes(element.dataIndex)) {
          element["isDynamic"] = 1
        }
      });
      let dataJson = JSON.stringify(json)

      requestDca.export('dcaUserAudit/excelBigTableDca', {
        sortField: "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer) ",
        sortOrder: 'ascend',
        state: 2,
        excelIndex: 0,
        dataJson: dataJson,
        ...this.queryParams
      })
    },
  },
  computed: {
    columns () {

      let clm = [
        {
          title: '申报年度',
          dataIndex: 'year',
          width: 100
        },
        
        {
          title: '人事编号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' },
        },

        {
          title: '申报等级',
          dataIndex: 'gwdj',
          width: 100
        },
        {
          title: '科室',
          dataIndex: 'ks',
          width: 100
        },
       
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '出生年月',
          dataIndex: 'birthdaystr',
          width: 100
        },
        {
          title: '年龄',
          dataIndex: 'age',
          width: 100
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 100
        },
        {
          title: '学历(位)',
          dataIndex: 'edu',
          width: 100
        },
        {
          title: '毕业时间',
          dataIndex: 'eduDate',
          width: 100
        },
        {
          title: '现职务',
          children: [
            {
              title: '现职务名称',
              dataIndex: 'positionName',
              width: 100
            },
            {
              title: '聘任时间',
              dataIndex: 'zygwDate',
              width: 100
            },
          ]
        },

        {
          title: '申报职称',
          dataIndex: 'npPositionName',
          width: 100
        },
        {
          title: '来院时间',
          dataIndex: 'schoolDate',
          width: 100
        },
       
        {
          title: '行风建设办公室',
          children: [
            {
              title: '是否存在收受红包的有效投诉',
              dataIndex: 'sshbdts',
              width: 100,
            },
            {
              title: '是否存在收受回扣的有效投诉',
              dataIndex: 'sshkyxts',
              width: 120
            },
            {
              title: '是否违反湖北省医务人员不良执业行为记分管理办法，并被卫生健康部门一次记12分、6分',
              dataIndex: 'blxwjf',
              width: 200
            },
            {
              title: '是否违反《职工守则》有关行风建设规定并被处罚',
              dataIndex: 'wfzgszcf',
              width: 150
            },
            {
              title: '行风审查是否通过',
              dataIndex: 'xingfscsftg',
              width: 120,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在行风建设一票否决的情况',
              dataIndex: 'sfczxfypfj61',
              width: 150
            },
          ]
        },
       
        {
          title: '联系方式',
          dataIndex: 'telephone',
          width: 100
        }
       


      ]
      // if(this.state==1){
      //   clm.push({
      //     title: '操作',
      //     dataIndex: 'action',
      //     scopedSlots: { customRender: 'action' },

      //     width: 100
      //   })
      // }
      return clm
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
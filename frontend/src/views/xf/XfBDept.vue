<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="7" :sm="24">
              <a-form-item v-bind="formItemLayout" label="科室">
                <a-select
                  v-model="queryParams.deptId"
                  option-filter-prop="children"
                  :filter-option="filterOption"
                  show-search
                >
                  <a-select-option
                    v-for="d in deptData"
                    :key="d.deptId"
                    :value="`${d.deptId}`"
                  >
                    {{ d.deptName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="10" :sm="24">
              <a-form-item label="日期" v-bind="formItemLayout">
                <a-date-picker
                  @change="onfzDateFromChange"
                  style="width: 48%"
                />-
                <a-date-picker @change="onfzDateToChange" style="width: 48%" />
              </a-form-item>
            </a-col>

           
          </div>
          <span style="float: right; margin-top: 3px">
            <a-button type="primary" @click="ExportDeptReport">生成行风建设报告</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
    
    </div>

    
  </a-card>
</template>

<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};
export default {
  name: "SdlBUser",
  components: { },
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
    
      queryParams: {
        
      },
      
      loading: false,
      bordered: true,
      deptData: [],
    };
  },
  computed: {
    
  },
  mounted() {
    this.fetchDept();
  },
  methods: {
   
    fetchDept() {
      this.$get("sdlBUser/deptNew", {}).then((res) => {
        this.deptData = [];
        this.deptData.push({
          deptId: "-1",
          deptName: "全部",
        });
        console.info(res);
        if (res.data[0] != null) {
          this.deptData.push(...res.data);
        }
      });
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    toggleAdvanced() {
      this.advanced = !this.advanced;
      if (!this.advanced) {
        this.queryParams.comments = "";
      }
    },
   
    ExportDeptReport() {
      if(this.queryParams.deptId==''||this.queryParams.deptId==undefined){
        this.$message.warn("请选择科室");
      }
      else {
        let deptName= (this.deptData.filter(p=>p.deptId==this.queryParams.deptId))[0].deptName
      this.$download(
        "xfBUser/deptPdf",
        {
          deptId: this.queryParams.deptId,
          deptName: deptName
        },
        deptName + ".pdf"
      );
      }
    },
    
  },
};
</script>

<style lang="less" scoped>
@import "../../../static/less/Common";
</style>

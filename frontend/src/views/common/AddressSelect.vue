<template>
  <a-cascader
    :disabled="isEdit==0"
    :fieldNames="{ label: 'text', value: 'text', children: 'children' }"
    :options="areaData"
    :placeholder="placeholder"
    v-model="selectedValues"
    @change="onChange"
  />
</template>

<script>
export default {
  name: "areaSelect",
  props: {
    placeholder: {
      type: String,
      default: "请选择省市区",
    },
    defaultValue: {
      type: Array,
      default() {
        return [];
      },
    },
    isEdit: {
      default: 1
    }
  },
  data() {
    return {
      //地区数据
      areaData: [],
      //选择的数据
      selectedValues: [],
    };
  },
  mounted() {
     
  },
  created() {
    if (this.defaultValue.length) {
      this.selectedValues = [...this.defaultValue];
    }
  },
  watch: {
    defaultValue(newValue) {
      if (newValue.length) {
        this.selectedValues = newValue;
      } else {
        this.selectedValues = [];
      }
    },
  },
  methods: {
    setData(){
        this.$get('assetDDictory').then((r) => {
          let data = r.data.rows.children
          console.info(data)
          this.areaData = data.filter(p=>p.type==4)
          console.info(this.areaData)
        });
    },
    //选择好之后的回调
    onChange(value) {
      this.$emit("change", value);
    },
    reset(){
      this.selectedValues = [];
    }
  },
};
</script>

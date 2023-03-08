<template>
  <div>
    <a-select
      v-model="selected"
      @search="handleSearch"
      :filter-option="false"
      show-search
      @change="userChange"
    >
      <a-select-option
        v-for="d2 in userData"
        :key="d2"
        :value="`${d2.userAccount}`"
      >
        {{
          d2.userAccountName +
          " " +
          d2.userAccount +
          " " +
          d2.birthday +
          " " +
          d2.deptNew
        }}
      </a-select-option>
    </a-select>
  </div>
</template>

<script>
export default {
  model: {
    prop: "value",
    event: "change",
  },
  props: {
    value: {
      type: String
    },
  },
  data() {
    return {
      userData: [],
      selected: this.value
    };
  },
  watch: {
    // value: {
    //   deep: true,
    //   immediate: true,
    //   handler: function(val){
    //      if(this.selected==val.key.userAccount){
    //         return ;
    //      }
    //      this.selected = val.key.userAccount;
    //      console.info(val);
    //     // this.fetchUser(val)
    //   },
    // },
  },
  methods: {
    handleSearch(value) {
      this.fetchUser(value);
    },
    fetchUser(userAccount) {
      this.$get("sdlBUser/search", {
        userAccount,
      }).then((r) => {
        this.userData = r.data;
      });
    },
    userChange(value, option) {
      console.info(value);
      this.value =value
      this.$emit("change", option, value);
    },
  },
};
</script>

<style>
</style>
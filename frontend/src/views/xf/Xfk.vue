<template>
  <ComPage
    :tabOptions="tabOptions"
    @setChangeTab="getChangeTab"
  />
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'MdlYwc',
  data () {
    return {
      tabOptions: [
        
      ]
    }
  },
  components: {
    ComPage: () => import('../common/ComPage'),
  },
  computed: {
    ...mapState({
      user: state => state.account.user
    }),
  },
  mounted() {
  },
  methods: {
    getChangeTab(name, item) {
      console.log(name, item)
    },
  },
};
</script>
<style lang="less" scoped>
</style>
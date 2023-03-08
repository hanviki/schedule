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
        // {label: '基本档案', icon: 'solution', path: '/mdl/MdlYwc/BaseInfo'},
        // {label: '管理资质', icon: 'appstore', path: '/mdl/MdlYwc/ManageInfo'},
        // {label: '业务资质管理', icon: 'apartment', path: '/mdl/MdlYwc/AdminShow'},
        // {label: '技术资质管理', icon: 'tool', path: '/mdl/MdlYwc/AdminTecShow'},
        // {label: '不良记分管理', icon: 'snippets', path: '/mdl/MdlYwc/AdminBL'},
        // {label: '应急保障管理', icon: 'alert', path: '/mdl/MdlYwc/MdlBForeign'},
        // {label: '辐射防护安全管理', icon: 'alert', path: '/fs/FsShow'}
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
<template>
  <ComPage
    :class="['mdlYwc']"
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
        {label: '基本档案', icon: 'solution', path: '/mdl/MdlYwc/BaseInfo'},
        {label: '管理资质', icon: 'appstore', path: '/mdl/MdlYwc/ManageInfo'},
        {label: '业务资质管理', icon: 'apartment', path: '/mdl/MdlYwc/AdminShow'},
        {label: '技术资质管理', icon: 'tool', path: '/mdl/MdlYwc/AdminTecShow'},
        {label: '不良记分管理', icon: 'snippets', path: '/mdl/MdlYwc/AdminBL'},
        {label: '应急保障管理', icon: 'alert', path: '/mdl/MdlYwc/MdlBForeign'},
        // {label: '辐射防护安全管理', icon: 'alert', path: '/fs/FsShow'}
      ]
    }
  },
  components: {
    ComPage: () => import('../common/ComPage')
  },
  computed: {
    ...mapState({
      user: state => state.account.user
    }),
    pageBg() {
      return this.$route.query.type == 1 ? 'card-area-bg' : ''
    },
  },

  mounted() {
  },
  methods: {
    getChangeTab(name, item) {
      if (name == 5) {
        this.$router.push({path: item.path, query: {}})
      } else {
        this.$router.push({path: item.path, query: {type: 1}})
      }
    },
  },
};
</script>
<style lang="less" scoped>
.mdlYwc{
  width: 100%;
  height: 100%;
}
</style>
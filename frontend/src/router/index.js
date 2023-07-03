import Vue from 'vue'
import Router from 'vue-router'
import MenuView from '@/views/common/MenuView'
import PageView from '@/views/common/PageView'
import LoginView from '@/views/login/Common'
import CustomList from '@/views/login/CustomList'
import EmptyPageView from '@/views/common/EmptyPageView'
import HomePageView from '@/views/HomePage'
import db from 'utils/localstorage'
import request from 'utils/request'
import * as consts from '@/const'

// 全局Router异常处理
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if (typeof err !== 'undefined') console.log(err)
  })
}
Vue.use(Router)

let constRouter = [{
    path: '/customHan/:id',
    name: '自定义数据',
    component: CustomList
  },
  {
    path: '/login',
    name: '登录页',
    component: LoginView
  },
  {
    path: '/index',
    name: '首页',
    redirect: '/home'
  }
]

let router = new Router({
  routes: constRouter
})

const whiteList = ['/login','/customHan']

let asyncRouter

// 导航守卫，渲染动态路由
router.beforeEach((to, from, next) => {
  if (to.path.indexOf("customHan") > 0) {
    console.info(to.path)
    next()
    next(to.path)
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    }
    let token = db.get('USER_TOKEN')
    let user = db.get('USER')
    let userRouter = get('USER_ROUTER')
    if (token.length && user) {
      if (!asyncRouter) {
        if (!userRouter) {
          request.get(`menu/${user.username}`).then((res) => {
            let dta= res.data;
            if (user.roleId == consts.RoleEnum.YWC || user.roleId == consts.RoleEnum.FSK || user.roleId == consts.RoleEnum.FSKGR || user.roleId == consts.RoleEnum.HFK|| user.roleId == consts.RoleEnum.MYK) {
              dta && dta.forEach(ele => {
                if (ele.name == '主页') {
                  ele.redirect = '/ywc'
                  // ele.children.push(
                  //   {name: '排班管理', path: '/sdl/SdlManage', component: 'sdl/SdlManage'}
                  // )
                  // ele.children.push(
                  //   {name: '辐射防护安全管理', path: '/fs/FsShow', component: 'fs/FsShow'}
                  // )
                  ele.children.push(
                    {name: '医师排班管理', path: '/sdl/Sdl', component: 'sdl/Sdl',
                      children : [
                        {name: '医师排班', path: '/sdl/Sdl/SdlManage', component: 'sdl/SdlManage'}
                      ]
                    }
                  )
                  ele.children.push(
                    {name: '辐射防护安全管理', path: '/fs/Fsk', component: 'fs/Fsk',
                      children : [
                        {name: '辐射防护安全', path: '/fs/FsShow', component: 'fs/FsShow'}
                      ]
                    }
                  )
                  ele.children.push(
                    {name: '科室证件管理', path: '/dept/Dept', component: 'dept/Dept',
                      children : [
                        {name: '科室证件管理', path: '/dept/DeptShow', component: 'dept/DeptShow'}
                      ]
                    }
                  )
                  ele.children.push(
                    {name: '行风建设管理', path: '/xf/Xfk', component: 'xf/Xfk',
                      children : [
                        {name: '行风建设管理', path: '/xf/XfkShow', component: 'xf/XfkShow'}
                      ]
                    }
                  )
                  ele.children.push(
                    {name: '资质管理', path: '/mdl/MdlYwc', component: 'mdl/MdlYwc',
                      children : [
                        {name: '基本档案', path: '/mdl/MdlYwc/BaseInfo', component: 'mdl/BaseInfo'},
                        {name: '资质管理', path: '/mdl/MdlYwc/ManageInfo', component: 'mdl/MdlBManager/ManageInfo'},
                        {name: '业务资质管理', path: '/mdl/MdlYwc/AdminShow', component: 'mdl/AdminShow'},
                        {name: '技术资质管理', path: '/mdl/MdlYwc/AdminTecShow', component: 'mdl/AdminTecShow'},
                        {name: '不良记分管理', path: '/mdl/MdlYwc/AdminBL', component: 'mdl/AdminBL'},
                        {name: '应急保障管理', path: '/mdl/MdlYwc/MdlBForeign', component: 'mdl/MdlBForeign'}
                      ]
                    }
                  )
                }
              });
            }
           
            if (user.roleId == consts.RoleEnum.GR) {
              dta && dta.forEach(ele => {
                if (ele.name == '主页') {
                  ele.redirect = '/personalManagement'
                  ele.children.push(
                    {name: '个人信息查询及维护', path: '/personalManagement', component: 'mdl/PersonalManagement',
                      // children : [
                      //   {name: '个人资料', path: '/mdl/PersonalInfo', component: 'mdl/PersonalInfo'},
                      //   {name: '放射辐射甲状腺屏蔽维护', path: '/mdl/MdlYwc/ManageInfo', component: 'mdl/MdlBManager/ManageInfo'},
                      //   {name: '放射辐射考试记录维护', path: '/fs/FsBExameSingle/FsBExame', component: 'fs/FsBExameSingle/FsBExame'},
                      // ]
                    }
                  )
                }
              })
            }
            if (user.roleId == consts.RoleEnum.KSZR) {
              dta && dta.forEach(ele => {
                if (ele.name == '主页') {
                  ele.redirect = '/FileManagement'
                  ele.children.push(
                    {name: '科室主任管理', path: '/FileManagement', component: 'mdl/FileManagement'}
                  )
                }
              })
            }
            if (user.roleId == consts.RoleEnum.YKYS) {
              dta && dta.forEach(ele => {
                if (ele.name == '主页') {
                  ele.redirect = '/WorkforceManagement'
                  ele.children.push(
                    {name: '排班管理', path: '/WorkforceManagement', component: 'sdl/WorkforceManagement'}
                  )
                }
              })
            }
            
            asyncRouter = dta
            save('USER_ROUTER', asyncRouter)
            go(to, next)
          }).catch(err => {
            console.error(err)
          })
        } else {
          asyncRouter = userRouter
          go(to, next)
        }
      } else {
        next()
      }
    } else {
      next('/login')
    }
  }
})

function go2(to, next) {
  router.addRoutes({
    path: '/customHan',
    name: '自定义数据',
    component: CustomList
  })
  next({
    ...to,
    replace: true
  })
}

function go(to, next) {
  asyncRouter = filterAsyncRouter(asyncRouter)
  router.addRoutes(asyncRouter)
  next({
    ...to,
    replace: true
  })
}

function save(name, data) {
  localStorage.setItem(name, JSON.stringify(data))
}

function get(name) {
  return JSON.parse(localStorage.getItem(name))
}

function filterAsyncRouter(routes) {
  return routes.filter((route) => {
    let component = route.component
    if (component) {
      switch (route.component) {
        case 'MenuView':
          route.component = MenuView
          break
        case 'PageView':
          route.component = PageView
          break
        case 'EmptyPageView':
          route.component = EmptyPageView
          break
        case 'HomePageView':
          route.component = HomePageView
          break
        default:
          route.component = view(component)
      }
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children)
      }
      return true
    }
  })
}

function view(path) {
  return function (resolve) {
    import(`@/views/${path}.vue`).then(mod => {
      resolve(mod)
    })
  }
}

export default router

<template>
  <global-layout @dynamicRouterShow="dynamicRouterShow">
    <contextmenu :itemList="menuItemList" :visible.sync="menuVisible" style="z-index: 9999;" @select="onMenuSelect"/>
    <a-tabs
      @contextmenu.native="e => onContextmenu(e)"
      v-if="multipage"
      :active-key="activePage"
      class="tab-layout-tabs"
      style="height:52px"
      :hide-add="true"
      type="editable-card"
      @change="changePage"
      @tabClick="tabCallBack"
      @edit="editPage">
      <a-tab-pane :id="page.fullPath" :key="page.fullPath" v-for="page in pageList">
        <span slot="tab" :pagekey="page.fullPath">{{ page.meta.title }}</span>
      </a-tab-pane>
    </a-tabs>
    <div style="margin: 4px 4px 0;">
      <transition name="page-toggle">
        <keep-alive v-if="multipage" :include="includedComponents">
          <router-view v-if="reloadFlag"/>
        </keep-alive>
        <template v-else>
          <router-view v-if="reloadFlag"/>
        </template>
      </transition>
    </div>
  </global-layout>
</template>

<script>
  import GlobalLayout from '@/components/page/GlobalLayout'
  import Contextmenu from '@/components/menu/Contextmenu'
  import { mixin, mixinDevice } from '@/utils/mixin.js'
  import { triggerWindowResizeEvent } from '@/utils/util'
  const indexKey = '/dashboard/analysis'
  import Vue from 'vue'
  import { CACHE_INCLUDED_ROUTES } from "@/store/mutation-types"

  export default {
    name: 'TabLayout',
    components: {
      GlobalLayout,
      Contextmenu
    },
    mixins: [mixin, mixinDevice],
    data() {
      return {
        pageList: [],
        linkList: [],
        activePage: '',
        menuVisible: false,
        menuItemList: [
          { key: '4', icon: 'reload', text: '刷 新' },
          { key: '1', icon: 'arrow-left', text: '关闭左侧' },
          { key: '2', icon: 'arrow-right', text: '关闭右侧' },
          { key: '3', icon: 'close', text: '关闭其它' }
        ],
        reloadFlag:true
      }
    },
    provide(){
      return{
        closeCurrent:this.closeCurrent
      }
    },
    computed: {
      multipage() {
        //判断如果是手机模式，自动切换为单页面模式
        if (this.isMobile()) {
          return false
        } else {
          return this.$store.state.app.multipage
        }
      },
      includedComponents() {
        const includedRouters = Vue.ls.get(CACHE_INCLUDED_ROUTES)
        //console.log("includedRouters：" + includedRouters)
        //加入到 cache_included_routes
        if (this.$route.meta.componentName) {
          let cacheRouterArray = Vue.ls.get(CACHE_INCLUDED_ROUTES) || []
          if(!cacheRouterArray.includes(this.$route.meta.componentName)){
            cacheRouterArray.push(this.$route.meta.componentName)
            //console.log("Vue ls set componentName ：" + this.$route.meta.componentName)
            Vue.ls.set(CACHE_INCLUDED_ROUTES, cacheRouterArray)
            //console.log("Vue ls includedRouterArrays ：" + Vue.ls.get(CACHE_INCLUDED_ROUTES))
            return cacheRouterArray;
          }
        }
        return includedRouters;
      }
    },
    created() {
      if (this.$route.path != indexKey) {
        this.addIndexToFirst()
      }
      let storeKey = 'route:title:' + this.$route.fullPath
      let routeTitle = this.$ls.get(storeKey)
      if (routeTitle) {
        this.$route.meta.title = routeTitle
      }
      this.pageList.push(this.$route)
      this.linkList.push(this.$route.fullPath)
      this.activePage = this.$route.fullPath
    },
    mounted() {
    },
    watch: {
      '$route': function(newRoute) {
        // console.log("新的路由",newRoute)
        this.activePage = newRoute.fullPath
        if (!this.multipage) {
          this.linkList = [newRoute.fullPath]
          this.pageList = [Object.assign({},newRoute)]
        } else if(indexKey==newRoute.fullPath) {
          //首页时 直接刷新
        }else if (this.linkList.indexOf(newRoute.fullPath) < 0) {
          this.linkList.push(newRoute.fullPath)
          this.pageList.push(Object.assign({},newRoute))
        } else if (this.linkList.indexOf(newRoute.fullPath) >= 0) {
          let oldIndex = this.linkList.indexOf(newRoute.fullPath)
          let oldPositionRoute = this.pageList[oldIndex]
          this.pageList.splice(oldIndex, 1, Object.assign({},newRoute,{meta:oldPositionRoute.meta}))
          //给菜单id赋值，用于调用之后控制按钮的显示
          Vue.ls.set('funId', oldPositionRoute.meta.id, 7 * 24 * 60 * 60 * 1000)
        }
      },
      'activePage': function(key) {
        let index = this.linkList.lastIndexOf(key)
        let waitRouter = this.pageList[index]
        this.$router.push(Object.assign({},waitRouter));
        this.changeTitle(waitRouter.meta.title)
      },
      'multipage': function(newVal) {
        if(this.reloadFlag){
          if (!newVal) {
            this.linkList = [this.$route.fullPath]
            this.pageList = [this.$route]
          }
        }
      },
      //从单页模式切换回多页模式后首页要居第一位
      device() {
        if (this.multipage && this.linkList.indexOf(indexKey) === -1) {
          this.addIndexToFirst()
        }
      },
    },
    methods: {
      // 将首页添加到第一位
      addIndexToFirst() {
        this.pageList.splice(0, 0, {
          name: '首页',
          path: indexKey,
          fullPath: indexKey,
          meta: {
            icon: 'dashboard',
            title: '首页'
          }
        })
        this.linkList.splice(0, 0, indexKey)
      },
      //动态更改页面标题
      changeTitle(title) {
        let projectTitle = window.SYS_TITLE
        // 首页特殊处理
        if (this.$route.path === indexKey) {
          document.title = projectTitle
        } else {
          document.title = title + ' · ' + projectTitle
        }
      },
      changePage(key) {
        this.activePage = key
      },
      tabCallBack() {
        this.$nextTick(() => {
          triggerWindowResizeEvent()
        })
      },
      editPage(key, action) {
        this[action](key)
      },
      remove(key) {
        if (key == indexKey) {
          this.$message.warning('首页不能关闭!')
          return
        }
        if (this.pageList.length === 1) {
          this.$message.warning('这是最后一页，不能再关闭了啦')
          return
        }
        //console.log("this.pageList ",this.pageList );
        let removeRoute = this.pageList.filter(item => item.fullPath == key)
        this.pageList = this.pageList.filter(item => item.fullPath !== key)
        let index = this.linkList.indexOf(key)
        this.linkList = this.linkList.filter(item => item !== key)
        index = index >= this.linkList.length ? this.linkList.length - 1 : index
        this.activePage = this.linkList[index]
        //update-begin--Author:scott  Date:20201015 for：路由缓存问题，关闭了tab页时再打开就不刷新 #842
        //关闭页面则从缓存cache_included_routes中删除路由，下次点击菜单会重新加载页面
        let cacheRouterArray = Vue.ls.get(CACHE_INCLUDED_ROUTES) || []
        if (removeRoute && removeRoute[0]) {
          let componentName = removeRoute[0].meta.componentName
          //console.log("key: ", key);
          //console.log("componentName: ", componentName);
          if(cacheRouterArray.includes(componentName)){
            cacheRouterArray.splice(cacheRouterArray.findIndex(item => item === componentName), 1)
            Vue.ls.set(CACHE_INCLUDED_ROUTES, cacheRouterArray)
          }
        }
        //update-end--Author:scott  Date:20201015 for：路由缓存问题，关闭了tab页时再打开就不刷新 #842
      },
      onContextmenu(e) {
        const pagekey = this.getPageKey(e.target)
        if (pagekey !== null) {
          e.preventDefault()
          this.menuVisible = true
        }
      },
      getPageKey(target, depth) {
        depth = depth || 0
        if (depth > 2) {
          return null
        }
        let pageKey = target.getAttribute('pagekey')
        pageKey = pageKey || (target.previousElementSibling ? target.previousElementSibling.getAttribute('pagekey') : null)
        return pageKey || (target.firstElementChild ? this.getPageKey(target.firstElementChild, ++depth) : null)
      },
      onMenuSelect(key, target) {
        let pageKey = this.getPageKey(target)
        switch (key) {
          case '1':
            this.closeLeft(pageKey)
            break
          case '2':
            this.closeRight(pageKey)
            break
          case '3':
            this.closeOthers(pageKey)
            break
          case '4':
            this.routeReload()
            break
          default:
            break
        }
      },
      //关闭当前tab页，供子页面调用->望菜单能配置外链，直接弹出新页面而不是嵌入iframe
      closeCurrent(){
        this.remove(this.activePage);
      },
      closeOthers(pageKey) {
        let index = this.linkList.indexOf(pageKey)
        if (pageKey == indexKey || pageKey.indexOf('?ticke=')>=0) {
          this.linkList = this.linkList.slice(index, index + 1)
          this.pageList = this.pageList.slice(index, index + 1)
          this.activePage = this.linkList[0]
        } else {
          let indexContent = this.pageList.slice(0, 1)[0]
          this.linkList = this.linkList.slice(index, index + 1)
          this.pageList = this.pageList.slice(index, index + 1)
          this.linkList.unshift(indexContent.fullPath)
          this.pageList.unshift(indexContent)
          this.activePage = this.linkList[1]
        }
      },
      closeLeft(pageKey) {
        if (pageKey == indexKey) {
          return
        }
        let tempList = [...this.pageList]
        let indexContent = tempList.slice(0, 1)[0]
        let index = this.linkList.indexOf(pageKey)
        this.linkList = this.linkList.slice(index)
        this.pageList = this.pageList.slice(index)
        this.linkList.unshift(indexContent.fullPath)
        this.pageList.unshift(indexContent)
        if (this.linkList.indexOf(this.activePage) < 0) {
          this.activePage = this.linkList[0]
        }
      },
      closeRight(pageKey) {
        let index = this.linkList.indexOf(pageKey)
        this.linkList = this.linkList.slice(0, index + 1)
        this.pageList = this.pageList.slice(0, index + 1)
        if (this.linkList.indexOf(this.activePage < 0)) {
          this.activePage = this.linkList[this.linkList.length - 1]
        }
      },
      //动态路由title显示配置的菜单title而不是其对应路由的title
      dynamicRouterShow(key, id, title, component){
        let keyIndex = this.linkList.indexOf(key)
        if(keyIndex>=0){
          //切换历史页签
          let currRouter = this.pageList[keyIndex]
          let meta = Object.assign({},currRouter.meta,{title:title})
          this.pageList.splice(keyIndex, 1, Object.assign({},currRouter,{meta:meta}))
          this.activePage = key
          if (key === this.activePage) {
            this.changeTitle(title)
          }
        } else {
          //打开新的页签
          if(component) {
            let index = component.lastIndexOf("\/");
            component = component.substring(index + 1, component.length);
          }
          this.pageList.push({
            name: title,
            path: key,
            fullPath: key,
            meta: {
              id: id,
              icon: key,
              title: title,
              componentName: component,
              keepAlive: true
            }
          })
          this.linkList.push(key)
          this.activePage = key
        }
      },
      //路由刷新
      routeReload(){
        this.reloadFlag = false
        let ToggleMultipage = "ToggleMultipage"
        this.$store.dispatch(ToggleMultipage,false)
        this.$nextTick(()=>{
          this.$store.dispatch(ToggleMultipage,true)
          this.reloadFlag = true
        })
      }
    }
  }
</script>

<style lang="less">

  /*
 * by ji-shenghua qq 75-27-18-920
 */

  .page-transition-enter {
    opacity: 0;
  }

  .page-transition-leave-active {
    opacity: 0;
  }

  .page-transition-enter .page-transition-container,
  .page-transition-leave-active .page-transition-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }

  /*美化弹出Tab样式*/
  .ant-tabs-nav-container {
    margin-top: 4px;
  }

  /* 修改 ant-tabs 样式 */
  .tab-layout-tabs.ant-tabs {
    border-bottom: 1px solid #ccc;
    border-left: 1px solid #ccc;
    background-color: white;
    padding: 0 20px;

    .ant-tabs-bar {
      margin: 4px 0 0;
      border: none;
    }

  }

  .ant-tabs {

    &.ant-tabs-card .ant-tabs-tab {

      padding: 0 24px !important;
      background-color: white !important;
      margin-right: 10px !important;

      .ant-tabs-close-x {
        width: 12px !important;
        height: 12px !important;
        opacity: 0 !important;
        cursor: pointer !important;
        font-size: 12px !important;
        margin: 0 !important;
        position: absolute;
        top: 36%;
        right: 6px;
      }

      &:hover .ant-tabs-close-x {
        opacity: 1 !important;
      }

    }

  }

  .ant-tabs.ant-tabs-card > .ant-tabs-bar {
    .ant-tabs-tab {
      border: none !important;
      border-bottom: 1px solid transparent !important;
    }
    .ant-tabs-tab-active {
      border-color: @primary-color!important;
    }
  }

  .main>.ant-card>.ant-card-body {
    padding: 8px 24px;
  }

</style>
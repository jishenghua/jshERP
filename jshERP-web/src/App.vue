<template>
  <a-config-provider :locale="locale">
    <div id="app">
      <router-view/>
    </div>
  </a-config-provider>
</template>
<script>
  import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
  import enquireScreen from '@/utils/device'

  export default {
    data () {
      return {
        locale: zhCN,
      }
    },
    created () {
      let that = this
      enquireScreen(deviceType => {
        // tablet
        if (deviceType === 0) {
          that.$store.commit('TOGGLE_DEVICE', 'mobile')
          that.$store.dispatch('setSidebar', false)
        }
        // mobile
        else if (deviceType === 1) {
          that.$store.commit('TOGGLE_DEVICE', 'mobile')
          that.$store.dispatch('setSidebar', false)
        }
        else {
          that.$store.commit('TOGGLE_DEVICE', 'desktop')
          that.$store.dispatch('setSidebar', true)
        }
      })
      //设置本地语种（当前网页的语种）。如果不设置，默认就是 'chinese_simplified' 简体中文
      translate.language.setLocal('chinese_simplified');
      translate.service.use('client.edge');
      //翻译时追加上自己想忽略不进行翻译的id的值，凡是在这里面的，都不进行翻译。
      translate.ignore.id.push('langSetting');
      //开启html页面变化的监控，对变化部分会进行自动翻译。注意，这里变化区域，是指使用 translate.setDocuments(...) 设置的区域。如果未设置，那么为监控整个网页的变化
      translate.listener.start();
      //执行翻译初始化操作，显示出select语言选择
      translate.execute();
      //不显示语言选择标签
      translate.selectLanguageTag.show = false
      //翻译自定义
      translate.nomenclature.append('chinese_simplified','english',`
					管伊佳ERP=GuanYiJia
			`)
    }
  }
</script>
<style>
  #app {
    height: 100%;
  }
</style>
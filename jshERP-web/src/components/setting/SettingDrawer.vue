<template>
  <div class="setting-drawer">
    <a-drawer
      width="300"
      placement="right"
      :closable="false"
      @close="onClose"
      :visible="visible"
      :style="{}"
    >
      <div class="setting-drawer-index-content">

        <div :style="{ marginBottom: '24px' }">
          <h3 class="setting-drawer-index-title">整体风格设置</h3>

          <div class="setting-drawer-index-blockChecbox">
            <a-tooltip>
              <template slot="title">
                暗色菜单风格
              </template>
              <div class="setting-drawer-index-item" @click="handleMenuTheme('dark')">
                <img src="../../assets/dark.svg" alt="dark">
                <div class="setting-drawer-index-selectIcon" v-if="navTheme === 'dark'">
                  <a-icon type="check"/>
                </div>
              </div>
            </a-tooltip>

            <a-tooltip>
              <template slot="title">
                亮色菜单风格
              </template>
              <div class="setting-drawer-index-item" @click="handleMenuTheme('light')">
                <img src="../../assets/light.svg" alt="light">
                <div class="setting-drawer-index-selectIcon" v-if="navTheme !== 'dark'">
                  <a-icon type="check"/>
                </div>
              </div>
            </a-tooltip>
          </div>
        </div>

        <div :style="{ marginBottom: '24px' }">
          <h3 class="setting-drawer-index-title">主题色</h3>

          <div style="height: 20px">
            <a-tooltip class="setting-drawer-theme-color-colorBlock" v-for="(item, index) in colorList" :key="index">
              <template slot="title">
                {{ item.key }}
              </template>
              <a-tag :color="item.color" @click="changeColor(item.color)">
                <a-icon type="check" v-if="item.color === primaryColor"></a-icon>
              </a-tag>
            </a-tooltip>

          </div>
        </div>
        <a-divider />

        <div :style="{ marginBottom: '24px' }">
          <div :style="{ marginTop: '24px' }">
            <a-list :split="false">
              <a-list-item>
                <a-switch slot="actions" size="small" :defaultChecked="fixedHeader" @change="handleFixedHeader" />
                <a-list-item-meta>
                  <div slot="title">固定 Header</div>
                </a-list-item-meta>
              </a-list-item>
              <a-list-item>
                <a-switch slot="actions" size="small" :disabled="!fixedHeader" :defaultChecked="autoHideHeader" @change="handleFixedHeaderHidden" />
                <a-list-item-meta>
                  <div slot="title" :style="{ textDecoration: !fixedHeader ? 'line-through' : 'unset' }">下滑时隐藏 Header</div>
                </a-list-item-meta>
              </a-list-item>
              <a-list-item >
                <a-switch slot="actions" size="small" :disabled="(layoutMode === 'topmenu')" :checked="fixSiderbar" @change="handleFixSiderbar" />
                <a-list-item-meta>
                  <div slot="title" :style="{ textDecoration: layoutMode === 'topmenu' ? 'line-through' : 'unset' }">固定侧边菜单</div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </div>
        <a-divider />

        <div :style="{ marginBottom: '24px' }">
          <h3 class="setting-drawer-index-title">语言设置</h3>
          <div style="padding: 10px 0 50px 0">
            <div style="float:left; width: 40%; line-height: 30px">{{langSetTitle}}</div>
            <div style="float:left; width: 60%" id="langSetting">
              <a-select placeholder="请选择语言" showSearch optionFilterProp="children" :value="currentLang" @change="handleChangeLang" style="width:150px">
                <a-select-option value="chinese_simplified">简体中文</a-select-option>
                <a-select-option value="chinese_traditional">繁體中文</a-select-option>
                <a-select-option value="english">English</a-select-option>
                <a-select-option value="korean">한국어</a-select-option>
                <a-select-option value="japanese">しろうと</a-select-option>
                <a-select-option value="vietnamese">Tiếng Việt</a-select-option>
                <a-select-option value="hindi">हिन्दी</a-select-option>
                <a-select-option value="russian">Русский язык</a-select-option>
                <a-select-option value="french">Français</a-select-option>
                <a-select-option value="ukrainian">УкраїнськаName</a-select-option>
                <a-select-option value="norwegian">Norge</a-select-option>
                <a-select-option value="welsh">color name</a-select-option>
                <a-select-option value="dutch">nederlands</a-select-option>
                <a-select-option value="filipino">Pilipino</a-select-option>
                <a-select-option value="lao">ກະຣຸນາ</a-select-option>
                <a-select-option value="telugu">తెలుగుQFontDatabase</a-select-option>
                <a-select-option value="romanian">Română</a-select-option>
                <a-select-option value="nepali">नेपालीName</a-select-option>
                <a-select-option value="haitian_creole">Kreyòl ayisyen</a-select-option>
                <a-select-option value="czech">český</a-select-option>
                <a-select-option value="swedish">Svenska</a-select-option>
                <a-select-option value="malagasy">Malagasy</a-select-option>
                <a-select-option value="burmese">ဗာရမ်</a-select-option>
                <a-select-option value="pashto">پښتوName</a-select-option>
                <a-select-option value="thai">คนไทย</a-select-option>
                <a-select-option value="armenian">Արմենյան</a-select-option>
                <a-select-option value="persian">Persian</a-select-option>
                <a-select-option value="kurdish">Kurdî</a-select-option>
                <a-select-option value="turkish">Türkçe</a-select-option>
                <a-select-option value="bulgarian">български</a-select-option>
                <a-select-option value="malay">Malay</a-select-option>
                <a-select-option value="swahili">Kiswahili</a-select-option>
                <a-select-option value="oriya">ଓଡିଆ</a-select-option>
                <a-select-option value="icelandic">ÍslandName</a-select-option>
                <a-select-option value="irish">Íris</a-select-option>
                <a-select-option value="khmer">ខ្មែរKCharselect unicode block name</a-select-option>
                <a-select-option value="gujarati">ગુજરાતી</a-select-option>
                <a-select-option value="slovak">Slovenská</a-select-option>
                <a-select-option value="kannada">ಕನ್ನಡ್Name</a-select-option>
                <a-select-option value="hebrew">היברית</a-select-option>
                <a-select-option value="hungarian">magyar</a-select-option>
                <a-select-option value="marathi">मराठीName</a-select-option>
                <a-select-option value="tamil">தாமில்</a-select-option>
                <a-select-option value="estonian">eesti keel</a-select-option>
                <a-select-option value="malayalam">മലമാലം</a-select-option>
                <a-select-option value="inuktitut">ᐃᓄᒃᑎᑐᑦ</a-select-option>
                <a-select-option value="arabic">بالعربية</a-select-option>
                <a-select-option value="deutsch">Deutsch</a-select-option>
                <a-select-option value="slovene">slovenščina</a-select-option>
                <a-select-option value="bengali">বেঙ্গালী</a-select-option>
                <a-select-option value="urdu">اوردو</a-select-option>
                <a-select-option value="azerbaijani">azerbaijani</a-select-option>
                <a-select-option value="portuguese">português</a-select-option>
                <a-select-option value="samoan">lifiava</a-select-option>
                <a-select-option value="afrikaans">afrikaans</a-select-option>
                <a-select-option value="tongan">汤加语</a-select-option>
                <a-select-option value="greek">ελληνικά</a-select-option>
                <a-select-option value="indonesian">IndonesiaName</a-select-option>
                <a-select-option value="spanish">Español</a-select-option>
                <a-select-option value="danish">dansk</a-select-option>
                <a-select-option value="amharic">amharic</a-select-option>
                <a-select-option value="punjabi">ਪੰਜਾਬੀName</a-select-option>
                <a-select-option value="albanian">albanian</a-select-option>
                <a-select-option value="lithuanian">Lietuva</a-select-option>
                <a-select-option value="italian">italiano</a-select-option>
                <a-select-option value="maltese">Malti</a-select-option>
                <a-select-option value="finnish">suomi</a-select-option>
                <a-select-option value="catalan">català</a-select-option>
                <a-select-option value="croatian">hrvatski</a-select-option>
                <a-select-option value="bosnian">bosnian</a-select-option>
                <a-select-option value="polish">Polski</a-select-option>
                <a-select-option value="latvian">latviešu</a-select-option>
                <a-select-option value="maori">Maori</a-select-option>
              </a-select>
            </div>
          </div>
        </div>
        <a-divider />

        <div :style="{ marginBottom: '24px' }">
          <h3 class="setting-drawer-index-title">其他设置</h3>
          <div>
            <a-list :split="false">
              <a-list-item>
                <a-switch slot="actions" size="small" :defaultChecked="colorWeak" @change="onColorWeak" />
                <a-list-item-meta>
                  <div slot="title">色弱模式</div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </div>
      </div>
      <div class="setting-drawer-index-handle" @click="toggle" v-if="visible">
        <a-icon type="close" />
      </div>
    </a-drawer>
  </div>
</template>

<script>
  import DetailList from '@/components/tools/DetailList'
  import SettingItem from '@/components/setting/SettingItem'
  import config from '@/defaultSettings'
  import { updateTheme, updateColorWeak, colorList } from '@/components/tools/setting'
  import { mixin, mixinDevice } from '@/utils/mixin.js'
  import { triggerWindowResizeEvent } from '@/utils/util'

  export default {
    components: {
      DetailList,
      SettingItem
    },
    mixins: [mixin, mixinDevice],
    data() {
      return {
        visible: true,
        langSetTitle: '选择语言',
        currentLang: '',
        colorList
      }
    },
    watch: {

    },
    mounted () {
      const vm = this
      setTimeout(() => {
        vm.visible = false
      }, 16)
      // 当主题色不是默认色时，才进行主题编译
      if (this.primaryColor !== config.primaryColor) {
        updateTheme(this.primaryColor)
      }
      if (this.colorWeak !== config.colorWeak) {
        updateColorWeak(this.colorWeak)
      }
      if (this.multipage !== config.multipage) {
        this.$store.dispatch('ToggleMultipage', this.multipage)
      }
    },
    created () {
      this.currentLang = translate.language.getCurrent()
    },
    methods: {
      showDrawer() {
        this.visible = true
      },
      onClose() {
        this.visible = false
      },
      toggle() {
        this.visible = !this.visible
      },
      onColorWeak (checked) {
        this.$store.dispatch('ToggleWeak', checked)
        updateColorWeak(checked)
      },
      onMultipageWeak (checked) {
        this.$store.dispatch('ToggleMultipage', checked)
      },
      handleMenuTheme (theme) {
        this.$store.dispatch('ToggleTheme', theme)
      },
      handleLayout (mode) {
        this.$store.dispatch('ToggleLayoutMode', mode)
        // 因为顶部菜单不能固定左侧菜单栏，所以强制关闭
        this.handleFixSiderbar(this.fixSiderbar)
        // 触发窗口resize事件
        triggerWindowResizeEvent()
      },
      handleContentWidthChange (type) {
        this.$store.dispatch('ToggleContentWidth', type)
      },
      changeColor (color) {
        if (this.primaryColor !== color) {
          this.$store.dispatch('ToggleColor', color)
          updateTheme(color)
        }
      },
      handleFixedHeader (fixed) {
        this.$store.dispatch('ToggleFixedHeader', fixed)
      },
      handleFixedHeaderHidden (autoHidden) {
        this.$store.dispatch('ToggleFixedHeaderHidden', autoHidden)
      },
      handleFixSiderbar (fixed) {
        this.fixSiderbar = fixed
        this.$store.dispatch('ToggleFixSiderbar', fixed)
      },
      handleChangeLang(value) {
        translate.changeLanguage(value)
        this.currentLang = translate.language.getCurrent()
      }
    },
  }
</script>

<style lang="less" scoped>

  .setting-drawer-index-content {

    .setting-drawer-index-blockChecbox {
      display: flex;

      .setting-drawer-index-item {
        margin-right: 16px;
        position: relative;
        border-radius: 4px;
        cursor: pointer;

        img {
          width: 48px;
        }

        .setting-drawer-index-selectIcon {
          position: absolute;
          top: 0;
          right: 0;
          width: 100%;
          padding-top: 15px;
          padding-left: 24px;
          height: 100%;
          color: #1890ff;
          font-size: 14px;
          font-weight: 700;
        }
      }
    }
    .setting-drawer-theme-color-colorBlock {
      width: 20px;
      height: 20px;
      border-radius: 2px;
      float: left;
      cursor: pointer;
      margin-right: 8px;
      padding-left: 0px;
      padding-right: 0px;
      text-align: center;
      color: #fff;
      font-weight: 700;

      i {
        font-size: 14px;
      }
    }
  }

  .setting-drawer-index-handle {
    position: absolute;
    top: 240px;
    background: #1890ff;
    width: 48px;
    height: 48px;
    right: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    pointer-events: auto;
    z-index: 1001;
    text-align: center;
    font-size: 16px;
    border-radius: 4px 0 0 4px;

    i {
      color: rgb(255, 255, 255);
      font-size: 20px;
    }
  }
</style>
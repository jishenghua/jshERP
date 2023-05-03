<template>
  <div>
    <a-input-group v-if="kind === 'material'" compact style="width:100%">
      <a-select placeholder="输入条码或名称" :dropdownMatchSelectWidth="false" showSearch :showArrow="false"
                v-model="names" optionFilterProp="children" :style="searchWidth"
                @search="handleSearch" @change="handleChange">
        <div slot="dropdownRender" slot-scope="menu">
          <v-nodes :vnodes="menu" />
          <a-divider v-if="materialData.length===20" style="margin: 4px 0;" />
          <div v-if="materialData.length===20" style="padding: 4px 8px; cursor: pointer;"
               @mousedown="e => e.preventDefault()">此处最多显示20条，如需更多请点击放大镜查询</div>
        </div>
        <a-select-option v-for="item in materialData" :key="item.barCode">
          {{ item.materialStr }}
        </a-select-option>
      </a-select>
      <a-button icon="search" @click="onSearch" />
    </a-input-group>
    <a-input-search v-if="kind === 'batch'||kind === 'sn'||kind === 'snAdd'" v-model="names" placeholder="请点开弹窗" readOnly @search="onSearch"></a-input-search>
    <j-select-material-modal v-if="kind === 'material'" ref="selectModal" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
    <j-select-batch-modal v-if="kind === 'batch'" ref="selectModal" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
    <j-select-sn-modal v-if="kind === 'sn'" ref="selectModal" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
    <j-select-sn-add-modal v-if="kind === 'snAdd'" ref="selectModal" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
  </div>
</template>

<script>
  import JSelectMaterialModal from './modal/JSelectMaterialModal'
  import JSelectBatchModal from './modal/JSelectBatchModal'
  import JSelectSnModal from './modal/JSelectSnModal'
  import JSelectSnAddModal from './modal/JSelectSnAddModal'
  import { getMaterialByParam } from '@/api/api'

  export default {
    name: 'JSelectList',
    components: {
      JSelectMaterialModal, JSelectBatchModal, JSelectSnModal, JSelectSnAddModal,
      VNodes: {
        functional: true,
        render: (h, ctx) => ctx.props.vnodes,
      }
    },
    props: {
      value: {
        type: String,
        required: false
      },
      disabled: {
        type: Boolean,
        required: false,
        default: false
      },
      rows: {
        type: String,
        required: false
      },
      kind: {
        type: String,
        required: false
      },
      multi: {
        type: Boolean,
        default: true,
        required: false
      }
    },
    data() {
      return {
        ids: "",
        names: "",
        materialData: [],
        setTimeFlag: null,
        searchWidth: ""
      }
    },
    mounted() {
      this.ids = this.value
    },
    watch: {
      value(val) {
        this.ids = val
      }
    },
    created () {
      const currentWidth = window.screen.width
      if(currentWidth<1500) {
        this.searchWidth = 'width:75%'
      } else {
        this.searchWidth = 'width:81%'
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    methods: {
      initComp(name) {
        this.names = name ? name : undefined
      },
      onSearch() {
        this.$refs.selectModal.showModal()
      },
      handleSearch(value) {
        let that = this
        if(this.setTimeFlag != null){
          clearTimeout(this.setTimeFlag);
        }
        this.setTimeFlag = setTimeout(()=>{
          getMaterialByParam({q: value}).then((res) => {
            if (res && res.code === 200) {
              that.materialData = res.data
            }
          })
        },500)
      },
      handleChange(value) {
        this.$emit("change", value)
      },
      selectOK(rows, idstr) {
        console.log("选中id", idstr)
        if (!rows) {
          this.ids = ''
        } else {
          this.ids = idstr
        }
        this.$emit("change", this.ids)
      }
    }
  }
</script>

<style scoped>

</style>
<template>
  <div>
    <a-input-search v-if="kind === 'material'" v-model="names" placeholder="请选择" @pressEnter="onPressEnter" @search="onSearch"></a-input-search>
    <a-input-search v-if="kind === 'batch'||kind === 'sn'" v-model="names" placeholder="请选择" readOnly @search="onSearch"></a-input-search>
    <j-select-material-modal v-if="kind === 'material'" ref="selectModal" :modal-width="modalWidth" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
    <j-select-batch-modal v-if="kind === 'batch'" ref="selectModal" :modal-width="modalWidth" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
    <j-select-sn-modal v-if="kind === 'sn'" ref="selectModal" :modal-width="modalWidth" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
  </div>
</template>

<script>
  import JSelectMaterialModal from './modal/JSelectMaterialModal'
  import JSelectBatchModal from './modal/JSelectBatchModal'
  import JSelectSnModal from './modal/JSelectSnModal'
  import { getMpListShort } from "@/utils/util"
  import {getMaterialByBarCode} from '@/api/api'
  import Vue from 'vue'

  export default {
    name: 'JSelectList',
    components: {JSelectMaterialModal, JSelectBatchModal, JSelectSnModal},
    props: {
      modalWidth: {
        type: Number,
        default: 1450,
        required: false
      },
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
    model: {
      prop: 'value',
      event: 'change'
    },
    methods: {
      initComp(name) {
        this.names = name
      },
      onPressEnter() {
        if(this.kind === 'material') {
          let param = {
            barCode: this.names,
            mpList: getMpListShort(Vue.ls.get('materialPropertyList')),  //扩展属性
            prefixNo: this.prefixNo
          }
          getMaterialByBarCode(param).then((res) => {
            if (res && res.code === 200) {
              let mList = res.data
              if(mList && mList.length === 1) {
                //如果条码可以查到商品，则直接加载，不用弹窗再选择
                this.$emit("change", this.names)
              } else {
                //匹配不到进行弹窗
                this.$refs.selectModal.showModal(this.names)
              }
            }
          })
        } else {
          this.$refs.selectModal.showModal()
        }
      },
      onSearch() {
        if(this.kind === 'material') {
          //直接进行弹窗
          this.$refs.selectModal.showModal(this.names)
        } else {
          this.$refs.selectModal.showModal()
        }
      },
      selectOK(rows, idstr) {
        console.log("选中id", idstr)
        if (!rows) {
          this.ids = ''
        } else {
          this.names = idstr
          this.ids = idstr
        }
        this.$emit("change", this.ids)
      }
    }
  }
</script>

<style scoped>

</style>
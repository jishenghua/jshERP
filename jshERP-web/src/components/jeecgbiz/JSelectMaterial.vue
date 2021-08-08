<template>
  <div>
    <a-input-search
      v-model="materialNames"
      placeholder="请选择商品"
      readOnly
      @search="onSearchMaterial">
    </a-input-search>
    <j-select-material-modal ref="selectModal" :modal-width="modalWidth" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
  </div>
</template>

<script>
  import JSelectMaterialModal from './modal/JSelectMaterialModal'

  export default {
    name: 'JSelectMaterial',
    components: {JSelectMaterialModal},
    props: {
      modalWidth: {
        type: Number,
        default: 1200,
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
      multi: {
        type: Boolean,
        default: true,
        required: false
      }
    },
    data() {
      return {
        materialIds: "",
        materialNames: "",
      }
    },
    mounted() {
      this.materialIds = this.value
    },
    watch: {
      value(val) {
        this.materialIds = val
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    methods: {
      initComp(barCode) {
        this.materialNames = barCode
      },
      onSearchMaterial() {
        this.$refs.selectModal.showModal()
      },
      selectOK(rows, idstr) {
        console.log("选中商品", rows)
        console.log("选中商品id", idstr)
        if (!rows) {
          this.materialNames = ''
          this.materialIds = ''
        } else {
          let temp = ''
          for (let item of rows) {
            temp += ',' + item.mBarCode
          }
          this.materialNames = temp.substring(1)
          this.materialIds = idstr
        }
        this.$emit("change", this.materialIds)
      }
    }
  }
</script>

<style scoped>

</style>
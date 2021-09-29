<template>
  <div>
    <a-input-search
      v-model="names"
      placeholder="请选择"
      readOnly
      @search="onSearch">
    </a-input-search>
    <j-select-material-modal v-if="kind === 'material'" ref="selectModal" :modal-width="modalWidth" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
    <j-select-batch-modal v-if="kind === 'batch'" ref="selectModal" :modal-width="modalWidth" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
    <j-select-sn-modal v-if="kind === 'sn'" ref="selectModal" :modal-width="modalWidth" :rows="rows" :multi="multi" :bar-code="value" @ok="selectOK" @initComp="initComp"/>
  </div>
</template>

<script>
  import JSelectMaterialModal from './modal/JSelectMaterialModal'
  import JSelectBatchModal from './modal/JSelectBatchModal'
  import JSelectSnModal from './modal/JSelectSnModal'

  export default {
    name: 'JSelectList',
    components: {JSelectMaterialModal, JSelectBatchModal, JSelectSnModal},
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
      onSearch() {
        this.$refs.selectModal.showModal()
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
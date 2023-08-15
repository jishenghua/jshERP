<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="width"
      :visible="visible"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      :style="modalStyle"
      @cancel="handleCancel"
      cancelText="关闭">
      <template slot="footer">
        <a-button key="back" @click="handleCancel">取消</a-button>
      </template>
      <a-form :form="form">
        <template>
          <iframe :src="billExcelUrl" width="100%" :height="height" frameborder="0" scrolling="no"></iframe>
        </template>
        <template>
          <a-row>
            <a-col>
              <a-form-item>
                <a-input v-decorator="['id']" hidden/>
              </a-form-item>
            </a-col>
          </a-row>
        </template>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
  import pick from 'lodash.pick'
  import {mixinDevice} from '@/utils/mixin'
  export default {
    name: 'BillExcelIframe',
    mixins: [mixinDevice],
    data () {
      return {
        title: "单据导出",
        width: '500px',
        visible: false,
        modalStyle: '',
        billExcelUrl: '',
        height: "",
        model: {},
        form: this.$form.createForm(this),
        loading: false
      }
    },
    created () {
    },
    methods: {
      show(record, billExcelUrl, height) {
        this.height = height
        this.billExcelUrl = billExcelUrl
        this.visible = true
        this.modalStyle = 'top:20%; height: 400px;'
        this.model = Object.assign({}, record)
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id'))
        });
      },
      handleCancel() {
        this.close()
      },
      close() {
        this.billExcelUrl = ''
        this.$emit('close')
        this.visible = false
        this.modalStyle = ''
      }
    }
  }
</script>

<style scoped>

</style>
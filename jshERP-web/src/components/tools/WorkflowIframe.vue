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
          <iframe :src="sendWorkflowUrl" width="100%" :height="height" frameborder="0" scrolling="no"></iframe>
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
    name: 'WorkflowIframe',
    mixins: [mixinDevice],
    data () {
      return {
        title: "发起流程",
        width: '500px',
        visible: false,
        modalStyle: '',
        sendWorkflowUrl: '',
        height: "",
        model: {},
        form: this.$form.createForm(this),
        loading: false
      }
    },
    created () {
    },
    methods: {
      show(record, sendWorkflowUrl, height) {
        this.height = height
        this.sendWorkflowUrl = sendWorkflowUrl
        this.visible = true
        this.modalStyle = 'top:20%; height: 50%;'
        this.model = Object.assign({}, record)
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id'))
        });
      },
      handleCancel() {
        this.close()
      },
      close() {
        this.sendWorkflowUrl = ''
        this.$emit('close')
        this.visible = false
        this.modalStyle = ''
      }
    }
  }
</script>

<style scoped>

</style>
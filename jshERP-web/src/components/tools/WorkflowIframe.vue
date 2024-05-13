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
        <a-button type="primary" :loading="loading" @click="handleSubmit">确认提交</a-button>
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
  import { postAction } from '@api/manage'
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
        loading: false,
      }
    },
    created () {
    },
    methods: {
      show(record, sendWorkflowUrl, billNo, type, height) {
        this.height = height
        this.sendWorkflowUrl = sendWorkflowUrl
        //单号
        this.billNo = billNo
        //1-进销存单据 2-财务单据 3-生产单据
        this.bigType = type
        this.visible = true
        this.modalStyle = 'top:20%; height: 55%;'
        this.model = Object.assign({}, record)
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id'))
        });
      },
      handleSubmit() {
        const that = this
        let formData = {}
        formData.bigType = this.bigType
        formData.billNo = this.billNo
        that.loading = true
        postAction('/api/plugin/workflow/workflowTask/add', formData).then((res)=>{
          if(res.code === 200){
            that.$message.success('提交成功！')
            that.close()
            setTimeout(function (){
              that.$emit('ok')
            },2000)
          }else{
            that.$message.warning(res.data.message)
          }
        }).finally(() => {
          that.loading = false
        })
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
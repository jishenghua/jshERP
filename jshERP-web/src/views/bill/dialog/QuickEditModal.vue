NEW_FILE_CODE
<template>
  <a-modal
    :title="title"
    :width="600"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    okText="保存"
    cancelText="取消">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="单据编号" :labelCol="{span: 5}" :wrapperCol="{span: 18}">
          <a-input v-decorator="['number']" :readOnly="true" />
        </a-form-item>
        <a-form-item label="备注" :labelCol="{span: 5}" :wrapperCol="{span: 18}">
          <a-textarea
            v-decorator="['remark']"
            :rows="4"
            placeholder="请输入备注" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { quickEditDepotHead } from '@/api/api'
import pick from 'lodash.pick'

export default {
  name: 'QuickEditModal',
  data() {
    return {
      title: '快捷编辑备注',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      model: {}
    }
  },
  methods: {
    show(record) {
      this.visible = true
      this.model = Object.assign({}, record)
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, ['number', 'remark']))
      })
    },
    handleOk() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.confirmLoading = true
          let params = Object.assign({}, this.model, values)
          quickEditDepotHead(params).then((res) => {
            if (res.code === 200) {
              this.$message.success('保存成功')
              this.close()
            } else {
              this.$message.warning(res.message)
            }
          }).catch(() => {
            this.$message.error('保存失败')
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      })
    },
    close() {
      this.form.resetFields()
      this.visible = false
      this.$emit('close')
    },
    handleCancel () {
      this.close()
    },
  }
}
</script>

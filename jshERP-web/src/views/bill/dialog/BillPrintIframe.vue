<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 100%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" @click="handleCancel">取消</a-button>
    </template>
    <a-form :form="form">
      <template>
        <iframe :src="billPrintUrl" width="100%" :height="height" frameborder="0" scrolling="no"></iframe>
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
</template>

<script>
  import pick from 'lodash.pick'
  export default {
    name: 'BillPrintIframe',
    data () {
      return {
        title: "三联打印预览",
        width: '1550px',
        visible: false,
        billPrintUrl: '',
        height: "",
        model: {},
        form: this.$form.createForm(this),
        loading: false
      }
    },
    created () {
    },
    methods: {
      show(record, billPrintUrl, billPrintHeight) {
        this.height = billPrintHeight
        this.billPrintUrl = billPrintUrl
        this.visible = true;
        this.model = Object.assign({}, record);
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id'))
        });
      },
      handleCancel() {
        this.close()
      },
      close() {
        this.$emit('close');
        this.visible = false;
      }
    }
  }
</script>

<style scoped>

</style>
<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="500"
      :visible="visible"
      :confirmLoading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'89px','left':'151px'}"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭"
      wrapClassName="ant-modal-cust-warp"
      style="top:30%;height: 35%;overflow-y: hidden">
      <template slot="footer">
        <a-button key="back" v-if="isReadOnly" @click="handleCancel">
          关闭
        </a-button>
      </template>
      <a-spin :spinning="confirmLoading">
        <a-form :form="form" id="batchSetDepot">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="仓库名称">
            <a-select placeholder="请选择仓库" v-decorator="[ 'depotId', validatorRules.depotId ]" showSearch optionFilterProp="children">
              <a-select-option v-for="(depot,index) in depotList" :value="depot.id">
                {{ depot.depotName }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  export default {
    name: "BatchSetDepot",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        depotList: [],
        isReadOnly: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          depotId:{
            rules: [
              { required: true, message: '请选择仓库!' }
            ]
          }
        },
      }
    },
    created () {
    },
    methods: {
      getDepotData() {
        getAction('/depot/findDepotByCurrentUser').then((res)=>{
          if(res.code === 200){
            this.depotList = res.data;
          }else{
            this.$message.info(res.data);
          }
        })
      },
      add () {
        this.edit({});
        this.getDepotData()
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'depotId'))
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            let depotId = formData.depotId
            that.$emit('ok', depotId);
            that.confirmLoading = false;
            that.close();
          }
        })
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>

<style scoped>

</style>
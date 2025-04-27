<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="800"
      :visible="visible"
      :confirmLoading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="取消"
      okText="保存"
      style="top:100px;height: 60%;">
      <template slot="footer">
        <a-button key="back" v-if="isReadOnly" @click="handleCancel">
          取消
        </a-button>
      </template>
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="属性名">
            <a-input placeholder="请输入属性名" v-decorator.trim="[ 'attributeName', validatorRules.attributeName]" />
          </a-form-item>
        </a-form>
        <a-form :form="form">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="属性值">
            <a-tabs v-model:activeKey="activeKey" size="small">
              <a-tab-pane key="1" tab="标签模式" forceRender>
                <template v-for="(tag, index) in tags">
                  <a-tag color="blue" style="margin-bottom: 10px" :key="tag" :closable="true" @close="() => handleClose(tag)">
                    {{ tag }}
                  </a-tag>
                </template>
                <a-input
                  v-if="inputVisible"
                  ref="input"
                  type="text"
                  size="small"
                  :style="{ width: '150px' }"
                  :value="inputValue"
                  @change="handleInputChange"
                  @blur="handleInputConfirm"
                  @keyup.enter="handleInputConfirm"
                />
                <a-tag v-else style="background: #fff; borderStyle: dashed;" @click="showInput">
                  <a-icon type="plus" /> 请输入属性值
                </a-tag>
              </a-tab-pane>
              <a-tab-pane key="2" tab="文字模式" forceRender>
                <a-textarea :rows="8" placeholder="请输入属性值" @change="handleValueChange"
                            v-decorator.trim="[ 'attributeValue', validatorRules.attributeValue]" />
                注意：属性值请用竖线隔开，比如：红色|橙色|黄色|绿色
              </a-tab-pane>
            </a-tabs>
          </a-form-item>
        </a-form>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import { addMaterialAttribute, checkMaterialAttribute, editMaterialAttribute } from '@/api/api'
  import { mixinDevice } from '@/utils/mixin'

  export default {
    name: "MaterialAttributeModal",
    mixins: [mixinDevice],
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        isReadOnly: false,
        activeKey: '1',
        tags: [],
        inputVisible: false,
        inputValue: '',
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
          attributeName:{
            rules: [
              { required: true, message: '请输入属性名!' },
              { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' },
              { validator: this.validateAttributeName}
            ]
          },
          attributeValue:{
            rules: [
              { required: true, message: '请输入属性值!' }
            ]
          }
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.activeKey = '1'
        this.visible = true;
        if(this.model.attributeValue) {
          this.tags = this.model.attributeValue.split('|')
        } else {
          this.tags = []
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'attributeName', 'attributeValue'))
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
            let obj;
            if(!this.model.id){
              obj=addMaterialAttribute(formData);
            }else{
              obj=editMaterialAttribute(formData);
            }
            obj.then((res)=>{
              if(res.code === 200){
                that.$emit('ok');
              }else{
                that.$message.warning(res.data.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel () {
        this.close()
      },
      validateAttributeName(rule, value, callback){
        if(value) {
          let params = {
            name: value,
            id: this.model.id?this.model.id:0
          }
          checkMaterialAttribute(params).then((res)=>{
            if(res && res.code===200) {
              if(!res.data.status){
                callback();
              } else {
                callback("名称已经存在");
              }
            } else {
              callback(res.data);
            }
          })
        } else {
          callback()
        }
      },
      //属性值变更
      handleValueChange(e) {
        let attributeValue = e.target.value
        if(attributeValue) {
          this.tags = attributeValue.split('|')
        } else {
          this.tags = []
        }
      },
      //删除tag
      handleClose(removedTag) {
        this.tags = this.tags.filter(tag => tag !== removedTag)
        this.form.setFieldsValue({'attributeValue': this.tags.join('|')})
      },
      //展示tag输入框
      showInput() {
        this.inputVisible = true
        this.$nextTick(function() {
          this.$refs.input.focus()
        });
      },
      handleInputChange(e) {
        this.inputValue = e.target.value
      },
      handleInputConfirm() {
        const inputValue = this.inputValue
        let tags = this.tags
        if (inputValue && tags.indexOf(inputValue) === -1) {
          tags = [...tags, inputValue]
        }
        Object.assign(this, {
          tags,
          inputVisible: false,
          inputValue: '',
        })
        this.form.setFieldsValue({'attributeValue': this.tags.join('|')})
      }
    }
  }
</script>
<style scoped>

</style>
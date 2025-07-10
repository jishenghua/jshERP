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
      style="top:5%;height: 95%;">
      <a-spin :spinning="confirmLoading">
        <a-col :md="10" :sm="24">
          <template>
            <a-tree
              checkable
              multiple
              @check="onCheck"
              :selectedKeys="selectedKeys"
              :checkedKeys="checkedKeys"
              :treeData="roleFunctionTree"
              :checkStrictly="checkStrictly"
              :expandedKeys="iExpandedKeys"
              :autoExpandParent="true"
              @expand="onExpand"/>
          </template>
        </a-col>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import pick from 'lodash.pick'
  import {mixinDevice} from '@/utils/mixin'
  import {addUserBusiness,editUserBusiness,checkUserBusiness} from '@/api/api'
  import {getAction} from '../../../api/manage'
  export default {
    name: "UserDepotModal",
    mixins: [mixinDevice],
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        roleId: 0,
        iExpandedKeys: [],
        roleFunctionTree: [],
        checkedKeys: [],
        selectedKeys: [],
        checkStrictly: false,
        hiding: true,
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
      }
    },
    created () {
    },
    methods: {
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, {});
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name', 'type', 'description'))
        });
        this.roleId = record.id
        this.checkedKeys = []
        this.loadTree(record.id)
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
            formData.type = 'UserDepot'
            formData.keyId = this.roleId
            formData.value = this.checkedKeys
            let obj;
            checkUserBusiness({'type': 'UserDepot','keyId': this.roleId}).then((res)=>{
              if(res.data && res.data.id) {
                formData.id=res.data.id
                obj=editUserBusiness(formData);
              } else {
                obj=addUserBusiness(formData);
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
            })
          }
        })
      },
      handleCancel () {
        this.close()
      },
      loadTree(id) {
        let that = this
        that.treeData = []
        that.roleFunctionTree = []
        let params = {};
        params.id='';
        getAction('/depot/findUserDepot?UBType=UserDepot&UBKeyId='+id).then((res) => {
          if (res) {
            //机构全选后，再添加机构，选中数量增多
            this.allTreeKeys = [];
            for (let i = 0; i < res.length; i++) {
              let temp = res[i]
              that.treeData.push(temp)
              that.roleFunctionTree.push(temp)
              that.setThisExpandedKeys(temp)
              that.getAllKeys(temp);
            }
            console.log(JSON.stringify(this.checkedKeys))
            this.loading = false
          }
        })
      },
      onCheck(checkedKeys, info) {
        console.log('onCheck', checkedKeys, info)
        this.hiding = false
        if(this.checkStrictly){
          this.checkedKeys = checkedKeys.checked;
        }else{
          this.checkedKeys = checkedKeys
        }
      },
      setThisExpandedKeys(node) {
        if(node.checked==true) {
          this.checkedKeys.push(node.key)
        }
        if (node.children && node.children.length > 0) {
          this.iExpandedKeys.push(node.key)
          for (let a = 0; a < node.children.length; a++) {
            this.setThisExpandedKeys(node.children[a])
          }
        }
      },
      getAllKeys(node) {
        // console.log('node',node);
        this.allTreeKeys.push(node.key)
        if (node.children && node.children.length > 0) {
          for (let a = 0; a < node.children.length; a++) {
            this.getAllKeys(node.children[a])
          }
        }
      },
      onExpand(expandedKeys) {
        console.log('onExpand', expandedKeys)
        this.iExpandedKeys = expandedKeys
      }
    }
  }
</script>
<style scoped>

</style>
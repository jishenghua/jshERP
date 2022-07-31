<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:10%;height: 90%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-col :md="10" :sm="24">
        <template>
          <a-tree
            multiple
            @select='onSelect'
            :selectedKeys="selectedKeys"
            :checkedKeys="checkedKeys"
            :treeData="roleFunctionTree"
            :checkStrictly="checkStrictly"
            :expandedKeys="iExpandedKeys"
            :autoExpandParent="true" />
        </template>
      </a-col>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import {getAction} from '../../../api/manage'
  export default {
    name: "FunctionTreeModal",
    data () {
      return {
        title:"操作",
        width: '800px',
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
      edit (id) {
        this.form.resetFields();
        this.model = Object.assign({}, {});
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name', 'type', 'description'))
        });
        this.roleId = id
        this.checkedKeys = []
        this.loadTree(id)
      },
      close () {
        this.$emit('close');
        this.visible = false;
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
        getAction('/function/findRoleFunction?UBType=RoleFunctions&UBKeyId='+id).then((res) => {
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
      onSelect(selectedKeys, info){
        let funId = info.node.value
        if(funId!==0) {
          getAction('/function/info?id=' + funId).then((res) => {
            if(res && res.code === 200) {
              if(res.data && res.data.info) {
                this.$emit('ok', res.data.info.number, res.data.info.name)
              }
            }
          })
        } else {
          this.$emit('ok', 0, '')
        }
        this.close()
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
        this.allTreeKeys.push(node.key)
        if (node.children && node.children.length > 0) {
          for (let a = 0; a < node.children.length; a++) {
            this.getAllKeys(node.children[a])
          }
        }
      },
    }
  }
</script>
<style scoped>

</style>
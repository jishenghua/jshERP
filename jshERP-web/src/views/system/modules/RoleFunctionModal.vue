<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="width"
      :visible="visible"
      :confirmLoading="confirmLoading"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭"
      wrapClassName="ant-modal-cust-warp"
      style="top:5%;height: 95%;overflow-y: hidden">
      <a-spin :spinning="confirmLoading">
        <div class="drawer-bootom-button">
          <a-dropdown :trigger="['click']" placement="topCenter">
            <a-menu slot="overlay">
              <a-menu-item key="1" @click="switchCheckStrictly(1)">父子关联</a-menu-item>
              <a-menu-item key="2" @click="switchCheckStrictly(2)">取消关联</a-menu-item>
              <a-menu-item key="3" @click="checkALL">全部勾选</a-menu-item>
              <a-menu-item key="4" @click="cancelCheckALL">取消全选</a-menu-item>
              <a-menu-item key="5" @click="expandAll">展开所有</a-menu-item>
              <a-menu-item key="6" @click="closeAll">合并所有</a-menu-item>
            </a-menu>
            <a-button>
              树操作 <a-icon type="up" />
            </a-button>
          </a-dropdown>
        </div>
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
  import {addUserBusiness,editUserBusiness,checkUserBusiness} from '@/api/api'
  import {getAction} from '../../../api/manage'
  export default {
    name: "RoleFunctionModal",
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
            formData.type = 'RoleFunctions'
            formData.keyId = this.roleId
            formData.value = this.checkedKeys
            let obj;
            checkUserBusiness({'type': 'RoleFunctions','keyId': this.roleId}).then((res)=>{
              if(res.data && res.data.id) {
                formData.id=res.data.id
                obj=editUserBusiness(formData);
              } else {
                obj=addUserBusiness(formData);
              }
              obj.then((res)=>{
                if(res.code === 200){
                  that.$emit('ok', this.roleId);
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
      expandAll () {
        this.iExpandedKeys = this.allTreeKeys
      },
      closeAll () {
        this.iExpandedKeys = []
      },
      checkALL () {
        this.checkStriccheckStrictlytly = false
        this.checkedKeys = this.allTreeKeys
      },
      cancelCheckALL () {
        this.checkedKeys = []
      },
      switchCheckStrictly (v) {
        if(v==1){
          this.checkStrictly = false
        }else if(v==2){
          this.checkStrictly = true
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
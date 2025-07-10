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
        <a-table
          ref="table"
          size="small"
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="false"
          :customRow="null"
          :loading="loading"
          :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}">
        </a-table>
      </a-spin>
    </a-modal>
  </div>
</template>
<script>
  import {mixinDevice} from '@/utils/mixin'
  import {addUserBusiness,editUserBusiness,checkUserBusiness} from '@/api/api'
  import {getAction} from '../../../api/manage'
  export default {
    name: "UserCustomerModal",
    mixins: [mixinDevice],
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        roleId: 0,
        // 表头
        columns: [
          { title: '客户名称', dataIndex: 'supplier', width: 200 }
        ],
        dataSource:[],
        selectedRowKeys: [],
        loading:false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        url: {
          cusList: "/supplier/getAllCustomer",
          selectedList: "/supplier/getUserCustomerValue",
        }
      }
    },
    created () {
    },
    methods: {
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, {});
        this.visible = true;
        this.roleId = record.id
        this.loadData()
        this.loadSelected(record.id)
      },
      close () {
        this.$emit('close')
        this.visible = false
      },
      handleCancel () {
        this.close()
      },
      loadData() {
        this.loading = true
        getAction(this.url.cusList).then((res) => {
          if (res.code===200) {
            this.dataSource = res.data.rows
          } else if(res.code===510){
            this.$message.warning(res.data)
          } else {
            this.$message.warning(res.data.message)
          }
          this.loading = false
        })
      },
      loadSelected(id) {
        getAction(this.url.selectedList + '?UBType=UserCustomer&UBKeyId='+id).then((res) => {
          if (res.code===200) {
            this.selectedRowKeys = res.data? res.data:[]
          } else {
            this.$message.warning(res.data)
          }
        })
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            formData.type = 'UserCustomer'
            formData.keyId = this.roleId
            formData.value = this.selectedRowKeys
            let obj;
            checkUserBusiness({'type': 'UserCustomer','keyId': this.roleId}).then((res)=>{
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
      }
    }
  }
</script>
<style scoped>
  .virtual-table {
    height: 500px; /* 必须指定高度 */
  }
</style>
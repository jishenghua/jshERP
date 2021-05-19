<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 95%;overflow-y: hidden">
    <a-spin :spinning="confirmLoading">
      <div class="table-page-search-wrapper">
        <!-- 按钮区域 -->
        <a-form layout="inline">
          <a-row :gutter="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-col :md="12" :sm="24">
                <a-button @click="toggleChecked">
                  {{ !checked ? '全选' : '全取消' }}
                </a-button>
              </a-col>
            </span>
          </a-row>
        </a-form>
      </div>
      <!-- table区域-begin -->
      <div>
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :pagination="false"
          :columns="columns"
          :dataSource="dataSource"
          :loading="loading">
          <span slot="action" slot-scope="text, record">
            <a-checkbox v-if="record.pushBtn.indexOf(1)>-1" value="1" :checked="record.btnStr?record.btnStr.indexOf(1)>-1:false" @change="onChange(record,'1')">编辑</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(2)>-1" value="2" :checked="record.btnStr?record.btnStr.indexOf(2)>-1:false" @change="onChange(record,'2')">审核反审核</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(3)>-1" value="3" :checked="record.btnStr?record.btnStr.indexOf(3)>-1:false" @change="onChange(record,'3')">导入导出</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(4)>-1" value="4" :checked="record.btnStr?record.btnStr.indexOf(4)>-1:false" @change="onChange(record,'4')">启用禁用</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(5)>-1" value="5" :checked="record.btnStr?record.btnStr.indexOf(5)>-1:false" @change="onChange(record,'5')">打印</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(6)>-1" value="6" :checked="record.btnStr?record.btnStr.indexOf(6)>-1:false" @change="onChange(record,'6')">作废</a-checkbox>
          </span>
        </a-table>
      </div>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction } from '@/api/manage'
  import { updateBtnStrByRoleId } from '@/api/api'
  import { removeByVal } from "@/utils/util"
  export default {
    name: "RolePushBtnModal",
    mixins:[JeecgListMixin],
    data () {
      return {
        title:"操作",
        width: '800px',
        visible: false,
        model: {},
        checked: false,
        disableMixinCreated: true,
        confirmLoading: false,
        form: this.$form.createForm(this),
        /* 数据源 */
        dataSource:[],
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:40,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title: '按钮列表',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/function/findRoleFunctionsById"
        }
      }
    },
    created () {
    },
    methods: {
      edit (roleId) {
        this.form.resetFields();
        this.model.id = roleId
        this.visible = true;
        if(roleId) {
          getAction(this.url.list, { roleId: roleId }).then((res) => {
            if (res.code === 200) {
              this.dataSource = res.data.rows;
              this.ipagination.total = res.data.total;
            }
            else if (res.code === 400) {
              this.dataSource = []
              this.ipagination.total = 0
            }
            else if (res.code === 500) {
              this.$message.warning(res.data)
            }
            this.loading = false;
          })
        }
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let funArray = this.dataSource
            let bindArr = [];
            let btnStr = ''
            for(let item of funArray){
              if (item.btnStr !== undefined && item.btnStr !== "" && item.btnStr !== "null" && item.btnStr !== null) {
                let bindJSON = {};
                bindJSON.funId = item.id;
                bindJSON.btnStr = item.btnStr;
                bindArr.push(bindJSON);
              }
            }
            if (bindArr.length) {
              btnStr = JSON.stringify(bindArr);
            }
            let obj=updateBtnStrByRoleId({roleId: this.model.id, btnStr: btnStr});
            obj.then((res)=>{
              if(res.code === 200){
                that.$emit('ok');
              }else{
                that.$message.warning(res.data);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      toggleChecked() {
        this.checked = !this.checked;
        let funArray = this.dataSource
        if(this.checked) {
          for(let item of funArray){
            item.btnStr = item.pushBtn
          }
        } else {
          for(let item of funArray){
            item.btnStr = ''
          }
        }
      },
      onChange(record,value) {
        let funArray = this.dataSource
        for(let item of funArray){
          if(item.id === record.id) {
            let btnStr = record.btnStr
            if(btnStr) {
              let btnArr = btnStr.split(',')
              if(btnStr.indexOf(value)>-1) {
                //去掉勾选
                removeByVal(btnArr, value)
                item.btnStr = btnArr.join()
              } else {
                //勾选
                btnArr.push(value)
                item.btnStr = btnArr.join()
              }
            } else {
              let btnArr = []
              //勾选
              btnArr.push(value)
              item.btnStr = btnArr.join()
            }
          }
        }
      }
    }
  }
</script>
<style scoped>

</style>
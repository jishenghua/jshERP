<template>
  <a-modal
    :title="title"
    :width="800"
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
            <a-checkbox v-if="record.pushBtn.indexOf(1)>-1" value="1" :checked="checked" @change="onChange">编辑</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(2)>-1" value="2" :checked="checked" @change="onChange">审核反审核</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(3)>-1" value="3" :checked="checked" @change="onChange">导入导出</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(4)>-1" value="4" :checked="checked" @change="onChange">启用禁用</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(5)>-1" value="5" :checked="checked" @change="onChange">打印</a-checkbox>
            <a-checkbox v-if="record.pushBtn.indexOf(6)>-1" value="6" :checked="checked" @change="onChange">作废</a-checkbox>
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
  import {addRole,editRole,checkRole } from '@/api/api'
  export default {
    name: "RolePushBtnModal",
    mixins:[JeecgListMixin],
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        checked: false,
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
          list: "/function/findByIds",
          getBasicData: "/userBusiness/getBasicData"
        }
      }
    },
    created () {
    },
    methods: {
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        getAction(this.url.getBasicData, {Type: 'RoleFunctions', KeyId: record.id}).then((res) => {
          if (res && res.code === 200) {
            let ubList = res.data.userBusinessList;
            let getValue = ubList[0].value;
            getValue = getValue.substring(1, getValue.length - 1);
            if (getValue.indexOf("][")) {
              let arr = getValue.split("][");
              arr = arr.toString();
              getAction(this.url.list, { functionsIds: arr }).then((res) => {
                if (res.code === 200) {
                  this.dataSource = res.data.rows;
                  this.ipagination.total = res.data.total;
                }
                if (res.code === 510) {
                  this.$message.warning(res.data)
                }
                this.loading = false;
              })
            }
          }
        })
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
              obj=addRole(formData);
            }else{
              obj=editRole(formData);
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
      toggleChecked() {
        this.checked = !this.checked;
      },
      onChange(e) {
        this.checked = e.target.checked;
      }
    }
  }
</script>
<style scoped>

</style>
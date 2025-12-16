<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="close"
    cancelText="关闭"
    style="top:12%;height: 90%;overflow-y: hidden"
    wrapClassName="ant-modal-cust-warp">
    <a-row :gutter="24">
      <a-col :md="24" :sm="24">
        <div>
          <a-form layout="inline" @keyup.enter.native="onAdd">
            <a-tabs default-active-key="1" tab-position="left">
              <a-tab-pane key="1" tab="单个序列号" forceRender>
                <a-row :gutter="24">
                  <a-col :md="24" :sm="24">
                    <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号">
                      <a-input ref="name" style="width:400px;" placeholder="请输入序列号并回车（只能输入数字或字母）"
                               oninput="value=value.replace(/[\W]/g,'')" v-model="queryParam.name"></a-input>
                      <div style="float:left;">
                        <a-button type="primary" @click="onAdd">添加</a-button>
                        <a-button style="margin-left: 8px" @click="clearAllSn">清空</a-button>
                      </div>
                    </a-form-item>
                  </a-col>
                </a-row>
              </a-tab-pane>
              <a-tab-pane key="2" tab="多个序列号" forceRender>
                <a-row :gutter="24">
                  <a-col :md="24" :sm="24">
                    <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序列号">
                      <a-textarea style="width:400px;"
                        placeholder="多个序列号用逗号隔开，请少于2000个字符"
                        :auto-size="{ minRows: 2, maxRows: 4 }"
                        v-model="queryParam.multiName" />
                      <div style="float:left;">
                        <a-button type="primary" @click="onBatchAdd">批量添加</a-button>
                        <a-button style="margin-left: 8px" @click="clearAllSn">清空</a-button>
                      </div>
                    </a-form-item>
                  </a-col>
                </a-row>
              </a-tab-pane>
            </a-tabs>
          </a-form>
        </div>
      </a-col>
    </a-row>
    <a-row :gutter="10" style="padding: 10px;">
      <a-col :md="24" :sm="24">
        <a-table
          ref="table"
          :scroll="scrollTrigger"
          size="middle"
          rowKey="id"
          :columns="columns"
          :dataSource="checkDataSource"
          :pagination="false"
          :loading="loading">
           <span slot="action" slot-scope="text, record">
              <a @click="removeSn(record)">移除</a>
           </span>
        </a-table>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'JSelectSnAddModal',
    mixins:[JeecgListMixin],
    components: {},
    props: ['rows', 'multi', 'barCode'],
    data() {
      return {
        modalWidth: 800,
        queryParam: {
          name: "",
          multiName: ""
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 18 },
        },
        categoryTree:[],
        columns: [
          {dataIndex: 'serialNumber', title: '已录入的序列号', width: 100, align: 'left'},
          {tdataIndex: 'action', title: '操作', align:"center", width: 50, scopedSlots: { customRender: 'action' }}
        ],
        scrollTrigger: {y: 460},
        checkDataSource: [],
        selectedRowKeys: [],
        selectRows: [],
        selectIds: [],
        title: '录入序列号',
        visible: false,
        form: this.$form.createForm(this),
        disableMixinCreated: true,
        loading: false,
      }
    },
    computed: {
      // 计算属性的 getter
      getType: function () {
        return this.multi == true ? 'checkbox' : 'radio';
      }
    },
    watch: {
      barCode: {
        immediate: true,
        handler() {
          this.initBarCode()
        }
      },
    },
    methods: {
      initBarCode() {
        if (this.barCode) {
          this.$emit('initComp', this.barCode)
        } else {
          this.$emit('initComp', '')
        }
      },
      showModal() {
        this.visible = true
        this.$nextTick(() => this.$refs.name.focus())
        this.form.resetFields()
        //加载存在的序列号
        if(this.rows) {
          this.checkDataSource = []
          let rowObj = JSON.parse(this.rows)
          let snArr = rowObj.snList.split(',')
          for (let i = 0; i < snArr.length; i++) {
            let snObj = {
              'id': snArr[i],
              'serialNumber': snArr[i]
            }
            this.checkDataSource.push(snObj)
          }
        }
      },
      clearAllSn() {
        this.checkDataSource = []
      },
      close() {
        this.visible = false;
      },
      handleSubmit() {
        let that = this;
        this.getSelectRows();
        that.$emit('ok', that.selectRows, that.selectIds);
        that.close();
      },
      removeSn(record) {
        let oldArr = this.checkDataSource
        let newArr = []
        for (let i = 0; i < oldArr.length; i++) {
          if(oldArr[i].id !== record.id) {
            newArr.push(oldArr[i])
          }
        }
        this.checkDataSource = newArr
      },
      //获取选择信息
      getSelectRows() {
        let ids = ""
        this.selectRows = this.checkDataSource
        let data = this.checkDataSource
        for (let i = 0; i < data.length; i++) {
          ids = ids + "," + data[i].serialNumber
        }
        this.selectIds = ids.substring(1)
      },
      onAdd() {
        if(!this.queryParam.name) {
          return
        }
        let checkObj = {
          id: this.queryParam.name,
          serialNumber: this.queryParam.name
        }
        let data = this.checkDataSource
        let isExist = false
        for (let i = 0; i < data.length; i++) {
          if(data[i].serialNumber === this.queryParam.name) {
            isExist = true
          }
        }
        if(isExist) {
          this.$message.warning('抱歉，此序列号已经添加过！');
        } else {
          this.checkDataSource.push(checkObj)
          this.queryParam.name = ''
        }
      },
      onBatchAdd() {
        if(!this.queryParam.multiName) {
          return
        }
        if(this.queryParam.multiName && this.queryParam.multiName.length>2000) {
          this.$message.warning('序列号长度不能超出2000个字符！');
          return
        }
        this.queryParam.multiName = this.queryParam.multiName.replaceAll('，',',')
        let nameArr = this.queryParam.multiName.split(',')
        for (let i = 0; i < nameArr.length; i++) {
          let checkObj = {
            id: nameArr[i],
            serialNumber: nameArr[i]
          }
          let data = this.checkDataSource
          let isExist = false
          for (let j = 0; j < data.length; j++) {
            if(data[j].serialNumber === nameArr[i]) {
              isExist = true
            }
          }
          if(isExist) {
            this.$message.warning('抱歉，序列号' + nameArr[i] + '已经添加过！');
            return
          } else {
            this.checkDataSource.push(checkObj)
          }
        }
        this.queryParam.multiName = ''
      }
    }
  }
</script>

<style lang="less">
  .ant-table-tbody .ant-table-row td {
    padding-top: 10px;
    padding-bottom: 10px;
  }

  #components-layout-demo-custom-trigger .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }

  .table-page-search-wrapper {
    .ant-form-inline {
      .ant-form-item {
        margin-bottom: 12px;
        margin-right: 0;
      }
    }

  }
</style>
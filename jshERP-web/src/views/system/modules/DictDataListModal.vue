<template>
  <div ref="container">
    <a-modal
      :title="title"
      :width="1300"
      :visible="visible"
      :getContainer="() => $refs.container"
      :maskStyle="{'top':'93px','left':'154px'}"
      :wrapClassName="wrapClassNameInfo()"
      :mask="isDesktop()"
      :maskClosable="false"
      @cancel="handleCancel"
      cancelText="关闭"
      style="top:40px;height: 90%;">
      <template slot="footer">
        <a-button @click="handleCancel">关闭</a-button>
      </template>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <!-- 搜索区域 -->
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :md="6" :sm="24">
              <a-form-item label="字典名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select placeholder="请选择字典名称" showSearch allow-clear optionFilterProp="children" v-model="queryParam.dictType">
                  <a-select-option v-for="(item,index) in typeOptions" :key="index" :value="item.dictType">
                    {{ item.dictName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="字典标签" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input placeholder="请输入字典标签" v-model="queryParam.dictLabel"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select v-model="queryParam.status" placeholder="请选择状态">
                  <a-select-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :value="dict.value">
                    {{ dict.label }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-col :md="4" :sm="24">
                <a-button type="primary" @click="searchQuery">查询</a-button>
                <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
              </a-col>
            </span>
          </a-row>
        </a-form>
      </div>
      <!-- 操作按钮区域 -->
      <div class="table-operator" style="border-top: 5px">
        <a-button @click="handleAddWithData" type="primary" icon="plus">新增</a-button>
        <a-button @click="batchDel" icon="delete">删除</a-button>
      </div>
      <!-- table区域-begin -->
      <a-table
        bordered
        ref="table"
        size="middle"
        rowKey="dictCode"
        :columns="columns"
        :dataSource="dataSource"
        :components="handleDrag(columns)"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.dictCode)">
            <a>删除</a>
          </a-popconfirm>
        </span>
        <template slot="customRenderDictLabel" slot-scope="text, record">
          <span v-if="record.listClass == '' || record.listClass == 'default'">{{record.dictLabel}}</span>
          <a-tag v-else :color="record.listClass == 'grey' ? '' : record.listClass">{{record.dictLabel}}</a-tag>
        </template>
        <!-- 状态渲染模板 -->
        <template slot="customRenderStatus" slot-scope="status">
          <dict-tag :options="dict.type.sys_normal_disable" :value="status"/>
        </template>
      </a-table>
      <!-- table区域-end -->
      <!-- 表单区域 -->
      <dict-data-modal ref="modalForm" @ok="modalFormOk"></dict-data-modal>
    </a-modal>
  </div>
</template>
<script>
  import { getDictOptionselect } from '@/api/api'
  import DictDataModal from './DictDataModal'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  export default {
    name: "DictDataListModal",
    dicts: ['sys_normal_disable'],
    components: { DictDataModal },
    mixins:[JeecgListMixin, mixinDevice],
    data () {
      return {
        title:"字典数据",
        visible: false,
        disableMixinCreated: true,
        // 类型数据字典
        typeOptions: [],
        queryParam: {
          dictType: undefined,
          dictLabel: "",
          status: undefined
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        // 表头
        columns: [
          {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'},
            align: "center",
            width: 80
          },
          { title: '字典编码', dataIndex: 'dictCode',width:100, align:"center"},
          { title: '字典标签',dataIndex: 'dictLabel',width: 100,align:"center",
            scopedSlots: { customRender: 'customRenderDictLabel' }
          },
          { title: '字典键值', dataIndex: 'dictValue',width:100, align:"center"},
          { title: '字典排序', dataIndex: 'dictSort',width:100, align:"center"},
          { title: '状态', dataIndex: 'status',width:100, align:"center",
            scopedSlots: { customRender: 'customRenderStatus' }
          },
          { title: '备注', dataIndex: 'remark',width:100},
          { title: '创建时间', dataIndex: 'createTime',width:100}
        ],
        dataSource:[],
        url: {
          list: "/dict/data/list",
          delete: "/dict/data/delete",
          deleteBatch: "/dict/data/deleteBatch"
        }
      }
    },
    created () {
    },
    methods: {
      show(record) {
        this.model = Object.assign({}, {})
        this.visible = true
        this.queryParam.dictType = record.dictType
        this.getTypeList()
        this.loadData(1)
      },
      /** 查询字典类型列表 */
      getTypeList() {
        getDictOptionselect().then(res => {
          this.typeOptions = res.data;
        });
      },
      handleAddWithData() {
        this.$refs.modalForm.add(this.queryParam.dictType)
        this.$refs.modalForm.title = "新增";
        this.$refs.modalForm.disableSubmit = false;
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      onDateChange: function (value, dateString) {
        this.queryParam.beginTime=dateString[0];
        this.queryParam.endTime=dateString[1];
      },
      onDateOk(value) {
        console.log(value);
      },
      searchReset() {
        this.queryParam.dictLabel = ""
        this.queryParam.status = undefined
        this.loadData(1);
      }
    }
  }
</script>
<style scoped>
</style>
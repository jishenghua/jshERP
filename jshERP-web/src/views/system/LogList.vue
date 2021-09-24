<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="操作模块" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入操作模块" v-model="queryParam.operation"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="操作详情" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入操作详情" v-model="queryParam.content"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-range-picker
                    v-model="queryParam.createTimeRange"
                    format="YYYY-MM-DD"
                    :placeholder="['开始时间', '结束时间']"
                    @change="onDateChange"
                    @ok="onDateOk"
                  />
                </a-form-item>
              </a-col>
              <template v-if="toggleSearchStatus">
                <a-col :md="6" :sm="24">
                  <a-form-item label="操作IP" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input placeholder="请输入操作IP" v-model="queryParam.clientIp"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="24">
                  <a-form-item label="操作状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-select v-model="queryParam.status" placeholder="请选择操作状态">
                      <a-select-option value="">请选择</a-select-option>
                      <a-select-option value="0">成功</a-select-option>
                      <a-select-option value="1">失败</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </template>
              <a-col :md="6" :sm="24" >
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
                  <a @click="handleToggleSearch" style="margin-left: 8px">
                    {{ toggleSearchStatus ? '收起' : '展开' }}
                    <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
                  </a>
                </span>
              </a-col>
            </a-row>
          </a-form>
        </div>
        <!-- table区域-begin -->
        <a-table
          ref="table"
          bordered
          size="middle"
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :scroll="scroll"
          :loading="loading"
          @change="handleTableChange">
          <!-- 字符串超长截取省略号显示-->
          <span slot="content" slot-scope="text, record">
              <j-ellipsis :value="text" :length="40"/>
            </span>
        </a-table>
        <!-- table区域-end -->
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- f r o m 7 5  2 7 1  8 9 2 0 -->
<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JEllipsis from '@/components/jeecg/JEllipsis'

  export default {
    name: "LogList",
    mixins:[JeecgListMixin],
    components: {
      JEllipsis
    },
    data () {
      return {
        // 查询条件
        queryParam: {
          operation:'',
          content:'',
          createTimeRange:[],
          clientIp:'',
          status:''
        },
        tabKey: "1",
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
          {title: '操作模块', dataIndex: 'operation', width: 120},
          {title: '操作详情', align:"left", dataIndex: 'content', scopedSlots: { customRender: 'content' }, width: 350 },
          {title: '操作人员', dataIndex: 'userName', width: 100, align: "center"},
          {
            title: '操作状态', dataIndex: 'status',width:80, align:"center",
            customRender:function (text) {
              if(text){
                return "失败";
              }else {
                return "成功";
              }
            }
          },
          {title: '操作IP', dataIndex: 'clientIp', width: 110, align: "center"},
          {title: '操作时间', dataIndex: 'createTimeStr', width: 150, align: "center"}
        ],
        operateColumn:
        {
          title: '操作类型',
          dataIndex: 'operateType_dictText',
          align:"center",
        },
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        url: {
          list: "/log/list",
        }
      }
    },
    methods: {
      // 日志类型
      callback(key){
        // 动态添加操作类型列
        if (key == 2) {
          this.tabKey = '2';
          this.columns.splice(7, 0, this.operateColumn);
        }else if(this.columns.length == 9)
        {
          this.tabKey = '1';
          this.columns.splice(7,1);
        }

        let that=this;
        that.queryParam.logType=key;
        that.loadData();
      },
      onDateChange: function (value, dateString) {
        console.log(dateString[0],dateString[1]);
        this.queryParam.beginTime=dateString[0];
        this.queryParam.endTime=dateString[1];
      },
      onDateOk(value) {
        console.log(value);
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
<template>
  <a-modal
    :title="title"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    cancelText="关闭"
    style="top:20%;height: 60%;overflow-y: hidden">
    <template slot="footer">
      <a-button key="back" @click="handleCancel">
        关闭
      </a-button>
    </template>
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery">查询</a-button>
              <a-button @click="searchReset" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
<!--    <div class="table-operator">-->
<!--      <a-button type="primary" @click="readAll" icon="book">全部标注已读</a-button>-->
<!--    </div>-->
    <div style="margin-top: 5px">
      <a-table
        ref="table"
        size="default"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange">
      <template slot="customRenderTitle" slot-scope="text, record">
        <span v-if="record.status =='1'" style="font-weight: bold">{{text}}</span>
        <span v-if="record.status =='2'">{{text}}</span>
      </template>
      <span slot="action" slot-scope="text, record">
        <a @click="showAnnouncement(record)">查看</a>
      </span>
      </a-table>
    </div>
    <show-announcement ref="ShowAnnouncement"></show-announcement>
    <dynamic-notice ref="showDynamNotice" :path="openPath" :formData="formData"/>
  </a-modal>
</template>

<script>
  import { filterObj } from '@/utils/util'
  import { getAction,putAction,postAction } from '@/api/manage'
  import ShowAnnouncement from '@/components/tools/ShowAnnouncement'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import DynamicNotice from '../../components/tools/DynamicNotice'

  export default {
    name: "MsgList",
    mixins: [JeecgListMixin],
    components: {
      DynamicNotice,
      ShowAnnouncement
    },
    data () {
      return {
        title:"通知",
        modalWidth:800,
        visible: false,
        confirmLoading: false,
        queryParam: {
          name: ''
        },
        columns: [{
          title: '标题',
          dataIndex: 'msgTitle',
          scopedSlots: { customRender: 'customRenderTitle' },
          width: 200
        },
        {
          title: '消息类型',
          dataIndex: 'type',
          width: 80
        },
        {
          title: '时间',
          dataIndex: 'createTimeStr',
          width: 90
        },
        {
          title: '操作',
          dataIndex: 'action',
          align:"center",
          scopedSlots: { customRender: 'action' },
          width: 50
        }],
		    url: {
          list: "/msg/list",
          batchUpdateStatus:"/msg/batchUpdateStatus",
          editCementSend:"sys/sysAnnouncementSend/editByAnntIdAndUserId",
          readAllMsg:"sys/sysAnnouncementSend/readAll",
        },
        loading:false,
        openPath:'',
        formData:''
      }
    },
    methods: {
      handleDetail: function(){
        this.visible = true
      },
      showAnnouncement(record){
        postAction(this.url.batchUpdateStatus,{ids:record.id, status: '2'}).then((res)=>{
          if(res && res.code === 200){
            this.loadData();
            //this.syncHeadNotic(record.anntId)
          }
        });
        if(record.openType==='component'){
          this.openPath = record.openPage;
          this.formData = {id:record.busId};
          this.$refs.showDynamNotice.detail();
        }else{
          this.$refs.ShowAnnouncement.detail(record);
        }
      },
      syncHeadNotic(anntId){
        getAction("sys/annountCement/syncNotic",{anntId:anntId})
      },
      readAll(){
        var that = this;
        that.$confirm({
          title:"确认操作",
          content:"是否全部标注已读?",
          onOk: function(){
            putAction(that.url.readAllMsg).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.loadData();
                that.syncHeadNotic();
              }
            });
          }
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>
<style scoped>
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}z

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}
</style>
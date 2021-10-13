<template>
  <a-row :gutter="24">
    <a-col :md="24">
      <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input placeholder="请输入名称查询" v-model="queryParam.name"></a-input>
                </a-form-item>
              </a-col>
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-col :md="6" :sm="24">
                  <a-button type="primary" @click="searchQuery">查询</a-button>
                  <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
                  <a-button type="primary" style="margin-left: 8px" @click="writeCode">填写激活码</a-button>
                </a-col>
              </span>
            </a-row>
          </a-form>
        </div>
        <!-- 操作按钮区域 -->
        <div class="table-operator"  style="margin-top: 5px">
          <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importUrl" @change="handleImportJar">
            <a-popover title="导入注意点">
              <template slot="content">
                <p>请选择需要导入的插件jar包</p>
              </template>
              <a-button type="primary" icon="import">上传插件包</a-button>
            </a-popover>
          </a-upload>
        </div>
        <!-- table区域-begin -->
        <div>
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :scroll="scroll"
            :loading="loading"
            @change="handleTableChange">
            <span slot="action" slot-scope="text, record">
              <a @click="uploadTemplate(record)" >上传页面</a>
              <a-divider type="vertical" />
              <a-popconfirm title="确定要开启该插件吗?" @confirm="() => startPlugin(record.pluginDescriptor.pluginId)">
                <a>开启</a>
              </a-popconfirm>
              <a-divider type="vertical" />
              <a-popconfirm title="确定要停止该插件吗?" @confirm="() => stopPlugin(record.pluginDescriptor.pluginId)">
                <a>停止</a>
              </a-popconfirm>
              <a-divider type="vertical" />
              <a-popconfirm title="确定要卸载该插件吗?" @confirm="() => uninstallPlugin(record.pluginDescriptor.pluginId)">
                <a>卸载</a>
              </a-popconfirm>
            </span>
            <span slot="linkInfo" slot-scope="text, record">
              <a @click="linkTo(record)" target='_blank'>链接跳转</a>
            </span>
            <template slot="customRenderFlag" slot-scope="pluginState">
              <a-tag v-if="pluginState=='STARTED'" color="green">启用</a-tag>
              <a-tag v-if="pluginState=='STOPPED'" color="orange">停止</a-tag>
            </template>
          </a-table>
        </div>
        <!-- table区域-end -->
        <!-- 表单区域 -->
        <plugin-modal ref="modalForm" @ok="modalFormOk"></plugin-modal>
      </a-card>
    </a-col>
  </a-row>
</template>
<!-- f r o m 7 5  2 7 1  8 9 2 0 -->
<script>
  import PluginModal from './modules/PluginModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {postAction} from '@/api/manage';
  import JDate from '@/components/jeecg/JDate'
  import { filterObj } from '@/utils/util'
  export default {
    name: "PluginList",
    mixins:[JeecgListMixin],
    components: {
      PluginModal,
      JDate
    },
    data () {
      return {
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 18,
          offset: 1
        },
        // 查询条件
        queryParam: {name:''},
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
          {title: '名称', dataIndex: '', width: 120,
            customRender:function (t,r,index) {
              if (r) {
                var desc = r.pluginDescriptor.pluginDescription;
                if(desc.indexOf("|")){
                  var arr = desc.split("|");
                  return arr[0];
                }
              }
            }
          },
          {title: '标识', dataIndex: '', width: 180,
            customRender:function (t,r,index) {
              if (r) {
                return r.pluginDescriptor.pluginId;
              }
            }
          },
          {title: '版本', dataIndex: '', width: 120,
            customRender:function (t,r,index) {
              if (r) {
                return r.pluginDescriptor.version;
              }
            }
          },
          {title: '作者', dataIndex: '', width: 100,
            customRender:function (t,r,index) {
              if (r) {
                return r.pluginDescriptor.provider;
              }
            }
          },
          {title: '页面链接', dataIndex: '', width: 250,
            scopedSlots: { customRender: 'linkInfo' }
          },
          {title: '状态', dataIndex: 'pluginState', width: 80, align: "center",
            scopedSlots: { customRender: 'customRenderFlag' }
          },
          {
            title: '操作',
            dataIndex: 'action',
            width: 200,
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/plugin/list",
          delete: "/plugin/delete",
          deleteBatch: "/plugin/deleteBatch",
          importJarUrl: "/plugin/uploadInstallPluginJar",
        }
      }
    },
    computed: {
      importUrl: function () {
        return `${window._CONFIG['domianURL']}${this.url.importJarUrl}`;
      }
    },
    methods: {
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        if(this.superQueryParams){
          sqp['superQueryParams']=encodeURI(this.superQueryParams)
          sqp['superQueryMatchType'] = this.superQueryMatchType
        }
        let param = {};
        param.name = this.queryParam.name;
        param.currentPage = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      writeCode() {
        this.$refs.modalForm.edit();
        this.$refs.modalForm.title = "填写激活码";
        this.$refs.modalForm.disableSubmit = false;
      },
      linkTo(record) {
        let desc = record.pluginDescriptor.pluginDescription;
        if(desc.indexOf("|")){
          let arr = desc.split("|");
          window.location.href = arr[1]
        }
      },
      uploadTemplate(record) {
        var rootPath = record.path.substring(0, record.path.indexOf("plugins"));
        this.$message.info('请将页面上传到服务器目录：' + " /前端根目录/plugins/");
      },
      startPlugin(pluginId) {
        postAction('/plugin/start/' + pluginId).then((res)=>{
          if(res && res.code == 200) {
            this.loadData();
          }
        })
      },
      stopPlugin(pluginId) {
        postAction('/plugin/stop/' + pluginId).then((res)=>{
          if(res && res.code == 200) {
            this.loadData();
          }
        })
      },
      uninstallPlugin(pluginId) {
        postAction('/plugin/uninstall/' + pluginId).then((res)=>{
          if(res && res.code == 200) {
            this.loadData();
          }
        })
      },
      handleImportJar(info){
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          if (info.file.response) {
            if (info.file.response.code === 200) {
              this.$message.success(info.file.response.data)
              this.loadData()
            }
          } else {
            this.$message.error(info.file.response.data);
          }
        } else if (info.file.status === 'error') {
          this.$message.error(`文件上传失败: ${info.file.msg} `);
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
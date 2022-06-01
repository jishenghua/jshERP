import {findBySelectSup, findBySelectCus, findBySelectOrgan, findBySelectRetail, getUserList, getPersonByType, getAccount} from '@/api/api'
import Vue from 'vue'

export const FinancialListMixin = {
  data () {
    return {
      supList: [],
      cusList: [],
      organList: [],
      retailList: [],
      userList: [],
      personList: [],
      accountList: []
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },

    isBatchDelEnabled: function () {
      for (let i = 0; i < this.selectedRowKeys.length; i++) {
        if (!this.selectionRows[i].actionsEnabled.delete) {
          return false;
        }
      }
      return true;
    }
  },
  created() {
  },
  methods: {
    myHandleAdd() {
      this.$refs.modalForm.action = "add";
      if(this.btnEnableList.indexOf(2)===-1) {
        this.$refs.modalForm.isCanCheck = false
      }
      this.handleAdd();
    },
    myHandleEdit(record) {
      if(record.status === '0') {
        this.$refs.modalForm.action = "edit";
        if(this.btnEnableList.indexOf(2)===-1) {
          this.$refs.modalForm.isCanCheck = false
        }
        this.handleEdit(record);
      } else {
        this.$message.warning("抱歉，只有未审核的单据才能编辑！")
      }
    },
    myHandleDelete(record) {
      if(record.status === '0') {
        this.handleDelete(record.id)
      } else {
        this.$message.warning("抱歉，只有未审核的单据才能删除！")
      }
    },
    myHandleDetail(record, type) {
      this.handleDetail(record, type);
    },
    handleApprove(record) {
      this.$refs.modalForm.action = "approve";
      this.$refs.modalForm.edit(record);
      this.$refs.modalForm.title = "审核";
    },
    searchReset() {
      this.queryParam = {
        type: this.queryParam.type,
        roleType: Vue.ls.get('roleType')
      }
      this.loadData(1);
    },
    initSupplier() {
      let that = this;
      findBySelectSup({}).then((res)=>{
        if(res) {
          that.supList = res;
        }
      });
    },
    initCustomer() {
      let that = this;
      findBySelectCus({}).then((res)=>{
        if(res) {
          that.cusList = res;
        }
      });
    },
    initOrgan() {
      let that = this;
      findBySelectOrgan({}).then((res)=>{
        if(res) {
          that.organList = res;
        }
      });
    },
    initRetail() {
      let that = this;
      findBySelectRetail({}).then((res)=>{
        if(res) {
          that.retailList = res;
        }
      });
    },
    initUser() {
      getUserList({}).then((res)=>{
        if(res) {
          this.userList = res;
        }
      });
    },
    initPerson() {
      let that = this;
      getPersonByType({type:'财务员'}).then((res)=>{
        if(res && res.code === 200) {
          that.personList = res.data.personList;
        }
      })
    },
    initAccount() {
      getAccount({}).then((res)=>{
        if(res && res.code === 200) {
          let list = res.data.accountList
          this.accountList = list
        }
      })
    },
    onDateChange: function (value, dateString) {
      this.queryParam.beginTime=dateString[0];
      this.queryParam.endTime=dateString[1];
    },
    onDateOk(value) {
      console.log(value);
    }
  }
}
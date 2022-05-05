(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-516e6540"],{"3dfb":function(e,t,a){},a82b:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:24,xs:24}},[a("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,size:"small",inline:!0,"label-width":"68px"}},[a("el-form-item",{attrs:{label:"会员卡号",prop:"card"}},[a("el-input",{staticStyle:{width:"240px"},attrs:{placeholder:"",clearable:""},model:{value:e.queryParams.card,callback:function(t){e.$set(e.queryParams,"card",t)},expression:"queryParams.card"}}),a("el-checkbox",{model:{value:e.queryParams.isAdmin,callback:function(t){e.$set(e.queryParams,"isAdmin",t)},expression:"queryParams.isAdmin"}},[e._v("过滤内部卡号")])],1),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")]),a("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],1),a("el-row",{staticClass:"mb8",attrs:{gutter:10}},[a("el-col",{attrs:{span:1.5}},[a("el-button",{attrs:{type:"danger",plain:"",icon:"el-icon-c-scale-to-original",size:"mini"},on:{click:e.handleDetail}},[e._v("明细")])],1),a("el-col",{attrs:{span:1.5}},[a("el-button",{attrs:{type:"warning",plain:"",icon:"el-icon-download",size:"mini"},on:{click:e.handleExport}},[e._v("导出")])],1)],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.userList,"show-summary":"","sum-text":"小计","summary-method":e.getSummaries1},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{key:"card",attrs:{label:"会员卡号",align:"center",prop:"card"}}),a("el-table-column",{key:"userName",attrs:{label:"姓名",align:"center",prop:"userName"}}),a("el-table-column",{key:"chipBalance",attrs:{label:"已存筹码余额",align:"center",sortable:"",prop:"chipBalance"}}),a("el-table-column",{key:"cashBalance",attrs:{label:"已存现金余额",align:"center",sortable:"",prop:"cashBalance"}}),a("el-table-column",{key:"totalBalance",attrs:{label:"总余额",align:"center",sortable:"",prop:"totalBalance"}}),a("el-table-column",{key:"remark",attrs:{label:"备注",align:"center",prop:"remark"}}),a("el-table-column",{attrs:{fixed:"right",label:"操作",align:"center",width:"260","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return 1!==t.row.userId?[a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-tickets"},on:{click:function(a){return e.handleSign(t.row)}}},[e._v("存码")]),a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-document-remove"},on:{click:function(a){return e.handleBack(t.row)}}},[e._v("取码")]),a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-document-remove"},on:{click:function(a){return e.handleBack(t.row.card)}}},[e._v("明细")])]:void 0}}],null,!0)})],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"table2",attrs:{data:e.userList,"show-summary":"","sum-text":"合计","summary-method":e.getSummaries},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{key:"card",attrs:{label:"会员卡号",align:"center",prop:"card"}}),a("el-table-column",{key:"userName",attrs:{label:"姓名",align:"center",prop:"userName"}}),a("el-table-column",{key:"chipBalance",attrs:{label:"已存筹码余额",align:"center",sortable:"",prop:"chipBalance"}}),a("el-table-column",{key:"cashBalance",attrs:{label:"已存现金余额",align:"center",sortable:"",prop:"cashBalance"}}),a("el-table-column",{key:"totalBalance",attrs:{label:"总余额",align:"center",sortable:"",prop:"totalBalance"}}),a("el-table-column",{key:"remark",attrs:{label:"备注",align:"center",prop:"remark"}}),a("el-table-column",{attrs:{fixed:"right",label:"操作",align:"center",width:"260","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return 1!==t.row.userId?[a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-tickets"},on:{click:function(a){return e.handleSign(t.row)}}},[e._v("存码")]),a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-document-remove"},on:{click:function(a){return e.handleBack(t.row)}}},[e._v("取码")])]:void 0}}],null,!0)})],1),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}})],1)],1),a("el-dialog",{attrs:{title:e.title,visible:e.open,width:"600px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[a("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"卡号",prop:"card"}},[a("el-input",{attrs:{placeholder:"",disabled:!0},model:{value:e.form.card,callback:function(t){e.$set(e.form,"card",t)},expression:"form.card"}})],1),e.isMain?a("el-form-item",{attrs:{label:"现有筹码",prop:"cashBalance"}},[a("el-input",{attrs:{placeholder:"",disabled:!0},model:{value:e.form.cashBalance,callback:function(t){e.$set(e.form,"cashBalance",t)},expression:"form.cashBalance"}})],1):e._e(),e.isMain?a("el-form-item",{attrs:{label:"现有现金",prop:"chipBalance"}},[a("el-input",{attrs:{placeholder:"",disabled:!0},model:{value:e.form.chipBalance,callback:function(t){e.$set(e.form,"chipBalance",t)},expression:"form.chipBalance"}})],1):e._e(),e.isMain?e._e():a("el-form-item",{attrs:{label:"存储筹码",prop:"chipAmount"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.form.chipAmount,callback:function(t){e.$set(e.form,"chipAmount",e._n(t))},expression:"form.chipAmount"}})],1),e.isMain?e._e():a("el-form-item",{attrs:{label:"存储现金",prop:"cashAmount"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.form.cashAmount,callback:function(t){e.$set(e.form,"cashAmount",e._n(t))},expression:"form.cashAmount"}})],1),e.isMain?a("el-form-item",{attrs:{label:"取出筹码",prop:"chipAmount"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.form.chipAmount,callback:function(t){e.$set(e.form,"chipAmount",e._n(t))},expression:"form.chipAmount"}})],1):e._e(),e.isMain?a("el-form-item",{attrs:{label:"取出现金",prop:"cashAmount"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.form.cashAmount,callback:function(t){e.$set(e.form,"cashAmount",e._n(t))},expression:"form.cashAmount"}})],1):e._e(),a("el-form-item",{attrs:{label:"备注",prop:"remark"}},[a("el-input",{attrs:{type:"textarea",rows:4,placeholder:"请输入内容"},model:{value:e.form.remark,callback:function(t){e.$set(e.form,"remark",t)},expression:"form.remark"}})],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),a("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},r=[],l=(a("d81d"),a("d3b7"),a("159b"),a("a9e3"),a("b775"));function o(e){return Object(l["a"])({url:"/system/accessCode/list",method:"get",params:e})}function i(e){return Object(l["a"])({url:"/system/accessCode/total",method:"get",params:e})}function s(e){return Object(l["a"])({url:"/system/accessCode/saveCode",method:"post",data:e})}function c(e){return Object(l["a"])({url:"/system/accessCode/updateCodeFetching",method:"post",data:e})}var u={name:"AccessCode",data:function(){var e=function(e,t,a){""==t?a(new Error("请输入额度")):a()};return{isMain:!1,loading:!0,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,userList:[],userData:[],userTotal:"",memlist:{},title:"",deptOptions:void 0,open:!1,detailOpen:!1,deptName:void 0,initPassword:void 0,dateRange:[],postOptions:[],roleOptions:[],form:{},defaultProps:{children:"children",label:"label"},queryParams:{pageNum:1,pageSize:30,card:"",isAdmin:!1},rules:{chipAmount:[{type:"number",pattern:"^[0-9]*$",message:"请输入数字",trigger:"blur"},{required:!0,validator:e,trigger:"blur"}],cashAmount:[{type:"number",pattern:"^[0-9]*$",message:"请输入数字",trigger:"blur"},{required:!0,validator:e,trigger:"blur"}]}}},watch:{},created:function(){this.getList()},methods:{getList:function(){var e=this,t={pageNum:1,pageSize:30};t["isAdmin"]=0==this.queryParams.isAdmin?0:1,t["card"]=this.queryParams.card,this.loading=!0,o(t).then((function(t){e.userList=t.rows,e.total=t.total,e.loading=!1})),i(t).then((function(t){e.userTotal=t.data,e.loading=!1})),this.$delete(t,"pageNum"),this.$delete(t,"pageSize"),o(t).then((function(t){e.userData=t.rows,console.log(e.userData)}))},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!=e.length,this.multiple=!e.length},getSummaries:function(e){var t=this,a=e.columns,n=(e.data,[]);return a.forEach((function(e,a){0!==a?2!==a?3!==a?4!==a||(n[a]=t.userTotal.totalBalance):n[a]=t.userTotal.cashBalance:n[a]=t.userTotal.chipBalance:n[a]="合计"})),n},getSummaries1:function(e){var t=e.columns,a=e.data,n=[];return t.forEach((function(e,t){if(0!==t)if(1!==t)if(5!==t){var r=a.map((function(t){return Number(t[e.property])}));r.every((function(e){return isNaN(e)}))||(n[t]=r.reduce((function(e,t){var a=Number(t);if(isNaN(a)){var n=e;return n}var r=e+t;return r}),0),n[t]+="")}else n[t]="";else n[t]="";else n[t]="小计"})),n},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={card:"",userName:"",chipBalance:"",cashBalance:"",totalBalance:"",remark:""},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.dateRange=[],this.resetForm("queryForm"),this.handleQuery()},handleSign:function(e){this.reset(),this.form=Object.assign({},e),this.open=!0,this.isMain=!1,this.title="存码"},handleBack:function(e){this.reset(),this.form=Object.assign({},e),this.open=!0,this.isMain=!0,this.title="取码"},handleExport:function(){var e=this;Promise.all([a.e("chunk-61779971"),a.e("chunk-91140928")]).then(function(){var t=a("39a0"),n=t.export_json_to_excel,r=["会员卡号","姓名","已存筹码余额","已存现金余额","总余额","备注"],l=["card","userName","chipBalance","cashBalance","totalBalance","remark"],o=e.userData,i=e.formatJson(l,o);n(r,i,"存取码列表")}.bind(null,a)).catch(a.oe)},formatJson:function(e,t){return t.map((function(t){return e.map((function(e){return t[e]}))}))},handlePrint:function(){},handleDetail:function(){},submitForm:function(){var e=this;console.log(this.title),this.$refs["form"].validate((function(t){t&&("取码"==e.title?c(e.form).then((function(t){e.$modal.msgSuccess("取码成功"),e.open=!1,e.getList()})):(e.form["cardType"]=1,s(e.form).then((function(t){e.$modal.msgSuccess("取码成功"),e.open=!1,e.getList()}))))}))}}},m=u,d=(a("d2bc"),a("2877")),p=Object(d["a"])(m,n,r,!1,null,null,null);t["default"]=p.exports},d2bc:function(e,t,a){"use strict";a("3dfb")}}]);
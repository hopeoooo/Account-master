(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-d51bf420"],{"88e3":function(e,t,a){},9769:function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container washcode-managemant"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:24,xs:24}},[a("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,size:"small",inline:!0,"label-width":"68px"}},[a("el-form-item",{attrs:{label:"会员卡号",prop:"card"}},[a("el-input",{staticStyle:{width:"240px","margin-right":"20px"},attrs:{placeholder:"",clearable:""},model:{value:e.queryParams.card,callback:function(t){e.$set(e.queryParams,"card",t)},expression:"queryParams.card"}}),a("el-checkbox",{model:{value:e.queryParams.isAdmin,callback:function(t){e.$set(e.queryParams,"isAdmin",t)},expression:"queryParams.isAdmin"}},[e._v("包含子卡号")]),a("el-checkbox",{model:{value:e.queryParams.cardType,callback:function(t){e.$set(e.queryParams,"cardType",t)},expression:"queryParams.cardType"}},[e._v("过滤内部卡号")])],1),a("el-form-item",[a("el-button",{directives:[{name:"prclick",rawName:"v-prclick"}],attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("查询")]),a("el-button",{directives:[{name:"prclick",rawName:"v-prclick"}],attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],1),a("el-row",{staticClass:"mb8",attrs:{gutter:10}},[a("el-col",{attrs:{span:1.5}},[a("el-button",{attrs:{type:"danger",plain:"",icon:"el-icon-c-scale-to-original",size:"mini"},on:{click:e.handleDetail}},[e._v("批量结算")])],1),a("el-col",{attrs:{span:1.5}},[a("el-button",{attrs:{type:"warning",plain:"",icon:"el-icon-download",size:"mini"},on:{click:e.handleExport}},[e._v("导出")])],1)],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.userList,"row-class-name":e.status_change,"show-summary":"","summary-method":e.getSummaries1},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{key:"userId",attrs:{fixed:"",type:"selection",prop:"userId",width:"80",align:"center"}}),a("el-table-column",{key:"card",attrs:{label:"会员卡号",align:"center",prop:"card"}}),a("el-table-column",{key:"userName",attrs:{label:"姓名",align:"center",prop:"name"}}),a("el-table-column",{key:"status",attrs:{label:"状态",align:"center",prop:"status"},scopedSlots:e._u([{key:"default",fn:function(t){return[0==t.row.status?a("span",[e._v("正常")]):a("span",{staticStyle:{color:"red"}},[e._v("停用")])]}}])}),a("el-table-column",{key:"isSettlement",attrs:{label:"是否可结算洗码",align:"center",prop:"isSettlement"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(0==t.row.isSettlement?"否":"是"))])]}}])}),a("el-table-column",{key:"water",attrs:{label:"未结算洗码量",align:"center",sortable:"",prop:"water"}}),a("el-table-column",{key:"waterAmount",attrs:{label:"未结算洗码费",align:"center",sortable:"",prop:"waterAmount"}}),a("el-table-column",{key:"remark",attrs:{label:"备注",align:"center",prop:"remark",width:"220px","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.remark?a("span",{staticStyle:{"text-align":"center"}},[e._v(e._s(t.row.remark))]):a("span",[e._v("--")])]}}])}),a("el-table-column",{attrs:{fixed:"right",label:"操作",align:"center",width:"260","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return 1!==t.row.userId?[a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-tickets"},on:{click:function(a){return e.handleSign(t.row.card)}}},[e._v("结算")]),a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-document-remove"},on:{click:function(a){return e.handleBack(t.row.card)}}},[e._v("明细")])]:void 0}}],null,!0)})],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"summary-table",attrs:{data:e.userList,"row-class-name":e.status_change,"show-summary":"","summary-method":e.getSummaries},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{key:"userId",attrs:{fixed:"",type:"selection",prop:"userId",width:"80",align:"center"}}),a("el-table-column",{key:"card",attrs:{label:"会员卡号",align:"center",prop:"card"}}),a("el-table-column",{key:"userName",attrs:{label:"姓名",align:"center",prop:"name"}}),a("el-table-column",{key:"status",attrs:{label:"状态",align:"center",prop:"status"},scopedSlots:e._u([{key:"default",fn:function(t){return[0==t.row.status?a("span",[e._v("正常")]):a("span",{staticStyle:{color:"red"}},[e._v("停用")])]}}])}),a("el-table-column",{key:"isSettlement",attrs:{label:"是否可结算洗码",align:"center",prop:"isSettlement"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(0==t.row.isSettlement?"否":"是"))])]}}])}),a("el-table-column",{key:"water",attrs:{label:"未结算洗码量",align:"center",sortable:"",prop:"water"}}),a("el-table-column",{key:"waterAmount",attrs:{label:"未结算洗码费",align:"center",sortable:"",prop:"waterAmount"}}),a("el-table-column",{key:"remark",attrs:{label:"备注",align:"center",prop:"remark",width:"220px","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.remark?a("span",{staticStyle:{"text-align":"center"}},[e._v(e._s(t.row.remark))]):a("span",[e._v("--")])]}}])}),a("el-table-column",{attrs:{fixed:"right",label:"操作",align:"center",width:"260","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return 1!==t.row.userId?[a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-tickets"},on:{click:function(a){return e.handleSign(t.row.card)}}},[e._v("结算")]),a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-document-remove"},on:{click:function(a){return e.handleBack(t.row.card)}}},[e._v("明细")])]:void 0}}],null,!0)})],1),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total > 0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}})],1)],1),a("el-dialog",{attrs:{title:e.title,visible:e.open,width:"600px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[a("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"卡号",prop:"card"}},[a("el-input",{attrs:{placeholder:"",disabled:!0},model:{value:e.form.card,callback:function(t){e.$set(e.form,"card",t)},expression:"form.card"}})],1),e.isMain?e._e():a("el-form-item",{attrs:{label:"汇入金额",prop:"amount"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.form.amount,callback:function(t){e.$set(e.form,"amount",t)},expression:"form.amount"}})],1),e.isMain?a("el-form-item",{attrs:{label:"汇出金额",prop:"amount"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.form.amount,callback:function(t){e.$set(e.form,"amount",t)},expression:"form.amount"}})],1):e._e(),e.isMain?e._e():a("el-form-item",{attrs:{label:"获取货币",prop:"operationType"}},[a("el-radio",{attrs:{label:1},model:{value:e.form.operationType,callback:function(t){e.$set(e.form,"operationType",t)},expression:"form.operationType"}},[e._v("筹码")]),a("el-radio",{attrs:{label:2},model:{value:e.form.operationType,callback:function(t){e.$set(e.form,"operationType",t)},expression:"form.operationType"}},[e._v("现金")])],1),e.isMain?a("el-form-item",{attrs:{label:"使用货币",prop:"operationType"}},[a("el-radio",{attrs:{label:1},model:{value:e.form.operationType,callback:function(t){e.$set(e.form,"operationType",t)},expression:"form.operationType"}},[e._v("筹码")]),a("el-radio",{attrs:{label:2},model:{value:e.form.operationType,callback:function(t){e.$set(e.form,"operationType",t)},expression:"form.operationType"}},[e._v("现金")])],1):e._e(),a("el-form-item",{attrs:{label:"备注",prop:"remark"}},[a("el-input",{attrs:{type:"textarea",rows:4,placeholder:"请输入内容"},model:{value:e.form.remark,callback:function(t){e.$set(e.form,"remark",t)},expression:"form.remark"}})],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),a("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},n=[],o=(a("d81d"),a("d3b7"),a("159b"),a("a9e3"),a("b775"));function l(e){return Object(o["a"])({url:"/system/water/list",method:"get",params:e})}function i(e){return Object(o["a"])({url:"/system/water/total",method:"get",params:e})}var s={name:"WashCode",data:function(){return{isMain:!1,loading:!0,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,userList:[],userTotal:"",memlist:{},title:"",deptOptions:void 0,open:!1,detailOpen:!1,deptName:void 0,initPassword:void 0,dateRange:[],postOptions:[],roleOptions:[],form:{},defaultProps:{children:"children",label:"label"},queryParams:{pageNum:1,pageSize:30,card:"",isAdmin:0,cardType:0},rules:{}}},watch:{},created:function(){this.getList()},methods:{getList:function(){var e=this,t={pageNum:this.queryParams.pageNum,pageSize:this.queryParams.pageSize};t["card"]=this.queryParams.card,t["isAdmin"]=0==this.queryParams.isAdmin?0:1,t["cardType"]=0==this.queryParams.cardType?0:1,this.loading=!0,l(t).then((function(t){e.userList=t.rows,e.total=t.total,e.loading=!1})),i(t).then((function(t){e.userTotal=t.data,e.loading=!1})),this.$delete(t,"pageNum"),this.$delete(t,"pageSize")},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!=e.length,this.multiple=!e.length},status_change:function(e){if(e.row.signedAmount>0)return"table-info-red"},getSummaries1:function(e){var t=e.columns,a=e.data,r=[];return t.forEach((function(e,t){if(0===t)r[t]="小计";else if(5==t||6==t){var n=a.map((function(t){return Number(t[e.property])}));n.every((function(e){return isNaN(e)}))||(r[t]=n.reduce((function(e,t){var a=Number(t);if(isNaN(a)){var r=e;return r}var n=e+t;return n}),0),r[t]+="")}else r[t]=""})),r},getSummaries:function(e){var t=this,a=e.columns,r=(e.data,[]);return a.forEach((function(e,a){0===a&&(r[a]="合计"),5===a&&(r[a]=t.userTotal.water),6===a&&(r[a]=t.userTotal.waterAmount)})),r},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={card:"",amount:"",operationType:"",remark:""},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.dateRange=[],this.queryParams.isAdmin=0,this.queryParams.cardType=0,this.resetForm("queryForm"),this.handleQuery()},handleSign:function(e){this.reset(),this.form["card"]=e,this.open=!0,this.isMain=!1,this.title="汇入"},handleBack:function(e){this.reset(),this.form["card"]=e,this.open=!0,this.isMain=!0,this.title="汇出"},handleExport:function(){var e=this;Promise.all([a.e("chunk-61779971"),a.e("chunk-91140928")]).then(function(){var t=a("39a0"),r=t.export_json_to_excel,n=["会员卡号","姓名","筹码余额","是否可汇出","备注"],o=["card","userName","chipAmount","isCash","remark"],l=e.userList,i=e.formatJson(o,l);r(n,i,"买码换现列表")}.bind(null,a)).catch(a.oe)},formatJson:function(e,t){return t.map((function(t){return e.map((function(e){return t[e]}))}))},handlePrint:function(){},handleDetail:function(){},submitForm:function(){var e=this;console.log(this.title),this.$refs["form"].validate((function(t){t&&("汇出"==e.title?addRemit(e.form).then((function(t){e.$modal.msgSuccess("汇出成功"),e.open=!1,e.getList()})):addImport(e.form).then((function(t){e.$modal.msgSuccess("汇入成功"),e.open=!1,e.getList()})))}))}}},c=s,u=(a("fa7b"),a("2877")),m=Object(u["a"])(c,r,n,!1,null,null,null);t["default"]=m.exports},fa7b:function(e,t,a){"use strict";a("88e3")}}]);
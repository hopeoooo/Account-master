(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-45fd4706"],{"038f":function(t,e,a){"use strict";a.d(e,"a",(function(){return r})),a.d(e,"f",(function(){return c})),a.d(e,"c",(function(){return i})),a.d(e,"h",(function(){return l})),a.d(e,"e",(function(){return o})),a.d(e,"d",(function(){return d})),a.d(e,"b",(function(){return u})),a.d(e,"g",(function(){return s}));var n=a("b775");function r(t){return Object(n["a"])({url:"/bet/baccarat/edit",method:"post",params:t})}function c(t){return Object(n["a"])({url:"/bet/baccarat/reckon",method:"post",params:t})}function i(t){return Object(n["a"])({url:"/bet/baccarat/input",method:"post",params:t})}function l(t){return Object(n["a"])({url:"/bet/baccarat/update",method:"post",params:t})}function o(t){return Object(n["a"])({url:"/bet/baccarat/open",method:"post",params:t})}function d(t){return Object(n["a"])({url:"/bet/baccarat/game",method:"post",data:t})}function u(t){return Object(n["a"])({url:"/bet/baccarat/info",method:"post",data:t})}function s(t){return Object(n["a"])({url:"/bet/push/save",method:"post",data:t})}},"0c5b":function(t,e,a){},3250:function(t,e,a){"use strict";a.d(e,"c",(function(){return r})),a.d(e,"d",(function(){return c})),a.d(e,"a",(function(){return i})),a.d(e,"b",(function(){return l}));var n=a("b775");function r(t){return Object(n["a"])({url:"/system/table/list",method:"get",params:t})}function c(t){return Object(n["a"])({url:"/system/table/total",method:"get",params:t})}function i(t){return Object(n["a"])({url:"/system/table/addOrUpdate",method:"post",data:t})}function l(t){return Object(n["a"])({url:"/system/table/"+t,method:"delete"})}},"62d8":function(t,e,a){"use strict";a("0c5b")},f636:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",[a("h1",[t._v("百家乐")]),a("span",[t._v("桌台编号")]),a("el-select",{attrs:{placeholder:"请选择"},model:{value:t.tableId,callback:function(e){t.tableId=e},expression:"tableId"}},t._l(t.options,(function(t){return a("el-option",{key:t.tableId,attrs:{label:t.tableId,value:t.tableId}})})),1)],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"betBox",attrs:{height:"800px",data:t.betList,border:""}},[a("el-table-column",{key:"type",attrs:{label:"选择币种",align:"center",fixed:"",prop:"type",width:"155px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(0==e.row.type?"现金":1==e.row.type?"筹码":"-")+" ")]}}])}),a("el-table-column",{key:"card",attrs:{label:"卡号",align:"center",prop:"card",width:"200px"}}),a("el-table-column",{key:"card1",attrs:{label:"庄",align:"center",prop:"card1"}}),a("el-table-column",{key:"card2",attrs:{label:"闲",align:"center",prop:"card2"}}),a("el-table-column",{key:"card3",attrs:{label:"和",align:"center",prop:"card3"}}),a("el-table-column",{key:"card4",attrs:{label:"庄对",align:"center",prop:"card4"}}),a("el-table-column",{key:"card5",attrs:{label:"闲对",align:"center",prop:"card5"}}),a("el-table-column",{key:"card6",attrs:{label:"庄保险",align:"center",prop:"card6"}}),a("el-table-column",{key:"card7",attrs:{label:"闲保险",align:"center",prop:"card7"}}),a("el-table-column",{key:"card8",attrs:{label:"大",align:"center",prop:"card8"}}),a("el-table-column",{key:"card9",attrs:{label:"小",align:"center",prop:"card9"}}),a("el-table-column",{key:"chip",attrs:{label:"现有筹码",align:"center",prop:"chip",fixed:"right"}}),a("el-table-column",{attrs:{label:"赔码数",align:"center",prop:"payout",fixed:"right"}})],1)],1)},r=[],c=(a("b0c0"),a("038f"),a("3250")),i={name:"ShowBj",data:function(){return{loading:!0,isVisibles:!1,options:"",tableId:"",queryParams:{gameId:1,pageNum:1,pageSize:1e4},betList:[{id:1},{id:2},{id:3},{id:4},{id:5},{id:6},{id:7},{id:8},{id:9},{id:10},{id:11},{id:12},{id:13},{id:14},{id:15},{id:16},{id:17},{id:18},{id:19},{id:20},{id:21},{id:22},{id:23},{id:24},{id:25},{id:26},{id:27},{id:28},{id:29},{id:30}],tableInfo:"",iskaipai:!0}},components:{},watch:{},created:function(){this.getList()},computed:{userName:function(){return this.$store.state.user.name}},methods:{getList:function(){var t=this;this.loading=!0,Object(c["c"])(this.queryParams).then((function(e){t.options=e.rows,t.loading=!1}))},getTableInfo:function(){var t=this;baccaratInfo().then((function(e){t.tableInfo=e.data,t.loading=!1}),this.setBaccaratList(this.betList),this.setBaccaratSum(this.sumdata))}}},l=i,o=(a("62d8"),a("2877")),d=Object(o["a"])(l,n,r,!1,null,null,null);e["default"]=d.exports}}]);

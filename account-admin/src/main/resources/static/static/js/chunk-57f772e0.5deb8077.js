(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-57f772e0"],{3932:function(a,t,i){"use strict";i.d(t,"b",(function(){return s})),i.d(t,"a",(function(){return l}));var e=i("b775");function s(a){return Object(e["a"])({url:"/system/oddsConfig/list",method:"get",params:a})}function l(a){return Object(e["a"])({url:"/system/oddsConfig",method:"post",data:a})}},"40b6":function(a,t,i){"use strict";i.r(t);var e=function(){var a=this,t=a.$createElement,i=a._self._c||t;return i("div",{directives:[{name:"loading",rawName:"v-loading",value:a.loading,expression:"loading"}],staticClass:"app-container"},[i("el-form",{ref:"form",attrs:{model:a.oddsList,rules:a.rules,"label-width":"80px"}},[i("div",{staticClass:"title"},[a._v("赔率设置")]),i("el-row",{attrs:{gutter:20}},[i("el-col",{attrs:{span:12,xs:24}},[i("div",{staticClass:"odds-container"},[i("div",{staticStyle:{height:"60px"}},[i("span",[a._v("百家乐")])]),i("div",{staticClass:"gamebox"},[i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"庄赢抽水",prop:"baccaratPump"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratPump,callback:function(t){a.$set(a.oddsList,"baccaratPump",t)},expression:"oddsList.baccaratPump"}}),a._v("% ")],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"庄赢:  1赔",prop:"baccaratBankerWin"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratBankerWin,callback:function(t){a.$set(a.oddsList,"baccaratBankerWin",t)},expression:"oddsList.baccaratBankerWin"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"闲赢:  1赔",prop:"baccaratPlayerWin"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratPlayerWin,callback:function(t){a.$set(a.oddsList,"baccaratPlayerWin",t)},expression:"oddsList.baccaratPlayerWin"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"和赢:  1赔",prop:"baccaratTieWin"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratTieWin,callback:function(t){a.$set(a.oddsList,"baccaratTieWin",t)},expression:"oddsList.baccaratTieWin"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"庄对:  1赔",prop:"baccaratBankerPair"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratBankerPair,callback:function(t){a.$set(a.oddsList,"baccaratBankerPair",t)},expression:"oddsList.baccaratBankerPair"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"闲对:  1赔",prop:"baccaratPlayerPair"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratPlayerPair,callback:function(t){a.$set(a.oddsList,"baccaratPlayerPair",t)},expression:"oddsList.baccaratPlayerPair"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"大:  1赔",prop:"baccaratLarge"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratLarge,callback:function(t){a.$set(a.oddsList,"baccaratLarge",t)},expression:"oddsList.baccaratLarge"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"小:  1赔",prop:"baccaratSmall"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratSmall,callback:function(t){a.$set(a.oddsList,"baccaratSmall",t)},expression:"oddsList.baccaratSmall"}})],1)],1)])])]),i("el-col",{attrs:{span:12,xs:24}},[i("div",{staticClass:"odds-container"},[i("div",{staticStyle:{height:"60px"}},[i("span",[a._v("龙虎")])]),i("div",{staticClass:"gamebox"},[i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"龙赢:  1赔",prop:"dragonWin"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.dragonWin,callback:function(t){a.$set(a.oddsList,"dragonWin",t)},expression:"oddsList.dragonWin"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"虎赢:  1赔",prop:"tigerWin"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.tigerWin,callback:function(t){a.$set(a.oddsList,"tigerWin",t)},expression:"oddsList.tigerWin"}})],1)],1),i("div",{staticClass:"list"},[i("el-form-item",{attrs:{label:"和赢:  1赔",prop:"tieWin"}},[i("el-input",{staticStyle:{width:"80px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.tieWin,callback:function(t){a.$set(a.oddsList,"tieWin",t)},expression:"oddsList.tieWin"}})],1)],1)])])])],1),i("div",{staticClass:"title"},[a._v("洗码比例")]),i("el-row",{attrs:{gutter:20}},[i("el-col",{attrs:{span:6,xs:12}},[i("div",{staticClass:"listb"},[i("el-form-item",{attrs:{label:"百家乐洗码比例（筹码）",prop:"baccaratRollingRatioChip","label-width":"200px"}},[i("el-input",{staticStyle:{witdh:"200px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratRollingRatioChip,callback:function(t){a.$set(a.oddsList,"baccaratRollingRatioChip",t)},expression:"oddsList.baccaratRollingRatioChip"}},[i("template",{slot:"suffix"},[i("span",{staticStyle:{color:"#000"}},[a._v(" % ")])])],2)],1)],1)]),i("el-col",{attrs:{span:6,xs:12}},[i("div",{staticClass:"listb"},[i("el-form-item",{attrs:{label:"百家乐洗码比例（现金）",prop:"baccaratRollingRatioCash","label-width":"200px"}},[i("el-input",{staticStyle:{witdh:"200px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.baccaratRollingRatioCash,callback:function(t){a.$set(a.oddsList,"baccaratRollingRatioCash",t)},expression:"oddsList.baccaratRollingRatioCash"}},[i("template",{slot:"suffix"},[i("span",{staticStyle:{color:"#000"}},[a._v(" % ")])])],2)],1)],1)]),i("el-col",{attrs:{span:6,xs:12}},[i("div",{staticClass:"listb"},[i("el-form-item",{attrs:{label:"龙虎洗码比例（筹码）",prop:"dragonTigerRatioChip","label-width":"200px"}},[i("el-input",{staticStyle:{witdh:"200px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.dragonTigerRatioChip,callback:function(t){a.$set(a.oddsList,"dragonTigerRatioChip",t)},expression:"oddsList.dragonTigerRatioChip"}},[i("template",{slot:"suffix"},[i("span",{staticStyle:{color:"#000"}},[a._v(" % ")])])],2)],1)],1)]),i("el-col",{attrs:{span:6,xs:12}},[i("div",{staticClass:"listb"},[i("el-form-item",{attrs:{label:"龙虎洗码比例（现金）",prop:"dragonTigerRatioCash","label-width":"200px"}},[i("el-input",{staticStyle:{witdh:"200px"},attrs:{oninput:"if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"},model:{value:a.oddsList.dragonTigerRatioCash,callback:function(t){a.$set(a.oddsList,"dragonTigerRatioCash",t)},expression:"oddsList.dragonTigerRatioCash"}},[i("template",{slot:"suffix"},[i("span",{staticStyle:{color:"#000"}},[a._v(" % ")])])],2)],1)],1)])],1),i("div",{staticClass:"title"},[a._v("其它配置")]),i("el-row",{attrs:{gutter:20}},[i("el-col",{attrs:{span:6,xs:12}},[i("el-checkbox",{attrs:{"v-model":a.checked},on:{change:a.onCheckChange1}},[a._v("洗码佣金取整")])],1),i("el-col",{attrs:{span:6,xs:12}},[i("el-checkbox",{attrs:{"v-model":a.checked1},on:{change:a.onCheckChange2}},[a._v("庄赢抽水取整")])],1)],1)],1),i("div",{directives:[{name:"prclick",rawName:"v-prclick"}],staticClass:"commitOdds",on:{click:a.submit}},[a._v("保存设置")])],1)},s=[],l=(i("fb6a"),i("3932")),r={name:"odds",data:function(){return{loading:!1,checked:!1,checked1:!1,oddsList:{baccaratPump:"",baccaratBankerWin:"",baccaratPlayerWin:"",baccaratTieWin:"",baccaratBankerPair:"",baccaratPlayerPair:"",baccaratLarge:"",baccaratSmall:"",dragonWin:"",tigerWin:"",tieWin:"",baccaratRollingRatioChip:"",baccaratRollingRatioCash:"",dragonTigerRatioChip:"",dragonTigerRatioCash:"",rollingCommissionRounding:0,bankerWinPumpRounding:0},rules:{baccaratPump:[{required:!0,message:"庄赢抽水比例不能为空",trigger:"blur"},{validator:this.perValidator,message:"只能输入0-100"}],baccaratBankerWin:[{required:!0,message:"不能为空",trigger:"blur"}],baccaratPlayerWin:[{required:!0,message:"不能为空",trigger:"blur"}],baccaratTieWin:[{required:!0,message:"不能为空",trigger:"blur"}],baccaratBankerPair:[{required:!0,message:"不能为空",trigger:"blur"}],baccaratPlayerPair:[{required:!0,message:"不能为空",trigger:"blur"}],baccaratLarge:[{required:!0,message:"不能为空",trigger:"blur"}],baccaratSmall:[{required:!0,message:"不能为空",trigger:"blur"}],dragonWin:[{required:!0,message:"不能为空",trigger:"blur"}],tigerWin:[{required:!0,message:"百家乐洗码比例（筹码）不能为空",trigger:"blur"}],tieWin:[{required:!0,message:"不能为空",trigger:"blur"}],baccaratRollingRatioChip:[{required:!0,message:"百家乐洗码比例（筹码）不能为空",trigger:"blur"},{validator:this.perValidator,message:"只能输入0-100",trigger:"blur"}],baccaratRollingRatioCash:[{required:!0,message:"百家乐洗码比例（现金）不能为空",trigger:"blur"},{validator:this.perValidator,message:"只能输入0-100",trigger:"blur"}],dragonTigerRatioChip:[{required:!0,message:"龙虎洗码比例（筹码）不能为空",trigger:"blur"},{validator:this.perValidator,message:"只能输入0-100",trigger:"blur"}],dragonTigerRatioCash:[{required:!0,message:"龙虎洗码比例（现金）不能为空",trigger:"blur"},{validator:this.perValidator,message:"只能输入0-100",trigger:"blur"}]}}},created:function(){this.getList()},methods:{perValidator:function(a,t,i){t>100||t<0?i(new Error("只能输入0-100")):i()},numberValitor:function(a,t,i){t<0?i(new Error("请输入大于0的数字")):i()},onInputChange:function(a){return isNaN(a)&&(a=null),a.indexOf(".")>0&&(a=a.slice(0,a.indexOf(".")+3)),a},getList:function(){var a=this;this.loading=!0,Object(l["b"])().then((function(t){a.oddsList=t.data,0==a.oddsList.rollingCommissionRounding?a.checked=!1:a.checked=!0,0==a.oddsList.bankerWinPumpRounding?a.checked1=!1:a.checked1=!0,a.loading=!1}))},submit:function(){var a=this;this.$refs["form"].validate((function(t){t&&(a.loading=!0,a.oddsList.baccaratPump=parseInt(a.oddsList.baccaratPump),a.oddsList.baccaratRollingRatioChip=parseInt(a.oddsList.baccaratRollingRatioChip),a.oddsList.baccaratRollingRatioCash=parseInt(a.oddsList.baccaratRollingRatioCash),a.oddsList.dragonTigerRatioChip=parseInt(a.oddsList.dragonTigerRatioChip),a.oddsList.dragonTigerRatioCash=parseInt(a.oddsList.dragonTigerRatioCash),Object(l["a"])(a.oddsList).then((function(t){a.$modal.msgSuccess("保存成功"),a.loading=!1})).catch((function(){})))}))},onCheckChange1:function(a){this.oddsList.rollingCommissionRounding=0==a?0:1},onCheckChange2:function(a){this.oddsList.bankerWinPumpRounding=0==a?0:1}}},n=r,o=(i("6aef"),i("2877")),c=Object(o["a"])(n,e,s,!1,null,null,null);t["default"]=c.exports},"6aef":function(a,t,i){"use strict";i("bb9d")},bb9d:function(a,t,i){}}]);
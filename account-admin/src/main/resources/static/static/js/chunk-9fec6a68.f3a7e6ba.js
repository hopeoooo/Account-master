(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-9fec6a68"],{"29ea":function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"app-container"},[r("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,size:"small",inline:!0}},[r("el-form-item",{attrs:{label:"角色名称",prop:"roleName"}},[r("el-input",{staticStyle:{width:"240px"},attrs:{placeholder:"请输入角色名称",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.roleName,callback:function(t){e.$set(e.queryParams,"roleName",t)},expression:"queryParams.roleName"}})],1),r("el-form-item",{attrs:{label:"权限字符",prop:"roleKey"}},[r("el-input",{staticStyle:{width:"240px"},attrs:{placeholder:"请输入权限字符",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.roleKey,callback:function(t){e.$set(e.queryParams,"roleKey",t)},expression:"queryParams.roleKey"}})],1),r("el-form-item",{attrs:{label:"状态",prop:"status"}},[r("el-select",{staticStyle:{width:"240px"},attrs:{placeholder:"角色状态",clearable:""},model:{value:e.queryParams.status,callback:function(t){e.$set(e.queryParams,"status",t)},expression:"queryParams.status"}},e._l(e.dict.type.sys_normal_disable,(function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),r("el-form-item",{attrs:{label:"创建时间"}},[r("el-date-picker",{staticStyle:{width:"240px"},attrs:{"value-format":"yyyy-MM-dd",type:"daterange","range-separator":"-","start-placeholder":"开始日期","end-placeholder":"结束日期"},model:{value:e.dateRange,callback:function(t){e.dateRange=t},expression:"dateRange"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")]),r("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],1),r("el-row",{staticClass:"mb8",attrs:{gutter:10}},[r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:add"],expression:"['system:role:add']"}],attrs:{type:"primary",plain:"",icon:"el-icon-plus",size:"mini"},on:{click:e.handleAdd}},[e._v("新增")])],1),r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:edit"],expression:"['system:role:edit']"}],attrs:{type:"success",plain:"",icon:"el-icon-edit",size:"mini",disabled:e.single},on:{click:e.handleUpdate}},[e._v("修改")])],1),r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:remove"],expression:"['system:role:remove']"}],attrs:{type:"danger",plain:"",icon:"el-icon-delete",size:"mini",disabled:e.multiple},on:{click:e.handleDelete}},[e._v("删除")])],1),r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:export"],expression:"['system:role:export']"}],attrs:{type:"warning",plain:"",icon:"el-icon-download",size:"mini"},on:{click:e.handleExport}},[e._v("导出")])],1),r("right-toolbar",{attrs:{showSearch:e.showSearch},on:{"update:showSearch":function(t){e.showSearch=t},"update:show-search":function(t){e.showSearch=t},queryTable:e.getList}})],1),r("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.roleList},on:{"selection-change":e.handleSelectionChange}},[r("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),r("el-table-column",{attrs:{label:"角色编号",prop:"roleId",width:"120"}}),r("el-table-column",{attrs:{label:"角色名称",prop:"roleName","show-overflow-tooltip":!0,width:"150"}}),r("el-table-column",{attrs:{label:"权限字符",prop:"roleKey","show-overflow-tooltip":!0,width:"150"}}),r("el-table-column",{attrs:{label:"显示顺序",prop:"roleSort",width:"100"}}),r("el-table-column",{attrs:{label:"状态",align:"center",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-switch",{attrs:{"active-value":"0","inactive-value":"1"},on:{change:function(r){return e.handleStatusChange(t.row)}},model:{value:t.row.status,callback:function(r){e.$set(t.row,"status",r)},expression:"scope.row.status"}})]}}])}),r("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(e.parseTime(t.row.createTime)))])]}}])}),r("el-table-column",{attrs:{label:"操作",align:"center","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return 1!==t.row.roleId?[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:edit"],expression:"['system:role:edit']"}],attrs:{size:"mini",type:"text",icon:"el-icon-edit"},on:{click:function(r){return e.handleUpdate(t.row)}}},[e._v("修改")]),r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:remove"],expression:"['system:role:remove']"}],attrs:{size:"mini",type:"text",icon:"el-icon-delete"},on:{click:function(r){return e.handleDelete(t.row)}}},[e._v("删除")]),r("el-dropdown",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:edit"],expression:"['system:role:edit']"}],attrs:{size:"mini"},on:{command:function(r){return e.handleCommand(r,t.row)}}},[r("span",{staticClass:"el-dropdown-link"},[r("i",{staticClass:"el-icon-d-arrow-right el-icon--right"}),e._v("更多 ")]),r("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[r("el-dropdown-item",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:edit"],expression:"['system:role:edit']"}],attrs:{command:"handleDataScope",icon:"el-icon-circle-check"}},[e._v("数据权限")]),r("el-dropdown-item",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:role:edit"],expression:"['system:role:edit']"}],attrs:{command:"handleAuthUser",icon:"el-icon-user"}},[e._v("分配用户")])],1)],1)]:void 0}}],null,!0)})],1),r("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}}),r("el-dialog",{attrs:{title:e.title,visible:e.open,width:"500px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[r("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"100px"}},[r("el-form-item",{attrs:{label:"角色名称",prop:"roleName"}},[r("el-input",{attrs:{placeholder:"请输入角色名称"},model:{value:e.form.roleName,callback:function(t){e.$set(e.form,"roleName",t)},expression:"form.roleName"}})],1),r("el-form-item",{attrs:{prop:"roleKey"}},[r("span",{attrs:{slot:"label"},slot:"label"},[r("el-tooltip",{attrs:{content:"控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)",placement:"top"}},[r("i",{staticClass:"el-icon-question"})]),e._v(" 权限字符 ")],1),r("el-input",{attrs:{placeholder:"请输入权限字符"},model:{value:e.form.roleKey,callback:function(t){e.$set(e.form,"roleKey",t)},expression:"form.roleKey"}})],1),r("el-form-item",{attrs:{label:"角色顺序",prop:"roleSort"}},[r("el-input-number",{attrs:{"controls-position":"right",min:0},model:{value:e.form.roleSort,callback:function(t){e.$set(e.form,"roleSort",t)},expression:"form.roleSort"}})],1),r("el-form-item",{attrs:{label:"状态"}},[r("el-radio-group",{model:{value:e.form.status,callback:function(t){e.$set(e.form,"status",t)},expression:"form.status"}},e._l(e.dict.type.sys_normal_disable,(function(t){return r("el-radio",{key:t.value,attrs:{label:t.value}},[e._v(e._s(t.label))])})),1)],1),r("el-form-item",{attrs:{label:"菜单权限"}},[r("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeExpand(t,"menu")}},model:{value:e.menuExpand,callback:function(t){e.menuExpand=t},expression:"menuExpand"}},[e._v("展开/折叠")]),r("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeNodeAll(t,"menu")}},model:{value:e.menuNodeAll,callback:function(t){e.menuNodeAll=t},expression:"menuNodeAll"}},[e._v("全选/全不选")]),r("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeConnect(t,"menu")}},model:{value:e.form.menuCheckStrictly,callback:function(t){e.$set(e.form,"menuCheckStrictly",t)},expression:"form.menuCheckStrictly"}},[e._v("父子联动")]),r("el-tree",{ref:"menu",staticClass:"tree-border",attrs:{data:e.menuOptions,"show-checkbox":"","node-key":"id","check-strictly":!e.form.menuCheckStrictly,"empty-text":"加载中，请稍候",props:e.defaultProps}})],1),r("el-form-item",{attrs:{label:"备注"}},[r("el-input",{attrs:{type:"textarea",placeholder:"请输入内容"},model:{value:e.form.remark,callback:function(t){e.$set(e.form,"remark",t)},expression:"form.remark"}})],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),r("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1),r("el-dialog",{attrs:{title:e.title,visible:e.openDataScope,width:"500px","append-to-body":""},on:{"update:visible":function(t){e.openDataScope=t}}},[r("el-form",{attrs:{model:e.form,"label-width":"80px"}},[r("el-form-item",{attrs:{label:"角色名称"}},[r("el-input",{attrs:{disabled:!0},model:{value:e.form.roleName,callback:function(t){e.$set(e.form,"roleName",t)},expression:"form.roleName"}})],1),r("el-form-item",{attrs:{label:"权限字符"}},[r("el-input",{attrs:{disabled:!0},model:{value:e.form.roleKey,callback:function(t){e.$set(e.form,"roleKey",t)},expression:"form.roleKey"}})],1),r("el-form-item",{attrs:{label:"权限范围"}},[r("el-select",{on:{change:e.dataScopeSelectChange},model:{value:e.form.dataScope,callback:function(t){e.$set(e.form,"dataScope",t)},expression:"form.dataScope"}},e._l(e.dataScopeOptions,(function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),r("el-form-item",{directives:[{name:"show",rawName:"v-show",value:2==e.form.dataScope,expression:"form.dataScope == 2"}],attrs:{label:"数据权限"}},[r("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeExpand(t,"dept")}},model:{value:e.deptExpand,callback:function(t){e.deptExpand=t},expression:"deptExpand"}},[e._v("展开/折叠")]),r("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeNodeAll(t,"dept")}},model:{value:e.deptNodeAll,callback:function(t){e.deptNodeAll=t},expression:"deptNodeAll"}},[e._v("全选/全不选")]),r("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeConnect(t,"dept")}},model:{value:e.form.deptCheckStrictly,callback:function(t){e.$set(e.form,"deptCheckStrictly",t)},expression:"form.deptCheckStrictly"}},[e._v("父子联动")]),r("el-tree",{ref:"dept",staticClass:"tree-border",attrs:{data:e.deptOptions,"show-checkbox":"","default-expand-all":"","node-key":"id","check-strictly":!e.form.deptCheckStrictly,"empty-text":"加载中，请稍候",props:e.defaultProps}})],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"primary"},on:{click:e.submitDataScope}},[e._v("确 定")]),r("el-button",{on:{click:e.cancelDataScope}},[e._v("取 消")])],1)],1)],1)},a=[],o=r("5530"),l=(r("d81d"),r("d3b7"),r("159b"),r("3528")),s=r("a6dc"),i=r("fcb7"),c={name:"niuniu",dicts:["sys_normal_disable"],data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,roleList:[],title:"",open:!1,openDataScope:!1,menuExpand:!1,menuNodeAll:!1,deptExpand:!0,deptNodeAll:!1,dateRange:[],dataScopeOptions:[{value:"1",label:"全部数据权限"},{value:"2",label:"自定数据权限"},{value:"3",label:"本部门数据权限"},{value:"4",label:"本部门及以下数据权限"},{value:"5",label:"仅本人数据权限"}],menuOptions:[],deptOptions:[],queryParams:{pageNum:1,pageSize:10,roleName:void 0,roleKey:void 0,status:void 0},form:{},defaultProps:{children:"children",label:"label"},rules:{roleName:[{required:!0,message:"角色名称不能为空",trigger:"blur"}],roleKey:[{required:!0,message:"权限字符不能为空",trigger:"blur"}],roleSort:[{required:!0,message:"角色顺序不能为空",trigger:"blur"}]}}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.loading=!0,Object(l["j"])(this.addDateRange(this.queryParams,this.dateRange)).then((function(t){e.roleList=t.rows,e.total=t.total,e.loading=!1}))},getMenuTreeselect:function(){var e=this;Object(s["f"])().then((function(t){e.menuOptions=t.data}))},getDeptTreeselect:function(){var e=this;Object(i["g"])().then((function(t){e.deptOptions=t.data}))},getMenuAllCheckedKeys:function(){var e=this.$refs.menu.getCheckedKeys(),t=this.$refs.menu.getHalfCheckedKeys();return e.unshift.apply(e,t),e},getDeptAllCheckedKeys:function(){var e=this.$refs.dept.getCheckedKeys(),t=this.$refs.dept.getHalfCheckedKeys();return e.unshift.apply(e,t),e},getRoleMenuTreeselect:function(e){var t=this;return Object(s["e"])(e).then((function(e){return t.menuOptions=e.menus,e}))},getRoleDeptTreeselect:function(e){var t=this;return Object(i["f"])(e).then((function(e){return t.deptOptions=e.depts,e}))},handleStatusChange:function(e){var t=this,r="0"===e.status?"启用":"停用";this.$modal.confirm('确认要"'+r+'""'+e.roleName+'"角色吗？').then((function(){return Object(l["f"])(e.roleId,e.status)})).then((function(){t.$modal.msgSuccess(r+"成功")})).catch((function(){e.status="0"===e.status?"1":"0"}))},cancel:function(){this.open=!1,this.reset()},cancelDataScope:function(){this.openDataScope=!1,this.reset()},reset:function(){void 0!=this.$refs.menu&&this.$refs.menu.setCheckedKeys([]),this.menuExpand=!1,this.menuNodeAll=!1,this.deptExpand=!0,this.deptNodeAll=!1,this.form={roleId:void 0,roleName:void 0,roleKey:void 0,roleSort:0,status:"0",menuIds:[],deptIds:[],menuCheckStrictly:!0,deptCheckStrictly:!0,remark:void 0},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.dateRange=[],this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.roleId})),this.single=1!=e.length,this.multiple=!e.length},handleCommand:function(e,t){switch(e){case"handleDataScope":this.handleDataScope(t);break;case"handleAuthUser":this.handleAuthUser(t);break;default:break}},handleCheckedTreeExpand:function(e,t){if("menu"==t)for(var r=this.menuOptions,n=0;n<r.length;n++)this.$refs.menu.store.nodesMap[r[n].id].expanded=e;else if("dept"==t)for(var a=this.deptOptions,o=0;o<a.length;o++)this.$refs.dept.store.nodesMap[a[o].id].expanded=e},handleCheckedTreeNodeAll:function(e,t){"menu"==t?this.$refs.menu.setCheckedNodes(e?this.menuOptions:[]):"dept"==t&&this.$refs.dept.setCheckedNodes(e?this.deptOptions:[])},handleCheckedTreeConnect:function(e,t){"menu"==t?this.form.menuCheckStrictly=!!e:"dept"==t&&(this.form.deptCheckStrictly=!!e)},handleAdd:function(){this.reset(),this.getMenuTreeselect(),this.open=!0,this.title="添加角色"},handleUpdate:function(e){var t=this;this.reset();var r=e.roleId||this.ids,n=this.getRoleMenuTreeselect(r);Object(l["i"])(r).then((function(e){t.form=e.data,t.open=!0,t.$nextTick((function(){n.then((function(e){var r=e.checkedKeys;r.forEach((function(e){t.$nextTick((function(){t.$refs.menu.setChecked(e,!0,!1)}))}))}))})),t.title="修改角色"}))},dataScopeSelectChange:function(e){"2"!==e&&this.$refs.dept.setCheckedKeys([])},handleDataScope:function(e){var t=this;this.reset();var r=this.getRoleDeptTreeselect(e.roleId);Object(l["i"])(e.roleId).then((function(e){t.form=e.data,t.openDataScope=!0,t.$nextTick((function(){r.then((function(e){t.$refs.dept.setCheckedKeys(e.checkedKeys)}))})),t.title="分配数据权限"}))},handleAuthUser:function(e){var t=e.roleId;this.$router.push("/system/role-auth/user/"+t)},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(void 0!=e.form.roleId?(e.form.menuIds=e.getMenuAllCheckedKeys(),Object(l["l"])(e.form).then((function(t){e.$modal.msgSuccess("修改成功"),e.open=!1,e.getList()}))):(e.form.menuIds=e.getMenuAllCheckedKeys(),Object(l["a"])(e.form).then((function(t){e.$modal.msgSuccess("新增成功"),e.open=!1,e.getList()}))))}))},submitDataScope:function(){var e=this;void 0!=this.form.roleId&&(this.form.deptIds=this.getDeptAllCheckedKeys(),Object(l["g"])(this.form).then((function(t){e.$modal.msgSuccess("修改成功"),e.openDataScope=!1,e.getList()})))},handleDelete:function(e){var t=this,r=e.roleId||this.ids;this.$modal.confirm('是否确认删除角色编号为"'+r+'"的数据项？').then((function(){return Object(l["h"])(r)})).then((function(){t.getList(),t.$modal.msgSuccess("删除成功")})).catch((function(){}))},handleExport:function(){this.download("system/role/export",Object(o["a"])({},this.queryParams),"role_".concat((new Date).getTime(),".xlsx"))}}},u=c,d=r("2877"),m=Object(d["a"])(u,n,a,!1,null,null,null);t["default"]=m.exports},3528:function(e,t,r){"use strict";r.d(t,"j",(function(){return a})),r.d(t,"i",(function(){return o})),r.d(t,"a",(function(){return l})),r.d(t,"l",(function(){return s})),r.d(t,"g",(function(){return i})),r.d(t,"f",(function(){return c})),r.d(t,"h",(function(){return u})),r.d(t,"b",(function(){return d})),r.d(t,"k",(function(){return m})),r.d(t,"c",(function(){return p})),r.d(t,"d",(function(){return h})),r.d(t,"e",(function(){return f}));var n=r("b775");function a(e){return Object(n["a"])({url:"/system/role/list",method:"get",params:e})}function o(e){return Object(n["a"])({url:"/system/role/"+e,method:"get"})}function l(e){return Object(n["a"])({url:"/system/role",method:"post",data:e})}function s(e){return Object(n["a"])({url:"/system/role",method:"put",data:e})}function i(e){return Object(n["a"])({url:"/system/role/dataScope",method:"put",data:e})}function c(e,t){var r={roleId:e,status:t};return Object(n["a"])({url:"/system/role/changeStatus",method:"put",data:r})}function u(e){return Object(n["a"])({url:"/system/role/"+e,method:"delete"})}function d(e){return Object(n["a"])({url:"/system/role/authUser/allocatedList",method:"get",params:e})}function m(e){return Object(n["a"])({url:"/system/role/authUser/unallocatedList",method:"get",params:e})}function p(e){return Object(n["a"])({url:"/system/role/authUser/cancel",method:"put",data:e})}function h(e){return Object(n["a"])({url:"/system/role/authUser/cancelAll",method:"put",params:e})}function f(e){return Object(n["a"])({url:"/system/role/authUser/selectAll",method:"put",params:e})}},a6dc:function(e,t,r){"use strict";r.d(t,"d",(function(){return a})),r.d(t,"c",(function(){return o})),r.d(t,"f",(function(){return l})),r.d(t,"e",(function(){return s})),r.d(t,"a",(function(){return i})),r.d(t,"g",(function(){return c})),r.d(t,"b",(function(){return u}));var n=r("b775");function a(e){return Object(n["a"])({url:"/system/menu/list",method:"get",params:e})}function o(e){return Object(n["a"])({url:"/system/menu/"+e,method:"get"})}function l(){return Object(n["a"])({url:"/system/menu/treeselect",method:"get"})}function s(e){return Object(n["a"])({url:"/system/menu/roleMenuTreeselect/"+e,method:"get"})}function i(e){return Object(n["a"])({url:"/system/menu",method:"post",data:e})}function c(e){return Object(n["a"])({url:"/system/menu",method:"put",data:e})}function u(e){return Object(n["a"])({url:"/system/menu/"+e,method:"delete"})}},fcb7:function(e,t,r){"use strict";r.d(t,"d",(function(){return a})),r.d(t,"e",(function(){return o})),r.d(t,"c",(function(){return l})),r.d(t,"g",(function(){return s})),r.d(t,"f",(function(){return i})),r.d(t,"a",(function(){return c})),r.d(t,"h",(function(){return u})),r.d(t,"b",(function(){return d}));var n=r("b775");function a(e){return Object(n["a"])({url:"/system/dept/list",method:"get",params:e})}function o(e){return Object(n["a"])({url:"/system/dept/list/exclude/"+e,method:"get"})}function l(e){return Object(n["a"])({url:"/system/dept/"+e,method:"get"})}function s(){return Object(n["a"])({url:"/system/dept/treeselect",method:"get"})}function i(e){return Object(n["a"])({url:"/system/dept/roleDeptTreeselect/"+e,method:"get"})}function c(e){return Object(n["a"])({url:"/system/dept",method:"post",data:e})}function u(e){return Object(n["a"])({url:"/system/dept",method:"put",data:e})}function d(e){return Object(n["a"])({url:"/system/dept/"+e,method:"delete"})}}}]);
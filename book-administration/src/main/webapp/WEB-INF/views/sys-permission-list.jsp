<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table class="easyui-treegrid" id="PerParamList" title="权限列表"
	data-options="singleSelect:false,collapsible:true,url : '/permission/listAll',
						method : 'get',
						queryParams : {
								'id' : '0'
							},
						treeField : 'permissionName',
						idField : 'id'">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:60">ID</th>
			<th data-options="field:'permissionName',width:200">权限名称</th>
			<th data-options="field:'permission',width:200">权限标识</th>
			<th
				data-options="field:'parentId',width:80,formatter:KindEditorUtil.formatpermission">父级权限</th>
			<th
				data-options="field:'isParent',width:80,formatter:KindEditorUtil.isparent">是否为父权限</th>
			<th
				data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
			<th
				data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
		</tr>
	</thead>
</table>
<div id="PERDIV_toolbar">
	权限名:<input id="per" type="text" style="width: 100px"> <a
		href="#" class="easyui-linkbutton" iconCls="icon-search"
		style="width: 80px" onclick="SearchPer()">查询</a> </select>
</div>
<div id="permissonEditWindow" class="easyui-window" title="编辑权限信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/sys-permission-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>

$.get("/check/adminquery",  function(data) {
	if (data.status == 201) {
		$.messager.alert("提示", "没有权限或系统维护!");
	}
	else{}
}); 	

//var date=$('#PerParamList').datagrid('getData');
	//alert(date);
	function getSelectionscatIds() {
		var itemList = $("#PerParamList");
		var sels = itemList.datagrid("getSelections");
		////console.log(sels);
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		//将数组拼接成串 1,2,3,4,5
		ids = ids.join(",");
		return ids;
	}
	/* $('#PerParamList')
	.pagination({
		
		}) */
	function Children(id) {
		var children = $('#PerParamList').treegrid('getChildren', id);
		return children;
	}
	
	function includes(arr1, arr2) {
		  return arr2.every(val => arr1.includes(val));
		}
	
	function SearchPer(){
		$.get("/check/adminquery",  function(data) {
			if (data.status == 201) {
				$.messager.alert("提示", "没有权限或系统维护!");
			}
			else{
				var ss=$('#per').val();
		 		console.log(ss);
		 		if(ss!=''){
		 		$.get("/permission/select",{name : ss} ,
						function(data) {
							$('#PerParamList').treegrid('loadData',data);
						});
		 		}else{
		 			$.get("/permission/listAll",{id : 0} ,
		 					function(data) {
		 						$('#PerParamList').treegrid('loadData',data);
		 					});
		 	 		}
				}
		}); 
			
 			}

	
	$('#PerParamList')
			.treegrid(
					{
						onSelect : function(rowIndex, rowData) {
							
								var id=$("#PerParamList").datagrid("getSelections");
							if(rowIndex.isParent && rowIndex.parentId ==0){
							//console.log(rowIndex);
							var father = $('#PerParamList').treegrid('find',
									rowIndex.id);
							var children = $('#PerParamList').treegrid(
									'getChildren', rowIndex.id);
							var ids = [];
							for ( var i in children) {
								ids.push(children[i].id);
							if(id.indexOf(children[i])==-1){
								  $('#PerParamList').treegrid('select',
										children[i].id)  
							}
							}
							////console.log(father.checked);
							}else if((rowIndex.isParent && rowIndex.parentId!=0)||!rowIndex.isParent)
								{
								var parentid=rowIndex.parentId;
								var childern=$('#PerParamList').treegrid(
										'getChildren', parentid);

								var child = $('#PerParamList').treegrid(
										'getChildren', rowIndex.id);
								var ids = [];
								for ( var i in child) {
									ids.push(child[i].id);
								if(id.indexOf(child[i])==-1){
									  $('#PerParamList').treegrid('select',
											  child[i].id)  
								
								}}
								if(includes(id,childern)){
								 	 $('#PerParamList').treegrid('select',
											parentid); 
									}
								}
								
						},
						onUnselect : function(rowIndex, rowData) {
							if(Children(rowIndex.id)!=null){
								var ids=$("#PerParamList").datagrid("getSelections");
								var children = Children(rowIndex.id);
								if(includes(ids, children)&&children.length!=0){
								////console.log('1');
							var children = Children(rowIndex.id);
							for ( var i in children) {
								$('#PerParamList').treegrid('unselect',
										children[i].id); 
							}}else{
								////console.log('2');
								var father = $('#PerParamList').treegrid(
										'getParent', rowIndex.id);
								if (father != null) {
								////console.log("f  "+father.id);
									var ids=$("#PerParamList").datagrid("getSelections");
									var children =Children(father.id);
								/* 	//console.log(ids); 
									//console.log(children); */
									////console.log(includes(ids, children));
									if(!includes(ids, children)){
									////console.log("2");
										 $('#PerParamList').treegrid('unselect',
												father.id); 
								}}
								}}

						},
						onExpand : function(row) {
							var select = $('#PerParamList').treegrid(
									'getSelections');
							for ( var i in select) {
								////console.log(select);
								if (select[i] != null) {
									if (select[i].id == row.id) {
										var children = $('#PerParamList')
												.treegrid('getChildren', row.id);
										for ( var i in children) {
											$('#PerParamList').treegrid(
													'select', children[i].id)
										}
									}
								}
							}
						},
						toolbar : [
								{
									text : '新增',
									iconCls : 'icon-add',
									handler : function() {
										$.get("/check/adminadd",  function(data) {
											if (data.status == 201) {
												$.messager.alert("提示", "没有权限或系统维护!");
											}
											else{
										$(".tree-title:contains('系统权限新增')").parent().click();
												}
										}); 
									}
								},

								{
									text : '编辑',
									iconCls : 'icon-edit',
									handler : function() {

										$.get("/check/adminupdate",  function(data) {
											if (data.status == 201) {
												$.messager.alert("提示", "没有权限或系统维护!");
											}
											else{
												//获取用户选中的数据
												var ids = getSelectionscatIds();
												if (ids.length == 0) {
													$.messager.alert('提示', '必须选择一个记录才能编辑!');
													return;
												}

												$("#permissonEditWindow")
														.window(
																{
																	onLoad : function() {
																		//回显数据
																		var data = $("#PerParamList").treegrid(
																				"getSelections")[0];
																		////console.log(data);
																		$("#permissonEditWindow").form("load",
																				data);
																		$.get(
																						"/permission/setparent",
																						{
																							parent : data.parentId
																						},
																						function(result,
																								status, xhr) {
																						});
																	}
																}).window("open");
												}
										}); 	
										
										
									}

								},
								{
									text : '删除',
									iconCls : 'icon-cancel',
									handler : function() {

										$.get("/check/admindelete",  function(data) {
											if (data.status == 201) {
												$.messager.alert("提示", "没有权限或系统维护!");
											}
											else{		
												var ids = getSelectionscatIds();
												var ss = ids.split(",");
												////console.log(ss);

												if (ids.length == 0) {
													$.messager.alert('提示', '未选中权限!');
													return;
												}
												$.messager
														.confirm(
																'确认',
																'确定删除ID为 ' + ids
																		+ ' 的权限吗？',
																function(r) {
																	if (r) {
																		var params = {
																			"ids" : ids
																		};
																		$
																				.post(
																						"/permission/delete",
																						params,
																						function(
																								data) {
																							if (data.status == 200) {
																								for ( var i in ss) {
																									$(
																											'#PerParamList')
																											.treegrid(
																													'unselect',
																													ss[i]);
																								}

																								$.messager
																										.alert(
																												'提示',
																												'删除权限成功!',
																												undefined,
																												function() {
																													//console.log(ids);
																													$(
																															"#PerParamList")
																															.treegrid(
																																	"reload");
																												});
																							}
																						});
																	}
																});
												}
										}); 	
								
									}
								},
								{
									text : '重置/刷新',
									iconCls : 'icon-reload',
									handler : function() {
										$("#PerParamList").treegrid("reload");
										//var pg = $('#PerParamList').treegrid("getPanel");
										//$('#pg').panel('open').panel('refresh');
										//$.parser.parse();
										$("#PerParamList").treegrid(
												"clearSelections");
										document.getElementById("per").value="";
									}
								},{
									text : document.getElementById('PERDIV_toolbar')
								} 
								
								]

					}

			)
	var pg = $('#PerParamList').treegrid("getPager");

</script>
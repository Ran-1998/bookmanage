<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table class="easyui-treegrid" id="itemParamList" title="图书类别列表"
	data-options="singleSelect:false,collapsible:true,url : '/book/cat/listAll',
						method : 'get',
						queryParams : {
								'id' : '0'
							},
						treeField : 'name',
						idField : 'id'">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:60">ID</th>
			<th data-options="field:'name',width:200">类别名称</th>
			<th
				data-options="field:'parentId',width:80,formatter:KindEditorUtil.findCatName">父级目录ID</th>
			<th
				data-options="field:'isParent',width:80,formatter:KindEditorUtil.isparent">是否为父类别</th>
			<th
				data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
			<th
				data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
		</tr>
	</thead>
</table>
<div id="CATDIV_toolbar">
	类别名:<input id="catname" type="text" style="width: 100px"> <a
		href="#" class="easyui-linkbutton" iconCls="icon-search"
		style="width: 80px" onclick="SearchCat()">查询</a> </select>
</div>
<div id="catEditWindow" class="easyui-window" title="编辑类目信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/book-cat-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>
$.get("/book/cat/listAll",  function(data) {
	if (data.status == 201) {
		$.messager.alert("提示", "没有权限或系统维护!");
	} 
}); 	
var bcindex = 0;
function error() {
	bcindex = bcindex + 1;
	if (bcindex == 2) {
		$.messager.alert('提示', '没有权限或系统维护');
	}
}
$('#itemParamList').datagrid({
	onLoadError: function(){
		$.messager.alert('提示', '没有权限或系统维护!');
	}
});

	function getSelectionscatIds() {
		var itemList = $("#itemParamList");
		var sels = itemList.datagrid("getSelections");
		//console.log(sels);
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		//将数组拼接成串 1,2,3,4,5
		ids = ids.join(",");
		return ids;
	}
	/* $('#itemParamList')
	.pagination({
		
		}) */
	function Children(id) {
		var children = $('#itemParamList').treegrid('getChildren', id);
		return children;
	}
	
	function includes(arr1, arr2) {
		  return arr2.every(val => arr1.includes(val));
		}
	
	function SearchCat(){
		var ss=$('#catname').val();
 		console.log(ss);
 		if(ss!=''){
 		$.get("/book/cat/select",{name : ss} ,
				function(data) {
					$('#itemParamList').treegrid('loadData',data);
				});
 		}else{
 			$.get("/book/cat/listAll",{id : 0} ,
 					function(data) {
 						$('#itemParamList').treegrid('loadData',data);
 					});
 	 		}
 		
		}

	
	$('#itemParamList')
			.treegrid(
					{
						onSelect : function(rowIndex, rowData) {
							//console.log(rowIndex);
							/* console.log(!rowIndex.isParent);
							console.log(rowIndex.isParent && rowIndex.parentId!=0); */
								var id=$("#itemParamList").datagrid("getSelections");
							if(rowIndex.isParent && rowIndex.parentId ==0){
							console.log(rowIndex);
							var father = $('#itemParamList').treegrid('find',
									rowIndex.id);
							var children = $('#itemParamList').treegrid(
									'getChildren', rowIndex.id);
							var ids = [];
							for ( var i in children) {
								ids.push(children[i].id);
							if(id.indexOf(children[i])==-1){
								  $('#itemParamList').treegrid('select',
										children[i].id)  
							}
							}
							//console.log(father.checked);
							}else if((rowIndex.isParent && rowIndex.parentId!=0)||!rowIndex.isParent)
								{
								var parentid=rowIndex.parentId;
								var childern=$('#itemParamList').treegrid(
										'getChildren', parentid);

								var child = $('#itemParamList').treegrid(
										'getChildren', rowIndex.id);
								var ids = [];
								for ( var i in child) {
									ids.push(child[i].id);
								if(id.indexOf(child[i])==-1){
									  $('#itemParamList').treegrid('select',
											  child[i].id)  
								
								}}
								if(includes(id,childern)){
								 	 $('#itemParamList').treegrid('select',
											parentid); 
									}
								}
								
						},
						onUnselect : function(rowIndex, rowData) {
							if(Children(rowIndex.id)!=null){
								var ids=$("#itemParamList").datagrid("getSelections");
								var children = Children(rowIndex.id);
								if(includes(ids, children)&&children.length!=0){
								//console.log('1');
							var children = Children(rowIndex.id);
							for ( var i in children) {
								$('#itemParamList').treegrid('unselect',
										children[i].id); 
							}}else{
								//console.log('2');
								var father = $('#itemParamList').treegrid(
										'getParent', rowIndex.id);
								if (father != null) {
								//console.log("f  "+father.id);
									var ids=$("#itemParamList").datagrid("getSelections");
									var children =Children(father.id);
								/* 	console.log(ids); 
									console.log(children); */
									//console.log(includes(ids, children));
									if(!includes(ids, children)){
									//console.log("2");
										 $('#itemParamList').treegrid('unselect',
												father.id); 
								}}
								}}

						},
						onExpand : function(row) {
							var select = $('#itemParamList').treegrid(
									'getSelections');
							for ( var i in select) {
								//console.log(select);
								if (select[i] != null) {
									if (select[i].id == row.id) {
										var children = $('#itemParamList')
												.treegrid('getChildren', row.id);
										for ( var i in children) {
											$('#itemParamList').treegrid(
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
										$(".tree-title:contains('新增类别')").parent().click();
									}
								},

								{
									text : '编辑',
									iconCls : 'icon-edit',
									handler : function() {
										//获取用户选中的数据
										var ids = getSelectionscatIds();
										if (ids.length == 0) {
											$.messager.alert('提示', '必须选择一个图书才能编辑!');
											return;
										}

										$("#catEditWindow")
												.window(
														{
															onLoad : function() {
																//回显数据
																var data = $("#itemParamList").treegrid(
																		"getSelections")[0];
																//console.log(data);
																$("#catEditForm").form("load",
																		data);
																$
																		.get(
																				"/book/cat/queryCatName",
																				{
																					cid : data.parentId
																				},
																				function(result,
																						status, xhr) {
																					var catName = result;
																					KindEditorUtil
																							.init({
																								"cid" : catName,
																								fun : function(
																										node) {
																								}
																							});
																				});
															}
														}).window("open");
									}

								},
								{
									text : '删除',
									iconCls : 'icon-cancel',
									handler : function() {
										var ids = getSelectionscatIds();
										var ss = ids.split(",");
										//console.log(ss);

										if (ids.length == 0) {
											$.messager.alert('提示', '未选中类别!');
											return;
										}
										$.messager
												.confirm(
														'确认',
														'确定删除ID为 ' + ids
																+ ' 的类别吗？',
														function(r) {
															if (r) {
																var params = {
																	"ids" : ids
																};
																$
																		.post(
																				"/book/cat/delete",
																				params,
																				function(
																						data) {
																					if (data.status == 200) {
																						for ( var i in ss) {
																							$(
																									'#itemParamList')
																									.treegrid(
																											'unselect',
																											ss[i]);
																						}

																						$.messager
																								.alert(
																										'提示',
																										'删除类别成功!',
																										undefined,
																										function() {
																											console
																													.log(ids);
																											$(
																													"#itemParamList")
																													.treegrid(
																															"reload");
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
										$("#itemParamList").treegrid("reload");
										//var pg = $('#itemParamList').treegrid("getPanel");
										//$('#pg').panel('open').panel('refresh');
										//$.parser.parse();
										$("#itemParamList").treegrid(
												"clearSelections");
										document.getElementById("catname").value="";
									}
								},{
									text : document.getElementById('CATDIV_toolbar')
								} 
								
								]

					}

			)
	var pg = $('#itemParamList').treegrid("getPager");

</script>
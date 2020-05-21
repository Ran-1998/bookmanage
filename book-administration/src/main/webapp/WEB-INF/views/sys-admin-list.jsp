<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="easyui-datagrid" id="sysAdminList" title="用户列表"
	data-options="singleSelect:false,fitColumns:true,collapsible:true,pagination:true,url : '/admin/query',method:'get',pageSize:20,toolbar:toolbar">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:30">id</th>
			<th data-options="field:'name',width:80">用户名</th>
			<th
				data-options="field:'roleId',width:100,formatter:KindEditorUtil.findRoleName">角色</th>
			<th data-options="field:'des',width:80">描述</th>
			<th
				data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
			<th
				data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
		</tr>
	</thead>
</table>
<div id="DIV_Admintoolbar">
	用户名:<input class="easyui-textbox" style="width: 100px" id="name">
	角色：
	<td><input id="roleId" name="roleId"></td> <a href="#"
		class="easyui-linkbutton" iconCls="icon-search" style="width: 80px"
		onclick="SearchAdmin()">查询</a> </select>
</div>
<div id="AdminEditWindow" class="easyui-window" title="编辑用户信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/sys-admin-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>
$.get("/check/adminquery",  function(data) {
	if (data.status == 201) {
		$.messager.alert("提示", "没有权限或系统维护!");
	}
	else{}
}); 	
	function SearchAdmin() {

		$.get("/check/adminquery",  function(data) {
			if (data.status == 201) {
				$.messager.alert("提示", "没有权限或系统维护!");
			}
			else{
				$('#sysAdminList').datagrid('load', {
					roleId : $("#roleId").combobox("getValue"),
					name : $("#name").textbox("getValue")
				});}
		}); 
		
	}
	$('#roleId').combobox({
		url : '/role/qurey',
		valueField : 'id',
		textField : 'roleName'
	});
	function getSelectionAdminIds() {
		var sysAdminList = $("#sysAdminList");
		var sels = sysAdminList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		//将数组拼接成串 1,2,3,4,5
		ids = ids.join(",");
		return ids;
	}
	var toolbar = [
			{
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {

					$.get("/check/adminadd",  function(data) {
						if (data.status == 201) {
							$.messager.alert("提示", "没有权限或系统维护!");
						}
						else{
					$(".tree-title:contains('系统用户新增')").parent().click();
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
							var ids = getSelectionAdminIds();
							if (ids.length == 0) {
								$.messager.alert('提示', '必须选择一个用户才能编辑!');
								return;
							}
							if (ids.indexOf(',') > 0) {
								$.messager.alert('提示', '只能选择一个用户!');
								return;
							}

							$("#AdminEditWindow").window(
									{
										onLoad : function() {
											//回显数据
											var data = $("#sysAdminList").datagrid(
													"getSelections")[0];
											//console.log(data);
											data.password = null;
											$("#AdminEditWindow").form("load", data);
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

							
							var ids = getSelectionAdminIds();
							if (ids.length == 0) {
								$.messager.alert('提示', '未选中用户!');
								return;
							}
							$.messager
									.confirm(
											'确认',
											'确定删除ID为 ' + ids + ' 的用户吗？',
											function(r) {
												if (r) {
													var params = {
														"ids" : ids
													};
													$
															.post(
																	"/admin/delete",
																	params,
																	function(data) {
																		if (data.status == 200) {
																			$.messager
																					.alert(
																							'提示',
																							'删除用户成功!',
																							undefined,
																							function() {
																								$(
																										"#sysAdminList")
																										.datagrid(
																												"reload");
																							});
																		} else {
																			$.messager
																					.alert(
																							"提示",
																							data.msg);
																		}
																	});
												}
											});
							}
					}); 

				}
			}, {
				text : '重置/刷新',
				iconCls : 'icon-reload',
				handler : function() {
					//var pg = $('#itemParamList').treegrid("getPanel");
					//$('#pg').panel('open').panel('refresh');
					//$.parser.parse();
					$("#sysAdminList").datagrid("clearSelections");
					$('#name').textbox('reset');
					$("#roleId").combobox("reset")
					$('#sysAdminList').datagrid('load', {
						roleId : $("#roleId").combobox("getValue"),
						name : $("#name").textbox("getValue")
					});

				}
			}, {
				text : document.getElementById('DIV_Admintoolbar')
			} ];
</script>
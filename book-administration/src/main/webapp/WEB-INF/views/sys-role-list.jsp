<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="roleList"></table>
<div id="DIV_Roletoolbar">
	<a href="#" class="easyui-linkbutton" id="add"
		data-options="iconCls:'icon-add',plain:true">添加</a> <a href="#"
		class="easyui-linkbutton" id="edit"
		data-options="iconCls:'icon-edit',plain:true">编辑</a> <a href="#"
		class="easyui-linkbutton" id="delete"
		data-options="iconCls:'icon-cancel',plain:true">删除</a> <a href="#"
		class="easyui-linkbutton" id="reload"
		data-options="iconCls:'icon-reload',plain:true">刷新/重置</a> 角色名:<input
		class="easyui-textbox" style="width: 100px" id="name"> <a
		href="#" class="easyui-linkbutton" iconCls="icon-search"
		style="width: 80px" onclick="SearchRole()">查询</a> </select>
</div>
<div id="RoleEditWindow" class="easyui-window" title="编辑角色信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/sys-role-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>
	$('#roleList').datagrid({
		title : '角色列表',
		height : '1000px',
		nowrap : true,
		fitColumns : true,
		toolbar : '#DIV_Roletoolbar',
		url : '/role/qurey',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : 'ID',
			width : 30
		}, {
			field : 'roleName',
			title : '角色',
			width : 80,
			align : 'right'
		}, {
			field : 'created',
			title : '创建时间',
			width : 130,
			align : 'center',
			formatter : KindEditorUtil.formatDateTime
		}, {
			field : 'updated',
			title : '更新时间',
			width : 130,
			align : 'center',
			formatter : KindEditorUtil.formatDateTime
		} ] ],
		view : detailview,
		detailFormatter : function(rowIndex, rowData) {
			return '<table id="role'+rowData.id+'PermissionList"></table>';
		},
		onExpandRow : function(index, row) {
			//alert(row.roleName);
			$('#role' + row.id + 'PermissionList').datagrid({
				width : '1000px',
				url : '/permission/findPerByRole',
				queryParams : {
					id : row.id,
				},
				columns : [ [ {
					field : 'ck',
					checkbox : true
				}, {
					field : 'id',
					title : 'Id',
					width : 100
				}, {
					field : 'permissionName',
					title : '权限名',
					width : 200
				}, {
					field : 'permission',
					title : '权限标识',
					width : 200
				}, {
					field : 'created',
					title : '创建时间',
					width : 230,
					align : 'center',
					formatter : KindEditorUtil.formatDateTime
				}, {
					field : 'updated',
					title : '更新时间',
					width : 230,
					align : 'center',
					formatter : KindEditorUtil.formatDateTime
				} ] ]
			});

		}
	});
	$.get("/permission/findPerByRole",  function(data) {
		if (data.status == 201 ) {
			$.messager.alert("提示", "没有权限或系统维护!");
		} 
	}); 
	$(function() {
		$('#edit').bind('click', function() {
			//获取角色选中的数据
			var ids = getRoleSelectionsIds();
			if (ids.length == 0) {
				$.messager.alert('提示', '必须选择一个角色才能编辑!');
				return;
			}
			if (ids.indexOf(',') > 0) {
				$.messager.alert('提示', '只能选择一个角色!');
				return;
			}
			$.post("/permission/setRoleId", {
				roleId : ids
			}, function(data) {

			});
			$("#RoleEditWindow").window({
				onLoad : function() {
					//回显数据
					var data = $("#roleList").datagrid("getSelections")[0];
					//console.log(data);
					$("#RoleEditWindow").form("load", data);
				}
			}).window("open");
		});

		$('#reload').bind('click', function() {

			$("#roleList").datagrid("clearSelections");
			$('#name').textbox('reset');
			$('#roleList').datagrid('load', {
				name : $("#name").textbox("getValue")
			});
		});

		$('#add').bind('click', function() {
			$(".tree-title:contains('系统角色新增')").parent().click();
		});
		$('#delete')
				.bind(
						'click',
						function() {
							var ids = getRoleSelectionsIds();
							if (ids.length == 0) {
								$.messager.alert('提示', '未选中角色!');
								return;
							}
							$.messager
									.confirm(
											'确认',
											'确定删除ID为 ' + ids + ' 的角色吗？',
											function(r) {
												if (r) {
													var params = {
														"ids" : ids
													};
													$
															.post(
																	"/role/delete",
																	params,
																	function(
																			data) {
																		if (data.status == 200) {
																			$.messager
																					.alert(
																							'提示',
																							'删除角色成功!',
																							undefined,
																							function() {
																								$(
																										"#roleList")
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

						});
	});

	function SearchRole() {
		//console.log($("#SearchRole").combobox("getValue"))
		$('#roleList').datagrid('load', {
			name : $("#name").textbox("getValue")
		});
	}
	function getRoleSelectionsIds() {
		var roleList = $("#roleList");
		var sels = roleList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		//将数组拼接成串 1,2,3,4,5
		ids = ids.join(",");
		return ids;
	}
</script>
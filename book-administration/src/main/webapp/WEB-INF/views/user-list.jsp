<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="easyui-datagrid" id="userList" title="用户列表"
	data-options="singleSelect:false,fitColumns:true,collapsible:true,pagination:true,url : '/user/query',method:'get',pageSize:20,toolbar:toolbar">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:30">id</th>
			<th data-options="field:'studentId',width:80">学号</th>
			<th data-options="field:'name',width:100">姓名</th>
			<th data-options="field:'className',width:100">班级</th>
			<th data-options="field:'phone',width:100">电话</th>
			<th data-options="field:'password',width:70">密码</th>

			<th
				data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
			<th
				data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
		</tr>
	</thead>
</table>
<div id="DIV_Usertoolbar">
	学号:<input class="easyui-textbox" style="width: 100px" id="studentId">
	姓名:<input class="easyui-textbox" style="width: 100px" id="name">
	班级:<input class="easyui-textbox" style="width: 100px" id="className">
	电话:<input class="easyui-textbox" style="width: 100px" id="phone">
	
	<a href="#" class="easyui-linkbutton" iconCls="icon-search"
		style="width: 80px" onclick="SearchUser()">查询</a> </select>
</div>
<div id="UserEditWindow" class="easyui-window" title="编辑用户信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/user-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>


$.get("/user/query",  function(data) {
	if (data.status == 201) {
		$.messager.alert("提示", "没有权限或系统维护!");
	} 
}); 

	function SearchUser() {
		//console.log($("#SearchUser").combobox("getValue"))
			$('#userList').datagrid('load', {
				studentId : $("#studentId").textbox("getValue"),
				className : $("#className").textbox("getValue"),
				phone : $("#phone").textbox("getValue"),
				name : $("#name").textbox("getValue")
			});
	}
	function getSelectionsIds() {
		var userList = $("#userList");
		var sels = userList.datagrid("getSelections");
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
					$(".tree-title:contains('新增用户')").parent().click();
				}
			},
			{
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					//获取用户选中的数据
					var ids = getSelectionsIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '必须选择一个用户才能编辑!');
						return;
					}
					if (ids.indexOf(',') > 0) {
						$.messager.alert('提示', '只能选择一个用户!');
						return;
					}

					$("#UserEditWindow").window(
							{
								onLoad : function() {
									//回显数据
									var data = $("#userList").datagrid(
											"getSelections")[0];
									//console.log(data);
									$("#UserEditWindow").form("load", data);
								}
							}).window("open");
				}
			},
			{
				text : '删除',
				iconCls : 'icon-cancel',
				handler : function() {
					var ids = getSelectionsIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中用户!');
						return;
					}
					$.messager
							.confirm(
									'确认',
									'确定删除ID为 ' + ids + ' 的用户与其对应的借阅信息吗？',
									function(r) {
										if (r) {
											var params = {
												"ids" : ids
											};
											$
													.post(
															"/user/delete",
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
																								"#userList")
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
			}, {
				text : '重置/刷新',
				iconCls : 'icon-reload',
				handler : function() {
					//var pg = $('#itemParamList').treegrid("getPanel");
					//$('#pg').panel('open').panel('refresh');
					//$.parser.parse();
					$("#userList").datagrid("clearSelections");
					$('#studentId').textbox('reset');
					$('#className').textbox('reset');
					$('#name').textbox('reset');
					$('#phone').textbox('reset');
					$('#userList').datagrid('load', {
						studentId : $("#studentId").textbox("getValue"),
						className : $("#className").textbox("getValue"),
						phone : $("#phone").textbox("getValue"),
						name : $("#name").textbox("getValue")
					});

				}
			}, {
				text : document.getElementById('DIV_Usertoolbar')
			} ];
</script>
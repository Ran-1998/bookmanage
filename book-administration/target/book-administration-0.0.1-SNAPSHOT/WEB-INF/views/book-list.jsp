<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="easyui-datagrid" id="itemList" title="图书列表"
	data-options="singleSelect:false,fitColumns:true,collapsible:true,pagination:true,url : '/book/query',method:'get',pageSize:20,toolbar:toolbar">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:30">id</th>
			<th data-options="field:'isbn',width:80">图书ISBN码</th>
			<th data-options="field:'bookname',width:100">图书名字</th>
			<th
				data-options="field:'cid',width:100,align:'center',formatter:KindEditorUtil.findCatName">图书类目</th>
			<th data-options="field:'author',width:100">作者</th>
			<th data-options="field:'press',width:70">出版社</th>
			<th data-options="field:'briefintroduction',width:100">简介</th>
			<th
				data-options="field:'status',width:60,align:'center',formatter:KindEditorUtil.formatItemStatus">状态</th>
			<th
				data-options="field:'publicationdate',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">出版日期</th>
			<th
				data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
			<th
				data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
		</tr>
	</thead>
</table>
<div id="DIV_toolbar">
	出版日期: <input class="easyui-datebox" style="width: 100px" id="date1">
	到: <input class="easyui-datebox" style="width: 100px" id="date2">
	类别:<input class="easyui-combotree" id="cat"
		data-options="url:'/book/cat/list',method:'get'" style="width: 100px;">
	作者:<input class="easyui-textbox" style="width: 100px" id="author">
	图书名:<input class="easyui-textbox" style="width: 100px" id="name">
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="width: 80px"
		onclick="Search()">查询</a> </select>
</div>
<div id="itemEditWindow" class="easyui-window" title="编辑书籍信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/book-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>
	function Search() {
		//console.log($("#search").combobox("getValue"))
		if (($("#date1").combobox("getValue") != "" && $("#date2").combobox(
				"getValue") == "")
				|| ($("#date1").combobox("getValue") == "" && $("#date2")
						.combobox("getValue") != "")) {
			$.messager.alert('提示', '请输入起始时间');
		}
		$('#itemList').datagrid('load', {
			catid : $("#cat").combobox("getValue"),
			data1 : $("#date1").combobox("getValue"),
			data2 : $("#date2").combobox("getValue"),
			author : $("#author").textbox("getValue"),
			name : $("#name").textbox("getValue")
		});

	}
	function getSelectionsIds() {
		var itemList = $("#itemList");
		var sels = itemList.datagrid("getSelections");
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
					$(".tree-title:contains('新增图书')").parent().click();
				}
			},
			{
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					//获取用户选中的数据
					var ids = getSelectionsIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '必须选择一个图书才能编辑!');
						return;
					}
					if (ids.indexOf(',') > 0) {
						$.messager.alert('提示', '只能选择一个图书!');
						return;
					}

					$("#itemEditWindow")
							.window(
									{
										onLoad : function() {
											//回显数据
											var data = $("#itemList").datagrid(
													"getSelections")[0];
											//console.log(data);
											$("#itemeEditForm").form("load",
													data);
											itemEditEditor
													.html(data.briefintroduction);

											$
													.get(
															"/book/cat/queryCatName",
															{
																cid : data.cid
															},
															function(result,
																	status, xhr) {
																var catName = result;
																KindEditorUtil
																		.init({
																			"pics" : data.image,
																			"cid" : catName,
																			fun : function(
																					node) {
																				KindEditorUtil
																						.changeItemParam(
																								node,
																								"itemeEditForm");
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
					var ids = getSelectionsIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中书籍!');
						return;
					}
					$.messager
							.confirm(
									'确认',
									'确定删除ID为 ' + ids + ' 的书籍吗？',
									function(r) {
										if (r) {
											var params = {
												"ids" : ids
											};
											$
													.post(
															"/book/delete",
															params,
															function(data) {
																if (data.status == 200) {
																	$.messager
																			.alert(
																					'提示',
																					'删除书籍成功!',
																					undefined,
																					function() {
																						$(
																								"#itemList")
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
			},
			'-',
			{
				text : '下架',
				iconCls : 'icon-remove',
				handler : function() {
					//获取选中的ID串中间使用","号分割
					var ids = getSelectionsIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中图书!');
						return;
					}
					$.messager
							.confirm(
									'确认',
									'确定下架ID为 ' + ids + ' 的图书吗？',
									function(r) {
										if (r) {
											var params = {
												"ids" : ids
											};
											$
													.post(
															"/book/instock",
															params,
															function(data) {
																if (data.status == 200) {
																	$.messager
																			.alert(
																					'提示',
																					'下架图书成功!',
																					undefined,
																					function() {
																						$(
																								"#itemList")
																								.datagrid(
																										"reload");
																					});
																}
															});
										}
									});
				}
			},
			{
				text : '上架',
				iconCls : 'icon-remove',
				handler : function() {
					var ids = getSelectionsIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中图书!');
						return;
					}
					$.messager
							.confirm(
									'确认',
									'确定上架ID为 ' + ids + ' 的图书吗？',
									function(r) {
										if (r) {
											var params = {
												"ids" : ids
											};
											$
													.post(
															"/book/reshelf",
															params,
															function(data) {
																if (data.status == 200) {
																	$.messager
																			.alert(
																					'提示',
																					'上架图书成功!',
																					undefined,
																					function() {
																						$(
																								"#itemList")
																								.datagrid(
																										"reload");
																					});
																}
															});
										}
									});
				}
			}, {
				text : document.getElementById('DIV_toolbar')
			} ];
</script>
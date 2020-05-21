<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="easyui-datagrid" id="reviewList" title="评论信息列表"
	data-options="singleSelect:false,fitColumns:true,collapsible:true,pagination:true,url : '/book/review/query',method:'get',pageSize:20,toolbar:toolbar">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:30">id</th>
			<th
				data-options="field:'bookId',width:100,formatter:KindEditorUtil.findBookName">图书名</th>
			<th
				data-options="field:'userId',width:50,formatter:KindEditorUtil.findUserName">用户名</th>
			<th data-options="field:'review',width:100">评论信息</th>
			<th data-options="field:'heat',width:10">热度</th>
			<th
				data-options="field:'created',width:100,align:'center',formatter:KindEditorUtil.formatDateTime">评论日期</th>
		</tr>
	</thead>
</table>
<div id="DIV_reviewtoolbar">
	<form id="selectForm">
		图书名:<input class="easyui-textbox" style="width: 100px" id="bookName">
		用户名:<input class="easyui-textbox" style="width: 100px" id="UserName">
		<a href="#" class="easyui-linkbutton" iconCls="icon-reviewSearch"
			style="width: 80px" onclick="reviewSearch()">查询</a> </select>
	</form>
</div>
<div id="itemEditWindow" class="easyui-window" title="编辑评论信息信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/book-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>
	$.get("/check/bookquery", function(data) {
		if (data.status == 201) {
			$.messager.alert("提示", "没有权限或系统维护!");
		} else {
		}
	});
	function reviewSearch() {
		//console.log($("#reviewSearch").combobox("getValue"))
		$.get("/check/bookquery", function(data) {
			if (data.status == 201) {
				$.messager.alert("提示", "没有权限或系统维护!");
			} else {
				$('#reviewList').datagrid('load', {
					bookName : $("#bookName").textbox("getValue"),
					UserName : $("#UserName").textbox("getValue")
				});
			}
		});

	}
	function getSelectionsreviewIds() {
		var reviewList = $("#reviewList");
		var sels = reviewList.datagrid("getSelections");
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
				text : '删除',
				iconCls : 'icon-cancel',
				handler : function() {
					$
							.get(
									"/check/bookdelete",
									function(data) {
										if (data.status == 201) {
											$.messager
													.alert("提示", "没有权限或系统维护!");
										} else {
											var ids = getSelectionsreviewIds();
											if (ids.length == 0) {
												$.messager.alert('提示',
														'未选中评论信息!');
												return;
											}
											$.messager
													.confirm(
															'确认',
															'确定删除ID为 '
																	+ ids
																	+ ' 的评论信息吗？',
															function(r) {
																if (r) {
																	var params = {
																		"ids" : ids
																	};
																	$
																			.post(
																					"/book/review/delete",
																					params,
																					function(
																							data) {
																						if (data.status == 200) {
																							$.messager
																									.alert(
																											'提示',
																											'删除评论信息成功!',
																											undefined,
																											function() {
																												$(
																														"#reviewList")
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
					$('#bookName').textbox('reset');
					$('#UserName').textbox('reset');

					$('#reviewList').datagrid('load', {
						bookName : $("#bookName").textbox("getValue"),
						UserName : $("#UserName").textbox("getValue")
					});

				}
			}, {
				text : document.getElementById('DIV_reviewtoolbar')
			} ];
</script>
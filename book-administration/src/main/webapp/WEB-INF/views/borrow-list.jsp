<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="easyui-datagrid" id="borrowList" title="记录列表"
	data-options="singleSelect:false,fitColumns:true,collapsible:true,pagination:true,url : '/borrow/query',method:'get',pageSize:20,toolbar:Borrowtoolbar">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:30">id</th>
			<th
				data-options="field:'userId',width:50,formatter:KindEditorUtil.findUserName">用户</th>
			<th
				data-options="field:'bookId',width:130,formatter:KindEditorUtil.findBookName">书名</th>
			<th
				data-options="field:'status',width:40,formatter:KindEditorUtil.formatBorrowStatus">状态</th>
			<!-- 	<th data-options="field:'studentId',width:80">学号</th>
			<th data-options="field:'name',width:100">姓名</th>
			<th data-options="field:'className',width:100">班级</th>
			<th data-options="field:'phone',width:100">电话</th>
			<th data-options="field:'password',width:70">密码</th> -->
			<th
				data-options="field:'created',width:100,align:'center',formatter:KindEditorUtil.formatDateTime">借阅/创建日期</th>
			<th
				data-options="field:'returnTime',width:100,align:'center',formatter:KindEditorUtil.formatDateTime">应归还日期</th>
			<th
				data-options="field:'updated',width:100,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
		</tr>
	</thead>
</table>
<div id="DIV_borrowtoolbar">
	<!-- 学号:<input class="easyui-textbox" style="width: 100px" id="studentId">
	 -->
	用户名:<input class="easyui-textbox" style="width: 100px" id="name">
	书名:<input class="easyui-textbox" style="width: 100px" id="bookName">
	<!-- 借阅日期: <input class="easyui-datebox" style="width: 100px" id="date1">
	归还日期: <input class="easyui-datebox" style="width: 100px" id="date2"> -->
	状态:<input class="easyui-combobox"  id="status" style="width: 70px" data-options="
		valueField: 'label',
		textField: 'value',
		data: [
		{
			label: '',
			value: '无'
		},{
			label: '0',
			value: '归还'
		},{
			label: '1',
			value: '未归还'
		},{
			label: '2',
			value: '超时'
		}]" />

	<a href="#" class="easyui-linkbutton" iconCls="icon-search"
		style="width: 80px" onclick="Searchborrow()">查询</a> </select>
</div>
<div id="Delaytoolbar">
	延期时间:<input class="easyui-combobox"  id="delayTime" style="width: 70px" data-options="
		valueField: 'label',
		textField: 'value',
		data: [
		{
			label: '',
			value: '无'
		},{
			label: '7',
			value: '7天'
		},{
			label: '15',
			value: '15天'
		},{
			label: '30',
			value: '30天'
		}]" />


</div>
<div id="borrowEditForm" class="easyui-window" title="编辑用户信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/user-edit'"
	style="width: 80%; height: 80%; padding: 10px;"></div>
<script>

$.get("/borrow/query",  function(data) {
	if (data.status == 201) {
		$.messager.alert("提示", "没有权限或系统维护!");
	} 
}); 

function Searchborrow() {
		//console.log($("#Searchborrow").combobox("getValue"))
		$('#borrowList').datagrid('load', {
			/* studentId : $("#studentId").textbox("getValue"),
			className : $("#className").textbox("getValue"),
			phone : $("#phone").textbox("getValue"),
			 */
			 name : $("#name").textbox("getValue"),
			 bookName : $("#bookName").textbox("getValue"),
			/*  data1 : $("#date1").combobox("getValue"),
			 data2 : $("#date2").combobox("getValue"), */
			 status : $("#status").textbox("getValue")
		});
	}
	function getSelectionsborrowIds() {
		var borrowList = $("#borrowList");
		var sels = borrowList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		//将数组拼接成串 1,2,3,4,5
		ids = ids.join(",");
		return ids;
	}

	function getSelectionsBookIds() {
		var borrowList = $("#borrowList");
		var sels = borrowList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].bookId);
		}
		//将数组拼接成串 1,2,3,4,5
		ids = ids.join(",");
		return ids;
	}
	function getSelectionsReturn() {
		var borrowList = $("#borrowList");
		var sels = borrowList.datagrid("getSelections");
		var returns = [];
		for ( var i in sels) {
			returns.push(sels[i].returnTime);
		}
		returns = returns.join(",");
		return returns;
	}
	function getSelectionsStatus() {
		var borrowList = $("#borrowList");
		var sels = borrowList.datagrid("getSelections");
		var status = [];
		for ( var i in sels) {
			status.push(sels[i].status);
		}
		return status;
	}
	
	var Borrowtoolbar = [
			{
				text : '删除',
				iconCls : 'icon-cancel',
				handler : function() {
					var ids = getSelectionsborrowIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中用户!');
						return;
					}
					$.messager
							.confirm(
									'确认',
									'确定删除ID为 ' + ids + ' 的记录吗？',
									function(r) {
										if (r) {
											var params = {
												"ids" : ids
											};
											$
													.post(
															"/borrow/delete",
															params,
															function(data) {
																if (data.status == 200) {
																	$.messager
																			.alert(
																					'提示',
																					'删除记录成功!',
																					undefined,
																					function() {
																						$(
																								"#borrowList")
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
					$('#name').textbox('reset');
					$('#bookName').textbox('reset');
					$('#status').textbox('reset');
					$('#borrowList').datagrid('load', {
						 name : $("#name").textbox("getValue"),
						 bookName : $("#bookName").textbox("getValue"),
						 status : $("#status").textbox("getValue")
					});

				}
			}, 
				{
				text : '归还',
				iconCls : 'icon-remove',
				handler : function() {
					//获取选中的ID串中间使用","号分割
					var ids = getSelectionsborrowIds();
					var bookids=getSelectionsBookIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中记录!');
						return;
					}
					$.messager
							.confirm(
									'确认',
									'确定归还ID为 ' + ids + ' 的记录吗？',
									function(r) {
										if (r) {
											var params = {
												"ids" : ids,
												"bookIds":bookids
											};
											$
													.post(
															"/borrow/return",
															params,
															function(data) {
																if (data.status == 200) {
																	$.messager
																			.alert(
																					'提示',
																					'归还记录成功!',
																					undefined,
																					function() {
																						$(
																								"#borrowList")
																								.datagrid(
																										"reload");
																					});
																}
															});
										}
									});
				}
			},{
				text : document.getElementById('Delaytoolbar')
			} ,{
				text : '延期归还',
				iconCls : 'icon-remove',
				handler : function() {
					//获取选中的ID串中间使用","号分割
					var ids = getSelectionsborrowIds();
					var returns = getSelectionsReturn();
					var day =$("#delayTime").textbox("getValue");
					var status=getSelectionsStatus();
					console.log(returns);
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中记录!');
						return;
					}
					for ( var i in status) {
					if (status[i] == 0) {
						$.messager.alert('提示', '只能选择未归还或超时记录!');
						return;
					}
						}
					$.messager
							.confirm(
									'确认',
									'确定延期ID为 ' + ids + ' 的记录吗？',
									function(r) {
										if (r) {
											var params = {
												"ids" : ids,
												"day":  day,
												"returnTime":returns
											};
											$.post(
															"/borrow/delay",
															params,
															function(data) {
																if (data.status == 200) {
																	$.messager
																			.alert(
																					'提示',
																					'延期成功!',
																					undefined,
																					function() {
																						$(
																								"#borrowList")
																								.datagrid(
																										"reload");
																					});
																}
															});
										}
									});
				}
			},{
				text : document.getElementById('DIV_borrowtoolbar')
			} ];
</script>
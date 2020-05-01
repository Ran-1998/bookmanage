<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="easyui-datagrid" id="logList" title="日志列表"
	data-options="singleSelect:false,fitColumns:true,collapsible:true,pagination:true,url : '/log/query',method:'get',pageSize:20,toolbar:toolbar">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',width:30">id</th>
			<th data-options="field:'username',width:80">用户名</th>
			<th data-options="field:'operation',width:100">操作名</th>
			<th data-options="field:'method',width:100">操作方法</th>
			<th data-options="field:'params',width:100">参数</th>
			<th data-options="field:'time',width:100">操作时间</th>
			<th data-options="field:'ip',width:100">操作IP</th>
			<th
				data-options="field:'created',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">创建日期</th>
			<th
				data-options="field:'updated',width:130,align:'center',formatter:KindEditorUtil.formatDateTime">更新日期</th>
		</tr>
	</thead>
</table>
<div id="DIV_Logtoolbar">
	<form id="selectlogForm">
		操作日期: <input class="easyui-datebox" style="width: 100px" id="date3">
		到: <input class="easyui-datebox" style="width: 100px" id="date4">
		用户名:<input class="easyui-textbox"
			style="width: 100px" id="username"> 
		操作名:<input
			class="easyui-textbox" style="width: 100px" id="operation"> <a
			href="#" class="easyui-linkbutton" iconCls="icon-SearchLog"
			style="width: 80px" onclick="SearchLog()">查询</a> </select>
	</form>
</div>
<script>

$.get("/log/query",  function(data) {
	if (data.status == 201) {
		$.messager.alert("提示", "没有权限或系统维护!");
	} 
}); 


	function tab(date3, date4) {
		var odate3 = new Date(date3);
		var odate4 = new Date(date4);
		if (odate3.getTime() > odate4.getTime()) {
			return false;
		} else {
			return true;
		}
	}
	function SearchLog() {
		//console.log($("#SearchLog").combobox("getValue"))
		if (($("#date3").combobox("getValue") != "" && $("#date4").combobox(
				"getValue") == "")
				|| ($("#date3").combobox("getValue") == "" && $("#date4")
						.combobox("getValue") != "")) {
			$.messager.alert('提示', '请输入起始时间');
		}
		var big = tab($("#date3").combobox("getValue"), $("#date4").combobox(
				"getValue"));
	if(big){
		$('#logList').datagrid('load', {
			date1 : $("#date3").combobox("getValue"),
			date2 : $("#date4").combobox("getValue"),
			operation : $("#operation").textbox("getValue"),
			username : $("#username").textbox("getValue")
		});
		}
	else{
		$.messager.alert('提示', '请输入正确时间');
		$('#selectlogForm').form('reset');
		}

	}
	function getSelectionsIds() {
		var logList = $("#logList");
		var sels = logList.datagrid("getSelections");
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
					var ids = getSelectionsIds();
					if (ids.length == 0) {
						$.messager.alert('提示', '未选中记录!');
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
															"/log/delete",
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
																								"#logList")
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
			{
				text : '重置/刷新',
				iconCls : 'icon-reload',
				handler : function() {
					//var pg = $('#itemParamList').treegrid("getPanel");
					//$('#pg').panel('open').panel('refresh');
					//$.parser.parse();
					$("#logList").datagrid("clearSelections");
					$('#selectlogForm').form('reset');
					$('#operation').textbox('reset');
					$('#username').textbox('reset');
					$('#logList').datagrid('load', {
						date1 : $("#date3").combobox("getValue"),
						date2 : $("#date4").combobox("getValue"),
						operation : $("#operation").textbox("getValue"),
						username : $("#username").textbox("getValue")
					});

				}
			}, {
				text : document.getElementById('DIV_Logtoolbar')
			} ];
</script>
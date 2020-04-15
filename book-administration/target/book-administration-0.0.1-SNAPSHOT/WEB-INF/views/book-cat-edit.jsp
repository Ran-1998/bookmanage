<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<!-- <script type="text/javascript" charset="utf-8"
	src="/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script> -->
<div style="padding: 10px 10px 10px 10px">
	<form id="catEditForm" class="itemForm" method="post">
		<input type="hidden" name="id" />
		<table cellpadding="5">
			<tr>
				<td>选择父类目:</td>
				<td><a href="javascript:void(0)"
					class="easyui-linkbutton selectItemCat">选择类目</a> <input
					type="hidden" name="parentId" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>类目标题:</td>
				<td><input class="easyui-textbox" type="text" name="name"
					data-options="required:true"></input></td>
			</tr>
			<!-- 	<tr>
				<td>类目作者:</td>
				<td><input class="easyui-textbox" type="text" name="author" /> 
			</tr>
			<tr>
				<td>ISBN码:</td>
				<td><input class="easyui-textbox" type="text" name="isbn" /></td>
			</tr>
			<tr>
				<td>出版社:</td>
				<td><input class="easyui-textbox" type="text" name="press"
					data-options="validType:'length[1,30]'" /></td>
			</tr> -->
		</table>
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitupdateForm()">提交</a>
	</div>
</div>
<script type="text/javascript">
	function submitupdateForm() {
		if (!$('#catEditForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		var paramJson = [];
		$("#itemeEditForm .params li").each(function(i, e) {
			var trs = $(e).find("tr");
			var group = trs.eq(0).text();
			var ps = [];
			for (var i = 1; i < trs.length; i++) {
				var tr = trs.eq(i);
				ps.push({
					"k" : $.trim(tr.find("td").eq(0).find("span").text()),
					"v" : $.trim(tr.find("input").val())
				});
			}
			paramJson.push({
				"group" : group,
				"params" : ps
			});
		});
		var s = $("#catEditForm").serialize();

		var index=s.indexOf("parentId=");
		var ss=s.substring(index);
		ss=ss.substring(ss.indexOf("=")+1,ss.indexOf("&"));
		//console.log(s);
		//console.log(ss);

	
		$.get("/book/cat/checkLV", $("#catEditForm").serialize(),
				function(data) {
					if (data.status == 200) {
						$.post("/book/cat/update", $("#catEditForm").serialize(),
								function(data) {
									if (data.status == 200) {
										$.messager.alert('提示', '修改类目成功!', 'info', function() {
											$("#catEditWindow").window('close');
											$("#itemList").datagrid("reload");
										});
									} else {
										$.messager.alert("提示", data.msg);
									}
								});
					} else {
						$.messager.alert("提示", data.msg);
					}
				}); 
	
	}
</script>

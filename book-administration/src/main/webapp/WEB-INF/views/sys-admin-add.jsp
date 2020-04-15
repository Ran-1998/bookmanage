<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="adminAddForm" class="itemForm" method="post">
		<table cellpadding="5">
			<tr>
				<td>用户名:</td>
				<td><input class="easyui-textbox" type="text" name="name""></input></td>
			</tr>
			<tr>
				<td>角色:</td>
				<td><input id="cc" name="roleId"></td>
			</tr>
			<tr>
				<td>描述:</td>
				<td><input class="easyui-textbox" type="text" name="des" " />
			</tr>
			<tr>
				<td>密码:</td>
				<td><input class="easyui-textbox" type="text" name="password" " /></td>
			</tr>
		</table>
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitForm()">提交</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	$('#cc').combobox({
		url : '/role/qurey',
		valueField : 'id',
		textField : 'roleName'
	});
	function submitForm() {
		//表单校验
		if (!$('#adminAddForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}

		//alert($("#adminAddForm").serialize());
		$.post("/admin/add", $("#adminAddForm").serialize(), function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '新增用户成功!');
				$('#adminAddForm').form('reset');
			} else {
				$.messager.alert("提示", "新增用户失败!");
			}
		});
	}

	function clearForm() {
		$('#adminAddForm').form('reset');
	}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="AdminEditForm"  method="post">
		<input type="hidden" name="id" />
		<table cellpadding="5">
			<tr>
				<td>用户名:</td>
				<td><input class="easyui-textbox" type="text" name="name" "></input></td>
			</tr>
			<tr>
				<td>角色:</td>
				<td><input id="cc" name="roleId"></td>
			</tr>
			<tr>
				<td>描述:</td>
				<td><input class="easyui-textbox" type="text" name="des"" />
			</tr>
			<tr>
				<td>密码:</td>
				<td><input class="easyui-textbox" type="text" name="password"" /></td>
			</tr>
		</table>
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitAForm()">提交</a>
	</div>
</div>
<script>

$('#cc').combobox({
    url:'/role/qurey',
    valueField:'id',
    textField:'roleName'
});


	function submitAForm() {
		if (!$('#AdminEditForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		//alert($("#AdminEditForm").serialize());
		$.post("/admin/update", $("#AdminEditForm").serialize(), function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '修改用户成功!', 'info', function() {
					$("#AdminEditWindow").window('close');
					$("#sysAdminList").datagrid("reload");
				});
			} else {
				$.message.alert("提示", data.msg);
			}
		});
	}
</script>

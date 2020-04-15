<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="perAddForm" class="itemForm" method="post">
		<table cellpadding="5">
			<tr>
				<td>选择父权限:</td>
				<td><input class="easyui-combotree" name="parentId" id="parentedt"
		data-options="url:'/permission/list',method:'get'" ></td>
			</tr>
			<tr>
				<td>权限名:</td>
				<td><input class="easyui-textbox" type="text" name="permissionName"
					data-options="required:true"></input></td>
			</tr>
			<tr>
				<td>权限标识:</td>
				<td><input class="easyui-textbox" type="text" name="permission"
					data-options="required:true"></input></td>
			</tr>
		</table>
		<input type="hidden" name="status" />
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitForm()">提交</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">

	function submitForm() {
		//表单校验
		if (!$('#perAddForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		$("#perAddForm [name=status]").val(1);

		//alert($("#perAddForm").serialize());
			$.get("/permission/checkLV", $("#perAddForm").serialize(),
				function(data) {
					if (data.status == 200) {
						$.post("/permission/add", $("#perAddForm").serialize(), function(data) {
							if (data.status == 200) {
								$.messager.alert('提示', '新增类别成功!');
								$('#parentedt').combotree('clear');
								$('#perAddForm').form('reset');
								$('#parentedt').combotree('reload');
							} else {
								$.messager.alert("提示", "新增类别失败!");
							} 
						}); 
					} else {
						$.messager.alert("提示", data.msg);
					}
				}); 
	 
	}

	function clearForm() {
		$('#perAddForm').form('reset');
		$('#parentedt').combotree('clear');
		$('#parentedt').combotree('reload');
	}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="roleAddForm" class="itemForm" method="post">
		<table cellpadding="5">
			<tr>
				<td>角色名:</td>
				<td><input class="easyui-textbox" type="text" name="name"
					data-options="required:true,validType:'length[1,12]'" ></input></td>
			</tr>
				<tr>
				<td>权限:</td>
				<td><input id="permissionadd"></input></td>
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


$('#permissionadd').combotree({
	multiple:true,
    required: false,
    url: '/permission/findPermissionTree',
}); 

	function submitForm() {
		//表单校验
		if (!$('#roleAddForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		var par=$("#roleAddForm").serialize()+"&perids="+$("#permissionadd").combotree("getValues");
		//alert($("#roleAddForm").serialize());
		$.post("/role/add", par, function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '新增角色成功!');
				$('#roleAddForm').form('reset');
			} else {
				$.messager.alert("提示", "新增角色失败!");
			} 
		}); 	 
	}

	function clearForm() {
		$('#roleAddForm').form('reset');
	}
</script>

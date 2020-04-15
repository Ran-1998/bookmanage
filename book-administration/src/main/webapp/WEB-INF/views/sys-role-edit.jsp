<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="RoleEditForm"  method="post">
		<input type="hidden" name="id" />
		<table cellpadding="5">
			<tr>
				<td>角色名:</td>
				<td><input id="name" class="easyui-textbox" type="text" name="roleName" "></input></td>
			</tr>
			<tr>
				<td>权限:</td>
				<td><input id="permission"></input></td>
			</tr>
		</table>
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitAForm()">提交</a>
	</div>
</div>
<script>

 $('#permission').combotree({
	multiple:true,
    url: '/permission/findPermissionTrees',
}); 

	function submitAForm() {
		if (!$('#RoleEditForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}

		var paramJson = [];
		$("#RoleEditForm .params li").each(function(i, e) {
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
		
		//alert($("#itemeEditForm [name=itemParams]").textbox("getValue"));
		var par=$("#RoleEditForm").serialize()+"&perids="+$("#permission").combotree("getValues");
		//alert( $('#name').text());
		$.post("/role/update",par, function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '修改用户成功!', 'info', function() {
					$("#RoleEditWindow").window('close');
					$("#roleList").datagrid("reload");
				});
			} else {
				$.message.alert("提示", data.msg);
			}
		});
	}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="UserEditForm"  method="post">
		<input type="hidden" name="id" />
		<table cellpadding="5">
			<tr>
				<td>学号:</td>
				<td><input class="easyui-textbox" type="text" name="studentId"
					data-options="required:true,validType:'length[1,12]'"></input></td>
			</tr>
			<tr>
				<td>姓名:</td>
				<td><input class="easyui-textbox" type="text" name="name"
					data-options="required:true,validType:'length[1,10]'"></input></td>
			</tr>
			<tr>
				<td>班级:</td>
				<td><input class="easyui-textbox" type="text" name="className"
					data-options="validType:'length[1,20]'" />
			</tr>
			<tr>
				<td>电话:</td>
				<td><input class="easyui-textbox" type="text" name="phone"
					data-options="validType:'length[11,11]'" /></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input class="easyui-textbox" type="text" name="password"
					data-options="validType:'length[6,15]'" /></td>
			</tr>
		</table>
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitAForm()">提交</a>
	</div>
</div>
<script>
	function submitAForm() {
		if (!$('#UserEditForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}

		var paramJson = [];
		$("#UserEditForm .params li").each(function(i, e) {
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
		
		//alert($("#UserEditForm").serialize());
		//alert($("#itemeEditForm [name=itemParams]").textbox("getValue"));
		
		$.post("/user/update", $("#UserEditForm").serialize(), function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '修改用户成功!', 'info', function() {
					$("#UseEditWindow").window('close');
					$("#userList").datagrid("reload");
				});
			} else {
				$.message.alert("提示", data.msg);
			}
		});
	}
</script>

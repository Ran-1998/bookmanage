<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="userAddForm" class="itemForm" method="post">
		<table cellpadding="5">
			<tr>
				<td>学号:</td>
				<td><input class="easyui-textbox" type="text" name="studentId"
					data-options="required:true,validType:'length[1,12]'" ></input></td>
			</tr>
			<tr>
				<td>姓名:</td>
				<td><input class="easyui-textbox" type="text" name="name"
					data-options="required:true,validType:'length[1,10]'" ></input></td>
			</tr>
			<tr>
				<td>班级:</td>
				<td><input class="easyui-textbox" type="text" name="className" 
				data-options="validType:'length[1,20]'"
				/> 
			</tr>
			<tr>
				<td>电话:</td>
				<td><input class="easyui-textbox" type="text" name="phone" 
				data-options="validType:'length[11,11]'"/></td>
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
			onclick="submitForm()">提交</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">

	function submitForm() {
		//表单校验
		if (!$('#userAddForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}

		//alert($("#userAddForm").serialize());
		$.post("/user/add", $("#userAddForm").serialize(), function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '新增用户成功!');
				$('#userAddForm').form('reset');
			} else {
				$.messager.alert("提示", "新增用户失败!");
			} 
		}); 	 
	}

	function clearForm() {
		$('#userAddForm').form('reset');
	}
</script>

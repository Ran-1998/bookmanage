<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<form id="itemAddForm" class="itemForm" method="post">
		<table cellpadding="5">
			<tr>
				<td>图书类目:</td>
				<td><a href="javascript:void(0)"
					class="easyui-linkbutton selectItemCat">选择类目</a> <input
					type="hidden" name="cid" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>图书标题:</td>
				<td><input class="easyui-textbox" type="text" name="bookname"
					data-options="required:true"></input></td>
			</tr>
			<tr>
				<td>图书作者:</td>
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
			</tr>
			<tr>
				<td>出版时间:</td>
				<td><input class="easyui-datetimebox" name="publicationdate">
				</td>
			</tr>
			<tr>
				<td>图书图片:</td>
				<td><a href="javascript:void(0)"
					class="easyui-linkbutton picFileUpload">上传图片</a> <input
					type="hidden" name="image" /></td>
			</tr>
			<tr>
				<td>图书描述:</td>
				<td><textarea
						style="width: 800px; height: 300px; visibility: hidden;"
						name="briefintroduction"></textarea></td>
			</tr>
		</table>
		<input type="hidden" name="itemParams" />
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitForm()">提交</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	var itemAddEditor;
	$(function() {
		//和form下的briefintroduction组件绑定
		itemAddEditor = KindEditorUtil
				.createEditor("#itemAddForm [name=briefintroduction]");
		KindEditorUtil.init({
			fun : function(node) {
				KindEditorUtil.changeItemParam(node, "itemAddForm");
			}
		});
	});
	function submitForm() {
		//表单校验
		if (!$('#itemAddForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		if ($("[name=cid]").val()=='') {
			$.messager.alert('提示', '请填写类别!');
			return;
		}
// 	alert($("[name=cid]").val());
		
		itemAddEditor.sync();//将输入的内容同步到多行文本中
		var paramJson = [];
		$("#itemAddForm .params li").each(function(i, e) {
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
		paramJson = JSON.stringify(paramJson);//将对象转化为json字符串

		$("#itemAddForm [name=itemParams]").val(paramJson);

		/*$.post/get(url,JSON,function(data){....})  
			?id=1&title="天龙八部&key=value...."
		 */
		//alert($("#itemAddForm").serialize());
	 	$.post("/book/save", $("#itemAddForm").serialize(), function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '新增图书成功!');
			} else {
				$.messager.alert("提示", "新增图书失败!");
			} 
		}); 
	}

	function clearForm() {
		$('#itemAddForm').form('reset');
		itemAddEditor.html('');
	}
</script>

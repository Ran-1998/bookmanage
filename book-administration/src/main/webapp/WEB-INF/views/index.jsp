<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书借阅后台管理系统</title>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
<style type="text/css">
.content {
	padding: 10px 10px 10px 10px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'菜单',split:true"
		style="width: 300px;">
		<div  class="easyui-accordion" style="width: 300px;" data-options="multiple:true,border:false">
			<div title="图书管理"">
				<div>
					<ul id="menu1" class="easyui-tree">
						<li data-options="attributes:{'url':'/page/book-list'}">查询图书</li>
						<li data-options="attributes:{'url':'/page/book-add'}">新增图书</li>
						<li data-options="attributes:{'url':'/page/book-cat-list'}">图书类别管理</li>
						<li data-options="attributes:{'url':'/page/book-cat-add'}">新增类别</li>
						<li data-options="attributes:{'url':'/page/book-review'}">图书评论管理</li>
					</ul>
					</li>
				</div>
			</div>
			<div title="用户管理"">
				<div>
					<ul id="menu2" class="easyui-tree">
						<li data-options="attributes:{'url':'/page/user-list'}">查询用户</li>
						<li data-options="attributes:{'url':'/page/user-add'}">新增用户</li>
						<li data-options="attributes:{'url':'/page/borrow-list'}">借阅管理</li>
					</ul>
					</li>
				</div>
			</div>
			<div title="系统管理"">
				<div>
					<ul id="menu3" class="easyui-tree">
						<li data-options="attributes:{'url':'/page/sys-admin-list'}">系统用户管理</li>
						<li data-options="attributes:{'url':'/page/sys-admin-add'}">系统用户新增</li>
						<li data-options="attributes:{'url':'/page/sys-role-list'}">系统角色管理</li>
						<li data-options="attributes:{'url':'/page/sys-role-add'}">系统角色新增</li>
						<li data-options="attributes:{'url':'/page/sys-permission-list'}">系统权限管理</li>
						<li data-options="attributes:{'url':'/page/sys-permission-add'}">系统权限新增</li>
					</ul>
					</li>
				</div>
			</div>
			<div title="日志管理" ">
				<div>
					<ul id="menu4" class="easyui-tree">
						<li data-options="attributes:{'url':'/page/log-list'}">查询日志</li>
					</ul>
					</li>
				</div>
			</div>
		</div>
		<!-- 	<ul id="menu" class="easyui-tree"
			style="margin-top: 10px; margin-left: 5px;">
			<li><span>图书管理</span>
				<ul>
					<li data-options="attributes:{'url':'/page/book-list'}">查询图书</li>
					<li data-options="attributes:{'url':'/page/book-add'}">新增图书</li>
					<li data-options="attributes:{'url':'/page/book-cat-list'}">图书类别管理</li>
					<li data-options="attributes:{'url':'/page/book-cat-add'}">新增类别</li>
				</ul></li>
			<li><span>用户管理</span>
				<ul>
					<li data-options="attributes:{'url':'/page/user-list'}">查询用户</li>
					<li data-options="attributes:{'url':'/page/user-add'}">新增用户</li>
					<li data-options="attributes:{'url':'/page/borrow-list'}">借阅管理</li>
				</ul></li>
			<li><span>日志管理</span>
				<ul>
					<li data-options="attributes:{'url':'/page/log-list'}">查询日志</li>
				</ul></li>
				
				<li><span>系统管理</span>
				<ul>
					<li data-options="attributes:{'url':'/page/sys-admin-list'}">系统用户管理</li>
					<li data-options="attributes:{'url':'/page/sys-admin-add'}">系统用户新增</li>
					<li data-options="attributes:{'url':'/page/sys-role-list'}">系统角色管理</li>
					<li data-options="attributes:{'url':'/page/sys-role-add'}">系统角色新增</li>
					<li data-options="attributes:{'url':'/page/sys-permission-list'}">系统权限管理</li>
					<li data-options="attributes:{'url':'/page/sys-permission-add'}">系统权限新增</li>
				</ul></li>
		</ul> -->
	</div>
	<div data-options="region:'center',title:''">
		<div id="tabs" class="easyui-tabs">
			<div title="欢迎使用" style="padding: 20px;">
				<div>
					<a style="font-size: 15px;">当前用户:</a> <a id="user"
						style="font-size: 15px;"></a> <a id="btn" href="#"
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">退出</a>
				</div>
			</div>

		</div>
	</div>
	<div id="tt"></div>
	<script type="text/javascript">
		$(function() {
			$('#menu1').tree({
				onClick : function(node) {
					if ($('#menu1').tree("isLeaf", node.target)) {
						click(node);
					}
				}
			});
			$('#menu2').tree({
				onClick : function(node) {
					if ($('#menu2').tree("isLeaf", node.target)) {
						click(node);
					}
				}
			});
			$('#menu3').tree({
				onClick : function(node) {
					if ($('#menu3').tree("isLeaf", node.target)) {
						click(node);
					}
				}
			});
			$('#menu4').tree({
				onClick : function(node) {
					if ($('#menu4').tree("isLeaf", node.target)) {
						click(node);
					}
				}
			});
			function click(node) {
				var tabs = $("#tabs");
				var tab = tabs.tabs("getTab", node.text);
				if (tab) {
					tabs.tabs("select", node.text);
				} else {
					tabs.tabs('add', {
						title : node.text,
						href : node.attributes.url,
						closable : true,
						bodyCls : "content"
					});
				}
			}
			$.ajax({
				url : '/admin/getName',
				type : "get",
				success : function(res) {
					var name = res;
					document.getElementById("user").innerHTML = name;
				}
			});
			$('#btn').bind('click', function() {
				$.ajax({
					url : '/admin/loginOut',
					type : "get",
					success : function(res) {
						location.href = "/admin/doLoginUI";
					}
				});
			});

		});

		function getverify(){
		var data=$('#verify').val();
		console.log(sels);
		$.get("/book/cat/select",{name : ss} ,
				function(data) {
					$('#itemParamList').treegrid('loadData',data);
				});
			}
	</script>
</body>
</html>
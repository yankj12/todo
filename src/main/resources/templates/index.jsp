<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/main/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>LayOut</title>
<script src="${ctx }/easyui/jquery.min.js"
	type="text/javascript"></script>
<script src="${ctx }/easyui/jquery.easyui.min.js"
	type="text/javascript"></script>
<link href="${ctx }/easyui/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx }/easyui/themes/icon.css"
	rel="stylesheet" type="text/css" />
<script language="JavaScript">
	function addTab(title, url) {
		if ($('#home').tabs('exists', title)) {
			$('#home').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0" src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#home').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}

	$(document).ready(function() {

	});
</script>
<style>
.footer {
	width: 100%;
	text-align: center;
	line-height: 35px;
}

.top-bg {
	background-color: #d8e4fe;
	height: 80px;
}
</style>
</head>

<body class="easyui-layout">

	<div region="north" border="true" split="true"
		style="overflow: hidden; height: 7%;">
		<div class="footer">
			简单数据系统
		</div>
	</div>

<!-- 	<div region="south" border="true" split="true"
		style="overflow: hidden; height: 40px;">
		<div class="footer">
			版权所有：<a href="http://www.ewan.cn/">益玩平台</a>
		</div>
	</div> -->

	<div region="west" split="true" title="功能菜单" style="width: 10%;">

		<div id="aa" class="easyui-accordion"
			style="position: absolute; top: 27px; left: 0px; right: 0px; bottom: 0px;">

			<div title="查看数据" selected="true"
				style="overflow: auto; padding: 10px;">
				<ul class="easyui-tree">
					<li><a href="#" onclick="addTab('待办任务','${ctx }/task/listTaskToDo')">待办任务</a></li>
				</ul>
			</div>

			<div title="添加数据" style="padding: 10px;">
				<ul class="easyui-tree">

					<li><a href="#" onclick="addTab('添加用户','add')">添加用户</a></li>

				</ul>
			</div>

		</div>
	</div>

	<div id="mainPanle" region="center" style="overflow: hidden;width: 90%;">

		<div id="home" class="easyui-tabs" style="width: 100%; height: 100%;">
			<div title="Home">Hello,welcome to use this system.</div>
		</div>
	</div>
</body>
</html>
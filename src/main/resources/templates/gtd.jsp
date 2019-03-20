<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/main/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>GTD is a life style.</title>
	<link rel="icon" href="${ctx }/commons/icons/g_24px.ico" type="image/x-icon"/> 
	<script src="${ctx }/easyui/jquery.min.js" type="text/javascript"></script>
	<script src="${ctx }/easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<link href="${ctx }/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	
	<script src="${ctx }/main/js/mycommon.js" type="text/javascript"></script>
	<script src="${ctx }/pages/task/js/listTaskToDo.js" type="text/javascript"></script>
	
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
		//清空搜索栏
		$('#tt_input').textbox('clear');
		
		
		//给一些菜单tree添加事件
		//$('#tt').tree({
		//	onClick: function(node){
		//		alert(node.text);  // alert node text property when clicked
		//	}
		//});
		
		//初始化的时候ajax加载菜单数据，再讲数据绘制到菜单中
		$.ajax({
	        type:"POST", 
	        url:contextRootPath + "/bill/queryBill",
	        dataType:"json", 
	        contentType: "text/html;charset=UTF-8", 
	        success:function(result){
	        	if (result.success){
	        		var ary = result.rows;
	        		
					var nodes = [];
					
	        		$('#bill_menu').tree('append', {
	        			data:ary
	        		});
	        	}
	        },
	       	failure:function (result) {  
	       		//(提示框标题，提示信息)
	    		$.messager.alert('提示',result.errorMsg);
	       	}
		});
		
		//点击菜单时打开tab页
		$('#bill_menu').tree({
			onClick: function(node){
				//alert(node.text);  // alert node text property when clicked
				addTab(node.text, contextRootPath +'/task/listTaskToDo');
			}
		});
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

		#fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
		.fitem input{
			width:160px;
		}
</style>
</head>

<body class="easyui-layout">

	<!-- <div region="north" border="true" split="true"
		style="overflow: hidden; height: 7%;">
		<div class="footer">
			简单数据系统
		</div>
	</div> -->

<!-- 	<div region="south" border="true" split="true"
		style="overflow: hidden; height: 40px;">
		<div class="footer">
			版权所有：<a href="http://www.ewan.cn/">益玩平台</a>
		</div>
	</div> -->

	<div region="west" split="true" title="GTD" style="width: 10%;">

		<div id="menu_accordin" class="easyui-accordion"
			style="position: absolute; top: 27px; left: 0px; right: 0px; bottom: 0px;">
			<div title="个人中心" selected="false"
				style="overflow: auto; padding: 10px;">
				<ul class="easyui-tree">
					<li><a href="#" onclick="addTab('待办任务','${ctx }/task/listTaskToDo')">待办任务</a></li>
				</ul>
			</div>
			
			<div title="清单" selected="true"
				style="overflow: auto; padding: 10px;">
				<ul id="bill_menu" class="easyui-tree">
					<!-- <li><a href="#" onclick="addTab('待办任务','${ctx }/task/listTaskToDo')">待办任务</a></li>
					<li><a href="#" onclick="addTab('周','${ctx }/task/listTaskToDo')">周</a></li>
					<li><a href="#" onclick="addTab('日历','${ctx }/task/listTaskToDo')">日历</a></li> -->
				</ul>
			</div>

			<div title="管理清单" style="padding: 10px;">
				<ul class="easyui-tree">
					<li><a href="#" onclick="addTab('创建清单','add')">创建清单</a></li>

				</ul>
			</div>
			<br/>
			<input id="bill_input" class="easyui-textbox" style="width:100%" data-options="
					prompt: '创建清单',
					iconWidth: 22,
					icons: [{
						iconCls:'icon-add',
						handler: function(e){
							var inputText = $(e.data.target).textbox('getValue');
							if(inputText != null && inputText != ''){
								//调用新增代办任务方法
								$.post('${ctx }/bill/saveBill',{name:inputText,editType:'new'},function(result){
									if (result.success){
										//需要重新加载菜单
										//或者将添加的选项添加进去
										$.messager.alert('提示','清单添加成功！');
										
										$('#bill_menu').tree('append', {
						        			data:[{
						        				text:inputText
						        			}]
						        		});
						        		
						        		$(e.data.target).textbox('clear');
									} else {
										$.messager.show({	// show error message
											title: 'Error',
											msg: result.errorMsg
										});
									}
								},'json');
							}
						}
					},{
						iconCls:'icon-cancel',
						handler: function(e){
							$(e.data.target).textbox('clear');
						}
					}]
					">
		</div>
	</div>

	<div id="mainPanle" region="center" style="overflow: hidden;width:80%;">
		<div id="home" class="easyui-tabs" style="width: 100%; height: 100%;">
			<div title="Home">Hello,welcome to use this system.</div>
		</div>
	</div>
	
</body>
</html>
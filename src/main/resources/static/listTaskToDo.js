var url;

function init(){
	console.log('listTaskToDo.init');
	
	//为查询出来的待办任务添加一个双击事件，打开右侧的折叠栏，显示详细信息
	$('#dg1').datagrid({
		onDblClickRow: function(index,field,value){
			console.log('onDblClickRow');
			
			//先查询数据
			
			//打开右侧的折叠栏，显示详细信息
			$('#taskToDo_layout').layout('expand', 'east');    //expand, collapse
			
			//将数据填写到打开的折叠栏中
			
		},
		onRowContextMenu: function(e,index,row){
			 e.preventDefault(); //阻止浏览器捕获右键事件
             $(this).datagrid("clearSelections"); //取消所有选中项
             $(this).datagrid("selectRow", index); //根据索引选中该行
             $('#datagrid_rclick_menu_1').menu('show', {                        
                 left: e.pageX,//在鼠标点击处显示菜单
                 top: e.pageY
             });
             e.preventDefault();  //阻止浏览器自带的右键菜单弹出
		}
	});
	
	//为查询出来的待办任务添加一个双击事件，打开右侧的折叠栏，显示详细信息
	$('#dg2').datagrid({
		onDblClickRow: function(index,field,value){
			console.log('onDblClickRow');
			
			//先查询数据
			
			//打开右侧的折叠栏，显示详细信息
			$('#taskToDo_layout').layout('expand', 'east');    //expand, collapse
			
			//将数据填写到打开的折叠栏中
			
		},
		onRowContextMenu: function(e,index,row){
			 e.preventDefault(); //阻止浏览器捕获右键事件
            $(this).datagrid("clearSelections"); //取消所有选中项
            $(this).datagrid("selectRow", index); //根据索引选中该行
            $('#datagrid_rclick_menu_2').menu('show', {                        
                left: e.pageX,//在鼠标点击处显示菜单
                top: e.pageY
            });
            e.preventDefault();  //阻止浏览器自带的右键菜单弹出
		}
	});
	
	
	//
	//mv_to_list_sub_menu
	// append a sub menu item
	var item = $('#datagrid_rclick_menu_1').menu('findItem', '移动任务到其他清单');  // find 'Open' item
	
	$('#datagrid_rclick_menu_1').menu('appendItem', {
		parent: item.target,  // the parent item element
		text: 'sub menu 5',
		iconCls: 'icon-excel',
		onclick: function(){alert('Open Excel')}
	});
	
	$('#datagrid_rclick_menu_1').menu('appendItem', {
		parent: item.target,  // the parent item element
		text: 'sub menu 5',
		iconCls: 'icon-excel',
		onclick: function(){alert('Open Excel')}
	});
	$('#mm').menu('appendItem', {
		parent: item.target,  // the parent item element
		text: 'sub menu 6',
		iconCls: 'icon-excel',
		onclick: function(){alert('Open Excel')}
	});
	
}


//通过让数据表格url为调用js方法，这样可以让不管是分页插件还是查询条件点击下一页，都可以将查询条件和分页信息同时传到后台action
function exeQuery(){
	var data = $('#conditionForm').serializeArray();//先进行序列化数组操作
    var postData = {};  //创建一个对象  
    $.each(data, function(n,v) {
    	//postData[data[n].name]=data[n].value;  //循环数组，把数组的每一项都添加到对象中
    	//上面这种方式，在遇到多个同名input的时候，会进行覆盖操作，导致后台只能获取到最后一个元素
    	
        //如果有多个input的name重名，那么postData[data[n].name]存储的应该是一个数组
        if(postData[data[n].name]){
        	//已经存在的话
        	//有可能其中存储的是单个变量，也有可能存储的是个数组
        	var value = postData[data[n].name];
        	//$.isArray(value);  //jquery的方式判断是否为数组
        	if(value instanceof Array){
        		value.push(data[n].value);
        	}else{
        		var perviewElement = postData[data[n].name];
        		var ary = new Array();
        		//先应该将上一个元素添加到数组中
        		ary.push(perviewElement);
        		ary.push(data[n].value);
        		postData[data[n].name]=ary;
        	}
        }else{
        	//没有存在，那么我们当做单个变量来处理
        	postData[data[n].name]=data[n].value;  //循环数组，把数组的每一项都添加到对象中
        }
    });
	//通过设置datagrid的queryParams，可以将数据表格的参数带到后台
    //此处并不需要专门给url赋值，因为使用的是表单的url
    $('#dg').datagrid({
		//queryParams: {
		//	userNames: '严凯杰'
		//}
		queryParams:postData
	}); 
}

/**
 * 重新加载数据
 * @param datagridId  数据集datagrid的id
 * @returns
 */
function reloadData(datagridId){
	$('#' + datagridId).datagrid('reload');	// reload the user data
}

function newRecord(title){
	//打开新的标签，在新的标签中进行添加操作
	//addTab(title,'leave/editLeaveApplication?editType=new');
	
	$('#dlg').dialog('open').dialog('setTitle','新增任务');
	$('#fm').form('clear');
	//设置修改类型，否则action中保存方法不知道是什么修改类型
	$('#editType_edit').val("new");
	
	url = 'task/saveTaskToDo?editType=new';
	//这里只是给url赋值，并不会调用url，调用url在form的submit处，也就是下面的saveUser方法中
	
	//给一些下来列表赋默认值
	//有效状态置为有效
	$('#validStatus_edit').combobox('setValue', '1');
	
	//紧急程度置为普通
	$('#emergency_edit').combobox('setValue', '1');
	
	//进度置为新建
	$('#progress_edit').combobox('setValue', '1');
	
	
}


function editRecord(title){
	
	var row = $('#dg').datagrid('getSelected');    //这一步可以改造为从后台异步获取数据
	
	if(row != null){
		var id = row.id;
		
		//异步从action中加载数据
		$.ajax({
	        type:"GET", 
	        url:"task/editTaskToDo?editType=edit&id=" + id,
	        //url:"leave/saveLeaveApplication?editType=新增",
	        dataType:"json", 
	        //data:postData,
	        contentType: "text/html;charset=UTF-8", 
	        success:function(result){
	        	if (result.success){
	        		$('#dlg').dialog('open').dialog('setTitle','修改任务');
	        		
	        		var taskToDo = result.taskToDo;
	        		var msg = '';
	        		if(result.msg != null){
	        			msg = result.msg;
	        		}
	        		//设置修改类型，否则action中保存方法不知道是什么修改类型
	        		$('#editType_edit').val("edit");
	        		
	        		//alert(audit);
	        		//将审批流水号添加到修改页面
	        		$("#id").text(taskToDo.id);
	        		$("#id_edit").val(taskToDo.id);
	        		
	        		//$("#serialNo").html("aaa");    
	        		//text()和html()的区别是：前者是处理的文本内容，只能写文本如果写了上面的标记则会以文本形式输出；
	        		//后者可以解析文本中的html标记，就是你可以添加像<a></a>、<p></p>等标记，最后会解析为其效果。	  
	        		
	        		//指派给
	        		$('#assign_edit').combobox('setValue', taskToDo.assign);
	        		
	        		//紧急程度
	        		$('#emergency_edit').combobox('setValue', taskToDo.emergency);
	        		
	        		//进度状态
	        		$('#progress_edit').combobox('setValue', taskToDo.progress);
	        		
	        		//有效状态
	        		$('#validStatus_edit').combobox('setValue', taskToDo.validStatus);
	        		
	        	}else{
	        		$.messager.alert('提示',result.errorMsg);
	        	}
	        },
	       	failure:function (result) {  
	       		//(提示框标题，提示信息)
	    		$.messager.alert('提示','保存失败');
	       	}
		});
	}else{
		//alert("请选择一条记录进行修改");
		//(提示框标题，提示信息)
		$.messager.alert('提示','请选择一条记录进行修改');
	}
}

function updateFinishFlag(datagridId, finishFlag){
	var rows = $('#' + datagridId).datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 0){
		var serialNos = "";  
        for (var i = 0; i < rows.length; i++) {  
            if (serialNos == '') {  
            	serialNos = rows[i].serialNo;  
            } else {  
            	serialNos += ',' + rows[i].serialNo;  
            }  
        }  
		$.post(contextRootPath + '/task/updateFinishFlag',{serialNos:serialNos, finishFlag:finishFlag},function(result){
			if (result.success){
				$('#dg1').datagrid('reload');	// reload the user data
				$('#dg2').datagrid('reload');	// reload the user data
			} else {
				$.messager.show({	// show error message
					title: 'Error',
					msg: result.errorMsg
				});
			}
		},'json');
	}
}

function updateEmergencyFlag(datagridId, emergencyFlag){
	var rows = $('#' + datagridId).datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 0){
		var serialNos = "";  
        for (var i = 0; i < rows.length; i++) {  
            if (serialNos == '') {  
            	serialNos = rows[i].serialNo;  
            } else {  
            	serialNos += ',' + rows[i].serialNo;  
            }  
        }  
		$.post(contextRootPath + '/task/updateEmergencyFlag',{serialNos:serialNos, emergencyFlag:emergencyFlag},function(result){
			if (result.success){
				$('#dg1').datagrid('reload');	// reload the user data
				$('#dg2').datagrid('reload');	// reload the user data
			} else {
				$.messager.show({	// show error message
					title: 'Error',
					msg: result.errorMsg
				});
			}
		},'json');
	}
}

/**
 * 更新到期时间
 * @param day
 * @returns
 */
//today tomorrow null
function updateDeadLineOnRow(datagridId, day){
	var rows = $('#' + datagridId).datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 0){
		var serialNos = "";  
        for (var i = 0; i < rows.length; i++) {  
            if (serialNos == '') {  
            	serialNos = rows[i].serialNo;  
            } else {  
            	serialNos += ',' + rows[i].serialNo;  
            }  
        }
        
		var time = new Date();
		time.setHours(9, 0, 0);
		
		if(day != null){
			if(day == 'today'){
				
			}else if(day == 'tomorrow'){
				time.setDate(time.getDate() + 1);
			}else if(day == 'null'){
				time = null;
			}
		}else{
			time = null;
		}
		
		//将日期类型转换为字符串，是为了方便后台处理时间
		var timeStr = null;
		if(time != null && time != ''){
			//myDateTimeFormatter 是在 mycommon.js中的方法
			timeStr = myDateTimeFormatter(time);
		}else{
			timeStr = null;
		}
		
		//通过异步方法在后台更新数据
		//并reload结果集
		$.post(contextRootPath + '/task/updateDeadLine',{serialNos:serialNos, deadLine:timeStr},function(result){
			if (result.success){
				$('#dg1').datagrid('reload');	// reload the user data
				$('#dg2').datagrid('reload');	// reload the user data
			} else {
				$.messager.show({	// show error message
					title: 'Error',
					msg: result.errorMsg
				});
			}
		},'json');
	}
}

function destroyRecord(datagridId){
	var rows = $('#' + datagridId).datagrid('getSelections');
	if (rows != null && rows.length != null && rows.length > 0){
		var serialNos = "";  
        for (var i = 0; i < rows.length; i++) {  
            if (serialNos == '') {  
            	serialNos = rows[i].serialNo;  
            } else {  
            	serialNos += ',' + rows[i].serialNo;  
            }  
        }
    
        //通过异步方法在后台更新数据
		//并reload结果集
		$.post(contextRootPath + '/task/deleteTaskToDo',{serialNos:serialNos},function(result){
			if (result.success){
				$('#' + datagridId).datagrid('reload');	// reload the user data
			} else {
				$.messager.show({	// show error message
					title: 'Error',
					msg: result.errorMsg
				});
			}
		},'json');
	}
}

function createNewListByTask(){
	
}

function sendTaskByEmail(){
	
}

/**
 * attendanceDay控件选择日期后，当点中某一天的时候会触发onSelect事件
 * @param date
 */
function setStatisticsMonthByDay(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	
	//经过多次尝试，在js中给easyui的textbox赋值，需要向下面这样进行
	//尽量通过id的方式获取，可以选择到easyui的textbox，如果通过其他选择器，那么只能选中textbox组件的某一部分
	$("#statisticsMonth_edit").combobox('setValue',''+y+(m<10?('0'+m):m));
}

/**
 * 下拉列表控件选择option后，会触发onSelect事件
 * @param date
 */
function setUserNameByCode(s){
	var name = s.label;
	//如下方式set值
	$('#userName_edit').combobox('setValue', name);
}

/**
 * 下拉列表控件选择option后，会触发onSelect事件
 * @param date
 */
function selectUserCodeByName(s){
	var name = s.label;
	var list = $('#userCode_edit').combobox('getData');
		
	var userCode = '';
	if(list != null){
		for(var i=0;i<list.length;i++){
			if(list[i].label==name){
				userCode = list[i].value;
				break;
			}
		}
	}
	
	//select方法会选中某一项
	//$('#userCode_edit').combobox('select', userCode);
	$('#userCode_edit').combobox('setValue', userCode);
	
	//easyui 的textbox，但是我觉得combobox类似
	//setText	text	Set the displaying text value.
	//setValue	value	Set the component value.
}

/**
 * 批量处理待办任务
 * @param title
 */
//function batchEdit(title){
//	//打开新的标签，在新的标签中进行添加操作
//	addTab(title,'task/batchEditTaskToDo');
//	
//}


/**
 * jquery easyui 在子tab页中打开新tab页
 * @param title
 * @param url
 */
function addTab(title, url) {
	//jquery easyui 在子tab页中打开新tab页(关于easyUI在子页面增加显示tabs的一个问题)
	//在子页面点个按钮也能触发增加子页面的情况。
	//改正的关键是用top.jQuery这个函数，这个函数具体出外我忘记了，用法看似是取得整个父页面对象，正确是写法：
	//http://blog.csdn.net/zhang527836447/article/details/44676581
	//其他写法尝试了下有问题，就采用了如下的写法	
	var jq = top.jQuery;
	if (jq('#home').tabs('exists', title)) {
		jq('#home').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0" src="'
				+ url + '" style="width:100%;height:100%;"></iframe>';
		jq('#home').tabs('add', {
			title : title,
			content : content,
			closable : true
		});
	}
}	


/**
 * 将easyui的datagrid中的代码翻译为汉字
 * @param val
 * @param row
 * @returns
 */
function formatTrueOrFalse(val,row){
	if(val == '1'){
		return '是';
	}else if(val == '0'){
		return '否';
	}
}


/**
 * 将easyui的datagrid中的代码翻译为汉字
 * @param val
 * @param row
 * @returns
 */
function formatEmergencyFlag(val,row){
	if(val == '1'){
		return '重要';
	}else if(val == '0'){
		return '非重要';
	}
}

/**
 * 将easyui的datagrid中的代码翻译为汉字
 * @param val
 * @param row
 * @returns
 */
function formatFinishFlag(val,row){
	if(val == '1'){
		return '已完成';
	}else if(val == '0'){
		return '未完成';
	}
}


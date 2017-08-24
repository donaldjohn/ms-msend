<@ms.html5>
	<@ms.nav title="发送消息模板表管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@ms.addButton id="addTemplateBtn"/>
					<@ms.delButton id="delTemplateBtn"/>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="templateList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delTemplate" title="授权数据删除" >
		<@ms.modalBody>删除此授权
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteTemplateBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#templateList").bootstrapTable({
			url:"${managerPath}/msend/template/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
				    	{
				        	field: 'templateId',
				        	title: '编号',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/template/form.do?templateId="+row.templateId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'modelId',
				        	title: '模块编号',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/template/form.do?modelId="+row.modelId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'appId',
				        	title: '应用编号',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/template/form.do?appId="+row.appId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'templateTitle',
				        	title: '标题',
				        	width:'100',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/template/form.do?templateTitle="+row.templateTitle;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'templateMail',
				        	title: '',
				        	width:'1,000',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/template/form.do?templateMail="+row.templateMail;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'templateSms',
				        	title: '',
				        	width:'1,000',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/template/form.do?templateSms="+row.templateSms;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'templateCode',
				        	title: '邮件模块代码',
				        	width:'20',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/template/form.do?templateCode="+row.templateCode;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	}			]
	    })
	})
	//增加按钮
	$("#addTemplateBtn").click(function(){
		location.href ="${managerPath}/msend/template/form.do"; 
	})
	//删除按钮
	$("#delTemplateBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#templateList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delTemplate").modal();
		}
	})
	
	$("#deleteTemplateBtn").click(function(){
		var rows = $("#templateList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/msend/template/delete.do",
			data: JSON.stringify(rows),
			dataType: "json",
			contentType: "application/json",
			success:function(msg) {
				if(msg.result == true) {
					<@ms.notify msg= "删除成功" type= "success" />
				}else {
					<@ms.notify msg= "删除失败" type= "fail" />
				}
				location.reload();
			}
		})
	});
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#templateList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#templateList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>
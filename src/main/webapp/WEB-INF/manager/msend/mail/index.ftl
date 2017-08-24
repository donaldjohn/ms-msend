<@ms.html5>
	<@ms.nav title="邮件管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@ms.addButton id="addMailBtn"/>
					<@ms.delButton id="delMailBtn"/>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="mailList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delMail" title="授权数据删除" >
		<@ms.modalBody>删除此授权
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteMailBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#mailList").bootstrapTable({
			url:"${managerPath}/msend/mail/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
				    	{
				        	field: 'appId',
				        	title: '应用编号',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?appId="+row.appId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailType',
				        	title: '邮件类型',
				        	width:'255',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailType="+row.mailType;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailName',
				        	title: '账号',
				        	width:'255',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailName="+row.mailName;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailPassword',
				        	title: '',
				        	width:'255',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailPassword="+row.mailPassword;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailPort',
				        	title: '',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailPort="+row.mailPort;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailServer',
				        	title: '服务器',
				        	width:'255',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailServer="+row.mailServer;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailForm',
				        	title: '',
				        	width:'255',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailForm="+row.mailForm;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailFormName',
				        	title: '',
				        	width:'0',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailFormName="+row.mailFormName;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'mailEnable',
				        	title: '0启用 1禁用',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/mail/form.do?mailEnable="+row.mailEnable;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	}			]
	    })
	})
	//增加按钮
	$("#addMailBtn").click(function(){
		location.href ="${managerPath}/msend/mail/form.do"; 
	})
	//删除按钮
	$("#delMailBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#mailList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delMail").modal();
		}
	})
	
	$("#deleteMailBtn").click(function(){
		var rows = $("#mailList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/msend/mail/delete.do",
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
        var params = $('#mailList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#mailList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>
<@ms.html5>
	<@ms.nav title="暂无描述管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@ms.addButton id="addSmsBtn"/>
					<@ms.delButton id="delSmsBtn"/>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="smsList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delSms" title="授权数据删除" >
		<@ms.modalBody>删除此授权
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteSmsBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#smsList").bootstrapTable({
			url:"${managerPath}/msend/sms/list.do",
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
				        		var url = "${managerPath}/msend/sms/form.do?appId="+row.appId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsType',
				        	title: '短信接口类型',
				        	width:'150',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsType="+row.smsType;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsUsername',
				        	title: '账号',
				        	width:'20',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsUsername="+row.smsUsername;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsPassword',
				        	title: '密码',
				        	width:'60',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsPassword="+row.smsPassword;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsSendUrl',
				        	title: '发送地址',
				        	width:'500',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsSendUrl="+row.smsSendUrl;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsAccountUrl',
				        	title: '',
				        	width:'500',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsAccountUrl="+row.smsAccountUrl;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsManagerUrl',
				        	title: '短信平台后台管理地址',
				        	width:'120',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsManagerUrl="+row.smsManagerUrl;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsSignature',
				        	title: '签名',
				        	width:'20',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsSignature="+row.smsSignature;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},							    	{
				        	field: 'smsEnable',
				        	title: '0启用 1禁用',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/msend/sms/form.do?smsEnable="+row.smsEnable;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	}			]
	    })
	})
	//增加按钮
	$("#addSmsBtn").click(function(){
		location.href ="${managerPath}/msend/sms/form.do"; 
	})
	//删除按钮
	$("#delSmsBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#smsList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delSms").modal();
		}
	})
	
	$("#deleteSmsBtn").click(function(){
		var rows = $("#smsList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/msend/sms/delete.do",
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
        var params = $('#smsList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#smsList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>
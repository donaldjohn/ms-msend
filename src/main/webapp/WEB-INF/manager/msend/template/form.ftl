<@ms.html5>
	 <@ms.nav title="发送消息模板表编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="templateForm" isvalidation=true>
    		<@ms.hidden name="templateId" value="${templateEntity.templateId?default('')}"/>
    			<@ms.number label="模块编号" name="modelId" value="${templateEntity.modelId?default('')}" width="240px;" placeholder="请输入模块编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"模块编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="应用编号" name="appId" value="${templateEntity.appId?default('')}" width="240px;" placeholder="请输入应用编号" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"应用编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="标题" name="templateTitle" value="${templateEntity.templateTitle?default('')}"  width="240px;" placeholder="请输入标题" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"标题长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="邮件模块代码" name="templateCode" value="${templateEntity.templateCode?default('')}"  width="240px;" placeholder="请输入邮件模块代码" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"邮件模块代码长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/msend/template/save.do";
	if($("input[name = 'templateId']").val() > 0){
		url = "${managerPath}/msend/template/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#templateForm").data("bootstrapValidator").validate();
			var isValid = $("#templateForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:$("form[name = 'templateForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.result == true) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/msend/template/index.do";
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
					location.href= "${managerPath}/msend/template/index.do";
				}
			}
		})
	}	
</script>

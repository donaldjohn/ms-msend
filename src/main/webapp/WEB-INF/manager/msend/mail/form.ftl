<@ms.html5>
	 <@ms.nav title="邮件编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="mailForm" isvalidation=true>
    		<@ms.hidden name="appId" value="${mailEntity.appId?default('')}"/>
    			<@ms.text label="邮件类型" name="mailType" value="${mailEntity.mailType?default('')}"  width="240px;" placeholder="请输入邮件类型" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"邮件类型长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="账号" name="mailName" value="${mailEntity.mailName?default('')}"  width="240px;" placeholder="请输入账号" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"账号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="" name="mailPassword" value="${mailEntity.mailPassword?default('')}"  width="240px;" placeholder="请输入" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="" name="mailPort" value="${mailEntity.mailPort?default('')}" width="240px;" placeholder="请输入" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="服务器" name="mailServer" value="${mailEntity.mailServer?default('')}"  width="240px;" placeholder="请输入服务器" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"服务器长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="" name="mailForm" value="${mailEntity.mailForm?default('')}"  width="240px;" placeholder="请输入" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="" name="mailFormName" value="${mailEntity.mailFormName?default('')}"  width="240px;" placeholder="请输入" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="0启用 1禁用" name="mailEnable" value="${mailEntity.mailEnable?default('')}" width="240px;" placeholder="请输入0启用 1禁用" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"0启用 1禁用长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/msend/mail/save.do";
	if($("input[name = 'appId']").val() > 0){
		url = "${managerPath}/msend/mail/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#mailForm").data("bootstrapValidator").validate();
			var isValid = $("#mailForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'mailForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.result == true) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/msend/mail/index.do";
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
					location.href= "${managerPath}/msend/mail/index.do";
				}
			}
		})
	}	
</script>

<@ms.html5>
	 <@ms.nav title="暂无描述编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="smsForm" isvalidation=true>
    		<@ms.hidden name="appId" value="${smsEntity.appId?default('')}"/>
    			<@ms.text label="短信接口类型" name="smsType" value="${smsEntity.smsType?default('')}"  width="240px;" placeholder="请输入短信接口类型" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"短信接口类型长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="账号" name="smsUsername" value="${smsEntity.smsUsername?default('')}"  width="240px;" placeholder="请输入账号" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"账号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="密码" name="smsPassword" value="${smsEntity.smsPassword?default('')}"  width="240px;" placeholder="请输入密码" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"密码长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="发送地址" name="smsSendUrl" value="${smsEntity.smsSendUrl?default('')}"  width="240px;" placeholder="请输入发送地址" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"发送地址长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="" name="smsAccountUrl" value="${smsEntity.smsAccountUrl?default('')}"  width="240px;" placeholder="请输入" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="短信平台后台管理地址" name="smsManagerUrl" value="${smsEntity.smsManagerUrl?default('')}"  width="240px;" placeholder="请输入短信平台后台管理地址" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"短信平台后台管理地址长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="签名" name="smsSignature" value="${smsEntity.smsSignature?default('')}"  width="240px;" placeholder="请输入签名" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"签名长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="0启用 1禁用" name="smsEnable" value="${smsEntity.smsEnable?default('')}" width="240px;" placeholder="请输入0启用 1禁用" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"0启用 1禁用长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/msend/sms/save.do";
	if($("input[name = 'appId']").val() > 0){
		url = "${managerPath}/msend/sms/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#smsForm").data("bootstrapValidator").validate();
			var isValid = $("#smsForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'smsForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.result == true) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/msend/sms/index.do";
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
					location.href= "${managerPath}/msend/sms/index.do";
				}
			}
		})
	}	
</script>

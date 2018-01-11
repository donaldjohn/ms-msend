<@ms.html5>
	<style>
		.select2-container .select2-container--default {  
		 	height: 34px;  
		} 
		.select2-container .select2-selection--single{
			font: inherit;
			border: 1px solid #ccc;
		    display: block;
		    height: 34px;
		    padding: 2px 3px;
    		font-size: 14px;
    		color: rgb(85, 85, 85);
		}
	</style> 
	 <@ms.nav title="短信编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="smsForm" isvalidation=true>
    		<@ms.hidden name="appId" value="${smsEntity.appId?default('')}"/>
    			<@ms.text label="短信接口类型" name="smsType" value="${smsEntity.smsType?default('')}"  width="240px;" placeholder="请输入短信接口类型" validation={"required":"true","maxlength":"20","data-bv-stringlength-message":"短信接口类型长度不能超过二十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="账号" name="smsUsername" value="${smsEntity.smsUsername?default('')}"  width="240px;" placeholder="请输入账号" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"账号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<#if smsEntity??>
    				<@ms.password name="smsPassword" label="密码"  title="" size="5" width="240"  validation={"data-bv-stringlength":"true","data-bv-stringlength-max":"20", "maxlength":"20", "data-bv-stringLength-min":"6" ,"data-bv-stringlength-message":"密码长度为6-20个字符","data-bv-regexp":"true","data-bv-regexp-regexp":'^[A-Za-z0-9_]+$',"data-bv-regexp-message":"密码只能由英文字母，数字，下划线组成!"}/>
    			<#else>
    				<@ms.password name="smsPassword" label="密码"  title="" size="5" width="240"  validation={"required":"false","data-bv-stringlength":"true","data-bv-stringlength-max":"20", "maxlength":"20", "data-bv-stringLength-min":"6" ,"data-bv-stringlength-message":"密码长度为6-20个字符","data-bv-regexp":"true","data-bv-regexp-regexp":'^[A-Za-z0-9_]+$',"data-bv-regexp-message":"密码只能由英文字母，数字，下划线组成!", "data-bv-notempty-message":"必填项目"}/>
    			</#if>
    			<!--
    				<@ms.text label="发送地址" name="smsSendUrl" value="${smsEntity.smsSendUrl?default('')}"  width="240px;" placeholder="请输入发送地址" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"发送地址长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    				<@ms.text label="短信平台后台管理地址" name="smsManagerUrl" value="${smsEntity.smsManagerUrl?default('')}"  width="240px;" placeholder="请输入短信平台后台管理地址" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"短信平台后台管理地址长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			-->
    			<@ms.text label="签名" name="smsSignature" value="${smsEntity.smsSignature?default('')}"  width="240px;" placeholder="请输入签名" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"签名长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.select 
    				id="smsEnable"
				    name="smsEnable" 
				    label="坐席类型" 
				    width="240"  
				    list=[{"id":0,"value":"启用"},{"id":1,"value":"禁用"}] 
				    value="${smsEntity.smsEnable?default('')}"
				    listKey="id" 
				    listValue="value"  
				    validation={"required":"true", "data-bv-notempty-message":"必选项目"}
				/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	$("#smsEnable").select2({width: "210px"});
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
				if(status.appId > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
				}
				$(".btn-success").text(btnWord);
				$(".btn-success").removeAttr("disabled");
			}
		})
	}	
</script>

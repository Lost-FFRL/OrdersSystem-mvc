$(function() {
	// 查询点击事件

	$("#e-submit").click(function() {
		// TODO 验证数据的有效性
		var name = $.trim($("#name").val());
		var account = $.trim($("#account").val());
		var pwd1 = $.trim($("#password").val());
		var pwd2 = $.trim($("#password1").val());
		var prompt = $("#prompt");
		if (null == name || "" == name) {
			prompt.text("请输入名字").show();
			return;
		} else if (null == account || "" == account){
		    prompt.text("请输入帐号").show();
            return;
		} else if(null == pwd1 || "" == pwd1){
		    prompt.text("请输入密码").show();
		    return;
		}  else if(null == pwd1 && pwd1 != pwd2){
		    prompt.text("密码不一致").show();
            return;
		}
		$("#e_add").submit();
	});
});
$(function() {
	// 查询点击事件

	$("#e-submit").click(function() {
		// TODO 验证数据的有效性
		var name = $("#name").val();
		var prompt = $("#prompt");
		if (null == name || "" == name) {
			prompt.text("请输入名字").show();
			return;
		}

		$("#e_add").submit();
	});
});
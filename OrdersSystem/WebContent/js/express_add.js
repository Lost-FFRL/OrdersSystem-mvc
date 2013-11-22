$(function() {

	$("#e-submit").click(function() {
		// TODO 验证数据的有效性
		var name = $("#name").val();
		var price = parseFloat($("#price").val());
		var prompt = $("#prompt");
		if (null == name || "" == name) {
			prompt.text("请输入名称").show();
			return;
		} else if (isNaN(price)) {
			prompt.text("请正确填写价格").show();
			return;
		}
		$("#e_add").submit();
	});
});
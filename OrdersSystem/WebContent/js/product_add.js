$(function() {
    // 查询点击事件

    $("#e-submit").click(function() {
        // TODO 验证数据的有效性
        var name = $.trim($("#name").val());
        var num = $.trim($("#number").val());
        var priceTex = $("#price").val();
        var price = parseFloat($("#price").val());
        var prompt = $("#prompt");
        if (null == name || "" == name) {
            prompt.text("请输入名字").show();
            return;
        } else if (null == num || "" == num) {
            prompt.text("请输入编号").show();
            return;
        } else if (null == priceTex || "" == priceTex || isNaN(price)) {
            prompt.text("请正确填写价格").show();
            return;
        }
        $("#e_add").submit();
    });
});
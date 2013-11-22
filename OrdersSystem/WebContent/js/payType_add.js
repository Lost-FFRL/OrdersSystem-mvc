$(function() {
    // 查询点击事件

    $("#e-submit").click(function() {
        // TODO 验证数据的有效性
        var name = $.trim($("#name").val());
        var num = $.trim($("#number").val());
        var prompt = $("#prompt");
        if (null == name || "" == name) {
            prompt.text("请输入名字").show();
            return;
        } else if (null == num || "" == num) {
            prompt.text("请输入编号").show();
            return;
        }
        $("#e_add").submit();
    });
});
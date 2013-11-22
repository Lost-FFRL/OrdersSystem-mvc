$(function() {

    // 分页查询点击事件
    $(".page").click(function() {
        var curPage = parseInt($("#curPage").val());
        if (this.id == "pre") {
            if (curPage > 1) {
                $("#curPage").val(curPage - 1);
                $("#p_form").submit();
            }
        } else {
            if (curPage < parseInt($("#totalPage").val())) {
                $("#curPage").val(curPage + 1);
                $("#p_form").submit();
            }
        }
    });

    // 查询点击事件
    $("#query").click(function() {
        $("#s_form").submit();
    });

    // 删除事件
    $(".d_delete").click(function() {
        if (confirm('确定要删除吗？')) {
            var self = $(this);
            var id = self.attr("flag");
            var tr = $("#tr-" + id);
            
            $.post("user.do?type=delById",{
                type: "delById",
                id: id
            },function(data){
                if (data && data.type == 0){
                 // 删除成功
                    tr.fadeOut("slow").remove();
                } else {
                    alert("删除失败：" + data.desc);
                }
            },"JSON");
        } else {
            return;
        }
    });

});

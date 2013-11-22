$(function() {

	// 获取快递
	$.post("express.do?type=getNames", function(data) {
		getExpCallbak(data);
	}, "JSON");

	// 获取支付方式
	$.post("payType.do?type=getNames", function(data) {
		getPayTypeCallbak(data);
	}, "JSON");

	// 获取商品
	$.post("product.do?type=getAll", function(data) {
		getProductCallbak(data);
	}, "JSON")

	$("#e-submit").click(function() {
		// TODO 验证数据的有效性
		var name = $.trim($("#name").val());
		var num = $.trim($("#ordersNum").val());
		var priceTex = $("#totalPrice").val();
		var price = parseFloat($("#totalPrice").val());
		var price = parseFloat($("#totalProduct").val());
		var prompt = $("#prompt");

		if (null == name || "" == name) {
			prompt.text("请输入名字").show();
			return;
		} else if (null == num || "" == num) {
			prompt.text("请输入编号").show();
			return;
		} else if (null == priceTex || "" == priceTex || isNaN(price)) {
			prompt.text("请正确填写价格：数字").show();
			return;
		} else if (null == priceTex || "" == priceTex || isNaN(price)) {
			prompt.text("请正确填写数量：数字").show();
			return;
		}

		console.info($("#sel-status option:selected").val());
		// 隐藏字段赋值
		var payType = $("#sel-payType option:selected").text();
		$("#payTypeName").val(payType);
		var express = $("#sel-express option:selected").text();
		$("#expressName").val(express);

		$("#e_add").submit();
	});

	// 添加商品点击事件
	$("#p-add")
			.click(
					function() {
						var sel = $("#sel_product :selected");
						var val = sel.val();
						var exist = ("0" == val ? true : false);
						$("#p-list tbody tr").each(function() {
							if ($(this).attr("id") == val) {
								exist = true;
								return false;
							}
						});
						if (exist) {
							return;
						} else {
							var html = "<tr id='"
									+ val
									+ "'><td>"
									+ "<input type='hidden' name='productId' value='"
									+ val
									+ "' />"
									+ "<input type='hidden' name='productName' value='"
									+ sel.attr("p-name")
									+ "' />"
									+ "<input type='hidden' name='productNum' value='"
									+ sel.attr("p-num")
									+ "' />"
									+ "<input type='hidden' name='productDesc' value='"
									+ sel.attr("p-desc")
									+ "' />"
									+ "<input type='hidden' name='productPrice' value='"
									+ sel.attr("p-price")
									+ "' />"
									+ sel.attr("p-name")
									+ "</td><td>"
									+ sel.attr("p-price")
									+ "</td><td><input name='productCount' class='p-num' type='text' /></td>"
									+ "<td><span class='p_del'>删除</span></td></tr>";
							$("#p-list tbody").append(html);
							$(".p_del").bind("click", function() {
								$(this).parent().parent().remove();
							});
							$("#p-list tr[id=" + val + "] input").focus();
						}
					});

	// TODO 计算商品总量，和价格
	$("#p-list input").focusout(function() {
		var price = 0;
		var num = 0;
		$("#p-list tbody tr").each(function() {
		});
	});

	// 获取快递回调函数
	function getExpCallbak(data) {
		if (data) {
			var html = "";
			for ( var obj in data) {
				if (data[obj]) {
					html += "<option value='" + data[obj].id + "'>"
							+ data[obj].name + "</option>";
				}
			}
			$("#sel-express").append(html);
		}
	}
	// 获取支付回调函数
	function getPayTypeCallbak(data) {
		if (data) {
			var html = "";
			for ( var obj in data) {
				if (data[obj]) {
					html += "<option value='" + data[obj].id + "'>"
							+ data[obj].name + "</option>";
				}
			}
			$("#sel-payType").append(html);
		}
	}

	// 获取产品回调函数
	function getProductCallbak(data) {
		if (data) {
			var html = "";
			for ( var obj in data) {
				if (data[obj]) {
					html += "<option value='" + data[obj].id + "' p-name='"
							+ data[obj].name + "' p-price='" + data[obj].price
							+ "' p-desc='" + data[obj].desc
							+ "' p-num='" + data[obj].number
							+ "' >" + data[obj].name + "</option>";
				}
			}
			$("#sel_product").append(html);
		}
	}

});
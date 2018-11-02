/**
 * 管理商品类别JS脚本
 */
$(function () {
	//后端从session中获取商铺信息，不依赖前端传入
    //var shopId = getQueryString("shopId");
    //var getPcByShopIdUrl = '/o2o/product/category/getpcbyshopid?shopId=' + shopId;
	
	var getPcByShopIdUrl = '/o2o/product/category/getpcbyshopid';
	var batchAddUrl = '/o2o/product/category/batchadd';
	var removePcUrl = '/o2o/product/category/remove';
	
	//加载商品类别列表
	getProductCategories();
	
	function getProductCategories(){
	    $.getJSON(getPcByShopIdUrl, function(result){
	    	//debugger;
	        if (result.success) {
	            var dataList = result.data;
	            var productCategoriesHtml = '';
	            dataList.map(function(item, index) {
	            	productCategoriesHtml += '<div class="row row-product-category now">'
	                                + '<div class="col-33 product-category-name info-item">'
	                                + item.productCategoryName
	                                + '</div><div class="col-25 info-item">'
	                                + item.priority
	                                + '</div><div class="col-20 info-item">'
	                                + '<a href="javascript:void(0)" class="button delete" data-id="'
	                                + item.productCategoryId
	                                + '">删除</a></div><div class="col-20 info-item">'
	                                + '<input type="checkbox" data-id="'
	                                + item.productCategoryId
	                                + '"/></div></div>';
	            });
	            $('.product-categroy-wrap').html(productCategoriesHtml);
	        }
	    });
	}
    
    //新增按钮的点击事件，每点一次新增一行表单数据
    $('#new').click(function(){
    	//新增数据以temp为标识，便于和库表中的数据区分开来
    	var tempHtml = '<div class="row row-product-category temp">'
    		+ '<div class="col-33 product-category-name info-item"><input class="category" type="text" placeholder="类别名称"/></div>'
    		+ '<div class="col-25 info-item"><input class="priority" type="number" placeholder="优先级"/></div>'
    		+ '<div class="col-40 info-item"><a href="javascript:void(0)" class="button delete">删除</a></div>'
    		+ '</div>';
    	$('.product-categroy-wrap').append(tempHtml);
    	return false; //阻止a标签跳转
    });

    //提交表单数据，实现批量新增商品类别
    $('#submit').click(function() {
        //通过temp获取新增的行
        var tempArr = $('.temp');
        //定义数组接收新增的数据
        var productCategories = [];
        tempArr.map(function(index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
            	productCategories.push(tempObj);
            }
        });
        $.ajax({
            url: batchAddUrl,
            type: 'POST',
            //后端通过@HttpRequestBody直接接收
            data: JSON.stringify(productCategories),
            contentType: 'application/json',
            success: function(data) {
                if (data.success) {
                	//debugger;
                    $.toast('新增（' + data.count + '）条成功！');
                    //重新加载数据
                    getProductCategories();
                } else {
                    $.toast(data.errMsg);
                }
            }
        });
        return false; //阻止a标签跳转
    });
    
    //删除商品类别点击事件
    //一种是需要提交到后台的删除.now，另外一种是 新增但未提交到数据库中的删除 temp
    //删除.now
    $('.product-categroy-wrap').on('click', '.now .delete', function(e) {
    	var target = e.currentTarget;
    	$.confirm('确定删除?', function() {
    		$.ajax({
    			url: removePcUrl,
    			type: 'POST',
    			data: {productCategoryId: target.dataset.id},
    			dataType: 'json',
    			success: function(data) {
    				if (data.success) {
    					$.toast('删除成功');
    					//重新加载数据
    					getProductCategories();
    				} else {
    					$.toast(data.errMsg + '，删除失败');
    				}
    			}
    		});
    	});
    });
    //删除.temp
    $('.product-categroy-wrap').on('click', '.temp .delete', function(e) {
    	$(this).parent().parent().remove();
    });
});
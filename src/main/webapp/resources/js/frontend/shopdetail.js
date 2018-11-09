/**
 * 商铺详情JS脚本，可参考商铺列表
 */
$(function() {
	//debugger;
    var loading = false;
    
    var maxItems = 20;
    var pageSize = 10;
    var pageIndex = 1;

    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';

    var initShopInfoUrl = '/o2o/frontend/initshopinfo?shopId=' + shopId;
    var listProductsUrl = '/o2o/frontend/listproducts';

    initShopInfo();
    listProducts(pageSize, pageIndex);

    function initShopInfo() {
        $.getJSON(initShopInfoUrl, function(data) {
        	//debugger;
        	if (data.success) {
        		//加载商铺信息
        		var shop = data.shop;
        		$('#shop-name').text(shop.shopName);
        		$('#shop-cover-pic').attr('src', shop.shopImg);
        		$('#shop-update-time').text(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
        		$('#shop-desc').text(shop.shopDesc);
        		$('#shop-addr').text(shop.shopAddr);
        		$('#shop-phone').text(shop.phone);
        		//加载商品类别信息
        		var productCategoryList = data.productCategoryList;
        		var productCategoryHtml = '';
        		productCategoryList.map(function(item, index) {
        			productCategoryHtml += '<a href="#" class="button" data-product-search-id='
        					+ item.productCategoryId
        					+ '>'
        					+ item.productCategoryName
        					+ '</a>';
        		});
        		$('#product-category-search').html(productCategoryHtml);
        	}
        });
    }

    function listProducts(pageSize, pageIndex) {
    	loading = true;
    	$.ajax({
    		url: listProductsUrl,
    		type: 'POST',
    		data: {
    			'pageIndex': pageIndex,
    			'pageSize': pageSize,
    			'productName': productName,
    			'shopId': shopId,
    			'productCategoryId': productCategoryId
    		},
    		dataType: 'json',
    		success: function(data){
    			//debugger;
    			if (data.success) {
                    maxItems = data.count;
                    var productListHtml = '';
                    data.productList.map(function(item, index) {
                    	productListHtml += '<div class="card" data-product-id='
                                + item.productId 
                                + '><div class="card-header">'
                                + item.productName
                                + '</div><div class="card-content">'
                                + '<div class="list-block media-list"><ul>'
                                + '<li class="item-content">'
                                + '<div class="item-media"><img src="'
                                + item.imgAddr
                                + '" width="44"></div>'
                                + '<div class="item-inner"><div class="item-subtitle">'
                                + item.productDesc
                                + '</div></div></li></ul></div></div>'
                                + '<div class="card-footer"><p class="color-gray">'
                                + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                                + '&nbsp;更新</p><span>点击查看</span>'
                                + '</div></div>';
                    });
                    $('.list-div').append(productListHtml);
                    var total = $('.list-div .card').length;
                    if (total >= maxItems) {
                        //隐藏加载提示符
                        $('.infinite-scroll-preloader').hide();
                    }else{
                        $('.infinite-scroll-preloader').show();
                    }
                    pageIndex += 1;
                    loading = false;
                    $.refreshScroller();
                }
    		}
    	});
    }

    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading){
        	return;
        }
        listProducts(pageSize, pageIndex);
    });

    $('#product-category-search').on('click', '.button', function(e) {
    	productCategoryId = e.target.dataset.productSearchId;
    	if (productCategoryId) {
    		if ($(e.target).hasClass('button-fill')) {
    			$(e.target).removeClass('button-fill');
    			productCategoryId = '';
    		} else {
    			$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
    		}
    		$('.list-div').empty();
    		pageIndex = 1;
    		listProducts(pageSize, pageIndex);
    	}
    });

    $('.list-div').on('click', '.card', function(e) {
    	var productId = e.currentTarget.dataset.productId;
    	window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
    });

    $('#search').on('change', function(e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageIndex = 1;
        listProducts(pageSize, pageIndex);
    });

    $('#me').click(function() {
        $.openPanel('#panel-left-demo');
    });
    
    //$.init();
});
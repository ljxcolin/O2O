/**
 * 前端商铺列表JS脚本
 */
$(function() {
	//debugger;
    var loading = false;
    
    //分页允许返回的最大条数，超过此数值，禁止访问后台
    var maxItems = 999;
    var pageSize = 2; //一页返回的最大条数
    var pageIndex = 1; //页码，第一页
    
    var parentId = getQueryString('parentId');
    var shopName = '';
    var shopCategoryId = '';
    var areaId = '';

    //用于加载数据的Url
    var initSearchCndUrl = '/o2o/frontend/initsearchcnd?parentId=' + parentId;
    var listShopsUrl = '/o2o/frontend/listshops';
    
    //加载所有一级商铺以及区域数据，以用于条件筛选
    initSearchCnd();
    
    //预先加载pageSize*pageIndex条
    listShops(pageSize, pageIndex);

    function initSearchCnd() {
        $.getJSON(initSearchCndUrl, function(data) {
        	if (data.success) {
        		//debugger;
        		//处理商铺类别
        		var shopCategoryList = data.shopCategoryList;
        		var shopCategoryHtml = '<a href="#" class="button" data-category-id="">全部类别 </a>';
        		shopCategoryList.map(function(item, index) {
        			shopCategoryHtml += '<a href="#" class="button" data-category-id='
        						+ item.shopCategoryId
        						+ '>'
        						+ item.shopCategoryName
        						+ '</a>';
        		});
        		$('#shop-category-search').html(shopCategoryHtml);
        		//处理商铺所属区域
        		var areaOptions = '<option value="">全部区域</option>';
        		var areaList = data.areaList;
        		areaList.map(function(item, index) {
        			areaOptions += '<option value="'
        				+ item.areaId + '">'
        				+ item.areaName + '</option>';
        		});
        		$('#area-search').html(areaOptions);
        	}
        });
    }

    function listShops(pageSize, pageIndex) {
    	loading = true;
    	//加载符合条件的商铺列表
    	$.ajax({
    		url: listShopsUrl,
    		type: 'POST',
    		data: {
    			'pageIndex': pageIndex,
    			'pageSize': pageSize,
    			'shopName': shopName,
    			'parentId': parentId,
    			'shopCategoryId': shopCategoryId,
    			'areaId': areaId
    		},
    		dataType: 'json',
    		success: function(data){
    			//debugger;
    			if (data.success) {
                    maxItems = data.count; //最大显示卡片个数
                    var shopListHtml = '';
                    data.shopList.map(function(item, index) {
                    	shopListHtml += '<div class="card" data-shop-id="'
                                + item.shopId
                                + '"><div class="card-header">'
                                + item.shopName
                                + '</div>'
                                + '<div class="card-content">'
                                + '<div class="list-block media-list"><ul>'
                                + '<li class="item-content">'
                                + '<div class="item-media"><img src="'
                                + item.shopImg
                                + '" width="44"/></div>'
                                + '<div class="item-inner">'
                                + '<div class="item-subtitle">'
                                + item.shopDesc
                                + '</div></div></li></ul>'
                                + '</div></div><div class="card-footer">'
                                + '<p class="color-gray">'
                                + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                                + '&nbsp;更新</p><span>点击查看</span>' 
                                + '</div></div>';
                    });
                    $('.list-div').append(shopListHtml);
                    var total = $('.list-div .card').length;
                    if (total >= maxItems) {
                        //异常加载提示符
                        $('.infinite-scroll-preloader').hide();
                    }else{
                        $('.infinite-scroll-preloader').show();
                    }
                    pageIndex += 1;
                    loading = false;
                    //刷新页面，显示新加载的商铺（下一页）
                    $.refreshScroller();
                }else{
                	loading = false;
                	$.toast(data.errMsg);
                }
    		}
    	});
    }

    //下滑屏幕，使用无极模式自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading){
        	return;
        }
        listShops(pageSize, pageIndex);
    });
    
    //点击商铺卡片，进入商铺详情
    $('.shop-list').on('click', '.card', function(e) {
    	debugger;
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
    });
    
    //根据搜索框的输入的商铺名称来搜索
    $('#search').on('change', function(e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageIndex = 1;
        listShops(pageSize, pageIndex);
    });
    
    //根据商铺类别搜索
    $('#shop-category-search').on('click', '.button', function(e) {
    	if (parentId) { //如果传递过来的是一个父类下的子类
    		shopCategoryId = e.target.dataset.categoryId;
    		if ($(e.target).hasClass('button-fill')) {
    			$(e.target).removeClass('button-fill');
    			shopCategoryId = '';
    		} else {
    			//将用户点击的按钮填充，旁边兄弟按钮变为不填充
    			$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
    		}
    		$('.list-div').empty(); //清空原先的商铺列表
    		pageIndex = 1;
    		listShops(pageSize, pageIndex);
    	} else { //如果传递过来的父类为空，则按照父类查询
    		parentId = e.target.dataset.categoryId;
    		if ($(e.target).hasClass('button-fill')) {
    			$(e.target).removeClass('button-fill');
    			parentId = '';
    		} else {
    			$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
    		}
    		$('.list-div').empty();
    		pageIndex = 1;
    		listShops(pageSize, pageIndex);
    		parentId = '';
    	}
    });
  
    //根据商铺所属区域来搜索
    $('#area-search').on('change', function() {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageIndex = 1;
        listShops(pageSize, pageIndex);
    });

    //我的
    $('#me').click(function() {
        $.openPanel('#panel-left-demo');
    });

    //$.init();
});
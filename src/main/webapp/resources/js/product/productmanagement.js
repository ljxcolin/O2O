/**
 * 商品管理JS脚本
 */
$(function() {
    var getProductListUrl = '/o2o/product/list?pageIndex=1';
    var changeStatusUrl = '/o2o/product/changestatus';

    getProductList();

    function getProductList() {
        $.getJSON(getProductListUrl, function(data) {
        	//debugger;
            if (data.success) {
                var productList = data.productList;
                var productListHtml = '';
                productList.map(function(item, index) {
                    var issue = '';
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                    	issue = '上架';
                        contraryStatus = 1;
                    } else {
                    	issue = '下架';
                        contraryStatus = 0;
                    }
                    productListHtml += '<div class="row row-product">'
                            + '<div class="col-30 info-item">'
                            + item.productName
                            + '</div><div class="col-25 info-item">'
                            + item.priority
                            + '</div><div class="col-45 info-item">'
                            + '<a href="" class="edit" data-id="'
                            + item.productId
                            + '" data-status="'
                            + item.enableStatus
                            + '">编辑</a>'
                            + '<a href="" class="status" data-id="'
                            + item.productId
                            + '" data-status="'
                            + contraryStatus
                            + '">'
                            + issue
                            + '</a><a href="" class="preview" data-id="'
                            + item.productId
                            + '" data-status="'
                            + item.enableStatus
                            + '">预览</a></div></div>';
                });
                $('.product-wrap').html(productListHtml);
            }
        });
    }

    /**
     * 上架或下架操作
     */
    function changeStatus(target) {
    	//debugger;
        var product = {};
        product.productId = target.dataset.id;
        product.enableStatus = target.dataset.status;
        var issue = '下架';
        var contraryStatus = 1;
        var confirmStr = '确定要';
        if(product.enableStatus == 1){
        	issue = '上架';
        	contraryStatus = 0;
        }
        confirmStr += issue + '该商品吗';
        $.confirm(confirmStr, function() {
            $.ajax({
                url: changeStatusUrl,
                type: 'POST',
                data: {productStr: JSON.stringify(product)},
                dataType: 'json',
                success: function(data) {
                	//debugger;
                    if (data.success) {
                        //getProductList();
                        target.dataset.status = contraryStatus;
                        $(target).text(contraryStatus == 1 ? '上架' : '下架');
                        $.toast(data.sucMsg);
                    } else {
                        $.toast(data.errMsg);
                    }
                }
            });
        });
    }

    $('.product-wrap').on('click', 'a', function(e) {
    	//debugger;
    	var target = e.currentTarget;
    	var $a = $(target);
    	if ($a.hasClass('edit')) {
    		window.location.href = '/o2o/productadmin/operation?productId=' + target.dataset.id;
    		//window.event.returnValue = false;
    	}else if ($a.hasClass('status')) {
    		changeStatus(target);
    	}else if ($a.hasClass('preview')) {
    		//window.location.href = '/o2o/frontend/productdetail?productId=' + e.currentTarget.dataset.id;
    	}
    	return false;
    });
});
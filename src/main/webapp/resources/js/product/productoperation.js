/**
 * 商品操作JS脚本
 * 添加商品和编辑商品复用同一个页面，需要根据url中的商品Id来判断
 */
$(function(){
	//debugger;
    //获取url中的productId
    var productId = getQueryString('productId');
    //标示符，判断productId是否为空，为空则为添加，非空则为编辑
    var isEdit = productId ? true : false ;

    //添加商品Url
    var addProductUrl = '/o2o/product/add';
    //编辑商品Url
    var editProductUrl = '/o2o/product/edit';
    //初始化添加商品的Url，只需要获取productCategory即可
    var initAddProductUrl = '/o2o/product/category/getpcbyshopid';
    //初始化编辑商品的Url，获取该ID的商品及所有商品类别
    var initEditProductUrl = '/o2o/product/getproductinfobyid?productId=' + productId;
    
    //通过标示符，确定调用的方法
    if(!isEdit){ //添加商品，初始化新增商品页面
    	$("#product-operation").text("添加商品");
    	initAddProduct();
    }else{
    	$("#product-operation").text("编辑商品");
    	initEditProduct();
    }

    /**
     * 初始化新增商品页面
     * 根据页面原型和数据模型，需要加载该商铺对应的商品类别信息（商铺信息从服务端session中获取）
     */
    function initAddProduct(){
        $.getJSON(initAddProductUrl, function(result){
        	if(result.success){
        		//设置product_category
        		var productCategoryList = result.data;
        		var productCategoryTempHtml = '';
        		productCategoryList.map(function(item, index) {
        			productCategoryTempHtml += '<option data-value="'
        				+ item.productCategoryId + '">'
        				+ item.productCategoryName + '</option>';
        		});
        		$('#product-category').html(productCategoryTempHtml);
        	}else{
        		$.toast(result.errMsg)
        	}
        });
    };
    
    /**
     * 初始化编辑商品的页面
     */
    function initEditProduct(){
    	$.getJSON(initEditProductUrl, function(data){
    		if (data.success) {
    			//debugger;
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(product.promotionPrice);

                var pcOptionHtml = '';
                var productCategories = data.productCategories;
                var currentPc = product.productCategory.productCategoryId;
                productCategories.map(function(item, index) {
                	var isSelect = currentPc === item.productCategoryId ? ' selected' : '';
                	pcOptionHtml += '<option data-value="'
                		+ item.productCategoryId
                		+ '"' + isSelect + '>'
                		+ item.productCategoryName
                		+ '</option>';
                });
                $('#product-category').html(pcOptionHtml);
                $('#delete-tip').css('display', 'block');
            }
        });
    }

    /**
     * 点击文件控件的最后一个且图片数量小于6个的时候，生成一个选择框
     */
    $('#product-detail-img').on('change', '.detail-img:last-child', function() {
        if ($('.detail-img').length < 6) {
            $('#detail-imgs').append('<input type="file" class="detail-img">');
        }
    });

    /**
     * 提交按钮的响应时间，分别对商品添加和商品编辑做不同的相应
     */
    $('#submit').click(function(){
    	debugger;
    	//获取表单中的验证码
    	var verifyCodeActual = $('#j_kaptcha').val();
    	if (!verifyCodeActual) {
    		$.toast('请输入验证码');
    		return;
    	}
    	
    	//创建商品Json对象，并从表单对象中获取对应的属性值
    	var product = {};
    	//如果是编辑操作，需要传入productId
    	if(isEdit){
    		product.productId = productId;
    	}

    	product.productName = $('#product-name').val();
    	product.productDesc = $('#product-desc').val();

    	//获取商品类别
    	product.productCategory = {
    			productCategoryId: $('#product-category').find('option').not(function() {
    				return !this.selected;
    			}).data('value')
    	};

    	product.priority = $('#priority').val();
    	product.normalPrice = $('#normal-price').val();
    	product.promotionPrice = $('#promotion-price').val();

    	//生成表单对象用于接收参数并传递给后台
    	var formData = new FormData();
    	formData.append("verifyCodeActual", verifyCodeActual);
    	//将product转换为json，添加到formData
    	formData.append('productStr', JSON.stringify(product));
    	
    	//商品图片，缩略图（只有一张），获取缩略图的文件流
    	var productImg = $('#product-img')[0].files[0];
    	formData.append('productImg', productImg);
    	//商品详情图片
    	$('.detail-img').map(function(index, item) {
    		//判断该控件是否已经选择了文件   
    		if ($('.detail-img')[index].files.length > 0) {
    			//将第i个文件流赋值给key为detailImgi的表单键值对里
    			formData.append('detailImg' + index, $('.detail-img')[index].files[0]);
    		}
    	});

    	//使用ajax异步提交
    	$.ajax({
    		url: isEdit ? editProductUrl : addProductUrl,
    		type: 'POST',
    		data: formData,
    		contentType: false,
    		processData: false,
    		cache: false,
    		success: function(data){
    			//debugger;
    			if (data.success) {
    				$.toast(data.sucMsg);
    				window.location.replace("/o2o/productadmin/management");
    			} else {
    				$.toast(data.errMsg);
    				$('#kaptcha_img').click();
    			}
    		}
    	});
    	return false;
    });
});
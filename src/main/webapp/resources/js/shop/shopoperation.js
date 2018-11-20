/**
 * 注册与修改商铺JS脚本
 */
$(function() {
	//debugger;
    //获取商铺分类与所属区域信息的URL，用于初始化注册页面
    var initUrl = '/o2o/shop/getshopinitinfo';
    //注册商铺的URL
    var registerShopUrl = '/o2o/shop/register';
    
    //从请求URI中获取商铺ID
    var shopId = getQueryString("shopId"); 
    
    //获取指定ID的商铺信息的URL，用于初始化修改页面
    var getShopInfoByIdUrl = '/o2o/shop/getshopinfobyid?shopId=' + shopId;
    //修改商铺的URL
    var modifyShopUrl = '/o2o/shop/modify';
    
    //判断请求是注册还是修改
    var isEdit = shopId ? true : false; //shopId为空返回false，非空返回true
    
    //调用函数，加载数据
    if(!isEdit){
    	$("#shop-operation").text("注册商铺");
    	getShopInitInfo(); //注册商铺
    }else{
    	$("#shop-operation").text("修改商铺");
    	getShopInfoById(shopId); //修改商铺
    }

    //验证表单输入，此处省略。
    /**
     * 从后台加载ShopCategory与Area的信息，并遍历到下拉列表中
     * 约定JSON<-->Map的key分别为shopCategoryList和areaList
     */
    function getShopInitInfo() {
        $.getJSON(initUrl, function(data) {
        	//debugger;
            if (data.success) {
                var shopCategoryHtml = '';
                var shopAreaHtml = '';
                data.shopCategoryList.map(function(item, index) {
                	shopCategoryHtml += '<option data-id="' + item.shopCategoryId
                	+ '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function(item, index) {
                	shopAreaHtml += '<option data-id="' + item.areaId
                            + '">' + item.areaName + '</option>';
                });
                // 获取html中对应标签的id并插入html片段
                $('#shop-category').html(shopCategoryHtml);
                $('#shop-area').html(shopAreaHtml);
            }else{
                $.toast(data.errMsg);
            }
        });
    };
    
    /**
     * 从后台加载Shop与Area的信息，并将Shop信息回显到修改页面，将Area信息遍历到下拉列表中
     * 约定JSON<-->Map的key分别为shop和areaList
     */
    function getShopInfoById(shopId){
    	$.getJSON(getShopInfoByIdUrl, function(data){
    		//debugger;
    		if(data.success){
    			var shop = data.shop;
    			$('#shop-name').val(shop.shopName);
    			$('#shop-name').prop("disabled", true); //商铺名称设为不可修改
    			$('#shop-name').css("pointer-events", "none");
    			$('#shop-addr').val(shop.shopAddr);
    	        $('#shop-phone').val(shop.phone);
    	        $('#old-shop-img').val(shop.shopImg); //设置隐藏的旧的商铺图片
    	        $('#shop-desc').val(shop.shopDesc);
    	        //设置商铺类别，且不可修改
    	        var shopCategoryHtml = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '">'
                    + shop.shopCategory.shopCategoryName + '</option>';
    	        $('#shop-category').html(shopCategoryHtml);
    	        $('#shop-category').prop("disabled", true);
    	        $('#shop-category').css("pointer-events", "none");
    	        //设置所属区域，可修改
                var shopAreaHtml = '';
                data.areaList.map(function(item, index) {
                	shopAreaHtml += '<option data-id="' + item.areaId
                            + '">' + item.areaName + '</option>';
                });
                $('#shop-area').html(shopAreaHtml);
                //设置该商铺原所属区域为选中状态
                $('#shop-area option[data-id="' + shop.area.areaId + '"]').prop("selected", true);
                $('#shop-name-div').click(function(){
                	$.toast("商铺名称不可修改");
                });
                $('#shop-category-div').click(function(){
                	$.toast("商铺类别不可修改");
                });
    		}else{
    			$.toast(data.errMsg);
    		}
    	});
    }

    /**
     * submit按钮触发的操作
     */
    $('#submit').click(function() {
    	//debugger;
        //获取页面表单的值
        var shop = {};
        /*注意： shop对象的属性要和Shop实体类中的属性保持一致，因为后台接收到shopStr后，会将Json转换为实体类。
		如果不一致会抛出异常com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException*/
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        //选择id，双重否定=肯定
        shop.shopCategory = {
            //这里定义的变量要和ShopCategory.shopCategoryId保持一致，否则使用databind转换会抛出异常
            shopCategoryId: $('#shop-category').find('option').not(function(){
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            //这里定义的变量要和Area.areaId属性名称保持一致，否则使用databind转换会抛出异常
            areaId:$('#shop-area').find('option').not(function(){
                return !this.selected;
            }).data('id')
        };

        //商铺图片
        var shopImg = $('#shop-img')[0].files[0];
        
        //验证码
        var verifyCodeActual = $('#j_kaptcha').val();
        console.log('verifyCodeActual: ' + verifyCodeActual);
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }

        //封装表单数据
        var formData = new FormData();
        //和后端约定好，利用shopImg和shopStr接收shop图片信息和shop信息
        formData.append('shopImg', shopImg);
        
        //如果是编辑，需要传入shopId和oldShopImg
        if(isEdit){
        	shop.shopId = shopId;
        	var oldShopImg = $('#old-shop-img').val();
        	formData.append('oldShopImg', oldShopImg);
        }
        
        //转成JSON格式，后端收到后将JSON转为实体类
        formData.append('shopStr', JSON.stringify(shop));
        //将验证码也封装到formData发送到后台
        formData.append('verifyCodeActual', verifyCodeActual);

        //利用ajax提交
        $.ajax({
            url: isEdit ? modifyShopUrl : registerShopUrl, //动态Url
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function(data){
                if(data.success){
                    $.toast(data.sucMsg);
                }else{
                    $.toast(data.errMsg);
                }
                //点击提交后不管成功失败都更换验证码，防止重复提交
                $('#kaptcha_img').click();
            }
        });
    });
});

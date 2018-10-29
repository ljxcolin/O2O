/**
 * 管理商铺JS脚本
 */
$(function(){
    //获取shopId
    var shopId = getQueryString("shopId");
    
    //商铺管理的url
    var manageShopUrl = '/o2o/shop/manage?shopId=' + shopId;

    $.getJSON(manageShopUrl, function (data) {
    	//debugger;
        //若后端返回redirect=true，则跳转后端到设置的url，回到列表
        if(data.redirect){
        	$.toast(data.errMsg);
            window.location.href = data.url;
        }else{
            //如果后台返回redirect=false，则设置shopId并给按钮设置超链接属性（即商铺信息-->编辑商铺）
            if (data.shopId != undefined && data.shopId != null){
                shopId = data.shopId;
            }
            $('#shopInfo').attr('href','/o2o/shopadmin/operation?shopId=' + shopId);
        }
    });
});
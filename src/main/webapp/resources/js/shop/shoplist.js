/**
 * 商铺列表JS脚本
 */
$(function(){
	//debugger;
    // 调用，加载数据
    getShopList();

    function getShopList(){
        $.ajax({
            url: "/o2o/shop/list",
            type: "get",
            dataType: "json",
            success: function(data){
            	//debugger;
                if (data.success) {
                	handleUser(data.user);
                    handleList(data.shopList);
                }
            }
        });
    }

    function handleUser(user){
        $('#user-name').text(user.name);
    }

    function handleList(shopList){
        var shopListHtml = '';
        shopList.map(function(item, index){
            shopListHtml += '<div class="row row-shop"><div class="info-item col-40">'
                + item.shopName + '</div><div class="info-item col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="info-item col-20">'
                + toShop(item.enableStatus, item.shopId)
                + '</div></div>'
        });
        $('.shop-wrap').html(shopListHtml);
    }

    function shopStatus(status){
        if (status == 0 ) {
            return '审核中';
        } else if (status == 1) {
            return '审核通过';
        } else{
            return '商铺非法';
        }
    }

    //进入商铺的管理页面，若商铺审核通过则可进入管理，商铺非法不可以操作。进入请求Url为/o2o/shop/manage
    function toShop(status, shopId){
        if (status == 1 ) {
            return '<a href="/o2o/shopadmin/management?shopId=' + shopId + '">进入</a>';
        }else{
            return '<a href="javascript:void(0)">取消</a>';
        }
    }
});

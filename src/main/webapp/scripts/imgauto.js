
/*
* 图片等比缩放
* @by ambar
* @create 2010-11-17
* @update 2010-11-17
*/
$.fn.imgAutoResizer = function (options) {
return this.each(function () {
var opt = $.extend({
sizeAttr : 'data-img-size'
,srcAttr : 'data-img-url'
,error : null
,loading : null
}, options || {});
var $el = $(this), src = $el.attr(opt.srcAttr), size = $el.attr(opt.sizeAttr).split(',');
// 容器宽高
var dw = size[0], dh = size[1];
var $img = $('<img />', { src : src }), img = $img[0];
var autoresize = function () {
if($el.data('img.complete')) return;
// 图片宽高
var iw = img.width, ih = img.height;
if(!iw || !ih) return;
// 比例
var dr = dw/dh, ir = iw/ih;
if( !(dw > iw && dh > ih) ){
if(dr>ir){
ih = dh; iw = ih * ir;
}else{
iw = dw; ih = iw / ir;
}
}
// console.log(dr,':',iw,'@',ih);
$el.data('img.complete',true).css({position:'relative',width:dw,height:dh,overflow:'hidden'});
$img.css({width:iw,height:ih,position:'absolute',top:'50%',left:'50%',marginLeft:-iw/2,marginTop:-ih/2}).appendTo($el.empty());
};
$img
.load(autoresize)
.error(function () {
if($.isFunction(opt.error)) opt.error.call($el);
});
if(img.complete){
if(img.width && img.height) autoresize();
}else{
if($.isFunction(opt.loading)) opt.loading.call($el);
}
})
}; 
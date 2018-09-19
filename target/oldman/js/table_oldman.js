/**
 * Created by netlab606 on 2017/5/20.
 */
function formatActionRoom() {
    return '<input type="button" class="jump" onclick="rooms(event)" value="房间"/> ';
}


function formatActionEquip() {
    return '<input type="button" class="jump" onclick="equips(event)" value="设备"/> ';
}

function rooms(event) {
    var e=event||window.event;
    var oid= e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.innerText;
    var url=pathJs+"/room/getOldManRooms?oid="+oid+"&type=front";
    $("#carousel-example-generic",parent.document).hide();
    var iframe=$('<iframe src="'+url+'" width="100%" style="overflow: hidden"></iframe>');
    var navHeight=$("nav",parent.document).height();
    var totalHeight=$(parent.document.body).height();
    var iframeHeight=totalHeight-navHeight;
    iframe.attr("height",iframeHeight);
    var $div=$("#toIframe",parent.document);
    $div.html("");
    $div.append(iframe);

}

function equips(event) {
    var e=event||window.event;
    var oid= e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.innerText;
    var url=pathJs+"/equip/getOldManEquips?oid="+oid+"&type=front";
    $("#carousel-example-generic",parent.document).hide();
    var iframe=$('<iframe src="'+url+'" width="100%" style="overflow: hidden"></iframe>');
    var navHeight=$("nav",parent.document).height();
    var totalHeight=$(parent.document.body).height();
    var iframeHeight=totalHeight-navHeight;
    iframe.attr("height",iframeHeight);
    var $div=$("#toIframe",parent.document);
    $div.html("");
    $div.append(iframe);
}
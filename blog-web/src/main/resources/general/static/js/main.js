$.ajaxSetup({
    // 关闭ajax缓存
    cache : false
});

$(".header .nav-side-btn").click(function (e) {
    $("body").toggleClass("active-side")
    e.stopPropagation();
})

$(".nav-side").click(function (e) {
    e.stopPropagation();
})

$(document).click(function (e) {
    $("body").removeClass("active-side")
    e.stopPropagation();
})

// 侧边栏父元素
var sidebar = $(".main .sidebar");
// 侧边栏
var sidebarContent = $(".main .sidebar-content");
// 侧边栏触发fixed高度（距离窗口顶端高度）
var slideBarOffset = 20;

$(window).on('scroll resize', function () {
    boxFix(sidebarContent, sidebar, slideBarOffset);
    if ($(document).scrollTop() > 200) {
        $(".back-to-top").show();
    } else {
        $(".back-to-top").hide();
    }
});

$(document).ready(function () {
    // 侧边栏浮动
    boxFix(sidebarContent, sidebar, slideBarOffset);
    if ($(document).scrollTop() > 200) {
        $(".back-to-top").show();
    } else {
        $(".back-to-top").hide();
    }
})
$(".back-to-top").click(function () {
    $("html,body").animate({scrollTop: 0}, 500);
})

function boxFix(parentbox, box, topOffset) {
    var sidebarTop = box[0].getBoundingClientRect().top; // 定制总价容器顶部距离窗口的高度
    // console.log(sidebarTop)
    parentbox.css("width", box.width());
    if (sidebarTop <= topOffset) {
        parentbox.addClass("fixed")
        parentbox.css("padding-top", topOffset);
    } else {
        parentbox.removeClass("fixed")
        parentbox.css("padding-top", 0);
    }
}

$(".search-input").keydown(function (event) {
    if (event.keyCode == 13) {
        $(".search-btn").click();
    }
});

$(".search-btn").click(function (event) {
    var container = $(this).parents('.header');
    if (!container.hasClass('active-search')) {
        container.addClass('active-search');
    } else if (container.hasClass('active-search')) {
        var keywords = container.find('.search-input').val();
        if (keywords == '') {
            container.find('.search-input').addClass("shake")
            setTimeout(function () {
                container.find('.search-input').removeClass("shake")
            }, 200)
            container.find('.search-input').focus();
        } else {
            // container.removeClass('active');
            location.href = "/search/" + keywords;
        }
    }
    // event.preventDefault();
})

$(".search-close").click(function (event) {
    var container = $(this).parents('.header');
    if (container.hasClass('active-search')) {
        container.removeClass('active-search');
        var keywords = container.find('.search-input').val('');
    }
    // event.preventDefault();
})

function formatTime(time) {
    var date =
        time[0] +
        '-' +
        (Array(2).join(0) + time[1]).slice(-2) +
        '-' +
        (Array(2).join(0) + time[2]).slice(-2) +
        ' ' +
        (Array(2).join(0) + time[3]).slice(-2) +
        ':' +
        (Array(2).join(0) + time[4]).slice(-2) +
        ':' +
        (Array(2).join(0) + (time[5] === undefined ? 0 : time[5])).slice(-2)
    return date;
}

Waves.init();


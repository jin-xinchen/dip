function getURLVar(key) {
	var value = [];

	var query = String(document.location).split('?');

	if (query[1]) {
		var part = query[1].split('&');

		for (i = 0; i < part.length; i++) {
			var data = part[i].split('=');

			if (data[0] && data[1]) {
				value[data[0]] = data[1];
			}
		}

		if (value[key]) {
			return value[key];
		} else {
			return '';
		}
	}
}

$(document).ready(function() {
	// Highlight any found errors
	$('.text-danger').each(function() {
		var element = $(this).parent().parent();

		if (element.hasClass('form-group')) {
			element.addClass('has-error');
		}
	});

	// Currency
	$('#form-currency .currency-select').on('click', function(e) {
		e.preventDefault();

		$('#form-currency input[name=\'code\']').val($(this).attr('name'));

		$('#form-currency').submit();
	});

	// Language
	$('#form-language .language-select').on('click', function(e) {
		e.preventDefault();

		$('#form-language input[name=\'code\']').val($(this).attr('name'));

		$('#form-language').submit();
	});

	/* Search */
	$('#search input[name=\'search\']').parent().find('button').on('click', function() {
		var url = $('base').attr('href') + 'index.php?route=product/search';

		var value = $('header #search input[name=\'search\']').val();

		if (value) {
			url += '&search=' + encodeURIComponent(value);
		}

		location = url;
	});

	$('#search input[name=\'search\']').on('keydown', function(e) {
		if (e.keyCode == 13) {
			$('header #search input[name=\'search\']').parent().find('button').trigger('click');
		}
	});

	// Menu
	$('#menu .dropdown-menu').each(function() {
		var menu = $('#menu').offset();
		var dropdown = $(this).parent().offset();

		var i = (dropdown.left + $(this).outerWidth()) - (menu.left + $('#menu').outerWidth());

		if (i > 0) {
			$(this).css('margin-left', '-' + (i + 10) + 'px');
		}
	});

	// Product List
	$('#list-view').click(function() {
		$('#content .product-grid > .clearfix').remove();

		$('#content .row > .product-grid').attr('class', 'product-layout product-list col-xs-12');
		$('#grid-view').removeClass('active');
		$('#list-view').addClass('active');

		localStorage.setItem('display', 'list');
	});

	// Product Grid
	$('#grid-view').click(function() {
		// What a shame bootstrap does not take into account dynamically loaded columns
		var cols = $('#column-right, #column-left').length;

		if (cols == 2) {
			$('#content .product-list').attr('class', 'product-layout product-grid col-lg-6 col-md-6 col-sm-12 col-xs-12');
		} else if (cols == 1) {
			$('#content .product-list').attr('class', 'product-layout product-grid col-lg-4 col-md-4 col-sm-6 col-xs-12');
		} else {
			$('#content .product-list').attr('class', 'product-layout product-grid col-lg-3 col-md-3 col-sm-6 col-xs-12');
		}

		$('#list-view').removeClass('active');
		$('#grid-view').addClass('active');

		localStorage.setItem('display', 'grid');
	});

	if (localStorage.getItem('display') == 'list') {
		$('#list-view').trigger('click');
		$('#list-view').addClass('active');
	} else {
		$('#grid-view').trigger('click');
		$('#grid-view').addClass('active');
	}

	// Checkout
	$(document).on('keydown', '#collapse-checkout-option input[name=\'email\'], #collapse-checkout-option input[name=\'password\']', function(e) {
		if (e.keyCode == 13) {
			$('#collapse-checkout-option #button-login').trigger('click');
		}
	});

	// tooltips on hover
	$('[data-toggle=\'tooltip\']').tooltip({container: 'body'});

	// Makes tooltips work on ajax generated content
	$(document).ajaxStop(function() {
		$('[data-toggle=\'tooltip\']').tooltip({container: 'body'});
	});
});

// Cart add remove functions
var cart = {
	'add': function(product_id, quantity) {
		$.ajax({
			url: 'index.php?route=checkout/cart/add',
			type: 'post',
			data: 'product_id=' + product_id + '&quantity=' + (typeof(quantity) != 'undefined' ? quantity : 1),
			dataType: 'json',
			beforeSend: function() {
				$('#cart > button').button('loading');
			},
			complete: function() {
				$('#cart > button').button('reset');
			},
			success: function(json) {
				$('.alert, .text-danger').remove();

				if (json['redirect']) {
					location = json['redirect'];
				}

				if (json['success']) {
					$('#content').parent().before('<div class="alert alert-success"><i class="fa fa-check-circle"></i> ' + json['success'] + ' <button type="button" class="close" data-dismiss="alert">&times;</button></div>');

					// Need to set timeout otherwise it wont update the total
					setTimeout(function () {
						$('#cart > button').html('<span id="cart-total"><i class="fa fa-shopping-cart"></i> ' + json['total'] + '</span>');
					}, 100);

					$('html, body').animate({ scrollTop: 0 }, 'slow');

					$('#cart > ul').load('index.php?route=common/cart/info ul li');
				}
			},
			error: function(xhr, ajaxOptions, thrownError) {
				alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
			}
		});
	},
	'update': function(key, quantity) {
		$.ajax({
			url: 'index.php?route=checkout/cart/edit',
			type: 'post',
			data: 'key=' + key + '&quantity=' + (typeof(quantity) != 'undefined' ? quantity : 1),
			dataType: 'json',
			beforeSend: function() {
				$('#cart > button').button('loading');
			},
			complete: function() {
				$('#cart > button').button('reset');
			},
			success: function(json) {
				// Need to set timeout otherwise it wont update the total
				setTimeout(function () {
					$('#cart > button').html('<span id="cart-total"><i class="fa fa-shopping-cart"></i> ' + json['total'] + '</span>');
				}, 100);

				if (getURLVar('route') == 'checkout/cart' || getURLVar('route') == 'checkout/checkout') {
					location = 'index.php?route=checkout/cart';
				} else {
					$('#cart > ul').load('index.php?route=common/cart/info ul li');
				}
			},
			error: function(xhr, ajaxOptions, thrownError) {
				alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
			}
		});
	},
	'remove': function(key) {
		$.ajax({
			url: 'index.php?route=checkout/cart/remove',
			type: 'post',
			data: 'key=' + key,
			dataType: 'json',
			beforeSend: function() {
				$('#cart > button').button('loading');
			},
			complete: function() {
				$('#cart > button').button('reset');
			},
			success: function(json) {
				// Need to set timeout otherwise it wont update the total
				setTimeout(function () {
					$('#cart > button').html('<span id="cart-total"><i class="fa fa-shopping-cart"></i> ' + json['total'] + '</span>');
				}, 100);

				if (getURLVar('route') == 'checkout/cart' || getURLVar('route') == 'checkout/checkout') {
					location = 'index.php?route=checkout/cart';
				} else {
					$('#cart > ul').load('index.php?route=common/cart/info ul li');
				}
			},
			error: function(xhr, ajaxOptions, thrownError) {
				alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
			}
		});
	}
}

var voucher = {
	'add': function() {

	},
	'remove': function(key) {
		$.ajax({
			url: 'index.php?route=checkout/cart/remove',
			type: 'post',
			data: 'key=' + key,
			dataType: 'json',
			beforeSend: function() {
				$('#cart > button').button('loading');
			},
			complete: function() {
				$('#cart > button').button('reset');
			},
			success: function(json) {
				// Need to set timeout otherwise it wont update the total
				setTimeout(function () {
					$('#cart > button').html('<span id="cart-total"><i class="fa fa-shopping-cart"></i> ' + json['total'] + '</span>');
				}, 100);

				if (getURLVar('route') == 'checkout/cart' || getURLVar('route') == 'checkout/checkout') {
					location = 'index.php?route=checkout/cart';
				} else {
					$('#cart > ul').load('index.php?route=common/cart/info ul li');
				}
			},
			error: function(xhr, ajaxOptions, thrownError) {
				alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
			}
		});
	}
}

var wishlist = {
	'add': function(product_id) {
		$.ajax({
			url: 'index.php?route=account/wishlist/add',
			type: 'post',
			data: 'product_id=' + product_id,
			dataType: 'json',
			success: function(json) {
				$('.alert').remove();

				if (json['redirect']) {
					location = json['redirect'];
				}

				if (json['success']) {
					$('#content').parent().before('<div class="alert alert-success"><i class="fa fa-check-circle"></i> ' + json['success'] + ' <button type="button" class="close" data-dismiss="alert">&times;</button></div>');
				}

				$('#wishlist-total span').html(json['total']);
				$('#wishlist-total').attr('title', json['total']);

				$('html, body').animate({ scrollTop: 0 }, 'slow');
			},
			error: function(xhr, ajaxOptions, thrownError) {
				alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
			}
		});
	},
	'remove': function() {

	}
}

var compare = {
	'add': function(product_id) {
		$.ajax({
			url: 'index.php?route=product/compare/add',
			type: 'post',
			data: 'product_id=' + product_id,
			dataType: 'json',
			success: function(json) {
				$('.alert').remove();

				if (json['success']) {
					$('#content').parent().before('<div class="alert alert-success"><i class="fa fa-check-circle"></i> ' + json['success'] + ' <button type="button" class="close" data-dismiss="alert">&times;</button></div>');

					$('#compare-total').html(json['total']);

					$('html, body').animate({ scrollTop: 0 }, 'slow');
				}
			},
			error: function(xhr, ajaxOptions, thrownError) {
				alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
			}
		});
	},
	'remove': function() {

	}
}

/* Agree to Terms */
$(document).delegate('.agree', 'click', function(e) {
	e.preventDefault();

	$('#modal-agree').remove();

	var element = this;

	$.ajax({
		url: $(element).attr('href'),
		type: 'get',
		dataType: 'html',
		success: function(data) {
			html  = '<div id="modal-agree" class="modal">';
			html += '  <div class="modal-dialog">';
			html += '    <div class="modal-content">';
			html += '      <div class="modal-header">';
			html += '        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
			html += '        <h4 class="modal-title">' + $(element).text() + '</h4>';
			html += '      </div>';
			html += '      <div class="modal-body">' + data + '</div>';
			html += '    </div';
			html += '  </div>';
			html += '</div>';

			$('body').append(html);

			$('#modal-agree').modal('show');
		}
	});
});

// Autocomplete */
(function($) {
	$.fn.autocomplete = function(option) {
		return this.each(function() {
			this.timer = null;
			this.items = new Array();

			$.extend(this, option);

			$(this).attr('autocomplete', 'off');

			// Focus
			$(this).on('focus', function() {
				this.request();
			});

			// Blur
			$(this).on('blur', function() {
				setTimeout(function(object) {
					object.hide();
				}, 200, this);
			});

			// Keydown
			$(this).on('keydown', function(event) {
				switch(event.keyCode) {
					case 27: // escape
						this.hide();
						break;
					default:
						this.request();
						break;
				}
			});

			// Click
			this.click = function(event) {
				event.preventDefault();

				value = $(event.target).parent().attr('data-value');

				if (value && this.items[value]) {
					this.select(this.items[value]);
				}
			}

			// Show
			this.show = function() {
				var pos = $(this).position();

				$(this).siblings('ul.dropdown-menu').css({
					top: pos.top + $(this).outerHeight(),
					left: pos.left
				});

				$(this).siblings('ul.dropdown-menu').show();
			}

			// Hide
			this.hide = function() {
				$(this).siblings('ul.dropdown-menu').hide();
			}

			// Request
			this.request = function() {
				clearTimeout(this.timer);

				this.timer = setTimeout(function(object) {
					object.source($(object).val(), $.proxy(object.response, object));
				}, 200, this);
			}

			// Response
			this.response = function(json) {
				html = '';

				if (json.length) {
					for (i = 0; i < json.length; i++) {
						this.items[json[i]['value']] = json[i];
					}

					for (i = 0; i < json.length; i++) {
						if (!json[i]['category']) {
							html += '<li data-value="' + json[i]['value'] + '"><a href="#">' + json[i]['label'] + '</a></li>';
						}
					}

					// Get all the ones with a categories
					var category = new Array();

					for (i = 0; i < json.length; i++) {
						if (json[i]['category']) {
							if (!category[json[i]['category']]) {
								category[json[i]['category']] = new Array();
								category[json[i]['category']]['name'] = json[i]['category'];
								category[json[i]['category']]['item'] = new Array();
							}

							category[json[i]['category']]['item'].push(json[i]);
						}
					}

					for (i in category) {
						html += '<li class="dropdown-header">' + category[i]['name'] + '</li>';

						for (j = 0; j < category[i]['item'].length; j++) {
							html += '<li data-value="' + category[i]['item'][j]['value'] + '"><a href="#">&nbsp;&nbsp;&nbsp;' + category[i]['item'][j]['label'] + '</a></li>';
						}
					}
				}

				if (html) {
					this.show();
				} else {
					this.hide();
				}

				$(this).siblings('ul.dropdown-menu').html(html);
			}

			$(this).after('<ul class="dropdown-menu"></ul>');
			$(this).siblings('ul.dropdown-menu').delegate('a', 'click', $.proxy(this.click, this));

		});
	}
})(window.jQuery);


<!DOCTYPE html>
<!--[if IE]><![endif]-->
<!--[if IE 8 ]><html dir="ltr" lang="en" class="ie8"><![endif]-->
<!--[if IE 9 ]><html dir="ltr" lang="en" class="ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html dir="ltr" lang="en">
<!--<![endif]-->
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>CanadaHealthClub</title>
<base href="http://canadahealthclub.com/" />
<meta name="description" content="CanadaHealthClub 加拿大健康俱乐部  健健宝" />
<meta name="keywords" content= "CanadaHealthClub 加拿大健康俱乐部 健健宝" />
<script src="catalog/view/javascript/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
<link href="catalog/view/javascript/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen" />
<script src="catalog/view/javascript/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="catalog/view/javascript/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="//fonts.googleapis.com/css?family=Open+Sans:400,400i,300,700" rel="stylesheet" type="text/css" />
<link href="catalog/view/theme/default/stylesheet/stylesheet.css" rel="stylesheet">
<link href="catalog/view/javascript/jquery/owl-carousel/owl.carousel.css" type="text/css" rel="stylesheet" media="screen" />
<script src="catalog/view/javascript/common.js" type="text/javascript"></script>
<link href="http://canadahealthclub.com/" rel="canonical" />
<link href="http://canadahealthclub.com/image/catalog/cart.png" rel="icon" />
<script src="catalog/view/javascript/jquery/owl-carousel/owl.carousel.min.js" type="text/javascript"></script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-87677744-1', 'auto');
  ga('send', 'pageview');

</script>  

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-87683541-1', 'auto');
  ga('send', 'pageview');

</script>
</head>
<body class="common-home">
<nav id="top">
  <div class="container">
    <div class="pull-left">
<form action="http://canadahealthclub.com/index.php?route=common/currency/currency" method="post" enctype="multipart/form-data" id="form-currency">
  <div class="btn-group">
    <button class="btn btn-link dropdown-toggle" data-toggle="dropdown">
            <strong>CAD $</strong>
                                    <span class="hidden-xs hidden-sm hidden-md">Currency</span> <i class="fa fa-caret-down"></i></button>
    <ul class="dropdown-menu">
                  <li><button class="currency-select btn btn-link btn-block" type="button" name="CAD">CAD $ Canadian Dollar(加元)</button></li>
                        <li><button class="currency-select btn btn-link btn-block" type="button" name="CNY">CNY ￥ China Yuan (人民币元)</button></li>
                        <li><button class="currency-select btn btn-link btn-block" type="button" name="EUR">EUR  Euro(欧元)</button></li>
                        <li><button class="currency-select btn btn-link btn-block" type="button" name="USD">USD $ US Dollar(美元)</button></li>
                </ul>
  </div>
  <input type="hidden" name="code" value="" />
  <input type="hidden" name="redirect" value="http://canadahealthclub.com/index.php?route=common/home" />
</form>
</div>
    <div class="pull-left">
<form action="http://canadahealthclub.com/index.php?route=common/language/language" method="post" enctype="multipart/form-data" id="form-language">
  <div class="btn-group">
    <button class="btn btn-link dropdown-toggle" data-toggle="dropdown">
            <img src="catalog/language/en-gb/en-gb.png" alt="English (英文)" title="English (英文)">
                    <span class="hidden-xs hidden-sm hidden-md">ENGLISH 转到中文网站</span> <i class="fa fa-caret-down"></i></button>
    <ul class="dropdown-menu">
            <li><button class="btn btn-link btn-block language-select" type="button" name="en-gb"><img src="catalog/language/en-gb/en-gb.png" alt="English (英文)" title="English (英文)" /> English (英文)</button></li>
            <li><button class="btn btn-link btn-block language-select" type="button" name="zh-cn"><img src="catalog/language/zh-cn/zh-cn.png" alt="简体中文 (Chinese)" title="简体中文 (Chinese)" /> 简体中文 (Chinese)</button></li>
          </ul>
  </div>
  <input type="hidden" name="code" value="" />
  <input type="hidden" name="redirect" value="http://canadahealthclub.com/index.php?route=common/home" />
</form>
</div>
    <div id="top-links" class="nav pull-right">
      <ul class="list-inline">
        <li><a href="http://canadahealthclub.com/index.php?route=information/contact"><i class="fa fa-phone"></i></a> <span class="hidden-xs hidden-sm hidden-md">1-416-800-7655</span></li>
        <li class="dropdown"><a href="http://canadahealthclub.com/index.php?route=account/account" title="My Account" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <span class="hidden-xs hidden-sm hidden-md">My Account</span> <span class="caret"></span></a>
          <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="http://canadahealthclub.com/index.php?route=account/register">Register</a></li>
            <li><a href="http://canadahealthclub.com/index.php?route=account/login">Login</a></li>
                      </ul>
        </li>
        <li><a href="http://canadahealthclub.com/index.php?route=account/wishlist" id="wishlist-total" title="Wish List (0)"><i class="fa fa-heart"></i> <span class="hidden-xs hidden-sm hidden-md">Wish List (0)</span></a></li>
        <li><a href="http://canadahealthclub.com/index.php?route=checkout/cart" title="Shopping Cart"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Shopping Cart</span></a></li>
        <li><a href="http://canadahealthclub.com/index.php?route=checkout/checkout" title="Checkout"><i class="fa fa-share"></i> <span class="hidden-xs hidden-sm hidden-md">Checkout</span></a></li>
      </ul>
    </div>
  </div>
</nav>
<header>
  <div class="container">
    <div class="row">
      <div class="col-sm-4">
        <div id="logo">
                    <a href="http://canadahealthclub.com/index.php?route=common/home"><img src="http://canadahealthclub.com/image/catalog/logo.png" title="CanadaHealthClub" alt="CanadaHealthClub" class="img-responsive" /></a>
                  </div>
      </div>
      <div class="col-sm-5"><div id="search" class="input-group">
  <input type="text" name="search" value="" placeholder="Search" class="form-control input-lg" />
  <span class="input-group-btn">
    <button type="button" class="btn btn-default btn-lg"><i class="fa fa-search"></i></button>
  </span>
</div>      </div>
      <div class="col-sm-3"><div id="cart" class="btn-group btn-block">
  <button type="button" data-toggle="dropdown" data-loading-text="Loading..." class="btn btn-inverse btn-block btn-lg dropdown-toggle"><i class="fa fa-shopping-cart"></i> <span id="cart-total">0 item(s) - CAD $0.00</span></button>
  <ul class="dropdown-menu pull-right">
        <li>
      <p class="text-center">Your shopping cart is empty!</p>
    </li>
      </ul>
</div>
</div>
    </div>
  </div>
</header>
<div class="container">
  <nav id="menu" class="navbar">
    <div class="navbar-header"><span id="category" class="visible-xs">Categories</span>
      <button type="button" class="btn btn-navbar navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse"><i class="fa fa-bars"></i></button>
    </div>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
                        <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=59">All products</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=60">New products</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=61">Monthly Special</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=63">About US</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=65">Contact Us</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=64">How to purchase</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=66">Affiliate Member</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=67">Natural health products</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=81">Blog</a></li>
                                <li><a href="http://canadahealthclub.com/index.php?route=product/category&amp;path=80">Register</a></li>
                      </ul>
    </div>
  </nav>
</div>
<div class="container">
  <div class="row">                <div id="content" class="col-sm-12"><div id="slideshow0" class="owl-carousel" style="opacity: 1;">
    <div class="item">
        <img src="http://canadahealthclub.com/image/cache/catalog/2017002-1140x380.jpg" alt="2017002" class="img-responsive" />
      </div>
  </div>
<script type="text/javascript"><!--
$('#slideshow0').owlCarousel({
	items: 6,
	autoPlay: 15000,
	singleItem: true,
	navigation: true,
	navigationText: ['<i class="fa fa-chevron-left fa-5x"></i>', '<i class="fa fa-chevron-right fa-5x"></i>'],
	pagination: true
});
--></script><h3>Featured</h3>
<div class="row">
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=68"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105海豹油200-200x200.jpg" alt=" ARCTIC SEAL OIL 200 Capsule" title=" ARCTIC SEAL OIL 200 Capsule" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=68"> ARCTIC SEAL OIL 200 Capsule</a></h4>
        <p>Arctic Seals are mammals that live in the Arctic Ocean.The blubber of seal contains high concentrati..</p>
                        <p class="price">
                    CAD $15.80                              <span class="price-tax">Ex Tax: CAD $15.80</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('68');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('68');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('68');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=85"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105心脑康90-200x200.jpg" alt=" Super Seal Oil Complex 90 Capsules" title=" Super Seal Oil Complex 90 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=85"> Super Seal Oil Complex 90 Capsules</a></h4>
        <p>Super seal oil complex is made from multiply natural nutrition components. It can decrease the risk ..</p>
                        <p class="price">
                    CAD $22.50                              <span class="price-tax">Ex Tax: CAD $22.50</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('85');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('85');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('85');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=78"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105深海鱼油100-200x200.jpg" alt="Fish Oil Softgel Capsules 100 Capsules" title="Fish Oil Softgel Capsules 100 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=78">Fish Oil Softgel Capsules 100 Capsules</a></h4>
        <p>The Arctic Fish Oil is one of the best sources of essential omega-3. The brand name ‘optimal nature’..</p>
                        <p class="price">
                    CAD $11.00                              <span class="price-tax">Ex Tax: CAD $11.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('78');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('78');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('78');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=82"><img src="http://canadahealthclub.com/image/cache/catalog/luanlinzhi100-200x200.jpg" alt="Lecithin 100 Capsules" title="Lecithin 100 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=82">Lecithin 100 Capsules</a></h4>
        <p>Lecithin, a phospholipid substance, combines with protein, and vitamins to five essential nutrients...</p>
                        <p class="price">
                    CAD $10.50                              <span class="price-tax">Ex Tax: CAD $10.50</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('82');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('82');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('82');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=72"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105葡萄籽-200x200.jpg" alt="Grape Seed OPC 120 Capsules" title="Grape Seed OPC 120 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=72">Grape Seed OPC 120 Capsules</a></h4>
        <p>Grape Seed Extract OPC is extracted from red grape seeds, which have a high content of compounds kno..</p>
                <div class="rating">
                              <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                            </div>
                        <p class="price">
                    CAD $32.00                              <span class="price-tax">Ex Tax: CAD $32.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('72');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('72');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('72');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=73"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105液体蜂胶-200x200.jpg" alt="Bee Propolis(100% Pure Concentrated Extract and Alcohol Free)" title="Bee Propolis(100% Pure Concentrated Extract and Alcohol Free)" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=73">Bee Propolis(100% Pure Concentrated Extract and Alcohol Free)</a></h4>
        <p>Propolis is a wax-like, resinous substance that bees collect from tree buds, or other botanical sour..</p>
                <div class="rating">
                              <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                            </div>
                        <p class="price">
                    CAD $37.50                              <span class="price-tax">Ex Tax: CAD $37.50</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('73');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('73');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('73');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=62"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105辅酶-200x200.jpg" alt="Coenzyme Q10 90 Capsules" title="Coenzyme Q10 90 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=62">Coenzyme Q10 90 Capsules</a></h4>
        <p>Coenzyme Q10 is a compound found naturally in every cell of the body and is a vital nutrient for the..</p>
                <div class="rating">
                              <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                            </div>
                        <p class="price">
                    CAD $46.00                              <span class="price-tax">Ex Tax: CAD $46.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('62');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('62');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('62');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=74"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105骨胶原-200x200.jpg" alt="Joint Complex Softgel Capsules 90 Capsules" title="Joint Complex Softgel Capsules 90 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=74">Joint Complex Softgel Capsules 90 Capsules</a></h4>
        <p>This powerful formula combines the most popular, scientifically researched ingredients for joint hea..</p>
                <div class="rating">
                              <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                            </div>
                        <p class="price">
                    CAD $46.00                              <span class="price-tax">Ex Tax: CAD $46.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('74');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('74');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('74');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=64"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105老人维生素-200x200.jpg" alt=" Multi Daily (For 50 Plus) 90 Capsules" title=" Multi Daily (For 50 Plus) 90 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=64"> Multi Daily (For 50 Plus) 90 Capsules</a></h4>
        <p>For those over 50 years old, the digestive system and absorption rate of nutrients deteriorate, ther..</p>
                <div class="rating">
                              <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                            </div>
                        <p class="price">
                    CAD $22.00                              <span class="price-tax">Ex Tax: CAD $22.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('64');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('64');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('64');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=69"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105大蒜油-200x200.jpg" alt="Odourless Garlic Oil 90 Capsules" title="Odourless Garlic Oil 90 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=69">Odourless Garlic Oil 90 Capsules</a></h4>
        <p>Garlic has been used as both a food and medicine in many cultures for centuries. Garlic is a powerfu..</p>
                <div class="rating">
                              <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                                        <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>
                            </div>
                        <p class="price">
                    CAD $22.00                              <span class="price-tax">Ex Tax: CAD $22.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('69');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('69');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('69');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=76"><img src="http://canadahealthclub.com/image/cache/catalog/_20180105蓝莓眼灵灵-200x200.jpg" alt="Blueberry &amp; Flax Seed Oil Softgel Capsules 90 Capsules" title="Blueberry &amp; Flax Seed Oil Softgel Capsules 90 Capsules" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=76">Blueberry &amp; Flax Seed Oil Softgel Capsules 90 Capsules</a></h4>
        <p>Blueberry is specific for the eyes because of its antioxidant qualities and its general effects on t..</p>
                        <p class="price">
                    CAD $36.00                              <span class="price-tax">Ex Tax: CAD $36.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('76');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('76');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('76');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
    <div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12">
    <div class="product-thumb transition">
      <div class="image"><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=56"><img src="http://canadahealthclub.com/image/cache/catalog/136_P_1273799349039-200x200.jpg" alt="Melatonin 60 Capsule" title="Melatonin 60 Capsule" class="img-responsive" /></a></div>
      <div class="caption">
        <h4><a href="http://canadahealthclub.com/index.php?route=product/product&amp;product_id=56">Melatonin 60 Capsule</a></h4>
        <p>Melatonin is a hormone found in our bodies responsible for regulating the body’s sleeping cycle.Norm..</p>
                        <p class="price">
                    CAD $16.00                              <span class="price-tax">Ex Tax: CAD $16.00</span>
                  </p>
              </div>
      <div class="button-group">
        <button type="button" onclick="cart.add('56');"><i class="fa fa-shopping-cart"></i> <span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button>
        <button type="button" data-toggle="tooltip" title="Add to Wish List" onclick="wishlist.add('56');"><i class="fa fa-heart"></i></button>
        <button type="button" data-toggle="tooltip" title="Compare this Product" onclick="compare.add('56');"><i class="fa fa-exchange"></i></button>
      </div>
    </div>
  </div>
  </div>
<div id="carousel0" class="owl-carousel">
  </div>
<script type="text/javascript"><!--
$('#carousel0').owlCarousel({
	items: 6,
	autoPlay: 3000,
	navigation: true,
	navigationText: ['<i class="fa fa-chevron-left fa-5x"></i>', '<i class="fa fa-chevron-right fa-5x"></i>'],
	pagination: true
});
--></script></div>
    </div>
</div>
<footer>
  <div class="container">
    <div class="row">
            <div class="col-sm-3">
        <h5>Information</h5>
        <ul class="list-unstyled">
                    <li><a href="http://canadahealthclub.com/index.php?route=information/information&amp;information_id=22">网站使用说明：登录，修改资料，下单，付款</a></li>
                    <li><a href="http://canadahealthclub.com/index.php?route=information/information&amp;information_id=6">Delivery Information</a></li>
                    <li><a href="http://canadahealthclub.com/index.php?route=information/information&amp;information_id=3">Privacy Policy</a></li>
                  </ul>
      </div>
            <div class="col-sm-3">
        <h5>Customer Service</h5>
        <ul class="list-unstyled">
          <li><a href="http://canadahealthclub.com/index.php?route=information/contact">Contact Us</a></li>
        </ul>
      </div>
      <div class="col-sm-3">
        <h5>Extras</h5>
        <ul class="list-unstyled">
          <li><a href="http://canadahealthclub.com/index.php?route=product/manufacturer">Brands</a></li>
          <li><a href="http://canadahealthclub.com/index.php?route=product/special">Specials</a></li>
        </ul>
      </div>
      <div class="col-sm-3">
        <h5>My Account</h5>
        <ul class="list-unstyled">
          <li><a href="http://canadahealthclub.com/index.php?route=account/account">My Account</a></li>
          <li><a href="http://canadahealthclub.com/index.php?route=account/order">Order History</a></li>
          <li><a href="http://canadahealthclub.com/index.php?route=account/wishlist">Wish List</a></li>
          <li><a href="http://canadahealthclub.com/index.php?route=account/newsletter">Newsletter</a></li>
        </ul>
      </div>
    </div>
    <hr>
    <p></p>
  </div>
  		
	<div class="copyright" align="center">
	 <a href="" target="_blank">官方微信客服  Wechat QR Code</a>	  
  	     </br>	  	  
  	    <img src="http://canadahealthclub.com/barcodesmall.jpg"  alt="微信" />
  	     </br>
  	<a href="http://canadahealthclub.com/" target="_blank">CanadahealthClub © 2016</a>	  
  		</br>
  <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1260984419'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1260984419%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>	

       </div>
</footer>

<!--
OpenCart is open source software and you are free to remove the powered by OpenCart if you want, but its generally accepted practise to make a small donation.
Please donate via PayPal to donate@opencart.com
//-->

<!-- Theme created by Welford Media for OpenCart 2.0 www.welfordmedia.co.uk -->

</body></html>

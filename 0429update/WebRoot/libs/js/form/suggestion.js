var parentTopHeight;
var broswerFlag;

jQuery.fn.extend( {
	suggestionRender : function() {
		return this.each(function() {
			$(this).empty();
			new jQuery.suggestionBox(this);
		});
	}
});

//设置深度，不被其他组件盖住
//var depth=500;
//定义初始标识
var selectTree_id = 1;
jQuery.suggestionBox = function(selectobj) {
	var opt = {};
	//文本框样式为selectbox
	opt.inputClass = opt.inputClass || "suggestion_input";
	//内容层样式为selectbox-tree
	opt.containerClass = opt.containerClass || "selectbox-tree";
	//内容项鼠标移入样式为current
	opt.hoverClass = opt.hoverClass || "current";
	//内容项选中的样式为current
	opt.currentClass = opt.selectedClass || "selected";
	opt.debug = opt.debug || false;

	var selInputHeight = 24;
	var selButtonWidth = 24;
	if (!splitMode) {
		var $parentThemeDom = $(window.top.document.getElementById("theme"));
		if ($parentThemeDom.attr("selInputHeight") != null) {
			selInputHeight = Number($parentThemeDom.attr("selInputHeight"));
		}
		if ($parentThemeDom.attr("selButtonWidth") != null) {
			selButtonWidth = Number($parentThemeDom.attr("selButtonWidth"));
		}
	}

	//每渲染一次标识+1
	selectTree_id++;

	//得到当前点击的input的id
	var curInputId = "0_input";

	//得到当前点击的button的id
	var curButtonId = "0_button";

	//标识是否使用键盘让文本框处于焦点
	var inFocus = false;

	var t_pop_focus = false;
	var t_suggest_focus = false;
	var t_suggest_page_click = false;

	var $select = $(selectobj);
	$select.addClass("mainCon");
	if ($select.attr("inputAlign") == "right") {
		$select.css("float", "right");
	}

	var popTitle = uncompile(quiLanguage.suggestion.popTitle);
	if ($select.attr("popTitle") != null) {
		popTitle = $select.attr("popTitle");
	}
	var showMultiList = false;
	if ($select.attr("showMultiList") == true
			|| $select.attr("showMultiList") == "true") {
		showMultiList = true;
	}

	var showList = false;
	if ($select.attr("showList") == true || $select.attr("showList") == "true") {
		showList = true;
	}

	//定义初始文本
	var promptText = uncompile(quiLanguage.suggestion.promptText1);
	if ($select.attr("prompt") != null) {
		promptText = $select.attr("prompt");
	} else if (showList || showMultiList) {
		promptText = uncompile(quiLanguage.suggestion.promptText2);
	}

	//用selectedValue标识是否初始时有选中项
	var selectedValue = "";
	if ($select.attr("selectedValue")) {
		selectedValue = $select.attr("selectedValue");
	}

	var pageNum = 10;
	if ($select.attr("pageNum") != null) {
		pageNum = Number($select.attr("pageNum"));
	}
	var pageCount = 5;
	if ($select.attr("pageCount") != null) {
		pageCount = Number($select.attr("pageCount"));
	}

	var colWidth = 50;
	if ($select.attr("colWidth") != null) {
		colWidth = Number($select.attr("colWidth"));
	}

	var suggestTitle = uncompile(quiLanguage.suggestion.suggestTitle);
	if ($select.attr("suggestTitle") != null) {
		suggestTitle = $select.attr("suggestTitle");
	}
	var errorMsg = uncompile(quiLanguage.suggestion.errorMsg);
	if ($select.attr("errorMsg") != null) {
		errorMsg = $select.attr("errorMsg");
	}
	var iconSrc;
	if ($select.attr("iconSrc") != null) {
		iconSrc = $select.attr("iconSrc");
	}
	var autoCheck = true;
	if ($select.attr("autoCheck") == false
			|| $select.attr("autoCheck") == "false") {
		autoCheck = false;
	}
	var errorAlert = false;
	if ($select.attr("errorAlert") == true
			|| $select.attr("errorAlert") == "true") {
		errorAlert = true;
	}

	var currentQueryString = "";
	var suggestMode = "local";
	if ($select.attr("suggestMode") == "remote") {
		suggestMode = "remote";
	}
	var minChars = 1;
	if ($select.attr("minChars") != null) {
		minChars = Number($select.attr("minChars"));
	}
	var delay = 0;
	if ($select.attr("delay") != null) {
		delay = Number($select.attr("delay"));
	}
	var matchName = "q";
	if ($select.attr("matchName") != null) {
		matchName = $select.attr("matchName");
	}

	//创建弹窗层
	var $container = setupContainer(opt);
	//弹窗层内容项区容器
	var popContainer = $container.find('.pop_city_container');
	//弹窗层标签区容器
	var labelMain = $container.find('.list_label');

	var $suggestMain = setupSuggestContainer(opt);

	//创建文本框
	var $input = setupInput(opt);
	try {
		$input.textInputStyleRender();
	} catch (e) {
	}

	//创建隐藏域
	var $hidden = setupHidden(opt);

	//如果处于禁用，让按钮也禁用
	//if($select.attr("disabled")=="disabled"||$select.attr("disabled")=="true"||$select.attr("disabled")==true){
	//$input.addClass("selectbox_disabled");
	//}

	//定义文本框默认长度
	var inputWidth;

	//如果设置了selWidth，则以selWidth长度为准
	if ($select.attr("inputWidth") != null) {
		inputWidth = Number($select.attr("inputWidth"));
		$input.width(inputWidth);
	} else {
		inputWidth = $input.width();
	}

	if ($select.attr("inputHeight") != null) {
		selInputHeight = Number($select.attr("inputHeight"));
		$input.height(selInputHeight);
	}

	//alert(inputWidth)

	//在主容器中添加文本框、按钮、内容层和隐藏域
	$select.append($input);
	$select.append($container);
	$select.append($suggestMain);
	$select.append($hidden);

	//隐藏层
	$container.hide();
	$suggestMain.hide();

	//获取数据前缀
	var dataRoot = "list";
	if ($select.attr("dataRoot")) {
		dataRoot = $select.attr("dataRoot");
	}

	//获取参数
	var paramsStr = $select.attr("params");
	var paramsObj = {};
	if (paramsStr) {

		try {
			paramsObj = JSON.parse(paramsStr);
		} catch (e) {
			paramsObj = [];
			alert(uncompile(quiLanguage.suggestion.paramErrorMessage))
		}
	}
	var urlStr = $select.attr("url");
	var finalDataList;
	var finalDataTab;
	var finalData;
	if (suggestMode == "local") {
		//获取数据
		var dataObj = "";

		var dataObj2 = $select.data("data");
		var tabData = null;
		if ($select.data("tab") != null) {
			tabData = $select.data("tab");
			if (tabData[dataRoot]) {
				finalDataTab = tabData[dataRoot]
			} else {
				finalDataTab = tabData;
			}
		}
		var listData = null;

		if ($select.data("list") != null) {
			listData = $select.data("list");
			if (listData[dataRoot]) {
				finalDataList = listData[dataRoot]
			} else {
				finalDataList = listData;
			}
		}

		//优先使用data
		if (dataObj2) {
			if (showMultiList) {
				createOptions(dataObj2, tabData);
			}
			if (selectedValue == "") {
				//设置水印
				if ($.trim($input.val()) == ''
						|| $.trim($input.val()) == promptText) {
					$input.val(promptText).css('color', '#aaa');
				}
				$select.attr("relText", "");
				$select.attr("relValue", "");
				$select.attr("value", "");
				$hidden.val("");
			} else {
				var listData = $select.data("data");
				if (listData) {
					if (listData[dataRoot]) {
						finalData = listData[dataRoot]
					} else {
						finalData = listData;
					}
					for ( var i = 0; i < finalData.length; i++) {
						if (finalData[i].value == selectedValue) {
							setCurrent(finalData[i].value, selectedValue);
							break;
						}
					}
				}
			}
		} else if (urlStr) {
			var dataType = "json";
			if ($select.attr("dataType")) {
				dataType = $select.attr("dataType");
			}
			$.ajax( {
				url : $select.attr("url"),
				dataType : dataType,
				data : paramsObj,
				error : function() {
					alert(uncompile(quiLanguage.suggestion.urlErrorMessage))
				},
				success : function(data) {
					var myData;
					if (dataType == "text") {
						myData = eval("(" + data + ")");
					} else {
						myData = data;
					}
					$select.data("data", myData);
					dataObj = myData;
					if (showMultiList) {
						createOptions(myData, tabData);
					}
					if (selectedValue == "") {
						//设置水印
				if ($.trim($input.val()) == ''
						|| $.trim($input.val()) == promptText) {
					$input.val(promptText).css('color', '#aaa');
				}
				$select.attr("relText", "");
				$select.attr("relValue", "");
				$select.attr("value", "");
				$hidden.val("");
			} else {
				var listData = $select.data("data");
				if (listData) {
					if (listData[dataRoot]) {
						finalData = listData[dataRoot]
					} else {
						finalData = listData;
					}
					for ( var i = 0; i < finalData.length; i++) {
						if (finalData[i].value == selectedValue) {
							setCurrent(finalData[i].value, selectedValue);
							break;
						}
					}
				}
			}
		}
			});
		}

	} else {
		if ($select.attr("relValue") && $select.attr("relValue") != "") {
			$hidden.val($select.attr("relValue"));
			$input.val($select.attr("relText")).css('color', '#000000');
		} else {
			//设置水印
			if ($.trim($input.val()) == ''
					|| $.trim($input.val()) == promptText) {
				$input.val(promptText).css('color', '#aaa');
			}
			$select.attr("relText", "");
			$select.attr("relValue", "");
			$select.attr("value", "");
			$hidden.val("");
		}
	}

	$input.focus(function() {
		//处于焦点时弹窗
			if (t_suggest_page_click) {
				t_suggest_page_click = false;
				return true;
			}
			//隐藏提示层
			//hideSuggest();
			//隐藏水印
			if ($.trim($(this).val()) == promptText) {
				$(this).val('').css('color', '#000');
			}
			//显示弹窗层
			//setHeight();
			//showMe();
		}).click(function() {
		//点击时隐藏提示层，打开弹窗层
			if (t_suggest_page_click) {
				t_suggest_page_click = false;
				return;
			}
			if (suggestMode == "local") {
				hideSuggest();
				setHeight();
				if (showMultiList) {
					showMe();
				} else if (showList) {
					loadCity();
					return;
				}
			}

		}).blur(function() {
		if (t_pop_focus == false) {
			//失去焦点时隐藏弹窗层
			hideMe();
		}

		if (t_suggest_focus == false) {
			hideSuggest();
		}
		//恢复水印	
		if ($.trim($input.val()) == '' || $.trim($input.val()) == promptText) {
			setCurrent(promptText, "");
			setTimeout(function() {
				$select.trigger("validate2");
			}, 500)
		} else {
			if (suggestMode == "local") {
				if (autoCheck) {
					setTimeout(function() {
						if ($suggestMain[0].style.display == "none"
								&& $container[0].style.display == "none") {
							var inputValue = $input.val();
							var isHave = false;
							var listData = $select.data("data");
							if (listData) {
								if (listData[dataRoot]) {
									finalData = listData[dataRoot]
								} else {
									finalData = listData;
								}
								for ( var i = 0; i < finalData.length; i++) {
									if (finalData[i].key == inputValue) {
										setCurrent(inputValue,
												finalData[i].value);
										isHave = true;
										break;
									}
								}
								if (!isHave) {
									if (errorAlert) {
										try {
											top.Dialog.alert(errorMsg);
										} catch (e) {
											alert(errorMsg);
										}
									}
									setCurrent(promptText, "");
								}
							}

						}
					}, 500)
				}
			} else {
				$select.attr("relText", $input.val());
				$select.attr("relvalue", $input.val());
				$select.attr("value", $input.val());
				$hidden.val($input.val())
			}
			setTimeout(function() {
				$select.trigger("validate", $.trim($input.val()));
			}, 500)

		}
	});
	//点击标签区域切换标签
	labelMain.find('a').bind('click', function() {
		$input.focus();//使焦点在输入框，避免blur事件无法触发
			t_pop_focus = true;
			var labelId = $(this).attr('id');
			labelMain.find('li a').removeClass('current');
			$(this).addClass('current');
			popContainer.find('ul').hide();
			$("#" + labelId + '_container').show();
		});
	//点击内容区选项，进行选中赋值，隐藏弹窗层
	popContainer.find('a').bind(
			'click',
			function() {
				setCurrent($(this).text(), $(this).attr("rel"));
				$select.trigger("multiListSelect", $(this).attr("rel"), $(this)
						.text());
				hideMe();
			});
	$container.mouseover(function() {
		t_pop_focus = true;
	}).mouseout(function() {
		t_pop_focus = false;
	});

	$input
			.keydown(function(event) {
				//按下键盘后弹窗关闭
					hideMe();
					event = window.event || event;
					var keyCode = event.keyCode || event.which
							|| event.charCode;
					//左右翻页，上下切换选中项
					if (keyCode == 37) {//左
						prevPage();
					} else if (keyCode == 39) {//右
						nextPage();
					} else if (keyCode == 38) {//上
						prevResult();
					} else if (keyCode == 40) {//下
						nextResult();
					}
				})
			.keypress(function(event) {
				event = window.event || event;
				var keyCode = event.keyCode || event.which || event.charCode;
				//回车选中
					if (13 == keyCode) {
						if ($suggestMain
								.find('.list_city_container a.selected').length > 0) {
							var $item = $suggestMain
									.find('.list_city_container a.selected');
							setCurrent($item.text(), $item.attr("rel"));
							$select.trigger("listSelect", {
								"relValue" : $item.attr("rel"),
								"relText" : $item.text()
							});
							// $input.val($suggestMain.find('.list_city_container a.selected').children('b').text());
							hideSuggest();
						}
					}
				}).keyup(
					function(event) {
						event = window.event || event;
						var keyCode = event.keyCode || event.which
								|| event.charCode;
						if (keyCode != 13 && keyCode != 37 && keyCode != 39
								&& keyCode != 9 && keyCode != 38
								&& keyCode != 40) {
							//keyCode == 9是tab切换键
							//每次输入时查询结果
							queryCity();
						}
					});

	//下一页
	function nextPage() {
		var add_cur = $suggestMain.find(".page_break a.current").next();
		if (add_cur != null) {
			if ($(add_cur).attr("inum") != null) {
				setAddPage($(add_cur).attr("inum"));
			}
		}
	}
	//上一页
	function prevPage() {
		var add_cur = $suggestMain.find(".page_break a.current").prev();
		if (add_cur != null) {
			if ($(add_cur).attr("inum") != null) {
				setAddPage($(add_cur).attr("inum"));
			}
		}
	}
	//下一条记录
	function nextResult() {
		var t_index = $suggestMain.find('.list_city_container a').index(
				$suggestMain.find('.list_city_container a.selected')[0]);
		$suggestMain.find('.list_city_container').children().removeClass(
				'selected');
		t_index += 1;
		var t_end = $suggestMain.find('.list_city_container a').index(
				$suggestMain.find('.list_city_container a:visible').filter(
						':last')[0]);
		if (t_index > t_end) {
			t_index = $suggestMain.find('.list_city_container a').index(
					$suggestMain.find('.list_city_container a:visible').eq(0));
		}
		$suggestMain.find('.list_city_container a').eq(t_index).addClass(
				'selected');
	}
	//上一条记录
	function prevResult() {
		var t_index = $suggestMain.find('.list_city_container a').index(
				$suggestMain.find('.list_city_container a.selected')[0]);
		$suggestMain.find('.list_city_container').children().removeClass(
				'selected');
		t_index -= 1;
		var t_start = $suggestMain.find('.list_city_container a').index(
				$suggestMain.find('.list_city_container a:visible').filter(
						':first')[0]);
		if (t_index < t_start) {
			t_index = $suggestMain.find('.list_city_container a').index(
					$suggestMain.find('.list_city_container a:visible').filter(
							':last')[0]);
		}
		$suggestMain.find('.list_city_container a').eq(t_index).addClass(
				'selected');
	}
	//加载hotList的数据
	function loadCity() {
		var cityList = $suggestMain.find('.list_city_container');
		cityList.empty();
		var hotData;
		if (listData) {
			hotData = listData;
		} else {
			hotData = $select.data("data");
		}
		if (hotData) {
			$
					.each(
							hotData[dataRoot],
							function(idx, item) {
								var $item = $("<a href='javascript:void(0)' style='display:none'><span>"
										+ item.key + "</span></a>");
								$item.attr("rel", item.value);
								cityList.append($item);
								//点击提示层，选中选项
								$item.bind(
										'click',
										function() {
											setCurrent($(this).text(), $(this)
													.attr("rel"));
											$select.trigger("listSelect", {
												"relValue" : $(this)
														.attr("rel"),
												"relText" : $(this).text()
											});
											hideSuggest();
										}).bind('mouseover', function() {
									t_suggest_focus = true;
								}).bind('mouseout', function() {
									t_suggest_focus = false;
								});
							});
		}
		$suggestMain.find('.list_city_head').html(suggestTitle);
		setAddPage(1);
		setHeightSuggestion();
		showSuggest();
		setTopSelect();
	}
	//查询
	function queryCity() {
		if (suggestMode == "local") {
			//弹窗层关闭
			hideMe();
			var value = $input.val().toLowerCase();
			//如果输入为空，直接加载固定的hotList数据
			if (value.length == 0) {
				if (showMultiList) {
					showMe();
					hideSuggest();
					setHeight();
				} else {
					loadCity();
				}
				return;
			}
			//显示提示层
			setHeightSuggestion();
			showSuggest();
			//alert($suggestMain)  
			var city_container = $suggestMain.find('.list_city_container');

			//设置是否存在标志位      
			var isHave = false;
			//设置匹配项存放的数组
			var _tmp = new Array();

			var listData = $select.data("data");
			if (listData) {
				if (listData[dataRoot]) {
					finalData = listData[dataRoot]
				} else {
					finalData = listData;
				}
				$.each(finalData, function(idx, item) {
					if (item.suggest) {
						var suggestArray = item.suggest.split("|");
						//alert(suggestArray.length)
						for ( var i = 0; i < suggestArray.length; i++) {
							if (suggestArray[i].indexOf(value) >= 0) {
								isHave = true;
								_tmp.push(item);
								break;
							}
						}
					}
				})
			}

			//如果存在匹配项
			if (isHave) {
				//先清空
				city_container.empty();
				//循环生成选项并隐藏（后面进行分页处理）
				for ( var item in _tmp) {
					var _data = _tmp[item];
					var $item = $("<a href='javascript:void(0)' style='display:none'><span>"
							+ _data.key + "</span></a>");
					$item.attr("rel", _data.value);
					city_container.append($item);

					//点击提示层，选中选项
					$item.bind('click', function() {
						setCurrent($(this).text(), $(this).attr("rel"));
						$select.trigger("listSelect", {
							"relValue" : $(this).attr("rel"),
							"relText" : $(this).text()
						});
						hideSuggest();
					}).bind('mouseover', function() {
						t_suggest_focus = true;
					}).bind('mouseout', function() {
						t_suggest_focus = false;
					});

				}
				//设置提示信息
				$suggestMain.find('.list_city_head').html(suggestTitle);
				//设置为第一页
				setAddPage(1);
				//设置第一行选中
				setTopSelect()

			} else {
				//无匹配项，设置提示信息：找不到
				$suggestMain
						.find('.list_city_head')
						.html(
								"<span class='msg'>"
										+ uncompile(quiLanguage.suggestion.errorMessage)
										+ value + "</span>");
			}
		} else {
			//获取当前输入
			currentQueryString = $.trim($input.val());
			if (currentQueryString == "") {
				hideSuggest();
			} else {
				//输入字符长度大于开始最小匹配个数
				if (currentQueryString.length >= minChars) {
					setTimeout(function() {
						//载入列表
							loadList(currentQueryString);
						}, delay);
				} else {
					//输入不够数则收起选项容器
					hideSuggest();
				}
			}
		}
	}
	function loadList(queryString) {
		// 构建url
		var queryUrl = buildUrl(queryString);
		$input.addClass("hintbox_loading");
		$
				.post(queryUrl.url,
						queryUrl.params,
						function(data, textStatus, jqXHR) {
							var city_container = $suggestMain
									.find('.list_city_container');
							//先清空
						city_container.empty();
						if (data[dataRoot]) {
							if (data[dataRoot].length > 0) {
								$
										.each(
												data[dataRoot],
												function(idx, item) {
													var $item = $("<a href='javascript:void(0)' style='display:none' ><span>"
															+ data[dataRoot][idx].key
															+ "</span></a>");
													$item
															.attr(
																	"rel",
																	data[dataRoot][idx].value);
													city_container
															.append($item);

													//点击提示层，选中选项
													$item
															.bind(
																	'click',
																	function() {
																		setCurrent(
																				$(
																						this)
																						.text(),
																				$(
																						this)
																						.attr(
																								"rel"));
																		$select
																				.trigger(
																						"listSelect",
																						{
																							"relValue" : $(
																									this)
																									.attr(
																											"rel"),
																							"relText" : $(
																									this)
																									.text()
																						});
																		hideSuggest();
																	})
															.bind(
																	'mouseover',
																	function() {
																		t_suggest_focus = true;
																	})
															.bind(
																	'mouseout',
																	function() {
																		t_suggest_focus = false;
																	});
												})
								//设置提示信息
								$suggestMain.find('.list_city_head').html(
										suggestTitle);
								//设置为第一页
								setAddPage(1);
								//设置第一行选中
								setTopSelect();

							} else {
								//无匹配项，设置提示信息：找不到
								$suggestMain
										.find('.list_city_head')
										.html(
												"<span class='msg'>"
														+ uncompile(quiLanguage.suggestion.errorMessage)
														+ $input.val()
														+ "</span>");
								setAddPage(1);
							}
							//显示提示层
							setHeightSuggestion();
							if ($suggestMain[0].style.display == "none") {
								showSuggest();
							}
						}
						$input.removeClass("hintbox_loading");
					}, 'json');

	}
	function buildUrl(queryString) {
		var urlResult = {
			url : urlStr,
			params : {},
			queryString : ''
		};
		urlResult.params[matchName] = queryString;
		//构建如此格式： http://xxxx.do?q=北
		urlResult.queryString = urlStr + "?" + matchName + "=" + queryString;
		//添加参数
		$.extend(true, urlResult.params, paramsObj);

		return urlResult;
	}
	//设置分页
	function setAddPage(pageIndex) {
		$suggestMain.find('.list_city_container a').removeClass('selected');
		$suggestMain.find('.list_city_container').children().each(function(i) {
			var k = i + 1;
			if (k > pageNum * (pageIndex - 1) && k <= pageNum * pageIndex) {
				$(this).css('display', 'block');
			} else {
				$(this).hide();
			}
		});
		setTopSelect();
		setAddPageHtml(pageIndex);
	}
	//每次分页时改变分页的html
	function setAddPageHtml(pageIndex) {

		var cityPageBreak = $suggestMain.find('.page_break');
		cityPageBreak.empty();
		if ($suggestMain.find('.list_city_container').children().length > pageNum) {
			var pageBreakSize = Math.ceil($suggestMain.find(
					'.list_city_container').children().length
					/ pageNum);
			if (pageBreakSize <= 1) {
				return;
			}
			var start = end = pageIndex;
			for ( var index = 0, num = 1; index < pageCount && num < pageCount; index++) {
				if (start > 1) {
					start--;
					num++;
				}
				if (end < pageBreakSize) {
					end++;
					num++;
				}
			}
			if (pageIndex > 1) {
				// cityPageBreak.append("<a href='javascript:void(0)' inum='"+(pageIndex-1)+"'>&lt;-</a>");
			}
			for ( var i = start; i <= end; i++) {
				if (i == pageIndex) {
					cityPageBreak
							.append("<a href='javascript:void(0)' class='current' inum='"
									+ (i) + "'>" + (i) + "</a");
				} else {
					cityPageBreak.append("<a href='javascript:void(0)' inum='"
							+ (i) + "'>" + (i) + "</a");
				}
			}
			if (pageIndex < pageBreakSize) {
				// cityPageBreak.append("<a href='javascript:void(0);' inum='"+ (i) +"'>-&gt;</a>");
			}
			cityPageBreak.show();
		} else {
			cityPageBreak.hide();
		}
		var pageitem = cityPageBreak.find("a");
		pageitem.unbind("click");
		pageitem.unbind("mouseover");
		pageitem.unbind("mouseout");
		pageitem.bind('click', function(event) {
			t_suggest_page_click = true;
			if ($(this).attr('inum') != null) {
				//进行分页
				setAddPage($(this).attr('inum'));
			}
			$input.focus()
		});

		pageitem.bind('mouseover', function() {
			t_suggest_focus = true;
		}).bind('mouseout', function() {
			t_suggest_focus = false;
			//$("#scrollContent").append("<div>"+t_suggest_focus+"<div>")
				//alert(t_suggest_focus)
			});
		setHeightSuggestion();
		t_suggest_focus = false;
		return;
	}
	//选中第一条
	function setTopSelect() {
		//alert($suggestMain.find('.list_city_container').children(':visible').length)	
		if ($suggestMain.find('.list_city_container').children().length > 0) {
			$suggestMain.find('.list_city_container').children(':visible')
					.eq(0).addClass('selected');
		}
	}

	function setHeight() {
		//先还原
		$container.css( {
			"overflowY" : "visible",
			"overflowX" : "visible"
		});
		$container.width("");
		$container.height("");
		var usefulHeight = 200;
		usefulHeight = window.document.documentElement.clientHeight
				- ($select.offset().top - $(window).scrollTop()) - 30;

		var trueWidth;
		if (!$select.attr("multiListWidth")) {
			trueWidth = $container.width();
		}
		$container.css( {
			"overflowY" : "auto",
			"overflowX" : "hidden"
		});
		if (!$select.attr("multiListWidth")) {
			$container.width(trueWidth);
		} else {
			$container.width(Number($select.attr("multiListWidth")));
		}

		var boxHeight = 0;
		if ($select.attr("multiListHeight")) {
			boxHeight = Number($select.attr("multiListHeight"));
		}

		//设置容器高度
		if (boxHeight != 0) {
			$container.height(boxHeight);

			//强制向上展开
			if ($select.attr("openDirection") == "top") {
				$container.css( {
					top : -boxHeight
				});
			}
			//强制向下展开
			else if ($select.attr("openDirection") == "bottom") {
				$container.css( {
					top : selInputHeight
				});
			}
			//智能判断方向
			else {
				if (usefulHeight < boxHeight) {//如果底部容纳不下
					if ($select.offset().top > boxHeight) {//如果上部能容纳下,向上展开
						$container.css( {
							top : -boxHeight
						});
					} else if (usefulHeight < 100
							&& $select.offset().top > usefulHeight
							&& $select.offset().top > 100) {//如果上部也容纳不下，并且底部不足100,向上展开并强制高度，出滚动条
						$container.css( {
							top : -boxHeight
						});
					} else {//上面容纳不下，下面大于100，则向下展开，并强制高度，出滚动条
						$container.css( {
							top : selInputHeight
						});
					}
				} else {
					$container.css( {
						top : selInputHeight
					});
				}
			}
		}
		//没有设置boxHeight
		else {
			//强制向上展开
			if ($select.attr("openDirection") == "top") {
				if ($select.offset().top > $container.height()) {//如果上部能容纳下
					$container.css( {
						top : -$container.height()
					});
				} else {//如果上部容纳不下，向上展开并强制高度，出滚动条
					$container.height($mainCon.offset().top);

					$container.css( {
						top : -$mainCon.offset().top
					});
				}
			}
			//强制向下展开
			else if ($select.attr("openDirection") == "bottom") {
				if (usefulHeight < $container.height()) {//如果底部容纳不下，向下展开并强制高度，出滚动条

					$container.css( {
						top : selInputHeight
					});
					$container.height(usefulHeight);
				} else {//底部能容纳下
					$container.css( {
						top : selInputHeight
					});
				}
			}
			//智能判断方向
			else {
				//获取内容页中slect所在位置距离最底部的高度
				if (usefulHeight < $container.height()) {//如果底部容纳不下
					if ($select.offset().top > $container.height()) {//如果上部能容纳下,向上展开
						$container.css( {
							top : -$container.height()
						});
					} else if (usefulHeight < 100
							&& $select.offset().top > usefulHeight
							&& $select.offset().top > 100) {//如果上部也容纳不下，并且底部不足100,向上展开并强制高度，出滚动条
						$container.height($select.offset().top);

						$container.css( {
							top : -$select.offset().top
						});
					} else {//上面容纳不下，下面大于100，则向下展开，并强制高度，出滚动条

						$container.css( {
							top : selInputHeight
						});
						$container.height(usefulHeight);
					}
				} else {
					$container.css( {
						top : selInputHeight
					});
				}
			}

		}

		//设置最小宽度
		if (!$select.attr("multiListWidth")) {
			if ($container.width() < $input.width()) {
				$container.width($input.width())
			}
		}
	}

	function setHeightSuggestion() {
		//先还原
		$suggestMain.css( {
			"overflowY" : "visible",
			"overflowX" : "visible"
		});
		$suggestMain.width("");
		$suggestMain.height("");
		var usefulHeight = 200;
		usefulHeight = window.document.documentElement.clientHeight
				- ($select.offset().top - $(window).scrollTop()) - 30;

		//var trueWidth;
		//if (!$select.attr("suggestWidth")) {
		//	trueWidth=$suggestMain.width();
		//}
		$suggestMain.css( {
			"overflowY" : "auto",
			"overflowX" : "hidden"
		});
		//if (!$select.attr("suggestWidth")) {
		//	$suggestMain.width(trueWidth);
		//}
		//else{
		//	$suggestMain.width(Number($select.attr("suggestWidth")));
		//}
		if ($select.attr("boxWidth")) {
			$suggestMain.width(Number($select.attr("boxWidth")));
		}

		var boxHeight = 0;
		if ($select.attr("boxHeight")) {
			boxHeight = Number($select.attr("boxHeight"));
		}

		//设置容器高度
		if (boxHeight != 0) {
			$suggestMain.height(boxHeight);

			//强制向上展开
			if ($select.attr("openDirection") == "top") {
				$suggestMain.css( {
					top : -boxHeight
				});
			}
			//强制向下展开
			else if ($select.attr("openDirection") == "bottom") {
				$suggestMain.css( {
					top : selInputHeight
				});
			}
			//智能判断方向
			else {
				if (usefulHeight < boxHeight) {//如果底部容纳不下
					if ($select.offset().top > boxHeight) {//如果上部能容纳下,向上展开
						$suggestMain.css( {
							top : -boxHeight
						});
					} else if (usefulHeight < 100
							&& $select.offset().top > usefulHeight
							&& $select.offset().top > 100) {//如果上部也容纳不下，并且底部不足100,向上展开并强制高度，出滚动条
						$suggestMain.css( {
							top : -boxHeight
						});
					} else {//上面容纳不下，下面大于100，则向下展开，并强制高度，出滚动条
						$suggestMain.css( {
							top : selInputHeight
						});
					}
				} else {
					$suggestMain.css( {
						top : selInputHeight
					});
				}
			}
		}
		//没有设置boxHeight
		else {
			//强制向上展开
			if ($select.attr("openDirection") == "top") {
				if ($select.offset().top > $suggestMain.height()) {//如果上部能容纳下
					$suggestMain.css( {
						top : -$suggestMain.height()
					});
				} else {//如果上部容纳不下，向上展开并强制高度，出滚动条
					$suggestMain.height($mainCon.offset().top);

					$suggestMain.css( {
						top : -$mainCon.offset().top
					});
				}
			}
			//强制向下展开
			else if ($select.attr("openDirection") == "bottom") {
				if (usefulHeight < $suggestMain.height()) {//如果底部容纳不下，向下展开并强制高度，出滚动条

					$suggestMain.css( {
						top : selInputHeight
					});
					$suggestMain.height(usefulHeight);
				} else {//底部能容纳下
					$suggestMain.css( {
						top : selInputHeight
					});
				}
			}
			//智能判断方向
			else {
				//获取内容页中slect所在位置距离最底部的高度
				if (usefulHeight < $suggestMain.height()) {//如果底部容纳不下
					if ($select.offset().top > $suggestMain.height()) {//如果上部能容纳下,向上展开
						$suggestMain.css( {
							top : -$suggestMain.height()
						});
					} else if (usefulHeight < 100
							&& $select.offset().top > usefulHeight
							&& $select.offset().top > 100) {//如果上部也容纳不下，并且底部不足100,向上展开并强制高度，出滚动条
						$suggestMain.height($select.offset().top);

						$suggestMain.css( {
							top : -$select.offset().top
						});
					} else {//上面容纳不下，下面大于100，则向下展开，并强制高度，出滚动条

						$suggestMain.css( {
							top : selInputHeight
						});
						$suggestMain.height(usefulHeight);
					}
				} else {
					$suggestMain.css( {
						top : selInputHeight
					});
				}
			}

		}

		//设置最小宽度
		if (!$select.attr("boxWidth")) {
			if (broswerFlag == "IE6") {
				$suggestMain.width($input.width())
			} else {
				if ($suggestMain.width() < $input.width()) {
					$suggestMain.width($input.width())
				}
			}

		}
	}

	//创建总容器，是一个class=mainCon的div
	function setupMainCon() {
		var $con = $("<div></div>");
		$con.addClass("mainCon");
		return $con;
	}

	//创建弹窗层
	function setupContainer(options) {
		//创建div id为num_container
		var $container = $("<div class='pop_city'><div class='pop_head'></div><ul class='list_label'></ul><div class='pop_city_container'></div></div>");
		$container.attr('id', "pop_city" + selectTree_id + '_container');
		return $container;
	}

	//创建提示层
	function setupSuggestContainer(options) {
		//创建div id为num_container
		var $container = $("<div class='list_city'><div class='list_city_head'></div><div class='list_city_container'></div><div class='page_break'></div></div>");
		$container.attr('id', "suggest_city_" + selectTree_id + '_container');
		return $container;
	}

	//创建文本框
	function setupInput(options) {
		//创建input元素 id为num_input，class为selectbox
		var input = document.createElement("input");
		var $input = $(input);
		$input.attr("id", "suggestion" + selectTree_id + "_input");
		$input.attr("type", "text");
		$input.addClass(options.inputClass);
		if (iconSrc != null) {
			$input.css( {
				"backgroundImage" : "url(" + iconSrc + ")"
			})
		}

		//关闭自动完成
		$input.attr("autocomplete", "off");

		//根据$select设置的disabled属性判断是否禁用，添加inputDisabled样式
		if ($select.attr("disabled") == "disabled"
				|| $select.attr("disabled") == "true"
				|| $select.attr("disabled") == true) {
			$input.attr("disabled", true);
			$input.addClass("suggestion_input_disabled");
		}

		return $input;
	}

	//创建隐藏域
	function setupHidden(options) {
		//创建hidden元素 id为num_input，class为selectbox
		var input = document.createElement("input");
		var $input = $(input);
		$input.attr("type", "hidden");
		if ($select.attr("name") != null) {
			$input.attr("name", $select.attr("name"));
		}
		return $input;
	}

	//选中项后处理
	function setCurrent(str, uid) {
		if (uid == "") {
			$input.css('color', '#aaa');
		} else {
			$input.css('color', '#000');
		}
		if (str == promptText) {
			$select.attr("relText", "");
		} else {
			$select.attr("relText", str);
		}
		$select.attr("relValue", uid);
		$select.attr("value", $select.attr("relText"));

		$hidden.val(uid);
		//为文本框赋值
		$input.val($select.attr("relValue"));

		//设置焦点
		//$select.focus();
		//return true;
	}

	//获取原容器内容
	function createOptions(data, tabData) {

		//alert(popMain)
		//添加标题
		$container.find('.pop_head').text(popTitle);

		if (data[dataRoot]) {
			finalData = data[dataRoot]
		} else {
			finalData = data;
		}

		//如果没有设置标签模式
		if (!tabData) {
			//添加ul
			popContainer.append("<ul id='label_" + selectTree_id
					+ "_container' class='current'></ul>");
			//移除tab区
			labelMain.remove();
			//ul中循环数据添加li选项
			$.each(finalData, function(idx, item) {
				var $li = $("<li><a href='javascript:void(0)'>" + item.key
						+ "</a></li>");
				$li.width(colWidth);
				$li.find("a").attr("rel", item.value);
				$("#label_" + selectTree_id + "_container").append($li);
			});
		} else {
			//如果设置了标签模式
			$.each(finalDataTab, function(idx, item) {
				if (idx == 0) {
					popContainer.append("<ul id='label_" + selectTree_id + idx
							+ "_container' class='current' data-type='"
							+ item.name + "'></ul>");
					labelMain.append("<li><a id='label_" + selectTree_id + idx
							+ "' class='current' href='javascript:void(0)'>"
							+ item.name + "</a></li>");
				} else {
					popContainer.append("<ul style='display:none' id='label_"
							+ selectTree_id + idx + "_container' data-type='"
							+ item.name + "'></ul>");
					labelMain.append("<li><a id='label_" + selectTree_id + idx
							+ "' href='javascript:void(0)'>" + item.name
							+ "</a></li>");
				}
				var index = idx;
				$.each(item["tab"], function(idx, item) {
					var $li_c = $("<li><a href='javascript:void(0)'>"
							+ item.key + "</a></li>");
					$li_c.width(colWidth);
					$li_c.find("a").attr("rel", item.value);
					$("#label_" + selectTree_id + index + "_container").append(
							$li_c);
				})
			})
		}

	}

	//隐藏层
	function hideMe() {
		$container.hide();
		$select.trigger("popClose");
	}
	function showMe() {
		if (showMultiList) {
			depth++;
			$select.css( {
				"zIndex" : depth
			});
			$container.show();
			$select.trigger("popOpen");
		}

	}
	function hideSuggest() {
		$suggestMain.hide();
		$select.trigger("suggestClose");
	}
	function showSuggest() {
		depth++;
		$select.css( {
			"zIndex" : depth
		});
		$suggestMain.show();
		$select.trigger("suggestOpen");
	}

	function getCurrentSelected() {
		return $select.val();
	}
	function getCurrentValue() {
		return $input.val();
	}

};

function getPosition(value, array) {//获得数组值的索引
	for ( var i = 0; i < array.length; i++) {
		if (value == array[i]) {
			return i;
			break;
		}
	}
}String.prototype.trim = function()
{
    // 用正则表达式将前后空格，用空字符串替代。
    return this.replace(/(^\s*)|(\s*$)/g, "");
} 
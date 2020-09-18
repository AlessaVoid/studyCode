/*edited by fukai*/
 var validationPrePath="../../";
if($("#skin").attr("prePath")!=null){
	validationPrePath=$("#skin").attr("prePath");
}
(function($) {
	var yzId=1;
	$.fn.validationEngine = function(settings) {
		
	if($.validationEngineLanguage){				
		allRules = $.validationEngineLanguage.allRules;
	}
 	settings = jQuery.extend({
		allrules:allRules,
		validationEventTriggers:"focusout",					
		inlineValidation: true,	
		returnIsValid:false,
		liveEvent:false,
		unbindEngine:true,
		ajaxSubmit: false,
		scroll:true,
		promptPosition: "bottomRight",	
		success : true,
		beforeSuccess :  function() {},
		failure : function() {},
		
		showArray:false, 
		showOnMouseOver:true, 
		errorClass:'error-field' 
		
	}, settings);	
	$.validationEngine.settings = settings;
	$.validationEngine.ajaxValidArray = new Array();	// ARRAY FOR AJAX: VALIDATION MEMORY 
	//初始时假设ajax验证通过
	$.validationEngine.ajaxValid=true;
	if(settings.inlineValidation == true){ 	
		if(!settings.returnIsValid){					
			allowReturnIsvalid = false;
			if(settings.liveEvent){						
				$(this).find("[class*=validate][type!=checkbox][type!=radio]").live(settings.validationEventTriggers, function(caller){ _inlinEvent(this);})
				$(this).find("[class*=validate][type=checkbox]").live("click", function(caller){ _inlinEvent(this); })
				$(this).find("[class*=validate][type=radio]").live("click", function(caller){ _inlinEvent(this); })
				$(this).find("select[class*=validate]").live("focus", function(caller){_inlinEvent(this); })
				//$(this).find("select[class*=validate]").live("blur", function(caller){_inlinEvent(this); })
				$(this).find("input:text[class*=date]").live("blur", function(caller){_inlinEvent(this);})
				$(this).find("input:file").live("blur", function(caller){_inlinEvent(this);})
				$(this).find("[class*=selectTree]").live("focus", function(caller){_inlinEvent(this);})
				$(this).find("[class*=filter]").live("click", function(caller){_inlinEvent(this);})
				$(this).find("[class*=lister]").live("itemClick", function(caller){_inlinEvent(this);})
				$(this).find("[class*=listerTree]").live("itemClick", function(caller){_inlinEvent(this);})
				$(this).find("[class*=selectCustom]").live("boxClose", function(caller){_inlinEvent(this);})
				$(this).find("[class*=suggestion]").live("validate", function(caller){_inlinEvent(this);})
				$(this).find("[class*=suggestion]").live("validate2", function(caller){_inlinEvent(this);})
				
			}else{
				$(this).find("[class*=validate]").not("[type=checkbox]").not("[type=radio]").bind(settings.validationEventTriggers, function(caller){ _inlinEvent(this); })
				$(this).find("[class*=validate][type=checkbox]").bind("click", function(caller){ _inlinEvent(this); })
				$(this).find("[class*=validate][type=radio]").bind("click", function(caller){ _inlinEvent(this); })
				$(this).find("select[class*=validate]").bind("focus", function(caller){ _inlinEvent(this); })
				//$(this).find("select[class*=validate]").bind("blur", function(caller){ _inlinEvent(this); })
				$(this).find("input:text[class*=date]").bind("blur", function(caller){_inlinEvent(this); })
				$(this).find("input:file").bind("blur", function(caller){_inlinEvent(this); })
				$(this).find("[class*=selectTree]").bind("focus", function(caller){_inlinEvent(this);})
				$(this).find("[class*=filter]").bind("click", function(caller){_inlinEvent(this);})
				$(this).find("[class*=lister]").bind("itemClick", function(caller){_inlinEvent(this);})
				$(this).find("[class*=listerTree]").bind("itemClick", function(caller){_inlinEvent(this);})
				$(this).find("[class*=selectCustom]").bind("boxClose", function(caller){_inlinEvent(this);})
				$(this).find("[class*=suggestion]").bind("validate", function(caller){_inlinEvent(this);})
				$(this).find("[class*=suggestion]").bind("validate2", function(caller){_inlinEvent(this);})
			}
			firstvalid = false;
			
			$(this).find("[class*=validate]").each(function(){
				$(this).attr("yzId",yzId)
				yzId++
			})
			
		}
			function _inlinEvent(caller){
				$.validationEngine.settings = settings;
				if($.validationEngine.intercept == false || !$.validationEngine.intercept){		// STOP INLINE VALIDATION THIS TIME ONLY
					//标识此时不是提交验证方式
					$.validationEngine.onSubmitValid=false;
					//触发验证
					$.validationEngine.loadValidation(caller); 
				}else{
					$.validationEngine.intercept = false;
				}
			}
	}
	//控制返回验证是否通过
	if (settings.returnIsValid){		
		//验证未通过
		if ($.validationEngine.submitValidation(this,settings)){
			return false;
		}else{
			//验证通过
			return true;
		}
	}
	//提交时触发
	$(this).bind("submit", function(caller){   
		//标识此时是提交验证方式
		$.validationEngine.onSubmitValid = true;
		$.validationEngine.settings = settings;
		//验证通过
		if($.validationEngine.submitValidation(this,settings) == false){
			if($.validationEngine.submitForm(this,settings) == true) {
				return false;
			}
		}
		//验证不通过
		else{
			if($(this).attr("failAlert")!=null){
				try {
					top.Dialog.alert($(this).attr("failAlert"));
				}
				catch(e){
					alert($(this).attr("failAlert"));
				}
			}
			settings.failure && settings.failure(); 
			return false;
		}		
	})
	$(".formError").live("click",function(){	 // REMOVE BOX ON CLICK
		$(this).fadeOut(150,function(){
			$(this).remove();
		}) 
	})
};	
$.validationEngine = {
	defaultSetting : function(caller) {		// NOT GENERALLY USED, NEEDED FOR THE API, DO NOT TOUCH
		if($.validationEngineLanguage){				
			allRules = $.validationEngineLanguage.allRules;
		}
		settings = {
			allrules:allRules,
			validationEventTriggers:"blur",					
			inlineValidation: true,	
			returnIsValid:false,
			scroll:true,
			unbindEngine:true,
			ajaxSubmit: false,
			promptPosition: "bottomRight",	// OPENNING BOX POSITION, IMPLEMENTED: topLeft, topRight, bottomLeft, centerRight, bottomRight
			success : false,
			failure : function() {}
		}	
		$.validationEngine.settings = settings;
	},
	//执行验证处理
	loadValidation : function(caller,isSubmit) {	
		try {
			
			if(!$.validationEngine.settings){
				$.validationEngine.defaultSetting()
			}
			rulesParsing = $(caller).attr('class');
			rulesRegExp = /\[(.*)\]/;
			getRules = rulesRegExp.exec(rulesParsing);
			str = getRules[1];
			pattern = /\[|,|\]/;
			result= str.split(pattern);	
			var validateCalll = $.validationEngine.validateCall(caller,result,isSubmit)
			return validateCalll;
		}
		catch(e){}
	},
	validateCall : function(caller,rules,isSubmit) {	
		var promptText =""	

		caller = caller;
		//当前验证的元素是否是ajax验证
		ajaxValidate = false;
		var callerName = $(caller).attr("name");
		$.validationEngine.isError = false;
		callerType = $(caller).attr("type");

		for (i=0; i<rules.length;i++){
			switch (rules[i]){
			case "optional": 
				if(!$(caller).val()){
					$.validationEngine.closePrompt(caller);
					return $.validationEngine.isError;
				}
			break;
			case "required": 
				_required(caller,rules);
			break;
			case "custom": 
				 _customRegex(caller,rules,i);
			break;
			case "exemptString": 
				 _exemptString(caller,rules,i);
			break;
			case "ajax": 
				if(typeof(isSubmit)=='undefined'||!isSubmit){
					_ajax(caller,rules,i);	
				}
			break;
			case "length": 
				 _length(caller,rules,i);
			break;
			case "maxCheckbox": 
				_maxCheckbox(caller,rules,i);
			 	groupname = $(caller).attr("name");
			 	caller = $("input[name='"+groupname+"']");
			break;
			case "minCheckbox": 
				_minCheckbox(caller,rules,i);
				groupname = $(caller).attr("name");
			 	caller = $("input[name='"+groupname+"']");
			break;
			case "confirm": 
				 _confirm(caller,rules,i);
			break;
			case "funcCall": 
		     	_funcCall(caller,rules,i);
			break;
			case "functionCall": 
		     	_functionCall(caller,rules,i);
			break;
			case "passwordStrength": 
		     	_passwordStrength(caller,rules,i);
			break;
			default :;
			};
		};
		radioHack();
		
		if($(caller).attr("passstrength")=="true"){
			$.validationEngine.buildPrompt_strength(caller,promptText,"error");
		}
		else{
			//如果该项验证未通过
			if ($.validationEngine.isError == true){
				//如果没有提示，则创建；如果有，则更新
				if($.validationEngine.settings.showOnMouseOver){
					linkTofield = $.validationEngine.linkTofield(caller);
					($("div."+linkTofield).size() ==0) ? $.validationEngine.buildPrompt(caller,promptText,"error")	: $.validationEngine.updatePromptText(caller,promptText);
				}
				else{
					$.validationEngine.buildPrompt(caller,promptText,"error");
				}
				
			}
			//通过则关闭提示
			else{ 
				if($(caller).attr("ajaxValidate")=="true"){}
				else{
					$.validationEngine.closePrompt(caller);
				}
				
			}
		}
		
			
				
		/* UNFORTUNATE RADIO AND CHECKBOX GROUP HACKS */
		/* As my validation is looping input with id's we need a hack for my validation to understand to group these inputs */
		function radioHack(){
	      if($("input[name='"+callerName+"']").size()> 1 && (callerType == "radio" || callerType == "checkbox")) {        // Hack for radio/checkbox group button, the validation go the first radio/checkbox of the group
	          caller = $("input[name='"+callerName+"'][type!=hidden]:first");     
	          $.validationEngine.showTriangle = false;
	      }      
	    }
		// 必填处理
		function _required(caller,rules){   
			//树形下拉框处理
			if($(caller).hasClass("selectTree")){
				if($(caller).attr("relValue")==null||$(caller).attr("relValue")==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			//自定义下拉框处理
			else if($(caller).hasClass("selectCustom")){
				if($(caller).attr("relValue")==null||$(caller).attr("relValue")==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			//自动完成框处理
			else if($(caller).hasClass("suggestion")){
				//alert($(caller).attr("relText"))
				if($(caller).attr("relText")==null||$(caller).attr("relText")==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			//上传控件
			else if($(caller).attr("type")=="file"){
				if($(caller).hasClass("fileComponent")){
					//alert($(caller).attr("relText"))
					var $fileInput=$(caller).parent().find("input[type=text]");
					if($fileInput.val()==""){
						$.validationEngine.isError = true;
						promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
					}
				}
				else{
					if($(caller).val()==""){
						$.validationEngine.isError = true;
						promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
					}
				}
			}
			
			//单选下拉框处理
			else if($(caller).is('select')){
				if($(caller).val()==null||$(caller).val()==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			//过滤器处理
			else if($(caller).hasClass("filter")){
				if($(caller).attr("relValue")==null||$(caller).attr("relValue")==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			//双选器处理
			else if($(caller).hasClass("lister")){
				if($(caller).attr("relValue")==null||$(caller).attr("relValue")==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			//树形双选器处理
			else if($(caller).hasClass("listerTree")){
				if($(caller).attr("relValue")==null||$(caller).attr("relValue")==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			//文本域处理
			else if($(caller).is('textarea')){
				if($(caller).val().trim()==null||$(caller).val().trim()==""){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
				}
			}
			else{
				callerType = $(caller).attr("type");
				if (callerType == "text" || callerType == "password" || callerType == "textarea"){
									
					
					if(!$(caller).val().trim()){
						$.validationEngine.isError = true;
						promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
					}	
				}	
				if (callerType == "radio" || callerType == "checkbox" ){
					callerName = $(caller).attr("name");
			
					if($("input[name='"+callerName+"']:checked").size() == 0) {
						$.validationEngine.isError = true;
						if($("input[name='"+callerName+"']").size() ==1) {
							promptText = $.validationEngine.settings.allrules[rules[i]].alertTextCheckboxe+"<br />"; 
						}else{
							 promptText = $.validationEngine.settings.allrules[rules[i]].alertTextCheckboxMultiple+"<br />";
						}	
					}
				}	
				if (callerType == "select-one") { 	
					if(!$(caller).val()) {
						if($(caller).attr("disabled")==true){
							
						}
						else{
							$.validationEngine.isError = true;
							promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
						}
					}
				}
				if (callerType == "select-multiple") { 
					if(!$(caller).find("option:selected").val()) {
						$.validationEngine.isError = true;
						promptText = $.validationEngine.settings.allrules[rules[i]].alertText+"<br />";
					}
				}
			}
			
		}
		//正则验证
		function _customRegex(caller,rules,position){		 
				customRule = rules[position+1];
				pattern = eval($.validationEngine.settings.allrules[customRule].regex);
				if($(caller).attr('value')!=""){
					//执行正则
					if(!pattern.test(jQuery.trim($(caller).attr('value')))){
						$.validationEngine.isError = true;
						promptText = $.validationEngine.settings.allrules[customRule].alertText+"<br />";
					}
				}
			
			
		}
		//过滤不允许输入的内容
		function _exemptString(caller,rules,position){		
			customRule = rules[position+1];
			var strArr = customRule.split("|");
			var txt = strArr[1];
			customString = strArr[0];
			if ($(caller).attr('value') != "") {
				if(customString == $(caller).attr('value')){
					$.validationEngine.isError = true;
					promptText = txt+"<br />";
				}
			}
		}
		//添加自定义函数
		function _functionCall(caller,rules,position){		 
			if ($.validationEngine.isError == false) {
				var strArr = rules[position + 1].split("|");
				var fun = window[strArr[0]];
				var txt = strArr[1];
				if ($(caller).attr('value') != "") {
					if (typeof(fun) === 'function') {
						var fun_result = fun();
						$.validationEngine.isError = fun_result;
						promptText = txt + "<br />";
					}
				}
			}
			//alert($.validationEngine.isError)
		}
		//密码强度检测
		function _passwordStrength(caller,rules,position){		 
			if ($.validationEngine.isError == false) {
				if ($(caller).attr('value') != "") {
					$(caller).attr("passStrength","true");
					customRule = rules[position];
					var level = $(caller).password_strength2($(caller).attr('value'));
					$(caller).data("passStrength",level);
					promptText = $.validationEngine.settings.allrules[customRule][level-1]+"<br />";
				}
			}
			else{
				$(caller).attr("passStrength","false");
			}
		}
		function _funcCall(caller,rules,position){  		
			customRule = rules[position+1];
			funce = $.validationEngine.settings.allrules[customRule].nname;
			
			var fn = window[funce];
			if (typeof(fn) === 'function'){
				var fn_result = fn();
				$.validationEngine.isError = fn_result;
				promptText = $.validationEngine.settings.allrules[customRule].alertText+"<br />";
			}
		}
		function _ajax(caller,rules,position){
			ajaxValidate=true;
			$(caller).attr("ajaxValidate","true");
			//if($.validationEngine.ajaxValid){
				$(caller).parents("form").eq(0).attr("ajaxIng","true");
				customAjaxRule = rules[position+1];
				var strArr = customAjaxRule.split("|");
				postfile = strArr[0];
				var txt = strArr[1];
				
				//postfile = validationPrePath+$.validationEngine.settings.allrules[customAjaxRule].file;
				fieldValue = $(caller).val();
				
				ajaxCaller = caller;
				fieldId = $(caller).attr("yzId");
				
				ajaxisError = $.validationEngine.isError;
				
				
				//非ajax验证通过后再进行ajax验证
				if(!ajaxisError){
					$.ajax({ 
					url: postfile, 
					dataType:"json",
					data:{
					 validateValue : fieldValue
					},
					error:function(){
						alert(uncompile(quiLanguage.validation.ajaxErrow))
					},
					//添加正在加载小图标
					beforeSend: function(){		   			
				   		if($(caller).parent().find("input:button[class='loading']").length==0){
							var loadingBtn=$('<input type="button" class="loading"/>');
							$(caller).after(loadingBtn);
							if(!$.validationEngine.settings.showOnMouseOver){
								loadingBtn.css({
									"paddingLeft":"15px",
									"paddingTop":"20px",
									"float":"left",
									"backgroundPosition":"100% 100%"
								})
							}
						}
						else{
							$(caller).parent().find("input:button[class='loading']").show();
						}
			  	 	},
					success: function(data){
						//setTimeout(function(){
							$(caller).parent().find("input:button[class='loading']").hide();
							var valid=data.validateResult.valid;
						   	if(valid == "false"||valid==false){
								//ajax验证未通过
								$.validationEngine.ajaxValid = false;
								$.validationEngine.isError=true;
								promptText = txt+"<br />";
								//更新提示
								//$.validationEngine.updatePromptText(ajaxCaller,promptText,"",true);
								
								$.validationEngine.buildPrompt(ajaxCaller,promptText,"load");
								$(caller).parents("form").eq(0).attr("ajaxSucess","false");
						 	}else{	 
								//ajax验证通过
								$.validationEngine.ajaxValid = true; 	
								$.validationEngine.isError=false;
						 		$.validationEngine.closePrompt(ajaxCaller);
								$(caller).parents("form").eq(0).attr("ajaxSucess","true");
			 			 	}
						
						$(caller).parents("form").eq(0).attr("ajaxIng","false");
						//},5000)
				    }
				});
				}
				else{
					$(caller).parents("form").eq(0).attr("ajaxIng","false");
				}
				
			//}
		}
		//密码确认
		function _confirm(caller,rules,position){		 
			confirmField = rules[position+1];
			
			if($(caller).attr('value') != $("#"+confirmField).attr('value')){
				$.validationEngine.isError = true;
				promptText = $.validationEngine.settings.allrules["confirm"].alertText+"<br />";
			}
		}
		//限制长度
		function _length(caller,rules,position){    	
			if ($(caller).attr('value') != "") {
				startLength = eval(rules[position+1]);
				endLength = eval(rules[position+2]);
				feildLength = $(caller).attr('value').length;
	
				if(feildLength<startLength || feildLength>endLength){
					$.validationEngine.isError = true;
					promptText = $.validationEngine.settings.allrules["length"].alertText+startLength+$.validationEngine.settings.allrules["length"].alertText2+endLength+$.validationEngine.settings.allrules["length"].alertText3+"<br />"
				}
			}
		}
		//控制checkbox最大选择个数
		function _maxCheckbox(caller,rules,position){  	
		
			nbCheck = eval(rules[position+1]);
			groupname = $(caller).attr("name");
			groupSize = $("input[name='"+groupname+"']:checked").size();
			if(groupSize > nbCheck){	
				$.validationEngine.showTriangle = false;
				$.validationEngine.isError = true;
				promptText = $.validationEngine.settings.allrules["maxCheckbox"].alertText+"<br />";
			}
		}
		//控制checkbox最小选择个数
		function _minCheckbox(caller,rules,position){  	 
		
			nbCheck = eval(rules[position+1]);
			groupname = $(caller).attr("name");
			groupSize = $("input[name='"+groupname+"']:checked").size();
			if(groupSize < nbCheck){	
			
				$.validationEngine.isError = true;
				$.validationEngine.showTriangle = false;
				promptText = $.validationEngine.settings.allrules["minCheckbox"].alertText+" "+nbCheck+" "+$.validationEngine.settings.allrules["minCheckbox"].alertText2+"<br />";
			}
		}
		return($.validationEngine.isError) ? $.validationEngine.isError : false;
	},
	submitForm : function(caller){
		//执行设置中提交成功的方法
		if(!$.validationEngine.settings.beforeSuccess()){
			if ($.validationEngine.settings.success){	// AJAX SUCCESS, STOP THE LOCATION UPDATE
				if($.validationEngine.settings.unbindEngine){ $(caller).unbind("submit") }
				try {
					$.validationEngine.settings.success && $.validationEngine.settings.success(); 
					return true;
				}
				catch(e){}
			}
		}else{
			return true;
		} 
		return false;
	},
	//当鼠标over 及out时的提示显示方法
	showTip:function(event){
		event.data.stop();
		event.data.fadeTo(100,1);
		event.data.css({
			top:event.pageY+10,
			left:event.pageX-20
		})
		
	},
	hideTip:function(event){
		event.data.stop(); 
		event.data.fadeTo(100,0,function(){$(this).hide()});
	},
	// 出现提示框
	buildPrompt : function(caller,promptText,type,ajaxed) {			
		if(!$.validationEngine.settings){
			$.validationEngine.defaultSetting()
		}
		//构造提示框
		var divFormError;
		
		//表单元素出现红框
		if($.validationEngine.settings.showOnMouseOver){
			linkTofield = $.validationEngine.linkTofield(caller);
			divFormError= document.createElement('div');
			deleteItself = "." + $(caller).attr("yzId") + "formError"
			//先移除
			if($(deleteItself)[0]){
				$(deleteItself).get(0).validateField.unbind('mouseover',$.validationEngine.showTip).unbind('mouseout',$.validationEngine.hideTip);
				$(deleteItself).stop();
				$(deleteItself).remove();
			}
			
			var formErrorContent = document.createElement('div');
			var formErrorBottom = document.createElement('div');
			
			$(divFormError).addClass("formError")
			
			$(divFormError).addClass(linkTofield);
			$(formErrorContent).addClass("formErrorContent");
			$(formErrorBottom).addClass("formErrorBottom");
			
			$("body").append(divFormError);
			$(divFormError).append(formErrorContent);
			$(divFormError).append(formErrorBottom);
				
			//添加文字
			$(formErrorContent).html(promptText)
		
			callerTopPosition = $(caller).offset().top;
			callerleftPosition = $(caller).offset().left;
			callerWidth =  $(caller).width();
			inputHeight = $(divFormError).height();
		
			//定位
			if($.validationEngine.settings.promptPosition == "topRight"){callerleftPosition +=  callerWidth -30; callerTopPosition += -inputHeight -10; }
			if($.validationEngine.settings.promptPosition == "topLeft"){ callerTopPosition += -inputHeight -10; }
			
			if($.validationEngine.settings.promptPosition == "centerRight"){ callerleftPosition +=  callerWidth +13; }
			
			if($.validationEngine.settings.promptPosition == "bottomLeft"){
				callerHeight =  $(caller).height();
				callerleftPosition = callerleftPosition;
				callerTopPosition = callerTopPosition + callerHeight + 15;
			}
			if($.validationEngine.settings.promptPosition == "bottomRight"){
				callerHeight =  $(caller).height();
				callerleftPosition +=  callerWidth -30;
				callerTopPosition +=  callerHeight + 15;
			}
			$(divFormError).css({
				//top:callerTopPosition,
				//left:callerleftPosition,
				opacity:$.validationEngine.settings.showOnMouseOver?1:0,
				display:$.validationEngine.settings.showOnMouseOver?'none':''
			})
			
			if($(caller).is(':checkbox,:radio')){
				$(divFormError).get(0).validateField=$(caller).parent();
			}
			else if($(caller).is('select')){
				
			//$(divFormError).get(0).validateField=$(caller).parent().find("input:text")
			if($(caller).parent().find("input:text").length>0){
				$(divFormError).get(0).validateField=$(caller).parent().find("input:text");
			}
			else{
				$(divFormError).get(0).validateField=$(caller).parent();
			}
			}
			else if($(caller).hasClass("selectTree")){
				//$(divFormError).get(0).validateField=$(caller).parent().find("input:text")
				if($(caller).find("input:text").length>0){
					$(divFormError).get(0).validateField=$(caller).find("input:text");
				}
				else{
					$(divFormError).get(0).validateField=$(caller);
				}
			}
			else if($(caller).hasClass("selectCustom")){
				if($(caller).find("input:text").length>0){
					$(divFormError).get(0).validateField=$(caller).find("input:text");
				}
				else{
					$(divFormError).get(0).validateField=$(caller);
				}
			}
			else if($(caller).hasClass("suggestion")){
				if($(caller).find("input:text").length>0){
					$(divFormError).get(0).validateField=$(caller).find("input:text");
				}
				else{
					$(divFormError).get(0).validateField=$(caller);
				}
			}
			else if($(caller).hasClass("fileComponent")){
				if($(caller).parent().find("input:text").length>0){
					$(divFormError).get(0).validateField=$(caller).parent().find("input:text");
					var $input=$(caller).parent();
					$input.bind('mouseover',$(divFormError),$.validationEngine.showTip).bind('mouseout',$(divFormError),$.validationEngine.hideTip);	
				}
				else{
					$(divFormError).get(0).validateField=$(caller);
				}
				
			}
			else{
				$(divFormError).get(0).validateField=$(caller);
			}
			
			$(divFormError).get(0).validateField.addClass($.validationEngine.settings.errorClass);
			$(divFormError).get(0).validateField.attr("access","false");
			$(divFormError).get(0).validateField.bind('mouseover',$(divFormError),$.validationEngine.showTip).bind('mouseout',$(divFormError),$.validationEngine.hideTip);	
		}else{
			//alert(promptText)
			divFormError=$.validationEngine.linkTofield2(caller);
			//添加文字
			promptText=promptText.replace("*","");
			divFormError.html(promptText);
			divFormError.removeClass("validation_right");
			divFormError.removeClass("validation_warn");
			divFormError.addClass("validation_wrong");
		}
		
	},
	// 出现提示框
	buildPrompt_strength : function(caller,promptText,type,ajaxed) {			
		if(!$.validationEngine.settings){
			$.validationEngine.defaultSetting()
		}
		//构造提示框
		var divFormError;
		
		divFormError=$.validationEngine.linkTofield2(caller);
		//添加文字
		promptText=promptText.replace("*","");
		divFormError.html(promptText);
		if($(caller).data("passStrength")>3){
			divFormError.removeClass("validation_warn");
			divFormError.removeClass("validation_wrong");
			divFormError.addClass("validation_right");
		}
		else{
			divFormError.removeClass("validation_right");
			divFormError.removeClass("validation_wrong");
			divFormError.addClass("validation_warn");
		}
		
		
	},
	//更新提示文本
	updatePromptText : function(caller,promptText,type,ajaxed) {
		linkTofield = $.validationEngine.linkTofield(caller);
		 //查找表单元素对应的提示框
		var updateThisPrompt =  "."+linkTofield;
		$(updateThisPrompt).find(".formErrorContent").html(promptText);
		callerTopPosition  = $(caller).offset().top;
		inputHeight = $(updateThisPrompt).height();
		
		if($.validationEngine.settings.promptPosition == "bottomLeft" || $.validationEngine.settings.promptPosition == "bottomRight"){
			callerHeight =  $(caller).height();
			callerTopPosition =  callerTopPosition + callerHeight + 15;
		}
		if($.validationEngine.settings.promptPosition == "centerRight"){  callerleftPosition +=  callerWidth +13;}
		if($.validationEngine.settings.promptPosition == "topLeft" || $.validationEngine.settings.promptPosition == "topRight"){
			callerTopPosition = callerTopPosition  -inputHeight -10;
		}
		//$(updateThisPrompt).animate({ top:callerTopPosition });
	},
	linkTofield : function(caller){
		if($.validationEngine.settings.showOnMouseOver){
			linkTofield = $(caller).attr("yzId") + "formError";
			linkTofield = linkTofield.replace(/\[/g,""); 
			linkTofield = linkTofield.replace(/\]/g,"");
			return linkTofield;
		}
	},
	linkTofield2 : function(caller){
		if(!$.validationEngine.settings.showOnMouseOver){
			var $target;
			if($(caller).hasClass("fileComponent")){
				$target=$(caller).parent().parent();
			}
			else if($(caller).is(':checkbox,:radio')){
				$target=$(caller).parent();
			}
			else{
				$target=$(caller);
			}
			var $info=$target.nextUntil(".validation_info").next().eq(-1);
			if($info.length==0){
				$info=$target.next().find(".validation_info").eq(0);
				if($info.length==0){
					$info=$target.parent().next().find(".validation_info").eq(0);
					if($info.length==0){
						$info=$target.parent().nextUntil(".validation_info").next().eq(-1);
					}
				}
			}
			return $info;
		}
	},
	//关闭提示
	closePrompt : function(caller,outside) {	
		if(!$.validationEngine.settings){
			$.validationEngine.defaultSetting()
		}
		if(typeof(ajaxValidate)=='undefined'){ajaxValidate = false}
		
		//if(!ajaxValidate){
			
			
			if($.validationEngine.settings.showOnMouseOver){
				linkTofield = $.validationEngine.linkTofield(caller);
				closingPrompt = "."+linkTofield;
				if($.validationEngine.settings.showOnMouseOver){
					if($(closingPrompt).get(0)){
						$(closingPrompt).get(0).validateField.removeClass($.validationEngine.settings.errorClass).unbind('mouseover',$.validationEngine.showTip).unbind('mouseout',$.validationEngine.hideTip);
						$(closingPrompt).get(0).validateField.attr("access","true");
					}
					$(closingPrompt).remove();
				}else{
					$(closingPrompt).fadeTo("fast",0,function(){
						$(closingPrompt).remove();
					});
				}
			}
			else{
				closingPrompt=$.validationEngine.linkTofield2(caller);
				closingPrompt.html("");
				closingPrompt.addClass("validation_right");
				closingPrompt.removeClass("validation_warn");
				closingPrompt.removeClass("validation_wrong");
			}
			
		//}
	},
	debug : function(error) {
		if(!$("#debugMode")[0]){
			$("body").append("<div id='debugMode'><div class='debugError'><strong>这是调试模式，来帮你解决设置的问题。</strong></div></div>");
		}
		$(".debugError").append("<div class='debugerror'>"+error+"</div>");
	},			
	submitValidation : function(caller) {
		var stopForm = false;
		//ajaxValid设置为true
		//$.validationEngine.ajaxValid = true;
		$(caller).find(".formError").remove();
		
		//这里caller代表from标签
		//正在ajax验证时不进行验证
		if($(caller).attr("ajaxIng")=="true"||$(caller).attr("ajaxIng")==true){
			try{
				top.Dialog.alert("正在进行ajax验证中，请稍后提交表单!");
			}
			catch(e){
				alert(uncompile(quiLanguage.validation.ajaxIng));
			}
			return true;
		}
		
		var toValidateSize = $(caller).find("[class*=validate]").size();
		
		//验证未通过时stopForm为true，验证通过时为fasle
		//提交时遍历表单元素，逐个验证
		$(caller).find("[class*=validate]").each(function(){
			linkTofield = $.validationEngine.linkTofield(this);
			if(!$("."+linkTofield).hasClass("ajaxed")){	// DO NOT UPDATE ALREADY AJAXED FIELDS (only happen if no normal errors, don't worry)
				var validationPass = $.validationEngine.loadValidation(this,true);
				return(validationPass) ? stopForm = true : "";					
			};
		});
		
		ajaxErrorLength = $.validationEngine.ajaxValidArray.length;		// LOOK IF SOME AJAX IS NOT VALIDATE
		/*
for(x=0;x<ajaxErrorLength;x++){
	 		//如果有一个未通过，则ajaxValid为false
			if($.validationEngine.ajaxValidArray[x][1] == false){
	 			$.validationEngine.ajaxValid = false;
 			}
 		}
*/
		
		//ajax验证未通过返回true
		if($(caller).attr("ajaxSucess")=="false"||$(caller).attr("ajaxSucess")==false){
			if($(caller).attr("failAlert")!=null){
				try {
					top.Dialog.alert($(caller).attr("failAlert"));
				}
				catch(e){
					alert($(caller).attr("failAlert"));
				}
			}
			return true;
		}
		else{
			//验证未通过返回true
			if(stopForm || !$.validationEngine.ajaxValid){		// GET IF THERE IS AN ERROR OR NOT FROM THIS VALIDATION FUNCTIONS
				return true;
			}else{
				return false;
			}
		}
		
	}
}
})(jQuery);

$(document).ready(function() {
	$("form").each(function(){
		if($(this).attr("showOnMouseOver")=="false"||$(this).attr("showOnMouseOver")==false){
			$(this).validationEngine({showOnMouseOver:false})
		}
		else{
			$(this).validationEngine()
		}
	})
	
});



function fmoney(obj, bit) {
	//将金额转换至money格式，增加千分符【，】
	temp = obj.value;
	if (obj.value != "" && !isNaN(obj.value)) {
		var money = obj.value;
		money = (zh(money, bit));
		obj.value = money;
	} else if (obj.value == "") {
		obj.value = "";
	} else {
		obj.value = (zh("0", bit));
	}

	if (obj.value == "") {
		obj.value = "0.00";
	}
}
function rmoney(s) {
	//将money格式转换至字符类型,去掉千分符如果值为0或者非法  值为空
	if (s.value != "" && s.value != " " && s.value != NaN) {
		var index = parseFloat(s.value.replace(/[^\d\.-]/g, ""));
		if (!isNaN(index)) {
			s.value = index;
		} else {
			s.value = '0.00';
		}
		if (s.value == '0' || s.value == '0.00') {
			s.value = "0";
		}
	}
}

function rmoney1(s) {
		//将money格式转换至字符类型,去掉千分符如果值非法 则转换成0
	if (s.value != "" && s.value != " " && s.value != NaN) {
		var index = parseFloat(s.value.replace(/[^\d\.-]/g, ""));
		if (!isNaN(index)) {
			s.value = index;
		} else {
			s.value = '0.00';
		}
		if (s.value == '0' || s.value == '0.00') {
			s.value = "";
		}
	}
}
function rmoneyStr(s) {
	if (s != "" && s != " " && s != NaN) {
		var index = parseFloat(s.replace(/[^\d\.-]/g, ""));
		if (!isNaN(index)) {
			return index;
		} else {
			return 0;
		}
	}
}
function zh(s, n) {
	var f = false;
	if (s < 0) {
		f = true;
	}
	if (f) {
		s = s * -1;
	}
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for ( var i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	if (f) {
		return "-" + (t.split("").reverse().join("") + "." + r);
	} else {
		return (t.split("").reverse().join("") + "." + r);
	}
}

function rallmoney() {
	$("input[class*='money']").each(function() {
		rmoney(this);
	});
}

function fallmoney() {
	$("input[class*='money']").css("text-align", "right");

	$("input[class*='money']").each(function() {
		var bit = getBit(this);
		fmoney(this,bit);
	});

	$("input[class*='money']").bind("blur", function() {
		var bit = getBit(this);
		fmoney(this,bit);
	}).bind("click", function() {
  		rmoney1(this);
  		var e = event.srcElement; 
     	var r = e.createTextRange(); 
     r.moveStart('character',e.value.length); 
     r.collapse(true); 
     r.select();
	});
}

function getBit(obj){
	var bit = 2;
	var array = $(obj).attr("class").split(" ");
	$.each( array, function(index, value){
		var begin = value.indexOf("-") + 1;
		if(value.indexOf("money")==0 && begin != 0){
			bit = this.substring(begin,value.length);
			}
		});
	return bit;
}

$(function() {
	fallmoney();
});
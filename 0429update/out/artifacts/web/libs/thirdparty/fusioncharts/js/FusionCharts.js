if (typeof infosoftglobal == "undefined") {
	var infosoftglobal = new Object()
}
if (typeof infosoftglobal.FusionChartsUtil == "undefined") {
	infosoftglobal.FusionChartsUtil = new Object()
}
infosoftglobal.FusionCharts = function(d, a, k, g, m, e, i, l, b, f, j) {
	if (!document.getElementById) {
		return
	}
	this.initialDataSet = false;
	this.params = new Object();
	this.variables = new Object();
	this.attributes = new Array();
	if (d) {
		this.setAttribute("swf", d)
	}
	if (a) {
		this.setAttribute("id", a)
	}
	if (k) {
		this.setAttribute("width", k)
	}
	if (g) {
		this.setAttribute("height", g)
	}
	this.setChartMessages();
	if (i) {
		this.addParam("bgcolor", i)
	}
	this.addParam("quality", "high");
	this.addParam("allowScriptAccess", "always");
	this.addVariable("chartWidth", k);
	this.addVariable("chartHeight", g);
	m = m ? m : 0;
	this.addVariable("debugMode", m);
	this.addVariable("DOMId", a);
	e = e ? e : 0;
	this.addVariable("registerWithJS", e);
	l = l ? l : "noScale";
	this.addVariable("scaleMode", l);
	b = b ? b : "EN";
	this.addVariable("lang", b);
	this.detectFlashVersion = f ? f : 1;
	this.autoInstallRedirect = j ? j : 1;
	this.installedVer = infosoftglobal.FusionChartsUtil.getPlayerVersion();
	if (!window.opera && document.all && this.installedVer.major > 7) {
		infosoftglobal.FusionCharts.doPrepUnload = true
	}
};
infosoftglobal.FusionCharts.prototype = {
	setAttribute : function(a, b) {
		this.attributes[a] = b
	},
	getAttribute : function(a) {
		return this.attributes[a]
	},
	addParam : function(a, b) {
		this.params[a] = b
	},
	getParams : function() {
		return this.params
	},
	addVariable : function(a, b) {
		this.variables[a] = b
	},
	getVariable : function(a) {
		return this.variables[a]
	},
	getVariables : function() {
		return this.variables
	},
	getVariablePairs : function() {
		var a = new Array();
		var b;
		var c = this.getVariables();
		for (b in c) {
			a.push(b + "=" + c[b])
		}
		return a
	},
	setChartMessages : function() {
		this.addVariable("PBarLoadingText", "正在加载图表，请稍候...");
		this.addVariable("XMLLoadingText", "获取数据，请稍候...");
		this.addVariable("ParsingDataText", "解析数据，请稍候...");
		this.addVariable("ChartNoDataText", "没有数据");
		this.addVariable("RenderingChartText", "渲染图表，请稍候...");
		this.addVariable("LoadDataErrorText", "加载数据失败");
		this.addVariable("InvalidXMLText", "数据格式有误")
	},
	getSWFHTML : function() {
		var d = "";
		if (navigator.plugins && navigator.mimeTypes
				&& navigator.mimeTypes.length) {
			d = '<embed type="application/x-shockwave-flash" src="'
					+ this.getAttribute("swf") + '" width="'
					+ this.getAttribute("width") + '" height="'
					+ this.getAttribute("height") + '"  ';
			d += ' id="' + this.getAttribute("id") + '" name="'
					+ this.getAttribute("id") + '" ';
			var c = this.getParams();
			for ( var a in c) {
				d += [ a ] + '="' + c[a] + '" '
			}
			var b = this.getVariablePairs().join("&");
			if (b.length > 0) {
				d += 'flashvars="' + b + '"'
			}
			d += "/>"
		} else {
			d = '<object id="'
					+ this.getAttribute("id")
					+ '" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="'
					+ this.getAttribute("width") + '" height="'
					+ this.getAttribute("height") + '">';
			d += '<param name="movie" value="' + this.getAttribute("swf")
					+ '" />';
			var c = this.getParams();
			for ( var a in c) {
				d += '<param name="' + a + '" value="' + c[a] + '" />'
			}
			var b = this.getVariablePairs().join("&");
			if (b.length > 0) {
				d += '<param name="flashvars" value="' + b + '" />'
			}
			d += "</object>"
		}
		return d
	},
	setDataURL : function(a) {
		if (this.initialDataSet == false) {
			this.addVariable("dataURL", a);
			this.initialDataSet = true
		} else {
			var b = infosoftglobal.FusionChartsUtil.getChartObject(this
					.getAttribute("id"));
			b.setDataURL(a)
		}
	},
	setDataXML : function(a) {
		if (this.initialDataSet == false) {
			this.addVariable("dataXML", a);
			this.initialDataSet = true
		} else {
			var b = infosoftglobal.FusionChartsUtil.getChartObject(this
					.getAttribute("id"));
			b.setDataXML(a)
		}
	},
	setTransparent : function(a) {
		if (typeof a == "undefined") {
			a = true
		}
		if (a) {
			this.addParam("WMode", "transparent")
		} else {
			this.addParam("WMode", "Opaque")
		}
	},
	render : function(a) {
		this.addParam("WMode", "transparent");
		if ((this.detectFlashVersion == 1) && (this.installedVer.major < 8)) {
			if (this.autoInstallRedirect == 1) {
			} else {
				return false
			}
		} else {
			var b = (typeof a == "string") ? document.getElementById(a) : a;
			b.innerHTML = this.getSWFHTML();
			if (!document.embeds[this.getAttribute("id")]
					&& !window[this.getAttribute("id")]) {
				window[this.getAttribute("id")] = document.getElementById(this
						.getAttribute("id"))
			}
			return true
		}
	}
};
infosoftglobal.FusionChartsUtil.getPlayerVersion = function() {
	var c = new infosoftglobal.PlayerVersion([ 0, 0, 0 ]);
	if (navigator.plugins && navigator.mimeTypes.length) {
		var a = navigator.plugins["Shockwave Flash"];
		if (a && a.description) {
			c = new infosoftglobal.PlayerVersion(a.description.replace(
					/([a-zA-Z]|\s)+/, "").replace(/(\s+r|\s+b[0-9]+)/, ".")
					.split("."))
		}
	} else {
		if (navigator.userAgent
				&& navigator.userAgent.indexOf("Windows CE") >= 0) {
			var d = 1;
			var b = 3;
			while (d) {
				try {
					b++;
					d = new ActiveXObject("ShockwaveFlash.ShockwaveFlash." + b);
					c = new infosoftglobal.PlayerVersion([ b, 0, 0 ])
				} catch (f) {
					d = null
				}
			}
		} else {
			try {
				var d = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7")
			} catch (f) {
				try {
					var d = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
					c = new infosoftglobal.PlayerVersion([ 6, 0, 21 ]);
					d.AllowScriptAccess = "always"
				} catch (f) {
					if (c.major == 6) {
						return c
					}
				}
				try {
					d = new ActiveXObject("ShockwaveFlash.ShockwaveFlash")
				} catch (f) {
				}
			}
			if (d != null) {
				c = new infosoftglobal.PlayerVersion(d.GetVariable("$version")
						.split(" ")[1].split(","))
			}
		}
	}
	return c
};
infosoftglobal.PlayerVersion = function(a) {
	this.major = a[0] != null ? parseInt(a[0]) : 0;
	this.minor = a[1] != null ? parseInt(a[1]) : 0;
	this.rev = a[2] != null ? parseInt(a[2]) : 0
};
infosoftglobal.FusionChartsUtil.cleanupSWFs = function() {
	var c = document.getElementsByTagName("OBJECT");
	for (var b = c.length - 1; b >= 0; b--) {
		c[b].style.display = "none";
		for ( var a in c[b]) {
			if (typeof c[b][a] == "function") {
				c[b][a] = function() {
				}
			}
		}
	}
};
if (infosoftglobal.FusionCharts.doPrepUnload) {
	if (!infosoftglobal.unloadSet) {
		infosoftglobal.FusionChartsUtil.prepUnload = function() {
			__flash_unloadHandler = function() {
			};
			__flash_savedUnloadHandler = function() {
			};
			window.attachEvent("onunload",
					infosoftglobal.FusionChartsUtil.cleanupSWFs)
		};
		window.attachEvent("onbeforeunload",
				infosoftglobal.FusionChartsUtil.prepUnload);
		infosoftglobal.unloadSet = true
	}
}
if (!document.getElementById && document.all) {
	document.getElementById = function(a) {
		return document.all[a]
	}
}
if (Array.prototype.push == null) {
	Array.prototype.push = function(a) {
		this[this.length] = a;
		return this.length
	}
}
infosoftglobal.FusionChartsUtil.getChartObject = function(b) {
	var a = null;
	if (navigator.appName.indexOf("Microsoft Internet") == -1) {
		if (document.embeds && document.embeds[b]) {
			a = document.embeds[b]
		} else {
			a = window.document[b]
		}
	} else {
		a = window[b]
	}
	if (!a) {
		a = document.getElementById(b)
	}
	return a
};
var getChartFromId = infosoftglobal.FusionChartsUtil.getChartObject;
var FusionCharts = infosoftglobal.FusionCharts;
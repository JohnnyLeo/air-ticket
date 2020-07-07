/**
 * ajaxUtil.js is a tool to help user use ajax for asynchronous
 * information transfer
 * 
 * @author jleo
 * @date 2019/11/1
 */

/**
 * createXMLHttpRequest()
 * 
 * @param null
 * @return XMLHttpRequest
 */
function createXMLHttpRequest() {
	try {
		return new XMLHttpRequest();
	} catch (e) {
		alert("请不要使用低版本IE浏览器！！！");
		throw e;
	}
}

/**
 * 请求方法：GET/POST
 */
var HTTP_METHOD = {
	"GET":"GET", 
	"POST":"POST"
}

/**
 * 数据返回类型：TEXT/JSON/XML
 */
var HTTP_RESPONSE_TYPE = {
	"TEXT":0, 
	"JSON":1, 
	"XML":2
}

/**
 * ajax()
 * 
 * @param {Object} option = {
 * 		method: HTTP_METHOD
 * 		url: String
 * 		async: boolean
 * 		params: String
 * 		type: HTTP_RESPONSE_TYPE
 * 		callback: function
 * 	}
 * @return data: HTTP_RESPONSE_TYPE
 */
function ajax(option) {
	if (option.url == undefined) {
		alert("请求地址错误！");
		return;
	}
	if (option.callback == undefined) {
		alert("请申明回调函数！");
		return;
	}
	if (option.method == undefined) {
		option.method = HTTP_METHOD.GET;
		option.params = null;
	}
	if (option.params == undefined) {
		option.params = null;
	}
	if (option.async == undefined) {
		option.async = true;
	}
	if (option.type == undefined) {
		option.type = HTTP_RESPONSE_TYPE.TEXT;
	}
	var xmlHttp = createXMLHttpRequest();
	xmlHttp.open(option.method, option.url, option.async);
	if (HTTP_METHOD.POST == option.method) {
		xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	}
	xmlHttp.send(option.params);
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var data;
			if (option.type == HTTP_RESPONSE_TYPE.XML) {
				data = xmlHttp.responseXML;
			} else if (option.type == HTTP_RESPONSE_TYPE.JSON) {
				var text = xmlHttp.responseText;
				data = eval("(" + text + ")");
			} else if (option.type == HTTP_RESPONSE_TYPE.TEXT) {
				data = xmlHttp.responseText;
			}
			option.callback(data);
		}
	}
}

/**
 * 使用方法：
	ajax({
		method: HTTP_METHOD.POST, 
   		url: "/login", 
   		async: true, 
   		params: "name=user&pwd=123456", 
   		type: HTTP_RESPONSE_TYPE.JSON, 
   		callback: function(data) {
   			alert("it's OK!");
   		}
	})
 */
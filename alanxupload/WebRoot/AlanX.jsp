<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ALANX 文件批量上传组件,可同时上传几百个个文件，每个文件大小可达到100Mb</title>
<script type="text/javascript" src="AlanX/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="AlanX/swfobject.js"></script>
<script type="text/javascript">
//设置传入flash中的参数
var flashvars = {};  
flashvars.uploadUrl="http://192.168.0.104:8080/alanxupload/AlanXUploadServlet";//上传的url;
flashvars.extensionName="*.*";//允许扩展名，如"*.xls;*.doc";
flashvars.extensionDisp="AlanX文件示例";//显示在扩展名前;
flashvars.maxFileN=1000;//允许上传的最大文件个数;
flashvars.maxFileSize=10485760000;//允许上传的最大文件大小(byte);(10M)
flashvars.maxAllFileSize=104857600000;//允许上传的总文件最大值(byte);(100M)
flashvars.waitForProgress = "false";//上一个文件上传完毕后，是否马上开始上传下一个文件(默认false)，还是等待业务逻辑处理完之后（比如可能需要解析文件等业务过程），再开始下一个文件的上传(true)
flashvars.errorContinue = "true";//上传某一个文件出错，是否继续上传其他文件默认为true
flashvars.showLogoTxt = "true";//显示AlanX的logo及链接，默认为显示，当鼠标在组件右边悬停时，logo会显示，点击可以看到官方的帮助文档 。
flashvars.isDebug = "true";//是否调试，默认为false;
var params = {};  
var attributes = {};  
//swfobject.embedSWF("AlanX/AlanX.swf", "AlanX", "130", "25", "9.0.0","AlanX/expressInstall.swf",flashvars, params, attributes);  
swfobject.embedSWF("AlanX/AlanX.swf", "AlanX", "500", "40", "9.0.0","AlanX/expressInstall.swf",flashvars, params, attributes);  

function showAlanXDebug(info){
	$('#AlanXDebug').html(info+"<br/>"+$('#AlanXDebug').html());
}

$(document).ready(function(){
	$("#brown").click(function(){
		browseAlanXFile();
	});
});
function browseAlanXFile(){
	var obj = swfobject.getObjectById("AlanX");
	if (obj) {
		obj.browseFileAlanX(); 
	}
}
</script>

</head>
<body>

<div>
	<div id="AlanX">
	<p>如果您看到这段文字，说明可能出现如下情况</p>
	<p>1.flash插件版本太低，需要升级到至少9.0.0版本</p>
	<p>2.参数配置有错误</p>
	</div>
</div>
<div id="AlanXServerInfo"></div>
<div id="AlanXDebug"></div>
</body>
</html>
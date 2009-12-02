
import flash.events.*;
import flash.net.FileReference;
import flash.net.FileReferenceList;
import flash.net.FileFilter;
import flash.net.URLRequest;
import flash.net.URLVariables;
import flash.text.TextFormat;
import fl.controls.ProgressBar;
import fl.controls.ProgressBarDirection;
import fl.controls.ProgressBarMode;
import fl.managers.StyleManager;
import fl.controls.Button;

import fl.controls.TextArea;
import fl.controls.TextInput;

import fl.controls.Label;
import SecurityError;
import Error;
import flash.external.ExternalInterface;
flash.system.Security.allowDomain('*');
//setp1 变量

//页面传递过来的参数
var flashvars:Object=root.loaderInfo.parameters; 
var uploadUrl:String = flashvars['uploadUrl']; //上传的url
//var uploadParam:String = flashvars['uploadParam']; //上传的参数
//var lasttime:Boolean = flashvars['uploadParam']; //是否只在上传最后一个文件的时候才提交参数，默认false
var extensionName:String = flashvars['extensionName'];//扩展名,默认*.*
var extensionDisp:String = flashvars['extensionDisp'];//扩展名描述 默认*.*
var maxFileN:int = flashvars['maxFileN'];//允许上传的最大文件数,默认为1000，不允许超过1000个
//var timeout:int = flashvars['timeout'];//允许上传的最大时间，默认永久等下去
var maxFileSize:int = flashvars['maxFileSize'];//允许单个文件的最大尺寸默认100M,不允许超过100Mb
var maxAllFileSize:int = flashvars['maxAllFileSize'];//允许总文件的最大尺寸,默认10000Mb,不允许超过10000Mb
//var useAlanXUI:Boolean = falshvars['useAlanXUI'];//是否使用AlanXUI,默认ture
//var onetime:uint = falshvars['onetime'];//上传文件时同时上传的数量，默认为1，最大为10
var waitForProgress:Boolean = flashvars['waitForProgress']=="true"?true:false;//上一个文件上传完毕后，是否马上开始上传下一个文件(false默认)，还是等待业务逻辑处理完之后（比如可能需要解析文件等业务过程），再开始下一个文件的上传(true),如果这样的话，服务端必须为客户端返回数据，不果不返回，就不会执行
var errorContinue:Boolean = flashvars['errorContinue']=="false"?false:true;//上传某一个文件出错，是否继续上传其他文件
//全局变量
var fileRefAlls:Array=null; //所有的fileRefList被放在这个数组中，每个fileRefList中包含多个fileRef
var totalFileSize:int=0; //文件的总大小
var curURL:URLRequest = new URLRequest();
var totalProgressBar:ProgressBar = new ProgressBar();
var oneProgressBar:ProgressBar = new ProgressBar();
//初始化
maxFileN = maxFileN < 1 || maxFileN > 1000 ? 1000 : maxFileN;//200
maxFileSize = maxFileSize <= 0 || maxFileSize > 104857600 ? 104857600 : maxFileSize;//100M
maxAllFileSize = maxAllFileSize <= 0 || maxAllFileSize > 10485760000 ? 10485760000 : maxAllFileSize; //10000M
if(uploadUrl == null) {
	uploadUrl ='http://www.alanx.cn:8080/ALANXUpload/UploadServlet';
}

if (uploadUrl!=null) { 	//封装url
	curURL.url=uploadUrl;
	curURL.method=URLRequestMethod.POST;
} else {
	//todo:提示用户必须输入uploadUrl
	btnBrowse.enabled=false;
	btnUpload.enabled=false;
}

btnBrowse.addEventListener(MouseEvent.CLICK,browseFile);
btnUpload.addEventListener(MouseEvent.CLICK,uploadFile);


//监听用户选择
function selectHandler(event:Event):void {
	//初始化fileRefAlls
	fileRefAlls = new Array();
	
	//先判断还可以添加多少个文件
	var canAddFlagN:int = 0; //目前还能添加的文件数
	var canAddFlagS:int = 0; //目前还允许添加的文件总大小
	var canAddFlag:Boolean = true; //目前能否添加文件
	var tempCurrTotalSize:int = 0; //临时变量 当前添加文件的总大小
	var tFile:FileReference = null; //遍历是用来引用
		
	
	canAddFlagN = maxFileN;
	canAddFlagS = maxAllFileSize;
	
	if(fileRefs == null || fileRefs.fileList == null ){
		//没有选中文件
		canAddFlag = false;
	}
	
	if(canAddFlag && fileRefs.fileList.length > canAddFlagN){
		//todo:用户选择的文件太多了，总数已经大过了允许的最大文件数
		canAddFlag = false;
	}
	try{
		if(canAddFlag){
			for (var i:int = 0; i < fileRefs.fileList.length; i++) {
				tFile = FileReference(fileRefs.fileList[i]);
				if(tFile.size > maxFileSize){
					//验证单个文件的大小是否超过了最大值M
					//todo:这个文件太大，被忽略
					canAddFlag = false;
					break;
				}else if(tempCurrTotalSize + tFile.size >canAddFlagS){
					//验证总文件大小是否超过了最大值
					//todo:到这里的时候，文件的总大小已经超过了允许上传文件的总大小
					canAddFlag = false;
					break;
				}else{
					tempCurrTotalSize = tempCurrTotalSize + tFile.size;
				}
			}
		}
	}catch (myError:Error) {  
    	//todo:可能因为文件太大，计算size的时候出现IOError错误
		canAddFlag = false;
	} 
	
	if(canAddFlag){
		//能走到这里，说明这批文件时允许被添加的。
		//将这批文件放入到数组中
		for (var j:int = 0; j < fileRefs.fileList.length; j++) {
			var fileRTemp :FileReference = FileReference(fileRefs.fileList[j]);
			//绑定监听器
			addPendingFile(fileRTemp);
			fileRefAlls.push(fileRTemp);
		}
		//更新当前所有文件的总的大小
		totalFileSize = totalFileSize + tempCurrTotalSize;

	}
	testTxt.text = "文件总数："+fileRefAlls.length+" | 文件总大小:"+ getSizeType(totalFileSize);
	
}

//计算大小
function getSizeType(size:Number):String {
	if (size<1024) {
		return int(size * 100) / 100 + "bytes";
	}
	if (size<1048576) {
		return int(size / 1024 * 100) / 100 + "KB";
	}
	if (size<1073741824) {
		return int(size / 1048576 * 100) / 100 + "MB";
	}
	return int(size / 1073741824 * 100) / 100 + "GB";
}

//点击后执行
function browseFile(event:MouseEvent) {
	fileRefs = new FileReferenceList();
	fileRefs.addEventListener(Event.SELECT, selectHandler);
	fileRefs.browse(getTypes());
}

function getTypes():Array {
	var allTypes:Array = new Array();
	extensionName = extensionName == null ? "*.*" : extensionName;
	extensionDisp = extensionDisp == null ? extensionName:extensionDisp;
	allTypes.push(new FileFilter(extensionDisp,extensionName));
	return allTypes;
}


function uploadFile(event:MouseEvent) {
	
	
	//锁定按钮，防止用户再次点击
	btnBrowse.enabled=false;
	btnUpload.enabled=false;
	//上传文件
	//总进度条开始工作
	totalProgressBar  = new ProgressBar();
	totalProgressBar.indeterminate=false;
	totalProgressBar.mode=ProgressBarMode.MANUAL;
	totalProgressBar.width=200;
	totalProgressBar.height=5;
	totalProgressBar.x=4;
	totalProgressBar.y=33;
	totalProgressBar.direction=ProgressBarDirection.RIGHT;
	totalProgressBar.minimum=0;
	totalProgressBar.maximum=totalFileSize;
	totalProgressBar.setProgress(0,totalProgressBar.maximum);
	addChild(totalProgressBar);
	uploadOneFile();
	
	
}
function uploadOneFile() {
	var file:FileReference;
	if (fileRefAlls != null && fileRefAlls.length > 0) {
		file=FileReference(fileRefAlls.shift());
		totalFileSize = totalFileSize - file.size;
		
		oneProgressBar  = new ProgressBar();
		oneProgressBar.indeterminate=false;
		oneProgressBar.mode=ProgressBarMode.MANUAL;
		oneProgressBar.width=200;
		oneProgressBar.height=4;
		oneProgressBar.x=4;
		oneProgressBar.y=28;
		oneProgressBar.direction=ProgressBarDirection.RIGHT;
		oneProgressBar.minimum=0;
		oneProgressBar.maximum=file.size;
		oneProgressBar.setProgress(0,file.size);
		addChild(oneProgressBar);
		file.upload(curURL);
		
		
	} else {
		//所有文件都已经处理完毕
		btnBrowse.enabled=true;
		btnUpload.enabled=true;
		
		//清空总进度条
		removeChild(totalProgressBar);
		totalProgressBar = null;
		
		//清空
		testTxt.text='上传完毕';
	}
}


//给每一个上传的文件绑定时间(todo:添加进度条)
function addPendingFile(file:FileReference):void {

	file.addEventListener(Event.OPEN, openHandler);
	//经过测试发现，如果启用了DataEvent.UPLOAD_COMPLETE_DATA，Event.COMPLETE就无效了
	if(waitForProgress){
		file.addEventListener(DataEvent.UPLOAD_COMPLETE_DATA, uploadCompleteDataHandler);
	}else{
		file.addEventListener(Event.COMPLETE, completeHandler);
	}
	file.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
	file.addEventListener(ProgressEvent.PROGRESS, progressHandler);
	file.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
	file.addEventListener(HTTPStatusEvent.HTTP_STATUS,httpStatusHandler);
	//单个文件的进度条，请区别总进度条
	//addProgressBar(file);
}

function httpStatusHandler(event:HTTPStatusEvent):void{
	testTxt.text = "httpStatusHandler";
}
function openHandler(event:Event):void {
	//var file:FileReference=FileReference(event.target);
	//trace("openHandler: name=" + file.name);
}
function progressHandler(event:ProgressEvent):void {
	var file:FileReference=FileReference(event.target);
	oneProgressBar.setProgress(event.bytesLoaded,file.size);
	testTxt2.htmlText = file.name+"<h6>[已读:"+getSizeType(event.bytesLoaded)+"/"+getSizeType(file.size)+"]</h6)"

	
}
function ioErrorHandler(event:Event):void {
	var file:FileReference=FileReference(event.target);
	if(errorContinue){
		//todo:告诉用户上传失败的文件
		removeChild(oneProgressBar);
		uploadOneFile();
	}else{
		testTxt.text = "ioErrorHandler:["+file.name+"]";
	}
	
	//如果出现io错误，重新上传，将该文件添加到队列，继续上传
	//var file:FileReference=FileReference(event.target);
	
	//serverInfo.htmlText=event+serverInfo.htmlText;
	//serverInfo.htmlText="<p>ioErrorHandler: name="+file.name+"</p>"+serverInfo.htmlText;
	//fileRefAlls.unshift(file);
	//testTxt.text = "ioErrorHandler:"+event.text;
	//trace("ioErrorHandler: " + event);
	
}
function securityErrorHandler(event:Event):void {
	//var file:FileReference=FileReference(event.target);
	//trace("securityErrorHandler: name=" + file.name + " event=" + event.toString());
	testTxt.text = "securityErrorHandler:"+event;
}
function uploadCompleteDataHandler(event:DataEvent):void {
	
		var file:FileReference=FileReference(event.target);
		totalProgressBar.setProgress(totalProgressBar.value + file.size, totalProgressBar.maximum);
		//将文件总数和文件总尺寸显示在文本框中
		testTxt.text = "文件总数："+fileRefAlls.length+" | 文件总大小:"+ getSizeType(totalFileSize);
		//testTxt.text = "已读："+getSizeType(totalProgressBar.value + file.size)+" | 文件总大小:"+ getSizeType(totalProgressBar.maximum);
		//(totalProgressBar.value + event.bytesLoaded);
		removeChild(oneProgressBar);
		testTxt2.htmlText="";
		uploadOneFile();

}
function completeHandler(event:Event):void {
	
		var file:FileReference=FileReference(event.target);
		totalProgressBar.setProgress(totalProgressBar.value + file.size, totalProgressBar.maximum);
		//将文件总数和文件总尺寸显示在文本框中
		testTxt.text = "文件总数："+fileRefAlls.length+" | 文件总大小:"+ getSizeType(totalFileSize);
		//testTxt.text = "已读："+getSizeType(totalProgressBar.value + file.size)+" | 文件总大小:"+ getSizeType(totalProgressBar.maximum);
		//(totalProgressBar.value + event.bytesLoaded);
		removeChild(oneProgressBar);
		testTxt2.htmlText="";
		uploadOneFile();

}

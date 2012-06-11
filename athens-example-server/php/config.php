<?
	header("Cache-Control:no-cache"); 
	header("Expires:0" ); 
	header("Pragma:no-cache"); 
	include_once "include/config.php";
	include_once INCLUDE_FOLDER."dbconn.php";
	include_once INCLUDE_FOLDER."common.php";
	
?>
<!DOCTYPE html>
<html>
<head>
<title>Athens Monitor</title>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="<?=JQUERY_URL?>"></script>
<link href="<?=WEB_ABSROOT?>include/common.css" rel="stylesheet" type="text/css" media="all" />

</head>
<body>
<div class="topLayer">
	<div class="topTitle">Athens Monitor</div>
	
	<div class="topConfig" onclick="location.href = '<?=WEB_ROOT?>config.php';">Config</div>
	<div class="topBlocks" onclick="location.href = '<?=WEB_ROOT?>blocks.php';">Blocks</div>
</div>
<textarea class="configTextarea" id="configTextarea"></textarea>
<div class="saveBtn" id="saveBtn">Save</div>
<script type="text/javascript">
var saveActive = false;
function saveConfig(){
	if(saveActive){
		saveActive = false;
		$.ajax({
   			url: "https://rothlee.net/athens/admin/uploadConfig",
   			dataType : "json",
   			type: "POST",
				data: ( {"content":$("textarea#configTextarea").val()}),
   			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
   			success:function(obj){
						if(obj.result){
							alert("save Complete");
						}else{
							alert("Fail to save!");
						}
						saveActive = true;
   			},
   			error: function(){
   				saveActive = true;
					alert("fail to load !");
   			}
	 	});
	 	
	}
}
onload = function(){
	$.ajax({
   			url: "https://rothlee.net/athens/admin/getConfig",
   			dataType : "text",
   			type: "GET",
   			data: ( {}),
   			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
   			success:function(obj){
						$("textarea#configTextarea").val(obj);
						saveActive = true;
						$(window).scrollTop(0);
   			},
   			error: function(){
					alert("fail to load !");
   			}
 	});
 	$("div#saveBtn").bind('click', saveConfig);
}
</script>
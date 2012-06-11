<?
	header("Cache-Control:no-cache"); 
	header("Expires:0" ); 
	header("Pragma:no-cache"); 
	include_once "include/config.php";
	include_once INCLUDE_FOLDER."dbconn.php";
	include_once INCLUDE_FOLDER."common.php";
	$page = (INT)getUrlData("page", "all", 1);
	$interval = 20;
	
	$limit = " Limit ".($page-1) * $interval.", ".$interval;
	$query = "SELECT ifnull(count(*), 0) as cnt FROM athens_block ";
	$blockCntObj = $DB->query($query);
	$totalCnt = $blockCntObj[0]['cnt'];
	$query = "SELECT * FROM athens_block ORDER BY id DESC ".$limit;
	
	$blocks = $DB->query($query);
?>
<!DOCTYPE html>
<html>
<head>
<title>Athens Monitor</title>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="<?=JQUERY_URL?>"></script>
<script type="text/javascript">
var listitems = [];
var listdatas = [];
</script>
<link href="<?=WEB_ABSROOT?>include/common.css" rel="stylesheet" type="text/css" media="all" />

</head>
<body>
<div class="topLayer">
	<div class="topTitle">Athens Monitor</div>
	
	<div class="topConfig" onclick="location.href = '<?=WEB_ROOT?>config.php';">Config</div>
	<div class="topBlocks" onclick="location.href = '<?=WEB_ROOT?>blocks.php';">Blocks</div>
</div>
<div class="listLayerOuter">
	<div class="listLayer" id="listLayer">
	<?
		if(count($blocks) > 0){
		}else{
	?>
		<div class="nolist">Empty.</div>
	<?
		}
	?>
	</div>
	<div class="pagingLayer">
	<?
		$pageVal['totalPage'] = (INT)(($totalCnt-1) / $interval ) + 1;
		$pageVal['thisPage'] = $page;
		$pageVal['url'] = WEB_ROOT."blocks.php";
		$pageVal['addQuery'] = "";
		$pageVal['pageInterval'] = 10;
		$pageVal['onStyle'] = "color:#FF0000;";
		$pageVal['offStyle'] = "color:#666666;";
		$pageVal['spacing'] = 5;
	
		view_paging($pageVal);
	?>
	</div>
</div>
<script type="text/javascript">
var actionPerm = true;
	<?
	if(count($blocks) > 0){
?>
<?
		for($i = 0; $i < count($blocks); $i++){
?>
	listdatas.push( {"id":<?=$blocks[$i]['id']?>,"target":"<?=$blocks[$i]['target']?>", "method":"<?=$blocks[$i]['method']?>", "path":"<?=$blocks[$i]['path']?>", "cause":"<?=$blocks[$i]['cause']?>", "expiry_time":"<?=$blocks[$i]['expiry_time']?>", "created_time":"<?=$blocks[$i]['created_time']?>"});
<?
		}
	}
?>
function createListItemTitle(){

	var itemObj = document.createElement("div");
	itemObj.className = "listItemTitle";
	
	var idxObj = document.createElement("div");
	idxObj.className = "idx";
	idxObj.innerHTML = "id";
	itemObj.appendChild(idxObj);
	
	var targetObj = document.createElement("div");
	targetObj.className = "target";
	targetObj.innerHTML = "target";
	itemObj.appendChild(targetObj);
	
	var methodObj = document.createElement("div");
	methodObj.className = "method";
	methodObj.innerHTML = "method";
	itemObj.appendChild(methodObj);
	
	var pathObj = document.createElement("div");
	pathObj.className = "path";
	pathObj.innerHTML = "path";
	itemObj.appendChild(pathObj);
	
	var causeObj = document.createElement("div");
	causeObj.className = "cause";
	causeObj.innerHTML = "cause";
	itemObj.appendChild(causeObj);
	
	var expiry_timeObj = document.createElement("div");
	expiry_timeObj.className = "expiry_time";
	expiry_timeObj.innerHTML = "expiry_time";
	itemObj.appendChild(expiry_timeObj);

	var created_timeObj = document.createElement("div");
	created_timeObj.className = "created_time";
	created_timeObj.innerHTML = "created_time";
	itemObj.appendChild(created_timeObj);	
	
	var btnsObj = document.createElement("div");
	btnsObj.className = "btns";
	btnsObj.innerHTML = "buttons";
	itemObj.appendChild(btnsObj);	
	return itemObj;
}

function createListItem( obj ){
	var itemObj = document.createElement("div");
	itemObj.className = "listItem";
	
	var idxObj = document.createElement("div");
	idxObj.className = "idx";
	idxObj.innerHTML = obj.id;
	itemObj.appendChild(idxObj);
	
	var targetObj = document.createElement("div");
	targetObj.className = "target";
	targetObj.innerHTML = obj.target;
	itemObj.appendChild(targetObj);
	
	var methodObj = document.createElement("div");
	methodObj.className = "method";
	methodObj.innerHTML = obj.method;
	itemObj.appendChild(methodObj);
	
	var pathObj = document.createElement("div");
	pathObj.className = "path";
	pathObj.innerHTML = obj.path;
	itemObj.appendChild(pathObj);
	
	var causeObj = document.createElement("div");
	causeObj.className = "cause";
	causeObj.innerHTML = obj.cause;
	itemObj.appendChild(causeObj);
	
	var expiry_timeObj = document.createElement("div");
	expiry_timeObj.className = "expiry_time";
	expiry_timeObj.innerHTML = obj.expiry_time;
	itemObj.appendChild(expiry_timeObj);

	var created_timeObj = document.createElement("div");
	created_timeObj.className = "created_time";
	created_timeObj.innerHTML = obj.created_time;
	itemObj.appendChild(created_timeObj);	
	
	var btnsObj = document.createElement("div");
	btnsObj.className = "btns";
	var btnObj = document.createElement("div");
	btnObj.className = "delBtn";
	btnObj.innerHTML = "delete";
	btnObj.onclick = function(){
		if(actionPerm){
			actionPerm = false;
			
			
			
			
			var idx_this = -1;
			for(var i = 0; i < listdatas.length; i++){
				if( obj.id == listdatas[i].id ){
					idx_this = i;
					break;
				}
			}
			if(idx_this != -1){
				
				$.ajax({
		   			url: "https://rothlee.net/athens/admin/releaseBlock",
		   			dataType : "json",
		   			type: "POST",
						data: ( {"ip_addr":listdatas[i].target, "path":listdatas[i].path, "method":listdatas[i].method}),
		   			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		   			success:function(obj){
								if(obj.result){
									listdatas = listdatas.splice(idx_this, 1);
									document.getElementById("listLayer").removeChild(listitems[idx_this]);
									listitems = listitems.splice(idx_this, 1);
								}else{
									alert("Fail to delete!");
								}
								actionPerm = true;
		   			},
		   			error: function(){
		   				actionPerm = true;
							alert("fail to delete !");
		   			}
			 	});
				
				
			}
			
			
		}
	}
	btnsObj.appendChild(btnObj);	
	
	itemObj.appendChild(btnsObj);	
	
	
	
	
	return itemObj;
}
onload = function(){
	if(listdatas.length > 0){
		document.getElementById("listLayer").appendChild(createListItemTitle());
		for(var i = 0; i < listdatas.length; i++){
			 listitems.push(document.getElementById("listLayer").appendChild(createListItem(listdatas[i])));
		}
	}
}
</script>
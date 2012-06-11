<?php

function getUrlData( $name, $type, $default = '' ){
	$rtnval = $default;
	if($type == "all"){
		if( isset($_GET[$name]) && $_GET[$name] != '' ){
			$rtnval = $_GET[$name];
		}else if( isset($_POST[$name]) && $_POST[$name] != '' ){
			$rtnval = $_POST[$name];
		}
	}else{
		if($type == 'post'){
			if( isset($_POST[$name]) && $_POST[$name] != '' ){
				$rtnval = $_POST[$name];
			}
		}else if($type == 'get'){
			if( isset($_GET[$name]) && $_GET[$name] != '' ){
				$rtnval = $_GET[$name];
			}
		}
	}
	if($rtnval == '') $rtnval = $default;
	if(!is_array($rtnval)){
		$rtnval = urlDecode($rtnval);
	}
	if (!get_magic_quotes_gpc()) {
		$rtnval =  addslashes($rtnval);
	}
	return $rtnval;
}


function view_paging( $initArray ){
	$totalPage = isset($initArray['totalPage']) ? $initArray['totalPage'] : 0;
	$thisPage = isset($initArray['thisPage']) ? $initArray['thisPage'] : 0;
	$url = isset($initArray['url']) ? $initArray['url'] : 0;
	$addQuery = isset($initArray['addQuery']) ? $initArray['addQuery'] : "";
	if($addQuery != "") $addQuery.="&";
	if(substr($addQuery,0,1) == "?"){
	}else if(substr($addQuery,0,1) == "&"){
		$addQuery = "?".substr($addQuery,1);
	}else{
		$addQuery = "?".$addQuery;
	}
	$link_type = isset($initArray['link_type']) ? $initArray['link_type'] : "href";
	$jsfunction = isset($initArray['jsfunction']) ? $initArray['jsfunction'] : "";

	$onStyle = isset($initArray['onStyle']) ? $initArray['onStyle'] : "color:#FF0000;";
	$offStyle = isset($initArray['offStyle']) ? $initArray['offStyle'] : "color:#666666;";
	$spacing = isset($pageVal['spacing']) ? $pageVal['spacing'] : 5;
	$pageInterval = isset($initArray['pageInterval']) ? $initArray['pageInterval'] : 10;
	$firstIcon = isset($initArray['firstIcon']) ? $initArray['firstIcon'] : "<font style='color:#CCCCCC;'>First</font>";
	$lastIcon = isset($initArray['lastIcon']) ? $initArray['lastIcon'] : "<font style='color:#CCCCCC;'>End</font>";
	$prevIcon = isset($initArray['prevIcon']) ? $initArray['prevIcon'] : "<font style='color:#CCCCCC;'>Prev</font>";
	$nextIcon = isset($initArray['nextIcon']) ? $initArray['nextIcon'] : "<font style='color:#CCCCCC;'>Next</font>";

	$firstIconActive = isset($initArray['firstIconActive']) ? $initArray['firstIconActive'] : "<font style='".$offStyle."'>First</font>";
	$lastIconActive = isset($initArray['lastIconActive']) ? $initArray['lastIconActive'] : "<font style='".$offStyle."'>End</font>";
	$prevIconActive = isset($initArray['prevIconActive']) ? $initArray['prevIconActive'] : "<font style='".$offStyle."'>Prev</font>";
	$nextIconActive = isset($initArray['nextIconActive']) ? $initArray['nextIconActive'] : "<font style='".$offStyle."'>Next</font>";

	
	$thisInterval = (INT)(($thisPage-1) / $pageInterval) * $pageInterval + 1;
	$prevInterval = $thisInterval - $pageInterval;
	if($prevInterval < 0) $prevInterval = $thisInterval;
	$nextInterval = $thisInterval + $pageInterval;
	if($nextInterval > $totalPage) $nextInterval = $thisInterval;

	if($totalPage > 1){
	?>
<script type="text/javascript">
	function view_paging_go_page( val ){
<?
	if(strtolower($link_type) == 'js' || strtolower($link_type) == 'javascript'){
		if($jsfunction != ''){
			echo $jsfunction."(val);";
		}
	}else{
?>
		location.href = '<?=$url?><?=$addQuery?>page=' + val;
<?
	}
?>
	}
</script>
<table cellpadding="0" cellspacing="0" border="0" align="center">
	<tr>
	<?
		if($thisPage == 1){
	?>
		<td style="cursor:pointer;"><?=$firstIcon?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
		}else{
	?>
		<td style="cursor:pointer;" onclick="view_paging_go_page(1);"><?=$firstIconActive?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
		}

		if($prevInterval == $thisInterval){
	?>
		<td style="cursor:pointer;"><?=$prevIcon?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
		}else{
	?>
		<td style="cursor:pointer;" onclick="view_paging_go_page(<?=$prevInterval?>);"><?=$prevIconActive?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
		}
		$strt = $thisInterval;
		$end = $thisInterval + $pageInterval - 1;
		if($end > $totalPage) $end = $totalPage;
		for($i = $strt; $i <= $end; $i++){
			if($i == $thisPage){
	?>
		<td style="cursor:pointer;<?=$onStyle?>"><?=$i?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
			}else{
	?>
		<td style="cursor:pointer;<?=$offStyle?>" onclick="view_paging_go_page(<?=$i?>);"><?=$i?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
			}
		}

		if($nextInterval == $thisInterval){
	?>
		<td style="cursor:pointer;"><?=$nextIcon?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
		}else{
	?>
		<td style="cursor:pointer;" onclick="view_paging_go_page(<?=$nextInterval?>);"><?=$nextIconActive?></td>
		<td style="width:<?=$spacing?>px;"></td>
	<?
		}

		if($thisPage == $totalPage){
	?>
		<td style="cursor:pointer;"><?=$lastIcon?></td>
	<?
		}else{
	?>
		<td style="cursor:pointer;" onclick="view_paging_go_page(<?=$totalPage?>);"><?=$lastIconActive?></td>
	<?
		}
	?>
	</tr>
</table>
	<?
	}
	?>
	<?
}

function gotoUrl( $url, $alert = ''){
	?>
	<script type="text/javascript">
	<?
		if($alert != ''){
			$alert = str_replace('"', '\"', $alert);
	?>
	alert("<?=$alert?>");
	<?
		}
		if($url == 'back'){
	?>
	history.back();
	<?
		}else{
	?>
	location.href = '<?=$url?>';
	<?
		}

	?>
	</script>
	<?
}


function randStr( $length = 8 ){
	$randStr = '';
	for($i = 0; $i < (INT)$length; $i++){
		$randStr  .= substr('0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz', rand(0, 61), 1);
	}
	return $randStr;
}

function ellipsisText($str, $len, $tail='..', $checkmb=true ) { 

	preg_match_all('/[\xE0-\xFF][\x80-\xFF]{2}|./', $str, $match); // target for BMP 
	   
	$m = $match[0]; 
		
	$slen = strlen($str); // length of source string 
	$tlen = strlen($tail); // length of tail string 
	$mlen = count($m); // length of matched characters 
	   
	if ($slen <= $len) return $str; 
	 
	if (!$checkmb && $mlen <= $len) return $str; 

	$ret = array(); 
		
	$count = 0; 
		
	for ($i=0; $i < $len; $i++) { 
		   
		$count += ($checkmb && strlen($m[$i]) > 1)?2:1; 
				
		if ($count + $tlen > $len) break; 
			   
		$ret[] = $m[$i]; 

	} 
	  
	return join('', $ret).$tail; 
}
function ellipsisText2( $str, $length, $etc = '..' ){

	$rtnVal = '';

	if( strlen($str) > $length ){
		$rtnVal = substr($str, 0, $length).$etc;
		preg_match('/^([\x00-\x7e]|.{2})*/', $rtnVal, $str);  
	}else{
		$rtnVal = $str;
	}
	return $rtnVal;
}
?>
<?php
	header("Cache-Control:no-cache");
	header("Expires:0" );
	header("Pragma:no-cache");

	/* connect and select database */
	$conn = mysql_connect('127.0.0.1:3306/olympus?characterEncoding=UTF8', 'olympus', 'olympus');
	if (!$conn) {
		die('Could not connect:'.mysql_error());
	} else {
		mysql_select_db('olympus',$conn);
	}
	mysql_set_charset('UTF-8');
	mysql_query("SET NAMES utf8");
	
	/* query results */
	$result = mysql_query("
		SELECT id, user_id, content, created_time
		FROM omp_post
		WHERE id < 1024 AND id > 0
		ORDER BY id DESC
		LIMIT 64");
	$result_count = mysql_num_rows($result);
	
	/* fetch result */
	$count = 0;
	while($row = mysql_fetch_array($result))
	{
		$post = array (
			'id' => $row['id'],
			'user_id' => $row['user_id'],
			'content' => $row['content'],
			'created_time' => strtotime($row['created_time']).'000'
		);
		$post_array[$count] = $post;
		$count++;
	}
	
	/* fetch user result */
	$count = 0;
	$post_count = count($post_array);
	for($count=0;$count<$post_count;$count++) {
	
		$post = $post_array[$count];
		$result = mysql_query("
			SELECT id, profile, email_addr, nickname, created_time
			FROM
			omp_user
			WHERE id = ".$post['user_id']);
		$result_count = mysql_num_rows($result);
		
		$row = mysql_fetch_array($result);
		$user = array (
			'id' => $row['id'],
			'email_addr' => $row['email_addr'],
			'nickname' => $row['nickname'],
			'created_time' => strtotime($row['created_time']).'000'
		);
		
		$id = $post['id'];
		$user_id = $post['user_id'];
		$content = $post['content'];
		$created_time = $post['created_time'];
		
		$new_post['id'] = $id;
		$new_post['user_id'] = $user_id;
		$new_post['content'] = $content;
		$new_post['created_time'] = $created_time;
		$new_post['user'] = $user;
		
		$result_array[$count] = $new_post;
	}
	$result = array (
		'result' => $result_array
	);
	echo json_encode($result);
?>

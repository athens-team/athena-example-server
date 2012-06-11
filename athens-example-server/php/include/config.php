<?
	
	define( 'DOCUMENT_ROOT', '/home/kurtix/public_html/');
	define( 'CONFIG_FOLDER', DOCUMENT_ROOT."/include/");
	define( 'INCLUDE_FOLDER', DOCUMENT_ROOT."/include/");

	define( 'DB_HOST', '127.0.0.1:3306/olympus?characterEncoding=UTF8');
	define( 'DB_USERID', 'olympus' );
	define( 'DB_PASSWD', 'olympus' );
	define( 'DB_NAME', 'olympus' );
	define( 'WEB_ROOT', '/athens/monitor/');
	define( 'IMG_ROOT', WEB_ROOT.'img/');
	define( 'SERVICE_NAME', 'kurtix' );
	define( 'WEB_ABSROOT', 'https://rothlee.net/athens/monitor/');

	$header_ua = $_SERVER['HTTP_USER_AGENT'];

	if( strpos($header_ua, 'iPhone') > 0){
		define( '_DEVICE_', 'iPhone');
		define( '_DOCUMENT_WIDTH_', '100%' );
	}else{
		define( '_DEVICE_', 'pc');
		define( '_DOCUMENT_WIDTH_', '1000' );
	}

	define('PROTOTYPE_URL', "https://ajax.googleapis.com/ajax/libs/prototype/1.7.0.0/prototype.js");
	define('JQUERY_URL', "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js");
?>
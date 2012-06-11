<?  
class DB {
		/**
		 * Sington 방식을 사용하기 위해 static 으로 선언한다
		 * @var DB Connect Link
		 */
		static $conn = FALSE;
		
		var $stmt = 0;
		var $abstractData = FALSE;
		var $error = FALSE;
		
		/**
		 * 생성자 : 인스턴스 생성시 자동으로 DB연결을 수행한다.
		 * @return unknown_type
		 */
		function __construct() {
			$this->connect();
		}
		
		/* --------------------------------------------------------------------- */
		/* 함수 제작 BY Puppy */

	
		// <find 함수> -------------------------------------------------------------- made by Puppy, 2009.08.31
		//
		// [함수설명] : 한개의 로우정보를 리턴
		// [Input Value]
		//   - $tableAssoc : 테이블 명 또는 LEFT 조인으로 묶인 쿼리문
		//	  - $where : 조건절, array또는 string (GROUP BY절 포함)
		//	  - $select : SELECT절
		//	  - $order : Order절
		// [Output Value] : array
		// [주의사항] JOIN했을경우, SELECT절에는 동일 필드명이 있을 수 있으므로, 반드시 Tablename.Field1 AS Field1, Tablename2.Field1 AS Field2 처럼 던질것!
		// [사용 예]
		// $tableAssoc = "address_peoples AddressPeople ";
		// $tableAssoc .= "LEFT JOIN address_maps AddressMap ON(AddressPeople.id=AddressMap.people_id)";
		//	$where = "AddressPeople.user_id='".$User['id']."'";
		// $address = $DB->find($tableAssoc, $where);
		function find($tableAssoc, $where=null, $select=null, $order=null) {
			if(!$select) $select = '*';

			if(is_array($where)) {
				$tmpWhere = '';
				foreach($where as $key=>$val) {
					if($tmpWhere) $tmpWhere .= " AND ";
					if(is_numeric($key)) {
						$tmpWhere .= $val;
					}
					else {
						if(substr(strtolower($val),0,8)=='to_date(') {
							$tmpWhere .= $key."=".$val;
						}
						else {
							$tmpWhere .= $key."='".$val."'";
						}
					}
				}
				$where = $tmpWhere;
			}

			$strQuery = "SELECT ".$select." FROM ".$tableAssoc.($where ? " WHERE ".$where : null).($order ? " ORDER BY ".$order : null);
			if(DEBUG!='0' && $this->ChkDebug!='0') $this->DebugQry[$this->queryDebugSID][] = $strQuery;
			$DBresult = $this->parseExec($strQuery);
			$ret = $this->fetchArray($DBresult);
			
			//$this->disconnect();
			return $ret;
		}

		// <findCount 함수> -------------------------------------------------------------- made by Puppy, 2009.08.31
		//
		// [함수설명] : 로우의 카운트를 리턴
		// [Input Value]
		//   - $tableAssoc : 테이블 명 또는 LEFT 조인으로 묶인 쿼리문
		//	  - $where : 조건절, array또는 string (GROUP BY절 포함)
		// [Output Value] : INT
		function findCount($tableAssoc, $where=null) {
			if(is_array($where)) {
				$tmpWhere = '';
				foreach($where as $key=>$val) {
					if($tmpWhere) $tmpWhere .= " AND ";
					if(is_numeric($key)) {
						$tmpWhere .= $val;
					}
					else {
						if(substr(strtolower($val),0,8)=='to_date(') {
							$tmpWhere .= $key."=".$val;
						}
						else {
							$tmpWhere .= $key."='".$val."'";
						}
					}
				}
				$where = $tmpWhere;
			}

			$strQuery = "SELECT COUNT(*) AS cnt FROM ".$tableAssoc.($where ? " WHERE ".$where : null);
			if(DEBUG!='0' && $this->ChkDebug!='0') $this->DebugQry[$this->queryDebugSID][] = $strQuery;
			$DBresult = $this->parseExec($strQuery);
			$ret = $this->fetchArray($DBresult);

			//$this->disconnect();
			return $ret['cnt'];
		}



		// <findInsertID 함수> -------------------------------------------------------------- made by Puppy, 2009.08.31
		//
		// [함수설명] : 이전의 INSERT 작업으로부터 생성된 ID를 반환
		// [Output Value] : INT
		function findInsertID() {
			$ret['INSERT_ID'] = mysql_insert_id();
			return $ret['INSERT_ID'];
		}
		
		function findAffectedRows() {			
			$ret['AFFECTED_ROWS'] = mysql_affected_rows();
			return $ret['AFFECTED_ROWS'];
		}


		
		// <findAll 함수> -------------------------------------------------------------- made by Puppy, 2009.08.31
		//
		// [함수설명] : 조건에 해당하는 로우들을 리턴
		// [Input Value]
		//   - $tableAssoc : 테이블 명 또는 LEFT 조인으로 묶인 쿼리문
		//	  - $where : 조건절, array또는 string (GROUP BY절 포함)
		//	  - $select : SELECT절
		//	  - $order : Order절
		//	  - $limit : 오프셋(페이지당 글수)
		//	  - $page : 오프셋 (현재 페이지)
		// [Output Value] : array
		// [주의사항] JOIN했을경우, SELECT절에는 동일 필드명이 있을 수 있으므로, 반드시 Tablename.Field1 AS Field1, Tablename2.Field1 AS Field2 처럼 던질것!
		function findAll($tableAssoc, $where=null, $select=null, $order=null, $limit=null, $page=null) {
			if($limit && !$page) $page = 1;
			if(is_array($where)) {
				$tmpWhere = '';
				foreach($where as $key=>$val) {
					if($tmpWhere) $tmpWhere .= " AND ";
					if(is_numeric($key)) {
						$tmpWhere .= $val;
					}
					else {
						if(substr(strtolower($val),0,8)=='to_date(') {
							$tmpWhere .= $key."=".$val;
						}
						else {
							$tmpWhere .= $key."='".$val."'";
						}
					}
				}
				$where = $tmpWhere;
			}

			if(is_array($select)) {
				$select = implode(", ",$select);
			}


			$tmpTable = explode(" JOIN ",str_ireplace(array(' left', ' right', ' inner',' outer', ' cross'),array('',''),$tableAssoc));
			$arrTable = array();
			foreach($tmpTable as $t) {
				$arr_t = explode(" ON",str_replace(' on',' ON',$t));
				$tmp_t = trim($arr_t[0]);
				$alias = trim(strrchr( $tmp_t, ' '));
				if($alias) {
					$arrTable[] = $alias;
				}
				else {
					$arrTable[] = $tmp_t;
				}
			}
			$arrTable = array_unique($arrTable);

			/*
			if(!$select && $where) {
				$tmpWhere = explode(" AND ",str_replace(" OR "," AND ",$where));
				$tmpSelect = array();
				$chkJoin = array();
				foreach($tmpWhere as $w) {
					$w = explode("=",str_replace(array('>=','<=','>','<','!='),'=',$w));
					$w = $w[0];

					$tmpF = explode(".",$w);
					if(isset($tmpF[1]) && $tmpF[1]) {
						$field = $tmpF[1];
						$chkJoin[$tmpF[0]] = $tmpF[0];
					}
					else {
						$field = $tmpF[0];
					}

					if(!isset($tmpSelect[$field])) {
						$tmpSelect[$field] = $w;
					}
				}

			
				if(count($tmpSelect)<2 || count($chkJoin) < 2) {
					$key = array_keys($tmpSelect);
					
					$val = $tmpSelect[$key[0]];
					$select = str_replace(".".$key[0],"",$val).".*";
					}
				else {
					$select = implode(", ",$tmpSelect);
				}

			}
			*/

			//$select = str_ireplace("GROUP_ID",'"GROUP_ID"', $select);
			//$where = str_ireplace("GROUP_ID",'"GROUP_ID"', $where);

			$currentQuery = "SELECT ".($select ? $select : '*')." FROM ".$tableAssoc.($where ? " WHERE ".$where : '').($order ? " ORDER BY ".$order : '').($limit ? " LIMIT ".( ($page-1) * $limit ).",".$limit : '');

			$strQuery = $currentQuery;

			if(DEBUG!='0' && $this->ChkDebug!='0' && $tableAssoc!='CONFIGS') $this->DebugQry[$this->queryDebugSID][] = $strQuery;

			$DBresult = $this->parseExec($strQuery);
			//$ret = $this->fetchArray(&$DBresult);
			$count=0;
			while ($ret = $this->fetchAssoc($DBresult)) {
				$tmp = $ret;
				$result[] = $tmp;
				$count++;
			}
			return $result;
		}


		// <query 함수> -------------------------------------------------------------- made by Puppy, 2009.08.31
		//
		// [함수설명] : 쿼리를 던진다. 리턴값이 있는 경우 리턴
		// [Input Value]
		//   - $query : 쿼리문
		// [Output Value] : array
		function query($strQuery) {
			$DBresult = $this->parseExec($strQuery);
			if(DEBUG!='0' && $this->ChkDebug!='0') $this->DebugQry[$this->queryDebugSID][] = $strQuery;

			if(strpos($strQuery,"INSERT ")!==false || strpos($strQuery,"UPDATE ")!==false || strpos($strQuery,"DELETE ")!==false) {
				//OCICommit($this->conn);
				$value = $DBresult;
			}
			else {
				if($DBresult) {
					while ($ret = $this->fetchArray($DBresult)) {
							$value[]=$ret;
					}
				}else {
					$value = $DBresult;
				}
			}
			
			//$this->disconnect();
			return $value;
		}


		// <queryDebug 함수> -------------------------------------------------------------- made by Puppy, 2009.08.31
		//
		// [함수설명] : DEBUG 0이 아닐때 실행된 쿼리를 보여준다
		// [Input Value]
		//   - $query : 에러가 발생된 쿼리문
		// [Output Value] : array
		function queryDebug($errorQry=null) {
			if($errorQry || (isset($this->DebugQry[$this->queryDebugSID]) && !empty($this->DebugQry[$this->queryDebugSID]))) {


				$arrWord1 = array('insert ', 'update ', 'delete ', 'select ','where ', 'order by ', 'group by ', 'join ', 'left ', 'right ', 'outer ', 'cross ', 'as ', 'from ', 'values ', 'into ', 'set ', 'and ', 'or ');
				$arrWord2 = array(' count', ' max');
				$repWord = array();
				foreach($arrWord1 as $w) {
					$repWord[] = "<font color='#666666'>".strtoupper(trim($w))."</font> ";
				}
				foreach($arrWord2 as $w) {
					$repWord[] = " <font color='blue'>".strtoupper(trim($w))."</font>";
				}
				$this->DebugQry[$this->queryDebugSID] = str_ireplace(array_merge($arrWord1,$arrWord2), $repWord, $this->DebugQry[$this->queryDebugSID]);

				echo '<strong>Quries : </strong>';
				echo ' (UID/SID : <strong>' . $this->queryDebugSID . '</strong>)';
				echo "\n<pre style=\"font-family:tahoma;font-size:13px;\">\n";

				if($errorQry) $this->DebugQry[$this->queryDebugSID][] = "<font color='red'>".$errorQry."</font>";

				$var = print_r($this->DebugQry[$this->queryDebugSID], true);
				echo $var . "\n</pre>\n";
				unset($this->DebugQry[$this->queryDebugSID]);
			}
		}


		// <debug 함수> -------------------------------------------------------------- made by Puppy, 2009.08.31
		//
		// [함수설명] : 쿼리 결과를 보여줄지 여부 판단
		// [Input Value]
		//   - $type : 0(보이지않음), 2(보임)
		function debug($type='0') {
			$this->ChkDebug = $type;
		}




		/* --------------------------------------------------------------------- */





		/**
		 * MySQL DB에 연결
		 * @param $HOST : DB Server URI
		 * @param $USERID : USER ID
		 * @param $PASSWD : USER PASSWORD
		 * @param $DBNAME : Use DB NAME
		 * @return DB Connect Link
		 */
		function connect($HOST=DB_HOST,$USERID=DB_USERID,$PASSWD=DB_PASSWD, $DBNAME=DB_NAME){
			if($this->conn) return $this->conn;

			$this->conn = mysql_connect($HOST, $USERID, $PASSWD, true, 131074);
			mysql_select_db($DBNAME, $this->conn);
			mysql_query('set names utf8');

			return $this->conn;
		}

		// OCIParse() 함수는 conn인수를 사용하는 query를 해석한다. 질의(query)가 유효하면 구문(statement)를 리턴한다. 그렇지 않으면 FALSE를 리턴한다. 
		// query인수는 유효한 SQL 구문(statement)이나 PL/SQL블록이 될 수 있다.
		function parseExec ($qry) {
			//$this->stmt = OCIParse($this->conn,$qry);
			//$this->exec(null,$qry);
			$result = mysql_query($qry);
			if ($result){//결과 리턴값이 있을경우
				return $result;
			} else {
				//echo (mysql_error());
			//  DB insert 오류가 났을때 파일로그 남김   
				$file_name = "/home/mnsms/log/DB_ERRLOG_".date("Ymd").".log";
				$handle = @fopen($file_name,'a');

				@chmod($file_name, 0777);
				$str = "|".date("H:i:s")."|".@mysql_errno()."|".@mysql_error()."|".$qry."|\n";

				@fwrite($handle,$str);
				@fclose($handle);
				return $result;
			}
		}

		//쿼리 실행시 오류 발생하면 DB 접속을 끊는다.
//		function exec ($mode=OCI_DEFAULT,$qry=null) {
			//OCI_DEFAULT : 매번 실행시마다 자동 commit가 되지 않도록 함.
			//OCI_COMMIT_ON_SUCCESS : 자동 Commit Default옵션
//			@OCIExecute($this->stmt,$mode);
//			$this->error = ocierror($this->stmt);
//			if($this->error) $this->disconnect($qry);
//		}

		// descpriptor나 LOB locator를 위한 저장공간을 할당한다. 유효한 type인수값은 OCI_D_FILE, OCI_D_LOB, OCI_D_ROWID 이다. 
		// LOB descriptor은 load, save, savefile 메쏘드를 사용할수 있고, BFILE은 load 메쏘드만 사용가능하다.
//		function newDescriptor($type) {
//			$this->abstractData = OCINewDescriptor($this->conn,$type);
//		}

//		function freeDescriptor() {
//			OCIFreeDescriptor($this->abstractData);
//		}

		// 페치(fetch)한 컬럼을 사용자정의 PHP변수 에 할당한다. 
		// 물론, 오라클 유저는 select 구문에서 소문자 컬럼명을 쓸수 있지만, OCIDefineByName()함수의 Column-Name인수는 반드시 대문자로 적어야 한다. 
		// select 구문에서 존재하지도 않는 변수를 선언해도, 에러는 발생하지않을것이다!
//		function defineByName($upper,&$var) {
//			OCIDefineByName($this->stmt,$upper,&$var);
//		}

		// PHP 변수 variable에 오라클 위치보유자(Oracle placeholder)인 ph_name변수에 연계시킨다. 
		// 용도가 입력인지 출력인지는 실시간으로 결정될것이고, 충분한 저장 	공간이 할당될 필요가 있다. length인수 는 연계를 위한 최대 용량을 세팅한다. 
		// length인수를 -1로 세팅하면 OCIBindByName()함수는 연계를 위한 최대 용량을 variable의 현재 용량으로 사용할것이다.
//		function bindByName($qry,$place_holder,&$var,$length) {
//			$this->stmt = OCIParse($this->conn,$qry);
//			OCIBindByName($this->stmt,$place_holder,$var,$length);
//			if(!$this->stmt) {
//				$this->error = ocierror($this->stmt);
//				if($this->error) $this->disconnect();
//			} 
//			else {
//				@OCIExecute($this->stmt);
//				$this->error = ocierror($this->stmt);
//				if($this->error) $this->disconnect();
//			}
//		}

//		function bindByNameAbstract($place_holder,$type) {
//			OCIBindByName($this->stmt,$place_holder,&$this->abstractData,-1,$type);
//			return $this->abstractData;
//		}

		//다음 열(SELECT 구문을 위해)을 내부 결과 버퍼(internal result-buffer)에 페치한다.
//		function fetch() {
//			return OCIFetch($this->stmt);
//		}

		//  현재 열의 컬럼 column에 대한 데이터를 리턴한다. 
		// OCIResult()함수는 추상형데이터타입(ROWID, LOB, FILE)을 제외한 문자열로서 모든 것을 리턴할 것이다.
//		function result($i) { //인덱스는 0이아닌 1부터 시작
//			return OCIresult($this->stmt,$i);
//		}

		// 다음열(SELECT 구문을 위해)을 result배열에 페치한다. OCIFetchInto()함수는 result 변수의 이전값을 덮어 쓸것이다. 
		// 기본적으로 result변수는 NULL이 아닌 모든 컬럼의 일차원 배열을 포함할것이다.
		//function fetchInto(&$col,$mode=OCI_ASSOC) {
		//	return OCIFetchInto($this->stmt,&$col,$mode);
		//}

		function fetchArray($result)
		{
			$aResult = @mysql_fetch_array($result);
			return $aResult;
		}
		function fetchAssoc($result)
		{
			$aResult = @mysql_fetch_assoc($result);
			return $aResult;
		}
//		function fetchIntoClob(&$col,$mode=OCI_RETURN_LOBS) {
//			return OCIFetchInto($this->stmt,&$col,$mode);
//		}

		//레코드수 알아내기
		function numRows($result) {
			$rownum = @mysql_num_rows($result);
			return $rownum;
		}

		//컬럼의 갯수를 리턴한다.
//		function numCols() {
//			return OCINumCols($this->stmt);
//		}

		//컬럼의 이름을 리턴한다
//		function columnName($index) {
//			return OCIColumnName($this->stmt,$index);
//		}

		//결과 컬럼이 널(NULL)인지 테스트한다
//		function columnisNull($index) {
//			return OCIColumnIsNull($this->stmt,$index);
//		}

		//컬럼의 데이터 타입을 리턴한다
//		function columnType($index) {
//			return OCIColumnType($this->stmt,$index);
//		}

		//결과 컬럼 사이즈를 리턴한다
//		function columnSize($index) {
//			return OCIColumnSize($this->stmt,$index);
//		}

		//OCI 구문(statement)의 타입을 리턴한다
//		function StatementType() {
//			return OCIStatementType($this->stmt);
//		}

		// update같은 구문에 의해 적용되어진 열의 갯수를 리턴한다. 이 함수는 select 가 리턴할 열의 갯수를 말해 주진 않을 것이다!
//		function rowCount() {
//			return OCIRowCount($this->stmt);
//		}

//		function commitauto() {
//			OCICommit($this->conn);
//		}

		//구문(statement)에 연관된 모든 자원을 해제한다.
		//function parseFree() {
		//	OCIFreeStatement($this->stmt);
		//}

//		function disconnect($errorqry=null) {
//			if($this->error) {
//				$this->queryDebug($errorqry);
//				//OciRollBack($this->conn);
//				die("<font color=red>ROLLBACK OCCURRED!!".$this->error["message"]."</font>");
//			} 
//			else {
//				$this->queryDebug();
				//OCICommit($this->conn);
//			}
			//OCILogoff($this->conn);
//			global $_aDBConnectionSet;
//			$k = "";
//			$v = "";

//			if (count($_aDBConnectionSet))
//			{
//				foreach($_aDBConnectionSet as $k=>$v)
//				{
//					if ($v)
//					{
//						unset($_aDBConnectionSet[$k]);
//						@mysql_close($v);
//					}
//				}
//			}
//		}
		/**
		 * 객체 파괴시 DB와의 모든 연결을 끊는다.
		 *
		 */
		function __destruct()
		{
			$this->disconnect();
		}
		function disconnect()
		{
			$this->queryDebug($errorqry);
			@mysql_close(DB_SERVERNAME);
		}
}
if(isset($_GET['debug']) && $_GET['debug'] == 'on') {
	define( 'DEBUG', '1' ); //디버그on
}
else {
	define( 'DEBUG', '0' ); //디버그off
}
$DB	= new DB;
?>

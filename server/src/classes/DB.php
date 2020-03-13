<?php

	class DB{
		public function connect(){
			$config = parse_ini_file("../src/config.ini");
			$dbhost = $config["server"];
			$dbname = $config['database'];
			$dbuser = $config['user'];
			$dbpassword = $config['password'];

			$mysql_connect_str = "mysql:host=$dbhost;dbname=$dbname";
			$conn = $dbconnection = new PDO($mysql_connect_str, $dbuser, $dbpassword);
			$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			return $conn;
		}

		public function showConfig(){
			print_r(parse_ini_file("../src/config.ini"));
		}

		public static function openQuery($sql){
			try{
				$db = new DB();
				$db = $db->connect();
				$stmt = $db->query($sql);
				$rows = $stmt->fetchAll(PDO::FETCH_OBJ);
				$db = null;
				return $rows;
			}catch(PDOException $e){
				echo '{"error":{"text": '. $e->getMessage() .'}}' ;
				throw $e;
			}
		}

		public static function executeSQL($sql){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				$int = $db->prepare($sql)->execute();
				$db->commit();
			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}

		public static function GUID(){

	        mt_srand((double)microtime()*10000);//optional for php 4.2.0 and up.
	        $charid = strtoupper(md5(uniqid(rand(), true)));
	        $hyphen = chr(45);// "-"
	        $uuid = substr($charid, 0, 8).$hyphen
	            .substr($charid, 8, 4).$hyphen
	            .substr($charid,12, 4).$hyphen
	            .substr($charid,16, 4).$hyphen
	            .substr($charid,20,12);
	        return $uuid;
		}

		public static function paginateSQL($sql, $limit = 10, $page = 1) {
		    if ( $limit == 0 ) {
		        return $sql;
		    } else {
		        $sql = $sql . " LIMIT " . ( ( $page - 1 ) * $limit ) . ", $limit";
				return $sql;
		    }
		}

		public static function getRecordCount($sql){
			$sql = "select count(*) as total from (" . $sql . ") as t";
			$data = DB::openQuery($sql);
			return $data[0]->total;
		}

		public static function paginateQuery($sql, $limit = 10, $page = 1){

			$obj = new stdClass();
			$obj->totalrecord = static::getRecordCount($sql);

			$sql = static::paginateSQL($sql, $limit, $page);
			$obj->data = DB::openQuery($sql);

			return $obj;
		}


	}

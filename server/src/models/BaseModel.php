<?php
	require_once '../src/classes/DB.php';

	class BaseModel{
		public static function getFields(){
			return array("id");
		}

		public static function getTableName(){
			$str = get_called_class();
			$str = str_replace("Model","",$str);
			$str = strtolower($str);
			return $str;
		}

		public static function retrieve($id){
			$obj = DB::openQuery('select * from '.static::getTableName().' where id='.$id);
			if (isset($obj[0])) return $obj[0];
		}


		public static function retrieveList($filter=''){
			$sql = 'select * from '.static::getTableName().' where 1=1 ';
			if ($filter<>''){
				$sql = $sql .' and '. $filter;
			}
			$obj = DB::openQuery($sql);
			return $obj;
		}

		public static function generateSQLInsert($obj){
			$classname = get_called_class();
			try{
				if ($obj == null){
					throw new Exception("[BaseModel] Object is null \n");
				}
				$sql = "";
				$strvalue = "";
				$fields = static::getFields();
				foreach ($fields as $field) {
					if ($field == "uid") {
						$obj->uid = DB::GUID();
						// if (!isset($obj->uid)){
						// 	$obj->uid = DB::GUID();
						// }else if (($obj->uid == null) || ($obj->uid == '')){
						// 	$obj->uid = DB::GUID();
						// }
					}

					if (!isset($obj->{$field})) continue;

					if ($sql<>""){
						$sql = $sql . ",";
						$strvalue = $strvalue . ",";
					}
				    $sql = $sql. $field;
					// if (!isset($obj->{$field}))
					// 	throw new Exception("undeclared property $field on object $classname", 1);
					$strvalue = $strvalue. "'". $obj->{$field} ."'";
				}
				$sql = "insert into ". static::getTableName() . "(" . $sql .")";
				$sql = $sql. "values(" . $strvalue . ");";
				return $sql;
			}catch(Exception $e){
				throw $e ;
			}
		}

		public static function generateSQLUpdate($obj){
			$strvalue = "";
			$fields = static::getFields();
			$classname = get_called_class();
			foreach ($fields as $field) {
				if ($field == "uid") continue;
				if (!isset($obj->{$field})) continue;
				// if (!isset($obj->{$field}))
				// 	throw new Exception("undeclared property $field on object $classname", 1);

				if ($strvalue<>""){
					$strvalue = $strvalue . ",";
				}

				$strvalue = $strvalue. $field ." = '". $obj->{$field} ."'";
			}
			$sql = "update ". static::getTableName() . " set " . $strvalue;
			$sql = $sql. " where id = " . $obj->id . ";";
			return $sql;
		}

		public static function isNewTransaction($obj){
			$do_insert = !isset($obj->id);
			if (!$do_insert){
				$do_insert = ($obj->id <= 0);
			};
			return $do_insert;
		}

		public static function setIDByUID($obj){
			$obj->id = 0; //default insert, sampai ditemukan UID di DB
			// $obj->name = "xxx";

			$hasUID = false;
			$fields = static::getFields();
			foreach ($fields as $field) {
				if ($field == "uid") $hasUID = true;
			}
			if (!$hasUID) return; //hanya id yg dicek

			if (!isset($obj->uid))	return;
			if (($obj->uid == null) || ($obj->uid == '')) return;

			$sql = "select id from ".static::getTableName()." where uid= '" .$obj->uid."'";


			$dbobj = DB::openQuery($sql);
			if (isset($dbobj[0])) {
				if (isset($dbobj[0]->id)){
					$obj->id = $dbobj[0]->id;
				}
			}
		}

		public static function generateSQL($obj){
			if (isset($obj->restclient)){
				if ($obj->restclient){
					static::setIDByUID($obj);
				}
			}

			if (static::isNewTransaction($obj)) {
				return static::generateSQLInsert($obj);
			}else{
				return static::generateSQLUpdate($obj);
			}
		}

		public static function generateSQLDelete($filter){
			return "delete from " . static::getTableName() . " where " . $filter .";";
		}

		public static function saveObjToDB($obj, $db){
			// $sql = static::generateSQL($obj);
			try {
				$sql = static::generateSQL($obj);
				$int = $db->prepare($sql)->execute();
				if (static::isNewTransaction($obj)){
					$obj->id = $db->lastInsertId();
				}
			} catch (Exception $e) {
				echo $sql;
				// $db->rollback(); //handle rollback diluar
				throw $e;
			}
		}


		public static function getIDFromUID($uid){
			$sql = "select id from ".static::getTableName()." where uid= '" .$uid."'";
			$dbobj = DB::openQuery($sql);
			if (isset($dbobj[0])) {
				if (isset($dbobj[0]->id)){
					return $dbobj[0]->id;
				}
			}
		}
		//master detail example :
		// public static function saveToDB($obj){
		// 	$db = new DB();
		// 	$db = $db->connect();
		// 	$db->beginTransaction();
		// 	try {
		// 		if (!static::isNewTransaction($obj)){
		// 			$sql = ModelUnits::generateSQLDelete("company_id=". $obj->id);
		// 			$db->prepare($sql)->execute();
		// 		}
		// 		static::saveObjToDB($obj, $db);
		// 		foreach($obj->items as $item){
		// 			$item->company_id = $obj->id;
		// 			ModelUnits::saveObjToDB($item, $db);
		// 		}
		// 		$db->commit();
		// 		$db = null;
		// 	} catch (Exception $e) {
		// 		$db->rollback();
		// 		throw $e;
		// 	}
		// }


	}

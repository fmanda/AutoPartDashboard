<?php
	require_once '../src/models/BaseModel.php';

	class ModelUsers extends BaseModel{
		public static function getFields(){
			return array(
				"username", "password"
			);
		}

		public static function retrieveByUser($username){
			$obj = DB::openQuery("select * from ".static::getTableName()." where username='".$username."'");
			if (isset($obj[0])) return $obj[0];
		}

	}

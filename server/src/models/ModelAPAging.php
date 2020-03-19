<?php
	require_once '../src/models/BaseModel.php';

	class ModelAPAging extends BaseModel{
		public static function getFields(){
			return array(
				"projectcode", "current", "range1", "range2", "range3", "range4", "total"
			);
		}

		public static function saveToDB($obj){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				static::saveObjToDB($obj, $db);
				$db->commit();
				$db = null;
			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}

		public static function deletePeriod($projectcode){
			$str = static::generateSQLDelete(
				"projectcode='". $projectcode . "'"
			);
			DB::executeSQL($str);
		}


    public static function saveBatch($objs){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
        foreach ($objs as $obj) {
          static::saveObjToDB($obj, $db);
  			}
				$db->commit();
				$db = null;
			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}
	}

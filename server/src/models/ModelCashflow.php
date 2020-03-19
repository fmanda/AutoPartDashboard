<?php
	require_once '../src/models/BaseModel.php';

	class ModelCashflow extends BaseModel{
		public static function getFields(){
			return array(
				"projectcode", "monthperiod", "yearperiod", "purchase", "sales", "otherincome", "otherexpense"
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

		public static function deletePeriod($projectcode, $yearperiod, $monthperiod){
			$str = static::generateSQLDelete(
				"projectcode='". $projectcode .
				"' and monthperiod = ". $monthperiod.
				" and yearperiod = ". $yearperiod
			);
			DB::executeSQL($str);
		}

		public static function debugdeletePeriod($projectcode, $yearperiod, $monthperiod){
			$str = static::generateSQLDelete(
				"projectcode='". $projectcode .
				"' and monthperiod = ". $monthperiod .
				" and yearperiod = ". $yearperiod
			);
			return $str;
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

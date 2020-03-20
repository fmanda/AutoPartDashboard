<?php
	require_once '../src/models/BaseModel.php';

	class ModelProfitLoss extends BaseModel{
		public static function getFields(){
			return array(
				"projectcode", "monthperiod", "yearperiod", "reportname", "reportval", "reportgroup", "groupname", "reportidx"
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


    public static function saveBatch($objs, $projectcode, $yearperiod, $monthperiod){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				$sql = static::generateSQLDelete(
					"projectcode='". $projectcode .
					"' and monthperiod = ". $monthperiod.
					" and yearperiod = ". $yearperiod
				);
				$db->prepare($sql)->execute();
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

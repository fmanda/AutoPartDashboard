<?php
	require_once '../src/models/BaseModel.php';

	class ModelSalesPeriod extends BaseModel{
		public static function getFields(){
			return array(
				"projectcode", "transdate", "netsales", "cogs", "grossprofit"
			);
		}

    public static function saveBatch($objs, $projectcode, $startdate, $enddate){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				$sql = static::generateSQLDelete(
						"projectcode='". $projectcode .
						"' and transdate between  '". $startdate .
						"' and '". $enddate . "'"
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

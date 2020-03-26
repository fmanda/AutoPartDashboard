<?php
	require_once '../src/models/BaseModel.php';

	class ModelARAging extends BaseModel{
		public static function getFields(){
			return array(
				"projectcode", "current", "range1", "range2", "range3", "range4", "total"
			);
		}

    public static function saveBatch($objs, $projectcode){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				$sql = static::generateSQLDelete(
					" projectcode='". $projectcode . "'"
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

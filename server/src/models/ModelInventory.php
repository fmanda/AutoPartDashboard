<?php
	require_once '../src/models/BaseModel.php';

	class ModelInventory extends BaseModel{
		public static function getFields(){
			return array(
				"projectcode", "header_flag", "amount"
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

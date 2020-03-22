<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

require '../vendor/autoload.php';
require_once '../src/classes/DB.php';
require_once '../src/models/ModelInventory.php';


$app->get('/inventory', function ($request, $response, $args) {
	try{
		$sql = "select * from inventory";
    $data = DB::openQuery($sql);
    $json = json_encode($data);
    $response->getBody()->write($json);

		return $response->withHeader('Content-Type', 'application/json;charset=utf-8');
	}catch(Exception $e){
    $msg = $e->getMessage();
    $response->getBody()->write($msg);
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html');
	}
});

$app->post('/inventory/{projectcode}', function ($request, $response) {
	$json = $request->getBody();
	$projectcode = $request->getAttribute('projectcode');
	$obj = json_decode($json);
	try{
		ModelInventory::saveBatch($obj, $projectcode);
    $json = json_encode($obj);
    $response->getBody()->write($json);
    return $response->withHeader('Content-Type', 'application/json;charset=utf-8');
	}catch(Exception $e){
		$msg = $e->getMessage();
    $response->getBody()->write($msg);
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html');
	}

});

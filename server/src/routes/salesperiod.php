<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

require '../vendor/autoload.php';
require_once '../src/classes/DB.php';
require_once '../src/models/ModelSalesPeriod.php';

$app->get('/salesperiod', function ($request, $response) {
  $response->getBody()->write("Sales Period !");
  return $response;
});


$app->get('/salesperiod/{startdate}/{enddate}', function ($request, $response, $args) {
	try{
    $monthperiod = 0;
		$yearperiod = 0;
		if (isset($args['startdate'])) $monthperiod = $args['startdate'];
		if (isset($args['enddate'])) $yearperiod = $args['enddate'];
		$sql = "select * from salesperiod where transdate between '"
      .$monthperiod. "' and '" . $yearperiod . "'";


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

$app->post('/salesperiod', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		ModelSalesPeriod::saveBatch($obj);
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

$app->delete('/salesperiod/{projectcode}/{startdate}/{enddate}', function (Request $request, Response $response) {
	$projectcode = $request->getAttribute('projectcode');
  $startdate = $request->getAttribute('startdate');
  $enddate = $request->getAttribute('enddate');
	try{
		ModelSalesPeriod::deletePeriod($projectcode,$startdate,$enddate);
    return $response;
	}catch(Exception $e){
		$msg = $e->getMessage();
    $response->getBody()->write($msg);
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html');
	}
});
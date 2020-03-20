<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

require '../vendor/autoload.php';
require_once '../src/classes/DB.php';
require_once '../src/models/ModelCashflow.php';

$app->get('/cashflow', function ($request, $response) {
  $response->getBody()->write("cash flow");
  return $response;
});


$app->get('/cashflow/{yearperiod}/{monthperiod}', function ($request, $response, $args) {
	try{
    $monthperiod = 0;
		$yearperiod = 0;
		if (isset($args['monthperiod'])) $monthperiod = $args['monthperiod'];
		if (isset($args['yearperiod'])) $yearperiod = $args['yearperiod'];
		$sql = "select * from cashflow where monthperiod = ".$monthperiod." and yearperiod = " . $yearperiod ;


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

$app->post('/cashflow/{projectcode}/{yearperiod}/{monthperiod}', function ($request, $response) {
	$json = $request->getBody();
  $projectcode = $request->getAttribute('projectcode');
  $yearperiod = $request->getAttribute('yearperiod');
  $monthperiod = $request->getAttribute('monthperiod');
	$obj = json_decode($json);
	try{
		ModelCashflow::saveBatch($obj, $projectcode, $yearperiod, $monthperiod);
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


$app->get('/cashflow/{projectcode}/{yearperiod}/{monthperiod}', function (Request $request, Response $response) {
	$projectcode = $request->getAttribute('projectcode');
  $monthperiod = $request->getAttribute('monthperiod');
  $yearperiod = $request->getAttribute('yearperiod');
	try{
    $json = ModelCashflow::debugdeletePeriod($projectcode,$yearperiod,$monthperiod);
    $response->getBody()->write($json);
    return $response;
	}catch(Exception $e){
		$msg = $e->getMessage();
    $response->getBody()->write($msg);
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html');
	}
});

<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

require '../vendor/autoload.php';
require_once '../src/classes/DB.php';
require_once '../src/models/ModelProfitLoss.php';

$app->get('/profitloss', function ($request, $response) {
  $response->getBody()->write("Hello Sales !");
  return $response;
});


$app->get('/profitloss/{monthperiod}/{yearperiod}', function ($request, $response, $args) {
	try{
    $monthperiod = 0;
		$yearperiod = 0;
		if (isset($args['monthperiod'])) $monthperiod = $args['monthperiod'];
		if (isset($args['yearperiod'])) $yearperiod = $args['yearperiod'];
		$sql = "select * from profitloss where monthperiod = ".$monthperiod." and yearperiod = " . $yearperiod ;


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

$app->post('/profitloss', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		ModelProfitLoss::saveBatch($obj);
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

$app->delete('/profitloss/{projectcode}/{monthperiod}/{yearperiod}', function (Request $request, Response $response) {
	$projectcode = $request->getAttribute('projectcode');
  $monthperiod = $request->getAttribute('monthperiod');
  $yearperiod = $request->getAttribute('yearperiod');
	try{
		ModelProfitLoss::deletePeriod($projectcode,$monthperiod,$yearperiod);
    return $response;
	}catch(Exception $e){
		$msg = $e->getMessage();
    $response->getBody()->write($msg);
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html');
	}
});

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


$app->get('/profitloss/{yearperiod}/{monthperiod}', function ($request, $response, $args) {
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

$app->get('/netprofit/{startparam}/{endparamm}', function ($request, $response, $args) {
	try{
    $startparam = $args['startparam'];
		$endparam = $args['endparamm'];
		$sql = "select sum(reportval) as netprofit from profitloss where reportidx = 501 and  concat(yearperiod, '.', right(concat('0',monthperiod),2)) between '".$startparam."' and '".$endparam."'" ;
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

$app->post('/profitloss/{projectcode}/{yearperiod}/{monthperiod}', function ($request, $response) {
	$json = $request->getBody();
  $projectcode = $request->getAttribute('projectcode');
  $monthperiod = $request->getAttribute('monthperiod');
  $yearperiod = $request->getAttribute('yearperiod');

	$obj = json_decode($json);
	try{
		ModelProfitLoss::saveBatch($obj, $projectcode, $yearperiod, $monthperiod);
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

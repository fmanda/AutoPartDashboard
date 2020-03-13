<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

require '../vendor/autoload.php';
require_once '../src/classes/DB.php';


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
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

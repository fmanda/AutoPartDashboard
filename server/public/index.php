<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require __DIR__ . '/../vendor/autoload.php';

$app = AppFactory::create();

// $app->options('/{routes:.+}', function ($request, $response, $args) {
//     return $response;
// });
$app->addErrorMiddleware(true, false, false);

$app->add(function ($request, $handler) {
    $response = $handler->handle($request);
    return $response
            ->withHeader('Access-Control-Allow-Origin', '*')
            ->withHeader('Access-Control-Allow-Headers', 'X-Requested-With, Content-Type, Accept, Origin, Authorization')
            ->withHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS');
});


$app->get('/', function (Request $request, Response $response, $args) {
    $response->getBody()->write("Hello world !");
    return $response;
});

require '../src/routes/profitloss.php';
require '../src/routes/project.php';
require '../src/routes/salesperiod.php';
require '../src/routes/cashflow.php';
require '../src/routes/apaging.php';
require '../src/routes/inventory.php';
require '../src/routes/dashboard.php';
require '../src/routes/araging.php';
require '../src/routes/user.php';

$app->run();

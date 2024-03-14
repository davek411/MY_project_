package com.calculator.My_Project;

import Calculator.CalculateService;
import io.vertx.core.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ServiceBinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import io.vertx.core.http.HttpRequest;
public class InputVerticle extends AbstractVerticle {

  public static final Logger logger = LogManager.getLogger(InputVerticle.class);

    //private PgPool pgPool;

    @Override
    public void start(Promise<Void> startPromise) {

      logger.info("Input Verticle deployed");

      // vertx.setPeriodic(1000,this::updateTemperature);
//      InputService inputService;
//      MessageConsumer<JsonObject> consumer;// = vertx.eventBus().consumer("serviceAddress");
//      inputService = new InputServiceImpl(vertx);
////    databaseService = DatabaseService.create(vertx);
//      consumer = new ServiceBinder(vertx)
//        .setAddress("inputserviceAddress")
//        .register(InputService.class, inputService);
//      startPromise.complete();

      //ROUTING TO THE METHODS AND HANDLER FUNCTION
      Router router = Router.router(vertx);
      router.get("/calculate")
        .handler(this::handleCalculateRequest);


//    router.get("/add").handler(this::addData);
//    router.get("/subtract").handler(this::subtractData);
//    router.get("/multiply").handler(this::multiplyData);
//    router.get("/divide").hanhhhhheeeeeeeeehlllllllllr(this::divideData);
////
      // vertx.eventBus().consumer("Data added".this::datadded);


     vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(ok ->{
       startPromise.complete();
     })
       .onFailure( cause -> startPromise.fail("failed to establish connection to the request handlers"));



//      vertx.createHttpServer().requestHandler(router)
//        .listen(8080)
//        .onComplete(ok -> {
////logger.info("http server running :http://0.0.0.0:{}"+httpPort);
//          startPromise.complete();
//        })
//        .onFailure(startPromise::fail);
//      req.response()
//        .putHeader("content-type", "text/plain")
//        .end("Hello from Vert.x!");
//    }).listen(8888, http -> {
//      if (http.succeeded()) {
      //startPromise.complete();
//        System.out.println("HTTP server started on port 8888");
//      } else {
//        startPromise.fail(http.cause());
//      }
//    });

//      startPromise.complete();

    }

//  private void getalldata(RoutingContext routingContext)
//  {
//    logger.info("Requesting all daat from {}",routingContext.request.remoteAddress());
//  }

    private void handleCalculateRequest(RoutingContext routingContext) {
      String xValue = routingContext.request().getParam("x");
      String yValue = routingContext.request().getParam("y");
      logger.info("routing context: "+xValue);
      logger.info("Input parameters extracted from the request");
      // Send the input parameters to the calculation verticle
      JsonObject message = new JsonObject();

      message.put("xvalue", xValue);
      message.put("yvalue", yValue);
      message.put("messsage", "Values have been extracted and sent");

      CalculateService calculateService = CalculateService.createProxy(vertx, "calculateserviceAddress");
      Future<Double> calcFuture = calculateService.calculateandpass(message);
      logger.info(calcFuture.toString());
        calcFuture.onSuccess(ok -> {
            routingContext.response().putHeader("content-type", "text/plain").end("Result: " + ok);
            //auting
          })
          .onFailure(notOk -> { logger.error(notOk.getCause());
          routingContext.response().setStatusCode(500).end(" request did not fall through");});
//      EventBus eventBus = vertx.eventBus();
//
//      eventBus.request("calculate",message, ok -> {
//        if (ok.succeeded()) {
          //routingContext.response().putHeader("content-type", "text/plain").end("Result: " + ok.result().body());
//        } else {
//          logger.error(ok.cause());
//          routingContext.response().setStatusCode(500).end(" request did not fall through");
//
//        }
//      });

//      CalculateService calculateService = CalculateService.createProxy(vertx, "calculateserviceAddress");
//      calculateService.calculateandpass(message);

//      eventBus.request("calculate", xValue + "," + yValue, reply -> {
//        if (reply.succeeded()) {
//          routingContext.response()
//            .putHeader("Content-type", "text/plain")
//            .end("Result: " + reply.result().body());
//        } else {
//          routingContext.response().setStatusCode(500).end("jaggu bandar");
//        }
//      });

//    private void addData (RoutingContext context){
//      //logger.info("Processing HTTP request from " + context.request().absoluteURI());
//      String x_value = context.request().getParam("x");
//      String y_value = context.request().getParam("y");
//
//      int x = Integer.parseInt(x_value);
//      int y = Integer.parseInt(y_value);
//
//      int result = x + y;
////    pgPool.query("INSERT INTO computation_history (x, y, operation, result) VALUES ($1, $2, $3, $4)")
////      .execute(Tuple.of(x, y, "add", result))
////      .onSuccess(rows -> {
////        // Send response with computation result
////
////      })
////      .onFailure(err -> {
////        // Handle database insertion error
////        logger.error("Failed to insert computation result into the database: " + err.getMessage());
////        context.response().setStatusCode(500).end("Internal Server Error");
////      });
////  }
//      context.response()
//        .putHeader("Content-Type", "text/plain")
//        .end("Result: " + result);
//
//
//    }
//    private void subtractData (RoutingContext context){
//      //logger.info("Processing HTTP request from " + context.request().absoluteURI());
//      String x_value = context.request().getParam("x");
//      String y_value = context.request().getParam("y");
//
//      int x = Integer.parseInt(x_value);
//      int y = Integer.parseInt(y_value);
//
//      int result = x - y;
//
//
//      context.response()
//        .putHeader("Content-type", "text/plain")
//        .end("Result: " + result);
//    }
//    private void divideData (RoutingContext context){
//      //logger.info("Processing HTTP request from " + context.request().absoluteURI());
//      String x_value = context.request().getParam("x");
//      String y_value = context.request().getParam("y");
//
//      int x = Integer.parseInt(x_value);
//      int y = Integer.parseInt(y_value);
//
//      int result = x / y;
//
//
//      context.response()
//        .putHeader("Content-type", "text/plain")
//        .end("Result: " + result);
//    }
//    private void multiplyData (RoutingContext context){
//      //logger.info("Processing HTTP request from " + context.request().absoluteURI());
//      String x_value = context.request().getParam("x");
//      String y_value = context.request().getParam("y");
//
//      int x = Integer.parseInt(x_value);
//      int y = Integer.parseInt(y_value);
//
//      int result = x * y;
//
//
//      context.response()
//        .putHeader("Content-type", "text/plain")
//        .end("Result: " + result);
//    }

      // void putHeader(String s, String contentType) {

//  private JsonObject createPayload() {
//    return new JsonObject()
//      .put("uuid",uuid)
//      .put("temperatur",temperature)
//      .put("timestamp",System.currentTimeMillis());
//  }

//  private void updateTemperature(Long id) {
//    temperature = temperature + (random.nextGaussian()/2.0d);
//    logger.info("Temperature updated: {}" + temperature);
//
//    vertx.eventBus()
//      .publish("temperature.updates", createPayload());
//
//  startPromise.complete();
//  }


    }
  }




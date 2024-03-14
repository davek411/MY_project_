package Calculator;

import Database.DatabaseService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;
//import tinkering.DatabaseService;
//import tinkering.DatabaseServiceImpl;

public class CalculatorVerticle extends AbstractVerticle {

  private CalculateService calculateService;

  private static final Logger logger = LoggerFactory.getLogger(CalculatorVerticle.class);

  public void start(Promise<Void> startPromise) {
    logger.info("Calculator verticle deployed");
   // vertx.eventBus().consumer()
//    vertx.eventBus().consumer("calculate", extractor ->
//   {
//


     MessageConsumer<JsonObject> consumer;// = vertx.eventBus().consumer("serviceAddress");
     calculateService = new CalculateServiceImpl(vertx);
//    databaseService = DatabaseService.create(vertx);
     consumer = new ServiceBinder(vertx)
       .setAddress("calculateserviceAddress")
       .register(CalculateService.class, calculateService);
     startPromise.complete();



    DatabaseService databaseService = DatabaseService.createProxy(vertx, "databaseserviceAddress");


//    JsonObject databasepayload = new JsonObject();
//    databasepayload.put("expression", expression);
//    databasepayload.put("result", result);

    vertx.eventBus().consumer("payload for database", message -> {
        JsonObject data = new JsonObject();
      logger.info("message.body()"+ message.body());

      data = (JsonObject) message.body();

      databaseService.receiveandstore(data);

    });
////      logger.debug("inside consume..");
////      String[] values = ((String) message.body()).split(",");
////      int x = Integer.parseInt(values[0]);
////      int y = Integer.parseInt(values[1]);
////      System.out.println("x: "+x);
////      System.out.println("y: "+y);
////      int result = x + y; // Perform calculation (you can modify this to perform any operation)
////
////      // Reply back with the result
////      message.reply(result);
////    });









//
//
//
//
//
//
//      JsonObject data = new JsonObject();
//        data = (JsonObject) extractor.body();
//
//      String xValue = data.getString("xvalue");
//      String yValue = data.getString("yvalue");
//      String messageString = data.getString("message");
//
//
//      double x = Double.parseDouble(xValue);
//      double y = Double.parseDouble(yValue);
//      System.out.println("x: "+ xValue);
//      System.out.println("y: "+ yValue);
//      double result = x + y; // Perform calculation (you can modify this to perform any operation)
//      // Reply back with the result
//      extractor.reply(result);
//      String expression = String.format("%.2f + %.2f = %.2f", x, y,x+y);
//
////    });
////    vertx.eventBus().consumer("calculate", message -> {
////      logger.debug("inside consume..");
////      String[] values = ((String) message.body()).split(",");
////      int x = Integer.parseInt(values[0]);
////      int y = Integer.parseInt(values[1]);
////      System.out.println("x: "+x);
////      System.out.println("y: "+y);
////      int result = x + y; // Perform calculation (you can modify this to perform any operation)
////
////      // Reply back with the result
////      message.reply(result);
////    });
//   JsonObject databasepayload = new JsonObject();
//   databasepayload.put("expression", expression);
//     databasepayload.put("result", result);
//
//
//     vertx.eventBus().request("databaselinkaddress", databasepayload );
//
//    startPromise.complete();
  }
}


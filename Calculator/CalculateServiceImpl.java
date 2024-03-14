package Calculator;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public  class CalculateServiceImpl implements CalculateService
{

  private final Vertx vertx;

  public CalculateServiceImpl(Vertx vertx) {
    this.vertx = vertx;
  }




 // public Future<Double>
  public Future<Double> calculateandpass(JsonObject message) {


//    vertx.eventBus().consumer("calculate", extractor ->
//    {
//      JsonObject data = new JsonObject();
//      data = (JsonObject) extractor.body();

      String xValue = message.getString("xvalue");
      String yValue = message.getString("yvalue");
      String messageString = message.getString("message");


      double x = Double.parseDouble(xValue);
      double y = Double.parseDouble(yValue);
      System.out.println("x: "+ xValue);
      System.out.println("y: "+ yValue);
      double result = x + y; // Perform calculation (you can modify this to perform any operation)
      // Reply back with the result
//    routingContext.response().putHeader("content-type", "text/plain").end("Result: " + ok.result().body());

//      extractor.reply(result);
      String expression = String.format("%.2f + %.2f = %.2f", x, y,x+y);

      JsonObject databasepayload = new JsonObject();
      databasepayload.put("expression", expression);
      databasepayload.put("result", result);


          EventBus eventBus = vertx.eventBus();
          eventBus.request("payload for database",databasepayload);

    System.out.println(expression);

      //vertx.eventBus().request("databaselinkaddress", databasepayload );

//
//    });
      return Future.succeededFuture(result);
      //catch

  }
}

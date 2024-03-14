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



      String xValue = message.getString("xvalue");
      String yValue = message.getString("yvalue");
      String messageString = message.getString("message");


      double x = Double.parseDouble(xValue);
      double y = Double.parseDouble(yValue);
      System.out.println("x: "+ xValue);
      System.out.println("y: "+ yValue);
      double result = x + y; 
      String expression = String.format("%.2f + %.2f = %.2f", x, y,x+y);

      JsonObject databasepayload = new JsonObject();
      databasepayload.put("expression", expression);
      databasepayload.put("result", result);


          EventBus eventBus = vertx.eventBus();
          eventBus.request("payload for database",databasepayload);

    System.out.println(expression);



      return Future.succeededFuture(result);
      //catch

  }
}

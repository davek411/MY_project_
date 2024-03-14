package Calculator;

import Database.DatabaseService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;


public class CalculatorVerticle extends AbstractVerticle {

  private CalculateService calculateService;

  private static final Logger logger = LoggerFactory.getLogger(CalculatorVerticle.class);

  public void start(Promise<Void> startPromise) {
    logger.info("Calculator verticle deployed");

     MessageConsumer<JsonObject> consumer;// = vertx.eventBus().consumer("serviceAddress");
     calculateService = new CalculateServiceImpl(vertx);
     consumer = new ServiceBinder(vertx)
       .setAddress("calculateserviceAddress")
       .register(CalculateService.class, calculateService);
     startPromise.complete();



    DatabaseService databaseService = DatabaseService.createProxy(vertx, "databaseserviceAddress");




    vertx.eventBus().consumer("payload for database", message -> {
        JsonObject data = new JsonObject();
      logger.info("message.body()"+ message.body());

      data = (JsonObject) message.body();

      databaseService.receiveandstore(data);

    });

  }
}


package com.calculator.My_Project;

import Calculator.CalculatorVerticle;
import Database.DatabaseVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class Main {
  public static void main(String[] args) {

    final Logger logger = LoggerFactory.getLogger(Main.class);
    logger.debug("inside main");
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new InputVerticle());
    logger.info ("Input verticle Deployed");
    vertx.deployVerticle(new CalculatorVerticle());
    logger.info ("Calculator verticle Deployed");

   vertx.deployVerticle(new DatabaseVerticle());

//    logger.info ("Database verticle Deployed");

//    vertx.deployVerticle(new DatabaseVerticle());


    //
    //ewffff
    //
    //
    // vvvvvvvvvvvvvvvvve(new MainVerticle2());


    // dddtttttttttttttttt
//      .<JsonObject> consumer("temperature.updates",mee -> {
//        lOkkkkOnfo(">>>{}" + message.body().encodepRETTILYY;
  }
}

package Database;

import Calculator.CalculateServiceVertxProxyHandler;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class DatabaseServiceImpl implements DatabaseService {
  private final Vertx vertx;

  PgPool client;
  final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);

 // private CalculateServiceVertxProxyHandler client;

  public DatabaseServiceImpl(Vertx vertx, PgPool client) {

    this.vertx = vertx;
    this.client = client;
  }


  public void receiveandstore (JsonObject data)
  {


    logger.info(" inside database");
    // Configure connection options
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setDatabase("postgres")
      .setUser("postgres")
      .setPassword("passmethrough100");

    // Configure pool options
    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(1);
    logger.info("before pool..");
    // Create client pool
    client = PgPool
      .pool(vertx, connectOptions, poolOptions);


    //vertx.eventBus().consumer("databaselinkaddress", databasextractor ->
   // {
     // JsonObject dataremove = new JsonObject();
     // dataremove = (JsonObject) databasextractor.body();

      String expression = data.getString("expression");
      double result = data.getDouble("result");
     // String messageString = dataremove.getString("message");
      String result2 = Double.toString(result);
      // Log pool creation status

      // Listen for events from other verticles
//      vertx.eventBus().consumer("computatiod, message -> {
//        String result = (String) message.body();
      System.out.println("hey: ");
      // Replace "your-table-name" with your actual table name
      String sql = "INSERT INTO user_expressions (result, expression) VALUES ($1,$2)";
      Tuple params = Tuple.of(result, expression);

      // Execute the query with the result

      client.withConnection(connection -> {
        connection.preparedQuery(sql).execute(params)
          .compose(res1 -> {
            logger.info(res1);
            return connection.query("select * from user_expressions").execute();
          })
          .compose(res2 -> {

            RowSet<Row> rowSet = res2;
            for (Row row : rowSet) {
              logger.info(row.toJson());
            }
            return connection.query("select result from user_expressions").execute();
          })
          .onComplete(ar -> {
            if (ar.succeeded()) {
              RowSet<Row> rowSet = ar.result();
              for (Row row : rowSet) {
                logger.info(row.toJson());
              }
//                logger.info(ar.result().next());
              System.out.println("Data inserted successfully!");
            } else {
              System.err.println("Failed to insert data: " + ar.cause().getMessage());
            }
          });

        return null;
      });

//      logger.info("completed query");

    }


    public void stop () {
    try {
      client.close();
      logger.info("Database connection pool closed successfully.");
    } catch (Exception e) {
      logger.error("Failed to close database connection pool: " + e.getMessage());
    }
  }
  }



















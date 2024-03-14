package Calculator;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;


@ProxyGen
@VertxGen
public interface CalculateService {

  @GenIgnore
  static CalculateService createProxy(Vertx vertx, String address) {
    return new CalculateServiceVertxEBProxy(vertx, address);
  }

  //void save(String collection, JsonObject document);
  Future<Double> calculateandpass(JsonObject message);

//  void calculateandpass(String message);
//}
}

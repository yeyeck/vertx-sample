package com.yeyeck.vertx;

import com.yeyeck.vertx.router.ArticleRouter;
import com.yeyeck.vertx.router.UserRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // 创建一个 router
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    // 创建一个http server 并将所有请求交给 router 来管理
    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
    // 在router上挂载url
    new ArticleRouter().init(router);
    new UserRouter().init(router);

  }
}

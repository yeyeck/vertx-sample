package com.yeyeck.vertx.router;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class UserRouter extends BasicRouter{

  @Override
  public void init(Router router) {
    router.get("/user/:id").handler(this::getById);
  }

  private void getById(RoutingContext routingContext) {
    Integer id = Integer.parseInt(routingContext.request().getParam("id"));
    routingContext.response().end(new JsonObject().put("id", id).put("name", "小明").toString());
  }
}

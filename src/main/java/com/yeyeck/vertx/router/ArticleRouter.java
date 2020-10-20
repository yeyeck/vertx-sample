package com.yeyeck.vertx.router;

import com.yeyeck.vertx.router.fo.ArticleFo;
import com.yeyeck.vertx.service.IArticleService;
import com.yeyeck.vertx.service.impl.ArticleServiceImpl;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ArticleRouter extends BasicRouter{

  private final IArticleService articleService = new ArticleServiceImpl();

  @Override
  public void init(Router router) {
    router.post("/article").handler(this::post);
    router.get("/article/:id").handler(this::get);
    router.put("/article/:id").handler(this::update);
    router.delete("/article/:id").handler(this::deleteArticle);
  }

  private void post(RoutingContext routingContext) {
    JsonObject jsonObject = routingContext.getBodyAsJson();
    ArticleFo articleFo = new ArticleFo(jsonObject);
    articleService.addArticle(articleFo).onSuccess(res -> {
      routingContext.response().setStatusCode(200).end(String.valueOf(res));
    }).onFailure(throwable -> {
      routingContext.response().setStatusCode(500).end(throwable.toString());
    });
  }
  private void get(RoutingContext routingContext) {
    Integer id = Integer.parseInt(routingContext.request().getParam("id"));
    articleService.getById(id).onSuccess(article -> {
      routingContext.response().setStatusCode(200).end(article.toJson().toString());
    }).onFailure(throwable -> {
      routingContext.response().setStatusCode(500).end(throwable.toString());
    });
  }

  private void update(RoutingContext routingContext) {
    Integer id = Integer.parseInt(routingContext.request().getParam("id"));
    JsonObject jsonObject = routingContext.getBodyAsJson();
    articleService.update(id, new ArticleFo(jsonObject))
      .onSuccess(res -> {
        routingContext.response().setStatusCode(200).end(String.valueOf(res));
      }).onFailure(throwable -> {
      routingContext.response().setStatusCode(500).end(throwable.toString());
    });;
  }
 private void deleteArticle(RoutingContext routingContext) {
   Integer id = Integer.parseInt(routingContext.request().getParam("id"));
   articleService.deleteById(id)
     .onSuccess(res -> {
       routingContext.response().setStatusCode(200).end(String.valueOf(res));
     }).onFailure(throwable -> {
     routingContext.response().setStatusCode(500).end(throwable.toString());
   });;
 }

}

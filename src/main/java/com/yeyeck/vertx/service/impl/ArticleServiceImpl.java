package com.yeyeck.vertx.service.impl;

import com.yeyeck.vertx.dao.IArticleDap;
import com.yeyeck.vertx.dao.impl.ArticleDaoImpl;
import com.yeyeck.vertx.pojo.Article;
import com.yeyeck.vertx.router.fo.ArticleFo;
import com.yeyeck.vertx.service.IArticleService;
import com.yeyeck.vertx.util.SqlUtil;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.sqlclient.SqlConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticleServiceImpl implements IArticleService {

  private final IArticleDap articleDao = new ArticleDaoImpl();

  @Override
  public Future<Integer> addArticle(ArticleFo articleFo) {
    Promise<Integer> promise = Promise.promise();
    Article article = articleFo.toArticle();
    SqlUtil.getConnection().compose(connection -> articleDao.add(connection, article))
      .onSuccess(promise::complete)
      .onFailure(promise::fail);
    return promise.future();
  }

  @Override
  public Future<Article> getById(Integer id) {
    Promise<Article> promise = Promise.promise();
    SqlUtil.pool().getConnection(as -> {
      if (as.succeeded()) {
        SqlConnection connection = as.result();
        articleDao.getById(connection, id)
          .onSuccess(article -> {
            promise.complete(article);
            connection.close();
          })
          .onFailure(throwable -> {
            promise.fail(throwable);
            connection.close();
          });
      } else {
        promise.fail(as.cause());
      }
    });
    return promise.future();
  }

  @Override
  public Future<Integer> update(Integer id, ArticleFo articleFo) {
    Promise<Integer> promise = Promise.promise();
    Article article = articleFo.toArticle();
    article.setId(id);
    SqlUtil.pool().getConnection().onSuccess(connection -> {
      articleDao.update(connection, article).onSuccess(integer -> {
        connection.close();
        promise.complete(integer);
      }).onFailure(throwable -> {
        connection.close();
        promise.fail(throwable);
      });
    });
    return promise.future();
  }

  @Override
  public Future<Integer> deleteById(Integer id) {
    Promise<Integer> promise = Promise.promise();
    SqlUtil.getConnection().compose(connection -> articleDao.deleteById(connection, id))
      .onSuccess(promise::complete)
      .onFailure(promise::fail);
    return promise.future();
  }
}

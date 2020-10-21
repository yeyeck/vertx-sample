package com.yeyeck.vertx.dao.impl;

import com.yeyeck.vertx.dao.IArticleDao;
import com.yeyeck.vertx.pojo.Article;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;

public class ArticleDaoImpl implements IArticleDao {

  @Override
  public Future<Integer> add(SqlConnection connection, Article article) {
    Promise<Integer> promise = Promise.promise();
    String sql = "insert into t_article(title, abstract_text, content) values (?, ?, ?)";
    Tuple params = Tuple.of(article.getTitle(), article.getAbstractText(), article.getContent());
    connection.preparedQuery(sql)
      .execute(params, ar ->{
        if(ar.succeeded()) {
          RowSet<Row> rows = ar.result();
          Long lastInsertId = rows.property(MySQLClient.LAST_INSERTED_ID);
          promise.complete(lastInsertId.intValue());
        } else {
          promise.fail(ar.cause());
        }
      });
    return promise.future();
  }

  @Override
  public Future<Article> getById(SqlConnection connection, Integer id) {
    Promise<Article> promise = Promise.promise();
    String sql = "select id, title, abstract_text, content from t_article where id = ?";
    Tuple params = Tuple.of(id);
    connection.preparedQuery(sql)
      .execute(params, ar ->{
        if(ar.succeeded()) {
          RowSet<Row> rows = ar.result();
          Row row = rows.iterator().next();
          Article article = new Article(row);
          promise.complete(article);
        } else {
          promise.fail(ar.cause());
        }
      });
    return promise.future();
  }

  @Override
  public Future<Integer> update(SqlConnection connection, Article article) {
    Promise<Integer> promise = Promise.promise();
    String sql = "update t_article set title = ?, abstract_text = ?, content = ? where id = ?";
    Tuple params = Tuple.of(article.getTitle(), article.getAbstractText(), article.getContent(), article.getId());
    connection.preparedQuery(sql)
      .execute(params, ar ->{
        if(ar.succeeded()) {
          promise.complete(1);
        } else {
          promise.fail(ar.cause());
        }
      });
    return promise.future();
  }

  @Override
  public Future<Integer> deleteById(SqlConnection connection, Integer id) {
    Promise<Integer> promise = Promise.promise();
    String sql = "delete from t_article where id = ?";
    Tuple params = Tuple.of(id);
    connection.preparedQuery(sql)
      .execute(params, ar ->{
        if(ar.succeeded()) {
          promise.complete(1);
        } else {
          promise.fail(ar.cause());
        }
      });
    return promise.future();
  }

}

package com.yeyeck.vertx.dao;

import com.yeyeck.vertx.pojo.Article;
import io.vertx.core.Future;
import io.vertx.sqlclient.SqlConnection;

public interface IArticleDap {
  Future<Integer> add(SqlConnection connection, Article article);
  Future<Article> getById(SqlConnection connection, Integer id);
  Future<Integer> update(SqlConnection connection, Article article);
  Future<Integer> deleteById(SqlConnection connection, Integer id);
}

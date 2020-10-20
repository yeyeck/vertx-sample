package com.yeyeck.vertx.util;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnection;


public class SqlUtil {
  private static final MySQLPool pool;
  static {
    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
      .setHost("47.106.185.245")
      .setUser("root")
      .setPassword("cckk00522")
      .setPort(3306)
      .setDatabase("vertx");
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    pool = MySQLPool.pool(Vertx.vertx(), connectOptions, poolOptions);
  }
  private SqlUtil(){}

  public static MySQLPool pool() {
    return pool;
  }

  public static Future<SqlConnection> getConnection() {
    Promise<SqlConnection> promise = Promise.promise();
    pool.getConnection(ar -> {
      if (ar.succeeded()) {
        promise.complete(ar.result());
      } else {
        ar.cause().printStackTrace();
        promise.fail(ar.cause());
      }
    });
    return promise.future();
  }
}

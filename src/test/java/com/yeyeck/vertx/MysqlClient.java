package com.yeyeck.vertx;

import io.vertx.core.Promise;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MysqlClient {


  public static void main(String[] args) {
    MySQLConnectOptions mysqlOptions = new MySQLConnectOptions()
      .setHost("47.106.185.245")
      .setUser("root")
      .setPassword("cckk00522")
      .setPort(3306)
      .setDatabase("vertx");
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    MySQLPool pool = MySQLPool.pool(mysqlOptions, poolOptions);
    pool.getConnection().onSuccess(connection -> {
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("1: " + connection.hashCode());
    }).onSuccess(connection -> {
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("2: " + connection.hashCode());
    }).onSuccess(connection -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("3: " + connection.hashCode());
    }).onSuccess(connection ->
      pool.close().onSuccess(aVoid -> System.out.println("关闭连接池"))
    );

    pool.getConnection().compose(connection -> {
      Promise<Integer> promise = Promise.promise();
      promise.complete(1);
      return promise.future();
    }).onSuccess(integer -> {
      // todo
      System.out.println("hhh");
    });
    System.out.println("end");
  }
}

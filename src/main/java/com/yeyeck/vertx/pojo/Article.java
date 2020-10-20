package com.yeyeck.vertx.pojo;

import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import lombok.Data;

@Data
public class Article {
  private Integer id;
  private String title;
  private String abstractText;
  private String content;

  public Article(){}

  public Article(Row row) {
    this.id = row.getInteger("id");
    this.title = row.getString("title");
    this.content = row.getString("content");
    this.abstractText = row.getString("abstract_text");
  }

  public JsonObject toJson() {
    return new JsonObject().put("id", this.id)
      .put("title", this.title)
      .put("abstractText", this.abstractText)
      .put("content", content);
  }
}

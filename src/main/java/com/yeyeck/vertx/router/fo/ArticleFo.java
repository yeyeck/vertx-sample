package com.yeyeck.vertx.router.fo;

import com.yeyeck.vertx.pojo.Article;
import io.vertx.core.json.JsonObject;
import lombok.Data;

@Data
public class ArticleFo {
  private Integer id;
  private String title;
  private String abstractText;
  private String content;

  public ArticleFo(JsonObject jsonObject) {
    this.id = jsonObject.getInteger("id");
    this.title = jsonObject.getString("title");
    this.abstractText = jsonObject.getString("abstractText");
    this.content = jsonObject.getString("content");
  }

  public Article toArticle() {
    Article article = new Article();
    article.setId(this.id);
    article.setTitle(this.title);
    article.setAbstractText(this.abstractText);
    article.setContent(this.content);
    return article;
  }
}

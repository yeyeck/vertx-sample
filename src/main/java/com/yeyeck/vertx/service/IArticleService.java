package com.yeyeck.vertx.service;

import com.yeyeck.vertx.pojo.Article;
import com.yeyeck.vertx.router.fo.ArticleFo;
import io.vertx.core.Future;

public interface IArticleService {
  Future<Integer> addArticle(ArticleFo articleFo);
  Future<Article> getById(Integer id);
  Future<Integer> update(Integer id, ArticleFo articleFo);
  Future<Integer> deleteById(Integer id);
}

package repository;

import entity.Article;
import entity.ArticleId;
import entity.Comment;

import java.util.List;

public interface ArticleRepository {
  ArticleId generateId();

  List<Article> findAllArticles();

  Article findById(ArticleId articleId);

  ArticleId create(Article article);

  void update(Article article);

  void addComment(ArticleId articleId, Comment comment);

  void deleteComment(ArticleId articleId, Comment comment);

  void delete(ArticleId articleId);
}

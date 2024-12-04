package repository;

import entity.Article;
import entity.ArticleId;
import entity.Comment;
import repository.exceptions.ArticleIdDuplicatedException;
import repository.exceptions.ArticleNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryArticleRepository implements ArticleRepository {
  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<ArticleId, Article> articleMap = new ConcurrentHashMap<>();

  @Override
  public ArticleId generateId() {
    return new ArticleId(nextId.incrementAndGet());
  }

  @Override
  public List<Article> findAllArticles() {
    return new ArrayList<>(articleMap.values());
  }

  @Override
  public Article findById(ArticleId articleId) throws ArticleNotFoundException {
    Article article = this.articleMap.get(articleId);
    if (article == null) {
      throw new ArticleNotFoundException("Cannot find article by id=" + articleId.id());
    }
    return article;
  }

  @Override
  public ArticleId create(Article article) throws ArticleIdDuplicatedException {
    if (articleMap.get(article.id()) != null) {
      throw new ArticleIdDuplicatedException("Article with the given id already exists:" + article.id().id());
    }
    articleMap.put(article.id(), article);
    return article.id();
  }

  @Override
  public void update(Article article) throws ArticleNotFoundException {
    if (articleMap.get(article.id()) == null) {
      throw new ArticleNotFoundException("Cannot find article by id=" + article.id().id());
    }
    articleMap.put(article.id(), article);
  }

  @Override
  public void addComment(ArticleId articleId, Comment comment) throws ArticleNotFoundException {
    if (articleMap.get(articleId) == null) {
      throw new ArticleNotFoundException("Cannot find article by id=" + articleId.id());
    }
    Article articleToAdd = articleMap.get(articleId);
    List<Comment> articleComments = articleToAdd.comments();
    articleComments.add(comment);
    articleMap.put(articleId, articleToAdd.withComments(articleComments));
  }

  @Override
  public void deleteComment(ArticleId articleId, Comment comment) throws ArticleNotFoundException {
    if (articleMap.get(articleId) == null) {
      throw new ArticleNotFoundException("Cannot find article by id=" + articleId.id());
    }
    Article articleToAdd = articleMap.get(articleId);
    List<Comment> articleComments = articleToAdd.comments();
    articleComments.remove(comment);
    articleMap.put(articleId, articleToAdd.withComments(articleComments));
  }

  @Override
  public void delete(ArticleId articleId) throws ArticleNotFoundException {
    if (articleMap.remove(articleId) == null) {
      throw new ArticleNotFoundException("Cannot find article by id=" + articleId.id());
    }
  }
}

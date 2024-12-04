package service;

import entity.Article;
import entity.ArticleId;
import entity.Comment;
import entity.CommentId;
import repository.InMemoryArticleRepository;
import repository.InMemoryCommentRepository;
import repository.exceptions.ArticleIdDuplicatedException;
import repository.exceptions.ArticleNotFoundException;
import repository.exceptions.CommentIdDuplicatedException;
import repository.exceptions.CommentNotFoundException;
import service.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArticleService {
  private final InMemoryArticleRepository articleRepository;
  private final InMemoryCommentRepository commentRepository;

  public ArticleService(InMemoryArticleRepository articleRepository, InMemoryCommentRepository commentRepository) {
    this.articleRepository = articleRepository;
    this.commentRepository = commentRepository;
  }

  public List<Article> findAllArticles() {
    return articleRepository.findAllArticles();
  }

  public Article findArticleById(ArticleId articleId) throws ArticleFindException {
    try {
      return articleRepository.findById(articleId);
    } catch (ArticleNotFoundException e) {
      throw new ArticleFindException("Cannot find article by id=" + articleId.id(), e);
    }
  }

  public ArticleId createArticle(String name, Set<String> tags) throws ArticleCreateException {
    ArticleId articleId = articleRepository.generateId();
    Article article = new Article(articleId, name, tags, new ArrayList<>());
    try {
      articleRepository.create(article);
    } catch (ArticleIdDuplicatedException e) {
      throw new ArticleCreateException("Cannot create article", e);
    }
    return articleId;
  }

  public void deleteArticle(ArticleId articleId) throws ArticleDeleteException, CommentDeleteException {
    try {
      Article deleteArticle = articleRepository.findById(articleId);
      articleRepository.delete(articleId);
      for (Comment comment : deleteArticle.comments()) {
          commentRepository.delete(comment.commentId());
      }
    } catch (ArticleNotFoundException e) {
      throw new ArticleDeleteException("Cannot delete article with id=" + articleId.id(), e);
    }
  }

  public void updateArticle(ArticleId articleId, String name, Set<String> tags) throws ArticleUpdateException {
    try {
      Article articleToUpdate = articleRepository.findById(articleId);
      Article updatedArticle = articleToUpdate.withName(name).withTags(tags);
      articleRepository.update(updatedArticle);
    } catch (ArticleNotFoundException e) {
      throw new ArticleUpdateException("Cannot update article with id=" + articleId.id(), e);
    }
  }

  public CommentId createComment(String text, ArticleId articleId) throws CommentCreateException, ArticleFindException {
    CommentId commentId = commentRepository.generateId();
    Comment comment = new Comment(commentId, articleId, text);
    try {
      commentRepository.create(comment);
    } catch (CommentIdDuplicatedException e) {
      throw new CommentCreateException("Cannot create comment", e);
    }
    try {
      articleRepository.addComment(articleId, comment);
    } catch (ArticleNotFoundException e) {
      commentRepository.delete(commentId);
      throw new ArticleFindException("Cannot find article by id=" + articleId.id(), e);
    }
    return commentId;
  }

  public void deleteComment(CommentId commentId) throws CommentDeleteException{
    try {
      Comment deletingComment = commentRepository.findById(commentId);
      commentRepository.delete(commentId);
      articleRepository.deleteComment(deletingComment.articleId(), deletingComment);
    } catch (CommentNotFoundException e) {
      throw new CommentDeleteException("Cannot delete comment with id=" + commentId.id(), e);
    }
  }

}

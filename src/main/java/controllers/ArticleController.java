package controllers;

import controllers.responses.*;
import entity.Article;
import entity.ArticleId;
import entity.CommentId;
import controllers.requests.ArticleCreateRequest;
import controllers.requests.ArticleUpdateRequest;
import controllers.requests.CommentCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ArticleService;
import service.exceptions.*;
import spark.Response;
import spark.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;

import java.util.List;

public class ArticleController implements Controller {

  private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);


  private final Service service;
  private final ArticleService articleService;
  private final ObjectMapper objectMapper;

  public ArticleController(Service service, ArticleService articleService, ObjectMapper objectMapper) {
    this.service = service;
    this.articleService = articleService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    createArticle();
    deleteArticle();
    updateArticle();
    getArticle();
    getAllArticles();
    createComment();
    deleteComment();
  }

  private void createArticle() {
    service.post("/api/articles",
    (Request request, Response response) -> {
      response.type("application/json");
      String body = request.body();
      ArticleCreateRequest articleCreateRequest = objectMapper.readValue(body, ArticleCreateRequest.class);
      try {
        ArticleId articleId = articleService.createArticle(articleCreateRequest.name(), articleCreateRequest.tags());
        LOG.debug("Article created successfully: {}", articleId);
        response.status(201);
        return objectMapper.writeValueAsString(new ArticleCreateResponse(articleId));
      } catch (ArticleCreateException e) {
        LOG.warn("Error creating article", e);
        response.status(400);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      } catch (Exception e) {
        LOG.error("Unexpected error while creating article", e);
        response.status(500);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      }
    });
  }

  private void deleteArticle() {
    service.delete("/api/articles/:articleId",
    (Request request, Response response) -> {
      response.type("application/json");
      String idString = request.params("articleId");
      ArticleId articleId = new ArticleId(Long.parseLong(idString));
      try {
        articleService.deleteArticle(articleId);
        LOG.debug("Article delete successfully: {}", articleId);
        response.status(200);
        return objectMapper.writeValueAsString(new ArticleDeleteResponse(articleId));
      } catch (ArticleDeleteException e) {
        LOG.warn("Error deleting article: {}", articleId, e);
        response.status(404);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      } catch (Exception e) {
        LOG.error("Unexpected error while deleting article", e);
        response.status(500);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      }
    });
  }

  private void updateArticle() {
    service.put("/api/articles/:articleId",
    (Request request, Response response) -> {
      response.type("application/json");
      String idString = request.params("articleId");
      ArticleId articleId = new ArticleId(Long.parseLong(idString));
      String body = request.body();
      ArticleUpdateRequest articleUpdateRequest = objectMapper.readValue(body, ArticleUpdateRequest.class);
      try {
        articleService.updateArticle(articleId, articleUpdateRequest.name(), articleUpdateRequest.tags());
        LOG.debug("Article update successfully: {}", articleId);
        response.status(200);
        return objectMapper.writeValueAsString(new ArticleUpdateResponse(articleId));
      } catch (ArticleUpdateException e) {
        LOG.warn("Error updating article: {}", articleId, e);
        response.status(404);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      } catch (Exception e) {
        LOG.error("Unexpected error while updating article", e);
        response.status(500);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      }
    });
  }

  private void getArticle() {
    service.get("/api/articles/:articleId",
    (Request request, Response response) -> {
      response.type("application/json");
      String idString = request.params("articleId");
      ArticleId articleId = new ArticleId(Long.parseLong(idString));
      try {
        Article article = articleService.findArticleById(articleId);
        LOG.debug("Article get successfully: {}", articleId);
        response.status(200);
        return objectMapper.writeValueAsString(new ArticleGetResponse(article));
      } catch (ArticleFindException e) {
        LOG.warn("Error getting article: {}", articleId, e);
        response.status(404);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      } catch (Exception e) {
        LOG.error("Unexpected error while getting article", e);
        response.status(500);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      }
    });
  }

  private void getAllArticles() {
    service.get("/api/articles",
    (Request request, Response response) -> {
      response.type("application/json");
      try {
        List<Article> articles = articleService.findAllArticles();
        LOG.debug("The articles were successfully received in JSON format");
        response.status(200);
        return objectMapper.writeValueAsString(new AllArticlesGetResponse(articles));
      } catch (Exception e) {
        LOG.error("Unexpected error while getting all articles in JSON format", e);
        response.status(500);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      }
    });
  }

  private void createComment() {
    service.post("/api/comments",
    (Request request, Response response) -> {
      response.type("application/json");
      String body = request.body();
      CommentCreateRequest commentCreateRequest = objectMapper.readValue(body, CommentCreateRequest.class);
      try {
        CommentId commentId = articleService.createComment(commentCreateRequest.text(), new ArticleId(commentCreateRequest.articleId()));
        LOG.debug("Comment create successfully: {}", commentId);
        response.status(201);
        return objectMapper.writeValueAsString(new CommentCreateResponse(commentId));
      } catch (CommentCreateException e) {
        LOG.warn("Error creating comment", e);
        response.status(400);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      } catch (Exception e) {
        LOG.error("Unexpected error while creating comment", e);
        response.status(500);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      }
    });
  }

  private void deleteComment() {
    service.delete("/api/comments/:commentId",
    (Request request, Response response) -> {
      response.type("application/json");
      String idString = request.params("commentId");
      CommentId commentId = new CommentId(Long.parseLong(idString));
      try {
        articleService.deleteComment(commentId);
        LOG.debug("Comment delete successfully: {}", commentId);
        response.status(200);
        return objectMapper.writeValueAsString(new CommentDeleteResponse(commentId));
      } catch (CommentDeleteException e) {
        LOG.warn("Error deleting comment: {}", commentId, e);
        response.status(404);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      } catch (Exception e) {
        LOG.error("Unexpected error while deleting comment", e);
        response.status(500);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      }
    });
  }
}

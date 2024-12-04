import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ArticleController;
import controllers.responses.*;
import entity.Article;
import entity.ArticleId;
import entity.Comment;
import entity.CommentId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryArticleRepository;
import repository.InMemoryCommentRepository;
import service.ArticleService;
import spark.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndTest {
  private Service service;

  @BeforeEach
  void befofeEach() {
    service = Service.ignite();
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void shouldExecuteAllRequestsCorrectly() throws Exception {
    InMemoryCommentRepository commentRepository = new InMemoryCommentRepository();
    InMemoryArticleRepository articleRepository = new InMemoryArticleRepository();
    ArticleService articleService = new ArticleService(articleRepository, commentRepository);

    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
      List.of(
        new ArticleController(
          service,
          articleService,
          objectMapper
        )
      )
    );

    application.start();
    service.awaitInitialization();

    HttpResponse<String> responseOfCreatingArticle = HttpClient.newHttpClient()
      .send(
        HttpRequest.newBuilder()
          .POST(
            BodyPublishers.ofString(
        """
{
  "name": "Java",
  "tags": ["Java top", "C++ dumb"]
}"""
            )
          )
          .uri(URI.create("http://localhost:%d/api/articles".formatted(service.port())))
    .build(),
    BodyHandlers.ofString(UTF_8)
    );

    assertEquals(201, responseOfCreatingArticle.statusCode());
    ArticleCreateResponse articleCreateResponse =
    objectMapper.readValue(responseOfCreatingArticle.body(), ArticleCreateResponse.class);
    assertEquals(new ArticleId(1L), articleCreateResponse.articleId());

    HttpResponse<String> responseOfCreatingComment = HttpClient.newHttpClient()
      .send(
        HttpRequest.newBuilder()
          .POST(
            BodyPublishers.ofString(
    """
{
    "text" : "Here there",
    "articleId" : "1"
}"""
            )
          )
          .uri(URI.create("http://localhost:%d/api/comments".formatted(service.port())))
            .build(),
            BodyHandlers.ofString(UTF_8)
    );
    assertEquals(201, responseOfCreatingComment.statusCode());
    CommentCreateResponse commentCreateResponse =
    objectMapper.readValue(responseOfCreatingComment.body(), CommentCreateResponse.class);
    assertEquals(new CommentId(1L), commentCreateResponse.commentId());

    HttpResponse<String> responseForUpdateArticle = HttpClient.newHttpClient()
      .send(
        HttpRequest.newBuilder()
          .PUT(
            BodyPublishers.ofString(
    """
{
  "name": "Python",
  "tags": ["Horror", "Love"]
}"""
            )
          )
          .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
            .build(),
            BodyHandlers.ofString(UTF_8)
      );

    assertEquals(200, responseForUpdateArticle.statusCode());
    ArticleUpdateResponse articleUpdateResponse =
    objectMapper.readValue(responseForUpdateArticle.body(), ArticleUpdateResponse.class);
    assertEquals(new ArticleId(1L), articleUpdateResponse.articleId());

    HttpResponse<String> responseOfDeleteComment = HttpClient.newHttpClient()
      .send(
        HttpRequest.newBuilder()
          .DELETE()
            .uri(URI.create("http://localhost:%d/api/comments/1".formatted(service.port())))
              .build(),
              BodyHandlers.ofString(UTF_8)
      );

    assertEquals(200, responseOfDeleteComment.statusCode());
    CommentDeleteResponse commentDeleteResponse =
    objectMapper.readValue(responseOfDeleteComment.body(), CommentDeleteResponse.class);
    assertEquals(new CommentId(1L), commentDeleteResponse.commentId());

    HttpResponse<String> responseOfGetArticle = HttpClient.newHttpClient()
      .send(
        HttpRequest.newBuilder()
          .GET()
            .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
              .build(),
              BodyHandlers.ofString(UTF_8)
      );
    assertEquals(200, responseOfGetArticle.statusCode());
    ArticleGetResponse articleGetResponse = objectMapper.readValue(responseOfGetArticle.body(), ArticleGetResponse.class);
    Article article = articleGetResponse.article();
    assertEquals(new ArticleId(1L), article.id());
    assertEquals("Python", article.name());
    assertEquals(new HashSet<>(Arrays.asList("Horror", "Love")), article.tags());
    assertEquals(new ArrayList<Comment>(), article.comments());
  }
}

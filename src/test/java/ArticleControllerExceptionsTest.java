import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ArticleController;
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
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleControllerExceptionsTest {
  private Service service;

  @BeforeEach
  void beforeEach() {
    service = Service.ignite();
    InMemoryArticleRepository articleRepository = new InMemoryArticleRepository();
    InMemoryCommentRepository commentRepository = new InMemoryCommentRepository();
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
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void should404IfArticleIsUnsuccessfullyDeleted() throws Exception {
    HttpResponse<String> responseOfDeleteArticle = HttpClient.newHttpClient()
    .send(
    HttpRequest.newBuilder()
    .DELETE()
    .uri(URI.create("http://localhost:%d/api/articles/10".formatted(service.port())))
    .build(),
    BodyHandlers.ofString(UTF_8)
    );
    assertEquals(404, responseOfDeleteArticle.statusCode());
  }

  @Test
  void should404IfCommentIsUnsuccessfullyDeleted() throws Exception {
    HttpResponse<String> responseOfDeleteComment = HttpClient.newHttpClient()
    .send(
    HttpRequest.newBuilder()
    .DELETE()
    .uri(URI.create("http://localhost:%d/api/comments/10".formatted(service.port())))
    .build(),
    BodyHandlers.ofString(UTF_8)
    );
    assertEquals(404, responseOfDeleteComment.statusCode());
  }

  @Test
  void should404IfArticleIsUnsuccessfullyUpdated() throws Exception {
    HttpResponse<String> responseOfUpdateArticle = HttpClient.newHttpClient()
    .send(
    HttpRequest.newBuilder()
    .PUT(
    HttpRequest.BodyPublishers.ofString(
    """
{
  "name": "Python",
  "tags": ["Horror", "Love"]
}"""
    )
    )
    .uri(URI.create("http://localhost:%d/api/articles/10".formatted(service.port())))
    .build(),
    BodyHandlers.ofString(UTF_8)
    );
    assertEquals(404, responseOfUpdateArticle.statusCode());
  }

  @Test
  void should404IfArticleIsUnsuccessfullyGet() throws Exception {
    HttpResponse<String> responseOfGetArticle = HttpClient.newHttpClient()
    .send(
    HttpRequest.newBuilder()
    .GET()
    .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
    .build(),
    BodyHandlers.ofString(UTF_8)
    );
    assertEquals(404, responseOfGetArticle.statusCode());
  }

}
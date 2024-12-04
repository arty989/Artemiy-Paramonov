//import com.fasterxml.jackson.databind.ObjectMapper;
//import controllers.ArticleController;
//import controllers.responses.*;
//import entity.Article;
//import entity.ArticleId;
//import entity.Comment;
//import entity.CommentId;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import repository.InMemoryArticleRepository;
//import repository.InMemoryCommentRepository;
//import service.ArticleService;
//import spark.Service;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpRequest.BodyPublishers;
//import java.net.http.HttpResponse;
//import java.net.http.HttpResponse.BodyHandlers;
//
//import java.util.*;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class ArticleControllerTest {
//  private Service service;
//
//  @BeforeEach
//  void befofeEach() {
//    service = Service.ignite();
//    InMemoryArticleRepository articleRepository = new InMemoryArticleRepository();
//    InMemoryCommentRepository commentRepository = new InMemoryCommentRepository();
//    ArticleService articleService = new ArticleService(articleRepository, commentRepository);
//    ObjectMapper objectMapper = new ObjectMapper();
//    Application application = new Application(
//    List.of(
//    new ArticleController(
//    service,
//    articleService,
//    objectMapper
//    )
//    )
//    );
//  }
//
//  @AfterEach
//  void afterEach() {
//    service.stop();
//    service.awaitStop();
//  }
//
//  @Test
//  void should404IfArticleIsUnsuccessfullyDeleted() throws Exception {
//    HttpResponse<String> responseOfDeleteArticle = HttpClient.newHttpClient()
//    .send(
//    HttpRequest.newBuilder()
//    .DELETE()
//    .uri(URI.create("http://localhost:%d/api/comments/1".formatted(service.port())))
//    .build(),
//    BodyHandlers.ofString(UTF_8)
//    );
//    assertEquals(404, responseOfDeleteArticle.statusCode());
//  }
//
//  @Test
//  void should404IfCommentIsUnsuccessfullyDeleted() throws Exception {
//    HttpResponse<String> responseOfDeleteArticle = HttpClient.newHttpClient()
//    .send(
//    HttpRequest.newBuilder()
//    .DELETE()
//    .uri(URI.create("http://localhost:%d/api/comments/1".formatted(service.port())))
//    .build(),
//    BodyHandlers.ofString(UTF_8)
//    );
//    assertEquals(404, responseOfDeleteArticle.statusCode());
//  }
//}

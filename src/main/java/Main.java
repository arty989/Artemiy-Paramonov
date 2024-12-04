import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ArticleController;
import controllers.ArticleFreemarkerController;
import repository.InMemoryArticleRepository;
import repository.InMemoryCommentRepository;
import service.ArticleService;
import spark.Service;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    final var articleService = new ArticleService (
      new InMemoryArticleRepository(), new InMemoryCommentRepository()
    );
    Application application = new Application(
      List.of(
        new ArticleController(
          service,
          articleService,
          objectMapper
        ),
        new ArticleFreemarkerController(
          service,
          articleService,
          TemplateFactory.freeMarkerEngine()
        )
      )
    );
    application.start();
  }
}

package service.exceptions;

public class ArticleDeleteException extends RuntimeException {

  public ArticleDeleteException (String message) {
    super(message);
  }

  public ArticleDeleteException (String message, Throwable cause) {
    super(message, cause);
  }

}

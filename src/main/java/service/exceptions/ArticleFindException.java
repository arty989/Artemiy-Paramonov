package service.exceptions;

public class ArticleFindException extends RuntimeException {

  public ArticleFindException (String message) {
    super(message);
  }

  public ArticleFindException (String message, Throwable cause) {
    super(message, cause);
  }

}

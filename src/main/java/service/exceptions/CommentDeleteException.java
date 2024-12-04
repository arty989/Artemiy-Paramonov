package service.exceptions;

public class CommentDeleteException extends RuntimeException {

  public CommentDeleteException (String message) {
    super(message);
  }

  public CommentDeleteException (String message, Throwable cause) {
    super(message, cause);
  }

}

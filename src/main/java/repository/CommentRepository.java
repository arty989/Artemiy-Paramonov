package repository;

import entity.Comment;
import entity.CommentId;
import repository.exceptions.ArticleNotFoundException;

public interface CommentRepository {
  CommentId generateId();

  Comment findById(CommentId commentId) throws ArticleNotFoundException;

  CommentId create(Comment comment);

  void delete(CommentId commentId);
}

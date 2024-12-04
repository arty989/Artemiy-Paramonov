package repository;

import entity.Comment;
import entity.CommentId;
import repository.exceptions.ArticleNotFoundException;
import repository.exceptions.CommentIdDuplicatedException;
import repository.exceptions.CommentNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCommentRepository implements CommentRepository {
  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<CommentId, Comment> commentMap = new ConcurrentHashMap<>();

  @Override
  public CommentId generateId() {
    return new CommentId(nextId.incrementAndGet());
  }

  @Override
  public Comment findById(CommentId commentId) throws ArticleNotFoundException {
    Comment comment = this.commentMap.get(commentId);
    if (comment == null) {
      throw new CommentNotFoundException("Cannot find comment by id=" + commentId.id());
    }
    return comment;
  }

  @Override
  public CommentId create(Comment comment) throws CommentIdDuplicatedException {
    if (commentMap.get(comment.getCommentId()) != null) {
      throw new CommentIdDuplicatedException("Comment with the given id already exists:" + comment.getCommentId().id());
    }
    commentMap.put(comment.getCommentId(), comment);
    return comment.getCommentId();
  }

  @Override
  public void delete(CommentId commentId) {
    if (commentMap.remove(commentId) == null) {
      throw new CommentNotFoundException("Cannot find comment by id=" + commentId.id());
    }
  }
}

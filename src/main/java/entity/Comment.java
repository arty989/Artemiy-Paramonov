package entity;

import java.util.Objects;

public class Comment {
  private final CommentId commentId;
  private final ArticleId articleId;
  private final String text;

  public Comment(CommentId commentId, ArticleId articleId, String text) {
    this.commentId = commentId;
    this.articleId = articleId;
    this.text = text;
  }

  public Comment withText(String newText) {
    return new Comment(this.commentId, this.articleId, newText);
  }

  public CommentId getCommentId() {
    return this.commentId;
  }

  public ArticleId getArticleId() {
    return this.articleId;
  }

  public String getText() {
    return this.text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment) o;
    return this.commentId.equals(comment.commentId);
  }
}

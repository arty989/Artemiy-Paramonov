package entity;

public record Comment(CommentId commentId, ArticleId articleId, String text) {

  public Comment withText(String newText) {
    return new Comment(this.commentId, this.articleId, newText);
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

package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Article(ArticleId id, String name, Set<String> tags, List<Comment> comments) {

  public Article withName(String newName) {
    return new Article(this.id, newName, this.tags, this.comments);
  }

  public Article withTags(Set<String> newTags) {
    return new Article(this.id, this.name, newTags, this.comments);
  }

  public Article withComments(List<Comment> newComments) {
    return new Article(this.id, this.name, this.tags, newComments);
  }

  @Override
  public Set<String> tags() {
    return new HashSet<>(this.tags);
  }

  @Override
  public List<Comment> comments() {
    return new ArrayList<>(this.comments);
  }
}

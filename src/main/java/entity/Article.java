package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Article {
  private final ArticleId id;
  private final String name;
  private final Set<String> tags;
  private final List<Comment> comments;

  public Article(ArticleId id, String name, Set<String> tags, List<Comment> comments) {
    this.id = id;
    this.name = name;
    this.tags = tags;
    this.comments = comments;
  }

  public Article withName(String newName) {
    return new Article(this.id, newName, this.tags, this.comments);
  }

  public Article withTags(Set<String> newTags) {
    return new Article(this.id, this.name, newTags, this.comments);
  }

  public Article withComments(List<Comment> newComments) {
    return new Article(this.id, this.name, this.tags, newComments);
  }

  public ArticleId getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Set<String> getTags() {
    return new HashSet<>(this.tags);
  }

  public List<Comment> getComments() {
    return new ArrayList<>(this.comments);
  }
}

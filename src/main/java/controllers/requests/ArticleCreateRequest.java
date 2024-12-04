package controllers.requests;

import java.util.Set;

public record ArticleCreateRequest(String name, Set<String> tags) {}

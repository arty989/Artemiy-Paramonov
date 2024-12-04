package controllers.requests;

import java.util.Set;

public record ArticleUpdateRequest(String name, Set<String> tags) {}

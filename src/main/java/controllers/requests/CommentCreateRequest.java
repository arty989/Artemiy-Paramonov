package controllers.requests;

import entity.ArticleId;

public record CommentCreateRequest(String text, long articleId) {}

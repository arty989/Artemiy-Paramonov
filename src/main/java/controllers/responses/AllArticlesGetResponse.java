package controllers.responses;

import entity.Article;

import java.util.List;

public record AllArticlesGetResponse(List<Article> articles) {}

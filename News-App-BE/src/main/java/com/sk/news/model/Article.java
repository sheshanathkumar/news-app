package com.sk.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.news.entity.ArticleEntity;
import com.sk.news.entity.CategoryEntity;
import com.sk.news.entity.SourceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Article implements Serializable {

    @JsonProperty("source")
    private String source;

    @JsonProperty("author")
    private String author;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("url")
    private String url;

    @JsonProperty("urlToImage")
    private String urlImage;

    @JsonProperty("publishedAt")
    private Timestamp publishedAt;

    @JsonProperty("content")
    private String content;

    @JsonProperty("category")
    private String category;

    public static Article of(ArticleEntity obj, SourceEntity source, CategoryEntity category) {

        if (obj == null) return null;

        return Article.builder()
                .category(category.getName())
                .source(source.getName())
                .author(obj.getAuthor())
                .content(obj.getContent())
                .description(obj.getDescription())
                .publishedAt(obj.getPublishedAt())
                .title(obj.getTitle())
                .url(obj.getUrl())
                .urlImage(obj.getUrlImage())
                .build();
    }


}

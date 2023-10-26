package com.sk.news.entity;

import com.sk.news.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "articles")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "descr")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "published_at")
    private Timestamp publishedAt;

    @Column(name = "content")
    private String content;

    @Column(name = "source_id")
    private Integer sourceId;

    @Column(name = "category")
    private Integer category;

    public static ArticleEntity of (Article article) {

        if (article == null) return null;

        return  ArticleEntity.builder()
                .url(article.getUrl())
                .title(article.getTitle())
                .author(article.getAuthor())
                .description(article.getAuthor())
                .urlImage(article.getUrlImage())
                .publishedAt(article.getPublishedAt())
                .content(article.getContent())
                .build();
    }


}

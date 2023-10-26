package com.sk.news.repository;

import com.sk.news.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Integer> {

    ArticleEntity findByArticleId(Integer id);

    List<ArticleEntity> findByAuthor(String author);

}

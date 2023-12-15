package com.sk.news.repository;

import com.sk.news.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Integer> {

    ArticleEntity findByArticleId(Integer id);

    List<ArticleEntity> findByAuthor(String author);

    @Query(value = "SELECT * FROM articles order by published_at desc limit 10", nativeQuery = true)
    List<ArticleEntity> fetchTop10records();
}

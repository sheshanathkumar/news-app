package com.sk.news.contoller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.news.entity.ArticleEntity;
import com.sk.news.entity.CategoryEntity;
import com.sk.news.entity.SourceEntity;
import com.sk.news.model.Article;
import com.sk.news.model.ModelApiResponse;
import com.sk.news.repository.ArticleRepo;
import com.sk.news.repository.CategoryRepo;
import com.sk.news.repository.SourceRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*")
@EnableCaching
@Slf4j
public class NewsController {

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    SourceRepo sourceRepo;

    ObjectMapper mapper = new ObjectMapper();


    @GetMapping(value = "/test")
    public ResponseEntity<ModelApiResponse> test() {
        return new ResponseEntity<>(new ModelApiResponse(200, "SUCCESS", "Test Success"), HttpStatus.OK);
    }


    @GetMapping(value = "/news")
    @Cacheable(value = "news")
    public ResponseEntity<ModelApiResponse> getAllNews() {
        log.info("fetching all news");
        try {

            Pageable page = Pageable.ofSize(100);
            Page<ArticleEntity> pages = articleRepo.findAll(page);
            int totalPage = pages.getTotalPages();

            HashMap<String, String> map = new HashMap<>();
            map.put("totalPage", "" + totalPage);

            List<ArticleEntity> articleEntities = pages.get().collect(Collectors.toList());

            List<Article> articles = new ArrayList<>();
            articleEntities.forEach((x) -> {

                CategoryEntity category = categoryRepo.findByCatId(x.getCategory());
                SourceEntity source = sourceRepo.findBySourceId(x.getSourceId());

                Article art = Article.of(x, source, category);
                articles.add(art);
            });

            return new ResponseEntity<>(new ModelApiResponse(200, "SUCCESS", articles, map), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ModelApiResponse(500, "FAILED", e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private Integer getRandomNumber() {
        Integer x = (int) Math.floor(Math.random() * 9999) % 6;
        return (x == 0) ? 1 : x;
    }


}

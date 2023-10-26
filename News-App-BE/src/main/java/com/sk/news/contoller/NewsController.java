package com.sk.news.contoller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.news.entity.ArticleEntity;
import com.sk.news.entity.CategoryEntity;
import com.sk.news.entity.SourceEntity;
import com.sk.news.model.Article;
import com.sk.news.model.ModelApiResponse;
import com.sk.news.repository.ArticleRepo;
import com.sk.news.repository.CategoryRepo;
import com.sk.news.repository.SourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*")
public class NewsController {

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    SourceRepo sourceRepo;


    @GetMapping(value = "/test")
    public ResponseEntity<ModelApiResponse> test() {
        return new ResponseEntity<>(new ModelApiResponse(200, "SUCCESS", "Test Success"), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<String> update() {


        List<ArticleEntity> articleEntities = articleRepo.findAll();

        articleEntities.forEach( (x) -> {
            x.setCategory(  getRandomNumber()  );
            articleRepo.save(x);
        }  );

        return new ResponseEntity<>( "SUCCESS", HttpStatus.OK);
    }


    @GetMapping(value = "/news")
    public ResponseEntity<ModelApiResponse> getAllNews() {

        try {

            Pageable page = Pageable.ofSize(100);
            Page<ArticleEntity> pages = articleRepo.findAll(page);
            int totalPage = pages.getTotalPages();

            HashMap<String, String> map = new HashMap<>();
            map.put("totalPage", ""+totalPage);

            List<ArticleEntity> articleEntities = pages.get().collect(Collectors.toList());

            List<Article> articles = new ArrayList<>();
            articleEntities.forEach( (x) -> {

                CategoryEntity category = categoryRepo.findByCatId(x.getCategory());
                SourceEntity source = sourceRepo.findBySourceId(x.getSourceId());

                Article art = Article.of(x, source, category);
                articles.add(art);
            } );

            return new ResponseEntity<>(new ModelApiResponse(200, "SUCCESS", articles, map), HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<>( new ModelApiResponse(500, "FAILED", e.getLocalizedMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private Integer getRandomNumber () {
        Integer x = (int) Math.floor( Math.random() * 9999 ) % 6 ;
        return (x == 0) ? 1 : x;
    }


}

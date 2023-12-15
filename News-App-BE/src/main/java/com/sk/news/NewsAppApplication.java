package com.sk.news;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.news.entity.ArticleEntity;
import com.sk.news.entity.CategoryEntity;
import com.sk.news.entity.SourceEntity;
import com.sk.news.model.ModelApiResponse;
import com.sk.news.model.news.NewsArticle;
import com.sk.news.model.news.Result;
import com.sk.news.repository.ArticleRepo;
import com.sk.news.repository.CategoryRepo;
import com.sk.news.repository.SourceRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class NewsAppApplication {

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	ArticleRepo articleRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	SourceRepo sourceRepo;

	public static void main(String[] args) {
		SpringApplication.run(NewsAppApplication.class, args);

		System.out.println("News App Running");
	}


	@Scheduled(fixedDelay = 1000 * 60 * 20)
	@Async
	public void fetchNews() throws Exception {

		log.info("Start fetching news "+ new Date());
		try {
			String api = "https://newsdata.io/api/1/news?apikey=pub_34246096e5ecd15ad746121a7c67008c50106&country=in&language=en";
			// String api = "http://localhost:9090/news";
			try {

				RestTemplate template = new RestTemplate();
				String res = template.getForObject(api, String.class);

				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				NewsArticle articles = mapper.readValue(res, new TypeReference<NewsArticle>() {
				});


				List<Result> results = articles.getResults();
				Set<String> categories = results.stream().flatMap(x -> Stream.of(x.getCategory())).collect(Collectors.toSet());
				Set<String> sources = results.stream().map(Result::getSourceId).collect(Collectors.toSet());

				log.info("All categories ==> {}", categories);
				log.info("All sources ==> {}", sources);

				categories.forEach(x -> {
					try {
						categoryRepo.saveCategories(x.toUpperCase());
					} catch (Exception e) {

					}
				});

				sources.forEach(x -> {
					try {
						sourceRepo.saveSource(x);
					} catch (Exception e) {

					}
				});

				List<ArticleEntity> articleEntities = new ArrayList<>();

				Map<String, Integer> sourceMap = new HashMap<>();
				Map<String, Integer> categoryMap = new HashMap<>();

				List<SourceEntity> sourceEntities = sourceRepo.findAll();
				List<CategoryEntity> categoryEntities = categoryRepo.findAll();

				sourceEntities.forEach(x -> {
					sourceMap.putIfAbsent(x.getName(), x.getSourceId());
				});

				categoryEntities.forEach(x -> {
					categoryMap.putIfAbsent(x.getName(), x.getCatId());
				});

				results.forEach(x -> {
					String [] cats = x.getCategory();
					ArticleEntity article = ArticleEntity.builder()
							.url(x.getLink())
							.title(x.getTitle())
							.publishedAt(Timestamp.valueOf(x.getDate()))
							.description(x.getDescription())
							.author("NA")
							.sourceId(sourceMap.getOrDefault(x.getSourceId(), 1))
							.urlImage(x.getImgUrl())
							.category(categoryMap.getOrDefault( cats[0].toUpperCase() , 1))
							.build();
					articleEntities.add(article);

				});

				articleEntities.forEach( x -> {
					try {
						articleRepo.save(x);
					}catch (Exception e) {

					}
				});

				log.info("Article save successfully");
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

		log.info("------------------------------------------\n\n");
	}
}

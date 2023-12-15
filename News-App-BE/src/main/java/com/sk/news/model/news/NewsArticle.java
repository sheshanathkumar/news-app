package com.sk.news.model.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class NewsArticle {

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "totalResults")
    private Integer totalResult;

    @JsonProperty(value = "results")
    private List<Result> results;

}

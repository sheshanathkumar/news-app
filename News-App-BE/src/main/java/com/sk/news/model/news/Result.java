package com.sk.news.model.news;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class Result {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "pubDate")
    private String date;

    @JsonProperty(value = "image_url")
    private String imgUrl;

    @JsonProperty(value = "source_id")
    private String sourceId;

    @JsonProperty(value = "category")
    private String [] category;

}

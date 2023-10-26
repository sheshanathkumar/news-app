package com.sk.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.news.entity.SourceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Source {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;


    public static Source of (SourceEntity source) {
        if (source == null) return null;

        return new Source(source.getSourceId(), source.getName());
    }

}



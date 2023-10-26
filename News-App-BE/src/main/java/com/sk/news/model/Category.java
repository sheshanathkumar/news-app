package com.sk.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.news.entity.CategoryEntity;
import com.sk.news.entity.SourceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    public static Category of(CategoryEntity obj) {
        if (obj == null) return null;
        return new Category(obj.getCatId(), obj.getName());
    }
}

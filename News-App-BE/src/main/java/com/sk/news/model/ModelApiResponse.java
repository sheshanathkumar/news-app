package com.sk.news.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelApiResponse implements Serializable {

    private Integer code;

    private String status;

    private Object response;

    private Object extra;

    public ModelApiResponse(Integer code, String status, Object response) {
        this.code = code;
        this.status = status;
        this.response = response;
    }
}

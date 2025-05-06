package com.aspira.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Runner {
    private long id;
    private String name;
    @JsonProperty("priceStr")
    private String odds;
}

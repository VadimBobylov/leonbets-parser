package com.aspira.api.model;

import lombok.Data;

@Data
public class League {
    private long id;
    private String name;
    private String url;
    private boolean top;
}

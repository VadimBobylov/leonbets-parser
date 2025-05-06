package com.aspira.api.model;

import java.util.List;
import lombok.Data;

@Data
public class Region {
    private String name;
    private String url;
    private List<League> leagues;
}

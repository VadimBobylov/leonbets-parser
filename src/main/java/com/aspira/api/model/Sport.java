package com.aspira.api.model;

import java.util.List;
import lombok.Data;

@Data
public class Sport {
    private String name;
    private List<Region> regions;
}

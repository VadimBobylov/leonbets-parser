package com.aspira.api.model;

import java.util.List;
import lombok.Data;

@Data
public class Market {
    private long id;
    private String name;
    private List<Runner> runners;
}

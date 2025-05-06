package com.aspira.api.model;

import java.util.List;
import lombok.Data;

@Data
public class Event {
    private long id;
    private String name;
    private String status;
    private String betline;
    private long kickoff;
    private List<Market> markets;
}

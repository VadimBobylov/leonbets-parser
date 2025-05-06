package com.aspira.dto;

import com.aspira.api.model.Market;
import java.util.List;

public record MatchResult(
        long id,
        String name,
        long kickoff,
        String status,
        String betline,
        List<Market> markets
) {}

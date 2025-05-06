package com.aspira.dto;

import java.util.List;

public record LeagueResult(
        String sportName,
        String leagueName,
        List<MatchResult> matches
) {}

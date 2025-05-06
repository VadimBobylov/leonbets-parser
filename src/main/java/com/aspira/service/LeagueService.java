package com.aspira.service;

import com.aspira.api.client.LeonClient;
import com.aspira.api.model.League;
import com.aspira.dto.LeagueResult;
import com.aspira.dto.MatchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;

public class LeagueService {

    private final LeonClient client;
    private final ExecutorService executor;

    public LeagueService(LeonClient client, ExecutorService executor) {
        this.client = client;
        this.executor = executor;
    }

    @SneakyThrows
    public List<LeagueResult> fetchTopLeagueResults(List<String> targetSports) {
        List<LeagueResult> results = Collections.synchronizedList(new ArrayList<>());

        client.getSports().stream()
                .filter(sport -> targetSports.contains(sport.getName()))
                .forEach(sport ->
                        sport.getRegions().stream()
                                .flatMap(region -> region.getLeagues().stream())
                                .filter(League::isTop)
                                .forEach(league -> executor.submit(() -> {
                                    var matches = client.getEventsForLeague(league.getId()).stream()
                                            .filter(ev -> "OPEN".equals(ev.getStatus()) && "prematch".equals(ev.getBetline()))
                                            .limit(2)
                                            .map(ev -> {
                                                var markets = client.getMarketsForEvent(ev.getId());
                                                return new MatchResult(
                                                        ev.getId(),
                                                        ev.getName(),
                                                        ev.getKickoff(),
                                                        ev.getStatus(),
                                                        ev.getBetline(),
                                                        markets
                                                );
                                            })
                                            .toList();

                                    if (!matches.isEmpty()) {
                                        results.add(new LeagueResult(sport.getName(), league.getName(), matches));
                                    }
                                }))
                );

        executor.shutdown();

        if (!executor.awaitTermination(2, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        return results;
    }
}

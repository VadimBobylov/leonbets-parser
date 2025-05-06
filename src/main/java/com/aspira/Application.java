package com.aspira;

import com.aspira.api.client.LeonClient;
import com.aspira.service.LeagueService;
import com.aspira.service.ResultPrinter;
import java.util.List;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {
        var leagueService = new LeagueService(new LeonClient(), Executors.newFixedThreadPool(3));
        var results = leagueService.fetchTopLeagueResults(List.of("Football", "Tennis", "Ice Hockey", "Basketball"));

        new ResultPrinter().print(results);
    }
}

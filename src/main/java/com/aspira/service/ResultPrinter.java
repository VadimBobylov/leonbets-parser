package com.aspira.service;

import com.aspira.dto.LeagueResult;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResultPrinter {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");

    public void print(List<LeagueResult> results) {
        for (var league : results) {
            System.out.println(league.sportName() + ", " + league.leagueName());
            for (var match : league.matches()) {
                var dateTime = Instant.ofEpochMilli(match.kickoff())
                        .atZone(ZoneOffset.UTC)
                        .format(FMT);
                System.out.printf("  %s, %s, %d%n", match.name(), dateTime, match.id());

                match.markets().forEach(market -> {
                    System.out.println("\t\t" + market.getName());
                    market.getRunners().forEach(runner -> System.out.printf(
                            "\t\t\t%s, %s, %d%n",
                            runner.getName(),
                            runner.getOdds(),
                            runner.getId()
                    ));
                });
                System.out.println();
            }
        }
    }
}

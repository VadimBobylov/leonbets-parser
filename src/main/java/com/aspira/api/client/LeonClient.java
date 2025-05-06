package com.aspira.api.client;

import com.aspira.api.model.Event;
import com.aspira.api.model.Market;
import com.aspira.api.model.Sport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.SneakyThrows;

public class LeonClient {

    private static final String BASE_URL = "https://leonbets.com/api-2/betline/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
    public List<Sport> getSports() {
        var response = getJson("sports?ctag=en-US&flags=urlv2");
        return mapper.readerFor(new TypeReference<List<Sport>>() {}).readValue(response);
    }

    @SneakyThrows
    public List<Event> getEventsForLeague(long leagueId) {
        var response = getJson(
                String.format("events/all?ctag=en-US&league_id=%d&hideClosed=true&flags=reg,urlv2,mm2,rrc,nodup", leagueId));
        return mapper.readerFor(new TypeReference<List<Event>>() {}).readValue(response.path("events"));
    }

    @SneakyThrows
    public List<Market> getMarketsForEvent(long eventId) {
        var response = getJson(
                String.format("event/all?ctag=en-US&eventId=%d&flags=reg,urlv2,mm2,rrc,nodup,smgv2,outv2,wd2,dark", eventId));
        return mapper.readerFor(new TypeReference<List<Market>>() {}).readValue(response.path("markets"));
    }

    @SneakyThrows
    private JsonNode getJson(String path) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Accept", "application/json")
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readTree(response.body());
    }
}

package com.scrim_helper.controller;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Comparator;
import java.util.List;

import com.scrim_helper.models.FinalDetails;
import com.scrim_helper.models.Match;
import com.scrim_helper.utils.ExcelExporter;
import com.scrim_helper.utils.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpGgScraper {

    private static final JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) {
        String playerId = "81a7e513eebb4ede94439bf89d059520"; // ID do jogador
        String matchesApiUrl = "https://supervive.op.gg/api/players/steam-" + playerId + "/matches?page=1";

        try {
            // Criando cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Criando requisição GET para obter as partidas
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(matchesApiUrl))
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // Enviando requisição e capturando resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Processando JSON de resposta
            if (response.statusCode() == 200) {
                String responseBody = response.body();

                List<Match> matches = jsonParser.parseMatches(responseBody);

                for (Match match : matches) {
                    System.out.println("Match ID: " + match.getMatchId());
                    System.out.println("Início da partida: " + match.getMatchStart());
                    System.out.println("Herói: " + match.getHeroAssetId());
                    System.out.println("Kills: " + match.getStats().getKills());
                    System.out.println("---------------------");
                }

                String matchId = matches.get(0).getMatchId();
                fetchMatchDetails(matchId);
            } else {
                System.out.println("Erro ao buscar os dados: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fetchMatchDetails(String matchId) {
        String matchApiUrl = "https://supervive.op.gg/api/matches/steam-" + matchId;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(matchApiUrl))
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                List<FinalDetails> matchesDetails = jsonParser.parseFinalDetails(responseBody);
                matchesDetails.sort(Comparator.comparingInt(FinalDetails::getPlacement));
                ExcelExporter.exportToExcel(matchesDetails, "matches.xlsx");
            } else {
                System.out.println("Erro ao obter detalhes da partida.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

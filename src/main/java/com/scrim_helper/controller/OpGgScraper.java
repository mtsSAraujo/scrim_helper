package com.scrim_helper.controller;

import com.scrim_helper.models.FinalDetails;
import com.scrim_helper.models.Match;
import com.scrim_helper.utils.ExcelExporter;
import com.scrim_helper.utils.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpGgScraper {

    private static final JsonParser jsonParser = new JsonParser();

    public String findPlayerId(String playerName) {

        playerName = playerName.replace("#", "%23");
        String URL = "https://supervive.op.gg/players/steam-" + playerName;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String responseBody = response.body();

                Pattern pattern = Pattern.compile("&quot;user_id&quot;:&quot;([a-f0-9]+)&quot;");
                Matcher matcher = pattern.matcher(responseBody);

                if (matcher.find()) {
                    String userId = matcher.group(1);
                    System.out.println("User ID encontrado: " + userId);
                    return userId;
                } else {
                    System.out.println("User ID não encontrado.");
                }
            } else {
                System.out.println("Erro ao buscar os dados: " + response.statusCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void OpGgWebScraper(String playerId) {
        final String matchesApiUrl = "https://supervive.op.gg/api/players/steam-" + playerId + "/matches?page=1";

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

                String matchId = matches.get(4).getMatchId();
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

package com.randomlol;

import java.util.Scanner;
import java.util.Random;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;


public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Você quer sortear um personagem de lol?");
        String respostaDoPlayer = scanner.next();

        if (respostaDoPlayer.equalsIgnoreCase("Sim")) {

            System.out.println("Tudo bem, aqui está seu campeão!");

            String lolRandom = fazerRequisição("https://ddragon.leagueoflegends.com/cdn/12.6.1/data/en_US/champion.json");

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> respostaTratada = mapper.readValue(lolRandom, Map.class);
            
            Map<String, Object> data = mapper.convertValue(respostaTratada.get("data"), Map.class);
            Collection<Object> campeoesCollection = data.values();
            List<Object> listaCampeoes = new ArrayList<>(campeoesCollection);

            Map<String, Object> campeaoAleatorio = (Map<String, Object>) listaCampeoes.get(randomNumber(listaCampeoes.size()));
            System.out.println(campeaoAleatorio.get("id"));
            


        } else {
            System.out.println("Tudo bem, até uma proxima!");
        }

        scanner.close();

    }

    public static int randomNumber(int range) {
        return new Random().nextInt(0, range);
    }

    public static String fazerRequisição(String url) throws Exception {

        HttpClient lolzada = HttpClient.newHttpClient();

        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> respostaRequisicao = lolzada.send(requisicao, HttpResponse.BodyHandlers.ofString());

        return respostaRequisicao.body();

    }
}

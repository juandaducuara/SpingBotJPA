package com.aluracursos.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//Conexion a la API Cliente servidor que responde el JSON con el la URL
public class ConsumoAPI {
    public String obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try{
            response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
}

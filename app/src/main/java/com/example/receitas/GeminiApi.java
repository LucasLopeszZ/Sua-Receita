package com.example.receitas;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GeminiApi {

    private static final String API_KEY = "AIzaSyCW8gy5DILtOCYxSJRrjlIjALAvFLODGyQ";

    private static final String ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build();


    public String gerarReceitas(String ingredientes) {

        String prompt = "Gere receitas possíveis usando apenas estes ingredientes: " +
                ingredientes + "\nResponda com: Nome, ingredientes e modo de preparo.";

        String json = "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\"text\": \"" + prompt + "\"}\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                return "Erro: " + response.code() + "\n" + response.message() + "\n" + response.body().string();

            return response.body().string();

        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
}

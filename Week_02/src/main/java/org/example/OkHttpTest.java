package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpTest {
    private static final String URL = "http://localhost:8801";

    public static void main(String[] args) throws IOException {
        OkHttpTest test = new OkHttpTest();
        System.out.println(test.run(URL));
    }

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        if(url == null || url.isEmpty()) {
            return "";
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}

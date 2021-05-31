package com.example.demo.oauth;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
public class MyOkHttpClient {
    private static String BASE_URL = "https://kauth.kakao.com";
    private static String APP_KEY = "6bbcbb7e909f4fc6af433b29fb9cb225";
    private static String REDIRECT_URI = "http://localhost:8080/kakao/callback2";

    private MyOkHttpClient() {}

    public static void getAccessToken(String authorizationCode) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl urlWithParameters = makeHttpUrlWithParameters(authorizationCode);
        Request request= makeRequest(urlWithParameters);

        System.out.println(client.newCall(request).execute().body().string());
        log.info("값 확인{}",client.newCall(request).execute().body().string());
    }

    private static Request makeRequest(HttpUrl url) {
        return new Request.Builder()
                .url(url)
                .build();
    }
    private static HttpUrl makeHttpUrlWithParameters(String authorizationCode) throws MalformedURLException {
        HttpUrl.Builder httpBuilder = HttpUrl
                .get(new URL(BASE_URL + "/oauth/token"))
                .newBuilder();

        httpBuilder.addQueryParameter("grant_type", "authorization_code");
        httpBuilder.addQueryParameter("client_id", APP_KEY);
        httpBuilder.addQueryParameter("redirect_uri", REDIRECT_URI);
        httpBuilder.addQueryParameter("code", authorizationCode);

        return httpBuilder.build();
    }
}


package ru.sfchick.fmusicplayer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ResponseApi {
    public static void main(String[] args) {
        System.out.println(getDataWithParams("http://localhost:8080/measurements"));
    }

    public static String getDataWithParams(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}

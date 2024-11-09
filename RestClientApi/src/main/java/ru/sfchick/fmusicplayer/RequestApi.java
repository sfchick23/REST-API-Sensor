package ru.sfchick.fmusicplayer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RequestApi {
    public static void main(String[] args) {
        Random random = new Random();
        random.setSeed(10);
        String randomSensorName = "sensor56";
        registerSensor(randomSensorName);
        for (int i = 0; i < 100; i++) {
            double randomValue = random.nextDouble(-100, 100);
            boolean rainingRandom = random.nextBoolean();
            sendMeasurement(randomValue, rainingRandom, randomSensorName);
        }
    }

    public static void makePostRequest(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonData, headers);

        try{
            restTemplate.postForObject(url, requestEntity, String.class);
            System.out.println("Successfully posted to " + url);
        }catch(HttpClientErrorException e){
            System.out.println("ERROR: " + e.getMessage());
        }


    }

    public static void registerSensor(String sensorName){
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData= new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequest(url, jsonData);
    }

    public static void sendMeasurement(double value, boolean raining, String sensorName){
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        makePostRequest(url, jsonData);

    }

}

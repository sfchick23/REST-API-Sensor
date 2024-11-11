package ru.sfchick.fmusicplayer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseApi {
    public static void main(String[] args) throws IOException {
        System.out.println(getDataWithParams("http://localhost:8080/measurements"));
        System.out.println(getTemperature("http://localhost:8080/measurements", 10));
    }

    public static String getDataWithParams(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public static List<Double> getTemperature(String url, int numMeasurements) throws IOException {
        List<Double> temperatures = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < numMeasurements; i++) {
            String responseBody = restTemplate.getForObject(url, String.class);

            List<Map<String, Object>> measurements = mapper.readValue(responseBody, new TypeReference<>(){});

            for (Map<String, Object> measurement : measurements) {
                Double temperature = (Double) measurement.get("value");
                temperatures.add(temperature);
            }
        }

        return temperatures;
    }
}

package com.example.codingchallengejava.calculation;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class PostcodeLoader {

    private final Map<String, String> postCodeMap = new HashMap<>();

    @PostConstruct
    public void loadPostcodes() throws Exception {
        ClassPathResource resource = new ClassPathResource("postcodes.csv");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        resource.getInputStream(),
                        StandardCharsets.UTF_8))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 7) {
                    String region = columns[2].trim().replace("\"", "");
                    String postcode = columns[6].trim().replace("\"", "");
                    postCodeMap.put(postcode, region);
                }
            }
        }
    }

    public String getRegion(String postcode) {
        return postCodeMap.get(postcode);
    }
}

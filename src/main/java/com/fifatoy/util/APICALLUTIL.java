package com.fifatoy.util;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class APICALLUTIL {

    public ArrayList<Map<String, Object>> NotKey(String url) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONArray.class);
        return (ArrayList<Map<String, Object>>) response.getBody();
    }

    public Map<String, Object> UseKeyObject(String url, String apiKey) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONObject.class);
        return (Map<String, Object>) response.getBody();
    }

    public ArrayList<Map<String, Object>> UseKeyArray(String url, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONArray.class);
        return (ArrayList<Map<String, Object>>) response.getBody();
    }

}

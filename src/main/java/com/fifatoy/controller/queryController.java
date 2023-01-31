package com.fifatoy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class queryController {

    @Value("${NexonApiKey}")
    private String apiKey;

    @GetMapping("/query")
    public String query(HttpSession session, Model model, @RequestParam(value = "name", required = false) String name) {

        // 공식경기 등급 가져오기 API 호출
        String url = "https://static.api.nexon.co.kr/fifaonline4/latest/division.json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> responseA = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONArray.class);
        // 공식경기 등급 맵에 가지고 있기
        ArrayList<Map<String, Object>> divisionMap = new ArrayList<Map<String, Object>>();
        divisionMap = (ArrayList<Map<String, Object>>) responseA.getBody();
        // 닉네임으로 회원정보 조회
        url = "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=" + name + "";
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", apiKey);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONObject.class);
        Map<String, Object> userinfo = new HashMap<String, Object>();
        userinfo = (Map<String, Object>) response.getBody();
        // 세션에 유저정보 담기
        session.setAttribute("nickname", String.valueOf(userinfo.get("nickname")));
        session.setAttribute("level", String.valueOf(userinfo.get("level")));
        String accessId = String.valueOf(userinfo.get("accessId"));
        // 회원정보로 최고등급 조회
        url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId + "/maxdivision";
        ResponseEntity<?> responseB = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONArray.class);
        ArrayList<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();
        resultListMap = (ArrayList<Map<String, Object>>) responseB.getBody();
        String divisionType = String.valueOf(resultListMap.get(0).get("matchType"));
        String divisionGrade = String.valueOf(resultListMap.get(0).get("division"));
        // 최고등급 구하기
        for (int i = 0; i < divisionMap.size(); i++) {
            if (divisionGrade.equals(String.valueOf(divisionMap.get(i).get("divisionId")))) {
                session.setAttribute("divisiontype", divisionMap.get(i).get("divisionName"));
                break;
            }
        }
        return "query";

    }

    @GetMapping("/queryhome")
    public String queryhome(Model model) {
        return "query";
    }
    // https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname={nickname}
}

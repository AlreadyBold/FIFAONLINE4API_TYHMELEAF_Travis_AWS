package com.fifatoy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fifatoy.util.APICALLUTIL;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class queryController {

    @Value("${NexonApiKey}")
    private String apiKey;
    APICALLUTIL apicallutil = new APICALLUTIL();

    @GetMapping("/query")
    public String query(HttpSession session, Model model, @RequestParam(value = "name", required = false) String name) {

        // 공식경기 등급 가져오기 API 호출 하여 맵에 저장
        String url = "https://static.api.nexon.co.kr/fifaonline4/latest/division.json";
        ArrayList<Map<String, Object>> divisionMap = new ArrayList<Map<String, Object>>();
        divisionMap = (ArrayList<Map<String, Object>>) apicallutil.NotKey(url);
        // 매치타입 가져오기 API 호출 하여 맵에 저장
        url = "https://static.api.nexon.co.kr/fifaonline4/latest/matchtype.json";
        ArrayList<Map<String, Object>> matchTypeMap = new ArrayList<Map<String, Object>>();
        matchTypeMap = (ArrayList<Map<String, Object>>) apicallutil.NotKey(url);

        // 닉네임으로 회원정보 조회
        url = "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=" + name + "";
        Map<String, Object> userinfo = new HashMap<String, Object>();
        userinfo = (Map<String, Object>) apicallutil.UseKeyObject(url, apiKey);

        // 세션에 유저정보 담기
        session.setAttribute("nickname", String.valueOf(userinfo.get("nickname")));
        session.setAttribute("level", String.valueOf(userinfo.get("level")));
        String accessId = String.valueOf(userinfo.get("accessId"));

        // 회원정보로 최고등급 조회
        url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId + "/maxdivision";
        ArrayList<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();
        resultListMap = (ArrayList<Map<String, Object>>) apicallutil.UseKeyArray(url, apiKey);
        String divisionType = String.valueOf(resultListMap.get(0).get("matchType"));
        String divisionGrade = String.valueOf(resultListMap.get(0).get("division"));

        for (int i = 0; i < resultListMap.size(); i++) {
            for (int j = 0; j < matchTypeMap.size(); j++) {
                if (resultListMap.get(i).get("matchType").equals(matchTypeMap.get(j).get("matchtype"))) {
                    session.setAttribute("matchtype" + i + "k", String.valueOf(matchTypeMap.get(j).get("desc")));
                    break;
                }
            }
            for (int j = 0; j < divisionMap.size(); j++) {
                if (resultListMap.get(i).get("division").equals(divisionMap.get(j).get("divisionId"))) {
                    session.setAttribute("division" + i + "k", String.valueOf(divisionMap.get(j).get("divisionName")));
                    break;
                }
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

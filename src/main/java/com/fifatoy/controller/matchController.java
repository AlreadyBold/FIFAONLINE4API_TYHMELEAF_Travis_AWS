package com.fifatoy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fifatoy.util.APICALLUTIL;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Match")
public class matchController {
    // API키
    @Value("${NexonApiKey}")
    private String apiKey;

    // UTIL
    APICALLUTIL apicallutil = new APICALLUTIL();

    // 매치타입 조회 URL
    String MatchTypeUrl = "https://static.api.nexon.co.kr/fifaonline4/latest/matchtype.json";

    @GetMapping("/MatchInfo")
    public String MatchInfo(HttpSession session, Model model, @RequestParam int code) {

        return "matchinfo";
    }

}

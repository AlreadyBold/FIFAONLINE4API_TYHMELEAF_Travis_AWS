package com.fifatoy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import com.fifatoy.util.APICALLUTIL;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Trade")
public class currentTradeController {
    // API키
    @Value("${NexonApiKey}")
    private String apiKey;
    // 유틸
    APICALLUTIL apicallutil = new APICALLUTIL();

    DecimalFormat df = new DecimalFormat("###,###");

    // 구매목록 조회
    @GetMapping("/buyInfo")
    public String buyInfo(HttpSession session, Model model) {
        // 구매 정보 불러오기
        String accessId = String.valueOf(session.getAttribute("accessId"));
        String type = "buy";
        model.addAttribute("BuyInfo", apicallutil.tradeInfo(accessId, type, apiKey));
        session.setAttribute("WHY", "BUY");

        // 매치타입 가져오기 API 호출 하여 맵에 저장
        String url = "https://static.api.nexon.co.kr/fifaonline4/latest/matchtype.json";
        ArrayList<Map<String, Object>> matchTypeMap = new ArrayList<Map<String, Object>>();
        matchTypeMap = (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);

        model.addAttribute("matchType", matchTypeMap);
        return "currentTradeinfo";
    }

    // 판매목록 조회
    @GetMapping("/sellInfo")
    public String sellInfo(HttpSession session, Model model) {
        // 구매 정보 불러오기
        String accessId = String.valueOf(session.getAttribute("accessId"));
        String type = "sell";

        model.addAttribute("BuyInfo", apicallutil.tradeInfo(accessId, type, apiKey));
        session.setAttribute("WHY", "SELL");

        // 매치타입 가져오기 API 호출 하여 맵에 저장
        String url = "https://static.api.nexon.co.kr/fifaonline4/latest/matchtype.json";
        ArrayList<Map<String, Object>> matchTypeMap = new ArrayList<Map<String, Object>>();
        matchTypeMap = (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);

        model.addAttribute("matchType", matchTypeMap);
        return "currentTradeinfo";
    }

}

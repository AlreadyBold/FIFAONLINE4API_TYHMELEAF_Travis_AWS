package com.fifatoy.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fifatoy.util.APICALLUTIL;

@Service
public class TradeService {
    APICALLUTIL apicallutil = new APICALLUTIL();

    /**
     * 이적시장 구매 및 판매 정보 조회
     * 
     * @accessId 유저고유식별자
     * @type BUY or SELL
     * @apiKey apikey
     * @date : 2023-07-15
     */
    public ArrayList<Map<String, Object>> treadInfoService (String accessId, String type, String apiKey ) {   // 공식경기 등급 가져오기 API 호출 하여 맵에 저장
        String url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId
        + "/markets?tradetype=" + type + "&offset=0&limit=100";

        return (ArrayList<Map<String, Object>>) apicallutil.UseKeyMapInArray(url, apiKey);
    }

    /**
     * 선수 시즌 정보 조회 
     * 
     * @date : 2023-07-15
     */
    public ArrayList<Map<String, Object>> seasonInfoService () {
        String url = "https://static.api.nexon.co.kr/fifaonline4/latest/seasonid.json";

        return (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);
    }

    /**
     * 선수 이름 정보 조회 
     * 
     * @date : 2023-07-15
     */
    public ArrayList<Map<String, Object>> spidInfoService () {
        String url = "https://static.api.nexon.co.kr/fifaonline4/latest/spid.json";

        return (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);
    }
     

}

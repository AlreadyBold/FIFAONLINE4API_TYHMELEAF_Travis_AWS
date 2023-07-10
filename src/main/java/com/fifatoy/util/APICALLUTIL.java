package com.fifatoy.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class APICALLUTIL {
    // DateTime
    DecimalFormat df = new DecimalFormat("###,###");

    /**
     * API키가 필요하지 않은 API 요청, 반환 데이터가 JSONObject 형식인 경우 ( API 주소 url )
     * 
     * @date : 2023-01-30
     */
    public Map<String, Object> NotKeyObject(String url) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONObject.class);
        return (Map<String, Object>) response.getBody();
    }

    /**
     * API키가 필요하지 않은 API 요청, 반환 데이터가 JSONArray 형식인 경우 ( API 주소 url )
     * 
     * @date : 2023-01-30
     */
    public ArrayList<Map<String, Object>> NotKeyArray(String url) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONArray.class);
        return (ArrayList<Map<String, Object>>) response.getBody();
    }

    /**
     * API키가 필요한 API 요청, 반환 데이터가 JSONObject 형식인 경우 ( API 주소 url, APIKEY )
     * 
     * @date : 2023-01-30
     */
    public Map<String, Object> UseKeyObject(String url, String apiKey) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONObject.class);
        return (Map<String, Object>) response.getBody();
    }

    /**
     * API키가 필요한 API 요청, 반환 데이터가 JSONArray 내부에 Map 형식인 경우 ( API 주소 url, APIKEY )
     * 
     * @date : 2023-01-30
     */
    public ArrayList<Map<String, Object>> UseKeyMapInArray(String url, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONArray.class);
        return (ArrayList<Map<String, Object>>) response.getBody();
    }

    /**
     * API키가 필요한 API 요청, 반환 데이터가 JSONArray 형식인 경우 ( API 주소 url, APIKEY )
     * 
     * @date : 2023-01-30
     */
    public ArrayList<Object> UseKeyArray(String url, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> tokenRequest = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, tokenRequest, JSONArray.class);
        return (ArrayList<Object>) response.getBody();
    }

    /**
     * 이적시장 구매 및 판매 조회
     * 
     * @accessId 유저고유식별자
     * @type BUY or SELL
     * @apiKey apikey
     * @date : 2023-02-01
     */
    public ArrayList<Map<String, Object>> tradeInfo(String accessId, String type, String apiKey) {
        // 엑세스아이디 / buy or sell / apikey
        APICALLUTIL apicallutil = new APICALLUTIL();
        // 유저 거래 정보 불러오기
        String url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId
                + "/markets?tradetype=" + type + "&offset=0&limit=100";
        ArrayList<Map<String, Object>> tradeInfoMap = new ArrayList<Map<String, Object>>();
        tradeInfoMap = (ArrayList<Map<String, Object>>) apicallutil.UseKeyMapInArray(url, apiKey);

        // 시즌 정보 불러오기
        url = "https://static.api.nexon.co.kr/fifaonline4/latest/seasonid.json";
        ArrayList<Map<String, Object>> seasonInfoMap = new ArrayList<Map<String, Object>>();
        seasonInfoMap = (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);

        // 선수 정보 불러오기
        url = "https://static.api.nexon.co.kr/fifaonline4/latest/spid.json";
        ArrayList<Map<String, Object>> spidMap = new ArrayList<Map<String, Object>>();
        spidMap = (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);

        ArrayList<Map<String, Object>> TradeInfo = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tradeInfoMap.size(); i++) {
            Map<String, Object> tradeInfo = new HashMap<String, Object>();
            // 거래날짜
            tradeInfo.put("tradeDate",
                    String.valueOf(tradeInfoMap.get(i).get("tradeDate").toString().substring(0,
                            10)));
            // 강화등급
            tradeInfo.put("grade",
                    String.valueOf(tradeInfoMap.get(i).get("grade").toString()));
            // 거래금액
            String value = df.format(tradeInfoMap.get(i).get("value"));
            tradeInfo.put("value", value);
            // 시즌정보
            String sn = tradeInfoMap.get(i).get("spid").toString().substring(0, 3);
            // 선수정보
            String sid = tradeInfoMap.get(i).get("spid").toString();

            for (int j = 0; j < seasonInfoMap.size(); j++) {
                if (sn.equals(String.valueOf(seasonInfoMap.get(j).get(
                        "seasonId")))) {
                    String classN = String.valueOf(seasonInfoMap.get(j).get("className"));
                    classN = classN.replace(" (", "-");
                    String[] arr = classN.split("-", 2);
                    tradeInfo.put("className", arr[0]);
                    break;
                }
            }
            for (int j = 0; j < spidMap.size(); j++) {
                if (sid.equals(spidMap.get(j).get("id").toString())) {
                    tradeInfo.put("name", String.valueOf(spidMap.get(j).get("name")));
                    if (tradeInfo.get("name").equals("")) {
                        tradeInfo.put("name", "삭제된 선수");
                    }
                    break;
                } else if (j + 1 == spidMap.size()) {
                    tradeInfo.put("name", "삭제된 선수");
                }
            }
            TradeInfo.add(tradeInfo);
        }
        return (ArrayList<Map<String, Object>>) TradeInfo;
    }

    public ArrayList<Object> matchInfo(String accessId, int type, String apiKey) {
        APICALLUTIL apicallutil = new APICALLUTIL();
        String url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId + "/matches?matchtype=" + type;

        log.info(url);
        // 10경기만
        ArrayList<Object> matchInfoArray = new ArrayList<Object>();
        matchInfoArray = apicallutil.UseKeyArray(url, apiKey);
        matchInfoArray.subList(0, 10);
        log.info(matchInfoArray);

        return matchInfoArray;
    }

    public ArrayList<Map<String, Object>> matchDetailInfo(ArrayList<Object> matchInfoArray, String apiKey,
            String accessId) {
        APICALLUTIL apicallutil = new APICALLUTIL();
        ArrayList<Map<String, Object>> matchDetailInfo = new ArrayList<>();
        // matchDate / matchResult / goalTotal ( int ) / controller
        // 상대선수 nickname / 상대선수 goalTotal( int )
        /*
         * matchDetailInfo.put("matchDate", " ");
         * matchDetailInfo.put("matchResult", " ");
         * matchDetailInfo.put("goalTotal", " ");
         * matchDetailInfo.put("controller", " ");
         * matchDetailInfo.put("otherPlayerNickname", " ");
         * matchDetailInfo.put("otherPlayerGoalTotal", " ");
         */

        for (int i = 0; i < 10; i++) {
            int userNo = 1;
            int otherUserNo = 0;
            Map<String, Object> deatilMap = new HashMap<String, Object>();

            String url = "https://api.nexon.co.kr/fifaonline4/v1.0/matches/" + matchInfoArray.get(i).toString();

            log.info(url);
            Map<String, Object> matches = new HashMap<String, Object>();
            matches = apicallutil.UseKeyObject(url, apiKey);

            // if문 시작
            ArrayList<Map<String, Object>> matchInfo = (ArrayList<Map<String, Object>>) matches.get("matchInfo");
            String validAccessId = (String) matchInfo.get(0).get("accessId");

            if (accessId.equals(validAccessId)) {
                userNo = 0;
                otherUserNo = 1;
            }

            String matchDate = (String) matches.get("matchDate").toString().substring(0, 10);

            String nickname = (String) matchInfo.get(userNo).get("nickname");

            String otherNickname = (String) matchInfo.get(otherUserNo).get("nickname");

            String matchResult = matchMapData("matchDetail", userNo, matchInfo).get("matchResult").toString();

            String goalTotal = matchMapData("shoot", userNo, matchInfo).get("goalTotal").toString();

            String otherGoalTotal = matchMapData("shoot", otherUserNo, matchInfo).get("goalTotal").toString();

            /*
             * log.info("matchDate = " + matchDate);
             * log.info(nickname + " VS " + otherNickname);
             * log.info("matchResult = " + matchResult);
             * log.info(goalTotal + " : " + otherGoalTotal);
             */

            deatilMap.put("matchDate", matchDate);
            deatilMap.put("names", nickname + " VS " + otherNickname);
            deatilMap.put("score", goalTotal + " : " + otherGoalTotal);
            deatilMap.put("result", matchResult);

            matchDetailInfo.add(deatilMap);
        }

        return matchDetailInfo;
    }

    public Map<String, Object> matchMapData(String key, int userNo, ArrayList<Map<String, Object>> matchInfo) {

        return (Map<String, Object>) matchInfo.get(userNo).get(key);
    }

}

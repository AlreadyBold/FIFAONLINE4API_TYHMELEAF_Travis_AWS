package com.fifatoy.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fifatoy.util.APICALLUTIL;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MatchService {

    APICALLUTIL apicallutil = new APICALLUTIL();

     /**
     * 매치 결과 조회
     * 
     * @accessId 유저고유식별자
     * @type 경기종류
     * @apiKey apikey
     * @date : 2023-07-10
     */
    public ArrayList<Object> matchInfo(String accessId, int type, String apiKey) {
        String url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId + "/matches?matchtype=" + type;

        return apicallutil.UseKeyArray(url, apiKey);
    }



    /**
     * Match MAP 내부데이터 꺼내기
     * 
     * @key 맵의 key 값
     * @userNo userNo
     * @matchInfo Map 을 가지고있는 Array
     * @date : 2023-07-10
     */
    public Map<String, Object> matchMapData(String key, int userNo, ArrayList<Map<String, Object>> matchInfo) {

        return (Map<String, Object>) matchInfo.get(userNo).get(key);
    }
}

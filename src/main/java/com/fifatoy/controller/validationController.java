package com.fifatoy.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fifatoy.util.APICALLUTIL;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/validation")
public class validationController {

    // API KEY 값
    @Value("${NexonApiKey}")
    private String apiKey;

    // UTIL
    APICALLUTIL apicallutil = new APICALLUTIL();

    @GetMapping("/nickname")
    @ResponseBody
    public String nicknamevalid(@RequestParam("name") String nickname) {
        // 닉네임으로 회원정보 조회
        String url = "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=" + nickname + "";
        try {
            Map<String, Object> userinfo = (Map<String, Object>) apicallutil.UseKeyObject(url, apiKey);
            return nickname;
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("/match")
    @ResponseBody
    public String matchvalid(HttpSession session, @RequestParam("matchType") String matchType) {
        // accessId , matchType으로 조회
        String accessId = session.getAttribute("accessId").toString();
        String url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId + "/matches?matchtype=" + matchType;

        ArrayList<Object> matchinfo = apicallutil.UseKeyArray(url, apiKey);
        log.info(matchinfo);
        if (matchinfo.isEmpty()) {
            return "FAIL";
        } else {
            return matchType;
        }

    }

}

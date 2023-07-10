package com.fifatoy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fifatoy.util.APICALLUTIL;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/membership")
public class membershipController {

    // API KEY 값
    @Value("${NexonApiKey}")
    private String apiKey;

    // UTIL
    APICALLUTIL apicallutil = new APICALLUTIL();

    @GetMapping("/nicknameoverlaptest")
    @ResponseBody
    public String nicknameOverlapTest(@RequestParam("nickname") String nickname) {
        // 닉네임으로 회원정보 조회
        String url = "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=" + nickname + "";
        Map<String, Object> userinfo = (Map<String, Object>) apicallutil.UseKeyObject(url, apiKey);
        // 리턴값
        if (userinfo.isEmpty()) {
            return "FAIL";
        } else {
            return "SUCC";
        }
    }
}

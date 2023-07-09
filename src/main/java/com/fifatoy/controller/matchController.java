package com.fifatoy.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fifatoy.util.APICALLUTIL;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/matchInfo")
@Log4j2
public class matchController {
    // API키
    @Value("${NexonApiKey}")
    private String apiKey;

    // UTIL
    APICALLUTIL apicallutil = new APICALLUTIL();


    @GetMapping()
    public String MatchInfo(HttpSession session, Model model, @RequestParam int code) {
        String accessId = (String) session.getAttribute("accessId");
        log.info(accessId);
        log.info(code);
        ArrayList<Object> matchInfoMap = apicallutil.matchInfo(accessId,code,apiKey);
        log.info(matchInfoMap);
        // date / detail 그리고 ..? 
        

        return "matchinfo";
    }

}

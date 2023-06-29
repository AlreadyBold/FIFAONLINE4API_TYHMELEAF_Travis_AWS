package com.fifatoy.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fifatoy.exception.DupException;
import com.fifatoy.exception.NoUserException;
import com.fifatoy.model.member;
import com.fifatoy.model.membersaveRequest;
import com.fifatoy.service.getMemberService;
import com.fifatoy.service.newMemberService;
import com.fifatoy.util.APICALLUTIL;

@Controller
@RequestMapping("/membership")
public class membershipController {

    // API KEY 값
    @Value("${NexonApiKey}")
    private String apiKey;

    // UTIL
    APICALLUTIL apicallutil = new APICALLUTIL();

    // JPA
    @Autowired
    private getMemberService getmemberservice;

    @Autowired
    private newMemberService newmemberservice;

    // logger
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/idoverlaptest")
    @ResponseBody
    public String idOverlapTest(@Param("id") String id) {
        try {
            member member = getmemberservice.getUser(id);
            return "FAIL";
        } catch (NoUserException e) {
            return "SUCC";
        }

    }

    @GetMapping("/nicknameoverlaptest")
    @ResponseBody
    public String nicknameOverlapTest(@Param("nickname") String nickname) {
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

    @PostMapping("/join")
    @ResponseBody
    public String memebershipJoin(@Param("id") String id, @Param("pwd") String pwd,
            @Param("nickname") String nickname) {
        membersaveRequest Membersaverequest = new membersaveRequest(id, nickname, pwd);
        try {
            newmemberservice.saveMember(Membersaverequest);
            logger.info("새 사용자 저장: {}", Membersaverequest.getid());
            return "index";
        } catch (DupException e) {
            logger.info(" 오류발생 ");
            return "join";
        }

    }
}

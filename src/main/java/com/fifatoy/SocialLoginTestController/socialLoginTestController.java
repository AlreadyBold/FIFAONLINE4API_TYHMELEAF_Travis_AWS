package com.fifatoy.SocialLoginTestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/social")
public class socialLoginTestController {

    /**
     * kakao callback
     * [GET] /social/kakaotest
     */
    @ResponseBody
    @GetMapping("/kakaotest")
    public void kakaoCallback(@RequestParam String code) {
        // 인가 코드 
        System.out.println(code);
    }


    /**
     * google callback
     * [GET] /social/googletest
     */
    @ResponseBody
    @GetMapping("/googletest")
    public void googleCallback(@RequestParam String code) {
        // 인가 코드 
        System.out.println(code);
    }


    /**
     * naver callback
     * [GET] /social/navertest
     */
    @ResponseBody
    @GetMapping("/navertest")
    public void naverCallback(@RequestParam String code) {
        // 인가 코드 
        System.out.println(code);
    }

}



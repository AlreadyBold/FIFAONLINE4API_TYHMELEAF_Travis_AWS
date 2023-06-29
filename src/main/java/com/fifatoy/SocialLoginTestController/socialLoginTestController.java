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
     * 카카오 callback
     * [GET] /social/kakaotest
     */
    @ResponseBody
    @GetMapping("/kakaotest")
    public void kakaoCallback(@RequestParam String code) {
        

        
        // 인가 코드 
        System.out.println(code);
    }
}



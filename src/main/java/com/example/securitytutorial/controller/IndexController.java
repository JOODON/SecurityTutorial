package com.example.securitytutorial.controller;

import com.example.securitytutorial.Entity.User;
import com.example.securitytutorial.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//Ioc ->Service , Repository , Entity 등등 객체의 생성 및 관리를 프레임 워크에게 넘기는 디자인 방법
//AutoWird or RequiredArgsConstructor 의존성 주입을 위한 어노테이션!
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final UserService userService;

    @GetMapping({" ","/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "USER";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "ADMON";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "MANAGER";
    }
    //스프링 시큐리티가 낚아채감
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String join(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String joinProc(User user){
        userService.saveUser(user);

        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_Manager') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }
}

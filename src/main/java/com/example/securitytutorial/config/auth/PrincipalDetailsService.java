package com.example.securitytutorial.config.auth;

import com.example.securitytutorial.Entity.User;
import com.example.securitytutorial.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//발생 하는 시점 시큐리티에서
//loginProcessingUrl("/login") 여기로 요청이 들어오면
//자동으로 타입의 IoC되어 있는 loadUserByUsername 실행

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserService userService;

    //시큐리티에서 session(Authentication(UserDetails)) 이런느낌임
    //session 내부에 Authentication 안에 UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //name 값이랑 똑같이 만들어주기
        User userEntity = userService.findByUsername(username);

        if (userEntity != null){

            //객체를 만들어서 리턴해주기
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}

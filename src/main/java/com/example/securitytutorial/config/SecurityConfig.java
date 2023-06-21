package com.example.securitytutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //웹 보안 기능을 사용하도록 활성화하는 특별한 어노테이션 이부분에서 로그인 처리하겠다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//secured 어노테이션 활성화(1개만 걸수있음) prePostEnabled 활성화 (2개 이상일때 사용가능)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 토큰을 비활성화합니다.
        http.csrf().disable();

        // HTTP 요청에 대한 인가 설정을 구성합니다.
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // "/user/**"로 시작하는 URL은 인증된 사용자만 접근할 수 있습니다.
                .antMatchers("/manager/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')") // "/manager/**"로 시작하는 URL은 "ROLE_ADMIN" 또는 "ROLE_MANAGER" 권한을 가진 사용자만 접근할 수 있습니다.
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // "/admin/**"로 시작하는 URL은 "ROLE_ADMIN" 권한을 가진 사용자만 접근할 수 있습니다.
                .anyRequest().permitAll() // 나머지 모든 요청에 대해 접근을 허용합니다.
                .and().formLogin().loginPage("/loginForm")//로그인 페이지는 login 으로 설정해줌
                .loginProcessingUrl("/login") //로그인 주소 가 호출되면 시큐리티가 낚아채서 대신 로그인을 해준다
                .defaultSuccessUrl("/");//로그인이 성공하면 가지는 곳!
    }

    //해당 메서드에 리턴되는 오브젝트를 IoC로 등록해준다
    //Ioc : @Service @Jpa 등등 처럼 의존성으로 호출이 가능한 녀석을 만함
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

}

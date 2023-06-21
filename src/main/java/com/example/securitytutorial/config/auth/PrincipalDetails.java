package com.example.securitytutorial.config.auth;
import com.example.securitytutorial.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//시큐리티가 /login 을 낚아채서 로그인을 진행시킴
//로그인이 진행이 완료가 되면 시큐리티 session 을 넣어줘야댐 ->
//어디에다가 저장? (Security ContextHolder)
//오브젝트 타입은 Authentication 임
//Authentication 안에 유저 정보가 있어야댐
//User 오브젝트 타입 -> UserDetails 타입임

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 유저의 권한을 리턴해줌
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        //지금까지 부여된 권한을 수집하거나 저장하기 위한 용도의 배열

        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        //User 에 Role 값을 가져와서 리턴하는 값을 추가해줌

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        /*
        이 메서드는 사용자 계정이 만료되었는지 여부를 확인합니다.
         만료된 계정은 더 이상 사용할 수 없으며, 일반적으로 계정 만료 시간을 기준으로 확인됩니다.
         */
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        /*
          이 메서드는 사용자 계정이 잠겨있는지 여부를 확인합니다.
          계정이 잠겨있을 경우 로그인을 시도할 수 없습니다.
          반적으로 계정 잠금은 관리자나 일정 횟수 이상의 실패한 로그인 시도로 인해 발생할 수 있습니다.
        */
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        /*
        이 메서드는 사용자의 인증 정보(비밀번호 등)의 만료 여부를 확인합니다.
        보안 상의 이유로 인증 정보는 일정 기간마다 갱신해야 할 수 있습니다.
        */
        return true;
    }

    @Override
    public boolean isEnabled() {
        /*
        이 메서드는 사용자 계정이 활성화되었는지 여부를 확인합니다.
         비활성화된 계정은 일반적으로 관리자에 의해 비활성화되거나 사용자의 요청에 따라 비활성화됩니다.
         */
        return true;

    }
}

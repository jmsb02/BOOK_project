package Book_Toy_Project.BOOK.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/**
 * SecurityConfig -> Spring Security 설정을 담당하는 클래스
 * -> 애플리케이션의 보안 설정 정의 및 사용자의 인증과 권한 부여 설정
 */

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    //SecurityFilterChain - HTTP 보안 설정 정의
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "signup").permitAll()
                        .anyRequest().permitAll()
                )
                //폼 로그인 설정 정의
                .formLogin(login -> login
                        .loginPage("/") // 사용자 정의 로그인 페이지 경로 지정
                        .permitAll() // 로그인 페이지에 대한 접근 허용
                )
                //CSRF 보호 비활성화
                .csrf().disable();
        return http.build();
    }

    //AuthenticationManager - 인증 매니저 구성
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        //DaoAuthenticationProvider - 데이터베이스 기반 인증 처리 인증 제공자
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        //ProviderManager - 인증 제공자 관리 매니저
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    //UserDetailsService - 사용자 세부 정보 관리
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        //InMemoryUserDetailsManager - 메모리 내 사용자 세부 정보 관리 서비스
        //생성한 UserDetails 객체를 사용하여 초기 사용자 정보 설정
        return new InMemoryUserDetailsManager(userDetails);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
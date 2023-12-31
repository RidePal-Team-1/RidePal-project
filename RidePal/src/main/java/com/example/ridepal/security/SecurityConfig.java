package com.example.ridepal.security;

import com.example.ridepal.services.CustomOAuth2UserService;
import com.example.ridepal.services.contracts.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oauthUserService;

    private final UserService userService;

    public SecurityConfig(CustomOAuth2UserService oauthUserService, UserService userService) {
        this.oauthUserService = oauthUserService;
        this.userService = userService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Public access
        http.authorizeHttpRequests(authz -> authz
                        //Public
                        .requestMatchers(
                                "/auth/login",
                                "/oauth/**",
                                "/auth/register",
                                "swagger-ui/index.html",
                                "/home",
                                "/static/js/**",
                                "/css/**",
                                "/assets/**",
                                "/playlists/{id}",
                                "/playlists").permitAll()
                        .requestMatchers("/playlists/{id}/update",
                                "/playlists/{id}/picture",
                                "/playlists/{id}/delete",
                                "/users/{id}",
                                "/users/{id}/update","/users","/users/{id}/delete").authenticated()
                        .requestMatchers("/users/{id}/delete").hasRole("ADMIN")
                        .anyRequest().permitAll())

                //TODO see if you are authenticated and try to login again if it works

                //Login
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .oauth2Login(oauth -> oauth
                        .loginPage("/auth/login")
                        .userInfoEndpoint(info -> info.userService(oauthUserService))
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                                userService.processOAuthPostLogin(oauthUser.getEmail());
                                response.sendRedirect("/home");
                            }
                        }))
                .logout(logout -> logout.logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login")
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)  ;
        http.httpBasic(Customizer.withDefaults());

        //Authenticated + Role

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.example.QuestBoard.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SpringSecurity class allows for replacing the default SpringSecurity options, such as the log-in page, registering
 * functions, logging out, how the current session behaves and what links are accessible by the currently logged-in user.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurity {
    //@Autowired
    //private UserDetailsService userDetailsService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Changes permissions for who can access certain links and complete certain requests.
     * @return the filter chain
     * @throws Exception internal exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**", "/login/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/users/**").hasRole("ADMIN")
                                .requestMatchers("/quests/**").authenticated()
                                .requestMatchers("/solutions").hasRole("ADMIN")
                                .requestMatchers("/solutions/**").authenticated()
                                .requestMatchers("/leaderboard/**").authenticated()
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/badges/**").hasRole("ADMIN")
                                .requestMatchers("/").permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/quests", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return httpSecurity.build();
    }

    /**
     * Globally configures the user details service that will be used (custom, here) and the password encoder.
     * @param authenticationManagerBuilder automatically builds an authentication manager with the given arguments
     * @throws Exception internal exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}

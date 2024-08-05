package com.learning.simran.prod_ready_feature_tutorial.configs;

import com.learning.simran.prod_ready_feature_tutorial.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers("/posts", "/error", "/auth/**").permitAll()
                      //.requestMatchers("/posts").hasRole("ADMIN")
                      .anyRequest().authenticated()) //any other request needs to be authenticated
              .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
              .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
              //.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  ---will avoid creating session
              //.formLogin(Customizer.withDefaults());  //This is for form login, when we use JWT, this , csrf and session wont be needed. this is only when we have UI for backened but if UI is different application, you will need JWT for authentication
        return httpSecurity.build();
    }

    //@Bean
    UserDetailsService myInMemoryUserDetailsService() {
        UserDetails normalUserDetails = User
                .withUsername("Simran")
                //.password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails adminUser = User
                .withUsername("Sahib")
                //.password(passwordEncoder().encode("manager"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normalUserDetails, adminUser);
    }

    @Bean
    AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

package com.example.demo.config;


import com.example.demo.security.CustomAuthenticationFilter;
import com.example.demo.security.CustomPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {

        //l'inizializzazione di questa classe fa si di creare un filtro di tutte le richieste proveniente dal pattern "/**" filtraggio che avviene grazie all'authenticationManager che farà passare solo le richieste che coicidono con le credeziali che ha al suo interno!
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(new AntPathRequestMatcher("/**"));
        customAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return customAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http

                //questo filter praticamente richiama la classe CustomAuthenticationFilter, inizializzata sopra, in modo tale di intercettare la richiesta http (Data nel pattern presumo) eseguendo l'autenticazione con i campi api-key api-secret presenti dentro la stessa
                .addFilterBefore(customAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //csrf blocca tutte le richieste proveniente da altri domini che altrimenti verrebbero eseguiti
                .csrf(csrf -> {
                    csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/**"));
                    csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/**", HttpMethod.DELETE.name()));
                })
                .authorizeHttpRequests(auth -> {
                    //in questa parte vengono gestite le richieste che posso essere autorizzate nello specifico get. Mentre tutte le altre richiedono l'autenticazione
                            auth
                                    .anyRequest()
                                    .authenticated();
                                                })
                .headers(h -> {
                    h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                //questa parte richiede le credenziali di accesso tramite pop-up solitamente dal browser o dall'header della richiesta
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return CustomPasswordEncoderFactories.createDelatingPasswordEncoder();
    }
}

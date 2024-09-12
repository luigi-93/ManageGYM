package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * Il filtro personalizzato CustomAuthenticationFilter è progettato per autenticare gli utenti tramite una
 * logica specifica basata sulle credenziali fornite negli header HTTP.
 * Estendendo AbstractAuthenticationProcessingFilter, eredita il comportamento base di un filtro di
 * autenticazione,
 * e implementa metodi specifici come attemptAuthentication e doFilter per fornire una logica personalizzata.
 */

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    //L'oggetto di tipo RequestMatcher come dice la parola stessa è responsabile di determinare quali richieste devono essere gestite da questo filtro.
    public CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    //è un pò il cuore di tutto perchè filtra le richieste che gli arrivano e viene chiamato ogni qual volta la richiesta corrisponde al pattern specificato dal RequestMatcher (la query della nostra chiamata).
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse =(HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //quindi qui sotto da un tentavo di richiesta di autenticazione con il metodo attemptAuthentication
        //Se fallisce o non è necessario autenticare, chiama il successivo filtro nella catena con chain.doFilter.
        try {
            Authentication authenticationResult = attemptAuthentication(httpRequest, httpResponse);
            if (authenticationResult != null) {
                successfulAuthentication(httpRequest, httpResponse, chain, authenticationResult);
            } else {
                chain.doFilter(httpRequest, httpResponse);
            }
        } catch (AuthenticationException ex) {
            unsuccessfulAuthentication(httpRequest, httpResponse, ex);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        SecurityContextHolder.clearContext();
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String username = getUsername(request);
        String password = getPassword(request);

        //se username e password non sono vuote crea un token e lo manda al getAuthenticationManager
        if (username == null) {
            username = "";
        } if (password == null) {
            password = "";
        } if (ObjectUtils.isEmpty(username)) {
            return null;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }


}

package com.example.demo.security;

import com.example.demo.model.security.Authority;
import com.example.demo.model.security.User;
import com.example.demo.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailsServices implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    //questo metodo è fondamentale in spring security e viene utilizzato per caricare i dati di un utente tramite il suo nome utente, se non lo trova ovviamente restituisce un errore.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("User loading {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        //una volta creato carica tutte le sue informazioni compreso le autorizzazioni che ha
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAccountNonLocked(),
                user.getAccountNonExpired(),
                user.getCredentialNonExpired(),
                user.getEnabled(),
                convertSpringAuthority(user.getAuthorities())
        );
    }

    //questo metodo converte un Set di tipo oggetto Authority in una collezione di oggetti GrantedAuthority ap patto che non sia stato già instanziato in tal caso infatti ritorno hashset già presente.
    private Collection<? extends GrantedAuthority> convertSpringAuthority(Set<Authority> authorities) {
        if (authorities != null && !authorities.isEmpty()) {

            //il ritorno viene gestito in questo modo, si estrae da prima il permesso all'interno di authority ognuno dei quali viene trasformato in un oggetto SimplGranteAuthory necessario per la gestione dei permessi degli utenti
            return authorities
                    .stream()
                    //quando si fanno due map il secondo map utilizza l'oggetto estratto dal primo map, in questo caso getPermission (string)
                    .map(Authority::getPermission)
                    //classe compatibile con la classe di spring security, in questo modo con il metodo reference (::) inizializzando il SimpleGrantedAuthority con ciò che gli arriva dal map precedente
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

}

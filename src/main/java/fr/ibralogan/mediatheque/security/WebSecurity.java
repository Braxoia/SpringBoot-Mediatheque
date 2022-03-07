package fr.ibralogan.mediatheque.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static fr.ibralogan.mediatheque.security.SecurityConstants.SIGN_URL;

/**
 * Classe configurant la sécurité en faisant le lien avec tous les composants créés et le framework
 */
//Annotation signalant que c'est cette classe qui configure la sécurité
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Configure les routes qui seront protégées
     * @param http objet comportant toutes les données de requêtes HTTP
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //toutes les routes sont secure par défaut sauf ceux qu'on rajoutera à la main, donc l'utilisateur doit être authentifié
		http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_URL).permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                .anyRequest().authenticated() //touutes les autres requêtes à l'api sont protégées
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                //pas besoin de session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * On passe notre de service de récupération d'utilisateurs au framework
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}

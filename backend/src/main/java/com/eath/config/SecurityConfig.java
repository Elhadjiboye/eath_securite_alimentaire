package com.eath.config;

import com.eath.Service.UtilisateurAdministrateurVueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UtilisateurAdministrateurVueService utilisateurService;

    public SecurityConfig(UtilisateurAdministrateurVueService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilisation de BCrypt pour le hachage des mots de passe
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Désactive la protection CSRF si vous utilisez JWT ou une autre méthode de sécurisation
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/**").permitAll() // Permet l'accès public aux routes d'inscription et de connexion
                                .requestMatchers("/api/prodIngredients/**").permitAll()
                                .requestMatchers("/api/historique-scans/**").permitAll()
                                .requestMatchers("/api/types-produits/**").permitAll()
                                .requestMatchers("/api/normes-halal/**").permitAll()
                                .requestMatchers("/api/produits/**").permitAll()
                                .requestMatchers("/api/ingredients/**").permitAll()
                                .requestMatchers("/api/produits-ingredients/**").permitAll()
                                .requestMatchers("/api/allergenes/**").permitAll()
                                .requestMatchers("/api/substances-nocives/**").permitAll()
                                .requestMatchers("/api/informations-nutritionnelles/**").permitAll()
                                .requestMatchers("/api/commentaires/**").permitAll()
                                .requestMatchers("/api/users/**").permitAll()
                                .requestMatchers("/webhook/**").permitAll()
                                .requestMatchers("/audio/**").permitAll()
                                .requestMatchers("/text-to-speech/**").permitAll() // Ajout de la route pour le service de synthèse vocale
                                .requestMatchers("/api/resultats-analyse/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()

                                .anyRequest().authenticated() // Requiert une authentification pour toutes les autres requêtes
                )
                .httpBasic(); // Utilisation de l'authentification HTTP Basic
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> utilisateurService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }
}

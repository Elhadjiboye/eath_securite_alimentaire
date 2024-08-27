package com.eath.web;

import com.eath.Service.AdministrateurService;
import com.eath.Service.UtilisateurAdministrateurVueService;
import com.eath.Service.UtilisateursService;
import com.eath.dao.RoleRepository;
import com.eath.entite.Administrateur;
import com.eath.entite.Role;
import com.eath.entite.Utilisateurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateursService utilisateursService;

    @Autowired
    private AdministrateurService administrateurService;

    @Autowired
    private UtilisateurAdministrateurVueService utilisateurAdministrateurVueService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Utilisateurs user) {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        user.setRoles(Set.of(userRole));
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse())); // Encodez le mot de passe
        utilisateursService.creerUtilisateur(user);

        return ResponseEntity.ok("User registered successfully with USER role");
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Administrateur admin) {
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));

        admin.setRoles(Set.of(adminRole));
        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse())); // Encodez le mot de passe
        administrateurService.addAdministrateur(admin);

        return ResponseEntity.ok("Admin registered successfully with ADMIN role");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getMotDePasse())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
                return "Login successful. Welcome, Admin!";
            } else if (authorities.contains(new SimpleGrantedAuthority("USER"))) {
                return "Login successful. Welcome, User!";
            } else {
                return "Login successful.";
            }
        } catch (AuthenticationException e) {
            return "Login failed: " + e.getMessage();
        }
    }
}

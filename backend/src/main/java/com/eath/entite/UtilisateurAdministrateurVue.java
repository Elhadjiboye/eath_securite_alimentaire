package com.eath.entite;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "utilisateur_administrateur_vue") // Nom de la vue
public class UtilisateurAdministrateurVue implements UserDetails {

    @Id
    @Column(name = "id_personne")
    private Integer id;

    @Column(name = "nom_personne")
    private String nom;

    @Column(name = "prenom_personne")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "mot_de_passe") // Assurez-vous que le nom correspond à celui de la vue
    private String motDePasse;

    @Column(name = "date_creation")
    private Timestamp dateCreation;

    @Column(name = "photo_de_profil")
    private String photoDeProfil;

    @Column(name = "taille")
    private BigDecimal taille;

    @Column(name = "poids")
    private BigDecimal poids;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "conditions_medicales")
    private String conditionsMedicales;

    //@Column(name = "preference")
    //private String preference;

    //@Column(name = "niveau_abonnement")
   // private String niveauAbonnement;

    @Column(name = "date_modification") // Assurez-vous que le nom correspond à celui de la vue
    private Timestamp utilisateurDateModification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id") // Assurez-vous que le nom correspond à celui de la vue
    private Role role;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

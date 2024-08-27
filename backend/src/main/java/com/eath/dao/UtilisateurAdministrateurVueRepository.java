package com.eath.dao;

import com.eath.entite.UtilisateurAdministrateurVue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UtilisateurAdministrateurVueRepository extends JpaRepository<UtilisateurAdministrateurVue, Integer> {
    Optional<UtilisateurAdministrateurVue> findByEmail(String email);
}
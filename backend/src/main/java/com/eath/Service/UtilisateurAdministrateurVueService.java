package com.eath.Service;

import com.eath.entite.UtilisateurAdministrateurVue;


import java.util.Optional;


public interface UtilisateurAdministrateurVueService  {
    Optional<UtilisateurAdministrateurVue> findByEmail(String email);
}

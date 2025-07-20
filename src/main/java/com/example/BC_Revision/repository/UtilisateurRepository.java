package com.example.BC_Revision.repository;

import com.example.BC_Revision.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
Optional<Utilisateur> findByUsername(String username);
Optional<Utilisateur> findByEmail(String email);
}

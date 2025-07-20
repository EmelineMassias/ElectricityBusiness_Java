package com.example.BC_Revision.repository;

import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorneRepository extends JpaRepository<Borne, Long>{
    List<Borne> findByUtilisateur(Utilisateur utilisateur);
}
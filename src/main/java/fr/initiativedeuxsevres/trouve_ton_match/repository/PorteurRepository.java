package fr.initiativedeuxsevres.trouve_ton_match.repository;

import fr.initiativedeuxsevres.trouve_ton_match.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PorteurRepository extends JpaRepository <Utilisateur, Long> {

}
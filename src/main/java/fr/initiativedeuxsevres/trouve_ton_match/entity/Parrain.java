package fr.initiativedeuxsevres.trouve_ton_match.entity;

import fr.initiativedeuxsevres.trouve_ton_match.enums.TypeUtilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
//@NoArgsConstructor
@SuperBuilder
@Table(name= "parrain")
public class Parrain extends Utilisateur {

    /**
     * Propriétés de la classe
     */
    @Column(name = "presentation_parcours")
    private String presentationParcours;

    @Column(name = "branches_reseau")
    private String branchesReseau;

    @Column(name = "domaines_expertise")
    private String domainesExpertise;

    @Column(name = "secteur_geographique")
    private String secteurGeographique;

    @Column(name = "disponibilites")
    private String disponibilites;

//    @Builder.Default
//    private String type = Parrain.class.getSimpleName();
//    private TypeUtilisateur type;

    public Parrain(){
        super();
    }
    /**
     * Constructeur avec tous les paramètres de la classe mère
     * @param idUtilisateur
     * @param nomUtilisateur
     * @param prenomUtilisateur
     * @param entrepriseUtilisateur
     * @param plateformeUtilisateur
     * @param codeUtilisateur
     * @param presentationParcours
     * @param branchesReseau
     * @param domainesExpertise
     * @param secteurGeographique
     * @param disponibilites
     */

    public Parrain(Long idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String entrepriseUtilisateur,
                   String plateformeUtilisateur, String codeUtilisateur, String presentationParcours,
                   String branchesReseau, String domainesExpertise, String secteurGeographique, String disponibilites) {

        // Appel au constructeur de la classe mère
        super(idUtilisateur, nomUtilisateur, prenomUtilisateur, entrepriseUtilisateur, plateformeUtilisateur, codeUtilisateur, "parrain");

        this.presentationParcours = presentationParcours;
        this.branchesReseau = branchesReseau;
        this.domainesExpertise = domainesExpertise;
        this.secteurGeographique = secteurGeographique;
        this.disponibilites = disponibilites;
    }

}

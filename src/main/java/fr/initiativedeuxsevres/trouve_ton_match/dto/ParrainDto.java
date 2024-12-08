package fr.initiativedeuxsevres.trouve_ton_match.dto;

import fr.initiativedeuxsevres.trouve_ton_match.entity.Parrain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
//  Indique que les méthodes equals et hashCode ne doivent pas inclure les champs de la superclasse (qui peut poser problème en cas d'héritage de classe).
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ParrainDto extends UtilisateurDto {

    /**
     * Propriétés
     */
    private String presentationParcours;
    private String branchesReseau;
    private String domainesExpertise;
    private String secteurGeographique;
    private String disponibilites;

    /**
     * Constructeur d'un parrain (compte utilisateur + paramètres propres au parrains)
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
    public ParrainDto(Long idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String entrepriseUtilisateur,
                      String plateformeUtilisateur, String codeUtilisateur, String presentationParcours,
                      String branchesReseau, String domainesExpertise, String secteurGeographique, String disponibilites) {


        // propriétés de la classe mère
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.entrepriseUtilisateur = entrepriseUtilisateur;
        this.plateformeUtilisateur = plateformeUtilisateur;
        this.codeUtilisateur = codeUtilisateur;
        this.typeUtilisateur = "parrain";

        // propriétés de la classe fille
        this.presentationParcours = presentationParcours;
        this.branchesReseau = branchesReseau;
        this.domainesExpertise = domainesExpertise;
        this.secteurGeographique = secteurGeographique;
        this.disponibilites = disponibilites;
    }

    /**
     * Contructeur d'un utilisateur de type : Parrain
     * @param utilisateurDto
     */
    public ParrainDto(UtilisateurDto utilisateurDto)
    {
        this.idUtilisateur = utilisateurDto.getIdUtilisateur();
        this.nomUtilisateur = utilisateurDto.getNomUtilisateur();
        this.prenomUtilisateur = utilisateurDto.getPrenomUtilisateur();
        this.entrepriseUtilisateur = utilisateurDto.getEntrepriseUtilisateur();
        this.plateformeUtilisateur = utilisateurDto.getPlateformeUtilisateur();
        this.codeUtilisateur = utilisateurDto.getCodeUtilisateur();
        this.typeUtilisateur = "parrain";

    }

    // Constructeur qui accepte un objet Parrain
    public ParrainDto(Parrain parrain) {
        this.idUtilisateur = parrain.getIdUtilisateur();
        this.nomUtilisateur = parrain.getNomUtilisateur();
        this.prenomUtilisateur = parrain.getPrenomUtilisateur();
        this.entrepriseUtilisateur = parrain.getEntrepriseUtilisateur();
        this.plateformeUtilisateur = parrain.getPlateformeUtilisateur();
        this.codeUtilisateur = parrain.getCodeUtilisateur();
        this.typeUtilisateur = "parrain";
        this.presentationParcours = parrain.getPresentationParcours();
        this.branchesReseau = parrain.getBranchesReseau();
        this.domainesExpertise = parrain.getDomainesExpertise();
        this.secteurGeographique = parrain.getSecteurGeographique();
        this.disponibilites = parrain.getDisponibilites();
    }

}

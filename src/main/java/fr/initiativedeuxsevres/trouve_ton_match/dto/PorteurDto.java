package fr.initiativedeuxsevres.trouve_ton_match.dto;

import fr.initiativedeuxsevres.trouve_ton_match.entity.Porteur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
//  Indique que les méthodes equals et hashCode ne doivent pas inclure les champs de la superclasse (qui peut poser problème en cas d'héritage de classe).
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PorteurDto extends UtilisateurDto {

    /**
     * Propriétés
     */
    private String dateLancement;
    private String domaine;
    private String descriptifActivite;
    private String besoins;
    private String lieuActivite;
    private String disponibilites;

    /**
     * Constructeur d'un porteur (compte utilisateur + paramètres propres au porteur)
     * @param idUtilisateur
     * @param nomUtilisateur
     * @param prenomUtilisateur
     * @param entrepriseUtilisateur
     * @param plateformeUtilisateur
     * @param codeUtilisateur
     * @param dateLancement
     * @param domaine
     * @param descriptifActivite
     * @param besoins
     * @param lieuActivite
     * @param disponibilites
     */
    public PorteurDto(Long idUtilisateur, String nomUtilisateur, String prenomUtilisateur,
                            String entrepriseUtilisateur,
                            String plateformeUtilisateur, String codeUtilisateur, String typeUtilisateur, String dateLancement,
                            String domaine, String descriptifActivite, String besoins, String lieuActivite, String disponibilites) {

        // propriétés de la classe mère
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.entrepriseUtilisateur = entrepriseUtilisateur;
        this.plateformeUtilisateur = plateformeUtilisateur;
        this.codeUtilisateur = codeUtilisateur;
        this.typeUtilisateur = typeUtilisateur;

        // propriétés de la classe fille
        this.dateLancement = dateLancement;
        this.domaine = domaine;
        this.descriptifActivite = descriptifActivite;
        this.besoins = besoins;
        this.lieuActivite = lieuActivite;
        this.disponibilites = disponibilites;

    }

    /**
     * Contructeur d'un utilisateur de type : Porteur
     * @param utilisateurDto
     */
    public PorteurDto(UtilisateurDto utilisateurDto)
    {
        this.idUtilisateur = utilisateurDto.getIdUtilisateur();
        this.nomUtilisateur = utilisateurDto.getNomUtilisateur();
        this.prenomUtilisateur = utilisateurDto.getPrenomUtilisateur();
        this.entrepriseUtilisateur = utilisateurDto.getEntrepriseUtilisateur();
        this.plateformeUtilisateur = utilisateurDto.getPlateformeUtilisateur();
        this.codeUtilisateur = utilisateurDto.getCodeUtilisateur();

    }

    // Constructeur qui accepte un objet Porteur
    public PorteurDto(Porteur porteur) {
        this.idUtilisateur = porteur.getIdUtilisateur();
        this.nomUtilisateur = porteur.getNomUtilisateur();
        this.prenomUtilisateur = porteur.getPrenomUtilisateur();
        this.entrepriseUtilisateur = porteur.getEntrepriseUtilisateur();
        this.plateformeUtilisateur = porteur.getPlateformeUtilisateur();
        this.codeUtilisateur = porteur.getCodeUtilisateur();
        this.dateLancement = porteur.getDateLancement();
        this.domaine = porteur.getDomaine();
        this.descriptifActivite = getDescriptifActivite();
        this.besoins = porteur.getBesoins();
        this.lieuActivite = porteur.getLieuActivite();
        this.disponibilites = porteur.getDisponibilites();
    }
}


